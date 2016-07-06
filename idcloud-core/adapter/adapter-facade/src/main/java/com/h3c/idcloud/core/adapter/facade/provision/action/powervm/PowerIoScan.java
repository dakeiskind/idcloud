package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Ios;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.IoScan;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerIoScan extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        IoScan ioScan = (IoScan) base;
        Ios ios = new Ios();
        log.info("io scanning by envPowervm...");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        String json = helper.getParam(ioScan.getProviderType(), ioScan);

        log.info("扫描io参数：" + json);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);
        client.accept(MediaType.APPLICATION_JSON);
        try {
            Response resp = client.path("/hmc/scan/ios").type(MediaType.APPLICATION_JSON).post(json);
            if (resp.getStatus() == 200) {
                Map<?, ?> respMap = resp.readEntity(Map.class);
                ios = BaseUtil.castObject(respMap, Ios.class);
                ios.setSuccess(true);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when scanning io");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof PowerException) {
                throw (PowerException) e;
            } else {
                log.error(e.getMessage());
                throw new PowerException("500", e.getMessage());
            }
        }
        return ios;
    }

}
