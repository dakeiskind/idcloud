package com.h3c.idcloud.core.adapter.pojo.network;

/**
 * Created by qct on 2016/4/11.
 *
 * @author qct
 */
public enum NetworkStatus {
    ACTIVE("active"),
    DOWN("down"),
    BUILD("build"),
    ERROR("error"),
    /**
     * Used by jclouds when the service returns an unknown value other than null.
     */
    UNRECOGNIZED("unrecognized");

    private final String name;

    private NetworkStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name();
    }

    /*
     * This provides GSON enum support in jclouds.
     * @param name The string representation of this enum value.
     * @return The corresponding enum value.
     */
    public static NetworkStatus fromValue(String name) {
        if (name != null) {
            for (NetworkStatus value : NetworkStatus.values()) {
                if (name.equalsIgnoreCase(value.name)) {
                    return value;
                }
            }
            return UNRECOGNIZED;
        }
        return null;
    }
}
