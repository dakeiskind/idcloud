package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.FloatingIp;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpCreate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmFloatingIpCreate extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        FloatingIpCreate floatingIpCreate = (FloatingIpCreate) base;

        FloatingIp result = new FloatingIp();

        String json = helper.getParam(floatingIpCreate.getProviderType(), floatingIpCreate);

        log.info("create folatingIp post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        client = BaseUtil.setHeaders(client, base);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        Map respMap = null;
        try {
            Response resp = client.path("/" + floatingIpCreate.getTenantId() + "/floating_ips").type(MediaType.APPLICATION_JSON)
                    .post(json);

            if (resp.getStatus() == 200) {
                respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("floating_ip")) {
                    Map map = (Map) respMap.get("floating_ip");
                    String floatingIpAddr = (String) map.get("floating_ip_address");
                    result.getFloatingIpCreateResult().setFloatingIpAddr(floatingIpAddr);
                    String floatingIpId = (String) map.get("id");
                    result.getFloatingIpCreateResult().setFloatingIpId(floatingIpId);
                }
                result.getFloatingIpCreateResult().setNetworkId(floatingIpCreate.getFloatingNetworkId());
                result.getFloatingIpCreateResult().setSuccess(true);
                result.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when attaching disk");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return result;
    }
}
