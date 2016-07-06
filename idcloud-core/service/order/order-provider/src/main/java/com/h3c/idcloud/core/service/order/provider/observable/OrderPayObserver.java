package com.h3c.idcloud.core.service.order.provider.observable;

import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.persist.charge.dao.ServiceBillMapper;
import com.h3c.idcloud.core.persist.order.dao.OrderMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstResMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstanceMapper;
import com.h3c.idcloud.core.persist.system.dao.LogRecordMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.charge.ServiceBill;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResFloatingIpService;
import com.h3c.idcloud.core.service.res.api.ResVdService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.infra.log.observable.ActionTraceObservable;
import com.h3c.idcloud.infrastructure.common.constants.BusinessMessageConstants;
import com.h3c.idcloud.infrastructure.common.constants.UuidConstants;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * order pay 观察者模式
 * @author swq
 *
 */
public class OrderPayObserver implements Observer  {

	private static Logger logger = LoggerFactory.getLogger(OrderPayObserver.class);
	private static volatile OrderPayObserver orderPayObserver = null;
	private static volatile boolean isLock = false;

	public OrderPayObserver(OrderPayObservable orderPayObservable) {
		orderPayObservable.addObserver(this);
	}
	private OrderPayObserver(){}

	/**
	 * 单例 get OrderPayObserver
	 * @return
     */
	public static OrderPayObserver getOrderPayObserver(){
        if(orderPayObserver == null){
            synchronized (OrderPayObserver.class) {
                if(orderPayObserver == null){
					orderPayObserver = new OrderPayObserver();
                }
            }
        }
        return orderPayObserver;
	}   
	
