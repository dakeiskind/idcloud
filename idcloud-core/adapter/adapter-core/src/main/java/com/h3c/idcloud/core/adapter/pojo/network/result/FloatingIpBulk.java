package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class FloatingIpBulk extends BaseResult {

    private String pool;
    private String ipRange;
    private String eth;

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }

    //	@JsonProperty("interface")
    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

}
