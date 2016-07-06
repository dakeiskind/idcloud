package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;

import com.h3c.idcloud.core.adapter.facade.common.KeystoneApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant.Tenants;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantCreate;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantCreateResult;

import org.jclouds.openstack.keystone.v2_0.domain.Tenant;
import org.jclouds.openstack.keystone.v2_0.extensions.TenantAdminApi;
import org.jclouds.openstack.keystone.v2_0.options.CreateTenantOptions;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/3/15.
 *
 * @author qct-office
 */
@Service
public class OpenStackTenantCreate extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException,
            AdapterUnavailableException {
        Optional<? extends TenantAdminApi> tenantAdminApiOptional = KeystoneApiFactory.INSTANCE
                .createKeystoneApi(base).getTenantAdminApi();
        if (tenantAdminApiOptional.isPresent()) {
            TenantCreate tenantCreate = (TenantCreate) base;
            CreateTenantOptions createTenantOptions = CreateTenantOptions.Builder
                    .description(tenantCreate.getDescription())
                    .enabled(tenantCreate.isEnable());
            Tenant tenant = tenantAdminApiOptional.get().create(tenantCreate.getName(),
                    createTenantOptions);
            TenantCreateResult result = new TenantCreateResult();
            result.setSuccess(true);
            result.setEnable(tenant.isEnabled());
            result.setId(tenant.getId());
            result.setDescription(tenant.getDescription());
            Tenants tenants = new Tenants();
            tenants.setTenantCreateResult(result);
            return tenants;
        } else {
            throw new KvmException("500", "TenantAdminApi is *not* present.");
        }
    }
}
