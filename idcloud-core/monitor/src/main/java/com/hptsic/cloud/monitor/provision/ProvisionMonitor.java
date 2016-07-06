package com.hptsic.cloud.monitor.provision;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.CurrentNodeInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeDeviceInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeMonitorInfoGet;
import com.hptsic.cloud.monitor.pojo.NodeOperate;
import com.hptsic.cloud.monitor.pojo.node.*;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;

import java.util.List;

public interface ProvisionMonitor {
	/***
	 * 获取节点当前监控信息
	 * @param nodeMonitorInfoGet
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public NodeMonitorInfo getNodeMonitorInfo(NodeMonitorInfoGet nodeMonitorInfoGet) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 获取节点CPU监控信息
	 * @param nodeDeviceInfoGet
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public NodeCpuInfo getNodeCpuInfo(NodeDeviceInfoGet nodeDeviceInfoGet) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 获取节点Memory监控信息
	 * @param nodeDeviceInfoGet
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public NodeMemInfo getNodeMemInfo(NodeDeviceInfoGet nodeDeviceInfoGet) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 获取节点Network信息
	 * @param nodeDeviceInfoGet
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public NodeNetworkInfo getNodeNetworkInfo(NodeDeviceInfoGet nodeDeviceInfoGet) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 获取节点Disk监控信息
	 * @param nodeDeviceInfoGet
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public NodeDiskInfo getNodeDiskInfo(NodeDeviceInfoGet nodeDeviceInfoGet) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 获取监控节点信息
	 * @param nodeOperate
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public NodeInfo getNodeInfo(NodeOperate nodeOperate) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 查询监控节点ID
	 * @param nodeOperate
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public NodeInfo getNodeId(NodeOperate nodeOperate) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 *新增监控节点
	 * @param nodeOperate
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public NodeInfo addNode(NodeOperate nodeOperate) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 更新监控节点
	 * @param nodeOperate
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public boolean updateNode(NodeOperate nodeOperate) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 删除监控节点
	 * @param nodeOperate
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public boolean deleteNode(NodeOperate nodeOperate) throws CommonAdapterException, AdapterUnavailableException;
	
	public List<NodeInfo> getNodeList(Base base) throws CommonAdapterException, AdapterUnavailableException;
	
	public CurrentInfo getCurrentInfo(CurrentNodeInfoGet nodeInfoGet) ;
}
