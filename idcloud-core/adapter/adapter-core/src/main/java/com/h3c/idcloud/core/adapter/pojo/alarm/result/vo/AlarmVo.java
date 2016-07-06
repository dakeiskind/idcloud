package com.h3c.idcloud.core.adapter.pojo.alarm.result.vo;

/***
 * 触发告警信息
 *
 * @author hpadmin
 */
public class AlarmVo {
    private String key;
    private String createEventid;
    private String name;//告警名称
    private String lastModifiedUser;//最后修改告警者
    private String description;//告警信息描述

    private String triggeredTime;//告警触发时间
    private String target;//触发告警对象
    private String status;//

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public String getCreateEventid() {

        return createEventid;
    }

    public void setCreateEventid(String createEventid) {

        this.createEventid = createEventid;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getLastModifiedUser() {

        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {

        this.lastModifiedUser = lastModifiedUser;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getTriggeredTime() {
        return triggeredTime;
    }

    public void setTriggeredTime(String triggeredTime) {
        this.triggeredTime = triggeredTime;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
