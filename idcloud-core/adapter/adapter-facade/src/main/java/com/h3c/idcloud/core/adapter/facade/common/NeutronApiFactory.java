package com.h3c.idcloud.core.adapter.facade.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.neutron.v2.NeutronApi;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by qct on 2016/3/15.
 */
public enum NeutronApiFactory {
    INSTANCE;
    public static final String OPENSTACK_NEUTRON = "openstack-neutron";

    volatile static Cache<String, NeutronApi> neutronApiCache = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .weakKeys()
            .maximumSize(10000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    public NeutronApi createNeutronApi(Base base) {
        try {
            return neutronApiCache.get(getApiKey(base), () -> ContextBuilder.newBuilder(buildProvier(base))
                    .endpoint(base.getProviderUrl())
                    .credentials(base.getTenantName() + ":" + base.getTenantUserName(), base.getTenantUserPass())
                    .modules(ImmutableSet.of(new SLF4JLoggingModule()))
                    .buildApi(NeutronApi.class));
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getApiKey(Base base) {
        return base.getProviderUrl().trim() + "@" + base.getTenantId().trim() + ":" + base.getTenantUserId().trim();
    }

    private String buildProvier(Base base) {
        return OPENSTACK_NEUTRON;
    }
}
