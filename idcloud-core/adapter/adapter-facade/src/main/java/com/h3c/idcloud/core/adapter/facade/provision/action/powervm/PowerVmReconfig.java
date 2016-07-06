package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.DiskFormat;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmDisk;
import com.h3c.idcloud.core.adapter.pojo.vm.VmReconfig;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerVmReconfig extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        VmReconfig reconfig = (VmReconfig) base;
        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);
        client.accept(MediaType.APPLICATION_JSON);
        String json = helper.getParam(reconfig.getProviderType(), reconfig);
        log.info("vm reconfig post data : " + json);
        Boolean flag = false;
        CommonAdapterResult result = new CommonAdapterResult();
        try {
            Response resp = client.path("/hmc/mpar/reconfig")
                    .type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                String resultMap = resp.readEntity(String.class);
                DiskFormat disk = BaseUtil.castObject(resultMap, DiskFormat.class);
                List<VmDisk> disks = disk.getDisks();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("disks", disks);
                result.setMap(map);
                flag = true;
                log.info("reconfig vm " + reconfig.getVmName() + " successed!");
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.error("power adapter is not available when reconfig vm");
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
