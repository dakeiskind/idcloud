package com.h3c.idcloud.core.adapter.pojo.tenant.result;


import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class TenantInfoGetResult extends BaseResult {


    private String id;

    private String name;

    private String description;

    private boolean enable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


}
