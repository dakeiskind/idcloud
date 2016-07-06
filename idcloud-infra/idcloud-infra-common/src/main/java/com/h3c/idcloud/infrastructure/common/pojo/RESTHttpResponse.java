/**
 * 
 */
package com.h3c.idcloud.infrastructure.common.pojo;

import org.apache.http.HttpStatus;

/**
 * REST返回对象
 *
 * @author 张荣
 */
public class RESTHttpResponse {

	/**
	 * 静态变量 SUCCESS.
	 */
	public static final String SUCCESS= "success";
	/**
	 * 静态变量 ERROR.
	 */
	public static final String ERROR= "error";

	/**
	 * 返回状态.
	 * 成功"success"
	 * 失败:"error"
	 */
	private String status;
	/**
	 * 返回内容.
	 */
	private String content;

	/**
	 * 获得 status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设定 status.
	 *
	 * @param statusCode the status code
	 */
	public void setStatus(int statusCode) {
		if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
			this.status = SUCCESS;
		} else {
			this.status = ERROR;
		}
	}

	/**
	 * 获得 content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设定 content.
	 *
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
