package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

public class Attachments implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 附件ID
     */
    private String attachmentSid;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件URL
     */
    private String attachmentUrl;

    /**
     * 附件存放位置
     */
    private String attachmentLocation;

    /**
     * 1：文档<br>
	 *             2：音频文件<br>
	 *             3：视频文件<br>
	 *             4：图片<br>
	 *             9：其它
     */
    private String attachmentType;

    /**
     * 备注
     */
    private String attachmentDesc;

    /**
     * 附件原始名称
     */
    private String originalName;

    /**
     * 附件扩展名
     */
    private String extName;

    /**
     * 单位为字节
     */
    private Long attachmentSize;

    /**
     * 附件上传日期
     */
    private Date uploadDate;

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
     * @return 附件ID
     */
    public String getAttachmentSid() {
        return attachmentSid;
    }

    /**
     * @param attachmentSid 
	 *            附件ID
     */
    public void setAttachmentSid(String attachmentSid) {
        this.attachmentSid = attachmentSid;
    }

    /**
     * @return 附件名称
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * @param attachmentName 
	 *            附件名称
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    /**
     * @return 附件URL
     */
    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    /**
     * @param attachmentUrl 
	 *            附件URL
     */
    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    /**
     * @return 附件存放位置
     */
    public String getAttachmentLocation() {
        return attachmentLocation;
    }

    /**
     * @param attachmentLocation 
	 *            附件存放位置
     */
    public void setAttachmentLocation(String attachmentLocation) {
        this.attachmentLocation = attachmentLocation;
    }

    /**
     * @return 1：文档<br>
	 *                     2：音频文件<br>
	 *                     3：视频文件<br>
	 *                     4：图片<br>
	 *                     9：其它
     */
    public String getAttachmentType() {
        return attachmentType;
    }

    /**
     * @param attachmentType 
	 *            1：文档<br>
	 *                        2：音频文件<br>
	 *                        3：视频文件<br>
	 *                        4：图片<br>
	 *                        9：其它
     */
    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    /**
     * @return 备注
     */
    public String getAttachmentDesc() {
        return attachmentDesc;
    }

    /**
     * @param attachmentDesc 
	 *            备注
     */
    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    /**
     * @return 附件原始名称
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * @param originalName 
	 *            附件原始名称
     */
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    /**
     * @return 附件扩展名
     */
    public String getExtName() {
        return extName;
    }

    /**
     * @param extName 
	 *            附件扩展名
     */
    public void setExtName(String extName) {
        this.extName = extName;
    }

    /**
     * @return 单位为字节
     */
    public Long getAttachmentSize() {
        return attachmentSize;
    }

    /**
     * @param attachmentSize 
	 *            单位为字节
     */
    public void setAttachmentSize(Long attachmentSize) {
        this.attachmentSize = attachmentSize;
    }

    /**
     * @return 附件上传日期
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate 
	 *            附件上传日期
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
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