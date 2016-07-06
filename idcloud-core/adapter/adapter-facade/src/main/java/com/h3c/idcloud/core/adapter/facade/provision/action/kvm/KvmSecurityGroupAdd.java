package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupAdd;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmSecurityGroupAdd extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmSecurityGroupAdd.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        ServerSecurityGroupAdd securityGroupAdd = (ServerSecurityGroupAdd) base;

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);

        String json = helper.getParam(securityGroupAdd.getProviderType(),
                securityGroupAdd);

        log.info("vm add securityGroup post data : " + json);

        CommonAdapterResult result = new CommonAdapterResult();

        try {
            Response resp = client
                    .path("/" + securityGroupAdd.getTenantId() + "/servers/"
                            + securityGroupAdd.getServerId() + "/action")
                    .type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                result.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when adding securitygroup");
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
