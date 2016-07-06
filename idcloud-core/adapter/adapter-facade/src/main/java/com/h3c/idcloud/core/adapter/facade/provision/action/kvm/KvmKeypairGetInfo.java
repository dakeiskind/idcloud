package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.keypair.KeyPairModel;
import com.h3c.idcloud.core.adapter.facade.provision.model.keypair.KeyPairResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.keyVo.KeyVo;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmKeypairGetInfo extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        KeypairGet keypairGet = (KeypairGet) base;

        KeyPairResult result = new KeyPairResult();
        log.info("keypair get info");

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        client = BaseUtil.setHeaders(client, base);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        try {
            Response resp = client
                    .path("/" + keypairGet.getTenantId() + "/keypairs/" + keypairGet.getKeyName())
                    .type(MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("keypair")) {
                    KeyPairModel keyPairModel = BaseUtil.castObject(
                            respMap.get("keypair"), KeyPairModel.class);
                    KeypairGetResult createResult = new KeypairGetResult();
                    KeyVo keyVo = new KeyVo();
                    BeanUtils.copyProperties(keyVo, keyPairModel);
                    createResult.setKeyVo(keyVo);
                    result.setKeypairGetResult(createResult);
                }
                result.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }

        } catch (ClientException e) {
            log.info("kvm adapter is not available when getting keypair info");
            throw new AdapterUnavailableException();

        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return result;
    }
}
