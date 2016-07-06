package com.h3c.idcloud.core.adapter.facade.provision;

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

import org.springframework.stereotype.Service;

@Service
public interface Monitor {

    Node createNode(NodeCreate nodeCreate) throws CommonAdapterException;

    boolean updateNode(NodeUpdate nodeUpdate) throws CommonAdapterException;

    boolean deleteNode(NodeDelete nodeDelete) throws CommonAdapterException;

    CpuInfo monitorCpu(NodeCpu nodeCpu) throws CommonAdapterException;

    CpuInfoHis monitorCpuHis(NodeCpuHis nodeCpuHis) throws CommonAdapterException;

    DiskInfo monitorDisk(NodeDisk nodeDisk) throws CommonAdapterException;

    DiskInfoHis monitorDiskHis(NodeDiskHis nodeDiskHis) throws CommonAdapterException;

    MemInfo monitorMem(NodeMem nodeMem) throws CommonAdapterException;

    MemInfoHis monitorMemHis(NodeMemHis nodeMemHis) throws CommonAdapterException;

    NodeInfo monitorNode(NodeSummary nodeSummary) throws CommonAdapterException;

    NetInfo monitorNet(NodeNet nodeNet) throws CommonAdapterException;

    NetInfoHis monitorNetHis(NodeNetHis nodeNetHis) throws CommonAdapterException;

    /***
     * 获取定义的告警规则
     */
    AlarmTriggerVosResult getAlarmTriggers(AlarmTriggerGet alarmTriggerGet) throws VmwareException, AdapterUnavailableException;

    /***
     * 获取已经触发的告警信息
     */
    AlarmVosResult getAlarmList(AlarmListGet alarmListGet) throws VmwareException, AdapterUnavailableException;
}
