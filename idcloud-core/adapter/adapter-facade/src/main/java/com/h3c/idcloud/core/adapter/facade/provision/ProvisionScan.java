package com.h3c.idcloud.core.adapter.facade.provision;

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

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProvisionScan {

    Cluster scanClusterAlone(ClusterScanAlone clusterScanAlone) throws CommonAdapterException,
            AdapterUnavailableException;

    Host scanHostAlone(HostScanAlone hostScanAlone) throws CommonAdapterException, AdapterUnavailableException;

    Vm scanVmAlone(VmScanAlone vmScanAlone) throws CommonAdapterException, AdapterUnavailableException;

    List<Host> scanAllInOne(AllInOneScan allInOneScan) throws CommonAdapterException, AdapterUnavailableException;

    List<DataStore> scanDataStore(StorageScan storageScan) throws CommonAdapterException, AdapterUnavailableException;

    List<Cluster> scanCluster(ClusterScan clusterScan) throws CommonAdapterException, AdapterUnavailableException;

    List<SimpleHost> scanHost(HostScanByCluster hostScanByCluster) throws CommonAdapterException, AdapterUnavailableException;

    Network scanNetwork(NetworkScan networkScan) throws CommonAdapterException, AdapterUnavailableException;

    List<Uuid> scanUuidByDvs(HostScanByDvs hostScanByDvs) throws CommonAdapterException, AdapterUnavailableException;

    List<Uuid> scanUuidBySvs(HostScanBySvs hostScanBySvs) throws CommonAdapterException, AdapterUnavailableException;

    List<Template> scanTemplate(TemplateScan templateScan) throws CommonAdapterException, AdapterUnavailableException;

    List<Host> scanHostsByEnv(HostScanByEnv hostScanByEnv) throws CommonAdapterException, AdapterUnavailableException;

    List<Host> scanHostWithVmsByEnv(HostScanWithVmsByEnv hostScanByEnv) throws CommonAdapterException, AdapterUnavailableException;

    boolean connectionEnvTest(EnvConnectionTest envConnectionTest) throws CommonAdapterException, AdapterUnavailableException;

    KvmStorages scanStorages(KvmStorageScan kvmStorageScan) throws CommonAdapterException, AdapterUnavailableException;

    CpuPools scanCpuPools(CpuPoolScan cpuPoolScan) throws CommonAdapterException, AdapterUnavailableException;

    Mpars scanMpars(MparsScan mparsScan) throws CommonAdapterException, AdapterUnavailableException;

    Vswitchs scanVswitchs(VswitchScan vswitchScan) throws CommonAdapterException, AdapterUnavailableException;

    Ios scanIo(IoScan ioScan) throws CommonAdapterException, AdapterUnavailableException;

    SSPResult scanSSP(SSPScan sspScan) throws CommonAdapterException, AdapterUnavailableException;

    DataDiskResult scanDataDisk(NpivScan npivSan) throws CommonAdapterException, AdapterUnavailableException;

    SysDiskResult scanSysDisk(DiskScan diskScan) throws CommonAdapterException, AdapterUnavailableException;

    IOSlotResult scanIoSlots(IOSlotScan ioSlotScan) throws CommonAdapterException, AdapterUnavailableException;
}	
