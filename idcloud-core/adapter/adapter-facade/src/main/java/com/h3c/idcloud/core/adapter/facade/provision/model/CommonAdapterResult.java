package com.h3c.idcloud.core.adapter.facade.provision.model;

import java.util.Map;

public class CommonAdapterResult {
    private Map<String, ? extends Object> map;
    private boolean success;

    public CommonAdapterResult(){}
    public CommonAdapterResult(boolean success) {
        this.success = success;
    }
    public CommonAdapterResult(boolean success, Map<String, ? extends Object> map) {
        this(success);
        this.map = map;
    }
    public Map<String, ? extends Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
