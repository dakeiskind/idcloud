package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.Monitor;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareAlarmListQuery;
import com.h3c.idcloud.core.adapter.facade.provision.action.vmware.VmwareAlarmTriggerQuery;
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
public class MonitorVMwareImpl implements Monitor {

    @Autowired
    private VmwareAlarmListQuery vmwareAlarmListQuery;
    @Autowired
    private VmwareAlarmTriggerQuery vmwareAlarmTriggerQuery;

    @Override
    public Node createNode(NodeCreate nodeCreate) throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateNode(NodeUpdate nodeUpdate)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteNode(NodeDelete nodeDelete)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public CpuInfo monitorCpu(NodeCpu nodeCpu) throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CpuInfoHis monitorCpuHis(NodeCpuHis nodeCpuHis)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DiskInfo monitorDisk(NodeDisk nodeDisk)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DiskInfoHis monitorDiskHis(NodeDiskHis nodeDiskHis)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MemInfo monitorMem(NodeMem nodeMem) throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MemInfoHis monitorMemHis(NodeMemHis nodeMemHis)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NodeInfo monitorNode(NodeSummary nodeSummary)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NetInfo monitorNet(NodeNet nodeNet) throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NetInfoHis monitorNetHis(NodeNetHis nodeNetHis)
            throws CommonAdapterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AlarmTriggerVosResult getAlarmTriggers(
            AlarmTriggerGet alarmTriggerGet) throws VmwareException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return (AlarmTriggerVosResult) vmwareAlarmTriggerQuery.invoke(alarmTriggerGet);
    }

    @Override
    public AlarmVosResult getAlarmList(AlarmListGet alarmListGet) throws VmwareException, AdapterUnavailableException {
        // TODO Auto-generated method stub
        return (AlarmVosResult) vmwareAlarmListQuery.invoke(alarmListGet);
    }


}
