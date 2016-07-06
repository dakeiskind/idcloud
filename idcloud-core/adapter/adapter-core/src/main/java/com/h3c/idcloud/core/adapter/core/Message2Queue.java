package com.h3c.idcloud.core.adapter.core;

import java.util.HashMap;

public class Message2Queue {

    public final static HashMap<String, String> AsynMessageMapper = new HashMap<String, String>();
    public final static HashMap<String, String> SynMessageMapper = new HashMap<String, String>();

    static {

        AsynMessageMapper.put("VmCreate", "heavy.vm.create");
        AsynMessageMapper.put("VmOperate", "light.vm.operate");
        AsynMessageMapper.put("VmQuery", "heavy.vm.query");
        AsynMessageMapper.put("VmReconfig", "heavy.vm.reconfig");
        AsynMessageMapper.put("VmRebuild", "heavy.vm.rebuild");
        AsynMessageMapper.put("VmRemove", "heavy.vm.remove");
        AsynMessageMapper.put("VmNicAdd", "heavy.vm.addnic");
        AsynMessageMapper.put("VmNicDelete", "heavy.vm.deletenic");
        AsynMessageMapper.put("VmModify", "heavy.vm.modify");
        AsynMessageMapper.put("VmMigrate", "heavy.vm.migrate");
        AsynMessageMapper.put("VmRecovery", "heavy.vm.recovery");
        AsynMessageMapper.put("SoftwareDeploy", "heavy.vm.software");

        AsynMessageMapper.put("VmSnapshotCreate", "heavy.vm.createSnap");
        AsynMessageMapper.put("VmSnapshotRevert", "heavy.vm.revertSnap");
        AsynMessageMapper.put("VmSnapshotDelete", "heavy.vm.deleteSnap");

        AsynMessageMapper.put("DiskFormat", "light.disk.format");
        AsynMessageMapper.put("DiskCreate", "heavy.disk.create");
        AsynMessageMapper.put("DiskDelete", "heavy.disk.delete");
        AsynMessageMapper.put("DiskAttach", "light.disk.attach");
        AsynMessageMapper.put("DiskDetach", "light.disk.detach");
        AsynMessageMapper.put("BlockSnapshotCreate", "light.disk.createsnapshot");
        AsynMessageMapper.put("BlockSnapshotDelete", "light.disk.deletesnapshot");
        AsynMessageMapper.put("BlockBackupCreate", "heavy.disk.createbackup");
        AsynMessageMapper.put("BlockBackupRecovery", "heavy.disk.backuprecovery");
        AsynMessageMapper.put("BlockSnapshotRecovery", "heavy.disk.snapshotrecovery");
        AsynMessageMapper.put("DiskExpand", "heavy.disk.expand");
        //image
        AsynMessageMapper.put("ImageUpdate", "light.vm.imageupdate");
        AsynMessageMapper.put("ImageDelete", "light.vm.imagedelete");
        //vmuser
        SynMessageMapper.put("VmUserAdd", "sync.vm.useradd");

        //keypair
        SynMessageMapper.put("KeypairCreate", "sync.keypair.create");
        SynMessageMapper.put("KeypairDelete", "sync.keypair.delete");
        SynMessageMapper.put("KeypairGet", "sync.keypair.getinfo");
        SynMessageMapper.put("KeypairListGet", "sync.keypair.getlist");
        //network
        SynMessageMapper.put("SgCreate", "sync.network.sgcreate");
        SynMessageMapper.put("SgDelete", "sync.network.sgdelete");
        AsynMessageMapper.put("NetCreate", "heavy.network.netcreate");
        AsynMessageMapper.put("NetDelete", "heavy.network.netdelete");
        AsynMessageMapper.put("FloatingIpCreate", "light.network.floatingipcreate");
        AsynMessageMapper.put("FloatingIpDelete", "light.network.floatingipdelete");
        //disk
        SynMessageMapper.put("VmQuery", "sync.vm.query");
        SynMessageMapper.put("NodeCreate", "sync.monitor.create");
        SynMessageMapper.put("NodeUpdate", "sync.monitor.update");
        SynMessageMapper.put("NodeDelete", "sync.monitor.delete");
        SynMessageMapper.put("NodeSummary", "sync.monitor.summary");
        SynMessageMapper.put("NodeCpu", "sync.monitor.cpu");
        SynMessageMapper.put("NodeCpuHis", "sync.monitor.cpuhis");
        SynMessageMapper.put("NodeDisk", "sync.monitor.disk");
        SynMessageMapper.put("NodeDiskHis", "sync.monitor.diskhis");
        SynMessageMapper.put("NodeNet", "sync.nonitor.net");
        SynMessageMapper.put("NodeNetHis", "sync.monitor.nethis");
        SynMessageMapper.put("NodeMem", "sync.monitor.mem");
        SynMessageMapper.put("NodeMemHis", "sync.monitor.memhis");

        SynMessageMapper.put("VmSnapshotQuery", "sync.vm.querySnap");
        SynMessageMapper.put("VmRename", "sync.vm.rename");
        SynMessageMapper.put("VmVncConsole", "sync.vm.vncconsole");

        SynMessageMapper.put("RoleList", "sync.role.list");
        SynMessageMapper.put("UserRoleList", "sync.userrole.list");
        SynMessageMapper.put("UserListGet", "sync.user.list");
        SynMessageMapper.put("TenantCreate", "sync.tenant.create");
        SynMessageMapper.put("TenantDelete", "sync.tenant.delete");
        SynMessageMapper.put("TenantListGet", "sync.tenant.list");
        SynMessageMapper.put("TenantUserInit", "sync.tenant.user.init");
        SynMessageMapper.put("TenantResourcesDelete", "sync.tenant.deleteresources");
        SynMessageMapper.put("UserCreate", "sync.user.create");
        SynMessageMapper.put("UserDelete", "sync.user.delete");
        SynMessageMapper.put("UserModify", "sync.user.modify");
        SynMessageMapper.put("RemoveUserFromTenant", "sync.userrole.removeformtenant");
        SynMessageMapper.put("AddUserToTenant", "sync.userrole.addtotenant");
        SynMessageMapper.put("UserRoleAdd", "sync.userrole.add");
        SynMessageMapper.put("UserRoleDelete", "sync.userrole.delete");
        //alarm
        SynMessageMapper.put("AlarmListGet", "sync.alarm.alarmListGet");
        SynMessageMapper.put("AlarmTriggerGet", "sync.alarm.alarmTriggerGet");
        //datastore
        AsynMessageMapper.put("DataStoreCreate", "heavy.disk.dscreate");
        AsynMessageMapper.put("DataStoreDelete", "heavy.disk.dsdelete");
        AsynMessageMapper.put("DataStoreExtend", "heavy.disk.dsextend");
        /*SynMessageMapper.put("DataStoreRefresh", "sync.datastore.refresh");
        SynMessageMapper.put("DataStoreReScan", "sync.datastore.rescan");*/
        //network
        SynMessageMapper.put("VmQuerySgs", "sync.network.vmsgquery");
        SynMessageMapper.put("ServerSecurityGroupAdd", "sync.network.serversgadd");
        SynMessageMapper.put("ServerSecurityGroupDelete", "sync.network.serversgdel");
        SynMessageMapper.put("SecurityGroupQuery", "sync.network.querysglist");
        SynMessageMapper.put("SgRuleCreate", "sync.network.sgrulecreate");
        SynMessageMapper.put("SgRuleDelete", "sync.network.sgruledelete");
        SynMessageMapper.put("SgRuleListQuery", "sync.network.sgrulesquery");
        SynMessageMapper.put("FloatingIpAttach", "sync.network.floatingipattach");
        SynMessageMapper.put("FloatingIpDetach", "sync.network.floatingipdetach");

        //router
        SynMessageMapper.put("RouterCreate", "sync.router.create");
        SynMessageMapper.put("RouterDelete", "sync.router.delete");
        SynMessageMapper.put("RouterAddInterface", "sync.router.addInterface");
        SynMessageMapper.put("RouterAddExternalGateway", "sync.router.addExternalGateway");
        SynMessageMapper.put("RouterRemoveExternalGateway", "sync.router.removeExternalGateway");
        SynMessageMapper.put("RouterRemoveInterface", "sync.router.removeInterface");

        // scan
//		AsynMessageMapper.put("AllInOneScan", "heavy.vm.allinone");
        SynMessageMapper.put("EnvConnectionTest", "sync.connection.test");
        SynMessageMapper.put("KvmStorageScan", "sync.scan.kvmstorges");
        SynMessageMapper.put("AllInOneScan", "sync.scan.allinone");
        SynMessageMapper.put("ClusterScan", "sync.scan.cluster");
        SynMessageMapper.put("ClusterScanAlone", "sync.scan.clusteralone");
        SynMessageMapper.put("HostScanAlone", "sync.scan.hostalone");
        SynMessageMapper.put("HostScanByEnv", "sync.scan.hostbyenv");
        SynMessageMapper.put("HostScanWithVmsByEnv", "sync.scan.hostwithvm");
        SynMessageMapper.put("HostScanByCluster", "sync.scan.hostbycluster");
        SynMessageMapper.put("HostScanByDvs", "sync.scan.hostbydvs");
        SynMessageMapper.put("HostScanBySvs", "sync.scan.hostbysvs");
        SynMessageMapper.put("NetworkScan", "sync.scan.network");
        SynMessageMapper.put("StorageScan", "sync.scan.storage");
        SynMessageMapper.put("VmScanAlone", "sync.scan.vmalone");
        SynMessageMapper.put("CpuPoolScan", "sync.scan.cpuPpols");
        SynMessageMapper.put("VswitchScan", "sync.scan.vswitchs");
        SynMessageMapper.put("IoScan", "sync.scan.io");
        SynMessageMapper.put("IOSlotScan", "sync.scan.ioslot");
        SynMessageMapper.put("NpivScan", "sync.scan.npiv");
        SynMessageMapper.put("DiskScan", "sync.scan.sysdisk");
        SynMessageMapper.put("SSPScan", "sync.scan.ssp");
        SynMessageMapper.put("MparsScan", "sync.scan.mpars");
        SynMessageMapper.put("AvailabilityZoneQuery", "sync.scan.availability");
        AsynMessageMapper.put("TemplateScan", "heavy.vm.templatescan");

    }

}
