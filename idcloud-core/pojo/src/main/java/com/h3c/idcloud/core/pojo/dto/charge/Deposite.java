package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Deposite implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 充值记录SID
	 */
	private Long depositeSid;

	/**
	 * 第三方支付平台交易号
	 */
	private String thirdPaymentNo;
	
	/**
	 * 是否自动开始
	 */
	private String isAutoStart;

	/**
	 * 充值支付状态
	 */
	private String payStatus;

	
	public String getIsAutoStart() {
		return isAutoStart;
	}

	public void setIsAutoStart(String isAutoStart) {
		this.isAutoStart = isAutoStart;
	}

	private String payStatusLable;

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getThirdPaymentNo() {
		return thirdPaymentNo;
	}

	public void setThirdPaymentNo(String thirdPaymentNo) {
		this.thirdPaymentNo = thirdPaymentNo;
	}

	private String accountName;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 账户SID
	 */
	private Long accountSid;

	/**
	 * 充值人
	 */
	private Long userSid;
	private String userName;

	public Long getUserSid() {
		return userSid;
	}

	public void setUserSid(Long userSid) {
		this.userSid = userSid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 充值时间
	 */
	private Date time;

	/**
	 * 充值渠道
	 */
	private String channel;
	private String channelName;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * 财务负责人
	 */
	private String fpic;

	/**
	 * 充值金额
	 */
	private BigDecimal amountDeposited;

	/**
	 * 兑换虚拟币值
	 */
	private String currency;

	/**
	 * 交易流水号
	 */
	private String flowId;

	/**
	 * 销售（指向系统用户）
	 */
	private Long sales;

	/**
	 * 实收金额
	 */
	private BigDecimal amountReceived;

	/**
	 * 充值送
	 */
	private BigDecimal amountGift;

	/**
	 * 优惠券代码
	 */
	private String couponCode;

	public BigDecimal getAmountGift() {
		return amountGift;
	}

	public void setAmountGift(BigDecimal amountGift) {
		this.amountGift = amountGift;
	}

	/**
	 * @return 充值记录SID
	 */
	public Long getDepositeSid() {
		return depositeSid;
	}

	/**
	 * @param depositeSid
	 *            充值记录SID
	 */
	public void setDepositeSid(Long depositeSid) {
		this.depositeSid = depositeSid;
	}

	/**
	 * @return 账户SID
	 */
	public Long getAccountSid() {
		return accountSid;
	}

	/**
	 * @param accountSid
	 *            账户SID
	 */
	public void setAccountSid(Long accountSid) {
		this.accountSid = accountSid;
	}

	/**
	 * @return 充值时间
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * 获取格式化后的充值时间 YYYYMMDDhhmmss
	 * 
	 * @return
	 */
	public String getDepositeTime() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		if (null != time) {
			return df.format(time);
		}
		return null;
	}

	/**
	 * @param time
	 *            充值时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return 充值渠道
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            充值渠道
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return 财务负责人
	 */
	public String getFpic() {
		return fpic;
	}

	/**
	 * @param fpic
	 *            财务负责人
	 */
	public void setFpic(String fpic) {
		this.fpic = fpic;
	}

	/**
	 * @return 充值金额
	 */
	public BigDecimal getAmountDeposited() {
		return amountDeposited;
	}

	/**
	 * @param amountDeposited
	 *            充值金额
	 */
	public void setAmountDeposited(BigDecimal amountDeposited) {
		this.amountDeposited = amountDeposited;
	}

	/**
	 * @return 兑换虚拟币值
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            兑换虚拟币值
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return 交易流水号
	 */
	public String getFlowId() {
		return flowId;
	}

	/**
	 * @param flowId
	 *            交易流水号
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	/**
	 * @return 销售（指向系统用户）
	 */
	public Long getSales() {
		return sales;
	}

	/**
	 * @param sales
	 *            销售（指向系统用户）
	 */
	public void setSales(Long sales) {
		this.sales = sales;
	}

	/**
	 * @return 实收金额
	 */
	public BigDecimal getAmountReceived() {
		return amountReceived;
	}

	/**
	 * @param amountReceived
	 *            实收金额
	 */
	public void setAmountReceived(BigDecimal amountReceived) {
		this.amountReceived = amountReceived;
	}

	/**
	 * @return 优惠券代码
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param couponCode
	 *            优惠券代码
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getPayStatusLable() {
		return payStatusLable;
	}

	public void setPayStatusLable(String payStatusLable) {
		this.payStatusLable = payStatusLable;
	}

}