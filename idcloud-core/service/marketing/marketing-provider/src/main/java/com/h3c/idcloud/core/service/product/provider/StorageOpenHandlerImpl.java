package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.core.pojo.dto.customer.TicketRecord;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.MgtObjResService;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResVdService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.core.service.ticket.api.TicketRecordService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 块存储开通
 * 
 * @author ChengQi
 *
 */
//@Service("storageOpenHandlerImpl")
@Service(version = "1.0.0",group = "storageOpenHandlerImpl")
@Component("storageOpenHandlerImpl")
public class StorageOpenHandlerImpl implements ResourceRequestHanlder {

	private static final Logger logger = LoggerFactory.getLogger(StorageOpenHandlerImpl.class);

	/** 资源申请接口Service */
	@Reference(version = "1.0.0")
	private ResVdService resVdService;

//	@Autowired
//	private MockResVmService mockResVmService;

	/** 订单 Service */
	@Reference(version = "1.0.0")
	private OrderService orderService;

	/** 服务实例Service */
	@Autowired
	private ServiceInstanceService instanceService;

	/** 服务实例规格Service */
	@Autowired
	private ServiceInstanceSpecService instanceSpecService;

	/** 管理对象Service */
	@Reference(version = "1.0.0")
	private MgtObjService mgtObjService;

	/** 管理对象资源关联关系Service */
	@Autowired
	private MgtObjResService mgtObjResService;

	/** 服务实例对象资源关系Service */
	@Autowired
	private ServiceInstResService serviceInstResService;

	/** 流水号Service */
	@Reference(version = "1.0.0")
	private SidService sidService;

	/** 邮件提醒Service */
	@Reference(version = "1.0.0")
	private MailNotificationsService mailNotificationsService;

	/** 工单Service */
	@Reference(version = "1.0.0")
	private TicketService ticketService;

	/** 工单处理记录Service */
	@Reference(version = "1.0.0")
	private TicketRecordService ticketRecordService;

//	/** 日志处理Service */      --------wsl
//	@Autowired
//	private SysLoggerFactory sysLogger;

	@Autowired
	private ServiceInstanceChangeLogService changeLogService;

	@Override
	public boolean invoke(Long processObjectId) {
		boolean result = true;
		Order order = null;
		String orderId = null;
		ServiceInstance instance = null;
		Long mgtObjSid = null;
		Long instanceSid = null;

		//系统日志
		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount()); ---------wsl
		record.setActLevel("01");
		record.setActTarget("服务订购");
		record.setActName("块存储服务开通");
		record.setActStartDate(new Date());
		record.setActEndDate(new Date());

		try {

			order = orderService.selectByPrimaryKey(processObjectId);
	
			orderId = order.getOrderId();
			if(orderId != null) {
				Criteria criteria = new Criteria();
				criteria.put("orderId", order.getOrderId());
				criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
				List<ServiceInstance> serviceInstances = instanceService.selectByParams(criteria);
				for (ServiceInstance serviceInstance : serviceInstances) {
					instance = serviceInstance;
					mgtObjSid = instance.getMgtObjSid();
					instanceSid = serviceInstance.getInstanceSid();
/*
					ResVdInst resVdInst = new ResVdInst();
					resVdInst.setMgtObjSid(mgtObjSid);
					resVdInst.setResVdInstName(serviceInstance.getInstanceName());

					//设置存储大小
					List<ServiceInstanceSpec> instanceSpecs = instanceSpecService.selectByInstanceSpecBySid(serviceInstance.getInstanceSid());
					String diskSize = ResDataUtils.getSpecValueFromSpecs("DISK_SIZE",instanceSpecs);
					resVdInst.setDiskSize(Long.parseLong(diskSize));

					//设置存储分配目标
					criteria = new Criteria();
					criteria.put("bizSid", instance.getMgtObjSid());
					criteria.put("resCategory", "4");
//					criteria.put("resSetType", WebConstants.ResourceType.RES_HOST);
					List<MgtObjRes> mgtReses = mgtObjResService.selectByParams(criteria);
					List<String> allocateResStorageIds = new ArrayList<String>();
					for(MgtObjRes mgtRes : mgtReses) {
						allocateResStorageIds.add(mgtRes.getResSetSid());
					}
					resVdInst.setAllocateResStorageIds(allocateResStorageIds);
*/
//					ResVdInst resVdInst = changeLogService.getChangeInfo(serviceInstance.getInstanceSid(), new TypeReference<ResVdInst>() {}); ------wsl
//					logger.info("createVd input params:" + JsonUtil.toJson(resVdInst));
//					ResInstResult resInstResult = this.resVdService.createVd(resVdInst);
////					ResInstResult resInstResult = this.mockResVmService.createVd(resVdInst);
//					logger.info("createVd return result:" + JsonUtil.toJson(resInstResult));
//
//					saveInstanceRes(order, serviceInstance, resInstResult, mgtObjSid, instanceSid);
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error(e.getMessage(), e);
			//生成云块存储开通失败工单
			operateOpenError(orderId, instance.getMgtObjSid(), instance.getInstanceSid(), "未知错误", order, instance, false);
			return result;
		} finally {			
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);   --------wsl
//			log.debug(record);
		}
		return false;
	}

