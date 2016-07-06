/*
 * ts-resource
 * 概要：VmResultListener.java
 *
 * 创建人：徐印
 * 创建日：2014-8-26
 * 更新履历
 * 2014-8-26  徐印  创建
 *
 * @(#)VmResultListener.java
 *
 * Copyright (c) 2014 Hewlett Packard Corporation, All rights reserved.
 */
package com.h3c.idcloud.core.service.res.provider.resultListener;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;
import com.h3c.idcloud.core.adapter.pojo.network.Port;
import com.h3c.idcloud.core.adapter.pojo.scan.result.TemplateScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.TemplateVO;
import com.h3c.idcloud.core.adapter.pojo.software.Software;
import com.h3c.idcloud.core.adapter.pojo.software.result.SoftwareDeployResult;
import com.h3c.idcloud.core.adapter.pojo.vm.VmDisk;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmCreateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmMigrateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmModifyResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmNicAddResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmNicDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmOperateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmRebuildResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmReconfigResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmRemoveResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmSnapshotCreateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmSnapshotDeleteResult;
import com.h3c.idcloud.core.persist.res.dao.ResHostItemMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResImageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResOsSoftwareMapper;
import com.h3c.idcloud.core.persist.res.dao.ResOsUserMapper;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmBackupMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVolumeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcPortsIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcPortsMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResHostItem;
import com.h3c.idcloud.core.pojo.dto.res.ResImage;
import com.h3c.idcloud.core.pojo.dto.res.ResIp;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVmBackup;
import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVolume;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcPorts;
import com.h3c.idcloud.core.pojo.dto.res.ResVpcPortsIp;
import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.product.api.ResourceRequestHanlder;
import com.h3c.idcloud.core.service.product.api.ServiceInstResService;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.product.api.VmOperateHanlder;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.system.api.CodeService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VmResultListener.java
 *
 * @author 徐印
 */
@Component
public class VmResultListener {

    private static final Logger logger = LoggerFactory.getLogger(VmResultListener.class);
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private ResVmService resVmService;
    @Autowired
    private ResVdMapper resVdMapper;
    @Autowired
    private ResIpMapper resIpMapper;
    @Autowired
    private ResVmNetworkMapper resVmNetworkMapper;
    @Autowired
    private ResNetworkMapper resNetworkMapper;
    @Autowired
    private ResTopologyMapper resTopologyMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private ResVeMapper resVeMapper;
    @Autowired
    private ResHostMapper resHostMapper;
    @Autowired
    private ResStorageMapper resStorageMapper;
    @Autowired
    private ResHostStorageMapper resHostStorageMapper;
    @Autowired
    private ResVolumeMapper resVolumeMapper;
    @Autowired
    private ResVmBackupMapper resVmBackupMapper;
    @Autowired
    private ResHostItemMapper resHostItemMapper;
    @Autowired
    private ResImageMapper resImageMapper;
    @Autowired
    private ResOsSoftwareMapper resOsSoftwareMapper;
    @Autowired
    private ResOsUserMapper resOsUserMapper;
    @Autowired
    private ResVpcMapper resVpcMapper;
    @Autowired
    private ResVpcPortsMapper resVpcPortsMapper;
    @Autowired
    private ResVpcPortsIpMapper resVpcPortsIpMapper;

    @Reference(version = "1.0.0")
    private CodeService codeService;
    @Reference(version = "1.0.0")
    private ServiceInstResService serviceInstResService;
    @Reference(version = "1.0.0")
    private ServiceInstanceService serviceInstService;
    @Reference(version = "1.0.0", group = "vmOpenHandlerImpl")
    private ResourceRequestHanlder vmOpenHandlerImpl;
    @Reference(version = "1.0.0", group = "vmOperateHandlerImpl")
    private VmOperateHanlder vmOperateHanlder;
    @Reference(version = "1.0.0", group = "vmChangeHandlerImpl")
    private ResourceRequestHanlder vmChangeHandler;
    @Reference(version = "1.0.0", group = "vmDeleteHandlerImpl")
    private ResourceRequestHanlder vmDeleteHandler;
    @Reference(version = "1.0.0", group = "softwareAutoInstallHandlerImpl")
    private ResourceRequestHanlder softwareAutoInstallHandlerImpl;

    /**
     * 处理虚拟机创建结果
     *
     * @param vmCreateResult the vm create result
     */
    public void handleMessage(VmCreateResult vmCreateResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        ResVm resVm = null;
        try {
            logger.info("创建虚拟机回调 | MQ返回参数：{}", JsonUtil.toJson(vmCreateResult));
            // 查询虚拟化环境
            ResVe resVe = this.resVeMapper.selectByPrimaryKey(vmCreateResult.getVirtEnvUuid());
            // 虚拟机创建成功
            if (vmCreateResult.isSuccess()) {
                // TODO Openstack | Other
                if (WebConstants.VirtualPlatformType.OPENSTACK.equals(resVe.getVirtualPlatformType())) {
                    resVm = this.vmCreateResultForOpenstack(vmCreateResult);
                } else {
                    resVm = this.vmCreateResultForOther(vmCreateResult);
                }
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmCreateResult.getVmName());
                log.setTaskType(WebConstants.TaskType.CREATE_VM);
                taskLogger.success(log);

                ResVm resVM = this.resVmService.getVmInfo(resVm.getResVmSid());
                if (vmCreateResult.getSoftware() != null) {
                    List<ResOsSoftware> softwares = JsonUtil.fromJson(vmCreateResult.getSoftware(),
                                                                      new TypeReference<List<ResOsSoftware>>() {}
                    );
                    resVM.setSoftwares(softwares);
                }
            } else {
                // 根据虚拟机查询所属管理对象
                logger.error("{} 创建失败：{}", vmCreateResult.getVmName(), vmCreateResult.getErrMsg());
                logger.error("创建虚拟机回调 | 创建失败，开始重试");
//                if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
//                    // 工单不自动处理
//                    result.setReSend(false);
//
//                    // 预占用的HostItem释放(网卡|端口|磁盘卡|磁盘)
//                    Criteria condition = new Criteria().put("relateResSid", vmCreateResult.getId());
//                    ResHostItem hostItem = new ResHostItem();
//                    hostItem.setRelateResSid("");
//                    hostItem.setResAllocFlag(Integer.parseInt(WebConstants.ResHostItemAllocFlag.NOT_OCCUPIED));
//                    hostItem.setResAllocStatus(WebConstants.HostItemAllocStatus.FREE);
//                    this.resHostItemMapper.updateByParamsSelective(hostItem, condition.getCondition());
//
//                } else if (WebConstants.VirtualPlatformType.VMWARE.equals(resVe.getVirtualPlatformType())) {
//                    result.setReSend(true);
////					// 删除虚拟机 10003: 虚拟机名重复
//                }
                // 不管vc删除成功或者失败

//                // 删除磁盘
//                this.resVdMapper.deleteByParams(new Criteria("resVmSid", vmCreateResult.getId()));
//
//                // 删除虚拟机网络关系，并更新IP为有效
//                this.resVmNetworkMapper.deleteByPrimaryKey(vmCreateResult.getId());
//
//                // 删除虚拟机
//                this.resVmMapper.deleteByPrimaryKey(vmCreateResult.getId());
//
//                // 删除虚拟机用户
//                this.resOsUserMapper.deleteByParams(new Criteria("resSid", vmCreateResult.getId()));

                TaskLog log = new TaskLog();
                log.setTaskTarget(vmCreateResult.getVmName());
                log.setTaskType(WebConstants.TaskType.CREATE_VM);
                log.setTaskFailureReason(vmCreateResult.getVmName() + "创建失败：" + vmCreateResult.getErrMsg());
                taskLogger.fail(log);

                Map<String, Object> options = vmCreateResult.getOptions();
                String instId = options.get("instId").toString();
                // 回调service
                this.serviceInstService.resInvokeErrorCallback(vmCreateResult.getId(),
                                                               instId,
                                                               WebConstants.ResourceType.RES_VM,
                                                               vmCreateResult.getErrMsg()
                );
            }
        } catch (Exception e) {
            logger.error("创建虚拟机回调异常：{}", Throwables.getStackTraceAsString(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(vmCreateResult.getVmName());
            log.setTaskType(WebConstants.TaskType.CREATE_VM);
            log.setTaskFailureReason(vmCreateResult.getVmName() + "创建失败：" + Throwables.getStackTraceAsString(e));
            taskLogger.fail(log);
        }
    }

    private ResVm vmCreateResultForOpenstack(VmCreateResult vmCreateResult) {
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(vmCreateResult.getId());
        Map<String, Object> options = vmCreateResult.getOptions();
        String userAccount = options.get("userAccount").toString();
        // 更新虚拟机状态
        resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
        resVm.setUuid(vmCreateResult.getUuid());
        // 关联虚拟机所在主机
        // TODO VM --> host UUID
        List<ResHost> resHostList = this.resHostMapper.selectPrimaryKeyByUUID(new Criteria("uuid",
                                                                                           vmCreateResult.getHostName()
        ));
        ResHost resHost = resHostList.get(0);
        resVm.setAllocateResHostSid(resHost.getResHostSid());
        WebUtil.prepareUpdateParams(resVm, userAccount);
        this.resVmMapper.updateByPrimaryKeySelective(resVm);
        logger.debug("创建虚拟机回调 | 更新VM状态：" + JsonUtil.toJson(resVm));

        List<ResStorage> resStorageList = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
        ResStorage resStorage = resStorageList.get(0);

        // 更新磁盘状态
        Criteria criteria2 = new Criteria();
        criteria2.put("resVmSid", resVm.getResVmSid());
        List<ResVd> resVdList = this.resVdMapper.selectByParams(criteria2);
        final List<VmDisk> diskResults = vmCreateResult.getDisks();
        if (diskResults != null) {
            for (ResVd resVd : resVdList) {
                for (VmDisk diskResult : diskResults) {
                    if (resVd.getVdName().equals(diskResult.getName())) {
                        // OpenStack环境的时候，系统盘做存储的关联
                        if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose())) {
                            resVd.setAllocateResStorageSid(resStorage.getResStorageSid());
                        }
                        resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                        resVd.setUuid(diskResult.getUuid());
                        resVd.setPath(diskResult.getPath());
                        resVd.setReleaseMode(WebConstants.ReleaseMode.WITH_INSTANCE);
                        WebUtil.prepareUpdateParams(resVd, userAccount);
                        this.resVdMapper.updateByPrimaryKeySelective(resVd);
                    }
                }
            }
            logger.debug("创建虚拟机回调 | 更新VD状态：" + JsonUtil.toJson(resVdList));
        }

