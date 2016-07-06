package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IoVo;

import java.util.List;

public class IoScanResult extends BaseResult {
    private List<IoVo> ios;

    public List<IoVo> getIos() {
        return ios;
    }

    public void setIos(List<IoVo> ios) {
        this.ios = ios;
    }
}
