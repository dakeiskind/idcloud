package com.h3c.idcloud.core.adapter.pojo.keypairs;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class KeypairGet extends Base {
    private String keyName;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

}
