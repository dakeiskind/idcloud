package com.h3c.idcloud.core.adapter.pojo.block;


import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class BlockBackupCreate extends Base {

    private String volumeId;

    private String name;

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
