package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.service.product.api.MonitorService;
import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.hptsic.cloud.monitor.pojo.NodeOperate;
import com.hptsic.cloud.monitor.pojo.node.Interface;
import com.hptsic.cloud.monitor.pojo.node.Ipmi;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Service(version = "1.0.0")
@Component
public class MonitorServiceImpl implements MonitorService {

	/*@Autowired
	private MonitorHandler monitorHandler;*/

	@Reference(version = "1.0.0")
	private ResVmService resVmService;

	@Reference(version = "1.0.0")
	private ResHostService resHostService;
	

	public String addMonitorNode(String nodeName, String ip) throws Exception {
		String nodeId = null;
		NodeOperate nodeOperate = new NodeOperate();
		nodeOperate.setProviderType("opennms");
		nodeOperate.setName(nodeName);
		nodeOperate.setDescription("add monitor node id");
		Ipmi ipmi = new Ipmi();
		nodeOperate.setIpmi(ipmi);
		
		Interface interface1 = new Interface();
		interface1.setIp(ip);
		// 端口
		interface1.setPort("161");
		// 类型，2-snmp，3-ipmi
		interface1.setType("2");
		// 是否设置为默认，0-not default，1-default
		interface1.setMain("1");
		
		List<Interface> interfaces =new ArrayList<Interface>();
		interfaces.add(interface1);
		nodeOperate.setInterfaces(interfaces);
		/*NodeInfo nodeCpuInfo = monitorHandler.addNode(nodeOperate);
		nodeId = nodeCpuInfo.getId();
		if(StringUtils.isBlank(nodeCpuInfo.getId())) {
			throw new ServiceException("add node monitor failure, the nodeId is null or empty.");
		}*/
		return nodeId;
	}
	
	public void addVmToMonitor(ResVm resVm) throws Exception {
		String nodeId = addMonitorNode(resVm.getVmName(), resVm.getVmIp());
		resVm.setMonitorNodeId(nodeId);
//		resVmService.updateByPrimaryKeySelective(resVm); ---------wsl
	}
	
	public void addVmToMonitor(String vmId) throws Exception {
//		ResVm resVm = resVmService.selectByPrimaryKey(vmId);     ------------wsl
//		addVmToMonitor(resVm);     ------------wsl
	}

	public void addHostToMonitor(String hostId) throws Exception {
//		ResHost resHost = resHostService.selectByPrimaryKey(hostId); -------wsl
//		addHostToMonitor(resHost);
	}

	public void addHostToMonitor(ResHost resHost) throws Exception {
		String nodeId = addMonitorNode(resHost.getHostName(), resHost.getHostIp());
		resHost.setMonitorNodeId(nodeId);
//		resHostService.updateByPrimaryKeySelective(resHost);------wsl
	}
	
	public void deleteMonitorNode(String nodeId) throws Exception {
		NodeOperate delNodeOperate = new NodeOperate();
		delNodeOperate.setProviderType("opennms");
		delNodeOperate.setId(nodeId);
		/*boolean nodeCpuInfo = monitorHandler.deleteNode(delNodeOperate);
		if(!nodeCpuInfo){
			throw new ServiceException("delete node monitor failure, deleteNode return result is false.");
		}*/
	}
	
	public void deleteVmFromMonitor(ResVm resVm) throws Exception {
		if(resVm != null && resVm.getMonitorNodeId() != null) {
			deleteMonitorNode(resVm.getMonitorNodeId());
			resVm.setMonitorNodeId(null);
//			resVmService.updateByPrimaryKey(resVm);-------------wsl
		}
	}
	
	public void deleteVmFromMonitor(String vmId) throws Exception {
//		ResVm resVm = resVmService.selectByPrimaryKey(vmId); -----------wsl
//		deleteVmFromMonitor(resVm);
	}
	
	public void deleteHostFromMonitor(ResHost resHost) throws Exception {
		deleteMonitorNode(resHost.getMonitorNodeId());
		resHost.setMonitorNodeId(null);
//		resHostService.updateByPrimaryKey(resHost);---------wsl
	}
	
	public void deleteHostFromMonitor(String hostId) throws Exception {
//		ResHost resHost = resHostService.selectByPrimaryKey(hostId);  ---------wsl
//		deleteHostFromMonitor(resHost);
	}
}
