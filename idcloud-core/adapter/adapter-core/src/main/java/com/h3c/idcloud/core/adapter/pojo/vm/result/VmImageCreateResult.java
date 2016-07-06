package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class VmImageCreateResult extends BaseResult {

    private String id;
    private String imageName;
    private String imageDesp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageDesp() {
        return imageDesp;
    }

    public void setImageDesp(String imageDesp) {
        this.imageDesp = imageDesp;
    }

}
