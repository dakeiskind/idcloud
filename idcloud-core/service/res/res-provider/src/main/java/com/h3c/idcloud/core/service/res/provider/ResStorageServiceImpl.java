package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreCreate;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreDelete;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreExtend;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreCreateResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreExtendResult;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVolumeMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVolume;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.service.res.api.ResStorageService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Service(version = "1.0.0")
@Component
public class ResStorageServiceImpl implements ResStorageService {
    private static final Logger logger = LoggerFactory.getLogger(ResStorageServiceImpl.class);
    @Autowired
    private ResStorageMapper resstorageMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private ResVolumeMapper resVolumeMapper;

    @Override
    public DataStoreCreateResult createHostStorage(ResVe resVe, ResStorage resStorage) {
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("创建主机存储");
        log.setTaskTarget(resStorage.getStorageName());
        log.setTaskType(WebConstants.TaskType.CREATE_DATASTORAGE);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        DataStoreCreate ds = new DataStoreCreate();

        //base
        ds.setAuthUrl(resVe.getManagementUrl());
        ds.setAuthPass(resVe.getManagementPassword());
        ds.setAuthUser(resVe.getManagementAccount());
        ds.setVirtEnvType(resVe.getVirtualPlatformType());
        ds.setMsgId(resStorage.getUuid());
        ds.setSid(resStorage.getResStorageSid());
        ds.setVirtEnvUuid(resVe.getResTopologySid());
        ds.setProviderType(resVe.getVirtualPlatformType());
        // datastore
        ds.setClusterName(resStorage.getOwnerCluster());
        ds.setDatastoreName(resStorage.getStorageName());
        ds.setSize(resStorage.getTotalCapacity().intValue());
        DataStoreCreateResult result = new DataStoreCreateResult();
        try {
            String msg = MQHelper.sendMessage(ds);
            result.setSuccess(true);
            result.setMsgId(resStorage.getResStorageSid());

        } catch (MQException e) {
            // TODO Auto-generated catch block
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskType(WebConstants.TaskType.CREATE_DATASTORAGE);
            log.setTaskFailureReason("创建主机存储：" + e.getMessage());
            taskLogger.fail(log);
            logger.error("MQ调用异常" + ExceptionUtils.getFullStackTrace(e));
        }
        return result;

    }

    /**
     * 统计拓扑结构下的存储信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalTopologyOfStorage(String resTopologySid) {
        return this.resstorageMapper.statisticalTopologyOfStorage(resTopologySid);
    }

    /**
     * 统计拓扑结构下的存储分配信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalTopologyOfStorageAllotInfo(String resTopologySid) {
        return this.resstorageMapper.statisticalTopologyOfStorageAllotInfo(resTopologySid);
    }

    /**
     * 统计拓扑结构下的存储容量信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalTopologyOfStorageVolume(String resTopologySid) {
        return this.resstorageMapper.statisticalTopologyOfStorageVolume(resTopologySid);
    }

    /**
     * 统计资源集群下的存储容量信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalClusterOfStorageVolume(String resTopologySid) {
        return this.resstorageMapper.statisticalClusterOfStorageVolume(resTopologySid);
    }

    /**
     * 统计资源集群下的存储分配信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalClusterOfStorageAllotInfo(String resTopologySid) {
        return this.resstorageMapper.statisticalClusterOfStorageAllotInfo(resTopologySid);
    }

    /**
     * 统计主机下的存储信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalHostOfStorageVolume(String resTopologySid) {
        return this.resstorageMapper.statisticalHostOfStorageVolume(resTopologySid);
    }

    /**
     * 统计主机下的存储分配信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalHostOfStorageAllotInfo(String resTopologySid) {
        return this.resstorageMapper.statisticalHostOfStorageAllotInfo(resTopologySid);
    }

    /**
     * 统计资源分区下存储分配信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalRzOfStorageAllotInfo(String resTopologySid) {
        return this.resstorageMapper.statisticalRzOfStorageAllotInfo(resTopologySid);
    }

    /**
     * 统计资源分区下存储容量使用信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResStorage statisticalVolumeRzOfStorage(String resTopologySid) {
        return this.resstorageMapper.statisticalVolumeRzOfStorage(resTopologySid);
    }

    /**
     * 统计数据条数
     *
     * @param example
     *
     * @return
     */
    @Override
    public int countByParams(Criteria example) {
        return this.resstorageMapper.countByParams(example);
    }

