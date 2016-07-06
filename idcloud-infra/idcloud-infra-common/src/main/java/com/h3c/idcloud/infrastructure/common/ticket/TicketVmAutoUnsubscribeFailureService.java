package com.h3c.idcloud.infrastructure.common.ticket;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.DubboUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 云主机自动退订失败工单
 */
@Component
public class TicketVmAutoUnsubscribeFailureService extends TicketBaseService {

    private static final Logger logger = LoggerFactory.getLogger(TicketVmAutoUnsubscribeFailureService.class);

//    @Reference(version = "1.0.0", group = "vmDeleteHandlerImpl")
//    private ResourceRequestHanlder vmDeleteHandler;
//
//    private ResVmService resVmService;

    public TicketVmAutoUnsubscribeFailureService () {
//        resVmService = DubboUtil.getReference(ResVmService.class, "idcloud-core-service-marketing-provider", DubboUtil.DEFAULT_URL, DubboUtil.DEFAULT_VERSION);

        this.ticketType = WebConstants.ticketType.VM_AUTO_UNSUBSCRIBE_FAILURE_TICKET;
        this.ticketTitle = "云主机自动回收失败工单";
    }

    public String getTicketTitle(Map<String, Object> params) {
        String instanceName = (String)params.get("instanceName");
        return ticketTitle + "-" + instanceName;
    }

    @Override
    public void manualHandlerTicketUpdateServiceData(Map<String, Object> params) {
//        logger.info("parmas =" + JsonUtil.toJson(params));
//        try {
//            ServiceInstance instance = (ServiceInstance)params.get("instance");
//            //更新云主机服务实例的状态为退订
//            instance.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
//            instanceService.updateByPrimaryKeySelective(instance);
//
//            //更新云主机下的所有子服务实例的状态为已退订
//            instanceService.modifyAllChildServiceInstancesOfVmStatus(instance.getInstanceSid(), WebConstants.ServiceInstanceStatus.CANCELED);
//
//            //删除服务实例与资源关联关系
//            Criteria cre = new Criteria();
//            cre.put("instanceSid", instance.getInstanceSid());
//            List<ServiceInstRes> instRes = instResService.selectByParams(cre);
//            if(instRes.size() >  0) {
//                ServiceInstResKey key = new ServiceInstResKey();
//                key.setInstanceSid(instance.getInstanceSid());
//                key.setResId(instRes.get(0).getResId());
//                instResService.deleteByPrimaryKey(key);
//                this.resVmService.deleteVmAfterDeal(instRes.get(0).getResId());
//            }
//
//            ServiceInstanceChangeLog unsubscribeLog = this.instanceChangeLogService.getLastChangeLog(instance.getInstanceSid());
//
//            this.instanceChangeLogService.endChangeServiceInstance(instance, WebConstants.instanceChangeType.UNSUBSCRIBE);
//
//            String changePreSpec = unsubscribeLog.getChangePreSpec();
//            //发送退订通知邮件
//            mailNotificationsService.unsubscribeServiceEmail(instance.getInstanceSid(), JsonUtil.fromJson(changePreSpec, ResVm.class));
//        } catch(Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void autoHandlerTicket(Map<String, Object> params) {
//        logger.info("parmas =" + JsonUtil.toJson(params));
//        String processObjectId = (String)params.get("processObjectId");
//        Long instanceSid = Long.parseLong(processObjectId);
//        Boolean result = vmDeleteHandler.invoke(instanceSid);
//        if(!result) {
//            throw new RuntimeException("ticket autoHandler vmDeleteHandler.invoke() failure");
//        }
    }
}
