package com.hptsic.cloud.monitor.provision.action.event;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.AlarmLevel;
import com.hptsic.cloud.monitor.pojo.EventListGet;
import com.hptsic.cloud.monitor.pojo.event.Alarm;
import com.hptsic.cloud.monitor.provision.action.monitor.ActionNode;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import com.hptsic.cloud.monitor.provision.model.EventResult;
import com.hptsic.cloud.monitor.util.BaseUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryEventList extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("get alarm list info");
		EventListGet eventListGet = (EventListGet) base;
		EventResult result = new EventResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);
		HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
		conduit.getClient().setReceiveTimeout(0);
		client.accept(MediaType.APPLICATION_JSON);
		
		Map<?, ?> respMap = new HashMap<Object, Object>();
		try {
			if(!StringUtils.isEmpty(eventListGet.getEventidFrom())){
				client.query("eventid_from", eventListGet.getEventidFrom());
			}
			if(!StringUtils.isEmpty(eventListGet.getEventidTill())){
				client.query("eventid_till", eventListGet.getEventidTill());
			}
			if(!StringUtils.isEmpty(eventListGet.getTimeFrom())){
				client.query("time_from", eventListGet.getTimeFrom());
			}
			if(!StringUtils.isEmpty(eventListGet.getTimeTill())){
				client.query("time_till", eventListGet.getTimeTill());
			}
			if(eventListGet.isAcknowledged()){
				client.query("acknowledged", eventListGet.isAcknowledged());
			}
			if(eventListGet.isRetrieveAll()){
				client.query("retrieveAll", eventListGet.isRetrieveAll());
			}
			Response resp = client.path("/events")
					.type(MediaType.APPLICATION_JSON).get();

			if (resp.getStatus() == 200) {
				String string = resp.readEntity(String.class);
				if(!StringUtils.isEmpty(string)) {
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> infos = BaseUtil.castObject(string, List.class);
					List<Alarm>  eventInfos = new ArrayList<Alarm>();
					for (Map<String, Object> map : infos) {
						Alarm eventInfo = BaseUtil.castObject(map, Alarm.class);
						if ("1".equals(eventInfo.getPriority())) {
							eventInfo.setPriority(AlarmLevel.INFO);
						} else if ("2".equals(eventInfo.getPriority())) {
							eventInfo.setPriority(AlarmLevel.WARN);
						} else if ("3".equals(eventInfo.getPriority())) {
							eventInfo.setPriority(AlarmLevel.GENERAL);
						} else if ("4".equals(eventInfo.getPriority())) {
							eventInfo.setPriority(AlarmLevel.IMPORTANT);
						} else if ("5".equals(eventInfo.getPriority())) {
							eventInfo.setPriority(AlarmLevel.SERIOUS);
						}
						eventInfos.add(eventInfo);
					}
					result.setEvents(eventInfos);
				}
				result.setSuccess(true);
			}else {
				String resultStr = resp.readEntity(String.class);
				if(org.apache.commons.lang.StringUtils.isEmpty(resultStr) && !"null".equalsIgnoreCase(resultStr)){
					respMap = BaseUtil.fromJson(resultStr, Map.class);
				}
				MonitorException mException = new MonitorException(respMap);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
			log.info("adapter is not available when query node list");
			throw new AdapterUnavailableException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
