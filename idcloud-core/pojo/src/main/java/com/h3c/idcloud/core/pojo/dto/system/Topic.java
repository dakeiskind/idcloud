package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class Topic implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主题ID
     */
    private Long topicSid;

    /**
     * 主题名称
     */
    private String topicName;

    /**
     * 主题URL
     */
    private String topicUrl;

    /**
     * 是否显示 0:否 1:是
     */
    private Integer displayFlag;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;
    
    private int sortRank;

    /**
     * @return 主题ID
     */
    public Long getTopicSid() {
        return topicSid;
    }

    /**
     * @param topicSid 
	 *            主题ID
     */
    public void setTopicSid(Long topicSid) {
        this.topicSid = topicSid;
    }

    /**
     * @return 主题名称
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName 
	 *            主题名称
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * @return 主题URL
     */
    public String getTopicUrl() {
        return topicUrl;
    }

    /**
     * @param topicUrl 
	 *            主题URL
     */
    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl;
    }

    /**
     * @return 是否显示 0:否 1:是
     */
    public Integer getDisplayFlag() {
        return displayFlag;
    }

    /**
     * @param displayFlag 
	 *            是否显示 0:否 1:是
     */
    public void setDisplayFlag(Integer displayFlag) {
        this.displayFlag = displayFlag;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 
	 *            创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt 
	 *            创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy 
	 *            更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt 
	 *            更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * @return 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version 
	 *            版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

	public int getSortRank() {
		return sortRank;
	}

	public void setSortRank(int sortRank) {
		this.sortRank = sortRank;
	}
}