package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;

import com.h3c.idcloud.core.adapter.facade.common.NeutronApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddInterface;
import com.h3c.idcloud.core.adapter.pojo.network.RouterCreate;
import com.h3c.idcloud.core.adapter.pojo.network.RouterDelete;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveInterface;

import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.Ports;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.domain.RouterInterface;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by qct on 2016/4/15.
 *
 * @author qct
 */
@Service
public class OpenStackRouterAction extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        if (base instanceof RouterCreate) {
            Map<String, com.h3c.idcloud.core.adapter.pojo.network.Router> resultMap = Maps.newHashMap();
            resultMap.put("result", createRouter(base));
            return new CommonAdapterResult(true, resultMap);
        } else if (base instanceof RouterDelete) {
            return new CommonAdapterResult(deleteRouter(base));
        } else if (base instanceof RouterAddInterface) {
            Map<String, com.h3c.idcloud.core.adapter.pojo.network.Port> resultMap = Maps.newHashMap();
            resultMap.put("result", addInterfaceForSubnet(base));
            return new CommonAdapterResult(true, resultMap);
        } else if (base instanceof RouterAddExternalGateway) {
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("result", addExternalGateway(base));
            resultMap.put("port", queryExternalGatewayPort(base,
                    com.h3c.idcloud.core.adapter.pojo.network.Router.class.cast(resultMap.get("result"))));
            return new CommonAdapterResult(true, resultMap);
        } else if (base instanceof RouterRemoveExternalGateway) {
            return new CommonAdapterResult(removeExternalGateway(base));
        } else if (base instanceof RouterRemoveInterface) {
            return new CommonAdapterResult(removeRouterInterface(base));
        }
        return null;
    }

    private com.h3c.idcloud.core.adapter.pojo.network.Port queryExternalGatewayPort(
            Base base, com.h3c.idcloud.core.adapter.pojo.network.Router router) {
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("device_id", router.getId());
        multimap.put("network_id", router.getExternalGateway().getNetworkId());
        Ports ports = NeutronApiFactory.INSTANCE.createNeutronApi(base).getPortApi(base.getRegion())
                .list(PaginationOptions.Builder.queryParameters(multimap));
        if(ports.size() == 1) {
            return BaseUtil.assemblePort(ports.get(0));
        }
        return null;
    }

    private boolean removeRouterInterface(Base base) throws KvmException {
        Optional<RouterApi> routerApiOptional = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getRouterApi(base.getRegion());
        if (routerApiOptional.isPresent()) {
            return routerApiOptional.get().removeInterfaceForSubnet(RouterRemoveInterface.class.cast(base)
                    .getRouterId(),
                    RouterRemoveInterface.class.cast(base).getSubnetId());
        }else {
            throw new KvmException("500", "RouterApi is absent");
        }
    }

    private boolean removeExternalGateway(Base base) throws KvmException {
        Optional<RouterApi> routerApiOptional = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getRouterApi(base.getRegion());
        if (routerApiOptional.isPresent()) {
            Router.UpdateRouter updateRouter = routerApiOptional.get().get(
                    RouterRemoveExternalGateway.class.cast(base).getRouterId())
                    .updateBuilder()
                    .externalGatewayInfo(ExternalGatewayInfo.builder().build())
                    .build();
            Router update = routerApiOptional.get().update(
                    RouterRemoveExternalGateway.class.cast(base).getRouterId(), updateRouter);
            if(update.getExternalGatewayInfo() == null) {
                return true;
            }
        }else {
            throw new KvmException("500", "RouterApi is absent");
        }
        return false;
    }

    private com.h3c.idcloud.core.adapter.pojo.network.Router addExternalGateway(Base base) throws KvmException {
        Optional<RouterApi> routerApiOptional = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getRouterApi(base.getRegion());
        if (routerApiOptional.isPresent()) {
            Router router = routerApiOptional.get().update(RouterAddExternalGateway.class.cast(base).getRouterId(),
                    routerApiOptional.get().get(RouterAddExternalGateway.class.cast(base).getRouterId())
                            .updateBuilder().externalGatewayInfo(ExternalGatewayInfo.builder()
                            .networkId(RouterAddExternalGateway.class.cast(base).getExternalGateway().getNetworkId())
                            .enableSnat(RouterAddExternalGateway.class.cast(base).getExternalGateway().getEnableSnat())
                            .build()).build());
            return BaseUtil.assembleRouter(RouterAddExternalGateway.class.cast(base).getExternalGateway(), router);
        } else {
            throw new KvmException("500", "RouterApi is absent");
        }
    }

    private boolean deleteRouter(Base base) throws KvmException {
        Optional<RouterApi> routerApiOptional = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getRouterApi(base.getRegion());
        if (routerApiOptional.isPresent()) {
            return routerApiOptional.get().delete(RouterDelete.class.cast(base).getId());
        } else {
            throw new KvmException("500", "RouterApi is absent");
        }
    }

    private com.h3c.idcloud.core.adapter.pojo.network.Router createRouter(Base base) throws KvmException {
        Optional<RouterApi> routerApiOptional = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getRouterApi(base.getRegion());
        if (routerApiOptional.isPresent()) {
            Router.CreateBuilder createBuilder = Router.CreateRouter.createBuilder();
            createBuilder.name(RouterCreate.class.cast(base).getName()).adminStateUp(true).tenantId(base.getTenantId());
            if (RouterCreate.class.cast(base).getExternalGateway() != null) {
                createBuilder.externalGatewayInfo(ExternalGatewayInfo.builder()
                        .enableSnat(RouterCreate.class.cast(base).getExternalGateway().getEnableSnat())
                        .networkId(RouterCreate.class.cast(base).getExternalGateway().getNetworkId())
                        .build());
            }
            Router router = routerApiOptional.get().create(createBuilder.build());
            return BaseUtil.assembleRouter(RouterCreate.class.cast(base).getExternalGateway(), router);
        } else {
            throw new KvmException("500", "RouterApi is absent");
        }
    }

    private com.h3c.idcloud.core.adapter.pojo.network.Port addInterfaceForSubnet(Base base) throws KvmException {
        Optional<RouterApi> routerApiOptional = NeutronApiFactory.INSTANCE.createNeutronApi(base)
                .getRouterApi(base.getRegion());
        if (routerApiOptional.isPresent()) {
            RouterInterface routerInterface = routerApiOptional.get().addInterfaceForSubnet(
                    RouterAddInterface.class.cast(base).getRouterId(),
                    RouterAddInterface.class.cast(base).getSubnetId());

            return BaseUtil.assemblePort(NeutronApiFactory.INSTANCE.createNeutronApi(base).getPortApi(base.getRegion())
                    .get(routerInterface.getPortId()));
        } else {
            throw new KvmException("500", "RouterApi is absent");
        }
    }
}
