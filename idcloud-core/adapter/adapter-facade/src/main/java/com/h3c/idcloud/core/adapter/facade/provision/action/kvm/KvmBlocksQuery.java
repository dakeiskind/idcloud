package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Block;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Blocks;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmBlockQuery;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmBlocksQuery extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmBlocksQuery.class);

    @Override
    @SuppressWarnings("unchecked")
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        VmBlockQuery vmBlockQuery = (VmBlockQuery) base;

        Blocks blocks = new Blocks();

        String json = helper.getParam(vmBlockQuery.getVirtEnvType(), vmBlockQuery);

        log.info("vm vmBlockQuery post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);
        Map respMap = null;

        try {
            Response resp = client
                    .path("/" + vmBlockQuery.getTenantId() + "/servers/"
                            + vmBlockQuery.getId() + "/action")
                    .type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("server")) {

                    List<Block> blocklist = BaseUtil.castObject(
                            respMap.get("server"), List.class);
                    blocks.setBlocks(blocklist);
                }
                blocks.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when querying VmBlocks");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return blocks;
    }

}
