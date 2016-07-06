package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.ArrayList;
import java.util.List;

public class Network extends CommonAdapterResult {

    private List<VSwitch> vSwitchs = new ArrayList<VSwitch>();
    private List<DvSwitch> dvSwitchs = new ArrayList<DvSwitch>();


    public List<DvSwitch> getDvSwitchs() {
        return dvSwitchs;
    }

    public void setDvSwitchs(List<DvSwitch> dvSwitchs) {
        this.dvSwitchs = dvSwitchs;
    }

    public List<VSwitch> getvSwitchs() {
        return vSwitchs;
    }

    public void setvSwitchs(List<VSwitch> vSwitchs) {
        this.vSwitchs = vSwitchs;
    }


}
