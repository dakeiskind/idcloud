package com.h3c.idcloud.core.rest.charge;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;

import org.springframework.context.annotation.Scope;


@Path("/billingAccount")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface BillingAccountRest {

    /**
     * 分页查询用户信息
     */

    @GET
    @Path("/findByPage")
    Response findByPage(@Context HttpServletRequest request);

    /**
     * 新增账户
     */
    @WebMethod
    @POST
    @Path("/addAccount")
    public Response addAccount(BillingAccount account);

    /**
     * 查询所有账户
     */
    @WebMethod
    @POST
    @Path("/findAll")
    public Response findAll();

    /**
     * 删除账户
     */
    @WebMethod
    @POST
    @Path("/deleteAccount")
    public Response deleteAccount(BillingAccount account);

    /**
     * 更新账户
     */
    @WebMethod
    @POST
    @Path("/updateAccount")
    public Response updateAccount(BillingAccount account);

    /**
     * 账户充值
     */
    @WebMethod
    @POST
    @Path("/deposit")
    @Deprecated
    public Response deposit(BillingAccount account);

    /**
     * 账户充值
     *
     * @param userSid 用户sid
     * @param amount  充值金额
     */
    @WebMethod
    @POST
    @Path("/deposits/{userSid}/{amount}")
    public Response depositPay(@PathParam("userSid") Long userSid,
                               @PathParam("amount") Double amount);

    /**
     * 显示账户余额
     *
     * @param userSid 用户sid
     */
    @WebMethod
    @GET
    @Path("/display/account/{userSid}")
    public Response displayAccount(@PathParam("userSid") Long userSid);

    /**
     * 按accountSid查询
     */
    @WebMethod
    @POST
    @Path("/findByAccountSid")
    public Response findByAccountSid(long accountSid);

    /**
     * 根据条件查询
     */
    @WebMethod
    @POST
    @Path("/findByCriteria")
    public Response findByCriteria(BillingAccount account);

    /**
     * 获取账户余额
     */
    @WebMethod
    @POST
    @Path("/balance")
    public Response balance();

    /**
     * 批量修改账户余额及透支额度
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/batchUpdateInfo")
    public Response batchUpdateInfo(String param);

    /**
     * 查询充值记录
     */
    @GET
    @Path("/displayPaymentRecords")
    public Response displayPaymentRecords(@QueryParam("channel") String channel,
                                          @QueryParam("paymentTime") String paymentTime,
                                          @Context HttpServletRequest request,
                                          @Context HttpServletResponse response);

    /**
     * 查询账单记录信息
     */
    @GET
    @Path("/displayBillRecords")
    public Response displayBillRecords(@QueryParam("serviceSid") String serviceSid,
                                       @QueryParam("payStatus") String payStatus,
                                       @QueryParam("startTime") String startTime,
                                       @QueryParam("endTime") String endTime,
                                       @Context HttpServletRequest request);

}
