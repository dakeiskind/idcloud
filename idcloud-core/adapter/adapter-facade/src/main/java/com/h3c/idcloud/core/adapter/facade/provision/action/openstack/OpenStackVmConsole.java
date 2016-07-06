package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.VmConsole;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmVncConsole;

import org.jclouds.openstack.nova.v2_0.domain.Console;
import org.jclouds.openstack.nova.v2_0.extensions.ConsolesApi;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/3/31.
 *
 * @author qct
 */
@Service
public class OpenStackVmConsole extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        Optional<ConsolesApi> consolesApiOptional = NovaApiFactory.INSTANCE.createNovaApi(base)
                .getConsolesApi(base.getRegion());
        VmConsole resultConsole = new VmConsole();
        if (consolesApiOptional.isPresent()) {
            VmVncConsole vmVncConsole = (VmVncConsole) base;
            Console console = consolesApiOptional.get().getConsole(vmVncConsole.getId(), Console.Type.NOVNC);
            resultConsole.setType(Console.Type.NOVNC.name());
            resultConsole.setUrl(console.getUrl().toString());
            resultConsole.setSuccess(true);
        } else {
            throw new KvmException("500", "ConsolesApi is absent");
        }
        return resultConsole;
    }
}
