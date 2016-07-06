package com.h3c.idcloud.core.adapter.facade.provision.exception;

import java.util.Iterator;
import java.util.Map;

public class KvmException extends CommonAdapterException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String errCode;
    private String errMsg;

    public KvmException(String errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public KvmException(Map map) {
        super();
        if (map != null) {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                Map exceptionMap = (Map) entry.getValue();
                this.errCode = Integer.toString((Integer) exceptionMap.get("code"));
                this.errMsg = (String) exceptionMap.get("message");
            }
        }
    }

    public KvmException() {

    }

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
