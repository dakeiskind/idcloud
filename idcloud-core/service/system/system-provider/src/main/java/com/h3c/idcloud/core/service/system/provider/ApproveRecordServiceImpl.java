package com.h3c.idcloud.core.service.system.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.ApproveRecordMapper;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.dto.system.ApproveRecord;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.user.Role;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.order.api.OrderDetailService;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceConfigService;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceChangeLogService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceSpecService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.ApproveRecordService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.core.service.system.api.UserMgtObjService;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.BaseGridReturn;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.ActivitiWorkflowUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Service(version = "1.0.0")
@Component
public class ApproveRecordServiceImpl implements ApproveRecordService {
    @Autowired
    private ApproveRecordMapper approveRecordMapper;

    //服务实例Service
    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstanceService;

    //ChengQi start
    /**
     * 订单管理Service
     */
    @Reference(version = "1.0.0")
    private OrderService orderService;

    /**
     * 订单详细Service
     */
    @Reference(version = "1.0.0")
    private OrderDetailService orderDetailService;

    /**
     * Activiti任务Service
     *
     * @Reference(version = "1.0.0") private RuntimeService runtimeService;
     */

    @Reference(version = "1.0.0")
    private ServiceInstanceSpecService instanceSpecService;

    @Reference(version = "1.0.0")
    private ServiceChangeLogService serviceChangeLogService;
    //ChengQi end

    //LiuFujun start
//    @Autowired
//    private FreeResService freeResService;
//    @Autowired
//    private FreeResCheckLogService checkLogService;
    @Autowired
    private MgtObjService mgtObjService;

    //LiuFujun end

    @Reference(version = "1.0.0")
    private TaskService taskService;

    @Reference(version = "1.0.0")
    private ResVmService resVmService;

    @Reference(version = "1.0.0")
    private ServiceInstResService serviceInstResService;

//	@Autowired
//	private SysLoggerFactory sysLogger;

    @Autowired
    private UserMgtObjService userMgtObjService;

    @Reference(version = "1.0.0")
    private ServiceInstanceChangeLogService instanceChangeLogService;

//	@Reference(version = "1.0.0")
//	private MailNotificationsService mailNotificationsService;

    @Reference(version = "1.0.0")
    private UserService userService;

    @Reference(version = "1.0.0", group = "vmOpenHandlerImpl")
    private ResourceRequestHanlder vmOpenHandlerImpl;

    @Reference(version = "1.0.0", group = "vmChangeHandlerImpl")
    private ResourceRequestHanlder vmChangeHandlerImpl;

    @Reference(version = "1.0.0", group = "vmDeleteHandlerImpl")
    private ResourceRequestHanlder vmDeleteHandlerImpl;


    private static final Logger logger = LoggerFactory.getLogger(ApproveRecordServiceImpl.class);

    private ResourceRequestHanlder getResourceRequestHanlder(String processType) {
        ResourceRequestHanlder resourceRequestHanlder = null;
        if (WebConstants.ProcessType.VM_OPEN.equals(processType)) {
            resourceRequestHanlder = this.vmOpenHandlerImpl;
        } else if (WebConstants.ProcessType.INSTANCE_ADJUST.equals(processType)
                || WebConstants.ProcessType.FREE_RESOURCE_REDUCE.equals(processType)) {
            resourceRequestHanlder = this.vmChangeHandlerImpl;
        } else if (WebConstants.ProcessType.VM_CANCEL.equals(processType)) {
            resourceRequestHanlder = this.vmDeleteHandlerImpl;
        } else if (WebConstants.ProcessType.OBJECT_STORAGE_OPEN.equals(processType)) {
            resourceRequestHanlder = (ResourceRequestHanlder) SpringContextHolder.getApplicationContext().getBean("objectStorageOpenHandlerImpl");
        } else if (WebConstants.ProcessType.STORAGE_OPEN.equals(processType)) {
            resourceRequestHanlder = (ResourceRequestHanlder) SpringContextHolder.getApplicationContext().getBean("storageOpenHandlerImpl");
        } else if (WebConstants.ProcessType.FLOATING_IP_OPEN.equals(processType)) {
            resourceRequestHanlder = (ResourceRequestHanlder) SpringContextHolder.getApplicationContext().getBean("floatingIpOpenHandlerImpl");
        } else if (WebConstants.ProcessType.CDN_OPEN.equals(processType)) {
            resourceRequestHanlder = (ResourceRequestHanlder) SpringContextHolder.getApplicationContext().getBean("cdnServiceOpenHandlerImpl");
        } else if (WebConstants.ProcessType.CDN_CANCEL.equals(processType)) {
            resourceRequestHanlder = (ResourceRequestHanlder) SpringContextHolder.getApplicationContext().getBean("cdnServiceDeleteHandlerImpl");
        } else if (WebConstants.ProcessType.MANAGER_CHANGE.equals(processType) || WebConstants.ProcessType.MGT_OBJ_CHANGE.equals(processType)) {
            resourceRequestHanlder = (ResourceRequestHanlder) SpringContextHolder.getApplicationContext().getBean("otherChangeHandlerImpl");
        } else {
            throw new RuntimeException("Not support approved processType. processType=" + processType);
        }
        return resourceRequestHanlder;
    }

