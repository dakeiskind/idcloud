package com.h3c.idcloud.core.service.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 实例请求审批完成监听器
 * 审批完成时更新订单的状态为已审批
 *
 * @author NicholTong
 *
 */
public class InstanceCompleteListener implements TaskListener {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		
	}
}
