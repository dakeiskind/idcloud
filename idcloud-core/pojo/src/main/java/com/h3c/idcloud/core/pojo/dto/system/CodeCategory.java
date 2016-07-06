package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

public class CodeCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long categorySid;

    /**
     * 数据类别
     */
    private String codeCategory;

    /**
     * 数据类别名称
     */
    private String codeCategoryNm;

    /**
     * 数据是否可以编辑（0:不能编辑 1:可编辑）
     */
    private Integer state;
    
    private String stateName;

    public Long getCategorySid() {
        return categorySid;
    }

    public void setCategorySid(Long categorySid) {
        this.categorySid = categorySid;
    }

    /**
     * @return 数据类别
     */
    public String getCodeCategory() {
        return codeCategory;
    }

    /**
     * @param codeCategory 
	 *            数据类别
     */
    public void setCodeCategory(String codeCategory) {
        this.codeCategory = codeCategory;
    }

    /**
     * @return 数据类别名称
     */
    public String getCodeCategoryNm() {
        return codeCategoryNm;
    }

    /**
     * @param codeCategoryNm 
	 *            数据类别名称
     */
    public void setCodeCategoryNm(String codeCategoryNm) {
        this.codeCategoryNm = codeCategoryNm;
    }

    /**
     * @return 数据是否可以编辑（0:不能编辑 1:可编辑）
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state 
	 *            数据是否可以编辑（0:不能编辑 1:可编辑）
     */
    public void setState(Integer state) {
        this.state = state;
    }

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}