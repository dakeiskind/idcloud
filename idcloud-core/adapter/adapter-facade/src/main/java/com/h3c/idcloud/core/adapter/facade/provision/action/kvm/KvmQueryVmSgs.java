package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.VmSecurityGroups;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuerySgs;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmQueryVmSgs extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        VmQuerySgs vmQuerySgs = (VmQuerySgs) base;
        String json = helper.getParam(vmQuerySgs.getProviderType(), vmQuerySgs);

        VmSecurityGroups vmSecurityGroups = new VmSecurityGroups();
        log.info("querying vmSecurityGroup...");

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);

        try {
            Response resp = client.path("/" + vmQuerySgs.getTenantId() + "/servers/" + vmQuerySgs.getServerId() + "/action").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                vmSecurityGroups = resp.readEntity(VmSecurityGroups.class);
                vmSecurityGroups.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when creating network");
            vmSecurityGroups.setSuccess(false);
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return vmSecurityGroups;
    }
}
