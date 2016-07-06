package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionNetwork;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSgRuleDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackFloatingIpAttach;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackFloatingIpCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackFloatingIpDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackFloatingIpDetach;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackNetCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackNetDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackRouterAction;
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
public class ProvisionNetworkImpl implements ProvisionNetwork {

    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSecurityGroupAdd KvmSecurityGroupAdd;

    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSecurityGroupDelete KvmSecurityGroupDelete;

    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSgRuleCreate KvmSgRuleCreate;

    @Autowired
    private KvmSgRuleDelete kvmSgRuleDelete;

    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmNetCreate KvmNetCreate;

    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSgCreate KvmSgCreate;

    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSgDelete KvmSgDelete;

    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSgRuleListQuery KvmSgRuleListQuery;

    //jclouds
    @Autowired
    private OpenStackNetCreate openStackNetCreate;
    @Autowired
    private OpenStackFloatingIpCreate openStackFloatingIpCreate;
    @Autowired
    private OpenStackFloatingIpDelete openStackFloatingIpDelete;
    @Autowired
    private OpenStackFloatingIpAttach openStackFloatingIpAttach;
    @Autowired
    private OpenStackFloatingIpDetach openStackFloatingIpDetach;
    @Autowired
    private OpenStackNetDelete openStackNetDelete;
    @Autowired
    private OpenStackRouterAction openStackRouterAction;

    @Override
    public CommonAdapterResult createNet(NetCreate netCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackNetCreate.invoke(netCreate);
    }

    @Override
    public List<Network> queryNets(NetQuery netQuery) throws CommonAdapterException {
        return null;
    }

    @Override
    public Network getNet(NetGet netGet) throws CommonAdapterException {
        return null;
    }

    @Override
    public CommonAdapterResult deleteNet(NetDelete netDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackNetDelete.invoke(netDelete);
    }

    @Override
    public List<SecurityGroup> querySecurityGroups(SgQuery sgQuery) throws CommonAdapterException {
        return null;
    }

    @Override
    public SecurityGroup getSecurityGroup(SgGet sgGet) throws CommonAdapterException {
        return null;
    }

    @Override
    public boolean deleteSecurityGroup(SgDelete sgDelete) throws CommonAdapterException, AdapterUnavailableException {
        return KvmSgDelete.invoke(sgDelete).isSuccess();
    }

    @Override
    public SecurityGroupResult createSecurityGroup(SgCreate sgCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        SecurityGroupResult securityGroupResult = (SecurityGroupResult) KvmSgCreate.invoke(sgCreate);
        return securityGroupResult;
    }

    @Override
    public FloatingIpCreateResult createFloatingIp(FloatingIpCreate floatingIpCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return com.h3c.idcloud.core.adapter.facade.provision.model.network.FloatingIp
                .class.cast(openStackFloatingIpCreate.invoke(floatingIpCreate)).getFloatingIpCreateResult();
    }

    @Override
    public FloatingIp getFloatingIp(FloatingIpGet floatingIpGet) throws CommonAdapterException {
        return null;
    }

    @Override
    public List<FloatingIp> queryFloatingIps(FloatingIpQuery floatingQuery) throws CommonAdapterException {
        return null;
    }

    @Override
    public boolean deleteFloatingIp(FloatingIpDelete floatingIpDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackFloatingIpDelete.invoke(floatingIpDelete).isSuccess();
    }

    @Override
    public List<FloatingIpPool> queryFloatingIpPools(FloatingIpPoolQuery floatingIpPoolQuery)
            throws CommonAdapterException {
        return null;
    }

    @Override
    public FloatingIpBulk createFloatingIpBulk(FloatingIpBulkCreate floatingIpBulkCreate)
            throws CommonAdapterException {
        return null;
    }

    @Override
    public boolean deleteFloatingIpBulk(FloatingIpBulkDelete floatingIpBulkDelete) throws CommonAdapterException {
        return false;
    }

    @Override
    public List<FloatingIpBulkDetail> queryFloatingIpBulks(FloatingIpBulkQuery floatingIpBulkQuery)
            throws CommonAdapterException {
        return null;
    }

    @Override
    public CommonAdapterResult attachFloatingIp(FloatingIpAttach floatingIpAttach)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackFloatingIpAttach.invoke(floatingIpAttach);
    }

    @Override
    public boolean detachFloatingIp(FloatingIpDetach floatingIpDetach)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackFloatingIpDetach.invoke(floatingIpDetach).isSuccess();
    }

    @Override
    public boolean attachSecurityGroup(ServerSecurityGroupAdd securityGroupAdd)
            throws CommonAdapterException, AdapterUnavailableException {
        return KvmSecurityGroupAdd.invoke(securityGroupAdd).isSuccess();
    }

    @Override
    public boolean detachSecurityGroup(ServerSecurityGroupDelete securityGroupDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return KvmSecurityGroupDelete.invoke(securityGroupDelete).isSuccess();
    }

    @Override
    public SecurityGroupRulesResult createSgRule(SgRuleCreate sgRuleCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return (SecurityGroupRulesResult) KvmSgRuleCreate.invoke(sgRuleCreate);
    }

    @Override
    public boolean deleteSgRule(SgRuleDelete sgRuleDelete) throws CommonAdapterException, AdapterUnavailableException {
        return kvmSgRuleDelete.invoke(sgRuleDelete).isSuccess();
    }

    @Override
    public com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup querySgRuleList(
            SgRuleListQuery sgRuleListQuery) throws CommonAdapterException, AdapterUnavailableException {
        com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup securityGroup =
                (com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup) KvmSgRuleListQuery
                        .invoke(sgRuleListQuery);
        return securityGroup;
    }

    @Override
    public CommonAdapterResult createRouter(RouterCreate routerCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackRouterAction.invoke(routerCreate);
    }

    @Override
    public CommonAdapterResult deleteRouter(RouterDelete routerDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackRouterAction.invoke(routerDelete);
    }

    @Override
    public CommonAdapterResult addRouterInterface(RouterAddInterface routerAddInterface)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackRouterAction.invoke(routerAddInterface);
    }

    @Override
    public CommonAdapterResult addRouterExternalGateway(RouterAddExternalGateway routerAddExternalGateway)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackRouterAction.invoke(routerAddExternalGateway);
    }

    @Override
    public CommonAdapterResult removeRouterInterface(RouterRemoveInterface routerRemoveInterface)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackRouterAction.invoke(routerRemoveInterface);
    }

    @Override
    public CommonAdapterResult removeExternalGateway(RouterRemoveExternalGateway routerRemoveExternalGateway)
            throws CommonAdapterException, AdapterUnavailableException {
        return openStackRouterAction.invoke(routerRemoveExternalGateway);
    }
}
