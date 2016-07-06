package com.h3c.idcloud.core.rest.charge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.charge.Bill;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPlan;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPricing;
import com.h3c.idcloud.core.pojo.dto.charge.BillingPricingDetail;

import com.h3c.idcloud.core.pojo.vo.charge.PricingDetailParamVo;
import org.springframework.context.annotation.Scope;


@Path("/billings")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface BillingRest {


    /**
     * 查询资费列表
     */
    @WebMethod
    @POST
    @Path("/getPrice")
    public Response getPrice(ArrayList<Map<String, Object>> models);

    /**
     * 查询资费列表
     */
    @WebMethod
    @POST
    public Response findBilling(Bill bill);

    /**
     * 查询资费计划列表
     */
    @WebMethod
    @POST
    @Path("/platform/findBillingPlan")
    public Response findBillingPlan(BillingPlan billingPlan);


    /**
     * 新增资费计划
     */
    @WebMethod
    @POST
    @Path("/insertBillingPlan")
    public Response insertBillingPlan(BillingPlan billingPlan,@Context HttpServletRequest servletRequest);

    /**
     * 更新资费计划
     */
    @WebMethod
    @POST
    @Path("/updateBillingPlan")
    public Response updateBillingPlan(BillingPlan billingPlan);


    /**
     * 更新账单
     */
    @WebMethod
    @POST
    @Path("/updateBill")
    public Response updateBill(Bill bill);

    /**
     * 删除资费计划
     */
    @WebMethod
    @GET
    @Path("/deleteBillingPlan/{billingPlanSid}")
    public Response deleteBillingPlan(@PathParam("billingPlanSid") Long billingPlanSid);


    /**
     * 获得服务规格集合
     */
    @WebMethod
    @GET
    @Path("/getServiceSpec/{serviceSid}/{billingPlanSid}/{type}")
    public Response getServiceSpecList(@PathParam("serviceSid") Long serviceSid,
                                       @PathParam("billingPlanSid") Long billingPlanSid,
                                       @PathParam("type") String type);


    /**
     * 获得资费计划关联的所有规格设置信息
     *
     * @param serviceSid 服务sid
     * @param planSid    资费计划sid
     */
    @GET
    @Path("/getBillingPlanServiceSpec/{serviceSid}/{planSid}")
    public Response getbillingPlanServiceSpec(@PathParam("serviceSid") Long serviceSid,
                                              @PathParam("planSid") Long planSid);



    /**
     * 获得资费计划关联的所有规格设置信息
     *
     * @param serviceSid 服务sid
     */
    @GET
    @Path("/getBillingPlanServiceSpec/{serviceSid}")
    public Response getbillingPlanServiceSpec(@PathParam("serviceSid") Long serviceSid);

    /**
     * 获取可设置计费的规格列表
     * @param planSid
     * @return
     */
    @GET
    @Path("/getBilledPlanServiceSpec/{planSid}")
    public Response getBilledPlanServiceSpec(@PathParam("planSid") Long planSid);

    /**
     * 获取配置项规格信息
     */
    @GET
    @Path("/getServiceSpecDetailsInfo/{specSids}")
    public Response getServiceSpecDetailsInfo(@PathParam("specSids") String specSids);

    /**
     * 创建资费组合明细
     * @param pricingDetailParamVos
     * @param servletRequest
     * @return
     */
    @POST
    @Path("/create/priceDetails")
    public Response createPriceDetails(List<PricingDetailParamVo> pricingDetailParamVos,@Context HttpServletRequest servletRequest);

    /**
     * 新增资费定价
     */
    @POST
    @Path("/addBillingPrice")
    public Response saveMgtBillingPrice(BillingPricing billingPricing);

    /**
     * 新增资费定价
     */
    @WebMethod
    @POST
    @Path("/create/BillingPrice")
    public Response saveCreateBillingPrice(BillingPricing billingPricing,@Context HttpServletRequest servletRequest);

    /**
     * 更新资费定价
     */
    @WebMethod
    @POST
    @Path("/update/BillingPrice")
    public Response updateCreateBillingPrice(BillingPricing billingPricing);

    /**
     * 保存新增规格项
     */
    @WebMethod
    @POST
    @Path("/saveMgtBillingPrice")
    public Response saveMgtBillingPrice(List<BillingPricingDetail> list);

    /**
     * 查询所有定价详细集合
     */
    @WebMethod
    @POST
    @Path("/billingPricingDetail/findAll")
    public Response findAllBillingPricingDetail(BillingPricingDetail pricingDetail);

    /**
     * 通过资费类型获取资费定价明细信息
     */
    @WebMethod
    @GET
    @Path("/getBillingBytype/{type}/{billingPlanSid}")
    public Response saveMgtBillingPrice(@PathParam("type") String type,
                                        @PathParam("billingPlanSid") Long billingPlanSid);

    /**
     * 资费结算
     */
    @WebMethod
    @GET
    @Path("/billingbalance/{billingType}/{confirmTime}/{tenantId}")
    public Response billingBalance(@PathParam("billingType") String billingType,
                                   @PathParam("confirmTime") String confirmTime,
                                   @PathParam("tenantId") Long tenantId);


    /**
     * 资费结算当前月
     */
    @WebMethod
    @GET
    @Path("/current/billingbalance/{tenantId}")
    public Response billingBalanceCurrentMonth(@PathParam("tenantId") Long tenantId);

    /**
     * 查询账单详情
     */
    @WebMethod
    @GET
    @Path("/findBillDetails/{billSid}/{billName}")
    public Response findBillDetails(@PathParam("billSid") Long billSid,
                                    @PathParam("billName") String billName);

    /**
     * 新增资费定价明细details
     */
    @WebMethod
    @POST
    @Path("/create/BillingPriceDetails")
    public Response saveCreateBillingPriceDetails(BillingPricingDetail billingPricingDetail);

    /**
     * 删除资费定价明细
     */
    @WebMethod
    @GET
    @Path("/delete/BillingPricing/{billingPricingSid}")
    public Response deleteBillingPricing(@PathParam("billingPricingSid") Long billingPricingSid);

    /**
     * 删除资费定价明细details
     */
    @WebMethod
    @GET
    @Path("/delete/BillingPriceDetails/{pricingDetailSid}")
    public Response deleteCreateBillingPriceDetails(
            @PathParam("pricingDetailSid") Long pricingDetailSid);

    /**
     * 根据服务类型和flover查询价格
     */
    @WebMethod
    @GET
    @Path("/selectPricing/{serviceSid}/{configName}/{flover}")
    public Response selectPricingByServiceSidAndFlover(@PathParam("serviceSid") Long serviceSid,
                                                       @PathParam("configName") String configName,
                                                       @PathParam("flover") String flover);

    /**
     * 判断是否有相同的定价明细
     */
    @WebMethod
    @GET
    @Path("/checkSamePricingConfig/BillingPriceDetails/{billingPricingSid}/{billingConfiguration}")
    public Response checkSamePricingConfig(@PathParam("billingPricingSid") Long billingPricingSid,
                                           @PathParam("billingConfiguration") String billingConfiguration);

    /**
     * 租户充值/扣款
     */
    @WebMethod
    @GET
    @Path("/prepaidDebit/{billSid}/{mgtObjSid}/{amount}/{flag}")
    public Response mgtObjPrepaidDebit(@PathParam("billSid") Long billSid,
                                       @PathParam("mgtObjSid") Long mgtObjSid,
                                       @PathParam("amount") String amount,
                                       @PathParam("flag") boolean flag);
}
