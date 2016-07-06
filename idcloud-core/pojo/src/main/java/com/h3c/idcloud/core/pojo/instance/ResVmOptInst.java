package com.h3c.idcloud.core.pojo.instance;

import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;

import java.io.Serializable;
import java.util.List;

/**
 * 虚拟机操作Bean
 *
 * @author Chaohong.Mao
 */
public class ResVmOptInst extends ServiceBaseInput implements Serializable {
    /** VM Sid */
    private List<String> resVmSids;
    /** 虚拟机操作 */
    private String opt;

    public List<String> getResVmSids() {
        return resVmSids;
    }

    public void setResVmSids(List<String> resVmSids) {
        this.resVmSids = resVmSids;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}
