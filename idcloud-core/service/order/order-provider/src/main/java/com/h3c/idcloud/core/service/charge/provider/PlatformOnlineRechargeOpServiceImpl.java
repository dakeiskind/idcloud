package com.h3c.idcloud.core.service.charge.provider;

import java.math.BigDecimal;
import java.util.*;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountMapper;
import com.h3c.idcloud.core.persist.charge.dao.DepositeMapper;
import com.h3c.idcloud.core.persist.marketing.dao.AccountLevelMapper;
import com.h3c.idcloud.core.persist.marketing.dao.DepositPrizeMapper;
import com.h3c.idcloud.core.persist.user.dao.UserMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.charge.Deposite;
import com.h3c.idcloud.core.pojo.dto.marketing.AccountLevel;
import com.h3c.idcloud.core.pojo.dto.marketing.DepositPrize;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.service.charge.api.PlatformOnlineRechargeOpService;
import com.h3c.idcloud.core.service.charge.provider.observable.PaymentObservable;
import com.h3c.idcloud.infrastructure.common.constants.BusinessMessageConstants;
import com.h3c.idcloud.infrastructure.common.constants.SysConfigConstants;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.util.IdGenerator;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 
 * 在线充值平台操作服务类，整个充值流程如下
 * 用户充值--》插入未支付的充值记录--》进入第三方平台支付交易---》交易成功----》更新对应充值记录状态“已支付”---》更新当前用户余额表----》充值流程完成  
 *                                         ---》交易失败，不进行任何处理，平台不提供“未支付”状态的充值记录进行重新支付的操作，页面过滤掉“未支付”的充值记录。
 * @author swq
 *
 */
