package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import java.util.ArrayList;
import java.util.List;

public class VmNicDelete extends Base {

    private String vmName;
    private List<VmNic> nics = new ArrayList<VmNic>();

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public List<VmNic> getNics() {
        return nics;
    }

    public void setNics(List<VmNic> nics) {
        this.nics = nics;
    }

}
