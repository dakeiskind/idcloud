package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class VmVncConsoleResult extends BaseResult {

    private String id;
    private String url;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
