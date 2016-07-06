package com.hptsic.cloud.monitor.provision.action.alarm;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.AlarmListGet;
import com.hptsic.cloud.monitor.pojo.alarm.AlarmInfo;
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
public class QueryAlarmsInfo extends ActionAlarm {

	@SuppressWarnings("unchecked")
	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("get nodes alarm info");
		AlarmListGet alarmsInfoGet = (AlarmListGet) base;
		NodeAlarmResult result = new NodeAlarmResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);

		Map<?, ?> respMap = new HashMap<Object, Object>();
		try {
			if(base.isRetrieveAll()){
				client.query("retrieveAll", base.isRetrieveAll());
			}
			Response resp = client.path("/alarms").query("node_ids", alarmsInfoGet.getIds())
					.type(MediaType.APPLICATION_JSON).get();

			if (resp.getStatus() == 200) {
				respMap = resp.readEntity(Map.class);
				List<AlarmInfo> alarmInfos = BaseUtil.castObject(respMap, List.class);
				result.setAlarmInfos(alarmInfos);
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
			log.info("adapter is not available when quering alarmsinfo");
			throw new AdapterUnavailableException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
