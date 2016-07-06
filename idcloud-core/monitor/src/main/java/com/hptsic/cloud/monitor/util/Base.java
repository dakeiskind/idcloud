package com.hptsic.cloud.monitor.util;

import org.codehaus.jackson.annotate.JsonProperty;

public class Base {

	@JsonProperty("user_id")
	private String userId;
	private String name;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
