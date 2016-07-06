package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmModify;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class VmwareVmModify extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareVmModify.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException,
            AdapterUnavailableException {

        VmModify vmModify = (VmModify) base;

        String json = helper.getParam(vmModify.getProviderType(), vmModify);

        log.info("vm modify post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        Server server = null;

        try {
            Response resp = client.path("/vm/reConfigAll/")
                    .type(MediaType.APPLICATION_JSON).post(json);

            this.checkResponseStatus(resp);

            if (Math.floor(resp.getStatus() / 100) == 2) {
                String jsonResult = resp.readEntity(String.class);
                try {
                    server = BaseUtil.fromJson(jsonResult, Server.class);
                    server.setSuccess(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                VmwareException vmwareException = resp
                        .readEntity(VmwareException.class);
                throw vmwareException;
            }

        } catch (ClientException e) {
            log.info("vmware adapter is not available when modifing vm");
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
        return server;
    }

}
