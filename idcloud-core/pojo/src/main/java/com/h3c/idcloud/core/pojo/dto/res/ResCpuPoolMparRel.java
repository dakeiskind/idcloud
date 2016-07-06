package com.h3c.idcloud.core.pojo.dto.res;


import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.MparVO;

import java.io.Serializable;

/**
 * Res cpu pool mpar rel 类.
 *
 * @author Chaohong.Mao
 */
public class ResCpuPoolMparRel implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Res cpu pool sid.
     */
    private String resCpuPoolSid;

    /**
     * 资源实例ID
     */
    private String resParSid;

    /**
     * VIOS----0<br>
     * VIOC----1
     */
    private Integer resParType;

    /**
     * The Cpu value.
     */
    private Float cpuValue;

    /**
     * 构造 Res cpu pool mpar rel 的实例.
     */
    public ResCpuPoolMparRel() {

    }

    /**
     * 构造 Res cpu pool mpar rel 的实例.
     *
     * @param mparVO the mpar vo
     */
    public ResCpuPoolMparRel(MparVO mparVO) {
        // VIOS----0 VIOC----1
        if ("vioserver".equals(mparVO.getLparEnv())) {
            this.resParType = 0;
        } else {
            this.resParType = 1;
        }

        this.cpuValue = mparVO.getDesiredProcUnits();
    }

    /**
     * 获得 res cpu pool sid.
     *
     * @return the res cpu pool sid
     */
    public String getResCpuPoolSid() {
        return resCpuPoolSid;
    }

    /**
     * 设定 res cpu pool sid.
     *
     * @param resCpuPoolSid the res cpu pool sid
     */
    public void setResCpuPoolSid(String resCpuPoolSid) {
        this.resCpuPoolSid = resCpuPoolSid;
    }

    /**
     * 获得 res par sid.
     *
     * @return 资源实例ID res par sid
     */
    public String getResParSid() {
        return resParSid;
    }

    /**
     * 设定 res par sid.
     *
     * @param resParSid 资源实例ID
     */
    public void setResParSid(String resParSid) {
        this.resParSid = resParSid;
    }

    /**
     * 获得 res par type.
     *
     * @return VIOS ----0<br>                     VIOC----1
     */
    public Integer getResParType() {
        return resParType;
    }

    /**
     * 设定 res par type.
     *
     * @param resParType VIOS----0<br>                        VIOC----1
     */
    public void setResParType(Integer resParType) {
        this.resParType = resParType;
    }

    /**
     * 获得 cpu value.
     *
     * @return the cpu value
     */
    public Float getCpuValue() {
        return cpuValue;
    }

    /**
     * 设定 cpu value.
     *
     * @param cpuValue the cpu value
     */
    public void setCpuValue(Float cpuValue) {
        this.cpuValue = cpuValue;
    }
}