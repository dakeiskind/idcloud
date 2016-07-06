/**
 * 
 */
package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chxiaoqi
 *
 */
public class BillingRecord implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8598518254446844065L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 供应商
	 */
	private String provider;
	
	/**
	 * 请求id
	 */
	private String requestId;
	
	/**
	 * 支付状态
	 */
	private String status;
	
	/**
	 * 第三方交易流水号
	 */
	private String flowNo;
	
	/**
	 * 支付用户
	 */
	private Long accountSid;


	private String customerName;
    
    /**
     * 充值渠道
     */
    private String channel;
    
    /**
     * 充值金额，单位元，保留两位
     */
    private BigDecimal amount;
    
    private String createdBy;

    private Long version;
    
    /**
     * 记录创建时间
     */
    private Date createdDt;

    

    private String updatedBy;

    private String channelName;

    private String statusName;

    /**
     * 完成支付时间
     */
    private Date updatedDt;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(Long accountSid) {
		this.accountSid = accountSid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
