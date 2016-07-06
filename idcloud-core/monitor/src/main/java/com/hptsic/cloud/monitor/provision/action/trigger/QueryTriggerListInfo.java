package com.hptsic.cloud.monitor.provision.action.trigger;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.AlarmLevel;
import com.hptsic.cloud.monitor.pojo.trigger.TriggerVo;
import com.hptsic.cloud.monitor.provision.action.monitor.ActionNode;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import com.hptsic.cloud.monitor.provision.model.TriggerResult;
import com.hptsic.cloud.monitor.util.BaseUtil;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QueryTriggerListInfo extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("get trigger list info");
		TriggerResult result = new TriggerResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);

		try {
			if(base.isRetrieveAll()){
				client.query("retrieveAll", base.isRetrieveAll());
			}
			Response resp = client.path("/triggers")
					.type(MediaType.APPLICATION_JSON).get();

			if (resp.getStatus() == 200) {
				String string = resp.readEntity(String.class);
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> infos = BaseUtil.castObject(string, List.class);
				List<TriggerVo>  triggerVos = new ArrayList<TriggerVo>();
				for (Map<String, Object> map : infos) {
					TriggerVo triggerVo = BaseUtil.castObject(map, TriggerVo.class);
					if ("1".equals(triggerVo.getPriority())) {
						triggerVo.setPriority(AlarmLevel.INFO);
					} else if ("2".equals(triggerVo.getPriority())) {
						triggerVo.setPriority(AlarmLevel.WARN);
					} else if ("3".equals(triggerVo.getPriority())) {
						triggerVo.setPriority(AlarmLevel.GENERAL);
					} else if ("4".equals(triggerVo.getPriority())) {
						triggerVo.setPriority(AlarmLevel.IMPORTANT);
					} else if ("5".equals(triggerVo.getPriority())) {
						triggerVo.setPriority(AlarmLevel.SERIOUS);
					}
					triggerVos.add(triggerVo);
				}
				result.setTriggers(triggerVos);
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
//			log.info("adapter is not available when query node list");
			throw new AdapterUnavailableException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
