package com.h3c.idcloud.core.adapter.facade.provision;

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

import java.util.List;

public interface ProvisionNetwork {

    CommonAdapterResult createNet(NetCreate netCreate) throws CommonAdapterException, AdapterUnavailableException;

    List<Network> queryNets(NetQuery netQuery) throws CommonAdapterException;

    Network getNet(NetGet netGet) throws CommonAdapterException;

    CommonAdapterResult deleteNet(NetDelete netDelete) throws CommonAdapterException, AdapterUnavailableException;

    List<SecurityGroup> querySecurityGroups(SgQuery sgQuery)
            throws CommonAdapterException;

    SecurityGroup getSecurityGroup(SgGet sgGet) throws CommonAdapterException;

    boolean deleteSecurityGroup(SgDelete sgDelete)
            throws CommonAdapterException, AdapterUnavailableException;

    SecurityGroupResult createSecurityGroup(SgCreate sgCreate)
            throws CommonAdapterException, AdapterUnavailableException;

    FloatingIpCreateResult createFloatingIp(FloatingIpCreate floatingIpCreate)
            throws CommonAdapterException, AdapterUnavailableException;

    FloatingIp getFloatingIp(FloatingIpGet floatingIpGet)
            throws CommonAdapterException;

    List<FloatingIp> queryFloatingIps(FloatingIpQuery floatingQuery)
            throws CommonAdapterException;

    boolean deleteFloatingIp(FloatingIpDelete floatingIpDelete)
            throws CommonAdapterException, AdapterUnavailableException;

    List<FloatingIpPool> queryFloatingIpPools(
            FloatingIpPoolQuery floatingIpPoolQuery)
            throws CommonAdapterException;

    FloatingIpBulk createFloatingIpBulk(
            FloatingIpBulkCreate floatingIpBulkCreate)
            throws CommonAdapterException;

    boolean deleteFloatingIpBulk(FloatingIpBulkDelete floatingIpBulkDelete)
            throws CommonAdapterException;

    List<FloatingIpBulkDetail> queryFloatingIpBulks(
            FloatingIpBulkQuery floatingIpBulkQuery)
            throws CommonAdapterException;

    CommonAdapterResult attachFloatingIp(FloatingIpAttach floatingIpAttach)
            throws CommonAdapterException, AdapterUnavailableException;

    boolean detachFloatingIp(FloatingIpDetach floatingIpDetach)
            throws CommonAdapterException, AdapterUnavailableException;

    boolean attachSecurityGroup(ServerSecurityGroupAdd securityGroupAdd) throws CommonAdapterException, AdapterUnavailableException;

    boolean detachSecurityGroup(ServerSecurityGroupDelete securityGroupDelete) throws CommonAdapterException, AdapterUnavailableException;

    SecurityGroupRulesResult createSgRule(SgRuleCreate sgRuleCreate) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteSgRule(SgRuleDelete sgRuleDelete) throws CommonAdapterException, AdapterUnavailableException;

    com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup querySgRuleList(
            SgRuleListQuery sgRuleListQuery) throws CommonAdapterException,
            AdapterUnavailableException;

    /**
     * create router
     * @param routerCreate
     * @return
     */
    CommonAdapterResult createRouter(RouterCreate routerCreate)
            throws CommonAdapterException, AdapterUnavailableException;

    /**
     * delete router
     * @param routerDelete
     * @return
     * @throws CommonAdapterException
     * @throws AdapterUnavailableException
     */
    CommonAdapterResult deleteRouter(RouterDelete routerDelete)
            throws CommonAdapterException, AdapterUnavailableException;

    /**
     * add router interface for subnet
     * @param routerAddInterface
     * @return
     * @throws CommonAdapterException
     * @throws AdapterUnavailableException
     */
    CommonAdapterResult addRouterInterface(RouterAddInterface routerAddInterface)
            throws CommonAdapterException, AdapterUnavailableException;

    /**
     *
     * @param routerAddExternalGateway
     * @return
     * @throws CommonAdapterException
     * @throws AdapterUnavailableException
     */
    CommonAdapterResult addRouterExternalGateway(RouterAddExternalGateway routerAddExternalGateway)
            throws CommonAdapterException, AdapterUnavailableException;

    /**
     *
     * @param routerRemoveInterface
     * @return
     * @throws CommonAdapterException
     * @throws AdapterUnavailableException
     */
    CommonAdapterResult removeRouterInterface(RouterRemoveInterface routerRemoveInterface)
            throws CommonAdapterException, AdapterUnavailableException;

    /**
     *
     * @param routerRemoveExternalGateway
     * @return
     * @throws CommonAdapterException
     * @throws AdapterUnavailableException
     */
    CommonAdapterResult removeExternalGateway(RouterRemoveExternalGateway routerRemoveExternalGateway)
            throws CommonAdapterException, AdapterUnavailableException;
}
