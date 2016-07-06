package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class NetDeleteResult extends BaseResult {
    private String resNetworkSId;
    private String mgtObjSid;
    private String netId;

    public String getResNetworkSId() {
        return resNetworkSId;
    }

    public void setResNetworkSId(String resNetworkSId) {
        this.resNetworkSId = resNetworkSId;
    }

    public String getMgtObjSid() {
        return mgtObjSid;
    }

    public void setMgtObjSid(String mgtObjSid) {
        this.mgtObjSid = mgtObjSid;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

}
