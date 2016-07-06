package com.h3c.idclod.core.service.activiti.api;

import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.system.api.ApproveRecordService;
import com.h3c.idcloud.core.service.system.api.MailNotificationsService;
import com.h3c.idcloud.core.service.system.api.MailTemplateService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.core.service.user.api.UserRoleService;
import com.h3c.idcloud.core.service.user.api.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by swq on 2/24/2016.
 */
public interface ActivitiService {


    /**
     * 启动流程并返回processid
     * @param variables
     * @param activitiFlowId
     * @return
     */
    public String startWorkflow(Map<String, Object> variables, String activitiFlowId);


    /**
     * 读取资源
     * @param processInstanceId
     * @return
     */
    public InputStream loadResource(String processInstanceId);


    /**
     * 流程跟踪图
     * @param processInstanceId
     * @return
     */
    public Map<String, Object> traceProcess(String processInstanceId);

    /**
     * 根据流程id获取流程图map
     * @param processInstanceId
     * @return
     */
    public Map<String, Object> getActivityImageInfo(String processInstanceId) throws IOException;


    /**
     * 流程是否已经结束
     * @param processInstanceId
     * @return
     */
    public Boolean isFinal(String processInstanceId);

    /**
     * 认领并完成实例任务
     * @param processInstanceID
     * @param userID
     */
    public void claimAndCompleteTasks(String processInstanceID,String userID);

    /**
     * 认领任务
     * @param v1
     * @param v2
     */
    public void claim(String v1,String v2);

    /**
     * 执行提交processInstance
     * @param takId
     */
    public void complete(String takId);

    public UserService getUserDubboService();

    public ApproveRecordService getApproveRecordDubboService();

    public OrderService getOrderDubboService();

    public MailTemplateService getMailTemplateDubboService();

    public ServiceInstanceService getServiceInstanceDubboService();

    public MailNotificationsService getMailNotificationsDubboService();

    public UserMgtObjService getUserMgtObjDubboService();

    public UserRoleService getUserRoleDubboService();
}
