package com.h3c.idcloud.core.service.charge.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountCdrMapper;
import com.h3c.idcloud.core.persist.charge.dao.BillingAccountMapper;
import com.h3c.idcloud.core.persist.charge.dao.DepositeMapper;
import com.h3c.idcloud.core.persist.charge.dao.ServiceBillMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccount;
import com.h3c.idcloud.core.pojo.dto.charge.BillingAccountCdr;
import com.h3c.idcloud.core.pojo.dto.charge.Deposite;
import com.h3c.idcloud.core.pojo.dto.charge.ServiceBill;
import com.h3c.idcloud.core.pojo.vo.charge.DepositeVo;
import com.h3c.idcloud.core.pojo.vo.charge.ServiceBillVo;
import com.h3c.idcloud.core.service.charge.api.BillingAccountService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service(version = "1.0.0")
@Component
public class BillingAccountServiceImpl implements BillingAccountService {

	@Autowired
	private BillingAccountMapper billingAccountMapper;

	@Autowired
	private BillingAccountCdrMapper billingAccountCdrMapper;

	@Autowired
	ServiceBillMapper serviceBillMapper;

	@Reference(version = "1.0.0")
	SidService sidService;

	@Autowired
	private DepositeMapper depositeMapper;

	private static final Logger logger = LoggerFactory.getLogger(BillingAccountServiceImpl.class);


	@Override
	public int countByParams(Criteria example) {
		int count = this.billingAccountMapper.countByParams(example);
		logger.debug("count: {}", count);
		return count;
	}

	@Override
	public BillingAccount selectByPrimaryKey(Long accountSid) {
		return this.billingAccountMapper.selectByPrimaryKey(accountSid);
	}

	@Override
	public List<BillingAccount> selectByParams(Criteria example) {
		return this.billingAccountMapper.selectByParams(example);
	}

	@Override
	public int deleteByPrimaryKey(Long accountSid) {
		return this.billingAccountMapper.deleteByPrimaryKey(accountSid);
	}

