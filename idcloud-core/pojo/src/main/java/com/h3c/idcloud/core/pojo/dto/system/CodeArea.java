package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

public class CodeArea implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 区SID
     */
    private Long areaSid;

    /**
     * 区ID
     */
    private String areaId;

    /**
     * 区名称
     */
    private String areaName;

    /**
     * 城市SID
     */
    private String cityId;

    /**
     * @return 区SID
     */
    public Long getAreaSid() {
        return areaSid;
    }

    /**
     * @param areaSid 
	 *            区SID
     */
    public void setAreaSid(Long areaSid) {
        this.areaSid = areaSid;
    }

    /**
     * @return 区ID
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId 
	 *            区ID
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * @return 区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName 
	 *            区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
}