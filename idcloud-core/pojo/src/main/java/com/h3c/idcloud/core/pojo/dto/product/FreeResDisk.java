package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;

public class FreeResDisk implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long instanceSid;
    
    private String instSpecName;
    
    private String instSpecDesc;
    
    private Long instSpecValue;

    
	public Long getInstanceSid() {
		return instanceSid;
	}

	public void setInstanceSid(Long instanceSid) {
		this.instanceSid = instanceSid;
	}

	public String getInstSpecName() {
		return instSpecName;
	}

	public void setInstSpecName(String instSpecName) {
		this.instSpecName = instSpecName;
	}

	public String getInstSpecDesc() {
		return instSpecDesc;
	}

	public void setInstSpecDesc(String instSpecDesc) {
		this.instSpecDesc = instSpecDesc;
	}

	public Long getInstSpecValue() {
		return instSpecValue;
	}

	public void setInstSpecValue(Long instSpecValue) {
		this.instSpecValue = instSpecValue;
	}


    
    
}