package com.h3c.idcloud.core.rest.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;

import org.springframework.web.bind.annotation.RequestBody;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;


@Path("/orders")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
public interface OrderRest {

    /**
     * 查询所有订单
     */
    @POST
    public Response findAllOrderList(Order order);

    /**
     * 根据条件查询订单
     */
    @POST
    @Path("/orderList")
    public Response getOrderList(String params, @Context HttpServletRequest servletRequest);

    /**
     * 查询用户中心用户订单列表
     */
    @POST
    @Path("/displayPersonalOrderList")
    public Response displayPersonalOrderList(String params,
                                             @Context HttpServletRequest servletRequest)
            throws IOException;

    /**
     * 保存订单信息
     * @param models
     * @param servletRequest
     * @return
     */
    @POST
    @Path("/save")
    public Response saveServiceOrder(@RequestBody ArrayList<Map<String, Object>> models,
                                     @Context HttpServletRequest servletRequest);


    /**
     * 支付订单
     */
    @POST
    @Path("/pay/{orderId}")
    public Response payServiceOrder(@PathParam("orderId") String orderId,
                                    @Context HttpServletRequest servletRequest);


    /**
     * 取消作废订单
     */
    @POST
    @Path("/cancel/{orderId}")
    public Response cancelServiceOrder(@PathParam("orderId") String orderId,@Context HttpServletRequest servletRequest);


    /**
     * 审批订单
     */
    @POST
    @Path("/approveOrder")
    public Response tenantAdminApprove(String params) throws IOException;

    /**
     * 获取单一订单详情
     */
    @GET
    @Path("/orderDetail")
    public Response getOrderDetail(@QueryParam("orderId") String orderId);

    /**
     * 获取单一订单以及详情
     */
    @GET
    @Path("/getOrder")
    public Response getOrder(@QueryParam("orderId") String orderId);

    /**
     * 获取审批单一订单以及详情
     */
    @GET
    @Path("/getApproveOrder")
    public Response getApproveOrder(@QueryParam("orderId") String orderId,
                                    @QueryParam("processInstanceId") String processInstanceId);

    /**
     * 取消订单
     */
    @GET
    @Path("/cancelOrder")
    public Response cancelOrder(@QueryParam("orderSid") String orderSid);

    /**
     * 判断该租户是否已经申请了Exchange服务
     *
     * @param serviceSid 服务Sid
     */
    @GET
    @Path("/checkExSpOrder")
    public Response checkTenantExAndSpOrder(@QueryParam("serviceSid") long serviceSid);

    /**
     * 判断是否可重复订购
     *
     * @param serviceSid 服务Sid
     */
    @GET
    @Path("/checkRepeatOrder")
    public Response checkRepeatOrder(@QueryParam("serviceSid") long serviceSid);

    /**
     * 后台管理员审批时，修改订单详情资源池信息
     *
     * @param orderDetail 订单详情对象
     */
    @POST
    @Path("/modifyOrderDetail")
    public Response modifyOrderDetail(@RequestBody OrderDetail orderDetail);

    /**
     * 查询订单详细规格信息
     *
     * @param instanceSid 服务实例sid
     */
    @GET
    @Path("/getInstSpec/{instanceSid}")
    public Response getServiceInstanceSpecs(@PathParam("instanceSid") Long instanceSid);

    /**
     * 查询订单详细规格信息(多个数据盘逗号分隔)
     *
     * @param instanceSid 服务实例sid
     */
    @GET
    @Path("/getInstSpecForDataDisk/{instanceSid}")
    public Response getServiceInstanceSpecsForDataDisk(@PathParam("instanceSid") Long instanceSid);

    /**
     * 检查配额情况
     *
     * @param orderId 订单ID
     */
    @GET
    @Path("/checkQuota/{orderId}")
    public Response checkAllQuota(@PathParam("orderId") String orderId);

    /**
     * 检查配额情况
     *
     * @param mgtObjSid 订单ID
     */
    @POST
    @Path("/checkBizQuota/{mgtObjSid}")
    public Response checkBizQuota(@PathParam("mgtObjSid") Long mgtObjSid,
                                  @RequestBody List<Map<String, Object>> curOrderQuotaList);


    /**
     * 查询系统镜像或者用户镜像
     *
     * @param params 系统镜像 2 用户镜像
     */
    @POST
    @Path("/images")
    public Response findImages(String params);


    /**
     * 查询系统镜像或者用户镜像
     *
     * @param params 1 系统镜像 2 用户镜像
     */
    @POST
    @Path("/osVersion")
    public Response findImageOSVer(String params);

    /**
     * 统计订单当月，历史记录
     */
    @GET
    @Path("/getMyOrderServiceInfo")
    public Response getMyOrderServiceInfo();

    @POST
    @Path("/fileUpload")
    public Response addTemp(List<Attachment> attachments, @Context HttpServletRequest request);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveReSubmitOrder")
    public Response saveReSubmitOrder(@RequestBody ArrayList<Map<String, Object>> models,
                                      @Context HttpServletRequest servletRequest,
                                      @Context HttpServletResponse servletResponse);

    /**
     * 删除以拒绝订单
     */
    @GET
    @Path("/deleteOrder/{orderId}")
    public Response deleteOrder(@PathParam("orderId") String orderId);
}
