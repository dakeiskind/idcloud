package com.h3c.idcloud.core.pojo.dto.product;

public class ServiceSpecChange<T> {

	Long instanceSid;

	Long serviceSid;

	T changeData;

	public Long getInstanceSid() {
		return instanceSid;
	}

	public void setInstanceSid(Long instanceSid) {
		this.instanceSid = instanceSid;
	}

	public Long getServiceSid() {
		return serviceSid;
	}

	public void setServiceSid(Long serviceSid) {
		this.serviceSid = serviceSid;
	}

	public T getChangeData() {
		return changeData;
	}

	public void setChangeData(T changeData) {
		this.changeData = changeData;
	}

}
