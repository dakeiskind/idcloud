package com.h3c.idcloud.infrastructure.common.ticket;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.util.DubboUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * AIX系统外置盘开通工单
 */
@Component
public class TicketSoftwareInstallService extends TicketBaseService {

    private static final Logger logger = LoggerFactory.getLogger(TicketSoftwareInstallService.class);

//    private CodeService codeService;

    public TicketSoftwareInstallService () {
//        codeService = DubboUtil.getReference(CodeService.class, "idcloud-core-service-system-provider", DubboUtil.DEFAULT_URL, DubboUtil.DEFAULT_VERSION);

        this.ticketType = WebConstants.ticketType.SOFTWARE_INSTALL_TICKET;
        this.ticketTitle = "软件安装工单";
    }

    @Override
    public String getTicketContent(Map<String, Object> params) {
//        logger.info("params=" + params);
//        Long instanceSid = (Long)params.get("instanceSid");
//        String processTarget =  (String)params.get("processTarget");
//        Long mgtObjSid = (Long)params.get("mgtObjSid");
//        String flag = (String)params.get("flag");
//
//        StringBuilder content = new StringBuilder();
//        try {
//            ServiceInstance serviceInstance =instanceService.selectByPrimaryKey(instanceSid);
//            MgtObj mgtObj = mgtObjService.selectByPrimaryKey(mgtObjSid);
//
//            content.append("所属项目:").append(mgtObj.getName()).append("\r\n");
//            content.append("实例名称:").append(serviceInstance.getInstanceName()).append("\r\n");;
//            content.append("待安装软件列表:").append(serviceInstance.getInstanceName()).append("\r\n");
//            List<ResOsSoftware> softwares = JsonUtil.fromJson(processTarget, new TypeReference<List<ResOsSoftware>>() {
//            });
//            for(ResOsSoftware software : softwares) {
//                if(StringUtils.isNotBlank(software.getSoftwareVersion())) {
//                    String softwareName = this.codeService.getSoftwareName(software.getSoftwareVersion());
//                    if(StringUtils.isNotBlank(softwareName)) {
//                        content.append(" ").append(softwareName);
//                    }
//                }
//            }
//            if("1".equals(flag)) {
//                content.append("原因: 软件无对应自动化安装脚本").append("\r\n");
//            } else if("2".equals(flag)) {
//                content.append("原因: 软件自动化安装失败").append("\r\n");
//            }
//        } catch(Exception e) {
//            logger.info("getTicketContent failure", e);
//        }
//        return content.toString();
        return null;
    }

    @Override
    public void manualHandlerTicketUpdateServiceData(Map<String, Object> params) {
//        logger.info("parmas =" + JsonUtil.toJson(params));
//        try {
//            String processTarget = (String)params.get("processTarget");
//            ServiceInstance instance = (ServiceInstance)params.get("instance");
//            String orderId = instance.getOrderId();
//
//            //更新工单处理的目标软件的状态
//            List<ResOsSoftware> softwares = JsonUtil.fromJson(processTarget, new TypeReference<List<ResOsSoftware>>() {});
//            for(ResOsSoftware software : softwares) {
//                Long softwareInstanceSidSid = this.instResService.getInstanceSidByResSid(software.getResSoftwareSid());
//                ServiceInstance softwareInstance = this.instanceService.selectByPrimaryKey(softwareInstanceSidSid);
//                softwareInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//                this.instanceService.updateByPrimaryKeySelective(softwareInstance);
//            }
//
//            //检查云主机下的所有子服务实例是否开通完成,如果全部完成则更新云主机服务实例状态为已开通, 并发送邮件通知
//            instanceService.modifyVmServiceInstanceStatus(instance);
//
//            //检查当前云主机所属订单下的所有云主机服务实例是否全部开通完成，如果全部完成则更新订单的状态为已开通
//            instanceService.modifyOrderStatusOfVmServiceInstance(orderId);
//        } catch(Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void autoHandlerTicket(Map<String, Object> params) {
        // TODO Auto-generated method stub

    }
}
