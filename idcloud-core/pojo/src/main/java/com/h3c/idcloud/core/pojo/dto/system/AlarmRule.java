package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/9.
 */
public class AlarmRule implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long alarmRuleSid;

    private String alarmKpi;

    private String alarmKpiName;

    private String alarmLevel;

    private String alarmLevelName;

    private String alarmType;

    private String alarmTypeName;

    private String checkOptr;

    private String checkOptrName;

    private String platformType;

    private String platformTypeName;

    private String accumulateCount;

    private String alarmThreshold;

    private String title;

    private String content;

    private String operator;

    private String instanceId;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Long version;

    public Long getAlarmRuleSid() {
        return alarmRuleSid;
    }

    public void setAlarmRuleSid(Long alarmRuleSid) {
        this.alarmRuleSid = alarmRuleSid;
    }

    public String getAlarmKpi() {
        return alarmKpi;
    }

    public void setAlarmKpi(String alarmKpi) {
        this.alarmKpi = alarmKpi;
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

    public String getCheckOptr() {
        return checkOptr;
    }

    public void setCheckOptr(String checkOptr) {
        this.checkOptr = checkOptr;
    }

    public String getAccumulateCount() {
        return accumulateCount;
    }

    public void setAccumulateCount(String accumulateCount) {
        this.accumulateCount = accumulateCount;
    }

    public String getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(String alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
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
     * @return the alarmKpiName
     */
    public String getAlarmKpiName() {
        return alarmKpiName;
    }

    /**
     * @param alarmKpiName the alarmKpiName to set
     */
    public void setAlarmKpiName(String alarmKpiName) {
        this.alarmKpiName = alarmKpiName;
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
     * @return the checkOptrName
     */
    public String getCheckOptrName() {
        return checkOptrName;
    }

    /**
     * @param checkOptrName the checkOptrName to set
     */
    public void setCheckOptrName(String checkOptrName) {
        this.checkOptrName = checkOptrName;
    }

    /**
     * @return the platformType
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * @param platformType the platformType to set
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * @return the platformTypeName
     */
    public String getPlatformTypeName() {
        return platformTypeName;
    }

    /**
     * @param platformTypeName the platformTypeName to set
     */
    public void setPlatformTypeName(String platformTypeName) {
        this.platformTypeName = platformTypeName;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return the instanceId
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * @param instanceId the instanceId to set
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
