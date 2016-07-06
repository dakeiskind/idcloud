package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VSwitchVO;

import java.util.List;

public class VswitchScanResult extends BaseResult {
    private List<VSwitchVO> vSwitchs;

    public List<VSwitchVO> getvSwitchs() {
        return vSwitchs;
    }

    public void setvSwitchs(List<VSwitchVO> vSwitchs) {
        this.vSwitchs = vSwitchs;
    }

}
