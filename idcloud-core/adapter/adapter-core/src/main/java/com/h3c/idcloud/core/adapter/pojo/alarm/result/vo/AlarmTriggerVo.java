package com.h3c.idcloud.core.adapter.pojo.alarm.result.vo;


public class AlarmTriggerVo {
    private String name;//触发器规则名称
    private String entity;//触发器定义的实体对象
    private String description;//触发器定义描述

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
