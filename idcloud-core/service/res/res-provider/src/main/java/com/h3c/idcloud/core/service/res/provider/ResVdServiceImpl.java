package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskAttach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskCreate;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDetach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskExpand;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyConfigMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.system.dao.MgtObjMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.res.api.ResVdService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.res.api.base.ResBaseService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.*;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
@Component
public class ResVdServiceImpl implements ResVdService {
    private static final Logger logger = LoggerFactory.getLogger(ResVdServiceImpl.class);
    @Autowired
    private ResBaseService resBaseService;

    @Autowired
    private ResVdMapper resvdMapper;
    @Autowired
    private ResStorageMapper resStorageMapper;
    @Autowired
    private ResVeMapper resVeMapper;
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private ResVmService resVmService;
    @Autowired
    private MgtObjMapper mgtObjMapper;
    @Autowired
    private ResTopologyConfigMapper resTopologyConfigMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;

    /**
     * 创建虚拟磁盘
     *
     * @implSpec {
     * "region": "10",
     * "zone": "12e0c825-3ff5-11e5-8c09-005056ba3c46",
     * "dataDisk": [{
     * "dataDiskCategory": "cloud_ssd",
     * "dataDiskSize": "20"
     * }]
     * }
     */
    @Override
    @Transactional
    public ResInstResult createVd(ResCommonInst resCommonInst) {
        ResInstResult result;

        logger.info("创建块存储 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        String vdName = resCommonInst.getInstId();

        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccount = resCommonInst.getUserAccount();
        if (Strings.isNullOrEmpty(userAccount)) {
            log.setAccount("admin");
        } else {
            log.setAccount(userAccount);
        }
        log.setTaskDetail("创建虚拟磁盘");
        log.setTaskTarget(vdName);
        log.setTaskType(WebConstants.TaskType.CREATE_VD);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        // 查找Cinder存储
        // TODO 存储类型 ssd，normal
        Criteria criteria = new Criteria().put("parentTopologySid", resVe.getResTopologySid())
                .put("storageCategory", WebConstants.StorageCategory.CINDER);
        List<ResStorage> resStorages = this.resStorageMapper.selectBaseInfoByParams(criteria);
        if (null == resStorages || resStorages.isEmpty()) {
            String error = String.format("没有可用的存储设备，类型：%s", "cinder");
            logger.error(error);
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason(String.format("创建块存储异常：%s", error));
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, error);
            return result;
        }
        ResStorage resStorage = resStorages.get(0);

        // 插入VD表
        ResVd resVd = new ResVd();
        resVd.setAllocateDiskSize(Long.parseLong(jsonNode.findValue("dataDiskSize").getTextValue()));
        resVd.setStatus(WebConstants.ResVdStatus.CREATING);
        resVd.setVdName(vdName);
        resVd.setZone(zone);
        resVd.setMgtObjSid(resCommonInst.getMgtObjSid());
        resVd.setReleaseMode(WebConstants.ReleaseMode.STAND_ALONE);
        resVd.setStoragePurpose(WebConstants.StoragePurpose.DATA_DISK);
        resVd.setAllocateResStorageSid(resStorage.getResStorageSid());
        WebUtil.prepareInsertParams(resVd, userAccount);
        this.resvdMapper.insertSelective(resVd);

        // 组装参数，调用MQ
        DiskCreate diskCreate = new DiskCreate();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, diskCreate);
        diskCreate.setRegion(this.resBaseService.getRegionFromZone(zone));

        diskCreate.setSid(resVd.getResVdSid());
        diskCreate.setName(resVd.getVdName());
        diskCreate.setSize(resVd.getAllocateDiskSize().toString());
        logger.info("创建块存储 | 输入MQ参数：" + JsonUtil.toJson(diskCreate));

