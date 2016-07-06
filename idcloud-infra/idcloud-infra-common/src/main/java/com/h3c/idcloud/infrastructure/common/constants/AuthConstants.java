package com.h3c.idcloud.infrastructure.common.constants;

import com.google.common.base.Strings;

import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;

import java.util.Base64;

/**
 * Created by qct on 2016/2/4.
 */
public class AuthConstants {
    public static long TTL_MILLIS = 86400000;
    public static String SUBJECT = "inner";
    public static String SECRET_KEY = Base64.getEncoder().encodeToString("secretKey".getBytes());
    public static final String CLAIMS_KEY = "claims";
    public static final int PERIED_TIME = 60 * 60;
    public static final String CACHE_KEY_USER_PREFIX = "portal:user:";

    static {
        if (!Strings.isNullOrEmpty(PropertiesUtil.getProperty("xxx.ttl.millis"))) {
            TTL_MILLIS = Long.valueOf(PropertiesUtil.getProperty("xxx.ttl.millis"));
        }
        //TODO 从配置文件或者数据库中读取配置替换默认值
    }
}
