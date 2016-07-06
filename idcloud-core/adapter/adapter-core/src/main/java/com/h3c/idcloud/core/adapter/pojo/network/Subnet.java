package com.h3c.idcloud.core.adapter.pojo.network;

public class Subnet {
    private String id;
    private String name;
    private String cidr;
    private String gatewayIp;
    private String networkId;

    public Subnet() {}

    public Subnet(String id, String name, String cidr, String gatewayIp, String networkId) {
        this.id = id;
        this.name = name;
        this.cidr = cidr;
        this.gatewayIp = gatewayIp;
        this.networkId = networkId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Builder {
        protected String id;
        protected String name;
        protected String cidr;
        protected String networkId;
        protected String gatewayIp;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder cidr(String cidr) {
            this.cidr = cidr;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder gatewayIp(String gatewayIp) {
            this.gatewayIp = gatewayIp;
            return this;
        }

        public Builder networkId(String networkId) {
            this.networkId = networkId;
            return this;
        }

        public Subnet build() {
            return new Subnet(id, name, cidr, gatewayIp, networkId);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
