package com.h3c.idcloud.core.rest.marketing.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.marketing.DepositPrize;
import com.h3c.idcloud.core.rest.marketing.BillingDepositPrizeRest;
import com.h3c.idcloud.core.service.marketing.api.DepositPrizeService;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.apache.log4j.Logger;


import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.List;


/**
 * @author shiw
 */
@Component
public class BillingDepositPrizeRestImpl implements BillingDepositPrizeRest {

    Logger log = Logger.getLogger(this.getClass());

    @Reference(version = "1.0.0")
    DepositPrizeService depositPrizeService;


    @Override
    public Response findAll(@Context HttpServletRequest request) {
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "VALID_START_DT DESC");
        // 查询数据
        List<DepositPrize> list = this.depositPrizeService.selectByParams(param);
        int total = this.depositPrizeService.countByParams(param);
        String json = JsonUtil.toJson(new BaseGridReturn(total, list));
        return Response.ok(json).build();
    }


    @Override
    public Response addDepositPrize(DepositPrize depositPrize) {
        depositPrize.setVersion(Long.parseLong("1"));
        depositPrize.setValidStartDt(
                StringUtil.strToDate(depositPrize.getValidStartDtStr(), StringUtil.DF_YMD_24));
        depositPrize.setValidToDt(
                StringUtil.strToDate(depositPrize.getValidToDtStr(), StringUtil.DF_YMD_24));
        WebUtil.prepareInsertParams(depositPrize);
        this.depositPrizeService.insertSelective(depositPrize);
        return Response
                .status(Response.Status.OK)
                .entity(JsonUtil.toJson(new RestResult(
                        RestResult.Status.SUCCESS,
                        WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS),
                        depositPrize.getDepositPrizeSid()))).build();
    }

    @Override
    public Response removeDepositPrize(@PathParam("depositPrizeSid") Long depositPrizeSid) {
        this.depositPrizeService.deleteByPrimaryKey(depositPrizeSid);
        return Response.status(Response.Status.OK).entity(JsonUtil.toJson(
                new RestResult(RestResult.Status.SUCCESS,
                               WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS), null)))
                .build();
    }


    @Override
    public Response updateDepositPrize(DepositPrize depositPrize) {
        depositPrize.setValidStartDt(
                StringUtil.strToDate(depositPrize.getValidStartDtStr(), StringUtil.DF_YMD_24));
        depositPrize.setValidToDt(
                StringUtil.strToDate(depositPrize.getValidToDtStr(), StringUtil.DF_YMD_24));
        WebUtil.prepareUpdateParams(depositPrize);
        this.depositPrizeService.updateByPrimaryKeySelective(depositPrize);
        return Response.status(Response.Status.OK).entity(JsonUtil.toJson(
                new RestResult(RestResult.Status.SUCCESS,
                               WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null)))
                .build();
    }
}
