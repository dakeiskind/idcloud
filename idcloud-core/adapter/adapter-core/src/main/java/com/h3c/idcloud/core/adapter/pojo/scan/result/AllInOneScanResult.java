package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostVO;

import java.util.ArrayList;
import java.util.List;

public class AllInOneScanResult extends BaseResult {

    private List<HostVO> hosts = new ArrayList<HostVO>();

    public List<HostVO> getHosts() {
        return hosts;
    }

    public void setHosts(List<HostVO> hosts) {
        this.hosts = hosts;
    }

}
