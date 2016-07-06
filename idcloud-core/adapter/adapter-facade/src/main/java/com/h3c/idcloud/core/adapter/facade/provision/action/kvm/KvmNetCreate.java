package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;


import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.springframework.stereotype.Service;

@Service
public class KvmNetCreate extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        /*NetCreate netCreate = (NetCreate) base;
        NetworkResult networkResult = new NetworkResult();

        String json = helper.getParam(netCreate.getProviderType(), netCreate);

        log.info("network creating post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);

        try {
            Response resp = client.path("/" + netCreate.getTenantId() + "/networks").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                networkResult = resp.readEntity(NetworkResult.class);
                networkResult.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when creating network");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return networkResult;*/
        return null;
    }

}
