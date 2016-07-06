package com.h3c.idcloud.infrastructure.common.ticket;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.DubboUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 物理机开通工单
 */
@Component
public class TicketHostOpenService extends TicketBaseService {

    private static final Logger logger = LoggerFactory.getLogger(TicketHostOpenService.class);

//    private ResHostService hostService;
//
//    @Autowired
//    private ResStorageMapper resStorageMapper;

    public TicketHostOpenService () {
//        hostService = DubboUtil.getReference(ResHostService.class, "idcloud-core-service-res-provider", DubboUtil.DEFAULT_URL, DubboUtil.DEFAULT_VERSION);

        this.ticketType = WebConstants.ticketType.HOST_OPEN_TICKET;
        this.ticketTitle = "物理机开通工单";

    }

    @Override
    public String getTicketTitle(Map<String, Object> params) {
        String instanceName = (String)params.get("instanceName");
        return this.ticketTitle + "-" + instanceName;
    }

    @Override
    public String getTicketContent(Map<String, Object> params) {
//        Long instanceSid = (Long)params.get("instanceSid");
//        Long mgtObjSid = (Long)params.get("mgtObjSid");
//        MgtObj mgtObj = mgtObjService.selectByPrimaryKey(mgtObjSid);
//        List<ServiceInstanceSpec> instanceSpecs = instanceSpecService.selectByInstanceSpecBySid(instanceSid);
//        StringBuilder content = new StringBuilder();
//        content.append("所属项目:").append(mgtObj.getName()).append("\r\n");
//        content.append("待开通物理机规格:").append("\r\n");;
//        for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
//            if(StringUtils.isNotBlank(instanceSpec.getValueText())) {
//                content.append("  ").append(instanceSpec.getDescription()).append(":").append(instanceSpec.getValueText()).append("\r\n");
//            }
//        }
//        return content.toString();
        return null;
    }

    @Override
    public void manualHandlerTicketUpdateServiceData(Map<String, Object> params) {
//        ServiceInstance instance = (ServiceInstance)params.get("instance");
//        String orderId = instance.getOrderId();
//        Long instanceSid = instance.getInstanceSid();
//
//        ServiceInstanceChangeLogSpec<ResVmInst> openSpec = instanceChangeLogService.getChangeInfo(instanceSid, new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {});
//        Map<String, Object> variables = openSpec.getVariables();
//        String hostId = (String)variables.get("hostId");
//        if(hostId == null) {
//            logger.error("final approve need save the hostId to db.");
//            throw new RuntimeException("The hostId is null");
//        }
//        ResHost resHost = this.hostService.getHostInfo(hostId);
//        Integer cpuCores = resHost.getCpuCores();
//        Double memory = resHost.getMemorySize() / 1024;
//        if(cpuCores != 0) {
//            instanceSpecService.updateByName(instanceSid, "CPU", cpuCores.toString());
//        }
//        if(memory != 0L) {
//            instanceSpecService.updateByName(instanceSid, "MEMORY", memory.toString());
//        }
//        Criteria criteria = new Criteria();
//        criteria.put("parentInstanceSid", instanceSid);
//        criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_STORAGE);
//        List<ServiceInstance> diskInstances = this.instanceService.selectByParams(criteria);
//        for(ServiceInstance diskInstance : diskInstances) {
//            String diskResSid = this.instResService.getResSidByInstanceSid(diskInstance.getInstanceSid());
//            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(diskResSid);
//            Long diskSize = resStorage.getTotalCapacity();
//            this.instanceSpecService.updateByName(diskInstance.getInstanceSid(), WebConstants.InstanceSpecType.DISK_SIZE, diskSize.toString());
//        }
//
//        //更新云主机下的所有子服务实例的状态为已开通
//        instanceService.modifyAllChildServiceInstancesOfVmStatus(instanceSid, WebConstants.ServiceInstanceStatus.OPENED);
//
//        //检查云主机下的所有子服务实例是否开通完成,如果全部完成则更新云主机服务实例状态为已开通, 并发送邮件通知
//        instanceService.modifyVmServiceInstanceStatus(instance);
//
//        //检查当前云主机所属订单下的所有云主机服务实例是否全部开通完成，如果全部完成则更新订单的状态为已开通
//        instanceService.modifyOrderStatusOfVmServiceInstance(orderId);
    }

    @Override
    public void autoHandlerTicket(Map<String, Object> params) {
        //物理机开通没有自动开通
    }
}
