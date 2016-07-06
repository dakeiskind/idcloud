package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.google.common.collect.ImmutableSet;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.network.Router;
import com.h3c.idcloud.core.adapter.pojo.network.Subnet;

import java.util.Set;

public class NetCreateResult extends BaseResult {

    private String resId;
    private String id;

    private String name;

    private Router router;

    private Subnet subnet;

    private Set<Port> ports;

    public Set<Port> getPorts() {
        return ports;
    }

    public void setPorts(Set<Port> ports) {
        this.ports = ports;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Subnet getSubnet() {
        return subnet;
    }

    public void setSubnet(Subnet subnet) {
        this.subnet = subnet;
    }


}
