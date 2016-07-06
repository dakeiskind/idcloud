package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionVm;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.DiskFormatAction;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerSoftwareinstall;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerVmCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerVmOpereate;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerVmReconfig;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerVmRemove;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.UserAddAction;
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
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Softwares;
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
public class ProvisionVmPowerImpl implements ProvisionVm {

    @Autowired
    private PowerVmCreate powervmCreate;
    @Autowired
    private PowerVmRemove powerVmRemove;
    @Autowired
    private PowerSoftwareinstall powerSoftwareinstall;
    @Autowired
    private DiskFormatAction diskFormatAction;
    @Autowired
    private UserAddAction userAddAction;
    @Autowired
    private PowerVmOpereate powerVmOpereate;
    @Autowired
    private PowerVmReconfig powerVmReconfig;

    @Override
    public boolean operateVm(VmOperate vmOperate)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        CommonAdapterResult result = powerVmOpereate.invoke(vmOperate);
        return result.isSuccess();
    }

    @Override
    public Server createVm(VmCreate vmCreate) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return (Server) powervmCreate.invoke(vmCreate);
    }

    @Override
    public CommonAdapterResult reconfigVm(VmReconfig vmReconfig)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return powerVmReconfig.invoke(vmReconfig);
    }

    @Override
    public Template snapshotVm(VmSnapshotCreate vmSnapshotCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteSnapshot(VmSnapshotDelete vmSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeVm(VmRemove vmRemove) throws CommonAdapterException,
            AdapterUnavailableException {
        return powerVmRemove.invoke(vmRemove).isSuccess();
    }

    @Override
    public Server migrateVm(VmMigrate vmMigrate) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean revertVm(VmSnapshotRevert vmSnapshotRevert)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteVmSnapshot(VmSnapshotDelete vmSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<SnapshotInfo> queryVmSnapshot(VmSnapshotQuery vmSnapshotQuery)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean templateVm(VmTemplateCreate vmTemplateCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String queryVmStatus(VmStatus vmStatus)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Image getImage(ImageGet imageGet) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Image> queryImage(ImageQuery imageQuery)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Server getVm(VmGet vmGet) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Server> queryVms(VmQuery vmQuery)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean AddNic(VmNicAdd vmNicAdd) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean DeleteNic(VmNicDelete vmNicDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Server modifyVm(VmModify vmModify) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean renameVm(VmRename vmRename) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Server rebuildVm(VmRebuild vmRebuild) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getConsoleUrl(VmVncConsole vmVncConsole)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean resizeVm(VmResize vmResize) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean imageVm(VmImageCreate vmImageCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean recoveryVm(VmRecovery vmRecovery)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Block> queryBlocks(VmBlockQuery vmBlockQuery)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addSecurityGroup(ServerSecurityGroupAdd securityGroupAdd)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteSecurityGroup(
            ServerSecurityGroupDelete securityGroupDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<SecurityGroup> querySecurityGroup(
            SecurityGroupQuery securityGroupQuery)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VmSecurityGroups queryVmSecurityGroups(VmQuerySgs vmQuerySgs)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteVm(VmDelete vmDelete) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Server getVmInfo(VmInfoGet vmInfoGet) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteImage(ImageDelete ImageDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Template updateImage(ImageUpdate imageUpdate)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Software> installSoftware(SoftwareDeploy softwareDeploy)
            throws CommonAdapterException, AdapterUnavailableException {
        Softwares softwares = (Softwares) powerSoftwareinstall.invoke(softwareDeploy);
        return softwares.getSoftwares();
    }

    @Override
    public Fdisks formatDisk(DiskFormat diskFormat)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return (Fdisks) diskFormatAction.invoke(diskFormat);
    }

    @Override
    public boolean addUser(VmUserAdd userAdd) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        CommonAdapterResult result = userAddAction.invoke(userAdd);
        return result.isSuccess();
    }

}
