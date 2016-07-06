package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class License implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 序列号
     */
    private String licenseSerialno;
    
    /**
     * 最大主机数量
     */
    private String maxCount;
    
    /**
     * 到期时间
     */
    private Date expiryDate;

    public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getLicenseSerialno() {
        return licenseSerialno;
    }

    public void setLicenseSerialno(String licenseSerialno) {
        this.licenseSerialno = licenseSerialno;
    }
}