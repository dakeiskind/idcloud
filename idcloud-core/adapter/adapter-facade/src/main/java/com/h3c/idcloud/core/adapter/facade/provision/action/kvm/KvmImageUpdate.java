package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.image.ImageUpdate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmImageUpdate extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        ImageUpdate imageUpdate = (ImageUpdate) base;

        Template result = new Template();
        String json = helper.getParam(imageUpdate.getProviderType(), imageUpdate);

        log.info("image delete: " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);

        try {
            Response resp = client.path("/" + imageUpdate.getTenantId() + "/images/" + imageUpdate.getImageId()).type(MediaType.APPLICATION_JSON).put(json);

            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("image")) {
                    result = BaseUtil.castObject(respMap.get("image"), Template.class);
                    result.setSuccess(true);
                }
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when image updating");
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
        return result;
    }

}
