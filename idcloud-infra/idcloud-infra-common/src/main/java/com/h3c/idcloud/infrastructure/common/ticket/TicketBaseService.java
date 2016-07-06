package com.h3c.idcloud.infrastructure.common.ticket;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.DubboUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/23.
 */
public abstract class TicketBaseService {

    private static final Logger logger = LoggerFactory.getLogger(TicketBaseService.class);

//    @Autowired
//    protected TicketService ticketService;
//
//    @Autowired
//    protected TicketRecordService ticketRecordService;
//
//
//    protected ServiceInstanceService instanceService;
//    protected SidService sidService;
//    protected MgtObjService mgtObjService;
//    protected ServiceInstanceSpecService instanceSpecService;
//    protected MailNotificationsService mailNotificationsService;
//    protected ServiceInstanceChangeLogService instanceChangeLogService;
//    protected ServiceInstResService instResService;
//
//    TicketBaseService(){
//        instanceService = DubboUtil.getReference(ServiceInstanceService.class,"idcloud-core-service-marketing-provider",DubboUtil.DEFAULT_URL,DubboUtil.DEFAULT_VERSION);
//        instanceSpecService = DubboUtil.getReference(ServiceInstanceSpecService.class,"idcloud-core-service-marketing-provider",DubboUtil.DEFAULT_URL,DubboUtil.DEFAULT_VERSION);
//        instanceChangeLogService = DubboUtil.getReference(ServiceInstanceChangeLogService.class,"idcloud-core-service-marketing-provider",DubboUtil.DEFAULT_URL,DubboUtil.DEFAULT_VERSION);
//        instResService = DubboUtil.getReference(ServiceInstResService.class,"idcloud-core-service-marketing-provider",DubboUtil.DEFAULT_URL,DubboUtil.DEFAULT_VERSION);
//
//        sidService = DubboUtil.getReference(SidService.class,"idcloud-core-service-system-provider",DubboUtil.DEFAULT_URL,DubboUtil.DEFAULT_VERSION);
//        mgtObjService = DubboUtil.getReference(MgtObjService.class,"idcloud-core-service-system-provider",DubboUtil.DEFAULT_URL,DubboUtil.DEFAULT_VERSION);
//        mailNotificationsService = DubboUtil.getReference(MailNotificationsService.class,"idcloud-core-service-system-provider",DubboUtil.DEFAULT_URL,DubboUtil.DEFAULT_VERSION);
//    }








    protected String ticketType;

    protected String ticketTitle;

    public String getTicketType() {
        return ticketType;
    }

    /**
     * 自动创建工单
     *
     * @param params
     */
    public void createTicket(Map<String, Object> params) {
//        try {
//            Long instanceSid = (Long)params.get("instanceSid");
//            String orderId = (String)params.get("orderId");
//            Boolean isResSend = (Boolean)params.get("isResSend");
//            Long mgtObjSid = (Long)params.get("mgtObjSid");
//            String processTarget = (String)params.get("processTarget");
//            if(mgtObjSid == null || instanceSid == null) {
//                logger.error("can't generate ticket, because not enought data. mgtObjSid=" + mgtObjSid + " instanceSid=" + instanceSid);
//                return;
//            }
//            Criteria criteria = new Criteria();
//            criteria.put("statusList",
//                    "'" + WebConstants.TicketStatus.CREATED + "'," +
//                            "'" + WebConstants.TicketStatus.ALLOCATED + "'," +
//                            "'" + WebConstants.TicketStatus.PROCESSING + "'"
//            );
//            criteria.put("processType", ticketType);
//            criteria.put("processObjectId", instanceSid);
//            List<Ticket> ticketList  = ticketService.selectByParams(criteria);
//
//            if (!ticketList.isEmpty()){
//                logger.info("can't generate ticket, because of existing unsolved ticket with same processType and processObjectId");
//                logger.info("update auto handler ticket record start");
//
//                Ticket ticket = ticketList.get(0);
//                if(isResSend != null) {
//                    ticket.setAutoHandlerFlag(isResSend ? 1 : 0);
//                } else {
//                    ticket.setAutoHandlerFlag(0);
//                }
//                this.ticketService.updateByPrimaryKey(ticket);
//
//                criteria = new Criteria();
//                criteria.put("ticketSid", ticket.getTicketSid());
//                criteria.put("operate", WebConstants.TicketOperate.AUTO_HANDLER);
//                criteria.put("operateContent", "");
//                List<TicketRecord> ticketRecords = this.ticketRecordService.selectByParams(criteria);
//                if(!ticketRecords.isEmpty()) {
//                    TicketRecord ticketRecord = ticketRecords.get(0);
//                    ticketRecord.setUpdatedDt(new Date());
//                    ticketRecord.setOperateContent("工单自动处理失败");
//                    this.ticketRecordService.updateByPrimaryKeySelective(ticketRecord);
//                }
//                logger.info("update auto handler ticket record end");
//                return ;
//            }
//
//            ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
//            params.put("instanceName", instance.getInstanceName());
//            Ticket ticket = new Ticket();
//            ticket.setTitle(getTicketTitle(params));
//            ticket.setServiceSid(instance.getServiceSid());
//            ticket.setContent(getTicketContent(params));
//            if(isResSend != null) {
//                ticket.setAutoHandlerFlag(isResSend ? 1 : 0);
//            } else {
//                ticket.setAutoHandlerFlag(0);
//            }
//
//            ticket.setProcessObjectId(instanceSid);
//            if(processTarget != null) {
//                ticket.setProcessTarget(processTarget);
//            }
//            saveTicket(ticket);
//            //发送邮件通知
//            logger.info("create ticket mail send start");
//            logger.info("ticket object = " + JsonUtil.toJson(ticket));
//            mailNotificationsService.ticketNoticeEmail(ticket,WebConstants.RoleSid.OM_MANAGERKLB);
//            logger.info("create ticket mail send end");
//        } catch(Exception e) {
//            logger.error("createTicket failure.", e);
//        }
    }

