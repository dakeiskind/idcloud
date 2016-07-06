package com.hp.tsic.mq.interf;

import com.h3c.idcloud.core.adapter.facade.MonitorHandler;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.CpuInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.CpuInfoHis;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.CpuInfoValue;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.DiskInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.DiskInfoHis;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.MemInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.MemInfoHis;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.NetInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.NetInfoHis;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.Node;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.NodeInfo;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCpu;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCpuHis;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeDisk;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeDiskHis;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeMem;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeMemHis;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeNet;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeNetHis;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeSummary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class VmMonitorHandlerTest {

    @Autowired
    private MonitorHandler monitorHandler;

    public void testNodeCpu() {

        NodeCpu nodeCpu = new NodeCpu();

        nodeCpu.setProviderType("vmware");
        nodeCpu.setNodeId("1");

        try {
            CpuInfo cpuInfo = monitorHandler.monitorCpu(nodeCpu);

            cpuInfo.getNodeId();

            System.out.println(cpuInfo.getAvgUsage());
            System.out.println(cpuInfo.getMaxUsage());
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testNodeSummary() {

        NodeSummary nodeSummary = new NodeSummary();

        nodeSummary.setProviderType("vmware");
        nodeSummary.setNodeId("1");

        try {
            NodeInfo nodeInfo = monitorHandler.monitorNode(nodeSummary);

            System.out.println(nodeInfo.getCpuUsage());
            System.out.println(nodeInfo.getMemFree());
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testNodeCpuHis() {

        NodeCpuHis nodeCpuHis = new NodeCpuHis();

        nodeCpuHis.setProviderType("vmware");
        nodeCpuHis.setNodeId("1");
        nodeCpuHis.setPeriod("20140826000000:20140827000000");

        try {
            CpuInfoHis cpuInfoHis = monitorHandler.monitorCpuHis(nodeCpuHis);

            System.out.println(cpuInfoHis.getAvgUsage());
            System.out.println(cpuInfoHis.getPeroid());

            for (CpuInfoValue value : cpuInfoHis.getValues()) {
                System.out.println(value.getTime() + "--" + value.getValue());
            }

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testNodeNet() {

        NodeNet nodeNet = new NodeNet();

        nodeNet.setProviderType("vmware");
        nodeNet.setNodeId("1");

        try {
            NetInfo netInfo = monitorHandler.monitorNet(nodeNet);

            System.out.println(netInfo.getAvgIn());
            System.out.println(netInfo.getAvgOut());

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testNodeNetHis() {

        NodeNetHis nodeNetHis = new NodeNetHis();

        nodeNetHis.setProviderType("vmware");
        nodeNetHis.setNodeId("1");
        nodeNetHis.setPeriod("20140826000000:20140827000000");

        try {
            NetInfoHis netInfoHis = monitorHandler.monitorNetHis(nodeNetHis);

            System.out.println(netInfoHis.getAvgIn());
            System.out.println(netInfoHis.getAvgOut());
            System.out.println(netInfoHis.getData().size());

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testNodeDisk() {

        NodeDisk nodeDisk = new NodeDisk();

        nodeDisk.setProviderType("vmware");
        nodeDisk.setNodeId("1");

        try {
            DiskInfo diskInfo = monitorHandler.monitorDisk(nodeDisk);

            System.out.println(diskInfo.getAvgUsage());
            System.out.println(diskInfo.getTotal());

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testNodeDiskHis() {

        NodeDiskHis nodeDiskHis = new NodeDiskHis();

        nodeDiskHis.setProviderType("vmware");
        nodeDiskHis.setNodeId("1");
        nodeDiskHis.setPeriod("20140826000000:20140827000000");

        try {
            DiskInfoHis diskInfoHis = monitorHandler
                    .monitorDiskHis(nodeDiskHis);

            System.out.println(diskInfoHis.getAvgUsage());
            System.out.println(diskInfoHis.getTotal());

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testNodeMem() {

        NodeMem nodeMem = new NodeMem();

        nodeMem.setProviderType("vmware");
        nodeMem.setNodeId("1");

        try {
            MemInfo memInfo = monitorHandler.monitorMem(nodeMem);

            System.out.println(memInfo.getAvgFree());
            System.out.println(memInfo.getAvgUsage());

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testNodeMemHis() {

        NodeMemHis nodeMemHis = new NodeMemHis();

        nodeMemHis.setProviderType("vmware");
        nodeMemHis.setNodeId("1");
        nodeMemHis.setPeriod("20140826000000:20140827000000");

        try {
            MemInfoHis memInfoHis = monitorHandler.monitorMemHis(nodeMemHis);
            System.out.println(memInfoHis.getAvgFree());
            System.out.println(memInfoHis.getAvgUsage());
            System.out.println(memInfoHis.getDataUsage().size());

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testNodeCreate() {

        NodeCreate nodeCreate = new NodeCreate();

        nodeCreate.setProviderType("vmware");
        nodeCreate.setProviderUrl("http://192.168.9.239:8080");
        nodeCreate.setChildService("taiji");
        nodeCreate.setParentService("test");
        nodeCreate.setOperate("1");
        nodeCreate.setPrivateIp("8.8.8.8");
        nodeCreate.setPublicIp("6.6.6.6");
        nodeCreate.setOsType("linux");
        nodeCreate.setNodeId("44");

        try {
            Node node = monitorHandler.createNode(nodeCreate);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
