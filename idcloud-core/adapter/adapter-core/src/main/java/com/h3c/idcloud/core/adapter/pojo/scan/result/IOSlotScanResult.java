package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IOSlotVo;

import java.util.List;

public class IOSlotScanResult extends BaseResult {
    private List<IOSlotVo> ioSlotVos;

    public List<IOSlotVo> getIoSlotVos() {
        return ioSlotVos;
    }

    public void setIoSlotVos(List<IOSlotVo> ioSlotVos) {
        this.ioSlotVos = ioSlotVos;
    }
}
