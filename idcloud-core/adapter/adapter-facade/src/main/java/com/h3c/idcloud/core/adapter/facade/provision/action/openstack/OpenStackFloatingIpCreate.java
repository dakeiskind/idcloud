package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.FloatingIp;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpCreate;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpCreateResult;

import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/4/7.
 *
 * @author qct
 */
@Service
public class OpenStackFloatingIpCreate extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        Optional<FloatingIPApi> floatingIPApiOptional = NovaApiFactory.INSTANCE.createNovaApi(base)
                .getFloatingIPApi(base.getRegion());
        if(floatingIPApiOptional.isPresent()) {
            FloatingIP floatingIP = null;
            if(Strings.isNullOrEmpty(FloatingIpCreate.class.cast(base).getPool())) {
                floatingIP = floatingIPApiOptional.get().create();
            }else {
                floatingIP = floatingIPApiOptional.get().allocateFromPool(
                        FloatingIpCreate.class.cast(base).getPool());
            }
            if(floatingIP == null) {
                throw new KvmException("500", "No more floating ips available.");
            }
            FloatingIpCreateResult floatingIpCreateResult = new FloatingIpCreateResult();
            floatingIpCreateResult.setFloatingIpAddr(floatingIP.getIp());
            floatingIpCreateResult.setFloatingIpId(floatingIP.getId());
            floatingIpCreateResult.setSuccess(true);

            com.h3c.idcloud.core.adapter.facade.provision.model.network.FloatingIp resultFloatingIp = new FloatingIp();
            resultFloatingIp.setSuccess(true);
            resultFloatingIp.setFloatingIpCreateResult(floatingIpCreateResult);
            return resultFloatingIp;
        }else {
            throw new KvmException("500", "FloatingIPApi is absent");
        }
    }
}
