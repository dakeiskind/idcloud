package com.h3c.idcloud.core.adapter.pojo.image.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class ImageUpdateResult extends BaseResult {
    private String sid;
    private String uuid;
    private String imageId;
    private String imageName;
    private String osVersion;
    private String osType;
    private String allocateResHostName;
    private String allocateResStorageName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getAllocateResHostName() {
        return allocateResHostName;
    }

    public void setAllocateResHostName(String allocateResHostName) {
        this.allocateResHostName = allocateResHostName;
    }

    public String getAllocateResStorageName() {
        return allocateResStorageName;
    }

    public void setAllocateResStorageName(String allocateResStorageName) {
        this.allocateResStorageName = allocateResStorageName;
    }
}
