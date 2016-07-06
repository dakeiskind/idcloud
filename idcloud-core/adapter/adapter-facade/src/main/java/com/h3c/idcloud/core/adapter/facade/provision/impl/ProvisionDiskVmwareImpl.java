package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionStorage;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDataStoreCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDataStoreDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDataStoreExtend;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDataStoreRefresh;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDataStoreRescan;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDiskAttach;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDiskCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDiskDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDiskDetach;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareDiskExpand;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.DataStoreVo;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Volume;
import com.h3c.idcloud.core.adapter.pojo.block.BlockBackupCreate;
import com.h3c.idcloud.core.adapter.pojo.block.BlockBackupRecovery;
import com.h3c.idcloud.core.adapter.pojo.block.BlockClone;
import com.h3c.idcloud.core.adapter.pojo.block.BlockInfoGet;
import com.h3c.idcloud.core.adapter.pojo.block.BlockList;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotCreate;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotDelete;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotRecovery;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupCreateResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupRecoveryResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockCloneResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockInfoGetResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockListResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotCreateResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotRecovryResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreCreate;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreDelete;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreExtend;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreReScan;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreRefresh;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskAttach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskCreate;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDetach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskExpand;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskGet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvisionDiskVmwareImpl implements ProvisionStorage {

    @Autowired
    private VmwareDiskCreate vmwareDiskCreate;

    @Autowired
    private VmwareDiskExpand vmwareDiskExpand;

    @Autowired
    private VmwareDiskAttach vmwareDiskAttach;

    @Autowired
    private VmwareDiskDetach vmwareDiskDetach;

    @Autowired
    private VmwareDiskDelete vmwareDiskDelete;

    @Autowired
    private VmwareDataStoreCreate vmwareDatastoreCreate;

    @Autowired
    private VmwareDataStoreDelete vmwareDatastoreDelete;

    @Autowired
    private VmwareDataStoreExtend vmwareDatastoreExtend;

    @Autowired
    private VmwareDataStoreRefresh vmwareDatastoreRefresh;

    @Autowired
    private VmwareDataStoreRescan vmwareDatastoreRescan;

    public boolean expandDisk(DiskExpand diskExpand) throws VmwareException, AdapterUnavailableException {
        CommonAdapterResult result = vmwareDiskExpand.invoke(diskExpand);
        return result.isSuccess();
    }

    public boolean attachDisk(DiskAttach diskAttach) throws VmwareException, AdapterUnavailableException {
        CommonAdapterResult result = vmwareDiskAttach.invoke(diskAttach);
        return result.isSuccess();
    }

    public boolean detachDisk(DiskDetach diskDetach) throws VmwareException, AdapterUnavailableException {
        CommonAdapterResult result = vmwareDiskDetach.invoke(diskDetach);
        return result.isSuccess();
    }

    public Volume createDisk(DiskCreate diskCreate) throws VmwareException, AdapterUnavailableException {
        CommonAdapterResult result = vmwareDiskCreate.invoke(diskCreate);
        return (Volume) result;
    }

    public boolean getDisk(DiskGet diskGet) throws VmwareException, AdapterUnavailableException {
        return false;
    }

    public boolean deleteDisk(DiskDelete diskDelete) throws VmwareException, AdapterUnavailableException {
        CommonAdapterResult result = vmwareDiskDelete.invoke(diskDelete);
        return result.isSuccess();
    }


    @Override
    public BlockListResult getBlockList(BlockList blockList)
            throws CommonAdapterException, AdapterUnavailableException {
        return null;
    }

    @Override
    public BlockInfoGetResult getBlockInfo(BlockInfoGet blockInfoGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return null;
    }

    @Override
    public BlockSnapshotCreateResult createBlockSnapshot(BlockSnapshotCreate blockSnapshotCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return null;
    }

    @Override
    public BlockBackupCreateResult createBlockBackup(BlockBackupCreate backupCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return null;
    }

    @Override
    public BlockBackupRecoveryResult recoveryBlockBachup(BlockBackupRecovery backupRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        return null;
    }

    @Override
    public BlockSnapshotRecovryResult recoveryBlockSnapshot(BlockSnapshotRecovery blockSnapshotRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        return null;
    }

    @Override
    public BlockCloneResult cloneBlock(BlockClone blockClone)
            throws CommonAdapterException, AdapterUnavailableException {
        return null;
    }

    @Override
    public boolean deleteBlockSnapshot(BlockSnapshotDelete blockSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return false;
    }

    @Override
    public DataStoreVo createDataStore(DataStoreCreate create)
            throws CommonAdapterException, AdapterUnavailableException {
        return (DataStoreVo) vmwareDatastoreCreate.invoke(create);
    }

    @Override
    public boolean deleteDataStore(DataStoreDelete delete)
            throws CommonAdapterException, AdapterUnavailableException {
        return vmwareDatastoreDelete.invoke(delete).isSuccess();
    }

    @Override
    public DataStoreVo extendDataStore(DataStoreExtend extend)
            throws CommonAdapterException, AdapterUnavailableException {
        return (DataStoreVo) vmwareDatastoreExtend.invoke(extend);
    }

    @Override
    public boolean refreshDataStore(DataStoreRefresh refresh)
            throws CommonAdapterException, AdapterUnavailableException {
        return vmwareDatastoreRefresh.invoke(refresh).isSuccess();
    }

    @Override
    public boolean rescanDataStore(DataStoreReScan reScan) throws CommonAdapterException, AdapterUnavailableException {
        return vmwareDatastoreRescan.invoke(reScan).isSuccess();
    }
}