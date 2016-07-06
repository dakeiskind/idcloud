package com.h3c.idcloud.core.rest.system.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.system.Message;
import com.h3c.idcloud.core.pojo.dto.system.SiteMsg;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.rest.system.SiteMsgRest;
import com.h3c.idcloud.core.service.system.api.MessageService;
import com.h3c.idcloud.core.service.system.api.SiteMsgService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.*;

/**
 * Created by JJ on 2015/12/9.
 */
@Component
public class SiteMsgRestImpl implements SiteMsgRest {

    @Reference(version = "1.0.0")
    SiteMsgService siteMsgService;

    @Reference(version = "1.0.0")
    MessageService messageService;

    @Reference(version = "1.0.0")
    UserService userService;

    public Response find(@Context HttpServletRequest request) {
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "CREATED_DT DESC");
        // 查询数据
        List<SiteMsg> list = this.siteMsgService.selectByParams(param);
        int total = this.siteMsgService.countByParams(param);
        String json = JsonUtil.toJson(new BaseGridReturn(total, list));
        return Response.ok(json).build();
    }

    public Response findAll(Map map) {
        Criteria criteria = new Criteria();
        List<SiteMsg> list = this.siteMsgService.selectByParams(criteria);
        String json = JsonUtil.toJson(list);
        return Response.ok(json).build();
    }

    @Override
    public Response insert(SiteMsg siteMsg) {
        String returnJson = "";
        WebUtil.prepareInsertParams(siteMsg);
        int result = this.siteMsgService.insertSelective(siteMsg);
        if (result == 1) {
            Criteria criter = new Criteria();
            criter.put("status", WebConstants.UserStatus.AVAILABILITY);
            List<User> users = userService.selectByParams(criter);
            List<Message> messages = new ArrayList<Message>();
            for (User u : users) {
                Message message = new Message();
                message.setMsgTitle(siteMsg.getMsgTitle());
                message.setMsgType(siteMsg.getMsgType());
                message.setFromUser("Admin");
                message.setToUser(u.getAccount());
                message.setMsgContent(siteMsg.getMsgContent());
                message.setSendDate(new Date());
                message.setReadFlag("02");  // unread
                messages.add(message);
            }
            result = messageService.insertBatch(messages);
            if(messages.size() == result){
                returnJson = JsonUtil.toJson(
                        new RestResult(RestResult.Status.SUCCESS,
                                WebUtil.getMessage(WebConstants.MsgCd.INFO_MESSAGE_SUCCESS)));
            }else {
                returnJson = JsonUtil.toJson(
                        new RestResult(RestResult.Status.FAILURE,
                                WebUtil.getMessage(WebConstants.MsgCd.ERROR_MESSAGE_FAILURE)));
            }

        } else {
            returnJson = JsonUtil.toJson(
                    new RestResult(RestResult.Status.FAILURE,
                            WebUtil.getMessage(WebConstants.MsgCd.ERROR_MESSAGE_FAILURE)));
        }

        return Response.ok(returnJson).build();
    }

    public Response editRole(SiteMsg siteMsg) {
        WebUtil.prepareUpdateParams(siteMsg);
        int result = this.siteMsgService.updateByPrimaryKeySelective(siteMsg);
        if (result == 1) {
            return Response.ok(JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
                                                              WebUtil.getMessage(
                                                                      WebConstants.MsgCd.INFO_EDIT_SUCCESS),
                                                              null))).build();
        } else {
            return Response.ok(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                                                              WebUtil.getMessage(
                                                                      WebConstants.MsgCd.ERROR_EDIT_FAILURE),
                                                              null))).build();
        }
    }

    @Override
    public Response delete(SiteMsg siteMsg) {
        int result = 1;
        try {
            this.siteMsgService.deleteByPrimaryKey(siteMsg.getSiteMsgSid());
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        if (result == 1) {
            return Response.ok(JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
                                                              WebUtil.getMessage(
                                                                      WebConstants.MsgCd.INFO_DELETE_SUCCESS),
                                                              null))).build();
        } else {
            return Response.ok(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
                                                              WebUtil.getMessage(
                                                                      WebConstants.MsgCd.ERROR_DELETE_FAILURE),
                                                              null))).build();
        }
    }
}
