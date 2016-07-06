package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.Block;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.block.BlockBackupCreate;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupCreateResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmBlockCreateBackup extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        Block block = new Block();

        BlockBackupCreate blockBackupCreate = (BlockBackupCreate) base;

        String json = helper.getParam(blockBackupCreate.getProviderType(), blockBackupCreate);

        log.info("block backUp create post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);
        Map respMap = null;

        try {
            Response resp = client.path("/" + blockBackupCreate.getTenantId() + "/volumes/" + blockBackupCreate.getVolumeId() + "/volume_backups").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("volume_backup")) {
                    block.setBackupCreateResult(BaseUtil.castObject(respMap.get("volume_backup"), BlockBackupCreateResult.class));
                    block.getBackupCreateResult().setSuccess(true);
                }
                block.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when creating BlockBackup");
            throw new AdapterUnavailableException();
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
