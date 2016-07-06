package com.h3c.idcloud.core.adapter.pojo.tenant;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class TenantCreate extends Base {

    private String name;

    private String description;

    private boolean enable;

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
