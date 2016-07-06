package com.h3c.idcloud.core.adapter.facade.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by qct on 2016/2/23.
 */
public enum NovaApiFactory implements Serializable {
    INSTANCE;

    public static final String OPENSTACK_NOVA = "openstack-nova";
    volatile static Cache<String, NovaApi> novaApiCache = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .weakKeys()
            .maximumSize(10000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    public NovaApi createNovaApi(Base base) {
        try {
            return novaApiCache.get(getApiKey(base), () -> ContextBuilder.newBuilder(buildProvier(base))
                    .endpoint(base.getProviderUrl())
                    .credentials(base.getTenantName() + ":" + base.getTenantUserName(), base.getTenantUserPass())
                    .modules(ImmutableSet.of(new SLF4JLoggingModule()))
                    .buildApi(NovaApi.class));
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getApiKey(Base base) {
        return base.getProviderUrl().trim() + "@" + base.getTenantId().trim() + ":" + base.getTenantUserId().trim();
    }

    private String buildProvier(Base base) {
//        if(base.getClass().getSimpleName().startsWith("Vm")) {
//            return OPENSTACK_NOVA;
//        }
        return OPENSTACK_NOVA;
    }
}
