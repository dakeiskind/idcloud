package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmVmOperate extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmVmOperate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException, AdapterUnavailableException {

        VmOperate vmOperate = (VmOperate) base;

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);
        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        String json = helper.getParam(vmOperate.getProviderType(), vmOperate);
        log.info("vm operate post data : " + json);

        Boolean flag = false;

        CommonAdapterResult result = new CommonAdapterResult();
        try {
            Response resp = client.path("/" + vmOperate.getTenantId() + "/servers/" + vmOperate.getUuid() + "/action")
                    .type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                flag = true;
                log.info("vm id:" + vmOperate.getUuid() + " " + vmOperate.getAction() + " successed!");
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.error("kvm adapter is not available when " + vmOperate.getAction() + " vm");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        result.setSuccess(flag);
        return result;
    }
}
