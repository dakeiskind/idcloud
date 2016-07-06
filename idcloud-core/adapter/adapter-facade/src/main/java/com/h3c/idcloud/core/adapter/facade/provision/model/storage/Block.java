package com.h3c.idcloud.core.adapter.facade.provision.model.storage;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupCreateResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupRecoveryResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockCloneResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockInfoGetResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockListResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotCreateResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotRecovryResult;

public class Block extends CommonAdapterResult {

    private BlockListResult blockListResult = new BlockListResult();

    private BlockSnapshotCreateResult blockSnapshotCreateResult = new BlockSnapshotCreateResult();

    private BlockInfoGetResult blockInfoGetResult = new BlockInfoGetResult();

    private BlockBackupCreateResult backupCreateResult = new BlockBackupCreateResult();

    private BlockCloneResult blockCloneResult = new BlockCloneResult();

    private BlockSnapshotRecovryResult blockSnapshotRecovryResult = new BlockSnapshotRecovryResult();

    private BlockBackupRecoveryResult backupRecoveryResult = new BlockBackupRecoveryResult();

    private BlockSnapshotDeleteResult deleteResult = new BlockSnapshotDeleteResult();

    public BlockSnapshotDeleteResult getDeleteResult() {
        return deleteResult;
    }

    public void setDeleteResult(BlockSnapshotDeleteResult deleteResult) {
        this.deleteResult = deleteResult;
    }

    public BlockCloneResult getBlockCloneResult() {
        return blockCloneResult;
    }

    public void setBlockCloneResult(BlockCloneResult blockCloneResult) {
        this.blockCloneResult = blockCloneResult;
    }

    public BlockSnapshotRecovryResult getBlockSnapshotRecovryResult() {
        return blockSnapshotRecovryResult;
    }

    public void setBlockSnapshotRecovryResult(
            BlockSnapshotRecovryResult blockSnapshotRecovryResult) {
        this.blockSnapshotRecovryResult = blockSnapshotRecovryResult;
    }

    public BlockBackupRecoveryResult getBackupRecoveryResult() {
        return backupRecoveryResult;
    }

    public void setBackupRecoveryResult(
            BlockBackupRecoveryResult backupRecoveryResult) {
        this.backupRecoveryResult = backupRecoveryResult;
    }

    public BlockListResult getBlockListResult() {
        return blockListResult;
    }

    public void setBlockListResult(BlockListResult blockListResult) {
        this.blockListResult = blockListResult;
    }

    public BlockSnapshotCreateResult getBlockSnapshotCreateResult() {
        return blockSnapshotCreateResult;
    }

    public void setBlockSnapshotCreateResult(
            BlockSnapshotCreateResult blockSnapshotCreateResult) {
        this.blockSnapshotCreateResult = blockSnapshotCreateResult;
    }

    public BlockInfoGetResult getBlockInfoGetResult() {
        return blockInfoGetResult;
    }

    public void setBlockInfoGetResult(BlockInfoGetResult blockInfoGetResult) {
        this.blockInfoGetResult = blockInfoGetResult;
    }

    public BlockBackupCreateResult getBackupCreateResult() {
        return backupCreateResult;
    }

    public void setBackupCreateResult(BlockBackupCreateResult backupCreateResult) {
        this.backupCreateResult = backupCreateResult;
    }


}

