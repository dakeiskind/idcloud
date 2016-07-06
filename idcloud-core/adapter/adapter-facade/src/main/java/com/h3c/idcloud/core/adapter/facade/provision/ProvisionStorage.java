package com.h3c.idcloud.core.adapter.facade.provision;

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

public interface ProvisionStorage {
    Volume createDisk(DiskCreate diskCreate) throws CommonAdapterException, AdapterUnavailableException;

    boolean getDisk(DiskGet diskGet) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteDisk(DiskDelete diskDelete) throws CommonAdapterException, AdapterUnavailableException;

    boolean expandDisk(DiskExpand diskExpand) throws CommonAdapterException, AdapterUnavailableException;

    boolean attachDisk(DiskAttach diskAttach) throws CommonAdapterException, AdapterUnavailableException;

    boolean detachDisk(DiskDetach diskDetach) throws CommonAdapterException, AdapterUnavailableException;

    BlockListResult getBlockList(BlockList blockList) throws CommonAdapterException, AdapterUnavailableException;

    BlockInfoGetResult getBlockInfo(BlockInfoGet blockInfoGet)
            throws CommonAdapterException, AdapterUnavailableException;

    BlockSnapshotCreateResult createBlockSnapshot(BlockSnapshotCreate blockSnapshotCreate)
            throws CommonAdapterException, AdapterUnavailableException;

    BlockBackupCreateResult createBlockBackup(BlockBackupCreate backupCreate)
            throws CommonAdapterException, AdapterUnavailableException;

    BlockBackupRecoveryResult recoveryBlockBachup(BlockBackupRecovery backupRecovery)
            throws CommonAdapterException, AdapterUnavailableException;

    BlockSnapshotRecovryResult recoveryBlockSnapshot(BlockSnapshotRecovery blockSnapshotRecovery)
            throws CommonAdapterException, AdapterUnavailableException;

    BlockCloneResult cloneBlock(BlockClone blockClone) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteBlockSnapshot(BlockSnapshotDelete blockSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException;

    DataStoreVo createDataStore(DataStoreCreate create) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteDataStore(DataStoreDelete delete) throws CommonAdapterException, AdapterUnavailableException;

    DataStoreVo extendDataStore(DataStoreExtend extend) throws CommonAdapterException, AdapterUnavailableException;

    boolean refreshDataStore(DataStoreRefresh refresh) throws CommonAdapterException, AdapterUnavailableException;

    boolean rescanDataStore(DataStoreReScan reScan) throws CommonAdapterException, AdapterUnavailableException;
}
