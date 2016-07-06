package com.h3c.idcloud.core.rest.customer;

import com.h3c.idcloud.core.pojo.dto.customer.Ticket;

import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * 工单管理接口
 *
 * @author gujie
 */
@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface TicketRest {


    /**
     * 查询所有后台用户
     *
     * @return 返回数据
     */
    @WebMethod
    @POST
    @Path("/platform/findAllocateUserSelect")
    Response findAllocateUserSelect();

    /**
     * 添加工单
     *
     * @param params 参数
     * @return 返回数据
     */
    @WebMethod
    @POST
    @Path("/platform/createTicket")
    Response createTicket(String params);

    /**
     * 查询工单列表
     *
     * @param ticket 参数
     * @return 返回数据
     */
    @WebMethod
    @POST
    @Path("/platform/findTickets")
    Response findTickets(Ticket ticket);

    /**
     * 查询该工单详情
     *
     * @param ticketSid 参数
     * @return 返回数据
     */
    @WebMethod
    @GET
    @Path("/platform/findTicketBySid/{ticketSid}")
    Response findTicketBySid(@PathParam("ticketSid") Long ticketSid);

    /**
     * 查询该工单的记录列表
     *
     * @param ticketSid 参数
     * @return 返回数据
     */
    @WebMethod
    @POST
    @Path("/platform/{ticketSid}/ticketRecord")
    Response searchTicketRecord(@PathParam("ticketSid") Long ticketSid);

    /**
     * 更新工单
     *
     * @param parms 参数
     * @return 返回数据
     */
    @WebMethod
    @POST
    @Path("/platform/updateAllocateUser")
    Response updateAllocateUser(String parms);

    /**
     *  手动处理工单
     *
     *  @param parms 参数
     * @return 返回数据
     */
    @WebMethod
    @POST
    @Path("/platform/operationTicket")
    Response operationTicket(String parms);

    /**
     * 获取网络变更情况
     *
     * @param instanceSid 参数
     * @return 返回数据
     */
    @WebMethod
    @POST
    @Path("/platform/getNetChangeInfo/{instanceSid}")
    Response getNetChangeInfo(@PathParam("instanceSid")  Long instanceSid);

    /**
     * 重新执行开通/变更失败的工单
     *
     * @param params 参数
     * @return 返回数据
     */
    @WebMethod
    @POST
    @Path("/platform/executeTicket")
    Response executeTicket(String params);
}