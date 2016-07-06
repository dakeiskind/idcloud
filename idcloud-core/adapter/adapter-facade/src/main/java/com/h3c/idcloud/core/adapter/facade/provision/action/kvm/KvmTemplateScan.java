package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Templates;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.TemplateScan;

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
public class KvmTemplateScan extends ActionKvm {
    private static Logger log = LoggerFactory.getLogger(KvmTemplateScan.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        TemplateScan templateScan = (TemplateScan) base;

        Templates template = new Templates();

        log.info("template get ");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        try {
            Response resp = client.path("/" + templateScan.getTenantId() + "/images").type(MediaType.APPLICATION_JSON)
                    .get();
            if (resp.getStatus() == 200) {
                List<Template> templates = new ArrayList<Template>();
                Map respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("images")) {
                    List<Map> tempalteMaps = (List<Map>) respMap.get("images");

                    for (Map map : tempalteMaps) {
                        Template template2 = new Template();
                        template2 = BaseUtil.castObject(map, Template.class);
                        templates.add(template2);
                    }
                    template.setImages(templates);
                    template.setSuccess(true);
                }
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
        return template;
    }

}
