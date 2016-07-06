package com.h3c.idcloud.core.rest.marketing.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCard;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;
import com.h3c.idcloud.core.rest.marketing.GiftCardBatchRest;
import com.h3c.idcloud.core.service.marketing.api.GiftCardBatchService;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: Company:   HP Copyright: Copyright (c) 2012 Description: <Class Description >
 *
 * @author andre created at 10:34 2014/8/13
 */
@Component
public class GiftCardBatchRestImpl implements GiftCardBatchRest {

    @Reference(version = "1.0.0")
    GiftCardBatchService giftCardBatchService;


    @Override
    public Response getGiftCardBatchs(@Context HttpServletRequest request) {
        Criteria criteria = new Criteria();
        WebUtil.preparePageParams(request, criteria, "CREATED_DT DESC");
        List<GiftCardBatch> giftCards = this.giftCardBatchService.selectByParams(criteria);
        int count = this.giftCardBatchService.countByParams(criteria);
        String json = JsonUtil.toJson(new BaseGridReturn(count, giftCards));
        return Response.ok(json).build();
    }


    @Override
    public Response getAvailableBatchs(@Context HttpServletRequest request,
                                       @Context HttpServletResponse response) {

        //查询礼品卡批次信息
        List<GiftCardBatch> giftCardBatchs = this.giftCardBatchService.findAvailableBatchs();

        String json = JsonUtil.toJson(giftCardBatchs);

        return Response.status(Response.Status.OK).entity(json).build();

    }

    @POST
    public Response addGiftCardBatch(GiftCardBatch giftCardBatch) {
        try {
            //创建礼品卡批次
            User user = AuthUtil.getAuthUser();
            com.h3c.idcloud.core.pojo.dto.user.User
                    curUser =
                    new com.h3c.idcloud.core.pojo.dto.user.User();
            BeanUtils.copyProperties(user, curUser);

            giftCardBatch.setValidStart(StringUtil.strToDate(giftCardBatch.getValidStartStr(),
                                                             StringUtil.DF_YMD));
            giftCardBatch.setValidTo(StringUtil.strToDate(giftCardBatch.getValidToStr(),
                                                          StringUtil.DF_YMD));
            this.giftCardBatchService.createGiftCardBatch(giftCardBatch, curUser);
            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS),
                                   giftCardBatch.getBatchSid()))).build();
        }
//        catch (ApplicationException ea)
//        {
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(ea.getMessage()), null))).build();
//        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION),
                                   null))).build();
        }
    }

    /**
     * 整批生成礼品卡
     *
     * @return return response with true/false
     */
    @Path("/{giftCardBatchSid}/genGiftCard")
    @POST
    public Response genGiftCard(@PathParam("giftCardBatchSid") Long giftCardBatchSid) {
        User user = AuthUtil.getAuthUser();
        com.h3c.idcloud.core.pojo.dto.user.User
                curUser =
                new com.h3c.idcloud.core.pojo.dto.user.User();
        BeanUtils.copyProperties(user, curUser);
        //整批生成礼品卡
        this.giftCardBatchService.executeGenGiftCardsForBatch(giftCardBatchSid, curUser);
        return Response.status(Response.Status.OK).entity(JsonUtil.toJson(
                new RestResult(RestResult.Status.SUCCESS,
                               WebUtil.getMessage(WebConstants.MsgCd.INFO_GIFT_CARD_GEN_SUCCESS),
                               null))).build();
    }

    /**
     * 激活整批礼品卡
     *
     * @return true/false
     */
    @PUT
    @Path("/{giftCardBatchSid}/activateGiftCard")
    public Response activateGiftCardBatch(@PathParam("giftCardBatchSid") Long giftCardBatchSid) {
//         try {
//            int activeCnt = 0;
//            //激活整批礼品卡
//            activeCnt = this.giftCardBatchService.executeActivateGiftCardForBatch(giftCardBatchSid, AuthUtil.getAuthUser());
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_GIFT_CARD_ACTIVATE_SUCCESS,new Integer[]{activeCnt}), null))).build();
//        }catch (ApplicationException ea)
//        {
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(ea.getMessage()), null))).build();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//             return Response.status(Response.Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION), null))).build();
//        }
        return null;
    }

    /**
     * 作废礼品卡批次
     *
     * @return Response with success/failure
     */
    @DELETE
    @Path("/{giftCardBatchSid}/invalid")
    public Response invalidGiftCardBatch(@PathParam("giftCardBatchSid") Long giftCardBatchSid) {
//        try {
//            //更新虚拟工作室
//            this.giftCardBatchService.executeInvalidGiftCardBatch(giftCardBatchSid, AuthUtil.getAuthUser());
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_GIFT_CARD_INVALID_SUCCESS), null))).build();
//        }catch (ApplicationException ea)
//        {
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(ea.getMessage()), null))).build();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//             return Response.status(Response.Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION), null))).build();
//        }
        return null;
    }

    /**
     * 更新礼品卡批次信息
     *
     * @return Response with success/failure
     */
    @PUT
    public Response updateGiftCardBatch(GiftCardBatch giftCardBatch) {
//        try {
//            //更新礼品卡批次信息
//            this.giftCardBatchService.updateGiftCardBatch(giftCardBatch,AuthUtil.getAuthUser());
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null))).build();
//        }catch (ApplicationException ea)
//        {
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(ea.getMessage()), null))).build();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//             return Response.status(Response.Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION), null))).build();
//        }
        return null;
    }


    /**
     * 获取单个礼品卡批次
     *
     * @return return response with entity of GiftCardBatch
     */
    @GET
    @Path("/{giftCardBatchSid}")
    public Response getGiftCardBatch(@PathParam("giftCardBatchSid") Long giftCardBatchSid) {
//        try {
//            GiftCardBatch giftCardBatch = this.giftCardBatchService.selectByPrimaryKey(giftCardBatchSid);
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(giftCardBatch)).build();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//             return Response.status(Response.Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION), null))).build();
//        }
        return null;
    }
}
