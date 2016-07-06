package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.keypair.KeyPairResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairCreate;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairCreateResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.keyVo.KeyVo;

import org.jclouds.openstack.nova.v2_0.domain.KeyPair;
import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/4/5.
 *
 * @author qct
 */
@Service
public class OpenStackKeypairCreate extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        Optional<KeyPairApi> keyPairApiOptional = NovaApiFactory.INSTANCE.createNovaApi(base).getKeyPairApi(
                base.getRegion());
        if(keyPairApiOptional.isPresent()) {
            KeyPair keyPair = null;
            try {
                keyPair = keyPairApiOptional.get().createWithPublicKey(KeypairCreate.class.cast(base).getName(),
                        KeypairCreate.class.cast(base).getPublicKey());
            } catch (IllegalStateException e) {
                e.printStackTrace();
                throw new KvmException("500", e.getMessage());
            }

            KeyVo keyVo = new KeyVo();
            keyVo.setName(keyPair.getName());
            keyVo.setPublicKey(keyPair.getPublicKey());
            keyVo.setPrivateKey(keyPair.getPrivateKey());
            keyVo.setFingerprint(keyPair.getFingerprint());

            KeypairCreateResult keypairCreateResult = new KeypairCreateResult();
            keypairCreateResult.setSuccess(true);
            keypairCreateResult.setKeyVo(keyVo);

            KeyPairResult keyPairResult = new KeyPairResult();
            keyPairResult.setSuccess(true);
            keyPairResult.setKeypairCreateResult(keypairCreateResult);

            return keyPairResult;
        }else {
            throw new KvmException("500", "KeyPairApi is absent");
        }
    }
}
