package com.h3c.idcloud.core.service.res.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.scan.ClusterScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.result.ClusterScanAloneResult;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsHostMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVsHost;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.core.service.res.api.ResVcService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
@Component
public class ResVcServiceImpl implements ResVcService {
    private static final Logger logger = LoggerFactory.getLogger(ResVcServiceImpl.class);
    @Autowired
    private ResVcMapper resVcMapper;
    @Autowired
    private ResHostMapper reshostMapper;
    @Autowired
    private ResVeMapper resVeMapper;
    @Autowired
    private ResHostStorageMapper resHostStorageMapper;
    @Autowired
    private ResHostService resHostService;
    @Autowired
    private ResHostMapper resHostMapper;
    @Autowired
    private ResVsHostMapper resVsHostMapper;
    @Autowired
    private ResVdMapper resVdMapper;
    @Autowired
    private ResVmNetworkMapper resVmNetworkMapper;
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private ResTopologyMapper resTopologyMapper;

    /***
     * 调用MQ，组装共通的参数
     *
     * @param resVe
     *
     * @return
     */
    public Map<String, Object> commonParams(ResVe resVe) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("providerUrl", resVe.getManagementUrl());
        map.put("authUser", resVe.getManagementAccount());
        map.put("authPass", resVe.getManagementPassword());
        return map;
    }

    /**
     * 查询Vc列表
     *
     * @param example
     *
     * @return
     */
    @Override
    public List<ResVc> selectByParams(Criteria example) {
        return this.resVcMapper.selectByParams(example);
    }

    /**
     * @param resVcSid
     *
     * @return
     */
    @Override
    public ResVc selectByPrimaryKey(String resVcSid) {
        return this.resVcMapper.selectByPrimaryKey(resVcSid);
    }

    /**
     * 同步单一集群
     */
    @Override
    public boolean getAllByVc(String resTopologySid) {
        // TODO Auto-generated method stub
        ResVc resVc = this.resVcMapper.selectByPrimaryKey(resTopologySid);
        Criteria example = new Criteria();
        example.put("resTopologyType", "VE");
        ResTopology resTopology = resTopologyMapper.selectByPrimaryKey(resTopologySid);
        ResVe resVe = this.resVeMapper.selectByPrimaryKey(resTopology.getParentTopologySid());
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("同步集群");
        log.setTaskTarget(resVc.getResTopologyName());
        log.setTaskType(WebConstants.TaskType.SCAN_CLUSTER);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskLogSid = taskLog.getTaskLogSid();

        try {

            ClusterScanAlone clusterScanAlone = new ClusterScanAlone();
            clusterScanAlone.setProviderType(resVe.getVirtualPlatformType());
            clusterScanAlone.setAuthUrl(resVe.getManagementUrl());
            clusterScanAlone.setAuthUser(resVe.getManagementAccount());
            clusterScanAlone.setAuthPass(resVe.getManagementPassword());
            clusterScanAlone.setVirtEnvType(resVe.getVirtualPlatformType());
            clusterScanAlone.setVirtEnvUuid(resVe.getResTopologySid());
            clusterScanAlone.setName(resTopology.getResTopologyName());
            Object obj = MQHelper.rpc(clusterScanAlone);

            ClusterScanAloneResult clusterScanAloneResult = (ClusterScanAloneResult) obj;
            //			ResVc resVcInfo = new ResVc();
            if (clusterScanAloneResult.isSuccess()) {
                ResVc resVcInfo = new ResVc(clusterScanAloneResult.getCluster());
                List<ResHost> resHostList = resVcInfo.getHosts();
                boolean result = getAllByVc(resTopologySid, resVcInfo, resVe);
                // 更新任务表
                taskLog = new TaskLog();
                taskLog.setTaskLogSid(taskLogSid);

                if (result) {
                    taskLog.setTaskDetail("同步集群成功");
                    taskLogger.success(taskLog);
                    logger.info("同步集群" + resTopology.getResTopologyName() + "成功！");
                } else {
                    taskLog.setTaskDetail("同步集群失败");
                    taskLogger.fail(taskLog);
                    logger.info("同步集群" + resTopology.getResTopologyName() + "失败！");
                }
            } else {
                if ("3004".equals(clusterScanAloneResult.getErrCode())) {
                    example = new Criteria();
                    example.put("parentTopologySid", resTopologySid);
                    List<ResHost> localHosts = reshostMapper.selectByParams(example);

                    if (localHosts != null && localHosts.size() > 0) {
                        for (ResHost resHost : localHosts) {
                            WebUtil.prepareUpdateParams(resHost);
                            resHost.setParentTopologySid(resVe.getResTopologySid());
                            resHostMapper.updateByPrimaryKeySelective(resHost);
                            //deleteHostRelation(resHost.getResHostSid());
                        }
                    }
                    //	reshostMapper.deleteByParams(example);
                    resVcMapper.deleteByPrimaryKey(resTopologySid);
                    resTopologyMapper.deleteByPrimaryKey(resTopologySid);
                    // 更新任务表
                    taskLog = new TaskLog();
                    taskLog.setTaskLogSid(taskLogSid);
                    taskLog.setTaskDetail("同步集群成功");
                    taskLogger.success(taskLog);
                    logger.info("集群" + resTopology.getResTopologyName() + "不存在，已删除删除集群。");
                    return true;
                } else {
                    logger.error("获取集群异常：" + clusterScanAloneResult.getErrMsg());
                    return false;
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // 更新任务表
            taskLog = new TaskLog();
            taskLog.setTaskLogSid(taskLogSid);
            taskLog.setTaskDetail("同步集群失败");
            taskLog.setTaskFailureReason(ExceptionUtils.getMessage(e));
            taskLogger.fail(taskLog);
            return false;
        }
        return true;
    }

    public boolean getAllByVc(String resVcSid, ResVc resVcInfo, ResVe resVe) {
        // 查询集群
        ResVc resVc = this.resVcMapper.selectByPrimaryKey(resVcSid);
        List<ResHost> resHostList = resVcInfo.getHosts();
        //List<ResHost> clonelocalHosts = new ArrayList<ResHost>(resHostList);
        try {
            boolean flag = false;
            if (resVc.getResTopologyName().equals(resVcInfo.getClusterName())) {
                Criteria example = new Criteria();
                example.put("parentTopologySid", resVc.getResTopologySid());
                List<ResHost> localHosts = reshostMapper.selectByResVcSid(resVc.getResTopologySid());

                if (resHostList != null && resHostList.size() > 0) {
                    // 遍历查询主机
                    if (localHosts != null && localHosts.size() > 0) {
                        for (ResHost resH : resHostList) {
                            boolean hostFlag = false;
                            for (ResHost resHost : localHosts) {
                                if (resH.getUuid().equals(resHost.getUuid())) {
                                    resH.setResHostSid(resHost.getResHostSid());
                                    reshostMapper.updateByPrimaryKeySelective(resH);
                                    //resHostService.getAllByHost(resHost.getResHostSid(), resH);
                                    localHosts.remove(resHost);
                                    hostFlag = true;
                                    break;
                                }
                            }
                            if (!hostFlag) {
                                ResHost resHost = reshostMapper.countByHostUUID(resH.getUuid());
                                if (resHost != null) {
                                    WebUtil.prepareUpdateParams(resH);
                                    resH.setResHostSid(resHost.getResHostSid());
                                    resH.setParentTopologySid(resVcSid);
                                    reshostMapper.updateByPrimaryKeySelective(resH);

                                } else {
                                    // 主机不存在插入
                                    WebUtil.prepareInsertParams(resH);
                                    resH.setParentTopologySid(resVcSid);
                                    reshostMapper.insertSelective(resH);
                                    // resHostService.getAllByHost(resH.getResHostSid(), resH);
                                }
                            }
                        }
                        if (localHosts.size() > 0) {
                            // 不存在的主机则删除
                            for (ResHost resHost : localHosts) {
                                WebUtil.prepareUpdateParams(resHost);
                                resHost.setParentTopologySid(resVe.getResTopologySid());
                                reshostMapper.updateByPrimaryKeySelective(resHost);
                                //	deleteHostRelation(resHost.getResHostSid());
                                /*String uuid = UUID.randomUUID().toString();
								ResHost resHost2 = new ResHost();
								resHost2.setUuid(uuid);
								resHostService.getAllByHost(resHost.getResHostSid(), resHost2);*/
                            }
                        }

                    } else {
                        for (ResHost resHost : resHostList) {
                            ResHost resHost1 = reshostMapper.countByHostUUID(resHost.getUuid());
                            if (resHost1 != null) {
                                WebUtil.prepareUpdateParams(resHost);
                                resHost.setParentTopologySid(resVcSid);
                                resHost.setResHostSid(resHost1.getResHostSid());
                                reshostMapper.updateByPrimaryKeySelective(resHost);
                            } else {
                                // 数据库中没有的主机则插入
                                WebUtil.prepareInsertParams(resHost);
                                resHost.setParentTopologySid(resVcSid);
                                reshostMapper.insertSelective(resHost);
                                // resHostService.getAllByHost(resH.getResHostSid(), resH);
                            }
                        }
                    }
                } else {
                    // 集群下不存在主机时删除数据库中主机
                    if (localHosts != null && localHosts.size() > 0) {
                        for (ResHost localHost : localHosts) {
                            WebUtil.prepareUpdateParams(localHost);
                            localHost.setParentTopologySid(resVe.getResTopologySid());
                            reshostMapper.updateByPrimaryKeySelective(localHost);
                            //deleteHostRelation(localHost.getResHostSid());
							/*String uuid = UUID.randomUUID().toString();
							ResHost resHost2 = new ResHost();
							resHost2.setUuid(uuid);
							resHostService.getAllByHost(resHost.getResHostSid(), resHost2);*/
                        }
                    }
                }
                flag = true;
            }
            if (flag) {

                //List<ResHost> localHosts = resHostService.selectByParams(example);
                if (resHostList != null && resHostList.size() > 0) {
                    for (ResHost resHost : resHostList) {
                        resHostService.asyncHost(resHost.getResHostSid(), resHost.getHostName(), resVe);
                        //	resHostService.getAllByHost((resHost.getResHostSid()));
                    }
                }
            } else {
                // 集群不存在删除集群及关联主机
                Criteria example = new Criteria();
                example.put("parentTopologySid", resVc.getResTopologySid());
                List<ResHost> localHosts = reshostMapper.selectByParams(example);

                if (localHosts != null && localHosts.size() > 0) {
                    for (ResHost resHost : localHosts) {
                        WebUtil.prepareUpdateParams(resHost);
                        resHost.setParentTopologySid(resVe.getResTopologySid());
                        reshostMapper.updateByPrimaryKeySelective(resHost);
                        //deleteHostRelation(resHost.getResHostSid());
                    }
                }
                //	reshostMapper.deleteByParams(example);
                resVcMapper.deleteByPrimaryKey(resVcSid);
                resTopologyMapper.deleteByPrimaryKey(resVcSid);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("同步单一集群失败，集群：" + resVc.getResTopologyName() + "异常：" + ExceptionUtils.getFullStackTrace(e));
            return false;
        }
    }

    /**
     * 插入数据
     *
     * @param record
     *
     * @return
     */
    @Override
    public int insertSelective(ResVc record) {
        return this.resVcMapper.insertSelective(record);
    }

    /***
     * 删除主机以及主机下属关系
     *
     * @param resHostSid
     */
    private void deleteHostRelation(String resHostSid) {
        this.reshostMapper.deleteByPrimaryKey(resHostSid);
        // 删除主机与存储关系
        Criteria criteria = new Criteria();
        criteria.put("resHostSid", resHostSid);
        this.resHostStorageMapper.deleteByParams(criteria);
        // 删除主机与交换机关系
        ResVsHost vh = new ResVsHost();
        vh.setResHostSid(resHostSid);
        resVsHostMapper.deleteByPrimaryKeyHostSid(vh);
        // 查询主机下虚拟机
        Criteria criteria1 = new Criteria();
        criteria1.put("allocateResHostSid", resHostSid);
        List<ResVm> resVmList = this.resVmMapper.selectByParams(criteria1);

        if (resVmList != null && resVmList.size() > 0) {
            for (ResVm resVm : resVmList) {
                // 删除磁盘
                Criteria criteria2 = new Criteria();
                criteria2.put("resVmSid", resVm.getResVmSid());
                this.resVdMapper.deleteByParams(criteria2);
                // 删除虚拟机与网络关系
                this.resVmNetworkMapper.deleteByPrimaryKey(resVm.getResVmSid());
                // 删除虚拟机
                this.resVmMapper.deleteByPrimaryKey(resVm.getResVmSid());
            }
        }
    }

    /**
     * 修改主机状态对应code表中的key
     *
     * @param hostMapList
     *
     * @return 修改后的主机列表
     *
     * @throws IOException
     */
    private List<ResHost> setHostStatus(List<Map<String, Object>> hostMapList) throws IOException {
        List<ResHost> hosts = new ArrayList<ResHost>();
        if (hostMapList != null && hostMapList.size() > 0) {
            for (Map<String, Object> rhMap : hostMapList) {
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

                if (!StringUtil.isNullOrEmpty(rhMap.get("cpuUsage"))) {
                    Double useCpuGhz = Double.parseDouble((StringUtil.nullToEmpty(rhMap.get("cpuUsage"))));
                    rh.setUseCpuGhz(useCpuGhz);
                } else {
                    rh.setUseCpuGhz(0D);
                }
                if (!StringUtil.isNullOrEmpty(rhMap.get("menUsage"))) {
                    Double useMenGhz = Double.parseDouble((StringUtil.nullToEmpty(rhMap.get("menUsage"))));
                    rh.setUseMemorySize(useMenGhz);
                } else {
                    rh.setUseMemorySize(0D);
                }
                hosts.add(rh);
            }

        }
        return hosts;
    }
}