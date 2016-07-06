package com.h3c.idcloud.core.service.ticket.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.customer.dao.TicketMapper;
import com.h3c.idcloud.core.persist.customer.dao.TicketRecordMapper;
import com.h3c.idcloud.core.persist.product.dao.ServiceInstanceMapper;
import com.h3c.idcloud.core.persist.user.dao.UserMapper;
import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.core.pojo.dto.customer.TicketRecord;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.ticket.TicketBaseService;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gujie
 */
@Service(version = "1.0.0")
@Component
public class TicketServiceImpl implements TicketService {

    /**
     * spring 注入 ticketMapper
     */
    @Autowired
    private TicketMapper ticketMapper;

    /**
     * spring 注入 ticketRecordMapper
     */
    @Autowired
    private TicketRecordMapper ticketRecordMapper;

    /**
     * 实例service instanceService
     */
    @Reference(version = "1.0.0")
    private ServiceInstanceService instanceService;

    /**
     * 实例service serviceInstResService
     */
    @Reference(version = "1.0.0")
    private ServiceInstResService serviceInstResService;

    @Autowired
    private SidService sidService;

    @Autowired
    private MailNotificationsService mailNotificationsService;

    @Autowired
    private MgtObjService mgtObjService;

    @Autowired
    private ServiceInstanceMapper serviceInstanceMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Override
    public int countByParams(Criteria example) {
        int count = this.ticketMapper.countByParams(example);
        LOGGER.debug("count: {}", count);
        return count;
    }

    @Override
    public Ticket selectByPrimaryKey(Long ticketSid) {
        return this.ticketMapper.selectByPrimaryKey(ticketSid);
    }

    @Override
    public List<Ticket> selectByParams(Criteria example) {
        return this.ticketMapper.selectByParams(example);
    }

    @Override
    public int deleteByPrimaryKey(Long ticketSid) {
        return this.ticketMapper.deleteByPrimaryKey(ticketSid);
    }

