package com.h3c.idcloud.core.service.order.provider;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idclod.core.service.activiti.api.ActivitiService;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountMapper;
import com.h3c.idcloud.core.persist.charge.dao.BillingPlanMapper;
import com.h3c.idcloud.core.persist.charge.dao.BillingPlanSpecMapper;
import com.h3c.idcloud.core.persist.order.dao.OrderDetailMapper;
import com.h3c.idcloud.core.persist.order.dao.OrderMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceDefineMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstanceMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPlan;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPlanSpec;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.pojo.vo.order.UserOrderVo;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.charge.api.BillingPlanService;
import com.h3c.idcloud.core.service.order.api.OrderDetailService;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.order.provider.observable.OrderPayObservable;
import com.h3c.idcloud.core.service.product.api.ServiceConfigService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.res.api.ResFloatingIpService;
import com.h3c.idcloud.core.service.res.api.ResVdService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.core.service.system.api.SysconfigService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infra.log.aspect.ActionTrace;
import com.h3c.idcloud.infra.log.observable.ActionTraceObservable;
import com.h3c.idcloud.infrastructure.common.constants.BusinessMessageConstants;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.exception.ServiceException;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.BaseServiceObj;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.*;

import com.rabbitmq.client.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//import java_cup.internal_error;

@Service(version = "1.0.0")
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Reference(version = "1.0.0")
    private ActivitiService activitiService;

    /**
     * 订单详情Service
     */
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 资费计划Service
     */
    @Autowired
    private BillingPlanService billingPlanService;

    @Reference(version = "1.0.0")
    private SidService sidService;

    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;

    @Reference(version = "1.0.0")
    private SysconfigService sysconfigService;

    @Reference(version = "1.0.0")
    private ServiceConfigService serviceConfigService;

    @Autowired
    private BillingAccountMapper billingAccountMapper;

    @Reference(version = "1.0.0")
    private BillingAccountService billingAccountService;

    @Autowired
    private ServiceDefineMapper serviceDefineMapper;

    @Autowired
    private BillingPlanMapper billingPlanMapper;

    @Autowired
    private BillingPlanSpecMapper billingPlanSpecMapper;

    @Reference(version = "1.0.0")
    private ResVmService resVmService;

    @Reference(version = "1.0.0")
    private ResFloatingIpService resFloatingIpService;

    @Reference(version = "1.0.0")
    private ResVdService resVdService;

    //ChengQi end

    /**
     * 服务实例Mapper
     */
    @Autowired
    private ServiceInstanceMapper serviceInstanceMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.orderMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Order selectByPrimaryKey(Long orderSid) {
        return this.orderMapper.selectByPrimaryKey(orderSid);
    }

    public List<Order> selectByParams(Criteria example) {
        return this.orderMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long orderSid) {
        return this.orderMapper.deleteByPrimaryKey(orderSid);
    }

    public int updateByPrimaryKeySelective(Order record) {
        return this.orderMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Order record) {
        return this.orderMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.orderMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Order record, Criteria example) {
        return this.orderMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Order record, Criteria example) {
        return this.orderMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Order record) {
        return this.orderMapper.insert(record);
    }

    public int insertSelective(Order record) {
        return this.orderMapper.insertSelective(record);
    }



    /**
     * 好的json中data封装的key 数据
     * @param map  json map
     * @param key  需要获取的data中的key
     * @return key 对应的value
     */
    private String getValueByData(Map<String, Object> map,String key){
        Map<String,Object> data = (Map<String,Object>)map.get(WebConstants.SpecificationProperty.DATA);
        if(StringUtil.isNullOrEmpty(data.get(key)))
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(BusinessMessageConstants.OrderMessage.ERROR_KEY,
                new String[]{key}));
        return data.get(key).toString();
    }

    /**
     * 单独提取json中的规格配置json数据并以map方式返回
     * @param map  json map
     * @return 规格配置map数据
     */
    private Map<String,Object> getSpecifications(Map<String, Object> map){
        Map<String,Object> specifications = (Map<String,Object>)map.get(WebConstants.SpecificationProperty.SPECIFICATIONS);
        return specifications;
    }


    /**
     * 获取规格配置描述数据
     * @param map  json map
     * @return 规格配置描述
     */
    private String getSpecificationsDesc(Map<String, Object> map){
        return StringUtil.nullToEmpty(map.get(WebConstants.SpecificationProperty.SPECIFICATIONS_DESC));
    }


    @Transactional
    public String createServiceOrder(ArrayList<Map<String, Object>> models,
                                             AuthUser authUserInfo) throws RpcException {

        //TODO 配额检查

        BigDecimal orderTotalAmount = BigDecimal.ZERO;
        Long serviceSid = 100L;//默认主机服务
        String orderType = WebConstants.OrderType.NEW_BUY; //默认新购
        Order order = new Order();
        String orderId = sidService.getMaxSid(WebConstants.SidCategory.ORDER);
        order.setOrderId(orderId);
        order.setStatus(WebConstants.OrderStatusCd.NO_PAY);

        order.setMgtObjSid(StringUtil.nullToEmpty(authUserInfo.getMgtObjSid()));
        order.setOwnerId(StringUtil.nullToEmpty(authUserInfo.getAccount()));
        WebUtil.prepareInsertParams(order,authUserInfo);
        //订单明细
        for (Map<String, Object> map : models) {
            OrderDetail orderDetail = new OrderDetail();
            orderType = getValueByData(map,WebConstants.SpecificationProperty.ORDER_TYPE);
            orderDetail.setOrderId(orderId);

            serviceSid = this.billingPlanService.getServiceSid(map.get(WebConstants.SpecificationProperty.SERVICE_CODE).toString());
            orderDetail.setServiceSid(serviceSid);
            orderDetail.setQuantity(Integer.parseInt(getValueByData(map,WebConstants.SpecificationProperty.QUANTITY)));
            orderDetail.setBillingType(getValueByData(map,WebConstants.SpecificationProperty.BILLING_TYPE));
            orderDetail.setMgtObjSid(StringUtil.nullToEmpty(authUserInfo.getMgtObjSid()));
            orderDetail.setBuyLength(Long.parseLong(getValueByData(map,WebConstants.SpecificationProperty.DURATION)));
            orderDetail.setSpecificationDesc(getSpecificationsDesc(map));
            orderDetail.setSpecification(JsonUtil.toJson(getSpecifications(map)));
            WebUtil.prepareInsertParams(orderDetail,authUserInfo);
            //过滤与资费不相关的规格配置
            Map<String,Object> billingSpecJsonMap = billingPlanService.getBillingPriceConfig(serviceSid,orderDetail.getBillingType(),getSpecifications(map));
             //调用资费服务获取当前配置的总价
            BigDecimal price = billingPlanService.getBillingPrice(serviceSid,orderDetail.getBillingType(),JsonUtil.toJson(billingSpecJsonMap));
            //价格*数量*时常
            BigDecimal currentItemAmount = price.multiply(new BigDecimal(orderDetail.getQuantity())).multiply(BigDecimal.valueOf(orderDetail.getBuyLength()));
            orderDetail.setAmount(currentItemAmount);
            orderTotalAmount = orderTotalAmount.add(currentItemAmount);
            this.orderDetailMapper.insertSelective(orderDetail);
        }
        //订单总额设置
        order.setAmount(orderTotalAmount);
        order.setServiceSid(serviceSid);
        order.setServiceName(this.serviceDefineMapper.selectByPrimaryKey(order.getServiceSid()).getServiceName());
        order.setOrderName(order.getOrderId()+"-"+order.getServiceName());
        order.setOrderType(orderType);
        this.orderMapper.insertSelective(order);

        //插入log日志信息
        ActionTraceObservable logObservable = ActionTraceObservable.getActionTraceObservable();
        LogRecord logInfo = ActionTraceObservable.getLogRecord(authUserInfo,
                WebConstants.ACT_TARGET.ORDER_MANAGEMENT,
                WebUtil.getMessage(BusinessMessageConstants.ActionTrace.ORDER_SUBMIT),
                WebUtil.getMessage(BusinessMessageConstants.ActionTrace.ORDER_SUBMIT)+" "+
                        orderId,new Date(),new Date());
        logObservable.addLogQueue(logInfo,logObservable);
        return orderId;
    }

    @Override
    @Transactional
    public boolean payServiceOrder(String orderId,AuthUser authUserInfo) throws RpcException {
        Order order = this.orderMapper.selectOrderByOrderId(orderId);
        //订单验证
        if (StringUtil.isNullOrEmpty(order))
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(
                    BusinessMessageConstants.OrderMessage.CAN_NOT_FIND_ORDER_INFO,
                    new String[]{orderId}));
        //状态验证
        if(!order.getStatus().equals(WebConstants.OrderStatusCd.NO_PAY))
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(
                    BusinessMessageConstants.OrderMessage.ORDER_STATUS_ERROR));

        Criteria orderDetailParam = new Criteria();
        orderDetailParam.put("orderId", orderId);
        List<OrderDetail> orderDetails = this.orderDetailMapper.selectByParams(orderDetailParam);
        if (StringUtil.isNullOrEmpty(orderDetails) || orderDetails.size() == 0) {
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(
                    BusinessMessageConstants.OrderMessage.CAN_NOT_FIND_ORDER_DETAIL_INFO,
                    new String[]{orderId}));
        }
        order.setOrderDetail(orderDetails);
        order.setAuthUserInfo(authUserInfo);
        boolean isCheck = this.billingAccountService.checkAccountBalance(order.getAmount(),authUserInfo.getUserSid());
        if(!isCheck)
            throw new RpcException(RpcException.BIZ_EXCEPTION,WebUtil.getMessage(BusinessMessageConstants.OrderMessage.ACCOUNT_BALANCE_ERROR));

        // TODO 配额检查

        OrderPayObservable orderPayObservable = OrderPayObservable.getOrderPayObservable();
        orderPayObservable.addOrderIdQueue(order,orderPayObservable);

        //插入log日志信息
        ActionTraceObservable logObservable = ActionTraceObservable.getActionTraceObservable();
        LogRecord logInfo = ActionTraceObservable.getLogRecord(authUserInfo,
                WebConstants.ACT_TARGET.ORDER_MANAGEMENT,
                WebUtil.getMessage(BusinessMessageConstants.ActionTrace.ORDER_PAY),
                WebUtil.getMessage(BusinessMessageConstants.ActionTrace.ORDER_PAY)+" "+
                        orderId,new Date(),new Date());
        logObservable.addLogQueue(logInfo,logObservable);
        return true;
    }

    @Override
    public BaseServiceObj cancelServiceOrder(String orderId,AuthUser authUserInfo) throws RpcException {
        BaseServiceObj baseServiceObj = new BaseServiceObj();
        Order order = this.orderMapper.selectOrderByOrderId(orderId);
        if (StringUtil.isNullOrEmpty(order)) {
            throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(
                    BusinessMessageConstants.OrderMessage.CAN_NOT_FIND_ORDER_INFO,
                    new String[]{orderId}));
        }
        order.setStatus(WebConstants.OrderStatusCd.CANCEL);
        WebUtil.prepareUpdateParams(order,authUserInfo.getAccount());
        baseServiceObj.setActionDetail(
                WebUtil.getMessage(BusinessMessageConstants.ActionTrace.ORDER_CANCEL) + "：" + order
                        .getOrderId());
        this.orderMapper.updateByPrimaryKeySelective(order);
        //插入log日志信息
        ActionTraceObservable logObservable = ActionTraceObservable.getActionTraceObservable();
        LogRecord logInfo = ActionTraceObservable.getLogRecord(authUserInfo,
                WebConstants.ACT_TARGET.ORDER_MANAGEMENT,
                WebUtil.getMessage(BusinessMessageConstants.ActionTrace.ORDER_CANCEL),
                WebUtil.getMessage(BusinessMessageConstants.ActionTrace.ORDER_CANCEL)+" "+
                        orderId,new Date(),new Date());
        logObservable.addLogQueue(logInfo,logObservable);
        return baseServiceObj;
    }

    //检查租户配额是否超过
