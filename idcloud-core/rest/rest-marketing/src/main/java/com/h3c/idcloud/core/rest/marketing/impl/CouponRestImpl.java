package com.h3c.idcloud.core.rest.marketing.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.marketing.Coupon;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.dto.user.UserManager;
import com.h3c.idcloud.core.rest.marketing.CouponRest;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.marketing.api.CouponService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.Date;
import java.util.List;

/**
 * Title: Company:   HP Copyright: Copyright (c) 2012 Description: <Class Description >
 *
 * @author andre created at 10:34 2014/8/13
 */
@Component
public class CouponRestImpl implements CouponRest {

    @Reference(version = "1.0.0")
    CouponService couponService;

    @Reference(version = "1.0.0")
    BillingAccountService accountService;

    @Reference(version = "1.0.0")
    UserService userService;

//    @Reference(version = "1.0.0")
//    TraceService traceService;

    @Override
    public Response getCoupons(@Context HttpServletRequest request) {
        try {
//            //过滤
//		    Criteria criteria = new Criteria();
//
//            //构建查询对象
//		    WebUtil.preparePageParams(request, pager, criteria, "CREATED_DT DESC");
//		    criteria.setOrderByClause("CREATED_DT DESC");
//            //获取用户所在的虚拟工作室(自己创建后，将把自己添加为其中的成员)
//            List<Coupon> coupons = this.couponService.findByParams(criteria);
//		    int count = this.couponService.findByParamsCnt(criteria);
//
//		    Map<String, Object> data = new HashMap<String, Object>();
//		    data.put("TotalRows", count);
//		    data.put("Rows", coupons);
//		    String json = JsonUtil.toJson(data);
//
//            return Response.status(Status.OK).entity(json).build();

            // 参数设置
            Criteria param = new Criteria();
            WebUtil.preparePageParams(request, param, "CREATED_DT DESC");

            // 查询数据
            List<Coupon> coupons = this.couponService.findByParams(param);
            int count = this.couponService.findByParamsCnt(param);

            String json = JsonUtil.toJson(new BaseGridReturn(count, coupons));

            return Response.ok(json).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_RELATE_FAILURE),
                                   null))).build();
        }
    }

    @Override
    public Response addCoupon(Coupon coupon) {
//    	Criteria criteria = new Criteria();
//		criteria.put("accountLeavel", coupon.getUserLevel());
//		criteria.put("userGroup", coupon.getUserGroup());
//		criteria.put("status", WebConstants.UserStatus.AVAILABILITY);  //有效用户
//		List<User> sendUserList = userService.selectByParams(criteria);
//		if(sendUserList == null || sendUserList.size() <=0){
//			return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, "根据条件 accountLeavel "+coupon.getUserLevel() +" userGroup "+coupon.getUserGroup()+" 无法找到发送用户", null))).build();
//		}

        try {
            //创建优惠券
            coupon.setValidStartDt(
                    StringUtil.strToDate(coupon.getValidStartDtStr(), StringUtil.DF_YMD));
            coupon.setValidToDt(StringUtil.strToDate(coupon.getValidToDtStr(), StringUtil.DF_YMD));
            this.couponService.createCoupon(coupon);
            return Response.status(Status.OK).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS),
                                   coupon.getCouponSid()))).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_RELATE_FAILURE),
                                   null))).build();
        }
    }

    @Override
    public Response distributeCoupon(@PathParam("couponSid") Long couponSid) {
        System.err.println(couponSid);
        System.err.println("激活Service");
        //激活礼品卡
        this.couponService.executeGenCoupons(couponSid);
        return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult
                                                                                  (RestResult.Status
                                                                                           .SUCCESS,
                                                                                   WebUtil.getMessage(
                                                                                           WebConstants.MsgCd.INFO_GIFT_CARD_GEN_SUCCESS),
                                                                                   null))).build();
    }

    @Override
    public Response removeCoupon(@PathParam("couponSid") Long couponSid) {
        try {
            //删除虚拟工作室
            int i = this.couponService.deleteCoupon(couponSid);
            if (i == -2) {
                return Response.status(Status.OK).entity(JsonUtil.toJson(
                        new RestResult(RestResult.Status.FAILURE, "已分发优惠券的不可以删除", null))).build();
            }
            return Response.status(Status.OK).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS),
                                   null))).build();
        }
