package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Vswitchs;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.VswitchScan;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerVswitchScan extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        log.info("调用 powervm adapter 扫描虚拟交换机！");
        VswitchScan vswitchScan = (VswitchScan) base;
        Vswitchs vswitchs = new Vswitchs();
        String json = helper.getParam(vswitchScan.getProviderType(), vswitchScan);

        log.info("扫描虚拟交换机参数：" + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        Map<?, ?> respMap = null;
        try {
            Response resp = client.path("/hmc/scan/vswitchs").type(MediaType.APPLICATION_JSON)
                    .post(json);
            if (resp.getStatus() == 200) {
                respMap = resp.readEntity(Map.class);
                vswitchs = BaseUtil.castObject(respMap, Vswitchs.class);
                vswitchs.setSuccess(true);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("power adapter is not available when scanning network");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof PowerException) {
                throw (PowerException) e;
            } else {
                log.error(e.getMessage());
                throw new PowerException("500", e.getMessage());
            }
        }
        return vswitchs;
    }

}
