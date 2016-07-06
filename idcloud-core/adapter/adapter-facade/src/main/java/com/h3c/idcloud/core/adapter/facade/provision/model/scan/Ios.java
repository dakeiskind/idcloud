package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IoVo;

import java.util.List;

public class Ios extends CommonAdapterResult {
    private List<IoVo> ios;

    public List<IoVo> getIos() {
        return ios;
    }

    public void setIos(List<IoVo> ios) {
        this.ios = ios;
    }

}
