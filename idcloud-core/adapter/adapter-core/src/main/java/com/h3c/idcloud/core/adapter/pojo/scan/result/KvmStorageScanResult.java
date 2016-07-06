package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.KvmStorageVo;

import java.util.List;

public class KvmStorageScanResult extends BaseResult {

    private List<KvmStorageVo> storages;

    public List<KvmStorageVo> getStorages() {
        return storages;
    }

    public void setStorages(List<KvmStorageVo> storages) {
        this.storages = storages;
    }
}
