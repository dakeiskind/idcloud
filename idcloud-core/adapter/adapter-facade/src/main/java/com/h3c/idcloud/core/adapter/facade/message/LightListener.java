package com.h3c.idcloud.core.adapter.facade.message;

import com.h3c.idcloud.core.adapter.facade.AdminHandler;
import com.h3c.idcloud.core.adapter.facade.NetHandler;
import com.h3c.idcloud.core.adapter.facade.ScanHandler;
import com.h3c.idcloud.core.adapter.facade.StorageHandler;
import com.h3c.idcloud.core.adapter.facade.VmHandler;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.keypair.KeyPairResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Block;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.block.BlockInfoGet;
import com.h3c.idcloud.core.adapter.pojo.block.BlockList;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotCreate;
import com.h3c.idcloud.core.adapter.pojo.block.BlockSnapshotDelete;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockInfoGetResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockListResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotCreateResult;
import com.h3c.idcloud.core.adapter.pojo.block.result.BlockSnapshotDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskAttach;
import com.h3c.idcloud.core.adapter.pojo.disk.DiskDetach;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskAttachResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.DiskDetachResult;
import com.h3c.idcloud.core.adapter.pojo.image.ImageDelete;
import com.h3c.idcloud.core.adapter.pojo.image.ImageUpdate;
import com.h3c.idcloud.core.adapter.pojo.image.result.ImageDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.image.result.ImageUpdateResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairCreate;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairDelete;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairListGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairCreateResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairGetResult;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.KeypairListGetResult;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpCreate;
import com.h3c.idcloud.core.adapter.pojo.network.FloatingIpDelete;
import com.h3c.idcloud.core.adapter.pojo.network.Router;
import com.h3c.idcloud.core.adapter.pojo.network.RouterCreate;
import com.h3c.idcloud.core.adapter.pojo.network.RouterDelete;
import com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupQuery;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.RouterResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.SecurityGroupQueryResult;
import com.h3c.idcloud.core.adapter.pojo.scan.TemplateScan;
import com.h3c.idcloud.core.adapter.pojo.scan.result.TemplateScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.TemplateVO;
import com.h3c.idcloud.core.adapter.pojo.vm.VmBlockQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmInfoGet;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmBlockQueryResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmGetInfoResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmOperateResult;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LightListener {

    private static Logger log = LoggerFactory.getLogger(LightListener.class);
    @Autowired
    private VmHandler vmHandler;
    @Autowired
    private StorageHandler storageHandler;
    @Autowired
    private AdminHandler adminHandler;
    @Autowired
    private NetHandler netHandler;
    @Autowired
    private ScanHandler scanHandler;

    public VmOperateResult handleMessage(VmOperate vmOperate) {

        log.info("receiving message for operating vm" + "----" + "virtual type : " + vmOperate.getVirtEnvType()
                + " vm name : " + vmOperate.getVmName() + " action : " + vmOperate.getAction());

        log.info("msg id : " + vmOperate.getMsgId());

        VmOperateResult vmOperateResult = new VmOperateResult();
        vmOperateResult = (VmOperateResult) BaseUtil.setResult(vmOperate, VmOperateResult.class);
        vmOperateResult.setMsgId(vmOperate.getMsgId());
        //vmOperateResult.setVmName(vmOperate.getVmName());
        vmOperateResult.setAction(vmOperate.getAction());
        vmOperateResult.setId(vmOperate.getId());
        vmOperateResult.setUuid(vmOperate.getUuid());

        try {
            vmHandler.operateVm(vmOperate);
            vmOperateResult.setSuccess(true);
            log.info("[adaptor] vm :" + vmOperate.getVmName() + " has been " + vmOperate.getAction() + " successfully");
        } catch (CommonAdapterException e) {

            vmOperateResult.setSuccess(false);
            vmOperateResult.setErrCode(e.getErrCode());
            vmOperateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            vmOperateResult.setSuccess(false);
            vmOperateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmOperateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return vmOperateResult;
    }

    public DiskAttachResult handleMessage(DiskAttach diskAttach) {

        log.info("receiving message for attaching disk" + "----" + "virtual type : " + diskAttach.getVirtEnvType());

        log.info("msg id : " + diskAttach.getMsgId());

        DiskAttachResult diskAttachResult = new DiskAttachResult();
        diskAttachResult = (DiskAttachResult) BaseUtil.setResult(diskAttach, DiskAttachResult.class);
        diskAttachResult.setMsgId(diskAttach.getMsgId());
        diskAttachResult.setServerId(diskAttach.getServerId());
        diskAttachResult.setVolumeId(diskAttach.getVolumeId());
        diskAttachResult.setDevice(diskAttach.getDevice());

        try {
            storageHandler.attachDisk(diskAttach);
            diskAttachResult.setSuccess(true);
        } catch (CommonAdapterException e) {
            diskAttachResult.setSuccess(false);
            diskAttachResult.setErrCode(e.getErrCode());
            diskAttachResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            diskAttachResult.setSuccess(false);
            diskAttachResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            diskAttachResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return diskAttachResult;
    }

    public DiskDetachResult handleMessage(DiskDetach diskDetach) {

        log.info("receiving message for detaching disk" + "----" + "virtual type : " + diskDetach.getVirtEnvType());

        log.info("msg id : " + diskDetach.getMsgId());

        DiskDetachResult diskDetachResult = new DiskDetachResult();
        diskDetachResult = (DiskDetachResult) BaseUtil.setResult(diskDetach, DiskDetachResult.class);
        diskDetachResult.setMsgId(diskDetach.getMsgId());
        diskDetachResult.setServerId(diskDetach.getServerId());
        diskDetachResult.setVolumeId(diskDetach.getVolumeId());

        try {
            boolean result = storageHandler.detachDisk(diskDetach);
            diskDetachResult.setSuccess(result);
        } catch (CommonAdapterException e) {

            diskDetachResult.setSuccess(false);
            diskDetachResult.setErrCode(e.getErrCode());
            diskDetachResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            diskDetachResult.setSuccess(false);
            diskDetachResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            diskDetachResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }

        return diskDetachResult;

    }

    public BlockSnapshotCreateResult handleMessage(BlockSnapshotCreate blockSnapshotCreate) {

        log.info("receiving message for block backup creating" + "----" + "virtual type : " + blockSnapshotCreate.getVirtEnvType()
                + "snapshot name: " + blockSnapshotCreate.getName());

        log.info("msg id : " + blockSnapshotCreate.getMsgId());

        BlockSnapshotCreateResult blockSnapshotCreateResult = new BlockSnapshotCreateResult();
        blockSnapshotCreateResult = (BlockSnapshotCreateResult) BaseUtil.setResult(blockSnapshotCreate, BlockSnapshotCreateResult.class);
        blockSnapshotCreateResult.setMsgId(blockSnapshotCreate.getMsgId());
        try {
            blockSnapshotCreateResult = storageHandler.createBlockSnapshot(blockSnapshotCreate);
        } catch (CommonAdapterException e) {

            blockSnapshotCreateResult.setSuccess(false);
            blockSnapshotCreateResult.setErrCode(e.getErrCode());
            blockSnapshotCreateResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            blockSnapshotCreateResult.setSuccess(false);
            blockSnapshotCreateResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            blockSnapshotCreateResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        blockSnapshotCreateResult.setVolumeId(blockSnapshotCreate.getVolumeId());
        return blockSnapshotCreateResult;
    }

    public BlockSnapshotDeleteResult handleMessage(BlockSnapshotDelete blcokSnapShotDelete) {

        log.info("receiving message for block backup creating" + "----" + "virtual type : " + blcokSnapShotDelete.getVirtEnvType()
                + "snapshot id: " + blcokSnapShotDelete.getSnapshotId());

        log.info("msg id : " + blcokSnapShotDelete.getMsgId());

        BlockSnapshotDeleteResult blockSnapshotDeleteResult = new BlockSnapshotDeleteResult();
        blockSnapshotDeleteResult = (BlockSnapshotDeleteResult) BaseUtil.setResult(blcokSnapShotDelete, BlockSnapshotDeleteResult.class);
        blockSnapshotDeleteResult.setMsgId(blcokSnapShotDelete.getMsgId());
        blockSnapshotDeleteResult.setVolumeId(blcokSnapShotDelete.getVolumeId());
        blockSnapshotDeleteResult.setSnapshotId(blcokSnapShotDelete.getSnapshotId());
        try {
            boolean result = storageHandler.deleteBlockSnapshot(blcokSnapShotDelete);
            blockSnapshotDeleteResult.setSuccess(result);
        } catch (CommonAdapterException e) {

            blockSnapshotDeleteResult.setSuccess(false);
            blockSnapshotDeleteResult.setErrCode(e.getErrCode());
            blockSnapshotDeleteResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            blockSnapshotDeleteResult.setSuccess(false);
            blockSnapshotDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            blockSnapshotDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return blockSnapshotDeleteResult;
    }


    public VmBlockQueryResult handleMessage(VmBlockQuery vmBlockQuery) {

        log.info("receiving message for quering vmBlocks " + "----"
                + "virtual type : " + vmBlockQuery.getVirtEnvType());

        log.info("msg id : " + vmBlockQuery.getMsgId());

        VmBlockQueryResult vmBlockQueryResult = new VmBlockQueryResult();
        vmBlockQueryResult = (VmBlockQueryResult) BaseUtil.setResult(vmBlockQuery, VmBlockQueryResult.class);
        vmBlockQueryResult.setMsgId(vmBlockQuery.getMsgId());

        try {
            List<Block> blocks = new ArrayList<Block>();
            blocks = vmHandler.queryBlocks(vmBlockQuery);

            List<com.h3c.idcloud.core.adapter.pojo.vm.result.Block> blocksResult = new ArrayList<com.h3c.idcloud.core.adapter.pojo.vm.result.Block>();

            for (Block block : blocks) {
                com.h3c.idcloud.core.adapter.pojo.vm.result.Block blockResult = new com.h3c.idcloud.core.adapter.pojo.vm.result.Block();
                BeanUtils.copyProperty(blockResult, "id", block.getBlockId());
                BeanUtils.copyProperty(blockResult, "serverId", block.getId());
                BeanUtils
                        .copyProperty(blockResult, "device", block.getDevice());
                blocksResult.add(blockResult);
            }
            vmBlockQueryResult.setBlocks(blocksResult);
            vmBlockQueryResult.setSuccess(true);
        } catch (CommonAdapterException e) {


            vmBlockQueryResult.setSuccess(false);
            vmBlockQueryResult.setErrCode(e.getErrCode());
            vmBlockQueryResult.setErrMsg(e.getMessage());
        } catch (AdapterUnavailableException e) {


            vmBlockQueryResult.setSuccess(false);
            vmBlockQueryResult
                    .setErrCode(Constants.AdapterUnvailableException.CODE);
            vmBlockQueryResult
                    .setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vmBlockQueryResult;
    }

    public VmGetInfoResult handleMessage(VmInfoGet vmInfoGet) {

        log.info("receiving message for getting vmInfo" + "----" + "virtual type : " + vmInfoGet.getVirtEnvType());

        log.info("msg id : " + vmInfoGet.getMsgId());

        VmGetInfoResult vmGetInfoResult = new VmGetInfoResult();
        vmGetInfoResult = (VmGetInfoResult) BaseUtil.setResult(vmInfoGet, VmGetInfoResult.class);
        vmGetInfoResult.setMsgId(vmInfoGet.getMsgId());

        try {
            Server serverInfo = vmHandler.getVmInfo(vmInfoGet);

            try {
                BeanUtils.copyProperties(vmGetInfoResult, serverInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (CommonAdapterException e) {


            vmGetInfoResult.setErrCode(e.getErrCode());
            vmGetInfoResult.setErrMsg(e.getErrMsg());
            vmGetInfoResult.setSuccess(false);
        } catch (AdapterUnavailableException e) {


            vmGetInfoResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            vmGetInfoResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
            vmGetInfoResult.setSuccess(false);

        }
        return vmGetInfoResult;
    }

    public BlockInfoGetResult handleMessage(BlockInfoGet blockInfoGet) {

        log.info("receiving message for getting block info" + "----" + "virtual type : " + blockInfoGet.getVirtEnvType());

        log.info("msg id : " + blockInfoGet.getMsgId());

        BlockInfoGetResult blockInfoGetResult = new BlockInfoGetResult();
        blockInfoGetResult = (BlockInfoGetResult) BaseUtil.setResult(blockInfoGet, BlockInfoGetResult.class);
        blockInfoGetResult.setMsgId(blockInfoGet.getMsgId());

        try {
            blockInfoGetResult = storageHandler.getBlockInfo(blockInfoGet);
            blockInfoGetResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            blockInfoGetResult.setSuccess(false);
            blockInfoGetResult.setErrCode(e.getErrCode());
            blockInfoGetResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            blockInfoGetResult.setSuccess(false);
            blockInfoGetResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            blockInfoGetResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return blockInfoGetResult;
    }

    public BlockListResult handleMessage(BlockList blockList) {

        log.info("receiving message for getting blocklist" + "----" + "virtual type : " + blockList.getVirtEnvType());

        log.info("msg id : " + blockList.getMsgId());

        BlockListResult blockListResult = new BlockListResult();
        blockListResult = (BlockListResult) BaseUtil.setResult(blockList, BlockListResult.class);
        blockListResult.setMsgId(blockList.getMsgId());

        try {
            blockListResult = storageHandler.getBlockList(blockList);
            blockListResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            blockListResult.setSuccess(false);
            blockListResult.setErrCode(e.getErrCode());
            blockListResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            blockListResult.setSuccess(false);
            blockListResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            blockListResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return blockListResult;
    }

    @SuppressWarnings("unchecked")
    public SecurityGroupQueryResult handelMessage(SecurityGroupQuery securityGroupQuery) {

        log.info("receiving message for adding security group" + "----" + "virtual type : " + securityGroupQuery.getVirtEnvType());

        log.info("msg id : " + securityGroupQuery.getMsgId());

        SecurityGroupQueryResult securityGroupQueryResult = new SecurityGroupQueryResult();
        securityGroupQueryResult = (SecurityGroupQueryResult) BaseUtil.setResult(securityGroupQuery, SecurityGroupQueryResult.class);
        securityGroupQueryResult.setMsgId(securityGroupQuery.getMsgId());

        try {
            List<SecurityGroup> securityGroups = vmHandler.querySecurityGroup(securityGroupQuery);
            List<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup> sList =
                    (List<com.h3c.idcloud.core.adapter.pojo.network.SecurityGroup>) BaseUtil.castObject(securityGroups, com.h3c.idcloud.core.adapter.pojo.network.result.SecurityGroup.class);
            securityGroupQueryResult.setSecurityGroups(sList);
            securityGroupQueryResult.setSuccess(true);
        } catch (CommonAdapterException e) {

            securityGroupQueryResult.setSuccess(false);
            securityGroupQueryResult.setErrCode(e.getErrCode());
            securityGroupQueryResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {


            securityGroupQueryResult.setSuccess(false);
            securityGroupQueryResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            securityGroupQueryResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return securityGroupQueryResult;
    }

    public FloatingIpCreateResult handleMessage(FloatingIpCreate floatingIpCreate) {

        log.info("receiving message for creating floatingIp" + "----" + "virtual type : " + floatingIpCreate.getVirtEnvType());

        log.info("msg id : " + floatingIpCreate.getMsgId());

        FloatingIpCreateResult createResult = new FloatingIpCreateResult();
        createResult = (FloatingIpCreateResult) BaseUtil.setResult(floatingIpCreate, FloatingIpCreateResult.class);
        createResult.setMsgId(floatingIpCreate.getMsgId());
        createResult.setNetworkId(floatingIpCreate.getFloatingNetworkId());
        createResult.setId(floatingIpCreate.getId());

        try {

            FloatingIpCreateResult floatingIpCreateResult =  netHandler.createFloatingIp(floatingIpCreate);
            createResult.setFloatingIpAddr(floatingIpCreateResult.getFloatingIpAddr());
            createResult.setFloatingIpId(floatingIpCreateResult.getFloatingIpId());
            createResult.setSuccess(floatingIpCreateResult.isSuccess());
        } catch (CommonAdapterException e) {
            createResult.setSuccess(false);
            createResult.setErrCode(e.getErrCode());
            createResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {

            createResult.setSuccess(false);
            createResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            createResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return createResult;
    }

    public FloatingIpDeleteResult handleMessage(FloatingIpDelete floatingIpDelete) {
        log.info("receiving message for deleting floatingIp" + "----" + "virtual type : " + floatingIpDelete.getVirtEnvType());
        log.info("msg id : " + floatingIpDelete.getMsgId());

        FloatingIpDeleteResult floatingIpDeleteResult = new FloatingIpDeleteResult();
        floatingIpDeleteResult = (FloatingIpDeleteResult) BaseUtil.setResult(floatingIpDelete, FloatingIpDeleteResult.class);
        floatingIpDeleteResult.setMsgId(floatingIpDelete.getMsgId());
        floatingIpDeleteResult.setFloatingIpId(floatingIpDelete.getFloatingIpId());
        floatingIpDeleteResult.setResId(floatingIpDelete.getResId());
        try {
            boolean result = netHandler.deleteFloatingIp(floatingIpDelete);
            floatingIpDeleteResult.setSuccess(result);
        } catch (CommonAdapterException e) {
            floatingIpDeleteResult.setSuccess(false);
            floatingIpDeleteResult.setErrCode(e.getErrCode());
            floatingIpDeleteResult.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {

            floatingIpDeleteResult.setSuccess(false);
            floatingIpDeleteResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            floatingIpDeleteResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return floatingIpDeleteResult;
    }

    public TemplateScanResult handleMessage(TemplateScan templateScan) {

        log.info("receiving message for scanning templates" + "----" + "virtual type : "
                + templateScan.getVirtEnvType());

        log.info("msg id : " + templateScan.getMsgId());

        TemplateScanResult templateScanResult = new TemplateScanResult();
        templateScanResult = (TemplateScanResult) BaseUtil.setResult(templateScan, TemplateScanResult.class);
        templateScanResult.setMsgId(templateScan.getMsgId());

        try {
            List<Template> templates = scanHandler.scanTemplate(templateScan);
            templateScanResult.setSuccess(true);

            List<TemplateVO> templateVOs = new ArrayList<TemplateVO>();

            for (Template template : templates) {
                TemplateVO templateVO = new TemplateVO();
                try {
                    BeanUtils.copyProperties(templateVO, template);
                    templateVOs.add(templateVO);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            templateScanResult.setTemplates(templateVOs);

        } catch (CommonAdapterException e) {

            templateScanResult.setSuccess(false);
            templateScanResult.setErrCode(e.getErrCode());
            templateScanResult.setErrMsg(e.getErrMsg());

        } catch (AdapterUnavailableException e) {

            templateScanResult.setSuccess(false);
            templateScanResult.setErrCode(Constants.AdapterUnvailableException.CODE);
            templateScanResult.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return templateScanResult;
    }

    public KeypairCreateResult handleMessage(KeypairCreate keypairCreate) {
        log.info("receiving message for creatting keypair" + "----"
                + "virtual type : " + keypairCreate.getVirtEnvType());

        log.info("msg id : " + keypairCreate.getMsgId());
        KeypairCreateResult result = new KeypairCreateResult();
        result = (KeypairCreateResult) BaseUtil.setResult(keypairCreate, KeypairCreateResult.class);
        result.setMsgId(keypairCreate.getMsgId());

        try {
            KeyPairResult createKeyPair = adminHandler
                    .createKeyPair(keypairCreate);
            result = createKeyPair.getKeypairCreateResult();
            result.setSuccess(createKeyPair.isSuccess());
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public KeypairDeleteResult handleMessage(KeypairDelete keypairDelete) {
        log.info("receiving message for deleting keypair" + "----"
                + "virtual type : " + keypairDelete.getVirtEnvType());

        log.info("msg id : " + keypairDelete.getMsgId());
        KeypairDeleteResult result = new KeypairDeleteResult();
        result = (KeypairDeleteResult) BaseUtil.setResult(keypairDelete, KeypairDeleteResult.class);
        result.setMsgId(keypairDelete.getMsgId());

        try {
            boolean deletekeyPair = adminHandler.deletekeyPair(keypairDelete);
            result.setSuccess(deletekeyPair);
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public KeypairListGetResult handleMessage(KeypairListGet keypairListGet) {
        log.info("receiving message for getting keypairList" + "----"
                + "virtual type : " + keypairListGet.getVirtEnvType());

        log.info("msg id : " + keypairListGet.getMsgId());
        KeypairListGetResult result = new KeypairListGetResult();
        result = (KeypairListGetResult) BaseUtil.setResult(keypairListGet, KeypairListGetResult.class);
        result.setMsgId(keypairListGet.getMsgId());

        try {
            KeyPairResult keyPairList = adminHandler.getKeyPairList(keypairListGet);
            result = keyPairList.getKeypairListGetResult();
            result.setSuccess(keyPairList.isSuccess());
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public KeypairGetResult handleMessage(KeypairGet keypairGet) {
        log.info("receiving message for getting keypairInfo" + "----"
                + "virtual type : " + keypairGet.getVirtEnvType());

        log.info("msg id : " + keypairGet.getMsgId());
        KeypairGetResult result = new KeypairGetResult();
        result = (KeypairGetResult) BaseUtil.setResult(keypairGet, KeypairGetResult.class);
        result.setMsgId(keypairGet.getMsgId());

        try {
            KeyPairResult keyPairList = adminHandler.getKeyPairInfo(keypairGet);
            result = keyPairList.getKeypairGetResult();
            result.setSuccess(keyPairList.isSuccess());
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public ImageDeleteResult handleMessage(ImageDelete imageDelete) {
        log.info("receiving message for deleting image" + "----"
                + "virtual type : " + imageDelete.getVirtEnvType());

        log.info("msg id : " + imageDelete.getMsgId());
        ImageDeleteResult result = new ImageDeleteResult();
        result = (ImageDeleteResult) BaseUtil.setResult(imageDelete, ImageDeleteResult.class);
        result.setMsgId(imageDelete.getMsgId());
        result.setImageId(imageDelete.getImageId());
        result.setSid(imageDelete.getSid());
        try {
            boolean deleteImage = vmHandler.deleteImage(imageDelete);
            result.setSuccess(deleteImage);
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        }
        return result;
    }

    public ImageUpdateResult handleMessage(ImageUpdate imageUpdate) {
        log.info("receiving message for updating image" + "----"
                + "virtual type : " + imageUpdate.getVirtEnvType());

        log.info("msg id : " + imageUpdate.getMsgId());
        ImageUpdateResult result = new ImageUpdateResult();
        result = (ImageUpdateResult) BaseUtil.setResult(imageUpdate, ImageUpdateResult.class);
        result.setMsgId(imageUpdate.getMsgId());
        result.setImageId(imageUpdate.getImageId());
        result.setSid(imageUpdate.getSid());
        try {
            Template updateImage = vmHandler.updateImage(imageUpdate);
            BeanUtils.copyProperties(result, updateImage);
        } catch (CommonAdapterException e) {
            result.setSuccess(false);
            result.setErrCode(e.getErrCode());
            result.setErrMsg(e.getErrMsg());
        } catch (AdapterUnavailableException e) {
            result.setSuccess(false);
            result.setErrCode(Constants.AdapterUnvailableException.CODE);
            result.setErrMsg(Constants.AdapterUnvailableException.MSG);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
}
