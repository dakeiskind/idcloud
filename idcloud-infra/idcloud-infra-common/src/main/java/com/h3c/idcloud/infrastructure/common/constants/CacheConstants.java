package com.h3c.idcloud.infrastructure.common.constants;

/**
 * Created by qct on 2016/3/17.
 *
 * @author qct
 */
public class CacheConstants {
    public static final String USER = "user";
    public static final String TENANT = "tenant";
    public static final String SEPARATOR = ":";

    public static String redisUserKey(String env, String identifier) {
        return USER + SEPARATOR + env + SEPARATOR + identifier;
    }

    public static String redisTenantKey(String env, String identifier) {
            return TENANT + SEPARATOR + env + SEPARATOR + identifier;
    }
}
