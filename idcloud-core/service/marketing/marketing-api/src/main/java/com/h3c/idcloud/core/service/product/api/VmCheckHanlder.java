package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResVmCheck;

/**
 * 资源操作处理接口
 * 
 * @author Zhongshan
 *
 */
public interface VmCheckHanlder {


	/**
	 * 调用资源勘查接口
	 * 
	 * @param resVmCheck 
	 */
	void invoke(ResVmCheck resVmCheck);

	/**
	 * 资源勘查接口回调方法
	 * 
	 * @param result
	 */
	void operate(ResInstResult result);


}