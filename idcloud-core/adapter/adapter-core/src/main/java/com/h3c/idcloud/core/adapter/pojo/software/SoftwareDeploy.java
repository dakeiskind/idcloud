package com.h3c.idcloud.core.adapter.pojo.software;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import java.util.ArrayList;
import java.util.List;

public class SoftwareDeploy extends Base {

    private String vmId;
    private String targetSrvIp;
    private String targetUsrName;
    private String targetUsrPasswd;
    private String osType;
    private String osVersion;
    private List<Software> softwares = new ArrayList<Software>();

    public String getTargetSrvIp() {
        return targetSrvIp;
    }

    public void setTargetSrvIp(String targetSrvIp) {
        this.targetSrvIp = targetSrvIp;
    }

    public String getTargetUsrName() {
        return targetUsrName;
    }

    public void setTargetUsrName(String targetUsrName) {
        this.targetUsrName = targetUsrName;
    }

    public String getTargetUsrPasswd() {
        return targetUsrPasswd;
    }

    public void setTargetUsrPasswd(String targetUsrPasswd) {
        this.targetUsrPasswd = targetUsrPasswd;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public List<Software> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<Software> softwares) {
        this.softwares = softwares;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

}
