package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleAdd;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmUserRoleAdd extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmUserRoleAdd.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException, AdapterUnavailableException {

        UserRoleAdd userRoleAdd = (UserRoleAdd) base;

        Boolean flag = false;

        CommonAdapterResult result = new CommonAdapterResult();

        String json = helper.getParam(userRoleAdd.getProviderType(), userRoleAdd);

        log.info("user role add post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);

        try {
            Response resp = client.path("/keystone/roles/add_user_role").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                flag = true;
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when adding user role");
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
