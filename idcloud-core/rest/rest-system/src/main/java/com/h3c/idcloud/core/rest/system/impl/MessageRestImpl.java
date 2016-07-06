/**
 *
 */
package com.h3c.idcloud.core.rest.system.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.system.Message;
import com.h3c.idcloud.core.rest.system.MessageRest;
import com.h3c.idcloud.core.service.system.api.MessageService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.*;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author wuchong
 * @since 2016-2-20
 */
@Component
public class MessageRestImpl implements MessageRest {
    Logger log = Logger.getLogger(this.getClass());

    @Reference(version = "1.0.0")
    private MessageService messageService;

    @Reference(version = "1.0.0")
    private UserService userService;

//    @Reference(version = "1.0.0")
//    private NotificationService notificationService;

//    /**
//     * 根据条件查询
//     * @param msg
//     * @return
//     */
//    @Override
//    public Response findByCriteria(MsgWithPager msg) {
//        Criteria criteria = new Criteria();
//        HashMap<String, Object> con = new HashMap<>();
//        con.put("messages",msg.getMessage());
//        criteria.setCondition(con);
////        try {
////            criteria.setCondition(BeanUtil.collectProperties(msg.getMessage()));
////        } catch (IllegalAccessException e) {
////            e.printStackTrace();
////            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
////        } catch (InvocationTargetException e) {
////            e.printStackTrace();
////            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
////        }
//
//        if (msg.getPager() != null) {
//            WebUtil.preparePageParams(msg.getPager(), criteria, "SEND_DATE DESC");
//        }
//        List<Message> list = this.messageService.selectByParams(criteria);
//        String json = JsonUtil.toJson(list);
//        return Response.status(Status.OK).entity(json).build();
//    }

    /**
     * 分页查询用户信息
     *
     * @param request
     * @return
     */
    @Override
    public Response findByPage(@Context HttpServletRequest request) {
       //参数设置
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "MSG_SID");
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        param.put("toUser",authUser.getAccount());
        //查询数据
        List<Message> list = this.messageService.selectByParams(param);
//        int total = this.messageService.countByParams(param);
        String json = JsonUtil.toJson(new RestResult(list));
        return Response.status(Response.Status.OK).entity(json).build();
    }

    /**
     * 添加消息
     * @param message
     * @return
     */
    @Override
    public Response addMessage(Message message) {
        String json = "";
        int result = this.messageService.insert(message);
        if(result > 0 ){
            json = InterfaceResult.setResult(WebConstants.ResultStatus.SUCCESS, null);
            return Response.ok(json).build();
        }else {
            json = InterfaceResult.setResult(WebConstants.ResultStatus.FAILURE, null);
            return Response.ok(json).build();
        }
    }

    @Override
    public Response deleteMessages(String[] msgSids) {
        String json = "";
        try {
            this.messageService.deleteBatchByPks(Arrays.asList(msgSids));
            json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS), null));
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE), null));
        }
        return Response.ok(json).build();
    }
//
//    /**
//     * 删除全部消息
//     * @param message
//     * @return
//     */
//    @Override
//    public Response deleteAllMessages(Message message) {
//        Criteria criteria = new Criteria();
//        String json = "";
//        try {
//            if (message != null) {
//                if (!StringUtil.isNullOrEmpty(message.getToUser())) {
//                    criteria.put("toUser", message.getToUser());
//                }
//            }
//            messageService.deleteByParams(criteria);
//            json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_DELETE_SUCCESS), null));
//        } catch (Exception e) {
//            e.printStackTrace();
//            json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_DELETE_FAILURE), null));
//        }
//        return Response.status(Status.OK).entity(json).build();
//    }
//
    @Override
    public Response updateMessages(Message[] messages) {
        String json = "";
        System.out.println(JsonUtil.toJson(Arrays.asList(messages)));
        try {
            messageService.updateBatchByPks(Arrays.asList(messages));
            json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
        }
        return Response.ok(json).build();
    }
