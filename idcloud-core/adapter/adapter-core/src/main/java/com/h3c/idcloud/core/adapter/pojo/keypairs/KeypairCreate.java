package com.h3c.idcloud.core.adapter.pojo.keypairs;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class KeypairCreate extends Base {
    private String name;

    private String publicKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

}
