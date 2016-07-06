package com.h3c.idcloud.core.pojo.dto.product;

/**
 * 
 * 块存储扩容封装类
 * 
 * @author qiche
 *
 */
public class VdExpandRequest {

	private String resVdSid;
	
	private String mgtObjSid;
	
	private Long size;

	public String getResVdSid() {
		return resVdSid;
	}

	public void setResVdSid(String resVdSid) {
		this.resVdSid = resVdSid;
	}

	public String getMgtObjSid() {
		return mgtObjSid;
	}

	public void setMgtObjSid(String mgtObjSid) {
		this.mgtObjSid = mgtObjSid;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
