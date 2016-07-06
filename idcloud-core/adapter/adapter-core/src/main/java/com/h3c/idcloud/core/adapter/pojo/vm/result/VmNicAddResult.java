package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;

import java.util.ArrayList;
import java.util.List;

public class VmNicAddResult extends BaseResult {

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