    public int countByParams(Criteria example) {
        int count = this.approveRecordMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ApproveRecord selectByPrimaryKey(Long approveRecordSid) {
        return this.approveRecordMapper.selectByPrimaryKey(approveRecordSid);
    }

    public List<ApproveRecord> selectByParams2(Criteria example) {
        return this.approveRecordMapper.selectByParams2(example);
    }

    public List<ApproveRecord> selectByParams(Criteria example) {
        return this.approveRecordMapper.selectByParams(example);

    }

    public int deleteByPrimaryKey(Long approveRecordSid) {
        return this.approveRecordMapper.deleteByPrimaryKey(approveRecordSid);
    }

    public int updateByPrimaryKeySelective(ApproveRecord record) {
        return this.approveRecordMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ApproveRecord record) {
        return this.approveRecordMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.approveRecordMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(ApproveRecord record, Criteria example) {
        return this.approveRecordMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(ApproveRecord record, Criteria example) {
        return this.approveRecordMapper.updateByParams(record, example.getCondition());
    }

    public int insert(ApproveRecord record) {
        return this.approveRecordMapper.insert(record);
    }

    public int insertSelective(ApproveRecord record) {
        return this.approveRecordMapper.insertSelective(record);
    }

    public List<ApproveRecord> selectOrderOpenByParams(Criteria example) {
        return this.approveRecordMapper.selectOrderOpenByParams(example);
    }

    public List<ApproveRecord> selectProcessInstanceIdAndType(Criteria example) {
        return this.approveRecordMapper.selectProcessInstanceIdAndType(example);
    }

    public List<ApproveRecord> selectOperateApproveRecord(Criteria example) {
        return this.approveRecordMapper.selectOperateApproveRecord(example);
    }

    public List<ApproveRecord> selectApprovedRecord(Criteria example) {
        return this.approveRecordMapper.selectApprovedRecord(example);
    }

    /**
     * 取得已审批和待审批记录
     *
     * @param request 审批类型
     * @return List<ApproveRecord>
     * @author wxb
     */
    public String selectApproveRecord(HttpServletRequest request) throws Exception {
        List<ApproveRecord> approveRecordlist = new ArrayList<ApproveRecord>();
        Integer total = 0;
//		String aproveType=request.getParameter("approveType");
//		Criteria criteria=new Criteria();
//		String currentUser = AuthUtil.getAuthUser().getAccount();
//		if (aproveType.equals(WebConstants.ProcessApproveStatus.APPROVING)) {
//			WebUtil.preparePageParams(request, criteria, "A.CREATED_DT desc");
//			criteria.put("currentUser", currentUser);
//			if(isResourceMgtRole(AuthUtil.getAuthUser())) {
//				Long userSid = AuthUtil.getAuthUser().getUserSid();
//				Criteria params = new Criteria();
//				params.put("userSid", userSid);
//				List<UserMgtObjKey> userObjKeys = this.userMgtObjService.selectByParams(params);
//				List<Long> mgtObjSids = new ArrayList<Long>();
//				for(UserMgtObjKey mgtObjKey : userObjKeys) {
//					mgtObjSids.add(mgtObjKey.getMgtObjSid());
//				}
//				if(mgtObjSids.size() > 0) {
//					criteria.put("mgtObjSids", mgtObjSids);
//				}else{
//					criteria.put("proposeBy", currentUser);
//				}
//			}
//			criteria.put("approvorIdNull", StringUtils.EMPTY);
//			approveRecordlist = this.approveRecordMapper.selectByParams2(criteria);
//			total = this.approveRecordMapper.countByParams2(criteria);
//		} else {
//			criteria.setOrderByClause("A.APPROVE_DATE desc");
////			WebUtil.preparePageParams(request, criteria, "A.APPROVE_DATE desc");
//			criteria.put("approvorId", currentUser);
//			approveRecordlist = this.approveRecordMapper.selectApprovedRecord2(criteria);
//			total = this.approveRecordMapper.countApprovedRecord2(criteria);
//		}
        return JsonUtil.toJson(new BaseGridReturn(total, approveRecordlist));
    }

    public Boolean isResourceMgtRole(User user) {
        Boolean isResourceMgt = false;
        List<Role> roleList = user.getRoles();
        for (Role role : roleList) {
            if (role.getRoleSid() == 205L) {
                isResourceMgt = true;
                break;
            }
        }
        return isResourceMgt;
    }

    public Integer countApproveRecord(String type) {
        Integer count = 0;
        Criteria criteria = new Criteria();
//    	String currentUser = AuthUtil.getAuthUser().getAccount();
//    	if (type.equals(WebConstants.ProcessApproveStatus.APPROVING)) {
//			criteria.put("currentUser", currentUser);
//			criteria.put("approvorIdNull", StringUtils.EMPTY);
//			count = this.approveRecordMapper.countByParams2(criteria);
//		} else {
//			criteria.put("approvorId", currentUser);
//			count = this.approveRecordMapper.countApprovedRecord2(criteria);
//		}
        return count;
    }

    /**
     * 取得已审批和待审批实例记录
     *
     * @param type 审批类型
     * @return List<ApproveRecord>
     * @author wxb
     */
    public List<ApproveRecord> selectApproveRecordInst(Criteria criteria, String type) {

        List<Map<String, String>> processInstanceIds = new ArrayList<Map<String, String>>();

        List<ApproveRecord> approveRecordlist = new ArrayList<ApproveRecord>();
// 		String currentUser = AuthUtil.getAuthUser().getAccount();
// 		//List<ApproveRecord> approveRecords = this.approveRecordMapper.selectByParams2(criteria1);
//
// 		if (type.equals(WebConstants.ProcessApproveStatus.APPROVING)) {
// 			// 从审核记录表中查出全部的流程id和类型，DISTINCT
// 			String createdBy = null;
// 			if (criteria.get("approvorIdLike") != null) {
// 				createdBy = (String)criteria.get("approvorIdLike");
// 				criteria.put("approvorIdLike", null);
// 			}
// 			criteria.put("approvorIdNull", StringUtil.EMPTY);
// 			List<ApproveRecord> approveRecords = this.approveRecordMapper.selectByParams2(criteria);
// 			//approveRecordlist = this.approveRecordMapper.selectByParams2(criteria);
//
// 			for (ApproveRecord approveRecord : approveRecords) {
// 				processInstanceIds = getTaskProcessInstanceId(currentUser, createdBy);
// 				for (Map<String, String> processInstanceIdMap : processInstanceIds) {
// 					// 判断用户有权限审批的流程
// 					if (approveRecord.getProcessInstanceId().equals(processInstanceIdMap.get("processInstanceId"))) {
// 						//ApproveRecord approveRecordReturn = addOtherInfoForApproveRecord(approveRecord, type, processInstanceIdMap);
// 						boolean repeat = false;
// 						//去掉重复任务
// 						if (approveRecordlist!=null && approveRecordlist.size()>0) {
// 							for (ApproveRecord approveRecordRepeat : approveRecordlist) {
// 								//if (approveRecordRepeat.getProcessInstanceId().equals(approveRecordReturn.getProcessInstanceId())) {
// 								if (approveRecordRepeat.getProcessInstanceId().equals(approveRecord.getProcessInstanceId())) {
// 									repeat = true;
// 									break;
// 								}
// 							}
// 						}
// 						if (repeat == false) {
// 							//approveRecordlist.add(approveRecordReturn);
// 							approveRecordlist.add(approveRecord);
// 						}
// 					}
// 				}
// 			}
// 		} else {
//
// 		}
        return approveRecordlist;
    }

    /**
     * 添加未审核，已审核的审核记录表以外的其他信息
     *
     * @return ApproveRecord
     * @author wxb
     */
    private ApproveRecord addOtherInfoForApproveRecord(ApproveRecord approveRecord, String type, Map<String, String> processInstanceIdMap) {

        if (approveRecord.getProcessType().equals(WebConstants.ProcessType.VM_OPEN)) {
            // 取得订单编号
            Criteria criteria = new Criteria();
            criteria.put("processInstanceId", approveRecord.getProcessInstanceId());
            List<OrderDetail> orderDetails = orderDetailService.selectByParams(criteria);
            if (orderDetails != null && orderDetails.size() > 0) {
                OrderDetail orderDetail = orderDetails.get(0);
                // 查询订单表取得审核记录其他信息
                criteria = new Criteria();
                criteria.put("orderId", orderDetail.getOrderId());
                Order order = orderService.selectByParams(criteria).get(0);
                approveRecord.setTarget(orderDetail.getOrderId());
                approveRecord.setApproveObject(order.getOrderId());
                approveRecord.setServiceName(orderDetail.getServiceName());
// 	 			approveRecord.setTanentId(order.getTanentId());
// 	 			approveRecord.setTenantName(order.getTenantName());
// 	 			approveRecord.setCreatedBy(order.getCreatedBy());
// 	 			approveRecord.setCreatedDt(order.getCreatedDt());
            }

        } else if (approveRecord.getProcessType().equals(WebConstants.ProcessType.INSTANCE_ADJUST)) {
             /*ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(approveRecord.getProcessObjectId());
 			if (serviceInstance!=null) {
	 			approveRecord.setApproveObject(serviceInstance.getInstanceName());
	 			approveRecord.setServiceName(serviceInstance.getServiceName());
	 			approveRecord.setTarget(serviceInstance.getInstanceSid().toString());
	 			approveRecord.setApproveObjectId(serviceInstance.getInstanceSid());
//	 			approveRecord.setTanentId(serviceInstance.getTanentId());
//	 			approveRecord.setTenantName(serviceInstance.getTenantName());
//	 			approveRecord.setCreatedBy(serviceInstance.getOwnerId());
//	 			approveRecord.setCreatedDt(serviceInstance.getCreatedDt());
 			}*/
        }

        return approveRecord;
    }


    /**
     * 找出当前用户下所有待审批的流程实例ID
     *
     * @return List<Map<String, String>>
     * @author wxb
     */
    private List<Map<String, String>> getTaskProcessInstanceId(String currentUser, String createdBy) {

        // 订单流程服务类
        ActivitiWorkflowUtil activitiWorkflowService = new ActivitiWorkflowUtil();
        List<Map<String, String>> processInstanceIds = new ArrayList<Map<String, String>>();
        List<Task> allTask = activitiWorkflowService.queryUserAllTask(currentUser, createdBy);
        for (Task task : allTask) {
            Map<String, String> taskMap = new HashMap<String, String>();
            taskMap.put("processInstanceId", task.getProcessInstanceId());
            taskMap.put("taskDefKey", task.getTaskDefinitionKey());
            processInstanceIds.add(taskMap);
        }
// 		List<Map<String, String>> processInstanceIds = new ArrayList<Map<String, String>>();
//
// 		//个人任务：查询租户管理员待审批任务，根据个人用户id（动态指定租户管理员审批）
// 		List<Task> personalTask = activitiWorkflowService.queryPersonalTaskList(currentUser);
// 		
// 		for (Task task : personalTask) {
// 			Map<String, String> taskMap = new HashMap<String, String>();
// 			taskMap.put("processInstanceId", task.getProcessInstanceId());
// 			taskMap.put("taskDefKey", task.getTaskDefinitionKey());
// 			processInstanceIds.add(taskMap);
// 		}
// 		
// 		//角色任务： 查询运维管理员待审批深入，根据角色查询
// 		List<Role> roles = AuthUtil.getAuthUser().getRoles();
//
// 		for (Role role : roles) {
// 			// 该role下有没有可审批的任务
// 			List<Task> groupTask = activitiWorkflowService.queryUserGroupTask(role.getRoleSid().toString());
// 			for (Task task : groupTask) {
// 				Map<String, String> taskMap = new HashMap<String, String>();
// 				taskMap.put("processInstanceId", task.getProcessInstanceId());
// 				taskMap.put("taskDefKey", task.getTaskDefinitionKey());
// 				processInstanceIds.add(taskMap);
// 			}
// 		}

        return processInstanceIds;
    }

    /**
     * 找出当前用户下所有已审批的流程实例ID
     *
     * @return List<Map<String, String>>
     * @author wxb
     */
    private List<Map<String, String>> getHistoryTaskProcessInstanceId() {

        // 订单流程服务类
        ActivitiWorkflowUtil activitiWorkflowService = new ActivitiWorkflowUtil();

        List<Map<String, String>> processInstanceIds = new ArrayList<Map<String, String>>();

        // 该role下已完成的2级审批任务
        List<HistoricTaskInstance> groupTask = activitiWorkflowService.queryUserGroupHistoryTask();
        for (HistoricTaskInstance task : groupTask) {
            Map<String, String> taskMap = new HashMap<String, String>();
            taskMap.put("processInstanceId", task.getProcessInstanceId());
            taskMap.put("taskDefKey", task.getTaskDefinitionKey());
            processInstanceIds.add(taskMap);
        }

        return processInstanceIds;
    }


    //租户管理员审批
    public boolean tenantAdminApprove(String checkStatus, String checkcomments, String processInstanceId, String approvorAction, String processType) {

        // 订单流程服务类
        ActivitiWorkflowUtil activitiWorkflowService = new ActivitiWorkflowUtil();

        TaskService taskService = SpringContextHolder.getBean("taskService");
        HistoryService historyService = SpringContextHolder.getBean("historyService");

        // 获得组内任务。
        List<Task> personalTask = activitiWorkflowService.queryProcessInstanceTask(processInstanceId);

        //运营管理员审批
        String approveOm = PropertiesUtil.getProperty("approve.om");
//		String blnApproveTenantQuota = "";
//		List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
//		for (HistoricVariableInstance variableInstance : list) {
//			if (variableInstance.getVariableName().equals("blnApproveTenantQuota")) {
//				blnApproveTenantQuota = StringUtil.nullToEmpty(variableInstance.getValue());
//				break;
//			}
//		}
//		
//		//如果是配额超出需要审批，则设置运营管理员审批
//		if (blnApproveTenantQuota.equals("true")) {
//			approveOm = "true";
//			logger.info("用户订购超过租户配额，进行运维管理员审批11111。");   
//		}

        if (personalTask != null && personalTask.size() > 0) {

            for (Task task : personalTask) {

                Map<String, Object> variables = new HashMap<String, Object>();
                logger.info("租户管理员执行任务名称：" + task.getName());

                if (checkStatus.equals(WebConstants.ApproveStatus.AGREE)) {

                    //设置流程分支 true 结束流程，剩下任务为0 false 返回订单申请 剩下任务各为1
                    variables.put("tenantAdminApprove", true);

                    //保存数据到审批记录表
                    updateApproveRecord(task.getProcessInstanceId(), checkStatus, checkcomments, approvorAction, processType);

                    //判断运维管理员是否需要审批
                    if (approveOm.equals("true")) {
                        variables.put("approveOm", true);
                    } else {
                        variables.put("approveOm", false);
                        updateOneDataApproveRecord(task.getProcessInstanceId(), checkStatus, checkcomments,
                                WebConstants.ApproveAction.ORDER_APPROVE, processType);
                    }

                } else if (checkStatus.equals(WebConstants.ApproveStatus.DISAGREE)) {
                    //保存数据到审批记录表
                    updateApproveRecord(task.getProcessInstanceId(), checkStatus, checkcomments, approvorAction, processType);

                    //设置流程分支 true 结束流程，剩下任务为0 false 返回订单申请 剩下任务各为1
                    variables.put("tenantAdminApprove", false);
                    variables.put("processFailedDesc", "租户管理员审批不通过。");
                }

                //保存数据到审批记录表
//				updateApproveRecord(task.getProcessInstanceId(), checkStatus, checkcomments, approvorAction, processType);

                // 组内成员处理任务，任务结束。
                taskService.complete(task.getId(), variables);
                logger.info("流程实例：" + task.getProcessInstanceId() + task.getName() + checkStatus + "租户审批结束。");
            }
            return true;
        } else {
            logger.info("该管理员没有执行的任务。");
            return false;
        }

    }

    //运维管理员审批
    public boolean operateAdminApprove1(String checkStatus, String checkcomments, String processInstanceId, String approvorAction, String processType) {

        TaskService taskService = SpringContextHolder.getBean("taskService");

        // 订单流程服务类
        ActivitiWorkflowUtil activitiWorkflowService = new ActivitiWorkflowUtil();

        // 获得组内任务。
        List<Task> tasks = activitiWorkflowService.queryProcessInstanceTask(processInstanceId);

        if (tasks != null && tasks.size() > 0) {

            for (Task task : tasks) {

                Map<String, Object> variables = new HashMap<String, Object>();
                logger.info("订单管理员执行任务名称：" + task.getName());

                if (checkStatus.equals(WebConstants.ApproveStatus.AGREE)) {
                    //设置流程分支 true 结束流程，剩下任务为0 false 返回订单申请 剩下任务各为1
                    variables.put("omAdminApprove", true);
                } else if (checkStatus.equals(WebConstants.ApproveStatus.DISAGREE)) {

                    //设置流程分支 true 结束流程，剩下任务为0 false 返回订单申请 剩下任务各为1
                    variables.put("omAdminApprove", false);
                    variables.put("processFailedDesc", "运营管理员审批不通过。");
                }

                //更新数据到审批记录表
                updateApproveRecord(task.getProcessInstanceId(), checkStatus, checkcomments, approvorAction, processType);

                // 组内成员处理任务，任务结束。
                taskService.complete(task.getId(), variables);

                logger.info("流程实例：" + task.getProcessInstanceId() + task.getName() + checkStatus + "运营审批结束。");
            }
            return true;

        } else {
            logger.info("该组没有执行的任务。");
            return false;
        }

    }

    //ChengQi start
    //适用角色为（后端运维， 互联网科经理，网络部分管副总，IDC云运维）管理人员审批
    public boolean createAdminApprove(Map<String, Object> map, String checkStatus, String checkcomments, String processInstanceId, String approvorAction, String processType) {
//		TaskService taskService = SpringContextHolder.getBean("taskService");
//
//		// 订单流程服务类
//		ActivitiWorkflowUtil activitiWorkflowService = new ActivitiWorkflowUtil();
//		// 获得组内任务。
//		List<Task> tasks = activitiWorkflowService.queryProcessInstanceTask(processInstanceId);
//
//		String curUser = AuthUtil.getAuthUser().getAccount();
//
//		if (tasks != null && tasks.size() > 0) {
//
//			for (Task task : tasks) {
//
//				//更新数据到审批记录表
//				updateApproveRecord(task.getProcessInstanceId(), checkStatus, checkcomments, null, processType);
//
//				Map<String, Object> variables = new HashMap<String, Object>();
//				logger.info("订单管理员执行任务名称：" + task.getName());
//
//				if (checkStatus.equals(WebConstants.ApproveStatus.AGREE)) {
//
//					if(WebConstants.ProcessType.VM_OPEN.equals(processType)) {
//						//云主机开通流程
//						//如果是终审
//						if(activitiWorkflowService.isFinal(processInstanceId)) {
//							String orderId = StringUtil.nullToEmpty(map.get("orderId"));
//							Criteria criteria = new Criteria();
//							criteria.put("orderId", orderId);
//							criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//							List<ServiceInstance> vmServiceInstances = this.serviceInstanceService.selectByParams(criteria);
//							for(ServiceInstance serviceInstance : vmServiceInstances) {
//								ServiceInstanceChangeLogSpec<ResVmInst> instanceChangeLogSpec = this.serviceInstanceService.assembleServiceInstanceOpenParams(serviceInstance, map);
//								this.instanceChangeLogService.updateChangeServiceInstance(serviceInstance.getInstanceSid(),
//										WebConstants.instanceChangeType.CREATE,
//										instanceChangeLogSpec, new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {});
//
//								Map resSetMap = this.getInstanceResSet((List<Map<String, Object>>)map.get("resSet"), serviceInstance.getInstanceSid());
//								if(null!=resSetMap.get("allocateType")&&!StringUtil.isNullOrEmpty(resSetMap.get("allocateType").toString())
//										&& WebConstants.AllocateType.NANOTUBE.equals(resSetMap.get("allocateType").toString())){
//									if(!StringUtil.isNullOrEmpty(resSetMap.get("aixParSid").toString())){
//										//实例和资源进行关联
//										ServiceInstRes sir = new ServiceInstRes();
//										sir.setInstanceSid(serviceInstance.getInstanceSid());
//										sir.setResId(resSetMap.get("aixParSid").toString());
//										sir.setResType("RES-VM");
//										serviceInstResService.insertSelective(sir);
//										//将本次开通的changeLog的状态改成已完成
//										ServiceInstanceChangeLog instChangeLog = instanceChangeLogService.getLastChangeLog(serviceInstance.getInstanceSid());
//										instChangeLog.setChangeEndTime(new Date());
//										instChangeLog.setStatus(WebConstants.instanceChangeStatus.CHANGED);
//										instanceChangeLogService.updateByPrimaryKeySelective(instChangeLog);
//									}
//								}
//							}
//
//						}
//					} else if(WebConstants.ProcessType.INSTANCE_ADJUST.equals(processType)){
//						if(activitiWorkflowService.isFinal(processInstanceId)) {
//							String instanceSid = StringUtil.nullToEmpty(map.get("instanceSid"));
//							ServiceInstance serviceInstance = this.serviceInstanceService.selectByPrimaryKey(Long.parseLong(instanceSid));
//							ServiceInstanceChangeLogSpec<ResVmInst> instanceChangeLogSpec = new ServiceInstanceChangeLogSpec<ResVmInst>();
//							instanceChangeLogSpec.setParams(new ResVmInst());
//							instanceChangeLogSpec.setVariables(new HashMap<String, Object>());
//							ResVmInst resVmInst = instanceChangeLogSpec.getParams();
//							String cpu = (String)map.get("cpu");
//							if(StringUtils.isNotBlank(cpu)) {
//								resVmInst.setCpu(Integer.parseInt(cpu));
//							}
//							String cpuMin = (String)map.get("cpuMin");
//							if(StringUtils.isNotBlank(cpuMin)) {
//								resVmInst.setCpuMin(Integer.parseInt(cpuMin));
//							}
//							String cpuMax = (String)map.get("cpuMax");
//							if(StringUtils.isNotBlank(cpuMax)) {
//								resVmInst.setCpuMax(Integer.parseInt(cpuMax));
//							}
//							String memory = (String)map.get("memory");
//							if(StringUtils.isNotBlank(memory)) {
//								resVmInst.setMemory(Integer.parseInt(memory) * 1024);
//							}
//							String memoryMin = (String)map.get("memoryMin");
//							if(StringUtils.isNotBlank(memoryMin)) {
//								resVmInst.setMemoryMin(Integer.parseInt(memoryMin) * 1024);
//							}
//							String memoryMax = (String)map.get("memoryMax");
//							if(StringUtils.isNotBlank(memoryMax)) {
//								resVmInst.setMemoryMax(Integer.parseInt(memoryMax) * 1024);
//							}
//							String powerCpuUsedUnit = (String)map.get("powerCpuUsedUnit");
//							if(StringUtils.isNotBlank(powerCpuUsedUnit)) {
//								resVmInst.setPowerCpuUsedUnits(Float.parseFloat(powerCpuUsedUnit));
//							}
//							String powerCpuMinUnit = (String)map.get("powerCpuMinUnit");
//							if(StringUtils.isNotBlank(powerCpuMinUnit)) {
//								resVmInst.setPowerCpuMinUnits(Float.parseFloat(powerCpuMinUnit));
//							}
//							String powerCpuMaxUnit = (String)map.get("powerCpuMaxUnit");
//							if(StringUtils.isNotBlank(powerCpuMaxUnit)) {
//								resVmInst.setPowerCpuMaxUnits(Float.parseFloat(powerCpuMaxUnit));
//							}
//							this.instanceChangeLogService.updateChangeServiceInstance(serviceInstance.getInstanceSid(),
//									WebConstants.instanceChangeType.CHANGE,
//									instanceChangeLogSpec, new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {});
//						}
//					}else if(WebConstants.ProcessType.VM_CANCEL.equals(processType)){
//						String instanceSid = StringUtil.nullToEmpty(map.get("instanceSid"));
//						List<ServiceInstanceSpec> serviceInstanceSpecs =
//								instanceSpecService.selectByInstanceSpecForDataDiskBySid(Long.parseLong(instanceSid));
//						if(!CollectionUtils.isEmpty(serviceInstanceSpecs)){
//							for (ServiceInstanceSpec sis : serviceInstanceSpecs) {
//								if(WebConstants.InstanceSpecType.OS.equals(sis.getName())
//										&& WebConstants.VirtualEnv.POWER.equals(sis.getVe())){
//									String cancelType = StringUtil.nullToEmpty(map.get("cancelType"));
//									if("2".equals(cancelType)){
//										//取消纳管
//										ServiceInstance instance = this.serviceInstanceService.selectByPrimaryKey(Long.parseLong(instanceSid));
//										//更新Vm实例状态为正常,更新内外网络
//										instance.setStatus(WebConstants.ServiceInstanceStatus.CANCELED);
//										//设置销毁时间
//										instance.setDestroyDate(new Date());
//										serviceInstanceService.updateByPrimaryKeySelective(instance);
//
//										//删除关联关系
//										ResVm resVm = null;
//										Criteria example = new Criteria();
//										example.put("instanceSid", instanceSid);
//										List<ServiceInstRes> instRes = serviceInstResService.selectByParams(example);
//										if(!CollectionUtils.isEmpty(instRes)){
//											resVm = resVmService.selectByPrimaryKey(instRes.get(0).getResId());
//										}
//										serviceInstResService.deleteByParams(example);
//
//										//更新云主机下的磁盘的服务实例状态为已退订
//										serviceInstanceService.modifyAllChildServiceInstancesOfVmStatus(Long.parseLong(instanceSid), WebConstants.ServiceInstanceStatus.CANCELED);
//
//										//更新实例变更日志
//										instanceChangeLogService.endChangeServiceInstance(instance, WebConstants.instanceChangeType.UNSUBSCRIBE);
//
//										//发送退订通知邮件
//										if(resVm != null){
//											mailNotificationsService.unsubscribeServiceEmail(Long.parseLong(instanceSid), resVm);
//										}
//									}
//								}
//							}
//						}
//					}
//
//					/** lfj  start**/
//					//其他类型的流程，不需要调用handler直接操作数据库
//					//变更项目经理
//					else if(WebConstants.ProcessType.MANAGER_CHANGE.equals(processType)){
//						if(activitiWorkflowService.isFinal(processInstanceId)) {
//						}
//					}
//					else if(WebConstants.ProcessType.MGT_OBJ_CHANGE.equals(processType)){
//						if(activitiWorkflowService.isFinal(processInstanceId)) {
//						}
//					}
//					/** lfj  end**/
//
//					//如果需要云平台负责人审核，则不过滤
//					Boolean isNeedPrincipal = (Boolean)map.get("isNeedPrincipal");
//					if(isNeedPrincipal != null) {
//						if(isNeedPrincipal) {
//							variables.put("approveNetDeptMgt", true);
//						} else {
//							variables.put("approveNetDeptMgt", false);
//						}
//					}
//					//设置审批结果，决定流程分支
//					variables.put("approveResult", true);
//
//				} else if (checkStatus.equals(WebConstants.ApproveStatus.DISAGREE)){
//					String orderId = (String)runtimeService.getVariable(processInstanceId, "orderId");
//					Criteria criteria = new Criteria();
//					criteria.put("orderId", orderId);
//					Order order = orderService.selectByParams(criteria).get(0);
//					criteria = new Criteria();
//					criteria.put("account", order.getOwnerId());
//					List<User> userlist = this.userService.selectByParams(criteria);
//					List<String> toAddressList = new ArrayList<String>();
//					if(userlist.size()>0){
//						toAddressList.add(userlist.get(0).getEmail());
//					}
//					//将订单状态设置为已审批
//					if(WebConstants.ProcessType.VM_OPEN.equals(processType)) {
//						order.setStatus(WebConstants.OrderStatusCd.REJECTED);
//						WebUtil.prepareUpdateParams(order, curUser);
//						//更新订单信息
//						orderService.updateByPrimaryKeySelective(order);
//						//更新changeLog表
////						List<Map<String, Object>> resSetList = (List<Map<String, Object>>)map.get("resSet");
////						for (Map<String, Object> resSet : resSetList) {
////							ServiceInstance instance = instanceService.selectByPrimaryKey(Long.parseLong(StringUtil.nullToEmpty(resSet.get("instanceSid"))));
////							instanceChangeLogService.endChangeServiceInstance(instance, WebConstants.instanceChangeType.CREATE);
////						}
//
//						this.mailNotificationsService.refapproveInfoNotice(orderId, checkcomments, toAddressList);
//
//					} else if(WebConstants.ProcessType.INSTANCE_ADJUST.equals(processType)){
//						ServiceInstance instance = serviceInstanceService.selectByPrimaryKey(Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid"))));
//						instance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//						this.serviceInstanceService.updateByPrimaryKeySelective(instance);
//
//						instanceChangeLogService.cancelChangeServiceInstance(instance, WebConstants.instanceChangeType.CHANGE);
//
//						this.mailNotificationsService.refapproveInfoNotice(orderId, checkcomments, toAddressList);
//					} else if(WebConstants.ProcessType.VM_CANCEL.equals(processType)){
//						ServiceInstance instance = serviceInstanceService.selectByPrimaryKey(Long.parseLong(StringUtil.nullToEmpty(map.get("instanceSid"))));
//						instance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//						this.serviceInstanceService.updateByPrimaryKeySelective(instance);
//
//						instanceChangeLogService.cancelChangeServiceInstance(instance, WebConstants.instanceChangeType.UNSUBSCRIBE);
//
//						this.mailNotificationsService.refapproveInfoNotice(orderId, checkcomments, toAddressList);
//					} else if(WebConstants.ProcessType.MANAGER_CHANGE.equals(processType)){
//						String objectId = StringUtil.nullToEmpty(map.get("processObjectId"));
//						List<ServiceInstanceChangeLog> changeLogs = this.instanceChangeLogService.selectLastChangeLog(Long.parseLong(objectId));
//						ServiceInstanceChangeLog changeLog = changeLogs.get(0);
//						if(WebConstants.instanceChangeType.CHANGE_MANAGER.equals(changeLog.getChangeType())) {
//							try {
//								MgtObj mgtObj = mgtObjService.selectByPrimaryKey(Long.parseLong(objectId));
//								mgtObj.setStatus(WebConstants.MGT_OBJ_STATUS.NORMAL);
//								mgtObjService.updateByPrimaryKeySelective(mgtObj);
//
//								//更新changeLog的状态和 结束时间 完成本次变更
//								changeLog.setChangeEndTime( new Date());
//								changeLog.setStatus(WebConstants.instanceChangeStatus.CANCELED);
//
//								instanceChangeLogService.updateByPrimaryKeySelective(changeLog);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					} else if(WebConstants.ProcessType.MGT_OBJ_CHANGE.equals(processType)){
//						String objectId = StringUtil.nullToEmpty(map.get("processObjectId"));
//						List<ServiceInstanceChangeLog> changeLogs = this.instanceChangeLogService.selectLastChangeLog(Long.parseLong(objectId));
//						ServiceInstanceChangeLog changeLog = changeLogs.get(0);
//						if(WebConstants.instanceChangeType.CHANGE_MGT_OBJ.equals(changeLog.getChangeType())) {
//							try {
//								ServiceInstance instance = serviceInstanceService.selectByPrimaryKey(Long.parseLong(objectId));
//								instance.setStatus(WebConstants.ServiceInstanceStatus.OPENED);
//								serviceInstanceService.updateByPrimaryKeySelective(instance);
//
//								//更新changeLog的状态和 结束时间 完成本次变更
//								changeLog.setChangeEndTime( new Date());
//								changeLog.setStatus(WebConstants.instanceChangeStatus.CANCELED);
//								instanceChangeLogService.updateByPrimaryKeySelective(changeLog);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					//设置审批结果，决定流程分支
//					variables.put("approveResult", false);
//				}
//				variables.put("applyUserId", AuthUtil.getAuthUser().getAccount());
//				// 组内成员处理任务，任务结束。
//				taskService.complete(task.getId(), variables);
//
//				logger.info("流程实例：" + task.getProcessInstanceId()+ " " + task.getName() + " " + checkStatus + " " + task.getName() + "结束。");
//			}
//			return true;
//
//		} else {
//			logger.info("该组没有执行的任务。");
//			return false;
//		}
        return true;

    }

    private String getSpecChangeValue(String specKey, List<Map<String, Object>> specChanges) {
        String value = null;
        for (Map<String, Object> spec : specChanges) {
            if (specKey.equals(spec.get("speckKey"))) {
                value = (String) spec.get("specChangeValue");
                break;
            }
        }
        return value;
    }

    public boolean executeRequestResource(Map<String, Object> map, String processType) {
        //调用虚拟机资源层接口
        String processObjectId = StringUtil.nullToEmpty(map.get("processObjectId"));
        return executeRequestResource(processObjectId, processType);
    }

    public boolean executeRequestResource(String processObjectId, String processType) {
        boolean result = true;
        ResourceRequestHanlder requestHanlder = getResourceRequestHanlder(processType);
        if (WebConstants.ProcessType.VM_OPEN.equals(processType)) {
            Order order = this.orderService.selectByPrimaryKey(Long.parseLong(processObjectId));
            logger.info("审核通过，创建虚拟机，对应订单ID：" + order.getOrderId());
            Criteria criteria = new Criteria();
            criteria.put("orderId", order.getOrderId());
            criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
            List<ServiceInstance> serviceInstances = serviceInstanceService.selectByParams(criteria);
            for (ServiceInstance instance : serviceInstances) {
                logger.info("审核通过，创建虚拟机，对应服务实例ID：" + instance.getInstanceSid());
                requestHanlder.invoke(instance.getInstanceSid());
            }
        } else {
            result = requestHanlder.invoke(Long.parseLong(processObjectId));
        }
        return result;
    }

    //ChengQi end

    public ResInstResult vmResCheck(Map<String, Object> map) {
        ResInstResult resInstResult = null;

        String processType = StringUtil.nullToEmpty(map.get("processType"));
        if (WebConstants.ProcessType.VM_OPEN.equals(processType)) {
            resInstResult = openVmCheck(map);
        } else if (WebConstants.ProcessType.INSTANCE_ADJUST.equals(processType)) {
            resInstResult = reconfigVmCheck(map);
        }
        return resInstResult;
    }

    public Map getInstanceResSet(List<Map<String, Object>> resSetList, Long instanceSid) {
        Map res = null;
        for (Map<String, Object> resSet : resSetList) {
            if (resSet.get("instanceSid").toString().equals(instanceSid.toString())) {
                res = resSet;
                break;
            }
        }
        return res;
    }

    private ResInstResult openVmCheck(Map<String, Object> map) {
        Long processObjectId = Long.parseLong(StringUtil.nullToEmpty(map.get("processObjectId")));

        ResInstResult resInstResult = new ResInstResult();

        //构建虚拟机属性集合
//		List<ResVmInst> resVmList = new ArrayList<ResVmInst>();
//
//		Long bizSid = null;
//		Long instanceSid = null;
//		Order order = null;
//
//		//系统日志
//		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount());
//		record.setActLevel("01");
//		record.setActTarget("审核管理");
//		record.setActName("资源检查");
//		record.setActStartDate(new Date());
//		record.setActEndDate(new Date());
//
//		try {
//			// 根据orderSid查询出订单信息
//			order = orderService.selectByPrimaryKey(processObjectId);
//			List<ServiceInstance> serviceInstances = new ArrayList<ServiceInstance>();
//			Criteria criteria = new Criteria();
//			criteria.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM);
//			if (StringUtil.isNullOrEmpty(order)) {
//				record.setActDetail("实例编号:"+processObjectId+"审核管理:资源检查");
//				criteria.put("instanceSid", processObjectId);
//				serviceInstances.add(serviceInstanceService.selectByPrimaryKey(processObjectId));
//			}
//			else {
//				criteria.put("orderId", order.getOrderId());
//				record.setActDetail("订单编号:"+order.getOrderId()+"审核管理:资源检查");
//				serviceInstances = serviceInstanceService.selectByParams(criteria);
//			}
//
//			for (ServiceInstance serviceInstance : serviceInstances) {
//				Map resSetMap = this.getInstanceResSet((List<Map<String, Object>>)map.get("resSet"), serviceInstance.getInstanceSid());
//				if(null!=resSetMap.get("allocateType")&&!StringUtil.isNullOrEmpty(resSetMap.get("allocateType").toString())
//						&& WebConstants.AllocateType.NANOTUBE.equals(resSetMap.get("allocateType").toString())){
//					resInstResult = new ResInstResult(true);
//					resInstResult.setMessage("资源检查成功！");
//					resInstResult.setStatus(ResInstResult.SUCCESS);
//				}else{
//					ServiceInstanceChangeLogSpec<ResVmInst> instanceChangeLogSpec = this.serviceInstanceService.assembleServiceInstanceOpenParams(serviceInstance, map);
//					if(instanceChangeLogSpec.getParams() != null) {
//						resVmList.add(instanceChangeLogSpec.getParams());
//					}
//				}
//			}
//			if(!CollectionUtils.isEmpty(resVmList)){
//				logger.info("openVmCheck input params:" + JsonUtil.toJson(resVmList));
//				resInstResult = resVmService.createVmCheck(resVmList);
//				logger.info("openVmCheck return result:" + JsonUtil.toJson(resInstResult));
//			}
//			record.setActResult("02");
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			record.setActResult("01");
//			resInstResult.setMessage("开通资源检查失败");
//			resInstResult.setStatus(ResInstResult.FAILURE);
//		} finally {
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);
//			log.debug(record);
//		}
        return resInstResult;
    }

    private ResInstResult reconfigVmCheck(Map<String, Object> map) {
        Long processObjectId = Long.parseLong(StringUtil.nullToEmpty(map.get("processObjectId")));
        ResInstResult resInstResult = new ResInstResult();

//		ServiceInstance serviceInstance = null;
//
//		//系统日志
//		SysTLogRecord record = new SysTLogRecord();
//		record.setAccount(AuthUtil.getAuthUser().getAccount());
//		record.setActLevel("01");
//		record.setActTarget("审核管理");
//		record.setActName("资源检查");
//		record.setActStartDate(new Date());
//		record.setActEndDate(new Date());
//
//		try {
//			//根据实例sid查询出实例信息
//			serviceInstance = serviceInstanceService.selectByPrimaryKey(processObjectId);
//			ServiceInstanceChangeLogSpec<ResVmInst> changeSpec = this.instanceChangeLogService.getChangeInfo(processObjectId, new TypeReference<ServiceInstanceChangeLogSpec<ResVmInst>>() {});
//
//			//得到当前虚机信息
//			String resSid = serviceInstResService.getResSidByInstanceSid(processObjectId);
//			ResVm resVm = resVmService.selectByPrimaryKey(resSid);
//
//			ResVmInst resVmInst = changeSpec.getParams();
//			String cpu = (String)map.get("cpu");
//			if(StringUtils.isNotBlank(cpu)) {
//				resVmInst.setCpu(Integer.parseInt(cpu) - resVm.getCpuCores());
//			}
////			String cpuMin = (String)map.get("cpuMin");
////			if(StringUtils.isNotBlank(cpuMin)) {
////				resVmInst.setCpuMin(Integer.parseInt(cpuMin) - resVm.getPowerCpuMinUnits());
////			}
////			String cpuMax = (String)map.get("cpuMax");
////			if(StringUtils.isNotBlank(cpuMax)) {
////				resVmInst.setCpuMax(Integer.parseInt(cpuMax) - resVm.getPowerCpuMaxUnits());
////			}
//			String memory = (String)map.get("memory");
//			if(StringUtils.isNotBlank(memory)) {
//				resVmInst.setMemory((Integer.parseInt(memory) * 1024) - resVm.getMemorySize() );
//			}
////			String memoryMin = (String)map.get("memoryMin");
////			if(StringUtils.isNotBlank(memoryMin)) {
////				resVmInst.setMemoryMin(Integer.parseInt(memoryMin) * 1024);
////			}
////			String memoryMax = (String)map.get("memoryMax");
////			if(StringUtils.isNotBlank(memoryMax)) {
////				resVmInst.setMemoryMax(Integer.parseInt(memoryMax) * 1024);
////			}
////			String powerCpuUsedUnit = (String)map.get("powerCpuUsedUnit");
////			if(StringUtils.isNotBlank(powerCpuUsedUnit)) {
////				resVmInst.setPowerCpuUsedUnits(Float.parseFloat(powerCpuUsedUnit));
////			}
////			String powerCpuMinUnit = (String)map.get("powerCpuMinUnit");
////			if(StringUtils.isNotBlank(powerCpuMinUnit)) {
////				resVmInst.setPowerCpuMinUnits(Float.parseFloat(powerCpuMinUnit));
////			}
////			String powerCpuMaxUnit = (String)map.get("powerCpuMaxUnit");
////			if(StringUtils.isNotBlank(powerCpuMaxUnit)) {
////				resVmInst.setPowerCpuMaxUnits(Float.parseFloat(powerCpuMaxUnit));
////			}
//			logger.info("reconfigVmCheck input params:" + JsonUtil.toJson(changeSpec.getParams()));
//			resInstResult = resVmService.reconfigVmCheck(changeSpec.getParams());
//			logger.info("reconfigVmCheck result params:" + JsonUtil.toJson(resInstResult));
//			record.setActResult("02");
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//			record.setActResult("01");
//			resInstResult.setMessage("变更资源检查失败");
//			resInstResult.setStatus(ResInstResult.FAILURE);
//		} finally {
//			record.setActDetail("服务实例名称:"+serviceInstance.getInstanceName()+"审核管理:资源检查");
//			SysLogger log = sysLogger.getLogger(LoggerTypeEnum.DB);
//			log.debug(record);
//		}
        return resInstResult;
    }

    public boolean createApprove(String checkStatus, String checkcomments, String processInstanceId, String approvorAction, String processType) {

        return true;

    }

    public boolean initApprove(String instanceSid, Long changeLogSid) {
        try {
            insertApproveRecord(instanceSid, changeLogSid);
            logger.info("服务实例变更审批流程初始化成功。");
            return true;
        } catch (Exception e) {
            logger.info("服务实例变更审批流程初始化失败");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void insertApproveRecord(String instanceSid, Long changeLogSid) {
        String activitiFlowKey = "";
        ServiceConfigService serviceConfigService = SpringContextHolder.getBean("serviceConfigServiceImpl");
        activitiFlowKey = serviceConfigService.selectActivitiFlowByServiceSid(WebConstants.ServiceSid.SERVICE_VM, WebConstants.ServiceConfigActiviti.INSTANCE_ADJUST_PROCESS);
        ActivitiWorkflowUtil activitiWorkflowUtil = new ActivitiWorkflowUtil();
        Map<String, Object> variables = new HashMap<String, Object>();
		/*
		variables.put("detailSid", 2121L);
		variables.put("orderId", "OD2014081100002");
		variables.put("blnApproveTenantQuota", "99");
		variables.put("serviceInstaceSid", 2595);
		*/
        variables.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM.toString());
//		variables.put("applyUserId", AuthUtil.getAuthUser().getAccount());
        variables.put("serviceType", WebConstants.ServiceType.VM);
        variables.put("processType", WebConstants.ProcessType.INSTANCE_ADJUST);
        variables.put("processObjectId", Long.parseLong(instanceSid));
//		variables.put("proposeBy", AuthUtil.getAuthUser().getAccount());
        variables.put("proposeDt", new Date());
        variables.put("changeLogSid", changeLogSid);
        String approveResouceMgt = PropertiesUtil.getProperty("approve.resouce.mgt");
        if ("true".equals(approveResouceMgt)) {
            variables.put("approveResouceMgt", true);
        } else if ("false".equals(approveResouceMgt)) {
            variables.put("approveResouceMgt", false);
        }
        String approveIdcMgtPre = PropertiesUtil.getProperty("approve.idc.mgt.pre");
        if ("true".equals(approveIdcMgtPre)) {
            variables.put("approveIdcMgtPre", true);
        } else if ("false".equals(approveIdcMgtPre)) {
            variables.put("approveIdcMgtPre", false);
        }

        String approveNetDeptMgt = PropertiesUtil.getProperty("approve.net.dept.mgt");
        if ("true".equals(approveNetDeptMgt)) {
            variables.put("approveNetDeptMgt", true);
        } else if ("false".equals(approveNetDeptMgt)) {
            variables.put("approveNetDeptMgt", false);
        }

        String approveInternetMgt = PropertiesUtil.getProperty("approve.internet.mgt");
        if ("true".equals(approveInternetMgt)) {
            variables.put("approveInternetMgt", true);
        } else if ("false".equals(approveInternetMgt)) {
            variables.put("approveInternetMgt", false);
        }
        ProcessInstance processInstance = activitiWorkflowUtil.startWorkflow(variables, activitiFlowKey);

//    	String userId = AuthUtil.getAuthUser().getAccount();
        ExecutionEntity entity = (ExecutionEntity) processInstance;
        List<TaskEntity> tasks = entity.getTasks();
        for (Task task : tasks) {
            //认领任务
//			taskService.claim(task.getId(), userId);
            //完成任务
            taskService.complete(task.getId());
        }
		/*ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(Long.parseLong(instanceSid));
		serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.CHANGEING);
		serviceInstance.setProcessInstanceChangeId(processInstance.getProcessInstanceId());
		serviceInstanceService.updateByPrimaryKeySelective(serviceInstance);*/
    }

    public void initCancelApproveRecord(String instanceSid, Long changeLogSid) {
        String activitiFlowKey = "";
        ServiceConfigService serviceConfigService = SpringContextHolder.getBean("serviceConfigServiceImpl");
        activitiFlowKey = serviceConfigService.selectActivitiFlowByServiceSid(WebConstants.ServiceSid.SERVICE_VM, WebConstants.ServiceConfigActiviti.CLOUD_SERVICE_CANCEL_PROCESS);
        ActivitiWorkflowUtil activitiWorkflowUtil = new ActivitiWorkflowUtil();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("serviceSid", WebConstants.ServiceSid.SERVICE_VM.toString());
//		variables.put("applyUserId", AuthUtil.getAuthUser().getAccount());
        variables.put("serviceType", WebConstants.ServiceType.VM);
        variables.put("processType", WebConstants.ProcessType.VM_CANCEL);
        variables.put("processObjectId", Long.parseLong(instanceSid));
//		variables.put("proposeBy", AuthUtil.getAuthUser().getAccount());
        variables.put("changeLogSid", changeLogSid);
        variables.put("proposeDt", new Date());
        String approveResouceMgt = PropertiesUtil.getProperty("approve.resouce.mgt");
        if ("true".equals(approveResouceMgt)) {
            variables.put("approveResouceMgt", true);
        } else if ("false".equals(approveResouceMgt)) {
            variables.put("approveResouceMgt", false);
        }
        String approveIdcMgtPre = PropertiesUtil.getProperty("approve.idc.mgt.pre");
        if ("true".equals(approveIdcMgtPre)) {
            variables.put("approveIdcMgtPre", true);
        } else if ("false".equals(approveIdcMgtPre)) {
            variables.put("approveIdcMgtPre", false);
        }

        String approveNetDeptMgt = PropertiesUtil.getProperty("approve.net.dept.mgt");
        if ("true".equals(approveNetDeptMgt)) {
            variables.put("approveNetDeptMgt", true);
        } else if ("false".equals(approveNetDeptMgt)) {
            variables.put("approveNetDeptMgt", false);
        }

        String approveInternetMgt = PropertiesUtil.getProperty("approve.internet.mgt");
        if ("true".equals(approveInternetMgt)) {
            variables.put("approveInternetMgt", true);
        } else if ("false".equals(approveInternetMgt)) {
            variables.put("approveInternetMgt", false);
        }
        ProcessInstance processInstance = activitiWorkflowUtil.startWorkflow(variables, activitiFlowKey);

//    	String userId = AuthUtil.getAuthUser().getAccount();
        ExecutionEntity entity = (ExecutionEntity) processInstance;
        List<TaskEntity> tasks = entity.getTasks();
        for (Task task : tasks) {
            //认领任务
//			taskService.claim(task.getId(), userId);
            //完成任务
            taskService.complete(task.getId());
        }
    }

    /**
     * 将审核结果写入审核记录
     */
    private void updateApproveRecord(String processInstanceId, String checkStatus, String checkcomments, String approvorAction, String processType) {
        // 更新租户管理员审批结果
        Criteria criteria = new Criteria();
        criteria.put("processInstanceId", processInstanceId);
        criteria.setOrderByClause("A.APPROVE_RECORD_SID DESC");
        ApproveRecord approveRecord = this.approveRecordMapper.selectByParams(criteria).get(0);
//		approveRecord.setApprovorId(AuthUtil.getAuthUser().getAccount());

        approveRecord.setApproveStatus(checkStatus);
        approveRecord.setApproveOpinion(checkcomments);
        approveRecord.setApproveDate(new Date());
        WebUtil.prepareUpdateParams(approveRecord);
        this.approveRecordMapper.updateByPrimaryKeySelective(approveRecord);
    }

    // 插入审核记录表
    private void updateOneDataApproveRecord(String processInstanceId, String checkStatus, String checkcomments,
                                            String approvorAction, String processType) {

        // 更新租户管理员审批结果
        Criteria criteria = new Criteria();
        criteria.put("processInstanceId", processInstanceId);
        criteria.put("approvorAction", "");
        ApproveRecord approveRecord = this.approveRecordMapper.selectByParams(criteria).get(0);
//		approveRecord.setApprovorId(AuthUtil.getAuthUser().getAccount());
        approveRecord.setApprovorAction(approvorAction);
        approveRecord.setApproveStatus(checkStatus);
        approveRecord.setApproveOpinion(checkcomments);
        approveRecord.setApproveDate(new Date());
        this.approveRecordMapper.updateByPrimaryKeySelective(approveRecord);

    }

    /**
     * 查询审核历史信息
     */
    public List<ApproveRecord> findApproveHistoryRecords(String processInstanceId) {

        ApproveRecordService approveRecordService = SpringContextHolder.getBean("approveRecordServiceImpl");

        Criteria criteria = new Criteria();
        criteria.put("processInstanceId", processInstanceId);
        List<ApproveRecord> approveRecords = approveRecordService.selectByParams(criteria);
        List<ApproveRecord> approveRecordList = new ArrayList<ApproveRecord>();

        for (ApproveRecord approveRecord : approveRecords) {
            if (approveRecord.getApprovorId() != null && !StringUtil.EMPTY.equals(approveRecord.getApprovorId())) {
                approveRecordList.add(approveRecord);
            }
        }
        return approveRecordList;
    }

    public boolean initChangeApprove(String sid, Long changeLogSid, String processType) {
        try {
            insertChangeApproveRecord(sid, changeLogSid, processType);
            logger.info("其他变更审批流程初始化成功。");
            return true;
        } catch (Exception e) {
            logger.info("其他变更审批流程初始化失败");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void insertChangeApproveRecord(String sid, Long changeLogSid, String processType) {
        String activitiFlowKey = "";
        ServiceConfigService serviceConfigService = SpringContextHolder.getBean("serviceConfigServiceImpl");
        Long serviceSid = 0L;
        String activitiName = "";
        if (WebConstants.ProcessType.MANAGER_CHANGE.equals(processType)) {
            activitiName = WebConstants.ServiceConfigActiviti.CHANGE_MANAGER_PROCESS;
            serviceSid = WebConstants.ServiceSid.CHANGE_MANAGER_SERVICE;
        } else if (WebConstants.ProcessType.MGT_OBJ_CHANGE.equals(processType)) {
            activitiName = WebConstants.ServiceConfigActiviti.CHANGE_VM_MGT_OBJ_PROCESS;
            serviceSid = WebConstants.ServiceSid.CHANGE_MGTOBJ_SERVICE;
        }
        activitiFlowKey = serviceConfigService.selectActivitiFlowByServiceSid(serviceSid, activitiName);
        ActivitiWorkflowUtil activitiWorkflowUtil = new ActivitiWorkflowUtil();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("serviceSid", serviceSid.toString());
//		variables.put("applyUserId", AuthUtil.getAuthUser().getAccount());
        variables.put("serviceType", WebConstants.ServiceType.OTHER);
        variables.put("processType", processType);
        variables.put("processObjectId", Long.parseLong(sid));
//		variables.put("proposeBy", AuthUtil.getAuthUser().getAccount());
        variables.put("proposeDt", new Date());
        variables.put("changeLogSid", changeLogSid);
        String approveResouceMgt = PropertiesUtil.getProperty("approve.resouce.mgt");
        if ("true".equals(approveResouceMgt)) {
            variables.put("approveResouceMgt", true);
        } else if ("false".equals(approveResouceMgt)) {
            variables.put("approveResouceMgt", false);
        }

        String approveIdcMgtPre = PropertiesUtil.getProperty("approve.idc.mgt.pre");
        if ("true".equals(approveIdcMgtPre)) {
            variables.put("approveIdcMgtPre", true);
        } else if ("false".equals(approveIdcMgtPre)) {
            variables.put("approveIdcMgtPre", false);
        }

        String approveNetDeptMgt = PropertiesUtil.getProperty("approve.net.dept.mgt");
        if ("true".equals(approveNetDeptMgt)) {
            variables.put("approveNetDeptMgt", true);
        } else if ("false".equals(approveNetDeptMgt)) {
            variables.put("approveNetDeptMgt", false);
        }

        String approveInternetMgt = PropertiesUtil.getProperty("approve.internet.mgt");
        if ("true".equals(approveInternetMgt)) {
            variables.put("approveInternetMgt", true);
        } else if ("false".equals(approveInternetMgt)) {
            variables.put("approveInternetMgt", false);
        }
        ProcessInstance processInstance = activitiWorkflowUtil.startWorkflow(variables, activitiFlowKey);

//    	String userId = AuthUtil.getAuthUser().getAccount();
        ExecutionEntity entity = (ExecutionEntity) processInstance;
        List<TaskEntity> tasks = entity.getTasks();
        for (Task task : tasks) {
            //认领任务
//			taskService.claim(task.getId(), userId);
            //完成任务
            taskService.complete(task.getId());
        }
        if (WebConstants.ProcessType.MANAGER_CHANGE.equals(processType)) {
            MgtObj mgtObj = mgtObjService.selectByPrimaryKey(Long.parseLong(sid));
            mgtObj.setStatus(WebConstants.MGT_OBJ_STATUS.SETTING);
            mgtObjService.updateByPrimaryKeySelective(mgtObj);
        } else if (WebConstants.ProcessType.MGT_OBJ_CHANGE.equals(processType)) {
			/*ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(Long.parseLong(sid));
			serviceInstance.setStatus(WebConstants.ServiceInstanceStatus.CHANGEING);
			serviceInstance.setProcessInstanceChangeId(processInstance.getProcessInstanceId());
			serviceInstanceService.updateByPrimaryKeySelective(serviceInstance);*/
        }
    }

}