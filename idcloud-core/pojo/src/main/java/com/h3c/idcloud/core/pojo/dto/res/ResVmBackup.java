package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;
import java.util.Date;

public class ResVmBackup implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 备份ID
     */
    private Long backupSid;

    /**
     * 关联虚拟机资源ID
     */
    private String resVmSid;

    /**
     * 分配备份ID
     */
    private String allocateBackupId;

    /**
     * 备份名称
     */
    private String backupName;

    /**
     * 备份别名
     */
    private String backupAlias;

    /**
     * 备份类型
     */
    private String backupType;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusTxt;

    /**
     * 备份时间
     */
    private Date backupTime;

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
     * @return 备份ID
     */
    public Long getBackupSid() {
        return backupSid;
    }

    /**
     * @param backupSid 备份ID
     */
    public void setBackupSid(Long backupSid) {
        this.backupSid = backupSid;
    }

    /**
     * @return 关联虚拟机资源ID
     */
    public String getResVmSid() {
        return resVmSid;
    }

    /**
     * @param resVmSid 关联虚拟机资源ID
     */
    public void setResVmSid(String resVmSid) {
        this.resVmSid = resVmSid;
    }

    /**
     * @return 分配备份ID
     */
    public String getAllocateBackupId() {
        return allocateBackupId;
    }

    /**
     * @param allocateBackupId 分配备份ID
     */
    public void setAllocateBackupId(String allocateBackupId) {
        this.allocateBackupId = allocateBackupId;
    }

    /**
     * @return 备份名称
     */
    public String getBackupName() {
        return backupName;
    }

    /**
     * @param backupName 备份名称
     */
    public void setBackupName(String backupName) {
        this.backupName = backupName;
    }

    /**
     * @return 备份别名
     */
    public String getBackupAlias() {
        return backupAlias;
    }

    /**
     * @param backupAlias 备份别名
     */
    public void setBackupAlias(String backupAlias) {
        this.backupAlias = backupAlias;
    }

    /**
     * @return 备份类型
     */
    public String getBackupType() {
        return backupType;
    }

    /**
     * @param backupType 备份类型
     */
    public void setBackupType(String backupType) {
        this.backupType = backupType;
    }

    /**
     * @return 备份时间
     */
    public Date getBackupTime() {
        return backupTime;
    }

    /**
     * @param backupTime 备份时间
     */
    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 创建人
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
     * @param createdDt 创建时间
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
     * @param updatedBy 更新人
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
     * @param updatedDt 更新时间
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
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusTxt() {
        return statusTxt;
    }

    public void setStatusTxt(String statusTxt) {
        this.statusTxt = statusTxt;
    }
}