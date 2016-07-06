package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

public class Template extends CommonAdapterResult {

    private String uuid;
    private String imageId;
    private String imageName;
    private String osVersion;
    private String osType;
    private String allocateResHostName;
    private String allocateResStorageName;
    private String tenantId;
    private String visibility;

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getTenantId() {
        return tenantId;
    }

    @JsonProperty("owner")
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    @JsonProperty("os_type")
    public void setKvmOsType(String osType) {
        this.osType = osType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @JsonProperty("name")
    public void setKvmImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("id")
    public void setKvmUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @JsonProperty("os_version")
    public void setKvmOsVersion(String osVersion) {
        this.osVersion = osVersion;
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
