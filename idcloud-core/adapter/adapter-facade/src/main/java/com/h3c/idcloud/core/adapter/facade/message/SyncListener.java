package com.h3c.idcloud.core.adapter.facade.message;

import com.google.common.collect.Sets;

import com.h3c.idcloud.core.adapter.facade.AdminHandler;
import com.h3c.idcloud.core.adapter.facade.MonitorHandler;
import com.h3c.idcloud.core.adapter.facade.NetHandler;
import com.h3c.idcloud.core.adapter.facade.ScanHandler;
import com.h3c.idcloud.core.adapter.facade.VmHandler;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.Role;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.Roles;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant.Tenants;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.user.Users;
import com.h3c.idcloud.core.adapter.facade.provision.model.keypair.KeyPairResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.AlarmTriggerVosResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.AlarmVosResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.CpuInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.Node;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroupResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroupRule;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroupRulesResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Cluster;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.CpuPools;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataStore;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.IOSlotResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Ios;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Mpars;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Network;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SSPResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SimpleHost;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SysDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Uuid;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.ViosVo;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Vm;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Vswitchs;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.KvmStorage;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.KvmStorages;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.VmSecurityGroups;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.admin.RoleList;
import com.h3c.idcloud.core.adapter.pojo.admin.SgCreate;
import com.h3c.idcloud.core.adapter.pojo.admin.SgDelete;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleAdd;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleDelete;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleList;
import com.h3c.idcloud.core.adapter.pojo.admin.result.RoleListResult;
import com.h3c.idcloud.core.adapter.pojo.admin.result.RoleResult;
import com.h3c.idcloud.core.adapter.pojo.admin.result.UserRoleAddResult;
import com.h3c.idcloud.core.adapter.pojo.admin.result.UserRoleDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.admin.result.UserRoleListResult;
import com.h3c.idcloud.core.adapter.pojo.alarm.AlarmListGet;
import com.h3c.idcloud.core.adapter.pojo.alarm.AlarmTriggerGet;
import com.h3c.idcloud.core.adapter.pojo.alarm.result.AlarmListResult;
import com.h3c.idcloud.core.adapter.pojo.alarm.result.AlarmTriggerResult;
import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairCreate;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairDelete;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairListGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairCreateResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairListGetResult;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpAttach;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDetach;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.network.Router;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterAddInterface;
import com.h3c.idcloud.core.adapter.pojo.network.RouterCreate;
import com.h3c.idcloud.core.adapter.pojo.network.RouterDelete;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveExternalGateway;
import com.h3c.idcloud.core.adapter.pojo.network.RouterRemoveInterface;
import com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupQuery;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupAdd;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupDelete;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleCreate;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleDelete;
import com.h3c.idcloud.core.adapter.pojo.network.SgRuleListQuery;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpAttachResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpDetachResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.RouterResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SecurityGroupQueryResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.ServerSecurityGroupAddResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.ServerSecurityGroupDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgRuleListQueryResult;
import com.h3c.idcloud.core.adapter.pojo.other.EnvConnectionTest;
import com.h3c.idcloud.core.adapter.pojo.other.RegisterEnv;
import com.h3c.idcloud.core.adapter.pojo.other.UnRegisterEnv;
import com.h3c.idcloud.core.adapter.pojo.other.result.EnvConnectionTestResult;
import com.h3c.idcloud.core.adapter.pojo.other.result.RegisterEnvResult;
import com.h3c.idcloud.core.adapter.pojo.other.result.UnRegisterEnvResult;
import com.h3c.idcloud.core.adapter.pojo.scan.AllInOneScan;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScan;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.CpuPoolScan;
import com.h3c.idcloud.core.adapter.pojo.scan.DiskScan;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByCluster;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByDvs;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByEnv;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanBySvs;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanWithVmsByEnv;
import com.h3c.idcloud.core.adapter.pojo.scan.IOSlotScan;
import com.h3c.idcloud.core.adapter.pojo.scan.IoScan;
import com.h3c.idcloud.core.adapter.pojo.scan.KvmStorageScan;
import com.h3c.idcloud.core.adapter.pojo.scan.MparsScan;
import com.h3c.idcloud.core.adapter.pojo.scan.NetworkScan;
import com.h3c.idcloud.core.adapter.pojo.scan.NpivScan;
import com.h3c.idcloud.core.adapter.pojo.scan.SSPScan;
import com.h3c.idcloud.core.adapter.pojo.scan.StorageScan;
import com.h3c.idcloud.core.adapter.pojo.scan.VmScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.VswitchScan;
import com.h3c.idcloud.core.adapter.pojo.scan.result.AllInOneScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.ClusterScanAloneResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.ClusterScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.CpuPoolScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.DiskScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanAloneResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanByClusterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanByDvsResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanByEnvResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanBySvsResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanWithVmsByEnvResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.IOSlotScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.IoScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.KvmStorageScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.MparsScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.NetworkScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.NpivScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.SSPScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.StorageScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.VmScanAloneResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.VswitchScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.ClusterVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataStoreVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostNameVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.KvmStorageVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.NetworkVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.UuidVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.ViosResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VmVO;
import com.h3c.idcloud.core.adapter.pojo.tenant.AddUserToTenant;
import com.h3c.idcloud.core.adapter.pojo.tenant.RemoveUserFromTenant;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantCreate;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantDelete;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantListGet;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantResourcesDelete;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.AddUserToTenantResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.RemoveUserFromTenantResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantCreateResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantListGetResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantResourcesDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.user.UserCreate;
import com.h3c.idcloud.core.adapter.pojo.user.UserDelete;
import com.h3c.idcloud.core.adapter.pojo.user.UserListGet;
import com.h3c.idcloud.core.adapter.pojo.user.UserModify;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserCreateResult;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserListGetResult;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserModifyResult;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCpu;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuerySgs;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRename;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmUserAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.VmVncConsole;
import com.h3c.idcloud.core.adapter.pojo.vm.result.NodeCpuResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.NodeCreateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.SnapshotInfo;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmAddUserResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmInfo;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmQueryResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmQuerySgsResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmRenameResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmSnapshotQueryResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmVncConsoleResult;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SyncListener {

    private static Logger log = LoggerFactory.getLogger(SyncListener.class);
    @Autowired
    private VmHandler vmHandler;
    @Autowired
    private MonitorHandler monitorHandler;
    @Autowired
    private AdminHandler adminHandler;
    @Autowired
    private ScanHandler scanHandler;
    @Autowired
    private NetHandler netHandler;

    public EnvConnectionTestResult handleMessage(EnvConnectionTest envConnectionTest) {
        log.info("receiving message for connectting env" + "----" + "virtual type : " + envConnectionTest.getVirtEnvType());

        log.info("msg id : " + envConnectionTest.getMsgId());

        EnvConnectionTestResult result = new EnvConnectionTestResult();

        try {
            boolean connecteResult = scanHandler.connectionEnvTest(envConnectionTest);
            result.setSuccess(connecteResult);
            log.info("[adaptor] connected env " + envConnectionTest.getProviderType() + "successfully");
        } catch (CommonAdapterException e) {

            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public VmRenameResult handleMessage(VmRename vmRename) {

        log.info("receiving message for renaming vm" + "----" + "virtual type : " + vmRename.getVirtEnvType());

        log.info("msg id : " + vmRename.getMsgId());

        VmRenameResult vmRenameResult = new VmRenameResult();

        vmRenameResult.setMsgId(vmRename.getMsgId());
        vmRenameResult.setName(vmRename.getName());
        vmRenameResult.setNameTobe(vmRename.getNameTobe());
        vmRenameResult.setId(vmRename.getId());

        try {
            vmHandler.renameVm(vmRename);
            vmRenameResult.setSuccess(true);
            log.info("[adaptor] vm :" + vmRename.getName() + " has been renamed successfully");
        } catch (CommonAdapterException e) {

            vmRenameResult.setSuccess(false);
            vmRenameResult.setErrCode(e.getErrCode());
            vmRenameResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            vmRenameResult.setSuccess(false);
            vmRenameResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmRenameResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmRenameResult;

    }

    public VmQueryResult handleMessage(VmQuery vmQuery) {

        log.info("receiving message for querying vms" + "----" + "virtual type : " + vmQuery.getVirtEnvType());

        log.info("msg id : " + vmQuery.getMsgId());

        List<Server> servers = null;

        VmQueryResult vmQueryResult = new VmQueryResult();
        vmQueryResult = (VmQueryResult) BaseUtil.setResult(vmQuery, VmQueryResult.class);
        vmQueryResult.setMsgId(vmQuery.getMsgId());

        try {
            servers = vmHandler.queryVms(vmQuery);
        } catch (CommonAdapterException e) {
            e.printStackTrace();
            vmQueryResult.setSuccess(false);

            return vmQueryResult;
        } catch (AdapterUnavailableException e) {
            
            e.printStackTrace();
        }

        List<VmInfo> lstVms = new ArrayList<VmInfo>();

        for (Server server : servers) {
            VmInfo vmInfo = new VmInfo();
            vmInfo.setName(server.getName());
            vmInfo.setStatus(server.getStatus());
            lstVms.add(vmInfo);
        }

        vmQueryResult.setServers(lstVms);

        return vmQueryResult;

    }

    public RegisterEnvResult handleMessage(RegisterEnv registerEnv) {

        log.info("receiving message for registering env" + "----" + "virtual type : " + registerEnv.getVirtEnvType());

        RegisterEnvResult result = new RegisterEnvResult();
        result.setVirtualType(registerEnv.getVirtEnvType());
        result.setUuid(registerEnv.getVirtEnvUuid());

        return result;

    }

    public UnRegisterEnvResult handleMessage(UnRegisterEnv unRegisterEnv) {

        log.info("receiving message for registering env" + "----" + "virtual type : " + unRegisterEnv.getVirtEnvType());

        UnRegisterEnvResult result = new UnRegisterEnvResult();
        result.setVirtualType(unRegisterEnv.getVirtEnvType());
        result.setUuid(unRegisterEnv.getVirtEnvUuid());

        return result;

    }

    public NodeCreateResult handleMessage(NodeCreate nodeCreate) {

        log.info("receiving message for creating node" + "----");

        NodeCreateResult nodeCreateResult = new NodeCreateResult();
        nodeCreateResult = (NodeCreateResult) BaseUtil.setResult(nodeCreate, NodeCreateResult.class);
        try {
            Node node = monitorHandler.createNode(nodeCreate);
            nodeCreateResult.setNodeId(node.getNodeId());
        } catch (CommonAdapterException e) {
            e.printStackTrace();
            nodeCreateResult.setSuccess(false);

            return nodeCreateResult;
        }

        return nodeCreateResult;

    }

    public NodeCpuResult handleMessage(NodeCpu nodeCpu) {

        log.info("receiving message for monitoring node cpu ");

        NodeCpuResult nodeCpuResult = new NodeCpuResult();

        try {
            CpuInfo cpuInfo = monitorHandler.monitorCpu(nodeCpu);

            nodeCpuResult.setData(JsonUtil.toJson(cpuInfo));
        } catch (CommonAdapterException e) {
            e.printStackTrace();
            nodeCpuResult.setSuccess(false);

            return nodeCpuResult;
        }

        return nodeCpuResult;

    }

    public VmSnapshotQueryResult handleMessage(VmSnapshotQuery vmSnapshotQuery) {

        log.info("receiving message for querying vm snapshots" + "----" + "virtual type : "
                + vmSnapshotQuery.getVirtEnvType());

        log.info("msg id : " + vmSnapshotQuery.getMsgId());

        List<com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfo> snapshots = null;

        VmSnapshotQueryResult vmSnapshotQueryResult = new VmSnapshotQueryResult();

        vmSnapshotQueryResult.setMsgId(vmSnapshotQuery.getMsgId());

        try {
            snapshots = vmHandler.queryVmSnapshot(vmSnapshotQuery);
        } catch (CommonAdapterException e) {
            e.printStackTrace();
            vmSnapshotQueryResult.setSuccess(false);

            return vmSnapshotQueryResult;
        } catch (AdapterUnavailableException e) {
            
            e.printStackTrace();
        }

        List<SnapshotInfo> lstSnapshots = new ArrayList<SnapshotInfo>();

        for (com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfo snapshot : snapshots) {
            SnapshotInfo ss = new SnapshotInfo();
            ss.setName(snapshot.getName());
            ss.setSnapshot(snapshot.getSnapshot());
            ss.setCreateTime(snapshot.getCreateTime());
            lstSnapshots.add(ss);
        }

        vmSnapshotQueryResult.setSnapshotInfos(lstSnapshots);

        return vmSnapshotQueryResult;

    }

    public TenantCreateResult handleMessage(TenantCreate tenantCreate) {

        log.info("receiving message for createing tenant" + "----" + "virtual type : " + tenantCreate.getVirtEnvType());

        log.info("msg id : " + tenantCreate.getMsgId());

        TenantCreateResult tenantCreateResult = new TenantCreateResult();
        tenantCreateResult = (TenantCreateResult) BaseUtil.setResult(tenantCreate, TenantCreateResult.class);

        //	tenantCreateResult.setName(tenantCreate.getName());
        //	tenantCreateResult.setDescription(tenantCreate.getDescription());

        try {
            Tenants tenants = adminHandler.createTenant(tenantCreate);
            tenantCreateResult = tenants.getTenantCreateResult();
            tenantCreateResult.setMsgId(tenantCreate.getMsgId());
            tenantCreateResult.setSuccess(tenants.isSuccess());
            if (tenants.isSuccess()) {
                log.info("[adaptor] tenant :" + tenantCreate.getName() + " has been created successfully");
            }
        } catch (CommonAdapterException e) {

            tenantCreateResult.setSuccess(false);
            tenantCreateResult.setErrCode(e.getErrCode());
            tenantCreateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            tenantCreateResult.setSuccess(false);
            tenantCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            tenantCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        if (tenantCreateResult.isSuccess()) {
            log.info("[adaptor] tenant :" + tenantCreate.getName() + " has been created successfully");
        } else {
            log.info("[adaptor] tenant :" + tenantCreate.getName() + " has been created failure");
        }
        return tenantCreateResult;

    }

    public TenantListGetResult handleMessage(TenantListGet tenantListGet) {
        log.info("receiving message for getting tenantlist" + "----" + "virtual type : " + tenantListGet.getVirtEnvType());

        log.info("msg id : " + tenantListGet.getMsgId());
        TenantListGetResult tenantListGetResult = new TenantListGetResult();
        tenantListGetResult = (TenantListGetResult) BaseUtil.setResult(tenantListGet, TenantListGetResult.class);
        try {
            Tenants queryTenant = adminHandler.queryTenant(tenantListGet);
            tenantListGetResult = queryTenant.getTenantListGetResult();
            tenantListGetResult.setSuccess(queryTenant.isSuccess());

        } catch (CommonAdapterException e) {
            
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            
            e.printStackTrace();
        }
        return tenantListGetResult;
    }

    public TenantDeleteResult handleMessage(TenantDelete tenantDelete) {

        log.info("receiving message for deleting tenant" + "----" + "virtual type : " + tenantDelete.getVirtEnvType());

        log.info("msg id : " + tenantDelete.getMsgId());

        TenantDeleteResult tenantDeleteResult = new TenantDeleteResult();
        tenantDeleteResult = (TenantDeleteResult) BaseUtil.setResult(tenantDelete, TenantDeleteResult.class);
        tenantDeleteResult.setMsgId(tenantDelete.getMsgId());
        tenantDeleteResult.setTenantId(tenantDelete.getTenantId());
        try {
            boolean result = adminHandler.deleteTenant(tenantDelete);
            if (result) {
                tenantDeleteResult.setSuccess(true);
            } else {
                tenantDeleteResult.setSuccess(false);
            }
        } catch (CommonAdapterException e) {

            tenantDeleteResult.setSuccess(false);
            tenantDeleteResult.setErrCode(e.getErrCode());
            tenantDeleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            tenantDeleteResult.setSuccess(false);
            tenantDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            tenantDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        if (tenantDeleteResult.isSuccess()) {
            log.info("[adaptor] tenant :" + tenantDelete.getTenantId() + " has been deleted successfully");
        } else {
            log.info("[adaptor] tenant :" + tenantDelete.getTenantId() + " has been deleted failure");
        }
        return tenantDeleteResult;
    }

    public TenantResourcesDeleteResult handleMessage(TenantResourcesDelete tenantDelete) {

        log.info("receiving message for deleting tenant" + "----" + "virtual type : " + tenantDelete.getVirtEnvType());

        log.info("msg id : " + tenantDelete.getMsgId());

        TenantResourcesDeleteResult tenantDeleteResult = new TenantResourcesDeleteResult();
        tenantDeleteResult = (TenantResourcesDeleteResult) BaseUtil.setResult(tenantDelete, TenantResourcesDeleteResult.class);
        tenantDeleteResult.setMsgId(tenantDelete.getMsgId());
        //tenantDeleteResult.setTenantId(tenantDelete.getTenantId());
        try {
            boolean result = adminHandler.deleteTenantResources(tenantDelete);
            if (result) {
                tenantDeleteResult.setSuccess(true);
            } else {
                tenantDeleteResult.setSuccess(false);
            }
        } catch (CommonAdapterException e) {

            tenantDeleteResult.setSuccess(false);
            tenantDeleteResult.setErrCode(e.getErrCode());
            tenantDeleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            tenantDeleteResult.setSuccess(false);
            tenantDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            tenantDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        if (tenantDeleteResult.isSuccess()) {
            log.info("[adaptor] tenant :" + tenantDelete.getTenantId() + " has been deleted successfully");
        } else {
            log.info("[adaptor] tenant :" + tenantDelete.getTenantId() + " has been deleted failure");
        }
        return tenantDeleteResult;
    }


    public UserCreateResult handleMessage(UserCreate userCreate) {

        log.info("receiving message for creating user" + "----" + "virtual type : " + userCreate.getVirtEnvType());

        log.info("msg id : " + userCreate.getMsgId());

        UserCreateResult userCreateResult = new UserCreateResult();
        userCreateResult = (UserCreateResult) BaseUtil.setResult(userCreate, UserCreateResult.class);
    /*	userCreateResult.setMsgId(userCreate.getMsgId());
        userCreateResult.setName(userCreate.getName());
		userCreateResult.setPassword(userCreate.getPassword());
		userCreateResult.setTenantUuid(userCreate.getTenantUuid());*/

        try {
            Users users = adminHandler.createUser(userCreate);
            userCreateResult.setMsgId(userCreate.getMsgId());
            userCreateResult = users.getUserCreateResult();
            userCreateResult.setSuccess(users.isSuccess());

        } catch (CommonAdapterException e) {

            userCreateResult.setSuccess(false);
            userCreateResult.setErrCode(e.getErrCode());
            userCreateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            userCreateResult.setSuccess(false);
            userCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            userCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        if (userCreateResult.isSuccess()) {
            log.info("[adaptor] user :" + userCreate.getName() + " has been created successfully");
        } else {
            log.info("[adaptor] user :" + userCreate.getName() + " has been created failure");

        }
        return userCreateResult;

    }

    public UserDeleteResult handleMessage(UserDelete userDelete) {

        log.info("receiving message for deleting user" + "----" + "virtual type : " + userDelete.getVirtEnvType());

        log.info("msg id : " + userDelete.getMsgId());

        UserDeleteResult userDeleteResult = new UserDeleteResult();
        userDeleteResult = (UserDeleteResult) BaseUtil.setResult(userDelete, UserDeleteResult.class);
        userDeleteResult.setMsgId(userDelete.getMsgId());
        userDeleteResult.setUserId(userDelete.getUserId());
        try {
            boolean result = adminHandler.deleteUser(userDelete);
            if (result) {
                userDeleteResult.setSuccess(true);
            } else {
                userDeleteResult.setSuccess(false);
            }

        } catch (CommonAdapterException e) {

            userDeleteResult.setSuccess(false);
            userDeleteResult.setErrCode(e.getErrCode());
            userDeleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            userDeleteResult.setSuccess(false);
            userDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            userDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        if (userDeleteResult.isSuccess()) {
            log.info("[adaptor] user :" + userDelete.getUserId() + " has been deleted successfully");
        } else {
            log.info("[adaptor] user :" + userDelete.getUserId() + " has been deleted failure");

        }
        return userDeleteResult;

    }

    public UserListGetResult handleMessage(UserListGet userListGet) {

        log.info("receiving message for getting userlist" + "----" + "virtual type : " + userListGet.getVirtEnvType());

        log.info("msg id : " + userListGet.getMsgId());

        UserListGetResult result = new UserListGetResult();
        result = (UserListGetResult) BaseUtil.setResult(userListGet, UserListGetResult.class);
        result.setMsgId(userListGet.getMsgId());
        try {
            Users users = adminHandler.queryUser(userListGet);
            UserListGetResult userListGetResult = users.getUserListGetResult();
            result.setUsers(userListGetResult.getUsers());
            result.setSuccess(users.isSuccess());
        } catch (CommonAdapterException e) {

            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        if (result.isSuccess()) {
            log.info("[adaptor] get userList successfully");
        } else {
            log.info("[adaptor] get userList failure");
        }
        return result;

    }

    public UserRoleAddResult handleMessage(UserRoleAdd userRoleAdd) {

        log.info("receiving message for adding user role" + "----" + "virtual type : " + userRoleAdd.getVirtEnvType());

        log.info("msg id : " + userRoleAdd.getMsgId());

        UserRoleAddResult userRoleAddResult = new UserRoleAddResult();
        userRoleAddResult = (UserRoleAddResult) BaseUtil.setResult(userRoleAdd, UserRoleAddResult.class);
        userRoleAddResult.setMsgId(userRoleAdd.getMsgId());
        userRoleAddResult.setTenantUuid(userRoleAdd.getTenantUuid());
        userRoleAddResult.setUserUuid(userRoleAdd.getUserUuid());
        userRoleAddResult.setRoleUuid(userRoleAdd.getRoleUuid());

        try {
            adminHandler.addRoleToUser(userRoleAdd);
            userRoleAddResult.setSuccess(true);

            log.info("[adaptor] user :" + userRoleAdd.getUserUuid() + " has been assigned role successfully");
        } catch (CommonAdapterException e) {

            userRoleAddResult.setSuccess(false);
            userRoleAddResult.setErrCode(e.getErrCode());
            userRoleAddResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            userRoleAddResult.setSuccess(false);
            userRoleAddResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            userRoleAddResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return userRoleAddResult;

    }

    public UserRoleDeleteResult handleMessage(UserRoleDelete userRoleDelete) {

        log.info("receiving message for deleting user role" + "----" + "virtual type : "
                + userRoleDelete.getVirtEnvType());

        log.info("msg id : " + userRoleDelete.getMsgId());

        UserRoleDeleteResult userRoleDeleteResult = new UserRoleDeleteResult();
        userRoleDeleteResult = (UserRoleDeleteResult) BaseUtil.setResult(userRoleDelete, UserRoleDeleteResult.class);
        userRoleDeleteResult.setMsgId(userRoleDelete.getMsgId());

        userRoleDeleteResult.setTenantUuid(userRoleDelete.getTenantUuid());
        userRoleDeleteResult.setUserUuid(userRoleDelete.getUserUuid());
        userRoleDeleteResult.setRoleUuid(userRoleDelete.getRoleUuid());

        try {
            adminHandler.deleteRoleToUser(userRoleDelete);
            userRoleDeleteResult.setSuccess(true);

            log.info("[adaptor] user :" + userRoleDelete.getUserUuid() + " has been unassigned role successfully");
        } catch (CommonAdapterException e) {

            userRoleDeleteResult.setSuccess(false);
            userRoleDeleteResult.setErrCode(e.getErrCode());
            userRoleDeleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            userRoleDeleteResult.setSuccess(false);
            userRoleDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            userRoleDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return userRoleDeleteResult;

    }

    public UserRoleListResult handleMessage(UserRoleList userRoleList) {

        log.info("receiving message for listing user role" + "----" + "virtual type : " + userRoleList.getVirtEnvType());

        log.info("msg id : " + userRoleList.getMsgId());

        UserRoleListResult userRoleListResult = new UserRoleListResult();
        userRoleListResult = (UserRoleListResult) BaseUtil.setResult(userRoleList, UserRoleListResult.class);
        userRoleListResult.setMsgId(userRoleList.getMsgId());

        try {
            Roles listUserRoles = adminHandler.listUserRoles(userRoleList);
            userRoleListResult.setSuccess(true);

            List<RoleResult> listRoleResult = new ArrayList<RoleResult>();

            for (Role role : listUserRoles.getListdata()) {

                RoleResult roleResult = new RoleResult();
                try {
                    BeanUtils.copyProperties(roleResult, role);
                    listRoleResult.add(roleResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            userRoleListResult.setRoles(listRoleResult);

        } catch (CommonAdapterException e) {

            userRoleListResult.setSuccess(false);
            userRoleListResult.setErrCode(e.getErrCode());
            userRoleListResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            userRoleListResult.setSuccess(false);
            userRoleListResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            userRoleListResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return userRoleListResult;

    }


    public RoleListResult handleMessage(RoleList roleList) {

        log.info("receiving message for listing role" + "----" + "virtual type : " + roleList.getVirtEnvType());

        log.info("msg id : " + roleList.getMsgId());

        RoleListResult roleListResult = new RoleListResult();
        roleListResult = (RoleListResult) BaseUtil.setResult(roleList, RoleListResult.class);
        roleListResult.setMsgId(roleList.getMsgId());

        try {
            Roles listRoles = adminHandler.listRoles(roleList);
            roleListResult.setSuccess(listRoles.isSuccess());

            List<RoleResult> listRoleResult = new ArrayList<RoleResult>();

            for (Role role : listRoles.getListdata()) {

                RoleResult roleResult = new RoleResult();
                try {
                    BeanUtils.copyProperties(roleResult, role);
                    listRoleResult.add(roleResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            roleListResult.setRoles(listRoleResult);

        } catch (CommonAdapterException e) {

            roleListResult.setSuccess(false);
            roleListResult.setErrCode(e.getErrCode());
            roleListResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            roleListResult.setSuccess(false);
            roleListResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            roleListResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return roleListResult;

    }

    public VmScanAloneResult handleMessage(VmScanAlone vmScanAlone) {

        log.info("receiving message for scanning vm alone" + "----" + "virtual type : " + vmScanAlone.getVirtEnvType());

        log.info("msg id : " + vmScanAlone.getMsgId());

        VmScanAloneResult vmScanAloneResult = new VmScanAloneResult();
        vmScanAloneResult = (VmScanAloneResult) BaseUtil.setResult(vmScanAlone, VmScanAloneResult.class);
        vmScanAloneResult.setMsgId(vmScanAlone.getMsgId());

        try {
            Vm vm = scanHandler.scanVmAlone(vmScanAlone);
            vmScanAloneResult.setSuccess(true);

            VmVO vmVO = new VmVO();

            try {
                BeanUtils.copyProperties(vmVO, vm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            vmScanAloneResult.setVm(vmVO);

        } catch (CommonAdapterException e) {

            vmScanAloneResult.setSuccess(false);
            vmScanAloneResult.setErrCode(e.getErrCode());
            vmScanAloneResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            vmScanAloneResult.setSuccess(false);
            vmScanAloneResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmScanAloneResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmScanAloneResult;

    }

    public HostScanAloneResult handleMessage(HostScanAlone hostScanAlone) {

        log.info("receiving message for scanning host alone" + "----" + "virtual type : "
                + hostScanAlone.getVirtEnvType());

        log.info("msg id : " + hostScanAlone.getMsgId());

        HostScanAloneResult hostScanAloneResult = new HostScanAloneResult();
        hostScanAloneResult = (HostScanAloneResult) BaseUtil.setResult(hostScanAlone, HostScanAloneResult.class);
        hostScanAloneResult.setMsgId(hostScanAlone.getMsgId());

        try {
            Host host = scanHandler.scanHostAlone(hostScanAlone);
            hostScanAloneResult.setSuccess(true);

            HostVO hostVO = new HostVO();

            try {

                BeanUtils.copyProperty(hostVO, "hostName", host.getHostName());
                BeanUtils.copyProperty(hostVO, "cpuCores", host.getCpuCores());
                BeanUtils.copyProperty(hostVO, "cpuGhz", host.getCpuGhz());
                BeanUtils.copyProperty(hostVO, "cpuNumber", host.getCpuNumber());
                BeanUtils.copyProperty(hostVO, "cpuType", host.getCpuType());
                BeanUtils.copyProperty(hostVO, "cpuUsage", host.getCpuUsage());
                BeanUtils.copyProperty(hostVO, "dataStorages", host.getDataStorages());
                BeanUtils.copyProperty(hostVO, "hostIp", host.getHostIp());
                BeanUtils.copyProperty(hostVO, "hostOsType", host.getHostOsType());
                BeanUtils.copyProperty(hostVO, "memorySize", host.getMemorySize());
                BeanUtils.copyProperty(hostVO, "menUsage", host.getMenUsage());
                BeanUtils.copyProperty(hostVO, "nicNumber", host.getNicNumber());
                BeanUtils.copyProperty(hostVO, "status", host.getStatus());
                BeanUtils.copyProperty(hostVO, "uuid", host.getUuid());
                BeanUtils.copyProperty(hostVO, "vms", host.getVms());

                Network network = host.getNetWorks();
                NetworkVO networkVO = new NetworkVO();
                if (network != null) {
                    BeanUtils.copyProperties(networkVO, network);
                }

                hostVO.setNetWorks(networkVO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            hostScanAloneResult.setHost(hostVO);

        } catch (CommonAdapterException e) {

            hostScanAloneResult.setSuccess(false);
            hostScanAloneResult.setErrCode(e.getErrCode());
            hostScanAloneResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            hostScanAloneResult.setSuccess(false);
            hostScanAloneResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            hostScanAloneResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return hostScanAloneResult;

    }

    public ClusterScanAloneResult handleMessage(ClusterScanAlone clusterScanAlone) {

        log.info("receiving message for scanning cluster alone" + "----" + "virtual type : "
                + clusterScanAlone.getVirtEnvType());

        log.info("msg id : " + clusterScanAlone.getMsgId());

        ClusterScanAloneResult clusterScanAloneResult = new ClusterScanAloneResult();
        clusterScanAloneResult = (ClusterScanAloneResult) BaseUtil.setResult(clusterScanAlone, ClusterScanAloneResult.class);
        clusterScanAloneResult.setMsgId(clusterScanAloneResult.getMsgId());

        try {
            Cluster cluster = scanHandler.scanClusterAlone(clusterScanAlone);
            clusterScanAloneResult.setSuccess(true);

            ClusterVO clusterVO = new ClusterVO();

            try {
                BeanUtils.copyProperties(clusterVO, cluster);

            } catch (Exception e) {
                e.printStackTrace();
            }
            clusterScanAloneResult.setCluster(clusterVO);

        } catch (CommonAdapterException e) {

            clusterScanAloneResult.setSuccess(false);
            clusterScanAloneResult.setErrCode(e.getErrCode());
            clusterScanAloneResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            clusterScanAloneResult.setSuccess(false);
            clusterScanAloneResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            clusterScanAloneResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return clusterScanAloneResult;

    }

    public HostScanByClusterResult handleMessage(HostScanByCluster hostScanByCluster) {

        log.info("receiving message for scanning host by cluster" + "----" + "virtual type : "
                + hostScanByCluster.getVirtEnvType());

        log.info("msg id : " + hostScanByCluster.getMsgId());

        HostScanByClusterResult hostScanByClusterResult = new HostScanByClusterResult();
        hostScanByClusterResult = (HostScanByClusterResult) BaseUtil.setResult(hostScanByCluster, HostScanByClusterResult.class);
        hostScanByClusterResult.setMsgId(hostScanByCluster.getMsgId());

        try {
            List<SimpleHost> hosts = scanHandler.scanHost(hostScanByCluster);
            hostScanByClusterResult.setSuccess(true);

            List<HostNameVO> hostNameVOs = new ArrayList<HostNameVO>();

            for (SimpleHost host : hosts) {
                HostNameVO hostNameVO = new HostNameVO();
                try {
                    BeanUtils.copyProperty(hostNameVO, "hostName", host.getHostName());
                    BeanUtils.copyProperty(hostNameVO, "uuid", host.getUuid());
                    hostNameVOs.add(hostNameVO);

                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }

            hostScanByClusterResult.setHosts(hostNameVOs);

        } catch (CommonAdapterException e) {

            hostScanByClusterResult.setSuccess(false);
            hostScanByClusterResult.setErrCode(e.getErrCode());
            hostScanByClusterResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            hostScanByClusterResult.setSuccess(false);
            hostScanByClusterResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            hostScanByClusterResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return hostScanByClusterResult;

    }

    public StorageScanResult handleMessage(StorageScan storageScan) {

        log.info("receiving message for scanning storages" + "----" + "virtual type : " + storageScan.getVirtEnvType());

        log.info("msg id : " + storageScan.getMsgId());

        StorageScanResult storageScanResult = new StorageScanResult();
        storageScanResult = (StorageScanResult) BaseUtil.setResult(storageScan, StorageScanResult.class);
        storageScanResult.setMsgId(storageScan.getMsgId());

        try {
            List<DataStore> dataStores = scanHandler.scanDataStore(storageScan);
            storageScanResult.setSuccess(true);

            List<DataStoreVO> dataStoreVOs = new ArrayList<DataStoreVO>();
            for (DataStore dataStore : dataStores) {
                DataStoreVO dataStoreVO = new DataStoreVO();
                try {
                    BeanUtils.copyProperties(dataStoreVO, dataStore);
                    dataStoreVOs.add(dataStoreVO);

                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }

            storageScanResult.setDatastores(dataStoreVOs);

        } catch (CommonAdapterException e) {

            storageScanResult.setSuccess(false);
            storageScanResult.setErrCode(e.getErrCode());
            storageScanResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            storageScanResult.setSuccess(false);
            storageScanResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            storageScanResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return storageScanResult;

    }

    public ClusterScanResult handleMessage(ClusterScan clusterScan) {

        log.info("receiving message for scanning clusters" + "----" + "virtual type : " + clusterScan.getVirtEnvType());

        log.info("msg id : " + clusterScan.getMsgId());

        ClusterScanResult clusterScanResult = new ClusterScanResult();
        clusterScanResult = (ClusterScanResult) BaseUtil.setResult(clusterScan, ClusterScanResult.class);
        clusterScanResult.setMsgId(clusterScan.getMsgId());

        try {
            List<Cluster> clusters = scanHandler.scanCluster(clusterScan);
            clusterScanResult.setSuccess(true);

            List<ClusterVO> clusterVOs = new ArrayList<ClusterVO>();

            for (Cluster cluster : clusters) {
                ClusterVO clusterVO = new ClusterVO();
                try {
                    BeanUtils.copyProperties(clusterVO, cluster);
                    clusterVOs.add(clusterVO);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            clusterScanResult.setClusters(clusterVOs);

        } catch (CommonAdapterException e) {

            clusterScanResult.setSuccess(false);
            clusterScanResult.setErrCode(e.getErrCode());
            clusterScanResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            clusterScanResult.setSuccess(false);
            clusterScanResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            clusterScanResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            clusterScanResult.setSuccess(false);
            clusterScanResult.setErrCode("500");
            clusterScanResult.setErrMsg(e.getMessage());
        }

        return clusterScanResult;

    }

    public AllInOneScanResult handleMessage(AllInOneScan allInOneScan) {

        log.info("receiving message for scanning all in one" + "----" + "virtual type : "
                + allInOneScan.getVirtEnvType());

        log.info("msg id : " + allInOneScan.getMsgId());

        AllInOneScanResult allInOneScanResult = new AllInOneScanResult();
        allInOneScanResult = (AllInOneScanResult) BaseUtil.setResult(allInOneScan, AllInOneScanResult.class);
        allInOneScanResult.setMsgId(allInOneScan.getMsgId());

        try {
            List<Host> hosts = scanHandler.scanAllInOne(allInOneScan);

            allInOneScanResult.setSuccess(true);

            List<HostVO> hostVOs = new ArrayList<HostVO>();

            for (Host host : hosts) {

                HostVO hostVO = new HostVO();
                BeanUtils.copyProperty(hostVO, "hostName", host.getHostName());
                BeanUtils.copyProperty(hostVO, "host", host.getHost());

                BeanUtils.copyProperty(hostVO, "cpuCores", host.getCpuCores());
                BeanUtils.copyProperty(hostVO, "cpuGhz", host.getCpuGhz());
                BeanUtils.copyProperty(hostVO, "cpuNumber", host.getCpuNumber());
                BeanUtils.copyProperty(hostVO, "cpuType", host.getCpuType());
                BeanUtils.copyProperty(hostVO, "cpuUsage", host.getCpuUsage());
                BeanUtils.copyProperty(hostVO, "dataStorages", host.getDataStorages());
                BeanUtils.copyProperty(hostVO, "hostIp", host.getHostIp());
                BeanUtils.copyProperty(hostVO, "hostOsType", host.getHostOsType());
                BeanUtils.copyProperty(hostVO, "memorySize", host.getMemorySize());
                BeanUtils.copyProperty(hostVO, "menUsage", host.getMenUsage());
                BeanUtils.copyProperty(hostVO, "nicNumber", host.getNicNumber());
                BeanUtils.copyProperty(hostVO, "serverModel", host.getServerModel());
                if (host.getState() != null && !"".equals(host.getState())) {
                    BeanUtils.copyProperty(hostVO, "status", host.getState());
                } else {
                    BeanUtils.copyProperty(hostVO, "status", host.getStatus());
                }
                BeanUtils.copyProperty(hostVO, "uuid", host.getUuid());
//					BeanUtils.copyProperty(hostVO, "vms", host.getVms());
                List<VmVO> vmVOs = new ArrayList<VmVO>();

                List<Vm> vms = host.getVms();
                for (Vm vm : vms) {
                    VmVO vmVO = new VmVO();
                    BeanUtils.copyProperties(vmVO, vm);
                    vmVOs.add(vmVO);
                }
                hostVO.setVms(vmVOs);
                if (host.getNetWorks() != null) {

                    Network network = host.getNetWorks();
                    NetworkVO networkVO = new NetworkVO();

                    BeanUtils.copyProperties(networkVO, network);

                    hostVO.setNetWorks(networkVO);
                }
                hostVOs.add(hostVO);

            }
            allInOneScanResult.setHosts(hostVOs);

        } catch (CommonAdapterException e) {

            allInOneScanResult.setSuccess(false);
            allInOneScanResult.setErrCode(e.getErrCode());
            allInOneScanResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            allInOneScanResult.setSuccess(false);
            allInOneScanResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            allInOneScanResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allInOneScanResult;

    }

    public HostScanByDvsResult handleMessage(HostScanByDvs hostScanByDvs) {

        log.info("receiving message for scanning host by dvs" + "----" + "virtual type : "
                + hostScanByDvs.getVirtEnvType());

        log.info("msg id : " + hostScanByDvs.getMsgId());

        HostScanByDvsResult hostScanByDvsResult = new HostScanByDvsResult();
        hostScanByDvsResult = (HostScanByDvsResult) BaseUtil.setResult(hostScanByDvs, HostScanByDvsResult.class);
        hostScanByDvsResult.setMsgId(hostScanByDvs.getMsgId());

        try {
            List<Uuid> uuids = scanHandler.scanUuidByDvs(hostScanByDvs);
            hostScanByDvsResult.setSuccess(true);

            List<UuidVO> uuidVOs = new ArrayList<UuidVO>();

            for (Uuid uuid : uuids) {
                UuidVO uuidVO = new UuidVO();
                try {
                    BeanUtils.copyProperties(uuidVO, uuid);
                    uuidVOs.add(uuidVO);

                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }

            hostScanByDvsResult.setUuids(uuidVOs);

        } catch (CommonAdapterException e) {

            hostScanByDvsResult.setSuccess(false);
            hostScanByDvsResult.setErrCode(e.getErrCode());
            hostScanByDvsResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            hostScanByDvsResult.setSuccess(false);
            hostScanByDvsResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            hostScanByDvsResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return hostScanByDvsResult;

    }

    public HostScanBySvsResult handleMessage(HostScanBySvs hostScanBySvs) {

        log.info("receiving message for scanning host by svs" + "----" + "virtual type : "
                + hostScanBySvs.getVirtEnvType());

        log.info("msg id : " + hostScanBySvs.getMsgId());

        HostScanBySvsResult hostScanBySvsResult = new HostScanBySvsResult();
        hostScanBySvsResult = (HostScanBySvsResult) BaseUtil.setResult(hostScanBySvs, HostScanBySvsResult.class);
        hostScanBySvsResult.setMsgId(hostScanBySvs.getMsgId());

        try {
            List<Uuid> uuids = scanHandler.scanUuidBySvs(hostScanBySvs);
            hostScanBySvsResult.setSuccess(true);

            List<UuidVO> uuidVOs = new ArrayList<UuidVO>();

            for (Uuid uuid : uuids) {
                UuidVO uuidVO = new UuidVO();
                try {
                    BeanUtils.copyProperties(uuidVO, uuid);
                    uuidVOs.add(uuidVO);

                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }

            hostScanBySvsResult.setUuids(uuidVOs);

        } catch (CommonAdapterException e) {

            hostScanBySvsResult.setSuccess(false);
            hostScanBySvsResult.setErrCode(e.getErrCode());
            hostScanBySvsResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            hostScanBySvsResult.setSuccess(false);
            hostScanBySvsResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            hostScanBySvsResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return hostScanBySvsResult;

    }

    public HostScanByEnvResult handleMessage(HostScanByEnv hostScanByEnv) {
        log.info("receiving message for scanning hosts " + "----" + "virtual type : " + hostScanByEnv.getVirtEnvType());

        log.info("msg id : " + hostScanByEnv.getMsgId());

        HostScanByEnvResult result = new HostScanByEnvResult();
        result = (HostScanByEnvResult) BaseUtil.setResult(hostScanByEnv, HostScanByEnvResult.class);
        try {
            List<Host> scanHostsByEnv = scanHandler.scanHostsByEnv(hostScanByEnv);
            if (scanHostsByEnv != null) {
                List<HostVO> hostVOs = new ArrayList<HostVO>();
                for (int i = 0; i < scanHostsByEnv.size(); i++) {
                    HostVO hostVO = BaseUtil.castObject(scanHostsByEnv.get(i), HostVO.class);
                    hostVOs.add(hostVO);
                }
                result.setHostVOs(hostVOs);
            }
            result.setSuccess(true);
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        return result;
    }


    public HostScanWithVmsByEnvResult handleMessage(HostScanWithVmsByEnv hostScanByEnv) {
        log.info("receiving message for scanning hosts with vms" + "----" + "virtual type : " + hostScanByEnv.getVirtEnvType());

        log.info("msg id : " + hostScanByEnv.getMsgId());

        HostScanWithVmsByEnvResult result = new HostScanWithVmsByEnvResult();
        result = (HostScanWithVmsByEnvResult) BaseUtil.setResult(hostScanByEnv, HostScanWithVmsByEnvResult.class);
        try {
            List<Host> scanHostsByEnv = scanHandler.scanHostWithVmsByEnv(hostScanByEnv);
            if (scanHostsByEnv != null) {
                List<HostVO> hostVOs = new ArrayList<HostVO>();
                for (int i = 0; i < scanHostsByEnv.size(); i++) {
                    HostVO hostVO = BaseUtil.castObject(scanHostsByEnv.get(i), HostVO.class);
                    hostVOs.add(hostVO);
                }
                result.setHostVOs(hostVOs);
            }
        } catch (CommonAdapterException e) {
            
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            
            e.printStackTrace();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        return result;
    }

    public NetworkScanResult handleMessage(NetworkScan networkScan) {

        log.info("receiving message for scanning networks" + "----" + "virtual type : " + networkScan.getVirtEnvType());

        log.info("msg id : " + networkScan.getMsgId());

        NetworkScanResult networkScanResult = new NetworkScanResult();
        networkScanResult = (NetworkScanResult) BaseUtil.setResult(networkScan, NetworkScanResult.class);
        networkScanResult.setMsgId(networkScan.getMsgId());

        try {
            Network network = scanHandler.scanNetwork(networkScan);
            networkScanResult.setSuccess(true);

            NetworkVO networkVO = new NetworkVO();

            try {
                BeanUtils.copyProperty(networkVO, "svSwitchs", network.getvSwitchs());
                BeanUtils.copyProperty(networkVO, "dvSwitchs", network.getDvSwitchs());
//				BeanUtils.copyProperties(networkVO, network);

            } catch (Exception e) {
                e.printStackTrace();
            }
            networkScanResult.setDvSwitchs(networkVO.getDvSwitchs());
            networkScanResult.setSvSwitchs(networkVO.getSvSwitchs());

        } catch (CommonAdapterException e) {

            networkScanResult.setSuccess(false);
            networkScanResult.setErrCode(e.getErrCode());
            networkScanResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            networkScanResult.setSuccess(false);
            networkScanResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            networkScanResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return networkScanResult;

    }

    public VmVncConsoleResult handleMessage(VmVncConsole vmVncConsole) {

        log.info("receiving message for getting vmVncConsole" + "----" + "virtual type : " + vmVncConsole.getVirtEnvType());

        log.info("msg id : " + vmVncConsole.getMsgId());

        VmVncConsoleResult vmVncConsoleResult = new VmVncConsoleResult();
        vmVncConsoleResult = (VmVncConsoleResult) BaseUtil.setResult(vmVncConsole, VmVncConsoleResult.class);
        vmVncConsoleResult.setMsgId(vmVncConsole.getMsgId());

        try {
            String consoleUrl = vmHandler.getConsoleUrl(vmVncConsole);
            vmVncConsoleResult.setUrl(consoleUrl);
            vmVncConsoleResult.setSuccess(true);
        } catch (CommonAdapterException e) {
            vmVncConsoleResult.setErrCode(e.getErrCode());
            vmVncConsoleResult.setErrMsg(e.getErrMsg());
            vmVncConsoleResult.setSuccess(false);
        } catch (AdapterUnavailableException e) {
            vmVncConsoleResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmVncConsoleResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
            vmVncConsoleResult.setSuccess(false);
        }
        return vmVncConsoleResult;
    }

    public FloatingIpAttachResult handleMessage(FloatingIpAttach floatingIpAttach) {

        log.info("receiving message for attaching floatingIp" + "----" + "virtual type : " + floatingIpAttach.getVirtEnvType());

        log.info("msg id : " + floatingIpAttach.getMsgId());

        FloatingIpAttachResult result = new FloatingIpAttachResult();
        result = (FloatingIpAttachResult) BaseUtil.setResult(floatingIpAttach, FloatingIpAttachResult.class);
        result.setMsgId(floatingIpAttach.getMsgId());
        result.setFixedIp(floatingIpAttach.getFixedIp());
        result.setFloatingIpId(floatingIpAttach.getFloatingIpId());
        result.setFixedIpId(floatingIpAttach.getFixedIpId());
        result.setFloatingIp(floatingIpAttach.getFloatingIp());
        result.setServerId(floatingIpAttach.getServerId());
        try {
            CommonAdapterResult commonAdapterResult = netHandler.attachFloatingIp(floatingIpAttach);
            if(commonAdapterResult.getMap() != null) {
                result.setPort(Port.class.cast(commonAdapterResult.getMap().get("port")));
            }
            result.setSuccess(commonAdapterResult.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
            result.setSuccess(false);
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
            result.setSuccess(false);
        }
        return result;
    }

    public FloatingIpDetachResult handleMessage(FloatingIpDetach floatingIpDetach) {

        log.info("receiving message for detaching floatingIp" + "----" + "virtual type : " + floatingIpDetach.getVirtEnvType());

        log.info("msg id : " + floatingIpDetach.getMsgId());

        FloatingIpDetachResult result = new FloatingIpDetachResult();
        result = (FloatingIpDetachResult) BaseUtil.setResult(floatingIpDetach, FloatingIpDetachResult.class);
        result.setMsgId(floatingIpDetach.getMsgId());
        result.setFloatingIp(floatingIpDetach.getFloatingIp());
        result.setServerId(floatingIpDetach.getServerId());
        result.setFloatingIpId(floatingIpDetach.getFloatingIpId());
        try {
            boolean flag = netHandler.detachFloatingIp(floatingIpDetach);
            result.setSuccess(flag);
        } catch (CommonAdapterException e) {
            
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
            result.setSuccess(false);
        } catch (AdapterUnavailableException e) {
            
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
            result.setSuccess(false);
        }
        return result;
    }

    public SgCreateResult handleMessage(SgCreate sgCreate) {

        log.info("receiving message for creating securityGroup" + "----" + "virtual type : " + sgCreate.getVirtEnvType()
                + " network name :" + sgCreate.getName());

        log.info("msg id : " + sgCreate.getMsgId());
        SgCreateResult sgCreateResult = new SgCreateResult();
        sgCreateResult = (SgCreateResult) BaseUtil.setResult(sgCreate, SgCreateResult.class);
        sgCreateResult.setMsgId(sgCreate.getMsgId());
        sgCreateResult.setTenantId(sgCreate.getTenantId());
        sgCreateResult.setName(sgCreate.getName());
        sgCreateResult.setResId(sgCreate.getResId());
        try {
            SecurityGroupResult securityGroupResult = netHandler.createSecurityGroup(sgCreate);
            if (securityGroupResult != null) {
                if (securityGroupResult.getSecurityGroup() != null) {
                    BeanUtils.copyProperties(sgCreateResult, securityGroupResult.getSecurityGroup());
                }
                sgCreateResult.setSuccess(securityGroupResult.isSuccess());
            }
        } catch (CommonAdapterException e) {
            sgCreateResult.setSuccess(false);
            sgCreateResult.setErrCode(e.getErrCode());
            sgCreateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            sgCreateResult.setSuccess(false);
            sgCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            sgCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
            sgCreateResult.setSuccess(false);
        }
        return sgCreateResult;
    }

    public SgDeleteResult handleMessage(SgDelete sgDelete) {

        log.info("receiving message for creating securityGroup" + "----" + "virtual type : " + sgDelete.getVirtEnvType());

        log.info("msg id : " + sgDelete.getMsgId());
        SgDeleteResult sgDeleteResult = new SgDeleteResult();
        sgDeleteResult = (SgDeleteResult) BaseUtil.setResult(sgDelete, SgDeleteResult.class);
        sgDeleteResult.setMsgId(sgDelete.getMsgId());
        sgDeleteResult.setResId(sgDelete.getResId());
        try {
            boolean result = netHandler.deleteSecurityGroup(sgDelete);
            sgDeleteResult.setSuccess(result);
        } catch (CommonAdapterException e) {
            
            sgDeleteResult.setSuccess(false);
            sgDeleteResult.setErrCode(e.getErrCode());
            sgDeleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {
            

            sgDeleteResult.setSuccess(false);
            sgDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            sgDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            
            e.printStackTrace();
            sgDeleteResult.setSuccess(false);
        }
        return sgDeleteResult;
    }

    public SgRuleCreateResult handleMessage(SgRuleCreate sgRuleCreate) {
        log.info("receiving message for creating securitygrouprule" + "----" + "virtual type : " + sgRuleCreate.getVirtEnvType());

        log.info("msg id : " + sgRuleCreate.getMsgId());
        SgRuleCreateResult sgRuleCreateResult = new SgRuleCreateResult();
        sgRuleCreateResult = (SgRuleCreateResult) BaseUtil.setResult(sgRuleCreate, SgRuleCreateResult.class);
        sgRuleCreateResult.setMsgId(sgRuleCreate.getMsgId());
        try {
            SecurityGroupRulesResult createSgRule = netHandler.createSgRule(sgRuleCreate);
            sgRuleCreateResult.setSuccess(createSgRule.isSuccess());

        } catch (CommonAdapterException e) {
            
            sgRuleCreateResult.setErrCode(e.getErrCode());
            sgRuleCreateResult.setErrMsg(e.getErrMsg());
            sgRuleCreateResult.setSuccess(false);
        } catch (AdapterUnavailableException e) {
            
            sgRuleCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            sgRuleCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
            sgRuleCreateResult.setSuccess(false);
        }
        if (sgRuleCreateResult.isSuccess()) {
            log.info("[adapter] create securityGroupRule successfully");
        } else {
            log.info("[adapter] create securityGroupRule failure");
        }
        return sgRuleCreateResult;
    }

    public SgRuleDeleteResult handleMessage(SgRuleDelete sgRuleDelete) {
        log.info("receiving message for delete securityGroupRule" + "----" + "virtual type : " + sgRuleDelete.getVirtEnvType()
                + "securityGroup id :" + sgRuleDelete.getSgRuleId());

        log.info("msg id : " + sgRuleDelete.getMsgId());

        SgRuleDeleteResult sgRuleDeleteResult = new SgRuleDeleteResult();
        sgRuleDeleteResult = (SgRuleDeleteResult) BaseUtil.setResult(sgRuleDelete, SgRuleDeleteResult.class);
        sgRuleDeleteResult.setSgRuleId(sgRuleDelete.getSgRuleId());
        sgRuleDeleteResult.setMsgId(sgRuleDelete.getMsgId());

        try {
            boolean result = netHandler.deleteSgRule(sgRuleDelete);
            sgRuleDeleteResult.setSuccess(result);

        } catch (CommonAdapterException e) {
            sgRuleDeleteResult.setErrCode(e.getErrCode());
            sgRuleDeleteResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            
            sgRuleDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            sgRuleDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        if (sgRuleDeleteResult.isSuccess()) {
            log.info("[adapter] securityGroup id: " + sgRuleDelete.getSgRuleId() + " delete successfully");
        } else {
            log.info("[adapter] securityGroup id: " + sgRuleDelete.getSgRuleId() + " delete failure");
        }
        return sgRuleDeleteResult;
    }

    public SgRuleListQueryResult handleMessage(SgRuleListQuery sgRuleListQuery) {
        log.info("receiving message for delete securityGroupRule" + "----" + "virtual type : " + sgRuleListQuery.getVirtEnvType()
                + "securityGroup id :" + sgRuleListQuery.getSgId());

        log.info("msg id : " + sgRuleListQuery.getMsgId());

        SgRuleListQueryResult sgRuleListQueryResult = new SgRuleListQueryResult();
        sgRuleListQueryResult = (SgRuleListQueryResult) BaseUtil.setResult(sgRuleListQuery, SgRuleListQueryResult.class);
        sgRuleListQueryResult.setMsgId(sgRuleListQuery.getMsgId());
        try {
            SecurityGroup securityGroup = netHandler.querySgRuleList(sgRuleListQuery);
            List<SecurityGroupRule> securityGroupRules = securityGroup.getSecurityGroupRules();
            if (securityGroupRules != null) {
                List<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupRule> sGroupRules = new ArrayList<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupRule>();
                for (SecurityGroupRule securityGroupRule : securityGroupRules) {
                    com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupRule sGroupRule = new com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupRule();
                    BeanUtils.copyProperties(sGroupRule, securityGroupRule);
                    sGroupRules.add(sGroupRule);
                }
                sgRuleListQueryResult.setSecurityGroups(sGroupRules);
            }
            sgRuleListQueryResult.setSuccess(true);
        } catch (CommonAdapterException e) {
            sgRuleListQueryResult.setErrCode(e.getErrCode());
            sgRuleListQueryResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            
            sgRuleListQueryResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            sgRuleListQueryResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return sgRuleListQueryResult;
    }

    public VmQuerySgsResult handleMessage(VmQuerySgs vmQuerySgs) {
        log.info("receiving message for query vmSecurityGroups" + "----" + "virtual type : " + vmQuerySgs.getVirtEnvType()
                + "server id :" + vmQuerySgs.getServerId());

        log.info("msg id : " + vmQuerySgs.getMsgId());
        VmQuerySgsResult vmQuerySgsResult = new VmQuerySgsResult();
        vmQuerySgsResult = (VmQuerySgsResult) BaseUtil.setResult(vmQuerySgs, VmQuerySgsResult.class);
        try {
            VmSecurityGroups vmSecurityGroups = vmHandler.queryVmSecurityGroups(vmQuerySgs);
            vmQuerySgsResult.setSuccess(vmSecurityGroups.isSuccess());
            if (vmSecurityGroups.isSuccess()) {
                List<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup> securityGroups = vmSecurityGroups.getSecurityGroups();
                if (securityGroups != null) {
                    vmQuerySgsResult.setSecurityGroups(securityGroups);
                }
            }
        } catch (CommonAdapterException e) {
            vmQuerySgsResult.setErrCode(e.getErrCode());
            vmQuerySgsResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            vmQuerySgsResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmQuerySgsResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return vmQuerySgsResult;
    }

    public SecurityGroupQueryResult handleMessage(SecurityGroupQuery securityGroupQuery) {
        log.info("receiving message for query vmSecurityGroups" + "----" + "virtual type : " + securityGroupQuery.getVirtEnvType());

        log.info("msg id : " + securityGroupQuery.getMsgId());
        SecurityGroupQueryResult securityGroupQueryResult = new SecurityGroupQueryResult();
        securityGroupQueryResult = (SecurityGroupQueryResult) BaseUtil.setResult(securityGroupQuery, SecurityGroupQueryResult.class);
        try {
            List<SecurityGroup> querySecurityGroup = vmHandler.querySecurityGroup(securityGroupQuery);
            if (querySecurityGroup != null) {
                List<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup> securityGroups = new ArrayList<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup>();
                for (SecurityGroup securityGroup : querySecurityGroup) {
                    com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup sGroup = new com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup();
                    BeanUtils.copyProperties(sGroup, securityGroup);
                    securityGroups.add(sGroup);
                }
                securityGroupQueryResult.setSecurityGroups(securityGroups);
            }
        } catch (CommonAdapterException e) {
            securityGroupQueryResult.setErrCode(e.getErrCode());
            securityGroupQueryResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            securityGroupQueryResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            securityGroupQueryResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return securityGroupQueryResult;
    }

    public ServerSecurityGroupAddResult handleMessage(ServerSecurityGroupAdd securityGroupAdd) {

        log.info("receiving message for adding security group" + "----" + "virtual type : " + securityGroupAdd.getVirtEnvType()
                + " securityGroup name :" + securityGroupAdd.getSgName());

        log.info("msg id : " + securityGroupAdd.getMsgId());

        ServerSecurityGroupAddResult securityGroupAddResult = new ServerSecurityGroupAddResult();
        securityGroupAddResult = (ServerSecurityGroupAddResult) BaseUtil.setResult(securityGroupAdd, ServerSecurityGroupAddResult.class);
        securityGroupAddResult.setMsgId(securityGroupAdd.getMsgId());
        securityGroupAddResult.setServerId(securityGroupAdd.getServerId());
        securityGroupAddResult.setSgName(securityGroupAdd.getSgName());
        securityGroupAddResult.setResId(securityGroupAdd.getResId());

        try {
            boolean result = vmHandler.addSecurityGroup(securityGroupAdd);
            securityGroupAddResult.setSuccess(result);
        } catch (CommonAdapterException e) {
            
            securityGroupAddResult.setSuccess(false);
            securityGroupAddResult.setErrCode(e.getErrCode());
            securityGroupAddResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {
            

            securityGroupAddResult.setSuccess(false);
            securityGroupAddResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            securityGroupAddResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return securityGroupAddResult;
    }

    public ServerSecurityGroupDeleteResult handleMessage(ServerSecurityGroupDelete securityGroupDelete) {

        log.info("receiving message for adding security group" + "----" + "virtual type : " + securityGroupDelete.getVirtEnvType()
                + " securityGroup name :" + securityGroupDelete.getName());

        log.info("msg id : " + securityGroupDelete.getMsgId());

        ServerSecurityGroupDeleteResult securityGroupDeleteResult = new ServerSecurityGroupDeleteResult();
        securityGroupDeleteResult = (ServerSecurityGroupDeleteResult) BaseUtil.setResult(securityGroupDelete, ServerSecurityGroupDeleteResult.class);
        securityGroupDeleteResult.setMsgId(securityGroupDelete.getMsgId());

        try {
            vmHandler.deleteSecurityGroup(securityGroupDelete);
            securityGroupDeleteResult.setSuccess(true);
        } catch (CommonAdapterException e) {
            
            securityGroupDeleteResult.setSuccess(false);
            securityGroupDeleteResult.setErrCode(e.getErrCode());
            securityGroupDeleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {
            

            securityGroupDeleteResult.setSuccess(false);
            securityGroupDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            securityGroupDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return securityGroupDeleteResult;
    }

    public UserModifyResult handleMessage(UserModify userModify) {
        log.info("receiving message for change user password" + "----" + "virtual type : " + userModify.getVirtEnvType()
                + " userId:" + userModify.getUserId());

        log.info("msg id : " + userModify.getMsgId());

        UserModifyResult userModifyResult = new UserModifyResult();
        userModifyResult = (UserModifyResult) BaseUtil.setResult(userModify, UserModifyResult.class);
        try {
            boolean result = adminHandler.changeUserPwd(userModify);
            userModifyResult.setSuccess(result);
        } catch (CommonAdapterException e) {
            e.printStackTrace();
            userModifyResult.setErrCode(e.getErrCode());
            userModifyResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            
            e.printStackTrace();
            userModifyResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            userModifyResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return userModifyResult;
    }

    public RemoveUserFromTenantResult handleMessage(RemoveUserFromTenant removeUserFromTenant) {
        log.info("receiving message for removing user form tenant" + "----" + "virtual type : " + removeUserFromTenant.getVirtEnvType());

        log.info("msg id : " + removeUserFromTenant.getMsgId());
        RemoveUserFromTenantResult result = new RemoveUserFromTenantResult();
        result = (RemoveUserFromTenantResult) BaseUtil.setResult(removeUserFromTenant, RemoveUserFromTenantResult.class);
        try {
            boolean addresult = adminHandler.removeUserFromTenant(removeUserFromTenant);
            result.setSuccess(addresult);
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public AddUserToTenantResult handleMessage(AddUserToTenant addUserToTenant) {
        log.info("receiving message for adding user to tenant" + "----" + "virtual type : " + addUserToTenant.getVirtEnvType());

        log.info("msg id : " + addUserToTenant.getMsgId());
        AddUserToTenantResult result = new AddUserToTenantResult();
        result = (AddUserToTenantResult) BaseUtil.setResult(addUserToTenant, AddUserToTenantResult.class);
        try {
            boolean addresult = adminHandler.addUserToTenant(addUserToTenant);
            result.setSuccess(addresult);
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public KeypairCreateResult handleMessage(KeypairCreate keypairCreate) {
        log.info("receiving message for creating keyPair" + "----" + "virtual type : " + keypairCreate.getVirtEnvType());

        log.info("msg id : " + keypairCreate.getMsgId());

        KeypairCreateResult result = new KeypairCreateResult();
        result = (KeypairCreateResult) BaseUtil.setResult(keypairCreate, KeypairCreateResult.class);
        try {
            KeyPairResult createKeyPair = adminHandler.createKeyPair(keypairCreate);
            result = createKeyPair.getKeypairCreateResult();
            result.setSuccess(createKeyPair.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public KeypairGetResult handleMessage(KeypairGet keypairGet) {
        log.info("receiving message for getting keyPairInfo" + "----" + "virtual type : " + keypairGet.getVirtEnvType());

        log.info("msg id : " + keypairGet.getMsgId());
        KeypairGetResult result = new KeypairGetResult();
        result = (KeypairGetResult) BaseUtil.setResult(keypairGet, KeypairGetResult.class);
        try {
            KeyPairResult keyPairInfo = adminHandler.getKeyPairInfo(keypairGet);
            result = keyPairInfo.getKeypairGetResult();
            result.setSuccess(keyPairInfo.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public KeypairListGetResult handleMessage(KeypairListGet keypairListGet) {
        log.info("receiving message for getting keyPairList" + "----" + "virtual type : " + keypairListGet.getVirtEnvType());

        log.info("msg id : " + keypairListGet.getMsgId());
        KeypairListGetResult result = new KeypairListGetResult();
        result = (KeypairListGetResult) BaseUtil.setResult(keypairListGet, KeypairListGetResult.class);
        try {
            KeyPairResult keyPairInfo = adminHandler.getKeyPairList(keypairListGet);
            result = keyPairInfo.getKeypairListGetResult();
            result.setSuccess(keyPairInfo.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public KeypairDeleteResult handleMessage(KeypairDelete keypairDelete) {
        log.info("receiving message for getting keyPairList" + "----" + "virtual type : " + keypairDelete.getVirtEnvType());

        log.info("msg id : " + keypairDelete.getMsgId());
        KeypairDeleteResult result = new KeypairDeleteResult();
        result = (KeypairDeleteResult) BaseUtil.setResult(keypairDelete, KeypairDeleteResult.class);
        try {
            boolean deleteResult = adminHandler.deletekeyPair(keypairDelete);
            result.setSuccess(deleteResult);
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public KvmStorageScanResult handleMessage(KvmStorageScan kvmStorageScan) {
        log.info("receiving message for scan kvmstorages" + "----" + "virtual type : " + kvmStorageScan.getVirtEnvType());

        log.info("msg id : " + kvmStorageScan.getMsgId());
        KvmStorageScanResult result = new KvmStorageScanResult();
        result = (KvmStorageScanResult) BaseUtil.setResult(kvmStorageScan, KvmStorageScanResult.class);
        try {
            KvmStorages scanStorages = scanHandler.scanStorages(kvmStorageScan);
            if (scanStorages != null) {
                List<KvmStorage> storages = scanStorages.getStorages();
                if (!CollectionUtils.isEmpty(storages)) {
                    List<KvmStorageVo> storageVos = new ArrayList<KvmStorageVo>();
                    for (KvmStorage kvmStorage : storages) {
                        KvmStorageVo storageVo = new KvmStorageVo();
                        BeanUtils.copyProperties(storageVo, kvmStorage);
                        storageVos.add(storageVo);
                    }
                    result.setStorages(storageVos);
                }
            }
            result.setSuccess(scanStorages.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public CpuPoolScanResult handleMessage(CpuPoolScan cpuPoolScan) {
        log.info("receiving message for scan hmcCpuPools" + "----"
                + "virtual type : " + cpuPoolScan.getVirtEnvType());

        log.info("msg id : " + cpuPoolScan.getMsgId());
        CpuPoolScanResult result = new CpuPoolScanResult();
        result = (CpuPoolScanResult) BaseUtil.setResult(cpuPoolScan, CpuPoolScanResult.class);
        try {
            CpuPools CpuPools = scanHandler.scanCpuPools(cpuPoolScan);
            if (CpuPools != null && CpuPools.isSuccess()) {
                result.setCpuPools(CpuPools.getCpuPools());
                result.setSuccess(true);
            }
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public MparsScanResult handleMessage(MparsScan mparsScan) {
        log.info("receiving message for scan hmcmparsScan" + "----"
                + "virtual type : " + mparsScan.getVirtEnvType());

        log.info("msg id : " + mparsScan.getMsgId());
        MparsScanResult result = new MparsScanResult();
        result = (MparsScanResult) BaseUtil.setResult(mparsScan, MparsScanResult.class);
        try {
            Mpars mpars = scanHandler.scanMpars(mparsScan);
            if (mpars != null) {
                result.setVms(mpars.getVms());
                result.setSuccess(true);
            }
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public VswitchScanResult handleMessage(VswitchScan vswitchScan) {
        log.info("receiving message for scan hmcvswitchScan" + "----"
                + "virtual type : " + vswitchScan.getVirtEnvType());

        log.info("msg id : " + vswitchScan.getMsgId());
        VswitchScanResult result = new VswitchScanResult();
        result = (VswitchScanResult) BaseUtil.setResult(vswitchScan, VswitchScanResult.class);
        try {
            Vswitchs vswitchs = scanHandler.scanVswitchs(vswitchScan);
            if (vswitchs != null) {
                result.setvSwitchs(vswitchs.getvSwitchs());
                result.setSuccess(true);
            }
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public IoScanResult handleMessage(IoScan ioScan) {
        log.info("receiving message for scan hmcIoScan" + "----"
                + "virtual type : " + ioScan.getVirtEnvType());

        log.info("msg id : " + ioScan.getMsgId());
        IoScanResult result = new IoScanResult();
        result = (IoScanResult) BaseUtil.setResult(ioScan, IoScanResult.class);
        try {
            Ios ios = scanHandler.scanIo(ioScan);
            result.setIos(ios.getIos());
            result.setSuccess(ios.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public SSPScanResult handleMessage(SSPScan sspScan) {
        log.info("receiving message for scan hmc ssp Scan" + "----"
                + "virtual type : " + sspScan.getVirtEnvType());

        log.info("msg id : " + sspScan.getMsgId());

        SSPScanResult result = new SSPScanResult();
        result = (SSPScanResult) BaseUtil.setResult(sspScan, SSPScanResult.class);
        try {
            SSPResult scanSSP = scanHandler.scanSSP(sspScan);
            log.info("扫描存储池完成！");
            result.setSspVos(scanSSP.getSspVos());
            result.setSuccess(scanSSP.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public DiskScanResult handleMessage(DiskScan diskScan) {
        log.info("receiving message for scan hmc disk Scan" + "----"
                + "virtual type : " + diskScan.getVirtEnvType());

        log.info("msg id : " + diskScan.getMsgId());

        DiskScanResult result = new DiskScanResult();
        result = (DiskScanResult) BaseUtil.setResult(diskScan, DiskScanResult.class);
        try {
            SysDiskResult scanSysDisk = scanHandler.scanSysDisk(diskScan);
            log.info("扫描系统盘完成！");
            List<ViosResult> vios = new ArrayList<ViosResult>();
            List<ViosVo> viosList = scanSysDisk.getViosList();
            for (ViosVo viosVo : viosList) {
                ViosResult vResult = new ViosResult();
                BeanUtils.copyProperties(vResult, viosVo);
                vios.add(vResult);
            }
            result.setViosVos(vios);
            result.setSuccess(scanSysDisk.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public NpivScanResult handleMessage(NpivScan npivSan) {
        log.info("receiving message for scan hmc npiv Scan" + "----"
                + "virtual type : " + npivSan.getVirtEnvType());

        log.info("msg id : " + npivSan.getMsgId());

        NpivScanResult result = new NpivScanResult();
        result = (NpivScanResult) BaseUtil.setResult(npivSan, NpivScanResult.class);
        try {
            DataDiskResult scanNpiv = scanHandler.scanDataDisk(npivSan);
            log.info("扫描存储池完成！");
            result.setDisks(scanNpiv.getDisks());
            result.setSuccess(scanNpiv.isSuccess());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public AlarmListResult handleMessage(AlarmListGet alarmListGet) {
        log.info("receiving message for get alarm list" + "----"
                + "virtual type : " + alarmListGet.getVirtEnvType());

        log.info("msg id : " + alarmListGet.getMsgId());

        AlarmListResult result = new AlarmListResult();
        result = (AlarmListResult) BaseUtil.setResult(alarmListGet, AlarmListResult.class);
        try {
            AlarmVosResult alarmListResult = monitorHandler.getAlarmList(alarmListGet);
            log.info("获取告警信息完成！");
            result.setAlarmList(alarmListResult.getAlarmVos());
            result.setSuccess(true);
        } catch (CommonAdapterException e) {
            log.error("获取告警信息失败！");
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            log.error("获取告警信息失败！");
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            log.error("获取告警信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    public AlarmTriggerResult handleMessage(AlarmTriggerGet alarmTriggerGet) {
        log.info("receiving message for get alarm trigger" + "----"
                + "virtual type : " + alarmTriggerGet.getVirtEnvType());

        log.info("msg id : " + alarmTriggerGet.getMsgId());

        AlarmTriggerResult result = new AlarmTriggerResult();
        result = (AlarmTriggerResult) BaseUtil.setResult(alarmTriggerGet, AlarmTriggerResult.class);
        try {
            AlarmTriggerVosResult alarmTriggersResult = monitorHandler.getAlarmTriggers(alarmTriggerGet);
            log.info("获取告警规则完成！");
            result.setAlarmTriggerVos(alarmTriggersResult.getAlarmTriggerVos());
            result.setSuccess(true);
        } catch (CommonAdapterException e) {
            log.error("获取告警规则失败！");
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            log.error("获取告警规则失败！");
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            log.error("获取告规则失败！");
            e.printStackTrace();
        }
        return result;
    }

    public IOSlotScanResult handleMessage(IOSlotScan ioSlotScan) {
        log.info("receiving message for scan hmcIoslotScan" + "----"
                + "virtual type : " + ioSlotScan.getVirtEnvType());

        log.info("msg id : " + ioSlotScan.getMsgId());
        IOSlotScanResult result = new IOSlotScanResult();
        result = (IOSlotScanResult) BaseUtil.setResult(ioSlotScan, IOSlotScanResult.class);
        try {
            IOSlotResult scanIoSlots = scanHandler.scanIoSlots(ioSlotScan);
            result.setSuccess(scanIoSlots.isSuccess());
            result.setIoSlotVos(scanIoSlots.getIoSlotList());
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public VmAddUserResult handleMessage(VmUserAdd userAdd) {
        log.info("receiving message for add vm user");

        log.info("msg id : " + userAdd.getMsgId());
        VmAddUserResult result = new VmAddUserResult();
        try {
            boolean addResult = vmHandler.addUser(userAdd);
            result.setSuccess(addResult);
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrMsg(e.getMessage());
        }
        return result;
    }

    public BaseResult handleMessage(RouterCreate routerCreate){
        log.info("receiving message for updating image" + "----"
                + "virtual type : " + routerCreate.getVirtEnvType());

        log.info("msg id : " + routerCreate.getMsgId());
        RouterResult result = new RouterResult();
        result = (RouterResult) BaseUtil.setResult(routerCreate, RouterResult.class);
        result.setMsgId(routerCreate.getMsgId());
        try {
            CommonAdapterResult commonAdapterResult = netHandler.createRouter(routerCreate);
            result.setSuccess(commonAdapterResult.isSuccess());
            result.setRouter(Router.class.cast(commonAdapterResult.getMap().get("result")));
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public BaseResult handleMessage(RouterDelete routerDelete){
        log.info("receiving message for updating image" + "----"
                + "virtual type : " + routerDelete.getVirtEnvType());

        log.info("msg id : " + routerDelete.getMsgId());
        RouterResult result = new RouterResult();
        result = (RouterResult) BaseUtil.setResult(routerDelete, RouterResult.class);
        result.setMsgId(routerDelete.getMsgId());
        try {
            CommonAdapterResult commonAdapterResult = netHandler.deleteRouter(routerDelete);
            result.setSuccess(commonAdapterResult.isSuccess());
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public BaseResult handleMessage(RouterAddInterface routerAddInterface){
        log.info("receiving message for updating image" + "----"
                + "virtual type : " + routerAddInterface.getVirtEnvType());

        log.info("msg id : " + routerAddInterface.getMsgId());
        RouterResult result = new RouterResult();
        result = (RouterResult) BaseUtil.setResult(routerAddInterface, RouterResult.class);
        result.setMsgId(routerAddInterface.getMsgId());
        try {
            CommonAdapterResult commonAdapterResult = netHandler.addRouterInterface(routerAddInterface);
            result.setSuccess(commonAdapterResult.isSuccess());
            result.setPorts(Sets.newHashSet(Port.class.cast(commonAdapterResult.getMap().get("result"))));
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public BaseResult handleMessage(RouterAddExternalGateway routerAddExternalGateway){
        log.info("receiving message for updating image" + "----"
                + "virtual type : " + routerAddExternalGateway.getVirtEnvType());

        log.info("msg id : " + routerAddExternalGateway.getMsgId());
        RouterResult result = new RouterResult();
        result = (RouterResult) BaseUtil.setResult(routerAddExternalGateway, RouterResult.class);
        result.setMsgId(routerAddExternalGateway.getMsgId());
        try {
            CommonAdapterResult commonAdapterResult = netHandler.addRouterExternalGateway(routerAddExternalGateway);
            result.setSuccess(commonAdapterResult.isSuccess());
            result.setRouter(Router.class.cast(commonAdapterResult.getMap().get("result")));
            result.setPorts(Sets.newHashSet(Port.class.cast(commonAdapterResult.getMap().get("port"))));
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public BaseResult handleMessage(RouterRemoveExternalGateway routerRemoveExternalGateway){
        log.info("receiving message for updating image" + "----"
                + "virtual type : " + routerRemoveExternalGateway.getVirtEnvType());

        log.info("msg id : " + routerRemoveExternalGateway.getMsgId());
        RouterResult result = new RouterResult();
        result = (RouterResult) BaseUtil.setResult(routerRemoveExternalGateway, RouterResult.class);
        result.setMsgId(routerRemoveExternalGateway.getMsgId());
        try {
            CommonAdapterResult commonAdapterResult = netHandler.removeExternalGateway(routerRemoveExternalGateway);
            result.setSuccess(commonAdapterResult.isSuccess());
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public BaseResult handleMessage(RouterRemoveInterface routerRemoveInterface){
        log.info("receiving message for updating image" + "----"
                + "virtual type : " + routerRemoveInterface.getVirtEnvType());

        log.info("msg id : " + routerRemoveInterface.getMsgId());
        RouterResult result = new RouterResult();
        result = (RouterResult) BaseUtil.setResult(routerRemoveInterface, RouterResult.class);
        result.setMsgId(routerRemoveInterface.getMsgId());
        try {
            CommonAdapterResult commonAdapterResult = netHandler.removeRouterInterface(routerRemoveInterface);
            result.setSuccess(commonAdapterResult.isSuccess());
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
