package com.h3c.idcloud.core.service.activiti.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idclod.core.service.activiti.api.ActivitiService;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.system.api.ApproveRecordService;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.spring.annotations.ActivitiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 服务申请流程任务
 * 
 * @author ChengQi
 *
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
@ActivitiComponent
public class ServiceRequestTask implements JavaDelegate{

	private static Logger logger = LoggerFactory.getLogger(ServiceRequestTask.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		try {

			ActivitiService activitiService = SpringContextHolder.getBean("activitiServiceImpl");
			ApproveRecordService approveRecordService = activitiService.getApproveRecordDubboService();
			String processType = (String)execution.getVariable("processType");
			Long processObjectId = (Long)execution.getVariable("processObjectId");
			approveRecordService.executeRequestResource(processObjectId.toString(), processType);
		} catch(Exception e) {
			logger.error("start open service task failure.", e);
		}
	}

}

