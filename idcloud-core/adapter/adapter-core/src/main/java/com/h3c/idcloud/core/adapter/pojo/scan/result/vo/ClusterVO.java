package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

import java.util.ArrayList;
import java.util.List;

public class ClusterVO {

    private String resTopologyName;
    private String openHa;
    private List<HostVO> hosts = new ArrayList<HostVO>();
    private List<DataStoreVO> datastores = new ArrayList<DataStoreVO>();

    public String getResTopologyName() {
        return resTopologyName;
    }

    public void setResTopologyName(String resTopologyName) {
        this.resTopologyName = resTopologyName;
    }

    public String getOpenHa() {
        return openHa;
    }

    public void setOpenHa(String openHa) {
        this.openHa = openHa;
    }

    public List<HostVO> getHosts() {
        return hosts;
    }

    public void setHosts(List<HostVO> hosts) {
        this.hosts = hosts;
    }

    public List<DataStoreVO> getDatastores() {
        return datastores;
    }

    public void setDatastores(List<DataStoreVO> datastores) {
        this.datastores = datastores;
    }

}
