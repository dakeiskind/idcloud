package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.VmScanAlone;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmGetVmInfo extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        VmScanAlone vmScanAlone = (VmScanAlone) base;
//		ServerInfo serverInfo = new ServerInfo();
        Server serverInfo = new Server();
        log.info("vminfo get");

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);

        Response resp = null;
        try {
            resp = client.path("/" + vmScanAlone.getTenantId() + "/servers/" + vmScanAlone.getUuid()).type(MediaType.APPLICATION_JSON).get();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Map respMap = null;
        if (resp != null) {

            if (resp.getStatus() == 200) {
                respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("server")) {
                    try {
                        serverInfo = BaseUtil.castObject(respMap.get("server"), Server.class);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                serverInfo.setSuccess(true);
                ;

            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        }
        return serverInfo;
    }
}
