package com.h3c.idcloud.core.adapter.facade;

import com.h3c.idcloud.core.adapter.facade.common.ProviderFactory;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionStorage;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
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
public class StorageHandler implements ProvisionStorage {

    @Autowired
    private ProviderFactory provider;

    public boolean expandDisk(DiskExpand diskExpand) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(diskExpand.getProviderType()).expandDisk(diskExpand);
    }

    public Volume createDisk(DiskCreate diskCreate) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(diskCreate.getProviderType()).createDisk(diskCreate);
    }

    public boolean getDisk(DiskGet diskGet) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(diskGet.getProviderType()).getDisk(diskGet);
    }

    public boolean deleteDisk(DiskDelete diskDelete) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(diskDelete.getProviderType()).deleteDisk(diskDelete);
    }

    public boolean attachDisk(DiskAttach diskAttach) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(diskAttach.getProviderType()).attachDisk(diskAttach);
    }

    public boolean detachDisk(DiskDetach diskDetach) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(diskDetach.getProviderType()).detachDisk(diskDetach);
    }

    @Override
    public BlockListResult getBlockList(BlockList blockList)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(blockList.getProviderType()).getBlockList(blockList);
    }

    @Override
    public BlockInfoGetResult getBlockInfo(BlockInfoGet blockInfoGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(blockInfoGet.getProviderType()).getBlockInfo(blockInfoGet);
    }

    @Override
    public BlockSnapshotCreateResult createBlockSnapshot(BlockSnapshotCreate blockSnapshotCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(blockSnapshotCreate.getProviderType())
                .createBlockSnapshot(blockSnapshotCreate);
    }

    @Override
    public BlockBackupCreateResult createBlockBackup(BlockBackupCreate backupCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(backupCreate.getProviderType()).createBlockBackup(backupCreate);
    }

    @Override
    public BlockBackupRecoveryResult recoveryBlockBachup(BlockBackupRecovery backupRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(backupRecovery.getProviderType()).recoveryBlockBachup(backupRecovery);
    }

    @Override
    public BlockSnapshotRecovryResult recoveryBlockSnapshot(BlockSnapshotRecovery blockSnapshotRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(blockSnapshotRecovery.getProviderType())
                .recoveryBlockSnapshot(blockSnapshotRecovery);
    }

    @Override
    public BlockCloneResult cloneBlock(BlockClone blockClone)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(blockClone.getProviderType()).cloneBlock(blockClone);
    }

    @Override
    public boolean deleteBlockSnapshot(BlockSnapshotDelete blockSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(blockSnapshotDelete.getProviderType())
                .deleteBlockSnapshot(blockSnapshotDelete);
    }

    @Override
    public DataStoreVo createDataStore(DataStoreCreate create)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(create.getProviderType()).createDataStore(create);
    }

    @Override
    public boolean deleteDataStore(DataStoreDelete delete) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(delete.getProviderType()).deleteDataStore(delete);
    }

    @Override
    public DataStoreVo extendDataStore(DataStoreExtend extend)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(extend.getProviderType()).extendDataStore(extend);
    }

    @Override
    public boolean refreshDataStore(DataStoreRefresh refresh)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(refresh.getProviderType()).refreshDataStore(refresh);
    }

    @Override
    public boolean rescanDataStore(DataStoreReScan rescan) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionStorage(rescan.getProviderType()).rescanDataStore(rescan);
    }
}