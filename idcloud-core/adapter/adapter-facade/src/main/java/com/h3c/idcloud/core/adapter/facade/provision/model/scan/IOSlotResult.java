package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IOSlotVo;

import java.util.List;

public class IOSlotResult extends CommonAdapterResult {
    private List<IOSlotVo> ioSlotList;

    public List<IOSlotVo> getIoSlotList() {
        return ioSlotList;
    }

    public void setIoSlotList(List<IOSlotVo> ioSlotList) {
        this.ioSlotList = ioSlotList;
    }

}
