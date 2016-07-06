package com.h3c.idcloud.core.adapter.facade.provision.action.vmware;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.AlarmTriggerVosResult;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.alarm.AlarmTriggerGet;
import com.h3c.idcloud.core.adapter.pojo.common.Base;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class VmwareAlarmTriggerQuery extends ActionVmware {

    private static Logger log = LoggerFactory.getLogger(VmwareAlarmTriggerQuery.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws VmwareException,
            AdapterUnavailableException {

        AlarmTriggerGet alarmListGet = (AlarmTriggerGet) base;
        AlarmTriggerVosResult result = new AlarmTriggerVosResult();
        String json = helper.getParam(alarmListGet.getProviderType(),
                alarmListGet);

        log.info("alrm list query post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(),
                this.providers);

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        try {
            Response resp = client.path("/alarm/getAllAlarm")
                    .type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                String jsonResult = resp.readEntity(String.class);
                result = BaseUtil.fromJson(jsonResult, AlarmTriggerVosResult.class);
            }

        } catch (ClientException e) {
            log.info("vmware adapter is not available when querying alarm list");
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

        return result;
    }

}
