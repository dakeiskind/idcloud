package com.h3c.idcloud.core.pojo.dto.user;

import java.io.Serializable;

/**
 * 权限管理
 * @author zharong
 */
public class Module implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 模块SID
     */
    private String moduleSid;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块URL
     */
    private String moduleUrl;

    /**
     * 父模块SID
     */
    private String parentSid;

    /**
     * 模块类型(0:菜单; 1:功能)
     */
    private Integer moduleType;

    /**
     * 是否显示 0:县 1:斿
     */
    private Integer displayFlag;

    /**
     * 显示顺序
     */
    private Integer sortRank;
    
    /**
     * 模块图片url
     */
    private  String moduleIconUrl;
    
    /**
     * 模块功能描述
     */
    private  String description;
    
    /**
     * 是否选中
     */
    private boolean selected;

    /**
     * @return 模块SID
     */
    public String getModuleSid() {
        return moduleSid;
    }

    /**
     * @param moduleSid 
	 *            模块SID
     */
    public void setModuleSid(String moduleSid) {
        this.moduleSid = moduleSid;
    }

    /**
     * @return 模块名称
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName 
	 *            模块名称
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return 模块URL
     */
    public String getModuleUrl() {
        return moduleUrl;
    }

    /**
     * @param moduleUrl 
	 *            模块URL
     */
    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    /**
     * @return 父模块SID
     */
    public String getParentSid() {
        return parentSid;
    }

    /**
     * @param parentSid 
	 *            父模块SID
     */
    public void setParentSid(String parentSid) {
        this.parentSid = parentSid;
    }

    /**
     * @return 模块类型(0:菜单; 1:功能)
     */
    public Integer getModuleType() {
        return moduleType;
    }

    /**
     * @param moduleType 
	 *            模块类型(0:菜单; 1:功能)
     */
    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    /**
     * @return 是否显示 0:县 1:斿
     */
    public Integer getDisplayFlag() {
        return displayFlag;
    }

    /**
     * @param displayFlag 
	 *            是否显示 0:县 1:斿
     */
    public void setDisplayFlag(Integer displayFlag) {
        this.displayFlag = displayFlag;
    }

    /**
     * @return 显示顺序
     */
    public Integer getSortRank() {
        return sortRank;
    }

    /**
     * @param sortRank 
	 *            显示顺序
     */
    public void setSortRank(Integer sortRank) {
        this.sortRank = sortRank;
    }

	public String getModuleIconUrl() {
		return moduleIconUrl;
	}

	public void setModuleIconUrl(String moduleIconUrl) {
		this.moduleIconUrl = moduleIconUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}