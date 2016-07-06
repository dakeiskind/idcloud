package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;

import com.h3c.idcloud.core.adapter.facade.common.NeutronApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.ExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.NetCreate;
import com.h3c.idcloud.core.adapter.pojo.network.NetworkStatus;

import org.apache.commons.beanutils.BeanUtils;
import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.IpVersion;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.NetworkType;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

/**
 * Created by qct on 2016/3/15.
 *
 * @author qct
 */
@Service
public class OpenStackNetCreate extends ActionKvm {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(OpenStackNetCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException,
            AdapterUnavailableException {
        logger.debug(base.toString());

        if (NetCreate.class.cast(base).getSubnet() == null) {
            return initNetwork(base);
        } else {
            com.h3c.idcloud.core.adapter.facade.provision.model.network.Network resultNetwork =
                    com.h3c.idcloud.core.adapter.facade.provision.model.network.Network.builder().build();
            resultNetwork.setSubnet(createSubnet(base, NetCreate.class.cast(base).getId()));
            resultNetwork.setId(NetCreate.class.cast(base).getId());
            resultNetwork.setName(NetCreate.class.cast(base).getName());
            return resultNetwork;
        }
    }

    private com.h3c.idcloud.core.adapter.facade.provision.model.network.Network initNetwork(Base base)
            throws KvmException {
        com.h3c.idcloud.core.adapter.facade.provision.model.network.Network resultNetwork = createNetwork(base);

        resultNetwork.setSubnet(createSubnet(base, resultNetwork.getId()));

        if (NetCreate.class.cast(base).getRouter() != null) {
            resultNetwork.setRouter(createRouter(base));
        }

        HashMultimap<String, String> paramsMap = HashMultimap.create();
        paramsMap.put("network_id", resultNetwork.getId());
        // find ports belong to the network
        resultNetwork.setPorts(ImmutableSet.copyOf(NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getPortApi(base.getRegion())
                .list(PaginationOptions.Builder.queryParameters(paramsMap))
                .toSet()
                .stream()
                .map(port -> BaseUtil.assemblePort(port))
                .collect(Collectors.toSet()))
        );

        resultNetwork.setSuccess(true);
        return resultNetwork;
    }


    private com.h3c.idcloud.core.adapter.facade.provision.model.network.Network createNetwork(Base base) {
        Network network = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getNetworkApi(base.getRegion()).create(Network.createBuilder(NetCreate.class.cast(base).getName())
                        .networkType(NetworkType.VXLAN)
                        .tenantId(NetCreate.class.cast(base).getTenantId()).build());
        return BaseUtil.assembleNetwork(network);
    }



    private com.h3c.idcloud.core.adapter.pojo.network.Router createRouter(Base base) throws KvmException {
        Router publicRouter = createPublicRouter(base);
        com.h3c.idcloud.core.adapter.pojo.network.Router router =
                com.h3c.idcloud.core.adapter.pojo.network.Router.builder().build();
        try {
            router.setNetworkStatus(NetworkStatus.fromValue(publicRouter
                    .getStatus().name()));
            BeanUtils.copyProperties(router, publicRouter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        router.setExternalGateway(ExternalGateway.builder()
                .enableSnat(publicRouter.getExternalGatewayInfo().getEnableSnat())
                .networkId(publicRouter.getExternalGatewayInfo().getNetworkId()).build()
        );
        return router;
    }

    private com.h3c.idcloud.core.adapter.pojo.network.Subnet createSubnet(Base base, String networkId) {
        com.h3c.idcloud.core.adapter.pojo.network.Subnet resultSubnet =
                com.h3c.idcloud.core.adapter.pojo.network.Subnet.builder().build();
        Subnet subnet = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getSubnetApi(base.getRegion()).create(
                        Subnet.createBuilder(networkId,
                                Strings.isNullOrEmpty(NetCreate.class.cast(base).getCidr()) ?
                                        NetCreate.class.cast(base).getSubnet().getCidr() :
                                        NetCreate.class.cast(base).getCidr())
                                .ipVersion(IpVersion.IPV4.version()).build());
        try {
            BeanUtils.copyProperties(resultSubnet, subnet);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return resultSubnet;
    }

    private Router createPublicRouter(Base base) throws KvmException {
        Optional<RouterApi> routerApiOptional = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getRouterApi(base.getRegion());
        if (routerApiOptional.isPresent()) {
            com.h3c.idcloud.core.adapter.pojo.network.Router router2Create = NetCreate.class.cast(base).getRouter();
            router2Create.setTenantId(base.getTenantId());
            return routerApiOptional.get().create(
                    Router.CreateRouter.createBuilder()
                            .name(router2Create.getName())
                            .adminStateUp(true)
                            .tenantId(base.getTenantId())
                            .externalGatewayInfo(ExternalGatewayInfo.builder()
                                    .enableSnat(router2Create.getExternalGateway().getEnableSnat())
                                    .networkId(router2Create.getExternalGateway().getNetworkId())
                                    .build()
                            )
                            .build());
        } else {
            throw new KvmException("500", "RouterApi is absent");
        }
    }
}
