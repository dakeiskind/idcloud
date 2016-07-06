package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.product.api.MonitorService;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.h3c.idcloud.core.service.customer.mgt.service.TicketMgt;

/**
 * 虚拟机退订申请回调处理
 * 
 * @author Chengqi
 *
 */
//@Service("vmDeleteHandlerImpl")
@Service(version = "1.0.0",group = "vmDeleteHandlerImpl")
@Component("vmDeleteHandlerImpl")
public class VmDeleteHandlerImpl implements ResourceRequestHanlder {

	private static final Logger logger = LoggerFactory
			.getLogger(VmDeleteHandlerImpl.class);

	/** 资源申请接口Service */
	@Reference(version = "1.0.0")
	private ResVmService resVmService;
	
//	@Autowired
//	private MockResVmService mockResVmService;

	@Autowired
	private ServiceInstanceService instanceService;

	@Autowired
	private ServiceInstResService serviceInstResService;

	@Reference(version = "1.0.0")
	private MailNotificationsService mailNotificationsService;

	@Reference(version = "1.0.0")
	private TicketService ticketService;

//	@Autowired           ---------------wsl
//	private SysLoggerFactory sysLogger;

	@Autowired
	private ServiceInstanceChangeLogService changeLogService;
	
//	@Autowired
//	private TicketMgt ticketMgt;

	@Autowired
	private ServiceInstanceSpecService instanceSpecService;

	@Autowired
	private MonitorService monitorService;

