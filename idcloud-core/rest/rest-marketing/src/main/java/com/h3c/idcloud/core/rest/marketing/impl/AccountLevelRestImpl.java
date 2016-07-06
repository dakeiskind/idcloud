package com.h3c.idcloud.core.rest.marketing.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.marketing.AccountLevel;
import com.h3c.idcloud.core.rest.marketing.AccountLevelRest;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.marketing.api.AccountLevelService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.InterfaceResult;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Date;
import java.util.List;


/**
 * @author gujie
 */
@Component
public class AccountLevelRestImpl implements AccountLevelRest {

    /** 日志Log */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AccountLevelRestImpl.class);

    /**
     * 用户等级Service
     */
    @Reference(version = "1.0.0")
    private  AccountLevelService accountLevelService;

    /** billingAccountService Service */
    @Reference(version = "1.0.0")
    private BillingAccountService billingAccountService;
//  /** 用户Service */
//	@Reference(version = "1.0.0")
//	UserService userService;

    @Override
    public Response findAllLevel(AccountLevel level) {

        Criteria example = new Criteria();
        if (level != null) {
            if (!StringUtil.isNullOrEmpty(level.getLevelName())) {
                example.put("levelNameLike", level.getLevelName());
            }
        }
        List<AccountLevel> list = this.accountLevelService.selectByParams(example);
        return Response.ok(JsonUtil.toJson(new RestResult(list))).build();

    }

    @Override
    public Response findLevel(@Context HttpServletRequest request) {
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "CREATED_DT DESC");
        // 查询数据
        List<AccountLevel> list = this.accountLevelService.selectByParams(param);
        int total = this.accountLevelService.countByParams(param);
        String json = JsonUtil.toJson(new BaseGridReturn(total, list));
        return Response.ok(json).build();
    }

    @Override
    public Response insertLevel(AccountLevel level) {
        String returnJson;
        Criteria criteria = new Criteria();
        criteria.put("levelName", level.getLevelName());
        List<AccountLevel> levelList = this.accountLevelService.selectByParams(criteria);
        if (levelList.size() > 0) {
            return Response.ok(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage("error.admin.accountLevelMgt.exists"), null))).build();
        } else {
            level.setCreatedBy("admin");
            level.setCreatedDt(new Date());
            boolean result = this.accountLevelService.insertLevel(level);
            if (result) {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                        .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
            } else {
                returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                        .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
            }
            return Response.ok(returnJson).build();
        }
    }

    @Override
    public Response updateLevel(AccountLevel level) {
        String returnJson = "";
        WebUtil.prepareUpdateParams(level);
        int result = this.accountLevelService.updateByPrimaryKeySelective(level);
        if (result == 1) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }
        return Response.ok(returnJson).build();
    }

    @Override
    public Response deleteLevel(AccountLevel level) {
        String json = "";
        //查看账户等级是否被使用
        Criteria criteria = new Criteria();
        criteria.put("accountLevelSid", level.getLevelSid());
        List<BillingAccount> billingAccounts = billingAccountService.selectByParams(criteria);

        if (!StringUtil.isNullOrEmpty(billingAccounts) && billingAccounts.size() > 0) {
            return Response.ok(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                    WebUtil.getMessage("error.admin.accountLevelMgt.used"), null))).build();
        }

        boolean result = this.accountLevelService.deleteLevel(level.getLevelSid());
        if (result) {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
            return Response.ok(json).build();
        } else {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(json).build();
        }
    }

    @Override
    public Response findLevenDiscount(@Context HttpServletRequest request) {
//		if(!StringUtil.isNullOrEmpty(request.getParameter("isRequest"))){
//			BigDecimal decount = this.billingOperationService.getUserAccountLevelDescount(
//                    true,
//                    Long.parseLong(request.getParameter("userSid")),
//                    Long.parseLong(request.getParameter("requestSid")));
//			return Response.status(Status.OK).entity(decount).build();
//		}else{
//			User curruser = null;
//			if(!StringUtil.isNullOrEmpty(request.getParameter("userSid"))){
//				curruser = this.userService.selectByPrimaryKey(
//                        Long.parseLong(request.getParameter("userSid").toString()));
//			}else{
//				curruser = UserManager.getUserSession().getUser();
//			}
//			if(StringUtil.isNullOrEmpty(curruser))
//				return Response.status(Status.OK).entity(JsonUtil.toJson(
//                        new RestResult(RestResult.FAILURE,
//                                WebUtil.getMessage("error.admin.accountLevelMgt.getDiscount.failure"),
//                                        null))).build();
//			Account account = accountService.selectByPrimaryKey(curruser.getAccountSid());
//			if(StringUtil.isNullOrEmpty(account.getAccountLevelSid()))
//				return Response.status(Status.OK).entity(100).build();
//			AccountLevel leven = this.accountLevelService.selectByPrimaryKey(account.getAccountLevelSid());
//			if(StringUtil.isNullOrEmpty(leven))
//				return Response.status(Status.OK).entity(100).build();
//			BigDecimal decount = leven.getDiscount().divide(new BigDecimal(100)).setScale(
//                    2,BigDecimal.ROUND_HALF_UP);
//			return Response.status(Status.OK).entity(decount).build();
//		}
        return null;
    }

}
