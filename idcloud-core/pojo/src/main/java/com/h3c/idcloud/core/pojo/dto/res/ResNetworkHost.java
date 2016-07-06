package com.h3c.idcloud.core.pojo.dto.res;

import java.io.Serializable;

/**
 * Res network host 类.
 *
 * @author Chaohong.Mao
 */
public class ResNetworkHost implements Serializable {
    /**
     * 静态变量 serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Res network sid.
     */
    private String resNetworkSid;

    /**
     * The Res host sid.
     */
    private String resHostSid;

    /**
     * 获得 res network sid.
     *
     * @return the res network sid
     */
    public String getResNetworkSid() {
        return resNetworkSid;
    }

    /**
     * 设定 res network sid.
     *
     * @param resNetworkSid the res network sid
     */
    public void setResNetworkSid(String resNetworkSid) {
        this.resNetworkSid = resNetworkSid;
    }

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
}