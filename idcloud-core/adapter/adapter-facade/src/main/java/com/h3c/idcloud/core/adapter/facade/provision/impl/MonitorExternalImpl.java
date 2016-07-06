package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.Monitor;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeCpu;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeCpuHis;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeDisk;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeDiskHis;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeMem;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeMemHis;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeNet;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeNetHis;
import com.h3c.idcloud.core.adapter.facade.provision.action.monitor.external.ExternalNodeSummary;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
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
public class MonitorExternalImpl implements Monitor {

    @Autowired
    private ExternalNodeCpu externalNodeCpu;

    @Autowired
    private ExternalNodeCpuHis externalNodeCpuHis;

    @Autowired
    private ExternalNodeMem externalNodeMem;

    @Autowired
    private ExternalNodeMemHis externalNodeMemHis;

    @Autowired
    private ExternalNodeNet externalNodeNet;

    @Autowired
    private ExternalNodeNetHis externalNodeNetHis;

    @Autowired
    private ExternalNodeDisk externalNodeDisk;

    @Autowired
    private ExternalNodeDiskHis externalNodeDiskHis;

    @Autowired
    private ExternalNodeSummary externalNodeSummary;

    public Node createNode(NodeCreate nodeCreate) throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean updateNode(NodeUpdate nodeUpdate)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean deleteNode(NodeDelete nodeDelete)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return false;
    }

    public CpuInfo monitorCpu(NodeCpu nodeCpu) throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeCpu
                .invoke(nodeCpu);

        return (CpuInfo) result;
    }

    public CpuInfoHis monitorCpuHis(NodeCpuHis nodeCpuHis)
            throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeCpuHis
                .invoke(nodeCpuHis);

        return (CpuInfoHis) result;
    }

    public DiskInfo monitorDisk(NodeDisk nodeDisk)
            throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeDisk
                .invoke(nodeDisk);

        return (DiskInfo) result;
    }

    public DiskInfoHis monitorDiskHis(NodeDiskHis nodeDiskHis)
            throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeDiskHis
                .invoke(nodeDiskHis);

        return (DiskInfoHis) result;
    }

    public MemInfo monitorMem(NodeMem nodeMem) throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeMem
                .invoke(nodeMem);

        return (MemInfo) result;
    }

    public MemInfoHis monitorMemHis(NodeMemHis nodeMemHis)
            throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeMemHis
                .invoke(nodeMemHis);

        return (MemInfoHis) result;
    }

    public NodeInfo monitorNode(NodeSummary nodeSummary)
            throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeSummary
                .invoke(nodeSummary);

        return (NodeInfo) result;
    }

    public NetInfo monitorNet(NodeNet nodeNet) throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeNet
                .invoke(nodeNet);

        return (NetInfo) result;
    }

    public NetInfoHis monitorNetHis(NodeNetHis nodeNetHis)
            throws CommonAdapterException {

        CommonAdapterResult result = (CommonAdapterResult) externalNodeNetHis
                .invoke(nodeNetHis);

        return (NetInfoHis) result;
    }

    @Override
    public AlarmTriggerVosResult getAlarmTriggers(
            AlarmTriggerGet alarmTriggerGet) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AlarmVosResult getAlarmList(AlarmListGet alarmListGet) {
        // TODO Auto-generated method stub
        return null;
    }

}
