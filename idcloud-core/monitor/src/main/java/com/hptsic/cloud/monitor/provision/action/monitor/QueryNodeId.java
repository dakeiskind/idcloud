package com.hptsic.cloud.monitor.provision.action.monitor;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.NodeOperate;
import com.hptsic.cloud.monitor.pojo.node.NodeInfo;
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
public class QueryNodeId extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("get node id");
		NodeOperate nodeOperate = (NodeOperate) base;
		NodeMonitorResult result = new NodeMonitorResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);
//		client = BaseUtil.setHeaders(client, base);

		try {
			Response resp = client.path("/nodes").query("ip", nodeOperate.getIp())
					.type(MediaType.APPLICATION_JSON).get();

			if (resp.getStatus() == 200) {
//				 NodeMonitorInfo info = resp.readEntity(new GenericType<NodeMonitorInfo>(){});
				String  resultStr= resp.readEntity(String.class);
				NodeInfo info = BaseUtil.castObject(resultStr, NodeInfo.class);
				result.setNodeInfo(info);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
