package com.h3c.idcloud.core.adapter.facade;

import com.h3c.idcloud.core.adapter.facade.common.ProviderFactory;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionVm;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class VmHandler implements ProvisionVm {

    @Autowired
    private ProviderFactory provider;

    public boolean operateVm(VmOperate vmOperate) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmOperate.getProviderType()).operateVm(vmOperate);
    }

    public Server createVm(VmCreate vmCreate) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmCreate.getProviderType()).createVm(vmCreate);
    }

    public CommonAdapterResult reconfigVm(VmReconfig vmReconfig) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmReconfig.getProviderType()).reconfigVm(vmReconfig);
    }

    public Template snapshotVm(VmSnapshotCreate vmSnapshotCreate) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionVm(vmSnapshotCreate.getProviderType()).snapshotVm(vmSnapshotCreate);
    }

    public boolean removeVm(VmRemove vmRemove) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmRemove.getProviderType()).removeVm(vmRemove);
    }

    public Server migrateVm(VmMigrate vmMigrate) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(vmMigrate.getProviderType()).migrateVm(vmMigrate);
    }

    public boolean revertVm(VmSnapshotRevert vmRevert) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(vmRevert.getProviderType()).revertVm(vmRevert);
    }

    public boolean templateVm(VmTemplateCreate vmTemplateCreate) throws CommonAdapterException,
            AdapterUnavailableException {
        return provider.getProvisionVm(vmTemplateCreate.getProviderType()).templateVm(vmTemplateCreate);
    }

    public String queryVmStatus(VmStatus vmStatus) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(vmStatus.getProviderType()).queryVmStatus(vmStatus);
    }

    public Image getImage(ImageGet imageGet) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(imageGet.getProviderType()).getImage(imageGet);
    }

    public List<Image> queryImage(ImageQuery imageQuery) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(imageQuery.getProviderType()).queryImage(imageQuery);
    }

    public Server getVm(VmGet vmGet) throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(vmGet.getProviderType()).getVm(vmGet);
    }

    public List<Server> queryVms(VmQuery vmQuery) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmQuery.getProviderType()).queryVms(vmQuery);

    }

    public boolean deleteVmSnapshot(VmSnapshotDelete vmSnapshotDelete) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionVm(vmSnapshotDelete.getProviderType()).deleteVmSnapshot(vmSnapshotDelete);
    }

    public List<SnapshotInfo> queryVmSnapshot(VmSnapshotQuery vmSnapshotQuery) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionVm(vmSnapshotQuery.getProviderType()).queryVmSnapshot(vmSnapshotQuery);
    }

    public boolean AddNic(VmNicAdd vmNicAdd) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmNicAdd.getProviderType()).AddNic(vmNicAdd);
    }

    public boolean DeleteNic(VmNicDelete vmNicDelete) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmNicDelete.getProviderType()).DeleteNic(vmNicDelete);
    }

    public Server modifyVm(VmModify vmModify) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmModify.getProviderType()).modifyVm(vmModify);
    }

    public boolean renameVm(VmRename vmRename) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionVm(vmRename.getProviderType()).renameVm(vmRename);
    }

    @Override
    public Server rebuildVm(VmRebuild vmRebuild) throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(vmRebuild.getProviderType()).rebuildVm(vmRebuild);
    }

    @Override
    public String getConsoleUrl(VmVncConsole vmVncConsole) throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(vmVncConsole.getProviderType()).getConsoleUrl(vmVncConsole);
    }

    @Override
    public boolean resizeVm(VmResize vmResize) throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(vmResize.getProviderType()).resizeVm(vmResize);
    }

    @Override
    public boolean imageVm(VmImageCreate vmImageCreate) throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(vmImageCreate.getProviderType()).imageVm(vmImageCreate);
    }

    @Override
    public boolean recoveryVm(VmRecovery vmRecovery) throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(vmRecovery.getProviderType()).recoveryVm(vmRecovery);
    }

    @Override
    public List<Block> queryBlocks(VmBlockQuery vmBlockQuery) throws CommonAdapterException,
            AdapterUnavailableException {
        
        return provider.getProvisionVm(vmBlockQuery.getProviderType()).queryBlocks(vmBlockQuery);
    }

    @Override
    public boolean addSecurityGroup(ServerSecurityGroupAdd securityGroupAdd)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(securityGroupAdd.getProviderType()).addSecurityGroup(securityGroupAdd);
    }

    @Override
    public boolean deleteSecurityGroup(ServerSecurityGroupDelete securityGroupDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(securityGroupDelete.getProviderType()).deleteSecurityGroup(securityGroupDelete);
    }

    @Override
    public List<SecurityGroup> querySecurityGroup(
            SecurityGroupQuery securityGroupQuery)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(securityGroupQuery.getProviderType()).querySecurityGroup(securityGroupQuery);
    }

    @Override
    public boolean deleteVm(VmDelete vmDelete) throws CommonAdapterException,
            AdapterUnavailableException {
        
        return false;
    }

    @Override
    public Server getVmInfo(VmInfoGet vmInfoGet) throws CommonAdapterException,
            AdapterUnavailableException {
        

        return provider.getProvisionVm(vmInfoGet.getProviderType()).getVmInfo(vmInfoGet);
    }

    @Override
    public boolean deleteImage(ImageDelete ImageDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(ImageDelete.getProviderType()).deleteImage(ImageDelete);
    }

    @Override
    public boolean deleteSnapshot(VmSnapshotDelete vmSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(vmSnapshotDelete.getProviderType()).deleteVmSnapshot(vmSnapshotDelete);
    }

    @Override
    public VmSecurityGroups queryVmSecurityGroups(VmQuerySgs vmQuerySgs)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(vmQuerySgs.getProviderType()).queryVmSecurityGroups(vmQuerySgs);
    }

    @Override
    public Template updateImage(ImageUpdate imageUpdate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(imageUpdate.getProviderType()).updateImage(imageUpdate);
    }

    @Override
    public List<Software> installSoftware(SoftwareDeploy softwareDeploy)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionVm(softwareDeploy.getProviderType()).installSoftware(softwareDeploy);
    }

    @Override
    public Fdisks formatDisk(DiskFormat diskFormat)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return provider.getProvisionVm(diskFormat.getProviderType()).formatDisk(diskFormat);
    }

    @Override
    public boolean addUser(VmUserAdd userAdd) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionVm(userAdd.getProviderType()).addUser(userAdd);
    }

}
