package com.hptsic.cloud.monitor.provision.exception;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Iterator;
import java.util.Map;

public class MonitorException extends CommonAdapterException {
	private static final long serialVersionUID = 4329074761932262199L;

	public MonitorException(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MonitorException(Map map) {
		super();
		if(map != null) {
			Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
				Map exceptionMap = (Map) entry.getValue();
				this.errCode = Integer.toString((Integer) exceptionMap.get("code"));
				this.errMsg = (String) exceptionMap.get("message");
			}
		}
	}
	public MonitorException() {

	}
	@JsonProperty("code")
	private String errCode;
	@JsonProperty("msg")
	private String errMsg;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
