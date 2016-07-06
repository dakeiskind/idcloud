package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import java.util.ArrayList;
import java.util.List;

public class DvSwitch {

    private String name;
    private String uuid;
    private List<DvPortGroup> dvPortGroupInfos = new ArrayList<DvPortGroup>();
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

    public List<DvPortGroup> getDvPortGroupInfos() {
        return dvPortGroupInfos;
    }

    public void setDvPortGroupInfos(List<DvPortGroup> dvPortGroupInfos) {
        this.dvPortGroupInfos = dvPortGroupInfos;
    }

    public String[] getHostsUuid() {
        return hostsUuid;
    }

    public void setHostsUuid(String[] hostsUuid) {
        this.hostsUuid = hostsUuid;
    }

}
