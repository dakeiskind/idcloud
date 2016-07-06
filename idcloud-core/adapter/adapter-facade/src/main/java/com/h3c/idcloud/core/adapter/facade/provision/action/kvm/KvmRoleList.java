package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.Roles;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.admin.RoleList;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmRoleList extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmRoleList.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException, AdapterUnavailableException {

        RoleList roleList = (RoleList) base;

        String json = helper.getParam(roleList.getProviderType(), roleList);

        log.info("role list post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        Roles roles = new Roles();

        try {
            Response resp = client.path("/keystone/roles/list").type(MediaType.APPLICATION_JSON).post(json.toString());

            if (resp.getStatus() == 200) {
                roles = resp.readEntity(Roles.class);
                roles.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }

        } catch (ClientException e) {
            log.info("kvm adapter is not available when listing role");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return roles;
    }
}
