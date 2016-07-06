package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;


/**
 * 组织业务关系对象
 *
 * @author ChengQi
 *
 */
public class Orgbiz extends OrgbizKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private Long level;

	private Long parentBizSid;

	private Boolean selected;
	
	private String parentBizName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getParentBizSid() {
		return parentBizSid;
	}

	public void setParentBizSid(Long parentBizSid) {
		this.parentBizSid = parentBizSid;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getParentBizName() {
		return parentBizName;
	}

	public void setParentBizName(String parentBizName) {
		this.parentBizName = parentBizName;
	}

}
