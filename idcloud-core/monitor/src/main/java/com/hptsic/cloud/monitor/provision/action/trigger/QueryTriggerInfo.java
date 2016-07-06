package com.hptsic.cloud.monitor.provision.action.trigger;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.AlarmLevel;
import com.hptsic.cloud.monitor.pojo.AlarmRoleInfoGet;
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

@Service
public class QueryTriggerInfo extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("get trigger info");
		AlarmRoleInfoGet tInfo = (AlarmRoleInfoGet) base;
		TriggerResult result = new TriggerResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);

		try {
			if(base.isRetrieveAll()){
				client.query("retrieveAll", base.isRetrieveAll());
			}
			Response resp = client.path("/triggers/"+tInfo.getId())
					.type(MediaType.APPLICATION_JSON).get();

			if (resp.getStatus() == 200) {
				String string = resp.readEntity(String.class);
				TriggerVo triggerVo = BaseUtil.fromJson(string, TriggerVo.class);
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
				result.setTrigger(triggerVo);
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
//			log.info("adapter is not available when query node info");
			throw new AdapterUnavailableException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
