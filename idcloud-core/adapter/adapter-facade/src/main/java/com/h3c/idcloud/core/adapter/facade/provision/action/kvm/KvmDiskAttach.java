package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Block;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskAttach;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmDiskAttach extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmDiskAttach.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException, AdapterUnavailableException {

        DiskAttach diskAttach = (DiskAttach) base;

        Block block = new Block();

        String json = helper.getParam(diskAttach.getProviderType(), diskAttach);

        log.info("disk attach post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);

        try {
            Response resp = client.path("/" + diskAttach.getTenantId() + "/servers/" + diskAttach.getServerId() + "/action").type(MediaType.APPLICATION_JSON)
                    .post(json);
            System.out.println(resp.getStatus());
            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                System.out.println(BaseUtil.toJson(respMap));

                if (respMap.containsKey("volume_attachment")) {
                    block = BaseUtil.castObject(respMap.get("volume_attachment"), Block.class);
                }
                block.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when attaching disk");
            throw new AdapterUnavailableException();

        } catch (IOException e) {
            block.setSuccess(false);
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return block;

    }
}
