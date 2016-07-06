package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmMigrate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class VmwareVmMigrate extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareVmMigrate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException,
            AdapterUnavailableException {

        VmMigrate vmMigrate = (VmMigrate) base;

        CommonAdapterResult result = new CommonAdapterResult();

        String json = helper.getParam(vmMigrate.getProviderType(), vmMigrate);

        log.info("vm migrate post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            Response resp = client.path("/vm/relocate/")
                    .type(MediaType.APPLICATION_JSON).post(json);

            this.checkResponseStatus(resp);

            if (resp.getStatus() == 200) {

                result.setSuccess(true);
            } else {
                VmwareException vmwareException = resp
                        .readEntity(VmwareException.class);
                throw vmwareException;
            }

        } catch (ClientException e) {
            log.info("vmware adapter is not available when migrating vm");
            throw new AdapterUnavailableException();

        } catch (Exception e) {
            log.error(e.getMessage());
            if (e instanceof VmwareException) {
                throw (VmwareException) e;
            } else {
                VmwareException vmwareException = new VmwareException("500", e.getMessage());
                throw vmwareException;
            }
        }

        return result;

    }
}