	@Transactional
	public void saveInstanceRes(Order order, ServiceInstance serviceInstance, ResInstResult resInstResult, Long mgtObjSid, Long instanceSid) {
		if(ResInstResult.SUCCESS == resInstResult.getStatus()) {
			//保存实例和资源关联关系
			ResVd resVd = (ResVd)resInstResult.getData();
			ServiceInstRes serviceInstRes = new ServiceInstRes();
			serviceInstRes.setInstanceSid(serviceInstance.getInstanceSid());
			serviceInstRes.setResId(resVd.getResVdSid());
			serviceInstRes.setResType(WebConstants.ResourceType.RES_STORAGE);
			serviceInstResService.insert(serviceInstRes);

			//更新实例状态
			serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENING);
			instanceService.updateByPrimaryKeySelective(serviceInstance);
			//更新虚拟机实例规格状态
//			instanceSpecService.modifyInstanceSpecToChanging(serviceInstance.getInstanceSid());
			//更新订单状态
			order.setStatus(WebConstants.ORDER_STATUS.OPENING);
			orderService.updateByPrimaryKeySelective(order);

			//更新实例变更日志
//			this.changeLogService.beginChangeServiceInstance(instanceSid, WebConstants.instanceChangeType.CREATE); ----------wsl

		} else {
			operateOpenError(order.getOrderId(), mgtObjSid, instanceSid, (String)resInstResult.getMessage(), order, serviceInstance, resInstResult.isReSend());
		}
		
	}

	public void operateOpenError(String orderId, Long mgtObjSid, Long instanceSid, String errorMessage, Order order, ServiceInstance serviceInstance, Boolean isResSend) {
		if(order == null || serviceInstance == null) {
			logger.error("Can't modify order or serviceInstance status, because they is null. order=" + order + " serviceInstance=" + serviceInstance);
			return;
		}
		//更新实例状态
		serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENING);
		instanceService.updateByPrimaryKeySelective(serviceInstance);
		//更新订单状态 
		if(order!=null){
			order.setStatus(WebConstants.ORDER_STATUS.OPENING);
			orderService.updateByPrimaryKeySelective(order);
		}
		//生成块存储开通失败工单
		saveOpenErrorTicket(orderId, mgtObjSid, instanceSid, errorMessage, isResSend);
	}

	@Transactional
	public void saveOpenErrorTicket(String orderId, Long mgtObjSid, Long instanceSid, String errorMessage, Boolean isResSend) {
		try {
			if(orderId == null || mgtObjSid == null || instanceSid == null) {
				logger.error("can't generate ticket, because not enought data. orderId=" + orderId + " mgtObjSid=" + mgtObjSid + " instanceSid=" + instanceSid);
				return;
			}
			Criteria criteria = new Criteria();
			criteria.put("statusList", 
					"'" + WebConstants.TicketStatus.CREATED + "'," +
					"'" + WebConstants.TicketStatus.ALLOCATED + "'," +
					"'" + WebConstants.TicketStatus.PROCESSING + "'," +
					"'" + WebConstants.TicketStatus.RESOLVE + "'"
					);
			criteria.put("processType", WebConstants.TicketProcessType.STORAGE_OPEN);
			criteria.put("processObjectId", instanceSid);
			List<Ticket> ticketList  = this.ticketService.selectByParams(criteria);
			
			if (!ticketList.isEmpty()){
				logger.info("can't generate ticket, because of existing unsolved ticket with same processType and processObjectId");
				logger.info("update auto handler openVd ticket record start");
				Ticket ticket = ticketList.get(0);
				criteria = new Criteria();
				criteria.put("ticketSid", ticket.getTicketSid());
				criteria.put("operate", WebConstants.TicketOperate.AUTO_HANDLER);
				criteria.put("operateContent", "");
				List<TicketRecord> ticketRecords = this.ticketRecordService.selectByParams(criteria);
				if(!ticketRecords.isEmpty()) {
					TicketRecord ticketRecord = ticketRecords.get(0);
					ticketRecord.setUpdatedDt(new Date());
					ticketRecord.setOperateContent("块存储开通工单自动处理失败");
					this.ticketRecordService.updateByPrimaryKeySelective(ticketRecord);
				}
				logger.info("update auto handler openVd ticket record end");
				return ;
			}

			String type = "块存储开通";
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
			MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(mgtObjSid);
			List<ServiceInstanceSpec> instanceSpecs = instanceSpecService.selectByInstanceSpecBySid(instanceSid);
			StringBuilder content = new StringBuilder();
			content.append("所属业务:").append(mgtObj.getName()).append("\r\n");
			content.append("规格:").append("\r\n");;
			for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
				if(StringUtils.isNotBlank(instanceSpec.getValueText())) {
					content.append("  ").append(instanceSpec.getDescription()).append(":").append(instanceSpec.getValueText()).append("\r\n");
				}
			}
			content.append("错误信息:").append(StringUtil.nullToEmpty(errorMessage));
			Ticket ticket = new Ticket();
			ticket.setQuestionType("03");
			ticket.setQuestionLevel(WebConstants.QuestionLevel.BEST_HIGH);
			ticket.setTitle(type + "失败-" + orderId + "-" + instance.getInstanceName());
			ticket.setServiceSid(instance.getServiceSid());
			ticket.setTicketNo(sidService.getMaxSid(WebConstants.SidCategory.TICKET));
			ticket.setVersion(1L);
			if(StringUtils.isNotBlank(instance.getTanentId())) {
				ticket.setTenantSid(Long.parseLong(instance.getTanentId()));
			}
			ticket.setContent(content.toString());
			if(isResSend != null) {
				ticket.setAutoHandlerFlag(isResSend ? 1 : 0);
			} else {
				ticket.setAutoHandlerFlag(0);
			}
			ticket.setTicketUser("admin");
			ticket.setCreatedBy("admin");
			ticket.setCreatedDt(new Date());
			ticket.setStatus(WebConstants.TicketStatus.CREATED);
			ticket.setProcessType(WebConstants.TicketProcessType.STORAGE_OPEN);
			ticket.setProcessObjectId(instanceSid);
			ticketService.insertSelective(ticket);
			mailNotificationsService.ticketNoticeEmail(ticket,WebConstants.RoleSid.OM_MANAGERKLB);
		} catch(Exception e) {
			logger.error("saveOpenVmErrorTicket failure.", e);
		}
	}

	@Override
	public void operate(ResInstResult result) {
		try {
			logger.info("createVd callback result:" + JsonUtil.toJson(result));

			Long instanceSid = null;
			//根据资源id查询出对应实例id
			ResVd resVd = (ResVd)result.getData();
			String resVdSid = resVd.getResVdSid();

			Criteria criteria = new Criteria();
			criteria.put("resId", resVdSid);
			List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(criteria);
			instanceSid = null;
			if(serviceInstReses.size() > 0) {
				instanceSid = serviceInstReses.get(0).getInstanceSid();
				if(serviceInstReses.size() > 0) {
					instanceSid = serviceInstReses.get(0).getInstanceSid();
					ServiceInstance serviceInstance=this.instanceService.selectByPrimaryKey(instanceSid);
					Calendar curr = Calendar.getInstance();
					//更新到期时间
					if (serviceInstance.getBillingType().equals(WebConstants.BillingType.YEAR)) {
						curr.set(Calendar.YEAR,curr.get(Calendar.YEAR) + Integer.parseInt(serviceInstance.getBuyLength().toString()));
						serviceInstance.setExpiringDate(curr.getTime());
					} else if (serviceInstance.getBillingType().equals(WebConstants.BillingType.MONTH)) {
						curr.set(Calendar.MONTH,curr.get(Calendar.MONTH) + Integer.parseInt(serviceInstance.getBuyLength().toString()));
						serviceInstance.setExpiringDate(curr.getTime());
					}
					
//					serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
					WebUtil.prepareUpdateParams(serviceInstance);
					this.instanceService.updateByPrimaryKeySelective(serviceInstance);
				}
			}
			if(instanceSid == null) {
				throw new RuntimeException("The resSid no mapping in service_inst_res table resSid=" + resVdSid);
			}
			//查询出该实例所属的订单号
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
			String orderId = instance.getOrderId();
			Long mgtObjSid = instance.getMgtObjSid();

			if(ResInstResult.SUCCESS == result.getStatus()) {
				handlerData(instance, resVdSid);
			} else {
				//开通失败，删除服务实例和磁盘与资源表关联信息
				if(instanceSid != null) {
					List<ServiceInstRes> serviceInstResList = serviceInstResService.selectInstanceReses(instanceSid);
					for(ServiceInstRes serviceInstRes : serviceInstResList) {
						serviceInstResService.deleteByPrimaryKey(serviceInstRes);
					}
				}
				criteria = new Criteria();
				Order order = null;
				criteria.put("orderId", orderId);
				List<Order> orders = orderService.selectByParams(criteria);
				if(orders.size() > 0) {
					order = orders.get(0);
				}
				operateOpenError(orderId, mgtObjSid, instanceSid, (String)result.getMessage(), order, instance, result.isReSend());
			}
		} catch(Exception e) {
			logger.error("operate failure.", e);
		}
	}

	@Override
	public void handlerData(ServiceInstance instance, String resVdSid) {

		String orderId = instance.getOrderId();
		Long instanceSid = instance.getInstanceSid();

		instanceService.modifyAllInstanceStatus(instance);

		//更新实例变更日志
		changeLogService.endChangeServiceInstance(instance, WebConstants.instanceChangeType.CREATE);

		if(instanceService.checkOrderSuccess(orderId, WebConstants.ServiceSid.SERVICE_STORAGE)) {
			//更新订单状态为已完成
			Criteria criteria = new Criteria();
			criteria.put("orderId", orderId);
			List<Order> orders = orderService.selectByParams(criteria);
			Order order = null;
			if(orders.size() > 0){
				order = orders.get(0);
				order.setStatus(WebConstants.ORDER_STATUS.OPENED);
				order.setDredgeDate(new Date());
				orderService.updateByPrimaryKeySelective(order);
			}
		}
		//更新工单信息
		ticketService.modifyTicketStatus(instanceSid, WebConstants.ProcessType.STORAGE_OPEN);
		//发送邮件通知
//		mailNotificationsService.launchServiceEmail(instanceSid);

	}


	/**
	 * 调用申请资源接口
	 * @param params 动态参数
	 * @return
	 */
	public boolean invoke(Map<String, Object> params) {
		return false;
	}

	/**
	 * 调用申请资源接口
	 */
	public boolean invoke(List<Long> instanceSids) {
		return false;
	}

}
