package com.h3c.idcloud.core.pojo.dto.res;

import com.google.common.base.Strings;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.CpuPoolVo;

import java.io.Serializable;

/**
 * Res cpu pool 类.
 *
 * @author Chaohong.Mao
 */
public class ResCpuPool implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Res cpu pool sid.
     */
    private String resCpuPoolSid;

    /**
     * 资源ID
     */
    private String resHostSid;

    /**
     * The Cpu pool name.
     */
    private String cpuPoolName;

    /**
     * The Reserved value.
     */
    private Float reservedValue;

    /**
     * The Max value.
     */
    private Float maxValue;

    /**
     * The Cpu pool id.
     */
    private int cpuPoolId;

    /**
     * The Uuid.
     */
    private String uuid;

    /**
     * The Total used cpu value.
     */
    private float totalUsedCpuValue;

    /**
     * 构造 Res cpu pool 的实例.
     */
    public ResCpuPool() {

    }

    /**
     * 转换MQ对象为平台对象
     *
     * @param cpuPoolVo the cpu pool vo
     */
    public ResCpuPool(CpuPoolVo cpuPoolVo) {
        this.uuid = cpuPoolVo.getUuid();
        this.cpuPoolName = cpuPoolVo.getCpuPoolName();
        if (!Strings.isNullOrEmpty(cpuPoolVo.getMaxPoolProcUnits())) {
            this.maxValue = Float.parseFloat(cpuPoolVo.getMaxPoolProcUnits());
        }
        if (!Strings.isNullOrEmpty(cpuPoolVo.getCurrOeservedPoolProcUnits())) {
            this.reservedValue = Float.parseFloat(cpuPoolVo.getCurrOeservedPoolProcUnits());
        }
        this.cpuPoolId = cpuPoolVo.getShareProcPoolId();
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
     * 获得 res host sid.
     *
     * @return 资源ID res host sid
     */
    public String getResHostSid() {
        return resHostSid;
    }

    /**
     * 设定 res host sid.
     *
     * @param resHostSid 资源ID
     */
    public void setResHostSid(String resHostSid) {
        this.resHostSid = resHostSid;
    }

    /**
     * 获得 cpu pool name.
     *
     * @return the cpu pool name
     */
    public String getCpuPoolName() {
        return cpuPoolName;
    }

    /**
     * 设定 cpu pool name.
     *
     * @param cpuPoolName the cpu pool name
     */
    public void setCpuPoolName(String cpuPoolName) {
        this.cpuPoolName = cpuPoolName;
    }

    /**
     * 获得 reserved value.
     *
     * @return the reserved value
     */
    public Float getReservedValue() {
        return reservedValue;
    }

    /**
     * 设定 reserved value.
     *
     * @param reservedValue the reserved value
     */
    public void setReservedValue(Float reservedValue) {
        this.reservedValue = reservedValue;
    }

    /**
     * 获得 max value.
     *
     * @return the max value
     */
    public Float getMaxValue() {
        return maxValue;
    }

    /**
     * 设定 max value.
     *
     * @param maxValue the max value
     */
    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 获得 cpu pool id.
     *
     * @return the cpu pool id
     */
    public int getCpuPoolId() {
        return cpuPoolId;
    }

    /**
     * 设定 cpu pool id.
     *
     * @param cpuPoolId the cpu pool id
     */
    public void setCpuPoolId(int cpuPoolId) {
        this.cpuPoolId = cpuPoolId;
    }

    /**
     * 获得 uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设定 uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获得 total used cpu value.
     *
     * @return the total used cpu value
     */
    public float getTotalUsedCpuValue() {
        return totalUsedCpuValue;
    }

    /**
     * 设定 total used cpu value.
     *
     * @param totalUsedCpuValue the total used cpu value
     */
    public void setTotalUsedCpuValue(float totalUsedCpuValue) {
        this.totalUsedCpuValue = totalUsedCpuValue;
    }
}