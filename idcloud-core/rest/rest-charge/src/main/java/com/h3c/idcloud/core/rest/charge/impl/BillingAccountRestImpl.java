package com.h3c.idcloud.core.rest.charge.impl;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountMgtObj;
import com.h3c.idcloud.core.pojo.dto.charge.Deposite;
import com.h3c.idcloud.core.pojo.dto.charge.ServiceBill;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.vo.charge.DepositeVo;
import com.h3c.idcloud.core.pojo.vo.charge.ServiceBillVo;
import com.h3c.idcloud.core.rest.charge.BillingAccountRest;
import com.h3c.idcloud.core.service.charge.api.BillingAccountMgtObjService;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.charge.api.PlatformOnlineRechargeOpService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.*;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.RequestContextUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Component
public class BillingAccountRestImpl implements BillingAccountRest {

    @Reference(version = "1.0.0")
    private BillingAccountService billingAccountService;


    @Reference(version = "1.0.0")
    private UserService userService;

    @Reference(version = "1.0.0")
    private BillingAccountMgtObjService billingAccountMgtObjService;

    @Reference(version = "1.0.0")
    private PlatformOnlineRechargeOpService platformOnlineRechargeOpService;

    /**
     * 分页查询用户信息
     */
    @Override
    public Response findByPage(@Context HttpServletRequest request) {

        // 参数设置
        Criteria param = new Criteria();
        WebUtil.preparePageParams(request, param, "A.ACCOUNT_SID DESC");

        // 查询数据
        List<BillingAccount> list = this.billingAccountService.selectByParams(param);
        int total = this.billingAccountService.countByParams(param);

        String json = JsonUtil.toJson(new BaseGridReturn(total, list));

        return Response.ok(json).build();
    }

