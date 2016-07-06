package com.hptsic.cloud.monitor;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.common.ProviderFactory;
import com.hptsic.cloud.monitor.pojo.CurrentNodeInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeDeviceInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeMonitorInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeOperate;
import com.hptsic.cloud.monitor.pojo.node.*;
import com.hptsic.cloud.monitor.provision.ProvisionMonitor;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorHandler implements ProvisionMonitor {
	@Autowired
	private ProviderFactory providerFactory;

	@Override
	public NodeMonitorInfo getNodeMonitorInfo(
			NodeMonitorInfoGet nodeMonitorInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeMonitorInfoGet.getProviderType();
		return providerFactory.getProvisionMonitor(type).getNodeMonitorInfo(
				nodeMonitorInfoGet);
	}

/*	@Override
	public NodeCpuInfo getNodeCpuInfo(NodeDeviceInfoGet nodeDeviceInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeDeviceInfoGet.getProviderType();
		
		NodeCpuInfo info = new NodeCpuInfo();
		info.setMaxUsage("45.32");
		info.setAvgUsage("34.23");
		info.setMinUsage("19.72");
		List<CpuData> list = new ArrayList<CpuData>();
		for(int i=10;i<20;i++){
			CpuData data = new CpuData();
			data.setTime("2015-06-"+i+" 00:00:00");
			Double tt = Math.floor(Math.random() * 11);
			data.setUsage(tt.toString());
			list.add(data);
		}
		info.setData(list);
		
		return info;
		
//		return providerFactory.getProvisionMonitor(type).getNodeCpuInfo(nodeDeviceInfoGet);
	}*/

	@Override
	public NodeCpuInfo getNodeCpuInfo(NodeDeviceInfoGet nodeDeviceInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeDeviceInfoGet.getProviderType();
		return providerFactory.getProvisionMonitor(type).getNodeCpuInfo(
				nodeDeviceInfoGet);
	}

	@Override
	public NodeMemInfo getNodeMemInfo(NodeDeviceInfoGet nodeDeviceInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeDeviceInfoGet.getProviderType();
		return providerFactory.getProvisionMonitor(type).getNodeMemInfo(
				nodeDeviceInfoGet);
	}

	@Override
	public NodeNetworkInfo getNodeNetworkInfo(
			NodeDeviceInfoGet nodeDeviceInfoGet) throws CommonAdapterException,
			AdapterUnavailableException {
		String type = nodeDeviceInfoGet.getProviderType();
		return providerFactory.getProvisionMonitor(type).getNodeNetworkInfo(
				nodeDeviceInfoGet);
	}

	@Override
	public NodeDiskInfo getNodeDiskInfo(NodeDeviceInfoGet nodeDeviceInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeDeviceInfoGet.getProviderType();
		return providerFactory.getProvisionMonitor(type).getNodeDiskInfo(
				nodeDeviceInfoGet);
	}

	@Override
	public NodeInfo getNodeInfo(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeOperate.getProviderType();
		return providerFactory.getProvisionMonitor(type).getNodeInfo(
				nodeOperate);
	}

	@Override
	public NodeInfo getNodeId(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeOperate.getProviderType();
		return providerFactory.getProvisionMonitor(type).getNodeId(nodeOperate);
	}

	@Override
	public NodeInfo addNode(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeOperate.getProviderType();
		return providerFactory.getProvisionMonitor(type).addNode(nodeOperate);
	}

	@Override
	public boolean updateNode(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeOperate.getProviderType();
		return providerFactory.getProvisionMonitor(type)
				.updateNode(nodeOperate);
	}

	@Override
	public boolean deleteNode(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		String type = nodeOperate.getProviderType();
		return providerFactory.getProvisionMonitor(type)
				.deleteNode(nodeOperate);
	}

	@Override
	public List<NodeInfo> getNodeList(Base base) throws CommonAdapterException,
			AdapterUnavailableException {
		return providerFactory.getProvisionMonitor(base.getProviderType()).getNodeList(base);
	}

	@Override
	public CurrentInfo getCurrentInfo(CurrentNodeInfoGet nodeInfoGet) {
		return providerFactory.getProvisionMonitor(nodeInfoGet.getProviderType()).getCurrentInfo(nodeInfoGet);
	}

}