    /**
     * 查询存储列表
     *
     * @param example
     *
     * @return
     */
    @Override
    public List<ResStorage> selectByParams(Criteria example) {
        return this.resstorageMapper.selectByParams(example);
    }

    public DataStoreDeleteResult deleteStorage(ResVe resVe, ResStorage resStorage) {

        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("删除主机存储");
        log.setTaskTarget(resStorage.getStorageName());
        log.setTaskType(WebConstants.TaskType.DELETE_DATASTORAGE);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        DataStoreDelete ds = new DataStoreDelete();

        //base
        ds.setAuthUrl(resVe.getManagementUrl());
        ds.setAuthPass(resVe.getManagementPassword());
        ds.setAuthUser(resVe.getManagementAccount());
        ds.setVirtEnvType(resVe.getVirtualPlatformType());
        ds.setProviderType(resVe.getVirtualPlatformType());
        ds.setVirtEnvUuid(resVe.getResTopologySid());
        // datastore
        ds.setClusterName(resStorage.getOwnerCluster());
        ds.setDatastoreName(resStorage.getStorageName());
        ds.setMsgId(resStorage.getParentTopologySid());
        ds.setSid(resStorage.getResStorageSid());

        Criteria example = new Criteria();
        example.put("resStorageSid", resStorage.getResStorageSid());
        List<ResVolume> volumes = this.resVolumeMapper.selectByParams(example);
        List<Volume> volumeInput = new ArrayList<Volume>();
        for (ResVolume resVolume : volumes) {
            Volume volume = new Volume();
            volume.setWwn(resVolume.getVolumeWwn());
            volumeInput.add(volume);
        }
        ds.setVolumes(volumeInput);
        DataStoreDeleteResult result = new DataStoreDeleteResult();
        try {
            String msg = MQHelper.sendMessage(ds);
            result.setSuccess(true);
            result.setMsgId(resStorage.getResStorageSid());
        } catch (MQException e) {
            // TODO Auto-generated catch block
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskType(WebConstants.TaskType.DELETE_DATASTORAGE);
            log.setTaskFailureReason("删除主机存储：" + e.getMessage());
            taskLogger.fail(log);
            logger.error("MQ调用异常" + ExceptionUtils.getFullStackTrace(e));
        }

        return result;
    }

    @Override
    public DataStoreExtendResult extendStorage(ResVe resVe, ResStorage resStorage) {


        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("存储扩容");
        log.setTaskTarget(resStorage.getStorageName());
        log.setTaskType(WebConstants.TaskType.EXTEND_DATASTORAGE);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        DataStoreExtend ds = new DataStoreExtend();

        //base
        ds.setAuthUrl(resVe.getManagementUrl());
        ds.setAuthPass(resVe.getManagementPassword());
        ds.setAuthUser(resVe.getManagementAccount());
        ds.setVirtEnvType(resVe.getVirtualPlatformType());
        ds.setProviderType(resVe.getVirtualPlatformType());
        ds.setVirtEnvUuid(resVe.getResTopologySid());
        // datastore
        ds.setClusterName(resStorage.getOwnerCluster());
        ds.setDatastoreName(resStorage.getStorageName());
        ds.setMsgId(resStorage.getParentTopologySid());
        ds.setSid(resStorage.getResStorageSid());
        ds.setNewSize(resStorage.getTotalCapacity().intValue());

        DataStoreExtendResult result = new DataStoreExtendResult();
        try {
            String msg = MQHelper.sendMessage(ds);
            result.setSuccess(true);
        } catch (MQException e) {
            // TODO Auto-generated catch block
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskType(WebConstants.TaskType.EXTEND_DATASTORAGE);
            log.setTaskFailureReason("存储扩容：" + e.getMessage());
            taskLogger.fail(log);
            logger.error("MQ调用异常" + ExceptionUtils.getFullStackTrace(e));
        }

        return result;
    }

    @Override
    public ResStorage statisticalBizOfStorage(Criteria example) {
        return this.resstorageMapper.statisticalBizOfStorage(example);
    }

    @Override
    public List<ResStorage> selectByBizParams(Criteria example) {
        return this.resstorageMapper.selectByBizParams(example);
    }

    @Override
    public int countByBizParams(Criteria example) {
        return this.resstorageMapper.countByBizParams(example);
    }
}