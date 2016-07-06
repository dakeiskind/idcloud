package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionStorage;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlockClone;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlockCreateBackup;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlockCreateSnapshot;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlockDeleteSnapshot;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlockGetInfo;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlockListGet;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlockRecoveryBackup;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmDiskAttach;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmDiskCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmDiskDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmDiskDetach;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmDiskExpand;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmStoragesScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVolumeAttach;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVolumeCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVolumeDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVolumeDetach;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.Block;
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
public class ProvisionDiskKvmImpl implements ProvisionStorage {

//    @Autowired
//    private KvmDiskCreate kvmDiskCreate;

//    @Autowired
//    private KvmDiskDelete kvmDiskDelete;

//    @Autowired
//    private KvmDiskAttach kvmDiskAttach;

//    @Autowired
//    private KvmDiskDetach kvmDiskDetach;

    @Autowired
    private KvmBlockListGet kvmBlockListGet;

    @Autowired
    private KvmBlockCreateBackup kvmBlockCreateBackup;

    @Autowired
    private KvmBlockCreateSnapshot kvmBlockCreateSnapshot;

    @Autowired
    private KvmBlockDeleteSnapshot kvmBlockDeleteSnapshot;

    @Autowired
    private KvmBlockClone kvmBlockClone;

    @Autowired
    private KvmBlockRecoveryBackup kvmBlockRecoveryBackup;

    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlockRecoverySnapshot KvmBlockRecoverySnapshot;
    @Autowired
    private KvmBlockGetInfo kvmBlockGetInfo;

    @Autowired
    private KvmDiskExpand kvmDiskExpand;
    @Autowired
    private KvmStoragesScan kvmStorageScan;

    //OpenStack
    @Autowired
    private OpenStackVolumeCreate openStackVolumeCreate;
    @Autowired
    private OpenStackVolumeDelete openStackVolumeDelete;
    @Autowired
    private OpenStackVolumeAttach openStackVolumeAttach;
    @Autowired
    private OpenStackVolumeDetach openStackVolumeDetach;

    public Volume createDisk(DiskCreate diskCreate) throws CommonAdapterException, AdapterUnavailableException {
//        CommonAdapterResult result = kvmDiskCreate.invoke(diskCreate);
        return Volume.class.cast(openStackVolumeCreate.invoke(diskCreate));
    }

    public boolean getDisk(DiskGet diskGet) throws CommonAdapterException, AdapterUnavailableException {
        return false;
    }

    public boolean deleteDisk(DiskDelete diskDelete) throws CommonAdapterException, AdapterUnavailableException {
//        CommonAdapterResult result = kvmDiskDelete.invoke(diskDelete);
        return openStackVolumeDelete.invoke(diskDelete).isSuccess();
    }

    public boolean expandDisk(DiskExpand diskExpand) throws CommonAdapterException, AdapterUnavailableException {
        return kvmDiskExpand.invoke(diskExpand).isSuccess();
    }

    public boolean attachDisk(DiskAttach diskAttach) throws CommonAdapterException, AdapterUnavailableException {
//        CommonAdapterResult result = kvmDiskAttach.invoke(diskAttach);
        return openStackVolumeAttach.invoke(diskAttach).isSuccess();
    }

    public boolean detachDisk(DiskDetach diskDetach) throws CommonAdapterException, AdapterUnavailableException {
//        CommonAdapterResult result = kvmDiskDetach.invoke(diskDetach);
        return openStackVolumeDetach.invoke(diskDetach).isSuccess();
    }

    @Override
    public BlockListResult getBlockList(BlockList blockList)
            throws CommonAdapterException, AdapterUnavailableException {
        Block block = (Block) kvmBlockListGet.invoke(blockList);
        return block.getBlockListResult();
    }

    @Override
    public BlockInfoGetResult getBlockInfo(BlockInfoGet blockInfoGet)
            throws CommonAdapterException, AdapterUnavailableException {
        Block block = (Block) kvmBlockGetInfo.invoke(blockInfoGet);
        return block.getBlockInfoGetResult();
    }

    @Override
    public BlockSnapshotCreateResult createBlockSnapshot(BlockSnapshotCreate blockSnapshotCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        Block block = (Block) kvmBlockCreateSnapshot.invoke(blockSnapshotCreate);
        return block.getBlockSnapshotCreateResult();
    }

    @Override
    public BlockBackupCreateResult createBlockBackup(BlockBackupCreate backupCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        Block block = (Block) kvmBlockCreateBackup.invoke(backupCreate);
        return block.getBackupCreateResult();
    }

    @Override
    public BlockBackupRecoveryResult recoveryBlockBachup(BlockBackupRecovery backupRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        Block block = (Block) kvmBlockRecoveryBackup.invoke(backupRecovery);
        return block.getBackupRecoveryResult();
    }

    @Override
    public BlockSnapshotRecovryResult recoveryBlockSnapshot(BlockSnapshotRecovery blockSnapshotRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        Block block = (Block) KvmBlockRecoverySnapshot.invoke(blockSnapshotRecovery);
        return block.getBlockSnapshotRecovryResult();
    }

    @Override
    public BlockCloneResult cloneBlock(BlockClone blockClone)
            throws CommonAdapterException, AdapterUnavailableException {
        Block block = (Block) kvmBlockClone.invoke(blockClone);
        return block.getBlockCloneResult();
    }

    @Override
    public boolean deleteBlockSnapshot(BlockSnapshotDelete blockSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return kvmBlockDeleteSnapshot.invoke(blockSnapshotDelete).isSuccess();
    }

    @Override
    public DataStoreVo createDataStore(DataStoreCreate create)
            throws CommonAdapterException, AdapterUnavailableException {
        return null;
    }

    @Override
    public boolean deleteDataStore(DataStoreDelete delete)
            throws CommonAdapterException, AdapterUnavailableException {
        return false;
    }

    @Override
    public DataStoreVo extendDataStore(DataStoreExtend extend) throws CommonAdapterException,
            AdapterUnavailableException {
        return null;
    }

    @Override
    public boolean refreshDataStore(DataStoreRefresh refresh) throws CommonAdapterException,
            AdapterUnavailableException {
        return false;
    }

    @Override
    public boolean rescanDataStore(DataStoreReScan reScan) throws CommonAdapterException, AdapterUnavailableException {
        return false;
    }
}