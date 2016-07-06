package com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external;

import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.Node;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeDelete;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class ExternalNodeDelete extends ActionExternal {

    private static Logger log = LoggerFactory.getLogger(ExternalNodeDelete.class);

    @Override
    protected CommonAdapterResult execute(Base base)
            throws CommonAdapterException {

        NodeDelete nodeDelete = (NodeDelete) base;

        Node node = null;

        String json = helper.getParam(nodeDelete.getProviderType(),
                nodeDelete);

        log.info("node delete post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            Response resp = client.path("/nodes/get")
                    .type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                node = resp.readEntity(Node.class);
                node.setSuccess(true);
            } else {

            }

        } catch (ClientException e) {
            e.printStackTrace();

        }

        return node;

    }

}
