package com.h3c.idcloud.core.adapter.facade.message;

import com.google.common.collect.Sets;

import com.h3c.idcloud.core.adapter.facade.NetHandler;
import com.h3c.idcloud.core.adapter.facade.ScanHandler;
import com.h3c.idcloud.core.adapter.facade.StorageHandler;
import com.h3c.idcloud.core.adapter.facade.VmHandler;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Vm;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.DataStoreVo;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.Disk;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.DiskFormat;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Fdisks;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.ServerNetwork;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.ServerNic;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Volume;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.block.BlockBackupCreate;
import com.h3c.idcloud.core.adapter.pojo.block.BlockBackupRecovery;
import com.h3c.idcloud.core.adapter.pojo.block.BlockClone;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotRecovery;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupCreateResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockBackupRecoveryResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockCloneResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotRecovryResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreCreate;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreDelete;
import com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreExtend;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreCreateResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.datastore.result.DataStoreExtendResult;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskCreate;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDelete;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskExpand;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskCreateResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskExpandResult;
import com.h3c.idcloud.core.adapter.pojo.network.NetCreate;
import com.h3c.idcloud.core.adapter.pojo.network.NetDelete;
import com.h3c.idcloud.core.adapter.pojo.network.Network;
import com.h3c.idcloud.core.adapter.pojo.network.Subnet;
import com.h3c.idcloud.core.adapter.pojo.network.result.NetCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.NetDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.scan.AllInOneScan;
import com.h3c.idcloud.core.adapter.pojo.scan.TemplateScan;
import com.h3c.idcloud.core.adapter.pojo.scan.result.AllInOneScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.TemplateScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.NetworkVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.TemplateVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VmVO;
import com.h3c.idcloud.core.adapter.pojo.software.Software;
import com.h3c.idcloud.core.adapter.pojo.software.SoftwareDeploy;
import com.h3c.idcloud.core.adapter.pojo.software.result.SoftwareDeployResult;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmDisk;
import com.h3c.idcloud.core.adapter.pojo.vm.VmImageCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmMigrate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmModify;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNicAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNicDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRebuild;
import com.h3c.idcloud.core.adapter.pojo.vm.VmReconfig;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRecovery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;
import com.h3c.idcloud.core.adapter.pojo.vm.VmResize;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotRevert;
import com.h3c.idcloud.core.adapter.pojo.vm.VmUserAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmCreateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmImageCreateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmInfo;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmMigrateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmModifyResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmNicAddResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmNicDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmQueryResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmRebuildResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmReconfigResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmRecoveryResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmRemoveResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmResizeResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmSnapshotCreateResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmSnapshotDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmSnapshotRevertResult;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * heavy listener
 *
 * @author qct
 */
@Service
public class HeavyListener {

    /**
     * LOGGER
     */
    private static Logger LOGGER = LoggerFactory.getLogger(HeavyListener.class);
    /**
     * handle action about virtual machine
     */
    @Autowired
    private VmHandler vmHandler;
    /**
     * handle action about storage
     */
    @Autowired
    private StorageHandler storageHandler;
    /**
     * handle action about net
     */
    @Autowired
    private NetHandler netHandler;
    /**
     * handle scan action
     */
    @Autowired
    private ScanHandler scanHandler;