        try {
            MQHelper.sendMessage(diskCreate);
            Map<String, String> resMap = Maps.newHashMap();
            resMap.put(resVd.getResVdSid(), WebConstants.ResourceType.RES_VD);
            result = new ResInstResult(ResInstResult.SUCCESS, "创建块存储成功", resMap);
        } catch (MQException e) {
            logger.error("创建块存储 | MQ异常：{}", Throwables.getStackTraceAsString(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason(String.format("创建块存储异常：%s", e.getMessage()));
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }

        // TODO 存储用量变化计算
        logger.debug("创建块存储 | " + resStorage.getStorageName() + "上预占成功");
        return result;
    }


    /**
     * 挂载虚拟磁盘
     *
     * @implSpec {
     * "region": "10",
     * "zone": "12e0c825-3ff5-11e5-8c09-005056ba3c46",
     * "resVdSid": "",
     * "resVmSid": "",
     * "mountPath": "/dev/vdb"
     * }
     */
    @Override
    @Transactional
    public ResInstResult attachVd(ResCommonInst resCommonInst) {
        logger.info("挂载块存储 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        ResVd resVd = this.resvdMapper.selectByPrimaryKey(jsonNode.get("resVdSid").getTextValue());

        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccount = resCommonInst.getUserAccount();
        if (Strings.isNullOrEmpty(userAccount)) {
            log.setAccount("admin");
        } else {
            log.setAccount(userAccount);
        }
        log.setTaskDetail("挂载磁盘");
        log.setTaskTarget(resVd.getVdName());
        log.setTaskType(WebConstants.TaskType.ATTACH_VD);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        // 更新状态
        resVd.setStatus(WebConstants.ResVdStatus.SETTING);
        WebUtil.prepareUpdateParams(resVd, userAccount);
        this.resvdMapper.updateByPrimaryKeySelective(resVd);

        DiskAttach diskAttach = new DiskAttach();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, diskAttach);
        diskAttach.setRegion(this.resBaseService.getRegionFromZone(zone));

        logger.info("输入MQ参数：" + JsonUtil.toJson(diskAttach));
        ResInstResult result;
        try {
            // 组装参数，调用MQ
            ResVm resVm = this.resVmMapper.selectByPrimaryKey(jsonNode.get("resVmSid").getTextValue());
            diskAttach.setServerId(resVm.getUuid());
            diskAttach.setVolumeId(resVd.getUuid());
//        diskAttach.setDevice(jsonNode.get("mountPath").getTextValue());
//            String device = String.format("/dev/%s", resVd.getVdName().replaceAll("-", ""));
            diskAttach.setDevice(UuidUtil.getShortAlphabeticUuid("/dev/"));

            MQHelper.sendMessage(diskAttach);
            result = new ResInstResult(ResInstResult.SUCCESS, "挂载块存储成功", null);
        } catch (MQException e) {
            logger.error("挂载块存储 | MQ异常：{}", Throwables.getStackTraceAsString(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason(String.format("挂载块存储异常：%s", e.getMessage()));
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;

    }

    /**
     * 卸载块存储
     *
     * @implSpec {
     * "region": "10",
     * "zone": "12e0c825-3ff5-11e5-8c09-005056ba3c46",
     * "resVdSid": "",
     * "resVmSid": ""
     * }
     */
    @Override
    @Transactional
    public ResInstResult detachVd(ResCommonInst resCommonInst) {
        logger.info("卸载块存储 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        ResVd resVd = this.resvdMapper.selectByPrimaryKey(jsonNode.get("resVdSid").getTextValue());

        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccount = resCommonInst.getUserAccount();
        if (Strings.isNullOrEmpty(userAccount)) {
            log.setAccount("admin");
        } else {
            log.setAccount(userAccount);
        }
        log.setTaskDetail("卸载块存储");
        log.setTaskTarget(resVd.getVdName());
        log.setTaskType(WebConstants.TaskType.ATTACH_VD);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        // 更新状态
        resVd.setStatus(WebConstants.ResVdStatus.SETTING);
        WebUtil.prepareUpdateParams(resVd, userAccount);
        this.resvdMapper.updateByPrimaryKeySelective(resVd);

        DiskDetach diskDetach = new DiskDetach();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, diskDetach);
        diskDetach.setRegion(this.resBaseService.getRegionFromZone(zone));

        // 组装参数，调用MQ
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(jsonNode.get("resVmSid").getTextValue());
        diskDetach.setServerId(resVm.getUuid());
        diskDetach.setVolumeId(resVd.getUuid());

        logger.info("输入MQ参数：" + JsonUtil.toJson(diskDetach));
        ResInstResult result;
        try {
            MQHelper.sendMessage(diskDetach);
            result = new ResInstResult(ResInstResult.SUCCESS, "卸载块存储成功", null);
        } catch (MQException e) {
            logger.error("卸载块存储 | MQ异常：{}", Throwables.getStackTraceAsString(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason(String.format("卸载块存储异常：%s", e.getMessage()));
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;

    }

    /**
     * 删除虚拟磁盘
     *
     * @implSpec {
     * "region": "10",
     * "zone": "12e0c825-3ff5-11e5-8c09-005056ba3c46",
     * "resVdSid": ""
     * }
     */
    @Override
    @Transactional
    public ResInstResult deleteVd(ResCommonInst resCommonInst) {
        logger.info("删除块存储 | 输入参数：{}", JsonUtil.toJson(resCommonInst));
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String zone = jsonNode.get("zone").getTextValue();
        resCommonInst.setZoneId(zone);
        ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
        ResVd resVd = this.resvdMapper.selectByPrimaryKey(jsonNode.get("resVdSid").getTextValue());
        if (!Strings.isNullOrEmpty(resVd.getResVmSid())) {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "该存储已被挂载，无法删除。请卸载后重试。");
        }

        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccount = resCommonInst.getUserAccount();
        if (Strings.isNullOrEmpty(userAccount)) {
            log.setAccount("admin");
        } else {
            log.setAccount(userAccount);
        }
        log.setTaskDetail("删除块存储");
        log.setTaskTarget(resVd.getVdName());
        log.setTaskType(WebConstants.TaskType.CREATE_VD);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        // 插入VD表
        resVd.setStatus(WebConstants.ResVdStatus.DELETING);
        WebUtil.prepareInsertParams(resVd, userAccount);
        this.resvdMapper.updateByPrimaryKey(resVd);

        // 组装参数，调用MQ
        DiskDelete diskDelete = new DiskDelete();
        this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, diskDelete);
        diskDelete.setRegion(this.resBaseService.getRegionFromZone(zone));

        diskDelete.setId(resVd.getUuid());
        logger.info("删除块存储 | 输入MQ参数：" + JsonUtil.toJson(diskDelete));

        ResInstResult result;
        try {
            MQHelper.sendMessage(diskDelete);
            Map<String, String> resMap = Maps.newHashMap();
            resMap.put(resVd.getResVdSid(), WebConstants.ResourceType.RES_VD);
            result = new ResInstResult(ResInstResult.SUCCESS, "删除块存储成功", resMap);
        } catch (MQException e) {
            logger.error("删除块存储 | MQ异常：{}", Throwables.getStackTraceAsString(e));
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason(String.format("删除块存储异常：%s", e.getMessage()));
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }

        // TODO 存储用量变化计算
        return result;

    }

    /**
     * 扩大虚拟磁盘
     */
    @Override
    public ResInstResult expandVd(String resVdSid, long mgtObj, long size) {

        MgtObj mgtObj1 = this.mgtObjMapper.selectByPrimaryKey(mgtObj);
        ResVd resVd = this.resvdMapper.selectByPrimaryKey(resVdSid);
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("扩容磁盘");
        log.setTaskTarget(resVd.getVdName());
        log.setTaskType(WebConstants.TaskType.EXPAND_VD);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        try {
            ResStorage resSto = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
            // 判断存储空间是否足够
            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());

            // 可用空间
            BigDecimal restStorageSize = new BigDecimal(resStorage.getAvailableCapacity());
            if (restStorageSize.compareTo(new BigDecimal(resVd.getAllocateDiskSize())) >= 0) {

                DiskExpand diskExpand = new DiskExpand();
                if (resVd.getResVmSid() != null) {
                    ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVd.getResVmSid());
                    diskExpand.setVmName(resVm.getVmName());
                }
                // 组装参数，调用MQ
                ResVe resVe = this.resVeMapper.selectByPrimaryKey(resSto.getParentTopologySid());
                diskExpand.setAuthUrl(resVe.getManagementUrl());
                diskExpand.setAuthUser(resVe.getManagementAccount());
                diskExpand.setAuthPass(resVe.getManagementPassword());
                diskExpand.setProviderType(resVe.getVirtualPlatformType());
                diskExpand.setVirtEnvType(resVe.getVirtualPlatformType());
                diskExpand.setVirtEnvUuid(resVe.getResTopologySid().toString());
                diskExpand.setName(resVd.getVdName());
                diskExpand.setVolumeId(resVd.getUuid());
                diskExpand.setSize(Long.toString(size));
                diskExpand.setStorage(resSto.getStorageName());
                diskExpand.setTenantId(mgtObj1.getUuid());
                diskExpand.setTenantName(StringUtil.nullToEmpty(mgtObj1.getMgtObjSid()));
                // TODO 缺少虚拟机的ID
                String msgId = MQHelper.sendMessage(diskExpand);
                ResInstResult result = new ResInstResult(ResInstResult.SUCCESS, resVd.getVdName() + "扩容成功");
                return result;
            } else {
                log = new TaskLog();
                log.setTaskLogSid(taskId);
                log.setTaskFailureReason(resVd.getVdName() + "所在存储空间不足");
                taskLogger.fail(log);
                ResInstResult result = new ResInstResult(ResInstResult.FAILURE, resStorage.getStorageName() + "存储空间不足");
                return result;
            }
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("磁盘扩容异常：" + e.getMessage());
            taskLogger.fail(log);
            ResInstResult result = new ResInstResult(ResInstResult.FAILURE, "磁盘扩容异常：" + e.getMessage());
            return result;
        }
    }

    @Override
    public List<ResVd> selectVdResSum(Criteria example) {
        return this.resvdMapper.selectVdResSum(example);
    }
}