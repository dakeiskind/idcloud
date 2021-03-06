package com.h3c.idcloud.core.pojo.dto.product;

import java.io.Serializable;

public class ServiceInstRes extends ServiceInstResKey implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源类型
     */
    private String resType;

    /**
     * @return 资源类型
     */
    public String getResType() {
        return resType;
    }

    /**
     * @param resType 
	 *            资源类型
     */
    public void setResType(String resType) {
        this.resType = resType;
    }
}