package com.h3c.idcloud.core.adapter.facade.provision.model.network;


import com.google.common.collect.ImmutableSet;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.network.NetworkStatus;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.network.Router;
import com.h3c.idcloud.core.adapter.pojo.network.Subnet;

public class Network extends CommonAdapterResult {
    private String id;
    private String name;
    private Router router;
    private NetworkStatus networkStatus;
    private Subnet subnet;
    private ImmutableSet<Port> ports;

    public Network(){}

    public Network(String id, String name, Router router, Subnet subnet, NetworkStatus networkStatus,
                      ImmutableSet<Port> ports) {
        this.id = id;
        this.name = name;
        this.subnet = subnet;
        this.router = router;
        this.networkStatus = networkStatus;
        this.ports = ports;
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

    public NetworkStatus getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
    }

    public Subnet getSubnet() {
        return subnet;
    }

    public void setSubnet(Subnet subnet) {
        this.subnet = subnet;
    }

    public ImmutableSet<Port> getPorts() {
        return ports;
    }

    public void setPorts(ImmutableSet<Port> ports) {
        this.ports = ports;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected String id;
        protected String name;
        protected Subnet subnet;
        protected Router router;
        protected NetworkStatus status;
        protected ImmutableSet<Port> ports;

        public Builder id(String id) {
            this.id = id;
            return self();
        }

        public Builder name(String name) {
            this.name = name;
            return self();
        }

        public Builder subnet(Subnet subnet) {
            this.subnet = subnet;
            return self();
        }

        public Builder router(Router router) {
            this.router = router;
            return self();
        }

        public Builder status(NetworkStatus status) {
            this.status = status;
            return self();
        }

        public Builder ports(ImmutableSet<Port> ports) {
            this.ports = ports;
            return self();
        }

        protected Builder self() {
            return this;
        }

        public Network build() {
            return new Network(id, name, router, subnet, status, ports);
        }
    }
}
