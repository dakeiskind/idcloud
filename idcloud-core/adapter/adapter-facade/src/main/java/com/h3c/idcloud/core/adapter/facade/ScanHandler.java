package com.h3c.idcloud.core.adapter.facade;

import com.h3c.idcloud.core.adapter.facade.common.ProviderFactory;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionScan;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Cluster;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.CpuPools;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataStore;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.IOSlotResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Ios;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Mpars;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Network;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SSPResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SimpleHost;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SysDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
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
public class ScanHandler implements ProvisionScan {

    @Autowired
    private ProviderFactory provider;

    @Override
    public Cluster scanClusterAlone(ClusterScanAlone clusterScanAlone) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionScan(clusterScanAlone.getProviderType()).scanClusterAlone(clusterScanAlone);
    }

    @Override
    public Host scanHostAlone(HostScanAlone hostScanAlone) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionScan(hostScanAlone.getProviderType()).scanHostAlone(hostScanAlone);
    }

    @Override
    public Vm scanVmAlone(VmScanAlone vmScanAlone) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionScan(vmScanAlone.getProviderType()).scanVmAlone(vmScanAlone);
    }

    @Override
    public List<Host> scanAllInOne(AllInOneScan allInOneScan) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionScan(allInOneScan.getProviderType()).scanAllInOne(allInOneScan);
    }

    @Override
    public List<DataStore> scanDataStore(StorageScan storageScan) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionScan(storageScan.getProviderType()).scanDataStore(storageScan);
    }

    @Override
    public List<Cluster> scanCluster(ClusterScan clusterScan) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionScan(clusterScan.getProviderType()).scanCluster(clusterScan);
    }

    @Override
    public List<SimpleHost> scanHost(HostScanByCluster hostScanByCluster) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionScan(hostScanByCluster.getProviderType()).scanHost(hostScanByCluster);
    }

    @Override
    public Network scanNetwork(NetworkScan networkScan) throws CommonAdapterException, AdapterUnavailableException {

        return provider.getProvisionScan(networkScan.getProviderType()).scanNetwork(networkScan);
    }

    @Override
    public List<Uuid> scanUuidByDvs(HostScanByDvs hostScanByDvs) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionScan(hostScanByDvs.getProviderType()).scanUuidByDvs(hostScanByDvs);
    }

    @Override
    public List<Uuid> scanUuidBySvs(HostScanBySvs hostScanBySvs) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionScan(hostScanBySvs.getProviderType()).scanUuidBySvs(hostScanBySvs);
    }

    @Override
    public List<Template> scanTemplate(TemplateScan templateScan) throws CommonAdapterException,
            AdapterUnavailableException {

        return provider.getProvisionScan(templateScan.getProviderType()).scanTemplate(templateScan);
    }

    @Override
    public List<Host> scanHostsByEnv(HostScanByEnv hostScanByEnv)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return provider.getProvisionScan(hostScanByEnv.getProviderType()).scanHostsByEnv(hostScanByEnv);
    }

    @Override
    public List<Host> scanHostWithVmsByEnv(HostScanWithVmsByEnv hostScanByEnv)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return provider.getProvisionScan(hostScanByEnv.getProviderType()).scanHostWithVmsByEnv(hostScanByEnv);
    }

    @Override
    public boolean connectionEnvTest(EnvConnectionTest envConnectionTest)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return provider.getProvisionScan(envConnectionTest.getProviderType()).connectionEnvTest(envConnectionTest);
    }

    @Override
    public KvmStorages scanStorages(KvmStorageScan kvmStorageScan)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionScan(kvmStorageScan.getProviderType()).scanStorages(kvmStorageScan);
    }

    @Override
    public CpuPools scanCpuPools(CpuPoolScan cpuPoolScan)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionScan(cpuPoolScan.getProviderType()).scanCpuPools(cpuPoolScan);
    }

    @Override
    public Mpars scanMpars(MparsScan mparsScan) throws CommonAdapterException,
            AdapterUnavailableException {
        return provider.getProvisionScan(mparsScan.getProviderType()).scanMpars(mparsScan);
    }

    @Override
    public Vswitchs scanVswitchs(VswitchScan vswitchScan)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionScan(vswitchScan.getProviderType()).scanVswitchs(vswitchScan);
    }

    @Override
    public Ios scanIo(IoScan ioScan) throws CommonAdapterException,
            AdapterUnavailableException {
        return provider.getProvisionScan(ioScan.getProviderType()).scanIo(ioScan);
    }

    @Override
    public SSPResult scanSSP(SSPScan sspScan) throws CommonAdapterException,
            AdapterUnavailableException {
        return provider.getProvisionScan(sspScan.getProviderType()).scanSSP(sspScan);
    }

    @Override
    public DataDiskResult scanDataDisk(NpivScan npivSan)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionScan(npivSan.getProviderType()).scanDataDisk(npivSan);
    }

    @Override
    public SysDiskResult scanSysDisk(DiskScan diskScan)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionScan(diskScan.getProviderType()).scanSysDisk(diskScan);
    }

    @Override
    public IOSlotResult scanIoSlots(IOSlotScan ioSlotScan)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionScan(ioSlotScan.getProviderType()).scanIoSlots(ioSlotScan);
    }

}
