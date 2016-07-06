package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class LogRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ip
     */
    private String opIp;
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
     * 操作名称
     */
    private String actName;

    /**
     * 操作级别
     */
    private String actLevel;

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
     * 操作失败原因
     */
    private String actFailureReason;

    /**
     * 操作内容
     */
    private String actDetail;

    public String getActFailureReason() {
        return actFailureReason;
    }

    public void setActFailureReason(String actFailureReason) {
        this.actFailureReason = actFailureReason;
    }

    public String getActDetail() {
        return actDetail;
    }

    public void setActDetail(String actDetail) {
        this.actDetail = actDetail;
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
}