package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.CpuPools;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.CpuPoolScan;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerCpuPoolScan extends ActionPowervm {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        log.info("调用 powervm adapter 扫描cpu池！");
        CpuPoolScan cpuPoolScan = (CpuPoolScan) base;
        CpuPools cpuPools = new CpuPools();
        String json = helper.getParam(cpuPoolScan.getProviderType(), cpuPoolScan);

        log.info("扫描cpu池参数：" + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        Map<?, ?> respMap = null;
        try {
            Response resp = client.path("/hmc/scan/cpupool").type(MediaType.APPLICATION_JSON)
                    .post(json);
            if (resp.getStatus() == 200) {
                respMap = resp.readEntity(Map.class);
                cpuPools = BaseUtil.castObject(respMap, CpuPools.class);
                cpuPools.setSuccess(true);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("power adapter is not available when creating vm");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof PowerException) {
                throw (PowerException) e;
            }
            e.printStackTrace();
        }
        return cpuPools;
    }

}
