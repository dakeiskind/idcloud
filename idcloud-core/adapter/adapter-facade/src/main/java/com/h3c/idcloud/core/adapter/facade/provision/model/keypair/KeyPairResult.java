package com.h3c.idcloud.core.adapter.facade.provision.model.keypair;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairCreateResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairListGetResult;

public class KeyPairResult extends CommonAdapterResult {
    private KeypairCreateResult keypairCreateResult = new KeypairCreateResult();
    private KeypairDeleteResult keypairDeleteResult = new KeypairDeleteResult();
    private KeypairGetResult keypairGetResult = new KeypairGetResult();
    private KeypairListGetResult keypairListGetResult = new KeypairListGetResult();

    public KeypairCreateResult getKeypairCreateResult() {
        return keypairCreateResult;
    }

    public void setKeypairCreateResult(KeypairCreateResult keypairCreateResult) {
        this.keypairCreateResult = keypairCreateResult;
    }

    public KeypairDeleteResult getKeypairDeleteResult() {
        return keypairDeleteResult;
    }

    public void setKeypairDeleteResult(KeypairDeleteResult keypairDeleteResult) {
        this.keypairDeleteResult = keypairDeleteResult;
    }

    public KeypairGetResult getKeypairGetResult() {
        return keypairGetResult;
    }

    public void setKeypairGetResult(KeypairGetResult keypairGetResult) {
        this.keypairGetResult = keypairGetResult;
    }

    public KeypairListGetResult getKeypairListGetResult() {
        return keypairListGetResult;
    }

    public void setKeypairListGetResult(KeypairListGetResult keypairListGetResult) {
        this.keypairListGetResult = keypairListGetResult;
    }

}
