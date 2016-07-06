package com.h3c.idcloud.core.service.product.api;

/**
 * vcenter中资源信息变更同步到服务层和天馈
 * 
 * @author ChengQi
 *
 */
public interface ResInfoSync {

	/**
	 * 更新资源层信息（暂时只调用天馈接口）
	 * @param resVmSid
	 */
	public void updateResVmInfo(String resVmSid, String operate);

}
