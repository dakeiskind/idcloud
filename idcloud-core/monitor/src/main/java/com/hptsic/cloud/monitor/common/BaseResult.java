package com.hptsic.cloud.monitor.common;


public class BaseResult {
	private boolean success;
	private String errCode;
	private String errMsg;
	public boolean isSuccess() {
		return success;
	}
	public String getErrCode() {
		return errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
