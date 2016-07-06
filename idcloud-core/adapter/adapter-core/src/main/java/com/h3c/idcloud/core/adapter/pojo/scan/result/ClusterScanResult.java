package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.ClusterVO;

import java.util.ArrayList;
import java.util.List;

public class ClusterScanResult extends BaseResult {

    private List<ClusterVO> clusters = new ArrayList<ClusterVO>();

    public List<ClusterVO> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterVO> clusters) {
        this.clusters = clusters;
    }

}
