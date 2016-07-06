package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

public class CpuInfoValue extends CommonAdapterResult {

    private String time;
    private String value;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
