package com.h3c.idcloud.core.pojo.vo.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by swq on 4/7/2016.
 */
public class DepositeVo implements Serializable {

    /**
     * 交易流水号
     */
    private String flowId;

    /**
     * 支付渠道
     */
    private String channelName;

    /**
     * 充值金额
     */
    private BigDecimal amountDeposited;


    /**
     * 实收金额(实际支付金额)
     */
    private BigDecimal amountReceived;

    /**
     * 充值送
     */
    private BigDecimal amountGift;

    /**
     * 第三方支付平台交易号
     */
    private String thirdPaymentNo;

    /**
     * 充值人
     */
    private String userName;

    /**
     * 所属账户名称
     */
    private String accountName;

    /**
     * 充值时间
     */
    private String time;

    /**
     * 支付状态名称
     */
    private String payStatusName;

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getAmountDeposited() {
        return amountDeposited;
    }

    public void setAmountDeposited(BigDecimal amountDeposited) {
        this.amountDeposited = amountDeposited;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    public BigDecimal getAmountGift() {
        return amountGift;
    }

    public void setAmountGift(BigDecimal amountGift) {
        this.amountGift = amountGift;
    }

    public String getThirdPaymentNo() {
        return thirdPaymentNo;
    }

    public void setThirdPaymentNo(String thirdPaymentNo) {
        this.thirdPaymentNo = thirdPaymentNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
