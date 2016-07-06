package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResVmCheck;
import com.h3c.idcloud.core.service.product.api.MockResVmService;
import com.h3c.idcloud.core.service.product.api.VmCheckHanlder;
import com.h3c.idcloud.core.service.res.api.ResVmService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 虚拟机操作申请回调处理
 * 
 * @author Zhongshan
 *
 */
//@Service("vmCheckHandlerImpl")
@Service(version = "1.0.0",group = "vmCheckHandlerImpl")
@Component("vmCheckHandlerImpl")
public class VmCheckHandlerImpl implements VmCheckHanlder {
	
	private static final Logger logger = LoggerFactory
			.getLogger(VmCheckHandlerImpl.class);

	/** 资源申请接口Service */
	@Reference(version = "1.0.0")
	private ResVmService resVmService;
	
	/** 资源申请接口Service */
	@Autowired
	private MockResVmService mockResVmService;

//	@Reference(version = "1.0.0")
//	private VmService vmService;
	
	@Override
	public void operate(ResInstResult result) {
		
		
	}

	@Override
	public void invoke(ResVmCheck resVmCheck) {
		try {
//			resVmService.checkResIsEnough(resVmCheck); ----------wsl
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
