package com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external;

import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.NetInfo;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeNet;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class ExternalNodeNet extends ActionExternal {

    private static Logger log = LoggerFactory.getLogger(ExternalNodeNet.class);

    @Override
    protected CommonAdapterResult execute(Base base)
            throws CommonAdapterException {

        NodeNet nodeNet = (NodeNet) base;

        NetInfo netInfo = null;

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            Response resp = client
                    .path("/nodes/monitor/" + nodeNet.getNodeId()
                            + "/network/current")
                    .type(MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() == 200) {
                netInfo = resp.readEntity(NetInfo.class);
                netInfo.setSuccess(true);
            } else {

            }

        } catch (ClientException e) {
            e.printStackTrace();

        }

        return netInfo;

    }

}
