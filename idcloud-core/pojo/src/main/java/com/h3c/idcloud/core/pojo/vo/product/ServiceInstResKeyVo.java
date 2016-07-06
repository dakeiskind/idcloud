package com.h3c.idcloud.core.pojo.vo.product;

import java.io.Serializable;

public class ServiceInstResKeyVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务实例SID
     */
    private Long instanceSid;

    /**
     * 资源SID
     */
    private String resId;

    /**
     * @return 服务实例SID
     */
    public Long getInstanceSid() {
        return instanceSid;
    }

    /**
     * @param instanceSid 
	 *            服务实例SID
     */
    public void setInstanceSid(Long instanceSid) {
        this.instanceSid = instanceSid;
    }

    /**
     * @return 资源SID
     */
    public String getResId() {
        return resId;
    }

    /**
     * @param resId 
	 *            资源SID
     */
    public void setResId(String resId) {
        this.resId = resId;
    }
}