package com.h3c.idcloud.core.adapter.pojo.scan;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import java.util.List;

public class SSPScan extends Base {
    private List<ViosVo> visoList;

    public List<ViosVo> getVisoList() {
        return visoList;
    }

    public void setVisoList(List<ViosVo> visoList) {
        this.visoList = visoList;
    }

}
