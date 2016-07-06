package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class SysTLogRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    private Long logSid;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 操作对象
     */
    private String actTarget;

    /**
     * 操作对象名称
     */
    private String actTargetName;

    /**
     * 操作名称
     */
    private String actName;

    /**
     * 操作级别
     */
    private String actLevel;
    
    /**
     * 操作级别名称
     */
    private String actLevelName;

    /**
     * 操作开始时间
     */
    private Date actStartDate;

    /**
     * 操作结束时间
     */
    private Date actEndDate;

    /**
     * 操作结果
     */
    private String actResult;
    
    /**
     * 操作结果名称
     */
    private String actResultName;

    /**
     * 操作失败原因
     */
    private String actFailureReason;

    /**
     * 操作内容
     */
    private String actDetail;
    
    private String startTime;
    
    private String endTime ;

    private String opIp ;

    public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp;
    }

    /**
     * @return 日志ID
     */
    public Long getLogSid() {
        return logSid;
    }

    /**
     * @param logSid 
	 *            日志ID
     */
    public void setLogSid(Long logSid) {
        this.logSid = logSid;
    }

    /**
     * @return 用户账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account 
	 *            用户账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    public String getActTargetName() {
        return actTargetName;
    }

    public void setActTargetName(String actTargetName) {
        this.actTargetName = actTargetName;
    }

    /**
     * @return 操作对象
     */
    public String getActTarget() {
        return actTarget;
    }

    /**
     * @param actTarget 
	 *            操作对象
     */
    public void setActTarget(String actTarget) {
        this.actTarget = actTarget;
    }

    /**
     * @return 操作名称
     */
    public String getActName() {
        return actName;
    }

    /**
     * @param actName 
	 *            操作名称
     */
    public void setActName(String actName) {
        this.actName = actName;
    }

    /**
     * @return 操作级别
     */
    public String getActLevel() {
        return actLevel;
    }

    /**
     * @param actLevel 
	 *            操作级别
     */
    public void setActLevel(String actLevel) {
        this.actLevel = actLevel;
    }

    /**
     * @return 操作开始时间
     */
    public Date getActStartDate() {
        return actStartDate;
    }

    /**
     * @param actStartDate 
	 *            操作开始时间
     */
    public void setActStartDate(Date actStartDate) {
        this.actStartDate = actStartDate;
    }

    /**
     * @return 操作结束时间
     */
    public Date getActEndDate() {
        return actEndDate;
    }

    /**
     * @param actEndDate 
	 *            操作结束时间
     */
    public void setActEndDate(Date actEndDate) {
        this.actEndDate = actEndDate;
    }

    /**
     * @return 操作结果
     */
    public String getActResult() {
        return actResult;
    }

    /**
     * @param actResult 
	 *            操作结果
     */
    public void setActResult(String actResult) {
        this.actResult = actResult;
    }

    /**
     * @return 操作失败原因
     */
    public String getActFailureReason() {
        return actFailureReason;
    }

    /**
     * @param actFailureReason 
	 *            操作失败原因
     */
    public void setActFailureReason(String actFailureReason) {
        this.actFailureReason = actFailureReason;
    }

    /**
     * @return 操作内容
     */
    public String getActDetail() {
        return actDetail;
    }

    /**
     * @param actDetail 
	 *            操作内容
     */
    public void setActDetail(String actDetail) {
        this.actDetail = actDetail;
    }

	public String getActLevelName() {
		return actLevelName;
	}

	public void setActLevelName(String actLevelName) {
		this.actLevelName = actLevelName;
	}

	public String getActResultName() {
		return actResultName;
	}

	public void setActResultName(String actResultName) {
		this.actResultName = actResultName;
	}
    
}