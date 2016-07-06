package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

public class Volume extends CommonAdapterResult {

    private String description;

    @JsonProperty("id")
    private String volumeId;

    private String name;

    private String size;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


}
