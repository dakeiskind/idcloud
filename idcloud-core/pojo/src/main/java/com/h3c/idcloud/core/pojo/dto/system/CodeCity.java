package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

public class CodeCity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 城市SID
     */
    private Long citySid;

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 省SID
     */
    private String provinceId;

    /**
     * @return 城市SID
     */
    public Long getCitySid() {
        return citySid;
    }

    /**
     * @param citySid 
	 *            城市SID
     */
    public void setCitySid(Long citySid) {
        this.citySid = citySid;
    }

    /**
     * @return 城市ID
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * @param cityId 
	 *            城市ID
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * @return 城市名称
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName 
	 *            城市名称
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

}