	@Override
	public boolean invoke(Long processObjectId) {
		Long mgtObjSid = null;
		Long instanceSid = null;
		
		//系统日志
		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser()==null?"admin":AuthUtil.getAuthUser().getAccount()); ---------wsl
		record.setActLevel("01");		
		record.setActTarget("自服务");
		record.setActName("服务退订");
		record.setActStartDate(new Date());
		record.setActEndDate(new Date());
		ServiceInstance serviceInstance = null;
		Boolean result = false;
		try {
			//根据虚拟机实例id查询出关联的虚拟机资源id
			Criteria criteria = new Criteria();
			criteria.put("instanceSid", instanceSid);
			List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(criteria);
			
			if(CollectionUtils.isEmpty(serviceInstReses)){
				//取消纳管类型的退订
				record.setActResult("02");
				result = true;
			}else{
				String vmResId = serviceInstResService.getResSidByInstanceSid(processObjectId);
				//根据实例sid查询出实例信息
				serviceInstance = instanceService.selectByPrimaryKey(processObjectId);
				mgtObjSid = serviceInstance.getMgtObjSid();
				instanceSid = serviceInstance.getInstanceSid();
/*			
			//查询出云主机的虚拟机化环境，如果是AIX系统则生成数据盘卸载工单
			List<ServiceInstanceSpec> specList = instanceSpecService.selectByInstanceSpecBySid(instanceSid);
			ServiceInstanceSpec osSpec = ResDataUtils.getSpecObjectFromSpecs(WebConstants.InstanceSpecType.OS, specList);
			String ve = osSpec.getVe();
			if(WebConstants.VirtualEnv.POWER.equals(ve)) {
				Criteria criteria = new Criteria();
				criteria.put("parentInstanceSid", instanceSid);
				criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
				List<ServiceInstance> diskInstances = this.instanceService.selectByParams(criteria);
				//创建AIX系统外置盘卸载工单
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("mgtObjSid", mgtObjSid);
				params.put("instanceSid", serviceInstance.getInstanceSid());
				params.put("dataDiskInstances", diskInstances);
				ticketMgt.createTicket(WebConstants.ticketType.AIX_DISK_REMOVE_TICKET, params);
			}
*/			
				logger.info("deleteVm input params: vmResId=" + vmResId);
				// TODO ServiceBaseInput from REST
				ResInstResult resInstResult = resVmService.deleteVm(new ResCommonInst());
//				ResInstResult resInstResult = mockResVmService.deleteVm(vmResId);
				logger.info("deleteVm return params:" + JsonUtil.toJson(resInstResult));
				modifyChangeVmData(resInstResult, serviceInstance);
				record.setActResult("02");
				result = resInstResult.getStatus();
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			saveDeleteVmErrorTicket(mgtObjSid, instanceSid, "未知错误", false);
			record.setActResult("01");	
			result = false;
		} finally {
			record.setActDetail("云主机名称:"+serviceInstance.getInstanceName()+"云主机操作:退订");
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);  -----------wsl
//			log.debug(record);
		}
		return result;
		
	}

	@Transactional
	public void modifyChangeVmData(ResInstResult resInstResult, ServiceInstance serviceInstance) {
		if(ResInstResult.SUCCESS == resInstResult.getStatus()) {
			//更新实例状态为退订中
			serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.CANCELING);
			// 更新实例退订时间,方便服务计费---by jipeigui
			
			instanceService.updateByPrimaryKeySelective(serviceInstance);
			//更新实例变更日志
//			this.changeLogService.beginChangeServiceInstance(serviceInstance.getInstanceSid(), WebConstants.instanceChangeType.UNSUBSCRIBE); ---wsl
		} else {
			saveDeleteVmErrorTicket(serviceInstance.getMgtObjSid(), serviceInstance.getInstanceSid(), (String)resInstResult.getMessage(), resInstResult.isReSend());
		}
	}

	@Override
	public void operate(ResInstResult result) {
		try {
			logger.info("deleteVm callback result:" + JsonUtil.toJson(result));
			//根据资源id查询出对应实例id
			ResVm resVm = (ResVm)result.getData();
			String resVmSid = resVm.getResVmSid();
			Long instanceSid = null;
			Criteria criteria = new Criteria();
			criteria.put("resId", resVmSid);
			List<ServiceInstRes> serviceInstReses = this.serviceInstResService.selectByParams(criteria);
			if(serviceInstReses.size() > 0) {
				instanceSid = serviceInstReses.get(0).getInstanceSid();
			}
			if(instanceSid == null) {
				logger.warn("The resSid no mapping in service_inst_res table resSid=" + resVmSid);
				this.resVmService.deleteVmAfterDeal(resVmSid);
				try {
					monitorService.deleteVmFromMonitor(resVm);
				} catch(Exception e) {
					logger.error("delete monitor failure.", e);
				}
				return;
			}
			//查询出Vm实例
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
			Long mgtObjSid = instance.getMgtObjSid();

			if(ResInstResult.SUCCESS == result.getStatus()) {
				//更新Vm实例状态为正常,更新内外网络
				instance.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
				//设置销毁时间
				instance.setDestroyDate(new Date());
				instanceService.updateByPrimaryKeySelective(instance);

				//更新云主机下的磁盘的服务实例状态为已退订
				this.instanceService.modifyAllChildServiceInstancesOfVmStatus(instanceSid, WebConstants.ServiceInstanceStatus.CANCELED);
				
				//更新实例变更日志
				this.changeLogService.endChangeServiceInstance(instance, WebConstants.instanceChangeType.UNSUBSCRIBE);
				ticketService.modifyTicketStatus(instanceSid, WebConstants.ticketType.VM_AUTO_UNSUBSCRIBE_FAILURE_TICKET);
				ticketService.deleteInstanceTickets(instanceSid);

				try {
					monitorService.deleteVmFromMonitor(resVm);
				} catch(Exception e) {
					logger.error("delete monitor failure.", e);
				}
				//发送退订通知邮件
				mailNotificationsService.unsubscribeServiceEmail(instanceSid, resVm);
			} else {
				saveDeleteVmErrorTicket(mgtObjSid, instanceSid, (String)result.getMessage(), result.isReSend());
			}
		} catch(Exception e) {
			logger.error("operate failure.", e);
		}

	}

	@Transactional
	public void saveDeleteVmErrorTicket(Long mgtObjSid, Long instanceSid, String errorMessage, Boolean isResSend) {
		try {
			//生成云主机自动退订失败工单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mgtObjSid", mgtObjSid);
			params.put("instanceSid", instanceSid);
			params.put("errorMessage", errorMessage);
			params.put("isResSend", isResSend);
//			ticketMgt.createTicket(WebConstants.ticketType.VM_AUTO_UNSUBSCRIBE_FAILURE_TICKET, params);-------wsl
		} catch(Exception e) {
			logger.error("saveDeleteVmErrorTicket failure.", e);
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
