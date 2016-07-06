package com.h3c.idcloud.core.adapter.pojo.image;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class ImageUpdate extends Base {
    private String sid;
    private String osType;
    private String osVersion;
    private String imageId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
