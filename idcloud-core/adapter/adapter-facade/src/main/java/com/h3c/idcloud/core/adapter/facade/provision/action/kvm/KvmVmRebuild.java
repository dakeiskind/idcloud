package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.ServerNic;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRebuild;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmVmRebuild extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmVmRebuild.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        VmRebuild vmRebuild = (VmRebuild) base;

        Server server = new Server();

        String json = helper.getParam(vmRebuild.getProviderType(), vmRebuild);

        log.info("vm rebuild post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);
        Map respMap = null;

        try {
            Response resp = client.path("/" + vmRebuild.getTenantId() + "/servers/" + vmRebuild.getId() + "/action").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("server")) {
                    Map serverMap = (Map) respMap.get("server");
                    List<ServerNic> serverNics = new ArrayList<ServerNic>();
                    if (serverMap.containsKey("id")) {
                        server.setUuid((String) serverMap.get("id"));
                    }
                    List<Map> nicMap = (List<Map>) serverMap.get("networks");
                    for (Map map : nicMap) {
                        ServerNic serverNic = new ServerNic();
                        serverNic.setMac((String) map.get("mac_address"));
                        serverNic.setPrivateIp((String) map.get("fixed_ip"));
                        serverNic.setSubnetId((String) map.get("subnet_id"));
                        serverNic.setNetId((String) map.get("network_id"));
                        serverNics.add(serverNic);
                    }
                    server.setNics(serverNics);
                }
                server.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when rebuilding vm");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return server;
    }

}
