package com.h3c.idcloud.core.rest.marketing;


import com.h3c.idcloud.core.pojo.dto.marketing.Coupon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Title:
 * Company:   HP
 * Copyright: Copyright (c) 2012
 * Description:
 * <Class Description >
 *
 * @author andre created at 14:18 2014/8/7
 */
@Path("/coupons")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public interface CouponRest {
    /**
     * 查询优惠券列表
     * @param request
     * @return return response with List<Coupon>
     */
    @Path("/getCoupons")
    @GET
    Response getCoupons(@Context HttpServletRequest request);

        /**
     * 创建优惠券
     * @param coupon
     * @return return response with couponSid
     */
    @POST
    @Path("/addCoupon")
    Response addCoupon(Coupon coupon);

    /**
     * 激活优惠券
     * @param couponSid
     * @return return response with couponSid
     */
    @Path("/{couponSid}/distribute")
    @POST
    Response distributeCoupon(@PathParam("couponSid") Long couponSid);

    /**
     *删除优惠券
     * @param couponSid
     * @return
     */
    @DELETE
    @Path("/{couponSid}")
    Response removeCoupon(@PathParam("couponSid") Long couponSid);

    /**
     *更新优惠券
     * @param coupon
     * @return Response with true/false
     */
    @PUT
    Response updateCoupon(Coupon coupon);


    /**
     * 获取单个优惠券
     * @param couponSid
     * @return return response with entity of Coupon
     */
    @GET
    @Path("/{couponSid}")
    Response getCoupon(@PathParam("couponSid") Long couponSid);
    
    /**
     * 检查用户充值使用的优惠码是否可用，能用
     * @param couponCode
     * @return return response with entity of Coupon
     */
    @POST
    @Path("/check/{couponCode}")
    Response checkCoupon(@PathParam("couponCode") String couponCode);

    /**
     * 获取激活状态下未分发的优惠券
     * @param request
     * @return
     */
    @GET
    @Path("/getAvailableCoupon")
    Response getAvailableCoupon(@Context HttpServletRequest request);

    @GET
    @Path("/displayPersonalCoupons")
    public Response displayPersonalCoupons(@QueryParam("validStartDt") String validStart,
                                           @QueryParam("validToDt") String validTo,
                                           @QueryParam("distributeStatus") String status,
                                           @Context HttpServletRequest request,
                                           @Context HttpServletResponse response);
    
    
}
