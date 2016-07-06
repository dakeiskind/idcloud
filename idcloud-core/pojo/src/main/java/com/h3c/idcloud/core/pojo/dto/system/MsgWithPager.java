/**
 * 
 */
package com.h3c.idcloud.core.pojo.dto.system;

import com.h3c.idcloud.infrastructure.common.pojo.BasePager;


/**
 * @author 吴冲
 * 
 * @since 2016-2-20
 */
public class MsgWithPager {
	BasePager pager;
	Message message;

	/**
	 * @return the pager
	 */
	public BasePager getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(BasePager pager) {
		this.pager = pager;
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}
}
