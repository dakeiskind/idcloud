package com.h3c.idcloud.core.rest.system;

import com.h3c.idcloud.core.pojo.dto.system.Message;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * 消息接口管理
 */
@Path("/message")
@Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
@Consumes({MediaType.APPLICATION_JSON + "; charset=utf-8"})
public interface MessageRest {

    /**
     * 分页查询用户信息
     */
    @GET
    @Path("/findByPage")
    Response findByPage(@Context HttpServletRequest request);

    /**
     * 添加消息
     */
    @POST
    @Path("/addMessage")
    Response addMessage(Message message);

    /**
     * 删除消息
     */
    @POST
    @Path("/deleteMessages")
    Response deleteMessages(String[] msgSids);
//
//	/**
//	 * 删除全部消息
//	 * @param message
//	 * @return
//	 */
//	@WebMethod
//	@POST
//	@Path("/deleteAllMessages")
//	public Response deleteAllMessages(Message message);
//

    /**
     * 批量更新
     */
    @POST
    @Path("/updateMessages")
    Response updateMessages(Message[] messages);
//
//	/**
//	 * 全部标记为已读
//	 * @param message
//	 * @return
//	 */
//	@WebMethod
//	@POST
//	@Path("/updateAllMessages")
//	public Response updateAllMessages(Message message);
//
//	/**
//	 * 添加系统消息
//	 * @param systemMessage
//	 * @return
//	 */
//	@WebMethod
//	@POST
//	@Path("/addSystemMessage")
//	public Response addSystemMessage(SystemMessage systemMessage);
//
//    /**
//     * 群发系统消息
//     * @param systemMessage
//     * @return
//     */
//    @WebMethod
//    @POST
//    @Path("/addSystemMessages")
//    public Response addSystemMessages(SystemMessage systemMessage);
//
//
//    @WebMethod
//    @POST
//    @Path("/addCouponMessages")
//    public Response addSystemMessages(Coupon coupon);
//
    /**
     * 根据条件查询message的数量
     * @param message
     * @return
     */
    @WebMethod
    @POST
    @Path("/getCountByCriteria")
    public Response getCountByCriteria(@Context HttpServletRequest request,Message message);
//
//
//    /**
//     * 查询所有信息
//     * @param message
//     * @return
//     */
//    @WebMethod
//    @POST
//    @Path("/getMsgByCriteria")
//    public Response getMsgByCriteria(Message message);
//
//    /**
//     * 根据条件查询
//     * @param response
//     * @return
//     */
//    @WebMethod
//    @POST
//    @Path("/findMessageList")
//    public Response findMessageList(String param, @Context HttpServletRequest request, @Context HttpServletResponse response);

}