    @Override
    public int updateByPrimaryKeySelective(Ticket record) {
        return this.ticketMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Ticket record) {
        return this.ticketMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByParams(Criteria example) {
        return this.ticketMapper.deleteByParams(example);
    }

    @Override
    public int updateByParamsSelective(Ticket record, Criteria example) {
        return this.ticketMapper.updateByParamsSelective(record, example.getCondition());
    }

    @Override
    public int updateByParams(Ticket record, Criteria example) {
        return this.ticketMapper.updateByParams(record, example.getCondition());
    }

    @Override
    public int insert(Ticket record) {
        return this.ticketMapper.insert(record);
    }

    @Override
    public int insertSelective(Ticket record) {
        return this.ticketMapper.insertSelective(record);
    }

    @Override
    public boolean createServiceTicket(Ticket ticket) {

        SidService sidService = SpringContextHolder.getBean("sidServiceImpl");
        int result = 0;
        String ticketNo = "";

        // 定单编号
        ticketNo = sidService.getMaxSid(WebConstants.SidCategory.TICKET);
        ticket.setTicketNo(StringUtil.nullToEmpty(ticketNo));
        // 状态 0:已提交,1:待审批;2:已审批;3:关闭;4：完成
        ticket.setStatus(WebConstants.TicketStatus.CREATED);

        WebUtil.prepareInsertParams(ticket);
        result = this.ticketMapper.insertSelective(ticket);
        return result == 1;
    }

    @Override
    public boolean getAllocateAccess(String account) {

        // 添加用户信息
        UserService userService = SpringContextHolder.getBean("userServiceImpl");
        UserRoleService userRoleService = SpringContextHolder.getBean("userRoleServiceImpl");
        boolean result = false;
        Criteria criteria = new Criteria();
        criteria.put("account", account);
        List<User> users = userService.selectByParams(criteria);

        for (User user : users) {
            criteria = new Criteria();
            criteria.put("userSid", user.getUserSid());
            List<UserRole> userRoles = userRoleService.selectByParams(criteria);

            for (UserRole userRole : userRoles) {
                if (userRole.getRoleSid().equals(100L)
                    || userRole.getRoleSid().equals(101L)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public boolean getProcessAccess(String account, Long ticketSid) {

        boolean result = false;
        Ticket ticket = this.ticketMapper.selectByPrimaryKey(ticketSid);
        if (StringUtil.nullToEmpty(ticket.getAllocationTicketUser()).contains(account)) {
            result = true;
        }
        return result;

    }

    @Override
    public void modifyTicketStatus(Long instanceSid, String ticketType) {
        Criteria criteria = new Criteria();
        criteria.put("statusList", "'" + WebConstants.TicketStatus.CREATED
                                   + "'," + "'" + WebConstants.TicketStatus.ALLOCATED
                                   + "'," + "'" + WebConstants.TicketStatus.PROCESSING
                                   + "'," + "'" + WebConstants.TicketStatus.RESOLVE + "'"
        );
        criteria.put("processType", ticketType);
        criteria.put("processObjectId", instanceSid);
        List<Ticket> ticketList = this.ticketMapper.selectByParams(criteria);

        if (!ticketList.isEmpty()) {
            Ticket ticket = ticketList.get(0);
            ticket.setUpdatedDt(new Date());
            ticket.setStatus(WebConstants.TicketStatus.RESOLVE);
            this.ticketMapper.updateByPrimaryKeySelective(ticket);

            criteria = new Criteria();
            criteria.put("ticketSid", ticket.getTicketSid());
            criteria.put("operate", WebConstants.TicketOperate.AUTO_HANDLER);
            criteria.put("operateContent", "");
            List<TicketRecord> ticketRecords = this.ticketRecordMapper.selectByParams(criteria);
            if (!ticketRecords.isEmpty()) {
                TicketRecord ticketRecord = ticketRecords.get(0);
                ticketRecord.setUpdatedDt(new Date());
                if (WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET.equals(ticketType)) {
                    ticketRecord.setOperateContent("云主机自动开通失败工单自动处理成功");
                } else if (WebConstants.ticketType.VM_AUTO_CHANGE_FAILURE_TICKET
                        .equals(ticketType)) {
                    ticketRecord.setOperateContent("云主机自动变更失败工单自动处理成功");
                } else if (WebConstants.ticketType.VM_AUTO_UNSUBSCRIBE_FAILURE_TICKET
                        .equals(ticketType)) {
                    ticketRecord.setOperateContent("云主机自动退订失败工单自动处理成功");
                }
                this.ticketRecordMapper.updateByPrimaryKeySelective(ticketRecord);
            }
        }
    }

    @Override
    public void operateTicket(Map map) {
        Long ticketSid = Long.parseLong(StringUtil.nullToEmpty(map.get("ticketSid")));
        String action = StringUtil.nullToEmpty(map.get("action"));
        String operateContent = StringUtil.nullToEmpty(map.get("operateContent"));
        String operator = StringUtil.nullToEmpty(map.get("operator"));
        String operateTime = StringUtil.nullToEmpty(map.get("operateTime"));
        String status = StringUtil.nullToEmpty(map.get("operateStatus"));
        TicketRecord ticketRecord = new TicketRecord();
        //手动处理
        ticketRecord.setTicketSid(Long.parseLong(StringUtil.nullToEmpty(ticketSid)));
        ticketRecord.setOperator(operator);
        ticketRecord.setOperate(action);
        ticketRecord.setOperateTime(StringUtil.strToDate(operateTime, StringUtil.DF_YMD_24));
        ticketRecord.setOperateContent(StringUtil.nullToEmpty(operateContent));
        //新增时添加用户以及当前时间信息
        WebUtil.prepareInsertParams(ticketRecord);
        this.ticketRecordMapper.insertSelective(ticketRecord);

        //更新工单状态
        Ticket
                ticket = ticketMapper.selectByPrimaryKey(
                Long.parseLong(StringUtil.nullToEmpty(ticketSid)));
        ticket.setStatus(status);
        WebUtil.prepareUpdateParams(ticket);
        ticketMapper.updateByPrimaryKeySelective(ticket);
        //如果手动处理工单为已解决
        if (WebConstants.TicketStatus.RESOLVE.equals(status)) {
            Long instanceSid = ticket.getProcessObjectId();

            Criteria criteria = new Criteria();
            criteria.put("statusList", "'" + WebConstants.TicketStatus.CREATED
                                       + "'," + "'" + WebConstants.TicketStatus.ALLOCATED
                                       + "'," + "'" + WebConstants.TicketStatus.PROCESSING
                                       + "'," + "'" + WebConstants.TicketStatus.RESOLVE + "'"
            );
            criteria.put("notTicketSid", ticketSid);
            criteria.put("processType", ticket.getProcessType());
            criteria.put("processObjectId", ticket.getProcessObjectId());
            List<Ticket> ticketList = ticketMapper.selectByParams(criteria);
            if (!ticketList.isEmpty()) {
                return;
            }
            LOGGER.info("All of service instance's other tickets completed.");

            //查询出该实例
            ServiceInstance instance = instanceService.selectByPrimaryKey(instanceSid);
            criteria = new Criteria();
            criteria.put("instanceSid", instanceSid);
            List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(criteria);
            String resVmSid = null;
            if (serviceInstReses.size() > 0) {
                resVmSid = serviceInstReses.get(0).getResId();
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("instanceSid", instance.getInstanceSid());
            params.put("instance", instance);
            params.put("resVmSid", resVmSid);
            params.put("processTarget", ticket.getProcessTarget());
            manualHandlerTicketUpdateServiceData(ticket.getProcessType(), params);
        }
    }

    @Override
    public void deleteInstanceTickets(Long instanceSid) {
        Criteria criteria = new Criteria();
        criteria.put("processObjectId", instanceSid);
        List<Ticket> ticketList = this.ticketMapper.selectByParams(criteria);
        for (Ticket ticket : ticketList) {
            if (!WebConstants.ticketType.VM_AUTO_UNSUBSCRIBE_FAILURE_TICKET
                    .equals(ticket.getProcessType())) {
                Long ticketSid = ticket.getTicketSid();
                criteria = new Criteria();
                criteria.put("ticketSid", ticket.getTicketSid());
                this.ticketRecordMapper.deleteByParams(criteria);
                this.ticketMapper.deleteByPrimaryKey(ticketSid);
            }
        }
    }

    /**
     *
     * @param ticketType 工单类型
     * @return 返回ticketBaseService实例
     */
    public TicketBaseService getTicketService(String ticketType) {
        Map<String, TicketBaseService>
                beans = SpringContextHolder.getApplicationContext().getBeansOfType(
                TicketBaseService.class);
        TicketBaseService ticketBaseService = null;
        for (Map.Entry<String, TicketBaseService> entry : beans.entrySet()) {
            if (entry.getValue().getTicketType().equals(ticketType)) {
                ticketBaseService = entry.getValue();
                break;
            }
        }
        return ticketBaseService;
    }

    @Override
    public void createTicket(String ticketType, Map<String, Object> params) {
        String instanceID = params.get("instanceID").toString();
        ServiceInstance serviceInstance = serviceInstanceMapper.selectByInstanceId(instanceID);
        Criteria criteria = new Criteria();
        criteria.put("account",serviceInstance.getOwnerId());
        List<User> users = userMapper.selectByParams(criteria);
        User user = null;
        Ticket ticket = new Ticket();
        if(!StringUtil.isNullOrEmpty(users) || users.size() == 1){
            user = users.get(0);
        }
        ticket.setQuestionType("03");
        ticket.setQuestionLevel(WebConstants.QuestionLevel.BEST_HIGH);
        ticket.setTicketNo(sidService.getMaxSid(WebConstants.SidCategory.TICKET));
        ticket.setVersion(1L);
        ticket.setTicketUser("admin");
        ticket.setCreatedBy("admin");
        ticket.setCreatedDt(new Date());
        ticket.setStatus(WebConstants.TicketStatus.CREATED);
        ticket.setProcessType(ticketType);
        ticket.setTitle(getTicketTitle(params));
        ticket.setContent(getTicketContent(params,user,serviceInstance));
        this.ticketMapper.insertSelective(ticket);
        //发送工单生成邮件
		mailNotificationsService.ticketNoticeEmail(ticket,WebConstants.RoleSid.OM_MANAGERKLB);
    }

    /**
     * 通用的工单内容拼装
     *
     * @param params
     * @return
     */
    public String getTicketContent(Map<String, Object> params,User user,ServiceInstance serviceInstance) {
        String instanceID = params.get("instanceID").toString();
        String resId = params.get("resId").toString();
        String errorMessage = (String)params.get("errorMsg");
        StringBuilder content = new StringBuilder();

        if(!StringUtil.isNullOrEmpty(user)){
            content.append("用户:").append("\r\n");
            content.append(user.getAccount()).append("\r\n");
        }
        content.append("资源ID:").append("\r\n");
        content.append(resId).append("\r\n");
        content.append("实例ID:").append("\r\n").append(instanceID).append("\r\n");
        content.append("规格:").append("\r\n");
        content.append(serviceInstance.getServiceInstanceSpec()).append("\r\n");
        if(StringUtil.isNullOrEmpty(errorMessage)){
            errorMessage = "未知错误";
        }
        content.append("错误信息:").append(StringUtil.nullToEmpty(errorMessage));
        return content.toString();
    }

    /**
     * 通用的工单主题拼装
     *
     * @param params
     * @return
     */
    public String getTicketTitle(Map<String, Object> params) {;
        String resId = (String)params.get("resId");
        String instanceID = (String)params.get("instanceID");
        return  instanceID + "-" + resId;
    }

    @Override
    public void manualHandlerTicketUpdateServiceData(String ticketType,
                                                     Map<String, Object> params) {
//        TicketBaseService ticketBaseService = this.getTicketService(ticketType);
//        ticketBaseService.manualHandlerTicketUpdateServiceData(params);
    }

    @Override
    public void autoHandlerTicket(String ticketType, Map<String, Object> params) {
//        TicketBaseService ticketBaseService = this.getTicketService(ticketType);
//        ticketBaseService.autoHandlerTicket(params);
    }
}