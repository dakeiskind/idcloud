package com.h3c.idcloud.core.rest.customer.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.customer.Issue;
import com.h3c.idcloud.core.pojo.dto.customer.IssueReply;
import com.h3c.idcloud.core.rest.customer.IssueReplyRest;
import com.h3c.idcloud.core.service.ticket.api.IssueReplyService;
import com.h3c.idcloud.core.service.ticket.api.IssueService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
@Component
public class IssueReplyRestImpl implements IssueReplyRest{

    /**实例化Service**/
    @Reference(version = "1.0.0")
    private IssueReplyService issueReplyService;

    /**实例化Service**/
    @Reference(version = "1.0.0")
    private IssueService issueService;

    /**日志log**/
    private static final Logger LOGGER = LoggerFactory.getLogger(IssueReplyRestImpl.class);

    @Override
    public Response findReply(String issueSid) {
        Criteria example = new Criteria();
        example.put("issueSid",issueSid);
        example.setOrderByClause("CREATED_DT DESC");
        List<IssueReply> list = this.issueReplyService.selectByParams(example);
        return Response.ok(JsonUtil.toJson(new RestResult(list))).build();
    }

    @Override
    public Response reply(@Context HttpServletRequest request, IssueReply issueReply) {
        String returnJson;
        Issue issue = new Issue();
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        String account = authUser.getAccount();
        issueReply.setCreatedBy(account);
        issueReply.setCreatedDt(new Date());
        issueReply.setUpdatedBy(account);
        issueReply.setUpdatedDt(new Date());
        if(!"admin".equals(account)){
            issueReply.setReplyType(WebConstants.IssueReplyType.USER);
        }else {
            issue.setIssueStatus("02");
            issueReply.setReplyType(WebConstants.IssueReplyType.ADMIN);
        }
        int result = this.issueReplyService.insertSelective(issueReply);
        if (result == 1) {
            issue.setIssueSid(issueReply.getIssueSid());
            issue.setUpdatedBy(account);
            issue.setUpdatedDt(new Date());
            issueService.updateByPrimaryKeySelective(issue);
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS, null));
        } else {
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_MESSAGE_FAILURE), null));
        }
        return Response.ok(returnJson).build();
    }
}
