package com.h3c.idcloud.infrastructure.common.ticket;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 云主机自动开通失败工单
 */
@Component
public class TicketVmAutoOpenFailureService extends TicketBaseService {

    private static final Logger logger = LoggerFactory.getLogger(TicketVmAutoOpenFailureService.class);

//    @Reference(version = "1.0.0",group = "vmOpenHandlerImpl")
//    private ResourceRequestHanlder vmOpenHanlder;

    public TicketVmAutoOpenFailureService() {
        this.ticketType = WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET;
        this.ticketTitle = "云主机自动开通失败工单";
    }

    @Override
    public void manualHandlerTicketUpdateServiceData(Map<String, Object> params) {
//        logger.info("parmas =" + JsonUtil.toJson(params));
//        ServiceInstance instance = (ServiceInstance)params.get("instance");
//
//        String orderId = instance.getOrderId();
//        Long instanceSid = instance.getInstanceSid();
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
//        logger.info("parmas =" + JsonUtil.toJson(params));
//        String processObjectId = (String)params.get("processObjectId");
//        Long instanceSid = Long.parseLong(processObjectId);
//        ServiceInstance serviceInstance = this.instanceService.selectByPrimaryKey(instanceSid);
//        ServiceInstanceChangeLogSpec<ResVmInst> instanceChangeLogSpec = this.instanceService.assembleServiceInstanceOpenParams(serviceInstance, params);
//        this.instanceChangeLogService.updateChangeServiceInstance(serviceInstance.getInstanceSid(),
//                WebConstants.instanceChangeType.CREATE,
//                instanceChangeLogSpec, new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {});
//        Boolean result = vmOpenHanlder.invoke(instanceSid);
//        if(!result) {
//            throw new RuntimeException("ticket autoHandler vmOpenHandler.invoke() failure");
//        }
    }
}
