package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerVmRemove extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        VmRemove vmRemove = (VmRemove) base;
        String json = helper.getParam(vmRemove.getProviderType(), vmRemove);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        client.accept(MediaType.APPLICATION_JSON);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(300 * 1000);
        log.info("powervm remove");

        Boolean flag = false;

        CommonAdapterResult result = new CommonAdapterResult();
        if ("lpar".equalsIgnoreCase(vmRemove.getVmType())) {
            client.path("/hmc/lpar/relese");
        } else if ("mpar".equalsIgnoreCase(vmRemove.getVmType())) {
            client.path("/hmc/mpar/relese");
        }
        try {
            Response resp = client.type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                flag = true;
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when removing vm");
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
