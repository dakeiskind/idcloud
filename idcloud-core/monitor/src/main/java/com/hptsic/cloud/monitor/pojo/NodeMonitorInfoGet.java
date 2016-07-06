package com.hptsic.cloud.monitor.pojo;

import com.hptsic.cloud.monitor.common.Base;

import java.util.List;

/***
 * 获取监控节点的信息所需参数id，参数为list为了后期支持同时获取多个节点监控信息
 * @author lei-feng.li
 *
 */
public class NodeMonitorInfoGet extends Base{
	private List<String> nodeIds;

	public List<String> getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(List<String> nodeIds) {
		this.nodeIds = nodeIds;
	}

}
