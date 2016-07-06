package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmScanHostWithVms;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmStoragesScan;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Cluster;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Clusters;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.CpuPools;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataStore;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Hosts;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.IOSlotResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Ios;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Mpars;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Network;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SSPResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SimpleHost;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SysDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Templates;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Uuid;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Vm;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Vswitchs;
import com.h3c.idcloud.core.adapter.facade.provision.model.storage.KvmStorages;
import com.h3c.idcloud.core.adapter.facade.provision.model.vm.Server;
import com.h3c.idcloud.core.adapter.pojo.other.EnvConnectionTest;
import com.h3c.idcloud.core.adapter.pojo.scan.AllInOneScan;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScan;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.CpuPoolScan;
import com.h3c.idcloud.core.adapter.pojo.scan.DiskScan;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByCluster;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByDvs;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByEnv;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanBySvs;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanWithVmsByEnv;
import com.h3c.idcloud.core.adapter.pojo.scan.IOSlotScan;
import com.h3c.idcloud.core.adapter.pojo.scan.IoScan;
import com.h3c.idcloud.core.adapter.pojo.scan.KvmStorageScan;
import com.h3c.idcloud.core.adapter.pojo.scan.MparsScan;
import com.h3c.idcloud.core.adapter.pojo.scan.NetworkScan;
import com.h3c.idcloud.core.adapter.pojo.scan.NpivScan;
import com.h3c.idcloud.core.adapter.pojo.scan.SSPScan;
import com.h3c.idcloud.core.adapter.pojo.scan.StorageScan;
import com.h3c.idcloud.core.adapter.pojo.scan.TemplateScan;
import com.h3c.idcloud.core.adapter.pojo.scan.VmScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.VswitchScan;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvisionScanKvmImpl implements ProvisionScan {

    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(ProvisionScanKvmImpl.class);

    private List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmTemplateScan KvmTemplateScan;
    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmScanHostByEnv KvmScanHostByEnv;
    @Autowired
    private KvmScanHostWithVms kvmScanHostWithVms;
    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmAvailabilityZone KvmAvailabilityZone;
    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmScanHostAlone KvmScanHostAlone;
    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmGetVmInfo KvmGetVmInfo;
    @Autowired
    private KvmStoragesScan kvmStorageScan;
    public ProvisionScanKvmImpl() {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        providers.add(jsonProvider);
    }

    @Override
    public Cluster scanClusterAlone(ClusterScanAlone clusterScanAlone) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Host scanHostAlone(HostScanAlone hostScanAlone) throws CommonAdapterException, AdapterUnavailableException {
        Hosts hosts = (Hosts) KvmScanHostAlone.invoke(hostScanAlone);
        return hosts.getHost();
    }

    @Override
    public Vm scanVmAlone(VmScanAlone vmScanAlone) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        Server server = (Server) KvmGetVmInfo.invoke(vmScanAlone);
        Vm vm = new Vm();
        vm.setVmName(server.getName());
        vm.setHostName(server.getHostName());
        vm.setUuid(server.getUuid());
        vm.setStatus(server.getStatus());
        try {
            BeanUtils.copyProperty(vm, "ips", server.getNetworks());
            BeanUtils.copyProperty(vm, "resVdList", server.getDisks());
            BeanUtils.copyProperty(vm, "securityGroups", server.getSecuritygroups());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return vm;
    }

    @Override
    public List<Host> scanAllInOne(AllInOneScan allInOneScan) throws CommonAdapterException,
            AdapterUnavailableException {
        HostScanWithVmsByEnv hostScanByEnv = new HostScanWithVmsByEnv();
        try {
            BeanUtils.copyProperties(hostScanByEnv, allInOneScan);
        } catch (Exception e) {
            List<Host> hosts = new ArrayList<Host>();
            return hosts;
        }
        return ((Hosts) kvmScanHostWithVms.invoke(hostScanByEnv)).getListdata();
    }

    @Override
    public List<DataStore> scanDataStore(StorageScan storageScan) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cluster> scanCluster(ClusterScan clusterScan) throws CommonAdapterException,
            AdapterUnavailableException {

        // TODO Auto-generated method stub
        return ((Clusters) KvmAvailabilityZone.invoke(clusterScan)).getListdata();
    }

    @Override
    public List<SimpleHost> scanHost(HostScanByCluster hostScanByCluster) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Network scanNetwork(NetworkScan networkScan) throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Uuid> scanUuidByDvs(HostScanByDvs hostScanByDvs) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Uuid> scanUuidBySvs(HostScanBySvs hostScanBySvs) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Template> scanTemplate(TemplateScan templateScan) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        Templates templates = (Templates) KvmTemplateScan.invoke(templateScan);
        return templates.getImages();
    }

    @Override
    public List<Host> scanHostsByEnv(HostScanByEnv hostScanByEnv)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return ((Hosts) KvmScanHostByEnv.invoke(hostScanByEnv)).getListdata();
    }

    @Override
    public List<Host> scanHostWithVmsByEnv(HostScanWithVmsByEnv hostScanByEnv)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return ((Hosts) kvmScanHostWithVms.invoke(hostScanByEnv)).getListdata();
    }

    @Override
    public boolean connectionEnvTest(EnvConnectionTest envConnectionTest)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KvmStorages scanStorages(KvmStorageScan kvmStorageScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return (KvmStorages) this.kvmStorageScan.invoke(kvmStorageScan);
    }

    @Override
    public CpuPools scanCpuPools(CpuPoolScan cpuPoolScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mpars scanMpars(MparsScan mparsScan) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vswitchs scanVswitchs(VswitchScan vswitchScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Ios scanIo(IoScan ioScan) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SSPResult scanSSP(SSPScan sspScan) throws CommonAdapterException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataDiskResult scanDataDisk(NpivScan npivSan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysDiskResult scanSysDisk(DiskScan diskScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IOSlotResult scanIoSlots(IOSlotScan ioSlotScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

}
