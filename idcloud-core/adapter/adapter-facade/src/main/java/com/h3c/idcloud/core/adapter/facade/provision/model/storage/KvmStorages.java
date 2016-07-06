package com.h3c.idcloud.core.adapter.facade.provision.model.storage;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.List;

public class KvmStorages extends CommonAdapterResult {
    private List<KvmStorage> storages;

    public List<KvmStorage> getStorages() {
        return storages;
    }

    public void setStorages(List<KvmStorage> storages) {
        this.storages = storages;
    }
}
