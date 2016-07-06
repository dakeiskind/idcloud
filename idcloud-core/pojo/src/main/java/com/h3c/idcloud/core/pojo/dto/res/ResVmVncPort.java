package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

public class ResVmVncPort implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resVmSid;

    private Long vncPort;

    private String token;

    private String resTopologySid;

    private String hostIp;

    public String getResVmSid() {
        return resVmSid;
    }

    public void setResVmSid(String resVmSid) {
        this.resVmSid = resVmSid;
    }

    public Long getVncPort() {
        return vncPort;
    }

    public void setVncPort(Long vncPort) {
        this.vncPort = vncPort;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getResTopologySid() {
        return resTopologySid;
    }

    public void setResTopologySid(String resTopologySid) {
        this.resTopologySid = resTopologySid;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

}