package com.hptsic.cloud.monitor.provision.action.monitor;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.CurrentNodeInfoGet;
import com.hptsic.cloud.monitor.pojo.node.CurrentInfo;
import com.hptsic.cloud.monitor.pojo.node.NodeResult;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import com.hptsic.cloud.monitor.util.BaseUtil;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Service
public class QueryCurrentInfo extends ActionNode {

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("get current node info");
		CurrentNodeInfoGet nodeInfoGet = (CurrentNodeInfoGet) base;
		NodeResult result = new NodeResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);

		try {
			if(base.isRetrieveAll()){
				client.query("retrieveAll", base.isRetrieveAll());
			}
			List<String> typeList = nodeInfoGet.getTypeList();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0;i<typeList.size();i++) {
				buffer.append(typeList.get(i));
				if(i != typeList.size()-1) {
					buffer.append(",");
				}
			}
			Response resp = client.path("/data/current")
					.query("type", buffer.toString())
					.query("node_id", nodeInfoGet.getNodeId())
					.type(MediaType.APPLICATION_JSON).get();
			
			if (resp.getStatus() == 200) {
				String resultStr = resp.readEntity(String.class);
				CurrentInfo info = BaseUtil.castObject(resultStr, CurrentInfo.class);
				info.setSuccess(true);
				result.setCurrentInfo(info);
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
			throw new AdapterUnavailableException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
