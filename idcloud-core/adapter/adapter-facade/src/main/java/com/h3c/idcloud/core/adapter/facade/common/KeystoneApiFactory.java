package com.h3c.idcloud.core.adapter.facade.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.v2_0.KeystoneApi;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by qct on 2016/3/15.
 */
public enum KeystoneApiFactory {
    INSTANCE;
    public static final String OPENSTACK_KEYSTONE = "openstack-keystone";

    volatile static Cache<String, KeystoneApi> keystoneApiCache = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .weakKeys()
            .maximumSize(10000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    public KeystoneApi createKeystoneApi(Base base) {
        try {
            return keystoneApiCache.get(getApiKey(base), () -> ContextBuilder.newBuilder(buildProvier(base))
                    .endpoint(base.getAuthUrl())
                    .credentials(base.getAuthTenant() + ":" + base.getAuthUser(), base.getAuthPass())
                    .modules(ImmutableSet.of(new SLF4JLoggingModule()))
                    .buildApi(KeystoneApi.class));
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getApiKey(Base base) {
        return base.getAuthUrl().trim() + "@" +
                base.getAuthTenant().trim() + ":" + base.getAuthUser().trim() + "@" +
                base.getAuthPass().trim();
    }

    private String buildProvier(Base base) {
        return OPENSTACK_KEYSTONE;
    }
}
