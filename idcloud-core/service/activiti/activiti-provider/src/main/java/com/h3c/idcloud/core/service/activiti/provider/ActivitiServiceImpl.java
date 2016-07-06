package com.h3c.idcloud.core.service.activiti.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idclod.core.service.activiti.api.ActivitiService;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.system.api.ApproveRecordService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MailTemplateService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;

import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.FileUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swq on 2/24/2016.
 */
@Service(version = "1.0.0")
public class ActivitiServiceImpl implements ActivitiService {

    @Reference(version = "1.0.0")
    private ApproveRecordService approveRecordService;

    @Reference(version = "1.0.0")
    private UserService userService;

    @Reference(version = "1.0.0")
    private OrderService orderService;

    @Reference(version = "1.0.0")
    private MailTemplateService mailTemplateService;

    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;

    @Reference(version = "1.0.0")
    private MailNotificationsService mailNotificationsService;

    @Reference(version = "1.0.0")
    private UserMgtObjService userMgtObjService;

    @Reference(version = "1.0.0")
    private UserRoleService userRoleService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ManagementService managementService;

    @Override
    public void claim(String v1, String v2) {
        taskService.claim(v1, v2);
    }

    @Override
    public void complete(String takId) {
        taskService.complete(takId);
    }

    private static Logger logger = LoggerFactory.getLogger(ActivitiServiceImpl.class);

    @Override
    public String startWorkflow(Map<String, Object> variables, String activitiFlowId) {
        try{
            if(AuthUtil.getAuthUser() != null) {
                identityService.setAuthenticatedUserId(AuthUtil.getAuthUser().getAccount());
            } else {
                identityService.setAuthenticatedUserId("system");
            }
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(activitiFlowId, variables);
            logger.info("流程已启动，流程实例ID：" + processInstance.getId());
            return processInstance.getProcessInstanceId();
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

    @Override
    public InputStream loadResource(String processInstanceId) {
        try {
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

    @Override
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

    @Override
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

    @Override
    public Map<String, Object> getActivityImageInfo(String processInstanceId) throws IOException {
        Map<String, Object> activityImageInfo = this.traceProcess(processInstanceId);
        InputStream resourceAsStream = this.loadResource(processInstanceId);
        if(activityImageInfo != null) {
            activityImageInfo.putAll(FileUtil.getImageSizeByBufferedImage(resourceAsStream));
        }
        return activityImageInfo;
    }

    /**
     * 获取当前流程节点对象
     * @param processInstanceId
     * @return
     */
    public ActivityImpl getCurActivity(String processInstanceId) {
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

    @Override
    public void claimAndCompleteTasks(String processInstanceID, String userID) {

       ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceID).singleResult();
        Map<String,Object> param = processInstance.getProcessVariables();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceID).list();
//        ExecutionEntity entity = (ExecutionEntity)processInstance;
//        List<TaskEntity> tasks2 = entity.getTasks();
//        int tasksSize = tasks.size();
        for(Task task : tasks) {
            //认领任务
            System.out.println("taskId=" + task.getId());
			taskService.claim(task.getId(), userID);
            //完成任务
			taskService.complete(task.getId());
        }
    }

    @Override
    public UserService getUserDubboService() {
        return this.userService;
    }

    @Override
    public ApproveRecordService getApproveRecordDubboService() {
        return this.approveRecordService;
    }

    @Override
    public OrderService getOrderDubboService() {
        return this.orderService;
    }

    @Override
    public MailTemplateService getMailTemplateDubboService() {
        return this.mailTemplateService;
    }

    @Override
    public ServiceInstanceService getServiceInstanceDubboService() {
        return this.serviceInstanceService;
    }

    @Override
    public MailNotificationsService getMailNotificationsDubboService() {
        return this.mailNotificationsService;
    }

    @Override
    public UserMgtObjService getUserMgtObjDubboService() {
        return this.userMgtObjService;
    }

    @Override
    public UserRoleService getUserRoleDubboService() {
        return this.userRoleService;
    }
}

