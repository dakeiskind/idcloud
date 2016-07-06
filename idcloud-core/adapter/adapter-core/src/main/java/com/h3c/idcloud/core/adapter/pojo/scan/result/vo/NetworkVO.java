package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

import java.util.ArrayList;
import java.util.List;

public class NetworkVO {

    private List<VSwitchVO> svSwitchs = new ArrayList<VSwitchVO>();
    private List<DvSwitchVO> dvSwitchs = new ArrayList<DvSwitchVO>();


    public List<DvSwitchVO> getDvSwitchs() {
        return dvSwitchs;
    }

    public void setDvSwitchs(List<DvSwitchVO> dvSwitchs) {
        this.dvSwitchs = dvSwitchs;
    }

    public List<VSwitchVO> getSvSwitchs() {
        return svSwitchs;
    }

    public void setSvSwitchs(List<VSwitchVO> svSwitchs) {
        this.svSwitchs = svSwitchs;
    }

}
