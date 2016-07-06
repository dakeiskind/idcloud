package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataDiskResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.NpivScan;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerNpivScan extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        NpivScan diskScan = (NpivScan) base;
        DataDiskResult result = new DataDiskResult();
        log.info("DataDisk scanning...");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        String json = helper.getParam(diskScan.getProviderType(), diskScan);

        log.info("扫描数据盘参数：" + json);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);
        client.accept(MediaType.APPLICATION_JSON);
        try {
            Response resp = client.path("/hmc/scan/npivs").type(MediaType.APPLICATION_JSON).post(json);
            if (resp.getStatus() == 200) {
                String jsonResult = resp.readEntity(String.class);
                result = BaseUtil.castObject(jsonResult, DataDiskResult.class);
                //List<DataDiskVo> listVos = BaseUtil.fromJson(json, clazz)
                //result.setDisks(listVos);
                result.setSuccess(true);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("powervm adapter is not available when scanning datadisk");
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
