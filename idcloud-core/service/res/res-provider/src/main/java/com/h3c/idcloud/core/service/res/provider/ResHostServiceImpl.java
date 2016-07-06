package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.scan.HostScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.result.HostScanAloneResult;
import com.h3c.idcloud.core.persist.product.dao.MgtObjResMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsPortGroupMapper;
import com.h3c.idcloud.core.pojo.dto.product.MgtObjRes;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResHostStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVs;
import com.h3c.idcloud.core.pojo.dto.res.ResVsHost;
import com.h3c.idcloud.core.pojo.dto.res.ResVsPortGroup;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.LicenseResult;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RESTHttpResponse;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.RSETClientUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Res host service 类.
 *
 * @author Chaohong.Mao
 */
@Service(version = "1.0.0")
@Component
public class ResHostServiceImpl implements ResHostService {
    /**
     * 静态变量 logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ResHostServiceImpl.class);
    @Autowired
    private ResHostMapper resHostMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private ResTopologyMapper resTopologyMapper;
    @Autowired
    private ResVeMapper resVeMapper;
    @Autowired
    private ResVeService resVeService;
    @Autowired
    private ResVmService resVmService;
    @Autowired
    private ResHostStorageMapper resHostStorageMapper;
    @Autowired
    private ResStorageMapper resStorageMapper;
    @Autowired
    private ResVsMapper resVsMapper;
    @Autowired
    private ResVsHostMapper resVsHostMapper;
    @Autowired
    private ResVsPortGroupMapper resVsPortGroupMapper;
    @Autowired
    private ResVdMapper resVdMapper;
    @Autowired
    private ResVmNetworkMapper resVmNetworkMapper;
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private MgtObjResMapper mgtObjResMapper;

    /**
     * 调用MQ，组装共通的参数
     *
     * @param resVe the res ve
     *
     * @return map
     */
    public Map<String, Object> commonParams(ResVe resVe) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("providerUrl", resVe.getManagementUrl());
        map.put("authUser", resVe.getManagementAccount());
        map.put("authPass", resVe.getManagementPassword());
        return map;
    }

    @Override
    public int countByBizParams(Criteria example) {
        return this.resHostMapper.countByBizParams(example);
    }

    /**
     * 统计数据条数
     *
     * @param example
     *
     * @
     */
    @Override
    public int countByParams(Criteria example) {
        return this.resHostMapper.countByParams(example);
    }

    /**
     * 查询主机列表
     *
     * @param example
     *
     * @return
     */
    @Override
    public List<ResHost> selectByParams(Criteria example) {
        return this.resHostMapper.selectByParams(example);
    }

    /**
     * 根据sid查询资主机信息
     *
     * @param resHostSid
     *
     * @return
     */
    @Override
    public ResHost selectByPrimaryKey(String resHostSid) {
        return this.resHostMapper.selectByPrimaryKey(resHostSid);
    }

    /**
     * 查询vCenter主机监控信息
     */
    @Override
    public String getHostMonitorInformation(String ipAddr, ResVe ve) throws Exception {
        String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");

        String url = adapterUrl + "/perform/host/" + ipAddr;

        Map<String, Object> mapVDisk = new HashMap<String, Object>();
        mapVDisk.put("providerUrl", ve.getManagementUrl());
        mapVDisk.put("authUser", ve.getManagementAccount());
        mapVDisk.put("authPass", ve.getManagementPassword());
        String json = JsonUtil.toJson(mapVDisk);
        RESTHttpResponse result = RSETClientUtil.post(url, json);

        if (RESTHttpResponse.SUCCESS.equals(result.getStatus())) {
            return result.getContent();
        } else {
            return null;
        }
    }


    /**
     * 同步单一主机
     *
     * @throws Exception
     */
    @Override
    public ResInstResult getAllByHost(String resHostSid) throws Exception {

        // 查询主机
        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resHostSid);
        Criteria example1 = new Criteria();
        example1.put("findParentBySid", resHost.getParentTopologySid());
        example1.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.VE);
        List<ResTopology> list = this.resTopologyMapper.selectByParams(example1);

        ResTopology resVc = list.get(0);
        ResVe resVe = new ResVe();

        if (list != null && list.size() > 0) {
            if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resVc.getResTopologyType())) {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getResTopologySid());
            } else {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getParentTopologySid());
            }
        } else {
            return new ResInstResult(ResInstResult.FAILURE, WebUtil.getMessage("获取主机所属虚拟化环境信息失败！"));
        }

        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("同步主机");
        log.setTaskTarget(resHost.getHostName());
        log.setTaskType(WebConstants.TaskType.SCAN_HOST);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskLogSid = taskLog.getTaskLogSid();

        try {
            boolean result = asyncHost(resHostSid, resHost.getHostName(), resVe);
            // 更新任务表
            taskLog = new TaskLog();
            taskLog.setTaskLogSid(taskLogSid);
            taskLogger.success(taskLog);
            if (result) {
                taskLog.setTaskDetail("同步主机成功");
                taskLogger.success(taskLog);
                return new ResInstResult(ResInstResult.FAILURE, "同步主机成功！");
            } else {
                taskLog.setTaskDetail("同步主机失败");
                taskLogger.fail(taskLog);
                return new ResInstResult(ResInstResult.FAILURE, "同步主机失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 更新任务表
            taskLog = new TaskLog();
            taskLog.setTaskLogSid(taskLogSid);
            taskLog.setTaskDetail("同步主机失败");
            taskLog.setTaskFailureReason(ExceptionUtils.getMessage(e));
            taskLogger.fail(taskLog);
            return new ResInstResult(ResInstResult.FAILURE, "同步主机失败！");
        }
    }

    /**
     * 设定 host status.
     *
     * @param rh    the rh
     * @param rhMap the rh map
     *
     * @return the host status
     */
    // 修改主机状态对应code表中的key
    private ResHost setHostStatus(ResHost rh, Map<String, Object> rhMap) {

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

        return rh;
    }

    public boolean asyncHost(String resHostSid, String hostName, ResVe resVe) throws Exception {

        ResHost hostInfo = null;

        HostScanAlone hostScanAlone = new HostScanAlone();
        hostScanAlone.setProviderType(resVe.getVirtualPlatformType());
        hostScanAlone.setAuthUrl(resVe.getManagementUrl());
        hostScanAlone.setAuthUser(resVe.getManagementAccount());
        hostScanAlone.setAuthPass(resVe.getManagementPassword());
        hostScanAlone.setVirtEnvType(resVe.getVirtualPlatformType());
        hostScanAlone.setVirtEnvUuid(resVe.getResTopologySid());
        hostScanAlone.setName(hostName);
        Object obj = MQHelper.rpc(hostScanAlone);

        HostScanAloneResult hostScanAloneResult = (HostScanAloneResult) obj;

        if (hostScanAloneResult.isSuccess()) {
            hostInfo = new ResHost(hostScanAloneResult.getHost());
            //			hostInfo = setHostStatus(hostInfo,hostmap);
            boolean result = getAllByHost(resHostSid, hostInfo, resVe);
            return result;
        } else {
            String code = hostScanAloneResult.getErrCode();
            if ("10001".equals(code)) {
                logger.info("主机" + hostName + "不存在！");
                if (resHostSid != null) {
                    ResHost resHost = resHostMapper.selectByPrimaryKey(resHostSid);
                    resHost.setParentTopologySid(resVe.getResTopologySid());
                    WebUtil.prepareUpdateParams(resHost);
                    resHostMapper.updateByPrimaryKeySelective(resHost);
                    //getAllByHost(resHostSid, new ResHost(),resVe);
                }
                //return false;// new ResInstResult(ResInstResult.SUCCESS, "主机不存在，删除主机成功");
            } else {
                logger.error("调用南向接口同步主机 " + hostName + " 出错");
                //return false;//new ResInstResult(ResInstResult.FAILURE, "同步主机失败！");
            }
            return false;
        }


    }

    /**
     * 获得 net work by host.
     *
     * @param resVe      the res ve
     * @param netMaps    the net maps
     * @param resHostSid the res host sid
     *
     * @return the net work by host
     *
     * @throws Exception the exception
     */
    public boolean getNetWorkByHost(ResVe resVe, Map<String, Object> netMaps, String resHostSid) throws Exception {
        boolean result = false;
        if (netMaps != null) {
            List<ResVs> allResVsList = new ArrayList<ResVs>();
            // 分布式交换机
            List<Map<String, Object>> distributeVss = (List<Map<String, Object>>) netMaps.get("dvSwitchs");
            if (distributeVss != null && distributeVss.size() > 0) {
                List<ResVs> resDisVsList = this.getResVsByHost(distributeVss,
                                                               resVe,
                                                               WebConstants.ResVsType.DISTRIBUTE_VS,
                                                               resHostSid
                );
                // 插入分布式端口组
                for (ResVs resVs : resDisVsList) {
                    for (Map<String, Object> dvsMap : distributeVss) {
                        String dvsUuid = StringUtil.nullToEmpty(dvsMap.get("uuid")).replaceAll(" ", "");
                        if (dvsUuid.equals(resVs.getUuid())) {
                            List<Map<String, Object>> portGroupMapList = (List<Map<String, Object>>) dvsMap.get(
                                    "dvPortGroupInfos");
                            this.insertDisPortGroup(portGroupMapList, resVs);
                        }
                    }
                }
                allResVsList.addAll(resDisVsList);
            }

            // 更新与主机的关系
            this.updateHostResVsRelationship(allResVsList, resHostSid);
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Update host res vs relationship.
     *
     * @param resVsList  the res vs list
     * @param resHostSid the res host sid
     *
     * @throws Exception the exception
     */
    public void updateHostResVsRelationship(List<ResVs> resVsList, String resHostSid) throws Exception {

        ResVsHost vh = new ResVsHost();
        vh.setResHostSid(resHostSid);
        resVsHostMapper.deleteByPrimaryKeyHostSid(vh);
        for (ResVs resVs : resVsList) {
            ResVsHost newResVsHost = new ResVsHost();
            newResVsHost.setResHostSid(resHostSid);
            newResVsHost.setResVsSid(resVs.getResVsSid());
            this.resVsHostMapper.insertSelective(newResVsHost);
        }
    }

    /**
     * Insert dis port group.
     *
     * @param disPortGroupList the dis port group list
     * @param resVs            the res vs
     */
    public void insertDisPortGroup(List<Map<String, Object>> disPortGroupList, ResVs resVs) {
        // 查询数据库中分布式交换机下端口组
        Criteria criteria2 = new Criteria();
        criteria2.put("resVsSid", resVs.getResVsSid());
        List<ResVsPortGroup> managedResVsPortGroups = this.resVsPortGroupMapper.selectByParams(criteria2);

        // 克隆集合
        List<Map<String, Object>> clonedisPortGroupList = null;
        if (disPortGroupList != null && disPortGroupList.size() > 0) {
            clonedisPortGroupList = new ArrayList<Map<String, Object>>(disPortGroupList);
        }

        if (managedResVsPortGroups != null && managedResVsPortGroups.size() > 0) {
            for (ResVsPortGroup managedResVsPortGroup : managedResVsPortGroups) {
                boolean portGroupFlag = false;
                for (Map<String, Object> portGroupMap : disPortGroupList) {
                    if (managedResVsPortGroup.getUuid().equals(portGroupMap.get("uuid"))) {
                        ResVsPortGroup resVsPortGroup = new ResVsPortGroup();
                        resVsPortGroup.setName(StringUtil.nullToEmpty(portGroupMap.get("name")));
                        resVsPortGroup.setResVsSid(resVs.getResVsSid());
                        resVsPortGroup.setUuid(StringUtil.nullToEmpty(portGroupMap.get("uuid")));
                        resVsPortGroup.setVlanId(StringUtil.nullToEmpty(portGroupMap.get("vlanId")));
                        resVsPortGroup.setTotalPorts(Long.parseLong(StringUtil.nullToEmpty((portGroupMap.get("numPorts")))));
                        WebUtil.prepareUpdateParams(resVsPortGroup);
                        this.resVsPortGroupMapper.updateByPrimaryKeySelective(resVsPortGroup);
                        clonedisPortGroupList.remove(portGroupMap);
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
            for (Map<String, Object> portGroupMap : clonedisPortGroupList) {
                ResVsPortGroup resVsPortGroup = new ResVsPortGroup();
                resVsPortGroup.setName(StringUtil.nullToEmpty(portGroupMap.get("name")));
                resVsPortGroup.setResVsSid(resVs.getResVsSid());
                resVsPortGroup.setUuid(StringUtil.nullToEmpty(portGroupMap.get("uuid")));
                resVsPortGroup.setVlanId(StringUtil.nullToEmpty(portGroupMap.get("vlanId")));
                resVsPortGroup.setTotalPorts(Long.parseLong(StringUtil.nullToEmpty((portGroupMap.get("numPorts")))));
                WebUtil.prepareInsertParams(resVsPortGroup);
                this.resVsPortGroupMapper.insertSelective(resVsPortGroup);
            }
        }
    }

    /**
     * 获得 res vs by host.
     *
     * @param vsList     the vs list
     * @param resVe      the res ve
     * @param vsType     the vs type
     * @param resHostSid the res host sid
     *
     * @return the res vs by host
     */
    public List<ResVs> getResVsByHost(List<Map<String, Object>> vsList, ResVe resVe, String vsType, String resHostSid) {
        List<Map<String, Object>> cloneVsList = null;

        if (vsList != null && vsList.size() > 0) {
            cloneVsList = new ArrayList<Map<String, Object>>(vsList);
        }
        List<ResVs> managedResVsList = this.resVsMapper.selectByHostSid(resHostSid);
        if (managedResVsList != null && managedResVsList.size() > 0) {
            for (ResVs managedResVs : managedResVsList) {
                boolean vdFlag = false;
                if (vsList != null && vsList.size() > 0) {
                    for (Map<String, Object> resVsMap : vsList) {
                        // 插入交换机
                        ResVs resVs = new ResVs();
                        resVs.setUuid(StringUtil.nullToEmpty(resVsMap.get("uuid")).replaceAll(" ", ""));
                        resVs.setResVsName(StringUtil.nullToEmpty(resVsMap.get("name")));
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
                                cloneVsList.remove(resVsMap);
                                vdFlag = true;
                            }
                        } else {
                            if (managedResVs.getResVsName().equals(resVs.getResVsName())) {
                                cloneVsList.remove(resVsMap);
                                vdFlag = true;
                            }
                        }
                    }
                    if (!vdFlag) {
                        // 删除交换机
                        //	this.resVsMapper.deleteByPrimaryKey(managedResVs.getResVsSid());

                        // 删除交换机与主机关系
                        /*Criteria example = new Criteria();
                        example.put("resVsSid", managedResVs.getResVsSid());
						this.resVsHostMapper.deleteByParams(example);*/

                        // 删除端口组
                        /*Criteria criteria = new Criteria();
                        criteria.put("resVsSid", managedResVs.getResVsSid());
						this.resVsPortGroupMapper.deleteByParams(criteria);*/
                    }
                } else {
                    logger.info("调用南向接口成功，网络为空");
                }
            }
        }

        if (cloneVsList != null && cloneVsList.size() > 0) {

            Criteria example1 = new Criteria();
            example1.put("parentTopologySid", resVe.getResTopologySid());
            List<ResVs> allVsList = this.resVsMapper.selectByParams(example1);
            List<Map<String, Object>> existVsList = new ArrayList<Map<String, Object>>();
            if (allVsList != null && allVsList.size() > 0) {
                for (Map<String, Object> resVsMap : cloneVsList) {
                    String uuid = StringUtil.nullToEmpty(resVsMap.get("uuid")).replaceAll(" ", "");
                    for (ResVs aResVs : allVsList) {
                        if (uuid != null && uuid.equals(aResVs.getUuid())) {
                            existVsList.add(resVsMap);
                        }
                    }
                }
                cloneVsList.removeAll(existVsList);
            }
            for (Map<String, Object> resVsMap : cloneVsList) {
                ResVs resVs = new ResVs();
                resVs.setResVsName(StringUtil.nullToEmpty(resVsMap.get("name")));
                if (vsType.equals(WebConstants.ResVsType.STANDARD_VS)) {
                    resVs.setResVsType(WebConstants.ResVsType.STANDARD_VS);
                } else if (vsType.equals(WebConstants.ResVsType.DISTRIBUTE_VS)) {
                    resVs.setResVsType(WebConstants.ResVsType.DISTRIBUTE_VS);
                }
                resVs.setUuid(StringUtil.nullToEmpty(resVsMap.get("uuid")).replaceAll(" ", ""));
                resVs.setParentTopologySid(resVe.getResTopologySid());
                WebUtil.prepareInsertParams(resVs);
                this.resVsMapper.insertSelective(resVs);
            }
        }

        List<ResVs> resVsList = new ArrayList<ResVs>();
        for (Map<String, Object> dvsMap : vsList) {
            ResVs resVs = new ResVs();
            Criteria example2 = new Criteria();
            example2.put("uuid", StringUtil.nullToEmpty(dvsMap.get("uuid")).replaceAll(" ", ""));
            if (vsType.equals(WebConstants.ResVsType.STANDARD_VS)) {
                example2.put("resVsType", WebConstants.ResVsType.STANDARD_VS);
            } else if (vsType.equals(WebConstants.ResVsType.DISTRIBUTE_VS)) {
                example2.put("resVsType", WebConstants.ResVsType.DISTRIBUTE_VS);
            }
            resVs = this.resVsMapper.selectByParams(example2).get(0);
            resVsList.add(resVs);
        }
        return resVsList;
    }

    /**
     * 获得 storage by host.
     *
     * @param resVe       the res ve
     * @param storageList the storage list
     * @param resHostSid  the res host sid
     *
     * @return the storage by host
     *
     * @throws JSONException the json exception
     * @throws IOException   the io exception
     */
    private List<ResStorage> getStorageByHost(ResVe resVe, List<ResStorage> storageList, String resHostSid) throws JSONException, IOException {
        // Long taskLogSid = taskLog.getTaskLogSid();
        // TaskLog log = new TaskLog();
        // log.setTaskLogSid(taskLogSid);
        // log.setTaskDetail("获取" + resVe.getResTopologyName() + "下存储");
        List<ResStorage> resStorageList = new ArrayList<ResStorage>();
        // 获取存储

        List<ResStorage> managedStorageList = new ArrayList<ResStorage>();

        // 查询主机下存储
        managedStorageList = this.resStorageMapper.selectStoByHostSid(resHostSid);
        // 存储存在则更新，不存在则插入
        List<ResStorage> cloneStorageList = null;
        if (storageList != null && storageList.size() > 0) {
            cloneStorageList = new ArrayList<ResStorage>(storageList);
        }
        ResHostStorage hs = new ResHostStorage();
        hs.setResHostSid(resHostSid);
        this.resHostStorageMapper.deleteByPrimaryKeyHostSid(hs);
        if (managedStorageList != null && managedStorageList.size() > 0) {
            for (ResStorage managedStorage : managedStorageList) {
                boolean storageFlag = false;
                if (storageList != null && storageList.size() > 0) {
                    for (ResStorage storageMap : storageList) {
                        String storageMapStr = JsonUtil.toJson(storageMap);
                        ResStorage rs = JsonUtil.fromJson(storageMapStr, ResStorage.class);
                        if ("true".equals(rs.getStorageCategory())) {
                            rs.setStorageCategory(WebConstants.StorageCategory.SHARE);
                        } else {
                            rs.setStorageCategory(WebConstants.StorageCategory.LOCAL);
                        }
                        if ("true".equals(rs.getStatus())) {
                            rs.setStatus(WebConstants.ResStorageStatus.NORMAL);
                        } else {
                            rs.setStatus(WebConstants.ResStorageStatus.OUTLINE);
                        }
                        if (managedStorage.getUuid() != null) {
                            if (managedStorage.getUuid().equals(rs.getUuid())) {
                                // 更新存储
                                rs.setParentTopologySid(resVe.getResTopologySid());
                                rs.setResStorageSid(managedStorage.getResStorageSid());

                                rs.setStorageType(rs.getStorageType().toUpperCase());
                                WebUtil.prepareUpdateParams(rs);
                                this.resStorageMapper.updateByPrimaryKeySelective(rs);
                                cloneStorageList.remove(storageMap);

                                // 更新主机与存储关联表
                                List<String> hosts = storageMap.getHostNames();
                                if (hosts != null && hosts.size() > 0) {
                                    for (String hostName : hosts) {
                                        // 查询对应主机Sid
                                        Criteria criteria2 = new Criteria();
                                        criteria2.put("hostName", hostName);
                                        List<ResHost> manHost = this.resHostMapper.selectByParams(criteria2);
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
                        //this.resStorageMapper.deleteByPrimaryKey(managedStorage.getResStorageSid());
                        ResHostStorage rhs = new ResHostStorage();
                        rhs.setResStorageSid(managedStorage.getResStorageSid());
                        this.resHostStorageMapper.deleteByPrimaryKey(rhs);
                    }

                } else {
                    // 删除存储，以及存储与主机的关联
                    //this.resStorageMapper.deleteByPrimaryKey(managedStorage.getResStorageSid());
                    ResHostStorage rhs = new ResHostStorage();
                    rhs.setResStorageSid(managedStorage.getResStorageSid());
                    this.resHostStorageMapper.deleteByPrimaryKey(rhs);
                }
            }
        }

        // 未加入数据库的存储，插入数据库
        if (cloneStorageList != null && cloneStorageList.size() > 0) {
            Criteria criteria = new Criteria();
            criteria.put("parentTopologySid", resVe.getResTopologySid());
            List<ResStorage> allStorageList = this.resStorageMapper.selectByParams(criteria);
            List<ResStorage> newList = new ArrayList<ResStorage>();
            for (ResStorage resStorage : cloneStorageList) {
                for (ResStorage resStorage1 : allStorageList) {
                    if (resStorage.getUuid().equals(resStorage1.getUuid())) {
                        resStorage.setResStorageSid(resStorage1.getResStorageSid());
                        newList.add(resStorage);
                        break;
                    }
                }
            }
            if (newList.size() > 0) {
                cloneStorageList.removeAll(newList);

                for (ResStorage storageMap : newList) {
                    String storageMapStr = JsonUtil.toJson(storageMap);
                    ResStorage rs = JsonUtil.fromJson(storageMapStr, ResStorage.class);
                    // 更新主机与存储关联表
                    List<String> hosts = storageMap.getHostNames();

                    // 根据存储删除关系表
                    /*	ResHostStorage rhs = new ResHostStorage();
						rhs.setResStorageSid(rs.getResStorageSid());
						this.resHostStorageMapper.deleteByPrimaryKey(rhs);*/
                    if (hosts != null && hosts.size() > 0) {
                        for (String hostName : hosts) {
                            // 查询对应主机Sid
                            Criteria criteria2 = new Criteria();
                            criteria2.put("hostName", hostName);
                            List<ResHost> manHost = this.resHostMapper.selectByParams(criteria2);
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
            for (ResStorage storageMap : cloneStorageList) {
                String storageMapStr = JsonUtil.toJson(storageMap);
                ResStorage rs = JsonUtil.fromJson(storageMapStr, ResStorage.class);
                if ("true".equals(rs.getStorageCategory())) {
                    rs.setStorageCategory(WebConstants.StorageCategory.SHARE);
                } else {
                    rs.setStorageCategory(WebConstants.StorageCategory.LOCAL);
                }
                if ("true".equals(rs.getStatus())) {
                    rs.setStatus(WebConstants.ResStorageStatus.NORMAL);
                } else {
                    rs.setStatus(WebConstants.ResStorageStatus.OUTLINE);
                }
                WebUtil.prepareInsertParams(rs);
                rs.setParentTopologySid(resVe.getResTopologySid());
                this.resStorageMapper.insertSelective(rs);

                // 更新主机与存储关联表
                List<String> hosts = storageMap.getHostNames();

                // 根据存储删除关系表
				/*	ResHostStorage rhs = new ResHostStorage();
					rhs.setResStorageSid(rs.getResStorageSid());
					this.resHostStorageMapper.deleteByPrimaryKey(rhs);*/
                if (hosts != null && hosts.size() > 0) {
                    for (String hostName : hosts) {
                        // 查询对应主机Sid
                        Criteria criteria2 = new Criteria();
                        criteria2.put("hostName", hostName);
                        List<ResHost> manHost = this.resHostMapper.selectByParams(criteria2);

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

        return resStorageList;
    }


    /**
     * 获得 all by host.
     *
     * @param resHostSid the res host sid
     * @param hostInfo   the host info
     * @param resVe      the res ve
     *
     * @return the all by host
     *
     * @throws Exception the exception
     */
    public boolean getAllByHost(String resHostSid, ResHost hostInfo, ResVe resVe) throws Exception {
        ResHost resHost = null;
        if (resHostSid == null) {
            resHost = resHostMapper.countByHostUUID(hostInfo.getUuid());
            if (resHost == null) {

                //调用license接口判断是否过期
                LicenseResult dateResult = resVeService.checkLincense();
                if (!dateResult.isValid()) {
                    throw new RpcException(RpcException.BIZ_EXCEPTION, dateResult.getMessage().toString());
                }

                hostInfo.setParentTopologySid(resVe.getResTopologySid());
                WebUtil.prepareInsertParams(hostInfo);
                resHostMapper.insertSelective(hostInfo);
                resHostSid = hostInfo.getResHostSid();
                // 查询主机
                resHost = this.resHostMapper.selectByPrimaryKey(resHostSid);
            } else {
                resHostSid = resHost.getResHostSid();
            }
        } else {
            resHost = resHostMapper.selectByPrimaryKey(resHostSid);
        }

        try {
            boolean flag = false;

            if (resHost.getUuid().equals(hostInfo.getUuid())) {
				/*
				 * 判断uuid是提供集群同步调用，查询数据库中主机是否在该集群下
				 */
                // 遍历查询主机下虚拟机,查询虚拟机，并操作数据库
                Criteria criteria = new Criteria();
                criteria.put("allocateResHostSid", resHostSid);
                String statusNotIn =
                        "'" + WebConstants.ResVmStatus.DELETED + "','" + WebConstants.ResVmStatus.DELETING + "','" +
                                WebConstants.ResVmStatus.OCCUPING + "'";
                criteria.put("statusNotIn", statusNotIn);
                List<ResVm> localVmList = resVmMapper.selectByParams(criteria);
                List<ResVm> resVmList = hostInfo.getVms();
                hostInfo.setResHostSid(resHost.getResHostSid());
                WebUtil.prepareUpdateParams(hostInfo);

                resHostMapper.updateByPrimaryKeySelective(hostInfo);
                if (resVmList != null && resVmList.size() > 0) {
                    for (ResVm resVm : resVmList) {
                        boolean targe = false;
                        for (ResVm localVm : localVmList) {
                            // 虚拟机存在则更新
                            if (localVm.getUuid().equals(resVm.getUuid())) {
                                resVm.setResVmSid(localVm.getResVmSid());
                                resVmService.synaVmInfo(resVm, resHost, resVe);
                                localVmList.remove(localVm);
                                targe = true;
                                break;
                            }
                        }
                        if (!targe) {
                            ResVm resVm2 = resVmMapper.selectByVmUUID(resVm.getUuid());
                            if (resVm2 != null) {
                                resVm.setResVmSid(resVm2.getResVmSid());
                            }
                            // 虚拟机不存在则插入
                            resVm.setAllocateResHostSid(resHostSid);
                            resVmService.synaVmInfo(resVm, resHost, resVe);
                        }
                    }
                    if (localVmList != null && localVmList.size() > 0) {
                        for (ResVm resVm : localVmList) {
                            resVm.setHostName(resHost.getHostName());
                            resVmService.synaVmInfo(resVm.getResVmSid());
                        }
                    }
                } else {
                    logger.info("调用南向接口成功，虚拟机为空，主机：" + resHost.getHostName());
                    if (localVmList != null && localVmList.size() > 0) {
                        for (ResVm resVm : localVmList) {
                            resVm.setHostName(resHost.getHostName());
                            resVmService.synaVmInfo(resVm.getResVmSid());
                        }
                    }
                }
                flag = true;
            }
            if (!flag) {
                // 不存在则删除主机，以及该主机与存储的关系
                ResHost rH = resHostMapper.selectByPrimaryKey(resHostSid);
                rH.setParentTopologySid(resVe.getResTopologySid());
                WebUtil.prepareUpdateParams(rH);
                resHostMapper.updateByPrimaryKeySelective(rH);
                //deleteHostRelation(resHostSid);
            }
            getStorageByHost(resVe, hostInfo.getDataStorages(), resHostSid);
            getNetWorkByHost(resVe, hostInfo.getNetWorks(), resHostSid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("同步单一主机失败，主机：" + resHost.getHostName() + "异常：" + ExceptionUtils.getFullStackTrace(e));
            return false;
        }
    }

    /**
     * 删除主机以及主机下属关系
     *
     * @param resHostSid the res host sid
     */
    private void deleteHostRelation(String resHostSid) {
        this.resHostMapper.deleteByPrimaryKey(resHostSid);
        // 删除主机与存储关系
        Criteria criteria = new Criteria();
        criteria.put("resHostSid", resHostSid);
        this.resHostStorageMapper.deleteByParams(criteria);
        //删除主机与交换机关系
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

    @Override
    public boolean hostOperation(String operation, String hostSid, ResVe ve) throws Exception {
        String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
        ResHost resHost = this.resHostMapper.selectByPrimaryKey(hostSid);
        String url = adapterUrl + "/host/" + resHost.getHostName() + "/" + operation;

        Map<String, Object> mapVDisk = new HashMap<String, Object>();
        mapVDisk.put("providerUrl", ve.getManagementUrl());
        mapVDisk.put("authUser", ve.getManagementAccount());
        mapVDisk.put("authPass", ve.getManagementPassword());
        String json = JsonUtil.toJson(mapVDisk);
        RESTHttpResponse result = RSETClientUtil.post(url, json);

        if (RESTHttpResponse.SUCCESS.equals(result.getStatus())) {
            if (result.getContent().equals("true")) {
                if ("stop".equals(operation)) {
                    resHost.setStatus("04");
                } else if ("enterMaintenance".equals(operation)) {
                    resHost.setStatus("03");
                } else if ("exitMaintenance".equals(operation)) {
                    resHost.setStatus("01");
                }
                WebUtil.prepareUpdateParams(resHost);
                this.resHostMapper.updateByPrimaryKeySelective(resHost);

                ResVm resVm = new ResVm();
                resVm.setStatus("poweredOff");

                Criteria criteria = new Criteria();
                criteria.put("allocateResHostSid", resHost.getResHostSid());
                this.resVmMapper.updateByParamsSelective(resVm, criteria.getCondition());
                return true;
            } else if (result.getContent().equals("false")) {
                return false;
            } else {
                throw new Exception(result.getContent());
            }
        } else {

            throw new Exception(result.getContent());
        }
    }

    /***
     * 关闭主机下虚拟机进入维护模式
     *
     * @param resHostSid
     *
     * @return
     *
     * @throws Exception
     */
    @Override
    public boolean stopAllVmOfHost(String resHostSid) throws Exception {
        // 查询主机
        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resHostSid);
        Criteria example1 = new Criteria();
        example1.put("findParentBySid", resHost.getParentTopologySid());
        example1.put("resTopologyType", "VE");
        List<ResTopology> list = this.resTopologyMapper.selectByParams(example1);

        ResVe resVe = null;
        if (list != null && list.size() > 0) {
            resVe = this.resVeMapper.selectByPrimaryKey(list.get(0).getResTopologySid());
        } else {
            return false;
        }

        String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
        String url = adapterUrl + "/host/stopAllVmOfHost/" + resHost.getHostName();

        Map<String, Object> mapVDisk = new HashMap<String, Object>();
        mapVDisk.put("providerUrl", resVe.getManagementUrl());
        mapVDisk.put("authUser", resVe.getManagementAccount());
        mapVDisk.put("authPass", resVe.getManagementPassword());
        String json = JsonUtil.toJson(mapVDisk);
        RESTHttpResponse result = RSETClientUtil.post(url, json);
        if (RESTHttpResponse.SUCCESS.equals(result.getStatus())) {

            Criteria criteria = new Criteria();
            criteria.put("allocateResHostSid", resHostSid);
            List<ResVm> resVms = resVmMapper.selectByParams(criteria);
            if ("00".equals(result.getContent())) {
                if (resVms != null && resVms.size() > 0) {
                    for (ResVm resVm : resVms) {
                        if ("normal".equals(resVm.getStatus())) {
                            WebUtil.prepareUpdateParams(resVm);
                            resVm.setStatus("poweredOff");
                            resVmMapper.updateByPrimaryKeySelective(resVm);
                        }
                    }
                }
                resHost.setStatus("03");
                WebUtil.prepareUpdateParams(resHost);
                resHostMapper.updateByPrimaryKeySelective(resHost);
                return true;
            } else if ("02".equals(result.getContent())) {
                if (resVms != null && resVms.size() > 0) {
                    for (ResVm resVm : resVms) {
                        if ("normal".equals(resVm.getStatus())) {
                            WebUtil.prepareUpdateParams(resVm);
                            resVm.setStatus("poweredOff");
                            resVmMapper.updateByPrimaryKeySelective(resVm);
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * 删除主机
     */
    @Override
    public int deleteHostWithObjRes(String resHostSid) {
        Criteria criteria = new Criteria();
        criteria.put("resSetSid", resHostSid);
        List<MgtObjRes> resList = this.mgtObjResMapper.selectByParams(criteria);
        if (resList.size() > 0) {
            return this.resHostMapper.deleteHostWithObjRes(resHostSid);
        } else {
            return this.resHostMapper.deleteByPrimaryKey(resHostSid);
        }
    }

    /**
     * 获取物理主机和关联的存储信息
     *
     * @param resHostSid 物理主机id
     *
     * @return
     */
    @Override
    public ResHost getHostInfo(String resHostSid) {
        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resHostSid);
        List<ResStorage> resStorages = this.resStorageMapper.selectStoByHostSid(resHostSid);
        resHost.setResStorages(resStorages);
        return resHost;
    }

    /**
     * 获取物理主机和关联的存储信息
     *
     * @return
     */
    @Override
    public ResHost statisticalTopologyOfHost(Criteria criteria) {
        return this.resHostMapper.statisticalTopologyOfHost(criteria);
    }

    @Override
    public ResHost statisticalBizOfHost(Criteria example) {
        return this.resHostMapper.statisticalBizOfHost(example);
    }

    /**
     * 统计拓扑结构下的主机资源分配信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResHost statisticalTopologyOfHostAllotInfo(String resTopologySid) {
        return this.resHostMapper.statisticalTopologyOfHostAllotInfo(resTopologySid);
    }

    /**
     * 统计资源分区结构下的主机资源信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResHost statisticalHostPoolOfRz(String resTopologySid) {
        return this.resHostMapper.statisticalHostPoolOfRz(resTopologySid);
    }

    /**
     * 统计资源分区结构下的主机资源分配信息
     *
     * @param resTopologySid
     *
     * @return
     */
    @Override
    public ResHost statisticalRzOfHostAllotInfo(String resTopologySid) {
        return this.resHostMapper.statisticalRzOfHostAllotInfo(resTopologySid);
    }

    /**
     * 统计资源池下的主机信息
     *
     * @param resPoolSid
     *
     * @return
     */
    @Override
    public ResHost statisticalComputePoolOfHost(String resPoolSid) {
        return this.resHostMapper.statisticalComputePoolOfHost(resPoolSid);
    }

    @Override
    public List<ResHost> selectByBizParams(Criteria example) {
        // TODO Auto-generated method stub
        return this.resHostMapper.selectByBizParams(example);
    }
}