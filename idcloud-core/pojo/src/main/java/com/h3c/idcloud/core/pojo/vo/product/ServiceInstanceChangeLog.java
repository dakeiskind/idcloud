package com.h3c.idcloud.core.pojo.vo.product;

import java.io.Serializable;
import java.util.Date;

public class ServiceInstanceChangeLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 变更记录SID
     */
    private Long changeLogSid;

    /**
     * 服务实例SID
     */
    private Long instanceSid;

    /**
     * 变更发起者
     */
    private String changeSponsor;

    /**
     * 变更类型
     */
    private String changeType;

    /**
     * 变更规格
     */
    private String changeSpec;

    /**
     * 变更前规格
     */
    private String changePreSpec;

    /**
     * 变更前规格描述
     */
    private String changePreSpecDesc;

    /**
     * 当前变更规格
     */
    private String changeCurSpec;

    /**
     * 当前变更规格描述
     */
    private String changeCurSpecDesc;

    /**
     * 变更开始时间
     */
    private Date changeBeginTime;

    /**
     * 变更结束时间
     */
    private Date changeEndTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String changeComments;

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
     * @return 变更记录SID
     */
    public Long getChangeLogSid() {
        return changeLogSid;
    }

    /**
     * @param changeLogSid 
	 *            变更记录SID
     */
    public void setChangeLogSid(Long changeLogSid) {
        this.changeLogSid = changeLogSid;
    }

    /**
     * @return 服务实例SID
     */
    public Long getInstanceSid() {
        return instanceSid;
    }

    /**
     * @param instanceSid 
	 *            服务实例SID
     */
    public void setInstanceSid(Long instanceSid) {
        this.instanceSid = instanceSid;
    }

    /**
     * @return 变更发起者
     */
    public String getChangeSponsor() {
        return changeSponsor;
    }

    /**
     * @param changeSponsor 
	 *            变更发起者
     */
    public void setChangeSponsor(String changeSponsor) {
        this.changeSponsor = changeSponsor;
    }

    /**
     * @return 变更类型
     */
    public String getChangeType() {
        return changeType;
    }

    /**
     * @param changeType 
	 *            变更类型
     */
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    /**
     * @return 变更规格
     */
    public String getChangeSpec() {
        return changeSpec;
    }

    /**
     * @param changeSpec 
	 *            变更规格
     */
    public void setChangeSpec(String changeSpec) {
        this.changeSpec = changeSpec;
    }

    /**
     * @return 变更前规格
     */
    public String getChangePreSpec() {
        return changePreSpec;
    }

    /**
     * @param changePreSpec 
	 *            变更前规格
     */
    public void setChangePreSpec(String changePreSpec) {
        this.changePreSpec = changePreSpec;
    }

    /**
     * @return 变更前规格描述
     */
    public String getChangePreSpecDesc() {
        return changePreSpecDesc;
    }

    /**
     * @param changePreSpecDesc 
	 *            变更前规格描述
     */
    public void setChangePreSpecDesc(String changePreSpecDesc) {
        this.changePreSpecDesc = changePreSpecDesc;
    }

    /**
     * @return 当前变更规格
     */
    public String getChangeCurSpec() {
        return changeCurSpec;
    }

    /**
     * @param changeCurSpec 
	 *            当前变更规格
     */
    public void setChangeCurSpec(String changeCurSpec) {
        this.changeCurSpec = changeCurSpec;
    }

    /**
     * @return 当前变更规格描述
     */
    public String getChangeCurSpecDesc() {
        return changeCurSpecDesc;
    }

    /**
     * @param changeCurSpecDesc 
	 *            当前变更规格描述
     */
    public void setChangeCurSpecDesc(String changeCurSpecDesc) {
        this.changeCurSpecDesc = changeCurSpecDesc;
    }

    /**
     * @return 变更开始时间
     */
    public Date getChangeBeginTime() {
        return changeBeginTime;
    }

    /**
     * @param changeBeginTime 
	 *            变更开始时间
     */
    public void setChangeBeginTime(Date changeBeginTime) {
        this.changeBeginTime = changeBeginTime;
    }

    /**
     * @return 变更结束时间
     */
    public Date getChangeEndTime() {
        return changeEndTime;
    }

    /**
     * @param changeEndTime 
	 *            变更结束时间
     */
    public void setChangeEndTime(Date changeEndTime) {
        this.changeEndTime = changeEndTime;
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return 备注
     */
    public String getChangeComments() {
        return changeComments;
    }

    /**
     * @param changeComments 
	 *            备注
     */
    public void setChangeComments(String changeComments) {
        this.changeComments = changeComments;
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