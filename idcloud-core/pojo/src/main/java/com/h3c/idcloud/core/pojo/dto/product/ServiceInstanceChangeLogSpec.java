package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author ChengQi
 *
 */
public class ServiceInstanceChangeLogSpec<T> implements Serializable{

	private Map<String, Object> variables;
	
	private T params;

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public T getParams() {
		return params;
	}

	public void setParams(T params) {
		this.params = params;
	}

	
	
	
}
