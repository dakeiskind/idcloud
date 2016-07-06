package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.DiskFormat;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Fdisks;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.disk.result.FormatDiskResult;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class DiskFormatAction extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        log.info("格式化磁盘...");
        DiskFormat dFormat = (DiskFormat) base;
        Fdisks result = new Fdisks();
        String json = helper.getParam(dFormat.getProviderType(), dFormat);

        log.info("格式化磁盘参数：" + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        try {
            Response resp = client.path("/hmc/disk/format").type(MediaType.APPLICATION_JSON)
                    .post(json);
            if (resp.getStatus() == 200) {
                String resultJson = resp.readEntity(String.class);
                result = BaseUtil.fromJson(resultJson, Fdisks.class);
                boolean flag = true;
                List<FormatDiskResult> disks = result.getDisks();
                for (FormatDiskResult formatDiskResult : disks) {
                    if (!formatDiskResult.isSuccess()) {
                        flag = false;
                        break;
                    }
                }
                result.setSuccess(flag);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("power adapter is not available when format disk！");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof PowerException) {
                throw (PowerException) e;
            }
            e.printStackTrace();
        }
        return result;
    }
}
