package com.h3c.idcloud.core.adapter.facade.provision.exception;

public class CommonAdapterException extends Exception {

    private static final long serialVersionUID = 2687601128266133287L;

    private String errCode;

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
