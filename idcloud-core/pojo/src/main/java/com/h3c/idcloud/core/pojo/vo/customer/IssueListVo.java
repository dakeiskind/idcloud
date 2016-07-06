package com.h3c.idcloud.core.pojo.vo.customer;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/2.
 */
public class IssueListVo {

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
    private Date createdDt;
    private Date updatedDt;

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
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

    public String getIssueTypeName() {
        return issueTypeName;
    }

    public void setIssueTypeName(String issueTypeName) {
        this.issueTypeName = issueTypeName;
    }

    public String getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(String issuePriority) {
        this.issuePriority = issuePriority;
    }

    public String getIssuePriorityName() {
        return issuePriorityName;
    }

    public void setIssuePriorityName(String issuePriorityName) {
        this.issuePriorityName = issuePriorityName;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
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

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getIssueStatusName() {
        return issueStatusName;
    }

    public void setIssueStatusName(String issueStatusName) {
        this.issueStatusName = issueStatusName;
    }
}