//         catch (ApplicationException ea)
//        {
//            return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(ea.getMessage()), null))).build();
//        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION),
                                   null))).build();
        }
    }

    @Override
    public Response updateCoupon(Coupon coupon) {
//    	Criteria criteria = new Criteria();
//		criteria.put("accountLeavel", coupon.getUserLevel());
//		criteria.put("userGroup", coupon.getUserGroup());
//		criteria.put("status", WebConstants.UserStatus.AVAILABILITY);  //有效用户
//		List<User> sendUserList = userService.selectByParams(criteria);
//		if(sendUserList == null || sendUserList.size() <=0){
//			return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, "根据条件 accountLeavel "+coupon.getUserLevel() +" userGroup "+coupon.getUserGroup()+" 无法找到发送用户", null))).build();
//		}
        try {
            WebUtil.prepareUpdateParams(coupon);
            coupon.setVersion(
                    StringUtil.isNullOrEmpty(coupon.getVersion()) ? 1 : coupon.getVersion() + 1);
            this.couponService.updateByPrimaryKey(coupon);
//            traceService.insertTraceLog(UserManager.getUserSession().getUser(), "营销管理", "修改优惠券:"+coupon.getCouponCode());
            return Response.status(Status.OK).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS),
                                   null))).build();
        }
//        catch (ApplicationException ea)
//        {
//            return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(ea.getMessage()), null))).build();
//        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION),
                                   null))).build();
        }
    }


    @Override
    public Response getCoupon(@PathParam("couponSid") Long couponSid) {
        try {
            Coupon coupon = this.couponService.selectByPrimaryKey(couponSid);
            return Response.status(Status.OK).entity(JsonUtil.toJson(coupon)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION),
                                   null))).build();
        }
    }

    @Override
    public Response checkCoupon(@PathParam("couponCode") String couponCode) {
        String msg = "";
        if (StringUtil.isNullOrEmpty(couponCode)) {
            return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(
                    RestResult.Status.FAILURE, "入参为空", null))).build();
        }
        User user = UserManager.getUserSession().getUser();

        if (user == null) {
            return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(
                    RestResult.Status.FAILURE, "用户未登录", null))).build();
        }
//		Account account = this.accountService.selectByPrimaryKey(user.getAccountSid());
        BillingAccount
                account =
                this.accountService.selectByPrimaryKey(Long.parseLong(user.getAccount()));
        if (account == null) {
            return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(
                    RestResult.Status.FAILURE, "用户账户不明", null))).build();
        }
        Criteria example = new Criteria();
        example.put("couponCode", couponCode);
        List<Coupon> coupon = this.couponService.findByParams(example);
        if (null == coupon || coupon.size() == 0) {
            return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(
                    RestResult.Status.FAILURE, "无效优惠码", null))).build();
        } else {
            Coupon c = coupon.get(0);
            Date now = new Date();
            Date startTime = c.getValidStartDt();
            Date endTime = c.getValidToDt();
            Short status = c.getDistributeStatus();
            String userLevel = c.getUserLevel();
            String userGroup = c.getUserGroup();
            if (now.before(startTime) || now.after(endTime) || status != 1) {
                msg = "无效优惠码";
                return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(
                        RestResult.Status.FAILURE, msg, null))).build();
            }

            try {
                if (StringUtils.isEmpty(userGroup) && StringUtils.isEmpty(userLevel)) {
                    return Response.status(Status.OK).entity(JsonUtil.toJson(c)).build();
                }
//				if(user.getUserGroup().equals(userGroup) && StringUtils.isEmpty(userLevel)){----------wsl
//					return Response.status(Status.OK).entity(JsonUtil.toJson(c)).build();
//				}
                if (StringUtils.isEmpty(userGroup) && account.getAccountLevelSid().toString()
                        .equals(userLevel)) {
                    return Response.status(Status.OK).entity(JsonUtil.toJson(c)).build();
                }
//				if(user.getUserGroup().equals(userGroup) && account.getAccountLevelSid().toString().equals(userLevel)){  --------wsl
//					return Response.status(Status.OK).entity(JsonUtil.toJson(c)).build();
//				}
                msg = "您所在用户组或者级别不够使用该优惠券";
                return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(
                        RestResult.Status.FAILURE, msg, null))).build();
            } catch (Exception e) {
                msg = "您所在用户组或者级别不够使用该优惠券";
                return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(
                        RestResult.Status.FAILURE, msg, null))).build();
            }
        }
//		return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(
//				RestResult.FAILURE,msg,null))).build();
    }

    @Override
    public Response getAvailableCoupon(@Context HttpServletRequest request) {
        List<Coupon> coupons = this.couponService.findAvailableCoupons();
        String json = JsonUtil.toJson(coupons);
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response displayPersonalCoupons(String validStartDt, String validToDt, String distributeStatus,
                                     @Context HttpServletRequest request,
                                     @Context HttpServletResponse response) {
        Criteria example = new Criteria();
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        if (!StringUtil.isNullOrEmpty(validStartDt)) {
            example.put("validStartDtStr", validStartDt + "00:00:00");
        }
        if (!StringUtil.isNullOrEmpty(validToDt)) {
            example.put("validToDt", validToDt + "23:59:59");
        }
        if (!StringUtil.isNullOrEmpty(distributeStatus)) {
            example.put("distributeStatus", distributeStatus);
        }
        example.put("userSid", authUser.getUserSid());
        List<Coupon> coupons = this.couponService.selectByParams(example);
        return Response.status(Status.OK).entity(new RestResult(coupons)).build();
    }
}
