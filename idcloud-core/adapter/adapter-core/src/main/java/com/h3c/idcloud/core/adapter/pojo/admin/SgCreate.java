package com.h3c.idcloud.core.adapter.pojo.admin;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class SgCreate extends Base {

    private String resId;
    private String name;
    private String description;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
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


}
