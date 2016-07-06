package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreDelete;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerDataStoreDelete extends ActionPowervm {

    private static Logger log = LoggerFactory.getLogger(PowerDataStoreDelete.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {

        DataStoreDelete delete = (DataStoreDelete) base;
        CommonAdapterResult result = new CommonAdapterResult();
        String json = helper.getParam(delete.getProviderType(), delete);

        log.info("datastore delete post data : " + json);

        String j = json.toString();
        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);
        conduit.getClient().setConnectionTimeout(10 * 1000);

        client.accept(MediaType.APPLICATION_JSON);
        Response resp = null;
        try {
            resp = client.path("/datastore/delete")
                    .type(MediaType.APPLICATION_JSON).post(j);

            if (resp.getStatus() == 200) {
                result.setSuccess(true);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                throw exception;
            }

        } catch (ClientException e) {
            log.info("power adapter is not available when deleting datastore");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (e instanceof VmwareException) {
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                throw exception;
            }
        }
        return result;
    }
}
