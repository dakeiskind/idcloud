package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.res.ResFloatingIp;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResCdnInstService;
import com.h3c.idcloud.core.service.res.api.ResFloatingIpService;
import com.h3c.idcloud.core.service.system.api.CodeService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.core.service.system.api.SysTLogRecordService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 浮动IP退订申请回调处理
 * 
 *
 */
//@Service("cdnServiceDeleteHandlerImpl")
@Service(version = "1.0.0", group = "cdnServiceDeleteHandlerImpl")
@Component("cdnServiceDeleteHandlerImpl")
public class CdnServiceDeleteHandlerImpl implements ResourceRequestHanlder {

	private static final Logger logger = LoggerFactory
			.getLogger(CdnServiceDeleteHandlerImpl.class);

	/** 资源申请接口Service */
	@Reference(version = "1.0.0")
	private ResFloatingIpService floatingIpService;

	@Autowired
	private ServiceInstanceSpecService instanceSpecService;

	@Autowired
	private ServiceInstanceService instanceService;

	@Autowired
	private ServiceInstResService serviceInstResService;

	@Reference(version = "1.0.0")
	private MailNotificationsService mailNotificationsService;

	@Reference(version = "1.0.0")
	private TicketService ticketService;

	@Reference(version = "1.0.0")
	private MgtObjService mgtObjService;

	@Reference(version = "1.0.0")
	private SidService sidService;

	@Reference(version = "1.0.0")
	private CodeService codeService;

	@Reference(version = "1.0.0")
	private SysTLogRecordService logService;
//	@Autowired
//	private SysLoggerFactory sysLogger;

//	@Reference(version = "1.0.0")
//	private VmService vmService;

	@Reference(version = "1.0.0")
	private OrderService orderService;

//	@Reference(version = "1.0.0")
//	private IdcOrderService idcOrderService;
//
//	@Reference(version = "1.0.0")
//	private IdcOrderDetailService idcOrderDetailService;
	
	/** 管理对象Service */
	@Reference(version = "1.0.0")
	private ResCdnInstService resCdnInstService;
	
	@Override
	public boolean invoke(Long processObjectId) {
		Long mgtObjSid = null;
		Long instanceSid = null;
		
		//系统日志
		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount()); -------wsl
		record.setActLevel("01");		
		record.setActTarget("自服务");
		record.setActName("CDN服务退订");
		record.setActStartDate(new Date());
		record.setActEndDate(new Date());
		
