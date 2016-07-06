package com.hptsic.cloud.monitor.provision.action.monitor;

import com.hptsic.cloud.monitor.common.Base;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QueryNodeList extends ActionNode {

	@SuppressWarnings("unchecked")
	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		
		log.info("get node list info");
		NodeMonitorResult result = new NodeMonitorResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);
//		client = BaseUtil.setHeaders(client, base);

		try {
			if(base.isRetrieveAll()){
				client.query("retrieveAll", base.isRetrieveAll());
			}
			Response resp = client.path("/nodes")
					.type(MediaType.APPLICATION_JSON).get();

			if (resp.getStatus() == 200) {
//				List object = resp.readEntity(List.class);
			//	InputStream inputStream = (InputStream) resp.getEntity();
				//				 NodeMonitorInfo info = resp.readEntity(new GenericType<NodeMonitorInfo>(){});
				
				String string = resp.readEntity(String.class);
				List<Map<String, Object>> infos = BaseUtil.castObject(string, List.class);
				List<NodeInfo>  nodeInfos = new ArrayList<NodeInfo>();
				for (Map<String, Object> map : infos) {
					NodeInfo nodeInfo = BaseUtil.castObject(map, NodeInfo.class);
					nodeInfos.add(nodeInfo);
				}
				result.setNodeList(nodeInfos);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
