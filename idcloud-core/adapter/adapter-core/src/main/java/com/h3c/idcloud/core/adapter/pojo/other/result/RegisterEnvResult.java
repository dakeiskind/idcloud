package com.h3c.idcloud.core.adapter.pojo.other.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class RegisterEnvResult extends BaseResult {

    private String virtualType;
    private String uuid;

    public String getVirtualType() {
        return virtualType;
    }

    public void setVirtualType(String virtualType) {
        this.virtualType = virtualType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
