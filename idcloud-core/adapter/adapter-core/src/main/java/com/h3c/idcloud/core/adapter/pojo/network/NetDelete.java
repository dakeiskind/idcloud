package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import java.util.Set;

public class NetDelete extends Base {
    private String resNetworkSId;
    private String mgtObjSid;
    private String netId;
    private Set<String> subnetIds;

    public Set<String> getSubnetIds() {
        return subnetIds;
    }

    public void setSubnetIds(Set<String> subnetIds) {
        this.subnetIds = subnetIds;
    }

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
