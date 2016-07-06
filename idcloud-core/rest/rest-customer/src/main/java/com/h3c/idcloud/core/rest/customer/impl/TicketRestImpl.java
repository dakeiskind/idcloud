package com.h3c.idcloud.core.rest.customer.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.common.ResDataUtils;
import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.core.pojo.dto.customer.TicketRecord;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.product.ServiceChangeLog;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstanceSpec;
import com.h3c.idcloud.core.pojo.dto.res.ResIp;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResNetworkInst;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.core.pojo.vo.customer.TicketVo;
import com.h3c.idcloud.core.rest.customer.TicketRest;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.ServiceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResNetworkService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.ApproveRecordService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.ticket.api.TicketRecordService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.exception.BizException;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author gujie
 */
@Component
public class TicketRestImpl implements TicketRest {

    /**
     * log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketRestImpl.class);

    /**
     * 工单Service
     */
    @Reference(version = "1.0.0")
    private TicketService ticketService;

    /**
     * 工单Service
     */
    @Reference(version = "1.0.0")
    private TicketRecordService ticketRecordService;

    /**
     * 用户Service
     */
    @Reference(version = "1.0.0")
    private UserService userService;

    /**
     * 实例service*
     */
    @Reference(version = "1.0.0")
    private ServiceInstanceService instanceService;

    /**
     * 规格service*
     */
    @Reference(version = "1.0.0")
    private ServiceInstanceSpecService instanceSpecService;

    /**
     * 审核记录service
     */
    @Reference(version = "1.0.0")
    private ApproveRecordService approveRecordService;

    /**
     * 服务变更service*
     */
    @Reference(version = "1.0.0")
    private ServiceChangeLogService changeLogService;

    /**
     * 订单service*
     */
    @Reference(version = "1.0.0")
    private OrderService orderService;

//    @Autowired
//gujie    private VmService vmService;

    /**
     * 邮件service
     */
    @Reference(version = "1.0.0")
    private MailNotificationsService mailNotificationsService;

    /**
     * 实例 serviceInstResService
     */
    @Reference(version = "1.0.0")
    private ServiceInstResService serviceInstResService;

    /**
     * 实例 resVmService
     */
    @Reference(version = "1.0.0")
    private ResVmService resVmService;

    /**
     * 实例 networkService
     */
    @Reference(version = "1.0.0")
    private ResNetworkService networkService;

    @Override
    public Response findAllocateUserSelect() {
        //查询可分配用户信息
//gujie        String roleIds = PropertiesUtil.getProperty("tick.allocate.roleid");
        String roleIds = "202,204";
        if (roleIds == null) {
            //未配置工单分配用户角色ID
            String returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE, "系统参数尚未配置\"工单分配用户角色ID\"!", null));
            return Response.status(Status.OK).entity(returnJson).build();
        }
        List<Long> roleList = new ArrayList<Long>();
        String[] roles = roleIds.split(",");
        for (String roleId : roles) {
            roleList.add(Long.parseLong(roleId));
        }
        Criteria params = new Criteria();
        params.put("roleList", roleList);
        List<User> users = userService.selectByParams(params);
        String json = JsonUtil.toJson(users);
        return Response.ok(json).build();
//        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response createTicket(String params) {
        String returnJson;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            ticketService.createTicket(WebConstants.ticketType.DAILY_MAINTENANCE_TICKET, map);
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS),
                                   null));
        } catch (Exception e) {
            e.printStackTrace();
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE),
                                   null));
        }
