package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmTemplateCreate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;

@Service
public class VmwareVmTemplateCreate extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareVmTemplateCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException,
            AdapterUnavailableException {

        VmTemplateCreate vmTemplateCreate = (VmTemplateCreate) base;

        // VmwareTemplateCreateVO template = new VmwareTemplateCreateVO();
        // template.setCloneImangeId(vmTemplateCreate.getTemplateName());
        // template.setHostName(vmTemplateCreate.getHostName());
        // template.setVolumeUnitNo(vmTemplateCreate.getStore());

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        try {

            // Response response = client
            // .back(true)
            // .path("/" + vmTemplateCreate.getVmName() + "/cloneTemplate")
            // .header("Content-Type", "application/json")
            // .accept(MediaType.APPLICATION_JSON).post(template);

        } catch (Exception e) {
            log.info("vmware adapter is not available when templating vm");
            throw new AdapterUnavailableException();

        }
        return null;
    }

}
