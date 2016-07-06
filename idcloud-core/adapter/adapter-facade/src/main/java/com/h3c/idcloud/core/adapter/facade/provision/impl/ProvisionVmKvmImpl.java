package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionVm;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmBlocksQuery;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmConsoleUrl;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmGetVmInfo;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmImageDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmImageUpdate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmQueryVmSgs;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSecurityGroupAdd;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSecurityGroupDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmSecurityGroupQuery;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmImageCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmMigrate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmOperate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmRebuild;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmRecovery;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmRemove;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmResize;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmSnapshotCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmVmSnapshotDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVmConsole;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVmCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVmOperate;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVmQuery;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackVmRemove;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroups;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Block;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Blocks;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.DiskFormat;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Fdisks;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Servers;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.VmConsole;
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
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class ProvisionVmKvmImpl implements ProvisionVm {

    @Autowired
    private KvmVmCreate kvmVmCreate;

    @Autowired
    private KvmVmOperate kvmVmOperate;

    @Autowired
    private KvmVmRemove kvmVmRemove;

    @Autowired
    private KvmVmRebuild kvmVmRebuild;

//    @Autowired
//    private KvmConsoleUrl kvmConsoleUrl;

    @Autowired
    private KvmVmResize kvmVmResize;

    @Autowired
    private KvmVmImageCreate kvmVmImageCreate;

    @Autowired
    private KvmVmSnapshotCreate kvmVmSnapshotCreate;

    @Autowired
    private KvmVmRecovery kvmVmSnapshotRevert;

    @Autowired
    private KvmVmRecovery kvmVmRecovery;

    @Autowired
    private KvmBlocksQuery kvmBlocksQuery;

    @Autowired
    private KvmSecurityGroupAdd kvmSecurityGroupAdd;

    @Autowired
    private KvmSecurityGroupDelete kvmSecurityGroupDelete;

    @Autowired
    private KvmSecurityGroupQuery kvmSecurityGroupQuery;

    @Autowired
    private KvmGetVmInfo kvmGetVmInfo;

    @Autowired
    private KvmImageDelete kvmVmImageDelete;

    @Autowired
    private KvmVmSnapshotDelete kvmVmSnapshotDelete;

    @Autowired
    private KvmQueryVmSgs kvmQueryVmSgs;

    @Autowired
    private KvmVmMigrate kvmVmMigrate;

    @Autowired
    private KvmImageUpdate kvmImageUpdate;


    //jclouds
//    @Autowired
//    private OpenStackVmCreate openStackVmCreate;
    @Autowired
    private OpenStackVmRemove openStackVmRemove;
    @Autowired
    private OpenStackVmOperate openStackVmOperate;
    @Autowired
    private OpenStackVmConsole openStackVmConsole;

    public boolean operateVm(VmOperate vmOperate) throws CommonAdapterException, AdapterUnavailableException {

//        CommonAdapterResult result = (CommonAdapterResult) kvmVmOperate.invoke(vmOperate);
        CommonAdapterResult result = openStackVmOperate.invoke(vmOperate);
        return result.isSuccess();
    }

    public Server createVm(VmCreate vmCreate) throws CommonAdapterException, AdapterUnavailableException {

//        Server result = (Server) kvmVmCreate.invoke(vmCreate);
//        Server result = (Server) openstackVmCreate.invoke(vmCreate);
        // in the sake of per request per OpenStackVmCreate class, get bean from spring context.
        OpenStackVmCreate openStackVmCreate = SpringContextHolder.getBean("openStackVmCreate");
        Server result = (Server) openStackVmCreate.invoke(vmCreate);
        return result;
    }

    public CommonAdapterResult reconfigVm(VmReconfig vmReconfig) throws KvmException, AdapterUnavailableException {
        
        return null;
    }

    public Template snapshotVm(VmSnapshotCreate vmSnapshot) throws CommonAdapterException, AdapterUnavailableException {
        
        Template template = (Template) kvmVmSnapshotCreate.invoke(vmSnapshot);
        return template;
    }

    public boolean removeVm(VmRemove vmRemove) throws CommonAdapterException, AdapterUnavailableException {

//        CommonAdapterResult result = (CommonAdapterResult) kvmVmRemove.invoke(vmRemove);
        CommonAdapterResult result = openStackVmRemove.invoke(vmRemove);
        return result.isSuccess();
    }

    public Server migrateVm(VmMigrate vmMigrate) throws CommonAdapterException, AdapterUnavailableException {
        
        return (Server) kvmVmMigrate.invoke(vmMigrate);
    }

    public boolean revertVm(VmSnapshotRevert vmRevert) throws CommonAdapterException, AdapterUnavailableException {
        
        CommonAdapterResult result = kvmVmSnapshotRevert.invoke(vmRevert);
        return result.isSuccess();
    }

    public boolean templateVm(VmTemplateCreate vmTemplateCreate) throws KvmException, AdapterUnavailableException {
        
        return false;
    }

    public String queryVmStatus(VmStatus vmStatus) throws KvmException, AdapterUnavailableException {
        
        return null;
    }

    public Image getImage(ImageGet imageGet) throws KvmException, AdapterUnavailableException {
        
        return null;
    }

    public List<Image> queryImage(ImageQuery imageQuery) throws KvmException, AdapterUnavailableException {
        
        return null;
    }

    public Server getVm(VmGet vmGet) throws KvmException, AdapterUnavailableException {
        
        return null;
    }

    public List<Server> queryVms(VmQuery vmQuery) throws CommonAdapterException, AdapterUnavailableException {

//        KvmVmQuery kvmVmQuery = new KvmVmQuery();
//        CommonAdapterResult result = kvmVmQuery.invoke(vmQuery);
        OpenStackVmQuery openStackVmQuery = new OpenStackVmQuery();
        CommonAdapterResult result = openStackVmQuery.invoke(vmQuery);

        return ((Servers) result).getServers();

    }

    public boolean deleteVmSnapshot(VmSnapshotDelete vmSnapshotDelete) throws CommonAdapterException, AdapterUnavailableException {
        
        CommonAdapterResult result = kvmVmSnapshotDelete.invoke(vmSnapshotDelete);
        return result.isSuccess();
    }

    public List<SnapshotInfo> queryVmSnapshot(VmSnapshotQuery vmSnapshotQuery) throws KvmException,
            AdapterUnavailableException {
        
        return null;
    }

    public boolean AddNic(VmNicAdd vmNicAdd) throws KvmException, AdapterUnavailableException {
        
        return false;
    }

    public boolean DeleteNic(VmNicDelete vmNicDelete) throws KvmException, AdapterUnavailableException {
        
        return false;
    }

    public Server modifyVm(VmModify vmModify) throws KvmException, AdapterUnavailableException {
        
        return null;
    }

    @Override
    public boolean renameVm(VmRename vmRename) throws CommonAdapterException, AdapterUnavailableException {
        
        return false;
    }

    @Override
    public Server rebuildVm(VmRebuild vmRebuild) throws CommonAdapterException, AdapterUnavailableException {
        
        Server server = (Server) kvmVmRebuild.invoke(vmRebuild);
        return server;
    }

    @Override
    public String getConsoleUrl(VmVncConsole vmVncConsole) throws CommonAdapterException, AdapterUnavailableException {
//        CommonAdapterResult result = kvmConsoleUrl.invoke(vmVncConsole);
        CommonAdapterResult result = openStackVmConsole.invoke(vmVncConsole);
        return ((VmConsole) result).getUrl();
    }

    @Override
    public boolean resizeVm(VmResize vmResize) throws CommonAdapterException, AdapterUnavailableException {
        

        CommonAdapterResult result = kvmVmResize.invoke(vmResize);

        return result.isSuccess();
    }

    @Override
    public boolean imageVm(VmImageCreate vmImageCreate) throws CommonAdapterException, AdapterUnavailableException {
        

        CommonAdapterResult result = kvmVmImageCreate.invoke(vmImageCreate);
        return result.isSuccess();
    }

    @Override
    public boolean recoveryVm(VmRecovery vmRecovery) throws CommonAdapterException, AdapterUnavailableException {
        
        CommonAdapterResult result = kvmVmRecovery.invoke(vmRecovery);
        return result.isSuccess();
    }

    @Override
    public List<Block> queryBlocks(VmBlockQuery vmBlockQuery) throws CommonAdapterException,
            AdapterUnavailableException {
        
        CommonAdapterResult result = kvmBlocksQuery.invoke(vmBlockQuery);
        return ((Blocks) result).getBlocks();
    }

    @Override
    public boolean addSecurityGroup(ServerSecurityGroupAdd securityGroupAdd)
            throws CommonAdapterException, AdapterUnavailableException {
        
        CommonAdapterResult result = kvmSecurityGroupAdd.invoke(securityGroupAdd);
        return result.isSuccess();
    }

    @Override
    public boolean deleteSecurityGroup(ServerSecurityGroupDelete securityGroupDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        
        CommonAdapterResult result = kvmSecurityGroupDelete.invoke(securityGroupDelete);
        return result.isSuccess();
    }

    @Override
    public List<SecurityGroup> querySecurityGroup(
            SecurityGroupQuery securityGroupQuery)
            throws CommonAdapterException, AdapterUnavailableException {
        
        CommonAdapterResult result = kvmSecurityGroupQuery.invoke(securityGroupQuery);
        return ((SecurityGroups) result).getSecurityGroups();
    }

    @Override
    public boolean deleteVm(VmDelete vmDelete) throws CommonAdapterException,
            AdapterUnavailableException {
        
        return false;
    }

    @Override
    public Server getVmInfo(VmInfoGet vmInfoGet) throws CommonAdapterException,
            AdapterUnavailableException {
        

        Server serverInfo = (Server) kvmGetVmInfo.invoke(vmInfoGet);
        return serverInfo;
    }

    @Override
    public boolean deleteImage(ImageDelete vmImageDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        

        CommonAdapterResult result = kvmVmImageDelete.invoke(vmImageDelete);
        return result.isSuccess();
    }

    @Override
    public boolean deleteSnapshot(VmSnapshotDelete vmSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        
        CommonAdapterResult result = kvmVmSnapshotDelete.invoke(vmSnapshotDelete);
        return result.isSuccess();
    }

    @Override
    public VmSecurityGroups queryVmSecurityGroups(VmQuerySgs vmQuerySgs)
            throws CommonAdapterException, AdapterUnavailableException {
        

        return (VmSecurityGroups) kvmQueryVmSgs.invoke(vmQuerySgs);
    }

    @Override
    public Template updateImage(ImageUpdate imageUpdate)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return (Template) kvmImageUpdate.invoke(imageUpdate);
    }

    @Override
    public List<Software> installSoftware(SoftwareDeploy softwareDeploy)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return null;
    }

    @Override
    public Fdisks formatDisk(DiskFormat diskFormat)
            throws CommonAdapterException, AdapterUnavailableException {
        
        return null;
    }

    @Override
    public boolean addUser(VmUserAdd userAdd) throws CommonAdapterException,
            AdapterUnavailableException {
        
        return false;
    }

}
