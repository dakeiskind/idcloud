package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VmVO;

public class VmScanAloneResult extends BaseResult {

    private String uuid;
    private VmVO vm;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public VmVO getVm() {
        return vm;
    }

    public void setVm(VmVO vm) {
        this.vm = vm;
    }

}