    /**
     * 通用的工单主题拼装
     *
     * @param params
     * @return
     */
    public String getTicketTitle(Map<String, Object> params) {
        String orderId = (String)params.get("orderId");
        String instanceName = (String)params.get("instanceName");
        return ticketTitle + "-" + orderId + "-" + instanceName;
    }

    /**
     * 通用的工单内容拼装
     *
     * @param params
     * @return
     */
    public String getTicketContent(Map<String, Object> params) {
//        Long instanceSid = (Long)params.get("instanceSid");
//        Long mgtObjSid = (Long)params.get("mgtObjSid");
//        String errorMessage = (String)params.get("errorMessage");
//        MgtObj mgtObj = mgtObjService.selectByPrimaryKey(mgtObjSid);
//        List<ServiceInstanceSpec> instanceSpecs = instanceSpecService.selectByInstanceSpecBySid(instanceSid);
//        StringBuilder content = new StringBuilder();
//        content.append("所属项目:").append(mgtObj.getName()).append("\r\n");
//        content.append("规格:").append("\r\n");
//        for(ServiceInstanceSpec instanceSpec : instanceSpecs) {
//            if(StringUtils.isNotBlank(instanceSpec.getValueText())) {
//                content.append("  ").append(instanceSpec.getDescription()).append(":").append(instanceSpec.getValueText()).append("\r\n");
//            }
//        }
//        if(StringUtil.isNullOrEmpty(errorMessage)){
//            errorMessage = "未知错误";
//        }
//        content.append("错误信息:").append(StringUtil.nullToEmpty(errorMessage));
//        return content.toString();
//    }
//
//    /**
//     * 手动保存工单
//     * @param ticket
//     */
//    public void saveTicket(Ticket ticket) {
//        ticket.setQuestionType("03");
//        ticket.setQuestionLevel(WebConstants.QuestionLevel.BEST_HIGH);
//        ticket.setTicketNo(sidService.getMaxSid(WebConstants.SidCategory.TICKET));
//        ticket.setVersion(1L);
//        ticket.setTicketUser("admin");
//        ticket.setCreatedBy("admin");
//        ticket.setCreatedDt(new Date());
//        ticket.setStatus(WebConstants.TicketStatus.CREATED);
//        ticket.setProcessType(ticketType);
//        ticketService.insertSelective(ticket);
//        //发送工单生成邮件
//		mailNotificationsService.ticketNoticeEmail(ticket,WebConstants.RoleSid.OM_MANAGERKLB);
        return null;
    }

    /**
     * 手动处理工单更新服务数据
     */
    public abstract void manualHandlerTicketUpdateServiceData(Map<String, Object> params);


    /**
     * 自动处理工单
     *
     * @param params
     */
    public abstract void autoHandlerTicket(Map<String, Object> params);
}
