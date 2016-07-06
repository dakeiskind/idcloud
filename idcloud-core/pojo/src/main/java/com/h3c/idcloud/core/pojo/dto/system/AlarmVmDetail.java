package com.h3c.idcloud.core.pojo.dto.system;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Administrator on 2016/3/10.
 */
public class AlarmVmDetail {
    /**
     * JSON格式示例：
     * [{
     *		alarmTarget:"3453",               //监控的节点
     *		alarmLevel:"01",                  //告警级别
     *		alarmLevelName:"紧急",            //告警级别显示值
     *		alarmDetail:"虚拟机启动失败",     //告警详情
     *		alarmDate:"2014-12-30 13:22:22"   //告警时间
     *	}]
     */

    @JsonProperty("alarmTarget")
    private String alarmTarget;

    @JsonProperty("alarmLevel")
    private String alarmLevel;

    @JsonProperty("alarmLevelName")
    private String alarmLevelName;

    @JsonProperty("alarmDetail")
    private String alarmDetail;

    @JsonProperty("alarmDate")
    private String alarmDate;

    //非接口返回字段
    private String instanceName;

    private String vmName;

    private String resInsVmStatusName;

    public String getAlarmTarget() {
        return alarmTarget;
    }

    public void setAlarmTarget(String alarmTarget) {
        this.alarmTarget = alarmTarget;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmLevelName() {
        return alarmLevelName;
    }

    public void setAlarmLevelName(String alarmLevelName) {
        this.alarmLevelName = alarmLevelName;
    }

    public String getAlarmDetail() {
        return alarmDetail;
    }

    public void setAlarmDetail(String alarmDetail) {
        this.alarmDetail = alarmDetail;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getResInsVmStatusName() {
        return resInsVmStatusName;
    }

    public void setResInsVmStatusName(String resInsVmStatusName) {
        this.resInsVmStatusName = resInsVmStatusName;
    }
}
