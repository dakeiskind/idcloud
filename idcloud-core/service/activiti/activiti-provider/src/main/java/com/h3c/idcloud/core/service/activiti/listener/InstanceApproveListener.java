package com.h3c.idcloud.core.service.activiti.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.h3c.idclod.core.service.activiti.api.ActivitiService;
import com.h3c.idcloud.core.pojo.dto.system.ApproveRecord;
import com.h3c.idcloud.core.service.system.api.ApproveRecordService;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.spring.annotations.ActivitiComponent;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 实例审批记录预创建监听器
 * 在每个审批阶段自动的创建一条待审批记录
 *
 * @author NicholTong
 *
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
@ActivitiComponent
public class InstanceApproveListener implements TaskListener {

	private static final long serialVersionUID = 1L;


	@Override
	public void notify(DelegateTask delegateTask) {
		ActivitiService activitiService = SpringContextHolder.getBean("activitiServiceImpl");
		ApproveRecordService approveRecordService = activitiService.getApproveRecordDubboService();
		Map<String, Object> variables = delegateTask.getVariables();
		//从任务变量中获取processType
		String processType = StringUtil.nullToEmpty(variables.get("processType"));
		//创建一个新的审批记录对象
		ApproveRecord approveRecord = new ApproveRecord();
		approveRecord.setProcessInstanceId(delegateTask.getProcessInstanceId());
		approveRecord.setProcessType(processType);
		approveRecord.setApprovorId("");
		approveRecord.setApproveStatus("");
		approveRecord.setApprovorAction(delegateTask.getName());
		//插入待审批记录
		approveRecordService.insertSelective(approveRecord);
	}
}
