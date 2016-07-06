package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionStorage;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerDataStoreCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerDataStoreDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerDataStoreExtend;
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

public class ProvisionDiskPowerImpl implements ProvisionStorage {

    @Autowired
    private PowerDataStoreCreate powerDatastoreCreate;

    @Autowired
    private PowerDataStoreDelete powerDatastoreDelete;

    @Autowired
    private PowerDataStoreExtend powerDatastoreExtend;

    @Override
    public Volume createDisk(DiskCreate diskCreate) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getDisk(DiskGet diskGet) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteDisk(DiskDelete diskDelete) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean expandDisk(DiskExpand diskExpand) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean attachDisk(DiskAttach diskAttach) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean detachDisk(DiskDetach diskDetach) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public BlockListResult getBlockList(BlockList blockList) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BlockInfoGetResult getBlockInfo(BlockInfoGet blockInfoGet) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BlockSnapshotCreateResult createBlockSnapshot(BlockSnapshotCreate blockSnapshotCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BlockBackupCreateResult createBlockBackup(BlockBackupCreate backupCreate) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BlockBackupRecoveryResult recoveryBlockBachup(BlockBackupRecovery backupRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BlockSnapshotRecovryResult recoveryBlockSnapshot(BlockSnapshotRecovery blockSnapshotRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BlockCloneResult cloneBlock(BlockClone blockClone) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteBlockSnapshot(BlockSnapshotDelete blockSnapshotDelete) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DataStoreVo createDataStore(DataStoreCreate create) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return (DataStoreVo) powerDatastoreCreate.invoke(create);
    }

    @Override
    public boolean deleteDataStore(DataStoreDelete delete) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return powerDatastoreDelete.invoke(delete).isSuccess();
    }

    @Override
    public DataStoreVo extendDataStore(DataStoreExtend extend) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return (DataStoreVo) powerDatastoreExtend.invoke(extend);
    }

    @Override
    public boolean refreshDataStore(DataStoreRefresh refresh) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean rescanDataStore(DataStoreReScan reScan) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

}
