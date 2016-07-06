package com.h3c.idcloud.core.adapter.pojo.block;


import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class BlockBackupRecovery extends Base {

    private String volume;

    private String backupId;

    private String volumeId;


    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId;
    }


}
