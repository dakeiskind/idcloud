package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmStatus;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;

@Service
public class VmwareVmStatus extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareVmStatus.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException,
            AdapterUnavailableException {

        VmStatus vmStatus = (VmStatus) base;

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            // VmwareStatusResource status = client.back(true)
            // .path("/" + vmStatus.getVmName())
            // .header("Content-Type", "application/json")
            // .get(VmwareStatusResource.class);
            // br.setStatus(status.getResourceInstance().getPropertys()
            // .getStatus());
        } catch (Exception e) {
            log.info("vmware adapter is not available when querying vm");
            throw new AdapterUnavailableException();
        }
        return null;

    }
}
