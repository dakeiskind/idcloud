package com.h3c.idcloud.core.adapter.facade.provision;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Block;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.DiskFormat;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Fdisks;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.VmSecurityGroups;
import com.h3c.idcloud.core.adapter.pojo.image.ImageDelete;
import com.h3c.idcloud.core.adapter.pojo.image.ImageUpdate;
import com.h3c.idcloud.core.adapter.pojo.network.SecurityGroupQuery;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupAdd;
import com.h3c.idcloud.core.adapter.pojo.network.ServerSecurityGroupDelete;
import com.h3c.idcloud.core.adapter.pojo.software.Software;
import com.h3c.idcloud.core.adapter.pojo.software.SoftwareDeploy;
import com.h3c.idcloud.core.adapter.pojo.vm.ImageGet;
import com.h3c.idcloud.core.adapter.pojo.vm.ImageQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmBlockQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmGet;
import com.h3c.idcloud.core.adapter.pojo.vm.VmImageCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmInfoGet;
import com.h3c.idcloud.core.adapter.pojo.vm.VmMigrate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmModify;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNicAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNicDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmQuerySgs;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRebuild;
import com.h3c.idcloud.core.adapter.pojo.vm.VmReconfig;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRecovery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRename;
import com.h3c.idcloud.core.adapter.pojo.vm.VmResize;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotQuery;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotRevert;
import com.h3c.idcloud.core.adapter.pojo.vm.VmStatus;
import com.h3c.idcloud.core.adapter.pojo.vm.VmTemplateCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmUserAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.VmVncConsole;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public interface ProvisionVm {

    boolean operateVm(VmOperate vmOperate) throws CommonAdapterException, AdapterUnavailableException;

    Server createVm(VmCreate vmCreate) throws CommonAdapterException, AdapterUnavailableException;

    CommonAdapterResult reconfigVm(VmReconfig vmReconfig) throws CommonAdapterException, AdapterUnavailableException;

    Template snapshotVm(VmSnapshotCreate vmSnapshotCreate) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteSnapshot(VmSnapshotDelete vmSnapshotDelete) throws CommonAdapterException, AdapterUnavailableException;

    boolean removeVm(VmRemove vmRemove) throws CommonAdapterException, AdapterUnavailableException;

    Server migrateVm(VmMigrate vmMigrate) throws CommonAdapterException, AdapterUnavailableException;

    boolean revertVm(VmSnapshotRevert vmSnapshotRevert) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteVmSnapshot(VmSnapshotDelete vmSnapshotDelete) throws CommonAdapterException,
            AdapterUnavailableException;

    List<SnapshotInfo> queryVmSnapshot(VmSnapshotQuery vmSnapshotQuery) throws CommonAdapterException,
            AdapterUnavailableException;

    boolean templateVm(VmTemplateCreate vmTemplateCreate) throws CommonAdapterException, AdapterUnavailableException;

    String queryVmStatus(VmStatus vmStatus) throws CommonAdapterException, AdapterUnavailableException;

    Image getImage(ImageGet imageGet) throws CommonAdapterException, AdapterUnavailableException;

    List<Image> queryImage(ImageQuery imageQuery) throws CommonAdapterException, AdapterUnavailableException;

    Server getVm(VmGet vmGet) throws CommonAdapterException, AdapterUnavailableException;

    List<Server> queryVms(VmQuery vmQuery) throws CommonAdapterException, AdapterUnavailableException;

    boolean AddNic(VmNicAdd vmNicAdd) throws CommonAdapterException, AdapterUnavailableException;

    boolean DeleteNic(VmNicDelete vmNicDelete) throws CommonAdapterException, AdapterUnavailableException;

    Server modifyVm(VmModify vmModify) throws CommonAdapterException, AdapterUnavailableException;

    boolean renameVm(VmRename vmRename) throws CommonAdapterException, AdapterUnavailableException;

    Server rebuildVm(VmRebuild vmRebuild) throws CommonAdapterException, AdapterUnavailableException;

    String getConsoleUrl(VmVncConsole vmVncConsole) throws CommonAdapterException, AdapterUnavailableException;

    boolean resizeVm(VmResize vmResize) throws CommonAdapterException, AdapterUnavailableException;

    boolean imageVm(VmImageCreate vmImageCreate) throws CommonAdapterException, AdapterUnavailableException;

    boolean recoveryVm(VmRecovery vmRecovery) throws CommonAdapterException, AdapterUnavailableException;

    List<Block> queryBlocks(VmBlockQuery vmBlockQuery) throws CommonAdapterException, AdapterUnavailableException;

    boolean addSecurityGroup(ServerSecurityGroupAdd securityGroupAdd) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteSecurityGroup(ServerSecurityGroupDelete securityGroupDelete) throws CommonAdapterException, AdapterUnavailableException;

    List<SecurityGroup> querySecurityGroup(SecurityGroupQuery securityGroupQuery) throws CommonAdapterException, AdapterUnavailableException;

    VmSecurityGroups queryVmSecurityGroups(VmQuerySgs vmQuerySgs) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteVm(VmDelete vmDelete) throws CommonAdapterException, AdapterUnavailableException;

    Server getVmInfo(VmInfoGet vmInfoGet) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteImage(ImageDelete ImageDelete) throws CommonAdapterException, AdapterUnavailableException;

    Template updateImage(ImageUpdate imageUpdate) throws CommonAdapterException, AdapterUnavailableException;

    List<Software> installSoftware(SoftwareDeploy softwareDeploy) throws CommonAdapterException, AdapterUnavailableException;

    Fdisks formatDisk(DiskFormat diskFormat) throws CommonAdapterException, AdapterUnavailableException;

    boolean addUser(VmUserAdd userAdd) throws CommonAdapterException, AdapterUnavailableException;
}
