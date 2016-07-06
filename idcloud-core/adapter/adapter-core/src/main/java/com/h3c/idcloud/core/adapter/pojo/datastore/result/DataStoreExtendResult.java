package com.h3c.idcloud.core.adapter.pojo.datastore.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreVo;

public class DataStoreExtendResult extends BaseResult {
    private String hostName;
    private String datastoreName;
    private DataStoreVo dataStoreVo;
    private String sid;
    private int newSize;  //增量

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }


    public int getNewSize() {
        return newSize;
    }

    public void setNewSize(int newSize) {
        this.newSize = newSize;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDatastoreName() {
        return datastoreName;
    }

    public void setDatastoreName(String datastoreName) {
        this.datastoreName = datastoreName;
    }

    public DataStoreVo getDataStoreVo() {
        return dataStoreVo;
    }

    public void setDataStoreVo(DataStoreVo dataStoreVo) {
        this.dataStoreVo = dataStoreVo;
    }
}
