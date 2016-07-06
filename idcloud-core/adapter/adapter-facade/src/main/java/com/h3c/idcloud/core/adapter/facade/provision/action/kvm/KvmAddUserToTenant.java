package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;


import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.tenant.AddUserToTenant;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmAddUserToTenant extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        AddUserToTenant addUserToTenant = (AddUserToTenant) base;

        String json = helper.getParam(addUserToTenant.getProviderType(),
                addUserToTenant);

        log.info("add user to tenant post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        CommonAdapterResult result = new CommonAdapterResult();
        Response resp = null;
        try {
            resp = client
                    .path("/tenants/" + addUserToTenant.getTenantId()
                            + "/action").type(MediaType.APPLICATION_JSON)
                    .post(json);

        } catch (ClientException e) {
            log.info("kvm adapter is not available when add user to tenant");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (resp != null) {
            if (resp.getStatus() == 200) {
                result.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        }
        return result;
    }
}
