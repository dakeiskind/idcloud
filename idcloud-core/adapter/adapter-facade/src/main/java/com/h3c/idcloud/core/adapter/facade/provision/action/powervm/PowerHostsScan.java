package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Hosts;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByEnv;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerHostsScan extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        HostScanByEnv hostScanByEnv = (HostScanByEnv) base;
        Hosts hosts = new Hosts();
        log.info("host scanning by envPowervm...");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        String json = helper.getParam(hostScanByEnv.getProviderType(), hostScanByEnv);

        log.info("扫描cpu池参数：" + json);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);
        client.accept(MediaType.APPLICATION_JSON);
        try {
            Response resp = client.path("/hmc/scan/hosts").type(MediaType.APPLICATION_JSON).post(json);
            if (resp.getStatus() == 200) {
                Map<?, ?> respMap = resp.readEntity(Map.class);
//				List<Host> castObject = BaseUtil.castObject(respMap, List.class);
                if (respMap.containsKey("hypervisors")) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> hostlist = BaseUtil.castObject(respMap.get("hypervisors"), List.class);
                    if (!CollectionUtils.isEmpty(hostlist)) {

                        List<Host> hostvos = new ArrayList<Host>();
                        for (int i = 0; i < hostlist.size(); i++) {
                            Host hostVO = BaseUtil.castObject(hostlist.get(i), Host.class);
                            hostvos.add(hostVO);
                        }
                        hosts.setHypervisors(hostvos);
                    }
                }
//				BeanUtils.copyProperties(hosts, respMap);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when scanning host");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof PowerException) {
                throw (PowerException) e;
            } else {
                log.error(e.getMessage());
                throw new PowerException("500", e.getMessage());
            }
        }
        return hosts;
    }
}
