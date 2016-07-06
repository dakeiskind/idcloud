package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;

import com.h3c.idcloud.core.adapter.facade.common.KeystoneApiFactory;
import com.h3c.idcloud.core.adapter.facade.common.NeutronApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.AbstractAction;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.util.FreeMarkerHelper;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.infrastructure.common.cache.JedisUtil;
import com.h3c.idcloud.infrastructure.common.constants.CacheConstants;

import org.apache.http.HttpStatus;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.jclouds.http.HttpResponseException;
import org.jclouds.openstack.keystone.v2_0.domain.Role;
import org.jclouds.openstack.keystone.v2_0.domain.Tenant;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.keystone.v2_0.extensions.RoleAdminApi;
import org.jclouds.openstack.keystone.v2_0.extensions.TenantAdminApi;
import org.jclouds.openstack.keystone.v2_0.extensions.UserAdminApi;
import org.jclouds.openstack.keystone.v2_0.features.TenantApi;
import org.jclouds.openstack.keystone.v2_0.features.UserApi;
import org.jclouds.openstack.keystone.v2_0.options.CreateTenantOptions;
import org.jclouds.openstack.keystone.v2_0.options.CreateUserOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * default implementation of Action Created by qct on 2016/3/18.
 *
 * @author qct
 */
public abstract class ActionKvm extends AbstractAction {

    /**
     * 创建用户和租户时候的重试次数
     */
    private static final int RETRY_TIMES = 3;

    /**
     * log
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected FreeMarkerHelper helper;

    protected List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();

    Properties adapter = new Properties();

    private String adapterUrl = null;

    public ActionKvm() {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        providers.add(jsonProvider);

        URL adapter_ = super.getClass().getResource("/adapter.properties");

        try {
            adapter.load(adapter_.openStream());
            setAdapterUrl(adapter.getProperty("kvm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeExec(Base base) throws KvmException {
        log.debug(base.toString());
        checkParam(base);
        for(int i=0;i<RETRY_TIMES;i++) {
            try {
                checkTenant(base);
                checkUser(base);
            }catch (KvmException e) {
                if(String.valueOf(HttpStatus.SC_CONFLICT).equals(e.getErrCode())) {
                    continue;
                }else {
                    throw e;
                }
            }
            break;
        }
    }

    /**
     * check parameters
     *
     * @param base action base
     * @throws KvmException kvm exception
     */
    protected void checkParam(Base base) throws KvmException {
        if (base == null) {
            throw new KvmException("500", "base is absent");
        }
        if (Strings.isNullOrEmpty(base.getTenantUserName())) {
            throw new KvmException("500", "TenantUserName is absent");
        }
        if (Strings.isNullOrEmpty(base.getTenantName())) {
            throw new KvmException("500", "TenantName is absent");
        }
        if (Strings.isNullOrEmpty(base.getTenantUserPass())) {
            throw new KvmException("500", "TenantUserPass is absent");
        }
        if (Strings.isNullOrEmpty(base.getRegion())) {
            throw new KvmException("500", "region is absent");
        }
    }

    @Override
    public void afterExec(Base base) throws CommonAdapterException {

    }

    public String getAdapterUrl() {
        return adapterUrl;
    }

    private void setAdapterUrl(String adapterUrl) {
        this.adapterUrl = adapterUrl;
    }

    /**
     * check user, if there is no user in openstack, create a new one, add admin role to the newly created user
     *
     * @throws KvmException kvm exception
     */
    protected void checkUser(Base base) throws KvmException {
        if (Strings.isNullOrEmpty(JedisUtil.instance().hget(CacheConstants.redisUserKey(base.getVirtEnvUuid(),
                base.getTenantUserName()),"openstack.id"))) {
            Optional<? extends UserApi> userApiOptional = KeystoneApiFactory.INSTANCE.createKeystoneApi(base)
                    .getUserApi();
            if (userApiOptional.isPresent()) {
                User openstackUser = userApiOptional.get().getByName(base.getTenantUserName());
                if (openstackUser == null) {
                    log.debug("user did't exists, will create a new user of ({})", base.getTenantUserName());
                    Optional<? extends UserAdminApi> userAdminApiOptional = KeystoneApiFactory.INSTANCE
                            .createKeystoneApi(base).getUserAdminApi();
                    if (userAdminApiOptional.isPresent()) {
                        CreateUserOptions createUserOptions = CreateUserOptions.Builder.enabled(true)
                                .tenant(base.getTenantId());

                        User user;
                        try {
                            user = userAdminApiOptional.get().create(base.getTenantUserName(),
                                    base.getTenantUserPass(), createUserOptions);
                        }catch (HttpResponseException e) {
                            if(e.getResponse().getStatusCode() == HttpStatus.SC_CONFLICT) {
                                throw new KvmException("409", e.getMessage());
                            }else {
                                throw e;
                            }
                        }

                        base.setTenantUserId(user.getId());
                        base.setTenantUserName(user.getName());
                        addAdminRoleOnTenant(base, user);
                        JedisUtil.instance().hset(CacheConstants.redisUserKey(base.getVirtEnvUuid(),
                                base.getTenantUserName()),"openstack.id", user.getId());
                    } else {
                        throw new KvmException("500", "user admin api is absent");
                    }
                } else {
                    base.setTenantUserId(openstackUser.getId());
                    JedisUtil.instance().hset(CacheConstants.redisUserKey(base.getVirtEnvUuid(),
                            base.getTenantUserName()), "openstack.id", openstackUser.getId());
                }
            } else {
                throw new KvmException("500", "user api is absent");
            }
        }else {
            base.setTenantUserId(JedisUtil.instance().hget(CacheConstants.redisUserKey(base.getVirtEnvUuid(),
                    base.getTenantUserName()),"openstack.id"));
        }
    }

