package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.SysDiskVo;

import java.util.List;

public class ViosVo {
    private String frameHost;
    private String viosLparName;
    private String viosLparId;
    private List<SysDiskVo> disks;

    public String getFrameHost() {
        return frameHost;
    }

    public void setFrameHost(String frameHost) {
        this.frameHost = frameHost;
    }

    public String getViosLparName() {
        return viosLparName;
    }

    public void setViosLparName(String viosLparName) {
        this.viosLparName = viosLparName;
    }

    public String getViosLparId() {
        return viosLparId;
    }

    public void setViosLparId(String viosLparId) {
        this.viosLparId = viosLparId;
    }

    public List<SysDiskVo> getDisks() {
        return disks;
    }

    public void setDisks(List<SysDiskVo> disks) {
        this.disks = disks;
    }
}
