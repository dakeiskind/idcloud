package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostNameVO;

import java.util.ArrayList;
import java.util.List;

public class HostScanByClusterResult extends BaseResult {

    private String cluster;

    private List<HostNameVO> hosts = new ArrayList<HostNameVO>();

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public List<HostNameVO> getHosts() {
        return hosts;
    }

    public void setHosts(List<HostNameVO> hosts) {
        this.hosts = hosts;
    }

}
