package com.h3c.idcloud.core.rest.marketing.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.marketing.Agreement;
import com.h3c.idcloud.core.rest.marketing.AgreementRest;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.marketing.api.AgreementService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * @author ranchao
 */
@Component
public class AgreementRestImpl implements AgreementRest {

    /**
     * 大客户协议Service
     */
    @Reference(version = "1.0.0")
    private AgreementService agreementService;


    /**
     * 用户Service
     */
    @Reference(version = "1.0.0")
    private UserService userService;

    /**
     * 账户Service
     */
    @Reference(version = "1.0.0")
    private BillingAccountService accountService;

    /**
     * 文件处理Service
     */
//	@Reference(version = "1.0.0")
//	FileResourceService fileResourceService;


    /**
     * 查询所有大客户协议
     */
    @Override
    public Response find(@Context HttpServletRequest request) {
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "DATE_SIGNED DESC");
        // 查询数据
        List<Agreement> list = this.agreementService.selectByParams(param);
        int total = this.agreementService.countByParams(param);
        String json = JsonUtil.toJson(new BaseGridReturn(total, list));
        return Response.ok(json).build();
    }

    /**
     * 查询所有大客户协议
     */
    @Override
    public Response findAllAgreement(Agreement agreement) {
        Criteria criteria = new Criteria();
        List<Agreement> list = this.agreementService.selectByParams(criteria);
        String json = JsonUtil.toJson(list);
        return Response.ok(json).build();
    }

    /**
     * 新增大客户协议
     */
    @Override
    public Response insertAgreement(Agreement agreement) {
        agreement.setUserSid(Long.parseLong("100"));
        WebUtil.prepareInsertParams(agreement);
        this.agreementService.insertSelective(agreement);
        return Response
                .status(Response.Status.OK)
                .entity(JsonUtil.toJson(new RestResult(
                        RestResult.Status.SUCCESS,
                        WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS),
                        agreement.getAccountSid()))).build();
    }

    /**
     * 更新大客户协议
     */
    @Override
    public Response updateAgreement(Agreement agreement) {
        WebUtil.prepareUpdateParams(agreement);
        this.agreementService.updateByPrimaryKeySelective(agreement);
        return Response.status(Response.Status.OK).entity(JsonUtil.toJson(
                new RestResult(RestResult.Status.SUCCESS,
                               WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null)))
                .build();
    }

    @Override
    public Response deleteAgreement(@PathParam("agreementSid")  Long agreementSid) {
        this.agreementService.deleteByPrimaryKey(agreementSid);
        return Response.status(Response.Status.OK).entity(JsonUtil.toJson(
                new RestResult(RestResult.Status.SUCCESS,
                               WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS), null)))
                .build();
    }


}
