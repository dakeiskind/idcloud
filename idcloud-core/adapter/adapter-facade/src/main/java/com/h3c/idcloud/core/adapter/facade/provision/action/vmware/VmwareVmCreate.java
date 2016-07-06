package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class VmwareVmCreate extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareVmCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException,
            AdapterUnavailableException {

        VmCreate vmCreate = (VmCreate) base;

        Server server = null;

        String json = helper.getParam(vmCreate.getProviderType(), vmCreate);

        log.info("vm create post data : " + json);

        String j = json.toString();
        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);
        conduit.getClient().setConnectionTimeout(10 * 1000);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            Response resp = client.path("/vm/create/")
                    .type(MediaType.APPLICATION_JSON).post(j);

            this.checkResponseStatus(resp);
            if (resp.getStatus() == 200) {
                String jsonResult = resp.readEntity(String.class);
                try {
                    server = BaseUtil.fromJson(jsonResult, Server.class);
                    server.setSuccess(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                VmwareException vmwareException = resp
                        .readEntity(VmwareException.class);
                if (resp.getStatus() != 404
                        && !"10003".equals(vmwareException.getErrCode())) {

                    VmRemove vmRemove = new VmRemove();
                    client = WebClient.create(this.getAdapterUrl(),
                            this.providers);
                    BeanUtils.copyProperties(vmRemove, vmCreate);
                    vmRemove.setVmName(vmCreate.getName());
                    String removeJson = helper.getParam(vmRemove.getProviderType(), vmRemove);
                    log.info("vm remove post data : " + json);
                    client.accept(MediaType.APPLICATION_JSON);
                    Response response = client
                            .path("/vm/operate/destroy").type(MediaType.APPLICATION_JSON)
                            .query("name", vmRemove.getVmName())
                            .post(removeJson);
                    if (response.getStatus() == 200) {
                        log.info("删除虚拟机" + vmCreate.getName() + "成功！");
                    }
                }
                throw vmwareException;
            }

        } catch (ClientException e) {
            log.info("vmware adapter is not available when creating vm");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (e instanceof VmwareException) {
                throw (VmwareException) e;
            } else {
                VmwareException vmwareException = new VmwareException("500", e.getMessage());
                throw vmwareException;
            }
        }

        return server;

    }
}
