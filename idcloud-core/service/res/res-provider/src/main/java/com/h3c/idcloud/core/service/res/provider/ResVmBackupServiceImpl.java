package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRebuild;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotDelete;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmBackupMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVmBackup;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.res.api.ResVmBackupService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0")
@Component
public class ResVmBackupServiceImpl implements ResVmBackupService {
    private static final Logger logger = LoggerFactory.getLogger(ResVmBackupServiceImpl.class);
    @Autowired
    private ResVmBackupMapper resVmBackupMapper;
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private ResVmService resVmService;
    @Autowired
    private ResHostMapper resHostMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;

    @Override
    public ResInstResult createSnapShot(ResVmBackup vmBackup, MgtObj mgtObj) {
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("创建虚机快照");
        log.setTaskType(WebConstants.TaskType.CREATE_VM_SNAPSHOT);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            //添加快照信息
            this.resVmBackupMapper.insertSelective(vmBackup);

            ResVm resVm = resVmMapper.selectByPrimaryKey(vmBackup.getResVmSid());
            ResHost host = resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
            ResVe resVe = resVmService.getVeByRes(host);

            //传入MQ，在监听中更新对应的快照所缺少的信息
            VmSnapshotCreate vmSnapshotCreate = new VmSnapshotCreate();
            vmSnapshotCreate.setVmName(resVm.getVmName());
            vmSnapshotCreate.setSnapshotName(vmBackup.getBackupName());
            vmSnapshotCreate.setSnapshotDesp("");

            vmSnapshotCreate.setServerId(resVm.getUuid());

            vmSnapshotCreate.setProviderType(resVe.getVirtualPlatformType());
            vmSnapshotCreate.setAuthUrl(resVe.getManagementUrl());
            vmSnapshotCreate.setAuthUser(resVe.getManagementAccount());
            vmSnapshotCreate.setAuthPass(resVe.getManagementPassword());
            vmSnapshotCreate.setVirtEnvType(resVe.getVirtualPlatformType());
            vmSnapshotCreate.setVirtEnvUuid(resVe.getResTopologySid().toString());

            vmSnapshotCreate.setTenantId(mgtObj.getUuid());
            vmSnapshotCreate.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            //			vmSnapshotCreate.setTenantName("demo");

            logger.info("输入MQ参数：" + JsonUtil.toJson(vmSnapshotCreate));
            String msgId = MQHelper.sendMessage(vmSnapshotCreate);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("创建虚拟机快照异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    @Override
    public ResInstResult deleteVmSnapShot(long resVmBackupSid, MgtObj mgtObj) {
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("删除虚机快照");
        log.setTaskType(WebConstants.TaskType.DELETE_VM_SNAPSHOT);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            //添加快照信息
            ResVmBackup vmBackup = this.resVmBackupMapper.selectByPrimaryKey(resVmBackupSid);

            //状态更新成删除中（执行中）
            vmBackup.setStatus(WebConstants.BACKUP_STATUS.DELETING);
            resVmBackupMapper.updateByPrimaryKeySelective(vmBackup);

            ResVm resVm = resVmMapper.selectByPrimaryKey(vmBackup.getResVmSid());
            ResHost host = resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
            ResVe resVe = resVmService.getVeByRes(host);

            //传入MQ，在监听中更新对应的快照所缺少的信息
            VmSnapshotDelete vmSnapshotDelete = new VmSnapshotDelete();
            vmSnapshotDelete.setResId(vmBackup.getBackupSid().toString());
            vmSnapshotDelete.setVmName(resVm.getVmName());
            vmSnapshotDelete.setSnapshotName(vmBackup.getBackupName());
            vmSnapshotDelete.setImageId(vmBackup.getAllocateBackupId());
            vmSnapshotDelete.setServerId(resVm.getUuid());

            vmSnapshotDelete.setProviderType(resVe.getVirtualPlatformType());
            vmSnapshotDelete.setAuthUrl(resVe.getManagementUrl());
            vmSnapshotDelete.setAuthUser(resVe.getManagementAccount());
            vmSnapshotDelete.setAuthPass(resVe.getManagementPassword());
            vmSnapshotDelete.setVirtEnvType(resVe.getVirtualPlatformType());
            vmSnapshotDelete.setVirtEnvUuid(resVe.getResTopologySid().toString());

            vmSnapshotDelete.setTenantId(mgtObj.getUuid());
            vmSnapshotDelete.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            //			vmSnapshotDelete.setTenantName("demo");

            logger.info("输入MQ参数：" + JsonUtil.toJson(vmSnapshotDelete));
            String msgId = MQHelper.sendMessage(vmSnapshotDelete);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("删除虚拟机快照异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    @Override
    public ResInstResult revertVmBySnapshot(long backupSid, MgtObj mgtObj) {
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("恢复虚机快照");
        // log.setTaskTarget(resNetwork.getNetworkName());
        log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            //添加快照信息
            ResVmBackup vmBackup = this.resVmBackupMapper.selectByPrimaryKey(backupSid);

            ResVm resVm = resVmMapper.selectByPrimaryKey(vmBackup.getResVmSid());
            ResHost host = resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
            ResVe resVe = resVmService.getVeByRes(host);

            //更新vm状态为恢复中
            resVm.setStatus(WebConstants.ResVmStatus.RECOVERING);
            resVmMapper.updateByPrimaryKeySelective(resVm);

            //传入MQ，在监听中更新对应的快照所缺少的信息
            VmRebuild vmRebuild = new VmRebuild();
            vmRebuild.setImageId(vmBackup.getAllocateBackupId());
            vmRebuild.setId(resVm.getUuid());
            vmRebuild.setProviderType(resVe.getVirtualPlatformType());
            vmRebuild.setAuthUrl(resVe.getManagementUrl());
            vmRebuild.setAuthUser(resVe.getManagementAccount());
            vmRebuild.setAuthPass(resVe.getManagementPassword());
            vmRebuild.setVirtEnvType(resVe.getVirtualPlatformType());
            vmRebuild.setVirtEnvUuid(resVe.getResTopologySid().toString());

            vmRebuild.setTenantId(mgtObj.getUuid());
            vmRebuild.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            //			vmRebuild.setTenantName("demo");

            logger.info("输入MQ参数：" + JsonUtil.toJson(vmRebuild));
            String msgId = MQHelper.sendMessage(vmRebuild);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("恢复虚拟机快照异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    public List<ResVmBackup> selectByParams(Criteria example) {
        return this.resVmBackupMapper.selectByParams(example);
    }

    public int updateByPrimaryKeySelective(ResVmBackup record) {
        return this.resVmBackupMapper.updateByPrimaryKeySelective(record);
    }
}