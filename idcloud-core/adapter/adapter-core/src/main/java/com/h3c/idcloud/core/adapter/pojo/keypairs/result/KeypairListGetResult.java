package com.h3c.idcloud.core.adapter.pojo.keypairs.result;


import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.keyVo.KeyVo;

import java.io.Serializable;
import java.util.List;

public class KeypairListGetResult extends BaseResult implements Serializable {
    private List<KeyVo> keyVos;

    public List<KeyVo> getKeyVos() {
        return keyVos;
    }

    public void setKeyVos(List<KeyVo> keyVos) {
        this.keyVos = keyVos;
    }

}