	/**
	 * 订单支付主要处理方法
	 * @param orderPayObservable
	 */
	@Transactional
	public static void orderObserverConsumer(OrderPayObservable orderPayObservable){
		logger.debug("OrderPayObserver------------orderObserverConsumer started isLock="+isLock+" actionTraceObservable="+orderPayObservable.hashCode());
		if(!isLock){
			while(true){
				isLock = true;
				Order order = orderPayObservable.getOrderQueue().poll();
				String orderId = order.getOrderId();
				logger.debug("OrderPayObserver------------orderObserverConsumer orderId="+ JsonUtil.toJson(orderId)+" start");
				logger.debug("OrderPayObserver------------orderObserverConsumer orderId="+ JsonUtil.toJson(orderId)+" LogQueue size="+orderPayObservable.getOrderQueue().size());
				try{
					List<OrderDetail> orderDetails = order.getOrderDetail();
					OrderService orderService = SpringContextHolder.getBean("orderServiceImpl");
					ServiceInstanceService serviceInstanceService = orderService.getServiceInstanceService();
					BillingAccountService billingAccountService = SpringContextHolder.getBean("billingAccountServiceImpl");
					OrderMapper orderMapper = SpringContextHolder.getBean("orderMapper");
					ServiceInstanceMapper serviceInstanceMapper = SpringContextHolder.getBean("serviceInstanceMapper");

					Order checkOrder = orderMapper.selectOrderByOrderId(orderId);
                    //check状态
					if(!checkOrder.getStatus().equals(WebConstants.OrderStatusCd.NO_PAY))
						throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(
								BusinessMessageConstants.OrderMessage.ORDER_STATUS_ERROR));
                    //check余额
					boolean isCheck = billingAccountService.checkAccountBalance(order.getAmount(),order.getAuthUserInfo().getUserSid());
					if(!isCheck)
						throw new RpcException(RpcException.BIZ_EXCEPTION,WebUtil.getMessage(BusinessMessageConstants.OrderMessage.ACCOUNT_BALANCE_ERROR));

					//扣款
					billingAccountService.updateAccount(order.getAuthUserInfo().getUserSid(),order.getAmount(),"03");

					checkOrder.setStatus(WebConstants.OrderStatusCd.PAYED);
					checkOrder.setTimePurchase(new Date());
					BillingAccount billingAccount = billingAccountService.selectByUserId(order.getAuthUserInfo().getUserSid());
					if(StringUtil.isNullOrEmpty(billingAccount))
						throw new RpcException(RpcException.BIZ_EXCEPTION,"can' find billing account info! by user sid="+order.getAuthUserInfo().getUserSid());
					orderMapper.updateByPrimaryKey(checkOrder);
					//生成服务实例
					for (OrderDetail orderDetail : orderDetails) {
						ServiceInstance serviceInstance = new ServiceInstance();
						serviceInstance.setServiceSid(orderDetail.getServiceSid());
						serviceInstance.setTemplateSid(orderDetail.getTemplateSid());
						serviceInstance.setBillingType(orderDetail.getBillingType());
						serviceInstance.setOwnerId(order.getAuthUserInfo().getAccount());
						serviceInstance.setOrderId(orderDetail.getOrderId());
						serviceInstance.setDetailSid(orderDetail.getDetailSid());
						serviceInstance.setBuyLength(orderDetail.getBuyLength());
						serviceInstance.setMgtObjSid(order.getAuthUserInfo().getMgtObjSid());
						// 服务开通时间为空
						serviceInstance.setDredgeDate(null);
						// 服务到期时间   需要资源开通后根据购买时常更新此事件数据
						serviceInstance.setExpiringDate(null);
						// 实例创建开始结束时间
						serviceInstance.setCreationDateBegin(new Date());
						serviceInstance.setCreationDateEnd(new Date());
						serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.PENDING);
						Map<String,Object> specificationMap = JsonUtil.fromJson(orderDetail.getSpecification(),HashMap.class);
						for (int i = 0; i < orderDetail.getQuantity(); i++) {

							String insNameID = UuidUtil.getShortUuid(orderDetail.getServiceCode()+"-");
							serviceInstance.setInstanceId(insNameID);
							serviceInstance.setInstanceName(insNameID.replace("-",""));
							String region = specificationMap.get(WebConstants.InstanceSpecType.REGION).toString();
							serviceInstance.setRegionSid(region);
							String zone = specificationMap.get(WebConstants.InstanceSpecType.ZONE).toString();
							serviceInstance.setZoneSid(zone);
							//价格 当前订单详情价格/数量
							BigDecimal amount = orderDetail.getAmount().divide(new BigDecimal(orderDetail.getQuantity())).setScale(2,BigDecimal.ROUND_HALF_UP);
							serviceInstance.setAmount(amount);
							serviceInstance.setSpecification(orderDetail.getSpecification());
							WebUtil.prepareInsertParams(serviceInstance,order.getAuthUserInfo());
//							serviceInstanceMapper.insertSelective(serviceInstance);
							// 调用资源层开通服务
							ResCommonInst resCommonInst = new ResCommonInst();
							resCommonInst.setMgtObjSid(order.getAuthUserInfo().getMgtObjSid());
							resCommonInst.setUserAccount(order.getAuthUserInfo().getAccount());
							resCommonInst.setUserPass(order.getAuthUserInfo().getPassword());
							resCommonInst.setZoneId(zone);
							resCommonInst.setInstId(insNameID);
							resCommonInst.setResSpecParam(orderDetail.getSpecification());
							//资源层异常消耗
							//TODO 此处调用后续会优化为泛用反射自动化调用相关服务
							ResInstResult resInstResult = invokeResService(orderDetail.getServiceCode(),resCommonInst,orderService);
//							ResInstResult resInstResult = new ResInstResult(ResInstResult.SUCCESS);
//							Map<String,String> testMap = new HashMap<>();
//							testMap.put("4ec70557-02d3-11e6-852e-0242ac117777","RES-VM");
//							resInstResult.setData(testMap);
							if (ResInstResult.SUCCESS == resInstResult.getStatus()) {
								Map<String,String> resMap = (Map<String,String>)resInstResult.getData();
								User user = new User();
								user.setUserSid(order.getAuthUserInfo().getUserSid());
								user.setAccount(order.getAuthUserInfo().getAccount());
								user.setMgtObjSid(order.getAuthUserInfo().getMgtObjSid());
								serviceInstanceService.openServiceSuccessHandle(resMap,serviceInstance,user,false);
							}else{
								//保存服务实例数据
								serviceInstanceMapper.insertSelective(serviceInstance);
								//TODO 重试调用 or  自动发送失败工单
								logger.error("OrderPayObserver------------orderObserverConsumer OrderId="+orderId+" RESException instanceName="+insNameID+" message="+ StringUtil.nullToEmpty(resInstResult.getMessage()));
							}


							logger.debug("OrderPayObserver------------orderObserverConsumer orderId="+ JsonUtil.toJson(orderId)+" instance name="+insNameID);
						}
					}
					logger.debug("OrderPayObserver------------orderObserverConsumer orderId="+ JsonUtil.toJson(orderId)+" end");
				}catch (RpcException rpce){
					rpce.printStackTrace();
					logger.error("OrderPayObserver------------orderObserverConsumer orderId="+orderId+" RpcException message="+rpce.getMessage());
				}catch (Exception e){
					e.printStackTrace();
					logger.error("OrderPayObserver------------orderObserverConsumer OrderId="+orderId+" Exception message="+e.getMessage());
				}
				if(orderPayObservable.getOrderQueue().isEmpty()){
					logger.debug("OrderPayObserver------------orderObserverConsumer OrderId="+orderId+" isEmpty");
					isLock = false;
					break;
				}
				
			}
		}
		
	}


	/**
	 * 暂时使用此方式，后续会优化为自动反射到相应的服务调用方法上
	 * @param serviceCode 服务code
	 * @param resCommonInst 资源层公共参数
	 * @return
	 * @throws Exception
     */
	private static ResInstResult invokeResService(String serviceCode,ResCommonInst resCommonInst,OrderService orderService){
		ResVmService resVmService = orderService.getResVmService();
		ResFloatingIpService resFloatingIpService = orderService.getResFloatingIpService();
		ResVdService resVdService = orderService.getResVdService();
		ResInstResult resInstResult = null;
		//TODO 暂时使用此方式，后续会优化为自动反射到相应的服务调用方法上
		switch (serviceCode){
			case WebConstants.ServiceCode.CS:
				resInstResult = resVmService.createVm(resCommonInst);
				break;
			case WebConstants.ServiceCode.EIP:
				resInstResult = resFloatingIpService.applyFloatingIP(resCommonInst);
				break;
			case WebConstants.ServiceCode.CBS:
				resInstResult = resVdService.createVd(resCommonInst);
				break;
			default:
				resInstResult = new ResInstResult();
				resInstResult.setStatus(ResInstResult.FAILURE);
				resInstResult.setMessage("validate error service code "+serviceCode);
				break;
		}
		return resInstResult;
	}

	@SuppressWarnings("static-access")
	public void update(Observable o, Object arg) {
		OrderPayObservable orderPayObservable = (OrderPayObservable)o;
		Queue<Order> orderQueue = orderPayObservable.getOrderQueue();
		logger.debug("OrderPayObserver--------------------------------------update invoke queue size="+orderQueue.size());
		OrderPayObserver.getOrderPayObserver().orderObserverConsumer(orderPayObservable);
	}

}
