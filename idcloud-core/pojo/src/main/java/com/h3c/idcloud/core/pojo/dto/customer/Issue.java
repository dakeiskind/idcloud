package com.h3c.idcloud.core.pojo.dto.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/2.
 */
public class Issue implements Serializable{
    private static final long serialVersionUID = -1283442542185878905L;

    private Long issueSid;
    private String issueType;
    private String issueTypeName;
    private String issuePriority;
    private String issuePriorityName;
    private String issueTitle;
    private String issueDesc;
    private String issueComment;
    private String issueStatus;
    private String issueStatusName;
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
    private String createdDtFromDate;
    private String createdDtToDate;
    /**
     * 发聩邮箱
     */
    private String feedbackMail;
    /**
     * 反馈号码
     */
    private String feedbackPhone;

    private String cloudHost;


    public String getFeedbackMail() {
        return feedbackMail;
    }

    public void setFeedbackMail(String feedbackMail) {
        this.feedbackMail = feedbackMail;
    }

    public String getFeedbackPhone() {
        return feedbackPhone;
    }

    public void setFeedbackPhone(String feedbackPhone) {
        this.feedbackPhone = feedbackPhone;
    }

    public String getCloudHost() {
        return cloudHost;
    }

    public void setCloudHost(String cloudHost) {
        this.cloudHost = cloudHost;
    }

    public Long getIssueSid() {
        return issueSid;
    }

    public void setIssueSid(Long issueSid) {
        this.issueSid = issueSid;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(String issuePriority) {
        this.issuePriority = issuePriority;
    }

    public String getIssueDesc() {
        return issueDesc;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    public String getIssueComment() {
        return issueComment;
    }

    public void setIssueComment(String issueComment) {
        this.issueComment = issueComment;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getIssueTypeName() {
        return issueTypeName;
    }

    public void setIssueTypeName(String issueTypeName) {
        this.issueTypeName = issueTypeName;
    }

    public String getIssuePriorityName() {
        return issuePriorityName;
    }

    public void setIssuePriorityName(String issuePriorityName) {
        this.issuePriorityName = issuePriorityName;
    }

    public String getIssueStatusName() {
        return issueStatusName;
    }

    public void setIssueStatusName(String issueStatusName) {
        this.issueStatusName = issueStatusName;
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

    public String getCreatedDtFromDate() {
        return createdDtFromDate;
    }

    public void setCreatedDtFromDate(String createdDtFromDate) {
        this.createdDtFromDate = createdDtFromDate;
    }

    public String getCreatedDtToDate() {
        return createdDtToDate;
    }

    public void setCreatedDtToDate(String createdDtToDate) {
        this.createdDtToDate = createdDtToDate;
    }


}