    /**
     * add a role of admin on the tenant
     *
     * @param base action base
     * @param user user
     * @throws KvmException kvm exception
     */
    private void addAdminRoleOnTenant(Base base, User user) throws KvmException {
        Optional<? extends RoleAdminApi> roleAdminApiOptional = KeystoneApiFactory.INSTANCE.createKeystoneApi(base)
                .getRoleAdminApi();
        if (roleAdminApiOptional.isPresent()) {
            Optional<? extends Role> roleOptional = roleAdminApiOptional.get().list().firstMatch(new Predicate<Role>() {
                @Override
                public boolean apply(Role role) {
                    return "admin".equals(role.getName());
                }
            });
            if (roleOptional.isPresent()) {
                Optional<? extends TenantAdminApi> tenantAdminApiOptional = KeystoneApiFactory.INSTANCE
                        .createKeystoneApi(base).getTenantAdminApi();
                if (tenantAdminApiOptional.isPresent()) {
                    tenantAdminApiOptional.get().addRoleOnTenant(base.getTenantId(), user.getId(), roleOptional.get()
                            .getId());
                }
            }
        } else {
            throw new KvmException("500", "role admin api is absent");
        }
    }

    /**
     * check tenant, if there is no tenant in openstack, create a new one.
     *
     * @param base action base
     * @throws KvmException kvm exception
     */
    protected void checkTenant(Base base) throws KvmException {
        Optional<? extends TenantAdminApi> tenantAdminApiOptional;
        if (Strings.isNullOrEmpty(JedisUtil.instance().hget(
                CacheConstants.redisTenantKey(base.getVirtEnvUuid(), base.getTenantName()), "openstack.id"))) {
            Optional<? extends TenantApi> tenantApiOptional = KeystoneApiFactory.INSTANCE
                    .createKeystoneApi(base).getTenantApi();
            if (tenantApiOptional.isPresent()) {
                Tenant openstackTenant = tenantApiOptional.get().getByName(base.getTenantName());
                if (openstackTenant == null) {
                    log.debug("tenant did't exists, will create a new tenant of ({})", base.getTenantName());
                    tenantAdminApiOptional = KeystoneApiFactory.INSTANCE.createKeystoneApi(base).getTenantAdminApi();
                    if (tenantAdminApiOptional.isPresent()) {
                        CreateTenantOptions createTenantOptions = CreateTenantOptions.Builder
                                .enabled(true);
                        Tenant tenant;
                        try {
                            tenant = tenantAdminApiOptional.get().create(base.getTenantName(), createTenantOptions);
                        }catch (HttpResponseException e) {
                            if(e.getResponse().getStatusCode() == HttpStatus.SC_CONFLICT) {
                                throw new KvmException("409", e.getMessage());
                            }else {
                                throw e;
                            }
                        }
                        base.setTenantId(tenant.getId());
                        JedisUtil.instance().hset(CacheConstants.redisTenantKey(base.getVirtEnvUuid(),
                                base.getTenantName()), "openstack.id", tenant.getId());
                    } else {
                        throw new KvmException("500", "tenant admin api is absent");
                    }
                } else {
                    base.setTenantId(openstackTenant.getId());
                    JedisUtil.instance().hset(CacheConstants.redisTenantKey(base.getVirtEnvUuid(),
                            base.getTenantName()), "openstack.id",openstackTenant.getId());
                }
            } else {
                throw new KvmException("500", "tenant api is absent");
            }
        } else {
            base.setTenantId(JedisUtil.instance().hget(CacheConstants.redisTenantKey(base.getVirtEnvUuid(),
                    base.getTenantName()), "openstack.id"));
        }
    }
}
