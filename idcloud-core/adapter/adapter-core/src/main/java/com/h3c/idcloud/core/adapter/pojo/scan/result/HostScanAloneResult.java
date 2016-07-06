package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostVO;

public class HostScanAloneResult extends BaseResult {

    private String name;
    private HostVO host;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HostVO getHost() {
        return host;
    }

    public void setHost(HostVO host) {
        this.host = host;
    }

}
