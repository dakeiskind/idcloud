package com.h3c.idcloud.core.adapter.pojo.admin.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class SecurityGroup extends BaseResult {

    private String name;
    private String id;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
