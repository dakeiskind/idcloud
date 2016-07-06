package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;


public class Block extends CommonAdapterResult {


    private String device;

    @JsonProperty("server_id")
    private String id;

    @JsonProperty("id")
    private String blockId;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }


}
