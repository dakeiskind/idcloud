package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;


import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairDelete;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmKeypairDelete extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws
            AdapterUnavailableException, KvmException {
        KeypairDelete keypairDelete = (KeypairDelete) base;

        CommonAdapterResult result = new CommonAdapterResult();
        log.info("keypair delete");

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        client = BaseUtil.setHeaders(client, base);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            Response resp = client
                    .path("/" + keypairDelete.getTenantId() + "/keypairs/"
                            + keypairDelete.getKeyName())
                    .type(MediaType.APPLICATION_JSON).delete();

            if (resp.getStatus() == 200) {
                result.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }

        } catch (ClientException e) {
            log.info("kvm adapter is not available when deletingkeypair");
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
