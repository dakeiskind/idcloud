package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerVmOpereate extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        VmOperate vmOperate = (VmOperate) base;

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        client.accept(MediaType.APPLICATION_JSON);
        String json = helper.getParam(vmOperate.getProviderType(), vmOperate);
        log.info("vm operate post data : " + json);

        Boolean flag = false;

        CommonAdapterResult result = new CommonAdapterResult();
        try {
            Response resp = client.path("/hmc/par/operation")
                    .type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                flag = true;
                log.info("vm id:" + vmOperate.getUuid() + " " + vmOperate.getAction() + " successed!");
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.error("power adapter is not available when " + vmOperate.getAction() + " vm");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof PowerException) {
                throw (PowerException) e;
            } else {
                log.error(e.getMessage());
                throw new PowerException("500", e.getMessage());
            }
        }
        result.setSuccess(flag);
        return result;
    }
}
