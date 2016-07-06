package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;

import com.h3c.idcloud.core.adapter.facade.common.NeutronApiFactory;
import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpAttach;
import com.h3c.idcloud.core.adapter.pojo.network.NetworkStatus;

import org.apache.commons.beanutils.BeanUtils;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.RouterInterface;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by qct on 2016/4/7.
 *
 * @author qct
 */
@Service
public class OpenStackFloatingIpAttach extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        Optional<FloatingIPApi> floatingIPApiOptional = NovaApiFactory.INSTANCE.createNovaApi(base)
                .getFloatingIPApi(base.getRegion());
        if (floatingIPApiOptional.isPresent()) {
            CommonAdapterResult result = new CommonAdapterResult();
            if (!checkInterface2Router(base)) {
                Port port = NeutronApiFactory.INSTANCE.createNeutronApi(base).getPortApi(base.getRegion())
                        .get(createInterface2Router(base).getPortId());

                result.setMap(ImmutableMap.of("port", BaseUtil.assemblePort(port)));
            }
            if (Strings.isNullOrEmpty(FloatingIpAttach.class.cast(base).getFixedIp())) {
                floatingIPApiOptional.get().addToServer(FloatingIpAttach.class.cast(base).getFloatingIp(),
                        FloatingIpAttach.class.cast(base).getServerId());
            } else {
                floatingIPApiOptional.get().addToServer(FloatingIpAttach.class.cast(base).getFloatingIp(),
                        FloatingIpAttach.class.cast(base).getServerId(),
                        FloatingIpAttach.class.cast(base).getFixedIp());
            }
            result.setSuccess(true);
            return result;
        } else {
            throw new KvmException("500", "FloatingIPApi is absent");
        }
    }

    private RouterInterface createInterface2Router(Base base) throws KvmException {
        Optional<RouterApi> routerApiOptional = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getRouterApi(base.getRegion());
        if(routerApiOptional.isPresent()) {
            return routerApiOptional.get().addInterfaceForSubnet(
                    FloatingIpAttach.class.cast(base).getRouterId(), FloatingIpAttach.class.cast(base).getSubnetId());
        }else {
            throw new KvmException("500", "RouterApi is absent");
        }
    }

    private boolean checkInterface2Router(Base base) {
        HashMultimap<String, String> paramsMap = HashMultimap.create();
        paramsMap.put("network_id", FloatingIpAttach.class.cast(base).getNetworkId());
        paramsMap.put("device_id", FloatingIpAttach.class.cast(base).getRouterId());
        final boolean[] result = {false};
        NeutronApiFactory.INSTANCE.createNeutronApi(base).getPortApi(base.getRegion())
                .list(PaginationOptions.Builder.queryParameters(paramsMap))
                .forEach(port -> port.getFixedIps().forEach(ip -> {
                    if(ip.getSubnetId().equals(FloatingIpAttach.class.cast(base).getSubnetId())) {
                        result[0] = true;
                    }
                }));
        return result[0];
    }
}
