package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareAllInOneScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareClusterScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareClusterScanAlone;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareEnvTest;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareHostScanAlone;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareHostScanByCluster;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareHostScanByDvs;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareHostScanBySvs;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareNetworkScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareStorageScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareTemplateScan;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareVmScanAlone;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Cluster;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Clusters;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.CpuPools;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataStore;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataStores;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Hosts;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.IOSlotResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Ios;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Mpars;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Network;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SSPResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SimpleHost;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SimpleHosts;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SysDiskResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Templates;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Uuid;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Uuids;
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

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvisionScanVmwareImpl implements ProvisionScan {

    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(ProvisionScanVmwareImpl.class);
    @Autowired
    private VmwareAllInOneScan vmwareAllInOneScan;
    @Autowired
    private VmwareClusterScan vmwareClusterScan;
    @Autowired
    private VmwareClusterScanAlone vmwareClusterScanAlone;
    @Autowired
    private VmwareHostScanAlone vmwareHostScanAlone;
    @Autowired
    private VmwareHostScanByDvs vmwareHostScanByDvs;
    @Autowired
    private VmwareHostScanBySvs vmwareHostScanBySvs;
    @Autowired
    private VmwareNetworkScan vmwareNetworkScan;
    @Autowired
    private VmwareStorageScan vmwareStorageScan;
    @Autowired
    private VmwareVmScanAlone vmwareVmScanAlone;
    @Autowired
    private VmwareHostScanByCluster vmwareHostScanByCluster;
    @Autowired
    private VmwareTemplateScan vmwareTemplateScan;
    @Autowired
    private VmwareEnvTest vmwareEnvTest;
    private List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();

    public ProvisionScanVmwareImpl() {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        providers.add(jsonProvider);
    }

    @Override
    public Cluster scanClusterAlone(ClusterScanAlone clusterScanAlone) throws CommonAdapterException,
            AdapterUnavailableException {

        Cluster cluster = (Cluster) vmwareClusterScanAlone.invoke(clusterScanAlone);

        return cluster;
    }

    @Override
    public Host scanHostAlone(HostScanAlone hostScanAlone) throws CommonAdapterException, AdapterUnavailableException {

        Host host = (Host) vmwareHostScanAlone.invoke(hostScanAlone);

        return host;
    }

    @Override
    public Vm scanVmAlone(VmScanAlone vmScanAlone) throws CommonAdapterException, AdapterUnavailableException {

        Vm vm = (Vm) vmwareVmScanAlone.invoke(vmScanAlone);

        return vm;
    }

    @Override
    public List<Host> scanAllInOne(AllInOneScan allInOneScan) throws CommonAdapterException,
            AdapterUnavailableException {

        Hosts hosts = (Hosts) vmwareAllInOneScan.invoke(allInOneScan);
        return hosts.getListdata();
    }

    @Override
    public List<DataStore> scanDataStore(StorageScan storageScan) throws CommonAdapterException,
            AdapterUnavailableException {

        DataStores dataStores = (DataStores) vmwareStorageScan.invoke(storageScan);
        return dataStores.getListdata();
    }

    @Override
    public List<Cluster> scanCluster(ClusterScan clusterScan) throws CommonAdapterException,
            AdapterUnavailableException {

        Clusters clusters = (Clusters) vmwareClusterScan.invoke(clusterScan);
        return clusters.getListdata();
    }

    @Override
    public List<SimpleHost> scanHost(HostScanByCluster hostScanByCluster) throws CommonAdapterException,
            AdapterUnavailableException {

        SimpleHosts hosts = (SimpleHosts) vmwareHostScanByCluster.invoke(hostScanByCluster);
        return hosts.getListdata();
    }

    @Override
    public Network scanNetwork(NetworkScan networkScan) throws CommonAdapterException, AdapterUnavailableException {

        Network network = (Network) vmwareNetworkScan.invoke(networkScan);

        return network;
    }

    @Override
    public List<Uuid> scanUuidByDvs(HostScanByDvs hostScanByDvs) throws CommonAdapterException,
            AdapterUnavailableException {

        Uuids uuids = (Uuids) vmwareHostScanByDvs.invoke(hostScanByDvs);
        return uuids.getListdata();

    }

    @Override
    public List<Uuid> scanUuidBySvs(HostScanBySvs hostScanBySvs) throws CommonAdapterException,
            AdapterUnavailableException {

        Uuids uuids = (Uuids) vmwareHostScanBySvs.invoke(hostScanBySvs);
        return uuids.getListdata();

    }

    @Override
    public List<Template> scanTemplate(TemplateScan templateScan) throws CommonAdapterException,
            AdapterUnavailableException {

        Templates templates = (Templates) vmwareTemplateScan.invoke(templateScan);
        return templates.getListdata();
    }

    @Override
    public List<Host> scanHostsByEnv(HostScanByEnv hostScanByEnv)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Host> scanHostWithVmsByEnv(HostScanWithVmsByEnv hostScanByEnv)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean connectionEnvTest(EnvConnectionTest envConnectionTest)
            throws CommonAdapterException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return vmwareEnvTest.invoke(envConnectionTest).isSuccess();
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
