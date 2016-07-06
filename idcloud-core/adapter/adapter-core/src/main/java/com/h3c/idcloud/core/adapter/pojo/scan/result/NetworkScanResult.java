package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DvSwitchVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VSwitchVO;

import java.util.ArrayList;
import java.util.List;

public class NetworkScanResult extends BaseResult {

    private List<VSwitchVO> svSwitchs = new ArrayList<VSwitchVO>();
    private List<DvSwitchVO> dvSwitchs = new ArrayList<DvSwitchVO>();

    public List<VSwitchVO> getSvSwitchs() {
        return svSwitchs;
    }

    public void setSvSwitchs(List<VSwitchVO> svSwitchs) {
        this.svSwitchs = svSwitchs;
    }

    public List<DvSwitchVO> getDvSwitchs() {
        return dvSwitchs;
    }

    public void setDvSwitchs(List<DvSwitchVO> dvSwitchs) {
        this.dvSwitchs = dvSwitchs;
    }

}
