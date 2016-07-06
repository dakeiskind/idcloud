package com.h3c.idcloud.core.pojo.dto.user;

import java.io.Serializable;

/**
 * 角色模块
 */
public class RoleModule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色SID
     */
    private Long roleSid;

    /**
     * 模块SID
     */
    private String moduleSid;

    /**
     * @return 角色SID
     */
    public Long getRoleSid() {
        return roleSid;
    }

    /**
     * @param roleSid 
	 *            角色SID
     */
    public void setRoleSid(Long roleSid) {
        this.roleSid = roleSid;
    }

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
}