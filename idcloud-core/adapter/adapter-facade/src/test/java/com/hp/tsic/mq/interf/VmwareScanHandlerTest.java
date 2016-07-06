package com.hp.tsic.mq.interf;

import com.h3c.idcloud.core.adapter.facade.ScanHandler;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Cluster;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.DataStore;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Host;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Network;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.SimpleHost;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Uuid;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Vm;
import com.h3c.idcloud.core.adapter.pojo.scan.AllInOneScan;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScan;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByCluster;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByDvs;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanBySvs;
import com.h3c.idcloud.core.adapter.pojo.scan.NetworkScan;
import com.h3c.idcloud.core.adapter.pojo.scan.StorageScan;
import com.h3c.idcloud.core.adapter.pojo.scan.TemplateScan;
import com.h3c.idcloud.core.adapter.pojo.scan.VmScanAlone;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class VmwareScanHandlerTest {

    @Autowired
    private ScanHandler scanHandler;

    public void testVmScanAlone() {

        VmScanAlone vmScanAlone = new VmScanAlone();

        vmScanAlone.setProviderType("vmware");
        vmScanAlone.setProviderUrl("https://10.125.188.242:443/sdk/");
        vmScanAlone.setAuthUser("administrator");
        vmScanAlone.setAuthPass("Hp1nvent");
        vmScanAlone.setUuid("50134e8d-082b-6c3d-7466-6cb534aca344");

        Vm vm = null;

        try {
            vm = scanHandler.scanVmAlone(vmScanAlone);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertEquals("test133", vm.getVmName());

    }


    public void testHostScanAlone() {

        HostScanAlone hostScanAlone = new HostScanAlone();

        hostScanAlone.setProviderType("vmware");
        hostScanAlone.setProviderUrl("https://10.125.188.242:443/sdk/");
        hostScanAlone.setAuthUser("administrator");
        hostScanAlone.setAuthPass("Hp1nvent");
        hostScanAlone.setName("10.125.188.24");

        Host host = null;

        try {
            host = scanHandler.scanHostAlone(hostScanAlone);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(host.getVms().size());
        Assert.assertEquals("10.125.188.24", host.getHostName());

    }

    public void testClusterScanAlone() {

        ClusterScanAlone clusterScanAlone = new ClusterScanAlone();

        clusterScanAlone.setProviderType("vmware");
        clusterScanAlone.setProviderUrl("https://10.125.188.242:443/sdk/");
        clusterScanAlone.setAuthUser("administrator");
        clusterScanAlone.setAuthPass("Hp1nvent");
        clusterScanAlone.setName("klb");

        Cluster cluster = null;

        try {
            cluster = scanHandler.scanClusterAlone(clusterScanAlone);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(cluster.getResTopologyName());

    }

    public void testNetworkScan() {

        NetworkScan networkScan = new NetworkScan();

        networkScan.setProviderType("vmware");
        networkScan.setProviderUrl("https://10.125.188.242:443/sdk/");
        networkScan.setAuthUser("administrator");
        networkScan.setAuthPass("Hp1nvent");

        Network network = null;

        try {
            network = scanHandler.scanNetwork(networkScan);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(network.getvSwitchs().size());

    }

    public void testHostScanByCluster() {

        HostScanByCluster hostScanByCluster = new HostScanByCluster();

        hostScanByCluster.setProviderType("vmware");
        hostScanByCluster.setProviderUrl("https://10.125.188.242:443/sdk/");
        hostScanByCluster.setAuthUser("administrator");
        hostScanByCluster.setAuthPass("Hp1nvent");
        hostScanByCluster.setCluster("klb");

        List<SimpleHost> hosts = null;

        try {
            hosts = scanHandler.scanHost(hostScanByCluster);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(hosts.size());

        for (SimpleHost simpleHost : hosts) {
            System.out.println(simpleHost.getUuid());
            System.out.println(simpleHost.getHostName());
        }

    }

    public void testStorageScan() {

        StorageScan storageScan = new StorageScan();

        storageScan.setProviderType("vmware");
        storageScan.setProviderUrl("https://10.125.188.242:443/sdk/");
        storageScan.setAuthUser("administrator");
        storageScan.setAuthPass("Hp1nvent");

        List<DataStore> dataStores = null;

        try {
            dataStores = scanHandler.scanDataStore(storageScan);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(dataStores.size());

    }

    public void testClusterScan() {

        ClusterScan clusterScan = new ClusterScan();

        clusterScan.setProviderType("vmware");
        clusterScan.setProviderUrl("https://10.125.188.242:443/sdk/");
        clusterScan.setAuthUser("administrator");
        clusterScan.setAuthPass("Hp1nvent");

        List<Cluster> clusters = null;

        try {
            clusters = scanHandler.scanCluster(clusterScan);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(clusters.size());

    }

    @Test
    public void testAllInOneScan() {

        AllInOneScan allInOneScan = new AllInOneScan();

        allInOneScan.setProviderType("VMware");
        allInOneScan.setProviderUrl("https://18.5.197.151/sdk/");
        allInOneScan.setAuthUser("administrator@vsphere.local");
        allInOneScan.setAuthPass("P@ssw0rd");

        List<Host> hosts = null;

        try {
            hosts = scanHandler.scanAllInOne(allInOneScan);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(hosts.size());

    }

    public void testHostScanByDvs() {

        HostScanByDvs hostScanByDvs = new HostScanByDvs();

        hostScanByDvs.setProviderType("vmware");
        hostScanByDvs.setProviderUrl("https://10.125.188.242:443/sdk/");
        hostScanByDvs.setAuthUser("administrator");
        hostScanByDvs.setAuthPass("Hp1nvent");
        hostScanByDvs.setDvsName("dvSwitch3333");

        List<Uuid> uuids = null;

        try {
            uuids = scanHandler.scanUuidByDvs(hostScanByDvs);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(uuids.size());

    }

    public void testHostScanBySvs() {

        HostScanBySvs hostScanBySvs = new HostScanBySvs();

        hostScanBySvs.setProviderType("vmware");
        hostScanBySvs.setProviderUrl("https://192.168.7.3:443/sdk/");
        hostScanBySvs.setAuthUser("hpadmin");
        hostScanBySvs.setAuthPass("Hp1nvent");
        hostScanBySvs.setSvsName("VM Network");

        List<Uuid> uuids = null;

        try {
            uuids = scanHandler.scanUuidBySvs(hostScanBySvs);

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(uuids.size());

    }

    //	@Test
    public void testTempalteScan() throws InterruptedException {
        while (true) {
            Thread.sleep(10 * 1000);
            TemplateScan templateScan = new TemplateScan();

            templateScan.setProviderType("Vmware");
            templateScan.setProviderUrl("https://18.5.197.151:443/sdk/");
            templateScan.setAuthUser("administrator@vsphere.local");
            templateScan.setAuthPass("P@ssw0rd");

            List<Template> lst = null;

            try {
                lst = scanHandler.scanTemplate(templateScan);
                System.out.println("111");
            } catch (CommonAdapterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (AdapterUnavailableException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println(lst.size());
            Thread.sleep(480 * 1000);
        }
    }
}
