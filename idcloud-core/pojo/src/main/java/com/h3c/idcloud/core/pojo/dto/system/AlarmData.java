package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class AlarmData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long alarmDataSid;

    private Date alarmTime;

    private String alarmLevel;
    
    private String alarmLevelName;


    private String alarmType;
    
    private String alarmTypeName;
    
    /**
     * 告警Id
     */
    private String alarmId;
    
    /**
     * 告警规则SID
     */
    private String alarmRuleSid;

    /**
     * 告警目标
     */
    private String alarmTarget;
    
    private String alarmTargetName;
    
    /**
     * 告警来源
     */
    private String alarmSource;

    private String title;

    private String content;

    private String status;
    
    private String statusName;

    private String confirmUser;

    private Date confirmTime;

    private String confirmContent;

    private String createdBy;

    private Date createdDt;
    
    private String alarmTimeFromDate;
    
    private String alarmTimeToDate;

    private String updatedBy;

    private Date updatedDt;

    private Long version;

    /**
	  * 数据中心管理查询类型
	  */
	 private String searchType;
	 
	 /**
	  * 数据中心管理查询Sid
	  */
	 private String searchSid;
	 
	 private Integer alarmCount;
	 
    public Integer getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(Integer alarmCount) {
		this.alarmCount = alarmCount;
	}

	public Long getAlarmDataSid() {
        return alarmDataSid;
    }

    public void setAlarmDataSid(Long alarmDataSid) {
        this.alarmDataSid = alarmDataSid;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    /**
     * @return Ŀ
     */
    public String getAlarmTarget() {
        return alarmTarget;
    }

    /**
     * @param alarmTarget 
	 *            Ŀ
     */
    public void setAlarmTarget(String alarmTarget) {
        this.alarmTarget = alarmTarget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
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
	 * @return the alarmLevelName
	 */
	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	/**
	 * @param alarmLevelName the alarmLevelName to set
	 */
	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	/**
	 * @return the alarmTypeName
	 */
	public String getAlarmTypeName() {
		return alarmTypeName;
	}

	/**
	 * @param alarmTypeName the alarmTypeName to set
	 */
	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	/**
	 * @return the confirmContent
	 */
	public String getConfirmContent() {
		return confirmContent;
	}

	/**
	 * @param confirmContent the confirmContent to set
	 */
	public void setConfirmContent(String confirmContent) {
		this.confirmContent = confirmContent;
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

	public String getAlarmTimeFromDate() {
		return alarmTimeFromDate;
	}

	public void setAlarmTimeFromDate(String alarmTimeFromDate) {
		this.alarmTimeFromDate = alarmTimeFromDate;
	}

	public String getAlarmTimeToDate() {
		return alarmTimeToDate;
	}

	public void setAlarmTimeToDate(String alarmTimeToDate) {
		this.alarmTimeToDate = alarmTimeToDate;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchSid() {
		return searchSid;
	}

	public void setSearchSid(String searchSid) {
		this.searchSid = searchSid;
	}

	/**
	 * @return the alarmSource
	 */
	public String getAlarmSource() {
		return alarmSource;
	}

	/**
	 * @param alarmSource the alarmSource to set
	 */
	public void setAlarmSource(String alarmSource) {
		this.alarmSource = alarmSource;
	}

	/**
	 * @return the alarmId
	 */
	public String getAlarmId() {
		return alarmId;
	}

	/**
	 * @param alarmId the alarmId to set
	 */
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	/**
	 * @return the alarmRuleSid
	 */
	public String getAlarmRuleSid() {
		return alarmRuleSid;
	}

	/**
	 * @param alarmRuleSid the alarmRuleSid to set
	 */
	public void setAlarmRuleSid(String alarmRuleSid) {
		this.alarmRuleSid = alarmRuleSid;
	}

	/**
	 * @return the alarmTargetName
	 */
	public String getAlarmTargetName() {
		return alarmTargetName;
	}

	/**
	 * @param alarmTargetName the alarmTargetName to set
	 */
	public void setAlarmTargetName(String alarmTargetName) {
		this.alarmTargetName = alarmTargetName;
	}
	
}