		ServiceInstance serviceInstance = null;
		try {
			//根据虚拟机实例id查询出关联的虚拟机资源id
			Criteria criteria = new Criteria();
			criteria.put("instanceSid", processObjectId);
			List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(criteria);
			String resCdnInstSid = null;
			if(serviceInstReses.size() > 0) {
				resCdnInstSid = serviceInstReses.get(0).getResId();
			}
			if(resCdnInstSid == null) {
				throw new RuntimeException("The instanceSid no mapping in service_inst_res table instanceSid=" + processObjectId);
			}
			//根据实例sid查询出实例信息
			serviceInstance = instanceService.selectByPrimaryKey(processObjectId);
			logger.info("deleteCdn input params: resCdnInstSid=" + resCdnInstSid);
			ResInstResult resInstResult = resCdnInstService.stopCDN(resCdnInstSid);
			logger.info("deleteCdn result params:" + JsonUtil.toJson(resInstResult));
			modifyChangeCdnData(resInstResult, serviceInstance);
			record.setActResult("02");
			return resInstResult.getStatus();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			saveDeleteFloatingIpErrorTicket(mgtObjSid, instanceSid, "未知错误");
			record.setActResult("01");	
			return false;
		} finally {
			record.setActDetail("云主机名称:"+serviceInstance.getInstanceName()+"云主机操作:退订");
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);
//			log.debug(record);
		}
		
	}

	@Transactional
	public void modifyChangeCdnData(ResInstResult resInstResult, ServiceInstance serviceInstance) {
		if(ResInstResult.SUCCESS == resInstResult.getStatus()) {
			//更新实例状态为退订中
			serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
			// 更新实例退订时间,方便服务计费---by jipeigui
			serviceInstance.setDestroyDate(new Date());
			instanceService.updateByPrimaryKeySelective(serviceInstance);
		} else {
			saveDeleteFloatingIpErrorTicket(serviceInstance.getMgtObjSid(), serviceInstance.getInstanceSid(), (String)resInstResult.getMessage());
		}
	}

	@Override
	public void operate(ResInstResult result) {
		try {
			logger.info("deleteFloatingIp callback result:" + JsonUtil.toJson(result));
			//根据资源id查询出对应实例id
			ResFloatingIp floatingIp = (ResFloatingIp)result.getData();
			String resSid = floatingIp.getResSid();
			Long instanceSid = getInstanceSidByResSid(resSid);
			//查询出浮动IP实例
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
			Long mgtObjSid = instance.getMgtObjSid();
			String orderId = instance.getOrderId();
	
			if(ResInstResult.SUCCESS == result.getStatus()) {
				//更新Vm实例状态为正常,更新内外网络
				instance.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
				instanceService.updateByPrimaryKeySelective(instance);
	
				//判断是否为外部实例
				if (!StringUtil.isNullOrEmpty(instance
						.getoServiceId())
						&& PropertiesUtil.getProperty("idc.owner.id")
								.equals(instance.getOwnerId())) {					
					//检查订单下所有虚拟机实例是否退订完成
					if(instanceService.checkUnsubscribeVmSuccess(orderId)) {
						//更新订单状态为已退订
						Criteria criteria = new Criteria();
						criteria.put("orderId", orderId);
						List<Order> orders = orderService.selectByParams(criteria);
						Order order = null;
						if(orders.size() > 0){
							order = orders.get(0);
							order.setStatus(WebConstants.ORDER_STATUS.UNSUBSCRIBED);
							orderService.updateByPrimaryKeySelective(order);
//							vmService.unsubscribeVmCallBack(orderId); ---------wsl
						}
					}
				} else {
					//发送退订通知邮件
//					mailNotificationsService.unsubscribeServiceEmail(instanceSid, floatingIp);
				}				
				//删除监控节点信息
//				TkUtils.delMonitorNode(resVmSid, mgtObjSid);
				ticketService.modifyTicketStatus(instanceSid, WebConstants.ProcessType.FLOATING_IP_CANCEL);
			} else {
				saveDeleteFloatingIpErrorTicket(mgtObjSid, instanceSid, (String)result.getMessage());
			}
		} catch(Exception e) {
			logger.error("operate failure.", e);
		}

	}

	private Long getInstanceSidByResSid(String resSid) {
		Long instanceSid = null;
		Criteria criteria = new Criteria();
		criteria.put("resId", resSid);
		List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(criteria);
		if(serviceInstReses.size() > 0) {
			instanceSid = serviceInstReses.get(0).getInstanceSid();
		}
		if(instanceSid == null) {
			throw new RuntimeException("The resSid no mapping in service_inst_res table resSid=" + resSid);
		}
		return instanceSid;
	}

	@Transactional
	public void saveDeleteFloatingIpErrorTicket(Long mgtObjSid, Long instanceSid, String errorMessage) {
		try {
			if(mgtObjSid == null || instanceSid == null) {
				logger.error("can't generate ticket, because not enought data. mgtObjSid=" + mgtObjSid + " instanceSid=" + instanceSid);
				return;
			}
			String type = "浮动IP退订";
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
			
			MgtObj mgtObj = mgtObjService.selectByPrimaryKey(mgtObjSid);
			List<ServiceInstanceSpec> instanceSpecs = instanceSpecService.selectByInstanceSpecBySid(instanceSid);
			StringBuilder content = new StringBuilder();
			content.append("所属管理对象:").append(mgtObj.getName()).append("\r\n");
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
			ticket.setTitle(type + "失败-" + instanceSid);
			ticket.setServiceSid(instance.getServiceSid());
			ticket.setTicketNo(sidService.getMaxSid(WebConstants.SidCategory.TICKET));
			ticket.setVersion(1L);
			ticket.setTenantSid(Long.parseLong(instance.getTanentId()));
			ticket.setContent(content.toString());
			ticket.setTicketUser("admin");
			ticket.setCreatedBy("admin");
			ticket.setCreatedDt(new Date());
			ticket.setStatus(WebConstants.TicketStatus.CREATED);
			ticket.setProcessType(WebConstants.TicketProcessType.FLOATING_IP_CANCEL);
		 	ticket.setProcessObjectId(instanceSid);
			ticketService.insertSelective(ticket);
			mailNotificationsService.ticketNoticeEmail(ticket,WebConstants.RoleSid.OM_MANAGERKLB);
		} catch(Exception e) {
			logger.error("saveChangeVmErrorTicket failure.", e);
		}
	}

	@Override
	public void handlerData(ServiceInstance instance, String resVmSid) {
		
		
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
