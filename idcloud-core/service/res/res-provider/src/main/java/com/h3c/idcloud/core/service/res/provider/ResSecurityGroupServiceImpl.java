package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.admin.SgCreate;
import com.h3c.idcloud.core.adapter.pojo.admin.SgDelete;
import com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupQuery;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupAdd;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupDelete;
import com.h3c.idcloud.core.adapter.pojo.network.result.SecurityGroupQueryResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.ServerSecurityGroupAddResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.ServerSecurityGroupDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SgDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuerySgs;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmQuerySgsResult;
import com.h3c.idcloud.core.persist.res.dao.ResSecurityGroupMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResSecurityGroup;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResSecurityGroupInst;
import com.h3c.idcloud.core.service.res.api.ResSecurityGroupService;
import com.h3c.idcloud.core.service.system.api.MgtObjService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.h3c.idcloud.infrastructure.common.constants.WebConstants.TaskType;

@Service(version = "1.0.0")
@Component
public class ResSecurityGroupServiceImpl implements ResSecurityGroupService {
    private static final Logger logger = LoggerFactory.getLogger(ResSecurityGroupServiceImpl.class);

    @Autowired
    private ResSecurityGroupMapper resSecurityGroupMapper;

    @Reference(version = "1.0.0")
    private MgtObjService mgtObjService;

    @Autowired
    private ResVeMapper resVeMapper;

    @Autowired
    private ResVmMapper resVmMapper;

    @Autowired
    private TaskLoggerFactory taskLogger;


