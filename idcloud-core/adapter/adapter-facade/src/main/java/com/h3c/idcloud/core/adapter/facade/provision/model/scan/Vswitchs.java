package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VSwitchVO;

import java.util.List;

public class Vswitchs extends CommonAdapterResult {
    private List<VSwitchVO> vSwitchs;

    public List<VSwitchVO> getvSwitchs() {
        return vSwitchs;
    }

    public void setvSwitchs(List<VSwitchVO> vSwitchs) {
        this.vSwitchs = vSwitchs;
    }
}
