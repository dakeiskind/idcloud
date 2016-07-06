package com.h3c.idcloud.core.adapter.facade.provision.exception;

public class PowerException extends CommonAdapterException {

    /**
     *
     */
    private static final long serialVersionUID = 6377726734510252773L;
    private String errCode;
    private String errMsg;

    public PowerException(String errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
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
