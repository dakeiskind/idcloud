package com.h3c.idcloud.core.adapter.facade.provision.action.powervm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.PowerException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.DataStoreVo;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreExtend;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PowerDataStoreExtend extends ActionPowervm {

    private static Logger log = LoggerFactory.getLogger(PowerDataStoreExtend.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws AdapterUnavailableException, PowerException {

        DataStoreExtend extend = (DataStoreExtend) base;

        String json = helper.getParam(extend.getProviderType(), extend);

        log.info("datastore extend post data : " + json);

        String j = json.toString();
        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);
        DataStoreVo vo = new DataStoreVo();
        Response resp = null;
        try {
            resp = client.path("/datastore/extend")
                    .type(MediaType.APPLICATION_JSON).post(j);

            if (resp.getStatus() == 200) {
                String jsonResult = resp.readEntity(String.class);
                vo = BaseUtil.castObject(jsonResult, DataStoreVo.class);
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                throw exception;
            }

        } catch (ClientException e) {
            log.info("vmware adapter is not available when extending datastore");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (e instanceof PowerException) {
                throw (PowerException) e;
            } else {
                String errMsg = resp.readEntity(String.class);
                PowerException exception = new PowerException(resp.getStatus() + "", errMsg);
                throw exception;
            }
        }
        return vo;
    }
}
