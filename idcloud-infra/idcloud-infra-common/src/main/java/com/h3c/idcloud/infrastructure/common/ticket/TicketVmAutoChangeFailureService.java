package com.h3c.idcloud.infrastructure.common.ticket;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.util.DubboUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 云主机自动变更失败工单
 */
@Component
public class TicketVmAutoChangeFailureService extends TicketBaseService {

    private static final Logger logger = LoggerFactory.getLogger(TicketVmAutoChangeFailureService.class);

//    @Reference(version = "1.0.0", group = "vmChangeHandlerImpl")
//    private ResourceRequestHanlder vmChangeHandler;
//
//    private ResVmService resVmService;

    public TicketVmAutoChangeFailureService () {
//        resVmService = DubboUtil.getReference(ResVmService.class, "idcloud-core-service-res-provider", DubboUtil.DEFAULT_URL, DubboUtil.DEFAULT_VERSION);

        this.ticketType = WebConstants.ticketType.VM_AUTO_CHANGE_FAILURE_TICKET;
        this.ticketTitle = "云主机自动变更失败工单";
    }

    public String getTicketTitle(Map<String, Object> params) {
        String instanceName = (String)params.get("instanceName");
        return ticketTitle + "-" + instanceName;
    }

    @Override
    public void manualHandlerTicketUpdateServiceData(Map<String, Object> params) {
//        logger.info("parmas =" + JsonUtil.toJson(params));
//        ServiceInstance instance = (ServiceInstance)params.get("instance");
//        Long vmInstanceSid = instance.getInstanceSid();
//        //获取云主机变更参数
//        ServiceInstanceChangeLogSpec<ResVmInst> openSpec = this.instanceChangeLogService.getChangeInfo(instance.getInstanceSid(), new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>(){});
//        ResVmInst resVmInst = openSpec.getParams();
//        Map<String, Object> variables = openSpec.getVariables();
//
//        Integer cupCores = resVmInst.getCpu();
//        Long memorySize = resVmInst.getMemory();
//        if(cupCores != 0) {
//            instanceSpecService.updateByName(vmInstanceSid, "CPU", String.valueOf(cupCores));
//        }
//        if(memorySize != 0L) {
//            instanceSpecService.updateByName(vmInstanceSid, "MEMORY", String.valueOf(memorySize / 1024));
//        }
//
//        String diskInstanceSids = (String)variables.get("diskInstanceSids");
//        if(!StringUtil.isNullOrEmpty(diskInstanceSids)){
//            String [] diskInstanceSidArray = diskInstanceSids.split(",");
//            List<ResVdInst> resVdList = resVmInst.getDataDisks();
//            for(int i = 0; i < resVdList.size(); i++) {
//                ResVdInst resVd = resVdList.get(i);
//                Long diskInstanceSid = Long.parseLong(diskInstanceSidArray[i]);
//                if(WebConstants.VdOperate.ADD.equals(resVd.getOperate())) {
//                    //将新加的磁盘实例状态改为开通
//                    ServiceInstance diskInstance = instanceService.selectByPrimaryKey(diskInstanceSid);
//                    diskInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//                    instanceService.updateByPrimaryKeySelective(diskInstance);
//                } else if(WebConstants.VdOperate.EXPAND.equals(resVd.getOperate())) {
//                    //更新扩容的磁盘的实例规格项为变更后的分配大小
//                    Long diskSize = resVd.getDiskSize();
//                    this.instanceSpecService.updateByName(diskInstanceSid, WebConstants.InstanceSpecType.DISK_SIZE, diskSize.toString());
//
//                    //将扩容的磁盘实例状态改为开通
//                    ServiceInstance diskInstance = instanceService.selectByPrimaryKey(diskInstanceSid);
//                    diskInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//                    instanceService.updateByPrimaryKeySelective(diskInstance);
//                } else if(WebConstants.VdOperate.DELLETE.equals(resVd.getOperate())) {
//                    //将删除磁盘实例状态改为退订
//                    ServiceInstance diskInstance = instanceService.selectByPrimaryKey(diskInstanceSid);
//                    diskInstance.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
//                    instanceService.updateByPrimaryKeySelective(diskInstance);
//                }
//            }
//        }
//        instance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//        instanceService.updateByPrimaryKeySelective(instance);
//        resVmService.reconfigVmAfterDeal(resVmInst);
//
//        this.instanceChangeLogService.endChangeServiceInstance(instance, WebConstants.instanceChangeType.CHANGE);
//        mailNotificationsService.changeSuccessEmail(vmInstanceSid);
    }

    @Override
    public void autoHandlerTicket(Map<String, Object> params) {
//        logger.info("parmas =" + JsonUtil.toJson(params));
//        String processObjectId = (String)params.get("processObjectId");
//        Long instanceSid = Long.parseLong(processObjectId);
//        Boolean result = vmChangeHandler.invoke(instanceSid);
//        if(!result) {
//            throw new RuntimeException("ticket autoHandler vmChangeHandler.invoke() failure");
//        }
    }
}
