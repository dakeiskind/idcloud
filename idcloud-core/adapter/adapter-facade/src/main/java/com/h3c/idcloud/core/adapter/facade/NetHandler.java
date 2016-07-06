package com.h3c.idcloud.core.adapter.facade;

import com.h3c.idcloud.core.adapter.facade.common.ProviderFactory;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionNetwork;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroupResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroupRulesResult;
import com.h3c.idcloud.core.adapter.pojo.admin.SgCreate;
import com.h3c.idcloud.core.adapter.pojo.admin.SgDelete;
import com.h3c.idcloud.core.adapter.pojo.admin.SgGet;
import com.h3c.idcloud.core.adapter.pojo.admin.SgQuery;
import com.h3c.idcloud.core.adapter.pojo.admin.result.SecurityGroup;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpAttach;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpBulkCreate;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpBulkDelete;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpBulkQuery;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpCreate;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDelete;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDetach;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpGet;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpPoolQuery;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpQuery;
import com.h3c.idcloud.core.adapter.pojo.network.NetCreate;
import com.h3c.idcloud.core.adapter.pojo.network.NetDelete;
import com.h3c.idcloud.core.adapter.pojo.network.NetGet;
import com.h3c.idcloud.core.adapter.pojo.network.NetQuery;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddInterface;
import com.h3c.idcloud.core.adapter.pojo.network.RouterCreate;
import com.h3c.idcloud.core.adapter.pojo.network.RouterDelete;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveInterface;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupAdd;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupDelete;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleCreate;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleDelete;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleListQuery;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIp;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpBulk;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpBulkDetail;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpPool;
import com.h3c.idcloud.core.adapter.pojo.network.result.Network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetHandler implements ProvisionNetwork {
    @Autowired
    private ProviderFactory provider;

    public boolean createNet(Network network) throws CommonAdapterException {
        return false;
    }

    public List<Network> queryNets(NetQuery netQuery)
            throws CommonAdapterException {
        return provider.getProvisionNetwork(netQuery.getProviderType()).queryNets(netQuery);
    }

    public Network getNet(NetGet netGet) throws CommonAdapterException {
        return provider.getProvisionNetwork(netGet.getProviderType()).getNet(netGet);
    }

    public CommonAdapterResult createNet(NetCreate netCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(netCreate.getProviderType()).createNet(netCreate);
    }

    public CommonAdapterResult deleteNet(NetDelete netDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(netDelete.getProviderType()).deleteNet(netDelete);
    }

    public List<SecurityGroup> querySecurityGroups(SgQuery sgQuery) throws CommonAdapterException {
        return provider.getProvisionNetwork(sgQuery.getProviderType()).querySecurityGroups(sgQuery);
    }

    public SecurityGroup getSecurityGroup(SgGet sgGet) throws CommonAdapterException {
        return provider.getProvisionNetwork(sgGet.getProviderType()).getSecurityGroup(sgGet);
    }

    public boolean deleteSecurityGroup(SgDelete sgDelete) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(sgDelete.getProviderType()).deleteSecurityGroup(sgDelete);
    }

    public SecurityGroupResult createSecurityGroup(SgCreate sgCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(sgCreate.getProviderType()).createSecurityGroup(sgCreate);
    }

    public FloatingIpCreateResult createFloatingIp(FloatingIpCreate floatingIpCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(floatingIpCreate.getProviderType()).createFloatingIp(floatingIpCreate);
    }

    public FloatingIp getFloatingIp(FloatingIpGet floatingIpGet) throws CommonAdapterException {
        return provider.getProvisionNetwork(floatingIpGet.getProviderType()).getFloatingIp(floatingIpGet);
    }

    public List<FloatingIp> queryFloatingIps(FloatingIpQuery floatingQuery) throws CommonAdapterException {
        return provider.getProvisionNetwork(floatingQuery.getProviderType()).queryFloatingIps(floatingQuery);
    }

    public boolean deleteFloatingIp(FloatingIpDelete floatingIpDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(floatingIpDelete.getProviderType()).deleteFloatingIp(floatingIpDelete);
    }

    public List<FloatingIpPool> queryFloatingIpPools(FloatingIpPoolQuery floatingIpPoolQuery)
            throws CommonAdapterException {
        return provider.getProvisionNetwork(floatingIpPoolQuery.getProviderType())
                .queryFloatingIpPools(floatingIpPoolQuery);

    }

    public FloatingIpBulk createFloatingIpBulk(FloatingIpBulkCreate floatingIpBulkCreate)
            throws CommonAdapterException {
        return provider.getProvisionNetwork(floatingIpBulkCreate.getProviderType())
                .createFloatingIpBulk(floatingIpBulkCreate);
    }

    public boolean deleteFloatingIpBulk(FloatingIpBulkDelete floatingIpBulkDelete) throws CommonAdapterException {
        return provider.getProvisionNetwork(floatingIpBulkDelete.getProviderType())
                .deleteFloatingIpBulk(floatingIpBulkDelete);
    }

    public List<FloatingIpBulkDetail> queryFloatingIpBulks(FloatingIpBulkQuery floatingIpBulkQuery)
            throws CommonAdapterException {
        return provider.getProvisionNetwork(floatingIpBulkQuery.getProviderType())
                .queryFloatingIpBulks(floatingIpBulkQuery);
    }

    public CommonAdapterResult attachFloatingIp(FloatingIpAttach floatingIpAttach)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(floatingIpAttach.getProviderType()).attachFloatingIp(floatingIpAttach);
    }

    public boolean detachFloatingIp(FloatingIpDetach floatingIpDetach)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(floatingIpDetach.getProviderType()).detachFloatingIp(floatingIpDetach);
    }

    @Override
    public boolean attachSecurityGroup(ServerSecurityGroupAdd securityGroupAdd)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(securityGroupAdd.getProviderType()).attachSecurityGroup(securityGroupAdd);
    }

    @Override
    public boolean detachSecurityGroup(ServerSecurityGroupDelete securityGroupDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(securityGroupDelete.getProviderType())
                .detachSecurityGroup(securityGroupDelete);
    }

    @Override
    public SecurityGroupRulesResult createSgRule(SgRuleCreate sgRuleCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(sgRuleCreate.getProviderType()).createSgRule(sgRuleCreate);
    }

    @Override
    public boolean deleteSgRule(SgRuleDelete sgRuleDelete) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(sgRuleDelete.getProviderType()).deleteSgRule(sgRuleDelete);
    }

    @Override
    public com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup querySgRuleList(
            SgRuleListQuery sgRuleListQuery) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(sgRuleListQuery.getProviderType()).querySgRuleList(sgRuleListQuery);
    }

    @Override
    public CommonAdapterResult createRouter(RouterCreate routerCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(routerCreate.getProviderType()).createRouter(routerCreate);
    }

    @Override
    public CommonAdapterResult deleteRouter(RouterDelete routerDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(routerDelete.getProviderType()).deleteRouter(routerDelete);
    }

    @Override
    public CommonAdapterResult addRouterInterface(RouterAddInterface routerAddInterface)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(routerAddInterface.getProviderType())
                .addRouterInterface(routerAddInterface);
    }

    @Override
    public CommonAdapterResult addRouterExternalGateway(RouterAddExternalGateway routerAddExternalGateway)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(routerAddExternalGateway.getProviderType())
                .addRouterExternalGateway(routerAddExternalGateway);
    }

    @Override
    public CommonAdapterResult removeRouterInterface(RouterRemoveInterface routerRemoveInterface)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(routerRemoveInterface.getProviderType())
                .removeRouterInterface(routerRemoveInterface);
    }

    @Override
    public CommonAdapterResult removeExternalGateway(RouterRemoveExternalGateway routerRemoveExternalGateway)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionNetwork(routerRemoveExternalGateway.getProviderType())
                .removeExternalGateway(routerRemoveExternalGateway);
    }
}
