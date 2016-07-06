package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.util.Date;

public class OccupyResourceStat implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long occupyResourceSid;

    /**
     * 业务SID
     */
    private String bizSid;

    /**
     * 业务名称
     */
    private String bizName;

    /**
     * 统计时间
     */
    private Date statDate;

    /**
     * 1. CPU 2. MEMORY 3.存储
     */
    private String resType;

    /**
     * 业务资源占用量
     */
    private Double resOccupyValue;

    /**
     * 业务资源可用总量
     */
    private Double resTotalValue;
    
    private Double resUsage;

    public Long getOccupyResourceSid() {
        return occupyResourceSid;
    }

    public void setOccupyResourceSid(Long occupyResourceSid) {
        this.occupyResourceSid = occupyResourceSid;
    }

    /**
     * @return 业务SID
     */
    public String getBizSid() {
        return bizSid;
    }

    /**
     * @param bizSid 
	 *            业务SID
     */
    public void setBizSid(String bizSid) {
        this.bizSid = bizSid;
    }

    /**
     * @return 业务名称
     */
    public String getBizName() {
        return bizName;
    }

    /**
     * @param bizName 
	 *            业务名称
     */
    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    /**
     * @return 统计时间
     */
    public Date getStatDate() {
        return statDate;
    }

    /**
     * @param statDate 
	 *            统计时间
     */
    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    /**
     * @return 1. CPU 2. MEMORY 3.存储
     */
    public String getResType() {
        return resType;
    }

    /**
     * @param resType 
	 *            1. CPU 2. MEMORY 3.存储
     */
    public void setResType(String resType) {
        this.resType = resType;
    }

    /**
     * @return 业务资源占用量
     */
    public Double getResOccupyValue() {
        return resOccupyValue;
    }

    /**
     * @param resOccupyValue 
	 *            业务资源占用量
     */
    public void setResOccupyValue(Double resOccupyValue) {
        this.resOccupyValue = resOccupyValue;
    }

    /**
     * @return 业务资源可用总量
     */
    public Double getResTotalValue() {
        return resTotalValue;
    }

    /**
     * @param resTotalValue 
	 *            业务资源可用总量
     */
    public void setResTotalValue(Double resTotalValue) {
        this.resTotalValue = resTotalValue;
    }

	public Double getResUsage() {
		return resUsage;
	}

	public void setResUsage(Double resUsage) {
		this.resUsage = resUsage;
	}
}