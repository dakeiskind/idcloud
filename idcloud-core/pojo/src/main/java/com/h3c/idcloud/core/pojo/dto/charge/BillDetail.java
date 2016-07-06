package com.h3c.idcloud.core.pojo.dto.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long billDetailSid;

    private Long billSid;

    private String serviceSid;

    private String serviceInstanceSid;
    
    private String billTimeName;

    private String billingType;

    private Float longUse;
    
    private String usageVolume;

    private BigDecimal money;

    private String number;

    private String status;
    
    private String statusName;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Long version;
    
    private String serviceName;
    
    private String ownerId;
    
    /**
     * 计费开始时间
     */
    private String billingStartTime;
    
    /**
     * 计费结束时间
     */
    private String billingEndTime;
    
    /**
     * 租户ID
     */
    private String tanentId;
    
    private String billingTypeName;
    private String serviceInstanceName;

    public Long getBillDetailSid() {
        return billDetailSid;
    }

    public void setBillDetailSid(Long billDetailSid) {
        this.billDetailSid = billDetailSid;
    }

    public Long getBillSid() {
        return billSid;
    }

    public void setBillSid(Long billSid) {
        this.billSid = billSid;
    }

    public String getServiceSid() {
        return serviceSid;
    }

    public void setServiceSid(String serviceSid) {
        this.serviceSid = serviceSid;
    }

    public String getServiceInstanceSid() {
        return serviceInstanceSid;
    }

    public void setServiceInstanceSid(String serviceInstanceSid) {
        this.serviceInstanceSid = serviceInstanceSid;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    /**
     * @return ʹ
     */
    public Float getLongUse() {
        return longUse;
    }

    /**
     * @param longUse 
	 *            ʹ
     */
    public void setLongUse(Float longUse) {
        this.longUse = longUse;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return ״̬
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            ״̬
     */
    public void setStatus(String status) {
        this.status = status;
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

	/**
	 * @return the tanentId
	 */
	public String getTanentId() {
		return tanentId;
	}

	/**
	 * @param tanentId the tanentId to set
	 */
	public void setTanentId(String tanentId) {
		this.tanentId = tanentId;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getBillingStartTime() {
		return billingStartTime;
	}

	public void setBillingStartTime(String billingStartTime) {
		this.billingStartTime = billingStartTime;
	}

	public String getBillingEndTime() {
		return billingEndTime;
	}

	public void setBillingEndTime(String billingEndTime) {
		this.billingEndTime = billingEndTime;
	}

	public String getBillingTypeName() {
		return billingTypeName;
	}

	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}

	public String getServiceInstanceName() {
		return serviceInstanceName;
	}

	public void setServiceInstanceName(String serviceInstanceName) {
		this.serviceInstanceName = serviceInstanceName;
	}

	public String getBillTimeName() {
		return billTimeName;
	}

	public void setBillTimeName(String billTimeName) {
		this.billTimeName = billTimeName;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getUsageVolume() {
		return usageVolume;
	}

	public void setUsageVolume(String usageVolume) {
		this.usageVolume = usageVolume;
	}

	
}