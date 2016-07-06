package com.h3c.idcloud.core.adapter.pojo.network;

import java.util.Set;

/**
 * Created by qct on 2016/4/11.
 *
 * @author qct
 */
public class Port {
    private String id;
    private String name;
    private String macAddress;
    private String networkId;
    private NetworkStatus networkStatus;
    private String deviceOwner;
    private String deviceId;
    private Set<String> ips;

    public Set<String> getIps() {
        return ips;
    }

    public void setIps(Set<String> ips) {
        this.ips = ips;
    }

    public Port(){}

    public Port(String id, String name, String macAddress, String networkId, NetworkStatus networkStatus,
                String deviceOwner, String deviceId) {
        this.id = id;
        this.name = name;
        this.macAddress = macAddress;
        this.networkId = networkId;
        this.networkStatus = networkStatus;
        this.deviceOwner = deviceOwner;
        this.deviceId = deviceId;
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

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public NetworkStatus getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getDeviceOwner() {
        return deviceOwner;
    }

    public void setDeviceOwner(String deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected String id;
        protected String name;
        protected String macAddress;
        protected String networkId;
        protected NetworkStatus networkStatus;
        protected String deviceOwner;
        protected String deviceId;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder macAddress(String macAddress) {
            this.macAddress = macAddress;
            return this;
        }

        public Builder networkId(String networkId) {
            this.networkId = networkId;
            return this;
        }

        public Builder networkStatus(NetworkStatus networkStatus) {
            this.networkStatus = networkStatus;
            return this;
        }

        public Builder deviceOwner(String deviceOwner) {
            this.deviceOwner = deviceOwner;
            return this;
        }

        public Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Port build() {
            return new Port(id, name, macAddress, networkId, networkStatus, deviceOwner, deviceId);
        }
    }

    @Override
    public String toString() {
        return "Port{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", networkId='" + networkId + '\'' +
                ", networkStatus=" + networkStatus +
                ", deviceOwner='" + deviceOwner + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
