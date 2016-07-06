package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.other.RegisterEnv;
import com.h3c.idcloud.core.adapter.pojo.other.UnRegisterEnv;
import com.h3c.idcloud.core.adapter.pojo.scan.AllInOneScan;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScan;
import com.h3c.idcloud.core.adapter.pojo.scan.CpuPoolScan;
import com.h3c.idcloud.core.adapter.pojo.scan.DiskScan;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByCluster;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByDvs;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanByEnv;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanBySvs;
import com.h3c.idcloud.core.adapter.pojo.scan.IOSlotScan;
import com.h3c.idcloud.core.adapter.pojo.scan.IoScan;
import com.h3c.idcloud.core.adapter.pojo.scan.MparsScan;
import com.h3c.idcloud.core.adapter.pojo.scan.NetworkScan;
import com.h3c.idcloud.core.adapter.pojo.scan.NpivScan;
import com.h3c.idcloud.core.adapter.pojo.scan.SSPScan;
import com.h3c.idcloud.core.adapter.pojo.scan.StorageScan;
import com.h3c.idcloud.core.adapter.pojo.scan.ViosVo;
import com.h3c.idcloud.core.adapter.pojo.scan.VswitchScan;
import com.h3c.idcloud.core.adapter.pojo.scan.result.AllInOneScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.ClusterScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.CpuPoolScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.DiskScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanByClusterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanByDvsResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanByEnvResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanBySvsResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.IOSlotScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.IoScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.MparsScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.NetworkScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.NpivScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.SSPScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.StorageScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.VswitchScanResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.ClusterVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.CpuPoolVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataDiskVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataStoreVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DvSwitchVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostNameVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IOSlotVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IoVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.MparVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.SSPVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.SysDiskVo;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.UuidVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.VSwitchVO;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.ViosResult;
import com.h3c.idcloud.core.persist.res.dao.ResCpuPoolMapper;
import com.h3c.idcloud.core.persist.res.dao.ResCpuPoolMparRelMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostItemMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResNetworkHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResPoolVlanVsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResStorageViosRelMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVfcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResViosMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsPortGroupMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResCpuPool;
import com.h3c.idcloud.core.pojo.dto.res.ResCpuPoolMparRel;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResHostItem;
import com.h3c.idcloud.core.pojo.dto.res.ResHostStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResIp;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResPoolVlanVs;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResStorageViosRel;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVfc;
import com.h3c.idcloud.core.pojo.dto.res.ResVios;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVs;
import com.h3c.idcloud.core.pojo.dto.res.ResVsHost;
import com.h3c.idcloud.core.pojo.dto.res.ResVsPortGroup;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.LicenseResult;
import com.h3c.idcloud.core.service.product.api.ResInfoSync;
import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.res.provider.task.ResVeSyncTask;
import com.h3c.idcloud.core.service.system.api.LicenseService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RESTHttpResponse;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.RSETClientUtil;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.activiti.engine.impl.util.json.JSONException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Res ve service 类.
 *
 * @author Chaohong.Mao
 */
@Service(version = "1.0.0")
@Component
public class ResVeServiceImpl implements ResVeService {

    /**
     * 静态变量 logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ResVeServiceImpl.class);

    @Reference(version = "1.0.0")
    private ResInfoSync resInfoSync;

    @Autowired
    private ResVmService resVmService;
    @Autowired
    private ResHostService resHostService;
    @Autowired
    private ResVeMapper resveMapper;
    @Autowired
    private ResVcMapper resVcMapper;
    @Autowired
    private ResHostMapper resHostMapper;
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private ResStorageMapper resStorageMapper;
    @Autowired
    private ResTopologyMapper resTopologyMapper;
    @Autowired
    private ResVmNetworkMapper resVmNetworkMapper;
    @Autowired
    private ResVdMapper resVdMapper;
    @Autowired
    private ResHostStorageMapper resHostStorageMapper;
    @Autowired
    private ResIpMapper resIpMapper;
    @Autowired
    private ResVsMapper resVsMapper;
    @Autowired
    private ResVsHostMapper resVsHostMapper;
    @Autowired
    private ResVsPortGroupMapper resVsPortGroupMapper;
    @Autowired
    private ResCpuPoolMapper resCpuPoolMapper;
    @Autowired
    private ResCpuPoolMparRelMapper resCpuPoolMparRelMapper;
    @Autowired
    private ResViosMapper resViosMapper;
    @Autowired
    private ResHostItemMapper resHostItemMapper;
    @Autowired
    private ResStorageViosRelMapper resStorageViosRelMapper;
    @Autowired
    private ResVfcMapper resVfcMapper;
    @Autowired
    private ResPoolVlanVsMapper resPoolVlanVsMapper;
    @Autowired
    private ResNetworkHostMapper resNetworkHostMapper;
    @Reference(version = "1.0.0")
    private LicenseService licenseService;
    @Autowired
    private TaskLoggerFactory taskLogger;

    /**
     * 查询Ve列表
     *
     * @param criteria
     *
     * @return
     */
    @Override
    public List<ResVe> selectByParams(Criteria criteria) {
        return this.resveMapper.selectByParams(criteria);
    }

    /**
     * 根据Sid查询资源环境信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResVe selectByPrimaryKey(String resTopologySid) {
        return this.resveMapper.selectByPrimaryKey(resTopologySid);
    }

    public int deleteByPrimaryKey(String resTopologySid) {

		/* 1.集群 ---> 主机--------->虚拟机
                      |            |
	                  |             —— vd
	                  |
	                  | —— 主机设备(hostItem)、          vios、          cpuPool
	                  |                          |         |
	                  |                          |         |
	                  |                          |          ——RES_CPU_POOL_MPAR_REL
	                  |——  RES_VS_HOST           |
	                  |     	                  ——RES_STORAGE_VIOS_REL
	                   ——  RES_NETWORK_HOST
			2.存储
			|
			 ——res_storage_vios_rel
			3.vs
			|
			 ——res_vs_host
			|
			 —— res_vs_port_group
	   */
        Criteria example = new Criteria();
        example.put("findChildBySid", resTopologySid);
        example.put("resTopologyType", "VC");
        List<ResTopology> vcList = this.resTopologyMapper.selectByParams(example);
        // 查询集群
        if (vcList.size() > 0) {
            // 检查集群是否存在关联池的
            for (ResTopology res : vcList) {
                ResVc vc = this.resVcMapper.selectByPrimaryKey(res.getResTopologySid());
                if (!StringUtil.isNullOrEmpty(vc.getResPoolSid())) {
                    return 0;
                }
            }
            // 查询集群下面的主机
            for (ResTopology res : vcList) {
                Criteria exam = new Criteria();
                exam.put("parentTopologySid", res.getResTopologySid());
                List<ResHost> hostList = this.resHostMapper.selectByParamsForScan(exam);
                for (ResHost host : hostList) {
                    if (!StringUtil.isNullOrEmpty(host.getResPoolSid())) {
                        return 0;
                    }
                }
            }
        }

        // 检查存储
        Criteria sxam = new Criteria();
        sxam.put("parentTopologySid", resTopologySid);
        List<ResStorage> Slist = this.resStorageMapper.selectByParams(sxam);
        for (ResStorage res : Slist) {
            if (!StringUtil.isNullOrEmpty(res.getResPoolSid())) {
                return 0;
            }
        }

        // 检查vs是否和池关联了
        Criteria vsam = new Criteria();
        vsam.put("parentTopologySid", resTopologySid);
        List<ResVs> vsList = this.resVsMapper.selectByParams(vsam);
        for (ResVs res : vsList) {
            Criteria vsPoolam = new Criteria();
            vsPoolam.put("resVsSid", res.getResVsSid());
            List<ResPoolVlanVs> list = this.resPoolVlanVsMapper.selectByParams(vsPoolam);
            if (list.size() > 0) {
                return 0;
            }
        }

        // 检查ve下的主机
        Criteria hostSelect = new Criteria();
        hostSelect.put("parentTopologySid", resTopologySid);
        List<ResHost> hostListInVe = this.resHostMapper.selectByParams(hostSelect);
        for (ResHost host : hostListInVe) {
            if (!StringUtil.isNullOrEmpty(host.getResPoolSid())) {
                return 0;
            }
        }

        // 删除
        if (vcList.size() > 0) {

            // 删除主机下的相关信息
            for (ResTopology res : vcList) {
                Criteria exam = new Criteria();
                exam.put("parentTopologySid", res.getResTopologySid());
                List<ResHost> hostList = this.resHostMapper.selectByParamsForScan(exam);
                if (hostList.size() > 0) {
                    for (ResHost host : hostList) {
                        // 查询下面的虚拟机
                        Criteria vm = new Criteria();
                        vm.put("allocateResHostSid", host.getResHostSid());
                        List<ResVm> vmList = this.resVmMapper.selectByParams(vm);
                        // 删除虚拟机关联的信息
                        for (ResVm resvm : vmList) {

                            // 删除虚拟机与网络关联数据
                            Criteria delete1 = new Criteria();
                            delete1.put("resVmSidDelete", resvm.getResVmSid());
                            this.resVmNetworkMapper.deleteByParams(delete1);

                            // 删除虚拟机关联res_vd
                            delete1 = new Criteria();
                            delete1.put("resVmSid", resvm.getResVmSid());
                            this.resVdMapper.deleteByParams(example);

                            // 删除虚拟机
                            this.resVmMapper.deleteByPrimaryKey(resvm.getResVmSid());
                        }

                        // 删除主机与存储数据
                        Criteria hostStorage = new Criteria();
                        hostStorage.put("resHostSidDelete", host.getResHostSid());
                        this.resHostStorageMapper.deleteByParams(hostStorage);

                        // 删除主机设备(hostItem)
                        Criteria relationDel = new Criteria();
                        relationDel.put("resHostSidDel", host.getResHostSid());
                        this.resHostItemMapper.deleteByParams(relationDel);

                        // 删除vios ——> res_storage_vios_rel
                        Criteria relationSelect = new Criteria();
                        relationSelect.put("resHostSid", host.getResHostSid());
                        List<ResVios> viosList = this.resViosMapper.selectByParams(relationSelect);
                        for (ResVios vios : viosList) {
                            // 删除res_storage_vios_rel
                            relationDel = new Criteria();
                            relationDel.put("resViosSid", vios.getResViosSid());
                            this.resStorageViosRelMapper.deleteByParams(relationDel);

                            // 删除vios
                            this.resViosMapper.deleteByPrimaryKey(vios.getResViosSid());
                        }

                        // 删除cpuPool ——> res_cpu_pool_mpar_rel
                        relationSelect = new Criteria();
                        relationSelect.put("resHostSid", host.getResHostSid());
                        List<ResCpuPool> cpuPoolList = this.resCpuPoolMapper.selectByParams(relationSelect);
                        for (ResCpuPool cpuPool : cpuPoolList) {

                            // 删除res_cpu_pool_mpar_rel
                            relationDel = new Criteria();
                            relationDel.put("resCpuPoolSid", cpuPool.getResCpuPoolSid());
                            this.resCpuPoolMparRelMapper.deleteByParams(relationDel);

                            // 删除cpuPool
                            this.resCpuPoolMapper.deleteByPrimaryKey(cpuPool.getResCpuPoolSid());
                        }
                        // 删除res_vs_host
                        relationDel = new Criteria();
                        relationDel.put("resHostSid", host.getResHostSid());
                        this.resVsHostMapper.deleteByParams(relationDel);

                        // 删除res_network_host
                        relationDel = new Criteria();
                        relationDel.put("resHostSid", host.getResHostSid());
                        this.resNetworkHostMapper.deleteByParams(relationDel);


                        // 删除主机
                        this.resHostMapper.deleteByPrimaryKey(host.getResHostSid());
                    }
                }
            }

            // 删除集群
            for (ResTopology res : vcList) {
                this.resTopologyMapper.deleteByPrimaryKey(res.getResTopologySid());
                this.resVcMapper.deleteByPrimaryKey(res.getResTopologySid());
            }
        }


        // 删除直接VE下的主机
        for (ResHost host : hostListInVe) {
            // 查询下面的虚拟机
            Criteria vm = new Criteria();
            vm.put("allocateResHostSid", host.getResHostSid());
            List<ResVm> vmList = this.resVmMapper.selectByParams(vm);
            // 删除虚拟机关联的信息
            for (ResVm resvm : vmList) {

                // 删除虚拟机与网络关联数据
                Criteria delete1 = new Criteria();
                delete1.put("resVmSidDelete", resvm.getResVmSid());
                this.resVmNetworkMapper.deleteByParams(delete1);

                // 删除虚拟机关联res_vd
                delete1 = new Criteria();
                delete1.put("resVmSid", resvm.getResVmSid());
                this.resVdMapper.deleteByParams(example);

                // 删除虚拟机
                this.resVmMapper.deleteByPrimaryKey(resvm.getResVmSid());
            }

            // 删除主机与存储数据
            Criteria hostStorage = new Criteria();
            hostStorage.put("resHostSidDelete", host.getResHostSid());
            this.resHostStorageMapper.deleteByParams(hostStorage);

            // 删除主机设备(hostItem)
            Criteria relationDel = new Criteria();
            relationDel.put("resHostSidDel", host.getResHostSid());
            this.resHostItemMapper.deleteByParams(relationDel);

            // 删除vios ——> res_storage_vios_rel
            Criteria relationSelect = new Criteria();
            relationSelect.put("resHostSid", host.getResHostSid());
            List<ResVios> viosList = this.resViosMapper.selectByParams(relationSelect);
            for (ResVios vios : viosList) {
                // 删除res_storage_vios_rel
                relationDel = new Criteria();
                relationDel.put("resViosSid", vios.getResViosSid());
                this.resStorageViosRelMapper.deleteByParams(relationDel);

                // 删除vios
                this.resViosMapper.deleteByPrimaryKey(vios.getResViosSid());
            }

            // 删除cpuPool ——> res_cpu_pool_mpar_rel
            relationSelect = new Criteria();
            relationSelect.put("resHostSid", host.getResHostSid());
            List<ResCpuPool> cpuPoolList = this.resCpuPoolMapper.selectByParams(relationSelect);
            for (ResCpuPool cpuPool : cpuPoolList) {

                // 删除res_cpu_pool_mpar_rel
                relationDel = new Criteria();
                relationDel.put("resCpuPoolSid", cpuPool.getResCpuPoolSid());
                this.resCpuPoolMparRelMapper.deleteByParams(relationDel);

                // 删除cpuPool
                this.resCpuPoolMapper.deleteByPrimaryKey(cpuPool.getResCpuPoolSid());
            }
            // 删除res_vs_host
            relationDel = new Criteria();
            relationDel.put("resHostSid", host.getResHostSid());
            this.resVsHostMapper.deleteByParams(relationDel);

            // 删除res_network_host
            relationDel = new Criteria();
            relationDel.put("resHostSid", host.getResHostSid());
            this.resNetworkHostMapper.deleteByParams(relationDel);

            // 删除主机
            this.resHostMapper.deleteByPrimaryKey(host.getResHostSid());
        }

        // 删除存储
        for (ResStorage res : Slist) {
            // 删除res_storage_vios_rel
            Criteria relationDel = new Criteria();
            relationDel.put("resStorageSid", res.getResStorageSid());
            this.resStorageViosRelMapper.deleteByParams(relationDel);

            // 删除存储
            this.resStorageMapper.deleteByPrimaryKey(res.getResStorageSid());
        }

        // 删除vs
        for (ResVs res : vsList) {
            // 删除res_vs_host
            Criteria relationDel = new Criteria();
            relationDel.put("resVsSid", res.getResVsSid());
            this.resVsHostMapper.deleteByParams(relationDel);

            // 删除res_vs_port_group
            relationDel = new Criteria();
            relationDel.put("resVsSid", res.getResVsSid());
            this.resVsPortGroupMapper.deleteByParams(relationDel);

            // res_vs
            this.resVsMapper.deleteByPrimaryKey(res.getResVsSid());
        }

        // 删除ve的task
        ResVe ve = this.resveMapper.selectByPrimaryKey(resTopologySid);
        ResVeSyncTask resVeSyncTask = SpringContextHolder.getBean("resVeSyncTask");
        resVeSyncTask.cancelTask(ve.getTaskId());

        // 删除Topology表中的ve
        this.resTopologyMapper.deleteByPrimaryKey(resTopologySid);