    /**
     * 创建安全组
     */
    @Override
    public SgCreateResult createSg(ResSecurityGroupInst resSecurityGroupInst) {
        MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(resSecurityGroupInst.getMgtObjSid());
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("创建安全组");
        log.setTaskTarget(resSecurityGroupInst.getSecurityGourpName());
        log.setTaskType(WebConstants.TaskType.CREATE_SECURITY_GROUP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        SgCreateResult result = new SgCreateResult();
        try {
            ResSecurityGroup resSecurityGroup = new ResSecurityGroup();
            resSecurityGroup.setMgtObjSid(resSecurityGroupInst.getMgtObjSid());
            resSecurityGroup.setSecurityGroupName(resSecurityGroupInst.getSecurityGourpName());
            resSecurityGroup.setDescription(resSecurityGroupInst.getDescription());
            resSecurityGroup.setStatus(WebConstants.SECURITY_GROUP_STATUS.CREATING);
            resSecurityGroupMapper.insertSelective(resSecurityGroup);

            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            SgCreate sgCreate = new SgCreate();

            sgCreate.setProviderType(resVe.getVirtualPlatformType());
            sgCreate.setAuthUrl(resVe.getManagementUrl());
            sgCreate.setAuthUser(resVe.getManagementAccount());
            sgCreate.setAuthPass(resVe.getManagementPassword());
            sgCreate.setVirtEnvType(resVe.getVirtualPlatformType());
            sgCreate.setVirtEnvUuid(resVe.getResTopologySid());
            sgCreate.setTenantId(mgtObj.getUuid());
            sgCreate.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
//			sgCreate.setTenantName("lileifeng0001");
//			sgCreate.setTenantId("34f11c8cba8f42fda9000982fbdcf51d");

            sgCreate.setResId(resSecurityGroup.getId() + "");
            sgCreate.setName(resSecurityGroupInst.getSecurityGourpName());
            sgCreate.setDescription(resSecurityGroupInst.getDescription());

            logger.info("输入MQ参数：" + JsonUtil.toJson(sgCreate));
            Object msgId = MQHelper.rpc(sgCreate);
            result = (SgCreateResult) msgId;
        } catch (Exception e) {
            e.printStackTrace();
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("创建安全组异常：" + e.getMessage());
            taskLogger.fail(log);
        }
        return result;
    }

    /**
     * 删除安全组
     */
    @Override
    public SgDeleteResult deleteSg(String groupInstSid) {
        //		ResSecurityGroup securityGroup = resSecurityGroupMapper.selectByPrimaryKey(Long.parseLong(resSecurityGroupInstSid));
        MgtObj mgtObj = this.mgtObjService.selectFirstUserMgtObj(AuthUtil.getAuthUser().getUserSid());
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("删除安全组");
//		 log.setTaskTarget(securityGroup.getSecurityGroupName());
        log.setTaskType(TaskType.DELETE_SECURITY_GROUP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        SgDeleteResult result = new SgDeleteResult();
        try {
//			securityGroup.setStatus(WebConstants.SECURITY_GROUP_STATUS.DELETING);
//			resSecurityGroupMapper.updateByPrimaryKeySelective(securityGroup);

            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            SgDelete sgDelete = new SgDelete();

            sgDelete.setProviderType(resVe.getVirtualPlatformType());
            sgDelete.setAuthUrl(resVe.getManagementUrl());
            sgDelete.setAuthUser(resVe.getManagementAccount());
            sgDelete.setAuthPass(resVe.getManagementPassword());
            sgDelete.setVirtEnvType(resVe.getVirtualPlatformType());
            sgDelete.setVirtEnvUuid(resVe.getResTopologySid());
            sgDelete.setTenantId(mgtObj.getUuid());
            sgDelete.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            sgDelete.setSecurityGroupId(groupInstSid);

            logger.info("输入MQ参数：" + JsonUtil.toJson(sgDelete));
            Object msgId = MQHelper.rpc(sgDelete);
            result = (SgDeleteResult) msgId;
        } catch (Exception e) {
            e.printStackTrace();
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("删除安全组异常：" + e.getMessage());
            taskLogger.fail(log);
        }
        return result;
    }

    @Override
    public ServerSecurityGroupAddResult attach(String sgId, String vmId) {
        //		ResSecurityGroup securityGroup = resSecurityGroupMapper.selectByPrimaryKey(Long.parseLong(sgId));
        MgtObj mgtObj = this.mgtObjService.selectFirstUserMgtObj(AuthUtil.getAuthUser().getUserSid());

        ResVm resVm = resVmMapper.selectByPrimaryKey(vmId);

        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("绑定安全组");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(TaskType.BUND_SECURITY_GROUP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ServerSecurityGroupAddResult groupAddResult = new ServerSecurityGroupAddResult();
        try {
            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            ServerSecurityGroupAdd groupAdd = new ServerSecurityGroupAdd();

            groupAdd.setProviderType(resVe.getVirtualPlatformType());
            groupAdd.setAuthUrl(resVe.getManagementUrl());
            groupAdd.setAuthUser(resVe.getManagementAccount());
            groupAdd.setAuthPass(resVe.getManagementPassword());
            groupAdd.setVirtEnvType(resVe.getVirtualPlatformType());
            groupAdd.setVirtEnvUuid(resVe.getResTopologySid());
            groupAdd.setTenantId(mgtObj.getUuid());
            groupAdd.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));

            groupAdd.setResId(resVm.getResVmSid());
            groupAdd.setSgName(sgId);
            groupAdd.setServerId(resVm.getUuid());

            logger.info("输入MQ参数：" + JsonUtil.toJson(groupAdd));
            Object result = MQHelper.rpc(groupAdd);
            groupAddResult = (ServerSecurityGroupAddResult) result;
        } catch (Exception e) {
            e.printStackTrace();
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("绑定安全组异常：" + e.getMessage());
            taskLogger.fail(log);
        }
        return groupAddResult;
    }

    @Override
    public ServerSecurityGroupDeleteResult detach(String sgId, String vmId) {
        MgtObj mgtObj = this.mgtObjService.selectFirstUserMgtObj(AuthUtil.getAuthUser().getUserSid());

        ResVm resVm = resVmMapper.selectByPrimaryKey(vmId);

        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("解绑安全组");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(TaskType.BUND_SECURITY_GROUP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ServerSecurityGroupDeleteResult groupDeleteResult = new ServerSecurityGroupDeleteResult();
        try {
            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            ServerSecurityGroupDelete groupDelete = new ServerSecurityGroupDelete();

            groupDelete.setProviderType(resVe.getVirtualPlatformType());
            groupDelete.setAuthUrl(resVe.getManagementUrl());
            groupDelete.setAuthUser(resVe.getManagementAccount());
            groupDelete.setAuthPass(resVe.getManagementPassword());
            groupDelete.setVirtEnvType(resVe.getVirtualPlatformType());
            groupDelete.setVirtEnvUuid(resVe.getResTopologySid());
            groupDelete.setTenantId(mgtObj.getUuid());
            groupDelete.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));

            groupDelete.setName(sgId);
            groupDelete.setServerId(resVm.getUuid());

            logger.info("输入MQ参数：" + JsonUtil.toJson(groupDelete));
            Object result = MQHelper.rpc(groupDelete);
            groupDeleteResult = (ServerSecurityGroupDeleteResult) result;
        } catch (Exception e) {
            e.printStackTrace();
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("解绑安全组异常：" + e.getMessage());
            taskLogger.fail(log);
        }
        return groupDeleteResult;
    }

