package com.h3c.idcloud.core.adapter.facade.provision.action.openstack;

import com.google.common.base.Optional;

import com.h3c.idcloud.core.adapter.facade.common.NovaApiFactory;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.ActionKvm;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairCreate;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairDelete;

import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.springframework.stereotype.Service;

/**
 * Created by qct on 2016/4/5.
 *
 * @author qct
 */
@Service
public class OpenStackKeypairDelete extends ActionKvm {
    @Override
    protected CommonAdapterResult execute(Base base) throws CommonAdapterException, AdapterUnavailableException {
        Optional<KeyPairApi> keyPairApiOptional = NovaApiFactory.INSTANCE.createNovaApi(base).getKeyPairApi(
                base.getRegion());
        CommonAdapterResult commonAdapterResult = new CommonAdapterResult();
        if(keyPairApiOptional.isPresent()) {
            commonAdapterResult.setSuccess(keyPairApiOptional.get().delete(KeypairDelete.class.cast(base).getKeyName()));
        }else {
            throw new KvmException("500", "KeyPairApi is absent");
        }
        return commonAdapterResult;
    }
}
