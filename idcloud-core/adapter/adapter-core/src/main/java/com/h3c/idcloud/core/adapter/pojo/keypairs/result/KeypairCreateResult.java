package com.h3c.idcloud.core.adapter.pojo.keypairs.result;


import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.keyVo.KeyVo;

public class KeypairCreateResult extends BaseResult {
    private KeyVo keyVo;

    public KeyVo getKeyVo() {
        return keyVo;
    }

    public void setKeyVo(KeyVo keyVo) {
        this.keyVo = keyVo;
    }

}
