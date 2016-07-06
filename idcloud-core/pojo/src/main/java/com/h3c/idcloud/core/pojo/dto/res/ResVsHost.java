package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

/**
 * Res vs host 类.
 *
 * @author Chaohong.Mao
 */
public class ResVsHost implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Res host sid.
     */
    private String resHostSid;

    /**
     * The Res vs sid.
     */
    private String resVsSid;

    /**
     * The Res vs name.
     */
    private String resVsName;

    /**
     * The Vlan id.
     */
    private String vlanId;

    /**
     * 获得 res host sid.
     *
     * @return the res host sid
     */
    public String getResHostSid() {
        return resHostSid;
    }

    /**
     * 设定 res host sid.
     *
     * @param resHostSid the res host sid
     */
    public void setResHostSid(String resHostSid) {
        this.resHostSid = resHostSid;
    }

    /**
     * 获得 res vs sid.
     *
     * @return the res vs sid
     */
    public String getResVsSid() {
        return resVsSid;
    }

    /**
     * 设定 res vs sid.
     *
     * @param resVsSid the res vs sid
     */
    public void setResVsSid(String resVsSid) {
        this.resVsSid = resVsSid;
    }

    /**
     * 获得 res vs name.
     *
     * @return the resVsName
     */
    public String getResVsName() {
        return resVsName;
    }

    /**
     * 设定 res vs name.
     *
     * @param resVsName the resVsName to set
     */
    public void setResVsName(String resVsName) {
        this.resVsName = resVsName;
    }

    /**
     * 获得 vlan id.
     *
     * @return the vlanId
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * 设定 vlan id.
     *
     * @param vlanId the vlanId to set
     */
    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

}