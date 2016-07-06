package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.service.product.api.MonitorService;
import com.h3c.idcloud.core.service.product.api.ResInfoSync;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ResInfoSyncImpl implements ResInfoSync {

	private static final Logger logger = LoggerFactory.getLogger(ResInfoSyncImpl.class);
	
//	@Autowired        --------wsl
//	private ResBizVmService resBizVmService;

	@Autowired
	private MonitorService monitorService;

	@Autowired
	private ServiceInstResService serviceInstResService;

	@Autowired
	private ServiceInstanceSpecService instanceSpecService;

	@Reference(version = "1.0.0")
	private ResVmService resVmService;

	@Override
	public void updateResVmInfo(String resVmSid,String operate) {
		try {
			if(WebConstants.scanVmChangeType.CHANGE.equals(operate)) {
				//TkUtils.updateMonitorNode(resVmSid, false);
				//更新服务层相关信息
				Long instanceSid = null;
				try {
					instanceSid = serviceInstResService.getInstanceSidByResSid(resVmSid);
				} catch(Exception e) {
					logger.debug("The vm is not mapping service");
				}
				if(instanceSid == null) {
					return;
				}
				ResVm resVm = this.resVmService.getVmInfo(resVmSid);
				Integer cpuCores = resVm.getCpuCores();
				Long memorySize = resVm.getMemorySize() / 1024;
				this.instanceSpecService.updateByName(instanceSid, WebConstants.InstanceSpecType.CPU, cpuCores.toString());
				this.instanceSpecService.updateByName(instanceSid, WebConstants.InstanceSpecType.MEMORY, memorySize.toString());
				List<ResVd> resVdList = resVm.getResVdList();
				if(resVdList !=null && resVdList.size() > 0){
					Long dataDiskTotalSize = 0L;
					Long sysDiskTotalSize = 0L;
					for(ResVd resVd : resVdList) {
						if(WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) { 
							dataDiskTotalSize += resVd.getAllocateDiskSize();
						} else if(WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose())) {
							sysDiskTotalSize += resVd.getAllocateDiskSize();
						}
						Long diskInstanceSid = null;
						try {
							diskInstanceSid = serviceInstResService.getInstanceSidByResSid(resVd.getResVdSid());
						} catch(Exception e) {
							logger.debug("The resVd is not mapping service");
						}
						if(diskInstanceSid == null) {
							continue;
						}
						this.instanceSpecService.updateByName(diskInstanceSid, WebConstants.InstanceSpecType.DISK_SIZE, resVd.getAllocateDiskSize().toString());
					}
					this.instanceSpecService.updateByName(instanceSid, WebConstants.InstanceSpecType.SYSTEM_DISK, sysDiskTotalSize.toString());
					this.instanceSpecService.updateByName(instanceSid, WebConstants.InstanceSpecType.DATA_DISK, dataDiskTotalSize.toString());
				}
				
			} else if(WebConstants.scanVmChangeType.DELETE.equals(operate)){
				//删除服务实例相关信息
//				resBizVmService.deleteServiceInstanceRelation(resVmSid);   --------wsl
				//TkUtils.updateMonitorNode(resVmSid, true);
				//删除监控
				monitorService.deleteVmFromMonitor(resVmSid);
			}
		} catch(Exception e) {
			logger.error("同步操作时，更新服务层信息失败", e);
		}
	}


}