//        return Response.ok(returnJson).build();
        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response findTickets(Ticket ticket) {

        Criteria example = new Criteria();
        if (ticket != null) {
            if (!StringUtil.isNullOrEmpty(ticket.getStatus())) {
                example.put("statusList", ticket.getStatus());
            }
            if (!StringUtil.isNullOrEmpty(ticket.getTicketNoLike())) {
                example.put("ticketNoLike", ticket.getTicketNoLike());
            }
            if (!StringUtil.isNullOrEmpty(ticket.getProcessType())) {
                example.put("processType", ticket.getProcessType());
            }
            if (!StringUtil.isNullOrEmpty(ticket.getCreatedStartDt())) {
                example.put("createdStartDt", ticket.getCreatedStartDt());
            }
            if (!StringUtil.isNullOrEmpty(ticket.getCreatedEndDt())) {
                example.put("createdEndDt", ticket.getCreatedEndDt());
            }
            if (!StringUtil.isNullOrEmpty(ticket.getAllocationTicketUser())) {
                example.put("allocationTicketUser", "%" + ticket.getAllocationTicketUser() + "%");
            }
        }
        example.setOrderByClause("A.UPDATED_DT desc,A.CREATED_DT desc");
        List<Ticket> list = this.ticketService.selectByParams(example);
        //设置分配权限和处理权限
        for (Ticket ticketTemp : list) {
            //能访问工单管理的都有分配权限
            ticketTemp.setAllocateAccess(
                    WebConstants.TicketStatus.CREATED.equals(ticketTemp.getStatus()));
            ticketTemp.setProcessAccess(
                    ticketService.getProcessAccess(AuthUtil.getAuthUser().getAccount(),
                                                   ticketTemp.getTicketSid()));
        }

        return Response.ok(JsonUtil.toJson(list)).build();
    }

    @Override
    public Response findTicketBySid(Long ticketSid) {

        Ticket ticket = ticketService.selectByPrimaryKey(ticketSid);

        if (ticket == null) {
            // 获取租户信息失败
            String returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE),
                                   null));
            return Response.ok(returnJson).build();
            //return Response.status(Status.OK).entity(returnJson).build();
        } else {
            ticket.setOperator(AuthUtil.getAuthUser().getAccount());
            ticket.setOperateTime(StringUtil.dateFormat(new Date(), StringUtil.DF_YMD_24));
            // 返回租户信息
            TicketVo ticketVo = new TicketVo();
            BeanUtils.copyProperties(ticket, ticketVo);
            return Response.ok(JsonUtil.toJson(ticketVo)).build();
            //return Response.status(Status.OK).entity(ticket).build();
        }
    }

    @Override
    public Response searchTicketRecord(Long ticketSid) {
        Criteria criteria = new Criteria();
        criteria.put("ticketSid", ticketSid);
        List<TicketRecord> list = ticketRecordService.selectByParams(criteria);
        String json = JsonUtil.toJson(list);
        return Response.ok(json).build();
        //return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response updateAllocateUser(String params) {
        String returnJson;

        int result = 0;
        StringBuffer accouts = new StringBuffer();
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            List<HashMap> users = (List<HashMap>) map.get("allocateUsers");
            Long ticketSid = Long.parseLong(StringUtil.nullToEmpty(map.get("ticketSid")));

            for (int i = 0; i < users.size(); i++) {
                String account = StringUtil.nullToEmpty(users.get(i).get("account"));
                accouts.append("," + account);
            }

            if (accouts.toString().length() > 0) {
                Ticket ticket = ticketService.selectByPrimaryKey(ticketSid);
                ticket.setAllocationTicketUser(accouts.toString().substring(1));
                ticket.setStatus(WebConstants.TicketStatus.ALLOCATED);
                WebUtil.prepareUpdateParams(ticket);

                result = ticketService.updateByPrimaryKeySelective(ticket);
                this.mailNotificationsService.ticketAllocationNoticeEmail(ticket, users);
                if (1 == result) {
                    returnJson = JsonUtil.toJson(
                            new RestResult(RestResult.Status.SUCCESS,
                                           WebUtil.getMessage(WebConstants.MsgCd.INFO_TICKET_SUCCESS), null));
                } else {
                    returnJson = JsonUtil.toJson(
                            new RestResult(RestResult.Status.FAILURE,
                                           WebUtil.getMessage(WebConstants.MsgCd.ERROR_TICKET_ALLOCATE), null));
                }
            } else {
                returnJson = JsonUtil.toJson(
                        new RestResult(RestResult.Status.FAILURE,
                                       WebUtil.getMessage(WebConstants.MsgCd.ERROR_TICKET_ALLOCATE), null));
            }
            return Response.ok(returnJson).build();
//            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            e.printStackTrace();
            String resultJson = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.serverError().entity(resultJson).build();
//            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(resultJson).build();
        }
    }

    @Override
    public Response operationTicket(String params) {
        String returnJson = "";

        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            Long ticketSid = Long.parseLong(StringUtil.nullToEmpty(map.get("ticketSid")));
            Ticket ticket = ticketService.selectByPrimaryKey(ticketSid);
            Criteria criteria = new Criteria();
            criteria.put("ticketSid", ticketSid);
            criteria.put("operateContent", "");
            List<TicketRecord> autoHandlerRecord = ticketRecordService.selectByParams(criteria);

            //如果正在进行自动处理,返回提示正在自动处理正在处理中
            if (WebConstants.TicketStatus.PROCESSING.equals(ticket.getStatus())
                && !autoHandlerRecord.isEmpty()) {
                returnJson = JsonUtil.toJson(
                        new RestResult(RestResult.Status.FAILURE,
                                       "自动处理正在进行中，请勿重复提交处理!", null));
                return Response.status(Status.OK).entity(returnJson).build();
            }

            ticketService.operateTicket(map);
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS),
                                   null));
            return Response.ok(returnJson).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE),
                                   null));
            return Response.ok(returnJson).build();
        }
    }

    @Override
    public Response getNetChangeInfo(Long instanceSid) {
        String returnJson = "";
        try {
            LOGGER.info("instanceSid=" + instanceSid);

            //查询出变更记录中的变更信息
            Criteria criteria = new Criteria();
            criteria.put("instanceSid", instanceSid);
            criteria.put("stauts", WebConstants.ServiceChangeStatus.NOT_CHANGE);
            List<ServiceChangeLog> serviceChangeLogs = changeLogService.selectByParams2(criteria);
            if (CollectionUtils.isEmpty(serviceChangeLogs)) {
                throw new BizException("service_change_log record isn't exist.");
            }
            String content = serviceChangeLogs.get(0).getChangeContent();
            ResVmInst resVmInst = JsonUtil.fromJson(content, ResVmInst.class);
            String vmSid = resVmInst.getResVmInstId();
            ResVm vmInfo = this.resVmService.getVmInfo(vmSid);
            if (vmInfo == null) {
                throw new BizException("resVmService.getVmInfo() return value is null.");
            }
            //网络变更信息
            List<ResNetworkInst> changeNets = resVmInst.getNets();
            //所有网络信息
            List<ResIp> allNets = vmInfo.getResIpList();
            //页面显示网络列表
            List<Map<String, String>> nets = new ArrayList<Map<String, String>>();
            for (ResIp net : allNets) {
                Map<String, String> viewNet = new HashMap<String, String>();
                if (WebConstants.RES_TOPOLOGY_TYPE.PNI.equals(net.getResPoolType())) {
                    viewNet.put("resNetworkType", WebConstants.ResNetworkType.PRIVATE);
                    viewNet.put("resNetworkTypeName", "内网");
                } else if (WebConstants.RES_TOPOLOGY_TYPE.PNE.equals(net.getResPoolType())) {
                    viewNet.put("resNetworkType", WebConstants.ResNetworkType.PUBLIC);
                    viewNet.put("resNetworkTypeName", "外网");
                }

                viewNet.put("ipAddress", net.getIpAddress());
                viewNet.put("ipAddressWithStatus", net.getIpAddress() + "(已使用)");
                viewNet.put("resNetworkId", net.getResNetworkSid());
                ResNetworkInst
                        changeNet =
                        findNetWorkChange(net.getResNetworkSid(), net.getIpAddress(), changeNets);
                if (changeNet != null) {
                    if (WebConstants.NetOperate.ADD.equals(changeNet.getOperate())) {
                        viewNet.put("operate", WebConstants.NetOperate.ADD);
                        viewNet.put("operateText", "添加");
                    } else if (WebConstants.NetOperate.DELLETE.equals(changeNet.getOperate())) {
                        viewNet.put("operate", WebConstants.NetOperate.DELLETE);
                        viewNet.put("operateText", "删除");
                    } else if (WebConstants.NetOperate.UNCHANGE.equals(changeNet.getOperate())) {
                        viewNet.put("operate", WebConstants.NetOperate.UNCHANGE);
                        viewNet.put("operateText", "不变");
                    }
                    ResNetwork
                            netWork =
                            networkService.selectByPrimaryKey(changeNet.getResNetworkId());
                    viewNet.put("resNetworkName",
                                (netWork == null ? "" : netWork.getNetworkName()));
                    if (net.getResNetworkSid() == null) {
                        viewNet.put("resNetworkId", changeNet.getResNetworkId());
                    }
                } else {
                    viewNet.put("operate", WebConstants.NetOperate.UNCHANGE);
                    viewNet.put("operateText", "不变");
                    if (net.getResNetworkSid() != null) {
                        ResNetwork
                                netWork =
                                networkService.selectByPrimaryKey(net.getResNetworkSid());
                        viewNet.put("resNetworkName",
                                    (netWork == null ? "" : netWork.getNetworkName()));
                    }
                }
                nets.add(viewNet);
            }

            returnJson = JsonUtil.toJson(nets);
            return Response.ok(returnJson).build();
//            return Response.status(Status.OK).entity(returnJson).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_QUERY_FAILURE),
                                   null));
            return Response.ok(returnJson).build();
