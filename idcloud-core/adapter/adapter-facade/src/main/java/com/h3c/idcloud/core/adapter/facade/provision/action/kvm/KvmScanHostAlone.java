package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Hosts;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanAlone;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmScanHostAlone extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        Hosts hosts = new Hosts();
        HostScanAlone hostScanAlone = (HostScanAlone) base;
        log.info("host scanning alone...");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        try {
            Response resp = client.path("/hypervisors/get_with_servers").query("hostname", hostScanAlone.getName()).type(MediaType.APPLICATION_JSON).get();
            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("hypervisor")) {
                    Host host = BaseUtil.castObject(respMap.get("hypervisor"), Host.class);
                    hosts.setHost(host);
                }
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when scanning host");
            throw new AdapterUnavailableException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return hosts;
    }
}
