package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.UuidVO;

import java.util.ArrayList;
import java.util.List;

public class HostScanBySvsResult extends BaseResult {

    private List<UuidVO> uuids = new ArrayList<UuidVO>();

    public List<UuidVO> getUuids() {
        return uuids;
    }

    public void setUuids(List<UuidVO> uuids) {
        this.uuids = uuids;
    }

}
