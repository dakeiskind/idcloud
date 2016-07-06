package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataStoreVO;

import java.util.ArrayList;
import java.util.List;

public class StorageScanResult extends BaseResult {

    private List<DataStoreVO> datastores = new ArrayList<DataStoreVO>();

    public List<DataStoreVO> getDatastores() {
        return datastores;
    }

    public void setDatastores(List<DataStoreVO> datastores) {
        this.datastores = datastores;
    }


}
