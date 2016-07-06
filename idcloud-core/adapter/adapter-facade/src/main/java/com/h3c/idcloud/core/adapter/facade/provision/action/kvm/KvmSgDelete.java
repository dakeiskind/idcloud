package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.admin.SgDelete;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmSgDelete extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        SgDelete sgDelete = (SgDelete) base;

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);

        log.info("securityGroup delete....");

        CommonAdapterResult result = new CommonAdapterResult();
        try {
            Response resp = client
                    .path("/" + sgDelete.getTenantId() + "/security_groups/" + sgDelete.getSecurityGroupId())
                    .type(MediaType.APPLICATION_JSON).delete();
            if (resp.getStatus() == 200) {
                result.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when deleting securitygroup");
            throw new AdapterUnavailableException();
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
