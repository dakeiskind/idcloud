package com.h3c.idcloud.core.service.activiti.listener;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.h3c.idclod.core.service.activiti.api.ActivitiService;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.system.ApproveRecord;
import com.h3c.idcloud.core.pojo.dto.system.UserMgtObjKey;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.dto.user.UserRole;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.system.api.ApproveRecordService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MailTemplateService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.task.IdentityLink;
import org.activiti.spring.annotations.ActivitiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 订单审批记录预创建监听器
 * 在每个审批阶段自动的创建一条待审批记录
 *
 * @author ChengQi
 *
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
@ActivitiComponent
public class OrderApproveListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(OrderApproveListener.class);

	//订单是否可以编辑标记位
	private FixedValue orderEditable;

	public void setOrderEditable(FixedValue orderEditable) {
		this.orderEditable = orderEditable;
	}


	@Override
	public void notify(DelegateTask delegateTask) {

		ActivitiService activitiService = SpringContextHolder.getBean("activitiServiceImpl");
		OrderService orderService = activitiService.getOrderDubboService();
		MailTemplateService mailTemplateService = activitiService.getMailTemplateDubboService();
		ServiceInstanceService serviceInstanceService = activitiService.getServiceInstanceDubboService();
		MailNotificationsService mailNotificationsService = activitiService.getMailNotificationsDubboService();
		UserMgtObjService userMgtObjService = activitiService.getUserMgtObjDubboService();
		UserRoleService userRoleService = activitiService.getUserRoleDubboService();
		ApproveRecordService approveRecordService = activitiService.getApproveRecordDubboService();
		UserService userService = activitiService.getUserDubboService();

		Map<String, Object> variables = delegateTask.getVariables();
		//从任务变量中获取processType
		String processType = StringUtil.nullToEmpty(variables.get("processType"));
		//从任务变量中获取processObjectId
		Long processObjectId = (Long)variables.get("processObjectId");
		//从任务变量中获取version
		Long changeLogSid = (Long)variables.get("changeLogSid");
		//从任务变量获取订单号信息
		String orderId = (String)variables.get("orderId");
		//从任务变量获取申请人账号
		String proposeBy = (String)variables.get("proposeBy");
		//从任务变量获取申请时间
		Date proposeDt = (Date)variables.get("proposeDt");
		//创建一个新的审批记录对象
		ApproveRecord approveRecord = new ApproveRecord();
		approveRecord.setProcessInstanceId(delegateTask.getProcessInstanceId());
		approveRecord.setProcessType(processType);
		approveRecord.setProcessObjectId(processObjectId);
		approveRecord.setApprovorId("");
		approveRecord.setApproveStatus("");
		approveRecord.setApprovorAction(delegateTask.getName());
//		approveRecord.setChangeVersion(version);
		approveRecord.setProposeBy(proposeBy);
		approveRecord.setProposeDt(proposeDt);
		approveRecord.setProcessTargetId(changeLogSid);
		WebUtil.prepareInsertParams(approveRecord);
		//插入待审批记录
		approveRecordService.insertSelective(approveRecord);

		//发送审批通知邮件
		List<UserRole> userRole = new ArrayList<>();
		List<UserRole> userRolelist = new ArrayList<UserRole>();
		try {
			Set<IdentityLink> candidates = delegateTask.getCandidates();
			List<String> roles = new ArrayList<String>();
			for(IdentityLink identityLink : candidates) {
				roles.add(identityLink.getGroupId());
			}
			Criteria criteria = new Criteria();
			criteria.put("orderId", orderId);
			List<ServiceInstance> serviceInstances = serviceInstanceService.selectByParams(criteria);
			criteria = new Criteria();
			criteria.put("mgtObjSid", serviceInstances.get(0).getMgtObjSid());
			List<UserMgtObjKey> userMgtObjKeys = userMgtObjService.selectByParams(criteria);
			for(int i=0;i<userMgtObjKeys.size();i++){
				criteria = new Criteria();
				criteria.put("userSid",userMgtObjKeys.get(i).getUserSid());
				userRole = userRoleService.selectByParams(criteria);

				for(int j=0;j<userRole.size();j++){
					if("205".equals(userRole.get(j).getRoleSid().toString())){
						userRolelist.add(userRole.get(j));
					}
				}
			}

			if(roles.size() > 0) {
				List<String> toAddressList = new ArrayList<String>();
				if("205".equals(roles.get(0))){
					for(int i=0;i<userRolelist.size();i++){
						if("205".equals(userRolelist.get(0).getRoleSid().toString())){
							criteria = new Criteria();
							criteria.put("userSid",userRolelist.get(i).getUserSid());
							List<User>user = userService.selectByParams(criteria);
							toAddressList.add(user.get(0).getEmail());
						}
					}
				}else{
					List<User> users = userService.selectUserByRoles(StringUtils.join(roles, ","));
					if(users.size() == 0) {
						logger.warn("no user need to send nofity.");
						return;
					}

					for(User user : users) {
						toAddressList.add(user.getEmail());
					}
				}
				if(WebConstants.ProcessType.VM_OPEN.equals(processType)) {
					mailNotificationsService.unapproveInfoNotice(orderId, toAddressList);
				} else if(WebConstants.ProcessType.INSTANCE_ADJUST.equals(processType)) {
					mailNotificationsService.changeUnapproveInfoNotice(processObjectId, toAddressList);
				} else if(WebConstants.ProcessType.VM_CANCEL.equals(processType)) {
					mailNotificationsService.unsubscribeInfoNotice(processObjectId, toAddressList);
				}
			}
		} catch(Exception e) {
			logger.error("send email to notify approve failure.", e);
		}
	}

}
