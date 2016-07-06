package com.h3c.idcloud.core.adapter.pojo.network;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by qct on 2016/4/7.
 *
 * @author qct
 */
public class Router {
    private String id;
    private String name;
    private String tenantId;
    private Boolean adminStateUp;
    private NetworkStatus networkStatus;
    private ExternalGateway externalGateway;

    public Router() {
    }

    public Router(String id, String name, String tenantId, Boolean adminStateUp, NetworkStatus networkStatus,
                  ExternalGateway externalGateway) {
        this.id = id;
        this.networkStatus = networkStatus;
        this.name = name;
        this.tenantId = tenantId;
        this.adminStateUp = adminStateUp;
        this.externalGateway = externalGateway;
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(Boolean adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

    public NetworkStatus getNetworkStatus() {
        return networkStatus;
    }

    public Router setNetworkStatus(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
        return this;
    }

    public ExternalGateway getExternalGateway() {
        return externalGateway;
    }

    public void setExternalGateway(ExternalGateway externalGateway) {
        this.externalGateway = externalGateway;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Router that = (Router) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.networkStatus, that.networkStatus) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.tenantId, that.tenantId) &&
                Objects.equal(this.adminStateUp, that.adminStateUp) &&
                Objects.equal(this.externalGateway, that.externalGateway);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, tenantId, adminStateUp, networkStatus, externalGateway);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("status", networkStatus)
                .add("name", name)
                .add("tenantId", tenantId)
                .add("adminStateUp", adminStateUp)
                .add("externalGatewayInfo", externalGateway)
                .toString();
    }

    public static class Builder {
        protected String id;
        protected String name;
        protected String tenantId;
        protected Boolean adminStateUp;
        protected NetworkStatus networkStatus;
        protected ExternalGateway externalGateway;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder adminStateUp(boolean adminStateUp) {
            this.adminStateUp = adminStateUp;
            return this;
        }

        public Builder status(NetworkStatus networkStatus) {
            this.networkStatus = networkStatus;
            return this;
        }

        public Builder externalGateway(ExternalGateway externalGateway) {
            this.externalGateway = externalGateway;
            return this;
        }

        public Router build() {
            return new Router(id, name, tenantId, adminStateUp, networkStatus, externalGateway);
        }
    }
}
