package com.h3c.idcloud.core.adapter.facade;

import com.h3c.idcloud.core.adapter.facade.common.ProviderFactory;
import com.h3c.idcloud.core.adapter.facade.provision.Monitor;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.VmwareException;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.AlarmTriggerVosResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.AlarmVosResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.CpuInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.CpuInfoHis;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.DiskInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.DiskInfoHis;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.MemInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.MemInfoHis;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.NetInfo;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.NetInfoHis;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.Node;
import com.h3c.idcloud.core.adapter.facade.provision.model.monitor.NodeInfo;
import com.h3c.idcloud.core.adapter.pojo.alarm.AlarmListGet;
import com.h3c.idcloud.core.adapter.pojo.alarm.AlarmTriggerGet;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCpu;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCpuHis;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeDisk;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeDiskHis;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeMem;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeMemHis;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeNet;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeNetHis;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeSummary;
import com.h3c.idcloud.core.adapter.pojo.vm.NodeUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorHandler implements Monitor {

    @Autowired
    private ProviderFactory provider;

    public Node createNode(NodeCreate nodeCreate) throws CommonAdapterException {

        return provider.getMonitor(nodeCreate.getProviderType()).createNode(
                nodeCreate);
    }

    public boolean updateNode(NodeUpdate nodeUpdate)
            throws CommonAdapterException {

        return provider.getMonitor(nodeUpdate.getProviderType()).updateNode(
                nodeUpdate);
    }

    public boolean deleteNode(NodeDelete nodeDelete)
            throws CommonAdapterException {

        return provider.getMonitor(nodeDelete.getProviderType()).deleteNode(
                nodeDelete);

    }

    public CpuInfo monitorCpu(NodeCpu nodeCpu) throws CommonAdapterException {

        return provider.getMonitor(nodeCpu.getProviderType()).monitorCpu(
                nodeCpu);
    }

    public CpuInfoHis monitorCpuHis(NodeCpuHis nodeCpuHis)
            throws CommonAdapterException {

        return provider.getMonitor(nodeCpuHis.getProviderType()).monitorCpuHis(
                nodeCpuHis);
    }

    public DiskInfo monitorDisk(NodeDisk nodeDisk)
            throws CommonAdapterException {

        return provider.getMonitor(nodeDisk.getProviderType()).monitorDisk(
                nodeDisk);
    }

    public DiskInfoHis monitorDiskHis(NodeDiskHis nodeDiskHis)
            throws CommonAdapterException {

        return provider.getMonitor(nodeDiskHis.getProviderType())
                .monitorDiskHis(nodeDiskHis);
    }

    public MemInfo monitorMem(NodeMem nodeMem) throws CommonAdapterException {

        return provider.getMonitor(nodeMem.getProviderType()).monitorMem(
                nodeMem);
    }

    public MemInfoHis monitorMemHis(NodeMemHis nodeMemHis)
            throws CommonAdapterException {

        return provider.getMonitor(nodeMemHis.getProviderType()).monitorMemHis(
                nodeMemHis);
    }

    public NodeInfo monitorNode(NodeSummary nodeSummary)
            throws CommonAdapterException {

        return provider.getMonitor(nodeSummary.getProviderType()).monitorNode(
                nodeSummary);
    }

    public NetInfo monitorNet(NodeNet nodeNet) throws CommonAdapterException {

        return provider.getMonitor(nodeNet.getProviderType()).monitorNet(
                nodeNet);
    }

    public NetInfoHis monitorNetHis(NodeNetHis nodeNetHis)
            throws CommonAdapterException {

        return provider.getMonitor(nodeNetHis.getProviderType()).monitorNetHis(
                nodeNetHis);
    }

    @Override
    public AlarmTriggerVosResult getAlarmTriggers(
            AlarmTriggerGet alarmTriggerGet) throws VmwareException,
            AdapterUnavailableException {
        // TODO Auto-generated method stub
        return provider.getMonitor(alarmTriggerGet.getProviderType()).getAlarmTriggers(alarmTriggerGet);
    }

    @Override
    public AlarmVosResult getAlarmList(AlarmListGet alarmListGet)
            throws VmwareException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return provider.getMonitor(alarmListGet.getProviderType()).getAlarmList(alarmListGet);
    }
}
