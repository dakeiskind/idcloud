package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;


import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmImageCreate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmVmImageCreate extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmVmImageCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        VmImageCreate vmImageCreate = (VmImageCreate) base;

        Server server = new Server();
        System.out.println(vmImageCreate);
        String json = helper.getParam(vmImageCreate.getProviderType(),
                vmImageCreate);

        log.info("vm rebuild post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);

        try {
            Response resp = client
                    .path("/" + vmImageCreate.getTenantId() + "/servers/"
                            + vmImageCreate.getId() + "/action")
                    .type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                server.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when imagecreating vm");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return server;
    }

}
