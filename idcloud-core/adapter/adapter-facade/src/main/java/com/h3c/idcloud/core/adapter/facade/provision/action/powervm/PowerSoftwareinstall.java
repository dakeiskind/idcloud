package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Softwares;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.software.Software;
import com.h3c.idcloud.core.adapter.pojo.software.SoftwareDeploy;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerSoftwareinstall extends ActionPowervm {

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        log.info("调用 powervm adapter 安装软件");
        SoftwareDeploy softwareDeploy = (SoftwareDeploy) base;
        List<Software> softwares = softwareDeploy.getSoftwares();
        for (Software software : softwares) {

            if (!StringUtils.isEmpty(software.getMediaPath())) {
                String mediaPath = software.getMediaLib() + "/" + software.getMediaPath();
                software.setMediaPath(mediaPath);
            } else {
                software.setMediaPath(software.getMediaLib());
            }
            if (!StringUtils.isEmpty(software.getScriptPath())) {
                String scriptPath = software.getScriptLib() + "/" + software.getScriptPath();
                software.setScriptPath(scriptPath);
            } else {
                software.setScriptPath(software.getScriptLib());
            }
            String softVersion = software.getVersion().split("_")[1];
            software.setVersion(softVersion);
            String osVersion = software.getOsVersion().split("_")[1];
            String osType = software.getOsVersion().split("_")[0];
            softwareDeploy.setOsType(osType);
            softwareDeploy.setOsVersion(osVersion);
        }
        Softwares result = new Softwares();
        String json = helper.getParam(softwareDeploy.getProviderType(), softwareDeploy);

        log.info("安装软件参数：" + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        try {

            Response resp = client.type(MediaType.APPLICATION_JSON).path("hmc/software/install")
                    .post(json);

            if (resp.getStatus() == 200) {
                String jsonResult = resp.readEntity(String.class);
                result = BaseUtil.fromJson(jsonResult, Softwares.class);
                List<Software> softwares2 = result.getSoftwares();
                boolean flag = true;
                for (Software software : softwares2) {
                    if (!software.isSuccess()) {
                        flag = false;
                        break;
                    }
                }
                result.setSuccess(flag);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                log.error(errMsg);
                throw exception;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when install software");
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