        return this.resveMapper.deleteByPrimaryKey(resTopologySid);
    }

    public int updateByPrimaryKeySelective(ResVe record) {
        int result = 0;
        WebUtil.prepareUpdateParams(record);
        result = this.resTopologyMapper.updateByPrimaryKeySelective(record);
        if (1 == result) {
            return this.resveMapper.updateByPrimaryKeySelective(record);
        } else {
            return result;
        }

    }

    public int insertSelective(ResVe record) {
        int result = 0;
        try {
            // 新增Topology主表
            int resultRes = this.resTopologyMapper.insertSelective(record);
            // 新增TopologyVe表
            if (1 == resultRes) {
                int resultVe = this.resveMapper.insertSelective(record);
                if (1 == resultVe) {
                    result = 1;
                } else {
                    result = 0;
                }
            }
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 扫描虚拟化环境获取下面的集群、主机、以及虚拟机
     *
     * @throws Exception 异常
     */
    @Override
    public boolean findAllByVe(ResVe resve) throws Exception {
        boolean result = false;
        // 插入任务表
        TaskLog log = new TaskLog();
        log.setAccount(resve.getAccount());
        log.setTaskDetail("扫描虚拟化环境");
        log.setTaskTarget(resve.getResTopologyName());
        log.setTaskType(WebConstants.TaskType.SCAN_VCENTER);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskLogSid = taskLog.getTaskLogSid();
        try {
            // 更新虚拟化环境连接状态
            ResVe resVe = new ResVe();
            resVe.setResTopologySid(resve.getResTopologySid());
            resVe.setConnectStatus(WebConstants.ResVeConnectStatus.SUCCESS);
            resVe.setUpdateStatus(WebConstants.ResVeUpdateStatus.UPDATING);
            this.updateByPrimaryKeySelective(resVe);

            // 获取虚拟化环境下所有主机
            List<ResHost> allHosts = this.getAllHostByVe(resve);

            if (!WebConstants.VirtualPlatformType.OPENSTACK.equals(resve.getVirtualPlatformType())) {

                // 查询集群下存储 ,并操作数据库
                // this.getStorageByVe(resve);

                // 查询集群，并操作数据库
                List<ResTopology> resVcList = this.getVcByVe(resve);
                if (resVcList != null && resVcList.size() > 0) {
                    // 遍历查询集群下主机
                    for (ResTopology resT : resVcList) {

                        ResVc resVc = this.resVcMapper.selectByPrimaryKey(resT.getResTopologySid());

                        // 查询集群下主机，并更新主机与集群关系
                        this.updateHostsByVc(resVc, resve, allHosts);
                    }
                }
            }

            // 查询所有主机SID为虚拟化环境的SID的虚拟机
            Criteria criteria = new Criteria();
            criteria.put("allocateResHostSid", resve.getResTopologySid());
            List<ResVm> resVmList = this.resVmMapper.selectByParams(criteria);
            // 删除虚拟机以，网络，磁盘
            if (resVmList != null && resVmList.size() > 0) {
                for (ResVm resVm : resVmList) {

                    // 当虚拟机被删除，调用服务层接口
                    try {
                        this.resInfoSync.updateResVmInfo(resVm.getResVmSid(), WebConstants.scanVmChangeType.DELETE);
                    } catch (Exception e) {
                        logger.error("删除虚拟机，调用服务层接口异常：" + ExceptionUtils.getFullStackTrace(e));
                    }

                    // 删除虚拟机
                    this.resVmMapper.deleteByPrimaryKey(resVm.getResVmSid());
                    // 删除网络关系
                    this.resVmNetworkMapper.deleteByPrimaryKey(resVm.getResVmSid());
                    // 删除磁盘
                    Criteria criteria2 = new Criteria();
                    criteria2.put("resVmSid", resVm.getResVmSid());
                    this.resVdMapper.deleteByParams(criteria2);
                }
            }

            if (!WebConstants.VirtualPlatformType.OPENSTACK.equals(resve.getVirtualPlatformType())) {
                // 更新网络
                this.getNetWorkByVe(resve);
            }
            // 更新虚拟化环境连接状态
            resVe.setConnectStatus(WebConstants.ResVeConnectStatus.SUCCESS);
            resVe.setUpdateStatus(WebConstants.ResVeUpdateStatus.UPDATE_SUCCESS);
            resVe.setUpdateTime(new Date());
            this.resveMapper.updateByPrimaryKeySelective(resVe);

            // 更新任务表
            taskLog = new TaskLog();
            taskLog.setTaskLogSid(taskLogSid);
            taskLog.setTaskDetail("扫描虚拟化环境成功");
            taskLogger.success(taskLog);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("扫描虚拟化环境出错", ExceptionUtils.getFullStackTrace(e));
            // 更新虚拟化环境连接状态
            ResVe resVe = new ResVe();
            resVe.setResTopologySid(resve.getResTopologySid());
            resVe.setConnectStatus(WebConstants.ResVeConnectStatus.FAILED);
            resVe.setUpdateStatus(WebConstants.ResVeUpdateStatus.UPDATE_FAIL);
            resVe.setUpdateTime(new Date());
            this.resveMapper.updateByPrimaryKeySelective(resVe);

            // 更新任务表
            taskLog = new TaskLog();
            taskLog.setTaskLogSid(taskLogSid);
            taskLog.setTaskDetail("扫描虚拟化环境失败");
            taskLog.setTaskFailureReason(ExceptionUtils.getMessage(e));
            taskLogger.fail(taskLog);
        }
        return result;
    }

    /**
     * 调用接口查询虚拟化环境下的集群，并插入数据库
     *
     * @param resVe
     *
     * @return
     *
     * @throws IOException
     * @throws MQException
     */
    @Override
    public List<ResTopology> getVcByVe(ResVe resVe) throws IOException, MQException {
        List<ResTopology> resVcList = new ArrayList<ResTopology>();
        ClusterScan clusterScan = new ClusterScan();
        clusterScan.setProviderType(resVe.getVirtualPlatformType());
        clusterScan.setAuthUrl(resVe.getManagementUrl());
        clusterScan.setAuthUser(resVe.getManagementAccount());
        clusterScan.setAuthPass(resVe.getManagementPassword());
        clusterScan.setVirtEnvType(resVe.getVirtualPlatformType());
        clusterScan.setVirtEnvUuid(resVe.getResTopologySid());

        Object obj = MQHelper.rpc(clusterScan);
        ClusterScanResult clusterScanResult = (ClusterScanResult) obj;
        if (clusterScanResult.isSuccess()) {
            List<ResVc> managedVcList = new ArrayList<ResVc>();

            // 查询虚拟环境下已纳管集群
            Criteria criteria = new Criteria();
            criteria.put("parentTopologySid", resVe.getResTopologySid());
            managedVcList = this.resVcMapper.selectByParams(criteria);

            // 集群存在则更新，不存在则插入
            List<ClusterVO> clusterList = clusterScanResult.getClusters();

            // 克隆集群信息
            List<ClusterVO> cloneClusterList = null;
            if (clusterList != null && clusterList.size() > 0) {
                cloneClusterList = new ArrayList<ClusterVO>(clusterList);
            }
            if (managedVcList != null && managedVcList.size() > 0) {
                for (ResVc managedVc : managedVcList) {
                    boolean clusterFlag = false;
                    if (clusterList != null && clusterList.size() > 0) {
                        for (ClusterVO clusterVO : clusterList) {
                            ResVc rvc = new ResVc(clusterVO);

                            // 数据库存在的集群，则更新并从clone的集合中去除
                            if (managedVc.getClusterName().equals(rvc.getClusterName())) {
                                rvc.setResTopologySid(managedVc.getResTopologySid());
                                this.resVcMapper.updateByPrimaryKeySelective(rvc);
                                cloneClusterList.remove(clusterVO);
                                clusterFlag = true;
                                break;
                            }
                        }
                        if (!clusterFlag) {
                            // 删除集群
                            this.resTopologyMapper.deleteByPrimaryKey(managedVc.getResTopologySid());
                            this.resVcMapper.deleteByPrimaryKey(managedVc.getResTopologySid());
                        }
                    }
                }
            }

            // 插入数据库不存的集群信息
            if (cloneClusterList != null && cloneClusterList.size() > 0) {
                for (ClusterVO clusterVO : cloneClusterList) {
                    ResVc rvc = new ResVc(clusterVO);
                    // 数据库不存在集群,插入拓扑表,集群表
                    ResTopology rt = new ResTopology();
                    rt.setResTopologyName(rvc.getClusterName());
                    rt.setResTopologyType(WebConstants.RES_TOPOLOGY_TYPE.VC);
                    rt.setParentTopologySid(resVe.getResTopologySid());
                    WebUtil.prepareInsertParams(rt);
                    this.resTopologyMapper.insertSelective(rt);
                    rvc.setResTopologySid(rt.getResTopologySid());
                    this.resVcMapper.insertSelective(rvc);
                }
            }

            // 查询已插入数据库的集群，并返回
            Criteria criteria2 = new Criteria();
            criteria2.put("parentTopologySid", resVe.getResTopologySid());
            criteria2.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.VC);
            resVcList = this.resTopologyMapper.selectByParams(criteria2);

            // 更新任务日志
            // taskLogger.update(log);

        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "下集群失败");

        }
        return resVcList;
    }

    /**
     * 调用接口查询虚拟化环境下的集群，并插入数据库
     *
     * @param resVe
     *
     * @return
     *
     * @throws Exception
     */
    @Override
    public List<ResHost> getAllHostByVe(ResVe resVe) throws Exception {

        List<ResHost> hosts = new ArrayList<ResHost>();
        AllInOneScan allInOneScan = new AllInOneScan();
        allInOneScan.setProviderType(resVe.getVirtualPlatformType());
        allInOneScan.setAuthUrl(resVe.getManagementUrl());
        allInOneScan.setAuthUser(resVe.getManagementAccount());
        allInOneScan.setAuthPass(resVe.getManagementPassword());
        allInOneScan.setVirtEnvType(resVe.getVirtualPlatformType());
        allInOneScan.setVirtEnvUuid(resVe.getResTopologySid());

        Object obj = MQHelper.rpc(allInOneScan);

        AllInOneScanResult allInOneScanResult = (AllInOneScanResult) obj;

        if (allInOneScanResult.isSuccess()) {
            // 查询集群下已纳管主机
            List<ResHost> managedHostList = new ArrayList<ResHost>();
            Criteria hostCriteria = new Criteria();
            hostCriteria.put("resTopologySid", resVe.getResTopologySid());
            managedHostList = this.resHostMapper.selectByParams(hostCriteria);

            // 主机列表
            List<HostVO> hostList = allInOneScanResult.getHosts();
            System.out.println(JsonUtil.toJson(hostList));
            // 克隆主机列表
            List<HostVO> cloneHostList = null;
            if (hostList != null && hostList.size() > 0) {
                logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有主机个数：" + hostList.size());
                cloneHostList = new ArrayList<HostVO>(hostList);
            }
            if (managedHostList != null && managedHostList.size() > 0) {

                // 已存在主机更新，否则插入
                for (ResHost managedHost : managedHostList) {
                    boolean hostFlag = false;
                    if (hostList != null && hostList.size() > 0) {
                        for (HostVO hostVo : hostList) {
                            ResHost rh = new ResHost(hostVo);
                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (managedHost.getUuid().equals(rh.getUuid())) {

                                rh.setResHostSid(managedHost.getResHostSid());
                                WebUtil.prepareUpdateParams(rh);
                                this.resHostMapper.updateByPrimaryKeySelective(rh);
                                cloneHostList.remove(hostVo);
                                hostFlag = true;
                            }
                        }
                        if (!hostFlag) {

                            // 删除主机
                            this.resHostService.deleteHostWithObjRes(managedHost.getResHostSid());
                            // 删除主机与存储关系
                            Criteria criteria = new Criteria();
                            criteria.put("resHostSidDelete", managedHost.getResHostSid());
                            this.resHostStorageMapper.deleteByParams(criteria);

                            // 查询主机下虚拟机
                            Criteria criteria1 = new Criteria();
                            criteria1.put("allocateResHostSid", managedHost.getResHostSid());
                            List<ResVm> resVmList = this.resVmMapper.selectByParams(criteria1);
                            if (resVmList != null && resVmList.size() > 0) {
                                for (ResVm resVm : resVmList) {
                                    // 更新虚拟机主机SID为虚拟化环境的SID
                                    resVm.setAllocateResHostSid(resVe.getResTopologySid());
                                    this.resVmMapper.updateByPrimaryKeySelective(resVm);
                                }
                            }
                        }
                    }
                }
            }

            // 插入数据库没有的主机
            if (cloneHostList != null && cloneHostList.size() > 0) {
                for (HostVO hostVo : cloneHostList) {
                    ResHost rh = new ResHost(hostVo);
                    rh.setParentTopologySid(resVe.getResTopologySid());

                    // 插入主机
                    WebUtil.prepareInsertParams(rh);
                    this.resHostMapper.insertSelective(rh);

                    // // 处理虚拟机
                    // List<ResVm> resVmList = rh.getVms();
                    // this.getVmsByHost(rh, resve, resVmList);
                }
            }
            // 查询已插入数据库的主机，并返回
            Criteria criteria = new Criteria();
            criteria.put("resTopologySid", resVe.getResTopologySid());
            hosts = this.resHostMapper.selectByParamsForScan(criteria);

            if (!WebConstants.VirtualPlatformType.OPENSTACK.equals(resVe.getVirtualPlatformType())) {
                // 查询虚拟化环境存储
                this.getStorageByVe(resVe);
            }

            // 插入虚拟机数据
            for (ResHost resHostDb : hosts) {
                for (HostVO hostVo : hostList) {
                    ResHost rh = new ResHost(hostVo);
                    if (resHostDb.getUuid().equals(rh.getUuid())) {
                        rh.setResHostSid(resHostDb.getResHostSid());
                        List<ResVm> resVmList = rh.getVms();
                        this.getVmsByHost(rh, resVe, resVmList);
                        break;
                    }
                }
            }
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "主机失败");
        }
        return hosts;
    }

    /**
     * 调用接口查询虚拟化环境下的存储，并插入数据库
     *
     * @param resVe the res ve
     *
     * @return storage by ve
     *
     * @throws JSONException the json exception
     * @throws IOException   the io exception
     * @throws MQException   the mq exception
     */
    public List<ResStorage> getStorageByVe(ResVe resVe) throws JSONException, IOException, MQException {
        // Long taskLogSid = taskLog.getTaskLogSid();
        // TaskLog log = new TaskLog();
        // log.setTaskLogSid(taskLogSid);
        // log.setTaskDetail("获取" + resVe.getResTopologyName() + "下存储");

        List<ResStorage> resStorageList = new ArrayList<ResStorage>();
        StorageScan storageScan = new StorageScan();
        storageScan.setProviderType(resVe.getVirtualPlatformType());
        storageScan.setAuthUrl(resVe.getManagementUrl());
        storageScan.setAuthUser(resVe.getManagementAccount());
        storageScan.setAuthPass(resVe.getManagementPassword());
        storageScan.setVirtEnvType(resVe.getVirtualPlatformType());
        storageScan.setVirtEnvUuid(resVe.getResTopologySid());

        Object obj = MQHelper.rpc(storageScan);

        StorageScanResult storageScanResult = (StorageScanResult) obj;

        if (storageScanResult.isSuccess()) {
            List<ResStorage> managedStorageList = new ArrayList<ResStorage>();

            // 查询虚拟环境下已纳管存储
            Criteria criteria = new Criteria();
            criteria.put("parentTopologySid", resVe.getResTopologySid());
            managedStorageList = this.resStorageMapper.selectByParams(criteria);
            // 集群存在则更新，不存在则插入
            List<DataStoreVO> storageList = storageScanResult.getDatastores();
            List<DataStoreVO> cloneStorageList = null;
            if (storageList != null && storageList.size() > 0) {
                cloneStorageList = new ArrayList<DataStoreVO>(storageList);
            }

            if (managedStorageList != null && managedStorageList.size() > 0) {
                for (ResStorage managedStorage : managedStorageList) {
                    boolean storageFlag = false;
                    if (storageList != null && storageList.size() > 0) {
                        for (DataStoreVO storageMap : storageList) {
                            // String storageMapStr =
                            // JsonUtil.toJson(storageMap);
                            ResStorage rs = new ResStorage(storageMap);

                            if (managedStorage.getUuid() != null) {
                                if (managedStorage.getUuid().equals(rs.getUuid())) {
                                    // 更新存储
                                    rs.setParentTopologySid(resVe.getResTopologySid());
                                    rs.setResStorageSid(managedStorage.getResStorageSid());

                                    // rs.setStorageType(rs.getStorageType().toUpperCase());
                                    rs.setStorageType(null);
                                    WebUtil.prepareUpdateParams(rs);
                                    this.resStorageMapper.updateByPrimaryKeySelective(rs);
                                    cloneStorageList.remove(storageMap);

                                    // 更新主机与存储关联表
                                    String[] hosts = storageMap.getHostNames();
                                    // 根据存储删除关系表
                                    ResHostStorage rhs = new ResHostStorage();
                                    rhs.setResStorageSid(rs.getResStorageSid());
                                    this.resHostStorageMapper.deleteByPrimaryKey(rhs);
                                    if (hosts != null && hosts.length > 0) {
                                        for (String hostName : hosts) {
                                            // 查询对应主机Sid
                                            Criteria criteria2 = new Criteria();
                                            criteria2.put("hostName", hostName);
                                            criteria2.put("resTopologySid", resVe.getResTopologySid());
                                            List<ResHost> manHost = this.resHostMapper.selectByParamsForScan(criteria2);

                                            if (manHost != null && manHost.size() > 0) {
                                                // 插入关系
                                                ResHostStorage rhsNew = new ResHostStorage();
                                                rhsNew.setResHostSid(manHost.get(0).getResHostSid());
                                                rhsNew.setResStorageSid(rs.getResStorageSid());
                                                this.resHostStorageMapper.insertSelective(rhsNew);
                                            }
                                        }
                                    }
                                    storageFlag = true;
                                }
                            } else {
                                if (managedStorage.getStorageName().equals(managedStorage.getStorageName())) {
                                    storageFlag = true;
                                    cloneStorageList.remove(storageMap);
                                }
                            }

                        }
                        if (!storageFlag) {
                            // 删除存储，以及存储与主机的关联
                            this.resStorageMapper.deleteByPrimaryKey(managedStorage.getResStorageSid());
                            ResHostStorage rhs = new ResHostStorage();
                            rhs.setResStorageSid(managedStorage.getResStorageSid());
                            this.resHostStorageMapper.deleteByPrimaryKey(rhs);
                        }

                    }
					/*
					 * else { // 删除存储，以及存储与主机的关联
					 * this.resStorageMapper.deleteByPrimaryKey
					 * (managedStorage.getResStorageSid()); ResHostStorage rhs =
					 * new ResHostStorage();
					 * rhs.setResStorageSid(managedStorage.getResStorageSid());
					 * this.resHostStorageMapper.deleteByPrimaryKey(rhs); }
					 */
                }
            }

            // 未加入数据库的存储，插入数据库
            if (cloneStorageList != null && cloneStorageList.size() > 0) {
                for (DataStoreVO storageMap : cloneStorageList) {
                    String storageMapStr = JsonUtil.toJson(storageMap);
                    ResStorage rs = new ResStorage(storageMap);

                    rs.setStorageType(null);
                    WebUtil.prepareInsertParams(rs);
                    rs.setParentTopologySid(resVe.getResTopologySid());
                    this.resStorageMapper.insertSelective(rs);

                    // 更新主机与存储关联表
                    String[] hosts = storageMap.getHostNames();

                    // 根据存储删除关系表
                    ResHostStorage rhs = new ResHostStorage();
                    rhs.setResStorageSid(rs.getResStorageSid());
                    this.resHostStorageMapper.deleteByPrimaryKey(rhs);
                    if (hosts != null && hosts.length > 0) {
                        for (String hostName : hosts) {
                            // 查询对应主机Sid
                            Criteria criteria2 = new Criteria();
                            criteria2.put("hostName", hostName);
                            criteria2.put("resTopologySid", resVe.getResTopologySid());
                            List<ResHost> manHost = this.resHostMapper.selectByParamsForScan(criteria2);

                            if (manHost != null && manHost.size() > 0) {
                                // 插入关系
                                ResHostStorage rhsNew = new ResHostStorage();
                                rhsNew.setResHostSid(manHost.get(0).getResHostSid());
                                rhsNew.setResStorageSid(rs.getResStorageSid());
                                this.resHostStorageMapper.insertSelective(rhsNew);
                            }
                        }
                    }
                }
            }
            Criteria criteria2 = new Criteria();
            criteria2.put("parentTopologySid", resVe.getResTopologySid());
            resStorageList = this.resStorageMapper.selectByParams(criteria2);
            // 更新任务日志
            // taskLogger.update(log);
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "下存储失败");
        }
        return resStorageList;
    }

    /**
     * 调用接口查询集群下主机
     *
     * @param resVc
     * @param resVe
     *
     * @return
     *
     * @throws IOException
     */
    @Override
    public List<ResHost> getHostByVc(ResVc resVc, ResVe resVe) throws Exception {
        // Long taskLogSid = taskLog.getTaskLogSid();
        // TaskLog log = new TaskLog();
        // log.setTaskLogSid(taskLogSid);
        // log.setTaskDetail("获取" + resVc.getClusterName() + "下主机");
        List<ResHost> hosts = new ArrayList<ResHost>();
        // 查询主机
        Map<String, Object> mapHost = commonParams(resVe);
        mapHost.put("resTopologyName", resVc.getResTopologyName());
        String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
        String hostUrl = adapterUrl + "/cluster/getHostByCluster";
        String hostParamsJson = JsonUtil.toJson(mapHost);
        RESTHttpResponse hostResult = RSETClientUtil.post(hostUrl, hostParamsJson);
        if (RESTHttpResponse.SUCCESS.equals(hostResult.getStatus())) {
            // 查询集群下已纳管主机
            List<ResHost> managedHostList = new ArrayList<ResHost>();
            Criteria hostCriteria = new Criteria();
            hostCriteria.put("parentTopologySid", resVc.getResTopologySid());
            managedHostList = this.resHostMapper.selectByParamsForScan(hostCriteria);

            List<Map<String, Object>> hostList = JsonUtil.fromJson(hostResult.getContent(), List.class);
            List<Map<String, Object>> cloneHostList = null;
            if (hostList != null && hostList.size() > 0) {
                cloneHostList = new ArrayList<Map<String, Object>>(hostList);
            }
            if (managedHostList != null && managedHostList.size() > 0) {

                // 已存在主机更新，否则插入
                for (ResHost managedHost : managedHostList) {
                    boolean hostFlag = false;
                    if (hostList != null && hostList.size() > 0) {
                        for (Map<String, Object> rhMap : hostList) {
                            // boolean hostFlag=false;
                            String rhMapStr = JsonUtil.toJson(rhMap);
                            ResHost rh = JsonUtil.fromJson(rhMapStr, ResHost.class);
                            if ("poweredOn".equals(rh.getStatus())) {
                                rh.setStatus(WebConstants.ResHostStatus.NORMAL);
                            }
                            if ("poweredOff".equals(rh.getStatus())) {
                                rh.setStatus(WebConstants.ResHostStatus.OUTLINE);
                            }
                            if ("standBy".equals(rh.getStatus())) {
                                rh.setStatus(WebConstants.ResHostStatus.STANDBY);
                            }
                            if ("unknown".equals(rh.getStatus())) {
                                rh.setStatus(WebConstants.ResHostStatus.UNKNOWN);
                            }
                            rh.setMemorySize(rh.getMemorySize() / 1024 / 1024);

                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (managedHost.getUuid().equals(rh.getUuid())) {
                                rh.setResHostSid(managedHost.getResHostSid());
                                rh.setParentTopologySid(resVc.getResTopologySid());
                                WebUtil.prepareUpdateParams(rh);
                                this.resHostMapper.updateByPrimaryKeySelective(rh);
                                cloneHostList.remove(rhMap);
                                hostFlag = true;
                            }
                        }
                        if (!hostFlag) {
                            this.resHostMapper.deleteByPrimaryKey(managedHost.getResHostSid());
                        }
                    }
                }

            }

            // 插入数据库没有的主机
            if (cloneHostList != null && cloneHostList.size() > 0) {
                for (Map<String, Object> rhMap : cloneHostList) {
                    // boolean hostFlag=false;
                    String rhMapStr = JsonUtil.toJson(rhMap);
                    ResHost rh = JsonUtil.fromJson(rhMapStr, ResHost.class);
                    if ("poweredOn".equals(rh.getStatus())) {
                        rh.setStatus(WebConstants.ResHostStatus.NORMAL);
                    }
                    if ("poweredOff".equals(rh.getStatus())) {
                        rh.setStatus(WebConstants.ResHostStatus.OUTLINE);
                    }
                    if ("standBy".equals(rh.getStatus())) {
                        rh.setStatus(WebConstants.ResHostStatus.STANDBY);
                    }
                    if ("unknown".equals(rh.getStatus())) {
                        rh.setStatus(WebConstants.ResHostStatus.UNKNOWN);
                    }
                    rh.setMemorySize(rh.getMemorySize() / 1024 / 1024);
                    rh.setParentTopologySid(resVc.getResTopologySid());
                    // 插入主机
                    WebUtil.prepareInsertParams(rh);

                    this.resHostMapper.insertSelective(rh);
                }
            }

            // 查询已插入数据库的主机，并返回
            Criteria criteria = new Criteria();
            criteria.put("parentTopologySid", resVc.getResTopologySid());
            hosts = this.resHostMapper.selectByParamsForScan(criteria);

            // 更新任务日志
            // taskLogger.update(log);
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVc.getClusterName() + "主机失败");
        }
        return hosts;
    }

    /**
     * 调用接口查询集群下主机,并更新主机与集群的关系（扫描虚拟化环境时）
     *
     * @param resVc   the res vc
     * @param resVe   the res ve
     * @param allHost the all host
     *
     * @return
     *
     * @throws IOException the io exception
     * @throws MQException the mq exception
     */
    public void updateHostsByVc(ResVc resVc, ResVe resVe, List<ResHost> allHost) throws IOException, MQException {
        // Long taskLogSid = taskLog.getTaskLogSid();
        // TaskLog log = new TaskLog();
        // log.setTaskLogSid(taskLogSid);
        // log.setTaskDetail("获取" + resVc.getClusterName() + "下主机");

        // 查询主机
        HostScanByCluster hostScanByCluster = new HostScanByCluster();
        hostScanByCluster.setProviderType(resVe.getVirtualPlatformType());
        hostScanByCluster.setAuthUrl(resVe.getManagementUrl());
        hostScanByCluster.setAuthUser(resVe.getManagementAccount());
        hostScanByCluster.setAuthPass(resVe.getManagementPassword());
        hostScanByCluster.setVirtEnvType(resVe.getVirtualPlatformType());
        hostScanByCluster.setVirtEnvUuid(resVe.getResTopologySid());
        hostScanByCluster.setCluster(resVc.getResTopologyName());

        Object obj = MQHelper.rpc(hostScanByCluster);
        HostScanByClusterResult hostScanByClusterResult = (HostScanByClusterResult) obj;
        if (hostScanByClusterResult.isSuccess()) {
            List<HostNameVO> hostList = hostScanByClusterResult.getHosts();
            if (allHost != null && allHost.size() > 0) {

                // 已存在主机更新，否则插入
                for (ResHost managedHost : allHost) {
                    if (hostList != null && hostList.size() > 0) {
                        for (HostNameVO hostVO : hostList) {
                            // boolean hostFlag=false;
                            // ResHost rh = new ResHost(hostVO);

                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (managedHost.getUuid().equals(hostVO.getUuid())) {
                                managedHost.setParentTopologySid(resVc.getResTopologySid());
                                WebUtil.prepareUpdateParams(managedHost);
                                this.resHostMapper.updateByPrimaryKeySelective(managedHost);
                            }
                        }
                    }
                }
            }

        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVc.getClusterName() + "主机失败");
        }
    }

    /**
     * 调用接口根据主机查询虚拟机
     *
     * @param resHost
     * @param resVe
     *
     * @return
     *
     * @throws Exception
     */
    @Override
    public List<ResVm> getVmsByHost(ResHost resHost, ResVe resVe, List<ResVm> resVmList) throws Exception {

        // 查询主机下数据库中已纳管虚拟机
        List<ResVm> managedVmList = new ArrayList<ResVm>();
        Criteria vmCriteria = new Criteria();
        vmCriteria.put("allocateResHostSid", resHost.getResHostSid());
        String statusNotIn =
                "'" + WebConstants.ResVmStatus.DELETED + "','" + WebConstants.ResVmStatus.DELETING + "','" +
                        WebConstants.ResVmStatus.OCCUPING + "'";
        vmCriteria.put("statusNotIn", statusNotIn);
        managedVmList = this.resVmMapper.selectByParams(vmCriteria);

        // FIXME 为了检查是否有错误信息查询出来
        for (ResVm vm : managedVmList) {
            if (StringUtil.isNullOrEmpty(vm.getUuid())) {
                logger.error("VM UUID IS NULL! sid : " + vm.getResVmSid());
            }
        }

        // 转换JSON为List对象
        // List<Map<String, Object>> vmList =
        // JsonUtil.fromJson(vmResult.getContent(), List.class);
        // Clone虚拟机列表
        List<ResVm> cloneVmList = null;
        if (resVmList != null && resVmList.size() > 0) {
            cloneVmList = new ArrayList<ResVm>(resVmList);
        }

        if (managedVmList != null && managedVmList.size() > 0) {
            // 已存在虚拟机更新虚拟机、ip、虚拟磁盘表，否则插入
            for (ResVm managedVm : managedVmList) {
                boolean vmFlag = false;
                if (resVmList != null && resVmList.size() > 0) {
                    // 扫描主机下存在虚拟机时
                    for (ResVm resVm : resVmList) {
                        if (!StringUtil.isNullOrEmpty(managedVm.getUuid())) {
                            if (managedVm.getUuid().equals(resVm.getUuid())) {
                                resVm.setAllocateResHostSid(resHost.getResHostSid());
                                // 更新虚拟机数据
                                resVm.setResVmSid(managedVm.getResVmSid());
                                resVm.setUseCpuGhz(Double.parseDouble(StringUtil.nullToEmpty(resVm.getCpuUsage())));
                                resVm.setUseMemorySize(resVm.getMemUsage());
                                // 更新虚拟机的时候不更新系统版本
                                resVm.setOsVersion(null);
                                WebUtil.prepareUpdateParams(resVm);
                                this.resVmMapper.updateByPrimaryKeySelective(resVm);
                                cloneVmList.remove(resVm);

                                // 更新网络
                                this.resVmService.updateVmNetByVm(resVm);

                                // 更新磁盘
                                List<ResVd> resVdList = resVm.getResVdList();
                                this.getVdsByVm(resVm, resVe, resVdList);

                                // 检查虚拟机是否有变化,调用服务层接口
                                boolean isChange = this.isResVmSame(managedVm, resVm);
                                if (!isChange) {
                                    try {
                                        this.resInfoSync.updateResVmInfo(resVm.getResVmSid(),
                                                                         WebConstants.scanVmChangeType.CHANGE
                                        );
                                    } catch (Exception e) {
                                        logger.error("更新虚拟机，调用服务层接口异常：" + ExceptionUtils.getFullStackTrace(e));
                                    }
                                }
                                vmFlag = true;
                                break;
                            }
                        } else {
                            if (managedVm.getVmName().equals(resVm.getVmName())) {
                                vmFlag = true;
                                cloneVmList.remove(resVm);
                                break;
                            } else {
                                if (WebConstants.ResVmStatus.CREATING.equals(managedVm.getStatus())) {
                                    vmFlag = true;
                                    break;
                                }
                            }
                        }

                    }
                    if (!vmFlag) {
                        if (StringUtil.isNullOrEmpty(managedVm.getUuid()) &&
                                WebConstants.ResVmStatus.CREATING.equals(managedVm.getStatus())) {
                            continue;
                        } else {
                            // 更新虚拟机主机SID为虚拟化环境的SID
                            managedVm.setAllocateResHostSid(resVe.getResTopologySid());
                            this.resVmMapper.updateByPrimaryKeySelective(managedVm);
                        }
                    }
                } else {
                    if (StringUtil.isNullOrEmpty(managedVm.getUuid()) &&
                            WebConstants.ResVmStatus.CREATING.equals(managedVm.getStatus())) {
                        continue;
                    } else {
                        // 更新虚拟机主机SID为虚拟化环境的SID
                        managedVm.setAllocateResHostSid(resVe.getResTopologySid());
                        this.resVmMapper.updateByPrimaryKeySelective(managedVm);
                    }
                }
            }
        }

        // 插入数据库不存在的虚拟机
        if (cloneVmList != null && cloneVmList.size() > 0) {
            for (ResVm resVm : cloneVmList) {
                // String vmMapStr = JsonUtil.toJson(resVmMap);
                // ResVm resVm = JsonUtil.fromJson(vmMapStr, ResVm.class);

                // 判断是否已经存在（考虑迁移的状况）
                Criteria criteria = new Criteria();
                criteria.put("uuid", resVm.getUuid());
                List<ResVm> listVm = this.resVmMapper.selectByParams(criteria);
                if (listVm != null && listVm.size() > 0) {

                    resVm.setAllocateResHostSid(resHost.getResHostSid());
                    // 更新虚拟机数据
                    resVm.setResVmSid(listVm.get(0).getResVmSid());
                    WebUtil.prepareUpdateParams(resVm);
                    this.resVmMapper.updateByPrimaryKeySelective(resVm);

                    // 更新网络
                    this.resVmService.updateVmNetByVm(resVm);

                    // 更新磁盘
                    List<ResVd> resVdList = resVm.getResVdList();
                    this.getVdsByVm(resVm, resVe, resVdList);
                } else {
                    resVm.setAllocateResHostSid(resHost.getResHostSid());
                    WebUtil.prepareInsertParams(resVm);
                    this.resVmMapper.insertSelective(resVm);

                    // 更新网络
                    this.resVmService.updateVmNetByVm(resVm);

                    // 更新磁盘
                    List<ResVd> resVdList = resVm.getResVdList();
                    this.getVdsByVm(resVm, resVe, resVdList);
                }
            }
        }

        Criteria criteria = new Criteria();
        criteria.put("allocateResHostSid", resHost.getResHostSid());
        String statusNotIn1 =
                "'" + WebConstants.ResVmStatus.CREATING + "','" + WebConstants.ResVmStatus.DELETED + "','" +
                        WebConstants.ResVmStatus.DELETING + "','" + WebConstants.ResVmStatus.OCCUPING + "'";
        criteria.put("statusNotIn", statusNotIn1);
        resVmList = this.resVmMapper.selectByParams(criteria);

        // 更新任务日志
        // taskLogger.update(log);

        return resVmList;
    }

    /**
     * 根据虚拟机查询磁盘，并插入数据库
     *
     * @param resVm
     * @param resVe
     *
     * @return
     *
     * @throws IOException
     */
    @Override
    public List<ResVd> getVdsByVm(ResVm resVm, ResVe resVe, List<ResVd> resVdList) throws IOException {

        List<ResVd> cloneVdList = null;

        if (resVdList != null && resVdList.size() > 0) {
            cloneVdList = new ArrayList<ResVd>(resVdList);
        }
        // 更新虚拟磁盘
        Criteria example1 = new Criteria();
        example1.put("resVmSid", resVm.getResVmSid());
        List<ResVd> managedResVdList = this.resVdMapper.selectByParams(example1);
        // 插入磁盘表
        if (managedResVdList != null && managedResVdList.size() > 0) {
            for (ResVd managedResvd : managedResVdList) {
                if (resVdList != null && resVdList.size() > 0) {
                    boolean vdFlag = false;
                    for (ResVd resVd : resVdList) {
                        Criteria criteria = new Criteria();
                        criteria.put("storageName", resVd.getDataStoreName());
                        criteria.put("resHostSid", resVm.getAllocateResHostSid());
                        List<ResStorage> resStoList = this.resStorageMapper.selectStoByHostWithStoName(criteria);
                        if (resStoList != null && resStoList.size() > 0) {
                            resVd.setAllocateResStorageSid(resStoList.get(0).getResStorageSid());
                        }
                        if (!StringUtil.isNullOrEmpty(managedResvd.getUuid())) {
                            if (managedResvd.getUuid().equals(resVd.getUuid())) {
                                resVd.setResVdSid(managedResvd.getResVdSid());
                                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                                resVd.setResVmSid(resVm.getResVmSid());
                                WebUtil.prepareUpdateParams(resVd);
                                this.resVdMapper.updateByPrimaryKeySelective(resVd);
                                cloneVdList.remove(resVd);
                                vdFlag = true;
                            }
                        } else {
                            if (managedResvd.getVdName().equals(resVd.getVdName())) {
                                cloneVdList.remove(resVd);
                                vdFlag = true;
                            }
                        }

                    }
                    if (!vdFlag) {
                        this.resVdMapper.deleteByPrimaryKey(managedResvd.getResVdSid());
                    }
                }
            }
        }
        if (cloneVdList != null && cloneVdList.size() > 0) {
            for (ResVd resVd : cloneVdList) {
                Criteria criteria = new Criteria();
                criteria.put("resHostSid", resVm.getAllocateResHostSid());
                List<ResStorage> resStoList = this.resStorageMapper.selectStoByHostWithStoName(criteria);
                if (resStoList != null && resStoList.size() > 0) {
                    resVd.setAllocateResStorageSid(resStoList.get(0).getResStorageSid());
                }
                resVd.setResVmSid(resVm.getResVmSid());
                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                WebUtil.prepareInsertParams(resVd);
                this.resVdMapper.insertSelective(resVd);
            }
        }

        // TODO
        // 查询已插入数据库的磁盘
        // Criteria example2 = new Criteria();
        // example2.put("resVmSid", resVm.getResVmSid());
        // resVdList = this.resVdService.selectByParams(example2);

        // 更新任务日志
        // taskLogger.update(log);

        return resVdList;
    }

    /**
     * 根据虚拟化环境扫描虚拟交换机，端口组
     *
     * @throws Exception
     */
    @Override
    public boolean getNetWorkByVe(ResVe resVe) throws Exception {
        boolean result = false;

        // 查询虚拟机
        ResNetwork netMaps = this.getAllNet(resVe);
        if (netMaps != null) {
            List<ResVs> allResVsList = new ArrayList<ResVs>();

            // 标准交换机
            List<ResVs> standardVss = netMaps.getvSwitches();
            if (standardVss != null && standardVss.size() > 0) {
                List<ResVs> resStaVsList = this.getResVsByVe(standardVss, resVe, WebConstants.ResVsType.STANDARD_VS);

                // 更新端口组表
                for (ResVs resVs : resStaVsList) {

                    // 查询标准交换机下的标准端口组
                    Criteria criteria = new Criteria();
                    criteria.put("resVsSid", resVs.getResVsSid());
                    List<ResVsPortGroup> resVsPortList = this.resVsPortGroupMapper.selectByParams(criteria);

                    // 一个标准交换机只会对应一个标准端口组，若存在则更新，不存在则插入
                    if (resVsPortList != null && resVsPortList.size() > 0) {
                        ResVsPortGroup resVsPortGourp = resVsPortList.get(0);
                        resVsPortGourp.setName(resVs.getResVsName());
                        resVsPortGourp.setResVsSid(resVs.getResVsSid());
                        resVsPortGourp.setUuid(resVs.getResVsName());
                        WebUtil.prepareUpdateParams(resVsPortGourp);
                        this.resVsPortGroupMapper.updateByPrimaryKeySelective(resVsPortGourp);
                    } else {
                        ResVsPortGroup resVsPortGroup = new ResVsPortGroup();
                        resVsPortGroup.setName(resVs.getResVsName());
                        resVsPortGroup.setResVsSid(resVs.getResVsSid());
                        resVsPortGroup.setUuid(resVs.getResVsName());
                        WebUtil.prepareInsertParams(resVsPortGroup);
                        this.resVsPortGroupMapper.insertSelective(resVsPortGroup);
                    }
                }
                allResVsList.addAll(resStaVsList);
            }

            // 分布式交换机
            List<ResVs> distributeVss = netMaps.getDvSwitches();
            if (distributeVss != null && distributeVss.size() > 0) {
                List<ResVs> resDisVsList = this.getResVsByVe(distributeVss,
                                                             resVe,
                                                             WebConstants.ResVsType.DISTRIBUTE_VS
                );
                // 插入分布式端口组
                for (ResVs resVs : resDisVsList) {
                    for (ResVs dvs : distributeVss) {
                        String dvsUuid = dvs.getUuid();
                        if (dvsUuid.equals(resVs.getUuid())) {
                            List<ResVsPortGroup> portGroupMapList = dvs.getResVsPortGroups();
                            this.insertDisPortGroup(portGroupMapList, resVs);
                        }
                    }
                }
                allResVsList.addAll(resDisVsList);
            }

            // 更新与主机的关系
            this.updateHostResVsRelationship(allResVsList, resVe);
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * 调用南向接口获取MAP
     *
     * @param resVe
     *
     * @return
     *
     * @throws Exception
     */
    @Override
    public ResNetwork getAllNet(ResVe resVe) throws Exception {
        ResNetwork netMaps = new ResNetwork();

        List<ResStorage> resStorageList = new ArrayList<ResStorage>();
        NetworkScan networkScan = new NetworkScan();
        networkScan.setProviderType(resVe.getVirtualPlatformType());
        networkScan.setAuthUrl(resVe.getManagementUrl());
        networkScan.setAuthUser(resVe.getManagementAccount());
        networkScan.setAuthPass(resVe.getManagementPassword());
        networkScan.setVirtEnvType(resVe.getVirtualPlatformType());
        networkScan.setVirtEnvUuid(resVe.getResTopologySid());

        Object obj = MQHelper.rpc(networkScan);

        NetworkScanResult networkScanResult = (NetworkScanResult) obj;
        if (networkScanResult.isSuccess()) {
            List<VSwitchVO> vSwithcVOs = networkScanResult.getSvSwitchs();
            if (vSwithcVOs != null && vSwithcVOs.size() > 0) {
                for (VSwitchVO vSwithcVO : vSwithcVOs) {
                    netMaps.getvSwitches().add(new ResVs(vSwithcVO));

                }
            }

            List<DvSwitchVO> dvSwithcVOs = networkScanResult.getDvSwitchs();
            if (dvSwithcVOs != null && dvSwithcVOs.size() > 0) {
                for (DvSwitchVO dvSwithcVO : dvSwithcVOs) {
                    netMaps.getDvSwitches().add(new ResVs(dvSwithcVO));

                }
            }
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "网络失败");
        }
        return netMaps;
    }

    /**
     * 插入交换机
     *
     * @param vsList the vs list
     * @param resVe  the res ve
     * @param vsType the vs type
     *
     * @return res vs by ve
     */
    public List<ResVs> getResVsByVe(List<ResVs> vsList, ResVe resVe, String vsType) {
        List<ResVs> cloneVsList = null;

        if (vsList != null && vsList.size() > 0) {
            cloneVsList = new ArrayList<ResVs>(vsList);
        }
        Criteria example1 = new Criteria();
        example1.put("parentTopologySid", resVe.getResTopologySid());
        if (vsType.equals(WebConstants.ResVsType.STANDARD_VS)) {
            example1.put("resVsType", WebConstants.ResVsType.STANDARD_VS);
        } else if (vsType.equals(WebConstants.ResVsType.DISTRIBUTE_VS)) {
            example1.put("resVsType", WebConstants.ResVsType.DISTRIBUTE_VS);
        }
        List<ResVs> managedResVsList = this.resVsMapper.selectByParams(example1);
        if (managedResVsList != null && managedResVsList.size() > 0) {
            for (ResVs managedResVs : managedResVsList) {
                boolean vdFlag = false;
                if (vsList != null && vsList.size() > 0) {
                    for (ResVs resVs : vsList) {
                        if (managedResVs.getUuid() != null) {
                            if (managedResVs.getUuid().equals(resVs.getUuid())) {
                                if (vsType.equals(WebConstants.ResVsType.STANDARD_VS)) {
                                    resVs.setResVsType(WebConstants.ResVsType.STANDARD_VS);
                                } else if (vsType.equals(WebConstants.ResVsType.DISTRIBUTE_VS)) {
                                    resVs.setResVsType(WebConstants.ResVsType.DISTRIBUTE_VS);
                                }
                                resVs.setResVsSid(managedResVs.getResVsSid());
                                resVs.setParentTopologySid(resVe.getResTopologySid());
                                WebUtil.prepareUpdateParams(resVs);
                                this.resVsMapper.updateByPrimaryKeySelective(resVs);
                                cloneVsList.remove(resVs);

                                // if(vsType.equals(WebConstants.ResVsType.DISTRIBUTE_VS)){
                                // //更新端口组
                                // List<Map<String,String>>
                                // dvPortGroupInfos=(List<Map<String, String>>)
                                // resVsMap.get("dvPortGroupInfos");
                                // this.insertDisPortGroup(dvPortGroupInfos,
                                // resVs);
                                // }

                                vdFlag = true;
                            }
                        } else {
                            if (managedResVs.getResVsName().equals(resVs.getResVsName())) {
                                cloneVsList.remove(resVs);
                                vdFlag = true;
                            }
                        }

                    }
                    if (!vdFlag) {
                        // 删除交换机
                        this.resVsMapper.deleteByPrimaryKey(managedResVs.getResVsSid());

                        // 删除交换机与主机关系
                        Criteria example = new Criteria();
                        example.put("resVsSid", managedResVs.getResVsSid());
                        this.resVsHostMapper.deleteByParams(example);

                        // 删除端口组
                        Criteria criteria = new Criteria();
                        criteria.put("resVsSid", managedResVs.getResVsSid());
                        this.resVsPortGroupMapper.deleteByParams(criteria);
                    }
                } else {
                    logger.info("调用南向接口成功，网络为空");
                }
            }
        }
        if (cloneVsList != null && cloneVsList.size() > 0) {
            for (ResVs resVs : cloneVsList) {
                if (vsType.equals(WebConstants.ResVsType.STANDARD_VS)) {
                    resVs.setResVsType(WebConstants.ResVsType.STANDARD_VS);
                } else if (vsType.equals(WebConstants.ResVsType.DISTRIBUTE_VS)) {
                    resVs.setResVsType(WebConstants.ResVsType.DISTRIBUTE_VS);
                }
                resVs.setParentTopologySid(resVe.getResTopologySid());
                WebUtil.prepareInsertParams(resVs);
                this.resVsMapper.insertSelective(resVs);
            }
        }

        // 查询已插入数据库的磁盘
        Criteria example2 = new Criteria();
        example2.put("parentTopologySid", resVe.getResTopologySid());
        if (vsType.equals(WebConstants.ResVsType.STANDARD_VS)) {
            example2.put("resVsType", WebConstants.ResVsType.STANDARD_VS);
        } else if (vsType.equals(WebConstants.ResVsType.DISTRIBUTE_VS)) {
            example2.put("resVsType", WebConstants.ResVsType.DISTRIBUTE_VS);
        }
        List<ResVs> resVsList = this.resVsMapper.selectByParams(example2);
        return resVsList;
    }

    /**
     * 更新主机与交换机关系
     *
     * @param resVsList
     * @param resVe
     *
     * @throws Exception
     */
    @Override
    public void updateHostResVsRelationship(List<ResVs> resVsList, ResVe resVe) throws Exception {
        List<UuidVO> hostUuidList = new ArrayList<UuidVO>();
        for (ResVs resVs : resVsList) {
            if (WebConstants.ResVsType.STANDARD_VS.equals(resVs.getResVsType())) {

                HostScanBySvs hostScanBySvs = new HostScanBySvs();
                hostScanBySvs.setProviderType(resVe.getVirtualPlatformType());
                hostScanBySvs.setAuthUrl(resVe.getManagementUrl());
                hostScanBySvs.setAuthUser(resVe.getManagementAccount());
                hostScanBySvs.setAuthPass(resVe.getManagementPassword());
                hostScanBySvs.setVirtEnvType(resVe.getVirtualPlatformType());
                hostScanBySvs.setVirtEnvUuid(resVe.getResTopologySid());
                hostScanBySvs.setSvsName(resVs.getResVsName());
                Object obj = MQHelper.rpc(hostScanBySvs);
                HostScanBySvsResult hostScanBySvsResult = (HostScanBySvsResult) obj;
                if (hostScanBySvsResult.isSuccess()) {
                    hostUuidList = hostScanBySvsResult.getUuids();
                } else {
                    throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVs.getResVsName() + "与主机关系失败");
                }
            } else if (WebConstants.ResVsType.DISTRIBUTE_VS.equals(resVs.getResVsType())) {
                HostScanByDvs hostScanByDvs = new HostScanByDvs();
                hostScanByDvs.setProviderType(resVe.getVirtualPlatformType());
                hostScanByDvs.setAuthUrl(resVe.getManagementUrl());
                hostScanByDvs.setAuthUser(resVe.getManagementAccount());
                hostScanByDvs.setAuthPass(resVe.getManagementPassword());
                hostScanByDvs.setVirtEnvType(resVe.getVirtualPlatformType());
                hostScanByDvs.setVirtEnvUuid(resVe.getResTopologySid());
                hostScanByDvs.setDvsName(resVs.getResVsName());
                Object obj = MQHelper.rpc(hostScanByDvs);
                HostScanByDvsResult hostScanByDvsResult = (HostScanByDvsResult) obj;
                if (hostScanByDvsResult.isSuccess()) {
                    hostUuidList = hostScanByDvsResult.getUuids();
                } else {
                    throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVs.getResVsName() + "与主机关系失败");
                }

            }

            // 删除该交换机与主机关系
            Criteria criteria2 = new Criteria();
            criteria2.put("resVsSid", resVs.getResVsSid());
            this.resVsHostMapper.deleteByParams(criteria2);

            // 插入主机与交换机关系
            if (hostUuidList != null && hostUuidList.size() > 0) {
                for (UuidVO uuidVO : hostUuidList) {
                    // 根据UUID查询主机
                    Criteria criteria3 = new Criteria();
                    criteria3.put("uuid", uuidVO.getUuid());
                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria3);
                    if (resHosts != null && resHosts.size() > 0) {
                        ResVsHost newResVsHost = new ResVsHost();
                        newResVsHost.setResHostSid(resHosts.get(0).getResHostSid());
                        newResVsHost.setResVsSid(resVs.getResVsSid());
                        this.resVsHostMapper.insertSelective(newResVsHost);
                    }
                }
            }
        }
    }

    /**
     * 更新分布式端口组
     *
     * @param disPortGroupList
     * @param resVs
     */
    @Override
    public void insertDisPortGroup(List<ResVsPortGroup> disPortGroupList, ResVs resVs) {
        // 查询数据库中分布式交换机下端口组
        Criteria criteria2 = new Criteria();
        criteria2.put("resVsSid", resVs.getResVsSid());
        List<ResVsPortGroup> managedResVsPortGroups = this.resVsPortGroupMapper.selectByParams(criteria2);

        // 克隆集合
        List<ResVsPortGroup> clonedisPortGroupList = null;
        if (disPortGroupList != null && disPortGroupList.size() > 0) {
            clonedisPortGroupList = new ArrayList<ResVsPortGroup>(disPortGroupList);
        }

        if (managedResVsPortGroups != null && managedResVsPortGroups.size() > 0) {
            for (ResVsPortGroup managedResVsPortGroup : managedResVsPortGroups) {
                boolean portGroupFlag = false;
                for (ResVsPortGroup portGroup : disPortGroupList) {
                    if (managedResVsPortGroup.getUuid().equals(portGroup.getUuid())) {
                        portGroup.setResVsSid(resVs.getResVsSid());
                        portGroup.setId(managedResVsPortGroup.getId());
                        WebUtil.prepareUpdateParams(portGroup);
                        this.resVsPortGroupMapper.updateByPrimaryKeySelective(portGroup);
                        clonedisPortGroupList.remove(portGroup);
                        portGroupFlag = true;
                    }
                }
                if (!portGroupFlag) {
                    // 删除端口组
                    this.resVsPortGroupMapper.deleteByPrimaryKey(managedResVsPortGroup.getId());
                }
            }
        }
        // 新增端口组
        if (clonedisPortGroupList != null && clonedisPortGroupList.size() > 0) {
            for (ResVsPortGroup portGroup : clonedisPortGroupList) {
                portGroup.setResVsSid(resVs.getResVsSid());
                WebUtil.prepareInsertParams(portGroup);
                this.resVsPortGroupMapper.insertSelective(portGroup);
            }
        }
    }

    /**
     * 测试虚拟化环境
     */
    @Override
    public boolean testVeConnection(ResVe resVe) throws Exception {
        String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
        String url = adapterUrl + "/vCenter/testVcenter";
        Map<String, Object> map = commonParams(resVe);
        String json = JsonUtil.toJson(map);
        RESTHttpResponse result = RSETClientUtil.post(url, json);
        if (RESTHttpResponse.SUCCESS.equals(result.getStatus())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 调用MQ，组装共通的参数
     *
     * @param resVe the res ve
     *
     * @return map map
     */
    public Map<String, Object> commonParams(ResVe resVe) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("providerUrl", resVe.getManagementUrl());
        map.put("authUser", resVe.getManagementAccount());
        map.put("authPass", resVe.getManagementPassword());
        return map;
    }

    /**
     * 判断数据VM与扫描过来的VM，CPU，MEMORY,IP,磁盘，是否有变化
     *
     * @param resVmDb
     * @param resVmReturn
     *
     * @return
     */
    @Override
    public boolean isResVmSame(ResVm resVmDb, ResVm resVmReturn) {
        boolean result = false;
        // 查询数据库虚拟机下网络
        Criteria example = new Criteria();
        example.put("resVmSid", resVmDb.getResVmSid());
        List<ResVmNetwork> resVmNetList = this.resVmNetworkMapper.selectByParams(example);

        // 网络个数
        Integer netDbSize = 0;
        if (resVmNetList != null && resVmNetList.size() > 0) {
            netDbSize = resVmNetList.size();
        }

        Integer netReturnSize = 0;
        if (resVmReturn.getIps() != null && resVmReturn.getIps().size() > 0) {
            netReturnSize = resVmReturn.getIps().size();
        }
        // 查询数据库虚拟机下磁盘
        List<ResVd> resVdList = this.resVdMapper.selectByParams(example);

        // 数据库虚拟机下磁盘个数
        Integer resVdDbSize = 0;
        if (resVdList != null && resVdList.size() > 0) {
            resVdDbSize = resVdList.size();
        }

        Integer resVdReturnSize = 0;
        if (resVmReturn.getResVdList() != null && resVmReturn.getResVdList().size() > 0) {
            resVdReturnSize = resVmReturn.getResVdList().size();
        }

        // 判断
        if (!(resVmDb.getVmName().equals(resVmReturn.getVmName()))) {
            result = false;
            return result;
        } else if (!(resVmDb.getCpuCores().equals(resVmReturn.getCpuCores()))) {
            result = false;
            return result;
        } else if (!(resVmDb.getMemorySize().equals(resVmReturn.getMemorySize()))) {
            result = false;
            return result;
        } else {
            // 标识所有IP是否改变
            boolean allIpFlag = false;
            if (netReturnSize != 0) {

                // 判断网络
                if (netDbSize.equals(netReturnSize)) {
                    // 个数相等，判断值
                    List<Map<String, String>> ips = resVmReturn.getIps();
                    if (ips != null && ips.size() > 0) {
                        for (Map<String, String> ip : ips) {
                            boolean flag = false;
                            if (resVmNetList != null && resVmNetList.size() > 0) {
                                for (ResVmNetwork resVmNet : resVmNetList) {
                                    if (ip.get("ip").equals(resVmNet.getIpAddress())) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            if (!flag) {
                                allIpFlag = false;
                                break;
                            } else {
                                allIpFlag = true;
                            }
                        }

                    }
                }
            } else {
                allIpFlag = true;

            }
            // IP有变化，返回false
            if (!allIpFlag) {
                result = false;
                return result;
            } else {
                boolean allVdFlag = false;
                // 判断磁盘
                if (resVdDbSize.equals(resVdReturnSize)) {
                    // 个数相等，判断值
                    List<ResVd> vds = resVmReturn.getResVdList();
                    if (vds != null && vds.size() > 0) {
                        for (ResVd resVd : vds) {
                            boolean flag = false;
                            if (resVdList != null && resVdList.size() > 0) {
                                for (ResVd resVdDb : resVdList) {
                                    if (resVd.getUuid().equals(resVdDb.getUuid())) {
                                        if (!(resVd.getAllocateDiskSize().equals(resVdDb.getAllocateDiskSize()))) {
                                            return false;
                                        } else {
                                            flag = true;
                                            break;
                                        }

                                    }
                                }
                            }

                            if (!flag) {
                                return false;
                            } else {
                                allVdFlag = true;
                            }
                        }

                    }
                }
                // IP有变化，返回false
                if (!allVdFlag) {
                    result = false;
                    return result;
                } else {
                    result = true;
                    return result;
                }
            }
        }

    }

    /**
     * 检查License
     */
    @Override
    public LicenseResult checkLincense() {
        // 调用license接口判断是否过期
        LicenseResult result;
        result = this.licenseService.getAndCheckLicense();
        if (result.isValid()) {
            result = this.licenseService.getHostMaxCount();
        }
        return result;
    }

    @Override
    public List<ResVe> selectMgtObjVe(Long mgtObjSid, String virtualType) {
        Criteria criteria = new Criteria();
        criteria.put("mgtObjSid", mgtObjSid);
        criteria.put("virtualPlatformType", virtualType);
        return this.resveMapper.selectResVeByMgtObj(criteria);
    }

    /**
     * 调用MQ接口注册虚拟化环境队列
     */
    @Override
    public boolean registerMqEnv(ResVe resVe) {
        boolean flag = false;
        try {
            RegisterEnv registerEnv = new RegisterEnv();
            registerEnv.setProviderType(resVe.getVirtualPlatformType());
            registerEnv.setAuthUrl(resVe.getManagementUrl());
            registerEnv.setVirtEnvType(resVe.getVirtualPlatformType());
            registerEnv.setVirtEnvUuid(resVe.getResTopologySid());
            flag = MQHelper.registerRpc(registerEnv);
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("队列创建失败");
        }
        return flag;
    }

    /**
     * 调用MQ接口删除虚拟化环境队列
     */
    @Override
    public boolean unRegisterrMqEnv(ResVe resVe) {
        boolean flag = false;
        try {
            UnRegisterEnv unRegister = new UnRegisterEnv();
            unRegister.setProviderType(resVe.getVirtualPlatformType());
            unRegister.setAuthUrl(resVe.getManagementUrl());
            unRegister.setVirtEnvType(resVe.getVirtualPlatformType());
            unRegister.setVirtEnvUuid(resVe.getResTopologySid());
            flag = MQHelper.unRegisterRpc(unRegister);
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("队列删除失败");
        }

        return flag;
    }

    /**
     * 扫描power环境
     */
    @Override
    public void findAllByPowerVe(ResVe resve) {

        // 插入任务表
        TaskLog log = new TaskLog();
        log.setAccount(resve.getAccount());
        log.setTaskDetail("扫描虚拟化环境");
        log.setTaskTarget(resve.getResTopologyName());
        log.setTaskType(WebConstants.TaskType.SCAN_VCENTER);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskLogSid = taskLog.getTaskLogSid();
        try {
            // 更新虚拟化环境连接状态
            ResVe resVe = new ResVe();
            resVe.setResTopologySid(resve.getResTopologySid());
            resVe.setConnectStatus(WebConstants.ResVeConnectStatus.SUCCESS);
            resVe.setUpdateStatus(WebConstants.ResVeUpdateStatus.UPDATING);
            this.updateByPrimaryKeySelective(resVe);
            logger.info("扫描虚拟化环境 | " + resve.getResTopologyName() + " - " + resve.getResTopologySid());
            // 查询主机
            this.getAllHostByPowerVe(resve);
            // 查询CPU池
            this.getCpuPoolByPowerVe(resve);
            // 查询虚拟交换机
            this.getVSwitchByPowerVe(resve);
            // 查询虚拟机
            this.getVmsByPowerVe(resve);
            // 更新主机IsViosFlg字段
            this.updateHostViosFlg(resve);
            // 查询配件
            this.getResHostItemByVe(resve);
            // 查询IO端口
            this.getResHostItemSlotByVe(resve);

            // 查询虚拟化环境下的VIOS
            List<ResVios> managedViosList = this.resViosMapper.selectByResVe(new Criteria().put("resVeSid",
                                                                                                resve.getResTopologySid()
            ));
            if (managedViosList != null && managedViosList.size() > 0) {
                for (ResVios resVios : managedViosList) {
                    if (!(StringUtil.isNullOrEmpty(resVios.getUser())) &&
                            !(StringUtil.isNullOrEmpty(resVios.getPassword()))) {
                        List<ResVios> resViosList = new ArrayList<ResVios>();
                        resViosList.add(resVios);
                        // 查询存储
                        this.getStorageByPowerVe(resve, resViosList);

                        // 系统盘
                        this.getSysDiskByPowerVe(resve, resViosList);

                        // 数据盘 TODO:环境没有数据盘，暂时不扫描
                        //						this.getDataDiskByPowerVe(resve, resViosList);

                    }
                }
            }

            // 在更新配件和系统盘之后，更新配件中物理系统盘的信息
            this.updateVdForLpar(resve);

            // 更新虚拟化环境连接状态
            resVe.setConnectStatus(WebConstants.ResVeConnectStatus.SUCCESS);
            resVe.setUpdateStatus(WebConstants.ResVeUpdateStatus.UPDATE_SUCCESS);
            resVe.setUpdateTime(new Date());
            this.resveMapper.updateByPrimaryKeySelective(resVe);

            // 更新任务表
            taskLog = new TaskLog();
            taskLog.setTaskLogSid(taskLogSid);
            taskLog.setTaskDetail("扫描虚拟化环境成功");
            taskLogger.success(taskLog);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("扫描虚拟化环境出错", ExceptionUtils.getFullStackTrace(e));
            // 更新虚拟化环境连接状态
            ResVe resVe = new ResVe();
            resVe.setResTopologySid(resve.getResTopologySid());
            resVe.setConnectStatus(WebConstants.ResVeConnectStatus.FAILED);
            resVe.setUpdateStatus(WebConstants.ResVeUpdateStatus.UPDATE_FAIL);
            resVe.setUpdateTime(new Date());
            this.resveMapper.updateByPrimaryKeySelective(resVe);

            // 更新任务表
            taskLog = new TaskLog();
            taskLog.setTaskLogSid(taskLogSid);
            taskLog.setTaskDetail("扫描虚拟化环境失败");
            taskLog.setTaskFailureReason(ExceptionUtils.getMessage(e));
            taskLogger.fail(taskLog);
        }
    }

    /**
     * 查询power主机
     *
     * @param resVe the res ve
     *
     * @return all host by power ve
     *
     * @throws Exception the exception
     */
    public List<ResHost> getAllHostByPowerVe(ResVe resVe) throws Exception {

        List<ResHost> hosts = new ArrayList<ResHost>();
        HostScanByEnv hostScan = new HostScanByEnv();

        hostScan.setProviderType(resVe.getVirtualPlatformType());
        hostScan.setAuthUrl(resVe.getManagementUrl());
        hostScan.setAuthUser(resVe.getManagementAccount());
        hostScan.setAuthPass(resVe.getManagementPassword());
        hostScan.setVirtEnvType(resVe.getVirtualPlatformType());
        hostScan.setVirtEnvUuid(resVe.getResTopologySid());

        Object obj = MQHelper.rpc(hostScan);

        HostScanByEnvResult hostScanByEnvResult = (HostScanByEnvResult) obj;

        if (hostScanByEnvResult.isSuccess()) {
            // 查询集群下已纳管主机
            List<ResHost> managedHostList = new ArrayList<ResHost>();
            Criteria hostCriteria = new Criteria();
            hostCriteria.put("resTopologySid", resVe.getResTopologySid());
            managedHostList = this.resHostMapper.selectByParamsForScan(hostCriteria);

            // 主机列表
            List<HostVO> hostList = hostScanByEnvResult.getHostVOs();
            System.out.println("HOST扫描结果：" + JsonUtil.toJson(hostList));
            System.out.println("HOST查询结果：" + JsonUtil.toJson(managedHostList));
            // 克隆主机列表
            List<HostVO> cloneHostList = null;
            if (hostList != null && hostList.size() > 0) {
                logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有主机个数：" + hostList.size());
                cloneHostList = new ArrayList<HostVO>(hostList);
            }
            if (managedHostList != null && managedHostList.size() > 0) {

                // 已存在主机更新，否则插入
                for (ResHost managedHost : managedHostList) {
                    boolean hostFlag = false;
                    if (hostList != null && hostList.size() > 0) {
                        for (HostVO hostVo : hostList) {
                            ResHost rh = new ResHost(hostVo);
                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (managedHost.getUuid().equals(rh.getUuid())) {
                                // 处于关机状态的主机，只更新状态
                                if (WebConstants.ResHostStatus.OUTLINE.equals(rh.getStatus())) {
                                    rh = new ResHost();
                                    rh.setStatus(WebConstants.ResHostStatus.OUTLINE);
                                }
                                rh.setResHostSid(managedHost.getResHostSid());
                                WebUtil.prepareUpdateParams(rh);
                                this.resHostMapper.updateByPrimaryKeySelective(rh);
                                cloneHostList.remove(hostVo);
                                hostFlag = true;
                                break;
                            }
                        }
                        if (!hostFlag) {

                            // 删除主机
                            this.resHostService.deleteHostWithObjRes(managedHost.getResHostSid());
                            // 删除主机与存储关系
                            Criteria criteria = new Criteria();
                            criteria.put("resHostSidDelete", managedHost.getResHostSid());
                            this.resHostStorageMapper.deleteByParams(criteria);

                        }
                    }
                }
            }

            // 插入数据库没有的主机
            if (cloneHostList != null && cloneHostList.size() > 0) {
                for (HostVO hostVo : cloneHostList) {
                    ResHost rh = new ResHost(hostVo);
                    rh.setParentTopologySid(resVe.getResTopologySid());

                    // 插入主机
                    WebUtil.prepareInsertParams(rh);
                    this.resHostMapper.insertSelective(rh);
                }
            }
            // 查询已插入数据库的主机，并返回
            Criteria criteria = new Criteria();
            criteria.put("resTopologySid", resVe.getResTopologySid());
            hosts = this.resHostMapper.selectByParamsForScan(criteria);
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "主机失败");
        }
        return hosts;
    }

    /**
     * 获取虚拟化环境CPU池
     *
     * @param resVe the res ve
     *
     * @return cpu pool by power ve
     *
     * @throws Exception the exception
     */
    List<ResCpuPool> getCpuPoolByPowerVe(ResVe resVe) throws Exception {
        List<ResCpuPool> resCpuPoolList = new ArrayList<ResCpuPool>();
        CpuPoolScan cpuPoolScan = new CpuPoolScan();
        cpuPoolScan.setProviderType(resVe.getVirtualPlatformType());
        cpuPoolScan.setAuthUrl(resVe.getManagementUrl());
        cpuPoolScan.setAuthUser(resVe.getManagementAccount());
        cpuPoolScan.setAuthPass(resVe.getManagementPassword());
        cpuPoolScan.setVirtEnvType(resVe.getVirtualPlatformType());
        cpuPoolScan.setVirtEnvUuid(resVe.getResTopologySid());

        Object rpc = MQHelper.rpc(cpuPoolScan);
        CpuPoolScanResult cpuPoolScanResult = (CpuPoolScanResult) rpc;
        if (cpuPoolScanResult == null) {
            logger.error("虚拟化环境（" + resVe.getResTopologyName() + "）CPU Pool扫描出错：扫描结果为空。");
            return resCpuPoolList;
        }
        if (cpuPoolScanResult.isSuccess()) {
            // 查询虚拟化环境下的CPU池
            List<ResCpuPool> manageCpuPoolVoList = this.resCpuPoolMapper.selectByPowerVe(resVe.getResTopologySid());

            // 主机列表
            List<CpuPoolVo> cpuPoolVoList = cpuPoolScanResult.getCpuPools();
            System.out.println(JsonUtil.toJson(cpuPoolVoList));
            // 克隆主机列表
            List<CpuPoolVo> clonecpuPoolVoList = null;
            if (cpuPoolVoList != null && cpuPoolVoList.size() > 0) {
                logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有CPU资源池个数：" + cpuPoolVoList.size());
                clonecpuPoolVoList = new ArrayList<CpuPoolVo>(cpuPoolVoList);
            }
            if (manageCpuPoolVoList != null && manageCpuPoolVoList.size() > 0) {

                // 已存在CPU资源池更新，否则插入
                for (ResCpuPool manageResCpuPool : manageCpuPoolVoList) {
                    boolean hostFlag = false;
                    if (cpuPoolVoList != null && cpuPoolVoList.size() > 0) {
                        for (CpuPoolVo cpuPoolVo : cpuPoolVoList) {
                            ResCpuPool cpuPool = new ResCpuPool(cpuPoolVo);
                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (manageResCpuPool.getUuid().equals(cpuPool.getUuid())) {
                                Criteria criteria = new Criteria();
                                criteria.put("hostName", cpuPoolVo.getHostName());
                                criteria.put("resTopologySid", resVe.getResTopologySid());
                                List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                                if (resHosts != null && resHosts.size() > 0) {
                                    cpuPool.setResHostSid(resHosts.get(0).getResHostSid());
                                }
                                // WebUtil.prepareUpdateParams(cpuPool);
                                this.resCpuPoolMapper.updateByPrimaryKeySelective(cpuPool);
                                clonecpuPoolVoList.remove(cpuPoolVo);
                                hostFlag = true;
                                break;
                            }
                        }
                        if (!hostFlag) {

                            // 删除CPU 池
                            this.resCpuPoolMapper.deleteByPrimaryKey(manageResCpuPool.getResCpuPoolSid());

                        }
                    }
                }
            }

            // 插入数据库没有的主机
            if (clonecpuPoolVoList != null && clonecpuPoolVoList.size() > 0) {
                for (CpuPoolVo cpuPoolVo : clonecpuPoolVoList) {
                    ResCpuPool cpuPool = new ResCpuPool(cpuPoolVo);
                    Criteria criteria = new Criteria();
                    criteria.put("hostName", cpuPoolVo.getHostName());
                    criteria.put("resTopologySid", resVe.getResTopologySid());
                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                    if (resHosts != null && resHosts.size() > 0) {
                        cpuPool.setResHostSid(resHosts.get(0).getResHostSid());
                    }
                    // 插入主机
                    // WebUtil.prepareInsertParams(cpuPool);
                    this.resCpuPoolMapper.insertSelective(cpuPool);
                }
            }

            // 查询虚拟化环境下的CPU池
            resCpuPoolList = this.resCpuPoolMapper.selectByPowerVe(resVe.getResTopologySid());
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "CPU资源池失败");
        }

        return resCpuPoolList;
    }

    /**
     * 获取虚拟化环境虚拟机交换机
     *
     * @param resVe the res ve
     *
     * @return v switch by power ve
     *
     * @throws Exception the exception
     */
    List<ResVs> getVSwitchByPowerVe(ResVe resVe) throws Exception {

        List<ResVs> resVsList = new ArrayList<ResVs>();

        VswitchScan vSwitchscan = new VswitchScan();
        vSwitchscan.setProviderType(resVe.getVirtualPlatformType());
        vSwitchscan.setAuthUrl(resVe.getManagementUrl());
        vSwitchscan.setAuthUser(resVe.getManagementAccount());
        vSwitchscan.setAuthPass(resVe.getManagementPassword());
        vSwitchscan.setVirtEnvType(resVe.getVirtualPlatformType());
        vSwitchscan.setVirtEnvUuid(resVe.getResTopologySid());

        Object rpc = MQHelper.rpc(vSwitchscan);
        VswitchScanResult vSwitchscanResult = (VswitchScanResult) rpc;

        if (vSwitchscanResult == null) {
            logger.error("虚拟化环境（" + resVe.getResTopologyName() + "）虚拟机交换机扫描出错：扫描结果为空。");
            return resVsList;
        }
        if (vSwitchscanResult.isSuccess()) {
            Criteria example1 = new Criteria();
            example1.put("parentTopologySid", resVe.getResTopologySid());
            List<ResVs> managedResVsList = this.resVsMapper.selectByParams(example1);

            // 虚拟机换机
            List<VSwitchVO> vSwitchVoList = vSwitchscanResult.getvSwitchs();
            System.out.println(JsonUtil.toJson(vSwitchVoList));
            // 克隆虚拟机换机
            List<VSwitchVO> cloneVSwitchVoList = null;
            if (vSwitchVoList != null && vSwitchVoList.size() > 0) {
                logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有虚拟交换机个数：" + vSwitchVoList.size());
                cloneVSwitchVoList = new ArrayList<VSwitchVO>(vSwitchVoList);
            }
            if (managedResVsList != null && managedResVsList.size() > 0) {

                // 已存在虚拟机换机更新，否则插入
                for (ResVs managedResVs : managedResVsList) {
                    boolean vsFlag = false;
                    if (vSwitchVoList != null && vSwitchVoList.size() > 0) {
                        for (VSwitchVO vSwitchVO : vSwitchVoList) {
                            ResVs resVs = new ResVs(vSwitchVO);
                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (vSwitchVO.getUuid().equals(managedResVs.getUuid())) {

                                // 更新虚拟交换机
                                resVs.setResVsSid(managedResVs.getResVsSid());
                                resVs.setParentTopologySid(resVe.getResTopologySid());
                                WebUtil.prepareUpdateParams(resVs);
                                this.resVsMapper.updateByPrimaryKeySelective(resVs);

                                // 删除虚拟交换机与主机关系
                                ResVsHost resVsHost = new ResVsHost();
                                resVsHost.setResVsSid(managedResVs.getResVsSid());
                                this.resVsHostMapper.deleteByResVsSid(resVsHost);

                                // 插入虚拟交换机与主机关系
                                Criteria criteria3 = new Criteria();
                                criteria3.put("hostName", vSwitchVO.getHostName());
                                criteria3.put("resTopologySid", resVe.getResTopologySid());
                                List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria3);
                                if (resHosts != null && resHosts.size() > 0) {
                                    ResVsHost newResVsHost = new ResVsHost();
                                    newResVsHost.setResHostSid(resHosts.get(0).getResHostSid());
                                    newResVsHost.setResVsSid(resVs.getResVsSid());
                                    this.resVsHostMapper.insertSelective(newResVsHost);
                                }
                                cloneVSwitchVoList.remove(vSwitchVO);
                                vsFlag = true;
                                break;
                            }
                        }
                        if (!vsFlag) {

                            // 删除CPU 池
                            this.resVsMapper.deleteByPrimaryKey(managedResVs.getResVsSid());

                            // 删除虚拟交换机与主机关系
                            ResVsHost resVsHost = new ResVsHost();
                            resVsHost.setResVsSid(managedResVs.getResVsSid());
                            this.resVsHostMapper.deleteByResVsSid(resVsHost);
                        }
                    }
                }
            }

            // 插入数据库没有的主机
            if (cloneVSwitchVoList != null && cloneVSwitchVoList.size() > 0) {
                for (VSwitchVO vSwitchVO : cloneVSwitchVoList) {
                    ResVs resVs = new ResVs(vSwitchVO);
                    resVs.setParentTopologySid(resVe.getResTopologySid());
                    // 插入主虚拟交换机
                    WebUtil.prepareInsertParams(resVs);
                    this.resVsMapper.insertSelective(resVs);

                    // 插入虚拟交换机与主机关系
                    Criteria criteria3 = new Criteria();
                    criteria3.put("hostName", vSwitchVO.getHostName());
                    criteria3.put("resTopologySid", resVe.getResTopologySid());
                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria3);
                    if (resHosts != null && resHosts.size() > 0) {
                        ResVsHost newResVsHost = new ResVsHost();
                        newResVsHost.setResHostSid(resHosts.get(0).getResHostSid());
                        newResVsHost.setResVsSid(resVs.getResVsSid());
                        this.resVsHostMapper.insertSelective(newResVsHost);
                    }
                }
            }

            Criteria example11 = new Criteria();
            example11.put("parentTopologySid", resVe.getResTopologySid());
            resVsList = this.resVsMapper.selectByParams(example11);
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "主机失败");
        }

        return resVsList;
    }

    /**
     * 调用接口根据主机查询虚拟机
     *
     * @param resVe the res ve
     *
     * @return vms by power ve
     *
     * @throws Exception the exception
     */
    public List<ResVm> getVmsByPowerVe(ResVe resVe) throws Exception {

        List<ResVm> resVms = new ArrayList<ResVm>();
        MparsScan vmScan = new MparsScan();
        vmScan.setProviderType(resVe.getVirtualPlatformType());
        vmScan.setAuthUrl(resVe.getManagementUrl());
        vmScan.setAuthUser(resVe.getManagementAccount());
        vmScan.setAuthPass(resVe.getManagementPassword());
        vmScan.setVirtEnvType(resVe.getVirtualPlatformType());
        vmScan.setVirtEnvUuid(resVe.getResTopologySid());

        Object rpc = MQHelper.rpc(vmScan);
        MparsScanResult result = (MparsScanResult) rpc;
        if (result == null) {
            logger.error("虚拟化环境（" + resVe.getResTopologyName() + "）虚拟机扫描出错：扫描结果为空。");
            return resVms;
        }

        List<MparVO> resVmList = result.getVms();
        System.out.println("VM扫描结果：" + JsonUtil.toJson(resVmList));
        // 查询虚拟化环境下的VIOS
        List<ResVios> managedViosList = this.resViosMapper.selectByResVe(new Criteria().put("resVeSid",
                                                                                            resVe.getResTopologySid()
        ));
        // 查询所有数据库中虚拟机
        List<ResVm> managedVmList = new ArrayList<ResVm>();
        managedVmList = this.resVmMapper.selectByPowerVe(resVe.getResTopologySid());
        System.out.println("VM查询结果（无VIOS）：" + JsonUtil.toJson(managedVmList));

        // 转换JSON为List对象
        // List<Map<String, Object>> vmList =
        // JsonUtil.fromJson(vmResult.getContent(), List.class);
        // Clone虚拟机列表
        List<MparVO> cloneVmList = null;
        if (resVmList != null && resVmList.size() > 0) {
            logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有虚拟机个数：" + resVmList.size());
            cloneVmList = new ArrayList<MparVO>(resVmList);
        }
        if (managedViosList != null && managedViosList.size() > 0) {
            for (ResVios managedVios : managedViosList) {
                boolean viosFlag = false;
                if (resVmList != null && resVmList.size() > 0) {
                    // 扫描主机下存在虚拟机时
                    for (MparVO resVmVo : resVmList) {
                        if ("vioserver".equals(resVmVo.getLparEnv())) {
                            ResVios resVios = new ResVios(resVmVo);
                            //							//查询主机
                            //							ResHost resHost=this.resHostMapper.selectByPrimaryKey(managedVios.getResHostSid());
                            // 虚拟机转换
                            if (!StringUtil.isNullOrEmpty(managedVios.getUuid())) {
                                if (managedVios.getUuid().equals(resVios.getUuid())) {
                                    Criteria criteria = new Criteria();
                                    criteria.put("hostName", resVmVo.getHostName());
                                    criteria.put("resTopologySid", resVe.getResTopologySid());
                                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                                    if (resHosts != null && resHosts.size() > 0) {
                                        resVios.setResHostSid(resHosts.get(0).getResHostSid());
                                    }
                                    // 更新虚拟机数据
                                    this.resViosMapper.updateByPrimaryKeySelective(resVios);

                                    // 如果PROC_MODE=shared，需要建立虚机与所使用cpu共享池的对应关系
                                    if ("shared".equalsIgnoreCase(resVmVo.getProcMode())) {
                                        // 更新虚拟机与cpu池关系
                                        ResCpuPoolMparRel poolMparRel = new ResCpuPoolMparRel(resVmVo);
                                        poolMparRel.setResParSid(managedVios.getResViosSid());
                                        this.setCpuPoolSid(resVmVo, poolMparRel);

                                        Criteria param = new Criteria();
                                        param.put("resParSid", managedVios.getResViosSid());

                                        this.resCpuPoolMparRelMapper.deleteByParams(param);
                                        this.resCpuPoolMparRelMapper.insertSelective(poolMparRel);
                                    }

                                    // 更新网络
                                    if (resVios.getIp() != null && !"".equals(resVios.getIp()) &&
                                            !"null".equals(resVios.getIp())) {
                                        resVios.setResViosSid(managedVios.getResViosSid());
                                        this.updateVmNetByVm(resVios);
                                    }
                                    cloneVmList.remove(resVmVo);
                                    viosFlag = true;
                                    break;
                                }
                            }
                        }

                    }
                    if (!viosFlag) {

                        // 删除
                        this.resViosMapper.deleteByPrimaryKey(managedVios.getResViosSid());

                        // 删除cpu池和虚拟机的关系
                        Criteria param = new Criteria();
                        param.put("resParSid", managedVios.getResViosSid());
                        this.resCpuPoolMparRelMapper.deleteByParams(param);

                        // 删除ip网络设置
                        param.put("resVmSid", managedVios.getResViosSid());
                        this.resVmNetworkMapper.deleteByParams(param);
                    }
                } else {
                    // 删除
                    this.resViosMapper.deleteByPrimaryKey(managedVios.getResViosSid());

                    // 删除cpu池和虚拟机的关系
                    Criteria param = new Criteria();
                    param.put("resParSid", managedVios.getResViosSid());
                    this.resCpuPoolMparRelMapper.deleteByParams(param);

                    // 删除ip网络设置
                    param.put("resVmSid", managedVios.getResViosSid());
                    this.resVmNetworkMapper.deleteByParams(param);
                }
            }
        }
        if (managedVmList != null && managedVmList.size() > 0) {
            // 已存在虚拟机更新虚拟机、ip、虚拟磁盘表，否则插入
            for (ResVm managedVm : managedVmList) {
                boolean vmFlag = false;
                if (resVmList != null && resVmList.size() > 0) {
                    // 扫描主机下存在虚拟机时
                    for (MparVO resVmVo : resVmList) {
                        if (!"vioserver".equals(resVmVo.getLparEnv())) {

                            // 虚拟机转换
                            ResVm resVm = new ResVm(resVmVo);
                            //查询主机
                            //							ResHost resHost=this.resHostMapper.selectByPrimaryKey(managedVm.getAllocateResHostSid());
                            if (!StringUtil.isNullOrEmpty(managedVm.getUuid())) {
                                if (managedVm.getUuid().equals(resVm.getUuid())) {
                                    Criteria criteria = new Criteria();
                                    criteria.put("hostName", resVmVo.getHostName());
                                    criteria.put("resTopologySid", resVe.getResTopologySid());
                                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                                    if (resHosts != null && resHosts.size() > 0) {
                                        resVm.setAllocateResHostSid(resHosts.get(0).getResHostSid());
                                    }
                                    // 更新虚拟机数据
                                    resVm.setResVmSid(managedVm.getResVmSid());
                                    resVm.setUseCpuGhz(Double.parseDouble(StringUtil.nullToEmpty(resVm.getCpuUsage())));
                                    resVm.setUseMemorySize(resVm.getMemUsage());
                                    // 更新虚拟机的时候不更新系统版本
                                    resVm.setOsVersion(null);
                                    WebUtil.prepareUpdateParams(resVm);
                                    this.resVmMapper.updateByPrimaryKeySelective(resVm);

                                    // 如果PROC_MODE=shared，需要建立虚机与所使用cpu共享池的对应关系
                                    if ("shared".equalsIgnoreCase(resVmVo.getProcMode())) {
                                        // 更新虚拟机与cpu池关系
                                        ResCpuPoolMparRel poolMparRel = new ResCpuPoolMparRel(resVmVo);
                                        poolMparRel.setResParSid(managedVm.getResVmSid());
                                        this.setCpuPoolSid(resVmVo, poolMparRel);

                                        Criteria param = new Criteria();
                                        param.put("resParSid", managedVm.getResVmSid());

                                        this.resCpuPoolMparRelMapper.deleteByParams(param);
                                        this.resCpuPoolMparRelMapper.insertSelective(poolMparRel);
                                    }

                                    // 更新Ip网络设置
                                    this.resVmService.updateVmNetByVm(resVm);

                                    cloneVmList.remove(resVmVo);

                                    vmFlag = true;
                                    break;
                                }
                            } else {
                                if (managedVm.getVmName().equals(resVm.getVmName())) {
                                    vmFlag = true;
                                    cloneVmList.remove(resVmVo);
                                    break;
                                } else {
                                    if (WebConstants.ResVmStatus.CREATING.equals(managedVm.getStatus())) {
                                        vmFlag = true;
                                    }
                                }
                            }
                        }

                    }
                    if (!vmFlag) {

                        // 删除
                        this.resVmMapper.deleteByPrimaryKey(managedVm.getResVmSid());
                        // 删除cpu池和虚拟机的关系
                        Criteria param = new Criteria();
                        param.put("resParSid", managedVm.getResVmSid());
                        this.resCpuPoolMparRelMapper.deleteByParams(param);

                        // 删除ip网络设置
                        param.put("resVmSid", managedVm.getResVmSid());
                        this.resVmNetworkMapper.deleteByParams(param);
                    }
                } else {
                    if (StringUtil.isNullOrEmpty(managedVm.getUuid()) &&
                            WebConstants.ResVmStatus.CREATING.equals(managedVm.getStatus())) {
                        continue;
                    } else {
                        // 删除
                        this.resVmMapper.deleteByPrimaryKey(managedVm.getResVmSid());
                        // 删除cpu池和虚拟机的关系
                        Criteria param = new Criteria();
                        param.put("resParSid", managedVm.getResVmSid());
                        this.resCpuPoolMparRelMapper.deleteByParams(param);

                        // 删除ip网络设置
                        param.put("resVmSid", managedVm.getResVmSid());
                        this.resVmNetworkMapper.deleteByParams(param);
                    }
                }
            }
        }

        // 插入数据库不存在的虚拟机
        if (cloneVmList != null && cloneVmList.size() > 0) {
            for (MparVO resVmVo : cloneVmList) {
                String vmSid;
                if ("vioserver".equals(resVmVo.getLparEnv())) {
                    ResVios resVios = new ResVios(resVmVo);
                    Criteria criteria = new Criteria();
                    criteria.put("hostName", resVmVo.getHostName());
                    criteria.put("resTopologySid", resVe.getResTopologySid());
                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                    if (resHosts != null && resHosts.size() > 0) {
                        resVios.setResHostSid(resHosts.get(0).getResHostSid());
                    }
                    // 更新虚拟机数据
                    this.resViosMapper.insertSelective(resVios);

                    // 更新网络
                    this.updateVmNetByVm(resVios);

                    vmSid = resVios.getResViosSid();
                } else {
                    // 虚拟机转换
                    ResVm resVm = new ResVm(resVmVo);

                    Criteria criteria = new Criteria();
                    criteria.put("hostName", resVmVo.getHostName());
                    criteria.put("resTopologySid", resVe.getResTopologySid());
                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                    if (resHosts != null && resHosts.size() > 0) {
                        resVm.setAllocateResHostSid(resHosts.get(0).getResHostSid());
                    }
                    WebUtil.prepareInsertParams(resVm);
                    this.resVmMapper.insertSelective(resVm);

                    vmSid = resVm.getResVmSid();

                    // 更新网络
                    this.resVmService.updateVmNetByVm(resVm);

                    // 更新磁盘
                    //					List<ResVd> resVdList = resVm.getResVdList();
                    //					this.getVdsByVm(resVm, resVe, resVdList);
                }

                if ("shared".equalsIgnoreCase(resVmVo.getProcMode())) {
                    // 更新虚拟机与cpu池关系
                    ResCpuPoolMparRel poolMparRel = new ResCpuPoolMparRel(resVmVo);
                    poolMparRel.setResParSid(vmSid);
                    this.setCpuPoolSid(resVmVo, poolMparRel);
                    this.resCpuPoolMparRelMapper.insertSelective(poolMparRel);
                }
            }
        }

        // 更新任务日志
        // taskLogger.update(log);

        return resVms;
    }


    /**
     * 更新网络配置
     *
     * @param resVios the res vios
     *
     * @return boolean boolean
     */
    private boolean updateVmNetByVm(ResVios resVios) {
        // 查询所属网络
        Criteria ipParam = new Criteria();
        ipParam.put("ipAddress", resVios.getIp());
        List<ResIp> resIps = this.resIpMapper.selectByParams(ipParam);

        ResVmNetwork resVmNet = new ResVmNetwork();
        resVmNet.setResVmSid(resVios.getResViosSid());
        resVmNet.setIpAddress(resVios.getIp());
        resVmNet.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
        if (resIps != null && resIps.size() > 0) {
            resVmNet.setResNetworkSid(resIps.get(0).getResNetworkSid());
        }
        // 数据库中有则更新没有则插入
        int updateResult = resVmNetworkMapper.updateByPrimaryKeySelective(resVmNet);
        if (updateResult == 0) {
            resVmNetworkMapper.insertSelective(resVmNet);
        }

        List<ResVmNetwork> resVmNetworks = resVmNetworkMapper.selectByVmSid(resVios.getResViosSid());
        if (resVmNetworks.size() > 0) {
            // 删除数据库中多余的ip
            for (ResVmNetwork resVmNetwork : resVmNetworks) {
                if (!resVmNetwork.getIpAddress().equals(resVios.getIp())) {
                    Criteria criteria = new Criteria();
                    criteria.put("ipAddress", resVmNetwork.getIpAddress());
                    criteria.put("resVmSid", resVios.getResViosSid());
                    resVmNetworkMapper.deleteByParams(criteria);
                }
            }
        }
        return true;
    }

    /**
     * 设定 cpu pool sid.
     *
     * @param resVmVo     the res vm vo
     * @param poolMparRel the pool mpar rel
     */
    private void setCpuPoolSid(MparVO resVmVo, ResCpuPoolMparRel poolMparRel) {
        Criteria cpuParam = new Criteria();
        // 主机CPU池UUID	${SERV_SN}#${SHARED_PROC_POOL_ID}
        cpuParam.put("uuid", resVmVo.getServerSn() + "#" + resVmVo.getSharedProcPoolID());
        List<ResCpuPool> cpuList = this.resCpuPoolMapper.selectByParams(cpuParam);
        if (cpuList != null && !cpuList.isEmpty()) {
            poolMparRel.setResCpuPoolSid(cpuList.get(0).getResCpuPoolSid());
        }
    }

    /**
     * 获取SSP存储
     *
     * @param resVe       the res ve
     * @param resViosList the res vios list
     *
     * @throws Exception the exception
     */
    public void getStorageByPowerVe(ResVe resVe, List<ResVios> resViosList) throws Exception {
        SSPScan sspScan = new SSPScan();

        sspScan.setProviderType(resVe.getVirtualPlatformType());
        sspScan.setAuthUrl(resVe.getManagementUrl());
        sspScan.setAuthUser(resVe.getManagementAccount());
        sspScan.setAuthPass(resVe.getManagementPassword());
        sspScan.setVirtEnvType(resVe.getVirtualPlatformType());
        sspScan.setVirtEnvUuid(resVe.getResTopologySid());

        List<ViosVo> viosVos = new ArrayList<ViosVo>();
        for (ResVios resVios : resViosList) {
            ViosVo viosVo = new ViosVo();
            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVios.getResHostSid());
            viosVo.setViosHostName(resHost.getHostName());
            viosVo.setViosId(resVios.getViosLparId());
            viosVo.setViosIp(resVios.getIp());
            viosVo.setViosName(resVios.getViosLparName());
            viosVo.setViosPwd(resVios.getPassword());
            viosVo.setViosUser(resVios.getUser());
            viosVos.add(viosVo);

        }
        sspScan.setVisoList(viosVos);
        Object rpc = MQHelper.rpc(sspScan);
        SSPScanResult sspScanResult = (SSPScanResult) rpc;
        if (sspScanResult == null) {
            logger.error("虚拟化环境（" + resVe.getResTopologyName() + "）SSP存储扫描出错：扫描结果为空。");
            return;
        }
        if (sspScanResult.isSuccess()) {
            // 查询虚拟化环境下的CPU池
            Criteria criteria = new Criteria();
            criteria.put("parentTopologySid", resVe.getResTopologySid());
            // 只更新系统盘
            criteria.put("storagePurpose", WebConstants.StoragePurpose.SYSTEM_DISK);
            List<ResStorage> manageStorageList = this.resStorageMapper.selectByParams(criteria);

            // 主机列表
            List<SSPVo> sspVos = sspScanResult.getSspVos();
            System.out.println(JsonUtil.toJson(sspVos));
            // 克隆主机列表
            List<SSPVo> cloneSspVos = null;
            if (sspVos != null && sspVos.size() > 0) {
                logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有存储池个数：" + sspVos.size());
                cloneSspVos = new ArrayList<SSPVo>(sspVos);
            }
            if (manageStorageList != null && manageStorageList.size() > 0) {

                // 已存在CPU资源池更新，否则插入
                for (ResStorage resStorage : manageStorageList) {
                    boolean storageFlag = false;
                    if (sspVos != null && sspVos.size() > 0) {
                        for (SSPVo sspVo : sspVos) {
                            ResStorage resSto = new ResStorage(sspVo);
                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (resStorage.getUuid().equals(resSto.getUuid())) {
                                Criteria criteria1 = new Criteria();
                                criteria1.put("hostName", sspVo.getFrameHost());
                                criteria1.put("resTopologySid", resVe.getResTopologySid());
                                List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria1);
                                if (resHosts != null && resHosts.size() > 0) {
                                    resSto.setResHostSid(resHosts.get(0).getResHostSid());
                                }
                                resSto.setResStorageSid(resStorage.getResStorageSid());
                                WebUtil.prepareUpdateParams(resSto);
                                this.resStorageMapper.updateByPrimaryKeySelective(resSto);

                                // TODO 现在为单个VIOS处理，只有一台对应主机
                                // 删除存储与主机关系
                                if (resHosts != null && resHosts.size() > 0) {
                                    this.resHostStorageMapper.deleteByParams(new Criteria().put("resHostSid",
                                                                                                resHosts.get(0)
                                                                                                        .getResHostSid()
                                    ));

                                    ResHostStorage resHostStorage = new ResHostStorage();
                                    resHostStorage.setResStorageSid(resSto.getResStorageSid());
                                    resHostStorage.setResHostSid(resHosts.get(0).getResHostSid());
                                    this.resHostStorageMapper.insertSelective(resHostStorage);
                                }


                                // 查询vios
                                Criteria criteria2 = new Criteria();
                                criteria2.put("viosLparId", sspVo.getViosLparId());
                                criteria2.put("viosLparName", sspVo.getViosLparName());
                                List<ResVios> resVioses = this.resViosMapper.selectByParams(criteria2);
                                if (resVioses != null && resVioses.size() > 0) {
                                    // 删除VIOS与存储关系
                                    this.resStorageViosRelMapper.deleteByParams(new Criteria().put("resViosSid",
                                                                                                   resVioses.get(0)
                                                                                                            .getResViosSid()
                                    ));
                                    ResStorageViosRel rel = new ResStorageViosRel();
                                    rel.setResStorageSid(resSto.getResStorageSid());
                                    rel.setResViosSid(resVioses.get(0).getResViosSid());
                                    this.resStorageViosRelMapper.insertSelective(rel);
                                }
                                cloneSspVos.remove(sspVo);
                                storageFlag = true;
                                break;
                            }
                        }
                        if (!storageFlag) {
                            // FIXME 由于无法扫描到，暂时不做删除操作
                            //							// 删除存储
                            //							this.resStorageMapper.deleteByPrimaryKey(resStorage.getResStorageSid());
                            //
                            //							// 删除VIOS与存储关系
                            //							this.resStorageViosRelMapper.deleteByParams(
                            //									new Criteria().put("resStorageSid", resStorage.getResStorageSid()));
                        }
                    }
                }
            }

            // 插入数据库没有的主机
            if (cloneSspVos != null && cloneSspVos.size() > 0) {
                for (SSPVo sspVo : cloneSspVos) {
                    ResStorage resSto = new ResStorage(sspVo);
                    // 数据库存在的主机，则更新并从clone的主机集合中剔除
                    Criteria criteria1 = new Criteria();
                    criteria1.put("hostName", sspVo.getFrameHost());
                    criteria1.put("resTopologySid", resVe.getResTopologySid());
                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria1);
                    if (resHosts != null && resHosts.size() > 0) {
                        resSto.setResHostSid(resHosts.get(0).getResHostSid());
                    }
                    WebUtil.prepareInsertParams(resSto);
                    resSto.setParentTopologySid(resVe.getResTopologySid());
                    this.resStorageMapper.insertSelective(resSto);

                    // 新建存储与主机关系
                    if (resHosts != null && resHosts.size() > 0) {
                        ResHostStorage resHostStorage = new ResHostStorage();
                        resHostStorage.setResStorageSid(resSto.getResStorageSid());
                        resHostStorage.setResHostSid(resHosts.get(0).getResHostSid());
                        this.resHostStorageMapper.insertSelective(resHostStorage);
                    }

                    // 查询vios
                    Criteria criteria2 = new Criteria();
                    criteria2.put("viosLparId", sspVo.getViosLparId());
                    criteria2.put("viosLparName", sspVo.getViosLparName());
                    List<ResVios> resVioses = this.resViosMapper.selectByParams(criteria2);
                    if (resVioses != null && resVioses.size() > 0) {
                        ResStorageViosRel rel = new ResStorageViosRel();
                        rel.setResStorageSid(resSto.getResStorageSid());
                        rel.setResViosSid(resVioses.get(0).getResViosSid());
                        this.resStorageViosRelMapper.insertSelective(rel);
                    }
                }
            }

        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "存储池失败");
        }
    }

    /**
     * 获取IO配件
     *
     * @param resVe the res ve
     *
     * @throws Exception the exception
     */
    public void getResHostItemByVe(ResVe resVe) throws Exception {
        IoScan ioScan = new IoScan();
        ioScan.setProviderType(resVe.getVirtualPlatformType());
        ioScan.setAuthUrl(resVe.getManagementUrl());
        ioScan.setAuthUser(resVe.getManagementAccount());
        ioScan.setAuthPass(resVe.getManagementPassword());
        ioScan.setVirtEnvType(resVe.getVirtualPlatformType());
        ioScan.setVirtEnvUuid(resVe.getResTopologySid());
        Object rpc = MQHelper.rpc(ioScan);
        IoScanResult ioScanResult = (IoScanResult) rpc;

        if (ioScanResult == null) {
            logger.error("虚拟化环境（" + resVe.getResTopologyName() + "）IO配件扫描出错：扫描结果为空。");
            return;
        }

        if (ioScanResult.isSuccess()) {
            // 查询虚拟化环境下的配件
            List<String> itemType = new ArrayList<String>();
            itemType.add(WebConstants.HostItemTypeCode.LOCAL_DISK);
            itemType.add(WebConstants.HostItemTypeCode.NETWORK_CARD);
            itemType.add(WebConstants.HostItemTypeCode.OPTICAL_CARD);
            List<ResHostItem> manageHostItemList = this.resHostItemMapper.selectByPowerVeForIo(new Criteria().put(
                    "resVeSid",
                    resVe.getResTopologySid()
            ).put("itemType", itemType));
            // 主机列表
            List<IoVo> ios = ioScanResult.getIos();
            System.out.println(JsonUtil.toJson(ios));
            // 克隆主机列表
            List<IoVo> cloneIoVoList = null;
            if (ios != null && ios.size() > 0) {
                logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有Io个数：" + ios.size());
                cloneIoVoList = new ArrayList<IoVo>(ios);
            }
            if (manageHostItemList != null && manageHostItemList.size() > 0) {

                // 已存在CPU资源池更新，否则插入
                for (ResHostItem hostItem : manageHostItemList) {
                    boolean hostFlag = false;
                    if (ios != null && ios.size() > 0) {
                        for (IoVo ioVo : ios) {
                            // 扫描无法识别的配件，不加入资源
                            if ("0".equals(ioVo.getIoType())) {
                                cloneIoVoList.remove(ioVo);
                                continue;
                            }
                            ResHostItem resHostItem = new ResHostItem(ioVo);
                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (hostItem.getUuid().equals(resHostItem.getUuid())) {
                                Criteria criteria = new Criteria();
                                criteria.put("hostName", ioVo.getHostName());
                                criteria.put("resTopologySid", resVe.getResTopologySid());
                                List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                                if (resHosts != null && resHosts.size() > 0) {
                                    resHostItem.setResHostSid(resHosts.get(0).getResHostSid());
                                }
                                resHostItem.setHostItemId(hostItem.getHostItemId());

                                // 查询关联的虚拟机，并设置ID
                                if (WebConstants.HostItemAllocStatus.OCCUPY == resHostItem.getResAllocStatus()) {
                                    // ${SERV_SN}#${LPAR_NAME} 虚拟机UUID规则
                                    // TODO if DISK then vd/storage sid
                                    String vmUuid = ioVo.getServerSn() + "#" + ioVo.getLparName();
                                    String vmSid = this.resVmMapper.selectByUuidInVmVios(vmUuid);
                                    resHostItem.setRelateResSid(vmSid);
                                } else {
                                    // 未被占用的情况下，将关联的SID也清空（避免脏数据）
                                    resHostItem.setRelateResSid("");
                                }

                                this.resHostItemMapper.updateByPrimaryKeySelective(resHostItem);
                                cloneIoVoList.remove(ioVo);
                                hostFlag = true;
                                break;
                            }
                        }
                        if (!hostFlag) {

                            // 判断是否为DISK卡，如果是，则删除VD
                            if (WebConstants.HostItemTypeCode.LOCAL_DISK.equals(hostItem.getHostItemTypeCode())) {
                                // 创建物理分区的时候，磁盘卡的UUID设置为该VD的uuid
                                this.resVdMapper.deleteByParams(new Criteria().put("uuid", hostItem.getUuid()));
                            }
                            // 删除HostItem
                            this.resHostItemMapper.deleteByPrimaryKey(hostItem.getHostItemId());

                        }
                    }
                }
            }

            // 插入数据库没有的主机
            if (cloneIoVoList != null && cloneIoVoList.size() > 0) {
                for (IoVo ioVo : cloneIoVoList) {
                    // 扫描无效，跳过插入
                    if ("0".equals(ioVo.getIoType())) {
                        continue;
                    }
                    ResHostItem resHostItem = new ResHostItem(ioVo);
                    // 数据库存在的主机，则更新并从clone的主机集合中剔除
                    Criteria criteria = new Criteria();
                    criteria.put("hostName", ioVo.getHostName());
                    criteria.put("resTopologySid", resVe.getResTopologySid());
                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                    if (resHosts != null && resHosts.size() > 0) {
                        resHostItem.setResHostSid(resHosts.get(0).getResHostSid());
                    }

                    // 查询关联的虚拟机，并设置ID
                    if (WebConstants.HostItemAllocStatus.OCCUPY == resHostItem.getResAllocStatus()) {
                        // ${SERV_SN}#${LPAR_NAME} 虚拟机UUID规则
                        String vmUuid = ioVo.getServerSn() + "#" + ioVo.getLparName();
                        String vmSid = this.resVmMapper.selectByUuidInVmVios(vmUuid);
                        resHostItem.setRelateResSid(vmSid);
                    }
                    this.resHostItemMapper.insertSelective(resHostItem);
                }
            }

        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "IO配件失败");
        }
    }

    /**
     * IO配件端口扫描
     *
     * @param resVe the res ve
     *
     * @throws Exception the exception
     */
    public void getResHostItemSlotByVe(ResVe resVe) throws Exception {
        IOSlotScan ioSlotScan = new IOSlotScan();
        ioSlotScan.setProviderType(resVe.getVirtualPlatformType());
        ioSlotScan.setAuthUrl(resVe.getManagementUrl());
        ioSlotScan.setAuthUser(resVe.getManagementAccount());
        ioSlotScan.setAuthPass(resVe.getManagementPassword());
        ioSlotScan.setVirtEnvType(resVe.getVirtualPlatformType());
        ioSlotScan.setVirtEnvUuid(resVe.getResTopologySid());

        Object rpc = MQHelper.rpc(ioSlotScan);

        IOSlotScanResult ioSlotScanResult = (IOSlotScanResult) rpc;

        if (ioSlotScanResult == null) {
            logger.error("虚拟化环境（" + resVe.getResTopologyName() + "）IO配件端口扫描出错：扫描结果为空。");
            return;
        }

        if (ioSlotScanResult.isSuccess()) {
            // 查询虚拟化环境下的IO端口
            List<String> slotType = new ArrayList<String>();
            slotType.add(WebConstants.HostItemTypeCode.DISK);
            slotType.add(WebConstants.HostItemTypeCode.ETHERNET);
            slotType.add(WebConstants.HostItemTypeCode.FC);
            List<ResHostItem> manageHostItemList = this.resHostItemMapper.selectByPowerVeForIo(new Criteria().put(
                    "resVeSid",
                    resVe.getResTopologySid()
            ).put("itemType", slotType));

            // IO端口列表
            List<IOSlotVo> ioSlots = ioSlotScanResult.getIoSlotVos();
            System.out.println(JsonUtil.toJson(ioSlots));
            // 克隆IO端口列表
            List<IOSlotVo> cloneIoSlotList = null;
            if (ioSlots != null && ioSlots.size() > 0) {
                logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有IO配件端口数：" + ioSlots.size());
                cloneIoSlotList = new ArrayList<IOSlotVo>(ioSlots);
            }
            if (manageHostItemList != null && manageHostItemList.size() > 0) {

                // 已存在CPU资源池更新，否则插入
                for (ResHostItem hostItem : manageHostItemList) {
                    boolean hostFlag = false;
                    if (ioSlots != null && ioSlots.size() > 0) {
                        for (IOSlotVo ioSlot : ioSlots) {
                            ResHostItem resHostItem = new ResHostItem(ioSlot);
                            // 数据库存在的端口，则更新并从clone的端口集合中剔除
                            if (hostItem.getUuid().equals(resHostItem.getUuid())) {
                                Criteria criteria = new Criteria();
                                criteria.put("hostName", ioSlot.getFrameName());
                                criteria.put("resTopologySid", resVe.getResTopologySid());
                                List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                                if (resHosts != null && resHosts.size() > 0) {
                                    resHostItem.setResHostSid(resHosts.get(0).getResHostSid());
                                }
                                resHostItem.setHostItemId(hostItem.getHostItemId());

                                // 更新所属配件的关联关系
                                criteria = new Criteria();
                                criteria.put("itemLocation", ioSlot.getParentPhysLoc());
                                List<ResHostItem> parentHostItem = this.resHostItemMapper.selectByParams(criteria);
                                if (parentHostItem.isEmpty()) {
                                    if (WebConstants.HostItemTypeCode.DISK.equals(ioSlot.getSlotChildren())) {
                                        // 当前规则，磁盘为3级挂载，跳过第二级配件，关联第三级和第一级
                                        criteria.put("itemLocation",
                                                     ioSlot.getParentPhysLoc().replaceAll("-\\w+$", "")
                                        );
                                        parentHostItem = this.resHostItemMapper.selectByParams(criteria);
                                    }
                                }
                                if (!parentHostItem.isEmpty()) {
                                    resHostItem.setSupHostItemId(parentHostItem.get(0).getHostItemId());
                                    resHostItem.setRelateResSid(parentHostItem.get(0).getRelateResSid());
                                }
                                this.resHostItemMapper.updateByPrimaryKeySelective(resHostItem);
                                cloneIoSlotList.remove(ioSlot);
                                hostFlag = true;
                                break;
                            }
                        }
                        if (!hostFlag) {

                            // 删除CPU 池
                            this.resHostItemMapper.deleteByPrimaryKey(hostItem.getHostItemId());

                        }
                    }
                }
            }

            // 插入数据库没有的IO端口
            if (cloneIoSlotList != null && cloneIoSlotList.size() > 0) {
                for (IOSlotVo ioSlot : cloneIoSlotList) {
                    ResHostItem resHostItem = new ResHostItem(ioSlot);
                    // 新建端口
                    Criteria criteria = new Criteria();
                    criteria.put("hostName", ioSlot.getFrameName());
                    criteria.put("resTopologySid", resVe.getResTopologySid());
                    List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);
                    if (resHosts != null && resHosts.size() > 0) {
                        resHostItem.setResHostSid(resHosts.get(0).getResHostSid());
                    }

                    // 更新所属配件的关联关系
                    criteria = new Criteria();
                    criteria.put("itemLocation", ioSlot.getParentPhysLoc());
                    List<ResHostItem> parentHostItem = this.resHostItemMapper.selectByParams(criteria);
                    if (parentHostItem.isEmpty()) {
                        if (WebConstants.HostItemTypeCode.DISK.equals(ioSlot.getSlotChildren())) {
                            // 当前规则，磁盘为3级挂载，跳过第二级配件，关联第三级和第一级
                            criteria.put("itemLocation", ioSlot.getParentPhysLoc().replaceAll("-\\w+$", ""));
                            parentHostItem = this.resHostItemMapper.selectByParams(criteria);
                        }
                    }
                    if (!parentHostItem.isEmpty()) {
                        resHostItem.setSupHostItemId(parentHostItem.get(0).getHostItemId());
                        resHostItem.setRelateResSid(parentHostItem.get(0).getRelateResSid());
                    }
                    this.resHostItemMapper.insertSelective(resHostItem);
                }
            }

        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "IO配件端口失败");
        }
    }

    /**
     * 获取系统盘
     *
     * @param resVe       the res ve
     * @param resViosList the res vios list
     *
     * @throws Exception the exception
     */
    public void getSysDiskByPowerVe(ResVe resVe, List<ResVios> resViosList) throws Exception {
        DiskScan sysDiskScan = new DiskScan();

        sysDiskScan.setProviderType(resVe.getVirtualPlatformType());
        sysDiskScan.setAuthUrl(resVe.getManagementUrl());
        sysDiskScan.setAuthUser(resVe.getManagementAccount());
        sysDiskScan.setAuthPass(resVe.getManagementPassword());
        sysDiskScan.setVirtEnvType(resVe.getVirtualPlatformType());
        sysDiskScan.setVirtEnvUuid(resVe.getResTopologySid());

        List<ViosVo> viosVos = new ArrayList<ViosVo>();
        for (ResVios resVios : resViosList) {
            ViosVo viosVo = new ViosVo();
            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVios.getResHostSid());
            viosVo.setViosHostName(resHost.getHostName());
            viosVo.setViosId(resVios.getViosLparId());
            viosVo.setViosIp(resVios.getIp());
            viosVo.setViosName(resVios.getViosLparName());
            viosVo.setViosPwd(resVios.getPassword());
            viosVo.setViosUser(resVios.getUser());
            viosVos.add(viosVo);

        }
        sysDiskScan.setVisoList(viosVos);
        Object rpc = MQHelper.rpc(sysDiskScan);
        DiskScanResult sysDiskScanResult = (DiskScanResult) rpc;

        if (sysDiskScanResult == null) {
            logger.error("虚拟化环境（" + resVe.getResTopologyName() + "）系统盘扫描出错：扫描结果为空。");
            return;
        }

        if (sysDiskScanResult.isSuccess()) {
            // 查询虚拟化环境下的系统盘
            Criteria params = new Criteria();
            params.put("resViosSid", resViosList.get(0).getResViosSid())
                  .put("storagePurpose", WebConstants.StoragePurpose.SYSTEM_DISK);
            List<ResVd> resSysVdList = this.resVdMapper.selectByPowerVe(params);

            // 主机列表
            List<ViosResult> viosResultList = sysDiskScanResult.getViosVos();
            for (ViosResult viosResult : viosResultList) {
                List<SysDiskVo> sysDiskVos = viosResult.getDisks();
                System.out.println(JsonUtil.toJson(sysDiskVos));
                // 克隆主机列表
                List<SysDiskVo> cloneSysDiskVos = null;
                if (sysDiskVos != null && sysDiskVos.size() > 0) {
                    logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有系统盘个数：" + sysDiskVos.size());
                    cloneSysDiskVos = new ArrayList<SysDiskVo>(sysDiskVos);
                }
                if (resSysVdList != null && resSysVdList.size() > 0) {

                    // 已存在Disk资源更新，否则插入
                    for (ResVd managedResVd : resSysVdList) {
                        // 还在创建中的VD，不做同步
                        if (StringUtil.isNullOrEmpty(managedResVd.getUuid()) &&
                                WebConstants.ResVdStatus.CREATING.equals(managedResVd.getStatus())) {
                            continue;
                        }
                        boolean sysDiskFlag = false;
                        if (sysDiskVos != null && sysDiskVos.size() > 0) {
                            for (SysDiskVo sysDiskVo : sysDiskVos) {
                                ResVd resVd = new ResVd(sysDiskVo);
                                if (!StringUtil.isNullOrEmpty(managedResVd.getUuid())) {
                                    // 数据库存在的主机，则更新并从clone的主机集合中剔除
                                    if (managedResVd.getUuid().equals(resVd.getUuid())) {
                                        Criteria criteria1 = new Criteria();
                                        criteria1.put("storageName", sysDiskVo.getSspName());
                                        List<ResStorage> resStorages = this.resStorageMapper.selectByParams(criteria1);
                                        if (resStorages != null && resStorages.size() > 0) {
                                            resVd.setAllocateResStorageSid(resStorages.get(0).getResStorageSid());
                                        }

                                        // 查询虚拟机
                                        Criteria criteria = new Criteria();
                                        criteria.put("hostName", viosResult.getFrameHost());
                                        criteria.put("resTopologySid", resVe.getResTopologySid());
                                        List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);

                                        criteria = new Criteria();
                                        criteria.put("parId", sysDiskVo.getLaprId());
                                        criteria.put("allocateResHostSid", resHosts.get(0).getResHostSid());
                                        List<ResVm> resVmList = this.resVmMapper.selectByParams(criteria);
                                        if (resVmList != null && resVmList.size() > 0) {
                                            resVd.setResVmSid(resVmList.get(0).getResVmSid());
                                        }
                                        resVd.setResVdSid(managedResVd.getResVdSid());
                                        WebUtil.prepareUpdateParams(resVd);
                                        this.resVdMapper.updateByPrimaryKeySelective(resVd);

                                        cloneSysDiskVos.remove(sysDiskVo);
                                        sysDiskFlag = true;
                                        break;
                                    }
                                } else if (managedResVd.getVdName().equals(resVd.getVdName())) {
                                    // UUID不存在的情况下，再判断磁盘名称是否一致，将其排除在处理范围外
                                    sysDiskFlag = true;
                                    cloneSysDiskVos.remove(sysDiskVo);
                                    break;
                                } else if (WebConstants.ResVdStatus.CREATING.equals(managedResVd.getStatus())) {
                                    // 在创建中的，将其排除在同步范围外
                                    sysDiskFlag = true;
                                    break;
                                }

                            }
                            if (!sysDiskFlag) {
                                this.resVdMapper.deleteByPrimaryKey(managedResVd.getResVdSid());
                            }
                        }
                    }
                }

                // 插入数据库没有的主机
                if (cloneSysDiskVos != null && cloneSysDiskVos.size() > 0) {
                    for (SysDiskVo sysDiskVo : cloneSysDiskVos) {
                        ResVd resVd = new ResVd(sysDiskVo);
                        // 数据库存在的主机，则更新并从clone的主机集合中剔除
                        Criteria criteria1 = new Criteria();
                        criteria1.put("storageName", sysDiskVo.getSspName());
                        List<ResStorage> resStorages = this.resStorageMapper.selectByParams(criteria1);
                        if (resStorages != null && resStorages.size() > 0) {
                            resVd.setAllocateResStorageSid(resStorages.get(0).getResStorageSid());
                        }

                        // 查询虚拟机
                        Criteria criteria = new Criteria();
                        criteria.put("hostName", viosResult.getFrameHost());
                        criteria.put("resTopologySid", resVe.getResTopologySid());
                        List<ResHost> resHosts = this.resHostMapper.selectByParamsForScan(criteria);

                        criteria = new Criteria();
                        criteria.put("parId", sysDiskVo.getLaprId());
                        criteria.put("allocateResHostSid", resHosts.get(0).getResHostSid());
                        List<ResVm> resVmList = this.resVmMapper.selectByParams(criteria);
                        if (resVmList != null && resVmList.size() > 0) {
                            resVd.setResVmSid(resVmList.get(0).getResVmSid());
                        }
                        resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                        WebUtil.prepareInsertParams(resVd);
                        this.resVdMapper.insertSelective(resVd);
                    }
                }
            }
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "系统盘失败");
        }
    }

    /**
     * 获取数据盘
     *
     * @param resVe       the res ve
     * @param resViosList the res vios list
     *
     * @throws Exception the exception
     */
    public void getDataDiskByPowerVe(ResVe resVe, List<ResVios> resViosList) throws Exception {
        NpivScan npivScan = new NpivScan();

        npivScan.setProviderType(resVe.getVirtualPlatformType());
        npivScan.setAuthUrl(resVe.getManagementUrl());
        npivScan.setAuthUser(resVe.getManagementAccount());
        npivScan.setAuthPass(resVe.getManagementPassword());
        npivScan.setVirtEnvType(resVe.getVirtualPlatformType());
        npivScan.setVirtEnvUuid(resVe.getResTopologySid());

        List<ViosVo> viosVos = new ArrayList<ViosVo>();
        List<String> hosts = new ArrayList<String>();
        hosts.add("vios16-8406-70Y-SN067639B");
        for (ResVios resVios : resViosList) {
            ViosVo viosVo = new ViosVo();
            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVios.getResHostSid());
            hosts.add(resHost.getHostName());
            viosVo.setViosHostName(resHost.getHostName());
            viosVo.setViosId(resVios.getViosLparId());
            viosVo.setViosIp(resVios.getIp());
            viosVo.setViosName(resVios.getViosLparName());
            viosVo.setViosPwd(resVios.getPassword());
            viosVo.setViosUser(resVios.getUser());
            viosVos.add(viosVo);

        }
        npivScan.setViosList(viosVos);
        npivScan.setHosts(hosts);
        Object rpc = MQHelper.rpc(npivScan);
        NpivScanResult dataDiskScanResult = (NpivScanResult) rpc;
        if (dataDiskScanResult == null) {
            logger.error("虚拟化环境（" + resVe.getResTopologyName() + "）数据盘扫描出错：扫描结果为空。");
            return;
        }
        if (dataDiskScanResult.isSuccess()) {
            // 查询虚拟化环境下的系统盘
            List<ResVfc> resVfcList = this.resVfcMapper.selectByPowerVe(resVe.getResTopologySid());

            // 主机列表
            List<DataDiskVo> dataDiskVos = dataDiskScanResult.getDisks();
            System.out.println(JsonUtil.toJson(dataDiskVos));
            // 克隆主机列表
            List<DataDiskVo> cloneDataDiskVos = null;
            if (dataDiskVos != null && dataDiskVos.size() > 0) {
                logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下所有数据盘个数：" + dataDiskVos.size());
                cloneDataDiskVos = new ArrayList<DataDiskVo>(dataDiskVos);
            }
            if (resVfcList != null && resVfcList.size() > 0) {

                // 已存在CPU资源池更新，否则插入
                for (ResVfc managedResVfc : resVfcList) {
                    boolean dataDiskFlag = false;
                    if (dataDiskVos != null && dataDiskVos.size() > 0) {
                        for (DataDiskVo dataDiskVo : dataDiskVos) {
                            ResVfc resVfc = new ResVfc(dataDiskVo);
                            // 数据库存在的主机，则更新并从clone的主机集合中剔除
                            if (managedResVfc.getWwpn().equals(resVfc.getWwpn())) {
                                Criteria criteria1 = new Criteria();
                                criteria1.put("viosLparId", dataDiskVo.getLparId());
                                criteria1.put("viosLparName", dataDiskVo.getViosName());
                                List<ResVios> resVioss = this.resViosMapper.selectByParams(criteria1);
                                if (resVioss != null && resVioss.size() > 0) {
                                    resVfc.setResViosId(resVioss.get(0).getResViosSid());
                                }
                                resVfc.setResFcPortSid(managedResVfc.getResFcPortSid());
                                this.resVfcMapper.updateByPrimaryKeySelective(resVfc);

                                cloneDataDiskVos.remove(dataDiskVo);
                                dataDiskFlag = true;
                            }
                        }
                        if (!dataDiskFlag) {
                            this.resVfcMapper.deleteByPrimaryKey(managedResVfc.getResFcPortSid());
                        }
                    }
                }
            }

            // 插入数据库没有的主机
            if (cloneDataDiskVos != null && cloneDataDiskVos.size() > 0) {
                for (DataDiskVo dataDiskVo : cloneDataDiskVos) {
                    ResVfc resVfc = new ResVfc(dataDiskVo);
                    // 数据库存在的主机，则更新并从clone的主机集合中剔除
                    Criteria criteria1 = new Criteria();
                    criteria1.put("viosLparId", dataDiskVo.getLparId());
                    criteria1.put("viosLparName", dataDiskVo.getViosName());
                    List<ResVios> resVioss = this.resViosMapper.selectByParams(criteria1);
                    if (resVioss != null && resVioss.size() > 0) {
                        resVfc.setResViosId(resVioss.get(0).getResViosSid());
                    }
                    this.resVfcMapper.insertSelective(resVfc);
                }
            }

        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "获取" + resVe.getResTopologyName() + "数据盘失败");
        }
    }

    /**
     * 物理配件更新VD表
     *
     * @param resVe the res ve
     */
    private void updateVdForLpar(ResVe resVe) {
        Criteria criteria = new Criteria();
        criteria.put("slotItemTypeCode", WebConstants.HostItemTypeCode.DISK);
        criteria.put("hostItemTypeCode", WebConstants.HostItemTypeCode.LOCAL_DISK);
        criteria.put("resVeSid", resVe.getResTopologySid());
        // 查询配件里面磁盘卡关联的DISK数量
        List<ResHostItem> resHostItems = this.resHostItemMapper.selectDiskItemForVd(criteria);
        logger.info("虚拟化环境（" + resVe.getResTopologyName() + "）下LPAR系统盘个数：" + resHostItems.size());

        int add = 0, upd = 0, del = 0;
        // 磁盘单个大小
        Long diskSize = Long.parseLong(PropertiesUtil.getProperty("host.item.disk.size"));
        for (ResHostItem resHostItem : resHostItems) {
            Criteria condition = new Criteria().put("uuid", resHostItem.getUuid());
            // 如果磁盘被移除或者没有被占用的情况，删除VD中对应的记录
            if (resHostItem.getDiskNum() == 0 || StringUtil.isNullOrEmpty(resHostItem.getRelateResSid())) {
                del += this.resVdMapper.deleteByParams(condition);
                continue;
            }

            List<ResVd> resVds = this.resVdMapper.selectByParams(condition);
            // 在UUID无法查询到的情况下，再用VD名和关联的VM联合查询
            if (resVds.isEmpty()) {
                condition = new Criteria().put("vdName", resHostItem.getUuid().replace("#", "#LPAR#D"))
                                          .put("resVmSid", resHostItem.getRelateResSid());
                resVds = this.resVdMapper.selectByParams(condition);
            }
            ResVd resVd;
            // 如果VD不存在，则插入，有则更新；
            if (resVds.isEmpty()) {
                resVd = new ResVd();
                resVd.setUuid(resHostItem.getUuid());
                resVd.setVdName(resHostItem.getUuid().replace("#", "#LPAR#D"));
                resVd.setAllocateDiskSize(resHostItem.getDiskNum() * diskSize);
                resVd.setResVmSid(resHostItem.getRelateResSid());
                resVd.setStoragePurpose(WebConstants.StoragePurpose.SYSTEM_DISK);
                resVd.setStatus(WebConstants.ResVdStatus.NORMAL);
                WebUtil.prepareInsertParams(resVd);
                this.resVdMapper.insertSelective(resVd);
                add++;
            } else {
                resVd = resVds.get(0);
                resVd.setAllocateDiskSize(resHostItem.getDiskNum() * diskSize);
                resVd.setResVmSid(resHostItem.getRelateResSid());
                WebUtil.prepareUpdateParams(resVd);
                this.resVdMapper.updateByPrimaryKeySelective(resVd);
                upd++;
            }
        }
        logger.info("LPAR系统盘 | VD追加：" + add + "个 | VD更新： " + upd + "个 | VD删除： " + del + "个 | 跳过处理：" +
                            (resHostItems.size() - add - upd - del) + "个");
    }

    /**
     * 更新IsViosFlg
     *
     * @param resVe the res ve
     */
    private void updateHostViosFlg(ResVe resVe) {
        // 查询资源环境下所有主机及其VIOS的数量
        List<ResHost> resHosts = this.resHostMapper.selectHostViosCnt(resVe.getResTopologySid());
        for (ResHost resHost : resHosts) {
            // 主机的IsViosFlag字段为空的时候，才更新，如果手动指定之后，不做修正
            if (StringUtil.isNullOrEmpty(resHost.getIsViosFlag())) {
                String flag = resHost.getVmCount() == 0 ? WebConstants.IsViosFlag.NO : WebConstants.IsViosFlag.YES;
                resHost.setIsViosFlag(flag);
                this.resHostMapper.updateByPrimaryKeySelective(resHost);
            }
        }
    }

}