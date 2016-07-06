package com.h3c.idcloud.infrastructure.common.ticket;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * AIX系统外置盘开通工单
 */
@Component
public class TicketAixDiskRemoveService extends TicketBaseService {

    private static final Logger logger = LoggerFactory.getLogger(TicketAixDiskRemoveService.class);

    public TicketAixDiskRemoveService () {
        this.ticketType = WebConstants.ticketType.AIX_DISK_REMOVE_TICKET;
        this.ticketTitle = "AIX系统外置盘卸载工单";
    }

    @Override
    public String getTicketContent(Map<String, Object> params) {
        Long mgtObjSid = (Long)params.get("mgtObjSid");
//        List<ServiceInstance> dataDiskInstances = (List<ServiceInstance>)params.get("dataDiskInstances");
//        MgtObj mgtObj = mgtObjService.selectByPrimaryKey(mgtObjSid);
//        StringBuilder content = new StringBuilder();
//        content.append("所属项目:").append(mgtObj.getName()).append("\r\n");
//        content.append("待卸载AIX系统外置盘规格: ").append("\r\n");;
//        for(ServiceInstance diskInstance : dataDiskInstances) {
//            List<ServiceInstanceSpec> diskSpecs = instanceSpecService.selectByInstanceSpecBySid(diskInstance.getInstanceSid());
//            String diskSize = ResDataUtils.getSpecValueFromSpecs(WebConstants.InstanceSpecType.DISK_SIZE, diskSpecs);
//            content.append(diskSize).append("GB").append(",");
//        }
//        return content.toString().substring(0, content.length() - 1);
        return null;
    }

    @Override
    public void manualHandlerTicketUpdateServiceData(Map<String, Object> params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void autoHandlerTicket(Map<String, Object> params) {
        // TODO Auto-generated method stub

    }
}
