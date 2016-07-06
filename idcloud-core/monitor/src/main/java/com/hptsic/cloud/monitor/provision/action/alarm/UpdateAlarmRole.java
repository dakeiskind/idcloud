package com.hptsic.cloud.monitor.provision.action.alarm;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.alarm.ServiceContext;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import com.hptsic.cloud.monitor.provision.model.NodeAlarmResult;
import com.hptsic.cloud.monitor.util.BaseUtil;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateAlarmRole extends ActionAlarm {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("update alarm role");
		NodeAlarmResult result = new NodeAlarmResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);

		Map<?, ?> respMap = new HashMap<Object, Object>();
		try {
			Response resp = client.path("/service/serviceTree")
					.type(MediaType.APPLICATION_JSON).get();

			if (resp.getStatus() == 200) {
				respMap = resp.readEntity(Map.class);
				@SuppressWarnings("unchecked")
				List<ServiceContext> serviceContexts = BaseUtil.castObject(respMap, List.class);
				result.setServiceContexts(serviceContexts);
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
			log.info("adapter is not available when update alarm role");
			throw new AdapterUnavailableException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
