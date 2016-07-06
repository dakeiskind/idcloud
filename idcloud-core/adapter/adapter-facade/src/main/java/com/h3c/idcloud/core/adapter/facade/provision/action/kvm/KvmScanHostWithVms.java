package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Hosts;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmScanHostWithVms extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        Hosts hosts = new Hosts();

//		String json = helper.getParam(templateScan.getProviderType(), templateScan);

        log.info("host with vms scanning by env...");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        try {
            Response resp = client.path("resources").query("params", "{\"hypervisors\": [{\"servers\":[]}]}").type(MediaType.APPLICATION_JSON).get();
            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("hypervisors")) {
                    List<Map<String, Object>> hostlist = BaseUtil.castObject(respMap.get("hypervisors"), List.class);
                    if (!CollectionUtils.isEmpty(hostlist)) {

                        List<Host> hostvos = new ArrayList<Host>();
                        for (int i = 0; i < hostlist.size(); i++) {
                            Host hostVO = BaseUtil.castObject(hostlist.get(i), Host.class);
                            hostvos.add(hostVO);
                        }
                        hosts.setListdata(hostvos);
                    }
                }

            } else {
                Map respMap = resp.readEntity(Map.class);
                System.out.println(respMap);
            }

        } catch (ClientException e) {
            log.info("kvm adapter is not available when scanning host with vms");
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