    public Response addAccount(BillingAccount account) {
        String json = "";
        //check if account name is exists
        if (billingAccountService
                    .selectByParams(new Criteria("accountName", account.getAccountName())).size()
            > 0) {
            return Response.status(Status.OK)
                    .entity(new RestResult(RestResult.Status.FAILURE, "账户名已存在")).build();
        }
        WebUtil.prepareInsertParams(account);
        int result = this.billingAccountService.insertSelective(account);
        if (result == 1) {
            json =
                    JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(
                            WebConstants.MsgCd.INFO_INSERT_SUCCESS), null));
        } else {
            json =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_INSERT_FAILURE), null));
        }
        return Response.status(Status.OK).entity(json).build();
    }

    public Response findAll() {
        List<BillingAccount> list = this.billingAccountService.selectByParams(new Criteria());
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    public Response deleteAccount(BillingAccount account) {
        String json = "";

        try {
            account.setStatus(WebConstants.ACCOUNT_STATUS.FREEZE);
            this.billingAccountService.updateByPrimaryKeySelective(account);
//		    boolean result = this.billingAccountService.deleteByPrimaryKey(account.getAccountSid()) == 1 ? true:false;
            json =
                    JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil.getMessage(
                            WebConstants.MsgCd.INFO_DELETE_SUCCESS), null));
        } catch (Exception e) {
            json =
                    JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil.getMessage(
                            WebConstants.MsgCd.ERROR_DELETE_FAILURE), null));
        }

        return Response.status(Status.OK).entity(json).build();
    }

    public Response updateAccount(BillingAccount account) {
        String returnJson = "";
        WebUtil.prepareUpdateParams(account);
        BillingAccount
                oldAccount =
                this.billingAccountService.selectByPrimaryKey(account.getAccountSid());
        int result = this.billingAccountService.updateByPrimaryKeySelective(account);
        if (result == 1) {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS, WebUtil
                    .getMessage(WebConstants.MsgCd.INFO_UPDATE_SUCCESS), null));
        } else {
            returnJson = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE, WebUtil
                    .getMessage(WebConstants.MsgCd.ERROR_UPDATE_FAILURE), null));
        }

        return Response.status(Status.OK).entity(returnJson).build();
    }

    @Override
    public Response depositPay(Long userSid, Double amount) {
        User user = this.userService.selectByPrimaryKey2(userSid);
        Criteria example = new Criteria();
        example.put("mgtObjSid", user.getMgtObjSid());
        List<BillingAccountMgtObj>
                billingAccountMgtObjs =
                billingAccountMgtObjService.selectByParams(example);
        BillingAccount
                billingAccount =
                this.billingAccountService
                        .selectByPrimaryKey(billingAccountMgtObjs.get(0).getAccountSid());
        // 临时使用页面直充
        Deposite deposite = new Deposite();
        deposite.setAccountSid(billingAccount.getAccountSid());
        deposite.setUserSid(userSid);
        deposite.setChannel(WebConstants.CHARGE_CHANNEL.ZHICHONG);
        deposite.setAmountDeposited(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
        deposite.setAmountReceived(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
        Long depositeSid = platformOnlineRechargeOpService.saveDepositeRecordInfo(deposite);

        Map<String, String> payInfoMap = new HashMap<>();
        payInfoMap.put("depositeSid", depositeSid.toString());
        payInfoMap.put("thirdPaymentNo",
                       "T" + StringUtil.dateFormat(new Date(), "yyyyMMddHHmmss") + System
                               .currentTimeMillis());

        platformOnlineRechargeOpService.executeDepositeOperationForRest(payInfoMap);

        return Response.status(Status.OK).entity(JsonUtil.toJson(new SimpleRestResult(true)))
                .build();
    }

    @Override
    public Response displayAccount(Long userSid) {
        User user = this.userService.selectByPrimaryKey2(userSid);
        Criteria example = new Criteria();
        example.put("mgtObjSid", user.getMgtObjSid());
        List<BillingAccountMgtObj>
                billingAccountMgtObjs =
                billingAccountMgtObjService.selectByParams(example);
        BillingAccount
                billingAccount =
                this.billingAccountService
                        .selectByPrimaryKey(billingAccountMgtObjs.get(0).getAccountSid());
        return Response.status(Status.OK)
                .entity(JsonUtil.toJson(new RestResult(billingAccount.getBalance()))).build();
    }

    public Response findByAccountSid(long accountSid) {
        String json = "";
        BillingAccount returnedAccount = this.billingAccountService.selectByPrimaryKey(accountSid);
        if (returnedAccount != null) {
            json = JsonUtil.toJson(returnedAccount);
        }
        return Response.status(Status.OK).entity(json).build();
    }

    public Response findByCriteria(BillingAccount account) {
        Criteria example = new Criteria();
        if (account != null) {
            if (!StringUtil.isNullOrEmpty(account.getAccountName())) {
                example.put("accountNameLike", account.getAccountName());
            }
            if (!StringUtil.isNullOrEmpty(account.getAccountType())) {
                example.put("accountType", account.getAccountType());
            }
            if (!StringUtil.isNullOrEmpty(account.getAccountLevelSid())) {
                example.put("accountLevelSid", account.getAccountLevelSid());
            }
            if (!StringUtil.isNullOrEmpty(account.getStatus())) {
                example.put("status", account.getStatus());
            } else {
                example.put("statusNotEqual", WebConstants.ACCOUNT_STATUS.FREEZE);
            }
        }
        List<BillingAccount> list = this.billingAccountService.selectByParams(example);
        String json = JsonUtil.toJson(list);
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    @WebMethod
    @POST
    @Path("/deposit")
    public Response deposit(BillingAccount account) {
        /*UserSession currentUser = UserManager.getUserSession();
		String json = "";
		if (null != currentUser) {
			try {
				if(this.userService.checkUserBelongToEnterprise(currentUser.getUser())){
					json = JsonUtil
							.toJson(new RestResult(
									RestResult.Status.FAILURE,
									WebUtil.getMessage("error.deposit.contact.admin"),
									null));
					return Response.status(Status.OK).entity(json).build();
				}
			} catch (ServiceException e) {
				log.error(e);
				json = JsonUtil
						.toJson(new RestResult(
								RestResult.Status.FAILURE,
								WebUtil.getMessage(WebConstants.MsgCd.ERROR_SYS_EXCEPTION),
								null));
				return Response.status(Status.OK).entity(json).build();
			}
			Long accountSid = currentUser.getUser().getAccountSid();
			account.setAccountSid(accountSid);
			BillingAccount a = this.billingAccountService.selectByPrimaryKey(accountSid);
			BigDecimal bal = account.getAccountBalance();

			// 增加积分
			if (a.getUsableCredit() != null) {
				account.setUsableCredit(a.getUsableCredit() + bal.longValue());
			} else {
				account.setUsableCredit(bal.longValue());
			}
			// 增加充值优惠
			List<DepositPrize> dpList = this.depositPrizeService.selectByParams(null);
			BigDecimal bd = new BigDecimal(0);
			if(dpList==null || dpList.size()==0){
				log.info("there is no depositPrize.");
			}else{
				Date now = new Date();
				List<DepositCash> dcList = null;
				label:for(int i=0;i<dpList.size();i++){
					DepositPrize dp = dpList.get(i);
					Date startTime = dp.getValidStartDt();
					Date endTime = dp.getValidToDt();
					if(now.after(startTime) && now.before(endTime)){
						dcList = new ArrayList<DepositCash>();
						DepositCash dc3 = new DepositCash();
						Long dp3 = dp.getMinDeposit3();
						Long cash3 = dp.getCashGiven3();
						dc3.setCash(cash3);
						dc3.setDeposit(dp3);

						DepositCash dc2 = new DepositCash();
						Long dp2 = dp.getMinDeposit2();
						Long cash2 = dp.getCashGiven2();
						dc2.setCash(cash2);
						dc2.setDeposit(dp2);

						DepositCash dc1 = new DepositCash();
						Long dp1 = dp.getMinDeposit1();
						Long cash1 = dp.getCashGiven1();
						dc1.setCash(cash1);
						dc1.setDeposit(dp1);

						dcList.add(dc1);
						dcList.add(dc2);
						dcList.add(dc3);

						Collections.sort(dcList);
						for(int j=dcList.size()-1;j>=0;j--){
							DepositCash dc = dcList.get(j);
							if(bal.longValue() >= dc.getDeposit()){
								bd = new BigDecimal(dc.getCash());
								log.info("depositPrize "+dc.getCash());
								break label;
							}
						}

					}else{
						continue;
					}

				}
			}
			if (a.getAccountBalance() != null) {
				account.setAccountBalance(a.getAccountBalance().add(bal).add(bd));
			}else{
				account.setAccountBalance(bal.add(bd));
			}
			try {
				//痕迹
				traceService.insertTraceLog(UserManager.getUserSession().getUser(), "我的财务", "用户充值"+bal+"元，赠送"+bd+"元。");
				this.billingAccountService.updateDeposit(account,currentUser.getUser(),bal);
				json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
						WebUtil.getMessage("info.deposit.success.givingaway",new Object[]{bal,bd}), null));

			} catch (ServiceException e) {
				json = JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
						WebUtil.getMessage("error.deposit.failure"), null));
				log.error(e);
			}
		}else{
			return Response.status(Status.FORBIDDEN).entity(JsonUtil.toJson(new RestResult(RestResult.Status.FAILURE,
					"User is not login.", null))).build();
		}
		return Response.status(Status.OK).entity(json).build();*/
        return null;
    }

    @Override
    @WebMethod
    @POST
    @Path("/balance")
    public Response balance() {
        String json = null;
		/*User user = UserManager.getUserSession().getUser();
		if(user == null){
			json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
					"user not login", null));
			return Response.status(Status.OK).entity(json).build();
		}
		BillingAccount account = this.billingAccountService.selectByPrimaryKey(user.getAccountSid());
		if(account == null){
			json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,
					"account is empty", null));
			return Response.status(Status.OK).entity(json).build();
		}
		if(account.getAccountBalance() == null){
			account.setAccountBalance(new BigDecimal(0));
		}
		json = JsonUtil.toJson(new Res(getFormatBigDecimal(account.getAccountBalance()),
				account.getUsableCredit(),
				account.getAccountLevelName(),
				getFormatBigDecimal(account.getAccountGiftBalance()),
				getFormatBigDecimal(account.getOverdraftLimit())));*/
        return Response.status(Status.OK).entity(json).build();
    }

    private BigDecimal getFormatBigDecimal(BigDecimal bigdecimal) {
        return StringUtil.isNullOrEmpty(bigdecimal) ? BigDecimal.ZERO : bigdecimal;
    }

    class Res {

        Res(BigDecimal data) {
            this.data = data;
        }

        Res(BigDecimal data, Long credit, String accountLevelName, BigDecimal giftBalance,
            BigDecimal overdraftLimit) {
            this.data = data;
            this.credit = credit;
            this.accountLevelName = accountLevelName;
            this.giftBalance = giftBalance;
            this.overdraftLimit = overdraftLimit;
        }

        private Long credit;
        private BigDecimal data;
        private BigDecimal giftBalance;
        private String accountLevelName;
        private BigDecimal overdraftLimit;

        public BigDecimal getData() {
            if (this.data == null) {
                this.data = BigDecimal.ZERO;
            }
            return data;
        }

        public void setData(BigDecimal data) {
            this.data = data;
        }

        public Long getCredit() {
            if (this.credit == null) {
                this.credit = 0l;
            }
            return credit;
        }

        public BigDecimal getOverdraftLimit() {
            if (this.overdraftLimit == null) {
                this.overdraftLimit = BigDecimal.ZERO;
            }
            return overdraftLimit;
        }

        public void setOverdraftLimit(BigDecimal overdraftLimit) {
            this.overdraftLimit = overdraftLimit;
        }

        public void setCredit(Long credit) {
            this.credit = credit;
        }

        public String getAccountLevelName() {
            if (StringUtils.isEmpty(accountLevelName)) {
                return "";
            }
            return accountLevelName;
        }

        public void setAccountLevelName(String accountLevelName) {
            this.accountLevelName = accountLevelName;
        }

        public BigDecimal getGiftBalance() {
            if (this.giftBalance == null) {
                this.giftBalance = BigDecimal.ZERO;
            }
            return giftBalance;
        }

        public void setGiftBalance(BigDecimal giftBalance) {
            this.giftBalance = giftBalance;
        }
    }

    class DepositCash implements Comparable<DepositCash> {

        private Long deposit;
        private Long cash;

        public Long getDeposit() {
            return deposit;
        }

        public void setDeposit(Long deposit) {
            this.deposit = deposit;
        }

        public Long getCash() {
            return cash;
        }

        public void setCash(Long cash) {
            this.cash = cash;
        }

        @Override
        public int compareTo(DepositCash o) {
            return (int) (this.deposit - o.getDeposit());
//			return 0;
        }

    }

    public Response batchUpdateInfo(String param) {
        String json = null;
		/*Map<String,String> jsonMap = JsonUtil.parseJSON2Map(param);
		Criteria criteria = new Criteria();
		Iterator<Map.Entry<String, String>> keyIterator = jsonMap.entrySet().iterator();
		while (keyIterator.hasNext()) {
			Map.Entry<String, String> entry = keyIterator.next();
			String key = entry.getKey();
			if (key.indexOf("qm.") == 0 && StringUtils.isNotBlank(entry.getValue())) {
				criteria.put(key.substring(3), entry.getValue());
			}
		}
		if(StringUtil.isNullOrEmpty(criteria.get("increaseBalance")) && StringUtil.isNullOrEmpty(criteria.get("increaseOverdraftLimit")))
			return Response.status(Status.OK).entity(JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,WebUtil.getMessage(WebConstants.MsgCd.INFO_OPERATION_SUCCESS, null)))).build();

		List<User> userList = this.userService.selectByParamsNew(criteria);
		List<Long> accountSids = new ArrayList<Long>();
		for(User u:userList){
			accountSids.add(u.getAccountSid());
		}
		BillingAccount paraAccount = new BillingAccount();
		paraAccount.setIncreaseBalance(StringUtil.isNullOrEmpty(criteria.get("increaseBalance"))?"0":criteria.get("increaseBalance").toString());
		paraAccount.setIncreaseOverdraftLimit(StringUtil.isNullOrEmpty(criteria.get("increaseOverdraftLimit"))?"0":criteria.get("increaseOverdraftLimit").toString());
		paraAccount.setAccountSids(accountSids);
		int result = this.billingAccountService.batchUpdateBalanceOverLimit(paraAccount);
		if(result>0){
			json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,WebUtil.getMessage(WebConstants.MsgCd.INFO_OPERATION_SUCCESS, null)));
		}else{
			json = JsonUtil.toJson(new RestResult(RestResult.Status.SUCCESS,WebUtil.getMessage(WebConstants.MsgCd.INFO_OPERATION_ERROR, null)));
		}*/
        return Response.status(Status.OK).entity(json).build();
    }

    @Override
    public Response displayPaymentRecords(@QueryParam("channel") String channel,
                                          @QueryParam("paymentTime") String paymentTime,
                                          @Context HttpServletRequest request,
                                          @Context HttpServletResponse response) {
        System.err.println(channel);
        System.err.println(paymentTime);
        Criteria example = new Criteria();
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        if (!StringUtil.isNullOrEmpty(channel)) {
            example.put("channel", channel);
        }
        if (!StringUtil.isNullOrEmpty(paymentTime)) {
            example.put("paymentTime", paymentTime);
        }
        example.put("userSid", authUser.getUserSid());
        // 查询符合条件的数据
        List<DepositeVo> depositeVos = this.billingAccountService.displayPaymentRecords(example);
        return Response.status(Status.OK).entity(new RestResult(depositeVos)).build();
    }

    @Override
    public Response displayBillRecords(String serviceSid, String payStatus,
                                       String startTime, String endTime,
                                       @Context HttpServletRequest request) {
        Criteria example = new Criteria();
        AuthUser authUser = RequestContextUtil.getAuthUserInfo(request);
        if (!StringUtil.isNullOrEmpty(serviceSid)) {
            example.put("serviceSid", serviceSid);
            System.err.println(example.get("serviceSid"));
        }
        if (!StringUtil.isNullOrEmpty(payStatus)) {
            example.put("status", payStatus);
            System.err.println(example.get("status"));
        }
        if (!StringUtil.isNullOrEmpty(startTime)) {
            example.put("searchStartTime", startTime + " 00:00:00");
            System.err.println(example.get("searchStartTime"));
        }
        if (!StringUtil.isNullOrEmpty(endTime)) {
            example.put("searchEndTime", endTime + " 23:59:59");
            System.err.println(example.get("searchEndTime"));
        }
        example.put("userSid", authUser.getUserSid());
        List<ServiceBillVo> serviceBills = this.billingAccountService.displayBillRecords(example);
        return Response.status(Status.OK).entity(new RestResult(serviceBills)).build();
    }

    private Map<String, Object> getParamsMap(String params) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        if (!StringUtil.isNullOrEmpty(params)) {
            // 将条件转换为Map
            conditionMap = JsonUtil.fromJson(params, Map.class);
        }
        return conditionMap;
    }
}
