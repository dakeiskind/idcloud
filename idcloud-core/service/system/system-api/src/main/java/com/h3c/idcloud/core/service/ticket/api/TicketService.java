package com.h3c.idcloud.core.service.ticket.api;

import com.h3c.idcloud.core.pojo.dto.customer.Ticket;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;
import java.util.Map;

/**
 * 工单管理Service
 * @author gujie
 */
public interface TicketService {

    /**
     *
     * @param example 参数
     * @return 查询记录条数
     */
    int countByParams(Criteria example);

    /**
     *
     * @param ticketSid 工单Sid
     * @return 一条工单数据
     */
    Ticket selectByPrimaryKey(Long ticketSid);

    /**
     *
     * @param example 查询条件
     * @return 工单数据List
     */
    List<Ticket> selectByParams(Criteria example);

    /**
     *
     * @param ticketSid 工单Sid
     * @return 返回值
     */
    int deleteByPrimaryKey(Long ticketSid);

    /**
     *
     * @param record 一条工单数据
     * @return 返回值
     */
    int updateByPrimaryKeySelective(Ticket record);

    /**
     *
     * @param record 一条工单数据
     * @return 返回值
     */
    int updateByPrimaryKey(Ticket record);

    /**
     *
     * @param example 参数
     * @return 返回值
     */
    int deleteByParams(Criteria example);

    /**
     *
     * @param record 一条工单数据
     * @param example 其他参数
     * @return 返回值
     */
    int updateByParamsSelective(Ticket record, Criteria example);

    /**
     *
     * @param record 一条工单数据
     * @param example 其他参数
     * @return 返回值
     */
    int updateByParams(Ticket record, Criteria example);

    /**
     *
     * @param record 一条工单数据
     * @return  返回值
     */
    int insert(Ticket record);

    /**
     *
     * @param record 一条工单数据
     * @return 返回值
     */
    int insertSelective(Ticket record);

    /**
     * 创建工单数据（前端系统创建）
     *
     * @param ticket 创建的工单数据
     *
     * @return 是否创建成功
     */
    boolean createServiceTicket(Ticket ticket);

    /**
     * 获取工单分配权限
     *
     * @param account 参数
     *
     * @return 是否成功
     */
    boolean getAllocateAccess(String account);

    /**
     * 获取工单处理权限
     *
     * @param account 参数
     * @param ticketSid 工单Sid
     * @return 是否成功
     */
    boolean getProcessAccess(String account, Long ticketSid);

    /**
     *
     * @param instanceSid 参数
     * @param vmOpen 参数
     */
    void modifyTicketStatus(Long instanceSid, String vmOpen);

    /**
     *
     * @param map 参数
     */
    void operateTicket(Map map);

    /**
     *
     * @param instanceSid 参数
     */
    void deleteInstanceTickets(Long instanceSid);

    /**
     * 更新工单类型自动适配工单服务类
     *
     * @param ticketType 工单类型
     *
     * @return
     */
    //public TicketBaseService getTicketService(String ticketType);

    /**
     * 自动创建各种类型的工单
     *
     * @param ticketType 工单类型
     * @param params     参数
     */
    void createTicket(String ticketType, Map<String, Object> params);

    /**
     * 更新各类工单手动处理的服务数据
     *
     * @param ticketType 工单类型
     * @param params     参数
     */
    void manualHandlerTicketUpdateServiceData(String ticketType, Map<String, Object> params);

    /**
     * 自动处理各种类型工单
     *
     * @param ticketType 工单类型
     * @param params     参数
     */
    void autoHandlerTicket(String ticketType, Map<String, Object> params);
}