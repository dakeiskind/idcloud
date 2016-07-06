package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;

/**
 * 资源操作处理接口
 * 
 * @author Zhongshan
 *
 */
public interface VmOperateHanlder {


	/**
	 * 调用操作资源接口
	 * 
	 * @param resVmSid 申请资源对象id
	 * @param action 操作名称
	 */
	void invoke(String resVmSid, ServiceBaseInput baseInput, String action, String rebootType);

	/**
	 * 操作资源接口回调方法
	 * 
	 * @param result
	 */
	void operate(ResInstResult result);


}