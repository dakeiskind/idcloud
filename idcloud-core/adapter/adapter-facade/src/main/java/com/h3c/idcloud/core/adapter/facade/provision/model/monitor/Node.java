package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

public class Node extends CommonAdapterResult {

    private String nodeId;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

}
