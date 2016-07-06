package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;


import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Servers;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuery;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmVmQuery extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmVmQuery.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException {

        VmQuery vmQuery = (VmQuery) base;
        log.info("vm query...");
        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);
        client.accept(MediaType.APPLICATION_JSON);
        Servers servers = new Servers();


        try {
            Response response = null;
            if (vmQuery.getTenantId() != null && !"".equals(vmQuery.getTenantId())) {
                client.header("X_TENANT_NAME", vmQuery.getTenantName());
                response = client.path("/" + vmQuery.getTenantId() + "/servers").type(MediaType.APPLICATION_JSON).get();
            } else {
                response = client.path("resources").query("params", "{\"servers\":[]}").type(MediaType.APPLICATION_JSON).get();
            }

            if (response.getStatus() == 200) {
                servers = response.readEntity(Servers.class);
                servers.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(response);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }

        return servers;
    }
}
