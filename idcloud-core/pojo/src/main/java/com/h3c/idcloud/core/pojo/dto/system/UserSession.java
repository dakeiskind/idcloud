/**
 * 
 */
package com.h3c.idcloud.core.pojo.dto.system;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.h3c.idcloud.core.pojo.dto.user.User;
import org.springframework.stereotype.Service;

/**
 * @author zharong
 *
 */
public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the ssoToken
	 */
	public String getSsoToken() {
		return ssoToken;
	}
	/**
	 * @param ssoToken the ssoToken to set
	 */
	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}
	/**
	 * @return the creationTime
	 */
	public Long getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	private String id;
	private String ssoToken;
	private Long creationTime;
	private User user;
}