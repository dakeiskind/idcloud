package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionVm;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmMigrate;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmModify;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmNicAdd;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmNicDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmOperate;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmReconfig;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmRemove;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmRename;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmSnapshotCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmSnapshotDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmSnapshotQuery;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmSnapshotRevert;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmStatus;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmTemplateCreate;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.network.SecurityGroup;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Block;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.DiskFormat;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Fdisks;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.SnapshotInfos;
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

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvisionVmVmwareImpl implements ProvisionVm {

    private static Logger log = LoggerFactory.getLogger(ProvisionVmVmwareImpl.class);

    @Autowired
    private VmwareVmCreate vmwareVmCreate;

    @Autowired
    private VmwareVmOperate vmwareVmOperate;

    @Autowired
    private VmwareVmReconfig vmwareVmReconfig;

    @Autowired
    private VmwareVmSnapshotCreate vmwareVmSnapshot;

    @Autowired
    private VmwareVmRemove vmwareVmRemove;

    @Autowired
    private VmwareVmMigrate vmwareVmMigrate;

    @Autowired
    private VmwareVmSnapshotRevert vmwareVmRevert;

    @Autowired
    private VmwareVmTemplateCreate vmwareVmTemplate;

    @Autowired
    private VmwareVmStatus vmwareVmStatus;

    @Autowired
    private VmwareVmSnapshotQuery vmwareVmSnapshotQuery;

    @Autowired
    private VmwareVmSnapshotDelete vmwareVmSnapshotDelete;

    @Autowired
    private VmwareVmNicAdd vmwareVmNicAdd;

    @Autowired
    private VmwareVmNicDelete vmwareVmNicDelete;

    @Autowired
    private VmwareVmModify vmwareVmModify;

    @Autowired
    private VmwareVmRename vmwareVmRename;

    private List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();

    public ProvisionVmVmwareImpl() {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        providers.add(jsonProvider);
    }

    public boolean operateVm(VmOperate vmOperate) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmOperate.invoke(vmOperate);

        return result.isSuccess();
    }

    public Server createVm(VmCreate vmCreate) throws VmwareException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmCreate.invoke(vmCreate);

        return (Server) result;
    }

    public CommonAdapterResult reconfigVm(VmReconfig vmReconfig) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmReconfig.invoke(vmReconfig);
        return result;

    }

    public Template snapshotVm(VmSnapshotCreate vmSnapshotCreate) throws CommonAdapterException,
            AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmSnapshot.invoke(vmSnapshotCreate);

        return null;
    }

    public boolean removeVm(VmRemove vmRemove) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmRemove.invoke(vmRemove);

        return result.isSuccess();

    }

    public Server migrateVm(VmMigrate vmMigrate) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmMigrate.invoke(vmMigrate);
        Server server = new Server();
        server.setSuccess(result.isSuccess());
        return server;
    }

    public boolean revertVm(VmSnapshotRevert vmRevert) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmRevert.invoke(vmRevert);

        return false;
    }

    public boolean templateVm(VmTemplateCreate vmTemplate) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmTemplate.invoke(vmTemplate);

        return false;
    }

    public String queryVmStatus(VmStatus vmStatus) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmStatus.invoke(vmStatus);

        return null;
    }

    public Image getImage(ImageGet imageGet) throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Image> queryImage(ImageQuery imageQuery) throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    public Server getVm(VmGet vmGet) throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Server> queryVms(VmQuery vmQuery) throws CommonAdapterException {

        return null;

    }

    public boolean deleteVmSnapshot(VmSnapshotDelete vmSnapshotDelete) throws CommonAdapterException,
            AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmSnapshotDelete.invoke(vmSnapshotDelete);

        return result.isSuccess();
    }

    public List<SnapshotInfo> queryVmSnapshot(VmSnapshotQuery vmSnapshotQuery) throws CommonAdapterException,
            AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmSnapshotQuery.invoke(vmSnapshotQuery);

        return ((SnapshotInfos) result).getSnapshotInfos();
    }

    public boolean AddNic(VmNicAdd vmNicAdd) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmNicAdd.invoke(vmNicAdd);

        return result.isSuccess();
    }

    public boolean DeleteNic(VmNicDelete vmNicDelete) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmNicDelete.invoke(vmNicDelete);

        return result.isSuccess();
    }

    public Server modifyVm(VmModify vmModify) throws CommonAdapterException, AdapterUnavailableException {

        Server server = (Server) vmwareVmModify.invoke(vmModify);

        return server;
    }

    public boolean renameVm(VmRename vmRename) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = (CommonAdapterResult) vmwareVmRename.invoke(vmRename);

        return result.isSuccess();
    }

    @Override
    public Server rebuildVm(VmRebuild vmRebuild) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getConsoleUrl(VmVncConsole vmVncConsole) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean resizeVm(VmResize vmResize) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean imageVm(VmImageCreate vmImageCreate) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean recoveryVm(VmRecovery vmRecovery) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Block> queryBlocks(VmBlockQuery vmBlockQuery) throws CommonAdapterException,
            AdapterUnavailableException {
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
    public boolean deleteImage(ImageDelete vmImageDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteSnapshot(VmSnapshotDelete vmSnapshotDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public VmSecurityGroups queryVmSecurityGroups(VmQuerySgs vmQuerySgs)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Fdisks formatDisk(DiskFormat diskFormat)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addUser(VmUserAdd userAdd) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

}
