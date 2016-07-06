package com.hptsic.cloud.monitor.provision.action.monitor;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.NodeDeviceInfoGet;
import com.hptsic.cloud.monitor.pojo.node.NodeDiskInfo;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import com.hptsic.cloud.monitor.provision.model.NodeMonitorResult;
import com.hptsic.cloud.monitor.util.BaseUtil;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Service
public class QueryNodeNetworkInfo extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("get nodeDiskInfo monitor message");
		NodeDeviceInfoGet infoGet = (NodeDeviceInfoGet) base;
		NodeMonitorResult result = new NodeMonitorResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);

		try {
			if(base.isRetrieveAll()){
				client.query("retrieveAll", base.isRetrieveAll());
			}
			Response resp = client.path("/data/disk")
					.query("node_ids", infoGet.getNodeIds())
					.query("time_form", infoGet.getTimeFrom())
					.query("time_to", infoGet.getTimeTo())
					.query("sort_field", infoGet.getSortField())
					.type(MediaType.APPLICATION_JSON).get();

			if (resp.getStatus() == 200) {
//				 NodeMonitorInfo info = resp.readEntity(new GenericType<NodeMonitorInfo>(){});
				String resultStr = resp.readEntity(String.class);
				NodeDiskInfo info = BaseUtil.castObject(resultStr, NodeDiskInfo.class);
				result.setDiskInfo(info);
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
//			log.info("adapter is not available when query diskifo of node");
			throw new AdapterUnavailableException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
