package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDetach;

import org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/4/7.
 *
 * @author qct
 */
@Service
public class OpenStackFloatingIpDetach extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        Optional<FloatingIPApi> floatingIPApiOptional = NovaApiFactory.INSTANCE.createNovaApi(base)
                .getFloatingIPApi(base.getRegion());
        if(floatingIPApiOptional.isPresent()) {
            floatingIPApiOptional.get().removeFromServer(FloatingIpDetach.class.cast(base).getFloatingIp(),
                    FloatingIpDetach.class.cast(base).getServerId());
            CommonAdapterResult result = new CommonAdapterResult();
            result.setSuccess(true);
            return result;
        }else {
            throw new KvmException("500", "FloatingIPApi is absent");
        }
    }
}
