package com.h3c.idcloud.core.pojo.dto.user;

import java.io.Serializable;

/**
 * 用户角色
 */
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户SID
     */
    private Long userSid;

    /**
     * 角色SID
     */
    private Long roleSid;

    /**
     * @return 用户SID
     */
    public Long getUserSid() {
        return userSid;
    }

    /**
     * @param userSid 
	 *            用户SID
     */
    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

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
}