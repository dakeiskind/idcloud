package com.h3c.idcloud.core.pojo.dto.system;

import com.h3c.idcloud.core.pojo.dto.product.MgtObjRes;

import java.util.List;



public class BizResForm {

	private List<MgtObjRes> bizReses;

	private Long bizSid;
	
	private String resVeSid;
	
	private String [] clearFlag;

	public List<MgtObjRes> getBizReses() {
		return bizReses;
	}

	public void setBizReses(List<MgtObjRes> bizReses) {
		this.bizReses = bizReses;
	}

	public Long getBizSid() {
		return bizSid;
	}

	public void setBizSid(Long bizSid) {
		this.bizSid = bizSid;
	}

	public String[] getClearFlag() {
		return clearFlag;
	}

	public void setClearFlag(String[] clearFlag) {
		this.clearFlag = clearFlag;
	}

	public String getResVeSid() {
		return resVeSid;
	}

	public void setResVeSid(String resVeSid) {
		this.resVeSid = resVeSid;
	}


}
