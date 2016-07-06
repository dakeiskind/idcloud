package com.h3c.idcloud.core.adapter.pojo.image;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class ImageDelete extends Base {
    private String imageId;
    private String sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
