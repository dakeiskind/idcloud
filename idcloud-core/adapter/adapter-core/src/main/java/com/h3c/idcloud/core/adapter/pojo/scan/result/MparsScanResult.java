package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.MparVO;

import java.util.List;

public class MparsScanResult extends BaseResult {
    private List<MparVO> vms;

    public List<MparVO> getVms() {
        return vms;
    }

    public void setVms(List<MparVO> vms) {
        this.vms = vms;
    }

}