        // 更新网络状态
        List<VmNic> vmNics = vmCreateResult.getNics();
        if (vmNics != null) {
            for (VmNic vmNic : vmNics) {
                // 更新网卡状态为正常
                Criteria criteria3 = new Criteria("uuid", vmNic.getNetId());
                criteria3.put("uuid", vmNic.getSubnetId());
                List<ResNetwork> resNetList = this.resNetworkMapper.selectByParams(criteria3);
                ResNetwork resNetwork = resNetList.get(0);
                // 插入虚拟机与IP关系表
                ResVmNetwork resVmNetwork = new ResVmNetwork();
                resVmNetwork.setIpAddress(vmNic.getPrivateIp());
                resVmNetwork.setResNetworkSid(resNetwork.getResNetworkSid());
                resVmNetwork.setResVmSid(resVm.getResVmSid());
                resVmNetwork.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
                resVmNetwork.setReleaseMode(WebConstants.ReleaseMode.WITH_INSTANCE);
                // TODO floatingip uuid
                this.resVmNetworkMapper.insertSelective(resVmNetwork);
                logger.debug("创建虚拟机回调 | 更新网络关系：{}", JsonUtil.toJson(resVmNetwork));

                // 更新端口信息
                // Port信息
                for (Port port : vmCreateResult.getPorts()) {
                    if (port.getId().equals(vmNic.getPort())) {
                        logger.debug("创建虚拟机回调 | 创建端口及关系：{}", JsonUtil.toJson(port));
                        // 创建端口
                        ResVpcPorts vpcPorts = new ResVpcPorts(port);
                        vpcPorts.setVpcId(resNetwork.getParentTopologySid());
                        vpcPorts.setOwnerId(userAccount);
                        vpcPorts.setMgtObjSid(Long.parseLong(options.get("mgtObjSid").toString()));
                        WebUtil.prepareInsertParams(vpcPorts, userAccount);
                        this.resVpcPortsMapper.insertSelective(vpcPorts);

                        // 创建端口与子网的关系
                        ResVpcPortsIp resVpcPortsIp = new ResVpcPortsIp();
                        resVpcPortsIp.setResPortSid(vpcPorts.getResPortSid());
                        resVpcPortsIp.setSubnetId(resNetwork.getResNetworkSid());
                        resVpcPortsIp.setIpAddress(vmNic.getPrivateIp());
                        WebUtil.prepareInsertParams(resVpcPortsIp, userAccount);
                        this.resVpcPortsIpMapper.insertSelective(resVpcPortsIp);
                    }
                }
            }
        }


        return resVm;
    }

    private ResVm vmCreateResultForOther(VmCreateResult vmCreateResult) {
        ResVe resVe = this.resVeMapper.selectByPrimaryKey(vmCreateResult.getVirtEnvUuid());
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(vmCreateResult.getId());
        List<ResHost> resHostList = this.resHostMapper.selectPrimaryKeyByUUID(new Criteria("uuid",
                                                                                           vmCreateResult.getHost()
        ));
        ResHost resHost = resHostList.get(0);
        // 更新虚拟机状态为正常
        resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
        resVm.setUuid(vmCreateResult.getUuid());
        // Power的uuid设置方式与底层扫描方式一致
        if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
            // Power虚拟机开通，uuid为扫描的组合方式
            // ${SERV_SN}#${LPAR_NAME}
            resVm.setUuid(resHost.getSerialNumber() + "#" + vmCreateResult.getVmName());
            // Power虚拟机result中的uuid为LparId
            resVm.setParId(vmCreateResult.getUuid());
        }
        WebUtil.prepareUpdateParams(resVm);
        this.resVmMapper.updateByPrimaryKeySelective(resVm);

        //TODO 查询主机下存储
        List<ResStorage> resStorageList = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
