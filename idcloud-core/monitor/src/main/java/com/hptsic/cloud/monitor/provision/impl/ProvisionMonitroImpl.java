package com.hptsic.cloud.monitor.provision.impl;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.CurrentNodeInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeDeviceInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeMonitorInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeOperate;
import com.hptsic.cloud.monitor.pojo.node.*;
import com.hptsic.cloud.monitor.provision.ProvisionMonitor;
import com.hptsic.cloud.monitor.provision.action.monitor.*;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;
import com.hptsic.cloud.monitor.provision.model.NodeMonitorResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvisionMonitroImpl implements ProvisionMonitor {

	@Autowired
	private QueryNodeInfo queryNodeInfo;
	@Autowired
	private QueryNodeCpuInfo queryNodeCpuInfo;
	@Autowired
	private QueryNodeDiskInfo queryNodeDiskInfo;
	@Autowired
	private QueryNodeNetworkInfo queryNodeNetworkInfo;
	@Autowired
	private QueryNodeMemInfo queryNodeMemInfo;
	@Autowired 
	private QueryNodeMonitorInfo queryNodeMonitorInfo;
	@Autowired
	private QueryNodeId queryNodeId;
	@Autowired 
	private AddNode addNode;
	@Autowired
	private UpdateNode updateNode;
	@Autowired 
	private DeleteNode deleteNode;
	@Autowired
	private QueryNodeList queryNodeList;
	@Autowired
	private QueryCurrentInfo queryCurrentInfo;
	@Override
	public NodeMonitorInfo getNodeMonitorInfo(
			NodeMonitorInfoGet nodeMonitorInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) queryNodeMonitorInfo.invoke(nodeMonitorInfoGet);
		return result.getNodeMonitorInfo();
	}

	@Override
	public NodeCpuInfo getNodeCpuInfo(NodeDeviceInfoGet nodeDeviceInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) queryNodeCpuInfo.invoke(nodeDeviceInfoGet);
		return result.getCpuInfo();
	}

	@Override
	public NodeMemInfo getNodeMemInfo(NodeDeviceInfoGet nodeDeviceInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) queryNodeMemInfo.invoke(nodeDeviceInfoGet);
		return result.getMemInfo();
	}

	@Override
	public NodeNetworkInfo getNodeNetworkInfo(
			NodeDeviceInfoGet nodeDeviceInfoGet) throws CommonAdapterException,
			AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) queryNodeNetworkInfo.invoke(nodeDeviceInfoGet);
		return result.getNetworkInfo();
	}

	@Override
	public NodeDiskInfo getNodeDiskInfo(NodeDeviceInfoGet nodeDeviceInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) queryNodeDiskInfo.invoke(nodeDeviceInfoGet);
		return result.getDiskInfo();
	}

	@Override
	public NodeInfo getNodeInfo(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) queryNodeInfo.invoke(nodeOperate);
		return result.getNodeInfo();
	}

	@Override
	public NodeInfo getNodeId(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) queryNodeId.invoke(nodeOperate);
		return result.getNodeInfo();
	}

	@Override
	public NodeInfo addNode(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) addNode.invoke(nodeOperate);
		return result.getNodeInfo();
	}

	@Override
	public boolean updateNode(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) updateNode.invoke(nodeOperate);
		return result.isSuccess();
	}

	@Override
	public boolean deleteNode(NodeOperate nodeOperate)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) deleteNode.invoke(nodeOperate);
		return result.isSuccess();
	}

	@Override
	public List<NodeInfo> getNodeList(Base base) throws CommonAdapterException,
			AdapterUnavailableException {
		NodeMonitorResult result = (NodeMonitorResult) queryNodeList.invoke(new Base());
		return result.getNodeList();
	}

	@Override
	public CurrentInfo getCurrentInfo(CurrentNodeInfoGet nodeInfoGet) {
		try {
			NodeResult result = (NodeResult) queryCurrentInfo.invoke(nodeInfoGet);
			return result.getCurrentInfo();
		} catch (Exception e) {
			return new CurrentInfo();
		} 
	}

}