//            return Response.status(Status.OK).entity(returnJson).build();
        }
    }

    /**
     *
     * @param resNetworkSid 资源网络Sid
     * @param ipAddress ip地址
     * @param changeNets 参数
     * @return 返回 ResNetworkInst 实例
     */
    private ResNetworkInst findNetWorkChange(String resNetworkSid, String ipAddress,
                                             List<ResNetworkInst> changeNets) {
        ResNetworkInst net = null;
        for (ResNetworkInst changeNet : changeNets) {
            if (changeNet.getResNetworkId().equals(resNetworkSid)
                || changeNet.getIpAddress().equals(ipAddress)) {
                net = changeNet;
                break;
            }
        }
        return net;
    }

    /**
     *
     * @param ticket 工单对象
     * @param instanceSid 参数
     * @param processType 参数
     * @return 返回值
     */
    public Boolean afterTicketOperated(Ticket ticket, Long instanceSid, String processType) {
        boolean flag = true;
        //判断是否有其他的工单
        Criteria example = new Criteria();
        example.put("processObjectId", instanceSid);
        if (processType.equals(WebConstants.TicketProcessType.DISK_REDUCE)) {
            example.put("processType", WebConstants.TicketProcessType.INSTANCE_ADJUST);
        } else if (processType.equals(WebConstants.TicketProcessType.INSTANCE_ADJUST)) {
            example.put("processType", WebConstants.TicketProcessType.DISK_REDUCE);
        }
        example.put("statusList", WebConstants.TicketStatus.CREATED + ","
                                  + WebConstants.TicketStatus.ALLOCATED + ","
                                  + WebConstants.TicketStatus.PROCESSING);
        List<Ticket> tickets = ticketService.selectByParams(example);
        Long maxVersion = instanceSpecService.selectByInstanceSpecMaxVersion(instanceSid);
        Long validInstaceSpec = instanceSpecService.selectValidInstanceSpecByVersion(instanceSid);
        Criteria condition = new Criteria();
        condition.put("parentInstanceSid", instanceSid);
        List<ServiceInstance> diskInstances = instanceService.selectByParams(condition);
        if (processType.equals(WebConstants.TicketProcessType.DISK_REDUCE)) {
            //缩容的工单关闭，不管是否有其他的工单，缩容的磁盘的规格状态变更为有效
            //得到缩容的磁盘实例
            for (ServiceInstance disk : diskInstances) {
                //获取上一个版本有效的Vm规格
                List<ServiceInstanceSpec>
                        oldInstaceSpec =
                        instanceSpecService.selectByInstanceSpecByVersion(disk.getInstanceSid(),
                                                                          WebConstants.SpecStatus.valid,
                                                                          validInstaceSpec);
                //获取当前版本中未生效Vm规格
                List<ServiceInstanceSpec>
                        newInstaceSpec = instanceSpecService.selectByInstanceSpecByVersion(
                        disk.getInstanceSid(), null, maxVersion);
                if (oldInstaceSpec.size() > 0 && newInstaceSpec.size() > 0) {
                    String
                            oldValue = ResDataUtils.getSpecValueFromSpecs("DISK_SIZE", oldInstaceSpec);
                    String
                            newValue = ResDataUtils.getSpecValueFromSpecs("DISK_SIZE", newInstaceSpec);
                    if (Long.parseLong(newValue) < Long.parseLong(oldValue)) {
                        instanceSpecService.modifyInstanceSpecToValid(disk.getInstanceSid());
                    }
                }
            }
        }
        //不存在未完成工单，查询下该实例的最大版本是不是在变更中
        if (tickets == null || tickets.size() == 0) {
            //将vm实例状态改为开通
            ServiceInstance serviceInstance = instanceService.selectByPrimaryKey(instanceSid);
            serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
            instanceService.updateByPrimaryKeySelective(serviceInstance);
            //更新Vm实例规格状态为有效
            instanceSpecService.modifyInstanceSpecToValid(instanceSid);
            //更新disk的实例规格为有效
            for (ServiceInstance disk : diskInstances) {
                List<ServiceInstanceSpec>
                        newInstaceSpec = instanceSpecService.selectByInstanceSpecByVersion(
                        disk.getInstanceSid(), null, maxVersion);
                //没有新版本规格的不更新
                if (newInstaceSpec.size() > 0) {
                    instanceSpecService.modifyInstanceSpecToValid(disk.getInstanceSid());
                }
            }

            Long bizSid = serviceInstance.getMgtObjSid();
            String resVmSid = "";
            Criteria cre = new Criteria();
            cre.put("instanceSid", instanceSid);
            List<ServiceInstRes> serviceInstReses = serviceInstResService.selectByParams(cre);
            if (serviceInstReses.size() > 0) {
                resVmSid = serviceInstReses.get(0).getResId();
            }
            //更新change_log
            Criteria criteria = new Criteria();
            criteria.put("instanceSid", instanceSid);
            criteria.put("stauts", WebConstants.ServiceChangeStatus.NOT_CHANGE);
            List<ServiceChangeLog> serviceChangeLogs = changeLogService.selectByParams2(criteria);
            Date curDate = new Date();
            for (ServiceChangeLog serviceChangeLog : serviceChangeLogs) {
                serviceChangeLog.setStatus(WebConstants.ServiceChangeStatus.CHANGED);
                serviceChangeLog.setCmEndTime(curDate);
                changeLogService.updateByPrimaryKeySelective(serviceChangeLog);
            }
            //处理IDC变更
            if (PropertiesUtil.getProperty("idc.owner.id").equals(serviceInstance.getOwnerId())) {
                //查询虚拟机实例对应的订单ID
                String orderId = serviceInstance.getOrderId();

                //检查订单下所有虚拟机实例是否开通完成
                if (instanceService.checkOrderSuccess(orderId, WebConstants.ServiceSid.SERVICE_VM)) {
                    //更新订单状态为已完成
                    criteria = new Criteria();
                    criteria.put("orderId", orderId);
                    List<Order> orders = orderService.selectByParams(criteria);
                    Order order = null;
                    if (orders.size() > 0) {
                        order = orders.get(0);
                        order.setStatus(WebConstants.ORDER_STATUS.OPENED);
                        orderService.updateByPrimaryKeySelective(order);
//gujie                        vmService.openVmCallBack(order);
                    }
                }
            }
            //处理闲置回收时，更改状态
            else if (serviceChangeLogs.get(0).getCmType() == 1L) {
                serviceInstance.setIsFree(2L);
                instanceService.updateByPrimaryKeySelective(serviceInstance);
            }
            //发送变更通知邮件
            else {
                mailNotificationsService.launchServiceEmail(instanceSid);
            }
            //等待AuthUtil
//gujie            TkUtils.updateMonitorNode(resVmSid, bizSid);
        }
        //还有其他的未处理的工单，等待处理完成
        else {
            flag = false;
        }
        return flag;
    }

    @Override
    public Response executeTicket(String params) {
        String returnJson;
        try {
            Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
            String ticketType = StringUtil.nullToEmpty(map.get("ticketType"));
            String ticketSid = StringUtil.nullToEmpty(map.get("ticketSid"));
            ResInstResult resInstResult = null;
            //当是
            if (WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET.equals(ticketType)
                || ticketType.equals(WebConstants.ticketType.VM_AUTO_CHANGE_FAILURE_TICKET)) {
                if (WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET.equals(ticketType)) {
                    map.put("processType", WebConstants.ProcessType.VM_OPEN);
                } else if (WebConstants.ticketType.VM_AUTO_CHANGE_FAILURE_TICKET.equals(ticketType)) {
                    map.put("processType", WebConstants.ProcessType.INSTANCE_ADJUST);
                }
                resInstResult = approveRecordService.vmResCheck(map);
                if (ResInstResult.FAILURE == resInstResult.getStatus()) {
                    String message = (String) resInstResult.getMessage();
                    if (StringUtil.isNullOrEmpty(message)) {
                        message = WebUtil.getMessage(WebConstants.MsgCd.ERROR_VM_RES_CHECK);
                    }
                    returnJson = JsonUtil.toJson(
                            new RestResult(RestResult.Status.FAILURE, message, null));
                    return Response.ok(returnJson).build();
//                    return Response.status(Status.OK).entity(returnJson).build();
                }
            }
            Ticket ticket = ticketService.selectByPrimaryKey(Long.parseLong(ticketSid));
            Criteria criteria = new Criteria();
            criteria.put("ticketSid", Long.parseLong(ticketSid));
            criteria.put("operateContent", "");
            List<TicketRecord> autoHandlerRecord = ticketRecordService.selectByParams(criteria);

            //如果正在进行自动处理,返回提示正在自动处理正在处理中
            if (WebConstants.TicketStatus.PROCESSING.equals(ticket.getStatus())
                && !autoHandlerRecord.isEmpty()) {
                returnJson = JsonUtil.toJson(
                        new RestResult(RestResult.Status.FAILURE, "自动处理正在进行中，请勿重复提交处理!",
                                       null));
                return Response.ok(returnJson).build();
//                return Response.status(Status.OK).entity(returnJson).build();
            }

            //保存自动处理记录
            TicketRecord ticketRecord = new TicketRecord();
            ticketRecord.setTicketSid(Long.parseLong(ticketSid));
            //等待AuthUtil
            ticketRecord.setOperator(AuthUtil.getAuthUser().getAccount());
            ticketRecord.setOperate(WebConstants.TicketOperate.AUTO_HANDLER);
            ticketRecord.setOperateTime(new Date());
            ticketRecord.setOperateContent("");
            //添加自动处理记录的处理用户当前时间信息
            WebUtil.prepareInsertParams(ticketRecord);
            ticketRecordService.insertSelective(ticketRecord);
            //更新工单状态为处理中
            ticket.setStatus(WebConstants.TicketStatus.PROCESSING);
            WebUtil.prepareUpdateParams(ticket);
            ticketService.updateByPrimaryKeySelective(ticket);

            //自动处理工单
            ticketService.autoHandlerTicket(ticketType, map);

            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS, "工单自动处理提交成功", null));
            return Response.ok(returnJson).build();
//            return Response.status(Status.OK).entity(returnJson).build();
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            //资源变更检查失败
            String resultJson = "";
            if (e.getMessage() != null && e.getMessage().startsWith("reconfigVmCheck")) {
                returnJson = JsonUtil.toJson(
                        new RestResult(RestResult.Status.FAILURE,
                                       WebUtil.getMessage(WebConstants.MsgCd.ERROR_VM_RES_CHECK), null));
            } else {
                resultJson = JsonUtil.toJson(
                        new RestResult(RestResult.Status.FAILURE, "工单自动处理提交失败", null));
            }
            return Response.ok(resultJson).build();
//            return Response.status(Status.OK).entity(resultJson).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            String
                    resultJson =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, "工单自动处理提交失败", null));
            return Response.ok(resultJson).build();
        }
    }

}
