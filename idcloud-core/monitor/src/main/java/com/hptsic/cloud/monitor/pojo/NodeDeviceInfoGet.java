package com.hptsic.cloud.monitor.pojo;

import com.hptsic.cloud.monitor.common.Base;

import java.util.List;

/***
 * 获取节点下的cpu，memory,disk,network 的信息参数设置
 * @author hpadmin
 *
 */
public class NodeDeviceInfoGet extends Base{
	private List<String> nodeIds;
	private String timeFrom;
	private String timeTo;
	private List<String> sortField;
	public List<String> getNodeIds() {
		return nodeIds;
	}
	public void setNodeIds(List<String> nodeIds) {
		this.nodeIds = nodeIds;
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	public String getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
	public List<String> getSortField() {
		return sortField;
	}
	public void setSortField(List<String> sortField) {
		this.sortField = sortField;
	}
}
