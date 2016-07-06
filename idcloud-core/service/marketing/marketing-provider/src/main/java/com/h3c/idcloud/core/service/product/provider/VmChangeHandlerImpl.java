package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstResKey;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.h3c.idcloud.core.service.customer.mgt.service.TicketMgt;

/**
 * 虚拟机变更申请回调处理
 * 
 * @author ChengQi
 *
 */
//@Service("vmChangeHandlerImpl")
@Service(version = "1.0.0",group = "vmChangeHandlerImpl")
@Component("vmChangeHandlerImpl")
public class VmChangeHandlerImpl implements ResourceRequestHanlder {
	
	private static final Logger logger = LoggerFactory
			.getLogger(VmChangeHandlerImpl.class);

	/** 资源申请接口Service */
	@Reference(version = "1.0.0")
	private ResVmService resVmService;
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
	
//	@Autowired       -----------wsl
//	private SysLoggerFactory sysLogger;

	@Autowired
	private ServiceInstanceChangeLogService instanceChangeLogService;

//	@Reference(version = "1.0.0")
//	private TicketMgt ticketMgt;

	@Override
	public boolean invoke(Long processObjectId) {
		Long mgtObjSid = null;
		Long instanceSid = processObjectId;	
		ServiceInstance serviceInstance = null;
		
		//系统日志
		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount()); ------------wsl
		record.setActLevel("01");		
		record.setActTarget("云主机管理");
		record.setActName("服务调整规格");
		record.setActStartDate(new Date());
		record.setActEndDate(new Date());
		Boolean result = false;
//		try {    ---wsl
//			//根据实例sid查询出实例信息
//			serviceInstance = instanceService.selectByPrimaryKey(processObjectId);
//			mgtObjSid = serviceInstance.getMgtObjSid();
//			instanceSid = serviceInstance.getInstanceSid();
//			List<ServiceInstanceSpec> specList = instanceSpecService.selectByInstanceSpecBySid(instanceSid);
//			ServiceInstanceSpec osSpec = ResDataUtils.getSpecObjectFromSpecs(WebConstants.InstanceSpecType.OS, specList);
//			String ve = osSpec.getVe();
//
//			//获取云主机变更参数
//			ServiceInstanceChangeLogSpec<ResVmInst> openSpec = this.instanceChangeLogService.getChangeInfo(serviceInstance.getInstanceSid(), new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {});
//			ResVmInst resVmInst = openSpec.getParams();
//			Map<String, Object> variables = openSpec.getVariables();
///*
//			if(WebConstants.VirtualEnv.POWER.equals(ve)) {
//				List<ResVdInst> dataDisks = resVmInst.getDataDisks();
//				List<ResVdInst> addDisks = new ArrayList<ResVdInst>();
//				for(ResVdInst disk : dataDisks) {
//					if(WebConstants.VdOperate.ADD.equals(disk.getOperate())) {
//						addDisks.add(disk);
//					}
//				}
//				//创建AIX系统外置盘开通工单
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("mgtObjSid", mgtObjSid);
//				params.put("instanceSid", serviceInstance.getInstanceSid());
//				params.put("dataDisks", addDisks);
//				ticketMgt.createTicket(WebConstants.ticketType.AIX_DISK_OPEN_TICKET, params);
//				//清除掉添加的磁盘参数
//				//dataDisks.removeAll(addDisks);
//			}
//*/
//			logger.info("reconfigVm input params:" + JsonUtil.toJson(resVmInst));
//			ResInstResult resInstResult = resVmService.reconfigVm(resVmInst);
////			ResInstResult resInstResult = mockResVmService.reconfigVm(resVmInst);
//			logger.info("reconfigVm result params:" + JsonUtil.toJson(resInstResult));
//			modifyChangeVmData(resInstResult, serviceInstance, resVmInst, variables);
////			modifyChangeVmData(resInstResult, serviceInstance,diskServiceInstances,diskServiceDeleteInstances);
//			record.setActResult("02");
//			result = resInstResult.getStatus();
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//			saveChangeVmErrorTicket(mgtObjSid, instanceSid, "未知错误", false);
//			record.setActResult("01");
//			result = false;
//		} finally {
//			record.setActDetail("服务实例名称:"+serviceInstance.getInstanceName()+"云主机管理:服务变更");
////			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB); -----------wsl
////			log.debug(record);
//		}
		return result;
	}

	@Transactional
	public void modifyChangeVmData(ResInstResult resInstResult, ServiceInstance serviceInstance, ResVmInst resVmInst, Map<String, Object> variables) {
		if(ResInstResult.SUCCESS == resInstResult.getStatus()) {
			//更新实例状态为变更中
			serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.CHANGEING);
			instanceService.updateByPrimaryKeySelective(serviceInstance);
			
			String diskInstanceSids = (String)variables.get("diskInstanceSids");
			if(diskInstanceSids != null) {
				String [] diskInstanceSidArray = diskInstanceSids.split(",");
				//添加新增磁盘关联关系
				ResVm resVm = (ResVm)resInstResult.getData();
				List<ResVd> resVdList = resVm.getResVdList();
				if(resVdList != null && resVdList.size() > 0){
					for(int i = 0; i < resVdList.size(); i++) {
						Long diskInstanceSid = Long.parseLong(diskInstanceSidArray[i]);
						if(WebConstants.VdOperate.ADD.equals(resVdList.get(i).getOperate())){
							ServiceInstRes diskServiceInstRes = new ServiceInstRes();
							diskServiceInstRes.setInstanceSid(diskInstanceSid);
							diskServiceInstRes.setResId(resVdList.get(i).getResVdSid());
							diskServiceInstRes.setResType(WebConstants.ResourceType.RES_VD);
							serviceInstResService.insert(diskServiceInstRes);
						}
						//更新变更的磁盘服务状态为变更中
						ServiceInstance diskServiceInstance = this.instanceService.selectByPrimaryKey(diskInstanceSid);
						diskServiceInstance.setStatus(WebConstants.ServiceInstanceStatus.CHANGEING);
						instanceService.updateByPrimaryKeySelective(diskServiceInstance);
					}
				}
			}
//			this.instanceChangeLogService.beginChangeServiceInstance(serviceInstance.getInstanceSid(), WebConstants.instanceChangeType.CHANGE); --wsl
		} else {
			saveChangeVmErrorTicket(serviceInstance.getMgtObjSid(), serviceInstance.getInstanceSid(), (String)resInstResult.getMessage(), resInstResult.isReSend());
		}
	}

	@Override
	public void operate(ResInstResult result) {
		try { 
			logger.info("reconfigVm callback result:" + JsonUtil.toJson(result));
			//根据资源id查询出对应实例id
			ResVm resVm = (ResVm)result.getData();
			String resVmSid = resVm.getResVmSid();
			Long instanceSid = this.serviceInstResService.getInstanceSidByResSid(resVmSid);
			//查询出Vm实例
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
			Long mgtObjSid = instance.getMgtObjSid();
	
			if(ResInstResult.SUCCESS == result.getStatus()) {
				//查询虚拟机实例的磁盘实例
				List<ResVd> vdList = resVm.getResVdList();
				if(vdList !=null && vdList.size() > 0){
					Long dataDiskTotalSize = 0L;
					for(ResVd resVd : vdList) {
						if(WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
							dataDiskTotalSize += resVd.getAllocateDiskSize();
						}
						Long diskInstanceSid = this.serviceInstResService.getInstanceSidByResSid(resVd.getResVdSid());
						if(WebConstants.VdOperate.ADD.equals(resVd.getOperate())) {
							//将新加的磁盘实例状态改为开通
//							if(resVm.getParType() == null) {
								ServiceInstance diskInstance = instanceService.selectByPrimaryKey(diskInstanceSid);
								diskInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
								instanceService.updateByPrimaryKeySelective(diskInstance);
//							} else {
								//Power分区
								//生成AIX系统数据盘手动开通工单
//								List<ResVd> dataDisks = new ArrayList<ResVd>();
//								dataDisks.add(resVd);
//								Map<String, Object> params = new HashMap<String, Object>();
//								params.put("mgtObjSid", mgtObjSid);
//								params.put("instanceSid", instanceSid);
//								params.put("dataDisks", dataDisks);
//								ticketMgt.createTicket(WebConstants.ticketType.AIX_DISK_OPEN_TICKET, params);
//							}
						} else if(WebConstants.VdOperate.EXPAND.equals(resVd.getOperate())) {
							//更新扩容的磁盘的实例规格项为变更后的分配大小
							Long diskSize = resVd.getAllocateDiskSize();
							this.instanceSpecService.updateByName(diskInstanceSid, WebConstants.InstanceSpecType.DISK_SIZE, diskSize.toString());

							//将扩容的磁盘实例状态改为开通
							ServiceInstance diskInstance = instanceService.selectByPrimaryKey(diskInstanceSid);
							diskInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
							instanceService.updateByPrimaryKeySelective(diskInstance);
						} else if(WebConstants.VdOperate.DELLETE.equals(resVd.getOperate())) {
							//将删除磁盘实例状态改为退订
							ServiceInstance diskInstance = instanceService.selectByPrimaryKey(diskInstanceSid);
							diskInstance.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
							instanceService.updateByPrimaryKeySelective(diskInstance);
							//删除退订磁盘的关联关系
							Criteria cre = new Criteria();
							cre.put("instanceSid", diskInstanceSid);
							List<ServiceInstRes> instRes = serviceInstResService.selectByParams(cre);
							if(instRes.size() >  0) {
								ServiceInstResKey key = new ServiceInstResKey();
								key.setInstanceSid(diskInstanceSid);
								key.setResId(instRes.get(0).getResId());
								serviceInstResService.deleteByPrimaryKey(key);
							}
						}
					}
					this.instanceSpecService.updateByName(instanceSid, WebConstants.InstanceSpecType.DATA_DISK, dataDiskTotalSize.toString());
				}

				Integer cupCores = resVm.getCpuCores();
				Long memorySize = resVm.getMemorySize();
				if(cupCores != 0) {
					instanceSpecService.updateByName(instanceSid, "CPU", String.valueOf(cupCores));
				}
				if(memorySize != 0L) {
					instanceSpecService.updateByName(instanceSid, "MEMORY", String.valueOf(memorySize / 1024));
				}
				handlerData(instance, resVmSid);
			} else {
				//删除新增盘的关系
//				List<ResVd> vdList = resVm.getResVdList();   --------wsl
//				if(vdList!=null && vdList.size()!=0){
//					for (ResVd resVd : vdList) {
//						if(resVd.getOperate().equals(WebConstants.VdOperate.ADD)){
//							Long instanceId = this.serviceInstResService.getInstanceSidByResSid(resVd.getResVdSid());
//							ServiceInstResKey key = new ServiceInstResKey();
//							key.setInstanceSid(instanceId);
//							key.setResId(resVd.getResVdSid());
//							serviceInstResService.deleteByPrimaryKey(key);
//						}
//					}
//				}
				saveChangeVmErrorTicket(mgtObjSid, instanceSid, (String)result.getMessage(), result.isReSend());
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
		ticketService.modifyTicketStatus(instanceSid, WebConstants.ticketType.VM_AUTO_CHANGE_FAILURE_TICKET);
	}


	@Transactional
	public void saveChangeVmErrorTicket(Long mgtObjSid, Long instanceSid, String errorMessage, Boolean isResSend) {
		try {
			
			ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
			instance.setStatus(WebConstants.ServiceInstanceStatus.CHANGEING);
			instanceService.updateByPrimaryKeySelective(instance);

			//生成云主机自动变更失败工单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mgtObjSid", mgtObjSid);
			params.put("instanceSid", instanceSid);
			params.put("errorMessage", errorMessage);
			params.put("isResSend", isResSend);
//			ticketMgt.createTicket(WebConstants.ticketType.VM_AUTO_CHANGE_FAILURE_TICKET, params);   ----wsl
		} catch(Exception e) {
			logger.error("saveChangeVmErrorTicket failure.", e);
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
