package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmUserAdd;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class UserAddAction extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        // TODO Auto-generated method stub
        log.info("开始添加用户......");
        VmUserAdd userAdd = (VmUserAdd) base;
        CommonAdapterResult result = new CommonAdapterResult();
        String json = helper.getParam(userAdd.getProviderType(), userAdd);

        log.info("添加用户参数：" + json);
        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(200 * 1000);

        client.accept(MediaType.APPLICATION_JSON);
        try {
            Response resp = client.path("/hmc/user/add").type(MediaType.APPLICATION_JSON)
                    .post(json);
            if (resp.getStatus() == 200) {
                result.setSuccess(true);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("power adapter is not available when add user！");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof PowerException) {
                throw (PowerException) e;
            } else {
                log.error(e.getMessage());
                throw new PowerException("500", e.getMessage());
            }
        }
        return result;
    }

}
