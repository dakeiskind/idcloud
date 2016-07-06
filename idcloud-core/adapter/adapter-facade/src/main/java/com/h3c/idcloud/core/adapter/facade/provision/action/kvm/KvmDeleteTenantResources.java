package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantResourcesDelete;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmDeleteTenantResources extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        TenantResourcesDelete tenantDelete = (TenantResourcesDelete) base;

        Boolean flag = false;

        CommonAdapterResult result = new CommonAdapterResult();

        String json = helper.getParam(tenantDelete.getProviderType(), tenantDelete);

        log.info("tenant resources delete");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        try {
            Response resp = client.path("/tenants/" + tenantDelete.getTenantId() + "/action").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                flag = true;
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }

        } catch (ClientException e) {
            log.info("kvm adapter is not available when deleting tenant resources");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        result.setSuccess(flag);
        return result;

    }

}