//	private Map<String, String> checkTenantQuota(ArrayList<Map<String, Object>> models, Long tenantSid) {
////		ServiceInstanceService serviceInstanceService = SpringContextHolder.getBean("serviceInstanceServiceImpl");
////		TenantQuotaService tenantQuotaService = SpringContextHolder.getBean("tenantQuotaServiceImpl");
//		Map<String, String> resultMap = new HashMap<String, String>();
//
//		resultMap.put("checkQuotaFlag", "true");
//
//		//统计租户云主机数配额
//		Criteria example = new Criteria();
//		example.put("tanentId", tenantSid);
//		example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//		example.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//		List<ServiceInstance> userServiceVmList = serviceInstanceService.countTenantServiceVmQuotaByParams(example);
//
//		//统计租户云主机cpu数配额
//		List<ServiceInstance> userServiceVmCpuList = serviceInstanceService.countTenantServiceVmCpuQuotaByParams(example);
//
//		//统计租户云主机内存数配额
//		List<ServiceInstance> userServiceVmMemoryList = serviceInstanceService.countTenantServiceVmMemoryQuotaByParams(example);
//
//		//统计租户块存储数配额
//		example = new Criteria();
//		example.put("volumeStorageOrderTenantId",tenantSid);
//		example.put("status", WebConstants.ServiceInstanceStatus.OPENED);
//		example.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//		List<ServiceInstance> userServiceStList = serviceInstanceService.countTenantServiceStQuotaByParams(example);
//
//		//统计租户块存储数磁盘大小配额
//		List<ServiceInstance> userServiceStDiskList = serviceInstanceService.countTenantServiceStDiskQuotaByParams(example);
//
//		for (Map<String, Object> map : models) {
//			String serviceSid = StringUtil.nullToEmpty(map.get("serviceSid"));
//			if (serviceSid.equals("100")) {
//
//				//云主机数
//				int orderVms = Integer.parseInt(StringUtil.nullToEmpty(map.get("quantity")));
//
//				//剩下的云主机减去申请的小于0 则提示错误
//				if (userServiceVmList != null && userServiceVmList.size() > 0) {
//					if (userServiceVmList.get(0).getRestQuotaServiceNum() - orderVms < 0) {
//						resultMap.put("checkQuotaFlag", "false");
//						resultMap.put("checkQuotaType", WebConstants.TenantQuota.INSTANCES);
//						break;
//					}
//				} else {
//					Criteria criteria = new Criteria();
//					criteria.put("tenantSid", tenantSid);
//					criteria.put("quotaKey", WebConstants.TenantQuota.INSTANCES);
//					List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(criteria);
//					if (tenantQuotas != null && tenantQuotas.size() > 0) {
//						if (Integer.parseInt(StringUtil.nullToEmpty(tenantQuotas.get(0).getQuotaValue())) - orderVms < 0) {
//							resultMap.put("checkQuotaFlag", "false");
//							resultMap.put("checkQuotaType", WebConstants.TenantQuota.INSTANCES);
//							break;
//						}
//					}
//				}
//
//				Map<String, Object> mapSpec = (HashMap<String, Object>) map.get("specifications");
//				int orderVmCpus = orderVms * Integer.parseInt(StringUtil.nullToEmpty(mapSpec.get("CPU")));
//				//剩下的内核减去申请的小于0 则提示错误
//				if (userServiceVmCpuList != null && userServiceVmCpuList.size() > 0) {
//					if (userServiceVmCpuList.get(0).getRestQuotaServiceNum() - orderVmCpus < 0) {
//						resultMap.put("checkQuotaFlag", "false");
//						resultMap.put("checkQuotaType", WebConstants.TenantQuota.CORES);
//						break;
//					}
//				} else {
//					Criteria criteria = new Criteria();
//					criteria.put("tenantSid", tenantSid);
//					criteria.put("quotaKey", WebConstants.TenantQuota.CORES);
//					List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(criteria);
//					if (tenantQuotas != null && tenantQuotas.size() > 0) {
//						if (Integer.parseInt(StringUtil.nullToEmpty(tenantQuotas.get(0).getQuotaValue())) - orderVmCpus < 0) {
//							resultMap.put("checkQuotaFlag", "false");
//							resultMap.put("checkQuotaType", WebConstants.TenantQuota.CORES);
//							break;
//						}
//					}
//				}
//
//				BigDecimal orderVmMemorys = new BigDecimal(orderVms).multiply(new BigDecimal(StringUtil.nullToEmpty(mapSpec.get("MEMORY"))));
//				//剩下的内存减去申请的小于0 则提示错误
//				if (userServiceVmMemoryList != null && userServiceVmMemoryList.size() > 0) {
//
//					if (userServiceVmMemoryList.get(0).getRestQuotaServiceNum() - orderVmMemorys.multiply(new BigDecimal(1024)).intValue() < 0) {
//						resultMap.put("checkQuotaFlag", "false");
//						resultMap.put("checkQuotaType", WebConstants.TenantQuota.RAMS);
//						break;
//					}
//				} else {
//					Criteria criteria = new Criteria();
//					criteria.put("tenantSid", tenantSid);
//					criteria.put("quotaKey", WebConstants.TenantQuota.RAMS);
//					List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(criteria);
//					if (tenantQuotas != null && tenantQuotas.size() > 0) {
//						if (Integer.parseInt(StringUtil.nullToEmpty(tenantQuotas.get(0).getQuotaValue())) - orderVmMemorys.multiply(new BigDecimal(1024)).intValue()< 0) {
//							resultMap.put("checkQuotaFlag", "false");
//							resultMap.put("checkQuotaType", WebConstants.TenantQuota.RAMS);
//							break;
//						}
//					}
//				}
//
//				List<HashMap<String, Object>> hostDisks = new ArrayList<HashMap<String, Object>>();
//				Map<String, Object> diskMap = new HashMap<String, Object>();
//				diskMap = (HashMap<String, Object>) map.get("specifications");
//				hostDisks = (List<HashMap<String, Object>>)diskMap.get("DATA_DISK");
//
//				int orderStsTemp = 0;
//				int orderStDisksTemp = 0;
//				// 主机存储系统盘服务实例，存储数据盘服务实例
//				for (int i = 0; i < hostDisks.size(); i++) {
//					if (hostDisks.get(i).get("storagePurpose").equals("02")) {
//						//磁盘数
//						orderStsTemp = orderStsTemp + 1;
//						// 磁盘大小
//						orderStDisksTemp = orderStDisksTemp + Integer.parseInt(StringUtil.nullToEmpty(hostDisks.get(i).get("diskSize")));
//					}
//				}
//				int orderSts = orderVms * orderStsTemp;
//				//剩下的块存储数减去申请的小于0 则提示错误
//				if (userServiceStList != null && userServiceStList.size() > 0) {
//					if (userServiceStList.get(0).getRestQuotaServiceNum() - orderSts < 0) {
//						resultMap.put("checkQuotaFlag", "false");
//						resultMap.put("checkQuotaType", WebConstants.TenantQuota.VOLUMES);
//						break;
//					}
//				} else {
//					Criteria criteria = new Criteria();
//					criteria.put("tenantSid", tenantSid);
//					criteria.put("quotaKey", WebConstants.TenantQuota.VOLUMES);
//					List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(criteria);
//					if (tenantQuotas != null && tenantQuotas.size() > 0) {
//						if (Integer.parseInt(StringUtil.nullToEmpty(tenantQuotas.get(0).getQuotaValue())) - orderSts < 0) {
//							resultMap.put("checkQuotaFlag", "false");
//							resultMap.put("checkQuotaType", WebConstants.TenantQuota.VOLUMES);
//							break;
//						}
//					}
//				}
//
//				int orderStDisks = orderVms * orderStDisksTemp;
//				//剩下的块存储磁盘大小减去申请的小于0 则提示错误
//				if (userServiceStDiskList != null && userServiceStDiskList.size() > 0) {
//
//					if (userServiceStDiskList.get(0).getRestQuotaServiceNum() - orderStDisks < 0) {
//						resultMap.put("checkQuotaFlag", "false");
//						resultMap.put("checkQuotaType", WebConstants.TenantQuota.GIGABYTES);
//						break;
//					}
//				} else {
//					Criteria criteria = new Criteria();
//					criteria.put("tenantSid", tenantSid);
//					criteria.put("quotaKey", WebConstants.TenantQuota.GIGABYTES);
//					List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(criteria);
//					if (tenantQuotas != null && tenantQuotas.size() > 0) {
//						if (Integer.parseInt(StringUtil.nullToEmpty(tenantQuotas.get(0).getQuotaValue())) - orderStDisks < 0) {
//							resultMap.put("checkQuotaFlag", "false");
//							resultMap.put("checkQuotaType", WebConstants.TenantQuota.GIGABYTES);
//							break;
//						}
//					}
//				}
//			} else if (serviceSid.equals("105")) {
//				//块存储数
//				int orderSts = Integer.parseInt(StringUtil.nullToEmpty(map.get("quantity")));
//				//剩下的云主机减去申请的小于0 则提示错误
//				if (userServiceStList != null && userServiceStList.size() > 0) {
//
//					if (userServiceStList.get(0).getRestQuotaServiceNum() - orderSts < 0) {
//						resultMap.put("checkQuotaFlag", "false");
//						resultMap.put("checkQuotaType", WebConstants.TenantQuota.VOLUMES);
//						break;
//					}
//				} else {
//					Criteria criteria = new Criteria();
//					criteria.put("tenantSid", tenantSid);
//					criteria.put("quotaKey", WebConstants.TenantQuota.VOLUMES);
//					List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(criteria);
//					if (tenantQuotas != null && tenantQuotas.size() > 0) {
//						if (Integer.parseInt(StringUtil.nullToEmpty(tenantQuotas.get(0).getQuotaValue())) - orderSts < 0) {
//							resultMap.put("checkQuotaFlag", "false");
//							resultMap.put("checkQuotaType", WebConstants.TenantQuota.VOLUMES);
//							break;
//						}
//					}
//				}
//				Map<String, Object> diskMap = new HashMap<String, Object>();
//				diskMap = (Map<String, Object>)map.get("specifications");
//				int orderStDisks = orderSts * Integer.parseInt(StringUtil.nullToEmpty(diskMap.get("DISK_SIZE")));
//				//剩下的块存储磁盘大小减去申请的小于0 则提示错误
//				if (userServiceStDiskList != null && userServiceStDiskList.size() > 0) {
//					if (userServiceStDiskList.get(0).getRestQuotaServiceNum() - orderStDisks < 0) {
//						resultMap.put("checkQuotaFlag", "false");
//						resultMap.put("checkQuotaType", WebConstants.TenantQuota.GIGABYTES);
//						break;
//					}
//				} else {
//					Criteria criteria = new Criteria();
//					criteria.put("tenantSid", tenantSid);
//					criteria.put("quotaKey", WebConstants.TenantQuota.GIGABYTES);
//					List<TenantQuota> tenantQuotas = tenantQuotaService.selectByParams(criteria);
//					if (tenantQuotas != null && tenantQuotas.size() > 0) {
//						if (Integer.parseInt(StringUtil.nullToEmpty(tenantQuotas.get(0).getQuotaValue())) - orderStDisks < 0) {
//							resultMap.put("checkQuotaFlag", "false");
//							resultMap.put("checkQuotaType", WebConstants.TenantQuota.GIGABYTES);
//							break;
//						}
//					}
//				}
//			}
//		}
//		return resultMap;
//	}

    //取得租户和用户有效购买服务实例数，排除系统盘
