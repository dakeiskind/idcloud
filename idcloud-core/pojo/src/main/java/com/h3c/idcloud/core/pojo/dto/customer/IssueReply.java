package com.h3c.idcloud.core.pojo.dto.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单回复
 */
public class IssueReply implements Serializable {
    private static final long serialVersionUID = -1283442542185878905L;

    /**
     * sid
     */
    private Long issueReplySid;

    /**
     * 问题SID
     */
    private Long issueSid;

    /**
     * 问题回复内容
     */
    private String content;

    /**
     * 类型(01表示管理员回复；02表示用户回复)
     */
    private String replyType;

    private String createdBy;

    private Date createdDt;

    private Date updatedDt;

    private String updatedBy;

    /**
     * 版本号
     */
    private Long version;

    /**
     * @return sid
     */
    public Long getIssueReplySid() {
        return issueReplySid;
    }

    /**
     * @param issueReplySid
     *            sid
     */
    public void setIssueReplySid(Long issueReplySid) {
        this.issueReplySid = issueReplySid;
    }

    /**
     * @return 问题SID
     */
    public Long getIssueSid() {
        return issueSid;
    }

    /**
     * @param issueSid
     *            问题SID
     */
    public void setIssueSid(Long issueSid) {
        this.issueSid = issueSid;
    }

    /**
     * @return 问题回复内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            问题回复内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 类型
     */
    public String getReplyType() {
        return replyType;
    }

    /**
     * @param replyType
     *            类型
     */
    public void setReplyType(String replyType) {
        this.replyType = replyType;
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

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
}