package com.h3c.idcloud.core.rest.customer.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.customer.Issue;
import com.h3c.idcloud.core.rest.customer.IssueRest;
import com.h3c.idcloud.core.service.ticket.api.IssueService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.*;
import com.h3c.idcloud.infrastructure.common.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 客户工单管理接口实现
 * @author gujie
 */
@Component
public class IssueRestImpl implements IssueRest {

    /**实例化Service**/
    @Reference(version = "1.0.0")
    private IssueService issueService;

    /**日志log**/
    private static final Logger LOGGER = LoggerFactory.getLogger(IssueRestImpl.class);

    @Override
    public Response findAll() {

        Criteria example = new Criteria();

        List<Issue> list = this.issueService.selectByParams(example);
        int total = this.issueService.countByParams(example);
        return Response.ok(new RestResult(new BaseGridReturn(total, list))).build();
    }

    @Override
    public Response find(@Context HttpServletRequest request, Issue issue) {

        Criteria example = new Criteria();

        if(!StringUtil.isNullOrEmpty(issue.getIssueTitle())){
            example.put("issueTitle",issue.getIssueTitle());
        }

        if(!StringUtil.isNullOrEmpty(issue.getCreatedBy())){
            example.put("createdBy",issue.getCreatedBy());
        }
        if(!StringUtil.isNullOrEmpty(issue.getIssuePriority())){
            example.put("issuePriority",issue.getIssuePriority());
        }
        if(!StringUtil.isNullOrEmpty(issue.getIssueStatus())){
            example.put("issueStatus",issue.getIssueStatus());
        }
        if(!StringUtil.isNullOrEmpty(issue.getIssueType())){
            example.put("issueType",issue.getIssueType());
        }

        if(!StringUtil.isNullOrEmpty(issue.getCreatedDtFromDate())){
            example.put("createdDtFromDate",issue.getCreatedDtFromDate());
        }
        if(!StringUtil.isNullOrEmpty(issue.getCreatedDtToDate())){
            example.put("createdDtToDate",issue.getCreatedDtToDate());
        }

        example.setOrderByClause("CREATED_DT DESC");
        List<Issue> list = this.issueService.selectByParams(example);

        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        String type = authUser.getUserType();
        List<Issue> listNew = new ArrayList<Issue>();
        //如果是前端用户，则进行过滤；后端用户，显示全部
        if (type.equalsIgnoreCase(WebConstants.USER_TYPE.FOREGROUND)){
            //将全部的问题进行过滤，只显示该用户及管理员发布的问题
            Iterator<Issue> ite = list.iterator();
            Issue ise = null;
            while (ite.hasNext()){
                ise = ite.next();
                if (ise.getCreatedBy().equalsIgnoreCase(authUser.getAccount()) || ise.getIssueType().indexOf("ADMIN") > 0){
                    listNew.add(ise);
                }
            }
            return Response.ok(JsonUtil.toJson(new RestResult(listNew))).build();
        }
        return Response.ok(JsonUtil.toJson(new RestResult(list))).build();
    }

    @Override
    public Response create(@Context HttpServletRequest request, Issue issue) {
        String returnJson;
        issue.setIssueStatus(WebConstants.IssueStatus.NEW);
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        issue.setCreatedBy(authUser.getAccount());
        issue.setCreatedDt(new Date());
        issue.setUpdatedBy(authUser.getAccount());
        issue.setUpdatedDt(new Date());
        issue.setIssuePriority("05");
        int result = this.issueService.insertSelective(issue);
        if (1 == result) {
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_INSERT_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_INSERT_FAILURE)));
        }
        return Response.ok(returnJson).build();
    }

    @Override
    public Response update(@Context HttpServletRequest request,Issue issue) {
        String returnJson;
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        issue.setUpdatedDt(new Date());
        issue.setUpdatedBy(authUser.getAccount());
        int result = this.issueService.updateByPrimaryKeySelective(issue);
        if (1 == result) {
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.SUCCESS,
                                   WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS)));
        } else {
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE)));
        }
        return Response.ok(returnJson).build();
    }
}
