package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Cluster;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScanAlone;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class VmwareClusterScanAlone extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareClusterScanAlone.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException, AdapterUnavailableException {

        ClusterScanAlone clusterScanAlone = (ClusterScanAlone) base;

        String json = helper.getParam(clusterScanAlone.getProviderType(), clusterScanAlone);

        log.info("cluster scan alone post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        Cluster cluster = null;

        try {
            Response resp = client.path("/cluster/asyncCluster/" + clusterScanAlone.getName())
                    .type(MediaType.APPLICATION_JSON).post(json);

            this.checkResponseStatus(resp);

            if (resp.getStatus() == 200) {
                cluster = resp.readEntity(Cluster.class);
                cluster.setSuccess(true);
            } else {
                VmwareException vmwareException = resp.readEntity(VmwareException.class);
                throw vmwareException;
            }

        } catch (ClientException e) {
            log.info("vmware adapter is not available when scanning cluster alone");
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
        return cluster;
    }

}
