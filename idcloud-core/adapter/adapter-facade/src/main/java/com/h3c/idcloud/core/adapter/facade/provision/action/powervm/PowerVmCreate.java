package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.Disk;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerVmCreate extends ActionPowervm {
    @SuppressWarnings("unchecked")
    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {
        log.info("调用 powervm adapter 创建虚拟机");
        VmCreate vmCreate = (VmCreate) base;
        Server server = new Server();

        String json = helper.getParam(vmCreate.getProviderType(), vmCreate);

        log.info("创建虚拟机参数：" + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        Map<String, Object> respMap = null;
        try {

            if ("lpar".equalsIgnoreCase(vmCreate.getVmType())) {
                client.path("/hmc/lpar");
                Response resp = client.type(MediaType.APPLICATION_JSON)
                        .post(json);

                if (resp.getStatus() == 200) {
                    respMap = resp.readEntity(Map.class);
                    server.setHostName(vmCreate.getHostName());
                    server.setName(vmCreate.getName());
                    if (respMap.containsKey("id")) {
                        server.setUuid((String) respMap.get("id"));
                    }
                    List diskList = BaseUtil.castObject(respMap.get("disks"), List.class);
                    List<Disk> disks = new ArrayList<Disk>();
                    if (!CollectionUtils.isEmpty(diskList)) {
                        for (Object object : diskList) {
                            Disk disk = new Disk();
                            disk = BaseUtil.castObject(object, Disk.class);
                            disks.add(disk);
                        }
                    }
                    server.setDisks(disks);
                    server.setSuccess(true);
                } else {
                    String errMsg = resp.readEntity(String.class);
                    PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                    log.error(errMsg);
                    throw exception;
                }
            } else if ("mpar".equalsIgnoreCase(vmCreate.getVmType())) {
                client.path("/hmc/mpar");
                Response resp = client.type(MediaType.APPLICATION_JSON)
                        .post(json);

                if (resp.getStatus() == 200) {
                    respMap = resp.readEntity(Map.class);
                    server.setHostName(vmCreate.getHostName());
                    server.setName(vmCreate.getName());
                    if (respMap.containsKey("id")) {
                        server.setUuid((String) respMap.get("id"));
                    }
                    List diskList = BaseUtil.castObject(respMap.get("disks"), List.class);
                    List<Disk> disks = new ArrayList<Disk>();
                    for (Object object : diskList) {
                        Disk disk = new Disk();
                        disk = BaseUtil.castObject(object, Disk.class);
                        disks.add(disk);
                    }
                    server.setDisks(disks);
                    server.setSuccess(true);
                } else {
                    String errMsg = resp.readEntity(String.class);
                    PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                    log.error(errMsg);
                    throw exception;
                }
            }

        } catch (ClientException e) {
            log.info("kvm adapter is not available when creating vm");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof PowerException) {
                throw (PowerException) e;
            } else {
                log.error(e.getMessage());
                throw new PowerException("500", e.getMessage());
            }
        }
        return server;
    }

}