//
//    /**
//     * 全部标记为已读消息
//     * @param message
//     * @return
//     */
//    @Override
//    public Response updateAllMessages(Message message) {
//        Criteria criteria = new Criteria();
//        String json = "";
//        if (!StringUtil.isNullOrEmpty(message.getFromUser())) {
//            criteria.put("fromUser", message.getFromUser());
//        }
//        if (!StringUtil.isNullOrEmpty(message.getToUser())) {
//            criteria.put("toUser", message.getToUser());
//        }
//
//        try {
//            messageService.updateByParamsSelective(message, criteria);
//            json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
//        } catch (Exception e) {
//            e.printStackTrace();
//            json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
//        }
//        return Response.status(Status.OK).entity(json).build();
//    }
//
//    /**
//     * 添加系统消息
//     * @param systemMessage
//     * @return
//     */
//    @Override
//    public Response addSystemMessage(SystemMessage systemMessage) {
//    	//渲染成功
//    	if(systemMessage.getMsgId().equals("system.message.render.success")){
//    		 User user = userService.selectByPrimaryKey(systemMessage.getUserSid());
//    		if(!StringUtil.isNullOrEmpty(user)){
//    			MsgVO msgVo = new MsgVO();
//    			msgVo.setSystemMessage(systemMessage);
//    			this.notificationService.sendNotificationToUser(user, WebConstants.MSG_CONTENT_TYPE.RENDER_SUCCESS_REMIND, msgVo);
//        		return Response.status(Status.OK).entity(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_MESSAGE_SUCCESS))).build();
//    		}else{
//                return Response.status(Status.OK).entity(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.SYSTEM_USER_NOT_FOUND))).build();
//
//    		}
//    	}
//        // 1.判断用户是否存在
//        User user = userService.selectByPrimaryKey(systemMessage.getUserSid());
//        if (user == null) {
//            return Response.status(Status.OK).entity(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.SYSTEM_USER_NOT_FOUND))).build();
//        } else {
//            // 2.转换参数
//            String msgContent = WebUtil.getMessage(systemMessage.getMsgId(), systemMessage.getParams());
//            //根据corelationId进行消息过滤
//            boolean isFirst =false;
//            Criteria criteria = new Criteria();
//            if(!StringUtil.isNullOrEmpty(systemMessage.getUserSid())) {
//            	criteria.put("toUser",userService.selectByPrimaryKey(systemMessage.getUserSid()).getAccount());
//            }else {
//            	criteria.put("toUser", systemMessage.getParams()[0].toString());
//            }
//            criteria.put("msgType", "01");
//            criteria.put("templateId", systemMessage.getMsgId());
//            criteria.put("corelationId", systemMessage.getCorelationId());
//            criteria.setOrderByClause("SEND_DATE DESC");
//            criteria.setMysqlLength(1);
//    		criteria.setMysqlOffset(0);
//            List<Message> messages = this.messageService.selectByParams(criteria);
//            if(StringUtil.isNullOrEmpty(messages) || messages.size() == 0){
//            	isFirst = true;
//            }
//
//            //计算同一消息本次发送距离上次发送的时间间隔
//            long minutes2 = 0;
//            if(!isFirst){
//            	Calendar c1 = Calendar.getInstance();
//                c1.setTime(new Date());
//                Calendar c2 = Calendar.getInstance();
//                c2.setTime(messages.get(messages.size()-1).getSendDate());
//                long difference = c1.getTimeInMillis()-c2.getTimeInMillis();
//                minutes2 = difference/(60*1000);
//            }
//            log.info("addSystemMessage------templateId="+systemMessage.getMsgId()+" corelationId="+systemMessage.getCorelationId()+" isFirst="+isFirst+" minutes2="+minutes2);
//           //消息过滤
//            if(isFirst ||  minutes2> 30){
//            	// 3.入库
//                Message realMsg = new Message();
//                // 4.设置message
//                realMsg.setMsgTitle("消息提示");
//                realMsg.setMsgContent(msgContent);
//                if(systemMessage.getUserSid()!=null) {
//                	realMsg.setToUser(userService.selectByPrimaryKey(systemMessage.getUserSid()).getAccount());
//                }else {
//                	realMsg.setToUser(systemMessage.getParams()[0].toString());
//                }
//                realMsg.setCorelationId(systemMessage.getCorelationId());
//                realMsg.setTemplateId(systemMessage.getMsgId());
//                realMsg.setFromUser("Admin");
//                realMsg.setSendDate(new Date());
//                realMsg.setReadFlag("02");  // unread
//                realMsg.setMsgType("01");  // system message
//                messageService.insertSelective(realMsg);
//            }
//
//        }
//        return Response.status(Status.OK).entity(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_MESSAGE_SUCCESS))).build();
//    }
//
//    /**
//     * 群发系统消息
//     * @param systemMessage
//     * @return
//     */
//    @Override
//    public Response addSystemMessages(SystemMessage systemMessage) {
//        int count;
//        try {
//            count = messageService.insertSystemMessages(systemMessage);
//        } catch (Exception e) {
//            return Response.status(Status.OK).entity(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_MESSAGE_FAILURE))).build();
//        }
//        return Response.status(Status.OK).entity(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_MESSAGE_SUCCESS), count)).build();
//    }
//
//    @Override
//    public Response addSystemMessages(Coupon coupon) {
//        int count;
//        try {
//            count = messageService.insertCouponMessages(coupon);
//        } catch (Exception e) {
//            return Response.status(Status.OK).entity(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_MESSAGE_FAILURE))).build();
//        }
//        return Response.status(Status.OK).entity(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(WebConstants.MsgCd.INFO_MESSAGE_SUCCESS), count)).build();
//    }
//
    /**
     * 查询所有消息数量
     * @param message
     * @return
     */
    @Override
    public Response getCountByCriteria(@Context HttpServletRequest request,Message message) {
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        Map<String, String> map = new HashMap<String, String>();
        Criteria criteria = new Criteria();
        if (!StringUtil.isNullOrEmpty(message.getReadFlag())) {
            criteria.put("readFlag", message.getReadFlag());
        }
        criteria.put("toUser",authUser.getAccount());
        Integer count = messageService.countByParams(criteria);
        map.put("toDoMessage",count.toString());
        return Response.ok(JsonUtil.toJson(new RestResult(map))).build();
    }