@Service(version = "1.0.0")
@Component
public class PlatformOnlineRechargeOpServiceImpl implements
		PlatformOnlineRechargeOpService {

	private static final Logger logger = LoggerFactory.getLogger(PlatformOnlineRechargeOpServiceImpl.class);

	/**
	 * 充值记录
	 */
	@Autowired
    private DepositeMapper depositeMapper;

	/**
	 * 用户计费账户
	 */
	@Autowired
	BillingAccountMapper billingAccountMapper;
	
	@Autowired
	DepositPrizeMapper depositPrizeMapper;
	
	@Autowired
	AccountLevelMapper accountLevelMapper;
	
	@Autowired
	UserMapper userMapper;


	@Override
	public Map<String,String> executeDepositeOperationForRest(Map<String, String> payInfoMap) {
		Long depositeSid = Long.parseLong(payInfoMap.get("depositeSid"));
		Deposite deposite = depositeMapper.selectByPrimaryKey(depositeSid);
		if(StringUtil.isNullOrEmpty(deposite))
			throw new RpcException(RpcException.BIZ_EXCEPTION, WebUtil.getMessage(BusinessMessageConstants.PaymentMessage.CAN_NOT_FIND_PAYMENT_RECORD,
					new String[]{String.valueOf(depositeSid)}));
		PaymentObservable payObservable = PaymentObservable.getPayObservable();
		payObservable.addPayQueue(payInfoMap,payObservable);
		Map<String,String> returnMap = new HashMap<String, String>();
		returnMap.put("isSuccess", "true");
		returnMap.put("amount", deposite.getAmountDeposited().toString());
		returnMap.put("gift", deposite.getAmountGift().toString());
		return returnMap;
	}

	@Override
	@Transactional
	public Long saveDepositeRecordInfo(Deposite deposite) {
	    Assert.notNull(deposite.getAccountSid());
	    Assert.notNull(deposite.getUserSid());
	    Assert.notNull(deposite.getChannel());
		//设置流水号
		deposite.setFlowId(String.valueOf(IdGenerator.createGenerator().generate()));
		//设置时间
		deposite.setTime(new Date());
		//未支付
		deposite.setPayStatus(WebConstants.RECHARGE_STATUS.NO_PAY);
		deposite.setCurrency(WebConstants.CURRENCY.RMB);
		
		BigDecimal bal = deposite.getAmountDeposited();
		List<DepositPrize> dpList = this.depositPrizeMapper.selectByParams(null);
		BigDecimal bd = new BigDecimal(0);
		if(dpList==null || dpList.size()==0){
			logger.info("there is no depositPrize.");
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
							logger.info("depositPrize "+dc.getCash());
							break label;
						}
					}

				}else{
					continue;
				}

			}
		}
		BigDecimal chargeGift = BigDecimal.ZERO;
		try{
			//充值送
			chargeGift = this.getChargeGift(bal);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("saveDepositeRecordInfo.getChargeGift error"+e.getMessage());
		}
		bd = bd.add(chargeGift);
		deposite.setAmountGift(bd);
		int r = this.depositeMapper.insertSelective(deposite);
		if(r<0)
			throw new RpcException(RpcException.BIZ_EXCEPTION,"deposite record failure!");
		// TODO 写入充值记录
		return deposite.getDepositeSid();
	}
	
	private BigDecimal getChargeGift(BigDecimal bal){
		BigDecimal bd = BigDecimal.ZERO;
		//充值送
		logger.info("PlatformOnlineRechargeOpServiceImpl.getChargeGift amount="+bal.toString());
		String payGiftCoeff = PropertiesUtil.getProperty(SysConfigConstants.CHARGE_GIFT_CONFIG);
        String charge = "\\[(\\*|([1-9]{1}[0-9]*,)*([1-9]{1}[0-9]*)*)\\]#\\[(\\*|([1-9]{1}[0-9]*,)*([1-9]{1}[0-9]*)*)\\]";
        logger.info("PlatformOnlineRechargeOpServiceImpl.getChargeGift amount="+bal.toString()+" config="+charge);
		if(!StringUtil.isNullOrEmpty(payGiftCoeff) && payGiftCoeff.matches(charge)){
			logger.info("PlatformOnlineRechargeOpServiceImpl.getChargeGift amount="+bal.toString()+" config="+charge+" matched");
			String matchAll = "[*]";
			String[] giftCoeS = payGiftCoeff.split("#");
			boolean matched = false;
			String[] gamount = giftCoeS[0].replaceAll("\\[", "").replaceAll("\\]", "").split(",");
			String[] coeff = giftCoeS[1].replaceAll("\\[", "").replaceAll("\\]", "").split(",");
			int index = 0;
			String matchedRmb = "";
			for(int i=0;i<gamount.length;i++){
				String rmb = gamount[i];
				if(!StringUtil.isNullOrEmpty(rmb) && (rmb.equals("*") || (StringUtil.isNumericS(rmb) && Integer.parseInt(rmb) == Integer.parseInt(bal.toString())))){
					matched = true;
					index = i;
					matchedRmb = rmb;
					break;
				}
			}
			if(matched && coeff.length>0){
				String gift = "";
				//[*] [*] 充多少送多少
				if(giftCoeS[0].equals(matchAll) && giftCoeS[1].equals(matchAll)){
					gift = bal.toString();
				}else if(!giftCoeS[0].equals(matchAll) && giftCoeS[1].equals(matchAll)){
					//[100,200] [*] p赠送的金额等于匹配的金额
					gift = matchedRmb;
				}else{
					if(index < coeff.length)
						gift = coeff[index];
					else
						gift = coeff[coeff.length-1];
				}
				if(!StringUtil.isNullOrEmpty(gift) &&StringUtil.isNumericS(gift)){
					bd = bd.add(new BigDecimal(gift));
				}
				
					
			}
			
		}
		logger.info("PlatformOnlineRechargeOpServiceImpl.getChargeGift amount="+bal.toString()+" gift amount="+bd.toString());
		return bd;
	}

	@Override
	@Transactional
	public Map<String,String> executeDepositeOperation(Long depositeSid,String thirdPaymentNo) {
		logger.info("executeDepositeOperation start--depositeSid="+depositeSid+"--thirdPaymentNo="+thirdPaymentNo);
		Map<String,String> returnMap = new HashMap<String, String>();
		returnMap.put("isSuccess", "false");
		returnMap.put("amount", "0");
		returnMap.put("gift", "0");
		//更新充值记录状态
		Deposite deposite = depositeMapper.selectByPrimaryKey(depositeSid);
		if(StringUtil.isNullOrEmpty(deposite))
			throw new RpcException(RpcException.BIZ_EXCEPTION,"can't find Deposite info by depositeSid="+depositeSid);
		//检测充值记录支付状态是否已经被处理过了
		if(deposite.getPayStatus().equals(WebConstants.RECHARGE_STATUS.PAYED)){
			logger.info("this deposite record info had payed! "+" DepositeSid "+deposite.getDepositeSid());
			returnMap.put("isSuccess", "true");
			returnMap.put("amount", StringUtil.isNullOrEmpty(deposite.getAmountDeposited())?"0":deposite.getAmountDeposited().toString());
			returnMap.put("gift", StringUtil.isNullOrEmpty(deposite.getAmountGift())?"0":deposite.getAmountGift().toString());
			return returnMap;
		}
		deposite.setThirdPaymentNo(thirdPaymentNo);
		deposite.setPayStatus(WebConstants.RECHARGE_STATUS.PAYED);
		int depr = depositeMapper.updateByPrimaryKeySelective(deposite);
		if(depr<0)
			throw new RpcException("update deposite failure!");
		BillingAccount account = this.billingAccountMapper.selectByPrimaryKey(deposite.getAccountSid());
		BigDecimal beforeBalance = account.getBalance();
		BigDecimal beforeGiftBalance = account.getGiftBalance();
		if(StringUtil.isNullOrEmpty(account))
			return returnMap;
		//充值奖励优惠
		BigDecimal bd = BigDecimal.ZERO;
		if(!StringUtil.isNullOrEmpty(deposite.getAmountGift()))
			bd = deposite.getAmountGift();
				
		//当账户存在负余额的时候首先进行账户抵扣
		if(!StringUtil.isNullOrEmpty(account.getGiftBalance()) && account.getGiftBalance().doubleValue()<0){
			BigDecimal newGiftBalance = account.getGiftBalance().add(bd);
			//仍然处于负状态
			if(newGiftBalance.doubleValue() < 0){
				BigDecimal newAccountBalance = deposite.getAmountDeposited().add(newGiftBalance);
				if(newAccountBalance.doubleValue() <0 ){
					account.setGiftBalance(newAccountBalance);
				}else{
					account.setGiftBalance(BigDecimal.ZERO);
					account.setBalance(StringUtil.isNullOrEmpty(account.getBalance())?BigDecimal.ZERO.add(newAccountBalance):account.getBalance().add(newAccountBalance));
				}
			}else{
				account.setGiftBalance(newGiftBalance);
				account.setBalance(StringUtil.isNullOrEmpty(account.getBalance())?BigDecimal.ZERO.add(deposite.getAmountDeposited()):account.getBalance().add(deposite.getAmountDeposited()));
			}
		}else{
			//设置当前用户余额=当前用户余额+充值金额
			account.setBalance(StringUtil.isNullOrEmpty(account.getBalance())?BigDecimal.ZERO.add(deposite.getAmountDeposited()):account.getBalance().add(deposite.getAmountDeposited()));
			//增加赠送金额
			account.setGiftBalance(StringUtil.isNullOrEmpty(account.getGiftBalance())?BigDecimal.ZERO.add(bd):account.getGiftBalance().add(bd));
		}
		BigDecimal bal = deposite.getAmountDeposited();
		// 增加积分
		account.setUsableCredit(StringUtil.isNullOrEmpty(account.getUsableCredit())?bal.longValue():account.getUsableCredit() + bal.longValue()); 
					
		returnMap.put("amount", deposite.getAmountDeposited().toString());
		returnMap.put("gift", bd.toString());
		//如果达到则修改用户等级
		//设置用户等级
		List<AccountLevel> alList = this.accountLevelMapper
				.selectByParams(null);
		if (alList != null && alList.size() > 0) {
			Collections.sort(alList, (a1,a2)->{return a1.getLevelLimit()>a2.getLevelLimit()?1:0;});
			Long userCredit = account.getUsableCredit();
			AccountLevel chosenOne = chooseAccountLevel(alList, userCredit);
			if (chosenOne != null) {
				account.setAccountLevelSid(chosenOne.getLevelSid());
				account.setAccountLevelName(chosenOne.getLevelName());
			}
		}
		//更新用户账户信息
		int i = this.billingAccountMapper.updateByPrimaryKeySelective(account);
		if(i<0)
			throw new RpcException(RpcException.BIZ_EXCEPTION,"update account failure!"+" accountsid "+account.getAccountSid());
		// TODO写入用户痕迹信息
		final User user = userMapper.selectByPrimaryKey(deposite.getUserSid());
//		traceService.insertTraceLog(user, "我的财务", "用户使用"+(deposite.getChannel().equals(WebConstants.CHARGE_CHANNEL.ALIPAY)?"支付宝":"其他方式")+"完成充值  "+
//		"充值金额  "+deposite.getAmountDeposited().setScale(2,BigDecimal.ROUND_HALF_UP)+
//		" 奖励金额 "+bd+" 积分信息 "+account.getUsableCredit()+" 原账户余额 "+beforeBalance+" 充值后余额 "+account.getAccountBalance()+" 原赠送余额 "+beforeGiftBalance+" 充值后赠送余额 "+account.getAccountGiftBalance());
		returnMap.put("isSuccess", "true");
		
		//开新匿名线程发送消息
		senNotification(user,deposite);

		logger.info("executeDepositeOperation end--depositeSid="+depositeSid+"--thirdPaymentNo="+thirdPaymentNo);
		//平台支付操作完成
		return returnMap;
	}
	
	/**
	 * 开新匿名线程发送消息
	 * @param user
	 * @param deposite
	 */
	private void senNotification(final User user, final Deposite deposite){
		// TODO 发送充值消息
	}
	
	
	private AccountLevel chooseAccountLevel(List<AccountLevel> alList, Long userCredit){
		if(null == userCredit) userCredit = 0l;
		AccountLevel chosenOne = null;
		for(int i=0;i<alList.size();i++){
			AccountLevel al = alList.get(i);
			if(userCredit >= al.getLevelLimit()){
				chosenOne = al;
			}
		}
		return chosenOne;
	}
	
	class DepositCash implements Comparable<DepositCash>{
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
		}

	}

}
