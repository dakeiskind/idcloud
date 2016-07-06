package com.hptsic.cloud.monitor.pojo.trigger;

import org.codehaus.jackson.annotate.JsonProperty;

public class TriggerVo {
	private String id;
	private String name;
	private String description;
	private String type;
	@JsonProperty("sub_type")
	private String subType;
	private String operator;
	private String value;
	@JsonProperty("value_type")
	private String valueType;
	private String count;
	private String priority;
	private String status;
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
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
	public String getOperator() {
		return operator;
	}
	public String getValue() {
		return value;
	}
	public String getCount() {
		return count;
	}
	public String getPriority() {
		return priority;
	}
	public String getStatus() {
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
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
