package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.ClusterVO;

public class ClusterScanAloneResult extends BaseResult {

    private String name;
    private ClusterVO cluster;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClusterVO getCluster() {
        return cluster;
    }

    public void setCluster(ClusterVO cluster) {
        this.cluster = cluster;
    }

}
