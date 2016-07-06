package com.h3c.idcloud.core.rest.marketing.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCard;
import com.h3c.idcloud.core.pojo.dto.marketing.GiftCardBatch;
import com.h3c.idcloud.core.rest.marketing.GiftCardRest;
import com.h3c.idcloud.core.service.marketing.api.GiftCardService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RestResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.*;

/**
 * Title: Company:   HP Copyright: Copyright (c) 2012 Description: <Class Description >
 *
 * @author andre created at 10:34 2014/8/13
 */
@Component
public class GiftCardRestImpl implements GiftCardRest {

    @Reference(version = "1.0.0")
    GiftCardService giftCardService;
//
//    @Autowired
//	AccountService accountService;
//
//	@Autowired
//	DepositeService depositeService;
//
//    @Reference(version = "1.0.0")
//    UserService userService;
//
//	@Autowired
//	MessageService messageService;
//
//	@Autowired
//	MailNotificationsService mailNotificationsService;
//
//	@Autowired
//	SmsService smsService;


    /**
     * 查询礼品卡
     *
     * @return return response with Page<GiftCard>
     */
    @Override
    public Response getGiftCards(@Context HttpServletRequest request) {
//        try {
//		    //分页排序处理
//		    BasePager pager = new BasePager();
//		    if (!StringUtil.isNullOrEmpty(request.getParameter("pagenum"))) {
//		    	pager.setPageIndex(Integer.parseInt(request.getParameter("pagenum").toString()));
//		    }
//		    if (!StringUtil.isNullOrEmpty(request.getParameter("pagesize"))) {
//		    	pager.setPageSize(Integer.parseInt(request.getParameter("pagesize").toString()));
//		    }
//		    if (!StringUtil.isNullOrEmpty(request.getParameter("sortdatafield"))) {
//		    	pager.setSortField(request.getParameter("sortdatafield").toString());
//		    }
//		    if (!StringUtil.isNullOrEmpty(request.getParameter("sortorder"))) {
//		    	pager.setSortOrder(request.getParameter("sortorder").toString());
//		    }
//
//            //过滤
//		    Criteria criteria = new Criteria();
//		    Map map = request.getParameterMap();
//            //构建查询对象
//		    WebUtil.preparePageParams(request, pager, criteria, "CREATED_DT DESC");
//
//            //查询礼品卡批次信息
//            List<GiftCard> giftCards = this.giftCardService.findByParams(criteria);
//		    int count = this.giftCardService.findByParamsCnt(criteria);
//
//		    Map<String, Object> data = new HashMap<String, Object>();
//		    data.put("TotalRows", count);
//		    data.put("Rows", giftCards);
//		    String json = JsonUtil.toJson(data);
//
//
//            return Response.status(Response.Status.OK).entity(json).build();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//            return Response.status(Response.Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION), null))).build();
//        }
        Criteria criteria = new Criteria();
        WebUtil.preparePageParams(request, criteria, "CREATED_DT DESC");
        List<GiftCard> giftCards = this.giftCardService.selectByParams(criteria);
        int count = this.giftCardService.countByParams(criteria);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("TotalRows", count);
        data.put("Rows", giftCards);
        String json = JsonUtil.toJson(data);
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response getGiftBinding(@Context HttpServletRequest request,
                                   @Context HttpServletResponse response) {

        //过滤
        Criteria criteria = new Criteria();
        //构建查询对象
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("disOwner", request.getParameter("disOwner"));
        System.out.println(request.getParameter("disOwner"));
        criteria.setCondition(condition);
        //查询礼品卡批次信息
        List<GiftCard> giftCards = this.giftCardService.findByParams(criteria);

        String json = JsonUtil.toJson(giftCards);

        return Response.status(Response.Status.OK).entity(json).build();


    }

    @Override
    public Response getAvailableBatchs(@Context HttpServletRequest request,
                                       @Context HttpServletResponse response) {

        //查询礼品卡批次信息
        List<GiftCard> giftCards = this.giftCardService.findAvailableGiftCards();

        String json = JsonUtil.toJson(giftCards);

        return Response.status(Response.Status.OK).entity(json).build();
    }

    @GET
    @Path("/getValidCardsByBatchSid")
    @Produces("application/json")
    public Response getValidCardsByBatchSid(@Context HttpServletRequest request,
                                            @Context HttpServletResponse response) {

        //过滤
        Criteria criteria = new Criteria();
        //构建查询对象
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("batchSid", request.getParameter("batchSid"));
        condition.put("status", 1);
        criteria.setCondition(condition);
        //查询礼品卡批次信息
        List<GiftCard> giftCards = this.giftCardService.findByParams(criteria);

        String json = JsonUtil.toJson(giftCards);

        return Response.status(Response.Status.OK).entity(json).build();

    }

    @Override
    public Response displayGiftCards(String validStart, String validTo, String status,
                                     @Context HttpServletRequest request,
                                     @Context HttpServletResponse response) {
        System.err.println("##########################");
        Criteria example = new Criteria();
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        System.err.println(authUser.getUserSid());
        System.err.println(authUser.getAccount());
        if (!StringUtil.isNullOrEmpty(validStart)) {
            example.put("validStart", validStart + "00:00:00");
        }
        if (!StringUtil.isNullOrEmpty(validTo)) {
            example.put("validTo", validTo + "23:59:59");
        }
        if (!StringUtil.isNullOrEmpty(status)) {
            example.put("status", status);
        }
        example.put("distributeUserSid", authUser.getUserSid());
        System.err.println(example.get("distributeUserSid"));
//        example.put("disOwner", authUser.getAccount());
//        System.err.println(example.get("disOwner"));
        List<GiftCard> giftCards = this.giftCardService.selectByParams(example);
        return Response.status(Status.OK).entity(new RestResult(giftCards)).build();
    }


    @Override
    public Response bindingByCardSids(Map<String, Object> data) {

        String ids = data.get("cardSid").toString();
//            ids = ids.substring(0, ids.length() - 1);
        String disOwner = data.get("disOwner").toString();
//            Long distributeUser = new Long(data.get("distributeUser").toString());
        String distributeUserSid = data.get("distributeUserSid").toString();

        Long userSid = new Long(distributeUserSid);
//            System.out.println(ids + " " + disOwner);
        //过滤
        Criteria criteria = new Criteria();
        //构建查询对象
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("cardSid", ids);
        System.err.println(condition.get("cardSid"));
        condition.put("disOwner", disOwner);
        System.err.println(condition.get("disOwner"));
        condition.put("distributeUserSid", userSid);
        System.err.println(condition.get("distributeUserSid"));
        criteria.setCondition(condition);
        //查询礼品卡批次信息
        int result = this.giftCardService.updateByCardSids(criteria);

        String json = JsonUtil.toJson(result);
        return Response.status(Status.OK).entity(json).build();


//            String sids[] = ids.split(",");
//            List<Long> sidsL = new ArrayList<Long>();
//            for (String sid : sids) {
//                sidsL.add(Long.parseLong(sid));
//            }
//            //获取列表
//            List<GiftCard> giftCards = this.giftCardService.findListBySids(sidsL);

        //发送站内信
//            for (int i = 0; i < giftCards.size(); i++) {
//                String msgContent = "";
//                GiftCard gc = giftCards.get(i);
//                System.out.println(gc.getCardSid());
//                gc.setDistributeUserSid(distributeUser);
//                gc.setDisOwner(disOwner);
//                gc.setStatus(WebConstants.GiftCardStatus.BINDING);
//                giftCardService.updateByPrimaryKeySelective(gc);
//                msgContent =
//                        WebUtil.getMessage("giftcard.binding.msg",
//                                           new String[]{gc.getCardNo(), gc.getCardPassword(),
//                                                        gc.getFaceValue() + ""});
//                //发送赠送礼品卡消息
//                Message realMsg = new Message();
//                realMsg.setMsgTitle("消息提示");
//                realMsg.setMsgContent(msgContent);
//                realMsg.setToUser(disOwner);
//                realMsg.setTemplateId("giftcard.binding.msg");
//                realMsg.setFromUser("Admin");
//                realMsg.setSendDate(new Date());
//                realMsg.setReadFlag("02");  // unread
//                realMsg.setMsgType("01");  // system message
//                messageService.insertSelective(realMsg);
//                //发送短信消息
//                Criteria criteria2 = new Criteria();
//                criteria2.put("account", disOwner);
//                List<User> userList = this.userService.selectByParams(criteria2);
//                for (User user : userList) {
//                    if (user.getMobile() != null && user.getMobile().length() == 11) {
//                        try {
//                            smsService.sendMessage(user.getMobile(), msgContent);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//            //发送邮件通知
//            Criteria criteria2 = new Criteria();
//            criteria2.put("account", disOwner);
//            List<User> userList = this.userService.selectByParams(criteria2);
//            boolean
//                    sendResult =
//                    mailNotificationsService.sendGiftCardBindingEmail(userList.get(0), giftCards,
//                                                                      WebConstants.MailTemplateId.EMAIL_TO_GIFTBINDING);
//
//            return Response.status(Response.Status.OK).entity(json).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Response.status(Response.Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(
//                    new RestResult(RestResult.FAILURE,
//                                   WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION),
//                                   null))).build();
//        }
//        return null;
    }

    @Override
    public Response findGiftCards(String param) {
        Criteria criteria = new Criteria();
//		WebUtil.preparePageParamsNew(param, criteria, "CREATED_DT DESC");
        List<GiftCard> giftCards = this.giftCardService.selectByParams(criteria);
        int count = this.giftCardService.countByParams(criteria);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("TotalRows", count);
        data.put("Rows", giftCards);
        String json = JsonUtil.toJson(data);
        return Response.status(Status.OK).entity(json).build();
//        return null;
    }

    /**
     * 导出指定的礼品卡
     *
     * @param giftCardSids 礼品卡sid，多个以","分隔
     * @return return response with excel File
     */
    @GET
    @Path("/exportSpecified")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportSpecifiedGiftCards(@Context HttpServletRequest request,
                                             @Context HttpServletResponse response,
                                             @QueryParam("giftCardSids") String giftCardSids) {
//        try {
//            //导出指定的礼品卡
//            if(giftCardSids != null && !giftCardSids.trim().equals("")) {
//                String sids[] = giftCardSids.split(",");
//                List<Long> sidsL = new ArrayList<Long>();
//                for(String sid:sids)
//                    sidsL.add( Long.parseLong(sid));
//                //获取列表
//                List<GiftCard> giftCards = this.giftCardService.findListBySids(sidsL);
//
//                 /**
//                  *  0  未激活   刚创建成功
//                     1  已激活   创建后需要激活后才能使用
//                     2  已充值
//                     3  已作废
//                  */
//                Map<String,Object> classMap = new HashMap<String,Object>();
//                    classMap.put(WebConstants.GiftCardStatus.NOT_ACTIVATED.toString(),"未激活");
//                    classMap.put(WebConstants.GiftCardStatus.ACTIVATED.toString(),"已激活");
//                    classMap.put(WebConstants.GiftCardStatus.CHARGED.toString(),"已充值");
//                    classMap.put(WebConstants.GiftCardStatus.INVALID.toString(),"已作废");
//
//                //导出到Excel文件
//                Object[][] titles = new Object[][] {
//                    {"batchNo","批次号","6000"},
//                    {"cardName","礼品卡名称","5000"},
//                    {"cardNo","卡号","5000"},
//                    {"cardPassword","卡密","4000"},
//                    {"validStart","有效期开始","4000"},
//                    {"validTo","有效期截止","3000"},
//                    {"faceValue","面额","3000"},
//                    {"status","状态","3000",classMap}
//                };
//                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
//
//                String fileName ="礼品卡导出"+df.format(new Date())+".xls";;
//                String sheetName ="礼品卡";
//                FileUtil.downloadCommonExcelFile(fileName,sheetName,titles,giftCards,response);
//            }
//            return null;
//            //return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.SUCCESS, null, null))).build();
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
     * 导出查村到的所有的礼品卡
     *
     * @return return response with excel File
     */
    @GET
    @Path("/exportQueried")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportQueriedGiftCards(@Context HttpServletRequest request,
                                           @Context HttpServletResponse response) {
//        try {
//             //分页排序处理
//		    BasePager pager = new BasePager();
//            //过滤
//		    Criteria criteria = new Criteria();
//            //构建查询对象
//		    WebUtil.preparePageParams(request, pager, criteria, "CREATED_DT DESC");
//
//             //查询礼品卡批次信息
//            List<GiftCard> giftCards = this.giftCardService.findByParams(criteria);
//
//             /**
//               *  0  未激活   刚创建成功
//                  1  已激活   创建后需要激活后才能使用
//                  2  已充值
//                  3  已作废
//               */
//             Map<String,Object> classMap = new HashMap<String,Object>();
//                 classMap.put(WebConstants.GiftCardStatus.NOT_ACTIVATED.toString(),"未激活");
//                 classMap.put(WebConstants.GiftCardStatus.ACTIVATED.toString(),"已激活");
//                 classMap.put(WebConstants.GiftCardStatus.CHARGED.toString(),"已充值");
//                 classMap.put(WebConstants.GiftCardStatus.INVALID.toString(),"已作废");
//
//             //导出到Excel文件
//             Object[][] titles = new Object[][] {
//                 {"batchNo","批次号","6000"},
//                 {"cardName","礼品卡名称","5000"},
//                 {"cardNo","卡号","5000"},
//                 {"cardPassword","卡密","4000"},
//                 {"validStart","有效期开始","4000"},
//                 {"validTo","有效期截止","3000"},
//                 {"faceValue","面额","3000"},
//                 {"status","状态","3000",classMap}
//             };
//             SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
//
//             String fileName ="礼品卡导出"+df.format(new Date())+".xls";
//             String sheetName ="礼品卡";
//             FileUtil.downloadCommonExcelFile(fileName,sheetName,titles,giftCards,response);
//
//             return null;
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
     * 批量激活一批指定的礼品卡
     *
     * @param giftCardSids 礼品卡sid串，以","隔开
     * @return Response with true/false
     */
    @PUT
    @Path("/activate")
    public Response activateGiftCards(@QueryParam("giftCardSids") String giftCardSids) {
//         try {
//             int activeCnt = 0;
//            //批量激活一批指定的礼品卡
//            if(giftCardSids != null && ! giftCardSids.trim().equals("")) {
//                String sids[] = giftCardSids.split(",");
//                List<Long> sidsL = new ArrayList<Long>();
//                for(String sid:sids)
//                    sidsL.add( Long.parseLong(sid));
//
//                activeCnt = this.giftCardService.executeActivateGiftCards(sidsL, AuthUtil.getAuthUser());
//            }
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

    @POST
    @Path("/binding")
    public Response binding(Map<String, Object> data) {
//         try {
//        	Long cardSid= new Long(data.get("cardSid").toString());
//        	Long distributeUser=new Long(data.get("distributeUser").toString());
//        	String disOwner=data.get("disOwner").toString();
//
//
//
//    		Criteria criteria1 = new Criteria();
//    		criteria1.put("account", disOwner);
//    		List<User> userExistList = this.userService.selectByParams(criteria1);
//    		if(userExistList==null || userExistList.size()==0){
//    			 return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, "该用户不存在.", null))).build();
//    		}
//
//        	System.out.println(cardSid+" "+distributeUser+" "+disOwner);
//        	 Criteria criteria = new Criteria();
//     		Map<String, Object> condition = new HashMap<String, Object>();
//    		condition.put("cardSid", cardSid);
//        	 criteria.setCondition(condition);
//
//        	 List<GiftCard> giftCards =this.giftCardService.findByParams(criteria);
//        	 String msgContent = "";
//        	 if(giftCards!=null && giftCards.size()>0){
//        		 GiftCard gc=giftCards.get(0);
//        		 System.out.println(gc.getCardSid());
//        		 gc.setDistributeUserSid(distributeUser);
//        		 gc.setDisOwner(disOwner);
//        		 gc.setStatus(WebConstants.GiftCardStatus.BINDING);
//        		 giftCardService.updateByPrimaryKeySelective(gc);
//        		 msgContent = WebUtil.getMessage("giftcard.binding.msg", new String [] {gc.getCardNo(), gc.getCardPassword(),gc.getFaceValue()+""});
//        		//发送充值消息
//          		Message realMsg = new Message();
//                  realMsg.setMsgTitle("消息提示");
//                  realMsg.setMsgContent(msgContent);
//                  realMsg.setToUser(disOwner);
//                  realMsg.setTemplateId("giftcard.binding.msg");
//                  realMsg.setFromUser("Admin");
//                  realMsg.setSendDate(new Date());
//                  realMsg.setReadFlag("02");  // unread
//                  realMsg.setMsgType("01");  // system message
//                  messageService.insertSelective(realMsg);
//        	 }
//
//    		 //发送邮件通知
//        	 Criteria criteria2 = new Criteria();
//             criteria2.put("account", disOwner);
//             List<User> userList = this.userService.selectByParams(criteria2);
//     	     boolean sendResult =mailNotificationsService.sendGiftCardBindingEmail(userList.get(0), giftCards, WebConstants.MailTemplateId.EMAIL_TO_GIFTBINDING);
//
//
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.SUCCESS, "", null))).build();
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
     * 作废指定的礼品卡
     *
     * @param giftCardSid 礼品卡sid
     * @return Response with true/false
     */
    @PUT
    @Path("/{giftCardSid}/invalid")
    public Response invalidGiftCard(@PathParam("giftCardSid") Long giftCardSid) {
//        try {
//            //更新虚拟工作室
//            this.giftCardService.executeInvalidGiftCard(giftCardSid, AuthUtil.getAuthUser());
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
     * 获取单个礼品卡
     *
     * @return return response with entity of GiftCard
     */
    @GET
    @Path("/{giftCardSid}")
    public Response getGiftCard(@PathParam("giftCardSid") Long giftCardSid) {
//        try {
//            GiftCard giftCard = this.giftCardService.selectByPrimaryKey(giftCardSid);
//            return Response.status(Response.Status.OK).entity(JsonUtil.toJson(giftCard)).build();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//             return Response.status(Response.Status.EXPECTATION_FAILED).entity(JsonUtil.toJson(new RestResult(RestResult.FAILURE, WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION), null))).build();
//        }
        return null;
    }

    @Override
    @POST
    @Path("/depositByCard")
    public Response depositByCard(GiftCard card) {
//		//判断礼品卡是否过期，是否激活
//		String json = null;
//		if(card==null || StringUtils.isEmpty(card.getCardNo())||StringUtils.isEmpty(card.getCardPassword())){
//			json = JsonUtil.toJson(new RestResult(
//					RestResult.SUCCESS,
//					"请输入卡号或密码",
//					null));
//			return Response.status(Status.OK).entity(json).build();
//		}
//		List<GiftCard> cardList = this.giftCardService.selectByParams(
//				new Criteria("cardNoEqual",card.getCardNo()).put("cardPassword", card.getCardPassword()));
//		if(cardList == null || cardList.size() == 0){
//			json = JsonUtil.toJson(new RestResult(
//					RestResult.SUCCESS,
//					"请输入正确的卡号或密码",
//					null));
//			return Response.status(Status.OK).entity(json).build();
//		}else{
//			GiftCard gc = cardList.get(0);
//			gc.setIsAutoStart(card.getIsAutoStart());
//			int status = gc.getStatus();
//			switch(status){
//				case 4:{}
//				case 1:{
//					Date startDay = gc.getValidStart();
//					Date endDay = gc.getValidTo();
//					Date now = new Date();
//					if(now.after(startDay)&&now.before(endDay)){
//						User user = UserManager.getUserSession().getUser();
//						if(user == null){
//							json = JsonUtil.toJson(new RestResult(
//									RestResult.FAILURE,
//									"请先登录！",
//									null));
//							return Response.status(Status.OK).entity(json).build();
//						}
//						Long accountSid = user.getAccountSid();
//						Account account = this.accountService.selectByPrimaryKey(accountSid);
//						if(null == account){
//							json = JsonUtil.toJson(new RestResult(
//									RestResult.FAILURE,
//									"账号不存在！",
//									null));
//							return Response.status(Status.OK).entity(json).build();
//						}
//						if(!StringUtil.isNullOrEmpty(gc.getDisOwner()) && !gc.getDisOwner().equals(user.getAccount())){
//							json = JsonUtil.toJson(new RestResult(
//									RestResult.FAILURE,
//									"非本人绑定礼品卡不可使用！",
//									null));
//							return Response.status(Status.OK).entity(json).build();
//						}
//
//						int result = this.accountService.updateDepositByCard(account, user, gc);
//						if(result > 0){
//							json = JsonUtil.toJson(new RestResult(
//									RestResult.SUCCESS,
//									"充值成功，充值金额为"+gc.getFaceValue()+"，目前余额："+account.getAccountBalance()+"! 赠送余额："+account.getAccountGiftBalance(),
//									null));
//							return Response.status(Status.OK).entity(json).build();
//						}else{
//							json = JsonUtil.toJson(new RestResult(
//									RestResult.FAILURE,
//									"充值失败！",
//									null));
//							return Response.status(Status.OK).entity(json).build();
//						}
////						BigDecimal money = gc.getFaceValue();
////						BigDecimal balance = account.getAccountBalance();
////						account.setAccountBalance(balance.add(money));
////						this.accountService.updateByPrimaryKeySelective(account);
////						gc.setChargeAccount(account.getAccountSid().toString());
////						gc.setChargeOperUserSid(user.getUserSid());
////						gc.setChargeTime(new Date());
////						gc.setStatus(2);
////						this.giftCardService.updateByPrimaryKeySelective(gc);
////
////						Deposite deposite = new Deposite();
////						deposite.setAccountSid(accountSid);
////						deposite.setAmountDeposited(money);
////						deposite.setAmountReceived(money);
////						deposite.setCurrency("01");
////						deposite.setFlowId((long) (Math.random() * 1000));
////						deposite.setTime(new Date());
////						this.depositeService.insertSelective(deposite);
//					}else{
//						json = JsonUtil.toJson(new RestResult(
//								RestResult.FAILURE,
//								"充值卡不在有效期内！",
//								null));
//						return Response.status(Status.OK).entity(json).build();
//					}
//				}
//				case 0:{
//					json = JsonUtil.toJson(new RestResult(
//							RestResult.FAILURE,
//						"充值卡未激活！",
//						null));
//					return Response.status(Status.OK).entity(json).build();
//					}
//				case 2:{
//					json = JsonUtil.toJson(new RestResult(
//							RestResult.FAILURE,
//							"充值卡已使用！",
//							null));
//					return Response.status(Status.OK).entity(json).build();
//				}
//				case 3:{
//					json = JsonUtil.toJson(new RestResult(
//							RestResult.FAILURE,
//							"充值卡已作废！",
//							null));
//					return Response.status(Status.OK).entity(json).build();
//				}
//			}
//		}
        return null;
    }
}