//	private int getServiceInstanceServiceCount(Criteria criteria, Tenant tenant, User user, String flag) {
//		ServiceInstanceService serviceInstanceService = SpringContextHolder.getBean("serviceInstanceServiceImpl");
//		 //开通中，已开通，退订中 这些数据算用户还在用的。
//		 String statusParams="'" + WebConstants.ServiceInstanceStatus.OPENING + "','" +
//				 WebConstants.ServiceInstanceStatus.OPENED + "','"
//				 + WebConstants.ServiceInstanceStatus.CANCELING + "'".replace(" ","");
//
//		List<ServiceInstance> resultServiceInstanceList = new ArrayList<ServiceInstance>();
//
//		criteria.setOrderByClause("A.OWNER_ID");
//		List<ServiceInstance> serviceInstanceVmList = serviceInstanceService.selectByParams(criteria);
//
//		for (ServiceInstance serviceInstance : serviceInstanceVmList) {
//			//块存储服务要单独筛选
//			if(serviceInstance.getServiceSid().equals(WebConstants.ServiceSid.SERVICE_STORAGE)){
//				//块存储 选出数据盘,去掉系统盘
//				criteria = new Criteria();
//				if (flag.equals("tenant")) {
//					criteria.put("volumeStorageOrderTenantId", tenant.getTenantSid());
//				} else {
//					criteria.put("volumeStorageOrderId", user.getAccount());
//				}
//				criteria.put("statusParams", statusParams);
//				criteria.put("storagePurpose", WebConstants.StoragePurpose.DATA_DISK);
//				List<ServiceInstance> serviceInstanceStList = serviceInstanceService.selectVolumeStorageInfo(criteria);
//				for (ServiceInstance serviceInstanceSt : serviceInstanceStList) {
//					if (serviceInstance.getInstanceSid().equals(serviceInstanceSt.getInstanceSid())) {
//						resultServiceInstanceList.add(serviceInstance);
//					}
//				}
//			} else {
//				resultServiceInstanceList.add(serviceInstance);
//			}
//		}
//
//		return resultServiceInstanceList.size();
//	}

    // 启动服务流程
    private boolean startServiceWorkflow(Long orderSid, String orderId, Long serviceSid,
                                         String blnApproveTenantQuota) {

        // 服务ID
        // 准备流程实例变量
        Map<String, Object> variables = new HashMap<String, Object>();
//			variables.put("detailSid", detailSid);

        variables.put("orderId", orderId);
        variables.put("serviceSid", serviceSid);
        if (WebConstants.ServiceSid.SERVICE_VM.equals(serviceSid)) {
            variables.put("serviceType", WebConstants.ServiceType.VM);
            variables.put("processType", WebConstants.ProcessType.VM_OPEN);
        } else if (WebConstants.ServiceSid.SERVICE_OBJ_STORAGE.equals(serviceSid)) {
            variables.put("serviceType", WebConstants.ServiceType.OBJECT_STORAGE);
            variables.put("processType", WebConstants.ProcessType.OBJECT_STORAGE_OPEN);
        } else if (WebConstants.ServiceSid.SERVICE_STORAGE.equals(serviceSid)) {
            variables.put("serviceType", WebConstants.ServiceType.STORAGE);
            variables.put("processType", WebConstants.ProcessType.STORAGE_OPEN);
        } else if (WebConstants.ServiceSid.FLOATING_IP.equals(serviceSid)) {
            variables.put("serviceType", WebConstants.ServiceType.FLOATING_IP);
            variables.put("processType", WebConstants.ProcessType.FLOATING_IP_OPEN);
        } else if (WebConstants.ServiceSid.SERVICE_CDN.equals(serviceSid)) {
            variables.put("serviceType", WebConstants.ServiceType.CDN);
            variables.put("processType", WebConstants.ProcessType.CDN_OPEN);
        }

        variables.put("blnApproveTenantQuota", blnApproveTenantQuota);
        // TODO
        variables.put("applyUserId", AuthUtil.getAuthUser().getAccount());
        //ChengQi start
        variables.put("processObjectId", orderSid);
        // TODO
        variables.put("proposeBy", AuthUtil.getAuthUser().getAccount());
        variables.put("proposeDt", new Date());

        String approveResouceMgt = sysconfigService.getValueByConfigKey("approve.resouce.mgt");
        if ("true".equals(approveResouceMgt)) {
            variables.put("approveResouceMgt", true);
        } else if ("false".equals(approveResouceMgt)) {
            variables.put("approveResouceMgt", false);
        }
        String approveIdcMgtPre = sysconfigService.getValueByConfigKey("approve.idc.mgt.pre");
        if ("true".equals(approveIdcMgtPre)) {
            variables.put("approveIdcMgtPre", true);
        } else if ("false".equals(approveIdcMgtPre)) {
            variables.put("approveIdcMgtPre", false);
        }

        String approveNetDeptMgt = sysconfigService.getValueByConfigKey("approve.net.dept.mgt");
        if ("true".equals(approveNetDeptMgt)) {
            variables.put("approveNetDeptMgt", true);
        } else if ("false".equals(approveNetDeptMgt)) {
            variables.put("approveNetDeptMgt", false);
        }

        String approveInternetMgt = sysconfigService.getValueByConfigKey("approve.internet.mgt");
        if ("true".equals(approveInternetMgt)) {
            variables.put("approveInternetMgt", true);
        } else if ("false".equals(approveInternetMgt)) {
            variables.put("approveInternetMgt", false);
        }

        String approveIdcMgtFinal = sysconfigService.getValueByConfigKey("approve.idc.mgt.final");
        if ("true".equals(approveIdcMgtFinal)) {
            variables.put("approveIdcMgtFinal", true);
        } else if ("false".equals(approveIdcMgtFinal)) {
            variables.put("approveIdcMgtFinal", false);
        }

        String activitiFlowKey = getActivitiFlow(WebConstants.ServiceSid.SERVICE_VM);
//			// 启动流程实例
        String processInstanceId = activitiService.startWorkflow(variables, activitiFlowKey);

        //ChengQi start
        //更新Order状态，OrderDetail关联activiti中的processInstanceId
        //更新订单数据审批中
        String userId = AuthUtil.getAuthUser().getAccount();
        Criteria criteria = new Criteria();
        criteria.put("orderId", orderId);
        Order order = selectByParams(criteria).get(0);
        order.setStatus(WebConstants.OrderStatusCd.APPROVING);
        //WebUtil.prepareUpdateParams(order, userId);//removed byjm because of starting it in creation process
        //暂不更新订单状态
//			updateByPrimaryKeySelective(order);

        //根据订单id，取得订单明细数据
        criteria = new Criteria();
        criteria.put("orderId", orderId);
        List<OrderDetail> orderDetails = orderDetailService.selectByParams(criteria);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setStatus(WebConstants.OrderStatusCd.APPROVING);
//				orderDetail.setProcessInstanceId(processInstance.getProcessInstanceId());
            orderDetail.setProcessInstanceId(processInstanceId);
            //WebUtil.prepareUpdateParams(orderDetail, userId);//removed byjm because of starting it in creation process
            orderDetailService.updateByPrimaryKeySelective(orderDetail);
        }

        activitiService.claimAndCompleteTasks(processInstanceId, userId);
        return true;

    }

    /**
     * 更新订单数据到订单表
     */
    public boolean updateStatus(Order order) {

        OrderService orderService = SpringContextHolder.getBean("orderServiceImpl");
        WebUtil.prepareUpdateParams(order);
        int result = orderService.updateByPrimaryKeySelective(order);
        return result > 0;
    }

    /**
     * 取得用户所在租户的具有审批权限的全部管理员
     */
    public String getTenantAdminId(String ownerId) {

        StringBuffer tenantAdminId = new StringBuffer();

        UserService userService = SpringContextHolder.getBean("userServiceImpl");

        Criteria criteria = new Criteria();
        criteria.put("account", ownerId);

        // 取得用户和所在租户sid
        List<User> tenantlist = userService.selectByParams(criteria);
        Long tenantSid = tenantlist.get(0).getTenantSid();

        // 取得租户下所有用户
        criteria = new Criteria();
        criteria.put("tenantSid", tenantSid);

        List<User> allTenantUserlist = userService.selectByParams(criteria);

        if (allTenantUserlist != null && allTenantUserlist.size() > 0) {
            for (User allTenantUser : allTenantUserlist) {
                boolean tenantAdminRole = getUserWithTenantAdminRole(allTenantUser);
                if (tenantAdminRole == true) {
                    if (tenantAdminId.length() == 0) {
                        tenantAdminId.append(allTenantUser.getAccount());
                    } else {
                        tenantAdminId.append("," + allTenantUser.getAccount());
                    }
                }
            }
        }

        // TODO Auto-generated method stub
        return tenantAdminId.toString();
    }

    /**
     * 判断租户用户是否具备审批权限
     */
    private boolean getUserWithTenantAdminRole(User allTenantUser) {

        UserRoleService userRoleService = SpringContextHolder.getBean("userRoleServiceImpl");

        Criteria criteria = new Criteria();
        criteria.put("userSid", allTenantUser.getUserSid());
        criteria.put("roleSid", WebConstants.RoleSid.T_MANAGER);

        List<UserRole> userRoles = userRoleService.selectByParams(criteria);

        return userRoles != null && userRoles.size() > 0;
    }

    /**
     * 取得流程启动key
     */
    private String getActivitiFlow(Long serviceSid) {
        String activitiFlow = "";
        // 取得流程启动key
        activitiFlow = serviceConfigService.selectActivitiFlowByServiceSid(serviceSid,
                                                                           WebConstants.ServiceConfigActiviti.CLOUD_SERVICE_APPROVE_PROCESS);

        return activitiFlow;
    }

    /**
     * 取消订单
     */
    @Override
    public boolean cancelOrder(Long orderSid) {
        try {
            Order order = this.orderMapper.selectByPrimaryKey(orderSid);
            order.setStatus(WebConstants.OrderStatusCd.CANCEL);
            WebUtil.prepareUpdateParams(order);
            this.orderMapper.updateByPrimaryKeySelective(order);
            Criteria example = new Criteria();
            example.put("orderId", order.getOrderId());
            List<ServiceInstance> serInsList = this.serviceInstanceMapper.selectByParams(example);
            for (ServiceInstance si : serInsList) {
                si.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
                WebUtil.prepareUpdateParams(si);
                this.serviceInstanceMapper.updateByPrimaryKeySelective(si);
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断某个order下面的所有服务实例是否都是已退订的
     */
    @Override
    public boolean judgementAllServiceInstanceIsCanceled(String orderId) {
        // 查询出所有的服务实例
        boolean isAllUnsubscribed = true;

        Criteria example = new Criteria();
        example.put("orderId", orderId);
        List<ServiceInstance> serInsList = this.serviceInstanceMapper.selectByParams(example);

        for (ServiceInstance si : serInsList) {
            if (!WebConstants.ORDER_STATUS.UNSUBSCRIBED.equals(si.getStatus())) {
                isAllUnsubscribed = false;
                break;
            }
        }

        return isAllUnsubscribed;

    }

    /**
     * 查询当月订单状态详情
     */
    @Override
    public List<Order> selectByOrderStatusInMonth(Criteria example) {
        return this.orderMapper.selectByOrderStatusInMonth(example);
    }

    /**
     * 查询历史订单状态详情
     */
    @Override
    public List<Order> selectByOrderStatusInAll(Criteria example) {
        return this.orderMapper.selectByOrderStatusInAll(example);
    }

    @Override
    public List<UserOrderVo> selectPersonalOrderList(Criteria example) {
        return this.orderMapper.selectPersonalOrderList(example);
    }

    /**
     * 再次提交订单数据（前端系统创建）
     *
     * @return orderId
     */
    @Override
    public Map<String, Object> updateServiceOrder(ArrayList<Map<String, Object>> models)
            throws Exception {

        SidService sidService = SpringContextHolder.getBean("sidServiceImpl");
//		TenantService tenantService = SpringContextHolder.getBean("tenantServiceImpl");
        ServiceInstanceService
                serviceInstanceService =
                SpringContextHolder.getBean("serviceInstanceServiceImpl");

        String orderId = "";
        String serviceSid = "";
        Order order = new Order();
        boolean vmServiceFlag = false;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("checkQuotaMsg", StringUtil.EMPTY);
        // 构造订单记录
        // 取得用户登录id，可以通过共通取得（租户用户）
//		User user = AuthUtil.getAuthUser();
        //租户配额审批标志
        String blnApproveTenantQuota = "false";
        //将订单状态改为审核中

        for (Map<String, Object> map : models) {
            OrderDetail orderDetail = new OrderDetail();
            Long detailSid = Long.parseLong(map.get("detailSid").toString());
            orderDetail = orderDetailService.selectByPrimaryKey(detailSid);
            orderId = orderDetail.getOrderId();
            serviceSid = orderDetail.getServiceSid().toString();
            Criteria example = new Criteria();
            example.put("orderId", orderId);
            List<Order> orders = orderMapper.selectByParams(example);
            order = orders.get(0);
            // 设置服务实例列表
            orderDetail.setServiceInstanceMaps(
                    (List<HashMap<String, Object>>) map.get("instanceList"));
            orderDetail.setMgtObjSid(order.getMgtObjSid());
            // 设置服务实例规格
            HashMap<String, Object>
                    specificationMaps =
                    JsonUtil.fromJson(orderDetail.getSpecification(), HashMap.class);
            orderDetail.setSpecificationMaps(specificationMaps);

            boolean createVmFlag = serviceInstanceService.updateServiceInstance(orderDetail);
            if (!createVmFlag) {
                throw new ServiceException("创建虚拟机实例失败");
            }
        }
        // 启动服务流程
        vmServiceFlag =
                startServiceWorkflow(order.getOrderSid(), orderId, Long.parseLong(serviceSid),
                                     blnApproveTenantQuota);

        // 如果有VM被开通则返回订单号，如果没有VM被开通，全是VM外的服务直接返回无法开通服务。
        if (vmServiceFlag == true) {
            resultMap.put("orderId", orderId);
            resultMap.put("errorMessage", StringUtil.EMPTY);
        } else {
            // 更新订单为已取消
            order.setStatus(WebConstants.OrderStatusCd.CANCEL);
            WebUtil.prepareUpdateParams(order);
            this.orderMapper.updateByPrimaryKeySelective(order);

            // 更新订单明细状态为已取消
            Criteria criteria = new Criteria();
            criteria.put("orderId", order.getOrderId());
            List<OrderDetail> list = this.orderDetailMapper.selectByParams(criteria);
            for (OrderDetail detail : list) {
                detail.setStatus(WebConstants.OrderStatusCd.CANCEL);
                WebUtil.prepareUpdateParams(detail);
                this.orderDetailMapper.updateByPrimaryKeySelective(detail);
            }

            resultMap.put("orderId", orderId);
            resultMap.put("errorMessage", "抱歉，后台资源紧张，无法开通该服务。");
        }

        return resultMap;

    }

    @Override
    public ResVmService getResVmService() {
        return this.resVmService;
    }

    @Override
    public ResFloatingIpService getResFloatingIpService() {
        return this.resFloatingIpService;
    }

    @Override
    public ServiceInstanceService getServiceInstanceService() {
        return this.serviceInstanceService;
    }

    @Override
    public ResVdService getResVdService() {
        return this.resVdService;
    }
}