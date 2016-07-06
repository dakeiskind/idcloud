package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class VmSnapshotCreateResult extends BaseResult {

    private String vmName;
    private String snapshotName;
    private String snapshotDesp;
    private String uuid;
    private String imageId;
    private String imageName;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getSnapshotName() {
        return snapshotName;
    }

    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

    public String getSnapshotDesp() {
        return snapshotDesp;
    }

    public void setSnapshotDesp(String snapshotDesp) {
        this.snapshotDesp = snapshotDesp;
    }

}
