package com.h3c.idcloud.core.adapter.pojo.common;

import java.util.Map;

public class BaseResult {
    private String providerType;
    private String virtEnvType;
    private String virtEnvUuid;
    private boolean success;
    private String errCode;
    private String errMsg;
    private String msgId;
    private Map<String, Object> options;//自定义参数

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getVirtEnvType() {
        return virtEnvType;
    }

    public void setVirtEnvType(String virtEnvType) {
        this.virtEnvType = virtEnvType;
    }

    public String getVirtEnvUuid() {
        return virtEnvUuid;
    }

    public void setVirtEnvUuid(String virtEnvUuid) {
        this.virtEnvUuid = virtEnvUuid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
