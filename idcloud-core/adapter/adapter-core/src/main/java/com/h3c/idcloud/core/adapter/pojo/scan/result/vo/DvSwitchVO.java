package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

import java.util.ArrayList;
import java.util.List;

public class DvSwitchVO {

    private String name;
    private String uuid;
    private List<DvPortGroupVO> dvPortGroupInfos = new ArrayList<DvPortGroupVO>();
    private String[] hostsUuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<DvPortGroupVO> getDvPortGroupInfos() {
        return dvPortGroupInfos;
    }

    public void setDvPortGroupInfos(List<DvPortGroupVO> dvPortGroupInfos) {
        this.dvPortGroupInfos = dvPortGroupInfos;
    }

    public String[] getHostsUuid() {
        return hostsUuid;
    }

    public void setHostsUuid(String[] hostsUuid) {
        this.hostsUuid = hostsUuid;
    }

}
