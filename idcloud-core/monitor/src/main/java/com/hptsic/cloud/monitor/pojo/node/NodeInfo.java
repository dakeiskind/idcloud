package com.hptsic.cloud.monitor.pojo.node;

import java.util.List;

public class NodeInfo {
	private String id;
	private String name;
	private String description;
	private Ipmi ipmi;
	private List<Interface> interfaces;
	
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
