package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant.Tenants;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantCreate;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantCreateResult;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmTenantCreate extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmTenantCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException, AdapterUnavailableException {

        TenantCreate tenantCreate = (TenantCreate) base;

        Tenants tenants = new Tenants();

        String json = helper.getParam(tenantCreate.getProviderType(), tenantCreate);

        log.info("tenant create post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        //client.header("X_TENANT_NAME",tenantCreate.getTenantName());
        try {
            Response resp = client.path("/tenants").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("tenant")) {
                    TenantCreateResult tenantCreateResult = BaseUtil.castObject(respMap.get("tenant"), TenantCreateResult.class);
                    tenants.setTenantCreateResult(tenantCreateResult);
                }
                tenants.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }

        } catch (ClientException e) {
            log.info("kvm adapter is not available when creating tenant");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }

        return tenants;

    }
}
