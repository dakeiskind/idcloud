package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.collect.Lists;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Servers;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuery;

import org.jclouds.openstack.nova.v2_0.domain.Server;

import java.util.stream.Collectors;

/**
 * Created by qct on 2016/2/23.
 *
 * @author qct-office
 */
public class OpenStackVmQuery extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException,
            AdapterUnavailableException {
        Server openstackServer = NovaApiFactory.INSTANCE.createNovaApi(base).getServerApi(base.getRegion())
                .get(((VmQuery) base).getServerId());
        Servers servers = new Servers();

        servers.setServers(Lists.newArrayList(openstackServer).stream().map(novaServer -> {
            if (novaServer == null) {
                return null;
            }
            com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server targetServer =
                    new com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server();
            targetServer.setUuid(novaServer.getId());
            targetServer.setStatus(novaServer.getStatus().value());
            if (novaServer.getExtendedAttributes().isPresent()) {
                targetServer.setHost(novaServer.getExtendedAttributes().get().getHostName());
                targetServer.setHostName(novaServer.getExtendedAttributes().get().getHostName());
            }
            targetServer.setName(novaServer.getName());

            System.out.println("nova server: " + novaServer.toString());
            System.out.println("target server: " + targetServer.toString());
            return targetServer;
        })
                .collect(Collectors.toList()));
        return servers;
    }
}
