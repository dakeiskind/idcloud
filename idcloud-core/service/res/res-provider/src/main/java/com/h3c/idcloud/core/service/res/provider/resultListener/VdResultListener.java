package com.h3c.idcloud.core.service.res.provider.resultListener;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Throwables;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupCreateResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupRecoveryResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotCreateResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotRecovryResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreCreateResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreExtendResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskAttachResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskCreateResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskDetachResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskExpandResult;
import com.h3c.idcloud.core.persist.res.dao.ResHostStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdBackupMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVolumeMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResHostStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVdBackup;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVolume;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * VdResultListener.java
 *
 * @author Chaohong.Mao
 */
@Component
public class VdResultListener {
    private static final Logger logger = LoggerFactory.getLogger(VdResultListener.class);

    @Autowired
    private ResVdMapper resVdMapper;
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private ResVdBackupMapper resVdBackupMapper;
    @Autowired
    private ResStorageMapper resStorageMapper;
    @Autowired
    private ResHostStorageMapper resHostStorageMapper;
    @Autowired
    private ResVolumeMapper resVolumeresVolumeMapper;

    @Reference(version = "1.0.0", group = "vdExpandHandlerImpl")
    private ResourceRequestHanlder vdExpandHandlerImpl;

