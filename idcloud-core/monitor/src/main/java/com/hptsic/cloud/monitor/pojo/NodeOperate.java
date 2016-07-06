package com.hptsic.cloud.monitor.pojo;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.node.Interface;
import com.hptsic.cloud.monitor.pojo.node.Ipmi;

import java.util.List;

/***
 * 节点操作输入参数，节点的add，delete，update等
 *
 * @author hpadmin
 *
 */
public class NodeOperate extends Base{
	private String ip;
	private String id;
	private String name;
	private String description;
	private Ipmi ipmi;
	private List<Interface> interfaces;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Ipmi getIpmi() {
		return ipmi;
	}
	public void setIpmi(Ipmi ipmi) {
		this.ipmi = ipmi;
	}
	public List<Interface> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}
}
