package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.user.Users;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.user.UserCreate;
import com.h3c.idcloud.core.adapter.pojo.user.result.UserCreateResult;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmUserCreate extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmUserCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException, AdapterUnavailableException {

        UserCreate userCreate = (UserCreate) base;

        Users users = new Users();

        String json = helper.getParam(userCreate.getProviderType(), userCreate);

        log.info("user create post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        try {
            Response resp = client.path("/users").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("user")) {

                    UserCreateResult userCreateResult = BaseUtil.castObject(respMap.get("user"), UserCreateResult.class);
                    users.setUserCreateResult(userCreateResult);
                }
                users.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when creating user");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return users;
    }
}