//                // 更新磁盘状态为使用中
        Criteria criteria2 = new Criteria();
        criteria2.put("resVmSid", resVm.getResVmSid());
        List<ResVd> resVdList = this.resVdMapper.selectByParams(criteria2);
        List<VmDisk> diskResults = vmCreateResult.getDisks();

        // 更新配件表DISK的占用
        if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType()) &&
                WebConstants.PowerPartitionType.LPAR.equals(resVm.getParType().toString())) {

            for (ResVd resVd : resVdList) {
                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                WebUtil.prepareUpdateParams(resVd);
                this.resVdMapper.updateByPrimaryKeySelective(resVd);
            }
            if (!CollectionUtils.isEmpty(diskResults)) {
                for (VmDisk vmDisk : diskResults) {
                    //保存volume关系
                    List<Volume> volumes = vmDisk.getVolumes();
                    if (!CollectionUtils.isEmpty(volumes)) {
                        ResVd resVd = this.resVdMapper.selectByPrimaryKey(vmDisk.getId());
                        if (WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
                            for (Volume volume : volumes) {
                                ResVolume resVolume = new ResVolume();
                                resVolume.setResStorageSid(resVd.getAllocateResStorageSid());
                                resVolume.setResVolumeSid(volume.getId() == null ? "" : volume.getId().toString());
                                resVolume.setSize(volume.getSize().longValue());
                                resVolume.setStatus("01");
                                resVolume.setVolumeName(volume.getName());
                                resVolume.setVolumeWwn(volume.getWwn());

                                resVolumeMapper.insertSelective(resVolume);
                            }
                        }
                    }
                }
            }
        } else {
            // 虚拟化环境更新逻辑
            for (ResVd resVd : resVdList) {
                for (VmDisk diskResult : diskResults) {
                    // HMC的MPAR系统盘因为要重命名，所以用sid来判断
                    if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType()) &&
                            WebConstants.PowerPartitionType.MPAR.equals(resVm.getParType().toString()) &&
                            WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose())) {
                        if (resVd.getResVdSid().equals(diskResult.getId())) {
                            resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                            resVd.setVdName(diskResult.getName());
                            resVd.setUuid(diskResult.getUuid());
                            resVd.setPath(diskResult.getPath());
                            WebUtil.prepareUpdateParams(resVd);
                            this.resVdMapper.updateByPrimaryKeySelective(resVd);
                        }
                    } else {
                        // 通过名称进行判断
                        if (resVd.getVdName().equals(diskResult.getName())) {

                            // OpenStack环境的时候，系统盘做存储的关联
                            if (WebConstants.VirtualPlatformType.OPENSTACK.equals(resVe.getVirtualPlatformType())) {
                                if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose())) {
                                    resVd.setAllocateResStorageSid(resStorageList.get(0).getResStorageSid());
                                }
                            }

                            resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                            resVd.setUuid(diskResult.getUuid());
                            resVd.setPath(diskResult.getPath());
                            WebUtil.prepareUpdateParams(resVd);
                            this.resVdMapper.updateByPrimaryKeySelective(resVd);

                            // HMC的添加数据盘的时候，将volumn的关系加入表中
                            if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                                if (WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
                                    //数据盘的volume关系保存
                                    List<Volume> volumes = diskResult.getVolumes();
                                    if (!CollectionUtils.isEmpty(volumes)) {
                                        for (Volume volume : volumes) {
                                            ResVolume resVolume = new ResVolume();
                                            resVolume.setResStorageSid(resVd.getAllocateResStorageSid());
                                            resVolume.setResVolumeSid(
                                                    volume.getId() == null ? "" : volume.getId().toString());
                                            resVolume.setSize(volume.getSize().longValue());
                                            resVolume.setStatus("01");
                                            resVolume.setVolumeName(volume.getName());
                                            resVolume.setVolumeWwn(volume.getWwn());

                                            resVolumeMapper.insertSelective(resVolume);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (WebConstants.VirtualPlatformType.OPENSTACK.equals(resVe.getVirtualPlatformType())) {
            resVm.setAllocateResHostSid(resHost.getResHostSid());
            this.resVmMapper.updateByPrimaryKeySelective(resVm);
            List<VmNic> vmNics = vmCreateResult.getNics();
            for (VmNic vmNic : vmNics) {
                // 更新网卡状态为正常
                Criteria criteria3 = new Criteria();
                criteria3.put("uuid", vmNic.getNetId());
                List<ResNetwork> resNetList = this.resNetworkMapper.selectByParams(criteria3);
                if (resNetList != null && resNetList.size() > 0) {
                    // 插入虚拟机与IP关系表
                    ResVmNetwork resVmNetwork = new ResVmNetwork();
                    resVmNetwork.setIpAddress(vmNic.getPrivateIp());
                    resVmNetwork.setResNetworkSid(resNetList.get(0).getResNetworkSid());
                    resVmNetwork.setResVmSid(resVm.getResVmSid());
                    resVmNetwork.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
                    this.resVmNetworkMapper.insertSelective(resVmNetwork);
                }

            }

        } else if (WebConstants.VirtualPlatformType.VMWARE.equals(resVe.getVirtualPlatformType())) {
            // 更新网卡状态为正常
            Criteria criteria3 = new Criteria();
            criteria3.put("resVmSid", resVm.getResVmSid());
            List<VmNic> vmNics = vmCreateResult.getNics();
            List<ResVmNetwork> resVmNetList = this.resVmNetworkMapper.selectByParams(criteria3);
            for (ResVmNetwork resNet : resVmNetList) {
                if (vmNics != null && vmNics.size() > 0) {
                    for (VmNic vmNic : vmNics) {
                        if (resNet.getIpAddress().equals(vmNic.getPrivateIp())) {
                            resNet.setMac(vmNic.getMac());
                            break;
                        }
                    }
                }
                resNet.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
                this.resVmNetworkMapper.updateByPrimaryKeySelective(resNet);
            }
        } else if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
            // 物理分区时，更新配件表

            // resVmNetwork 更新状态
            Criteria netParam = new Criteria();
            netParam.put("resVmSid", resVm.getResVmSid());
            List<ResVmNetwork> resVmNetList = this.resVmNetworkMapper.selectByParams(netParam);
            for (ResVmNetwork resNet : resVmNetList) {
                resNet.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
                this.resVmNetworkMapper.updateByPrimaryKeySelective(resNet);
            }

        }

        return resVm;
    }

    /**
     * 处理虚拟机操作结果
     *
     * @param vmOperateResult the vm operate result
     */
    public void handleMessage(VmOperateResult vmOperateResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        logger.info("操作虚拟机 | MQ返回参数：" + JsonUtil.toJson(vmOperateResult));
        ResInstResult result = new ResInstResult();
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(vmOperateResult.getId());
        String action = vmOperateResult.getAction();
        try {
            // 虚拟机创建成功
            if (vmOperateResult.isSuccess()) {

                if (WebConstants.VmOperation.START.equals(action) || WebConstants.VmOperation.ACTIVATE.equals(action)) {
                    resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                } else if (WebConstants.VmOperation.STOP.equals(action) ||
                        WebConstants.VmOperation.SHUTDOWN.equals(action)) {
                    resVm.setStatus(WebConstants.ResVmStatus.POWEREDOFF);
                } else if (WebConstants.VmOperation.PAUSE.equals(action)) {
                    resVm.setStatus(WebConstants.ResVmStatus.PAUSED);
                } else if (WebConstants.VmOperation.UNPAUSE.equals(action)) {
                    resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                } else if (WebConstants.VmOperation.SUSPEND.equals(action)) {
                    resVm.setStatus(WebConstants.ResVmStatus.SUSPENDED);
                } else if (WebConstants.VmOperation.RESUME.equals(action)) {
                    resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                } else if (WebConstants.VmOperation.REBOOT.equals(action)) {
                    resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                }
                WebUtil.prepareUpdateParams(resVm);
                int vmUpResult = this.resVmMapper.updateByPrimaryKeySelective(resVm);
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmOperateResult.getVmName());
                log.setTaskType(WebConstants.TaskType.OPERATE_VM);
                taskLogger.success(log);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("resVmSid", resVm.getResVmSid());
                map.put("action", action);
                result.setStatus(ResInstResult.SUCCESS);
                result.setData(map);

            } else {
                logger.error("{} 操作失败：{}", resVm.getVmName(), vmOperateResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(resVm.getVmName());
                log.setTaskType(WebConstants.TaskType.OPERATE_VM);
                log.setTaskFailureReason(resVm.getVmName() + "操纵失败：" + vmOperateResult.getErrMsg());
                taskLogger.fail(log);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("resVmSid", resVm.getResVmSid());
                map.put("action", action);
                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(vmOperateResult.getErrMsg());
                result.setData(map);
            }


        } catch (Exception e) {
            logger.error("操作虚拟机回调异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(vmOperateResult.getVmName());
            log.setTaskType(WebConstants.TaskType.OPERATE_VM);
            log.setTaskFailureReason(vmOperateResult.getVmName() + "操纵失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("resVmSid", vmOperateResult.getId());
            map.put("action", action);
            result.setStatus(ResInstResult.FAILURE);
            result.setMessage(vmOperateResult.getErrMsg());
            result.setData(map);
        }

        // 回调service方法
//        vmOperateHanlder.operate(result);
    }

    /**
     * 处理虚拟机调整配置结果
     *
     * @param vmReconfigResult the vm reconfig result
     */
    public void handleMessage(VmReconfigResult vmReconfigResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        logger.info("资源重新配置 | MQ返回参数：" + JsonUtil.toJson(vmReconfigResult));
        ResInstResult result = new ResInstResult();
        Criteria criteria = new Criteria();
        criteria.put("vmName", vmReconfigResult.getVmName());
        List<ResVm> resVmList = this.resVmMapper.selectByParams(criteria);
        ResVm resVm = resVmList.get(0);
        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
        ResVe resVe = this.resVmService.getVeByRes(resHost);
        try {
            // 虚拟机创建成功
            if (vmReconfigResult.isSuccess()) {
                if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                    if (!StringUtil.isNullOrEmpty(vmReconfigResult.getMinMem())) {
                        resVm.setMemoryMin(vmReconfigResult.getMinMem().longValue());
                    }
                    if (!StringUtil.isNullOrEmpty(vmReconfigResult.getMemory())) {
                        resVm.setMemorySize(Long.parseLong(vmReconfigResult.getMemory()));
                    }
                    if (!StringUtil.isNullOrEmpty(vmReconfigResult.getMaxMem())) {
                        resVm.setMemoryMax(vmReconfigResult.getMaxMem().longValue());
                    }
                    if (!StringUtil.isNullOrEmpty(vmReconfigResult.getCpu())) {
                        // 最小处理单元数
                        resVm.setCpuCoresMin(vmReconfigResult.getMinProcs());
                        // 期望处理单元数
                        resVm.setCpuCores(Integer.parseInt(vmReconfigResult.getCpu()));
                        // 最大处理单元数
                        resVm.setCpuCoresMax(vmReconfigResult.getMaxProcs());
                    }
                    if (!StringUtil.isNullOrEmpty(vmReconfigResult.getDesiredProcUnits())) {
                        // 最小共享物理cpu单元
                        resVm.setPowerCpuMinUnits(vmReconfigResult.getMinProcUnits());
                        // 共享物理cpu单元
                        resVm.setPowerCpuUsedUnits(vmReconfigResult.getDesiredProcUnits());
                        // 最大共享物理cpu单元
                        resVm.setPowerCpuMaxUnits(vmReconfigResult.getMaxProcUnits());
                    }
                    //处理磁盘的变更
                    // 更新磁盘
                    List<VmDisk> disks = vmReconfigResult.getDisks();
                    List<ResVd> resVdList = new ArrayList<ResVd>();
                    for (VmDisk vmDisk : disks) {
                        ResVd resVd = this.resVdMapper.selectByPrimaryKey(vmDisk.getId());
                        if (WebConstants.VdOperate.ADD.equals(vmDisk.getOperate())) {
                            resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                            resVd.setUuid(vmDisk.getUuid());
                            resVd.setPath(vmDisk.getPath());
                            resVd.setOperate(vmDisk.getOperate());
                            resVdList.add(resVd);
                            WebUtil.prepareUpdateParams(resVd);
                            this.resVdMapper.updateByPrimaryKeySelective(resVd);
                            //将storage和volume关联起来
                            List<Volume> volumes = vmDisk.getVolumes();
                            if (!CollectionUtils.isEmpty(volumes)) {
                                for (Volume volume : volumes) {
                                    ResVolume resVolume = new ResVolume();
                                    resVolume.setResStorageSid(resVd.getAllocateResStorageSid());
                                    resVolume.setResVolumeSid(volume.getId() == null ? "" : volume.getId().toString());
                                    resVolume.setSize(volume.getSize().longValue());
                                    resVolume.setStatus("01");
                                    resVolume.setVolumeName(volume.getName());
                                    resVolume.setVolumeWwn(volume.getWwn());

                                    resVolumeMapper.insertSelective(resVolume);
                                }
                            }
                        } else if (WebConstants.VdOperate.DELLETE.equals(vmDisk.getOperate())) {
                            //删除vd关联的存储storage以及它的关联关系表
                            //主机和存储
                            Criteria deleteParam = new Criteria();
                            deleteParam.put("resStorageSid", resVd.getAllocateResStorageSid());
                            resHostStorageMapper.deleteByParams(deleteParam);
                            //存储和volume
                            resVolumeMapper.deleteByResStorageSid(resVd.getAllocateResStorageSid());
                            //存储
                            resStorageMapper.deleteByPrimaryKey(resVd.getAllocateResStorageSid());
                            //删除vd
                            resVd.setOperate(vmDisk.getOperate());
                            resVdList.add(resVd);
                            this.resVdMapper.deleteByPrimaryKey(resVd.getResVdSid());
                        }

                    }
                    resVm.setResVdList(resVdList);

                } else {
                    resVm.setCpuCores(Integer.parseInt(vmReconfigResult.getCpu()));
                    resVm.setMemorySize(Long.parseLong(vmReconfigResult.getMemory()));
                }
                resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                WebUtil.prepareUpdateParams(resVm);
                int vmUpResult = this.resVmMapper.updateByPrimaryKeySelective(resVm);
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmReconfigResult.getVmName());
                log.setTaskType(WebConstants.TaskType.RECONFIG_VM);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
                result.setData(resVm);
//				vmChangeHandler.operate(result);
            } else {
                logger.error(vmReconfigResult.getVmName() + "调整失败：" + vmReconfigResult.getErrMsg());
                // 状态回滚
                resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                WebUtil.prepareUpdateParams(resVm);
                int vmUpResult = this.resVmMapper.updateByPrimaryKeySelective(resVm);
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmReconfigResult.getVmName());
                log.setTaskType(WebConstants.TaskType.RECONFIG_VM);
                log.setTaskFailureReason(vmReconfigResult.getVmName() + "调整失败：" + vmReconfigResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(vmReconfigResult.getErrMsg());
                result.setData(resVm);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(vmReconfigResult.getVmName() + "调整失败：" + vmReconfigResult.getErrMsg());
            TaskLog log = new TaskLog();
            log.setTaskTarget(vmReconfigResult.getVmName());
            log.setTaskType(WebConstants.TaskType.RECONFIG_VM);
            log.setTaskFailureReason(vmReconfigResult.getVmName() + "调整失败：" + vmReconfigResult.getErrMsg());
            taskLogger.fail(log);
            result.setStatus(ResInstResult.FAILURE);
            result.setMessage(vmReconfigResult.getErrMsg());
            result.setData(resVm);
        }
        vmChangeHandler.operate(result);
    }

    /**
     * 处理删除虚拟机
     *
     * @param vmRemoveResult the vm remove result
     */
    public void handleMessage(VmRemoveResult vmRemoveResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        logger.info("删除虚拟机 | MQ返回参数：" + JsonUtil.toJson(vmRemoveResult));
        ResInstResult result = new ResInstResult();
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(vmRemoveResult.getSid());
        try {
            // 虚拟机删除成功
            if (vmRemoveResult.isSuccess()) {

                // 查询虚拟机信息，作为返回参数
                ResVm resVM = this.resVmService.getVmInfo(resVm.getResVmSid());
//                ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
                ResVe resVe = this.resVeMapper.selectByPrimaryKey(vmRemoveResult.getVirtEnvUuid());

                // 更新已挂载块存储为未挂载的
                Criteria criteria1 = new Criteria();
                criteria1.put("resVmSid", resVm.getResVmSid());
                List<ResVd> resVdList = this.resVdMapper.selectByParams(criteria1);

                // 当为OpenStack的时候，数据盘只是取消关联
                for (ResVd resVd : resVdList) {
                    if (WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
                        if (WebConstants.VirtualPlatformType.OPENSTACK.equals(resVe.getVirtualPlatformType())) {
                            resVd.setResVmSid("");
                            WebUtil.prepareUpdateParams(resVd);
                            this.resVdMapper.updateByPrimaryKey(resVd);
                        }
                        if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                            // 删除vd关联的volume
                            this.resVolumeMapper.deleteByResStorageSid(resVd.getAllocateResStorageSid());
                            this.resStorageMapper.deleteByPrimaryKey(resVd.getAllocateResStorageSid());
                        }
                    }
                }
                this.resVdMapper.deleteByParams(criteria1);

                // 删除端口
                List<ResVmNetwork> resVmNetworks = this.resVmNetworkMapper.selectByVmSid(resVm.getResVmSid());
                resVmNetworks.forEach(resVmNetwork -> this.resVpcPortsIpMapper.deleteByParams(new Criteria("subnetId",
                                                                                                           resVmNetwork.getResNetworkSid()
                ).put("ipAddress", resVmNetwork.getIpAddress())));

                // 删除虚拟机网络关系，并更新IP为有效
                this.resVmNetworkMapper.deleteByPrimaryKey(resVm.getResVmSid());

                // 更改配件表的占用信息
                criteria1 = new Criteria();
                criteria1.put("relateResSid", resVm.getResVmSid());
                List<ResHostItem> resHostItems = this.resHostItemMapper.selectByParams(criteria1);
                for (ResHostItem resHostItem : resHostItems) {
                    resHostItem.setRelateResSid(null);
                    resHostItem.setResAllocFlag(Integer.parseInt(WebConstants.ResHostItemAllocFlag.NOT_OCCUPIED));
                    resHostItem.setResAllocStatus(WebConstants.HostItemAllocStatus.FREE);
                    this.resHostItemMapper.updateByPrimaryKey(resHostItem);
                }

                // 删除虚拟机
                this.resVmMapper.deleteByPrimaryKey(resVm.getResVmSid());
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmRemoveResult.getVmName());
                log.setTaskType(WebConstants.TaskType.DELETE_VM);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
                result.setData(resVM);
            } else {
                if ("9999".equals(vmRemoveResult.getErrCode())) {

                    logger.error(vmRemoveResult.getVmName() + "删除失败：" + vmRemoveResult.getErrMsg());
                    // 更新状态为正常
                    resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                    WebUtil.prepareUpdateParams(resVm);
                    this.resVmMapper.updateByPrimaryKeySelective(resVm);

                    // 更新磁盘为正常
                    Criteria criteria1 = new Criteria();
                    criteria1.put("resVmSid", resVm.getResVmSid());
                    List<ResVd> resVdList = this.resVdMapper.selectByParams(criteria1);

                    for (ResVd resVd : resVdList) {
                        resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                        this.resVdMapper.updateByPrimaryKeySelective(resVd);
                    }
                }

                TaskLog log = new TaskLog();
                log.setTaskTarget(vmRemoveResult.getVmName());
                log.setTaskType(WebConstants.TaskType.DELETE_VM);
                log.setTaskFailureReason(vmRemoveResult.getVmName() + "删除失败：" + vmRemoveResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(vmRemoveResult.getErrMsg());
                result.setData(resVm);
            }

        } catch (Exception e) {
            logger.error("删除虚拟机回调异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(vmRemoveResult.getVmName());
            log.setTaskType(WebConstants.TaskType.DELETE_VM);
            log.setTaskFailureReason(vmRemoveResult.getVmName() + "删除失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);

            result.setStatus(ResInstResult.FAILURE);
            result.setMessage(vmRemoveResult.getErrMsg());
            result.setData(resVm);
        }

        // 回调service
//        if (!vmRemoveResult.isDueToFailOfCreating()) {
//            vmDeleteHandler.operate(result);
//        }
    }

    /**
     * 处理虚拟机添加网卡
     *
     * @param vmNicAddResult the vm nic add result
     */
    public void handleMessage(VmNicAddResult vmNicAddResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        try {
            ResInstResult result = new ResInstResult();
            Criteria criteria = new Criteria();
            criteria.put("vmName", vmNicAddResult.getVmName());
            List<ResVm> resVmList = this.resVmMapper.selectByParams(criteria);
            ResVm resVm = resVmList.get(0);
            // 虚拟机添加网卡成功，插入虚拟机网络关系表
            if (vmNicAddResult.isSuccess()) {
                List<VmNic> vmNics = vmNicAddResult.getNics();
                for (VmNic vmNic : vmNics) {
                    String ip = vmNic.getPrivateIp();
                    Criteria criteria2 = new Criteria();
                    criteria2.put("ipAddress", ip);
                    List<ResIp> resIps = this.resIpMapper.selectByParams(criteria2);
                    ResIp resIp = resIps.get(0);

                    ResVmNetwork resVmNet = new ResVmNetwork();
                    resVmNet.setIpAddress(ip);
                    // resVmNet.setMac(vmNic.getMac());
                    resVmNet.setResNetworkSid(resIp.getResNetworkSid());
                    resVmNet.setResVmSid(resVm.getResVmSid());
                    this.resVmNetworkMapper.insertSelective(resVmNet);

                    // 更新IP为可用
                    resIp.setStatus(WebConstants.ResIpStatus.OCCUPIED);
                    WebUtil.prepareUpdateParams(resIp);
                    this.resIpMapper.updateByPrimaryKeySelective(resIp);
                }
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmNicAddResult.getVmName());
                log.setTaskType(WebConstants.TaskType.ADD_NET_VM);
                taskLogger.success(log);
            } else {
                logger.error(vmNicAddResult.getVmName() + "添加网卡失败：" + vmNicAddResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmNicAddResult.getVmName());
                log.setTaskType(WebConstants.TaskType.DELETE_VM);
                log.setTaskFailureReason(vmNicAddResult.getVmName() + "添加网卡失败：" + vmNicAddResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setData(resVm);
            }
            // 回调service
            vmOpenHandlerImpl.operate(result);
        } catch (Exception e) {
            logger.error("虚拟机添加网卡回调异常：" + ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * 处理虚拟机删除网卡
     *
     * @param vmNicDeleteResult the vm nic delete result
     */
    public void handleMessage(VmNicDeleteResult vmNicDeleteResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        try {
            ResInstResult result = new ResInstResult();
            Criteria criteria = new Criteria();
            criteria.put("vmName", vmNicDeleteResult.getVmName());
            List<ResVm> resVmList = this.resVmMapper.selectByParams(criteria);
            ResVm resVm = resVmList.get(0);
            // 虚拟机删除网卡成功，删除虚拟机网络关系表
            if (vmNicDeleteResult.isSuccess()) {
                List<VmNic> vmNics = vmNicDeleteResult.getNics();
                for (VmNic vmNic : vmNics) {
                    String ip = vmNic.getPrivateIp();
                    Criteria criteria2 = new Criteria();
                    criteria2.put("ipAddress", ip);
                    criteria2.put("resVmSid", resVm.getResVmSid());
                    this.resVmNetworkMapper.deleteByParams(criteria2);

                    Criteria criteria3 = new Criteria();
                    criteria3.put("ipAddress", ip);
                    List<ResIp> resIps = this.resIpMapper.selectByParams(criteria3);
                    ResIp resIp = resIps.get(0);

                    // 更新IP为可用
                    resIp.setStatus(WebConstants.ResIpStatus.UNOCCUPIED);
                    WebUtil.prepareUpdateParams(resIp);
                    this.resIpMapper.updateByPrimaryKeySelective(resIp);
                }

                TaskLog log = new TaskLog();
                log.setTaskTarget(vmNicDeleteResult.getVmName());
                log.setTaskType(WebConstants.TaskType.DEL_NET_VM);
                taskLogger.success(log);
            } else {
                logger.error(vmNicDeleteResult.getVmName() + "删除网卡失败：" + vmNicDeleteResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmNicDeleteResult.getVmName());
                log.setTaskType(WebConstants.TaskType.DEL_NET_VM);
                log.setTaskFailureReason(vmNicDeleteResult.getVmName() + "删除网卡失败：" + vmNicDeleteResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setData(resVm);
            }
            // 回调service
            vmOpenHandlerImpl.operate(result);
        } catch (Exception e) {
            logger.error("虚拟机删除回调异常：" + ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * 处理整体配置虚拟机
     *
     * @param vmModifyResult the vm modify result
     */
    public void handleMessage(VmModifyResult vmModifyResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        try {
            ResInstResult result = new ResInstResult();
            // 更新虚拟机状态为正常
            ResVm resVm = this.resVmMapper.selectByPrimaryKey(vmModifyResult.getVmId());
            if (vmModifyResult.isSuccess()) {

                resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                this.resVmMapper.updateByPrimaryKeySelective(resVm);

                // 更新磁盘
                List<VmDisk> disks = vmModifyResult.getDisks();
                List<ResVd> resVdList = new ArrayList<ResVd>();
                for (VmDisk vmDisk : disks) {
                    ResVd resVd = this.resVdMapper.selectByPrimaryKey(vmDisk.getId());
                    if (WebConstants.VdOperate.ADD.equals(vmDisk.getOperate())) {
                        resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                        resVd.setUuid(vmDisk.getUuid());
                        resVd.setPath(vmDisk.getPath());
                        resVd.setOperate(vmDisk.getOperate());
                        resVdList.add(resVd);
                        WebUtil.prepareUpdateParams(resVd);
                        this.resVdMapper.updateByPrimaryKeySelective(resVd);
                    } else if (WebConstants.VdOperate.DELLETE.equals(vmDisk.getOperate())) {
                        resVd.setOperate(vmDisk.getOperate());
                        resVdList.add(resVd);
                        this.resVdMapper.deleteByPrimaryKey(resVd.getResVdSid());
                    } else if (WebConstants.VdOperate.EXPAND.equals(vmDisk.getOperate())) {
                        resVd.setAllocateDiskSize(Long.parseLong(vmDisk.getSize()));
                        resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                        resVd.setOperate(vmDisk.getOperate());
                        resVdList.add(resVd);
                        WebUtil.prepareUpdateParams(resVd);
                        this.resVdMapper.updateByPrimaryKeySelective(resVd);
                    }

                }
                resVm.setResVdList(resVdList);
                List<VmNic> nics = vmModifyResult.getNics();
                List<ResIp> resIpList = new ArrayList<ResIp>();
                for (VmNic vmNic : nics) {

                    // 查询网络所属资源池
                    ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(vmNic.getNetId());
                    ResTopology resTopology = this.resTopologyMapper.selectByPrimaryKey(resNetwork.getResPoolSid());

                    if (WebConstants.NetOperate.ADD.equals(vmNic.getOperate())) {

                        Criteria criteria = new Criteria();
                        criteria.put("ipAddress", vmNic.getPrivateIp());
                        criteria.put("resVmSid", resVm.getResVmSid());
                        List<ResVmNetwork> netWorkList = this.resVmNetworkMapper.selectByParams(criteria);
                        ResVmNetwork resVmNet = netWorkList.get(0);
                        resVmNet.setMac(vmNic.getMac());
                        resVmNet.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
                        this.resVmNetworkMapper.updateByPrimaryKeySelective(resVmNet);

                        Criteria criteria1 = new Criteria();
                        criteria1.put("resNetworkSid", vmNic.getNetId());
                        criteria1.put("ipAddress", vmNic.getPrivateIp());
                        // 更新IP为有效
                        List<ResIp> ipList = this.resIpMapper.selectByParams(criteria1);
                        ResIp resIp = ipList.get(0);

                        // 更新IP状态为已占用
                        resIp.setResPoolType(resTopology.getResTopologyType());
                        resIp.setOperate(vmNic.getOperate());
                        resIpList.add(resIp);
                    } else if (WebConstants.NetOperate.DELLETE.equals(vmNic.getOperate())) {

                        Criteria criteria1 = new Criteria();
                        criteria1.put("resNetworkSid", vmNic.getNetId());
                        criteria1.put("resVmSid", resVm.getResVmSid());
                        criteria1.put("mac", vmNic.getMac());
                        List<ResVmNetwork> netWorkList = this.resVmNetworkMapper.selectByParams(criteria1);
                        ResVmNetwork resVmNet = netWorkList.get(0);

                        Criteria criteria2 = new Criteria();
                        criteria2.put("resNetworkSid", vmNic.getNetId());
                        criteria2.put("ipAddress", resVmNet.getIpAddress());

                        // 更新IP为有效
                        List<ResIp> ipList = this.resIpMapper.selectByParams(criteria2);
                        ResIp resIp = ipList.get(0);
                        // 删除虚拟机网络关系
                        this.resVmNetworkMapper.deleteByParams(criteria1);
                        resIp.setOperate(vmNic.getOperate());
                        resIpList.add(resIp);
                    } else {

                        Criteria criteria = new Criteria();
                        criteria.put("ipAddress", vmNic.getPrivateIp());
                        criteria.put("resVmSid", resVm.getResVmSid());
                        List<ResVmNetwork> netWorkList = this.resVmNetworkMapper.selectByParams(criteria);
                        ResVmNetwork resVmNet = netWorkList.get(0);
                        if (!StringUtil.isNullOrEmpty(vmNic.getMac())) {
                            resVmNet.setMac(vmNic.getMac());
                        }
                        resVmNet.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
                        this.resVmNetworkMapper.updateByPrimaryKeySelective(resVmNet);

                        Criteria criteria1 = new Criteria();
                        criteria1.put("resNetworkSid", vmNic.getNetId());
                        criteria1.put("ipAddress", vmNic.getPrivateIp());

                        // 更新IP为有效
                        List<ResIp> ipList = this.resIpMapper.selectByParams(criteria1);
                        ResIp resIp = ipList.get(0);
                        resIp.setResPoolType(resTopology.getResTopologyType());
                        resIp.setOperate(WebConstants.NetOperate.UNCHANGE);
                        resIpList.add(resIp);
                    }
                }
                resVm.setResIpList(resIpList);
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmModifyResult.getVmName());
                log.setTaskType(WebConstants.TaskType.RECONFIG_VM);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
                result.setData(resVm);
            } else {

                // TODO 判断返回错误代码
                // 整体变更失败，删除添加的盘和网络
                if ("9999".equals(vmModifyResult.getErrCode())) {

                    // 更新虚拟机CPU、内存
                    resVm.setCpuCores(Integer.parseInt(vmModifyResult.getOldCpu()));
                    resVm.setMemorySize(Long.parseLong(vmModifyResult.getOldMemory()));
                    resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                    this.resVmMapper.updateByPrimaryKeySelective(resVm);

                    Criteria criteria = new Criteria();
                    criteria.put("resVmSid", resVm.getResVmSid());
                    List<ResVd> resVdList = this.resVdMapper.selectByParams(criteria);

                    if (resVdList != null && resVdList.size() > 0) {
                        for (ResVd resVd : resVdList) {
                            if (WebConstants.ResVdStatus.CREATING.equals(resVd.getStatus())) {
                                this.resVdMapper.deleteByPrimaryKey(resVd.getResVdSid());
                            } else if (WebConstants.ResVdStatus.DELETING.equals(resVd.getStatus())) {
                                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                                this.resVdMapper.updateByPrimaryKeySelective(resVd);
                            } else if (WebConstants.ResVdStatus.SETTING.equals(resVd.getStatus())) {
                                for (VmDisk vmDisk : vmModifyResult.getDisks()) {
                                    if (resVd.getResVdSid().equals(vmDisk.getId())) {
                                        resVd.setAllocateDiskSize(Long.parseLong(vmDisk.getOldSize()));
                                        resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                                        this.resVdMapper.updateByPrimaryKeySelective(resVd);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    // 查询虚拟机网络
                    List<ResVmNetwork> resVmNetList = this.resVmNetworkMapper.selectByParams(criteria);
                    if (resVmNetList != null && resVmNetList.size() > 0) {
                        for (ResVmNetwork resVmNet : resVmNetList) {
                            if (WebConstants.ResVmNetworkStatus.CREATING.equals(resVmNet.getStatus())) {
                                Criteria criteria2 = new Criteria();
                                criteria2.put("resVmSid", resVmNet.getResVmSid());
                                criteria2.put("resNetworkSid", resVmNet.getResNetworkSid());
                                this.resVmNetworkMapper.deleteByParams(criteria2);
                            } else if (WebConstants.ResVmNetworkStatus.DELETING.equals(resVmNet.getStatus())) {
                                resVmNet.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
                                this.resVmNetworkMapper.updateByPrimaryKeySelective(resVmNet);
                            }
                        }
                    }
                    result.setReSend(true);
                }

                logger.error(vmModifyResult.getVmName() + "整体变更失败：" + vmModifyResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmModifyResult.getVmName());
                log.setTaskType(WebConstants.TaskType.RECONFIG_VM);
                log.setTaskFailureReason(vmModifyResult.getVmName() + "整体变更失败：" + vmModifyResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(vmModifyResult.getErrMsg());
                result.setData(resVm);
            }
            vmChangeHandler.operate(result);
        } catch (Exception e) {
            logger.error("整体配置虚拟机回调异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(vmModifyResult.getVmName());
            log.setTaskType(WebConstants.TaskType.RECONFIG_VM);
            log.setTaskFailureReason(vmModifyResult.getVmName() + "整体变更失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * 处理整体配置虚拟机
     *
     * @param vmMigrateResult the vm migrate result
     */
    public void handleMessage(VmMigrateResult vmMigrateResult) {
        /*TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        try {
			ResInstResult result = new ResInstResult();
			// 更新虚拟机状态为正常
			ResVm resVm = this.resVmMapper.selectByPrimaryKey(vmMigrateResult.getVmId());
			if (vmMigrateResult.isSuccess()) {

				resVm.setStatus(WebConstants.ResVmStatus.NORMAL);

				if (!StringUtil.isNullOrEmpty(vmMigrateResult.getTargetHostId())
						&& StringUtil.isNullOrEmpty(vmMigrateResult.getTargetStoreId())) {
					resVm.setAllocateResHostSid(vmMigrateResult.getTargetHostId());
					WebUtil.prepareUpdateParams(resVm);
					this.resVmMapper.updateByPrimaryKeySelective(resVm);
				} else if (StringUtil.isNullOrEmpty(vmMigrateResult.getTargetHostId())
						&& !StringUtil.isNullOrEmpty(vmMigrateResult.getTargetStoreId())) {

					WebUtil.prepareUpdateParams(resVm);
					this.resVmMapper.updateByPrimaryKeySelective(resVm);

					// 查询虚拟机下磁盘
					Criteria criteria = new Criteria();
					criteria.put("resVmSid", resVm.getResVmSid());
					List<ResVd> resVdList = this.resVdService.selectByParams(criteria);
					if (resVdList != null && resVdList.size() > 0) {
						for (ResVd resVd : resVdList) {
							// TODO 现在磁盘的UUID是存储路径，目前先替换UUID中存储的名称
							String UUID = resVd.getUuid();
							int startIndex = UUID.indexOf("[");
							int endIndex = UUID.indexOf("]");
							String subUUID = UUID.substring(endIndex + 1);
							ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(vmMigrateResult
									.getTargetStoreId());
							String newUUID = "[" + resStorage.getStorageName() + "]" + subUUID;
							resVd.setAllocateResStorageSid(vmMigrateResult.getTargetStoreId());
							resVd.setUuid(newUUID);
							resVd.setPath(newUUID);
							WebUtil.prepareUpdateParams(resVd);
							this.resVdService.updateByPrimaryKeySelective(resVd);
						}

					}
				} else if (!StringUtil.isNullOrEmpty(vmMigrateResult.getTargetHostId())
						&& !StringUtil.isNullOrEmpty(vmMigrateResult.getTargetStoreId())) {
					resVm.setAllocateResHostSid(vmMigrateResult.getTargetHostId());
					WebUtil.prepareUpdateParams(resVm);
					this.resVmMapper.updateByPrimaryKeySelective(resVm);

					// 查询虚拟机下磁盘
					Criteria criteria = new Criteria();
					criteria.put("resVmSid", resVm.getResVmSid());
					List<ResVd> resVdList = this.resVdService.selectByParams(criteria);
					if (resVdList != null && resVdList.size() > 0) {
						for (ResVd resVd : resVdList) {
							// TODO 现在磁盘的UUID是存储路径，目前先替换UUID中存储的名称
							String UUID = resVd.getUuid();
							int startIndex = UUID.indexOf("[");
							int endIndex = UUID.indexOf("]");
							String subUUID = UUID.substring(endIndex + 1);
							ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(vmMigrateResult
									.getTargetStoreId());
							String newUUID = "[" + resStorage.getStorageName() + "]" + subUUID;
							resVd.setAllocateResStorageSid(vmMigrateResult.getTargetStoreId());
							resVd.setUuid(newUUID);
							WebUtil.prepareUpdateParams(resVd);
							this.resVdService.updateByPrimaryKeySelective(resVd);
						}

					}
				} else {
					// TODO
				}
				TaskLog log = new TaskLog();
				log.setTaskTarget(vmMigrateResult.getVmName());
				log.setTaskType(WebConstants.TaskType.MIGRATE_VM);
				taskLogger.success(log);

				result.setStatus(ResInstResult.SUCCESS);
				result.setData(resVm);
			} else {
				logger.error(vmMigrateResult.getVmName() + "迁移失败：" + vmMigrateResult.getErrMsg());
				TaskLog log = new TaskLog();
				log.setTaskTarget(vmMigrateResult.getVmName());
				log.setTaskType(WebConstants.TaskType.MIGRATE_VM);
				log.setTaskFailureReason(vmMigrateResult.getVmName() + "迁移失败：" + vmMigrateResult.getErrMsg());
				taskLogger.fail(log);

				result.setStatus(ResInstResult.FAILURE);
				result.setMessage(vmMigrateResult.getErrMsg());
				result.setData(resVm);
			}
		} catch (Exception e) {
			logger.error("虚拟机迁移异常：" + ExceptionUtils.getFullStackTrace(e));
			TaskLog log = new TaskLog();
			log.setTaskTarget(vmMigrateResult.getVmName());
			log.setTaskType(WebConstants.TaskType.MIGRATE_VM);
			log.setTaskFailureReason(vmMigrateResult.getVmName() + "迁移失败：" + ExceptionUtils.getFullStackTrace(e));
			taskLogger.fail(log);
		}*/
    }

    /**
     * 同步镜像模板
     *
     * @param templateScanResult the template scan result
     */
    public void handleMessage(TemplateScanResult templateScanResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        ResInstResult result = new ResInstResult();
        ResTopology rt = this.resTopologyMapper.selectByPrimaryKey(templateScanResult.getResVeSid());
        if (templateScanResult.isSuccess()) {
            List<ResImage> resImageList = new ArrayList<ResImage>();
            List<TemplateVO> templates = templateScanResult.getTemplates();
            String resVeSid = templateScanResult.getResVeSid();
            //获取资源环境类型
            ResVe resve = this.resVeMapper.selectByPrimaryKey(resVeSid);
            String json = JsonUtil.toJson(templates);
            System.out.println(json);
            List<TemplateVO> cloneTemplates = null;
            if (templates.size() > 0) {
                cloneTemplates = new ArrayList<TemplateVO>(templates);
                Criteria criteria = new Criteria();
                criteria.put("resVeSidLike", templateScanResult.getResVeSid());
                resImageList = this.resImageMapper.selectByParams(criteria);

                if (null != resImageList && resImageList.size() > 0) {
                    for (ResImage image : resImageList) {
                        boolean imageFlag = false;
                        if (templates != null && templates.size() > 0) {
                            for (TemplateVO templateVO : templates) {
                                //如果数据已经存在数据库中，则更新数据
                                //判断UUID是都相同
                                if ("HMC".equals(resve.getVirtualPlatformType())) {
//									if(image.getUuid().equals(templateVO.getUuid())){
                                    if (image.getImageName().equals(templateVO.getImageName())) {
                                        image.setImageId(templateVO.getImageName());
                                        //image.setUuid(templateVO.getUuid());
                                        //image.setOsVersion(templateVO.getOsVersion());
                                        criteria = new Criteria();
                                        criteria.put("parentCodeValue", "AIX");
                                        criteria.put("codeCategory", "OS_VERSION");
                                        criteria.put("enabled", WebConstants.CodeEnable.ABLE);
                                        List<Code> code_list = this.codeService.selectByParams(criteria);
                                        String newAixOsVersion = templateVO.getOsVersion()
                                                                           .substring(4,
                                                                                      templateVO.getOsVersion()
                                                                                                .length() - 3
                                                                           );
                                        for (int i = 0; i < code_list.size(); i++) {
                                            if (code_list.get(i).getCodeDisplay().contains(newAixOsVersion)) {
                                                image.setOsVersion(code_list.get(i).getCodeDisplay());
                                                break;
                                            }
                                        }
                                        WebUtil.prepareUpdateParams(image);
                                        int upResImage = this.resImageMapper.updateByPrimaryKeySelective(image);
                                        cloneTemplates.remove(templateVO);
                                        imageFlag = true;
                                        break;
                                    }
                                } else {
                                    if (image.getUuid().equals(templateVO.getUuid())) {
                                        if (templateVO.getOsVersion().contains("Linux") ||
                                                templateVO.getOsVersion().contains("SUSE") ||
                                                templateVO.getOsVersion().contains("Red Hat") ||
                                                templateVO.getOsVersion().contains("CentOS")) {
                                            image.setOsType("Linux");
                                        } else if (templateVO.getOsVersion().contains("Win") ||
                                                templateVO.getOsVersion().contains("Windows")) {
                                            image.setOsType("Windows");
                                        }
                                        image.setImageId(templateVO.getImageId());
                                        image.setUuid(templateVO.getUuid());
//										if(templateVO.getOsVersion().contains("位")){
//											String newOsVersion = templateVO.getOsVersion().replace(" 位", "-bit");
//											image.setOsVersion(newOsVersion);
//										}else{
//											image.setOsVersion(templateVO.getOsVersion());
//										}
                                        WebUtil.prepareUpdateParams(image);
                                        int upResImage = this.resImageMapper.updateByPrimaryKeySelective(image);
                                        cloneTemplates.remove(templateVO);
                                        imageFlag = true;
                                        break;
                                    }
                                }
//								if(!imageFlag){
//									this.resImageService.deleteByPrimaryKey(image.getResImageSid());
//								}
                            }
                            if (!imageFlag) {
                                this.resImageMapper.deleteByPrimaryKey(image.getResImageSid());
                            }
                        } else {
                            this.resImageMapper.deleteByPrimaryKey(image.getResImageSid());
                        }
                    }
                }
                if (null != cloneTemplates && cloneTemplates.size() > 0) {
                    for (TemplateVO templateVO : cloneTemplates) {
                        String rimageMapStr = JsonUtil.toJson(templateVO);

                        ResImage resimage = JsonUtil.fromJson(rimageMapStr, ResImage.class);
                        if ("HMC".equals(resve.getVirtualPlatformType())) {
                            String imageName = resimage.getImageName();
                            criteria = new Criteria();
                            criteria.put("parentCodeValue", "AIX");
                            criteria.put("codeCategory", "OS_VERSION");
                            criteria.put("enabled", WebConstants.CodeEnable.ABLE);
                            List<Code> code_list = this.codeService.selectByParams(criteria);
                            String newAixOsVersion = resimage.getOsVersion()
                                                             .substring(4, templateVO.getOsVersion().length() - 3);
                            for (int i = 0; i < code_list.size(); i++) {
                                if (code_list.get(i).getCodeDisplay().contains(newAixOsVersion)) {
                                    resimage.setOsVersion(code_list.get(i).getCodeDisplay());
                                    break;
                                }
                            }
                            resimage.setImageType("");
                            resimage.setStatus(WebConstants.ImageStatus.NEWCREATE);
                            resimage.setResVeSid(resVeSid);
                            resimage.setImageName(imageName);
                            resimage.setImageId(resimage.getImageName());
                            resimage.setOsType("AIX");
                            resimage.setOsVersion(resimage.getOsVersion());
                        } else {
                            if (resimage.getOsVersion().contains("Linux") || resimage.getOsVersion().contains("SUSE") ||
                                    resimage.getOsVersion().contains("Red Hat") ||
                                    resimage.getOsVersion().contains("CentOS")) {
                                resimage.setOsType("Linux");
                            } else if (resimage.getOsVersion().contains("Win") ||
                                    resimage.getOsVersion().contains("Windows")) {
                                resimage.setOsType("Windows");
                            }
                            String imageName = resimage.getImageId();
                            resimage.setImageType("");
                            resimage.setStatus(WebConstants.ImageStatus.NEWCREATE);
                            resimage.setResVeSid(resVeSid);
                            resimage.setImageName(imageName);
                            if (templateVO.getOsVersion().contains("位")) {
                                String newOsVersion = templateVO.getOsVersion().replace(" 位", "-bit");
                                resimage.setOsVersion(newOsVersion);
                            } else {
                                resimage.setOsVersion(templateVO.getOsVersion());
                            }
                        }
                        WebUtil.prepareInsertParams(resimage);
                        int insertResult = this.resImageMapper.insertSelective(resimage);
                    }
                }
            }
            TaskLog log = new TaskLog();
            log.setTaskTarget(rt.getResTopologyName());
            log.setTaskType(WebConstants.TaskType.CREATE_IMAGE);
            taskLogger.success(log);
            result.setStatus(ResInstResult.SUCCESS);
        } else {
            logger.error("同步镜像模板失败：" + templateScanResult.getErrMsg());
            TaskLog log = new TaskLog();
            log.setTaskType(WebConstants.TaskType.CREATE_IMAGE);
            log.setTaskTarget(rt.getResTopologyName());
            log.setTaskFailureReason("同步镜像模板失败：" + templateScanResult.getErrMsg());
            taskLogger.fail(log);
            result.setStatus(ResInstResult.FAILURE);
            result.setMessage(templateScanResult.getErrMsg());
        }

    }


    /**
     * 创建虚拟机快照
     *
     * @param vmSnapshotCreateResult the vm snapshot create result
     */
    public void handleMessage(VmSnapshotCreateResult vmSnapshotCreateResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        //更新快照表
        String snapshotName = vmSnapshotCreateResult.getSnapshotName();
        Criteria example = new Criteria();
        example.put("backupName", snapshotName);
        List<ResVmBackup> backUps = resVmBackupMapper.selectByParams(example);
        ResVmBackup backUp = backUps.get(0);
        try {

            ResInstResult result = new ResInstResult();
            if (vmSnapshotCreateResult.isSuccess()) {

                backUp.setStatus(WebConstants.BACKUP_STATUS.CREATE_SUCCESS);
                backUp.setAllocateBackupId(vmSnapshotCreateResult.getImageId());
                resVmBackupMapper.updateByPrimaryKeySelective(backUp);

                TaskLog log = new TaskLog();
                log.setTaskTarget(vmSnapshotCreateResult.getVmName());
                log.setTaskType(WebConstants.TaskType.CREATE_VM_SNAPSHOT);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(vmSnapshotCreateResult.getVmName() + "创建快照失败：" + vmSnapshotCreateResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmSnapshotCreateResult.getVmName());
                log.setTaskType(WebConstants.TaskType.CREATE_VM_SNAPSHOT);
                log.setTaskFailureReason(
                        vmSnapshotCreateResult.getVmName() + "创建快照失败：" + vmSnapshotCreateResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(vmSnapshotCreateResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("虚拟机快照创建异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(vmSnapshotCreateResult.getVmName());
            log.setTaskType(WebConstants.TaskType.CREATE_VM_SNAPSHOT);
            log.setTaskFailureReason(
                    vmSnapshotCreateResult.getVmName() + "创建快照失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * 删除虚拟机快照
     *
     * @param vmSnapshotDeleteResult the vm snapshot delete result
     */
    public void handleMessage(VmSnapshotDeleteResult vmSnapshotDeleteResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        try {

            ResInstResult result = new ResInstResult();
            if (vmSnapshotDeleteResult.isSuccess()) {
                resVmBackupMapper.deleteByPrimaryKey(Long.parseLong(vmSnapshotDeleteResult.getResId()));
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmSnapshotDeleteResult.getVmName());
                log.setTaskType(WebConstants.TaskType.DELETE_VM_SNAPSHOT);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(vmSnapshotDeleteResult.getVmName() + "删除快照失败：" + vmSnapshotDeleteResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmSnapshotDeleteResult.getVmName());
                log.setTaskType(WebConstants.TaskType.DELETE_VM_SNAPSHOT);
                log.setTaskFailureReason(
                        vmSnapshotDeleteResult.getVmName() + "删除快照失败：" + vmSnapshotDeleteResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(vmSnapshotDeleteResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("虚拟机快照创建异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(vmSnapshotDeleteResult.getVmName());
            log.setTaskType(WebConstants.TaskType.DELETE_VM_SNAPSHOT);
            log.setTaskFailureReason(
                    vmSnapshotDeleteResult.getVmName() + "删除快照失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * 恢复虚拟机
     *
     * @param vmRebuildResult the vm rebuild result
     */
    public void handleMessage(VmRebuildResult vmRebuildResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        try {

            ResInstResult result = new ResInstResult();
            if (vmRebuildResult.isSuccess()) {

//				resVmBackupMapper.deleteByPrimaryKey(backUp.getBackupSid());
                //更新虚拟机状态为关机
                Criteria params = new Criteria();
                params.put("uuid", vmRebuildResult.getUuid());
                List<ResVm> vms = resVmMapper.selectByParams(params);
                ResVm resVm = vms.get(0);
                resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
                resVmMapper.updateByPrimaryKeySelective(resVm);

                TaskLog log = new TaskLog();
                log.setTaskTarget(vmRebuildResult.getVmName());
                log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
                taskLogger.success(log);

                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(vmRebuildResult.getVmName() + "虚拟机恢复失败：" + vmRebuildResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(vmRebuildResult.getVmName());
                log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
                log.setTaskFailureReason(vmRebuildResult.getVmName() + "虚拟机恢复失败：" + vmRebuildResult.getErrMsg());
                taskLogger.fail(log);

                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(vmRebuildResult.getErrMsg());
            }
        } catch (Exception e) {
            logger.error("虚拟机恢复异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(vmRebuildResult.getVmName());
            log.setTaskType(WebConstants.TaskType.REVERT_VM_SNAPSHOT);
            log.setTaskFailureReason(vmRebuildResult.getVmName() + "虚拟机恢复失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
        }
    }

    /**
     * 虚拟软件安装
     *
     * @param softwareDeployResult the software deploy result
     */
    public void handleMessage(SoftwareDeployResult softwareDeployResult) {
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        String target = "";
        ResInstResult result = new ResInstResult();
        try {
            logger.info("虚拟软件安装 | MQ返回参数：" + JsonUtil.toJson(softwareDeployResult));
            List<String> softwareNames = new ArrayList<String>();
            List<ResOsSoftware> softwares = new ArrayList<ResOsSoftware>();
            List<Software> deploySoftwares = softwareDeployResult.getSoftwares();
            if (deploySoftwares != null) {
                for (Software software : deploySoftwares) {
                    if (Strings.isNullOrEmpty(software.getName())) {
                        softwareNames.add(software.getName());
                    }
                    ResOsSoftware osSoftware = new ResOsSoftware();
                    osSoftware.setSoftwareVersion(software.getVersion());
                    osSoftware.setResSoftwareSid(software.getId());
                    if (software.isSuccess()) {
                        osSoftware.setStatus(WebConstants.OsSoftwareStatus.INSTALLED);
                    } else {
                        osSoftware.setStatus(WebConstants.OsSoftwareStatus.EXCEPTION);
                    }
                    this.resOsSoftwareMapper.updateByPrimaryKeySelective(osSoftware);
                    softwares.add(osSoftware);
                }
            }
            target = Joiner.on(";").join(softwareNames);

            if (softwareDeployResult.isSuccess()) {
                TaskLog log = new TaskLog();
                log.setTaskTarget(target);
                log.setTaskType(WebConstants.TaskType.INSTALL_SOFTWARE);
                taskLogger.success(log);
                result.setStatus(ResInstResult.SUCCESS);
            } else {
                logger.error(target + " 软件安装失败：" + softwareDeployResult.getErrMsg());
                TaskLog log = new TaskLog();
                log.setTaskTarget(target);
                log.setTaskType(WebConstants.TaskType.INSTALL_SOFTWARE);
                log.setTaskFailureReason(target + " 软件安装失败：" + softwareDeployResult.getErrMsg());
                taskLogger.fail(log);
                result.setStatus(ResInstResult.FAILURE);
                result.setMessage(softwareDeployResult.getErrMsg());
            }
            ResVm resVm = this.resVmService.getVmInfo(softwareDeployResult.getVmId());
            resVm.setSoftwares(softwares);
            result.setData(resVm);
        } catch (Exception e) {
            logger.error("软件安装异常：" + ExceptionUtils.getFullStackTrace(e));
            TaskLog log = new TaskLog();
            log.setTaskTarget(target);
            log.setTaskType(WebConstants.TaskType.INSTALL_SOFTWARE);
            log.setTaskFailureReason(target + " 软件软件失败：" + ExceptionUtils.getFullStackTrace(e));
            taskLogger.fail(log);
            result.setStatus(ResInstResult.FAILURE);
        }
        // 回调service
        softwareAutoInstallHandlerImpl.operate(result);
    }
}
