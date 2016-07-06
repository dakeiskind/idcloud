package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SimpleHost;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SimpleHosts;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByCluster;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class VmwareHostScanByCluster extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareHostScanByCluster.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException, AdapterUnavailableException {

        HostScanByCluster hostScanByCluster = (HostScanByCluster) base;

        String json = helper.getParam(hostScanByCluster.getProviderType(), hostScanByCluster);

        log.info("host scan post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        SimpleHosts hosts = new SimpleHosts();

        try {

            Response resp = client.path("/cluster/getHostByCluster").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                List<SimpleHost> listHosts = resp.readEntity(new GenericType<List<SimpleHost>>() {
                });
                hosts.setListdata(listHosts);
            } else {
                VmwareException vmwareException = resp.readEntity(VmwareException.class);
                throw vmwareException;
            }

        } catch (ClientException e) {

            log.info("vmware adapter is not available when scanning hosts by cluster");
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
        return hosts;
    }
}
