package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

public class CodeProvince implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 省SID
     */
    private Long provinceSid;

    /**
     * 省ID
     */
    private String provinceId;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 父节点
     */
    private String parent;

    /**
     * @return 省SID
     */
    public Long getProvinceSid() {
        return provinceSid;
    }

    /**
     * @param provinceSid 
	 *            省SID
     */
    public void setProvinceSid(Long provinceSid) {
        this.provinceSid = provinceSid;
    }

    /**
     * @return 省ID
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId 
	 *            省ID
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * @return 省名称
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * @param provinceName 
	 *            省名称
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * @return 父节点
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent 
	 *            父节点
     */
    public void setParent(String parent) {
        this.parent = parent;
    }
}