package com.h3c.idcloud.infrastructure.common.util;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Activiti共通Util
 *
 * @author wxb
 */
//@Service
public class ActivitiWorkflowUtil {

	private static Logger logger = LoggerFactory.getLogger(ActivitiWorkflowUtil.class);
	private IdentityService identityService = SpringContextHolder.getBean("identityService");
	private RuntimeService runtimeService = SpringContextHolder.getBean("runtimeService");
	private RepositoryService repositoryService = SpringContextHolder.getBean("repositoryService");
	private TaskService taskService = SpringContextHolder.getBean("taskService");
	private HistoryService historyService = SpringContextHolder.getBean("historyService");
	private ManagementService managementService = SpringContextHolder.getBean("managementService");

	/**
	 * 启动订单流程
	 *
	 * @param variables
	 */
	public ProcessInstance startWorkflow(Map<String, Object> variables, String activitiFlowId) {
		try{
			if(AuthUtil.getAuthUser() != null) {
				identityService.setAuthenticatedUserId(AuthUtil.getAuthUser().getAccount());
			} else {
				identityService.setAuthenticatedUserId("system");
			}
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(activitiFlowId, variables);
			logger.info("流程已启动，流程实例ID：" + processInstance.getId());

			return processInstance;
		}catch (ActivitiException e){
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				logger.warn("没有部署流程!", e);
			} else {
				logger.error("启动订单流程失败：", e);
			}
		} catch (Exception e) {
			logger.error("启动订单流程失败：", e);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return null;
	}

	/**
	 * 加载流程定义文件
	 * 
	 * @param
	 */
	public void deploymentInstance() {
		// 从文件部署流程
//		Deployment deployment = repositoryService.createDeployment()
//				.addClasspathResource("com/hptsic/cloud/diagrams/OrderApprvoeProcess.bpmn").deploy();
//		logger.info("流程文件已加载");
	}

	/**
	 * 查询组当前任务
	 * 
	 * @param candidateGroup
	 */
	public List<Task> queryUserGroupTask(String candidateGroup) {
		
		// 查询任务列表使用组名称
//		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(candidateGroup).list();
//
//		return tasks;
		return null;
	}
	
	/**
	 * 查询当前用户的所有任务
	 * 
	 * @param assignee
	 */
	public List<Task> queryUserAllTask(String assignee, String createdBy) {
//		List<Group> groupList = identityService.createNativeGroupQuery().sql("select g.ID_ from ACT_ID_GROUP g, ACT_ID_MEMBERSHIP membership " +
//				"where g.ID_ = membership.GROUP_ID_ and membership.USER_ID_ = '"+ assignee+"'")
//		.list();
//		List<String> groupidList = new ArrayList<String>();
//		for(Group group : groupList) {
//			groupidList.add(group.getId());
//		}
//		String sql = "select distinct RES.* from ACT_RU_TASK RES " +
//				"inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ " +
//				"left join ACT_HI_PROCINST J on J.PROC_INST_ID_ = RES.PROC_INST_ID_ " +
//				"WHERE RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' and ( I.USER_ID_ = '"+assignee+"' or I.GROUP_ID_ IN ( " + StringUtils.join(groupidList, ",") + " ) )";
//
//		if(StringUtils.isNotBlank(createdBy)) {
//			sql += " and J.START_USER_ID_ like '%"+createdBy+"%'";
//		}
//		sql += "  order by RES.ID_ asc";
//		System.out.println(sql);
//		NativeTaskQuery taskQuery = taskService.createNativeTaskQuery();
//		List<Task> tasks = taskQuery.sql(sql).list();
//		return tasks;
		return null;
	}

	/**
	 * 查询组历史已审批任务
	 * 
	 * @param
	 */
	public List<HistoricTaskInstance> queryUserGroupHistoryTask() {

		// 查询任务列表使用组名称
//		List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
//				.taskDefinitionKey("OrderApprove").finished().list();
//
//		return tasks;
		return null;
	}
	
	/**
	 * 查询个人用户任务
	 * 
	 * @param assignee
	 */
	public List<Task> queryPersonalTaskList(String assignee) {

		// 查询任务列表使用组名称
		// List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
//		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(assignee).list();
//
//		return tasks;
		return null;
	}

	/**
	 * 查询流程实例任务
	 * 
	 * @param processInstanceId
	 */
	public List<Task> queryProcessInstanceTask(String processInstanceId) {

		// 查询任务列表使用组名称
//		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
//
//		return tasks;
		return null;
	}

	/**
	 * 查询流程实例历史任务
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 */
	public List<HistoricTaskInstance> queryProcessInstanceHistoryTask(String processInstanceId) {

//		List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
//				.processInstanceId(processInstanceId).list();
//
//		return tasks;
		return null;
	}

	/**
	 * 根据流程实例ID查询流程定义对象{@link ProcessDefinition}
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return 流程定义对象{@link ProcessDefinition}
	 */
	public ProcessDefinition findProcessDefinitionByPid(String processInstanceId) {
		HistoryService historyService = SpringContextHolder.getBean("historyService");
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
		ProcessDefinition processDefinition = findProcessDefinition(processDefinitionId);
		return processDefinition;
	}

