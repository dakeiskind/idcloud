package com.hptsic.cloud.monitor.pojo;

import com.hptsic.cloud.monitor.common.Base;

public class AlarmRoleUpate extends Base {
	private String id;
	private String name;
	private String description;
	private String type;
	private String subType;
	private String value;
	private Integer valueType;
	private String count;
	private Integer operator;
	private String priority;
	private Integer status;
	public Integer getOperator() {
		return operator;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getType() {
		return type;
	}
	public String getSubType() {
		return subType;
	}
	public String getValue() {
		return value;
	}
	public Integer getValueType() {
		return valueType;
	}
	public String getCount() {
		return count;
	}
	public Integer getStatus() {
		return status;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
