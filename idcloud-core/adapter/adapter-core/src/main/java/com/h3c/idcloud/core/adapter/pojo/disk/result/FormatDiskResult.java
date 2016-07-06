package com.h3c.idcloud.core.adapter.pojo.disk.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class FormatDiskResult extends BaseResult {
    private String deviceTagert;//设备标示  平台不用传
    private String mountLocal;//挂载点
    private String fileSystem;//文件系统
    private String lvmParam;//lvm的参数
    private boolean success;

    public String getDeviceTagert() {
        return deviceTagert;
    }

    public void setDeviceTagert(String deviceTagert) {
        this.deviceTagert = deviceTagert;
    }

    public String getMountLocal() {
        return mountLocal;
    }

    public void setMountLocal(String mountLocal) {
        this.mountLocal = mountLocal;
    }

    public String getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(String fileSystem) {
        this.fileSystem = fileSystem;
    }

    public String getLvmParam() {
        return lvmParam;
    }

    public void setLvmParam(String lvmParam) {
        this.lvmParam = lvmParam;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
