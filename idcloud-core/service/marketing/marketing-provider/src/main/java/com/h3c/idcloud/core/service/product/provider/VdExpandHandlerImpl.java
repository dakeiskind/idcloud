package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.core.pojo.dto.customer.TicketRecord;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.product.VdExpandRequest;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
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
 * 块存储扩展
 * 
 * @author ChengQi
 *
 */
//@Service("vdExpandHandlerImpl")
@Service(version = "1.0.0",group = "vdExpandHandlerImpl")
@Component("vdExpandHandlerImpl")
public class VdExpandHandlerImpl implements ResourceRequestHanlder {
	
	private static final Logger logger = LoggerFactory
			.getLogger(VdExpandHandlerImpl.class);

	/** 资源申请接口Service */
	@Reference(version = "1.0.0")
	private ResVdService resVdService;
//	private MockResVmService mockResVmService;

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
	private TicketRecordService ticketRecordService;

	@Reference(version = "1.0.0")
	private MgtObjService mgtObjService;

	@Reference(version = "1.0.0")
	private SidService sidService;

//	@Autowired     --------wsl
//	private SysLoggerFactory sysLogger;

	@Autowired
	private ServiceInstanceChangeLogService instanceChangeLogService;

	@Override
	public boolean invoke(Long processObjectId) {
		Long mgtObjSid = null;
		Long instanceSid = processObjectId;	
		ServiceInstance serviceInstance = null;
		
		//系统日志
		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount()); ----------wsl
		record.setActLevel("01");		
		record.setActTarget("块存储管理");
		record.setActName("块存储服务扩容");
		record.setActStartDate(new Date());
		record.setActEndDate(new Date());
		return true;
//		try {     --------wsl
//			//根据实例sid查询出实例信息
//			serviceInstance = instanceService.selectByPrimaryKey(processObjectId);
//			mgtObjSid = serviceInstance.getMgtObjSid();
//			instanceSid = serviceInstance.getInstanceSid();
//			VdExpandRequest vdExpandRequest = this.instanceChangeLogService.getChangeInfo(instanceSid, new TypeReference<VdExpandRequest>() {});
//			logger.info("expandVd input params:" + JsonUtil.toJson(vdExpandRequest));
//			ResInstResult resInstResult = resVdService.expandVd(vdExpandRequest.getResVdSid(), Long.parseLong(vdExpandRequest.getMgtObjSid()), vdExpandRequest.getSize());
////			ResInstResult resInstResult = mockResVmService.expandVd(vdExpandRequest.getResVdSid(), vdExpandRequest.getMgtObjSid(), vdExpandRequest.getSize());
//			logger.info("expandVd result params:" + JsonUtil.toJson(resInstResult));
//			modifyChangeVdData(resInstResult, serviceInstance, vdExpandRequest);
//			record.setActResult("02");
//			return resInstResult.getStatus();
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//			saveExpandVdErrorTicket(mgtObjSid, instanceSid, "未知错误", false);
//			record.setActResult("01");
//			return false;
//		} finally {
//			record.setActDetail("服务实例名称:"+serviceInstance.getInstanceName()+"块存储扩容");
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);
//			log.debug(record);
//		}
	}

	@Transactional
	public void modifyChangeVdData(ResInstResult resInstResult, ServiceInstance serviceInstance, VdExpandRequest vdExpandRequest) {
		if(ResInstResult.SUCCESS == resInstResult.getStatus()) {
			//更新实例状态为变更中
			serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.CHANGEING);
			instanceService.updateByPrimaryKeySelective(serviceInstance);
			instanceSpecService.updateByName(serviceInstance.getInstanceSid(), "DISK_SIZE", vdExpandRequest.getSize().toString());
//			this.instanceChangeLogService.beginChangeServiceInstance(serviceInstance.getInstanceSid(), WebConstants.instanceChangeType.CHANGE);   ----wsl
		} else {
			saveExpandVdErrorTicket(serviceInstance.getMgtObjSid(), serviceInstance.getInstanceSid(), (String)resInstResult.getMessage(), resInstResult.isReSend());
		}
	}

	@Override
	public void operate(ResInstResult result) {
		try { 
			logger.info("expandVd callback result:" + JsonUtil.toJson(result));
			//根据资源id查询出对应实例id
			ResVd resVd = (ResVd)result.getData();
			String resVdSid = resVd.getResVdSid();
			Long instanceSid = getInstanceSidByResSid(resVdSid);

			//查询出额下服务实例
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
			Long mgtObjSid = instance.getMgtObjSid();
	
			if(ResInstResult.SUCCESS == result.getStatus()) {
				handlerData(instance, resVdSid);
			} else {
				saveExpandVdErrorTicket(mgtObjSid, instanceSid, (String)result.getMessage(), result.isReSend());
			}
		} catch(Exception e) {
			logger.error("operate failure.", e);
		}
		
	}

	/**
	 * 
	 */
	public void handlerData(ServiceInstance instance, String resVmSid) {
		Long instanceSid = instance.getInstanceSid();
		//更新Vm实例状态为正常
		instance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
		instanceService.updateByPrimaryKeySelective(instance);
		this.instanceChangeLogService.endChangeServiceInstance(instance, WebConstants.instanceChangeType.CHANGE);
		mailNotificationsService.changeSuccessEmail(instanceSid);
		ticketService.modifyTicketStatus(instanceSid, WebConstants.TicketProcessType.STORAGE_EXPAND);
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
	public void saveExpandVdErrorTicket(Long mgtObjSid, Long instanceSid, String errorMessage, Boolean isResSend) {
		try {
			if(mgtObjSid == null || instanceSid == null) {
				logger.error("can't generate ticket, because not enought data. mgtObjSid=" + mgtObjSid + " instanceSid=" + instanceSid);
				return;
			}
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);

			instance.setStatus(WebConstants.ServiceInstanceStatus.CHANGEING);
			instanceService.updateByPrimaryKeySelective(instance);

			Criteria criteria = new Criteria();
			criteria.put("statusList", 
					"'" + WebConstants.TicketStatus.CREATED + "'," +
					"'" + WebConstants.TicketStatus.ALLOCATED + "'," +
					"'" + WebConstants.TicketStatus.PROCESSING + "'," +
					"'" + WebConstants.TicketStatus.RESOLVE + "'"
					);
			criteria.put("processType", WebConstants.TicketProcessType.STORAGE_EXPAND);
			criteria.put("processObjectId", instanceSid);
			List<Ticket> ticketList  = ticketService.selectByParams(criteria);
			
			if (!ticketList.isEmpty()){
				logger.info("can't generate ticket, because of existing unsolved ticket with same processType and processObjectId");
				logger.info("update auto handler expand vd ticket record start");
				Ticket ticket = ticketList.get(0);
				criteria = new Criteria();
				criteria.put("ticketSid", ticket.getTicketSid());
				criteria.put("operate", WebConstants.TicketOperate.AUTO_HANDLER);
				criteria.put("operateContent", "");
				List<TicketRecord> ticketRecords = this.ticketRecordService.selectByParams(criteria);
				if(!ticketRecords.isEmpty()) {
					TicketRecord ticketRecord = ticketRecords.get(0);
					ticketRecord.setUpdatedDt(new Date());
					ticketRecord.setOperateContent("云主机变更工单自动处理失败");
					this.ticketRecordService.updateByPrimaryKeySelective(ticketRecord);
				}
				logger.info("update auto handler pand vd ticket record end");
				return;
			}
			
			String type = "云主机变更";
			MgtObj mgtObj = mgtObjService.selectByPrimaryKey(mgtObjSid);
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
			ticket.setTitle(type + "失败-" + instance.getInstanceName());
			ticket.setServiceSid(instance.getServiceSid());
			ticket.setTicketNo(sidService.getMaxSid(WebConstants.SidCategory.TICKET));
			ticket.setVersion(1L);
			ticket.setTenantSid(Long.parseLong(instance.getTanentId()));
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
			ticket.setProcessType(WebConstants.TicketProcessType.STORAGE_EXPAND);
			ticket.setProcessObjectId(instanceSid);
			ticketService.insertSelective(ticket);
			
			mailNotificationsService.ticketNoticeEmail(ticket,WebConstants.RoleSid.OM_MANAGERKLB);
		} catch(Exception e) {
			logger.error("saveExpandVdErrorTicket failure.", e);
		}
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
