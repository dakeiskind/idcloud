package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerCpuPoolScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerDiskScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerHostsScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerIOSlotScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerIoScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerNpivScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerSSPScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerTemplateScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerVswitchScan;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Cluster;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvisionScanPowerImpl implements ProvisionScan {
    @Autowired
    private PowerCpuPoolScan powervmCpuPoolScan;
    @Autowired
    private PowerVswitchScan powervmVswitchScan;
    @Autowired
    private com.h3c.idcloud.core.adapter.facade.provision.action.powervm.PowerVmScan PowerVmScan;
    @Autowired
    private PowerVswitchScan powerVswitchScan;
    @Autowired
    private PowerHostsScan powerHostsScan;
    @Autowired
    private PowerIoScan powerIoScan;
    @Autowired
    private PowerSSPScan powerSSPScan;
    @Autowired
    private PowerDiskScan powerDiskScan;
    @Autowired
    private PowerNpivScan powerNpivScan;
    @Autowired
    private PowerTemplateScan powerTemplateScan;
    @Autowired
    private PowerIOSlotScan powerIOSlotScan;

    @Override
    public Cluster scanClusterAlone(ClusterScanAlone clusterScanAlone)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Host scanHostAlone(HostScanAlone hostScanAlone)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vm scanVmAlone(VmScanAlone vmScanAlone)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Host> scanAllInOne(AllInOneScan allInOneScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DataStore> scanDataStore(StorageScan storageScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cluster> scanCluster(ClusterScan clusterScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SimpleHost> scanHost(HostScanByCluster hostScanByCluster)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Network scanNetwork(NetworkScan networkScan)
            throws CommonAdapterException, AdapterUnavailableException {
        Network network = (Network) powervmVswitchScan.invoke(networkScan);
        return network;
    }

    @Override
    public List<Uuid> scanUuidByDvs(HostScanByDvs hostScanByDvs)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Uuid> scanUuidBySvs(HostScanBySvs hostScanBySvs)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Template> scanTemplate(TemplateScan templateScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return ((Templates) powerTemplateScan.invoke(templateScan)).getListdata();
    }

    @Override
    public List<Host> scanHostsByEnv(HostScanByEnv hostScanByEnv)
            throws CommonAdapterException, AdapterUnavailableException {
        Hosts hosts = (Hosts) powerHostsScan.invoke(hostScanByEnv);
        return hosts.getHypervisors();
    }

    @Override
    public List<Host> scanHostWithVmsByEnv(HostScanWithVmsByEnv hostScanByEnv)
            throws CommonAdapterException, AdapterUnavailableException {
        Hosts hosts = (Hosts) powerHostsScan.invoke(hostScanByEnv);
        return hosts.getHypervisors();
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
        return null;
    }

    @Override
    public CpuPools scanCpuPools(CpuPoolScan cpuPoolScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        CpuPools result = (CpuPools) powervmCpuPoolScan.invoke(cpuPoolScan);
        return result;
    }

    @Override
    public Mpars scanMpars(MparsScan mparsScan) throws CommonAdapterException,
            AdapterUnavailableException {

        return (Mpars) PowerVmScan.invoke(mparsScan);
    }

    @Override
    public Vswitchs scanVswitchs(VswitchScan vswitchScan)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return (Vswitchs) powerVswitchScan.invoke(vswitchScan);
    }

    @Override
    public Ios scanIo(IoScan ioScan) throws CommonAdapterException,
            AdapterUnavailableException {
        return (Ios) powerIoScan.invoke(ioScan);
    }

    @Override
    public SSPResult scanSSP(SSPScan sspScan) throws CommonAdapterException,
            AdapterUnavailableException {
        return (SSPResult) powerSSPScan.invoke(sspScan);
    }

    @Override
    public DataDiskResult scanDataDisk(NpivScan npivSan)
            throws CommonAdapterException, AdapterUnavailableException {
        return (DataDiskResult) powerNpivScan.invoke(npivSan);
    }

    @Override
    public SysDiskResult scanSysDisk(DiskScan diskScan)
            throws CommonAdapterException, AdapterUnavailableException {
        return (SysDiskResult) powerDiskScan.invoke(diskScan);
    }

    @Override
    public IOSlotResult scanIoSlots(IOSlotScan ioSlotScan)
            throws CommonAdapterException, AdapterUnavailableException {
        return (IOSlotResult) powerIOSlotScan.invoke(ioSlotScan);
    }

}
