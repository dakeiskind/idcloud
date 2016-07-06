package com.h3c.idcloud.core.service.res.provider;


import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.block.BlockBackupCreate;
import com.h3c.idcloud.core.adapter.pojo.block.BlockBackupRecovery;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotCreate;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotDelete;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotRecovery;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdBackupMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.system.dao.MgtObjMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVdBackup;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.res.api.ResVdBackupService;
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

@Component
public class ResVdBackupServiceImpl implements ResVdBackupService {
    private static final Logger logger = LoggerFactory.getLogger(ResVdBackupServiceImpl.class);
    @Autowired
    private ResVdBackupMapper resVdBackupMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private ResVdMapper resVdMapper;
    @Autowired
    private ResStorageMapper resStorageMapper;
    @Autowired
    private ResVeMapper resVeMapper;
    @Autowired
    private MgtObjMapper mgtObjMapper;
    @Autowired
    private ResVmService resVmService;

    /**
     * 创建块存储快照
     */
    @Override
    public ResInstResult createSnapShot(ResVdBackup vdBackup, MgtObj mgtObj) {
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        ResVd resvd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
        log.setTaskDetail("创建块存储快照");
        log.setTaskType(WebConstants.TaskType.CREATE_VD_SNAPSHOT);
        log.setTaskTarget(resvd.getVdName());
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            this.resVdBackupMapper.insertSelective(vdBackup);
            ResVd resVd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(resStorage.getParentTopologySid());

            BlockSnapshotCreate blockSnapshotCreate = new BlockSnapshotCreate();
            blockSnapshotCreate.setProviderType(resVe.getVirtualPlatformType());
            blockSnapshotCreate.setTenantId(mgtObj.getUuid());
            blockSnapshotCreate.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            blockSnapshotCreate.setAuthUser(resVe.getManagementAccount());
            blockSnapshotCreate.setAuthPass(resVe.getManagementPassword());
            blockSnapshotCreate.setAuthUrl(resVe.getManagementUrl());
            blockSnapshotCreate.setVirtEnvType(resVe.getVirtualPlatformType());
            blockSnapshotCreate.setVirtEnvUuid(resVe.getResTopologySid());
            blockSnapshotCreate.setName(vdBackup.getBackupName());
            blockSnapshotCreate.setVolumeId(vdBackup.getResVdSid());

            logger.info("输入MQ参数：" + JsonUtil.toJson(blockSnapshotCreate));
            String msgId = MQHelper.sendMessage(blockSnapshotCreate);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("创建块存储快照异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    @Override
    public ResInstResult createBackup(ResVdBackup vdBackup, MgtObj mgtObj) {
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        ResVd resvd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
        log.setTaskDetail("创建块存储备份");
        log.setTaskType(WebConstants.TaskType.CREATE_VD_BACKUP);
        log.setTaskTarget(resvd.getVdName());
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            this.resVdBackupMapper.insertSelective(vdBackup);
            ResVd resVd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
            ResVe resve = this.resVeMapper.selectByPrimaryKey(resStorage.getParentTopologySid());

            BlockBackupCreate blockBackupCreate = new BlockBackupCreate();
            blockBackupCreate.setProviderType(resve.getVirtualPlatformType());
            blockBackupCreate.setTenantId(mgtObj.getUuid());
            blockBackupCreate.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            blockBackupCreate.setAuthUser(resve.getManagementAccount());
            blockBackupCreate.setAuthPass(resve.getManagementPassword());
            blockBackupCreate.setAuthUrl(resve.getManagementUrl());
            blockBackupCreate.setVirtEnvType(resve.getVirtualPlatformType());
            blockBackupCreate.setVirtEnvUuid(resve.getResTopologySid());
            blockBackupCreate.setName(vdBackup.getBackupName());
            blockBackupCreate.setVolumeId(resVd.getUuid());

            logger.info("输入MQ参数：" + JsonUtil.toJson(blockBackupCreate));
            String msgId = MQHelper.sendMessage(blockBackupCreate);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("创建块存储备份异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    /**
     * 获取虚机下快照集合
     */
    @Override
    public List<ResVdBackup> getSnapShotList(String resId) {
        Criteria criteria = new Criteria();
        criteria.put("resVdSid", resId);
        criteria.put("backupType", WebConstants.BACKUP_TYPE.SNAPSHOT);
        List<ResVdBackup> resVdBackupList = this.resVdBackupMapper.selectByParams(criteria);
        return resVdBackupList;
    }

    @Override
    public List<ResVdBackup> getBackupList(String resId) {
        Criteria criteria = new Criteria();
        criteria.put("resVdSid", resId);
        criteria.put("backupType", WebConstants.BACKUP_TYPE.BACKUP);
        List<ResVdBackup> resVdBackupList = this.resVdBackupMapper.selectByParams(criteria);
        return resVdBackupList;
    }


    @Override
    public ResInstResult revertVdBySnapshot(ResVdBackup vdBackup, MgtObj mgtObj) {
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        ResVd resvd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
        log.setTaskDetail("恢复块存储快照");
        log.setTaskType(WebConstants.TaskType.REVERT_VD_SNAPSHOT);
        log.setTaskTarget(resvd.getVdName());
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            //			this.resVdBackupMapper.insertSelective(vdBackup);
            ResVd resVd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
            ResVe resve = this.resVeMapper.selectByPrimaryKey(resStorage.getParentTopologySid());

            resVd.setStatus(WebConstants.ResVdStatus.RECOVERING);
            resVdMapper.updateByPrimaryKeySelective(resVd);

            BlockSnapshotRecovery snapshot = new BlockSnapshotRecovery();
            snapshot.setProviderType(resve.getVirtualPlatformType());
            snapshot.setTenantId(mgtObj.getUuid());
            snapshot.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            snapshot.setAuthUser(resve.getManagementAccount());
            snapshot.setAuthPass(resve.getManagementPassword());
            snapshot.setAuthUrl(resve.getManagementUrl());
            snapshot.setVirtEnvType(resve.getVirtualPlatformType());
            snapshot.setVirtEnvUuid(resve.getResTopologySid());
            snapshot.setSnapshotId(vdBackup.getAllocateBackupId());
            snapshot.setVolumeId(resVd.getUuid());

            logger.info("输入MQ参数：" + JsonUtil.toJson(snapshot));
            String msgId = MQHelper.sendMessage(snapshot);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("恢复块存储备份异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }


    @Override
    public ResInstResult revertVdByBackup(ResVdBackup vdBackup, MgtObj mgtObj) {
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        ResVd resvd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
        log.setTaskDetail("恢复块存储备份");
        log.setTaskType(WebConstants.TaskType.REVERT_VD_BACKUP);
        log.setTaskTarget(resvd.getVdName());
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            ResVd resVd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
            ResVe resve = this.resVeMapper.selectByPrimaryKey(resStorage.getParentTopologySid());

            resVd.setStatus(WebConstants.ResVdStatus.RECOVERING);
            resVdMapper.updateByPrimaryKeySelective(resVd);

            BlockBackupRecovery backup = new BlockBackupRecovery();


            backup.setProviderType(resve.getVirtualPlatformType());
            backup.setTenantId(mgtObj.getUuid());
            backup.setTenantName(mgtObj.getMgtObjSid().toString());
            backup.setVirtEnvType(resve.getVirtualPlatformType());
            backup.setVirtEnvUuid(resve.getResTopologySid());
            backup.setBackupId(vdBackup.getAllocateBackupId());
            backup.setVolume(vdBackup.getBackupName());
            backup.setVolumeId(resVd.getUuid());
            backup.setAuthUser(resve.getManagementAccount());
            backup.setAuthPass(resve.getManagementPassword());
            backup.setAuthUrl(resve.getManagementUrl());
            logger.info("输入MQ参数：" + JsonUtil.toJson(backup));
            String msgId = MQHelper.sendMessage(backup);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("恢复块存储备份异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    /**
     * 删除快照
     */
    @Override
    public ResInstResult deleteVdBySnapShot(ResVdBackup vdBackup, MgtObj mgtObj) {
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        ResVd resvd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
        log.setTaskDetail("删除块存储快照");
        log.setTaskType(WebConstants.TaskType.DELETE_VD_SNAPSHOT);
        log.setTaskTarget(resvd.getVdName());
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            ResVd resVd = this.resVdMapper.selectByPrimaryKey(vdBackup.getResVdSid());
            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
            ResVe resve = this.resVeMapper.selectByPrimaryKey(resStorage.getParentTopologySid());


            vdBackup.setStatus(WebConstants.ResVdStatus.DELETING);
            resVdBackupMapper.updateByPrimaryKeySelective(vdBackup);
            BlockSnapshotDelete blockSnapshotDelete = new BlockSnapshotDelete();

            blockSnapshotDelete.setVolumeId(resVd.getUuid());
            blockSnapshotDelete.setSnapshotId(vdBackup.getAllocateBackupId());
            blockSnapshotDelete.setProviderType(resve.getVirtualPlatformType());
            blockSnapshotDelete.setTenantId(mgtObj.getUuid());
            blockSnapshotDelete.setTenantName(mgtObj.getMgtObjSid().toString());
            blockSnapshotDelete.setVirtEnvType(resve.getVirtualPlatformType());
            blockSnapshotDelete.setVirtEnvUuid(resve.getResTopologySid());
            blockSnapshotDelete.setAuthUser(resve.getManagementAccount());
            blockSnapshotDelete.setAuthPass(resve.getManagementPassword());
            blockSnapshotDelete.setAuthUrl(resve.getManagementUrl());
            logger.info("输入MQ参数：" + JsonUtil.toJson(blockSnapshotDelete));
            String msgId = MQHelper.sendMessage(blockSnapshotDelete);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("删除块存储快照异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }


    /**
     * 删除虚拟磁盘
     */
    @Override
    public ResInstResult deleteVd(String resVdSid, long mgtObjSid) {
        ResVd resVd = this.resVdMapper.selectByPrimaryKey(resVdSid);
        MgtObj mgtObj = this.mgtObjMapper.selectByPrimaryKey(mgtObjSid);
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("删除块存储备份");
        log.setTaskTarget(resVd.getVdName());
        log.setTaskType(WebConstants.TaskType.DELETE_VD_BACKUP);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        try {
            DiskDelete diskDelete = new DiskDelete();
            Criteria example = new Criteria();
            example.put("resVdSid", resVdSid);
            List<ResVdBackup> backup = this.resVdBackupMapper.selectByParams(example);
            ResVdBackup vdBackup = this.resVdBackupMapper.selectByPrimaryKey(backup.get(0).getBackupSid());
            vdBackup.setStatus(WebConstants.ResVdStatus.DELETING);
            this.resVdBackupMapper.updateByPrimaryKeySelective(vdBackup);
            // 组装参数，调用MQ
            ResStorage resSto = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
            ResVe resVe = this.resVmService.getVeByRes(resSto);
            diskDelete.setProviderType(resVe.getVirtualPlatformType());
            diskDelete.setVirtEnvType(resVe.getVirtualPlatformType());
            diskDelete.setVirtEnvUuid(resVe.getResTopologySid());
            diskDelete.setId(vdBackup.getAllocateBackupId());
            diskDelete.setTenantId(mgtObj.getUuid());
            diskDelete.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            diskDelete.setAuthUser(resVe.getManagementAccount());
            diskDelete.setAuthPass(resVe.getManagementPassword());
            diskDelete.setAuthUrl(resVe.getManagementUrl());
            //由于目前底层Openstack中块存储和块存储备份在同一个列表中，用0和1区分块存储和块存储备份
            diskDelete.setDiskType("1");//块存储备份类型
            // TODO 缺少虚拟机的ID
            logger.info("输入MQ参数：" + JsonUtil.toJson(diskDelete));
            String msgId = MQHelper.sendMessage(diskDelete);
            ResInstResult result = new ResInstResult(ResInstResult.SUCCESS);
            return result;
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("删除块存储备份异常：" + e.getMessage());
            taskLogger.fail(log);
            ResInstResult result = new ResInstResult(ResInstResult.FAILURE, "删除块存储备份异常：" + e.getMessage());
            return result;
        }

    }
}