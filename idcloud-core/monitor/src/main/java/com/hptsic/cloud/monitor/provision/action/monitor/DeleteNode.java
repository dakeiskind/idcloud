package com.hptsic.cloud.monitor.provision.action.monitor;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.NodeOperate;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import com.hptsic.cloud.monitor.provision.model.NodeMonitorResult;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class DeleteNode extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("delete node");
		NodeOperate nodeOperate = (NodeOperate) base;
		NodeMonitorResult result = new NodeMonitorResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);
		client.accept(MediaType.APPLICATION_JSON);

		try {
			Response resp = client.path("/nodes/"+nodeOperate.getId())
					.type(MediaType.APPLICATION_JSON).delete();

			if (resp.getStatus() == 200) {
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
//			log.info("adapter is not available when rebuilding vm");
			throw new AdapterUnavailableException();
		}
		return result;
	}
}
