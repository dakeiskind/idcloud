package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class MailTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 邮件模板SID
     */
    private Long mailTemplateSid;

    /**
     * 邮件模板编号
     */
    private String mailTemplateId;

    /**
     * 邮件模板名称
     */
    private String mailTemplateName;

    /**
     * 邮件标题
     */
    private String mailSubject;

    /**
     * 邮件内容
     */
    private String mailContent;

    /**
     * 邮件内容文件路径
     */
    private String mailContentFilePath;

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

    /**
     * @return 邮件模板SID
     */
    public Long getMailTemplateSid() {
        return mailTemplateSid;
    }

    /**
     * @param mailTemplateSid 
	 *            邮件模板SID
     */
    public void setMailTemplateSid(Long mailTemplateSid) {
        this.mailTemplateSid = mailTemplateSid;
    }

    /**
     * @return 邮件模板编号
     */
    public String getMailTemplateId() {
        return mailTemplateId;
    }

    /**
     * @param mailTemplateId 
	 *            邮件模板编号
     */
    public void setMailTemplateId(String mailTemplateId) {
        this.mailTemplateId = mailTemplateId;
    }

    /**
     * @return 邮件模板名称
     */
    public String getMailTemplateName() {
        return mailTemplateName;
    }

    /**
     * @param mailTemplateName 
	 *            邮件模板名称
     */
    public void setMailTemplateName(String mailTemplateName) {
        this.mailTemplateName = mailTemplateName;
    }

    /**
     * @return 邮件标题
     */
    public String getMailSubject() {
        return mailSubject;
    }

    /**
     * @param mailSubject 
	 *            邮件标题
     */
    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    /**
     * @return 邮件内容
     */
    public String getMailContent() {
        return mailContent;
    }

    /**
     * @param mailContent 
	 *            邮件内容
     */
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    /**
     * @return 邮件内容文件路径
     */
    public String getMailContentFilePath() {
        return mailContentFilePath;
    }

    /**
     * @param mailContentFilePath 
	 *            邮件内容文件路径
     */
    public void setMailContentFilePath(String mailContentFilePath) {
        this.mailContentFilePath = mailContentFilePath;
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
}