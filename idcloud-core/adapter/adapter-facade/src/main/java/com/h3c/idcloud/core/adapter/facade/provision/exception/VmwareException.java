package com.h3c.idcloud.core.adapter.facade.provision.exception;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

public class VmwareException extends CommonAdapterException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String errCode;
    private String errMsg;
    @JsonIgnore()
    private String type;
    @JsonIgnore
    private String message;
    @JsonIgnore
    private String vmId;
    @JsonIgnore
    private List<String> disks;

    public VmwareException(String errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public VmwareException() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // @JsonProperty("type")
    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    // @JsonProperty("message")
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public List<String> getDisks() {
        return disks;
    }

    public void setDisks(List<String> disks) {
        this.disks = disks;
    }

}