	@Override
	public int updateByPrimaryKeySelective(BillingAccount record) {
		return this.billingAccountMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BillingAccount record) {
		return this.billingAccountMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByParams(Criteria example) {
		return this.billingAccountMapper.deleteByParams(example);
	}

	@Override
	public int updateByParamsSelective(BillingAccount record, Criteria example) {
		return this.billingAccountMapper.updateByParamsSelective(record, example.getCondition());
	}

	@Override
	public int updateByParams(BillingAccount record, Criteria example) {
		return this.billingAccountMapper.updateByParams(record, example.getCondition());
	}

	@Override
	public int insert(BillingAccount record) {
		return this.billingAccountMapper.insert(record);
	}

	@Override
	public int insertSelective(BillingAccount record) {
		return this.billingAccountMapper.insertSelective(record);
	}

	@Override
	public BillingAccount selectByUserId(Long userId) {
		return this.billingAccountMapper.selectByUserId(userId);
	}

	@Override
	public boolean checkAccountBalance(BigDecimal amount,Long userId) {
		BillingAccount billingAccount = billingAccountMapper.selectByUserId(userId);
		if(StringUtil.isNullOrEmpty(billingAccount))
			return false;
		return !(StringUtil.isNullOrEmpty(billingAccount.getBalance()) || billingAccount.getBalance().subtract(amount).doubleValue() < 0);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateAccount(Long userId, BigDecimal amount, String type) throws RpcException {
		if (userId == null) throw new RpcException(RpcException.BIZ_EXCEPTION, "AccountSid should not be null!");
		if (amount == null) throw new RpcException("amount should not be null!");
		if (amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new RpcException(RpcException.BIZ_EXCEPTION, "amount should be greater than 0!");

		BillingAccount ba = this.billingAccountMapper.selectByUserId(userId);
		if (ba == null) throw new RpcException(RpcException.BIZ_EXCEPTION, "Account not exsits!");

		BigDecimal bd = ba.getBalance() == null ? BigDecimal.ZERO : ba.getBalance();
		BillingAccountCdr baCdr = new BillingAccountCdr();
		ba.setBalance(bd.subtract(amount));
		//插入扣款交易记录
		if(AuthUtil.getAuthUser() != null && AuthUtil.getAuthUser().getAccount() != null) {
			baCdr.setOpUser(AuthUtil.getAuthUser().getAccount());
		} else {
			baCdr.setOpUser("admin");
		}
		baCdr.setBalance(bd.subtract(amount));
		baCdr.setOpAmount(amount);
		baCdr.setOpDate(new Date());
		baCdr.setOpType(type);
		baCdr.setBillingAccountSid(ba.getAccountSid());
		baCdr.setTradeNo(sidService.getMaxSid(WebConstants.SidCategory.ORDER));
		baCdr.setStatus(WebConstants.billingAccountCdrStatus.SUCCESS);
		ba.setUpdatedDt(new Date());
		WebUtil.prepareInsertParams(baCdr);
		int i = this.billingAccountMapper.updateByPrimaryKeySelective(ba);
		if (i > 0 && baCdr != null) {
			this.billingAccountCdrMapper.insert(baCdr);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean updateAccount(Long accountSid, BigDecimal amount, boolean flag)
			throws RpcException {

		if (accountSid == null) throw new RpcException(RpcException.BIZ_EXCEPTION, "AccountSid should not be null!");
		if (amount == null) throw new RpcException("amount should not be null!");
		if (amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new RpcException(RpcException.BIZ_EXCEPTION, "amount should be greater than 0!");

		BillingAccount ba = this.billingAccountMapper.selectByPrimaryKeyForUpdate(accountSid);
		if (ba == null) throw new RpcException(RpcException.BIZ_EXCEPTION, "Account not exsits!");

		BigDecimal bd = ba.getBalance() == null ? BigDecimal.ZERO : ba.getBalance();
		BillingAccountCdr baCdr = null;
		if (flag) {
			ba.setBalance(bd.subtract(amount));
			//插入扣款交易记录
			baCdr = new BillingAccountCdr();
//			baCdr.setOpType(WebConstants.billingAccountCdrOpType.CUT_PAYMENT);
			baCdr.setBalance(bd.subtract(amount));
			baCdr.setOpAmount(amount);
			baCdr.setOpDate(new Date());
			baCdr.setOpType("03");
			baCdr.setBillingAccountSid(ba.getAccountSid());
//			if(AuthUtil.getAuthUser() != null && AuthUtil.getAuthUser().getAccount() != null) {
//				baCdr.setOpUser(AuthUtil.getAuthUser().getAccount());
//			} else {
//				baCdr.setOpUser("admin");
//			}
			baCdr.setTradeNo(sidService.getMaxSid(WebConstants.SidCategory.ORDER));
			baCdr.setStatus(WebConstants.billingAccountCdrStatus.SUCCESS);
//			WebUtil.prepareInsertParams(baCdr);
		} else {
			ba.setBalance(bd.add(amount));

			baCdr = new BillingAccountCdr();
//			baCdr.setOpType(WebConstants.billingAccountCdrOpType.CUT_PAYMENT);
			baCdr.setBalance(bd.subtract(amount));
			baCdr.setOpAmount(amount);
			baCdr.setOpDate(new Date());
			baCdr.setOpType("02");
			baCdr.setBillingAccountSid(ba.getAccountSid());
//			if(AuthUtil.getAuthUser() != null && AuthUtil.getAuthUser().getAccount() != null) {
//				baCdr.setOpUser(AuthUtil.getAuthUser().getAccount());
//			} else {
//				baCdr.setOpUser("admin");
//			}
			baCdr.setTradeNo(sidService.getMaxSid(WebConstants.SidCategory.ORDER));
			baCdr.setStatus(WebConstants.billingAccountCdrStatus.SUCCESS);
//			WebUtil.prepareInsertParams(baCdr);
		}
		ba.setUpdatedDt(new Date());
		int i = this.billingAccountMapper.updateByPrimaryKeySelective(ba);
		if (i > 0 && baCdr != null) {
			this.billingAccountCdrMapper.insert(baCdr);
		}
		return true;
	}

	@Override
	public List<DepositeVo> displayPaymentRecords(Criteria criteria) {
		return depositeMapper.selectUserDeposites(criteria);
	}

	@Override
	public List<ServiceBillVo> displayBillRecords(Criteria criteria) {
		return serviceBillMapper.selectUserServiceBillVos(criteria);
	}

	@Override
	public List<ServiceBill> selectStatisticsAmountInfo(Criteria criteria) {
		return serviceBillMapper.selectStatisticsAmountInfo(criteria);
	}

	@Override
	public Map<String, Object> getUserCenterLineChartData(Long userSid,String timeLineFlag) {
		Calendar time = Calendar.getInstance();
		String timeLine;
		boolean isCurrent = false;
		if(timeLineFlag.equals("currentMonth")){
			timeLine = StringUtil.dateFormat(time.getTime(),StringUtil.DF_YM);
			isCurrent = true;
		}else{
			time.add(Calendar.MONTH,-1);
			timeLine = StringUtil.dateFormat(time.getTime(),StringUtil.DF_YM);
		}
		Criteria criteria = new Criteria();
		criteria.put("userSid",userSid);
		criteria.put("timeLine",timeLine);
		List<ServiceBill> serviceBills = serviceBillMapper.selectStatisticsByMonthTime(criteria);
		List<String> monthDays = getMonthDay(isCurrent);
		List<BigDecimal> monthData = getMonthData(monthDays,serviceBills);
		Map<String,Object> lineChartData = new HashMap<>();
		lineChartData.put("xAxisData",monthDays);
		lineChartData.put("seriesData",monthData);
		return lineChartData;
	}

	private List<String> getMonthDay(boolean isCurrent){
		List<String> days = new LinkedList<>();
		Calendar time = Calendar.getInstance();
		if(!isCurrent)
			time.add(Calendar.MONTH,-1);
		int startDay = time.getActualMinimum(Calendar.DAY_OF_MONTH);
		int endDay = time.getActualMaximum(Calendar.DAY_OF_MONTH);
		int month = time.get(Calendar.MONTH)+1;
		String showMonth = month<10?("0"+month):month+"";
		for(int day = startDay; day<=endDay;day++){
			days.add(getZeroFormatDate(showMonth,day));
		}
		return days;
	}

	private List<BigDecimal> getMonthData(List<String> monthDays,List<ServiceBill> serviceBills){
		List<BigDecimal> monthData = new LinkedList<>();
		monthDays.forEach(day ->{
			BigDecimal dayAmount = BigDecimal.ONE.ZERO;
			for(ServiceBill sb:serviceBills){
				if(day.equals(sb.getStartTimeLabel())){
					dayAmount = dayAmount.add(sb.getDayTotalAmount());
					break;
				}
			}
			monthData.add(dayAmount);

		});
		return monthData;
	}

	private String getZeroFormatDate(String month,int day){
		String showDay = day<10?("0"+day):day+"";
		return month+"-"+showDay;
	}
}