//
//
//    /**
//     * 查询所有消息
//     * @param message
//     * @return
//     */
//	@Override
//	public Response getMsgByCriteria(Message message) {
//		Criteria criteria = new Criteria();
//        HashMap<String, Object> con = new HashMap<>();
//        con.put("messages",message);
//        criteria.setCondition(con);
////        try {
////			criteria.setCondition(BeanUtil.collectProperties(message));
////		} catch (IllegalAccessException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (InvocationTargetException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//        List<Message> list = this.messageService.selectByParams(criteria);
//        String json = JsonUtil.toJson(list);
//        return Response.status(Status.OK).entity(json).build();
//	}
//
//
//    /**
//     * 根据条件查询
//     * @param param
//     * @param request
//     * @param response
//     * @return
//     */
//    @Override
//    public Response findMessageList(String param, @Context HttpServletRequest request, @Context HttpServletResponse response) {
//        Map<String,String> map = WebUtil.getParamsMap(param, "utf-8");
//        // 分页排序处理
//        BasePager pager = new BasePager();
//        if (!StringUtil.isNullOrEmpty(map.get("pagenum"))) {
//            pager.setPageIndex(Integer.parseInt(map.get("pagenum").toString()));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("pagesize"))) {
//            pager.setPageSize(Integer.parseInt(map.get("pagesize").toString()));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("sortdatafield"))) {
//            pager.setSortField(map.get("sortdatafield").toString());
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("sortorder"))) {
//            pager.setSortOrder(map.get("sortorder").toString());
//        }
//
//        // 过滤
//        Criteria criteria = new Criteria();
//        if (!StringUtil.isNullOrEmpty(map.get("sendDateStart"))) {
//            criteria.put("sendDateStart", map.get("sendDateStart"));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("sendDateEnd"))) {
//            criteria.put("sendDateEnd", map.get("sendDateEnd"));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("fromUser"))) {
//            criteria.put("fromUser", map.get("fromUser"));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("toUser"))) {
//            criteria.put("toUser", map.get("toUser"));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("msgType"))) {
//            criteria.put("msgType", map.get("msgType"));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("readFlag"))) {
//            criteria.put("readFlag", map.get("readFlag"));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("inboxInUse"))) {
//            criteria.put("inboxInUse", map.get("inboxInUse"));
//        }
//        if (!StringUtil.isNullOrEmpty(map.get("outboxInUse"))) {
//            criteria.put("outboxInUse", map.get("outboxInUse"));
//        }
//
//        WebUtil.preparePageParams(request, pager, criteria, "SEND_DATE DESC");
//        List<Message> messageList = this.messageService.selectByParams(criteria);
//        int count = this.messageService.countByParams(criteria);
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("TotalRows", count);
//        data.put("Rows", messageList);
//        String json = JsonUtil.toJson(data);
//        return Response.status(Status.OK).entity(json).build();
//    }

}