	/**
	 * 根据流程定义ID查询流程定义对象{@link ProcessDefinition}
	 * 
	 * @param processDefinitionId
	 *            流程定义对象ID
	 * @return 流程定义对象{@link ProcessDefinition}
	 */
	public ProcessDefinition findProcessDefinition(String processDefinitionId) {
		RepositoryService repositoryService = SpringContextHolder.getBean("repositoryService");
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	/**
	 * 获取 未 经完成的流程实例查询对象(从表ACT_RU_EXECUTION中查询数据)
	 * 
	 * @param processInstanceId
	 *            用户ID
	 */
	public ProcessInstanceQuery createUnFinishedProcessInstanceQuery(String processInstanceId) {
		RuntimeService runtimeService = SpringContextHolder.getBean("runtimeService");
		// String processDefinitionId = findProcessDefinitionByPid(processInstanceId).getId();
		ProcessInstanceQuery unfinishedQuery = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).active();
		return unfinishedQuery;
	}

	/**
	 * 获取 已 经完成的流程实例查询对象（从表ACT_HI_PROCINST中查询数据）
	 * 
	 * @param processInstanceId
	 *            用户ID
	 */
	public HistoricProcessInstanceQuery createFinishedProcessInstanceQuery(String processInstanceId) {
		HistoryService historyService = SpringContextHolder.getBean("historyService");

		// String processDefinitionId = findProcessDefinitionByPid(processInstanceId).getId();
		HistoricProcessInstanceQuery finishedQuery = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).finished();
		logger.info("该实例完成条数:" + finishedQuery.count());
		return finishedQuery;
	}

	/**
	 * 读取资源
	 * 
	 * @return
	 */
	public InputStream loadResource(String processInstanceId) {
		try {
			RuntimeService runtimeService = SpringContextHolder.getBean("runtimeService");
			RepositoryService repositoryService = SpringContextHolder.getBean("repositoryService");
			HistoryService historyService = SpringContextHolder.getBean("historyService");

			InputStream resourceAsStream = null;
			OutputStream outputStream = null;

			if (!StringUtil.isNullOrEmpty(processInstanceId)) {
//				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//						.processInstanceId(processInstanceId).singleResult();
				HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
						.processInstanceId(processInstanceId).singleResult();
				ProcessDefinition singleResult = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult();
				String resourceName = "";
				// if (resourceType.equals("image")) {
				resourceName = singleResult.getDiagramResourceName();
				// } else if (resourceType.equals("xml")) {
				// resourceName = singleResult.getResourceName();
				// }
				resourceAsStream = repositoryService.getResourceAsStream(singleResult.getDeploymentId(), resourceName);
			} else {
				// resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
				resourceAsStream = repositoryService.getResourceAsStream("", "");
			}

			return resourceAsStream;
			// byte[] b = new byte[1024];
			// int len = -1;
			// while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			//
			// //Struts2Utils.getResponse().getOutputStream().write(b, 0, len);
			// outputStream.write(b, 0, len);
			// }
		} catch (Exception e) {
			logger.error("读取资源出错：", e);
		}
		return null;
	}

	/**
	 * 流程跟踪图
	 * 
	 * @return
	 */
	public Map<String, Object> traceProcess(String processInstanceId) {
		try {
			ActivityImpl activity = getCurActivity(processInstanceId);
			if(activity == null) return null;
				Map<String, Object> activityImageInfo = new HashMap<String, Object>();
				// activityImageInfo.put("x", activity.getX() - 210);
				// activityImageInfo.put("y", activity.getY() - 68);
//				activityImageInfo.put("x", activity.getX() - 50);
//				activityImageInfo.put("y", activity.getY() - 25);
				activityImageInfo.put("x", activity.getX());
				activityImageInfo.put("y", activity.getY());
				activityImageInfo.put("width", activity.getWidth());
				activityImageInfo.put("height", activity.getHeight());
				return activityImageInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取当前流程节点对象
	 * @param processInstanceId
	 * @return
	 */
	public ActivityImpl getCurActivity(String processInstanceId) {
		RuntimeService runtimeService = SpringContextHolder.getBean("runtimeService");
		RepositoryService repositoryService = SpringContextHolder.getBean("repositoryService");

		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例
		if(execution == null) return null;
		String activityId = execution.getActivityId();

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点
		ActivityImpl activity = null;
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (id.equals(activityId)) {// 获得执行到那个节点
				activity = activityImpl;
				break;
			}
		}
		return activity;
	}
	
	/**
	 * @param processInstanceId
	 * @return
	 */
	public Boolean isFinal(String processInstanceId) {
		String finalFlag = "final";
		try {
			ActivityImpl activityImpl = getCurActivity(processInstanceId);
			if(activityImpl == null) return false;
			String activitiId = activityImpl.getId();
			if(activitiId.endsWith(finalFlag)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	//清除运行错误时的job，不重复执行
	public void cleanActivitiJob(String processInstanceId) {
		
// 		Job job = managementService.createJobQuery().processInstanceId(processInstanceId).singleResult();
// 		if (job.getId() != null) {
//   	 		managementService.setJobRetries(job.getId(), 0);
// 		}
	}
}