    /**
     * 处理创建磁盘结果
     *
     * @param diskCreateResult the disk create result
     */
    @Transactional
    public void handleMessage(DiskCreateResult diskCreateResult) {
        logger.info("创建块存储回调 | MQ返回参数：{}", JsonUtil.toJson(diskCreateResult));
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        try {
            if (diskCreateResult.isSuccess()) {
                ResVd resVd = this.resVdMapper.selectByPrimaryKey(diskCreateResult.getSid());
                resVd.setPath(diskCreateResult.getPath());
                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                resVd.setUuid(diskCreateResult.getUuid());
                this.resVdMapper.updateByPrimaryKeySelective(resVd);

                TaskLog log = new TaskLog();
                log.setTaskTarget(diskCreateResult.getName());
                log.setTaskType(WebConstants.TaskType.CREATE_VD);
                taskLogger.success(log);
            } else {
                logger.error("{}创建失败：{}", diskCreateResult.getName(), diskCreateResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(diskCreateResult.getName());
                log.setTaskType(WebConstants.TaskType.CREATE_VD);
                log.setTaskFailureReason(String.format("%s创建失败：%s",
                                                       diskCreateResult.getName(),
                                                       diskCreateResult.getErrMsg()
                ));
                taskLogger.fail(log);
            }
        } catch (Exception e) {
            logger.error("创建磁盘回调异常：{}", Throwables.getStackTraceAsString(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(diskCreateResult.getName());
            log.setTaskType(WebConstants.TaskType.CREATE_VD);
            log.setTaskFailureReason(String.format("%s创建失败：%s", diskCreateResult.getName(), e.getMessage()));
            taskLogger.fail(log);
        }
    }

    /**
     * 处理加载磁盘结果
     *
     * @param diskAttachResult the disk attach result
     */
    public void handleMessage(DiskAttachResult diskAttachResult) {
        logger.info("挂载块存储回调 | MQ返回参数：{}", JsonUtil.toJson(diskAttachResult));
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        ResVd resVd = this.resVdMapper.selectByVdUUID(diskAttachResult.getVolumeId());
        ResVm resVm = this.resVmMapper.selectByVmUUID(diskAttachResult.getServerId());
        try {
            if (diskAttachResult.isSuccess()) {
                resVd.setResVmSid(resVm.getResVmSid());
                resVd.setMountPoint(diskAttachResult.getDevice());
                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                this.resVdMapper.updateByPrimaryKeySelective(resVd);

                TaskLog log = new TaskLog();
                log.setTaskTarget(resVd.getVdName());
                log.setTaskType(WebConstants.TaskType.ATTACH_VD);
                taskLogger.success(log);
            } else {
                logger.error("{} 挂载磁盘：{}失败：{}", resVm.getVmName(), resVd.getVdName(), diskAttachResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resVd.getVdName());
                log.setTaskType(WebConstants.TaskType.ATTACH_VD);
                log.setTaskFailureReason(String.format("%s 挂载磁盘：%s失败：%s",
                                                       resVm.getVmName(),
                                                       resVd.getVdName(),
                                                       diskAttachResult.getErrMsg()
                ));
                taskLogger.fail(log);
            }

        } catch (Exception e) {
            logger.error("{} 挂载磁盘：{}失败：{}", resVm.getVmName(), resVd.getVdName(), diskAttachResult.getErrMsg());
            TaskLog log = new TaskLog();
            log.setTaskTarget(resVd.getVdName());
            log.setTaskType(WebConstants.TaskType.ATTACH_VD);
            log.setTaskFailureReason(String.format("%s 挂载磁盘失败：%s", resVd.getVdName(), e.getMessage()));
            taskLogger.fail(log);
        }
    }

    /**
     * 处卸载磁盘结果
     *
     * @param diskDetachResult the disk detach result
     */
    public void handleMessage(DiskDetachResult diskDetachResult) {
        logger.info("卸载块存储回调 | MQ返回参数：{}", JsonUtil.toJson(diskDetachResult));
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        ResVd resVd = this.resVdMapper.selectByVdUUID(diskDetachResult.getVolumeId());
        ResVm resVm = this.resVmMapper.selectByVmUUID(diskDetachResult.getServerId());
        try {
            if (diskDetachResult.isSuccess()) {
                resVd.setResVmSid("");
                resVd.setMountPoint("");
                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                this.resVdMapper.updateByPrimaryKey(resVd);

                TaskLog log = new TaskLog();
                log.setTaskTarget(resVd.getVdName());
                log.setTaskType(WebConstants.TaskType.ATTACH_VD);
                taskLogger.success(log);
            } else {
                logger.error("{} 卸载块存储：{}失败：{}", resVm.getVmName(), resVd.getVdName(), diskDetachResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resVd.getVdName());
                log.setTaskType(WebConstants.TaskType.DETACH_VD);
                log.setTaskFailureReason(String.format("%s 卸载块存储：%s失败：%s",
                                                       resVm.getVmName(),
                                                       resVd.getVdName(),
                                                       diskDetachResult.getErrMsg()
                ));
                taskLogger.fail(log);
            }

        } catch (Exception e) {
            logger.error("卸载块存储回调异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resVd.getVdName());
            log.setTaskType(WebConstants.TaskType.ATTACH_VD);
            log.setTaskFailureReason(String.format("%s卸载块存储：%s失败：%s",
                                                   resVm.getVmName(),
                                                   resVd.getVdName(),
                                                   e.getMessage()
            ));
            taskLogger.fail(log);
        }
    }

    /**
     * 处理删除磁盘结果
     *
     * @param diskDeleteResult the disk delete result
     */
    public void handleMessage(DiskDeleteResult diskDeleteResult) {
        logger.info("删除块存储回调 | MQ返回参数：{}", JsonUtil.toJson(diskDeleteResult));
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        ResVd resVd = this.resVdMapper.selectByVdUUID(diskDeleteResult.getId());

        try {
            if (diskDeleteResult.isSuccess()) {
                this.resVdMapper.deleteByPrimaryKey(resVd.getResVdSid());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resVd.getVdName());
                log.setTaskType(WebConstants.TaskType.DELETE_VD);
                taskLogger.success(log);
            } else {
                logger.error("{}删除块存储失败：{}", diskDeleteResult.getId(), diskDeleteResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resVd.getVdName());
                log.setTaskType(WebConstants.TaskType.DELETE_VD);
                log.setTaskFailureReason(String.format("%s删除块存储失败：%s",
                                                       diskDeleteResult.getId(),
                                                       diskDeleteResult.getErrMsg()
                ));
                taskLogger.fail(log);
            }

        } catch (Exception e) {
            logger.error("删除块存储回调异常：{}", Throwables.getStackTraceAsString(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resVd.getVdName());
            log.setTaskType(WebConstants.TaskType.DELETE_VD);
            log.setTaskFailureReason(String.format("%s删除块存储失败：%s", resVd.getVdName(), e.getMessage()));
            taskLogger.fail(log);
        }
    }

    /**
     * 处理扩容磁盘结果
     *
     * @param diskExpandResult the disk expand result
     */
    public void handleMessage(DiskExpandResult diskExpandResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        ResInstResult result = new ResInstResult();
        Criteria criteria = new Criteria();
        criteria.put("vdName", diskExpandResult.getName());
        List<ResVd> resVdList = this.resVdMapper.selectByParams(criteria);
        ResVd resVd = resVdList.get(0);
        try {
            if (diskExpandResult.isSuccess()) {
                resVd.setAllocateDiskSize(Long.parseLong(diskExpandResult.getSize()));
                this.resVdMapper.updateByPrimaryKeySelective(resVd);
                TaskLog log = new TaskLog();
                log.setTaskTarget(diskExpandResult.getName());
                log.setTaskType(WebConstants.TaskType.EXPAND_VD);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
                result.setData(resVd);
            } else {
                logger.error(diskExpandResult.getName() + "扩容失败：" + diskExpandResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(diskExpandResult.getName());
                log.setTaskType(WebConstants.TaskType.EXPAND_VD);
                log.setTaskFailureReason(diskExpandResult.getName() + "扩容失败：" + diskExpandResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setData(resVd);
            }
        } catch (Exception e) {
            logger.error("扩容扩容回调异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resVd.getVdName());
            log.setTaskType(WebConstants.TaskType.DELETE_VD);
            log.setTaskFailureReason("扩容扩容回调异常：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
            result.setStatus(ResInstResult.FAILURE);
            result.setData(resVd);
        }
        vdExpandHandlerImpl.operate(result);
    }

    /**
     * 快存储快照创建
     *
     * @param blockSnapshotCreateResult the block snapshot create result
     */
    public void handleMessage(BlockSnapshotCreateResult blockSnapshotCreateResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        String snapshotName = blockSnapshotCreateResult.getName();
        Criteria example = new Criteria();
        example.put("backupName", snapshotName);
        List<ResVdBackup> backUps = this.resVdBackupMapper.selectByParams(example);
        ResVd resvd = this.resVdMapper.selectByPrimaryKey(backUps.get(0).getResVdSid());
        ResVdBackup backUp = backUps.get(0);
        try {
            ResInstResult result = new ResInstResult();
            if (blockSnapshotCreateResult.isSuccess()) {
                backUp.setStatus(WebConstants.BACKUP_STATUS.CREATE_SUCCESS);
                backUp.setAllocateBackupId(blockSnapshotCreateResult.getId());
                this.resVdBackupMapper.updateByPrimaryKeySelective(backUp);
                TaskLog log = new TaskLog();
                log.setTaskTarget(resvd.getVdName());
                log.setTaskType(WebConstants.TaskType.CREATE_VD_SNAPSHOT);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(resvd.getVdName() + "创建块存储快照失败：" + blockSnapshotCreateResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resvd.getVdName());
                log.setTaskType(WebConstants.TaskType.CREATE_VD_SNAPSHOT);
                log.setTaskFailureReason(resvd.getVdName() + "创建块存储快照失败：" + blockSnapshotCreateResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(blockSnapshotCreateResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("块存储快照创建异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resvd.getVdName());
            log.setTaskType(WebConstants.TaskType.CREATE_VD_SNAPSHOT);
            log.setTaskFailureReason(resvd.getVdName() + "创建块存储快照失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * 快存储备份创建
     *
     * @param blockBackupCreateResult the block backup create result
     */
    public void handleMessage(BlockBackupCreateResult blockBackupCreateResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        String snapshotName = blockBackupCreateResult.getName();
        Criteria example = new Criteria();
        example.put("backupName", snapshotName);
        List<ResVdBackup> backUps = this.resVdBackupMapper.selectByParams(example);
        ResVd resvd = this.resVdMapper.selectByPrimaryKey(backUps.get(0).getResVdSid());
        ResVdBackup backUp = backUps.get(0);
        try {
            ResInstResult result = new ResInstResult();
            if (blockBackupCreateResult.isSuccess()) {
                backUp.setStatus(WebConstants.BACKUP_STATUS.CREATE_SUCCESS);
                backUp.setAllocateBackupId(blockBackupCreateResult.getId());
                this.resVdBackupMapper.updateByPrimaryKeySelective(backUp);
                TaskLog log = new TaskLog();
                log.setTaskTarget(resvd.getVdName());
                log.setTaskType(WebConstants.TaskType.CREATE_VD_BACKUP);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(resvd.getVdName() + "创建块存储备份失败：" + blockBackupCreateResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resvd.getVdName());
                log.setTaskType(WebConstants.TaskType.CREATE_VD_BACKUP);
                log.setTaskFailureReason(resvd.getVdName() + "创建块存储备份失败：" + blockBackupCreateResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(blockBackupCreateResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("创建块存储备份异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resvd.getVdName());
            log.setTaskType(WebConstants.TaskType.CREATE_VD_BACKUP);
            log.setTaskFailureReason(resvd.getVdName() + "创建块存储备份失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * 镜像恢复
     *
     * @param blockSnapshotRecovryResult the block snapshot recovry result
     */
    public void handleMessage(BlockSnapshotRecovryResult blockSnapshotRecovryResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        String resVdUUID = blockSnapshotRecovryResult.getVolumeId();
        Criteria example = new Criteria();
        //example.put("resVdSid", resVdSid);
        try {
            ResInstResult result = new ResInstResult();
            if (blockSnapshotRecovryResult.isSuccess()) {
                example = new Criteria();
                example.put("uuid", resVdUUID);

                List<ResVd> ResVds = this.resVdMapper.selectByParams(example);
                ResVd ResVd = this.resVdMapper.selectByPrimaryKey(ResVds.get(0).getResVdSid());
                ResVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                ResVd.setUuid(blockSnapshotRecovryResult.getId());
                resVdMapper.updateByPrimaryKeySelective(ResVd);
                TaskLog log = new TaskLog();
                log.setTaskTarget(blockSnapshotRecovryResult.getName());
                log.setTaskType(WebConstants.TaskType.REVERT_VD_SNAPSHOT);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(
                        blockSnapshotRecovryResult.getName() + "块存储恢复失败：" + blockSnapshotRecovryResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(blockSnapshotRecovryResult.getName());
                log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
                log.setTaskFailureReason(
                        blockSnapshotRecovryResult.getName() + "块存储恢复失败：" + blockSnapshotRecovryResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(blockSnapshotRecovryResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("块存储恢复异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(blockSnapshotRecovryResult.getName());
            log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
            log.setTaskFailureReason(
                    blockSnapshotRecovryResult.getName() + "块存储恢复失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * Handle message.
     *
     * @param blockBackupRecoveryResult the block backup recovery result
     */
    public void handleMessage(BlockBackupRecoveryResult blockBackupRecoveryResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        String snapshotName = blockBackupRecoveryResult.getName();
        Criteria example = new Criteria();
        example.put("backupName", snapshotName);
        List<ResVdBackup> backUps = this.resVdBackupMapper.selectByParams(example);
        ResVd resvd = this.resVdMapper.selectByPrimaryKey(backUps.get(0).getResVdSid());
        ResVdBackup backUp = backUps.get(0);
        String resVdUUID = blockBackupRecoveryResult.getVolumeId();
        try {
            ResInstResult result = new ResInstResult();
            if (blockBackupRecoveryResult.isSuccess()) {
                example = new Criteria();
                example.put("uuid", resVdUUID);
                List<ResVd> ResVds = this.resVdMapper.selectByParams(example);
                ResVd resVd = this.resVdMapper.selectByPrimaryKey(ResVds.get(0).getResVdSid());
                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                resVd.setUuid(blockBackupRecoveryResult.getId());
                resVdMapper.updateByPrimaryKeySelective(resVd);
                TaskLog log = new TaskLog();
                log.setTaskTarget(blockBackupRecoveryResult.getName());
                log.setTaskType(WebConstants.TaskType.REVERT_VD_SNAPSHOT);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(blockBackupRecoveryResult.getName() + "块存储恢复失败：" + blockBackupRecoveryResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(blockBackupRecoveryResult.getName());
                log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
                log.setTaskFailureReason(
                        blockBackupRecoveryResult.getName() + "块存储恢复失败：" + blockBackupRecoveryResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(blockBackupRecoveryResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("块存储恢复异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(blockBackupRecoveryResult.getName());
            log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
            log.setTaskFailureReason(
                    blockBackupRecoveryResult.getName() + "块存储恢复失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * Handle message.
     *
     * @param blockSnapshotDeleteResult the block snapshot delete result
     */
    public void handleMessage(BlockSnapshotDeleteResult blockSnapshotDeleteResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        String snapshotId = blockSnapshotDeleteResult.getSnapshotId();
        Criteria example = new Criteria();
        example.put("allocateBackupId", snapshotId);
        List<ResVdBackup> backUps = this.resVdBackupMapper.selectByParams(example);
        ResVd resvd = this.resVdMapper.selectByPrimaryKey(backUps.get(0).getResVdSid());
        ResVdBackup backUp = backUps.get(0);
        try {
            ResInstResult result = new ResInstResult();
            if (blockSnapshotDeleteResult.isSuccess()) {
                this.resVdBackupMapper.deleteByPrimaryKey(backUp.getBackupSid());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resvd.getVdName());
                log.setTaskType(WebConstants.TaskType.DELETE_VM_SNAPSHOT);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(resvd.getVdName() + "块存储删除失败：" + blockSnapshotDeleteResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resvd.getVdName());
                log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
                log.setTaskFailureReason(resvd.getVdName() + "块存储删除失败：" + blockSnapshotDeleteResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(blockSnapshotDeleteResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("块存储删除异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(resvd.getVdName());
            log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
            log.setTaskFailureReason(resvd.getVdName() + "块存储删除失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * 创建存储
     *
     * @param createResult the create result
     */
    public void handleMessage(DataStoreCreateResult createResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        if (createResult.isSuccess()) {
            ResStorage storage = new ResStorage();
            storage.setResStorageSid(createResult.getSid());
            List<Volume> volumes = createResult.getDataStoreVo().getVolumes();
            String uuid = createResult.getDataStoreVo().getUuid();
            for (Volume volume : volumes) {
                ResVolume resVolume = new ResVolume();
                resVolume.setResVolumeSid(volume.getId().toString());
                resVolume.setResStorageSid(createResult.getSid());
                resVolume.setVolumeName(volume.getName());
                resVolume.setSize(Long.parseLong(String.valueOf(volume.getSize())));
                resVolume.setStatus(WebConstants.VolumeStatus.NORMAL);
                resVolume.setVolumeWwn(volume.getWwn());
                this.resVolumeresVolumeMapper.insert(resVolume);
            }
            storage.setStatus(WebConstants.ResStorageStatus.NORMAL);
            storage.setUuid(uuid);
            int result = resStorageMapper.updateByPrimaryKeySelective(storage);
            TaskLog log = new TaskLog();
            log.setTaskTarget(createResult.getDatastoreName());
            log.setTaskType(WebConstants.TaskType.CREATE_DATASTORAGE);
            taskLogger.success(log);
        } else {
            this.resStorageMapper.deleteByPrimaryKey(createResult.getSid());
            Criteria example = new Criteria();
            example.put("resStorageSid", createResult.getSid());
            this.resHostStorageMapper.deleteByParams(example);
            /*String printMsg = "";
            String msg = createResult.getErrMsg();
			if(null != msg && msg.equals("invalid parameters exception")){
				printMsg = "输入参数有误";
			}else if(null != msg && msg.equals("available volume not found exception")){
				printMsg = "没有发现足够的空闲存储资源";
			}else if(null !=msg && msg.equals("export volume")){
				printMsg = "主机与存储资源关联有误";
			}else{
				printMsg = "资源环境有误,请稍后重试";
			}*/
            logger.error(createResult.getDatastoreName() + "创建存储失败：" + createResult.getErrMsg());
            TaskLog log = new TaskLog();
            log.setTaskTarget(createResult.getDatastoreName());
            log.setTaskType(WebConstants.TaskType.CREATE_DATASTORAGE);
            log.setTaskFailureReason(createResult.getDatastoreName() + "创建存储失败：" + createResult.getErrMsg());
            taskLogger.fail(log);
        }
    }

    /**
     * 删除存储
     *
     * @param deleteResult the delete result
     */
    public void handleMessage(DataStoreDeleteResult deleteResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        if (deleteResult.isSuccess()) {
            ResStorage storage = this.resStorageMapper.selectByPrimaryKey(deleteResult.getSid());
            //删除volume
            this.resVolumeresVolumeMapper.deleteByResStorageSid(deleteResult.getSid());
            //删除主机&存储关联
            ResHostStorage hoststorage = new ResHostStorage();
            hoststorage.setResStorageSid(storage.getResTopologySid());
            this.resHostStorageMapper.deleteByPrimaryKey(hoststorage);
            //删除存储
            this.resStorageMapper.deleteByPrimaryKey(deleteResult.getSid());
            TaskLog log = new TaskLog();
            log.setTaskTarget(deleteResult.getDatastoreName());
            log.setTaskType(WebConstants.TaskType.DELETE_DATASTORAGE);
            taskLogger.success(log);
        } else {
            ResStorage storage = this.resStorageMapper.selectByPrimaryKey(deleteResult.getSid());
            storage.setStatus(WebConstants.ResStorageStatus.NORMAL);
            this.resStorageMapper.updateByPrimaryKeySelective(storage);
            logger.error(deleteResult.getDatastoreName() + "删除存储失败：" + deleteResult.getErrMsg());
            TaskLog log = new TaskLog();
            log.setTaskTarget(deleteResult.getDatastoreName());
            log.setTaskType(WebConstants.TaskType.DELETE_DATASTORAGE);
            log.setTaskFailureReason(deleteResult.getDatastoreName() + "删除存储失败：" + deleteResult.getErrMsg());
            taskLogger.fail(log);
        }
    }

    /**
     * 扩容存储
     *
     * @param extendResult the extend result
     */
    public void handleMessage(DataStoreExtendResult extendResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        if (extendResult.isSuccess()) {
            ResStorage storage = this.resStorageMapper.selectByPrimaryKey(extendResult.getSid());
            //更新存储容量
            Long oldSize = storage.getTotalCapacity();
            int newSize = oldSize.intValue() + extendResult.getNewSize();
            storage.setTotalCapacity(Long.parseLong(String.valueOf(newSize)));
            storage.setStatus(WebConstants.ResStorageStatus.NORMAL);
            resStorageMapper.updateByPrimaryKeySelective(storage);
            //添加volume
            List<Volume> volumes = extendResult.getDataStoreVo().getVolumes();
            for (Volume volume : volumes) {
                ResVolume resVolume = new ResVolume();
                resVolume.setResVolumeSid(volume.getId().toString());
                resVolume.setResStorageSid(extendResult.getSid());
                resVolume.setVolumeName(volume.getName());
                resVolume.setSize(Long.parseLong(String.valueOf(volume.getSize())));
                resVolume.setStatus(WebConstants.VolumeStatus.NORMAL);
                resVolume.setVolumeWwn(volume.getWwn());
                this.resVolumeresVolumeMapper.insert(resVolume);
            }
            TaskLog log = new TaskLog();
            log.setTaskTarget(extendResult.getDatastoreName());
            log.setTaskType(WebConstants.TaskType.EXTEND_DATASTORAGE);
            taskLogger.success(log);
        } else {
            ResStorage storage = this.resStorageMapper.selectByPrimaryKey(extendResult.getSid());
            storage.setStatus(WebConstants.ResStorageStatus.NORMAL);
            resStorageMapper.updateByPrimaryKeySelective(storage);
            logger.error(extendResult.getDatastoreName() + "存储扩容失败：" + extendResult.getErrMsg());
            TaskLog log = new TaskLog();
            log.setTaskTarget(extendResult.getDatastoreName());
            log.setTaskType(WebConstants.TaskType.EXTEND_DATASTORAGE);
            log.setTaskFailureReason(extendResult.getDatastoreName() + "存储扩容失败：" + extendResult.getErrMsg());
            taskLogger.fail(log);
        }
    }

}





