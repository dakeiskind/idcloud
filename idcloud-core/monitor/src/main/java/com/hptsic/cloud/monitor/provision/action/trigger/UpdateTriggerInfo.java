package com.hptsic.cloud.monitor.provision.action.trigger;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.AlarmRoleUpate;
import com.hptsic.cloud.monitor.provision.action.monitor.ActionNode;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import net.sf.json.JSONObject;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class UpdateTriggerInfo extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("update alarm role");
		AlarmRoleUpate triggerInfoUpdate = (AlarmRoleUpate) base;
		if ("info".equalsIgnoreCase(triggerInfoUpdate.getPriority())) {
			triggerInfoUpdate.setPriority("1");
		} else if ("warning".equalsIgnoreCase(triggerInfoUpdate.getPriority())) {
			triggerInfoUpdate.setPriority("2");
		} else if ("general".equalsIgnoreCase(triggerInfoUpdate.getPriority())) {
			triggerInfoUpdate.setPriority("3");
		} else if ("important".equalsIgnoreCase(triggerInfoUpdate.getPriority())) {
			triggerInfoUpdate.setPriority("4");
		} else if ("serious".equalsIgnoreCase(triggerInfoUpdate.getPriority())) {
			triggerInfoUpdate.setPriority("5");
		}
		CommonAdapterResult result = new CommonAdapterResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);
		client.accept(MediaType.APPLICATION_JSON);
		JSONObject json = helper.convert(triggerInfoUpdate.getProviderType(), triggerInfoUpdate);
		try {
			Response resp = client.path("/triggers/"+triggerInfoUpdate.getId())
					.type(MediaType.APPLICATION_JSON).put(json);

			if (resp.getStatus() == 200) {
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
//			log.info("adapter is not available when update alarm role");
			throw new AdapterUnavailableException();
		}
		return result;
	}

}
