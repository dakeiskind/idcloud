package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SysDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.ViosVo;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.scan.DiskScan;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.SysDiskVo;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerDiskScan extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        DiskScan diskScan = (DiskScan) base;
        SysDiskResult result = new SysDiskResult();
        log.info("sysDisk scanning...");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        String json = helper.getParam(diskScan.getProviderType(), diskScan);

        log.info("扫描系统盘参数：" + json);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);
        client.accept(MediaType.APPLICATION_JSON);
        try {
            Response resp = client.path("/hmc/scan/disks").type(MediaType.APPLICATION_JSON).post(json);
            if (resp.getStatus() == 200) {
                List<ViosVo> listVos = resp.readEntity(new GenericType<List<ViosVo>>() {
                });
                for (ViosVo viosVo : listVos) {
                    List<SysDiskVo> disks = viosVo.getDisks();
                    for (SysDiskVo sysDiskVo : disks) {
                        Float size = Float.parseFloat(sysDiskVo.getSize());
                        size = size / 1024F;
                        sysDiskVo.setSize(size.toString());
                    }
                }
                result.setViosList(listVos);
                result.setSuccess(true);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("powervm adapter is not available when scanning sysDisk");
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
