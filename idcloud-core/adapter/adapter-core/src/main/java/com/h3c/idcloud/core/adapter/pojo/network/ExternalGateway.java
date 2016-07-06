package com.h3c.idcloud.core.adapter.pojo.network;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by qct on 2016/4/9.
 *
 * @author qct
 */
public class ExternalGateway {
    private String networkId;
    private Boolean enableSnat;

    public ExternalGateway() {}
    public ExternalGateway(String networkId, Boolean enableSnat) {
        this.networkId = networkId;
        this.enableSnat = enableSnat;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public Boolean getEnableSnat() {
        return enableSnat;
    }

    public void setEnableSnat(Boolean enableSnat) {
        this.enableSnat = enableSnat;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(networkId, enableSnat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ExternalGateway that = (ExternalGateway) o;

        return Objects.equal(this.networkId, that.networkId) &&
                Objects.equal(this.enableSnat, that.enableSnat);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("networkId", networkId)
                .add("enableSnat", enableSnat)
                .toString();
    }

    /**
     * @return the Builder for ExternalGatewayInfo
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets a Builder configured as this object.
     */
    public Builder toBuilder() {
        return new Builder().fromExternalGatewayInfo(this);
    }

    public static class Builder {
        protected String networkId;
        protected Boolean enableSnat;

        /**
         * Provide the networkId to the ExternalGatewayInfo's Builder.
         *
         * @return the Builder.
         * @see ExternalGateway#getNetworkId()
         */
        public Builder networkId(String networkId) {
            this.networkId = networkId;
            return this;
        }

        /**
         * Provide the enableSnat status to the ExternalGatewayInfo's Builder.
         *
         * @return the Builder.
         * @see ExternalGateway#getEnableSnat()
         */
        public Builder enableSnat(Boolean enableSnat) {
            this.enableSnat = enableSnat;
            return this;
        }

        /**
         * @return a ExternalGatewayInfo constructed with this Builder.
         */
        public ExternalGateway build() {
            return new ExternalGateway(networkId, enableSnat);
        }

        /**
         * @return a Builder from another ExternalGatewayInfo.
         */
        public Builder fromExternalGatewayInfo(ExternalGateway in) {
            return this.networkId(in.getNetworkId()).enableSnat(in.getEnableSnat());
        }
    }
}