    /**
     * receive and handle vm modify
     *
     * @param vmModify vm modify param
     * @return vm modification result
     */
    public VmModifyResult handleMessage(VmModify vmModify) {

        LOGGER.info("receiving message for modifying vm" + "----" + "virtual type : " + vmModify.getVirtEnvType()
                + " vm name : " + vmModify.getVmName());

        LOGGER.info("msg id : " + vmModify.getMsgId());

        VmModifyResult vmModifyResult = new VmModifyResult();
        vmModifyResult = (VmModifyResult) BaseUtil.setResult(vmModify, VmModifyResult.class);
        vmModifyResult.setMsgId(vmModify.getMsgId());
        vmModifyResult.setVmName(vmModify.getVmName());
        vmModifyResult.setVmId(vmModify.getVmId());
        vmModifyResult.setCpu(vmModify.getCpu());
        vmModifyResult.setMemory(vmModify.getMemory());
        vmModifyResult.setNics(vmModify.getNics());
        vmModifyResult.setDisks(vmModify.getDisks());
        vmModifyResult.setOldCpu(vmModify.getOldCpu());
        vmModifyResult.setOldMemory(vmModify.getOldMemory());

        List<VmDisk> vmDisks = vmModify.getDisks();

        List<VmNic> vmNics = vmModify.getNics();

        try {
            Server server = vmHandler.modifyVm(vmModify);
            List<Disk> disks = server.getDisks();
            for (VmDisk vmDisk : vmDisks) {
                for (Disk disk : disks) {
                    if (vmDisk.getId().equals(disk.getId())) {
                        vmDisk.setUuid(disk.getUuid());
                        vmDisk.setPath(disk.getPath());
                        vmDisk.setDeviceTagert(disk.getScsiUnit());
                        break;
                    }
                }
            }
            List<ServerNic> nics = server.getNics();
            for (VmNic vmNic : vmNics) {
                for (ServerNic serverNic : nics) {
                    if (vmNic.getPrivateIp().equals(serverNic.getPrivateIp())) {
                        vmNic.setMac(serverNic.getMac());
                        break;
                    }
                }
            }

            vmModifyResult.setDisks(vmDisks);
            vmModifyResult.setNics(vmNics);

            vmModifyResult.setSuccess(true);
            LOGGER.info("[adaptor] vm :" + vmModify.getVmName() + " has been modified successfully");

            if ("linux".equalsIgnoreCase(vmModify.getOsCategory())) {
                List<VmDisk> allDisks = vmModify.getDisks();
                List<VmDisk> fDisks = new ArrayList<VmDisk>();
                if (!CollectionUtils.isEmpty(allDisks)) {
                    for (VmDisk vmDisk : allDisks) {
                        if ("add".equalsIgnoreCase(vmDisk.getOperate())) {
                            fDisks.add(vmDisk);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(fDisks)) {
                    LOGGER.info("执行格式化磁盘....");
                    DiskFormat diskFormat = new DiskFormat();
                    diskFormat.setTargetSerIp(vmModify.getTargetSerIp());
                    diskFormat.setTargetUser(vmModify.getTargetUser());
                    diskFormat.setTargetPwd(vmModify.getTargetPwd());
                    diskFormat.setDisks(fDisks);
                    diskFormat.setProviderType("HMC");
                    Fdisks formatDisk = vmHandler.formatDisk(diskFormat);
                    if (formatDisk.isSuccess()) {
                        LOGGER.info("格式化磁盘成功！");
                    } else {
                        LOGGER.error("格式化磁盘失败！");
                    }
                }
            }
        } catch (CommonAdapterException e) {

            vmModifyResult.setSuccess(false);
            vmModifyResult.setErrCode(e.getErrCode());
            vmModifyResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            vmModifyResult.setSuccess(false);
            vmModifyResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmModifyResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return vmModifyResult;
    }

    public VmCreateResult handleMessage(VmCreate vmCreate) {

        LOGGER.info("receiving message for creating vm" + "----" + "virtual type : " + vmCreate.getVirtEnvType()
                + " vm name : " + vmCreate.getName());

        LOGGER.info("msg id : " + vmCreate.getMsgId());

        VmCreateResult vmCreateResult = new VmCreateResult();
        vmCreateResult = (VmCreateResult) BaseUtil.setResult(vmCreate, VmCreateResult.class);
        vmCreateResult.setMsgId(vmCreate.getMsgId());
        vmCreateResult.setVmName(vmCreate.getName());
        vmCreateResult.setId(vmCreate.getId());
        vmCreateResult.setSoftware(vmCreate.getSoftware());
        List<VmDisk> vmdisks = new ArrayList<VmDisk>();
        ArrayList<VmNic> lstNics = new ArrayList<VmNic>();

        ArrayList<ServerNetwork> networks = new ArrayList<ServerNetwork>();

        try {
            Server server = vmHandler.createVm(vmCreate);
            vmCreateResult.setSuccess(server.isSuccess());
            vmCreateResult.setUuid(server.getUuid());
            if (!StringUtils.isEmpty(server.getHost())) {
                vmCreateResult.setHost(server.getHost());
            }
            // vmCreateResult.setHost(server.getHost());
            if (!StringUtils.isEmpty(server.getHostName())) {
                vmCreateResult.setHostName(server.getHostName());
            } else if (!StringUtils.isEmpty(vmCreate.getHostName())) {
                vmCreateResult.setHostName(vmCreate.getHostName());
            }
            if (server.getPorts() != null) {
                vmCreateResult.setPorts(server.getPorts());
            }
            if (server.getDisks() != null) {
                if (vmCreate.getProviderType().equalsIgnoreCase("HMC")) {
                    List<VmDisk> disks = vmCreate.getDisks();
                    for (Disk disk : server.getDisks()) {
                        for (VmDisk vmDisk2 : disks) {
                            if ("sysDisk".equalsIgnoreCase(disk.getType()) && vmDisk2.getType().equals(disk.getType())) {
                                LOGGER.info("mpar 系统盘设置属性");
                                vmDisk2.setUuid(disk.getUuid());
                                vmDisk2.setName(disk.getName());
                                vmdisks.add(vmDisk2);
                            } else if ("dataDisk".equalsIgnoreCase(disk.getType())
                                    && vmDisk2.getType().equals(disk.getType())
                                    && vmDisk2.getName().equals(disk.getName())) {
                                LOGGER.info("返回数据盘：" + disk.getName());
                                vmDisk2.setVolumes(disk.getVolumes());
                                vmdisks.add(vmDisk2);
                            }
                        }
                    }
                    vmCreateResult.setDisks(vmdisks);
                } else {
                    for (Disk disk : server.getDisks()) {
                        VmDisk vmdisk = new VmDisk();
                        vmdisk.setName(disk.getName());
                        vmdisk.setUuid(disk.getUuid());
                        vmdisk.setPath(disk.getPath());
                        vmdisks.add(vmdisk);
                    }
                    vmCreateResult.setDisks(vmdisks);
                }
            }

//            if (server.getNics() != null) {
//                for (ServerNic serverNic : server.getNics()) {
//
//                    VmNic vmNic = new VmNic();
//                    vmNic.setPrivateIp(serverNic.getPrivateIp());
//                    vmNic.setMac(serverNic.getMac());
//                    vmNic.setNetId(serverNic.getNetId());
//                    vmNic.setSubnetId(serverNic.getSubnetId());
//                    lstNics.add(vmNic);
//                }
//            }
            if (server.getNetworks() != null) {
                for (ServerNetwork serverNet : server.getNetworks()) {
                    Network network = new Network();
                    BeanUtils.copyProperties(network, serverNet);
                    networks.add(serverNet);
                }
            }
            vmCreateResult.setNics(server.getVmNics());

            LOGGER.info("[adaptor] vm :" + vmCreate.getName() + " has been created successfully");
            try {
                if ("linux".equalsIgnoreCase(vmCreate.getOsCategory()) && !CollectionUtils.isEmpty(vmCreate.getDisks())) {

                    LOGGER.info("执行格式化磁盘....");

                    List<VmDisk> disks = vmCreate.getDisks();
                    List<Disk> diskResult = server.getDisks();
                    if (!CollectionUtils.isEmpty(diskResult)) {
                        for (Disk disk : diskResult) {
                            if (!CollectionUtils.isEmpty(disks)) {
                                for (VmDisk vmDisk : disks) {
                                    if (disk.getName().equals(vmDisk.getName())) {
                                        vmDisk.setDeviceTagert(disk.getScsiUnit());
                                    }
                                }
                            }
                        }
                    }
                    DiskFormat diskFormat = new DiskFormat();
                    List<VmNic> nics = vmCreate.getNics();
                    String ip = "";
                    for (VmNic vmNic : nics) {
                        if (vmNic.isPrimary()) {
                            ip = vmNic.getPrivateIp();
                        }
                    }
                    if (!StringUtils.isEmpty(ip)) {
                        diskFormat.setTargetSerIp(ip);
                    } else {
                        diskFormat.setTargetSerIp(nics.get(0).getPrivateIp());
                    }
                    diskFormat.setTargetUser(vmCreate.getAdminName());
                    diskFormat.setTargetPwd(vmCreate.getAdminPass());
                    diskFormat.setDisks(disks);
                    diskFormat.setProviderType("HMC");
                    Fdisks formatDisk = vmHandler.formatDisk(diskFormat);
                    if (formatDisk.isSuccess()) {
                        LOGGER.info("格式化磁盘成功！");
                    } else {
                        LOGGER.error("格式化磁盘失败！");
                        String message = vmCreateResult.getErrMsg();
                        vmCreateResult.setErrMsg(message + "格式化磁盘失败！");
                    }
                }
            } catch (Exception e) {
                String message = vmCreateResult.getErrMsg();
                vmCreateResult.setErrMsg(message + " " + e.getMessage());
            }
            try {
                List<VmUserAdd> users = vmCreate.getUsers();
                if (!CollectionUtils.isEmpty(users)) {

                    for (VmUserAdd user : users) {
                        if (user != null
                                && ("linux".equalsIgnoreCase(vmCreate.getOsCategory()) || "aix"
                                .equalsIgnoreCase(vmCreate.getOsCategory()))) {
                            LOGGER.info("创建用户......");
                            user.setProviderType("HMC");
                            List<VmNic> nics = vmCreate.getNics();
                            String ip = "";
                            for (VmNic vmNic : nics) {
                                if (vmNic.isPrimary()) {
                                    ip = vmNic.getPrivateIp();
                                }
                            }
                            if (!StringUtils.isEmpty(ip)) {
                                user.setServerIP(ip);
                            } else {
                                user.setServerIP(nics.get(0).getPrivateIp());
                            }
                            user.setServerUser(vmCreate.getAdminName());
                            user.setServerPwd(vmCreate.getAdminPass());

                            boolean addUserResult = vmHandler.addUser(user);
                            if (addUserResult) {
                                LOGGER.info("创建用户" + user.getNonRootUser() + "成功！");
                            } else {
                                LOGGER.error("创建用户" + user.getNonRootUser() + "失败！");
                                String message = vmCreateResult.getErrMsg();
                                vmCreateResult.setErrMsg(message + "创建用户" + user.getNonRootUser() + "失败！");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                String message = vmCreateResult.getErrMsg();
                vmCreateResult.setSuccess(false);
                vmCreateResult.setErrMsg(message + " " + e.getMessage());
            }
        } catch (CommonAdapterException e) {

            vmCreateResult.setSuccess(false);
            vmCreateResult.setErrCode(e.getErrCode());
            vmCreateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            vmCreateResult.setSuccess(false);
            vmCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            vmCreateResult.setSuccess(false);
            vmCreateResult.setErrMsg(e.getMessage());
        }
        LOGGER.info("返回平台创建vm完成json：" + BaseUtil.toJson(vmCreateResult));
        return vmCreateResult;
    }

    public VmQueryResult handleMessage(VmQuery vmQuery) {

        LOGGER.info("receiving message for querying vms" + "----" + "virtual type : " + vmQuery.getVirtEnvType());

        LOGGER.info("msg id : " + vmQuery.getMsgId());

        List<Server> servers = null;

        VmQueryResult vmQueryResult = new VmQueryResult();
        vmQueryResult = (VmQueryResult) BaseUtil.setResult(vmQuery, VmQueryResult.class);
        vmQueryResult.setMsgId(vmQuery.getMsgId());

        try {
            servers = vmHandler.queryVms(vmQuery);
        } catch (CommonAdapterException e) {

            vmQueryResult.setSuccess(false);
            vmQueryResult.setErrCode(e.getErrCode());
            vmQueryResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            vmQueryResult.setSuccess(false);
            vmQueryResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmQueryResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        List<VmInfo> lstVms = new ArrayList<VmInfo>();

        for (Server server : servers) {
            VmInfo vmInfo = new VmInfo();
            vmInfo.setName(server.getName());
            vmInfo.setStatus(server.getStatus());
            lstVms.add(vmInfo);
        }

        vmQueryResult.setSuccess(true);
        vmQueryResult.setServers(lstVms);

        return vmQueryResult;

    }

    public DiskCreateResult handleMessage(DiskCreate diskCreate) {

        LOGGER.info("receiving message for creating disk" + "----" + "virtual type : " + diskCreate.getVirtEnvType()
                + "disk name:" + diskCreate.getName());

        LOGGER.info("msg id : " + diskCreate.getMsgId());

        DiskCreateResult diskCreateResult = new DiskCreateResult();
        diskCreateResult = (DiskCreateResult) BaseUtil.setResult(diskCreate, DiskCreateResult.class);
        diskCreateResult.setSid(diskCreate.getSid());
        diskCreateResult.setMsgId(diskCreate.getMsgId());
        diskCreateResult.setDescription(diskCreate.getDescription());
        diskCreateResult.setLabel(diskCreate.getLabel());
        diskCreateResult.setLocation(diskCreate.getLocation());
        diskCreateResult.setName(diskCreate.getName());
        diskCreateResult.setSize(diskCreate.getSize());
        diskCreateResult.setVmName(diskCreate.getVmName());

        try {
            Volume volume = storageHandler.createDisk(diskCreate);
            diskCreateResult.setSuccess(volume.isSuccess());
            diskCreateResult.setUuid(volume.getVolumeId());
        } catch (CommonAdapterException e) {
            diskCreateResult.setSuccess(false);
            diskCreateResult.setErrCode(e.getErrCode());
            diskCreateResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            diskCreateResult.setSuccess(false);
            diskCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            diskCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            diskCreateResult.setErrMsg(e.getMessage());
        }
        if (diskCreateResult.isSuccess()) {
            LOGGER.info("[adaptor] disk :" + diskCreate.getName() + " has been created successfully");
        } else {
            LOGGER.info("[adaptor] disk :" + diskCreate.getName() + " has been created failure");
        }
        return diskCreateResult;

    }

    public DiskDeleteResult handleMessage(DiskDelete diskDelete) {

        LOGGER.info("receiving message for deleting disk" + "----" + "virtual type : " + diskDelete.getVirtEnvType());

        LOGGER.info("msg id : " + diskDelete.getMsgId());

        DiskDeleteResult diskDeleteResult = new DiskDeleteResult();

        diskDeleteResult = (DiskDeleteResult) BaseUtil.setResult(diskDelete, DiskDeleteResult.class);

        diskDeleteResult.setMsgId(diskDelete.getMsgId());
        diskDeleteResult.setDataStore(diskDelete.getDataStore());
        diskDeleteResult.setId(diskDelete.getId());
        diskDeleteResult.setVmName(diskDelete.getVmName());
        diskDeleteResult.setDiskType(diskDelete.getDiskType());
        try {
            diskDeleteResult.setSuccess(storageHandler.deleteDisk(diskDelete));
        } catch (CommonAdapterException e) {
            diskDeleteResult.setSuccess(false);
            diskDeleteResult.setErrCode(e.getErrCode());
            diskDeleteResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            diskDeleteResult.setSuccess(false);
            diskDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            diskDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return diskDeleteResult;
    }

    public DiskExpandResult handleMessage(DiskExpand diskExpand) {

        LOGGER.info("receiving message for expanding disk" + "----" + "virtual type : " + diskExpand.getVirtEnvType());

        LOGGER.info("msg id : " + diskExpand.getMsgId());

        DiskExpandResult diskExpandResult = new DiskExpandResult();

        diskExpandResult = (DiskExpandResult) BaseUtil.setResult(diskExpand, DiskExpandResult.class);

        diskExpandResult.setName(diskExpand.getName());
        diskExpandResult.setVmName(diskExpand.getVmName());
        diskExpandResult.setSize(diskExpand.getSize());
        diskExpandResult.setStorage(diskExpand.getStorage());

        diskExpandResult.setMsgId(diskExpand.getMsgId());

        try {
            boolean result = storageHandler.expandDisk(diskExpand);
            diskExpandResult.setSuccess(result);
        } catch (CommonAdapterException e) {
            diskExpandResult.setSuccess(false);
            diskExpandResult.setErrCode(e.getErrCode());
            diskExpandResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            diskExpandResult.setSuccess(false);
            diskExpandResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            diskExpandResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return diskExpandResult;
    }

    public VmReconfigResult handleMessage(VmReconfig vmReconfig) {

        LOGGER.info("receiving message for reconfiging vm");
        LOGGER.info("msg id : " + vmReconfig.getMsgId());

        VmReconfigResult vmReconfigResult = new VmReconfigResult();
        try {
            BeanUtils.copyProperties(vmReconfigResult, vmReconfig);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            vmReconfigResult = (VmReconfigResult) BaseUtil.setResult(vmReconfig, VmReconfigResult.class);
            BeanUtils.copyProperties(vmReconfigResult, vmReconfig);
            vmReconfigResult.setMsgId(vmReconfig.getMsgId());
            vmReconfigResult.setCpu(vmReconfig.getCpu());
            vmReconfigResult.setMemory(vmReconfig.getMemory());
            vmReconfigResult.setVmName(vmReconfig.getVmName());
            vmReconfigResult.setHostName(vmReconfig.getHostName());

            CommonAdapterResult result = vmHandler.reconfigVm(vmReconfig);
            @SuppressWarnings("unchecked")
            List<VmDisk> disks = (List<VmDisk>) result.getMap().get("disks");
            List<VmDisk> disks2 = vmReconfigResult.getDisks();
            for (VmDisk vmDisk : disks2) {
                for (VmDisk disk : disks) {
                    if (!StringUtils.isEmpty(disk.getLvmParam()) && disk.getLvmParam().equals(vmDisk.getLvmParam())) {
                        vmDisk.setVolumes(disk.getVolumes());
                        break;
                    }
                }
            }
            vmReconfigResult.setSuccess(result.isSuccess());
        } catch (CommonAdapterException e) {

            vmReconfigResult.setSuccess(false);
            vmReconfigResult.setErrCode(e.getErrCode());
            vmReconfigResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            vmReconfigResult.setSuccess(false);
            vmReconfigResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmReconfigResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            vmReconfigResult.setSuccess(false);
            vmReconfigResult.setErrCode("500");
            vmReconfigResult.setErrMsg(e.getMessage());
        }

        return vmReconfigResult;

    }

    public VmRemoveResult handleMessage(VmRemove vmRemove) {

        LOGGER.info("receiving message for removing vm");
        LOGGER.info("msg id : " + vmRemove.getMsgId());

        VmRemoveResult vmRemoveResult = new VmRemoveResult();

        vmRemoveResult = (VmRemoveResult) BaseUtil.setResult(vmRemove, VmRemoveResult.class);
        vmRemoveResult.setSid(vmRemove.getSid());
        vmRemoveResult.setMsgId(vmRemove.getMsgId());
        vmRemoveResult.setVmName(vmRemove.getVmName());
        vmRemoveResult.setId(vmRemove.getId());
        vmRemoveResult.setDueToFailOfCreating(vmRemove.isDueToFailOfCreating());
        vmRemoveResult.setHostName(vmRemove.getHostName());
        try {
            boolean result = vmHandler.removeVm(vmRemove);
            vmRemoveResult.setSuccess(result);
            LOGGER.info("[adaptor] vm :" + vmRemove.getVmName() + " has been removed successfully");
        } catch (CommonAdapterException e) {

            vmRemoveResult.setSuccess(false);
            vmRemoveResult.setErrCode(e.getErrCode());
            vmRemoveResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            vmRemoveResult.setSuccess(false);
            vmRemoveResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmRemoveResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmRemoveResult;

    }

    public VmSnapshotCreateResult handleMessage(VmSnapshotCreate vmSnapshotCreate) {

        LOGGER.info("receiving message for creating vm snapshot" + "----" + "virtual type : "
                + vmSnapshotCreate.getVirtEnvType() + " vm name : " + vmSnapshotCreate.getVmName());

        LOGGER.info("msg id : " + vmSnapshotCreate.getMsgId());

        VmSnapshotCreateResult vmSnapshotCreateResult = new VmSnapshotCreateResult();
        vmSnapshotCreateResult = (VmSnapshotCreateResult) BaseUtil.setResult(vmSnapshotCreate,
                VmSnapshotCreateResult.class);

        vmSnapshotCreateResult.setMsgId(vmSnapshotCreate.getMsgId());
        vmSnapshotCreateResult.setVmName(vmSnapshotCreate.getVmName());
        vmSnapshotCreateResult.setSnapshotName(vmSnapshotCreate.getSnapshotName());
        vmSnapshotCreateResult.setSnapshotDesp(vmSnapshotCreate.getSnapshotDesp());
        try {
            Template template = vmHandler.snapshotVm(vmSnapshotCreate);
            BeanUtils.copyProperties(vmSnapshotCreateResult, template);
            vmSnapshotCreateResult.setSuccess(template.isSuccess());
        } catch (CommonAdapterException e) {

            vmSnapshotCreateResult.setSuccess(false);
            vmSnapshotCreateResult.setErrCode(e.getErrCode());
            vmSnapshotCreateResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            vmSnapshotCreateResult.setSuccess(false);
            vmSnapshotCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmSnapshotCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {

            e.printStackTrace();
            vmSnapshotCreateResult.setSuccess(false);
        }

        return vmSnapshotCreateResult;

    }

	/*
     * public VmSnapshotDeleteResult handleMessage(VmSnapshotDelete vmSnapshotDelete) {
	 * 
	 * LOGGER.info("receiving message for deleting vm snapshot" + "----" + "virtual type : " + vmSnapshotDelete.getVirtEnvType() + " vm name : " + vmSnapshotDelete.getVmName());
	 * 
	 * LOGGER.info("msg id : " + vmSnapshotDelete.getMsgId());
	 * 
	 * VmSnapshotDeleteResult vmSnapshotDeleteResult = new VmSnapshotDeleteResult();
	 * 
	 * vmSnapshotDeleteResult.setMsgId(vmSnapshotDelete.getMsgId()); vmSnapshotDeleteResult.setVmName(vmSnapshotDelete.getVmName()); vmSnapshotDeleteResult.setSnapshotName(vmSnapshotDelete.getSnapshotName());
	 * 
	 * try { vmHandler.deleteVmSnapshot(vmSnapshotDelete); vmSnapshotDeleteResult.setSuccess(true); } catch (CommonAdapterException e) {
	 * 
	 * vmSnapshotDeleteResult.setSuccess(false); vmSnapshotDeleteResult.setErrCode(e.getErrCode()); vmSnapshotDeleteResult.setErrMsg(e.getErrMsg()); } catch (AdapterUnavailableException e) { vmSnapshotDeleteResult.setSuccess(false); vmSnapshotDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE); vmSnapshotDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG); }
	 * 
	 * return vmSnapshotDeleteResult;
	 * 
	 * }
	 */

    public VmSnapshotRevertResult handleMessage(VmSnapshotRevert vmSnapshotRevert) {

        LOGGER.info("receiving message for reverting vm" + "----" + "virtual type : " + vmSnapshotRevert.getVirtEnvType()
                + " vm name : " + vmSnapshotRevert.getVmName());

        LOGGER.info("msg id : " + vmSnapshotRevert.getMsgId());

        VmSnapshotRevertResult vmSnapshotRevertResult = new VmSnapshotRevertResult();
        vmSnapshotRevertResult = (VmSnapshotRevertResult) BaseUtil.setResult(vmSnapshotRevert,
                VmSnapshotRevertResult.class);
        vmSnapshotRevertResult.setMsgId(vmSnapshotRevert.getMsgId());
        vmSnapshotRevertResult.setVmName(vmSnapshotRevert.getVmName());
        vmSnapshotRevertResult.setSnapshotName(vmSnapshotRevert.getSnapshotName());
        vmSnapshotRevertResult.setHostName(vmSnapshotRevert.getHostName());

        try {
            vmHandler.revertVm(vmSnapshotRevert);
            vmSnapshotRevertResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            vmSnapshotRevertResult.setSuccess(false);
            vmSnapshotRevertResult.setErrCode(e.getErrCode());
            vmSnapshotRevertResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            vmSnapshotRevertResult.setSuccess(false);
            vmSnapshotRevertResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmSnapshotRevertResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmSnapshotRevertResult;

    }

    public VmNicAddResult handleMessage(VmNicAdd vmNicAdd) {

        LOGGER.info("receiving message for adding vm nic" + "----" + "virtual type : " + vmNicAdd.getVirtEnvType()
                + " vm name : " + vmNicAdd.getVmName());

        LOGGER.info("msg id : " + vmNicAdd.getMsgId());

        VmNicAddResult vmNicAddResult = new VmNicAddResult();
        vmNicAddResult = (VmNicAddResult) BaseUtil.setResult(vmNicAdd, VmNicAddResult.class);
        vmNicAddResult.setMsgId(vmNicAdd.getMsgId());
        vmNicAddResult.setVmName(vmNicAdd.getVmName());
        vmNicAddResult.setNics(vmNicAdd.getNics());

        try {
            vmHandler.AddNic(vmNicAdd);
            vmNicAddResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            vmNicAddResult.setSuccess(false);
            vmNicAddResult.setErrCode(e.getErrCode());
            vmNicAddResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            vmNicAddResult.setSuccess(false);
            vmNicAddResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmNicAddResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmNicAddResult;

    }

    public VmNicDeleteResult handleMessage(VmNicDelete vmNicDelete) {

        LOGGER.info("receiving message for deletinng vm nic" + "----" + "virtual type : " + vmNicDelete.getVirtEnvType()
                + " vm name : " + vmNicDelete.getVmName());

        LOGGER.info("msg id : " + vmNicDelete.getMsgId());

        VmNicDeleteResult vmNicDeleteResult = new VmNicDeleteResult();
        vmNicDeleteResult = (VmNicDeleteResult) BaseUtil.setResult(vmNicDelete, VmNicDeleteResult.class);
        vmNicDeleteResult.setMsgId(vmNicDelete.getMsgId());
        vmNicDeleteResult.setVmName(vmNicDelete.getVmName());
        vmNicDeleteResult.setNics(vmNicDelete.getNics());

        try {
            vmHandler.DeleteNic(vmNicDelete);
            vmNicDeleteResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            vmNicDeleteResult.setSuccess(false);
            vmNicDeleteResult.setErrCode(e.getErrCode());
            vmNicDeleteResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            vmNicDeleteResult.setSuccess(false);
            vmNicDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmNicDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmNicDeleteResult;

    }

    public VmMigrateResult handleMessage(VmMigrate vmMigrate) {

        LOGGER.info("receiving message for migrating vm " + "----" + "virtual type : " + vmMigrate.getVirtEnvType()
                + " vm name : " + vmMigrate.getVmName());

        LOGGER.info("msg id : " + vmMigrate.getMsgId());

        VmMigrateResult vmMigrateResult = new VmMigrateResult();
        vmMigrateResult = (VmMigrateResult) BaseUtil.setResult(vmMigrate, VmMigrateResult.class);
        vmMigrateResult.setMsgId(vmMigrate.getMsgId());
        vmMigrateResult.setVmName(vmMigrate.getVmName());
        vmMigrateResult.setVmId(vmMigrate.getVmId());
        vmMigrateResult.setMigrateType(vmMigrate.getMigrateType());
        try {
            Server server = vmHandler.migrateVm(vmMigrate);
            if (server.isSuccess()) {
                vmMigrateResult.setTargetHostName(server.getHostName());
                vmMigrateResult.setSuccess(true);
            }
        } catch (CommonAdapterException e) {

            vmMigrateResult.setSuccess(false);
            vmMigrateResult.setErrCode(e.getErrCode());
            vmMigrateResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            vmMigrateResult.setSuccess(false);
            vmMigrateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmMigrateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmMigrateResult;

    }

    public VmRebuildResult handleMessage(VmRebuild vmRebuild) {
        LOGGER.info("receiving message for vmRebuilding vm" + "----" + "virtual type : " + vmRebuild.getVirtEnvType()
                + " Image name : " + vmRebuild.getImageId());

        LOGGER.info("msg id : " + vmRebuild.getMsgId());

        VmRebuildResult vmRebuildResult = new VmRebuildResult();
        vmRebuildResult = (VmRebuildResult) BaseUtil.setResult(vmRebuild, VmRebuildResult.class);
        vmRebuildResult.setMsgId(vmRebuild.getMsgId());
        vmRebuildResult.setId(vmRebuild.getId());

        try {
            Server server = vmHandler.rebuildVm(vmRebuild);
            BeanUtils.copyProperties(vmRebuildResult, server);
            vmRebuildResult.setSuccess(server.isSuccess());

        } catch (CommonAdapterException e) {


            vmRebuildResult.setErrCode(e.getErrCode());
            vmRebuildResult.setErrMsg(e.getErrMsg());
            vmRebuildResult.setSuccess(false);
        } catch (AdapterUnavailableException e) {

            vmRebuildResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmRebuildResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
            vmRebuildResult.setSuccess(false);

        } catch (Exception e) {

            e.printStackTrace();
            vmRebuildResult.setSuccess(false);
        }

        return vmRebuildResult;
    }

    public VmResizeResult handleMessage(VmResize vmResize) {
        LOGGER.info("receiving message for vmResizing vm" + "----" + "virtual type : " + vmResize.getVirtEnvType()
                + " vm Id : " + vmResize.getId());

        LOGGER.info("msg id : " + vmResize.getMsgId());
        VmResizeResult vmResizeResult = new VmResizeResult();
        vmResizeResult = (VmResizeResult) BaseUtil.setResult(vmResize, VmResizeResult.class);
        vmResizeResult.setMsgId(vmResize.getMsgId());

        try {
            vmHandler.resizeVm(vmResize);
            vmResizeResult.setSuccess(true);
        } catch (CommonAdapterException e) {
            vmResizeResult.setSuccess(false);
            vmResizeResult.setErrCode(e.getErrCode());
            vmResizeResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            vmResizeResult.setSuccess(false);
            vmResizeResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmResizeResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return vmResizeResult;
    }

    public VmRecoveryResult handleMessage(VmRecovery vmRecovery) {
        LOGGER.info("receiving message for vmRecoverying vm" + "----" + "virtual type : " + vmRecovery.getVirtEnvType()
                + " vm Id : " + vmRecovery.getId());

        LOGGER.info("msg id : " + vmRecovery.getMsgId());

        VmRecoveryResult vmRecoveryResult = new VmRecoveryResult();
        vmRecoveryResult = (VmRecoveryResult) BaseUtil.setResult(vmRecovery, VmRecoveryResult.class);
        vmRecoveryResult.setMsgId(vmRecovery.getMsgId());

        try {
            boolean result = vmHandler.recoveryVm(vmRecovery);
            if (result) {
                vmRecoveryResult.setSuccess(true);
            }
        } catch (CommonAdapterException e) {

            vmRecoveryResult.setErrCode(e.getErrCode());
            vmRecoveryResult.setErrMsg(e.getErrMsg());
            vmRecoveryResult.setSuccess(false);
        } catch (AdapterUnavailableException e) {


            vmRecoveryResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmRecoveryResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
            vmRecoveryResult.setSuccess(false);

        }
        return vmRecoveryResult;
    }

    public VmImageCreateResult handleMessage(VmImageCreate vmImageCreate) {

        LOGGER.info("receiving message for vmImageCreating vm" + "----" + "virtual type : "
                + vmImageCreate.getVirtEnvType() + " image name: " + vmImageCreate.getImageName());

        LOGGER.info("msg id : " + vmImageCreate.getMsgId());

        VmImageCreateResult vmImageCreateResult = new VmImageCreateResult();
        vmImageCreateResult = (VmImageCreateResult) BaseUtil.setResult(vmImageCreate, VmImageCreateResult.class);
        vmImageCreateResult.setMsgId(vmImageCreate.getMsgId());

        try {
            vmHandler.imageVm(vmImageCreate);
            vmImageCreateResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            vmImageCreateResult.setSuccess(false);
            vmImageCreateResult.setErrCode(e.getErrCode());
            vmImageCreateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            vmImageCreateResult.setSuccess(false);
            vmImageCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmImageCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmImageCreateResult;
    }

    public BlockBackupCreateResult handleMessage(BlockBackupCreate backupCreate) {

        LOGGER.info("receiving message for block backup creating" + "----" + "virtual type : "
                + backupCreate.getVirtEnvType() + "backup name: " + backupCreate.getName());

        LOGGER.info("msg id : " + backupCreate.getMsgId());

        BlockBackupCreateResult backupCreateResult = new BlockBackupCreateResult();
        backupCreateResult = (BlockBackupCreateResult) BaseUtil.setResult(backupCreate, BlockBackupCreateResult.class);
        backupCreateResult.setVolumeId(backupCreate.getVolumeId());
        backupCreateResult.setMsgId(backupCreate.getMsgId());

        try {
            backupCreateResult = storageHandler.createBlockBackup(backupCreate);
            backupCreateResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            backupCreateResult.setSuccess(false);
            backupCreateResult.setErrCode(e.getErrCode());
            backupCreateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            backupCreateResult.setSuccess(false);
            backupCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            backupCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        backupCreateResult.setVolumeId(backupCreate.getVolumeId());
        return backupCreateResult;
    }

    public BlockSnapshotRecovryResult handleMessage(BlockSnapshotRecovery blockSnapshotRecovery) {

        LOGGER.info("receiving message for block backup recovring" + "----" + "virtual type : "
                + blockSnapshotRecovery.getVirtEnvType() + "snapshot id: " + blockSnapshotRecovery.getSnapshotId());

        LOGGER.info("msg id : " + blockSnapshotRecovery.getMsgId());

        BlockSnapshotRecovryResult blockSnapshotRecovryResult = new BlockSnapshotRecovryResult();
        blockSnapshotRecovryResult = (BlockSnapshotRecovryResult) BaseUtil.setResult(blockSnapshotRecovery,
                BlockSnapshotRecovryResult.class);
        try {
            blockSnapshotRecovryResult = storageHandler.recoveryBlockSnapshot(blockSnapshotRecovery);

            blockSnapshotRecovryResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            blockSnapshotRecovryResult.setSuccess(false);
            blockSnapshotRecovryResult.setErrCode(e.getErrCode());
            blockSnapshotRecovryResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            blockSnapshotRecovryResult.setSuccess(false);
            blockSnapshotRecovryResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            blockSnapshotRecovryResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        blockSnapshotRecovryResult.setVolumeId(blockSnapshotRecovery.getVolumeId());
        blockSnapshotRecovryResult.setSnapshotId(blockSnapshotRecovery.getSnapshotId());
        blockSnapshotRecovryResult.setMsgId(blockSnapshotRecovery.getMsgId());
        return blockSnapshotRecovryResult;
    }

    public BlockBackupRecoveryResult handleMessage(BlockBackupRecovery blockBackupRecovery) {

        LOGGER.info("receiving message for block backup recovring" + "----" + "virtual type : "
                + blockBackupRecovery.getVirtEnvType());

        LOGGER.info("msg id : " + blockBackupRecovery.getMsgId());

        BlockBackupRecoveryResult backupRecoveryResult = new BlockBackupRecoveryResult();
        backupRecoveryResult = (BlockBackupRecoveryResult) BaseUtil.setResult(blockBackupRecovery,
                BlockBackupRecoveryResult.class);
        try {
            backupRecoveryResult = storageHandler.recoveryBlockBachup(blockBackupRecovery);
            backupRecoveryResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            backupRecoveryResult.setSuccess(false);
            backupRecoveryResult.setErrCode(e.getErrCode());
            backupRecoveryResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            backupRecoveryResult.setSuccess(false);
            backupRecoveryResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            backupRecoveryResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        backupRecoveryResult.setVolumeId(blockBackupRecovery.getVolumeId());
        backupRecoveryResult.setBackupId(blockBackupRecovery.getBackupId());
        backupRecoveryResult.setMsgId(blockBackupRecovery.getMsgId());
        return backupRecoveryResult;
    }

    public BlockCloneResult handleMessage(BlockClone blockClone) {

        LOGGER.info("receiving message for block cloning" + "----" + "virtual type : " + blockClone.getVirtEnvType());

        LOGGER.info("msg id : " + blockClone.getMsgId());

        BlockCloneResult blockCloneResult = new BlockCloneResult();
        blockCloneResult = (BlockCloneResult) BaseUtil.setResult(blockClone, BlockCloneResult.class);
        blockCloneResult.setMsgId(blockClone.getMsgId());

        try {
            blockCloneResult = storageHandler.cloneBlock(blockClone);
            blockCloneResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            blockCloneResult.setSuccess(false);
            blockCloneResult.setErrCode(e.getErrCode());
            blockCloneResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            blockCloneResult.setSuccess(false);
            blockCloneResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            blockCloneResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return blockCloneResult;
    }

	/*
     * public SecurityGroupQueryResult handelMessage(SecurityGroupQuery securityGroupQuery) {
	 * 
	 * LOGGER.info("receiving message for adding security group" + "----" + "virtual type : " + securityGroupQuery.getVirtEnvType());
	 * 
	 * LOGGER.info("msg id : " + securityGroupQuery.getMsgId());
	 * 
	 * SecurityGroupQueryResult result = new SecurityGroupQueryResult();
	 * 
	 * result.setMsgId(securityGroupQuery.getMsgId()); try { List<SecurityGroup> secruitygGroups = vmHandler.querySecurityGroup(securityGroupQuery); result.setSecurityGroups(secruitygGroups); result.setSuccess(true); } catch (CommonAdapterException e) {  result.setSuccess(false); result.setErrCode(e.getErrCode()); result.setErrMsg(e.getErrMsg());
	 * 
	 * } catch (AdapterUnavailableException e) { 
	 * 
	 * result.setSuccess(false); result.setErrCode(Constants.AdapterUnvailableException.CODE); result.setErrMsg(Constants.AdapterUnvailableException.MSG); } return result; }
	 */

    public NetCreateResult handleMessage(NetCreate netCreate) {

        LOGGER.info("receiving message for creating network" + "----" + "virtual type : " + netCreate.getVirtEnvType()
                + " network name :" + netCreate.getName());

        LOGGER.info("msg id : " + netCreate.getMsgId());
        NetCreateResult netCreateResult = new NetCreateResult();
        netCreateResult = (NetCreateResult) BaseUtil.setResult(netCreate, NetCreateResult.class);
        netCreateResult.setMsgId(netCreate.getMsgId());
        netCreateResult.setResId(netCreate.getResId());
        try {
            com.h3c.idcloud.core.adapter.facade.provision.model.network.Network network =
                    com.h3c.idcloud.core.adapter.facade.provision.model.network.Network.class.cast(
                            netHandler.createNet(netCreate));
            if (network != null) {
                Subnet subnet = Subnet.builder().build();
                BeanUtils.copyProperties(subnet, network.getSubnet());
                netCreateResult.setSubnet(subnet);

                if (network.getRouter() != null) {
                    netCreateResult.setRouter(network.getRouter());
                }

                if (network.getPorts() != null) {
                    netCreateResult.setPorts(Sets.newHashSet(network.getPorts()));
                }

                BeanUtils.copyProperty(netCreateResult, "id", network.getId());
                BeanUtils.copyProperty(netCreateResult, "name", network.getName());
                netCreateResult.setSuccess(network.isSuccess());
            }

        } catch (CommonAdapterException e) {

            netCreateResult.setSuccess(false);
            netCreateResult.setErrCode(e.getErrCode());
            netCreateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            netCreateResult.setSuccess(false);
            netCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            netCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netCreateResult;
    }

    public NetDeleteResult handleMessage(NetDelete netDelete) {

        LOGGER.info("receiving message for deleting network" + "----" + "virtual type : " + netDelete.getVirtEnvType()
                + " network id :" + netDelete.getNetId());

        LOGGER.info("msg id : " + netDelete.getMsgId());
        NetDeleteResult deleteResult = new NetDeleteResult();
        deleteResult = (NetDeleteResult) BaseUtil.setResult(netDelete, NetDeleteResult.class);
        deleteResult.setMsgId(netDelete.getMsgId());
        deleteResult.setNetId(netDelete.getNetId());
        deleteResult.setMgtObjSid(netDelete.getMgtObjSid());
        deleteResult.setResNetworkSId(netDelete.getResNetworkSId());
        try {
            CommonAdapterResult commonAdapterResult = netHandler.deleteNet(netDelete);
            deleteResult.setSuccess(commonAdapterResult.isSuccess());
            deleteResult.getOptions().put("result", commonAdapterResult.getMap());
        } catch (CommonAdapterException e) {

            deleteResult.setSuccess(false);
            deleteResult.setErrCode(e.getErrCode());
            deleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            deleteResult.setSuccess(false);
            deleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            deleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return deleteResult;
    }

    public TemplateScanResult handleMessage(TemplateScan templateScan) {

        LOGGER.info("receiving message for scanning tempalte" + "----" + "virtual type : " + templateScan.getVirtEnvType());

        LOGGER.info("msg id : " + templateScan.getMsgId());
        TemplateScanResult templateScanResult = new TemplateScanResult();
        templateScanResult = (TemplateScanResult) BaseUtil.setResult(templateScan, TemplateScanResult.class);
        templateScanResult.setMsgId(templateScan.getMsgId());
        templateScanResult.setResVeSid(templateScan.getVirtEnvUuid());
        try {
            List<Template> templates = scanHandler.scanTemplate(templateScan);
            List<TemplateVO> templateVOs = new ArrayList<TemplateVO>();

            for (Template template : templates) {
                TemplateVO templateVO = new TemplateVO();
                BeanUtils.copyProperties(templateVO, template);
                templateVOs.add(templateVO);
            }
            templateScanResult.setTemplates(templateVOs);
            templateScanResult.setSuccess(true);
        } catch (CommonAdapterException e) {
            templateScanResult.setSuccess(false);
            templateScanResult.setErrCode(e.getErrCode());
            templateScanResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            templateScanResult.setSuccess(false);
            templateScanResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            templateScanResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
            templateScanResult.setSuccess(false);
        }
        return templateScanResult;
    }

    public VmSnapshotDeleteResult handleMessage(VmSnapshotDelete vmSnapshotDelete) {

        LOGGER.info("receiving message for deleting vmSnapshot" + "----" + "virtual type : "
                + vmSnapshotDelete.getVirtEnvType());

        LOGGER.info("msg id : " + vmSnapshotDelete.getMsgId());
        VmSnapshotDeleteResult vmSnapshotDeleteResult = new VmSnapshotDeleteResult();
        vmSnapshotDeleteResult = (VmSnapshotDeleteResult) BaseUtil.setResult(vmSnapshotDelete,
                VmSnapshotDeleteResult.class);
        vmSnapshotDeleteResult.setMsgId(vmSnapshotDelete.getMsgId());
        vmSnapshotDeleteResult.setServerId(vmSnapshotDelete.getServerId());
        vmSnapshotDeleteResult.setImageId(vmSnapshotDelete.getImageId());
        vmSnapshotDeleteResult.setResId(vmSnapshotDelete.getResId());
        try {
            boolean result = vmHandler.deleteVmSnapshot(vmSnapshotDelete);
            vmSnapshotDeleteResult.setSuccess(result);
        } catch (CommonAdapterException e) {

            vmSnapshotDeleteResult.setSuccess(false);
            vmSnapshotDeleteResult.setErrCode(e.getErrCode());
            vmSnapshotDeleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            vmSnapshotDeleteResult.setSuccess(false);
            vmSnapshotDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmSnapshotDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return vmSnapshotDeleteResult;
    }

    public AllInOneScanResult handleMessage(AllInOneScan allInOneScan) {

        LOGGER.info("receiving message for scanning all in one" + "----" + "virtual type : "
                + allInOneScan.getVirtEnvType());

        LOGGER.info("msg id : " + allInOneScan.getMsgId());

        AllInOneScanResult allInOneScanResult = new AllInOneScanResult();

        allInOneScanResult = (AllInOneScanResult) BaseUtil.setResult(allInOneScan, AllInOneScanResult.class);
        allInOneScanResult.setMsgId(allInOneScan.getMsgId());

        try {
            List<Host> hosts = scanHandler.scanAllInOne(allInOneScan);
            allInOneScanResult.setSuccess(true);
            List<HostVO> hostVOs = new ArrayList<HostVO>();

            for (Host host : hosts) {

                HostVO hostVO = new HostVO();
                try {
                    BeanUtils.copyProperty(hostVO, "hostName", host.getHostName());
                    BeanUtils.copyProperty(hostVO, "host", host.getHost());

                    BeanUtils.copyProperty(hostVO, "cpuCores", host.getCpuCores());
                    BeanUtils.copyProperty(hostVO, "cpuGhz", host.getCpuGhz());
                    BeanUtils.copyProperty(hostVO, "cpuNumber", host.getCpuNumber());
                    BeanUtils.copyProperty(hostVO, "cpuType", host.getCpuType());
                    BeanUtils.copyProperty(hostVO, "cpuUsage", host.getCpuUsage());
                    BeanUtils.copyProperty(hostVO, "dataStorages", host.getDataStorages());
                    BeanUtils.copyProperty(hostVO, "hostIp", host.getHostIp());
                    BeanUtils.copyProperty(hostVO, "hostOsType", host.getHostOsType());
                    BeanUtils.copyProperty(hostVO, "memorySize", host.getMemorySize());
                    BeanUtils.copyProperty(hostVO, "menUsage", host.getMenUsage());
                    BeanUtils.copyProperty(hostVO, "nicNumber", host.getNicNumber());
                    BeanUtils.copyProperty(hostVO, "status", host.getStatus());
                    BeanUtils.copyProperty(hostVO, "uuid", host.getUuid());
                    // BeanUtils.copyProperty(hostVO, "vms", host.getVms());
                    List<VmVO> vmVOs = new ArrayList<VmVO>();

                    List<Vm> vms = host.getVms();
                    for (Vm vm : vms) {
                        VmVO vmVO = new VmVO();
                        BeanUtils.copyProperties(vmVO, vm);
                        vmVOs.add(vmVO);
                    }
                    hostVO.setVms(vmVOs);
                    com.h3c.idcloud.core.adapter.facade.provision.model.scan.Network network = host.getNetWorks();
                    NetworkVO networkVO = new NetworkVO();
                    if (network != null) {
                        BeanUtils.copyProperties(networkVO, network);
                    }

                    hostVO.setNetWorks(networkVO);
                    hostVOs.add(hostVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            allInOneScanResult.setHosts(hostVOs);

        } catch (CommonAdapterException e) {

            allInOneScanResult.setSuccess(false);
            allInOneScanResult.setErrCode(e.getErrCode());
            allInOneScanResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            allInOneScanResult.setSuccess(false);
            allInOneScanResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            allInOneScanResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allInOneScanResult;
    }

    public SoftwareDeployResult handleMessage(SoftwareDeploy softwareDeploy) {
        softwareDeploy.setProviderType("HMC");
        LOGGER.info("receiving message for install software" + "----" + "virtual type : "
                + softwareDeploy.getVirtEnvType());

        LOGGER.info("msg id : " + softwareDeploy.getMsgId());

        SoftwareDeployResult result = new SoftwareDeployResult();
        result = (SoftwareDeployResult) BaseUtil.setResult(softwareDeploy, SoftwareDeployResult.class);
        result.setVmId(softwareDeploy.getVmId());
        try {
            List<Software> installSoftware = vmHandler.installSoftware(softwareDeploy);
            for (Software software : installSoftware) {
                for (int i = 0; i < softwareDeploy.getSoftwares().size(); i++) {
                    if (softwareDeploy.getSoftwares().get(i).getType().equals(software.getType())) {
                        softwareDeploy.getSoftwares().get(i).setSuccess(software.isSuccess());
                    }
                }
            }
            result.setSoftwares(softwareDeploy.getSoftwares());
            boolean flag = true;
            for (Software software : installSoftware) {
                if (!software.isSuccess()) {
                    flag = false;
                    break;
                }
            }
            result.setSuccess(flag);
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            result.setErrCode("500");
            result.setErrMsg(e.getMessage());
            e.printStackTrace();
        }
        if (result.isSuccess()) {
            LOGGER.info("安装软件成功！");
        } else {
            LOGGER.error("安装软件失败！");
        }
        return result;
    }

    public DataStoreCreateResult handleMessage(DataStoreCreate dataStoreCreate) {
        LOGGER.info("receiving message for datastore creating" + "----" + "virtual type : " + dataStoreCreate.getVirtEnvType());

        LOGGER.info("msg id : " + dataStoreCreate.getMsgId());

        DataStoreCreateResult result = new DataStoreCreateResult();
        result.setDatastoreName(dataStoreCreate.getDatastoreName());
        result.setSid(dataStoreCreate.getSid());
        try {
            DataStoreVo dsVo = storageHandler.createDataStore(dataStoreCreate);
            LOGGER.info("创建存储 " + dataStoreCreate.getDatastoreName() + " 完成！");
            com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreVo dsvo = new com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreVo();
            BeanUtils.copyProperties(dsvo, dsVo);
            result.setDataStoreVo(dsvo);
            result.setSuccess(true);
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
            result.setSuccess(false);
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
            result.setSuccess(false);

        } catch (Exception e) {
            result.setErrCode("500");
            result.setErrMsg(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

    public DataStoreDeleteResult handleMessage(DataStoreDelete delete) {
        LOGGER.info("receiving message for datastore creating" + "----" + "virtual type : " + delete.getVirtEnvType());

        LOGGER.info("msg id : " + delete.getMsgId());

        DataStoreDeleteResult result = new DataStoreDeleteResult();
        result.setDatastoreName(delete.getDatastoreName());
        result.setSid(delete.getSid());
        try {
            Boolean delResult = storageHandler.deleteDataStore(delete);
            LOGGER.info("删除存储 " + delete.getDatastoreName() + " 完成！");
            result.setSuccess(delResult);
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
            result.setSuccess(false);
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
            result.setSuccess(false);

        } catch (Exception e) {
            result.setErrCode("500");
            result.setErrMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public DataStoreExtendResult handleMessage(DataStoreExtend extend) {

        LOGGER.info("receiving message for datastore creating" + "----" + "virtual type : " + extend.getVirtEnvType());

        LOGGER.info("msg id : " + extend.getMsgId());

        DataStoreExtendResult result = new DataStoreExtendResult();
        result.setDatastoreName(extend.getDatastoreName());
        result.setNewSize(extend.getNewSize());
        result.setSid(extend.getSid());
        try {
            DataStoreVo dsVo = storageHandler.extendDataStore(extend);
            LOGGER.info("扩容存储 " + extend.getDatastoreName() + " 完成！");
            com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreVo dsvo = new com.h3c.idcloud.core.adapter.pojo.datastore.DataStoreVo();
            BeanUtils.copyProperties(dsvo, dsVo);
            result.setDataStoreVo(dsvo);
            result.setSuccess(true);
        } catch (CommonAdapterException e) {
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
            result.setSuccess(false);
        } catch (AdapterUnavailableException e) {
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
            result.setSuccess(false);

        } catch (Exception e) {
            result.setErrCode("500");
            result.setErrMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
