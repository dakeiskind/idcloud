package com.hptsic.cloud.monitor.provision.action.event;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.AlarmAction;
import com.hptsic.cloud.monitor.provision.action.monitor.ActionNode;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Service
public class EventAction extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		// TODO Auto-generated method stub
		AlarmAction alarmAction = (AlarmAction) base;
		CommonAdapterResult result = new CommonAdapterResult();
		Map<String, String> msgMap = new HashMap<String, String>();
		Map<String, Map<String, String>> ackMap = new HashMap<String, Map<String,String>>();
		msgMap.put("msg", "problem resolved");
		ackMap.put("ack", msgMap);
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);
		client.accept(MediaType.APPLICATION_JSON);
		try {
			Response resp = client.path("/events/"+alarmAction.getId()+"/action")
					.type(MediaType.APPLICATION_JSON).post(ackMap);
			if(resp.getStatus() == 200) {
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
//			log.info("adapter is not available when query ack event");
			throw new AdapterUnavailableException();
		}catch (Exception e) {
			if(e instanceof MonitorException) {
				throw (MonitorException) e;
			}else {
				log.error(e.getMessage());
				result.setSuccess(false);
			}
		}
		return result;
	}
}
