package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;

import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/2/24.
 *
 * @author qct-office
 */
@Service
public class OpenStackVmRemove extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException,
            AdapterUnavailableException {
        VmRemove vmRemove = (VmRemove) base;
        ServerApi serverApi = NovaApiFactory.INSTANCE.createNovaApi(base).getServerApi(base.getRegion());
        CommonAdapterResult result = new CommonAdapterResult();
        result.setSuccess(serverApi.delete(vmRemove.getId()));
        return result;
    }
}