    @Override
    public VmQuerySgsResult selectByVm(String vmId, String mgtObjSid) {
        MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(Long.parseLong(mgtObjSid));
        ResVm resVm = resVmMapper.selectByPrimaryKey(vmId);

        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("查询虚拟机安全组");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(TaskType.BUND_SECURITY_GROUP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        VmQuerySgsResult querySgsResult = new VmQuerySgsResult();
        try {
            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            VmQuerySgs vmQuerySgs = new VmQuerySgs();

            vmQuerySgs.setProviderType(resVe.getVirtualPlatformType());
            vmQuerySgs.setAuthUrl(resVe.getManagementUrl());
            vmQuerySgs.setAuthUser(resVe.getManagementAccount());
            vmQuerySgs.setAuthPass(resVe.getManagementPassword());
            vmQuerySgs.setVirtEnvType(resVe.getVirtualPlatformType());
            vmQuerySgs.setVirtEnvUuid(resVe.getResTopologySid().toString());
            vmQuerySgs.setTenantId(mgtObj.getUuid());
            vmQuerySgs.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));

            vmQuerySgs.setServerId(resVm.getUuid());

            logger.info("输入MQ参数：" + JsonUtil.toJson(vmQuerySgs));
            Object result = MQHelper.rpc(vmQuerySgs);
            querySgsResult = (VmQuerySgsResult) result;
        } catch (Exception e) {
            e.printStackTrace();
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("绑定安全组异常：" + e.getMessage());
            taskLogger.fail(log);
        }
        return querySgsResult;
    }

    @Override
    public SecurityGroupQueryResult selectSecurityGroups(String mgtObjSid) {
        MgtObj mgtObj = this.mgtObjService.selectByPrimaryKey(Long.parseLong(mgtObjSid));
        SecurityGroupQueryResult querySgsResult = new SecurityGroupQueryResult();
        try {
            String resVeSid = PropertiesUtil.getProperty("kvm.resve");
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resVeSid);
            SecurityGroupQuery vmQuerySgs = new SecurityGroupQuery();
//
            if(resVe!=null) {
                vmQuerySgs.setProviderType(resVe.getVirtualPlatformType());
                vmQuerySgs.setProviderUrl(resVe.getManagementUrl());
                vmQuerySgs.setAuthUser(resVe.getManagementAccount());
                vmQuerySgs.setAuthPass(resVe.getManagementPassword());
                vmQuerySgs.setVirtEnvType(resVe.getVirtualPlatformType());
                vmQuerySgs.setVirtEnvUuid(resVe.getResTopologySid());
                vmQuerySgs.setTenantId(mgtObj.getUuid());
                vmQuerySgs.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            }
            logger.info("输入MQ参数：" + JsonUtil.toJson(vmQuerySgs));
            Object result = MQHelper.rpc(vmQuerySgs);
            querySgsResult = (SecurityGroupQueryResult) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return querySgsResult;
    }
    public List<ResSecurityGroup> selectByParams(Criteria example) {
        return this.resSecurityGroupMapper.selectByParams(example);
    }
    public int updateByPrimaryKeySelective(ResSecurityGroup record) {
        return this.resSecurityGroupMapper.updateByPrimaryKeySelective(record);
    }
}