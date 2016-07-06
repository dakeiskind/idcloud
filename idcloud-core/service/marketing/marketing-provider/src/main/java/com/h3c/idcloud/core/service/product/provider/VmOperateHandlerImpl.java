package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstRes;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.system.SysTLogRecord;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.product.api.MockResVmService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.VmOperateHanlder;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.SysTLogRecordService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 虚拟机操作申请回调处理
 * 
 * @author Zhongshan
 *
 */
//@Service("vmOperateHandlerImpl")
//@Service(version = "1.0.0",group = "vmOperateHandlerImpl")
//@Component("vmOperateHandlerImpl")
@Service(version = "1.0.0")
@Component
public class VmOperateHandlerImpl implements VmOperateHanlder {
	
	private static final Logger logger = LoggerFactory
			.getLogger(VmOperateHandlerImpl.class);

	/** 资源申请接口Service */
	@Reference(version = "1.0.0")
	ResVmService resVmService;
	
	/** 资源申请接口Service */
	@Autowired
	private MockResVmService mockResVmService;

//	@Reference(version = "1.0.0") -----wsl
//	private VmService vmService;

	@Autowired
	private ServiceInstResService serviceInstResService;

	@Autowired
	private ServiceInstanceService serviceInstanceService;

	@Reference(version = "1.0.0")
	private SysTLogRecordService logService;
//	@Autowired     ---------wsl
//	private SysLoggerFactory sysLogger;

	public VmOperateHandlerImpl() {
	}

	@Override
	public void operate(ResInstResult result) {
		logger.info("operateVm callback params: " + JsonUtil.toJson(result));
		try {
			if (ResInstResult.SUCCESS == result.getStatus()) {
				//根据资源id查询出对应实例id
				@SuppressWarnings("unchecked")
				Map<String, String> operateMap = (Map<String, String>) result
						.getData();

				Criteria instResCriteria = new Criteria();
				instResCriteria.put("resId", operateMap.get("resVmSid"));
				List<ServiceInstRes> serviceInstResList = this.serviceInstResService
						.selectByParams(instResCriteria);
				if (serviceInstResList != null && serviceInstResList.size() > 0) {
					Long instanceSid = serviceInstResList.get(0)
							.getInstanceSid();

					ServiceInstance serviceInstance = this.serviceInstanceService
							.selectByPrimaryKeyInstance(instanceSid);

					//判断是否为外部实例
					if (!StringUtil.isNullOrEmpty(serviceInstance
							.getoServiceId())
							&& PropertiesUtil.getProperty("idc.owner.id")
									.equals(serviceInstance.getOwnerId())) {
//						vmService.operateVmCallBack(operateMap); ----------wsl
					} else {

					}
				}

			} else {

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void invoke(String resVmSid, ServiceBaseInput baseInput, String action, String rebootType) {
		
		//系统日志
		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount());    ---------wsl
		record.setActLevel("01");		
		record.setActTarget("云主机管理");
		record.setActName("实例操作");
		record.setActStartDate(new Date());
		record.setActEndDate(new Date());
				
		try {			
			logger.info("operateVm input params: " + "resVmSid," +resVmSid + " action," + action);
			// TODO ServiceBaseInput from REST
			resVmService.operateVm(resVmSid, baseInput, action, rebootType);
			record.setActResult("02");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			record.setActResult("01");
		} finally {
			record.setActDetail("云主机管理:实例"+action);
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);  --------wsl
//			log.debug(record);
		}
	}
}
