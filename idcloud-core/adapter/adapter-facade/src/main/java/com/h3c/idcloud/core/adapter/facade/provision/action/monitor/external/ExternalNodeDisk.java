package com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external;

import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.DiskInfo;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeDisk;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class ExternalNodeDisk extends ActionExternal {

    private static Logger log = LoggerFactory.getLogger(ExternalNodeDisk.class);

    @Override
    protected CommonAdapterResult execute(Base base)
            throws CommonAdapterException {

        NodeDisk nodeDisk = (NodeDisk) base;

        DiskInfo diskInfo = null;

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            Response resp = client
                    .path("/nodes/monitor/" + nodeDisk.getNodeId()
                            + "/diskusage/current")
                    .type(MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() == 200) {
                diskInfo = resp.readEntity(DiskInfo.class);
                diskInfo.setSuccess(true);
            } else {

            }

        } catch (ClientException e) {
            e.printStackTrace();
        }

        return diskInfo;
    }

}
