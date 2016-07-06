package com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external;

import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.Node;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCreate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExternalNodeCreate extends ActionExternal {

    private static Logger log = LoggerFactory.getLogger(ExternalNodeCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base)
            throws CommonAdapterException {

        NodeCreate nodeCreate = (NodeCreate) base;

        Node node = new Node();

        String json = helper.getParam(nodeCreate.getProviderType(),
                nodeCreate);

        log.info("node create post data : " + json);

        // WebClient client = WebClient.create(this.getAdapterUrl(),
        // this.providers);
        //
        // HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        // conduit.getClient().setReceiveTimeout(0);
        //
        // client.accept(MediaType.APPLICATION_JSON);
        //
        // try {
        // Response resp = client.path("/nodes/get")
        // .type(MediaType.APPLICATION_JSON).post(json);
        //
        // if (resp.getStatus() == 200) {
        // node = resp.readEntity(Node.class);
        // node.setSuccess(true);
        // } else {
        // throw new CommonAdapterException("exception");
        // }
        //
        // } catch (ClientException e) {
        // e.printStackTrace();
        // throw new CommonAdapterException("exception");
        // }

        node.setNodeId(nodeCreate.getNodeId());
        return node;

    }

}
