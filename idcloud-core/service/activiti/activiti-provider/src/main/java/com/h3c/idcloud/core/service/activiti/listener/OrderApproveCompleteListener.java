package com.h3c.idcloud.core.service.activiti.listener;

import com.h3c.idclod.core.service.activiti.api.ActivitiService;
import com.h3c.idcloud.core.pojo.dto.user.User;

import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.activiti.spring.annotations.ActivitiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 订购操作完成监听器
 *
 * @author ChengQi
 *
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
@ActivitiComponent
public class OrderApproveCompleteListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(OrderApproveCompleteListener.class);

	@Override
	public void notify(DelegateTask delegateTask) {

		ActivitiService activitiService = SpringContextHolder.getBean("activitiServiceImpl");
	
		Map<String, Object> variables = delegateTask.getVariables();
		//从任务变量获取申请人账号
		String proposeBy = (String)variables.get("proposeBy");
		
		Set<IdentityLink> candidates = delegateTask.getCandidates();
		List<String> roles = new ArrayList<String>();
		for(IdentityLink identityLink : candidates) {
			roles.add(identityLink.getGroupId());
		}
		//如果是项目经理审核
		Criteria criteria = new Criteria();
		criteria.put("account", proposeBy);
//		List<User> userList = userService.selectByParams(criteria);
		UserService userService = activitiService.getUserDubboService();
		List<User> userList = userService.selectByParams(criteria);
		boolean isProjectManager = false;
		if(userList.size() > 0 ) {
			isProjectManager = activitiService.getApproveRecordDubboService().isResourceMgtRole(userList.get(0));
		}
		//如果申请人是项目经理，则不进行项目经理审核步骤
		if(isProjectManager) {
			delegateTask.setVariable("approveResouceMgt", false);
		}

	}

}
