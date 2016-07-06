package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.datastore.Volume;
import com.h3c.idcloud.core.adapter.pojo.keypairs.result.keyVo.KeyVo;
import com.h3c.idcloud.core.adapter.pojo.scan.VmScanAlone;
import com.h3c.idcloud.core.adapter.pojo.scan.result.VmScanAloneResult;
import com.h3c.idcloud.core.adapter.pojo.software.Software;
import com.h3c.idcloud.core.adapter.pojo.software.SoftwareDeploy;
import com.h3c.idcloud.core.adapter.pojo.vm.FC;
import com.h3c.idcloud.core.adapter.pojo.vm.Vios;
import com.h3c.idcloud.core.adapter.pojo.vm.VmCreate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmDisk;
import com.h3c.idcloud.core.adapter.pojo.vm.VmMigrate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmModify;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNic;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNicAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.VmNicDelete;
import com.h3c.idcloud.core.adapter.pojo.vm.VmOperate;
import com.h3c.idcloud.core.adapter.pojo.vm.VmReconfig;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRemove;
import com.h3c.idcloud.core.adapter.pojo.vm.VmRename;
import com.h3c.idcloud.core.adapter.pojo.vm.VmUserAdd;
import com.h3c.idcloud.core.adapter.pojo.vm.VmVncConsole;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmRenameResult;
import com.h3c.idcloud.core.adapter.pojo.vm.result.VmVncConsoleResult;
import com.h3c.idcloud.core.persist.res.dao.ResCpuPoolMapper;
import com.h3c.idcloud.core.persist.res.dao.ResCpuPoolMparRelMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostItemMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResHostStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResImageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResIpMapper;
import com.h3c.idcloud.core.persist.res.dao.ResKeypairsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResKeypairsVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResNetworkHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResOsSoftwareMapper;
import com.h3c.idcloud.core.persist.res.dao.ResOsUserMapper;
import com.h3c.idcloud.core.persist.res.dao.ResSoftwareConfigMapper;
import com.h3c.idcloud.core.persist.res.dao.ResSoftwareMapper;
import com.h3c.idcloud.core.persist.res.dao.ResStorageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyConfigMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVdMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResViosMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVlanMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmNetworkMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVolumeMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVpcMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVsPortGroupMapper;
import com.h3c.idcloud.core.persist.system.dao.CodeMapper;
import com.h3c.idcloud.core.persist.system.dao.MgtObjMapper;
import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.ResCpuPool;
import com.h3c.idcloud.core.pojo.dto.res.ResCpuPoolMparRel;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResHostItem;
import com.h3c.idcloud.core.pojo.dto.res.ResHostStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResImage;
import com.h3c.idcloud.core.pojo.dto.res.ResImageSoftWare;
import com.h3c.idcloud.core.pojo.dto.res.ResIp;
import com.h3c.idcloud.core.pojo.dto.res.ResKeypairs;
import com.h3c.idcloud.core.pojo.dto.res.ResKeypairsVe;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResNetworkHost;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResOsUser;
import com.h3c.idcloud.core.pojo.dto.res.ResSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResSoftwareConfig;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResTopologyConfig;
import com.h3c.idcloud.core.pojo.dto.res.ResVc;
import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVios;
import com.h3c.idcloud.core.pojo.dto.res.ResVlan;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVolume;
import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.core.pojo.dto.res.ResVs;
import com.h3c.idcloud.core.pojo.dto.res.ResVsPortGroup;
import com.h3c.idcloud.core.pojo.dto.res.ResWebConsole;
import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResNetworkInst;
import com.h3c.idcloud.core.pojo.instance.ResVdInst;
import com.h3c.idcloud.core.pojo.instance.ResVmCheck;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.core.pojo.instance.ResVmOptInst;
import com.h3c.idcloud.core.pojo.instance.VmUser;
import com.h3c.idcloud.core.service.product.api.ResInfoSync;
import com.h3c.idcloud.core.service.res.api.ResHostService;
import com.h3c.idcloud.core.service.res.api.ResImageService;
import com.h3c.idcloud.core.service.res.api.ResVeService;
import com.h3c.idcloud.core.service.res.api.ResVmService;
import com.h3c.idcloud.core.service.res.api.base.ResBaseService;
import com.h3c.idcloud.core.service.system.api.CodeService;
import com.h3c.idcloud.core.service.system.api.SidService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.exception.ServiceException;
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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VM Serivce 类.
 *
 * @author Chaohong.Mao
 */
@Service(version = "1.0.0", cluster = "failfast")
@Component
public class ResVmServiceImpl implements ResVmService {
    private final Logger logger = LoggerFactory.getLogger(ResVmServiceImpl.class);

    @Reference(version = "1.0.0")
    private SidService sidService;
    @Reference(version = "1.0.0")
    private CodeService codeService;
    @Reference(version = "1.0.0")
    private ResInfoSync resInfoSync;

    @Autowired
    private ResVeService resVeService;
    @Autowired
    private ResImageService resImageService;
    @Autowired
    private ResBaseService resBaseService;

    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private ResVeMapper resVeMapper;
    @Autowired
    private ResHostMapper resHostMapper;
    @Autowired
    private ResHostService resHostService;
    @Autowired
    private ResTopologyMapper resTopologyMapper;
    @Autowired
    private ResStorageMapper resStorageMapper;
    @Autowired
    private ResVdMapper resVdMapper;
    @Autowired
    private ResIpMapper resIpMapper;
    @Autowired
    private ResNetworkMapper resNetworkMapper;
    @Autowired
    private ResVmNetworkMapper resVmNetworkMapper;
    @Autowired
    private ResVlanMapper resVlanMapper;
    @Autowired
    private ResImageMapper resImageMapper;
    @Autowired
    private ResVsMapper resVsMapper;
    @Autowired
    private ResVsPortGroupMapper resVsPortGroupMapper;
    @Autowired
    private ResTopologyConfigMapper resTopologyConfigMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private ResCpuPoolMparRelMapper resCpuPoolMparRelMapper;
    @Autowired
    private MgtObjMapper mgtObjMapper;
    @Autowired
    private ResCpuPoolMapper resCpuPoolMapper;
    @Autowired
    private ResViosMapper resViosMapper;
    @Autowired
    private ResHostItemMapper resHostItemMapper;
    @Autowired
    private ResOsSoftwareMapper resOsSoftwareMapper;
    @Autowired
    private ResSoftwareMapper resSoftwareMapper;
    @Autowired
    private ResSoftwareConfigMapper resSoftwareConfigMapper;
    @Autowired
    private ResNetworkHostMapper resNetworkHostMapper;
    @Autowired
    private ResOsUserMapper resOsUserMapper;
    @Autowired
    private ResHostStorageMapper resHostStorageMapper;
    @Autowired
    private ResVolumeMapper resVolumeMapper;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private ResVpcMapper resVpcMapper;
    @Autowired
    private ResKeypairsMapper resKeypairsMapper;
    @Autowired
    private ResKeypairsVeMapper resKeypairsVeMapper;

    @Override
    public ResVm selectByPrimaryKey(String resVmSid) {
        return this.resVmMapper.selectByPrimaryKey(resVmSid);
    }

    @Override
    public int countByParams(Criteria example) {
        return this.resVmMapper.countByParams(example);
    }

    @Override
    public List<ResVm> selectByParams(Criteria example) {
        return this.resVmMapper.selectByParams(example);
    }

    @Override
    public ResVm getVmInfo(String resVmSid) {

        logger.info("虚拟机SID：" + resVmSid);
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        if (resVm != null) {
            Criteria example = new Criteria();
            example.put("resVmSid", resVmSid);
            List<ResVd> resVdList = this.resVdMapper.selectByParams(example);
            List<ResIp> resIpList = this.resIpMapper.selectResIpByVM(example);
            resVm.setResVdList(resVdList);
            resVm.setResIpList(resIpList);
        }
        return resVm;
    }

    @Override
    public List<ResVm> selectBaseInfoByParams(Map<String, String> params) {
        Criteria criteria = new Criteria();
        params.forEach(criteria::put);
        return this.resVmMapper.selectBaseInfoByParam(criteria);
    }

    @Override
    public ResVm getVmCurrentInfo(ResVm resVm) throws Exception {

        String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
        ResTopology resVc = this.resTopologyMapper.selectByPrimaryKey(resHost.getParentTopologySid());
        ResTopology resT = this.resTopologyMapper.selectByPrimaryKey(resVc.getParentTopologySid());
        ResVe resVe = this.resVeMapper.selectByPrimaryKey(resT.getResTopologySid());

        // 调用南向接口查询当然虚拟机信息
        Map<String, Object> mapVDisk = new HashMap<String, Object>();
        mapVDisk.put("providerUrl", resVe.getManagementUrl());
        mapVDisk.put("authUser", resVe.getManagementAccount());
        mapVDisk.put("authPass", resVe.getManagementPassword());
        String url = adapterUrl + "/perform/vm/" + resVm.getVmName();
        String paramsJson = JsonUtil.toJson(mapVDisk);
        RESTHttpResponse result = RSETClientUtil.post(url, paramsJson);
        if (RESTHttpResponse.SUCCESS.equals(result.getStatus())) {
            ResVm resVm1 = JsonUtil.fromJson(result.getContent(), ResVm.class);
            return resVm1;
        } else {
            logger.error(resVm.getVmName() + "查询当前信息失败");
            return null;
        }

    }

    @Override
    @Transactional
    public ResInstResult createVm(ResCommonInst resVmInst) {
        ResInstResult result = null;
        VmCreate vmCreate = null;
        logger.info("创建虚拟机 | 输入参数：" + JsonUtil.toJson(resVmInst));
        JsonNode resVmSpec = JsonUtil.fromJson(resVmInst.getResSpecParam());
        String zone = resVmSpec.findValue("zone").getTextValue();
        // 选择ZONE对应的属性
        String region = this.resBaseService.getRegionFromZone(zone);
        // 查找VE的设定属性
        ResVe allcateResVe = this.resBaseService.getVeFromZone(zone);
        resVmInst.setZoneId(zone);
        // TODO add create vm for other env
        // TODO 加上资源检查
        vmCreate = this.createOpenstackVm(resVmInst, allcateResVe);
        // 设置Region的名称
        vmCreate.setRegion(region);

        // Send to MQ
        result = this.sendCreateMQ(vmCreate);

        return result;
    }

    /**
     * 发送创建VM的MQ消息
     *
     * @param vmCreate the vm create
     * @return the res inst result
     */
    private ResInstResult sendCreateMQ(VmCreate vmCreate) {
        ResInstResult result;
        // 插入任务表
        TaskLog log = new TaskLog();
        if (vmCreate.getTenantUserName() != null) {
            log.setAccount(vmCreate.getTenantUserName());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("创建虚拟机");
        log.setTaskTarget(vmCreate.getName());
        log.setTaskType(WebConstants.TaskType.CREATE_VM);
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        // 资源挑选完毕，调用MQ时，插入任务
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        try {
            logger.debug("输入MQ参数：" + JsonUtil.toJson(vmCreate));
            MQHelper.sendMessage(vmCreate);
//            ResVm resVM = this.getVmInfo(vmCreate.getId());
            Map<String, String> resMap = Maps.newHashMap();
            resMap.put(vmCreate.getId(), WebConstants.ResourceType.RES_VM);
            result = new ResInstResult(ResInstResult.SUCCESS, "", resMap);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("创建虚拟机异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    /**
     * 创建Openstack的VM.
     *
     * @param resVmInst VM参数
     * @return 创建结果
     *
     * @implSpec json:
     * <pre>
     * {
     *     "region": "10",
     *     "zone": "12e0c825-3ff5-11e5-8c09-005056ba3c46",
     *     "password": "",
     *     "hostname": "test",
     *     "instance": [{
     *          "instanceCategory": "idc-S",
     *          "cpu": "1",
     *          "memory": "1"
     *     }],
     *     "systemDisk": [{
     *          "systemDiskCategory": "cloud_efficiency",
     *          "systemDiskSize": "40",
     *          "systemDiskDevice": "/dev/xvda"
     *     }],
     *     "dataDisk": [{
     *          "dataDiskCategory": "cloud_ssd",
     *          "dataDiskSize": "100",
     *          "dataDiskSnapshot": "",
     *          "dataDiskDevice": "",
     *          "dataDiskDeletewithinstance": "true",
     *          "dataDiskInstanceId": null
     *     }, {
     *          "dataDiskCategory": "cloud_efficiency",
     *          "dataDiskSize": "150",
     *          "dataDiskSnapshot": "",
     *          "dataDiskDevice": "",
     *          "dataDiskDeletewithinstance": "true",
     *          "dataDiskInstanceId": null
     *     }],
     *     "networkType": "vpc",
     *     "networks": ["e581e6fa-efe3-11e5-9f4e-005056a50931"],
     *     "bandwidth": "0",
     *     "os": {
     *          "imageType": "public",
     *          "imageId": "605cdc9a-585c-11e5-8dea-005056a5742a"
     *     },
     *     "keyPair": "ccd98605-77ba-11e5-b6e5-005056a52fbf",
     *     "securityGroup": "ccd98605-77ba-11e5-b6e5-005056a52fbf"
     * }
     *
     * </pre>
     */
    private VmCreate createOpenstackVm(ResCommonInst resVmInst, ResVe allcateResVe) {
        JsonNode resVmSpec = JsonUtil.fromJson(resVmInst.getResSpecParam());
        ResVm resVm;
        String osSid = resVmSpec.findValue("imageId").getTextValue();
        ResImage resImage = this.resImageMapper.selectByPrimaryKey(osSid);
        // 规格项中，包含resVmSid则为重试；反之则是新开通
        if (resVmSpec.has("resVmSid")) {
            resVm = this.getVmInfo(resVmSpec.get("resVmSid").getTextValue());
            logger.info("创建虚拟机 | 重试开通 : " + JsonUtil.toJson(resVm));
        } else {
            resVm = this.preparePojoCreateVm(resVmInst.getInstId(), resVmSpec);
            // 镜像的配置
            resVm.setOsVersion(resImage.getOsVersion());
            resVm.setManagementAccount(resImage.getManagementAccount());
            if (Strings.isNullOrEmpty(resVmSpec.get("password").getTextValue())) {
                resVm.setManagementPassword(resImage.getManagementPassword());
            } else {
                resVm.setManagementPassword(resVmSpec.get("password").getTextValue());
            }

            // VM数据库插入
            resVm.setOsName(resImage.getImageName());
            resVm.setOsVersion(resImage.getOsVersion());
            resVm.setMgtObjSid(resVmInst.getMgtObjSid());
            resVm.setOwnerId(resVmInst.getUserAccount());
            resVm.setZone(resVmInst.getZoneId());
            WebUtil.prepareInsertParams(resVm, resVmInst.getUserAccount());
            this.resVmMapper.insertSelective(resVm);
            logger.info("创建虚拟机 | 插入DB - VM : " + JsonUtil.toJson(resVm));

            // VD数据库插入
            for (ResVd resVd : resVm.getResVdList()) {
                resVd.setResVmSid(resVm.getResVmSid());
                resVd.setZone(resVmInst.getZoneId());
                WebUtil.prepareInsertParams(resVd, resVmInst.getUserAccount());
                this.resVdMapper.insertSelective(resVd);
            }
        }
        // MQ参数组装
        VmCreate vmCreate = new VmCreate();
        // 设置创建基本环境信息
        this.resBaseService.setAdapterBaseInfo(allcateResVe, resVmInst, vmCreate);
        vmCreate.setId(resVm.getResVmSid());
        vmCreate.setName(resVm.getVmName());
        Map<String, Object> options = Maps.newHashMap();
        options.put("userAccount", resVmInst.getUserAccount());
        options.put("mgtObjSid", resVmInst.getMgtObjSid());
        options.put("instId", resVmInst.getInstId());
        vmCreate.setOptions(options);

        // 管理实例对象
        MgtObj mgtObj = this.mgtObjMapper.selectByPrimaryKey(resVmInst.getMgtObjSid());
        if (mgtObj != null) {
            vmCreate.setTenantId(mgtObj.getUuid());
            vmCreate.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
        }

        // Network
        List<VmNic> vmNicList = Lists.newArrayList();
        for (JsonNode jsonNode : resVmSpec.findValue("networks")) {
            // 根据子网，添加网络以及子网的参数
            ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(jsonNode.getTextValue());
            ResVpc resVpc = this.resVpcMapper.selectByPrimaryKey(resNetwork.getParentTopologySid());
            VmNic vmNic = new VmNic();
            vmNic.setNetId(resVpc.getUuid());
            vmNic.setSubnetId(resNetwork.getUuid());
            vmNicList.add(vmNic);
        }
        vmCreate.setNics(vmNicList);

        // Image
        vmCreate.setOsCategory(resImage.getOsType());
        // 不用uuid，和VMWare做统一
        vmCreate.setImage(resImage.getImageId());
        vmCreate.setAdminName(resVm.getManagementAccount());
        vmCreate.setAdminPass(resVm.getManagementPassword());

        // System Disk
        resVm.getResVdList()
             .stream()
             .filter(resVd -> WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose()))
             .forEach(resVd -> vmCreate.setSysDiskSize(resVd.getAllocateDiskSize().toString()));

        // TODO Data Disk

        // Keypairs
        if (!Strings.isNullOrEmpty(resVmSpec.get("keyPair").getTextValue())) {
            ResKeypairs resKeypairs = this.resKeypairsMapper.selectByPrimaryKey(resVmSpec.get("keyPair").getTextValue());
            KeyVo keyVo = new KeyVo();
            keyVo.setName(resKeypairs.getKeypairsName());
            keyVo.setPublicKey(resKeypairs.getPublicKey());
            vmCreate.setKeypair(keyVo);

            // 保存Keypair和环境的关系
            ResKeypairsVe resKeypairsVe = new ResKeypairsVe();
            resKeypairsVe.setResKeypairsSid(resKeypairs.getResKeypairsSid());
            resKeypairsVe.setResTopologySid(resVmInst.getZoneId());
            if (this.resKeypairsVeMapper.selectById(resKeypairsVe) == null) {
                this.resKeypairsVeMapper.insert(resKeypairsVe);
            }
        }

        // CPU && Memory
        vmCreate.setCpu(resVm.getCpuCores().toString());
        vmCreate.setMemory(resVm.getMemorySize().toString());

        return vmCreate;
    }


    /**
     * 创建其他虚拟机.
     *
     * @param resVmInst VM参数
     * @return 创建VM对象
     */
    private VmCreate creatOtherVm(ResVmInst resVmInst) {
        ResVm resVm = this.preparePojoCreateVm(resVmInst);

        VmCreate vmCreate = null;
        List<VmDisk> vmDataDiskList = new ArrayList<VmDisk>();
        List<VmNic> vmNicList = new ArrayList<VmNic>();
        List<FC> fcList = new ArrayList<FC>();

        // 根据服务层参数获取所有主机
        List<ResHost> resHosts = this.getResHostCreateVm(resVmInst);
        if (resHosts != null && resHosts.size() > 0) {
            // 取得主机下可用存储
            for (ResHost resHost : resHosts) {
                ResVe resVe = this.getVeByRes(resHost);
                if (WebConstants.VirtualPlatformType.VMWARE.equals(resVe.getVirtualPlatformType()) ||
                        WebConstants.VirtualPlatformType.OPENSTACK.equals(resVe.getVirtualPlatformType())) {
                    // 查找主机下的存储
                    List<ResStorage> resStorages = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
                    resHost.setResStorages(resStorages);
                } else if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {

                    List<ResStorage> resStorageList = new ArrayList<ResStorage>();
                    if (WebConstants.PowerPartitionType.MPAR.equals(resVm.getParType().toString())) {
                        // 根据主机查询VIOS
                        Criteria criteria = new Criteria();
                        criteria.put("resHostSid", resHost.getResHostSid());
                        List<ResVios> resVioses = this.resViosMapper.selectByParams(criteria);
                        // TODO 根据VIOS查询存储
                        if (resVioses != null && resVioses.size() > 0) {
                            for (ResVios resVios : resVioses) {
                                List<ResStorage> resStorages = this.resStorageMapper.selectAvaliableStorageByVios(
                                        resVios.getResViosSid());
                                resStorageList.addAll(resStorages);
                            }
                            resHost.setResStorages(resStorageList);
                        }
                    } else if (WebConstants.PowerPartitionType.LPAR.equals(resVm.getParType().toString())) {
                        Criteria example = new Criteria();
                        example.put("resHostSid", resHost.getResHostSid());
                        example.put("hostItemTypeCode", WebConstants.HostItemTypeCode.LOCAL_DISK);
                        example.put("resAllocStatus", WebConstants.HostItemAllocStatus.FREE);
                        List<ResHostItem> resHostItems = this.resHostItemMapper.selectByParams(example);
                        if (resHostItems != null && resHostItems.size() > 0) {
                            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resHostItems.get(0)
                                                                                                         .getRelateResSid());
                            if (resStorage != null) {
                                resStorageList.add(resStorage);
                            }
                        }
                        resHost.setResStorages(resStorageList);
                    }
                }
            }

            // 判断所有主机是否有足够的资源开通虚拟机
            ResVm allcateVm = this.getVmAvailability(resVm, resHosts);
            if (allcateVm == null) {
                throw new RpcException(RpcException.BIZ_EXCEPTION, "检查资源失败。");
            } else {
                resVm.setAllocateResHostSid(allcateVm.getAllocateResHostSid());
                resVm.setResVdList(allcateVm.getResVdList());
                // setResourceHosts(hostList, allcateVm);
            }
        } else {
            throw new RpcException(RpcException.BIZ_EXCEPTION, "该计算资源没有可用主机。");
        }

        // 查找被分配的VE
        ResVe allcateResVe = null;
        for (ResHost host : resHosts) {
            if (host.getResHostSid().equals(resVm.getAllocateResHostSid())) {
                allcateResVe = this.getVeByRes(host);
                break;
            }
        }
        logger.debug(JsonUtil.toJson(allcateResVe));

        // 插入数据库
        // 查询镜像
        ResImageSoftWare resImageSoftWare = new ResImageSoftWare();
        resImageSoftWare.setOsVersion(resVmInst.getOsVersion());
        resImageSoftWare.setResVeSid(allcateResVe.getResTopologySid());
        if (resVmInst.getSoftwares() != null) {
            resImageSoftWare.setSoftwares(resVmInst.getSoftwares());
        }
        // 获取资源环境下最匹配模板
        resImageSoftWare = this.resImageService.getImageBySoftWare(resImageSoftWare);
        if (StringUtil.isNullOrEmpty(resImageSoftWare.getImageSid())) {
            throw new RpcException(RpcException.BIZ_EXCEPTION,
                                   "选择的资源环境(" + allcateResVe.getResTopologyName() + ")没有对应操作系统模板\n(" +
                                           resVmInst.getOsVersion() + ")，请重新手动选择。"
            );
        }

        // 获取模板信息
        ResImage resImage = this.resImageMapper.selectByPrimaryKey(resImageSoftWare.getImageSid());

        resVm.setOsVersion(resImage.getOsVersion());
        resVm.setManagementAccount(resImage.getManagementAccount());
        resVm.setManagementPassword(resImage.getManagementPassword());
        WebUtil.prepareInsertParams(resVm);
        this.resVmMapper.insertSelective(resVm);
        logger.info("插入DB RES_VM : " + JsonUtil.toJson(resVm));

        // 插入OS用户
        if (!resVmInst.getVmUsers().isEmpty()) {
            for (VmUser vmUser : resVmInst.getVmUsers()) {
                ResOsUser resOsUser = new ResOsUser();
                resOsUser.setUserName(vmUser.getUserName());
                resOsUser.setPassword(vmUser.getPassword());
                resOsUser.setResSid(resVm.getResVmSid());
                resOsUser.setResType(WebConstants.ResourceType.RES_VM);
                WebUtil.prepareInsertParams(resOsUser);
                this.resOsUserMapper.insertSelective(resOsUser);
            }
        }

        // 插入磁盘
        for (ResVd resVd : resVm.getResVdList()) {
            resVd.setResVmSid(resVm.getResVmSid());
            WebUtil.prepareInsertParams(resVd);
            this.resVdMapper.insertSelective(resVd);

            //处理power的数据盘
            //处理方式为：插入res_vd的同时插入res_storage 两者一一对应
            //        创建虚机成功，返回该res_storage是由那些块组成
            if ((WebConstants.VirtualPlatformType.HMC.equals(allcateResVe.getVirtualPlatformType())) &&
                    WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
                //得到主机所在的资源环境
                String resVeSid = "";
                if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(allcateResVe.getResTopologyType())) {
                    resVeSid = allcateResVe.getResTopologySid();
                } else {
                    resVeSid = allcateResVe.getParentTopologySid();
                }

                //得到当前主机所在的资源分区
                ResHost rightResHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
                ResTopology poolInfo = resTopologyMapper.selectByPrimaryKey(rightResHost.getResPoolSid());
                Criteria stPoolParam = new Criteria();
                stPoolParam.put("parentTopologySid", poolInfo.getParentTopologySid());
                stPoolParam.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.PS);
                List<ResTopology> stPoolInfo = resTopologyMapper.selectByParams(stPoolParam);

                ResStorage storage = new ResStorage();
                storage.setParentTopologySid(resVeSid);
                storage.setResPoolSid(CollectionUtils.isEmpty(stPoolInfo) ? "" : stPoolInfo.get(0).getResTopologySid());
                storage.setStorageName(resVd.getVdName());
                storage.setTotalCapacity(resVd.getAllocateDiskSize());
                storage.setStorageCategory(WebConstants.StorageCategory.SHARE);
                storage.setStoragePurpose(WebConstants.StoragePurpose.DATA_DISK);
                storage.setStatus(WebConstants.ResStorageStatus.NORMAL);
                WebUtil.prepareInsertParams(storage);
                resStorageMapper.insertSelective(storage);

                //关联storage和vd
                resVd.setAllocateResStorageSid(storage.getResStorageSid());
                resVdMapper.updateByPrimaryKeySelective(resVd);
                //关联storage和host
                ResHostStorage hostStorage = new ResHostStorage();
                hostStorage.setResHostSid(rightResHost.getResHostSid());
                hostStorage.setResStorageSid(storage.getResStorageSid());
                resHostStorageMapper.insertSelective(hostStorage);
            }
        }

        //插入软件列表到数据库， 返回软件列表信息
        List<ResOsSoftware> softwares = resImageSoftWare.getSoftwares();
        if (softwares != null) {
            for (ResOsSoftware software : softwares) {
                software.setResSid(resVm.getResVmSid());
                software.setResType(WebConstants.ResType.VM);
                WebUtil.prepareInsertParams(software);
                resOsSoftwareMapper.insert(software);
            }
            resVm.setSoftwares(resImageSoftWare.getSoftwares());
        }
        //选择模板中不存在的软件，以备进行自动化安装
        List<ResOsSoftware> installedSoftwares = new ArrayList<ResOsSoftware>();
        for (ResOsSoftware software : softwares) {
            if (software.getStatus().equals(WebConstants.OsSoftwareStatus.WAITING)) {
                installedSoftwares.add(software);
            }
        }

        // 判断网络资源是否足够
        ResIp alocateResIp = new ResIp();
        List<ResNetworkInst> nets = null;
        // 有画面选择，则使用选择的网络
        if (resVmInst.getNets() != null && resVmInst.getNets().size() > 0) {
            nets = resVmInst.getNets();
        } else {
            // 网络资源没有选择的时候，查询主机网络关系表，选择可用资源
            List<ResNetworkHost> netList = this.resNetworkHostMapper.selectByParams(new Criteria().put("resHostSid",
                                                                                                       resVm.getAllocateResHostSid()
            ));
            if (netList.size() > 0) {
                nets = new ArrayList<ResNetworkInst>();
                for (ResNetworkHost resNetworkHost : netList) {
                    ResNetworkInst resNetworkInst = new ResNetworkInst();
                    resNetworkInst.setResNetworkId(resNetworkHost.getResNetworkSid());
                    nets.add(resNetworkInst);
                }
            } else {
                throw new RpcException(RpcException.BIZ_EXCEPTION, "主机没有关联的网络资源。");
            }
        }
        RpcException exception = null;
        for (ResNetworkInst rvni : nets) {
            // 组装网卡参数
            VmNic vmNic = new VmNic();
            FC fc = new FC();
            ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(rvni.getResNetworkId());
            if (WebConstants.VirtualPlatformType.VMWARE.equals(allcateResVe.getVirtualPlatformType())) {

                // 判断主机下是否存储在该vlan的端口组
                Criteria criteria = new Criteria();
                criteria.put("vlanId", resNetwork.getVlanId());
                criteria.put("resHostSid", resVm.getAllocateResHostSid());
                List<ResVsPortGroup> dvPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria);
                List<ResVsPortGroup> portList = new ArrayList<ResVsPortGroup>();
                if (dvPortList != null && dvPortList.size() > 0) {
                    portList = dvPortList;
                } else {
                    // 当没有对应vlanId的网络时，根据vlantag查询主机下有没有对应的端口组
                    Criteria criteria2 = new Criteria();
                    criteria2.put("vlanId", resNetwork.getVlanId());
                    List<ResVlan> resVlans = this.resVlanMapper.selectByParams(criteria2);
                    if (resVlans != null && resVlans.size() > 0) {
                        Criteria criteria3 = new Criteria();
                        criteria3.put("name", resVlans.get(0).getTag());
                        criteria3.put("resHostSid", resVm.getAllocateResHostSid());
                        List<ResVsPortGroup> svPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria3);
                        if (svPortList != null && svPortList.size() > 0) {
                            portList = svPortList;
                        }
                    } else {
                        exception = new RpcException(RpcException.BIZ_EXCEPTION, "分配的网络对应的VLAN不存在。");
                        continue;
                    }
                }
                if (portList != null && portList.size() > 0) {
                    if (!StringUtil.isNullOrEmpty(rvni.getIpAddress())) {
                        alocateResIp.setIpAddress(rvni.getIpAddress());

                    } else {
                        // 挑选可用IP
                        alocateResIp = this.getAlallocationResIp(rvni.getResNetworkId());
                    }
                    if (alocateResIp.getIpAddress() != null) {

                        // 插入虚拟机与IP关系表
                        ResVmNetwork resVmNetwork = new ResVmNetwork();
                        resVmNetwork.setIpAddress(alocateResIp.getIpAddress());
                        resVmNetwork.setResNetworkSid(resNetwork.getResNetworkSid());
                        resVmNetwork.setResVmSid(resVm.getResVmSid());
                        resVmNetwork.setStatus(WebConstants.ResVmNetworkStatus.CREATING);
                        if (WebConstants.NetPrimary.P.equals(StringUtil.nullToEmpty(rvni.getNetPrimary()))) {
                            resVmNetwork.setNetPrimary(WebConstants.NetPrimary.P);
                            vmNic.setPrimary(true);
                        }
                        this.resVmNetworkMapper.insertSelective(resVmNetwork);
                        vmNic.setDns(resNetwork.getDns1());
                        vmNic.setGateway(resNetwork.getGateway());
                        vmNic.setPort(portList.get(0).getName());
                        vmNic.setNetmask(resNetwork.getSubnetMask());
                        vmNic.setPrivateIp(alocateResIp.getIpAddress());
                        vmNicList.add(vmNic);
                        exception = null;
                        break;
                    } else {
                        exception = new RpcException(RpcException.BIZ_EXCEPTION, "IP资源不足。");
                    }
                } else {
                    exception = new RpcException(RpcException.BIZ_EXCEPTION, "计算资源没有对应的网络。");
                }
            } else if (WebConstants.VirtualPlatformType.OPENSTACK.equals(allcateResVe.getVirtualPlatformType())) {
                // 挑选可用IP
                alocateResIp = this.getAlallocationResIp(rvni.getResNetworkId());
                vmNic.setNetId(resNetwork.getUuid());
                vmNicList.add(vmNic);
                exception = null;
                break;
            } else if (WebConstants.VirtualPlatformType.HMC.equals(allcateResVe.getVirtualPlatformType())) {
                // 有IP直接设置，没有取第一个
                if (!StringUtil.isNullOrEmpty(rvni.getIpAddress())) {
                    alocateResIp.setIpAddress(rvni.getIpAddress());
                } else {
                    // 挑选可用IP
                    alocateResIp = this.getAlallocationResIp(rvni.getResNetworkId());
                }
                if (WebConstants.PowerPartitionType.MPAR.equals(resVm.getParType().toString())) {
                    if (alocateResIp.getIpAddress() != null) {
                        // 插入虚拟机与IP关系表
                        ResVmNetwork resVmNetwork = new ResVmNetwork();
                        resVmNetwork.setIpAddress(alocateResIp.getIpAddress());
                        resVmNetwork.setResNetworkSid(resNetwork.getResNetworkSid());
                        resVmNetwork.setResVmSid(resVm.getResVmSid());
                        resVmNetwork.setStatus(WebConstants.ResVmNetworkStatus.CREATING);
                        if (WebConstants.NetPrimary.P.equals(StringUtil.nullToEmpty(rvni.getNetPrimary()))) {
                            resVmNetwork.setNetPrimary(WebConstants.NetPrimary.P);
                            vmNic.setPrimary(true);
                        }
                        this.resVmNetworkMapper.insertSelective(resVmNetwork);
                        vmNic.setDns(resNetwork.getDns1());
                        vmNic.setGateway(resNetwork.getGateway());
                        vmNic.setNetmask(resNetwork.getSubnetMask());
                        vmNic.setPrivateIp(alocateResIp.getIpAddress());
                        vmNic.setPortVlanID(1);
                        // 关联虚拟机交换机
                        if (StringUtil.isNullOrEmpty(resVmInst.getVirtualSwitchSid())) {
                            List<ResVs> resVsList = this.resVsMapper.selectByHostSid(resVm.getAllocateResHostSid());
                            if (resVsList != null && resVsList.size() > 0) {
                                vmNic.setVirSwitch(resVsList.get(0).getResVsName());
                            } else {
                                exception = new RpcException(RpcException.BIZ_EXCEPTION, "主机未关联虚拟机交换机。");
                                continue;
                            }
                        } else {
                            ResVs resVs = this.resVsMapper.selectByPrimaryKey(resVmInst.getVirtualSwitchSid());
                            if (resVs != null) {
                                vmNic.setVirSwitch(resVs.getResVsName());
                            }
                        }
                        vmNicList.add(vmNic);
                        exception = null;
                        break;
                    } else {
                        exception = new RpcException(RpcException.BIZ_EXCEPTION, "IP资源不足。");
                    }
                } else {
                    ResHostItem supResHostItem = null;
                    if (StringUtil.isNullOrEmpty(rvni.getNetHBASid())) {
                        // 查询主机设备
                        Criteria example = new Criteria();
                        example.put("resHostSid", resVm.getAllocateResHostSid());
                        example.put("hostItemTypeCode", WebConstants.HostItemTypeCode.ETHERNET);
                        example.put("resAllocStatus", WebConstants.HostItemAllocStatus.FREE);
                        List<ResHostItem> resHostItems = this.resHostItemMapper.selectByParams(example);
                        if (resHostItems != null && resHostItems.size() > 0) {
                            vmNic.setMparSlotNumber(600);

                            fc.setHostItemAddr(resHostItems.get(0).getHostItemAddr());
                            supResHostItem = this.resHostItemMapper.selectByPrimaryKey(resHostItems.get(0)
                                                                                                   .getSupHostItemId());
                            fc.setHostItemIndex(supResHostItem.getHostItemIndex());
                            fcList.add(fc);

                            vmNic.setUnitPhysLoc(resHostItems.get(0).getItemLocation());
                            vmNic.setDns(resNetwork.getDns1());
                            vmNic.setGateway(resNetwork.getGateway());
                            vmNic.setNetmask(resNetwork.getSubnetMask());
                            vmNic.setPrivateIp(alocateResIp.getIpAddress());
                            vmNic.setPortVlanID(1);
                            vmNicList.add(vmNic);
                        } else {
                            exception = new RpcException(RpcException.BIZ_EXCEPTION, "物理网卡不足。");
                            continue;
                        }
                    } else {
                        ResHostItem resHostItem = this.resHostItemMapper.selectByPrimaryKey(rvni.getNetHBASid());
                        vmNic.setMparSlotNumber(600);

                        fc.setHostItemAddr(resHostItem.getHostItemAddr());
                        supResHostItem = this.resHostItemMapper.selectByPrimaryKey(resHostItem.getSupHostItemId());
                        fc.setHostItemIndex(supResHostItem.getHostItemIndex());
                        fcList.add(fc);

                        vmNic.setUnitPhysLoc(resHostItem.getItemLocation());
                        vmNic.setDns(resNetwork.getDns1());
                        vmNic.setGateway(resNetwork.getGateway());
                        vmNic.setNetmask(resNetwork.getSubnetMask());
                        vmNic.setPrivateIp(alocateResIp.getIpAddress());
                        vmNic.setPortVlanID(1);
                        vmNicList.add(vmNic);
                    }
                    // 插入虚拟机与IP关系表
                    ResVmNetwork resVmNetwork = new ResVmNetwork();
                    resVmNetwork.setIpAddress(alocateResIp.getIpAddress());
                    resVmNetwork.setResNetworkSid(resNetwork.getResNetworkSid());
                    resVmNetwork.setResVmSid(resVm.getResVmSid());
                    resVmNetwork.setStatus(WebConstants.ResVmNetworkStatus.CREATING);
                    if (WebConstants.NetPrimary.P.equals(StringUtil.nullToEmpty(rvni.getNetPrimary()))) {
                        resVmNetwork.setNetPrimary(WebConstants.NetPrimary.P);
                        vmNic.setPrimary(true);
                    }
                    this.resVmNetworkMapper.insertSelective(resVmNetwork);

                    // 网卡和网络端口预先占用
                    supResHostItem.setRelateResSid(resVm.getResVmSid());
                    supResHostItem.setResAllocFlag(Integer.valueOf(WebConstants.ResHostItemAllocFlag.OCCUPIED));
                    supResHostItem.setResAllocStatus(WebConstants.HostItemAllocStatus.OCCUPY);
                    this.resHostItemMapper.updateByPrimaryKeySelective(supResHostItem);
                    // 端口预先占用
                    Criteria criteria = new Criteria().put("supHostItemId", supResHostItem.getResHostSid())
                                                      .put("hostItemTypeCode", WebConstants.HostItemTypeCode.ETHERNET);
                    ResHostItem hostItem = new ResHostItem();
                    hostItem.setRelateResSid(resVm.getResVmSid());
                    hostItem.setResAllocFlag(Integer.valueOf(WebConstants.ResHostItemAllocFlag.OCCUPIED));
                    hostItem.setResAllocStatus(WebConstants.HostItemAllocStatus.OCCUPY);
                    this.resHostItemMapper.updateByParamsSelective(hostItem, criteria.getCondition());

                    exception = null;
                    break;
                }

            }
        }
        // 网络资源没有，抛出异常
        if (exception != null) {
            throw exception;
        }

        // 组装MQ参数
        vmCreate = new VmCreate();
        vmCreate.setProviderType(allcateResVe.getVirtualPlatformType());
        vmCreate.setAuthUrl(allcateResVe.getManagementUrl());
        vmCreate.setAuthUser(allcateResVe.getManagementAccount());
        vmCreate.setAuthPass(allcateResVe.getManagementPassword());
        vmCreate.setVirtEnvType(allcateResVe.getVirtualPlatformType());
        vmCreate.setVirtEnvUuid(allcateResVe.getResTopologySid());
        vmCreate.setRegion(resVmInst.getRegion());
        vmCreate.setName(resVm.getVmName());
        vmCreate.setMparProfile(resVm.getVmName());
        vmCreate.setVncPort("");
        vmCreate.setSoftware(JsonUtil.toJson(installedSoftwares));
        Criteria codeCriteria = new Criteria();
        codeCriteria.put("codeValue", resVm.getOsVersion());
        List<Code> osVersionList = this.codeMapper.selectByParams(codeCriteria);
        Code osVersion = osVersionList.get(0);
        vmCreate.setOsCategoryDetail(osVersion.getAttribute1());

        // 添加用户
        if (resVmInst.getVmUsers() != null && !resVmInst.getVmUsers().isEmpty()) {
            List<VmUserAdd> vmUserAdds = new ArrayList<>();
            for (VmUser vmUser : resVmInst.getVmUsers()) {
                VmUserAdd vmUserAdd = new VmUserAdd();
                //目标虚拟机IP
                vmUserAdd.setServerIP(alocateResIp.getIpAddress());
                //目标虚拟机root用户
                vmUserAdd.setServerUser(resVm.getManagementAccount());
                //目标虚拟机root密码
                vmUserAdd.setServerPwd(resVm.getManagementPassword());
                //需要添加的非root用户
                vmUserAdd.setNonRootUser(vmUser.getUserName());
                //添加非root用户密码
                vmUserAdd.setNonRootPwd(vmUser.getPassword());
                //目标机操作系统类型。
                vmUserAdd.setOsType(resImage.getOsType());
                vmUserAdds.add(vmUserAdd);
            }
            vmCreate.setUsers(vmUserAdds);
        }

        // 查询镜像
        vmCreate.setOsCategory(resImage.getOsType());
        vmCreate.setImage(resImage.getImageId());
        vmCreate.setAdminName(resVm.getManagementAccount());
        vmCreate.setAdminPass(resVm.getManagementPassword());
        if (WebConstants.VirtualPlatformType.HMC.equals(allcateResVe.getVirtualPlatformType())) {
            // PowerVm
            // 查询主机下CPU池
            if (resVm.getParType() != null &&
                    WebConstants.PowerPartitionType.MPAR.equals(resVm.getParType().toString())) {
                // 在CPU POOL中查找可用总数
                List<ResCpuPool> resCpuPools = this.resCpuPoolMapper.selectByPoolWithSum(resVm.getAllocateResHostSid());
                if (resCpuPools.size() > 0) {
                    // 未指定资源池的情况
                    if (StringUtil.isNullOrEmpty(resVmInst.getCpuPoolSid())) {
                        int maxFreeIndex = 0;
                        float maxFree = 0f;
                        for (int i = 0; i < resCpuPools.size(); i++) {
                            float tmp = resCpuPools.get(i).getMaxValue() - resCpuPools.get(i).getTotalUsedCpuValue();
                            if (tmp > maxFree) {
                                maxFree = tmp;
                                maxFreeIndex = i;
                            }
                        }
                        vmCreate.setSharedProcPoolID(resCpuPools.get(maxFreeIndex).getCpuPoolId());
                    } else {
                        // 查找指定资源池的cpu pool id
                        for (ResCpuPool resCpuPool : resCpuPools) {
                            if (resCpuPool.getResCpuPoolSid().equals(resVmInst.getCpuPoolSid())) {
                                vmCreate.setSharedProcPoolID(resCpuPool.getCpuPoolId());
                                break;
                            }
                        }
                    }

                }
            }

            if (WebConstants.PowerPartitionType.MPAR.equals(resVmInst.getPartitionType())) {
                vmCreate.setVmType("mpar");
            } else {
                vmCreate.setVmType("lpar");
            }

            // LPAR 对应CPU参数
            vmCreate.setDesiredProcs(resVm.getCpuCores());
            vmCreate.setMaxProcs(resVm.getCpuCoresMax());
            vmCreate.setMinProcs(resVm.getCpuCoresMin());
            // 暂时无用
            vmCreate.setDesiredProcUnits(resVm.getPowerCpuUsedUnits());
            vmCreate.setMaxProcUnits(resVm.getPowerCpuMaxUnits());
            vmCreate.setMinProcUnits(resVm.getPowerCpuMinUnits());
            // 对应内存参数
            vmCreate.setDesiredMem(resVm.getMemorySize().toString());
            vmCreate.setMaxMem(StringUtil.nullToEmpty(resVm.getMemorySize() * 4));
            vmCreate.setMinMem(StringUtil.nullToEmpty(resVm.getMemorySize() * 0.5));

            vmCreate.setProcMode("shared");
            vmCreate.setSharingMode("uncap");
            vmCreate.setUncapWeight(128);
            vmCreate.setMaxVirtualSlots("50");
            vmCreate.setLparEnv("aixlinux");
        } else {
            vmCreate.setCpu(resVm.getCpuCores().toString());
            vmCreate.setMemory(resVm.getMemorySize().toString());
        }
        ResHost resHost1 = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
        vmCreate.setHostName(resHost1.getHostName());
        vmCreate.setId(resVm.getResVmSid());
        vmCreate.setOsName(resVmInst.getVmSystemName());

        //存储HBA卡参数
        if (!StringUtil.isNullOrEmpty(resVmInst.getStHbaCardSids())) {
            String[] hbaCardSids = resVmInst.getStHbaCardSids().split(",");
            for (String cardid : hbaCardSids) {
                FC fc = new FC();
                ResHostItem stHbaItem = this.resHostItemMapper.selectByPrimaryKey(cardid);
                fc.setHostItemAddr(stHbaItem.getHostItemAddr());
                fc.setHostItemIndex(stHbaItem.getHostItemIndex());
                fcList.add(fc);
            }
        }

        // 组装磁盘参数
        // LPAR磁盘单个大小，单位GB
        // FIXME start
        //            Long diskSize = Long.parseLong(PropertiesUtil.getProperty("host.item.disk.size"));
        Long diskSize = 140L;
        // FIXME end
        Criteria criteria1 = new Criteria();
        criteria1.put("resVmSid", resVm.getResVmSid());
        List<ResVd> resVdListParams = this.resVdMapper.selectByParams(criteria1);
        for (ResVd resVd : resVdListParams) {
            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
            if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose())) {

                // 当为power时，需要把系统盘放到vmdatadisk中
                if (WebConstants.VirtualPlatformType.HMC.equals(allcateResVe.getVirtualPlatformType())) {
                    VmDisk vmDataDisk = new VmDisk();
                    FC fc = new FC();
                    ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
                    if (WebConstants.PowerPartitionType.MPAR.equals(resVm.getParType().toString())) {
                        // 回调的时候使用id判断
                        vmDataDisk.setId(resVd.getResVdSid());
                        vmDataDisk.setSspName(resStorage.getStorageName());
                        vmDataDisk.setName(resVm.getVmName());
                        vmDataDisk.setSize(resVd.getAllocateDiskSize().toString());
                        vmDataDisk.setType(WebConstants.VmDiskType.SYS_DISK);
                        vmDataDisk.setClusterName(resStorage.getPowerClusterName());
                        Criteria criteria11 = new Criteria();
                        criteria11.put("resHostSid", resVm.getAllocateResHostSid());
                        List<ResVios> resVioses = this.resViosMapper.selectByParams(criteria11);
                        if (resVioses != null && resVioses.size() > 0) {
                            Vios vios = new Vios();
                            vios.setViosIp(resVioses.get(0).getIp());
                            vios.setViosUser(resVioses.get(0).getUser());
                            vios.setViosPassword(resVioses.get(0).getPassword());
                            vios.setViosLparName(resVioses.get(0).getViosLparName());
                            vios.setViosLparProfile(resVioses.get(0).getViosProfile());
                            vmDataDisk.setVios(vios);
                        }
                        vmDataDiskList.add(vmDataDisk);
                    } else {
                        for (ResVdInst resVdInst : resVmInst.getDataDisks()) {

                            if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVdInst.getStoragePurpose())) {
                                ResHostItem hba = new ResHostItem();
                                if (StringUtil.isNullOrEmpty(resVdInst.getSysDiskHBASid())) {
                                    // 查询主机设备
                                    Criteria example = new Criteria();
                                    example.put("resHostSid", resVm.getAllocateResHostSid());
                                    example.put("hostItemTypeCode", WebConstants.HostItemTypeCode.LOCAL_DISK);
                                    example.put("resAllocStatus", WebConstants.HostItemAllocStatus.FREE);
                                    List<ResHostItem> resHostItems = this.resHostItemMapper.selectByParams(example);
                                    if (resHostItems != null && resHostItems.size() > 0) {
                                        fc.setHostItemIndex(resHostItems.get(0).getHostItemIndex());
                                        fc.setHostItemAddr(resHostItems.get(0).getHostItemAddr());
                                        fcList.add(fc);

                                        hba = resHostItems.get(0);
                                    }
                                    //										vmDataDiskList.add(vmDataDisk);

                                } else {
                                    hba = this.resHostItemMapper.selectByPrimaryKey(resVdInst.getSysDiskHBASid());

                                    fc.setHostItemIndex(hba.getHostItemIndex());
                                    fc.setHostItemAddr(hba.getHostItemAddr());
                                    fcList.add(fc);

                                    //										vmDataDiskList.add(vmDataDisk);
                                }
                                // 查找HBA卡关联磁盘，并插入
                                List<ResHostItem> diskList = this.resHostItemMapper.selectByParams(new Criteria().put(
                                        "supHostItemId",
                                        hba.getHostItemId()
                                ).put("hostItemTypeCode", WebConstants.HostItemTypeCode.DISK));
                                if (diskList.size() > 0) {
                                    // 将申请的系统盘容量改为140GB×挂载磁盘数
                                    resVd.setAllocateDiskSize(diskSize * diskList.size());
                                    resVd.setUuid(hba.getUuid());
                                    this.resVdMapper.updateByPrimaryKeySelective(resVd);
                                }
                                // TODO RelateResSid暂时更新为VM的SID，后面将加入新的字段进行维护VD的关系
                                // HBA卡预先占用
                                hba.setRelateResSid(resVm.getResVmSid());
                                hba.setResAllocStatus(WebConstants.HostItemAllocStatus.OCCUPY);
                                hba.setResAllocFlag(Integer.parseInt(WebConstants.ResHostItemAllocFlag.OCCUPIED));
                                this.resHostItemMapper.updateByPrimaryKey(hba);

                                // HBA卡挂载磁盘预先占用
                                for (ResHostItem hostItem : diskList) {
                                    hostItem.setRelateResSid(resVm.getResVmSid());
                                    hostItem.setResAllocStatus(WebConstants.HostItemAllocStatus.OCCUPY);
                                    hostItem.setResAllocFlag(Integer.parseInt(WebConstants.ResHostItemAllocFlag.OCCUPIED));
                                    this.resHostItemMapper.updateByPrimaryKey(hostItem);
                                }
                                break;
                            }
                        }
                    }
                } else {
                    if (resStorage != null) {
                        vmCreate.setSysDiskLocation(resStorage.getStorageName());
                    }
                    vmCreate.setSysDiskSize(resVd.getAllocateDiskSize().toString());
                }

            } else {
                // 组装数据盘磁盘参数
                VmDisk vmDataDisk = new VmDisk();
                if (resStorage != null) {
                    // 回调的时候使用id判断
                    vmDataDisk.setId(resVd.getResVdSid());
                    vmDataDisk.setLocation(resStorage.getStorageName());
                    vmDataDisk.setName(resVd.getVdName());
                    vmDataDisk.setSize(resVd.getAllocateDiskSize().toString());
                    vmDataDisk.setLvmParam(resVd.getLogicVolume());
                    vmDataDisk.setFileSystem(resVd.getFileSystemType());
                    vmDataDisk.setMountLocal(resVd.getMountPoint());
                    // HMC的时候，数据盘加上VIOS信息
                    if (WebConstants.VirtualPlatformType.HMC.equals(allcateResVe.getVirtualPlatformType())) {
                        Criteria criteria11 = new Criteria();
                        criteria11.put("resHostSid", resVm.getAllocateResHostSid());
                        List<ResVios> resVioses = this.resViosMapper.selectByParams(criteria11);
                        if (resVioses != null && resVioses.size() > 0) {
                            Vios vios = new Vios();
                            vios.setViosIp(resVioses.get(0).getIp());
                            vios.setViosUser(resVioses.get(0).getUser());
                            vios.setViosPassword(resVioses.get(0).getPassword());
                            vios.setViosLparName(resVioses.get(0).getViosLparName());
                            vios.setViosLparProfile(resVioses.get(0).getViosProfile());
                            vmDataDisk.setVios(vios);
                        }
                        vmDataDisk.setType(WebConstants.VmDiskType.DATA_DISK);
                    }
                    vmDataDiskList.add(vmDataDisk);
                }
            }
        }
        vmCreate.setDisks(vmDataDiskList);
        vmCreate.setNics(vmNicList);
        vmCreate.setFcs(fcList);

        return vmCreate;
    }

    @Override
    public ResInstResult operateVm(String resVmSid, ServiceBaseInput baseInput, String action, String rebootType) {

        // 更新虚拟机为中间状态
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        // 检查zoneID是否传递，如果没有则用vmSid查询
        if (Strings.isNullOrEmpty(baseInput.getZoneId())) {
            baseInput.setZoneId(this.resTopologyMapper.selectZoneByVm(resVmSid));
        }
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail(action + "虚拟机");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(WebConstants.TaskType.OPERATE_VM);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        try {
            VmOperate vmOperate = new VmOperate();
            switch (action) {
                case WebConstants.VmOperation.START:
                    resVm.setStatus(WebConstants.ResVmStatus.BOOTING);
                    break;
                case WebConstants.VmOperation.STOP:
                    resVm.setStatus(WebConstants.ResVmStatus.POWERINGOFF);
                    break;
                case WebConstants.VmOperation.PAUSE:
                    resVm.setStatus(WebConstants.ResVmStatus.PAUSEING);
                    break;
                case WebConstants.VmOperation.SUSPEND:
                    resVm.setStatus(WebConstants.ResVmStatus.SUSPENDING);
                    break;
                case WebConstants.VmOperation.RESUME:
                    resVm.setStatus(WebConstants.ResVmStatus.RECOVERING);
                    break;
                case WebConstants.VmOperation.REBOOT:
                    vmOperate.setRebootType(rebootType);
                    resVm.setStatus(WebConstants.ResVmStatus.REBOOTING);
                    break;
            }

            this.resVmMapper.updateByPrimaryKeySelective(resVm);
            ResVe resVe = this.resBaseService.getVeFromZone(baseInput.getZoneId());

            // 处理Driver基础信息
            this.resBaseService.setAdapterBaseInfo(resVe, baseInput, vmOperate);

            // 处理Action参数
            vmOperate.setVmName(resVm.getVmName());
            vmOperate.setAction(action);
            vmOperate.setId(resVm.getResVmSid());
            vmOperate.setUuid(resVm.getUuid());

            // Power操作单独处理
            if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
                vmOperate.setHostName(resHost.getHostName());
                vmOperate.setProFile(resVm.getParProfile());
                if (WebConstants.VmOperation.START.equals(action)) {
                    vmOperate.setAction(WebConstants.VmOperation.ACTIVATE);
                } else if (WebConstants.VmOperation.STOP.equals(action)) {
                    vmOperate.setAction(WebConstants.VmOperation.SHUTDOWN);
                }
            }

            logger.info("输入MQ参数：" + JsonUtil.toJson(vmOperate));
            String msgId = MQHelper.sendMessage(vmOperate);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("resVmSid", resVmSid);
            map.put("action", action);
            ResInstResult result = new ResInstResult(ResInstResult.SUCCESS, action + "虚拟机调用成功", map);
            return result;
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("操作虚拟机调用异常：" + e.getMessage());
            taskLogger.fail(log);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("resVmSid", resVmSid);
            map.put("action", action);
            ResInstResult result = new ResInstResult(ResInstResult.FAILURE, "操作虚拟机调用异常:" + e.getMessage(), map);
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 调整虚拟机
     */
    @Override
    @Deprecated
    public ResInstResult reconfigVm(String resVmSid, long mgtObjSid, int cpu, long memory) {
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        MgtObj mgtObj = this.mgtObjMapper.selectByPrimaryKey(mgtObjSid);
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("虚拟机调整配置");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(WebConstants.TaskType.RECONFIG_VM);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        try {

            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
            ResVe resVe = this.getVeByRes(resHost);

            // 查询主机所在计算资源池
            Criteria example = new Criteria();
            example.put("resTopologySid", resHost.getResPoolSid());

            // 查询资源池配置
            List<ResTopologyConfig> resTConfigList = this.resTopologyConfigMapper.selectByParams(example);

            // 资源分配策略
            String allocationPolicy = null;

            // 资源分配方式
            String allocationMode = null;

            // 资源分配比率
            String allocationRate = null;

            // 资源可分配阀值
            String allocationThreshold = null;
            for (ResTopologyConfig resTopConfig : resTConfigList) {
                if (WebConstants.ResPoolConfigKey.ALLOCATIONPOLICY.equals(resTopConfig.getConfigKey())) {
                    allocationPolicy = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONMODE.equals(resTopConfig.getConfigKey())) {
                    allocationMode = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONRATE.equals(resTopConfig.getConfigKey())) {
                    allocationRate = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONTHRESHOLD.equals(resTopConfig.getConfigKey())) {
                    allocationThreshold = resTopConfig.getConfigValue();
                }
            }
            if (WebConstants.AllocationMode.MEMORY.equals(allocationMode)) {

                if (memory > 0) {
                    // 总的可分配内存
                    BigDecimal allMemory = new BigDecimal(resHost.getMemorySize()).multiply(new BigDecimal(
                            allocationRate)).multiply(new BigDecimal(allocationThreshold));

                    // 从已分配的资源实例中取出已分配内存
                    Long resInstanceAllocateMemory = getResVmAllocateMemory(resHost);
                    logger.info(resHost.getHostName() + "总可用内存：" + allMemory);
                    logger.info(resHost.getHostName() + "已分配内存：" +
                                        (resInstanceAllocateMemory + resHost.getVmMemoryAllotSize()));
                    // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                    // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                    BigDecimal restMemory = allMemory.subtract(new BigDecimal(resHost.getVmMemoryAllotSize()))
                                                     .subtract(new BigDecimal(resInstanceAllocateMemory));

                    if (restMemory.compareTo(new BigDecimal(memory)) < 0) {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源内存不足。");
                    }
                }

            } else if (WebConstants.AllocationMode.CPU.equals(allocationMode)) {
                if (cpu > 0) {
                    BigDecimal allCpu = null;
                    if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                        // 查询主机下CPU池
                        Criteria criteria = new Criteria();
                        criteria.put("resHostSid", resVm.getAllocateResHostSid());
                        List<ResCpuPool> resCpuPool = this.resCpuPoolMapper.selectByParams(criteria);
                        if (resCpuPool != null && resCpuPool.size() > 0) {
                            allCpu = new BigDecimal(resCpuPool.get(0).getReservedValue());
                        }
                    } else {
                        // 总的可分配CPU
                        allCpu = new BigDecimal(resHost.getCpuCores()).multiply(new BigDecimal(allocationRate))
                                                                      .multiply(new BigDecimal(allocationThreshold));
                    }

                    // 从已分配的资源实例中取出已分配CPU
                    int resVmAllocateCpu = getResVmAllocateCpu(resHost);
                    logger.info(resHost.getHostName() + "总可用CPU：" + allCpu);
                    logger.info(resHost.getHostName() + "已分配CPU：" + (resVmAllocateCpu + resHost.getVmCpuCore()));
                    // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                    // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                    BigDecimal restCpu = allCpu.subtract(new BigDecimal(resHost.getVmCpuCore()))
                                               .subtract(new BigDecimal(resVmAllocateCpu));

                    if (restCpu.compareTo(new BigDecimal(cpu)) < 0) {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源CPU不足。");
                    }
                }

            }

            VmReconfig vmReconfig = new VmReconfig();
            vmReconfig.setAuthUrl(resVe.getManagementUrl());
            vmReconfig.setAuthUser(resVe.getManagementAccount());
            vmReconfig.setAuthPass(resVe.getManagementPassword());
            vmReconfig.setProviderType(resVe.getVirtualPlatformType());
            vmReconfig.setVirtEnvType(resVe.getVirtualPlatformType());
            vmReconfig.setVirtEnvUuid(resVe.getResTopologySid().toString());
            vmReconfig.setVmName(resVm.getVmName());
            vmReconfig.setTenantId(mgtObj.getUuid());
            vmReconfig.setHostName(resHost.getHostName());
            vmReconfig.setTenantName(StringUtil.nullToEmpty(mgtObj.getMgtObjSid()));
            // vmReconfig.setTenantId("6b9f67c0939b44a3a01a13da100e744d");
            // vmReconfig.setTenantName("admin");
            // vmReconfig.setVmId(resVm.getResVmSid());
            if (cpu != 0) {
                // vmModify.setOldCpu(StringUtil.nullToEmpty(resVm.getCpuCores()));
                vmReconfig.setCpu(StringUtil.nullToEmpty(cpu + resVm.getCpuCores()));
                // 设置虚拟机cpu
                // resVm.setCpuCores(cpu + resVm.getCpuCores());
            }
            if (memory != 0) {
                // vmModify.setOldMemory(StringUtil.nullToEmpty(resVm.getMemorySize()));
                vmReconfig.setMemory(StringUtil.nullToEmpty(memory + resVm.getMemorySize()));
                // 设置虚拟机memory
                // resVm.setMemorySize(memory + resVm.getMemorySize());
            }
            resVm.setStatus(WebConstants.ResVmStatus.SETTING);

            WebUtil.prepareUpdateParams(resVm);
            // 更新虚拟机
            this.resVmMapper.updateByPrimaryKeySelective(resVm);
            logger.info("输入MQ参数" + JsonUtil.toJson(vmReconfig));
            String msgId = MQHelper.sendMessage(vmReconfig);
            ResInstResult result = new ResInstResult(ResInstResult.SUCCESS, "调整虚拟机调用成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("调整虚拟机调用异常：" + e.getMessage());
            taskLogger.fail(log);
            ResInstResult result = new ResInstResult(ResInstResult.FAILURE, "MQ异常:" + e.getMessage());
            return result;
        }

    }

    /**
     * 删除虚拟机
     */
    @Override
    public ResInstResult deleteVm(ResCommonInst resCommonInst) {
        JsonNode jsonNode = JsonUtil.fromJson(resCommonInst.getResSpecParam());
        String resVmSid = jsonNode.get("resVmSid").getTextValue();
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        // 插入任务表
        TaskLog log = new TaskLog();
        String userAccount = resCommonInst.getUserAccount();
        if (!Strings.isNullOrEmpty(userAccount)) {
            log.setAccount(userAccount);
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("删除虚拟机");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(WebConstants.TaskType.DELETE_VM);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        try {

            // 更新状态为删除中
            resVm.setStatus(WebConstants.ResVmStatus.DELETING);
            WebUtil.prepareUpdateParams(resVm);
            this.resVmMapper.updateByPrimaryKeySelective(resVm);

            // 更新磁盘为删除中
            Criteria criteria = new Criteria();
            criteria.put("resVmSid", resVm.getResVmSid());
            List<ResVd> resVdList = this.resVdMapper.selectByParams(criteria);
            List<Volume> volumes = new ArrayList<Volume>();

            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
            ResVe resVe = this.resBaseService.getVeFromZone(resCommonInst.getZoneId());
            for (ResVd resVd : resVdList) {
                // 为OpenStack的时候,数据盘不做处理
                if (WebConstants.VirtualPlatformType.OPENSTACK.equals(resVe.getVirtualPlatformType()) &&
                        WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
                    continue;
                }
                //HMC环境下的VD删除时需要传给后台它关联的Volume
                if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType()) &&
                        WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
                    //得到VOlumes
                    Criteria example = new Criteria();
                    example.put("resStorageSid", resVd.getAllocateResStorageSid());
                    List<ResVolume> vdVolumes = resVolumeMapper.selectByParams(example);
                    if (!CollectionUtils.isEmpty(vdVolumes)) {
                        for (ResVolume resVolume : vdVolumes) {
                            Volume volume = new Volume();
                            volume.setId(new URI(resVolume.getResVolumeSid()));
                            volume.setSize(Integer.parseInt(resVolume.getSize() + ""));
                            volume.setName(resVolume.getVolumeName());
                            volume.setWwn(resVolume.getVolumeWwn());
                            volumes.add(volume);
                        }
                    }
                }
                resVd.setStatus(WebConstants.ResVdStatus.DELETING);
                this.resVdMapper.updateByPrimaryKeySelective(resVd);
            }

            VmRemove vmRemove = new VmRemove();
            this.resBaseService.setAdapterBaseInfo(resVe, resCommonInst, vmRemove);
            vmRemove.setSid(resVm.getResVmSid());
            vmRemove.setVmName(resVm.getVmName());
            vmRemove.setOsName(resVm.getOsName());
            vmRemove.setHostName(resHost.getHostName());
            if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                vmRemove.setId(resVm.getParId());
            } else {
                vmRemove.setId(resVm.getUuid());
            }
            vmRemove.setVolumes(volumes);
            vmRemove.setDueToFailOfCreating(Boolean.valueOf(jsonNode.get("isDueToCreateVm").getTextValue()));
            if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                if (WebConstants.PowerPartitionType.MPAR.equals(resVm.getParType().toString())) {

                    //查询VIOS
                    criteria = new Criteria();
                    criteria.put("resHostSid", resHost.getResHostSid());
                    List<ResVios> resVioss = this.resViosMapper.selectByParams(criteria);
                    if (resVioss != null && resVioss.size() > 0) {
                        Vios viosVo = new Vios();
                        viosVo.setViosIp(resVioss.get(0).getIp());
                        viosVo.setViosLparName(resVioss.get(0).getViosLparName());
                        viosVo.setViosLparProfile(resVioss.get(0).getViosProfile());
                        viosVo.setViosPassword(resVioss.get(0).getPassword());
                        viosVo.setViosUser(resVioss.get(0).getUser());
                        List<Vios> viosVos = new ArrayList<Vios>();
                        viosVos.add(viosVo);
                        vmRemove.setVisoList(viosVos);
                    }
                    //查询系统盘
                    for (ResVd resVd : resVdList) {
                        if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose())) {
                            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
                            vmRemove.setCluster(resStorage.getPowerClusterName());
                            vmRemove.setSsp(resStorage.getStorageName());
                            vmRemove.setSysDiskUuid(resVd.getUuid());
                        }
                    }
                    vmRemove.setVmType("mpar");
                } else {
                    vmRemove.setVmType("lpar");
                }
            }

            //传IP
            List<ResVmNetwork> nets = resVmNetworkMapper.selectByVmSid(resVmSid);
            if (nets != null && nets.size() > 0) {
                vmRemove.setIp(nets.get(0).getIpAddress());
            }

            logger.info("输入MQ参数：" + JsonUtil.toJson(vmRemove));
            String msgId = MQHelper.sendMessage(vmRemove);
            ResInstResult result = new ResInstResult(ResInstResult.SUCCESS, "删除虚拟机调用成功");
            return result;
        } catch (Exception e) {
            logger.error("删除虚拟机调用异常：", e);
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("删除虚拟机调用异常：" + e.getMessage());
            taskLogger.fail(log);
            ResInstResult result = new ResInstResult(ResInstResult.FAILURE, "删除虚拟机调用异常：" + e.getMessage());
            return result;
        }
    }

    /**
     * 处理工单后，删除虚拟机
     *
     * @param resVmSid 虚拟机Sid
     * @return ResInstResult
     */
    @Override
    public boolean deleteVmAfterDeal(String resVmSid) {
        boolean result = true;
        // 查询虚拟机信息，作为返回参数
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
        // 更新已挂载块存储为未挂载的
        Criteria criteria = new Criteria();
        criteria.put("resVmSid", resVm.getResVmSid());

        // FIXME 当前版本不用OPENSTACK
        ResVe resVe = this.getVeByRes(resHost);
        List<ResVd> resVdList = this.resVdMapper.selectByParams(criteria);

        // 当为OpenStack的时候，数据盘只是取消关联
        if (WebConstants.VirtualPlatformType.OPENSTACK.equals(resVe.getVirtualPlatformType())) {
            for (ResVd resVd : resVdList) {
                if (WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
                    resVd.setResVmSid("");
                    WebUtil.prepareUpdateParams(resVd);
                    this.resVdMapper.updateByPrimaryKey(resVd);
                }
            }
        }
        this.resVdMapper.deleteByParams(criteria);

        // 删除虚拟机网络关系，并更新IP为有效
        this.resVmNetworkMapper.deleteByPrimaryKey(resVm.getResVmSid());

        // 更改配件表的占用信息
        criteria = new Criteria();
        criteria.put("relateResSid", resVm.getResVmSid());
        List<ResHostItem> resHostItems = this.resHostItemMapper.selectByParams(criteria);
        for (ResHostItem resHostItem : resHostItems) {
            resHostItem.setRelateResSid(null);
            resHostItem.setResAllocFlag(Integer.parseInt(WebConstants.ResHostItemAllocFlag.NOT_OCCUPIED));
            resHostItem.setResAllocStatus(WebConstants.HostItemAllocStatus.FREE);
            this.resHostItemMapper.updateByPrimaryKey(resHostItem);
        }

        // 删除虚拟机
        this.resVmMapper.deleteByPrimaryKey(resVm.getResVmSid());

        return result;
    }

    /**
     * 虚拟机添加网络
     */
    @Override
    public ResInstResult addNetsForVm(String resVmSid, List<ResNetworkInst> vmNetList) {

        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }

        log.setTaskDetail("虚拟机添加网卡");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(WebConstants.TaskType.ADD_NET_VM);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        String msgId = null;
        ResInstResult result = new ResInstResult();
        try {
            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
            ResTopology resVc = this.resTopologyMapper.selectByPrimaryKey(resHost.getParentTopologySid());
            ResVe resVe = new ResVe();
            if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resVc.getResTopologyType())) {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getResTopologySid());
            } else {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getParentTopologySid());
            }
            VmNicAdd vmNicAdd = new VmNicAdd();
            vmNicAdd.setAuthUrl(resVe.getManagementUrl());
            vmNicAdd.setAuthUser(resVe.getManagementAccount());
            vmNicAdd.setAuthPass(resVe.getManagementPassword());
            vmNicAdd.setProviderType(resVe.getVirtualPlatformType());
            vmNicAdd.setVirtEnvType(resVe.getVirtualPlatformType());
            vmNicAdd.setVirtEnvUuid(resVe.getResTopologySid().toString());

            Criteria criteria = new Criteria();
            criteria.put("codeValue", resVm.getOsVersion());
            List<Code> osVersionList = this.codeMapper.selectByParams(criteria);
            Code osVersion = osVersionList.get(0);
            vmNicAdd.setOsType(osVersion.getParentCodeValue());
            vmNicAdd.setOsTypeDetail(osVersion.getAttribute1());
            vmNicAdd.setVmName(resVm.getVmName());

            List<VmNic> nics = new ArrayList<VmNic>();
            for (ResNetworkInst resNet : vmNetList) {
                // 挑选可用IP
                ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(resNet.getResNetworkId());
                Criteria vlanCri = new Criteria();
                vlanCri.put("vlanId", resNetwork.getVlanId());
                ResVlan resVlan = this.resVlanMapper.selectByParams(vlanCri).get(0);
                Criteria criteria1 = new Criteria();
                criteria1.put("resNetworkSid", resNetwork.getResNetworkSid());
                criteria1.put("status", WebConstants.ResIpUsageStatus.AVAILABLE);
                List<ResIp> ipList = this.resIpMapper.selectByParams(criteria1);
                if (ipList != null && ipList.size() > 0) {
                    ResIp resIp = ipList.get(0);

                    // 组装网卡参数
                    VmNic vmNic = new VmNic();
                    vmNic.setDns(resNetwork.getDns1());
                    vmNic.setGateway(resNetwork.getGateway());
                    vmNic.setNetmask(resNetwork.getSubnetMask());
                    vmNic.setPort(resVlan.getTag());
                    vmNic.setPrivateIp(resIp.getIpAddress());
                    nics.add(vmNic);
                } else {
                    log = new TaskLog();
                    log.setTaskLogSid(taskId);
                    log.setTaskDetail("IP资源不足");
                    log.setTaskStatus(WebConstants.TaskStatus.FAIL);
                    log.setTaskFailureReason("IP资源不足");
                    taskLogger.fail(log);
                    result = new ResInstResult(ResInstResult.FAILURE, "IP资源不足");
                    throw new RpcException(RpcException.BIZ_EXCEPTION, "IP资源不足");
                }
            }
            vmNicAdd.setNics(nics);
            msgId = MQHelper.sendMessage(vmNicAdd);
        } catch (MQException e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("虚拟机添加网卡异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, "虚拟机添加网卡异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 虚拟机删除网络
     */
    @Override
    public ResInstResult deleteNetsForVm(String resVmSid, List<ResNetworkInst> vmNetList) {
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("虚拟机删除网卡");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(WebConstants.TaskType.DEL_NET_VM);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        String msgId = null;
        ResInstResult result = new ResInstResult();
        try {
            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
            ResTopology resVc = this.resTopologyMapper.selectByPrimaryKey(resHost.getParentTopologySid());
            ResVe resVe = new ResVe();
            if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resVc.getResTopologyType())) {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getResTopologySid());
            } else {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getParentTopologySid());
            }
            VmNicDelete vmNicDelete = new VmNicDelete();
            vmNicDelete.setAuthUrl(resVe.getManagementUrl());
            vmNicDelete.setAuthUser(resVe.getManagementAccount());
            vmNicDelete.setAuthPass(resVe.getManagementPassword());
            vmNicDelete.setProviderType(resVe.getVirtualPlatformType());
            vmNicDelete.setVirtEnvType(resVe.getVirtualPlatformType());
            vmNicDelete.setVirtEnvUuid(resVe.getResTopologySid().toString());

            vmNicDelete.setVmName(resVm.getVmName());

            List<VmNic> nics = new ArrayList<VmNic>();
            for (ResNetworkInst resNet : vmNetList) {
                VmNic nic = new VmNic();
                if (resNet.getResNetworkId() != null) {
                    Criteria criteria = new Criteria();
                    criteria.put("resVmSid", resVmSid);
                    criteria.put("resNetworkSid", resNet.getResNetworkId());
                    List<ResVmNetwork> resVmNetList = this.resVmNetworkMapper.selectByParams(criteria);
                    ResVmNetwork resVmNet = resVmNetList.get(0);
                    nic.setPrivateIp(resVmNet.getIpAddress());
                } else {
                    nic.setPrivateIp(resNet.getIpAddress());
                }
                nics.add(nic);
            }
            vmNicDelete.setNics(nics);
            msgId = MQHelper.sendMessage(vmNicDelete);
        } catch (MQException e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("虚拟机删除网卡异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, "虚拟机删除网卡异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 整体重新配置虚拟机
     */
    @Override
    public ResInstResult reconfigVm(ResVmInst resVmInst) {
        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmInst.getResVmInstId());

        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("虚拟机配置");
        log.setTaskTarget(resVm.getVmName());
        log.setTaskType(WebConstants.TaskType.RECONFIG_VM);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        log.setTaskLogSid(taskId);
        String msgId = null;
        ResInstResult result = new ResInstResult();
        try {
            // 判断虚拟机是否关闭
            // this.checkIsNeedPoweroff(resVmInst);
            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
            ResTopology resVc = this.resTopologyMapper.selectByPrimaryKey(resHost.getParentTopologySid());
            ResVe resVe = new ResVe();
            if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resVc.getResTopologyType())) {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getResTopologySid());
            } else {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getParentTopologySid());
            }

            if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                VmReconfig vmReconfig = new VmReconfig();
                vmReconfig.setAuthUrl(resVe.getManagementUrl());
                vmReconfig.setAuthUser(resVe.getManagementAccount());
                vmReconfig.setAuthPass(resVe.getManagementPassword());
                vmReconfig.setProviderType(resVe.getVirtualPlatformType());
                vmReconfig.setVirtEnvType(resVe.getVirtualPlatformType());
                vmReconfig.setVirtEnvUuid(resVe.getResTopologySid().toString());

                vmReconfig.setHostIp(resVm.getVmIp());
                vmReconfig.setUserName(resVm.getManagementAccount());
                vmReconfig.setPassword(resVm.getManagementPassword());

                // 受管服务器系统名称
                vmReconfig.setHostName(resHost.getHostName());
                // 分区名称
                vmReconfig.setVmName(resVm.getVmName());
                // 分区概要文件名
                vmReconfig.setParProfile(resVm.getParProfile());
                // 分区ID
                vmReconfig.setParId(resVm.getParId());
                // 分区类型
                vmReconfig.setParType(resVm.getParType().toString());
                if (resVmInst.getMemory() != 0) {
                    // 最小内存值（M） = 期望内存值（M） / 2
                    vmReconfig.setMinMem((int) resVmInst.getMemoryMin());
                    // 期望内存值（M）
                    vmReconfig.setMemory(StringUtil.nullToEmpty(resVmInst.getMemory()));
                    // 最大内存值（M） = 期望内存值（M） * 4
                    vmReconfig.setMaxMem((int) resVmInst.getMemoryMax());
                }
                if (resVmInst.getCpu() != 0) {
                    // 最小处理单元数
                    vmReconfig.setMinProcs(resVmInst.getCpuMin());
                    // 期望处理单元数
                    vmReconfig.setCpu(String.valueOf(resVmInst.getCpu()));
                    // 最大处理单元数
                    vmReconfig.setMaxProcs(resVmInst.getCpuMax());
                    if (WebConstants.PowerPartitionType.MPAR.equals(resVm.getParType().toString())) {
                        // 最小共享物理cpu单元
                        vmReconfig.setMinProcUnits(resVmInst.getPowerCpuMinUnits());
                        // 共享物理cpu单元
                        vmReconfig.setDesiredProcUnits(resVmInst.getPowerCpuUsedUnits());
                        // 最大共享物理cpu单元
                        vmReconfig.setMaxProcUnits(resVmInst.getPowerCpuMaxUnits());
                    }
                }

                //磁盘操作
                List<ResVdInst> dataDisks = resVmInst.getDataDisks();
                if (!CollectionUtils.isEmpty(dataDisks)) {
                    List<VmDisk> disks = assemblePowerDisksParams(resVm, dataDisks);
                    vmReconfig.setDisks(disks);
                }

                resVm.setStatus(WebConstants.ResVmStatus.SETTING);

                WebUtil.prepareUpdateParams(resVm);
                // 更新虚拟机
                this.resVmMapper.updateByPrimaryKeySelective(resVm);

                logger.info("调用MQ参数:" + JsonUtil.toJson(vmReconfig));
                msgId = MQHelper.sendMessage(vmReconfig);
                logger.info("调用MQ时，返回服务层参数:" + JsonUtil.toJson(resVm));
                result = new ResInstResult(ResInstResult.SUCCESS, "虚拟机整体变更中", resVm);

            } else {
                VmModify vmModify = new VmModify();
                vmModify.setAuthUrl(resVe.getManagementUrl());
                vmModify.setAuthUser(resVe.getManagementAccount());
                vmModify.setAuthPass(resVe.getManagementPassword());
                vmModify.setProviderType(resVe.getVirtualPlatformType());
                vmModify.setVirtEnvType(resVe.getVirtualPlatformType());
                vmModify.setVirtEnvUuid(resVe.getResTopologySid().toString());
                Criteria criteria = new Criteria();
                criteria.put("codeValue", resVm.getOsVersion());
                List<Code> osVersionList = this.codeMapper.selectByParams(criteria);
                Code osVersion = osVersionList.get(0);
                vmModify.setOsCategory(osVersion.getParentCodeValue());
                vmModify.setOsCategoryDetail(osVersion.getAttribute1());
                vmModify.setVmName(resVm.getVmName());
                vmModify.setVmId(resVm.getResVmSid());

                vmModify.setTargetSerIp(resVm.getVmIp());
                vmModify.setTargetUser(resVm.getManagementAccount());
                vmModify.setTargetPwd(resVm.getManagementPassword());

                if (resVmInst.getCpu() != 0) {
                    vmModify.setOldCpu(StringUtil.nullToEmpty(resVm.getCpuCores()));
                    vmModify.setCpu(StringUtil.nullToEmpty(resVmInst.getCpu()));
                    // 设置虚拟机cpu
                    resVm.setCpuCores(resVmInst.getCpu());
                }
                if (resVmInst.getMemory() != 0) {
                    vmModify.setOldMemory(StringUtil.nullToEmpty(resVm.getMemorySize()));
                    vmModify.setMemory(StringUtil.nullToEmpty(resVmInst.getMemory()));
                    // 设置虚拟机memory
                    resVm.setMemorySize(resVmInst.getMemory());
                }
                resVm.setStatus(WebConstants.ResVmStatus.SETTING);

                WebUtil.prepareUpdateParams(resVm);
                // 更新虚拟机
                this.resVmMapper.updateByPrimaryKeySelective(resVm);

                // 设置网卡参数
                List<ResNetworkInst> vmNetList = resVmInst.getNets();
                List<VmNic> nics = this.assembleNicsParams(resVm, vmNetList);
                vmModify.setNics(nics);

                // 设置磁盘参数
                List<ResVdInst> resVdInstList = resVmInst.getDataDisks();
                List<VmDisk> disks = this.assembleVmDisksParams(resVm, resVdInstList);
                vmModify.setDisks(disks);
                // ResVm resVM = this.getVmInfo(resVmInst.getResVmInstId());
                logger.info("调用MQ参数:" + JsonUtil.toJson(vmModify));
                msgId = MQHelper.sendMessage(vmModify);
                logger.info("调用MQ时，返回服务层参数:" + JsonUtil.toJson(resVm));
                result = new ResInstResult(ResInstResult.SUCCESS, "虚拟机整体变更中", resVm);
            }

        } catch (Exception ex) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("虚拟机配置失败：" + ex.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, "虚拟机配置失败：" + ex.getMessage(), resVm);
        }
        return result;
    }

    /**
     * 工单处理后，整体重新配置虚拟机
     *
     * @param resVmInst 虚拟机实例pojo
     * @return
     */
    @Override
    public boolean reconfigVmAfterDeal(ResVmInst resVmInst) {
        boolean result = true;

        ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmInst.getResVmInstId());
        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
        ResVe resVe = this.getVeByRes(resHost);
        if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
            if (!StringUtil.isNullOrEmpty(resVmInst.getMemory())) {
                resVm.setMemoryMin(resVmInst.getMemoryMin());
                resVm.setMemorySize(resVmInst.getMemory());
                resVm.setMemoryMax(resVmInst.getMemoryMax());
            }
            if (!StringUtil.isNullOrEmpty(resVmInst.getCpu())) {
                // 最小处理单元数
                resVm.setCpuCoresMin(resVmInst.getCpuMin());
                // 期望处理单元数
                resVm.setCpuCores(resVmInst.getCpu());
                // 最大处理单元数
                resVm.setCpuCoresMax(resVmInst.getCpuMax());
            }
            if (!StringUtil.isNullOrEmpty(resVmInst.getPowerCpuUsedUnits())) {
                // 最小共享物理cpu单元
                resVm.setPowerCpuMinUnits(resVmInst.getPowerCpuMinUnits());
                // 共享物理cpu单元
                resVm.setPowerCpuUsedUnits(resVmInst.getPowerCpuUsedUnits());
                // 最大共享物理cpu单元
                resVm.setPowerCpuMaxUnits(resVmInst.getPowerCpuMaxUnits());
            }
        } else {
            resVm.setCpuCores(resVmInst.getCpu());
            resVm.setMemorySize(resVmInst.getMemory());
        }
        resVm.setStatus(WebConstants.ResVmStatus.NORMAL);
        WebUtil.prepareUpdateParams(resVm);

        this.resVmMapper.updateByPrimaryKeySelective(resVm);

        return result;
    }

    /**
     * 重新配置虚拟机时，组装网卡参数
     *
     * @param resVm
     * @param vmNetList
     * @return
     */
    private List<VmNic> assembleNicsParams(ResVm resVm, List<ResNetworkInst> vmNetList) {
        // 组装网卡参数

        List<VmNic> nics = new ArrayList<VmNic>();
        List<ResIp> resIpList = new ArrayList<ResIp>();
        if (vmNetList != null && vmNetList.size() > 0) {

            // 查询数据库中虚拟机网络
            Criteria example = new Criteria();
            example.put("resVmSid", resVm.getResVmSid());
            example.put("netPrimary", WebConstants.NetPrimary.P);
            List<ResVmNetwork> resVmNetListPrimary = this.resVmNetworkMapper.selectByParams(example);
            ResVmNetwork resVmNet = null;
            if (resVmNetListPrimary != null && resVmNetListPrimary.size() > 0) {
                resVmNet = resVmNetListPrimary.get(0);
            }

            // 查询虚拟机IP
            Criteria example2 = new Criteria();
            example2.put("resVmSid", resVm.getResVmSid());
            List<ResIp> resVmIps = this.resIpMapper.selectByParams(example2);
            List<ResIp> resVmIpsClone = new ArrayList<ResIp>(resVmIps);
            for (ResNetworkInst resNet : vmNetList) {
                if (WebConstants.NetOperate.ADD.equals(resNet.getOperate())) {
                    ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(resNet.getResNetworkId());
                    // 判断主机下是否存储在该vlan的端口组
                    Criteria criteria = new Criteria();
                    criteria.put("vlanId", resNetwork.getVlanId());
                    criteria.put("resHostSid", resVm.getAllocateResHostSid());
                    List<ResVsPortGroup> dvPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria);
                    List<ResVsPortGroup> portList = new ArrayList<ResVsPortGroup>();
                    if (dvPortList != null && dvPortList.size() > 0) {
                        portList = dvPortList;
                    } else {
                        // 当没有对应vlanId的网络时，根据vlantag查询主机下有没有对应的端口组
                        Criteria criteria2 = new Criteria();
                        criteria2.put("vlanId", resNetwork.getVlanId());
                        List<ResVlan> resVlans = this.resVlanMapper.selectByParams(criteria2);
                        if (resVlans != null && resVlans.size() > 0) {
                            Criteria criteria3 = new Criteria();
                            criteria3.put("name", resVlans.get(0).getTag());
                            criteria3.put("resHostSid", resVm.getAllocateResHostSid());
                            List<ResVsPortGroup> svPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria3);
                            if (svPortList != null && svPortList.size() > 0) {
                                portList = svPortList;
                            }
                        } else {
                            throw new RpcException(RpcException.BIZ_EXCEPTION, "分配的网络对应的VLAN不存在。");
                        }
                    }
                    if (portList != null && portList.size() > 0) {
                        // 挑选可用IP
                        ResIp alocateResIp = new ResIp();
                        if (!StringUtil.isNullOrEmpty(resNet.getIpAddress())) {
                            alocateResIp.setIpAddress(resNet.getIpAddress());
                            alocateResIp.setResNetworkSid(resNet.getResNetworkId());
                        } else {
                            // 挑选可用IP
                            alocateResIp = this.getAlallocationResIp(resNet.getResNetworkId());
                        }

                        if (alocateResIp != null) {

                            // 插入虚拟机与网络关系
                            ResVmNetwork resVmNetwork = new ResVmNetwork();
                            resVmNetwork.setIpAddress(alocateResIp.getIpAddress());
                            resVmNetwork.setResNetworkSid(resNet.getResNetworkId());
                            resVmNetwork.setResVmSid(resVm.getResVmSid());
                            resVmNetwork.setStatus(WebConstants.ResVmNetworkStatus.CREATING);
                            this.resVmNetworkMapper.insertSelective(resVmNetwork);

                            // 更新IP状态为已占用
                            // alocateResIp.setStatus(WebConstants.ResIpStatus.OCCUPIED);
                            alocateResIp.setPortGroupName(portList.get(0).getName());
                            // this.resIpMapper.updateByPrimaryKeySelective(alocateResIp);

                            // 设置主网络
                            if (WebConstants.NetPrimary.P.equals(resNet.getNetPrimary())) {
                                if (resVmNet != null) {

                                    // 原有的NetPrimary更新为空
                                    resVmNet.setNetPrimary(null);
                                    this.resVmNetworkMapper.updateByPrimaryKey(resVmNet);

                                    resVmNetwork.setNetPrimary(WebConstants.NetPrimary.P);
                                    this.resVmNetworkMapper.updateByPrimaryKey(resVmNetwork);
                                } else {
                                    resVmNetwork.setNetPrimary(WebConstants.NetPrimary.P);
                                    this.resVmNetworkMapper.updateByPrimaryKey(resVmNetwork);
                                }
                                alocateResIp.setVmPrimaryNet(WebConstants.NetPrimary.P);
                            }

                            alocateResIp.setOperate(WebConstants.NetOperate.ADD);
                            resIpList.add(alocateResIp);
                        } else {
                            throw new RpcException(RpcException.BIZ_EXCEPTION, resNetwork.getNetworkName() + "IP资源不足");
                        }
                    } else {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源没有对应的网络");
                    }
                } else if (WebConstants.NetOperate.DELLETE.equals(resNet.getOperate())) {

                    if (resNet.getResNetworkId() != null) {

                        Criteria criteria = new Criteria();
                        criteria.put("resVmSid", resVm.getResVmSid());
                        criteria.put("resNetworkSid", resNet.getResNetworkId());
                        List<ResVmNetwork> resVmNetList = this.resVmNetworkMapper.selectByParams(criteria);
                        ResVmNetwork resVmNet1 = resVmNetList.get(0);
                        resVmNet1.setStatus(WebConstants.ResVmNetworkStatus.DELETING);
                        this.resVmNetworkMapper.updateByPrimaryKeySelective(resVmNet1);
                        ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(resNet.getResNetworkId());

                        // 判断主机下是否存储在该vlan的端口组
                        Criteria criteria2 = new Criteria();
                        criteria2.put("vlanId", resNetwork.getVlanId());
                        criteria2.put("resHostSid", resVm.getAllocateResHostSid());
                        List<ResVsPortGroup> portList = this.resVsPortGroupMapper.selectPortsByHost(criteria2);
                        if (portList != null && portList.size() > 0) {
                            Criteria criteria1 = new Criteria();
                            criteria1.put("resNetworkSid", resNet.getResNetworkId());
                            criteria1.put("ipAddress", resVmNet1.getIpAddress());

                            // 更新IP为有效
                            List<ResIp> ipList = this.resIpMapper.selectByParams(criteria1);
                            if (ipList != null && ipList.size() > 0) {
                                ResIp resIp = ipList.get(0);
                                // 查询网络所属资源池
                                ResTopology resTopology = this.resTopologyMapper.selectByPrimaryKey(resNetwork.getResPoolSid());
                                resIp.setResPoolType(resTopology.getResTopologyType());
                                resIp.setOperate(WebConstants.NetOperate.DELLETE);
                                resIp.setPortGroupName(portList.get(0).getName());
                                if (WebConstants.NetPrimary.P.equals(resVmNet1.getNetPrimary())) {
                                    resIp.setVmPrimaryNet(WebConstants.NetPrimary.P);
                                }
                                resIp.setVmNetMac(resVmNet1.getMac());
                                resIpList.add(ipList.get(0));

                                // 剔除从数据库中查出来的相同的IP
                                for (ResIp resIpDb : resVmIps) {
                                    if (resIp.getResSid().equals(resIpDb.getResSid())) {
                                        resVmIpsClone.remove(resIpDb);
                                        break;
                                    }
                                }
                            }

                        } else {
                            throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源没有对应的网络");
                        }
                    }
                }

            }

            // 把剔除后的数据库虚拟机IP集合加入到返回集合中
            resIpList.addAll(resVmIpsClone);

            // 组装参数
            if (resIpList != null && resIpList.size() > 0) {

                for (ResIp resIp : resIpList) {

                    // 组装网卡参数
                    VmNic vmNic = new VmNic();
                    if (WebConstants.NetPrimary.P.equals(resIp.getVmPrimaryNet())) {
                        vmNic.setPrimary(true);
                    } else {
                        vmNic.setPrimary(false);
                    }
                    ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(resIp.getResNetworkSid());
                    if (WebConstants.NetOperate.ADD.equals(resIp.getOperate())) {
                        vmNic.setNetId(resNetwork.getResNetworkSid());
                        vmNic.setDns(resNetwork.getDns1());
                        vmNic.setGateway(resNetwork.getGateway());
                        vmNic.setNetmask(resNetwork.getSubnetMask());
                        vmNic.setPort(resIp.getPortGroupName());
                        vmNic.setPrivateIp(resIp.getIpAddress());
                        vmNic.setOperate(WebConstants.NetOperate.ADD);

                    } else if (WebConstants.NetOperate.DELLETE.equals(resIp.getOperate())) {
                        vmNic.setNetId(resIp.getResNetworkSid());
                        vmNic.setPort(resIp.getPortGroupName());
                        vmNic.setOperate(WebConstants.NetOperate.DELLETE);
                        vmNic.setMac(resIp.getVmNetMac());
                    } else {
                        vmNic.setNetId(resNetwork.getResNetworkSid());
                        vmNic.setDns(resNetwork.getDns1());
                        vmNic.setGateway(resNetwork.getGateway());
                        vmNic.setNetmask(resNetwork.getSubnetMask());

                        // 查询端口组名称
                        Criteria criteria2 = new Criteria();
                        criteria2.put("vlanId", resNetwork.getVlanId());
                        criteria2.put("resHostSid", resVm.getAllocateResHostSid());
                        List<ResVsPortGroup> dvPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria2);
                        List<ResVsPortGroup> portList = new ArrayList<ResVsPortGroup>();
                        if (portList != null && portList.size() > 0) {
                            portList = dvPortList;
                        } else {
                            // 当没有对应vlanId的网络时，根据vlantag查询主机下有没有对应的端口组
                            Criteria criteria3 = new Criteria();
                            criteria3.put("vlanId", resNetwork.getVlanId());
                            List<ResVlan> resVlans = this.resVlanMapper.selectByParams(criteria3);
                            if (resVlans != null && resVlans.size() > 0) {
                                Criteria criteria4 = new Criteria();
                                criteria4.put("name", resVlans.get(0).getTag());
                                criteria4.put("resHostSid", resVm.getAllocateResHostSid());
                                List<ResVsPortGroup> svPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria4);
                                if (svPortList != null && svPortList.size() > 0) {
                                    portList = svPortList;
                                }
                            } else {
                                throw new RpcException(RpcException.BIZ_EXCEPTION, "分配的网络对应的VLAN不存在。");
                            }
                        }
                        if (portList != null && portList.size() > 0) {
                            vmNic.setPort(portList.get(0).getName());
                        }
                        vmNic.setPrivateIp(resIp.getIpAddress());
                        vmNic.setOperate(WebConstants.NetOperate.UNCHANGE);
                    }

                    nics.add(vmNic);
                }
            }
        }
        resVm.setResIpList(resIpList);
        return nics;
    }

    /**
     * 重新配置虚拟机时，组装磁盘参数
     *
     * @param resVm
     * @param resVdInstList
     * @return
     */
    private List<VmDisk> assembleVmDisksParams(ResVm resVm, List<ResVdInst> resVdInstList) {
        // 组装磁盘参数
        List<VmDisk> disks = new ArrayList<VmDisk>();
        List<ResStorage> resStoList = new ArrayList<ResStorage>();
        if (resVdInstList != null && resVdInstList.size() > 0) {
            List<ResVd> resVdList = new ArrayList<ResVd>();

            // 查询虚拟机下磁盘，给磁盘递增命名
            Criteria criteria = new Criteria();
            criteria.put("resVmSid", resVm.getResVmSid());
            List<ResVd> resVdListDb = this.resVdMapper.selectByParams(criteria);
            int i = 0;
            for (ResVd resVd : resVdListDb) {
                String vdName = resVd.getVdName();
                int index = vdName.lastIndexOf("_");
                int flag = 0;
                if (index > 0) {
                    String flagIndex = vdName.substring(index + 1);
                    try {
                        flag = Integer.parseInt(flagIndex);
                    } catch (NumberFormatException e) {
                        flag = 0;
                    }
                } else {
                    flag = 0;
                }
                if (flag > i) {
                    i = flag;
                }

            }

            boolean allFlag = false;
            for (ResVdInst resVdInst : resVdInstList) {

                if (WebConstants.VdOperate.ADD.equals(resVdInst.getOperate())) {
                    i++;
                    String vdName = null;
                    // 判断磁盘名称是否指定
                    if (resVdInst.getResVdInstName() != null) {
                        vdName = resVdInst.getResVdInstName();
                    } else {
                        String vdNameDb = resVm.getVmName() + "_" + i;
                        // String vdNameDb =
                        // this.sidService.getMaxSid(WebConstants.SidCategory.VD_NAME);
                        // 判断前缀是否指定
                        if (resVdInst.getResVdInstNamePrefix() != null) {
                            vdName = resVdInst.getResVdInstNamePrefix() + "-" + vdNameDb;
                        } else {
                            vdName = vdNameDb;
                        }
                    }
                    String vmName = null;
                    // 插入数据盘磁盘
                    ResVd resVd = new ResVd();
                    resVd.setAllocateDiskSize(resVdInst.getDiskSize());

                    // 判断是否指定磁盘存储
                    if (resVdInst.getAllocateResStorageIds() != null &&
                            resVdInst.getAllocateResStorageIds().size() > 0) {
                        // resVd.setAllocateResStorageSid(resVdInst.getAllocateResStorageId());
                    } else {
                        vmName = resVm.getVmName();
                        // 若为空，则以均分方式挑选主机上的存储
                        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
                        // 查询主机下的存储
                        List<ResStorage> resHostStoList = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
                        if (resStoList != null && resStoList.size() > 0) {
                            if (resHostStoList != null && resHostStoList.size() > 0) {
                                for (ResStorage resStoDb : resHostStoList) {

                                    boolean flag = false;
                                    for (ResStorage resSto : resHostStoList) {
                                        if (resSto.getResStorageSid().equals(resStoDb.getResStorageSid())) {
                                            // logger.info(resSto.getStorageName());
                                            // resSto.setStoAllotSize(resStoDb.getStoAllotSize());
                                            flag = true;
                                        }
                                    }
                                    if (!flag) {
                                        resStoList.add(resStoDb);
                                    }
                                }
                            }

                        } else {
                            resStoList.addAll(resHostStoList);
                        }
                        // 资源分配策略
                        String stoAllocationPolicy = null;

                        // 资源分配方式
                        String stoAllocationMode = null;

                        // 资源分配比率
                        String stoAllocationRate = null;

                        // 资源可分配阀值
                        String stoAllocationThreshold = null;
                        for (ResStorage resStorage : resStoList) {

                            // 查询主机所在计算资源池
                            Criteria example1 = new Criteria();
                            example1.put("resTopologySid", resStorage.getResPoolSid());

                            // 查询资源池配置
                            List<ResTopologyConfig> resConfigList = this.resTopologyConfigMapper.selectByParams(example1);

                            for (ResTopologyConfig resTopConfig : resConfigList) {
                                if (WebConstants.ResPoolConfigKey.ALLOCATIONPOLICY.equals(resTopConfig.getConfigKey())) {
                                    stoAllocationPolicy = resTopConfig.getConfigValue();
                                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONMODE.equals(resTopConfig.getConfigKey())) {
                                    stoAllocationMode = resTopConfig.getConfigValue();
                                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONRATE.equals(resTopConfig.getConfigKey())) {
                                    stoAllocationRate = resTopConfig.getConfigValue();
                                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONTHRESHOLD.equals(resTopConfig.getConfigKey())) {
                                    stoAllocationThreshold = resTopConfig.getConfigValue();
                                }
                            }
                        }

                        if (resStoList != null && resStoList.size() > 0) {

                            boolean flag = false;

                            // 已均分方式排序存储，首先循环使用率最小的存储
                            List<ResStorage> resStoList1 = this.getAlallocationPolicyStoList(resStoList,
                                                                                             stoAllocationPolicy
                            );
                            for (ResStorage resStorage : resStoList1) {
                                // 总的可分配空间
                                BigDecimal allStorageSize = new BigDecimal(resStorage.getTotalCapacity()).multiply(new BigDecimal(
                                        stoAllocationRate)).multiply(new BigDecimal(stoAllocationThreshold));

                                // 获取该存储下已开通虚拟机占用存储大小
                                Long resInstanceAllocateStoSize = getResVmAllocateStoSize(resStorage);
                                // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                                // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                                BigDecimal restSto = allStorageSize.subtract(new BigDecimal(resStorage.getStoAllotSize()))
                                                                   .subtract(new BigDecimal(resInstanceAllocateStoSize));

                                // 若使用率最小的存储不满足，则不需要考虑该主机下其他存储
                                if (restSto.compareTo(new BigDecimal(resVdInst.getDiskSize())) >= 0) {
                                    logger.info(
                                            resHost.getHostName() + "下的存储：" + resStorage.getStorageName() + "总可用存储：" +
                                                    allStorageSize + " 已分配存储：" +
                                                    (resInstanceAllocateStoSize + resStorage.getStoAllotSize()));

                                    resVd.setStatus(WebConstants.ResVdStatus.CREATING);
                                    resVd.setVdName(vdName);
                                    resVd.setStoragePurpose(WebConstants.StoragePurpose.DATA_DISK);
                                    resVd.setResVmSid(resVm.getResVmSid());
                                    resVd.setAllocateResStorageSid(resStorage.getResStorageSid());
                                    resVd.setOperate(WebConstants.VdOperate.ADD);
                                    resVd.setFileSystemType(resVdInst.getFileSystem());
                                    resVd.setMountPoint(resVdInst.getMountPoint());
                                    resVd.setLogicVolume(resVdInst.getVolumeName());
                                    resVdList.add(resVd);
                                    setResourceStorages(resStorage, resStoList, resVdInst.getDiskSize());
                                    flag = true;
                                    allFlag = true;
                                    logger.info("添加磁盘在存储：" + resStorage.getStorageName() + "上预占成功");
                                    break;
                                } else {
                                    allFlag = false;
                                    flag = false;
                                }
                            }
                            if (!flag) {
                                throw new RpcException(RpcException.BIZ_EXCEPTION,
                                                       resHost.getHostName() + "主机下没有足够的存储空间。"
                                );
                            }
                        } else {
                            throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源没有关联存储。");
                        }
                    }
                } else if (WebConstants.VdOperate.DELLETE.equals(resVdInst.getOperate())) {
                    ResVd resVd = this.resVdMapper.selectByPrimaryKey(resVdInst.getResVdInstId());
                    resVd.setOperate(WebConstants.VdOperate.DELLETE);
                    resVd.setStatus(WebConstants.ResVdStatus.DELETING);
                    resVdList.add(resVd);
                    allFlag = true;
                } else if (WebConstants.VdOperate.EXPAND.equals(resVdInst.getOperate())) {
                    ResVd resVd = this.resVdMapper.selectByPrimaryKey(resVdInst.getResVdInstId());
                    ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());

                    // 资源分配策略
                    String stoAllocationPolicy = null;

                    // 资源分配方式
                    String stoAllocationMode = null;

                    // 资源分配比率
                    String stoAllocationRate = null;

                    // 资源可分配阀值
                    String stoAllocationThreshold = null;

                    // 查询主机所在计算资源池
                    Criteria example1 = new Criteria();
                    example1.put("resTopologySid", resStorage.getResPoolSid());

                    // 查询资源池配置
                    List<ResTopologyConfig> resConfigList = this.resTopologyConfigMapper.selectByParams(example1);

                    for (ResTopologyConfig resTopConfig : resConfigList) {
                        if (WebConstants.ResPoolConfigKey.ALLOCATIONPOLICY.equals(resTopConfig.getConfigKey())) {
                            stoAllocationPolicy = resTopConfig.getConfigValue();
                        } else if (WebConstants.ResPoolConfigKey.ALLOCATIONMODE.equals(resTopConfig.getConfigKey())) {
                            stoAllocationMode = resTopConfig.getConfigValue();
                        } else if (WebConstants.ResPoolConfigKey.ALLOCATIONRATE.equals(resTopConfig.getConfigKey())) {
                            stoAllocationRate = resTopConfig.getConfigValue();
                        } else if (WebConstants.ResPoolConfigKey.ALLOCATIONTHRESHOLD.equals(resTopConfig.getConfigKey())) {
                            stoAllocationThreshold = resTopConfig.getConfigValue();
                        }
                    }
                    // 总的可分配空间
                    BigDecimal allStorageSize = new BigDecimal(resStorage.getTotalCapacity()).multiply(new BigDecimal(
                            stoAllocationRate)).multiply(new BigDecimal(stoAllocationThreshold));

                    // 获取该存储下已开通虚拟机占用存储大小
                    Long resInstanceAllocateStoSize = getResVmAllocateStoSize(resStorage);
                    // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                    // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                    BigDecimal restSto = allStorageSize.subtract(new BigDecimal(resStorage.getStoAllotSize()))
                                                       .subtract(new BigDecimal(resInstanceAllocateStoSize));

                    if (restSto.compareTo(new BigDecimal(resVdInst.getDiskSize())) >= 0) {
                        resVd.setStatus(WebConstants.ResVdStatus.SETTING);
                        resVd.setBoforeExpandSize(resVd.getAllocateDiskSize());
                        resVd.setAllocateDiskSize(resVdInst.getDiskSize());
                        resVd.setOperate(WebConstants.VdOperate.EXPAND);
                        resVdList.add(resVd);
                        allFlag = true;
                    } else {
                        allFlag = false;
                        throw new RpcException(RpcException.BIZ_EXCEPTION, resStorage.getStorageName() + "存储空间不足。");
                    }
                }
            }

            // 全部检查成功时，组装参数
            if (allFlag) {
                if (resVdList != null && resVdList.size() > 0) {
                    for (ResVd resVd : resVdList) {
                        if (WebConstants.VdOperate.ADD.equals(resVd.getOperate())) {
                            WebUtil.prepareInsertParams(resVd);
                            this.resVdMapper.insertSelective(resVd);

                            ResVm resVmVd = this.resVmMapper.selectByPrimaryKey(resVd.getResVmSid());
                            ResStorage resSto = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
                            VmDisk diskCreate = new VmDisk();
                            diskCreate.setId(resVd.getResVdSid());
                            diskCreate.setVmName(resVmVd.getVmName());
                            diskCreate.setName(resVd.getVdName());
                            diskCreate.setLocation(resSto.getStorageName());
                            diskCreate.setSize(resVd.getAllocateDiskSize().toString());
                            diskCreate.setOperate(WebConstants.VdOperate.ADD);
                            // 格式化参数
                            diskCreate.setLvmParam(resVd.getLogicVolume());
                            diskCreate.setMountLocal(resVd.getMountPoint());
                            diskCreate.setFileSystem(resVd.getFileSystemType());
                            disks.add(diskCreate);
                        } else if (WebConstants.VdOperate.DELLETE.equals(resVd.getOperate())) {
                            WebUtil.prepareUpdateParams(resVd);
                            this.resVdMapper.updateByPrimaryKeySelective(resVd);
                            VmDisk diskDelete = new VmDisk();
                            // 组装参数，调用MQ
                            ResStorage resSto = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
                            diskDelete.setId(resVd.getResVdSid());
                            diskDelete.setVmName(resVm.getVmName());
                            diskDelete.setName(resVd.getVdName());
                            diskDelete.setLocation(resSto.getStorageName());
                            diskDelete.setPath(resVd.getPath());
                            diskDelete.setOperate(WebConstants.VdOperate.DELLETE);
                            disks.add(diskDelete);
                        } else if (WebConstants.VdOperate.EXPAND.equals(resVd.getOperate())) {
                            ResStorage resSto = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
                            WebUtil.prepareUpdateParams(resVd);
                            this.resVdMapper.updateByPrimaryKeySelective(resVd);
                            VmDisk diskExpand = new VmDisk();
                            diskExpand.setId(resVd.getResVdSid());
                            diskExpand.setVmName(resVm.getVmName());
                            // 组装参数，调用MQ
                            diskExpand.setName(resVd.getVdName());
                            diskExpand.setSize(StringUtil.nullToEmpty(resVd.getAllocateDiskSize()));
                            diskExpand.setOldSize(StringUtil.nullToEmpty(resVd.getBoforeExpandSize()));
                            diskExpand.setLocation(resSto.getStorageName());
                            diskExpand.setOperate(WebConstants.VdOperate.EXPAND);
                            disks.add(diskExpand);
                        }
                    }
                }
                resVm.setResVdList(resVdList);
            }
        }
        return disks;
    }


    /**
     * 重新配置POWER虚拟机时，组装磁盘参数
     *
     * @param resVm
     * @param resVdInstList
     * @return
     */
    private List<VmDisk> assemblePowerDisksParams(ResVm resVm, List<ResVdInst> resVdInstList) {
        // 组装磁盘参数
        List<VmDisk> disks = new ArrayList<VmDisk>();
        List<ResStorage> resStoList = new ArrayList<ResStorage>();
        if (resVdInstList != null && resVdInstList.size() > 0) {
            List<ResVd> resVdList = new ArrayList<ResVd>();

            // 查询虚拟机下磁盘，给磁盘递增命名
            Criteria criteria = new Criteria();
            criteria.put("resVmSid", resVm.getResVmSid());
            List<ResVd> resVdListDb = this.resVdMapper.selectByParams(criteria);
            int i = 0;
            for (ResVd resVd : resVdListDb) {
                String vdName = resVd.getVdName();
                int index = vdName.lastIndexOf("_");
                int flag = 0;
                if (index > 0) {
                    String flagIndex = vdName.substring(index + 1);
                    try {
                        flag = Integer.parseInt(flagIndex);
                    } catch (NumberFormatException e) {
                        flag = 0;
                    }
                } else {
                    flag = 0;
                }
                if (flag > i) {
                    i = flag;
                }

            }

            boolean allFlag = false;
            for (ResVdInst resVdInst : resVdInstList) {

                if (WebConstants.VdOperate.ADD.equals(resVdInst.getOperate())) {
                    i++;
                    String vdName = null;
                    // 判断磁盘名称是否指定
                    if (resVdInst.getResVdInstName() != null) {
                        vdName = resVdInst.getResVdInstName();
                    } else {
                        String vdNameDb = resVm.getVmName() + "_" + i;
                        // String vdNameDb =
                        // this.sidService.getMaxSid(WebConstants.SidCategory.VD_NAME);
                        // 判断前缀是否指定
                        if (resVdInst.getResVdInstNamePrefix() != null) {
                            vdName = resVdInst.getResVdInstNamePrefix() + "-" + vdNameDb;
                        } else {
                            vdName = vdNameDb;
                        }
                    }
                    boolean flag = false;
                    // 插入数据盘磁盘
                    ResVd resVd = new ResVd();
                    resVd.setAllocateDiskSize(resVdInst.getDiskSize());

                    //添加数据盘操作步骤：添加vd-->添加storage-->关联vd、storage、host、vm
                    ResHost rightResHost = resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
                    //得到主机所在的资源环境
                    ResTopology resVeInfo = resTopologyMapper.selectByPrimaryKey(rightResHost.getParentTopologySid());
                    String resVeSid = "";
                    if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resVeInfo.getResTopologyType())) {
                        resVeSid = resVeInfo.getResTopologySid();
                    } else {
                        resVeSid = resVeInfo.getParentTopologySid();
                    }

                    //得到当前主机所在的资源分区
                    ResTopology poolInfo = resTopologyMapper.selectByPrimaryKey(rightResHost.getResPoolSid());
                    Criteria stPoolParam = new Criteria();
                    stPoolParam.put("parentTopologySid", poolInfo.getParentTopologySid());
                    stPoolParam.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.PS);
                    List<ResTopology> stPoolInfo = resTopologyMapper.selectByParams(stPoolParam);

                    ResStorage storage = new ResStorage();
                    storage.setParentTopologySid(resVeSid);
                    storage.setResPoolSid(CollectionUtils.isEmpty(stPoolInfo) ? "" : stPoolInfo.get(0)
                                                                                               .getResTopologySid());
                    storage.setStorageName(vdName);
                    storage.setTotalCapacity(resVdInst.getDiskSize());
                    storage.setStorageCategory(WebConstants.StorageCategory.SHARE);
                    storage.setStoragePurpose(WebConstants.StoragePurpose.DATA_DISK);
                    storage.setStatus(WebConstants.ResStorageStatus.NORMAL);

                    resStorageMapper.insertSelective(storage);

                    //关联storage和host
                    ResHostStorage hostStorage = new ResHostStorage();
                    hostStorage.setResHostSid(rightResHost.getResHostSid());
                    hostStorage.setResStorageSid(storage.getResStorageSid());
                    resHostStorageMapper.insertSelective(hostStorage);

                    resVd.setStatus(WebConstants.ResVdStatus.CREATING);
                    resVd.setVdName(vdName);
                    resVd.setStoragePurpose(WebConstants.StoragePurpose.DATA_DISK);
                    resVd.setResVmSid(resVm.getResVmSid());
                    //关联storage和vd
                    resVd.setAllocateResStorageSid(storage.getResStorageSid());

                    resVd.setOperate(WebConstants.VdOperate.ADD);
                    resVd.setFileSystemType(resVdInst.getFileSystem());
                    resVd.setMountPoint(resVdInst.getMountPoint());
                    resVd.setLogicVolume(resVdInst.getVolumeName());
                    resVdList.add(resVd);
                    flag = true;
                    allFlag = true;
                } else if (WebConstants.VdOperate.DELLETE.equals(resVdInst.getOperate())) {
                    ResVd resVd = this.resVdMapper.selectByPrimaryKey(resVdInst.getResVdInstId());
                    resVd.setOperate(WebConstants.VdOperate.DELLETE);
                    resVd.setStatus(WebConstants.ResVdStatus.DELETING);
                    resVdList.add(resVd);
                    allFlag = true;
                }
            }

            // 全部检查成功时，组装参数
            if (allFlag) {
                if (resVdList != null && resVdList.size() > 0) {
                    for (ResVd resVd : resVdList) {
                        if (WebConstants.VdOperate.ADD.equals(resVd.getOperate())) {
                            WebUtil.prepareInsertParams(resVd);
                            this.resVdMapper.insertSelective(resVd);

                            ResVm resVmVd = this.resVmMapper.selectByPrimaryKey(resVd.getResVmSid());
                            ResStorage resSto = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
                            VmDisk diskCreate = new VmDisk();
                            diskCreate.setId(resVd.getResVdSid());
                            diskCreate.setVmName(resVmVd.getVmName());
                            diskCreate.setName(resVd.getVdName());
                            diskCreate.setLocation(resSto.getStorageName());
                            diskCreate.setSize(resVd.getAllocateDiskSize().toString());
                            diskCreate.setOperate(WebConstants.VdOperate.ADD);
                            // 格式化参数
                            diskCreate.setLvmParam(resVd.getLogicVolume());
                            diskCreate.setMountLocal(resVd.getMountPoint());
                            diskCreate.setFileSystem(resVd.getFileSystemType());
                            disks.add(diskCreate);
                        } else if (WebConstants.VdOperate.DELLETE.equals(resVd.getOperate())) {
                            WebUtil.prepareUpdateParams(resVd);
                            this.resVdMapper.updateByPrimaryKeySelective(resVd);
                            //得到vd所属的storage是由那些volume组成
                            Criteria example = new Criteria();
                            example.put("resStorageSid", resVd.getAllocateResStorageSid());
                            List<ResVolume> volumes = resVolumeMapper.selectByParams(example);
                            List<Volume> vdVolumes = new ArrayList<Volume>();
                            if (!CollectionUtils.isEmpty(volumes)) {
                                try {
                                    for (ResVolume volume : volumes) {
                                        Volume vdVolume = new Volume();
                                        vdVolume.setId(new URI(volume.getResVolumeSid()));
                                        vdVolume.setSize(Integer.parseInt(volume.getSize() + ""));
                                        vdVolume.setName(volume.getVolumeName());
                                        vdVolume.setWwn(volume.getVolumeWwn());

                                        vdVolumes.add(vdVolume);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            VmDisk diskDelete = new VmDisk();
                            // 组装参数，调用MQ
                            ResStorage resSto = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
                            diskDelete.setId(resVd.getResVdSid());
                            diskDelete.setVmName(resVm.getVmName());
                            diskDelete.setName(resVd.getVdName());
                            diskDelete.setLocation(resSto.getStorageName());
                            diskDelete.setPath(resVd.getPath());
                            diskDelete.setOperate(WebConstants.VdOperate.DELLETE);
                            diskDelete.setVolumes(vdVolumes);
                            disks.add(diskDelete);
                        }
                    }
                }
                resVm.setResVdList(resVdList);
            }
        }
        return disks;
    }

    /**
     * 资源勘察
     */
    @Override
    public ResInstResult checkResIsEnough(ResVmCheck resVmCheck) {
        ResInstResult result = new ResInstResult();
        try {
            List<ResVmInst> resVmInstList = resVmCheck.getResVmList();

            // 判断传入资源集合，若为集群，则收集所有主机
            List<ResHost> resHostList = new ArrayList<ResHost>();
            if (resVmCheck.getAllocateResVcIds() != null && resVmCheck.getAllocateResVcIds().size() > 0) {
                List<String> resVcSids = resVmCheck.getAllocateResVcIds();
                for (String resVcSid : resVcSids) {
                    Criteria example = new Criteria();
                    example.put("parentTopologySid", resVcSid);
                    example.put("status", WebConstants.ResHostStatus.NORMAL);
                    List<ResHost> resHosts = this.resHostMapper.selectByParams(example);
                    resHostList.addAll(resHosts);
                }

            } else if (resVmCheck.getAllocateResHostIds() != null && resVmCheck.getAllocateResHostIds().size() > 0) {
                List<String> resHostSids = resVmCheck.getAllocateResHostIds();
                for (String resHostSid : resHostSids) {
                    ResHost resHost = this.resHostMapper.selectByPrimaryKey(resHostSid);
                    resHostList.add(resHost);
                }
            }
            // 表示主机、主机下的存储是否满足
            boolean hostIsEnough = false;
            if (resHostList != null && resHostList.size() > 0) {

                // 设置主机下的存储
                for (ResHost resHost : resHostList) {
                    List<ResStorage> resStorages = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
                    resHost.setResStorages(resStorages);
                }

                // 遍历虚拟机实例，判断主机上的CPU、内存、存储是否足够
                for (ResVmInst resVmInst : resVmInstList) {
                    logger.info("虚拟机" + resVmInst.getResVmInstName());
                    ResVm resVM = new ResVm();
                    resVM.setCpuCores(resVmInst.getCpu());
                    resVM.setMemorySize(resVmInst.getMemory());
                    // 设置磁盘
                    List<ResVd> resVdList = new ArrayList<ResVd>();
                    List<ResVdInst> resVdInstList = resVmInst.getDataDisks();
                    for (ResVdInst resVdInst : resVdInstList) {
                        ResVd resVd = new ResVd();
                        resVd.setAllocateDiskSize(resVdInst.getDiskSize());
                        resVdList.add(resVd);
                    }
                    resVM.setResVdList(resVdList);
                    ResVm resVm = this.getVmAvailability(resVM, resHostList);
                    if (resVm != null) {
                        hostIsEnough = true;

                        // 设置资源
                        this.setResourceHosts(resHostList, resVm);
                    } else {
                        hostIsEnough = false;
                        break;
                    }
                }
            } else {
                throw new Exception("主机集合为空。");
            }
            // 主机、存储符合要求，判断网络
            if (hostIsEnough) {
                // 需要内网IP个数
                int pNIIpNum = 0;

                // 需要外网IP个数
                int pNEIpNum = 0;

                // 数据库剩余内网IP数
                int restPNIIpNum = 0;

                // 数据库剩余外网IP数
                int restPNEIpNum = 0;
                for (ResVmInst resVmInst : resVmInstList) {
                    List<ResNetworkInst> nets = resVmInst.getNets();
                    for (ResNetworkInst resNet : nets) {
                        if (WebConstants.ResNetworkType.PRIVATE.equals(resNet.getResNetworkType())) {
                            pNIIpNum++;
                        } else if (WebConstants.ResNetworkType.PUBLIC.equals(resNet.getResNetworkType())) {
                            pNEIpNum++;
                        }
                    }
                }

                // 查询传入内部网络剩余IP数
                List<ResNetworkInst> pNiNetSids = resVmCheck.getPNINets();
                for (ResNetworkInst resNet : pNiNetSids) {
                    Criteria example = new Criteria();
                    example.put("resNetworkSid", resNet.getResNetworkId());
                    example.put("usageStatus", WebConstants.ResIpUsageStatus.AVAILABLE);
                    List<ResIp> resIps = this.resIpMapper.selectByParams(example);
                    restPNIIpNum += resIps.size();
                }

                // 查询传入内部网络剩余IP数
                List<ResNetworkInst> pNENetSids = resVmCheck.getPNENets();
                for (ResNetworkInst resNet : pNENetSids) {
                    Criteria example = new Criteria();
                    example.put("resNetworkSid", resNet.getResNetworkId());
                    example.put("usageStatus", WebConstants.ResIpUsageStatus.AVAILABLE);
                    List<ResIp> resIps = this.resIpMapper.selectByParams(example);
                    restPNEIpNum += resIps.size();
                }
                logger.info("所需内网IP个数：" + pNIIpNum);
                logger.info("所需外网IP个数：" + pNEIpNum);
                logger.info("剩余内网IP个数：" + restPNIIpNum);
                logger.info("剩余外网IP个数：" + restPNEIpNum);
                // 判断IP是否满足
                if (pNIIpNum <= restPNIIpNum && pNEIpNum <= restPNEIpNum) {
                    throw new RpcException(RpcException.BIZ_EXCEPTION, "网络资源不足。");
                }
            }
            result = new ResInstResult(ResInstResult.SUCCESS, "资源检查成功。");

        } catch (RpcException appeE) {
            result = new ResInstResult(ResInstResult.FAILURE, appeE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResInstResult(ResInstResult.FAILURE, "系统异常。");
        }
        return result;
    }

    /**
     * 获取有效的虚拟机
     *
     * @param resVm
     * @param resHostList
     * @return
     */
    private ResVm getVmAvailability(ResVm resVm, List<ResHost> resHostList) {

        ResVm resVmReturn = null;
        // 符合要求的主机集合
        List<ResHost> rightHosts = new ArrayList<ResHost>();

        // TODO 因为传过来的集群或者主机只在一个资源池下，所以资源分配策略、资源分配方式都一样
        // 资源分配策略
        String allocationPolicy = null;

        // 资源分配方式
        String allocationMode = null;
        for (ResHost resHost : resHostList) {

            // 查询主机所在计算资源池
            Criteria example = new Criteria();
            example.put("resTopologySid", resHost.getResPoolSid());
            List<ResTopology> resPoolList = this.resTopologyMapper.selectPoolTopologyTree(example);
            // 查询资源池配置
            List<ResTopologyConfig> resTConfigList = this.resTopologyConfigMapper.selectByParams(example);
            if (resTConfigList == null || resTConfigList.isEmpty()) {
                throw new RpcException(RpcException.BIZ_EXCEPTION, "没有资源池配置信息。");
            }

            // 资源分配比率
            String allocationRate = null;

            // 资源可分配阀值
            String allocationThreshold = null;
            for (ResTopologyConfig resTopConfig : resTConfigList) {
                if (WebConstants.ResPoolConfigKey.ALLOCATIONPOLICY.equals(resTopConfig.getConfigKey())) {
                    allocationPolicy = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONMODE.equals(resTopConfig.getConfigKey())) {
                    allocationMode = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONRATE.equals(resTopConfig.getConfigKey())) {
                    allocationRate = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONTHRESHOLD.equals(resTopConfig.getConfigKey())) {
                    allocationThreshold = resTopConfig.getConfigValue();
                }
            }
            if (WebConstants.AllocationMode.MEMORY.equals(allocationMode)) {

                // 总的可分配内存
                BigDecimal allMemory = new BigDecimal(resHost.getMemorySize()).multiply(new BigDecimal(allocationRate))
                                                                              .multiply(new BigDecimal(
                                                                                      allocationThreshold));

                // 从已分配的资源实例中取出已分配内存
                Long resInstanceAllocateMemory = getResVmAllocateMemory(resHost);
                logger.info(resHost.getHostName() + "总可用内存：" + allMemory);
                logger.info(resHost.getHostName() + "已分配内存：" +
                                    (resInstanceAllocateMemory + resHost.getVmMemoryAllotSize()));
                // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                BigDecimal restMemory = allMemory.subtract(new BigDecimal(resHost.getVmMemoryAllotSize()))
                                                 .subtract(new BigDecimal(resInstanceAllocateMemory));

                if (restMemory.compareTo(new BigDecimal(resVm.getMemorySize())) >= 0) {
                    rightHosts.add(resHost);
                } else {
                    logger.info(resHost.getHostName() + "内存不足");
                }
            } else if (WebConstants.AllocationMode.CPU.equals(allocationMode)) {

                ResVe resVe = this.getVeByRes(resHost);
                BigDecimal allCpu = null;
                float resVmAllocateCpu = 0f;
                if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                    // 查询主机下CPU池
                    if (WebConstants.PowerPartitionType.LPAR.equals(resVm.getParType().toString())) {
                        // 开通VM为LPAR时，主机的CPU数量为可分配数
                        allCpu = new BigDecimal(resHost.getCpuNumber());
                        // 从已分配的资源实例中取出已分配CPU
                        Criteria param = new Criteria();
                        param.put("allocateResHostSid", resHost.getResHostSid());
                        param.put("parType", WebConstants.PowerPartitionType.LPAR);
                        List<ResVm> resVms = this.resVmMapper.selectByParams(param);
                        for (ResVm vm : resVms) {
                            resVmAllocateCpu += vm.getCpuCores();
                        }
                    } else {
                        // 在CPU POOL中查找可用总数
                        List<ResCpuPool> resCpuPools = this.resCpuPoolMapper.selectByPoolWithSum(resHost.getResHostSid());
                        ResCpuPool resCpuPool = null;
                        if (resCpuPools.size() > 0) {
                            if (StringUtil.isNullOrEmpty(resHost.getCpuPoolSid())) {
                                int maxFreeIndex = 0;
                                float maxFree = 0f;
                                for (int i = 0; i < resCpuPools.size(); i++) {
                                    float tmp = resCpuPools.get(i).getMaxValue() -
                                            resCpuPools.get(i).getTotalUsedCpuValue();
                                    if (tmp > maxFree) {
                                        maxFree = tmp;
                                        maxFreeIndex = i;
                                    }
                                }
                                allCpu = new BigDecimal(resCpuPools.get(maxFreeIndex).getMaxValue());
                                resVmAllocateCpu = resCpuPools.get(maxFreeIndex).getTotalUsedCpuValue();
                                resCpuPool = resCpuPools.get(maxFreeIndex);
                            } else {
                                for (ResCpuPool cpuPool : resCpuPools) {
                                    if (cpuPool.getResCpuPoolSid().equals(resHost.getCpuPoolSid())) {
                                        allCpu = new BigDecimal(cpuPool.getMaxValue());
                                        resVmAllocateCpu = cpuPool.getTotalUsedCpuValue();
                                        resCpuPool = cpuPool;
                                        break;
                                    }
                                }
                            }
                        }

                        // 从已分配的资源实例中取出已分配CPU
                        List<ResCpuPoolMparRel> mparRels = this.resCpuPoolMparRelMapper.selectByParams(new Criteria().put(
                                "resCpuPoolSid",
                                resCpuPool.getResCpuPoolSid()
                        ));
                        for (ResCpuPoolMparRel mparRel : mparRels) {
                            resVmAllocateCpu += mparRel.getCpuValue();
                        }
                    }
                } else {
                    // 总的可分配CPU
                    allCpu = new BigDecimal(resHost.getCpuCores()).multiply(new BigDecimal(allocationRate))
                                                                  .multiply(new BigDecimal(allocationThreshold));
                    // 从已分配的资源实例中取出已分配CPU
                    resVmAllocateCpu = getResVmAllocateCpu(resHost);
                }

                logger.info(resHost.getHostName() + "总可用CPU：" + allCpu);
                logger.info(resHost.getHostName() + "已分配CPU：" + (resVmAllocateCpu + resHost.getVmCpuCore()));
                // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                BigDecimal restCpu = allCpu.subtract(new BigDecimal(resHost.getVmCpuCore()))
                                           .subtract(new BigDecimal(resVmAllocateCpu));

                if (restCpu.compareTo(new BigDecimal(resVm.getCpuCores())) >= 0) {
                    rightHosts.add(resHost);
                } else {
                    logger.info(resHost.getHostName() + "CPU不足");
                }
            }
        }

        if (rightHosts != null && rightHosts.size() > 0) {

            // 根据资源池分配方式、分配策略挑选主机
            List<ResHost> rightHostsByStriping = this.getAlallocationPolicyHostList(rightHosts,
                                                                                    allocationMode,
                                                                                    allocationPolicy
            );
            if (rightHostsByStriping != null && rightHostsByStriping.size() > 0) {

                boolean hostFlag = false;

                // 遍历符合CPU或MEMORY的主机，检查主机下的存储是否能够分配
                for (ResHost rightResHost : rightHostsByStriping) {
                    // 查询主机下存储
                    List<ResStorage> resStorages = rightResHost.getResStorages();

                    // 资源分配策略
                    String stoAllocationPolicy = null;

                    // 资源分配方式
                    String stoAllocationMode = null;

                    // 资源分配比率
                    String stoAllocationRate = null;

                    // 资源可分配阀值
                    String stoAllocationThreshold = null;
                    for (ResStorage resStorage : resStorages) {

                        // 查询主机所在计算资源池
                        Criteria example = new Criteria();
                        example.put("resTopologySid", resStorage.getResPoolSid());

                        // 查询资源池配置
                        List<ResTopologyConfig> resTConfigList = this.resTopologyConfigMapper.selectByParams(example);

                        for (ResTopologyConfig resTopConfig : resTConfigList) {
                            if (WebConstants.ResPoolConfigKey.ALLOCATIONPOLICY.equals(resTopConfig.getConfigKey())) {
                                stoAllocationPolicy = resTopConfig.getConfigValue();
                            } else if (WebConstants.ResPoolConfigKey.ALLOCATIONMODE.equals(resTopConfig.getConfigKey())) {
                                stoAllocationMode = resTopConfig.getConfigValue();
                            } else if (WebConstants.ResPoolConfigKey.ALLOCATIONRATE.equals(resTopConfig.getConfigKey())) {
                                stoAllocationRate = resTopConfig.getConfigValue();
                            } else if (WebConstants.ResPoolConfigKey.ALLOCATIONTHRESHOLD.equals(resTopConfig.getConfigKey())) {
                                stoAllocationThreshold = resTopConfig.getConfigValue();
                            }
                        }
                    }

                    ResVe resVe = this.getVeByRes(rightResHost);
                    if (!(WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType()) &&
                            (rightResHost.getIsViosFlag() != null &&
                                    WebConstants.IsViosFlag.NO.equals(rightResHost.getIsViosFlag())))) {
                        if (resStorages != null && resStorages.size() > 0) {

                            if (resVm.getResVdList() != null && resVm.getResVdList().size() > 0) {

                                boolean allFlag = false;
                                for (ResVd resVd : resVm.getResVdList()) {

                                    boolean flag = false;

                                    // 已均分方式排序存储，首先循环使用率最小的存储
                                    List<ResStorage> resStoList = this.getAlallocationPolicyStoList(resStorages,
                                                                                                    stoAllocationPolicy
                                    );
                                    for (ResStorage resStorage : resStoList) {
                                        // 判断存储用途，与VD的用途需要一致
                                        if (!WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                                            if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(resVd.getStoragePurpose())) {
                                                if (WebConstants.StoragePurpose.DATA_DISK.equals(resStorage.getStoragePurpose())) {
                                                    flag = false;
                                                    allFlag = false;
                                                    continue;
                                                }
                                            } else if (WebConstants.StoragePurpose.DATA_DISK.equals(resVd.getStoragePurpose())) {
                                                if (WebConstants.StoragePurpose.SYSTEM_DISK.equals(resStorage.getStoragePurpose())) {
                                                    flag = false;
                                                    allFlag = false;
                                                    continue;
                                                }
                                            }
                                        }
                                        // 总的可分配空间
                                        BigDecimal allStorageSize = new BigDecimal(resStorage.getTotalCapacity()).multiply(
                                                new BigDecimal(stoAllocationRate))
                                                                                                                 .multiply(
                                                                                                                         new BigDecimal(
                                                                                                                                 stoAllocationThreshold));

                                        // 获取该存储下已开通虚拟机占用存储大小
                                        Long resInstanceAllocateStoSize = getResVmAllocateStoSize(resStorage);
                                        // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                                        // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                                        BigDecimal restSto = allStorageSize.subtract(new BigDecimal(resStorage.getStoAllotSize()))
                                                                           .subtract(new BigDecimal(
                                                                                   resInstanceAllocateStoSize));

                                        // 若使用率最小的存储不满足，则不需要考虑该主机下其他存储
                                        if (restSto.compareTo(new BigDecimal(resVd.getAllocateDiskSize())) >= 0) {
                                            // 设置已分配资源
                                            logger.info(
                                                    rightResHost.getHostName() + "下的存储：" + resStorage.getStorageName() +
                                                            "总可用存储：" + allStorageSize + " 已分配存储：" +
                                                            (resInstanceAllocateStoSize +
                                                                    resStorage.getStoAllotSize()));
                                            setResourceStorages(resStorage, resStorages, resVd.getAllocateDiskSize());
                                            resVd.setAllocateResStorageSid(resStorage.getResStorageSid());
                                            flag = true;
                                            allFlag = true;
                                            logger.info(
                                                    resVd.getVdName() + "在存储：" + resStorage.getStorageName() + "上预占成功");
                                            break;
                                        } else {
                                            flag = false;
                                            allFlag = false;
                                        }
                                    }
                                    if (!flag) {
                                        logger.error(rightResHost.getHostName() + "主机下没有足够的存储");
                                        break;
                                    }
                                }

                                //
                                if (allFlag) {
                                    hostFlag = true;
                                    // 把分配的主机和存储设置到虚拟机对象里
                                    resVm.setAllocateResHostSid(rightResHost.getResHostSid());
                                    resVmReturn = resVm;
                                    break;
                                }
                            }
                        } else {
                            logger.info(rightResHost.getHostName() + "主机下没有挂载的存储。");
                        }
                    } else {
                        hostFlag = true;
                        resVm.setAllocateResHostSid(rightResHost.getResHostSid());

                        resVmReturn = resVm;
                    }
                    if (resVmReturn != null) {
                        break;
                    }

                }

                if (!hostFlag) {
                    throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源存储不足！");
                }
            }
        } else {
            if (WebConstants.AllocationMode.MEMORY.equals(allocationMode)) {
                throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源内存不足。");
            } else if (WebConstants.AllocationMode.CPU.equals(allocationMode)) {
                throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源CPU不足。");
            }
        }
        return resVmReturn;
    }

    /**
     * 保存主机已分配的CPU、内存、以及主机下已分配的存储
     *
     * @param resHosts 主机列表
     * @param resVm    虚机信息
     */
    private void setResourceHosts(List<ResHost> resHosts, ResVm resVm) {
        Long memorySize = 0L;
        int cpuCore = 0;

        List<ResStorage> stoList = new ArrayList<ResStorage>();
        // 判断list中是否有已分配过的主机，如果有，则累加已分配的内存
        for (ResHost resHost1 : resHosts) {
            if (resVm.getAllocateResHostSid().equals(resHost1.getResHostSid())) {
                memorySize = resHost1.getVmMemoryAllotSize() + resVm.getMemorySize();
                cpuCore = resHost1.getVmCpuCore() + resVm.getCpuCores();
                resHost1.setVmMemoryAllotSize(memorySize);
                resHost1.setVmCpuCore(cpuCore);
                stoList = resHost1.getResStorages();
                logger.info(resHost1.getHostName() + "已预占内存：" + resHost1.getVmMemoryAllotSize());
                logger.info(resHost1.getHostName() + "已预占cpu：" + resHost1.getVmCpuCore());
                break;
            }
        }

        // 更新集合所有主机下相同的存储
        for (ResHost resHost1 : resHosts) {
            List<ResStorage> resStoList = resHost1.getResStorages();
            for (ResStorage resSto : resStoList) {
                for (ResStorage resS : stoList) {
                    if (resSto.getResStorageSid().equals(resS.getResStorageSid())) {
                        resSto.setStoAllotSize(resS.getStoAllotSize());
                    }
                }
            }

        }
        //
        // if (allocateFlag == false) {
        // ResourceHost resourceHost = new ResourceHost();
        // resourceHost.setResSid(resourceInstance.getResourceInstanceVm().getAllocateResHostSid());
        // resourceHost.setMemorySize(resourceInstance.getResourceInstanceVm().getMemorySize());
        // resourceHosts.add(resourceHost);
        // }

    }

    @Override
    public void setResourceStorages(ResStorage resStorage, List<ResStorage> resStorageList, Long allotSize) {
        Long stoSize = 0L;

        // 查询主机下存储，设置已分配存储
        for (ResStorage resStorage1 : resStorageList) {
            if (resStorage.getResStorageSid().equals(resStorage1.getResStorageSid())) {
                stoSize += resStorage1.getStoAllotSize() + allotSize.longValue();
                resStorage1.setStoAllotSize(stoSize);
                break;
            }
        }

        //
        // if (allocateFlag == false) {
        // ResourceHost resourceHost = new ResourceHost();
        // resourceHost.setResSid(resourceInstance.getResourceInstanceVm().getAllocateResHostSid());
        // resourceHost.setMemorySize(resourceInstance.getResourceInstanceVm().getMemorySize());
        // resourceHosts.add(resourceHost);
        // }

    }

    /**
     * 查询主机下已分配的内存
     *
     * @param resHost 主机
     * @return 已分配内存
     */
    private long getResVmAllocateMemory(ResHost resHost) {
        Criteria example = new Criteria();
        String statusNotIn = "'" + WebConstants.ResVmStatus.DELETED + "'";
        example.put("allocateResHostSid", resHost.getResHostSid());
        example.put("statusNotIn", statusNotIn);
        List<ResVm> resVmList = this.resVmMapper.selectByParams(example);
        long allVmMemorySize = 0L;
        for (ResVm resVm : resVmList) {
            allVmMemorySize += resVm.getMemorySize();
        }
        // 针对Power主机，加上Vios的内存使用
        List<ResVios> resViosList = this.resViosMapper.selectByParams(new Criteria().put("resHostSid",
                                                                                         resHost.getResHostSid()
        ));
        for (ResVios resVios : resViosList) {
            allVmMemorySize += resVios.getMemorySize() == null ? 0 : resVios.getMemorySize();
        }
        return allVmMemorySize;
    }

    /**
     * 查询主机下已分配的CPU
     *
     * @param resHost 选择主机
     * @return 已分配CPU
     */
    private int getResVmAllocateCpu(ResHost resHost) {
        Criteria example = new Criteria();
        String statusNotIn = "'" + WebConstants.ResVmStatus.DELETED + "'";
        example.put("allocateResHostSid", resHost.getResHostSid());
        example.put("statusNotIn", statusNotIn);
        List<ResVm> resVmList = this.resVmMapper.selectByParams(example);
        int allVmCpu = 0;
        for (ResVm resVm : resVmList) {
            allVmCpu += resVm.getCpuCores();
        }
        return allVmCpu;
    }


    @Override
    public long getResVmAllocateStoSize(ResStorage resStorage) {
        Criteria example = new Criteria();
        String statusNotIn = "'" + WebConstants.ResVdStatus.DELETED + "'";
        example.put("allocateResStorageSid", resStorage.getResStorageSid());
        example.put("statusNotIn", statusNotIn);
        List<ResVd> resVdList = this.resVdMapper.selectByParams(example);
        long allVdStoSize = 0L;
        for (ResVd resVd : resVdList) {
            allVdStoSize += resVd.getAllocateDiskSize();
        }
        return allVdStoSize;
    }

    /**
     * 按照分配策略、分配方式挑选主机
     *
     * @param resHostList      主机列表
     * @param allocationPolicy 分配策略
     * @return 可用主机列表
     */
    private List<ResHost> getAlallocationPolicyHostList(List<ResHost> resHostList, String allocationMode, String allocationPolicy) {

        if (WebConstants.AllocationMode.MEMORY.equals(allocationMode)) {

            for (ResHost resHost : resHostList) {
                long allVmMemorySize = this.getResVmAllocateMemory(resHost) + resHost.getVmMemoryAllotSize();
                BigDecimal allocateMemorySize = new BigDecimal(allVmMemorySize);
                BigDecimal memorySize = new BigDecimal(resHost.getMemorySize());
                BigDecimal usedMemorySizePercentage = allocateMemorySize.divide(memorySize,
                                                                                5,
                                                                                BigDecimal.ROUND_HALF_UP
                );
                resHost.setMemoryUsage(usedMemorySizePercentage.floatValue());
            }

            if (WebConstants.AllocationPolicy.STRIPING.equals(allocationPolicy)) {

                Comparator<ResHost> comparator = new Comparator<ResHost>() {
                    public int compare(ResHost h1, ResHost h2) {
                        return h1.getMemoryUsage().compareTo(h2.getMemoryUsage());

                    }
                };

                Collections.sort(resHostList, comparator);

            } else if (WebConstants.AllocationPolicy.PACKING.equals(allocationPolicy)) {
                Comparator<ResHost> comparator = new Comparator<ResHost>() {
                    public int compare(ResHost h1, ResHost h2) {
                        return h2.getMemoryUsage().compareTo(h1.getMemoryUsage());

                    }
                };
                Collections.sort(resHostList, comparator);
            }
            for (ResHost resHost : resHostList) {
                logger.info(resHost.getHostName() + "内存使用率率:" + resHost.getMemoryUsage().toString());
            }
        } else if (WebConstants.AllocationMode.CPU.equals(allocationMode)) {
            for (ResHost resHost : resHostList) {
                int allVmCpuCore = this.getResVmAllocateCpu(resHost) + resHost.getVmCpuCore();
                BigDecimal allocateCpuCore = new BigDecimal(allVmCpuCore);
                BigDecimal allCpuCore = new BigDecimal(resHost.getCpuCores());
                BigDecimal usedCpuCorePercentage = allocateCpuCore.divide(allCpuCore, 5, BigDecimal.ROUND_HALF_UP);
                resHost.setCpuUsage(usedCpuCorePercentage.floatValue());
            }

            if (WebConstants.AllocationPolicy.STRIPING.equals(allocationPolicy)) {

                Comparator<ResHost> comparator = new Comparator<ResHost>() {
                    public int compare(ResHost h1, ResHost h2) {
                        return h1.getCpuUsage().compareTo(h2.getCpuUsage());

                    }
                };

                Collections.sort(resHostList, comparator);

            } else if (WebConstants.AllocationPolicy.PACKING.equals(allocationPolicy)) {
                Comparator<ResHost> comparator = new Comparator<ResHost>() {
                    public int compare(ResHost h1, ResHost h2) {
                        return h2.getCpuUsage().compareTo(h1.getCpuUsage());

                    }
                };
                Collections.sort(resHostList, comparator);
            }
            for (ResHost resHost : resHostList) {
                logger.info(resHost.getHostName() + "CPU使用率率:" + resHost.getCpuUsage().toString());
            }
        }
        return resHostList;
    }

    /**
     * 按分配策略挑选存储
     *
     * @param resStorageList   存储列表
     * @param allocationPolicy 分配策略
     * @return 可选存储列表
     */
    private List<ResStorage> getAlallocationPolicyStoList(List<ResStorage> resStorageList, String allocationPolicy) {

        for (ResStorage resSto : resStorageList) {

            BigDecimal allocateStoSize = new BigDecimal(
                    this.getResVmAllocateStoSize(resSto) + resSto.getStoAllotSize());
            BigDecimal totalStoSize = new BigDecimal(resSto.getTotalCapacity());
            BigDecimal usedStoSizePercentage = allocateStoSize.divide(totalStoSize, 5, BigDecimal.ROUND_HALF_UP);
            resSto.setStorageUsage(usedStoSizePercentage.toString());
        }

        if (WebConstants.AllocationPolicy.STRIPING.equals(allocationPolicy)) {

            Comparator<ResStorage> comparator = new Comparator<ResStorage>() {
                public int compare(ResStorage h1, ResStorage h2) {
                    return h1.getStorageUsage().compareTo(h2.getStorageUsage());

                }
            };

            Collections.sort(resStorageList, comparator);

        } else if (WebConstants.AllocationPolicy.PACKING.equals(allocationPolicy)) {
            Comparator<ResStorage> comparator = new Comparator<ResStorage>() {
                public int compare(ResStorage h1, ResStorage h2) {
                    return h2.getStorageUsage().compareTo(h1.getStorageUsage());

                }
            };
            Collections.sort(resStorageList, comparator);
        }

        // for (ResStorage resSto : resStorageList) {
        // logger.info(resSto.getStorageName() + "使用率:" +
        // resSto.getStorageUsage().toString());
        // }
        return resStorageList;
    }

    @Override
    public ResVm statisticalTopologyOfVm(String resTopologySid) {

        return this.resVmMapper.statisticalTopologyOfVm(resTopologySid);
    }

    /**
     * 根据网络获取有效IP
     *
     * @param resVmNetId 网络Sid
     * @return IP实体类
     */
    private ResIp getAlallocationResIp(String resVmNetId) {

        ResIp alocateResIP = null;

        // 查询网络下所有IP
        Criteria example = new Criteria();
        example.put("resNetworkSid", resVmNetId);
        example.put("usageStatus", WebConstants.ResIpUsageStatus.AVAILABLE);
        List<ResIp> avaliableIps = this.resIpMapper.selectByParams(example);

        if (avaliableIps != null && avaliableIps.size() > 0) {
            alocateResIP = avaliableIps.get(0);

            // 查询网络所属资源池
            ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(resVmNetId);
            ResTopology resTopology = this.resTopologyMapper.selectByPrimaryKey(resNetwork.getResPoolSid());
            alocateResIP.setResPoolType(resTopology.getResTopologyType());
        }

        return alocateResIP;
    }

    @Override
    public ResVm selectByResHostSid(String allocateResHostSid) {

        return this.resVmMapper.selectByResHostSid(allocateResHostSid);
    }

    @Override
    public void configIp(ResVm resVm) {

        // 删除虚拟机与网络关联
        this.resVmNetworkMapper.deleteByPrimaryKey(resVm.getResVmSid());

        String vmIps = resVm.getVmIp();
        String[] ips = vmIps.split(",");

        for (String ip : ips) {
            // 查询IP所属网络
            Criteria criteria = new Criteria();
            criteria.put("ipAddress", ip);
            List<ResIp> ipList = this.resIpMapper.selectByParams(criteria);

            // 插入虚拟机和网络关联
            ResVmNetwork resVmNetwork = new ResVmNetwork();
            resVmNetwork.setResVmSid(resVm.getResVmSid());
            if (ipList != null && ipList.size() > 0) {
                resVmNetwork.setResNetworkSid(ipList.get(0).getResNetworkSid());
            }
            resVmNetwork.setIpAddress(ip);
            this.resVmNetworkMapper.insertSelective(resVmNetwork);
        }
    }

    @Override
    public List<ResVm> selectVmResSum(Criteria example) {
        return this.resVmMapper.selectVmResSum(example);
    }

    @Override
    public ResVm statisticalComputePoolOfVm(String resPoolSid) {

        return this.resVmMapper.statisticalComputePoolOfVm(resPoolSid);
    }

    /**
     * 重新配置虚拟机时资源检查
     */
    @Override
    public ResInstResult reconfigVmCheck(ResVmInst resVmInst) {

        ResInstResult result = new ResInstResult();
        try {

            // 判断虚拟机是否关闭
            //			this.checkIsNeedPoweroff(resVmInst);

            ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmInst.getResVmInstId());
            ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());

            // 查询主机所在计算资源池
            Criteria example = new Criteria();
            example.put("resTopologySid", resHost.getResPoolSid());

            // 查询资源池配置
            List<ResTopologyConfig> resTConfigList = this.resTopologyConfigMapper.selectByParams(example);

            // 资源分配策略
            String allocationPolicy = null;

            // 资源分配方式
            String allocationMode = null;

            // 资源分配比率
            String allocationRate = null;

            // 资源可分配阀值
            String allocationThreshold = null;
            for (ResTopologyConfig resTopConfig : resTConfigList) {
                if (WebConstants.ResPoolConfigKey.ALLOCATIONPOLICY.equals(resTopConfig.getConfigKey())) {
                    allocationPolicy = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONMODE.equals(resTopConfig.getConfigKey())) {
                    allocationMode = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONRATE.equals(resTopConfig.getConfigKey())) {
                    allocationRate = resTopConfig.getConfigValue();
                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONTHRESHOLD.equals(resTopConfig.getConfigKey())) {
                    allocationThreshold = resTopConfig.getConfigValue();
                }
            }
            if (WebConstants.AllocationMode.MEMORY.equals(allocationMode)) {

                if (resVmInst.getMemory() > 0) {
                    // 总的可分配内存
                    BigDecimal allMemory = new BigDecimal(resHost.getMemorySize()).multiply(new BigDecimal(
                            allocationRate)).multiply(new BigDecimal(allocationThreshold));

                    // 从已分配的资源实例中取出已分配内存
                    Long resInstanceAllocateMemory = getResVmAllocateMemory(resHost);
                    logger.info(resHost.getHostName() + "总可用内存：" + allMemory);
                    logger.info(resHost.getHostName() + "已分配内存：" +
                                        (resInstanceAllocateMemory + resHost.getVmMemoryAllotSize()));
                    // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                    // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                    BigDecimal restMemory = allMemory.subtract(new BigDecimal(resHost.getVmMemoryAllotSize()))
                                                     .subtract(new BigDecimal(resInstanceAllocateMemory));

                    if (restMemory.compareTo(new BigDecimal(resVmInst.getMemory())) < 0) {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源内存不足。");
                    }
                }

            } else if (WebConstants.AllocationMode.CPU.equals(allocationMode)) {
                if (resVmInst.getCpu() > 0) {
                    // 总的可分配CPU
                    BigDecimal allCpu = null;
                    float resVmAllocateCpu = 0f;
                    ResVe resVe = this.getVeByRes(resHost);
                    if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {
                        // 查询主机下CPU池
                        if (WebConstants.PowerPartitionType.LPAR.equals(resVm.getParType().toString())) {
                            // 开通VM为LPAR时，主机的CPU数量为可分配数
                            allCpu = new BigDecimal(resHost.getCpuNumber());
                            // 从已分配的资源实例中取出已分配CPU
                            Criteria param = new Criteria();
                            param.put("allocateResHostSid", resHost.getResHostSid());
                            param.put("parType", WebConstants.PowerPartitionType.LPAR);
                            List<ResVm> resVms = this.resVmMapper.selectByParams(param);
                            for (ResVm vm : resVms) {
                                resVmAllocateCpu += vm.getCpuCores();
                            }
                        } else {
                            // 在CPU POOL中查找可用总数
                            List<ResCpuPool> resCpuPools = this.resCpuPoolMapper.selectByPoolWithSum(resVm.getAllocateResHostSid());
                            ResCpuPool resCpuPool = null;
                            if (resCpuPools.size() > 0) {
                                if (StringUtil.isNullOrEmpty(resHost.getCpuPoolSid())) {
                                    int maxFreeIndex = 0;
                                    float maxFree = 0f;
                                    for (int i = 0; i < resCpuPools.size(); i++) {
                                        float tmp = resCpuPools.get(i).getMaxValue() -
                                                resCpuPools.get(i).getTotalUsedCpuValue();
                                        if (tmp > maxFree) {
                                            maxFree = tmp;
                                            maxFreeIndex = i;
                                        }
                                    }
                                    allCpu = new BigDecimal(resCpuPools.get(maxFreeIndex).getMaxValue());
                                    resVmAllocateCpu = resCpuPools.get(maxFreeIndex).getTotalUsedCpuValue();
                                    resCpuPool = resCpuPools.get(maxFreeIndex);
                                } else {
                                    for (ResCpuPool cpuPool : resCpuPools) {
                                        if (cpuPool.getResCpuPoolSid().equals(resHost.getCpuPoolSid())) {
                                            allCpu = new BigDecimal(cpuPool.getMaxValue());
                                            resVmAllocateCpu = cpuPool.getTotalUsedCpuValue();
                                            resCpuPool = cpuPool;
                                            break;
                                        }
                                    }
                                }
                            }

                            // 从已分配的资源实例中取出已分配CPU
                            List<ResCpuPoolMparRel> mparRels = this.resCpuPoolMparRelMapper.selectByParams(new Criteria()
                                                                                                                   .put("resCpuPoolSid",
                                                                                                                        resCpuPool
                                                                                                                                .getResCpuPoolSid()
                                                                                                                   ));
                            for (ResCpuPoolMparRel mparRel : mparRels) {
                                resVmAllocateCpu += mparRel.getCpuValue();
                            }
                        }
                    } else {
                        // 总的可分配CPU
                        allCpu = new BigDecimal(resHost.getCpuCores()).multiply(new BigDecimal(allocationRate))
                                                                      .multiply(new BigDecimal(allocationThreshold));
                        // 从已分配的资源实例中取出已分配CPU
                        resVmAllocateCpu = getResVmAllocateCpu(resHost);
                    }
                    logger.info(resHost.getHostName() + "总可用CPU：" + allCpu);
                    logger.info(resHost.getHostName() + "已分配CPU：" + (resVmAllocateCpu + resHost.getVmCpuCore()));
                    // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                    // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                    BigDecimal restCpu = allCpu.subtract(new BigDecimal(resHost.getVmCpuCore()))
                                               .subtract(new BigDecimal(resVmAllocateCpu));

                    if (restCpu.compareTo(new BigDecimal(resVmInst.getCpu())) < 0) {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源CPU不足。");
                    }
                }

            }

            // 判断磁盘是否足够
            List<ResVdInst> resVdInstList = resVmInst.getDataDisks();
            List<ResStorage> resStoList = new ArrayList<ResStorage>();
            if (resVdInstList != null && resVdInstList.size() > 0) {
                for (ResVdInst resVdInst : resVdInstList) {
                    if (resVdInst.getDiskSize() > 0) {
                        if (WebConstants.VdOperate.ADD.equals(resVdInst.getOperate())) {

                            // 查询主机下的存储
                            List<ResStorage> resHostStoList = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
                            if (resStoList.size() > 0) {
                                if (resHostStoList != null && resHostStoList.size() > 0) {
                                    for (ResStorage resStoDb : resHostStoList) {

                                        boolean flag = false;
                                        for (ResStorage resSto : resStoList) {
                                            if (resSto.getResStorageSid().equals(resStoDb.getResStorageSid())) {
                                                // logger.info(resSto.getStorageName());
                                                // resSto.setStoAllotSize(resStoDb.getStoAllotSize());
                                                flag = true;
                                            }
                                        }
                                        if (!flag) {
                                            resStoList.add(resStoDb);
                                        }
                                    }
                                }

                            } else {
                                resStoList.addAll(resHostStoList);
                            }
                            // 资源分配策略
                            String stoAllocationPolicy = null;

                            // 资源分配方式
                            String stoAllocationMode = null;

                            // 资源分配比率
                            String stoAllocationRate = null;

                            // 资源可分配阀值
                            String stoAllocationThreshold = null;
                            for (ResStorage resStorage : resStoList) {

                                // 查询主机所在计算资源池
                                Criteria example1 = new Criteria();
                                example1.put("resTopologySid", resStorage.getResPoolSid());

                                // 查询资源池配置
                                List<ResTopologyConfig> resConfigList = this.resTopologyConfigMapper.selectByParams(
                                        example1);

                                for (ResTopologyConfig resTopConfig : resConfigList) {
                                    if (WebConstants.ResPoolConfigKey.ALLOCATIONPOLICY.equals(resTopConfig.getConfigKey())) {
                                        stoAllocationPolicy = resTopConfig.getConfigValue();
                                    } else if (WebConstants.ResPoolConfigKey.ALLOCATIONMODE.equals(resTopConfig.getConfigKey())) {
                                        stoAllocationMode = resTopConfig.getConfigValue();
                                    } else if (WebConstants.ResPoolConfigKey.ALLOCATIONRATE.equals(resTopConfig.getConfigKey())) {
                                        stoAllocationRate = resTopConfig.getConfigValue();
                                    } else if (WebConstants.ResPoolConfigKey.ALLOCATIONTHRESHOLD.equals(resTopConfig.getConfigKey())) {
                                        stoAllocationThreshold = resTopConfig.getConfigValue();
                                    }
                                }
                            }

                            if (resStoList.size() > 0) {

                                boolean flag = false;

                                // 已均分方式排序存储，首先循环使用率最小的存储
                                List<ResStorage> resStoList1 = this.getAlallocationPolicyStoList(resStoList,
                                                                                                 stoAllocationPolicy
                                );
                                for (ResStorage resStorage : resStoList1) {
                                    // 总的可分配空间
                                    BigDecimal allStorageSize = new BigDecimal(resStorage.getTotalCapacity()).multiply(
                                            new BigDecimal(stoAllocationRate))
                                                                                                             .multiply(
                                                                                                                     new BigDecimal(
                                                                                                                             stoAllocationThreshold));

                                    // 获取该存储下已开通虚拟机占用存储大小
                                    Long resInstanceAllocateStoSize = getResVmAllocateStoSize(resStorage);
                                    // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                                    // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                                    BigDecimal restSto = allStorageSize.subtract(new BigDecimal(resStorage.getStoAllotSize()))
                                                                       .subtract(new BigDecimal(
                                                                               resInstanceAllocateStoSize));

                                    // 若使用率最小的存储不满足，则不需要考虑该主机下其他存储
                                    if (restSto.compareTo(new BigDecimal(resVdInst.getDiskSize())) >= 0) {
                                        logger.info(resHost.getHostName() + "下的存储：" + resStorage.getStorageName() +
                                                            "总可用存储：" + allStorageSize + " 已分配存储：" +
                                                            (resInstanceAllocateStoSize +
                                                                    resStorage.getStoAllotSize()));
                                        setResourceStorages(resStorage, resStoList, resVdInst.getDiskSize());
                                        flag = true;
                                        logger.info("添加磁盘在存储：" + resStorage.getStorageName() + "上预占成功");
                                        break;
                                    } else {
                                        flag = false;
                                    }
                                }
                                if (!flag) {
                                    throw new RpcException(RpcException.BIZ_EXCEPTION,
                                                           resHost.getHostName() + "主机下没有足够的存储空间。"
                                    );
                                }
                            } else {
                                throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源没有关联存储。");
                            }
                        } else if (WebConstants.VdOperate.EXPAND.equals(resVdInst.getOperate())) {

                            ResVd resVd = this.resVdMapper.selectByPrimaryKey(resVdInst.getResVdInstId());
                            ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resVd.getAllocateResStorageSid());
                            if (resStoList.size() > 0) {
                                boolean flag = false;
                                for (ResStorage resSto : resStoList) {
                                    if (resStorage.getResStorageSid().equals(resSto.getResStorageSid())) {
                                        resStorage.setStoAllotSize(resSto.getStoAllotSize());
                                        flag = true;
                                    }
                                }
                                if (!flag) {
                                    resStoList.add(resStorage);
                                }
                            } else {
                                resStoList.add(resStorage);
                            }

                            // 资源分配比率
                            String stoAllocationRate = null;

                            // 资源可分配阀值
                            String stoAllocationThreshold = null;

                            // 查询主机所在计算资源池
                            Criteria stoExample = new Criteria();
                            example.put("resTopologySid", resStorage.getResPoolSid());

                            // 查询资源池配置
                            List<ResTopologyConfig> resStoConfigList = this.resTopologyConfigMapper.selectByParams(
                                    example);

                            for (ResTopologyConfig resTopConfig : resStoConfigList) {
                                if (WebConstants.ResPoolConfigKey.ALLOCATIONRATE.equals(resTopConfig.getConfigKey())) {
                                    stoAllocationRate = resTopConfig.getConfigValue();
                                } else if (WebConstants.ResPoolConfigKey.ALLOCATIONTHRESHOLD.equals(resTopConfig.getConfigKey())) {
                                    stoAllocationThreshold = resTopConfig.getConfigValue();
                                }
                            }

                            // 总的可分配空间
                            BigDecimal allStorageSize = new BigDecimal(resStorage.getTotalCapacity()).multiply(new BigDecimal(
                                    stoAllocationRate)).multiply(new BigDecimal(stoAllocationThreshold));

                            // 获取该存储下已开通虚拟机占用存储大小
                            Long resInstanceAllocateStoSize = getResVmAllocateStoSize(resStorage);
                            logger.info(resStorage.getStorageName() + "总可用存储：" + allStorageSize);
                            logger.info(resStorage.getStorageName() + "已分配存储：" +
                                                (resInstanceAllocateStoSize + resStorage.getStoAllotSize()));
                            // 剩余可分配的资源数量 = 换算公式计算出的可分配的资源数量*可分配阀值 -
                            // 已分配使用的资源数量(实际)-已分配使用的资源数量(虚拟)
                            BigDecimal restStoSize = allStorageSize.subtract(new BigDecimal(resStorage.getStoAllotSize()))
                                                                   .subtract(new BigDecimal(resInstanceAllocateStoSize));

                            // 若使用率最小的存储不满足，则不需要考虑该主机下其他存储
                            if (restStoSize.compareTo(new BigDecimal(resVdInst.getDiskSize())) >= 0) {
                                resStorage.setStoAllotSize(resStorage.getStoAllotSize() + resVdInst.getDiskSize());
                            } else {
                                throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源存储空间不足。");
                            }
                        }
                    }
                }
            }

            // 判断网络
            List<ResNetworkInst> resNetList = resVmInst.getNets();
            if (resNetList != null && resNetList.size() > 0) {
                for (ResNetworkInst resNet : resNetList) {
                    if (WebConstants.NetOperate.ADD.equals(resNet.getOperate())) {
                        if (resNet.getIpAddress() == null) {
                            if (resNet.getResNetworkId() != null) {
                                ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(resNet.getResNetworkId());
                                // 判断主机下是否存储在该vlan的端口组
                                Criteria criteria = new Criteria();
                                criteria.put("vlanId", resNetwork.getVlanId());
                                criteria.put("resHostSid", resVm.getAllocateResHostSid());
                                List<ResVsPortGroup> dvPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria);
                                List<ResVsPortGroup> portList = new ArrayList<ResVsPortGroup>();
                                if (dvPortList != null && dvPortList.size() > 0) {
                                    portList = dvPortList;
                                } else {
                                    // 当没有对应vlanId的网络时，根据vlantag查询主机下有没有对应的端口组
                                    Criteria criteria2 = new Criteria();
                                    criteria2.put("vlanId", resNetwork.getVlanId());
                                    List<ResVlan> resVlans = this.resVlanMapper.selectByParams(criteria2);
                                    if (resVlans != null && resVlans.size() > 0) {
                                        Criteria criteria3 = new Criteria();
                                        criteria3.put("name", resVlans.get(0).getTag());
                                        criteria3.put("resHostSid", resVm.getAllocateResHostSid());
                                        List<ResVsPortGroup> svPortList = this.resVsPortGroupMapper.selectPortsByHost(
                                                criteria3);
                                        if (svPortList != null && svPortList.size() > 0) {
                                            portList = svPortList;
                                        }
                                    } else {
                                        throw new RpcException(RpcException.BIZ_EXCEPTION, "分配的网络对应的VLAN不存在。");
                                    }
                                }
                                if (portList != null && portList.size() > 0) {
                                    // 挑选可用IP
                                    ResIp alocateResIp = this.getAlallocationResIp(resNet.getResNetworkId());
                                    if (alocateResIp == null) {
                                        throw new RpcException(RpcException.BIZ_EXCEPTION,
                                                               resNetwork.getNetworkName() + "IP资源不足。"
                                        );
                                    }
                                } else {
                                    throw new RpcException(RpcException.BIZ_EXCEPTION, "计算资源没有对应的网络。");
                                }
                            }
                        }
                    }
                }
            }
            result = new ResInstResult(ResInstResult.SUCCESS);
        } catch (RpcException appE) {
            result = new ResInstResult(ResInstResult.FAILURE, appE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    @Override
    public ResInstResult createVmCheck(List<ResVmInst> resVmInstList) {
        ResInstResult result = null;

        // List<ResHost> allocatedResHosts = new ArrayList<ResHost>();
        List<ResIp> allocatedResIps = new ArrayList<ResIp>();
        List<ResHostItem> allocatedResHostNetHba = new ArrayList<ResHostItem>();
        List<ResHostItem> allocatedResHostLocalDiskHba = new ArrayList<ResHostItem>();
        // List<String> vcSidList = new ArrayList<String>();
        // List<ResNetwork> resNetworkList = new ArrayList<ResNetwork>();

        // 查询资源集下所有主机
        List<ResHost> allHosts = this.getAllResHosts(resVmInstList);

        try {
            for (ResVmInst resVmInst : resVmInstList) {

                ResVm resVm = new ResVm();

                // 插入虚拟机
                resVm.setCpuCores(resVmInst.getCpu());
                resVm.setMemorySize(resVmInst.getMemory());
                if (!StringUtil.isNullOrEmpty(resVmInst.getPartitionType())) {
                    resVm.setParType(Integer.parseInt(resVmInst.getPartitionType()));
                }

                // 设置磁盘
                List<ResVd> resVdList = new ArrayList<ResVd>();
                List<ResVdInst> resVdInstList = resVmInst.getDataDisks();
                for (ResVdInst resVdInst : resVdInstList) {

                    // 插入数据盘磁盘
                    ResVd resVd = new ResVd();
                    resVd.setAllocateDiskSize(resVdInst.getDiskSize());
                    resVd.setResVmSid(resVm.getResVmSid());
                    resVd.setStoragePurpose(resVdInst.getStoragePurpose());
                    resVdList.add(resVd);
                }
                resVm.setResVdList(resVdList);

                // 根据服务层参数获取所有主机
                List<ResHost> resHosts = this.getResHostCreateVm(resVmInst);
                if (resHosts != null && resHosts.size() > 0) {
                    // 取得主机下可用存储
                    for (ResHost resHost : resHosts) {
                        ResVe resVe = this.getVeByRes(resHost);
                        if (WebConstants.VirtualPlatformType.VMWARE.equals(resVe.getVirtualPlatformType())) {
                            // 查找主机下的存储
                            List<ResStorage> resStorages = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
                            resHost.setResStorages(resStorages);
                        } else if (WebConstants.VirtualPlatformType.HMC.equals(resVe.getVirtualPlatformType())) {

                            List<ResStorage> resStorageList = new ArrayList<ResStorage>();

                            // 根据主机查询VIOS
                            Criteria criteria = new Criteria();
                            criteria.put("resHostSid", resHost.getResHostSid());
                            List<ResVios> resVioses = this.resViosMapper.selectByParams(criteria);
                            // TODO 根据VIOS查询存储
                            if (resVioses != null && resVioses.size() > 0) {
                                for (ResVios resVios : resVioses) {
                                    List<ResStorage> resStorages = this.resStorageMapper.selectAvaliableStorageByVios(
                                            resVios.getResViosSid());
                                    resStorageList.addAll(resStorages);
                                }
                                resHost.setResStorages(resStorageList);
                            } else {
                                Criteria example = new Criteria();
                                example.put("resHostSid", resHost.getResHostSid());
                                example.put("hostItemTypeCode", WebConstants.HostItemTypeCode.LOCAL_DISK);
                                example.put("resAllocStatus", WebConstants.HostItemAllocStatus.FREE);
                                List<ResHostItem> resHostItems = this.resHostItemMapper.selectByParams(example);
                                if (resHostItems != null && resHostItems.size() > 0) {
                                    ResStorage resStorage = this.resStorageMapper.selectByPrimaryKey(resHostItems.get(0)
                                                                                                                 .getRelateResSid());
                                    if (resStorage != null) {
                                        resStorageList.add(resStorage);
                                    }
                                }
                                resHost.setResStorages(resStorageList);
                            }
                        }
                    }

                    if (allHosts != null && allHosts.size() > 0) {

                        // 把已分配的资源设置到当前主机集合中
                        for (ResHost resHost : allHosts) {
                            if (resHosts.size() > 0) {
                                for (ResHost resH : resHosts) {
                                    if (resH.getResHostSid().equals(resHost.getResHostSid())) {
                                        resH.setVmCpuCore(resHost.getVmCpuCore());
                                        resH.setVmMemoryAllotSize(resHost.getVmMemoryAllotSize());
                                        List<ResStorage> resStorageList = resHost.getResStorages();
                                        List<ResStorage> resStoList = resH.getResStorages();
                                        if (resStorageList != null && resStorageList.size() > 0) {
                                            if (resStoList != null && resStoList.size() > 0) {
                                                for (ResStorage resStorage : resStorageList) {
                                                    for (ResStorage resSto : resStoList) {
                                                        if (resSto.getResStorageSid()
                                                                  .equals(resStorage.getResStorageSid())) {
                                                            resSto.setStoAllotSize(resStorage.getStoAllotSize());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    } else {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, "资源集下没有关联主机。");
                    }

                    // 判断所有主机是否有足够的资源开通虚拟机
                    ResVm allcateVm = this.getVmAvailability(resVm, resHosts);
                    if (allcateVm == null) {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, "检查资源失败。");
                    } else {
                        resVm.setAllocateResHostSid(allcateVm.getAllocateResHostSid());
                        resVm.setResVdList(allcateVm.getResVdList());
                        // setResourceHosts(hostList, allcateVm);
                    }
                } else {
                    throw new RpcException(RpcException.BIZ_EXCEPTION, "该计算资源没有可用主机。");
                }

                // 查找被本配的VE
                ResVe allcateResVe = null;
                for (ResHost host : resHosts) {
                    if (host.getResHostSid().equals(resVm.getAllocateResHostSid())) {
                        allcateResVe = this.getVeByRes(host);
                        break;
                    }
                }

                // 检查资源镜像是否存在
                ResImageSoftWare resImageSoftWare = new ResImageSoftWare();
                resImageSoftWare.setOsVersion(resVmInst.getOsVersion());
                resImageSoftWare.setResVeSid(allcateResVe.getResTopologySid());
                if (resVmInst.getSoftwares() != null) {
                    resImageSoftWare.setSoftwares(resVmInst.getSoftwares());
                }
                // 查询资源环境最匹配模板
                resImageSoftWare = this.resImageService.getImageBySoftWare(resImageSoftWare);
                if (StringUtil.isNullOrEmpty(resImageSoftWare.getImageSid())) {
                    throw new RpcException(RpcException.BIZ_EXCEPTION,
                                           "选择的资源环境(" + allcateResVe.getResTopologyName() + ")没有对应操作系统模板\n(" +
                                                   resVmInst.getOsVersion() + ")，请重新手动选择。"
                    );
                }

                //如果是HMC环境，且主机没有关联VIOS，则判断配件
                if (WebConstants.VirtualPlatformType.HMC.equals(allcateResVe.getVirtualPlatformType()) &&
                        WebConstants.PowerPartitionType.LPAR.equals(resVmInst.getPartitionType())) {
                    ResHost resHost1 = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
                    // 查询主机设备
                    Criteria example = new Criteria();
                    example.put("resHostSid", resVm.getAllocateResHostSid());
                    example.put("hostItemTypeCode", WebConstants.HostItemTypeCode.LOCAL_DISK);
                    example.put("resAllocStatus", WebConstants.HostItemAllocStatus.FREE);
                    List<ResHostItem> resHostItems = this.resHostItemMapper.selectByParams(example);
                    List<ResHostItem> resHostItemsClone = new ArrayList<ResHostItem>(resHostItems);
                    if (resHostItems != null && resHostItems.size() > 0) {
                        if (allocatedResHostLocalDiskHba != null && allocatedResHostLocalDiskHba.size() > 0) {
                            for (ResHostItem resHostItemDb : resHostItems) {

                                // 判断是否IP是否已被分配
                                boolean is = allocatedResHostNetHba.contains(resHostItemDb);
                                if (is) {
                                    resHostItemsClone.remove(resHostItemDb);
                                } else {
                                    allocatedResHostLocalDiskHba.add(resHostItemDb);
                                    break;
                                }
                            }

                        } else {
                            allocatedResHostLocalDiskHba.add(resHostItems.get(0));
                        }
                    }
                    logger.info(resHost1.getHostName() + "剩余本地盘个数" + resHostItemsClone.size());
                    if (resHostItemsClone.size() <= 0) {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, resHost1.getHostName() + "本地盘不足。");
                    }
                }
                // 判断网络
                List<ResNetworkInst> nets = null;
                // 有画面选择，则使用选择的网络
                if (resVmInst.getNets() != null && resVmInst.getNets().size() > 0) {
                    nets = resVmInst.getNets();
                } else {
                    // 网络资源没有选择的时候，查询主机网络关系表，选择可用资源
                    List<ResNetworkHost> netList = this.resNetworkHostMapper.selectByParams(new Criteria().put(
                            "resHostSid",
                            resVm.getAllocateResHostSid()
                    ));
                    if (netList.size() > 0) {
                        nets = new ArrayList<ResNetworkInst>();
                        for (ResNetworkHost resNetworkHost : netList) {
                            ResNetworkInst resNetworkInst = new ResNetworkInst();
                            resNetworkInst.setResNetworkId(resNetworkHost.getResNetworkSid());
                            nets.add(resNetworkInst);
                        }
                    } else {
                        throw new RpcException(RpcException.BIZ_EXCEPTION, "主机没有关联的网络资源。");
                    }
                }
                RpcException exception = null;
                if (WebConstants.VirtualPlatformType.VMWARE.equals(allcateResVe.getVirtualPlatformType())) {
                    for (ResNetworkInst rvni : nets) {
                        ResNetwork resNetwork = this.resNetworkMapper.selectByPrimaryKey(rvni.getResNetworkId());

                        // 判断主机下是否存储在该vlan的端口组
                        Criteria criteria = new Criteria();
                        criteria.put("vlanId", resNetwork.getVlanId());
                        criteria.put("resHostSid", resVm.getAllocateResHostSid());
                        List<ResVsPortGroup> dvPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria);
                        List<ResVsPortGroup> portList = new ArrayList<ResVsPortGroup>();
                        if (dvPortList != null && dvPortList.size() > 0) {
                            portList = dvPortList;
                        } else {
                            // 当没有对应vlanId的网络时，根据vlantag查询主机下有没有对应的端口组
                            Criteria criteria2 = new Criteria();
                            criteria2.put("vlanId", resNetwork.getVlanId());
                            List<ResVlan> resVlans = this.resVlanMapper.selectByParams(criteria2);
                            if (resVlans != null && resVlans.size() > 0) {
                                Criteria criteria3 = new Criteria();
                                criteria3.put("name", resVlans.get(0).getTag());
                                criteria3.put("resHostSid", resVm.getAllocateResHostSid());
                                List<ResVsPortGroup> svPortList = this.resVsPortGroupMapper.selectPortsByHost(criteria3);
                                if (svPortList != null && svPortList.size() > 0) {
                                    portList = svPortList;
                                }
                            } else {
                                exception = new RpcException(RpcException.BIZ_EXCEPTION, "分配的网络对应的VLAN不存在。");
                                continue;
                            }
                        }
                        if (portList != null && portList.size() > 0) {
                            if (StringUtil.isNullOrEmpty(rvni.getIpAddress())) {

                                Criteria exampleCriteria = new Criteria();
                                exampleCriteria.put("resNetworkSid", rvni.getResNetworkId());
                                exampleCriteria.put("usageStatus", WebConstants.ResIpUsageStatus.AVAILABLE);
                                List<ResIp> avaliableIps = this.resIpMapper.selectByParams(exampleCriteria);
                                List<ResIp> avaliableIpsClone = new ArrayList<ResIp>(avaliableIps);

                                if (avaliableIps.size() > 0) {
                                    if (allocatedResIps.size() > 0) {
                                        for (ResIp resIpDb : avaliableIps) {
                                            // 判断是否IP是否已被分配
                                            if (allocatedResIps.contains(resIpDb)) {
                                                avaliableIpsClone.remove(resIpDb);
                                            } else {
                                                allocatedResIps.add(resIpDb);
                                                break;
                                            }
                                        }

                                    } else {
                                        allocatedResIps.add(avaliableIps.get(0));
                                    }
                                }
                                logger.info("剩余IP个数" + avaliableIpsClone.size());
                                if (avaliableIpsClone.size() <= 0) {
                                    exception = new RpcException(RpcException.BIZ_EXCEPTION, "IP资源不足。");
                                } else {
                                    exception = null;
                                    break;
                                }

                            }
                        } else {
                            exception = new RpcException(RpcException.BIZ_EXCEPTION, "计算资源没有对应的网络。");
                        }
                    }
                } else if (WebConstants.VirtualPlatformType.HMC.equals(allcateResVe.getVirtualPlatformType())) {
                    ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());

                    for (ResNetworkInst rvni : nets) {
                        if (WebConstants.PowerPartitionType.MPAR.equals(resVmInst.getPartitionType())) {
                            // 判断主机是否关联虚拟交换机
                            if (StringUtil.isNullOrEmpty(resVmInst.getVirtualSwitchSid())) {
                                List<ResVs> resVsList = this.resVsMapper.selectByHostSid(resVm.getAllocateResHostSid());
                                if (resVsList == null || resVsList.size() <= 0) {
                                    exception = new RpcException(RpcException.BIZ_EXCEPTION, "主机未关联虚拟机交换机。");
                                    continue;
                                }
                            } else {
                                ResVs resVs = this.resVsMapper.selectByPrimaryKey(resVmInst.getVirtualSwitchSid());
                                if (resVs == null) {
                                    exception = new RpcException(RpcException.BIZ_EXCEPTION, "主机未关联虚拟机交换机。");
                                    continue;
                                }
                            }
                        } else {
                            // 查询主机设备
                            Criteria example = new Criteria();
                            example.put("resHostSid", resVm.getAllocateResHostSid());
                            example.put("hostItemTypeCode", WebConstants.HostItemTypeCode.NETWORK_CARD);
                            example.put("resAllocStatus", WebConstants.HostItemAllocStatus.FREE);
                            List<ResHostItem> resHostItems = this.resHostItemMapper.selectByParams(example);
                            List<ResHostItem> resHostItemsClone = new ArrayList<ResHostItem>(resHostItems);
                            if (resHostItems.size() > 0) {
                                if (allocatedResHostNetHba.size() > 0) {
                                    for (ResHostItem resHostItemDb : resHostItems) {

                                        // 判断是否IP是否已被分配
                                        boolean is = allocatedResHostNetHba.contains(resHostItemDb);
                                        if (is) {
                                            resHostItemsClone.remove(resHostItemDb);
                                        } else {
                                            allocatedResHostNetHba.add(resHostItemDb);
                                            break;
                                        }
                                    }

                                } else {
                                    allocatedResHostNetHba.add(resHostItems.get(0));
                                }
                            }
                            logger.info(resHost.getHostName() + "剩余网络HBA卡个数" + resHostItemsClone.size());
                            if (resHostItemsClone.size() <= 0) {
                                exception = new RpcException(RpcException.BIZ_EXCEPTION,
                                                             resHost.getHostName() + "网络HBA卡不足。"
                                );
                                continue;
                            }
                        }

                        Criteria exampleCriteria = new Criteria();
                        exampleCriteria.put("resNetworkSid", rvni.getResNetworkId());
                        exampleCriteria.put("usageStatus", WebConstants.ResIpUsageStatus.AVAILABLE);
                        List<ResIp> avaliableIps = this.resIpMapper.selectByParams(exampleCriteria);
                        List<ResIp> avaliableIpsClone = new ArrayList<ResIp>(avaliableIps);

                        if (avaliableIps != null && avaliableIps.size() > 0) {
                            if (allocatedResIps != null && allocatedResIps.size() > 0) {
                                for (ResIp resIpDb : avaliableIps) {

                                    // 判断是否IP是否已被分配
                                    boolean is = allocatedResIps.contains(resIpDb);
                                    if (is) {
                                        avaliableIpsClone.remove(resIpDb);
                                    } else {
                                        allocatedResIps.add(resIpDb);
                                        break;
                                    }
                                }

                            } else {
                                allocatedResIps.add(avaliableIps.get(0));
                            }
                        }
                        logger.info("剩余IP个数" + avaliableIpsClone.size());
                        if (avaliableIpsClone == null || avaliableIpsClone.size() <= 0) {
                            exception = new RpcException(RpcException.BIZ_EXCEPTION, "IP资源不足。");
                        } else {
                            exception = null;
                            break;
                        }
                    }
                }
                // 如果有异常
                if (exception != null) {
                    throw exception;
                }
            }
            result = new ResInstResult(ResInstResult.SUCCESS, "检查资源成功");
        } catch (RpcException appE) {
            logger.error("资源挑选异常：" + ExceptionUtils.getFullStackTrace(appE));
            result = new ResInstResult(ResInstResult.FAILURE, appE.getMessage());
        } catch (Exception e) {
            logger.error("资源挑选异常：" + ExceptionUtils.getFullStackTrace(e));
            result = new ResInstResult(ResInstResult.FAILURE, "资源不足");
        }
        return result;
    }

    /**
     * 更新VD信息
     *
     * @param resVm 虚机
     * @return 更新后虚机对应VD列表
     *
     * @throws IOException the io exception
     */
    public List<ResVd> updataDisk(ResVm resVm) throws IOException {

        List<ResVd> resVdList = new ArrayList<ResVd>();
        List<ResVd> vdList = resVm.getResVdList();
        List<ResVd> cloneVdList = null;

        if (vdList != null && vdList.size() > 0) {
            cloneVdList = new ArrayList<ResVd>(vdList);
        }
        // 更新虚拟磁盘
        Criteria example1 = new Criteria();
        example1.put("resVmSid", resVm.getResVmSid());
        List<ResVd> managedResVdList = this.resVdMapper.selectByParams(example1);
        // 插入磁盘表
        if (managedResVdList != null && managedResVdList.size() > 0) {

            for (ResVd managedResvd : managedResVdList) {
                if (vdList != null && vdList.size() > 0) {
                    boolean vdFlag = false;
                    for (ResVd resVd : vdList) {
                        String dataStoreName = resVd.getDataStoreName();
                        Criteria criteria = new Criteria();
                        criteria.put("storageName", dataStoreName);
                        List<ResStorage> resStoList = this.resStorageMapper.selectByParams(criteria);
                        if (resStoList != null && resStoList.size() > 0) {
                            resVd.setAllocateResStorageSid(resStoList.get(0).getResStorageSid());
                        }
                        if (managedResvd.getUuid() != null) {
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
                String dataStoreName = resVd.getDataStoreName();
                Criteria criteria = new Criteria();
                criteria.put("storageName", dataStoreName);
                List<ResStorage> resStoList = this.resStorageMapper.selectByParams(criteria);
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

    @Override
    public boolean synaVmInfo(ResVm resVm, ResHost resHost, ResVe resVe) throws Exception {
        if (resVm != null) {
            if (resVm.getStatus().equals(WebConstants.ResVmStatus.DELETING) ||
                    resVm.getStatus().equals(WebConstants.ResVmStatus.DELETED) ||
                    resVm.getStatus().equals(WebConstants.ResVmStatus.CREATING) ||
                    resVm.getStatus().equals(WebConstants.ResVmStatus.OCCUPING)) {
                return true;
            }
            if (resVm.getHostName().equals(resHost.getHostName())) {
                if (resVm.getResVmSid() == null) {
                    WebUtil.prepareInsertParams(resVm);
                    this.resVmMapper.insertSelective(resVm);
                    updateVmNetByVm(resVm);
                    updataDisk(resVm);
                } else {
                    WebUtil.prepareUpdateParams(resVm);
                    this.resVmMapper.updateByPrimaryKeySelective(resVm);
                    updateVmNetByVm(resVm);
                    updataDisk(resVm);
                }
                return true;
            } else {
                Criteria criteria = new Criteria();
                criteria.put("hostName", resVm.getHostName());

                List<ResHost> resHostList = resHostMapper.selectResHostByParams(criteria);
                if (resHostList != null && resHostList.size() > 0) {
                    WebUtil.prepareUpdateParams(resVm);
                    resVm.setAllocateResHostSid(resHostList.get(0).getResHostSid());
                    this.resVmMapper.updateByPrimaryKeySelective(resVm);
                    updateVmNetByVm(resVm);
                    updataDisk(resVm);
                } else {
                    resHostService.asyncHost(null, resVm.getHostName(), resVe);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateVmNetByVm(ResVm resVm) {

        // 插入IP
        List<ResVmNetwork> nicMaps = resVm.getResVmNetList();
        if (nicMaps != null && nicMaps.size() > 0) {

            // 删除原有的虚拟机和ip的关系
            // this.resVmNetworkMapper.deleteByPrimaryKey(resVm.getResVmSid());
            for (ResVmNetwork nic : nicMaps) {
                String ip = nic.getIpAddress();
                String mac = nic.getMac();
                if (ip != null && !"".equals(ip)) {
                    // 查询所属网络
                    Criteria criteria = new Criteria();
                    criteria.put("ipAddress", ip);
                    List<ResIp> resIps = this.resIpMapper.selectByParams(criteria);
                    ResVmNetwork resVmNet = new ResVmNetwork();
                    resVmNet.setResVmSid(resVm.getResVmSid());
                    resVmNet.setIpAddress(ip);
                    resVmNet.setMac(mac);
                    resVmNet.setStatus(WebConstants.ResVmNetworkStatus.NORMAL);
                    if (resIps != null && resIps.size() > 0) {
                        resVmNet.setResNetworkSid(resIps.get(0).getResNetworkSid());
                    }
                     /*
                     * 数据库中有则更新没有则插入
					 */
                    int updateResult = resVmNetworkMapper.updateByPrimaryKeySelective(resVmNet);
                    if (updateResult == 0) {
                        int insertResult = resVmNetworkMapper.insertSelective(resVmNet);
                    }
                }
            }

            List<ResVmNetwork> resVmNetworks = new ArrayList<ResVmNetwork>();
            resVmNetworks = resVmNetworkMapper.selectByVmSid(resVm.getResVmSid());
            if (resVmNetworks.size() > 0) {
                 /*
                 * 删除数据库中多余的ip
				 */
                List<ResVmNetwork> removeNics = new ArrayList<ResVmNetwork>();
                for (ResVmNetwork resVmNetwork : resVmNetworks) {
                    boolean flag = false;
                    for (ResVmNetwork vmNic : nicMaps) {
                        if (resVmNetwork.getIpAddress().equals(vmNic.getIpAddress())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        removeNics.add(resVmNetwork);
                    }
                }
                if (removeNics.size() > 0) {
                    for (ResVmNetwork resVmNetwork : removeNics) {
                        Criteria criteria = new Criteria();
                        criteria.put("ipAddress", resVmNetwork.getIpAddress());
                        resVmNetworkMapper.deleteByParams(criteria);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public ResInstResult synaVmInfo(String resVmSid) throws Exception {

        ResVm resVmDb = resVmMapper.selectByPrimaryKey(resVmSid);
        if (resVmDb == null || resVmDb.getStatus().equals(WebConstants.ResVmStatus.DELETING) ||
                resVmDb.getStatus().equals(WebConstants.ResVmStatus.DELETED) ||
                resVmDb.getStatus().equals(WebConstants.ResVmStatus.CREATING) ||
                resVmDb.getStatus().equals(WebConstants.ResVmStatus.OCCUPING)) {
            return new ResInstResult(ResInstResult.FAILURE, "虚拟机正在删除中，无法同步！", null);
        }
        ResHost resHost = resHostMapper.selectByPrimaryKey(resVmDb.getAllocateResHostSid());
        ResTopology resVc = resTopologyMapper.selectByPrimaryKey(resHost.getParentTopologySid());
        ResVe resVe = new ResVe();
        if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resVc.getResTopologyType())) {
            resVe = this.resVeMapper.selectByPrimaryKey(resVc.getResTopologySid());
        } else {
            resVe = this.resVeMapper.selectByPrimaryKey(resVc.getParentTopologySid());
        }
        String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
        String url = adapterUrl + "/vm/getVmInfo/";

        Map<String, Object> mapVm = new HashMap<String, Object>();
        mapVm.put("providerUrl", resVe.getManagementUrl());
        mapVm.put("authUser", resVe.getManagementAccount());
        mapVm.put("authPass", resVe.getManagementPassword());
        mapVm.put("uuid", resVmDb.getUuid());

        VmScanAlone vmScanAlone = new VmScanAlone();
        vmScanAlone.setProviderType(resVe.getVirtualPlatformType());
        vmScanAlone.setAuthUrl(resVe.getManagementUrl());
        vmScanAlone.setAuthUser(resVe.getManagementAccount());
        vmScanAlone.setAuthPass(resVe.getManagementPassword());
        vmScanAlone.setVirtEnvType(resVe.getVirtualPlatformType());
        vmScanAlone.setVirtEnvUuid(resVe.getResTopologySid());
        vmScanAlone.setUuid(resVmDb.getUuid());

        Object obj = MQHelper.rpc(vmScanAlone);

        VmScanAloneResult vmScanAloneResult = (VmScanAloneResult) obj;

        // 插入任务表
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("同步虚拟机");
        log.setTaskTarget(resVmDb.getVmName());
        log.setTaskType(WebConstants.TaskType.SCAN_VM);

        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskLogSid = taskLog.getTaskLogSid();

        try {
            if (vmScanAloneResult.isSuccess()) {
                ResVm resVm = new ResVm();
                // ResVm resVm = new ResVm(vmScanAloneResult.getVm());
                resVm.setResVmSid(resVmSid);
                boolean res = synaVmInfo(resVm, resHost, resVe);
                if (res) {
                    // 检查虚拟机是否有变化,调用服务层接口
                    boolean isChange = this.resVeService.isResVmSame(resVmDb, resVm);
                    if (!isChange) {
                        System.out.println(resVmDb.getVmName());
                        try {
                            this.resInfoSync.updateResVmInfo(resVmDb.getResVmSid(),
                                                             WebConstants.scanVmChangeType.CHANGE
                            );
                        } catch (Exception e) {
                            logger.error("更新虚拟机，调用服务层接口异常：" + ExceptionUtils.getFullStackTrace(e));
                        }
                    }
                }
                // 更新任务表
                taskLog = new TaskLog();
                taskLog.setTaskLogSid(taskLogSid);

                taskLogger.fail(taskLog);
                if (res) {
                    taskLog.setTaskDetail("同步虚拟机成功");
                    taskLogger.success(taskLog);
                    return new ResInstResult(ResInstResult.SUCCESS, "同步虚拟机成功！", null);
                } else {
                    taskLog.setTaskDetail("同步虚拟机失败");
                    taskLogger.fail(taskLog);
                    return new ResInstResult(ResInstResult.FAILURE, "同步虚拟机失败！", null);
                }
            } else {
                if ("10001".equals(vmScanAloneResult.getErrCode())) {

                    Criteria criteria2 = new Criteria();
                    criteria2.put("resVmSid", resVmDb.getResVmSid());
                    this.resVdMapper.deleteByParams(criteria2);
                    // 删除虚拟机与网络关系
                    this.resVmNetworkMapper.deleteByPrimaryKey(resVmDb.getResVmSid());
                    // 删除虚拟机
                    resVmMapper.deleteByPrimaryKey(resVmDb.getResVmSid());
                    // 更新任务表
                    taskLog = new TaskLog();
                    taskLog.setTaskLogSid(taskLogSid);
                    taskLog.setTaskDetail("同步虚拟机成功");
                    taskLogger.success(taskLog);

                    // 当虚拟机删除的时候，调用service接口
                    this.resInfoSync.updateResVmInfo(resVmDb.getResVmSid(), WebConstants.scanVmChangeType.DELETE);
                    return new ResInstResult(ResInstResult.SUCCESS, "虚拟机不存在，删除虚拟机成功！", null);
                } else {
                    // 更新任务表
                    taskLog = new TaskLog();
                    taskLog.setTaskLogSid(taskLogSid);
                    taskLog.setTaskDetail("同步虚拟机失败");
                    taskLogger.fail(taskLog);

                    return new ResInstResult(ResInstResult.FAILURE, "同步虚拟机失败！", null);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // 更新任务表
            taskLog = new TaskLog();
            taskLog.setTaskLogSid(taskLogSid);
            taskLog.setTaskDetail("同步虚拟机失败");
            taskLog.setTaskFailureReason(ExceptionUtils.getMessage(e));
            taskLogger.fail(taskLog);
            return new ResInstResult(ResInstResult.FAILURE, "同步虚拟机失败！", null);
        }
    }

    @Override
    public ResInstResult mulitOp(ResVmOptInst resVmOptInst) throws JSONException, IOException {
        List<String> resVmSids = resVmOptInst.getResVmSids();
        for (String vmSid : resVmSids) {
            switch (resVmOptInst.getOpt()) {
                case WebConstants.VmOperation.START:
                    this.operateVm(vmSid, resVmOptInst, WebConstants.VmOperation.START, null);
                    break;
                case WebConstants.VmOperation.STOP:
                    this.operateVm(vmSid, resVmOptInst, WebConstants.VmOperation.STOP, null);
                    break;
                case WebConstants.VmOperation.REBOOT:
                    this.operateVm(vmSid, resVmOptInst, WebConstants.VmOperation.REBOOT, "SOFT");
                    break;
                case WebConstants.VmOperation.SUSPEND:
                    this.operateVm(vmSid, resVmOptInst, WebConstants.VmOperation.SUSPEND, null);
                    break;
                case WebConstants.VmOperation.RESUME:
                    this.operateVm(vmSid, resVmOptInst, WebConstants.VmOperation.RESUME, null);
                    break;
//                case WebConstants.VmOperation.DESTORY:
//                    this.deleteVm(vmSid, resVmOptInst, false);
//                    break;
                default:
                    break;
            }
        }
        return new ResInstResult(ResInstResult.SUCCESS);
    }

    @Override
    public ResInstResult migrateVm(List<String> resVmSids, String resHostSid, String resStorageSid) {

        if (resVmSids != null && resVmSids.size() > 0) {
            for (String resVmSid : resVmSids) {
                ResVm resVm = this.resVmMapper.selectByPrimaryKey(resVmSid);
                // 插入logger
                // 插入任务表
                TaskLog log = new TaskLog();
                User user = AuthUtil.getAuthUser();
                if (user != null) {
                    log.setAccount(AuthUtil.getAuthUser().getAccount());
                } else {
                    log.setAccount("admin");
                }
                log.setTaskDetail("迁移虚拟机");
                log.setTaskTarget(resVm.getVmName());
                log.setTaskType(WebConstants.TaskType.MIGRATE_VM);
                TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
                // 资源挑选完毕，调用MQ时，插入任务
                TaskLog taskLog = taskLogger.start(log);
                Long taskId = taskLog.getTaskLogSid();
                try {

                    // 更新虚拟机状态
                    resVm.setStatus(WebConstants.ResVmStatus.MIGRATING);
                    WebUtil.prepareUpdateParams(resVm);
                    this.resVmMapper.updateByPrimaryKeySelective(resVm);
                    ResHost resHost = this.resHostMapper.selectByPrimaryKey(resVm.getAllocateResHostSid());
                    ResTopology resVc = this.resTopologyMapper.selectByPrimaryKey(resHost.getParentTopologySid());
                    ResVe resVe = new ResVe();
                    if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resVc.getResTopologyType())) {
                        resVe = this.resVeMapper.selectByPrimaryKey(resVc.getResTopologySid());
                    } else {
                        resVe = this.resVeMapper.selectByPrimaryKey(resVc.getParentTopologySid());
                    }
                    ResHost targetHost = this.resHostMapper.selectByPrimaryKey(resHostSid);
                    ResStorage targetStore = this.resStorageMapper.selectByPrimaryKey(resStorageSid);

                    // //如果存储SID为空，主机SID不为空，说明只迁移主机
                    // if(!StringUtil.isNullOrEmpty(resHostSid)&&StringUtil.isNullOrEmpty(resStorageSid)){
                    //
                    // //判断主机CPU
                    // //如果存储SID不为空，主机SID为空，说明只迁移存储
                    // }else
                    // if(!StringUtil.isNullOrEmpty(resStorageSid)&&StringUtil.isNullOrEmpty(resHostSid)){
                    //
                    // //如果都不为空，说明同时迁移主机和存储
                    // }else
                    // if(!StringUtil.isNullOrEmpty(resStorageSid)&&!StringUtil.isNullOrEmpty(resHostSid)){
                    //
                    //
                    // }else{
                    //
                    // }
                    // 判断主机下
                    VmMigrate vmMigrate = new VmMigrate();

                    vmMigrate.setProviderType(resVe.getVirtualPlatformType());
                    vmMigrate.setAuthUrl(resVe.getManagementUrl());
                    vmMigrate.setAuthUser(resVe.getManagementAccount());
                    vmMigrate.setAuthPass(resVe.getManagementPassword());
                    vmMigrate.setVirtEnvType(resVe.getVirtualPlatformType());
                    vmMigrate.setVirtEnvUuid(resVe.getResTopologySid().toString());
                    vmMigrate.setVmId(resVm.getResVmSid());
                    if (targetHost != null) {
                        // vmMigrate.setTargetHost(targetHost.getHostName());
                        vmMigrate.setTargetHostId(targetHost.getResHostSid());
                    }
                    if (targetStore != null) {
                        vmMigrate.setTargetStore(targetStore.getStorageName());
                        vmMigrate.setTargetStoreId(targetStore.getResStorageSid());
                    }
                    vmMigrate.setVmName(resVm.getVmName());
                    logger.info("调用MQ输入参数：" + JsonUtil.toJson(vmMigrate));
                    String msgId = MQHelper.sendMessage(vmMigrate);
                } catch (Exception e) {
                    log = new TaskLog();
                    log.setTaskLogSid(taskId);
                    log.setTaskFailureReason("迁移虚拟机异常：" + e.getMessage());
                    taskLogger.fail(log);
                    logger.error("MQ调用异常" + ExceptionUtils.getFullStackTrace(e));
                }
            }
        }

        return new ResInstResult(ResInstResult.SUCCESS);
    }

    @Override
    public void checkIsNeedPoweroff(ResVmInst resVmInst) {
        // 当虚拟机CPU，memory 有变化的时候，同步虚拟机
        if (resVmInst.getCpu() != 0 || resVmInst.getMemory() != 0 ||
                (resVmInst.getNets() != null && resVmInst.getNets().size() > 0)) {
            try {
                this.synaVmInfo(resVmInst.getResVmInstId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logger.error("同步虚拟机失败");
            }

            // 同步完成，查询数据库虚拟机状态
            //			ResVm resVmDb = this.resVmMapper.selectByPrimaryKey(resVmInst.getResVmInstId());
            //			if (!(WebConstants.ResVmStatus.POWEREDOFF.equals(resVmDb.getStatus()))) {
            //				throw new RpcException(RpcException.BIZ_EXCEPTION, "虚拟机未关闭，此变更无法进行。");
            //			}
        }
    }

    @Override
    public ResWebConsole vmWebConsole(String params) {
        ResWebConsole resultlist = new ResWebConsole();
        String resvesid = "";
        try {
            if (!StringUtil.isNullOrEmpty(params)) {
                Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
                // String uuid = StringUtil.nullToEmpty(map.get("uuid"));
                // String resHostSid =
                // StringUtil.nullToEmpty(map.get("resHostSid"));
                String resVmSid = StringUtil.nullToEmpty(map.get("resVmSid"));
                ResVm resvm = this.resVmMapper.selectByPrimaryKey(resVmSid);
                String uuid = resvm.getUuid();
                ResHost reshost = this.resHostMapper.selectByPrimaryKey(resvm.getAllocateResHostSid());
                ResTopology restopology = this.resTopologyMapper.selectByPrimaryKey(reshost.getParentTopologySid());
                if ("VC".equals(restopology.getResTopologyType().trim())) {
                    resvesid = restopology.getParentTopologySid();
                } else {
                    resvesid = reshost.getParentTopologySid();
                }
                ResVe resve = this.resVeMapper.selectByPrimaryKey(resvesid);
                String veUrl = resve.getManagementUrl().trim();
                String hostName = veUrl.substring(veUrl.indexOf("//") + 2, veUrl.indexOf("/sdk/"));
                Map<String, Object> maps = new HashMap<String, Object>();
                maps.put("providerUrl", resve.getManagementUrl());
                maps.put("authUser", resve.getManagementAccount());
                maps.put("authPass", resve.getManagementPassword());
                maps.put("uuid", uuid);
                String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
                String clusterUrl = adapterUrl + "/vmrc/getTicket";
                String paramsJson = JsonUtil.toJson(maps);
                RESTHttpResponse responseResult = RSETClientUtil.post(clusterUrl, paramsJson);
                if (RESTHttpResponse.SUCCESS.equals(responseResult.getStatus())) {
                    Map<String, String> vmrcMap = JsonUtil.fromJson(responseResult.getContent(), Map.class);
                    ResWebConsole vmrcs = new ResWebConsole();
                    vmrcs.setModes(6);
                    vmrcs.setMsgMode(2);
                    vmrcs.setAdvancedConfig("");
                    vmrcs.setThumb("");
                    vmrcs.setAllowSSLErrors(true);
                    vmrcs.setTicket(vmrcMap.get("ticket"));
                    vmrcs.setUser("");
                    vmrcs.setPass("");
                    vmrcs.setVmID(vmrcMap.get("vmID"));
                    vmrcs.setDatacenter("");
                    vmrcs.setVmPath("");
                    vmrcs.setHost(hostName);
                    resultlist = (vmrcs);
                } else {
                    throw new RpcException(RpcException.BIZ_EXCEPTION, "调用adpator失败！");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultlist;
    }

    @Override
    public boolean reVmName(String resVmSid, String newVmName) {
        boolean result = false;
        try {
            ResVm resVmDb = resVmMapper.selectByPrimaryKey(resVmSid);
            ResHost resHost = resHostMapper.selectByPrimaryKey(resVmDb.getAllocateResHostSid());
            ResTopology resVc = resTopologyMapper.selectByPrimaryKey(resHost.getParentTopologySid());
            ResVe resVe = new ResVe();
            if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resVc.getResTopologyType())) {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getResTopologySid());
            } else {
                resVe = this.resVeMapper.selectByPrimaryKey(resVc.getParentTopologySid());
            }

            VmRename vmReName = new VmRename();
            vmReName.setProviderType(resVe.getVirtualPlatformType());
            vmReName.setAuthUrl(resVe.getManagementUrl());
            vmReName.setAuthUser(resVe.getManagementAccount());
            vmReName.setAuthPass(resVe.getManagementPassword());
            vmReName.setVirtEnvType(resVe.getVirtualPlatformType());
            vmReName.setVirtEnvUuid(resVe.getResTopologySid().toString());
            vmReName.setId(resVmSid);
            vmReName.setName(resVmDb.getVmName());
            vmReName.setNameTobe(newVmName);

            VmRenameResult reNameResult = (VmRenameResult) MQHelper.rpc(vmReName);
            if (reNameResult.isSuccess()) {
                resVmDb.setVmName(newVmName);
                WebUtil.prepareUpdateParams(resVmDb);
                this.resVmMapper.updateByPrimaryKeySelective(resVmDb);
                result = true;
            } else {
                result = false;

                logger.error("虚拟机修改名称失败");
            }

        } catch (Exception e) {
            logger.error("虚拟机重新命名异常：" + ExceptionUtils.getFullStackTrace(e));
            result = false;
        }
        return result;
    }

    /**
     * 根据集群获取主机以及主机下的存储
     *
     * @param resVcSid 集群Sid
     * @return 集群中主机列表
     */
    public List<ResHost> getHostAndStorageByVc(String resVcSid) {
        // 主机为空，判断是否指定集群
        Criteria example = new Criteria();
        example.put("parentTopologySid", resVcSid);
        example.put("status", WebConstants.ResHostStatus.NORMAL);
        example.setOrderByClause("A.HOST_NAME");
        List<ResHost> hostList = this.resHostMapper.selectByParams(example);
        if (hostList != null && hostList.size() > 0) {
            // 设置主机下的存储
            for (ResHost resHost1 : hostList) {
                List<ResStorage> resStorages = this.resStorageMapper.selectAllocateStoByHostSid(resHost1.getResHostSid());
                resHost1.setResStorages(resStorages);
            }
        }
        return hostList;
    }

    /**
     * 根据服务层资源集，搜集主机
     *
     * @param resVmInstList
     * @return
     */
    public List<ResHost> getAllResHosts(List<ResVmInst> resVmInstList) {
        List<ResHost> resHosts = new ArrayList<ResHost>();
        if (resVmInstList != null && resVmInstList.size() > 0) {
            for (ResVmInst resVmInst : resVmInstList) {
                // 先判断是否指定主机
                if (resVmInst.getAllocateResHostIds() != null && resVmInst.getAllocateResHostIds().size() > 0) {
                    for (String resHostSid : resVmInst.getAllocateResHostIds()) {
                        // resVm.setAllocateResHostSid(resVmInst.getAllocateResHostId());
                        ResHost resHost = this.resHostMapper.selectByPrimaryKey(resHostSid);

                        if (WebConstants.ResHostStatus.NORMAL.equals(resHost.getStatus())) {

                            // 判断主机集合里是否已经存在该主机
                            boolean isExistHost = this.isExistHostInList(resHosts, resHost);
                            if (!isExistHost) {
                                List<ResStorage> resStorages = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
                                resHost.setResStorages(resStorages);
                                resHosts.add(resHost);
                            }
                        }
                    }

                } else if (resVmInst.getAllocateResVcIds() != null && resVmInst.getAllocateResVcIds().size() > 0) {
                    for (String resVcSid : resVmInst.getAllocateResVcIds()) {
                        List<ResHost> hostList = this.getHostAndStorageByVc(resVcSid);
                        for (ResHost resHost : hostList) {
                            boolean isExistHost = this.isExistHostInList(resHosts, resHost);
                            if (!isExistHost) {
                                List<ResStorage> resStorages = this.resStorageMapper.selectAllocateStoByHostSid(resHost.getResHostSid());
                                resHost.setResStorages(resStorages);
                                resHosts.add(resHost);
                            }
                        }
                    }
                }
            }
        } else {
            logger.error("创建虚拟机资源检查，服务层参数：List<ResVmInst>为空");
        }
        return resHosts;
    }

    /**
     * 判断主机在主机集合是否存在
     *
     * @param resHosts 主机列表
     * @param resHost  检查主机
     * @return 判断结果
     */
    public boolean isExistHostInList(List<ResHost> resHosts, ResHost resHost) {

        boolean flag = false;
        if (resHosts != null && resHosts.size() > 0) {
            for (ResHost resH : resHosts) {
                if (resH.getResHostSid().equals(resHost.getResHostSid())) {
                    flag = true;
                    break;
                }
            }
            return flag;

        } else {
            return false;
        }
    }

    @Override
    public ResVe getVeByRes(Object object) {
        ResVe resVe = null;
        if (object instanceof ResHost) {
            ResHost resHost = (ResHost) object;
            // ResHost resHost = this.resHostService.selectByPrimaryKey(resSid);
            ResTopology resT = this.resTopologyMapper.selectByPrimaryKey(resHost.getParentTopologySid());
            if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(resT.getResTopologyType())) {
                resVe = this.resVeMapper.selectByPrimaryKey(resT.getResTopologySid());
            } else if (WebConstants.RES_TOPOLOGY_TYPE.VC.equals(resT.getResTopologyType())) {
                resVe = this.resVeMapper.selectByPrimaryKey(resT.getParentTopologySid());
            }
        } else if (object instanceof ResStorage) {
            ResStorage resStorage = (ResStorage) object;
            resVe = this.resVeMapper.selectByPrimaryKey(resStorage.getParentTopologySid());
        } else if (object instanceof ResVc) {
            ResVc resVc = (ResVc) object;
            resVe = this.resVeMapper.selectByPrimaryKey(resVc.getParentTopologySid());
        }
        return resVe;
    }

    @Override
    public String getWebConsole(String resVmSid, ServiceBaseInput baseInput) {
        // 通过VM取得Zone
        String zoneId = this.resTopologyMapper.selectZoneByVm(resVmSid);
        // 取得VE
        ResVe resVe = this.resBaseService.getVeFromZone(zoneId);
        String region = this.resBaseService.getRegionFromZone(zoneId);
        VmVncConsole vmVncConsole = new VmVncConsole();
        // Driver基本信息
        this.resBaseService.setAdapterBaseInfo(resVe, baseInput, vmVncConsole);
        vmVncConsole.setRegion(region);
        // 虚拟机UUID设置
        ResVm resVm = resVmMapper.selectByPrimaryKey(resVmSid);
        vmVncConsole.setId(resVm.getUuid());

        Object object;
        try {
            object = MQHelper.rpc(vmVncConsole);
        } catch (MQException e) {
            logger.error("VNC地址取得失败:", e);
            throw new RpcException(RpcException.BIZ_EXCEPTION, "VNC地址取得失败");
        }
        VmVncConsoleResult vmVncConsoleResult = (VmVncConsoleResult) object;
        return vmVncConsoleResult.getUrl();
    }

    private ResVm preparePojoCreateVm(String vmName, JsonNode resVmSpec) {
        ResVm resVm = new ResVm();
        if (Strings.isNullOrEmpty(vmName)) {
            vmName = this.sidService.getMaxSid(WebConstants.SidCategory.VM_NAME);
        }
        // 虚拟机设定CPU和MEM
        resVm.setCpuCores(resVmSpec.findValue("cpu").asInt());
        resVm.setMemorySize(resVmSpec.findValue("memory").asLong() * 1024L);

        resVm.setStatus(WebConstants.ResVmStatus.CREATING);
        resVm.setVmName(vmName);
        List<ResVd> resVdList = Lists.newArrayList();

        // System disk
        int cnt = 1;
        for (JsonNode jsonNode : resVmSpec.findValue("systemDisk")) {
            ResVd resVd = new ResVd();
            resVd.setAllocateDiskSize(jsonNode.get("systemDiskSize").asLong());
            resVd.setStatus(WebConstants.ResVdStatus.CREATING);
            resVd.setStoragePurpose(WebConstants.StoragePurpose.SYSTEM_DISK);
            resVd.setVdName(vmName + (cnt++));
            resVd.setMountPoint(jsonNode.get("systemDiskDevice").getTextValue());
            resVd.setReleaseMode(WebConstants.ReleaseMode.WITH_INSTANCE);
            resVdList.add(resVd);
        }

        // Data Disk
        for (JsonNode jsonNode : resVmSpec.findValue("dataDisk")) {
            ResVd resVd = new ResVd();
            resVd.setAllocateDiskSize(jsonNode.get("dataDiskSize").asLong());
            resVd.setStatus(WebConstants.ResVdStatus.CREATING);
            resVd.setStoragePurpose(WebConstants.StoragePurpose.SYSTEM_DISK);
            resVd.setVdName(vmName + (cnt++));
            resVd.setReleaseMode(WebConstants.ReleaseMode.WITH_INSTANCE);
            resVdList.add(resVd);
        }

        resVm.setResVdList(resVdList);

        return resVm;
    }

    /**
     * 根据服务层的参数，组装资源层ResVm对象
     *
     * @param resVmInst 服务层VM对象
     * @return 资源层VM对象
     */
    public ResVm preparePojoCreateVm(ResVmInst resVmInst) {
        String vmName = null;
        // 判断虚拟机名称是否指定
        if (!Strings.isNullOrEmpty(resVmInst.getResVmInstName())) {
            vmName = resVmInst.getResVmInstName();
        } else {
            String vmNameDb = this.sidService.getMaxSid(WebConstants.SidCategory.VM_NAME);
            // 判断前缀是否指定
            if (resVmInst.getResVmInstNamePrefix() != null) {
                vmName = resVmInst.getResVmInstNamePrefix() + "-" + vmNameDb;
            } else {
                vmName = vmNameDb;
            }
        }
        ResVm resVm = new ResVm();

        // 虚拟机设定CPU和MEM
        resVm.setCpuCores(resVmInst.getCpu());
        resVm.setMemorySize(resVmInst.getMemory());

        // Power虚拟CPU
        // FIXME for power START
        //        resVm.setPowerCpuUsedUnits(Float.parseFloat(PropertiesUtil.getProperty("power.cpu.used.units")));
        //        resVm.setPowerCpuMaxUnits(Integer.parseInt(PropertiesUtil.getProperty("power.cpu.max.units")));
        //        resVm.setPowerCpuMinUnits(Float.parseFloat(PropertiesUtil.getProperty("power.cpu.min.units")));
        //
        //        // 物理CPU
        //        resVm.setCpuCoresMax(
        //                resVmInst.getCpu() * Integer.parseInt(PropertiesUtil.getProperty("cpu.cores.max.times")));
        //        resVm.setCpuCoresMin(Integer.parseInt(PropertiesUtil.getProperty("cpu.cores.min")));
        // FIXME END
        if (!StringUtil.isNullOrEmpty(resVmInst.getPartitionType())) {
            resVm.setParType(Integer.parseInt(resVmInst.getPartitionType()));
            // 内存为规则定义，min=期望值 /2 ，max=期望值 × 4
            resVm.setMemoryMin(new BigDecimal(resVm.getMemorySize()).divideToIntegralValue(new BigDecimal(2))
                                                                    .longValue());
            resVm.setMemoryMax(resVm.getMemorySize() * 4);
            resVm.setParProfile(vmName);
        }

        resVm.setStatus(WebConstants.ResVmStatus.CREATING);
        resVm.setVmName(vmName);
        resVm.setOsName(resVmInst.getVmSystemName());

        // 设置磁盘
        List<ResVd> resVdList = new ArrayList<ResVd>();
        List<ResVdInst> resVdInstList = resVmInst.getDataDisks();

        // 磁盘增量
        int i = 0;
        for (ResVdInst resVdInst : resVdInstList) {
            i++;
            // 插入数据盘磁盘
            ResVd resVd = new ResVd();
            resVd.setAllocateDiskSize(resVdInst.getDiskSize());
            resVd.setResVmSid(resVm.getResVmSid());
            resVd.setStatus(WebConstants.ResVdStatus.CREATING);
            resVd.setStoragePurpose(resVdInst.getStoragePurpose());
            resVd.setLocalHostHbaSid(resVdInst.getSysDiskHBASid());
            if (WebConstants.StoragePurpose.DATA_DISK.equals(resVdInst.getStoragePurpose())) {
                // 判断磁盘名称是否指定
                if (resVdInst.getResVdInstName() != null) {
                    resVd.setVdName(resVdInst.getResVdInstName());
                } else {
                    String vdName = resVm.getVmName() + "_" + i;
                    // String vdName =
                    // this.sidService.getMaxSid(WebConstants.SidCategory.VD_NAME);
                    // 判断前缀是否指定
                    if (resVdInst.getResVdInstNamePrefix() != null) {
                        resVd.setVdName(resVdInst.getResVdInstNamePrefix() + "-" + vdName);
                    } else {
                        resVd.setVdName(vdName);
                    }
                }
                resVd.setFileSystemType(resVdInst.getFileSystem());
                resVd.setMountPoint(resVdInst.getMountPoint());
                resVd.setLogicVolume(resVdInst.getVolumeName());
            } else {
                resVd.setVdName(resVm.getVmName());
            }
            resVdList.add(resVd);
        }
        resVm.setResVdList(resVdList);
        return resVm;
    }

    /**
     * 根据服务层参数，获取所有可用主机
     *
     * @param resVmInst 为创建的主机寻找寄主
     * @return 可用寄主机列表
     */
    public List<ResHost> getResHostCreateVm(ResVmInst resVmInst) {
        List<ResHost> hostList = new ArrayList<ResHost>();
        // 先判断是否指定主机
        if (resVmInst.getAllocateResHostIds() != null && resVmInst.getAllocateResHostIds().size() > 0) {
            for (String resHostSid : resVmInst.getAllocateResHostIds()) {
                ResHost resHost = this.resHostMapper.selectByPrimaryKey(resHostSid);
                if (WebConstants.ResHostStatus.NORMAL.equals(resHost.getStatus())) {
                    hostList.add(resHost);
                }
            }
            // 指定主机之后，设置CPU POOL，只是针对Power主机
            if (hostList.size() == 1) {
                hostList.get(0).setCpuPoolSid(resVmInst.getCpuPoolSid());
            }
        } else {
            // 主机为空，判断是否指定集群
            if (resVmInst.getAllocateResVcIds() != null && resVmInst.getAllocateResVcIds().size() > 0) {
                for (String resVcSid : resVmInst.getAllocateResVcIds()) {
                    List<ResHost> resHostByVc = this.getHostAndStorageByVc(resVcSid);
                    hostList.addAll(resHostByVc);
                }

            } else {
                throw new RpcException(RpcException.BIZ_EXCEPTION, "请选择计算资源。");
            }
        }
        return hostList;
    }


    @Override
    public ResInstResult installSoftware(String resVmSid, List<ResOsSoftware> softwares) throws Exception {

        ResInstResult result = null;

        SoftwareDeploy softwareDeploy = null;

        String target = "";
        //新建任务日志对象
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("软件安装");


        log.setTaskType(WebConstants.TaskType.INSTALL_SOFTWARE);
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);

        try {
            List<String> softwareNames = new ArrayList<String>();
            for (ResOsSoftware software : softwares) {
                String softwareName = this.codeService.getSoftwareName(software.getSoftwareVersion());
                if (StringUtils.isNotBlank(softwareName)) {
                    software.setSoftwareVersionName(softwareName);
                    softwareNames.add(softwareName);
                }
                software.setResSid(resVmSid);
                software.setStatus(WebConstants.OsSoftwareStatus.PROCESSING);
                int resultNum = this.resOsSoftwareMapper.updateByPrimaryKey(software);
                if (resultNum == 0) {
                    this.resOsSoftwareMapper.insertSelective(software);
                }
            }
            target = StringUtils.join(softwareNames, ";");

            ResVm resVm = this.getVmInfo(resVmSid);
            softwareDeploy = new SoftwareDeploy();
            String allocHostId = resVm.getAllocateResHostSid();
            ResHost resHost = this.resHostMapper.selectByPrimaryKey(allocHostId);
            ResVe allcateResVe = this.getVeByRes(resHost);
            softwareDeploy.setProviderType(allcateResVe.getVirtualPlatformType());
            softwareDeploy.setAuthUrl(allcateResVe.getManagementUrl());
            softwareDeploy.setAuthUser(allcateResVe.getManagementAccount());
            softwareDeploy.setAuthPass(allcateResVe.getManagementPassword());
            softwareDeploy.setVirtEnvType(allcateResVe.getVirtualPlatformType());
            softwareDeploy.setVirtEnvUuid(allcateResVe.getResTopologySid().toString());

            softwareDeploy.setVmId(resVmSid);
            softwareDeploy.setOsVersion(resVm.getOsVersion());
            List<ResIp> ips = resVm.getResIpList();
            if (ips != null && ips.size() > 0) {
                softwareDeploy.setTargetSrvIp(ips.get(0).getIpAddress());
            }
            softwareDeploy.setTargetUsrName(resVm.getManagementAccount());
            softwareDeploy.setTargetUsrPasswd(resVm.getManagementPassword());
            //设置操作系统类型
            String osType = null;
            String osVersion = resVm.getOsVersion();
            Code code = this.codeService.getCodeByValue(osVersion, WebConstants.CodeCategroy.OS_VERSION);
            if (code != null) {
                osType = code.getParentCodeValue();
            }
            if (osType == null) {
                throw new ServiceException("无法获取虚拟机软件版本");
            }
            softwareDeploy.setOsType(code.getParentCodeValue());
            List<Software> deploySoftwares = new ArrayList<Software>();
            for (ResOsSoftware software : softwares) {
                Software deploySoftware = new Software();
                String softwareVersion = software.getSoftwareVersion();
                Criteria criteria = new Criteria();
                criteria.put("softwareVersion", softwareVersion);
                criteria.put("osType", osType);
                criteria.put("osVersion", osVersion);
                List<ResSoftware> resSoftwareList = this.resSoftwareMapper.selectByParams(criteria);
                ResSoftware resSoftware = null;
                if (resSoftwareList.size() > 0) {
                    resSoftware = resSoftwareList.get(0);
                    deploySoftware.setMediaLib(resSoftware.getMediaLibAddress());
                    deploySoftware.setMediaPath(resSoftware.getMediaPath());
                    deploySoftware.setScriptLib(resSoftware.getScriptLibAddress());
                    deploySoftware.setScriptPath(resSoftware.getScriptPath());
                    deploySoftware.setType(resSoftware.getSoftwareType());
                    deploySoftware.setOsVersion(resSoftware.getDeployOsVersion());
                }
                //				Code softwareCode = codeMapper.getCodeByValue(softwareVersion, WebConstants.CodeCategroy.SOFTWARE_VERSION);
                //				if(softwareCode != null) {
                //					deploySoftware.setType(softwareCode.getParentCodeValue());
                //				}
                if (resSoftware != null) {
                    criteria = new Criteria();
                    criteria.put("resSortwareSid", resSoftware.getResSortwareSid());
                    List<ResSoftwareConfig> resSoftwareConfigs = this.resSoftwareConfigMapper.selectByParams(criteria);
                    if (resSoftwareConfigs.size() > 0) {
                        Map<String, String> softwareConfigMap = new HashMap<String, String>();
                        for (ResSoftwareConfig resSoftwareConfig : resSoftwareConfigs) {
                            softwareConfigMap.put(resSoftwareConfig.getName(), resSoftwareConfig.getValue());
                        }
                        deploySoftware.setSoftWareConfig(softwareConfigMap);
                    }
                }
                deploySoftware.setVersion(softwareVersion);
                deploySoftware.setId(software.getResSoftwareSid());
                deploySoftware.setName(software.getSoftwareVersionName());
                deploySoftwares.add(deploySoftware);
            }
            softwareDeploy.setSoftwares(deploySoftwares);
        } catch (Exception e) {
            logger.error("组装软件安装参数异常", e);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
            throw e;
        }

        //资源挑选完毕，调用MQ时，插入任务
        log.setTaskTarget(target);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();

        if (softwareDeploy != null) {
            try {
                logger.info("输入MQ参数：" + JsonUtil.toJson(softwareDeploy));
                MQHelper.sendMessage(softwareDeploy);
                ResVm resVM = this.getVmInfo(resVmSid);
                result = new ResInstResult(ResInstResult.SUCCESS, "", resVM);
            } catch (Exception e) {
                log = new TaskLog();
                log.setTaskLogSid(taskId);
                log.setTaskFailureReason("安装软件异常：" + e.getMessage());
                taskLogger.fail(log);
                result = new ResInstResult(ResInstResult.FAILURE, "安装软件异常：" + e.getMessage());
            }
        }

        return result;
    }


    @Override
    public int insertPveVmSelective(ResVm record) {
        int result1 = this.resVmMapper.insertSelective(record);
        if (1 == result1) {
            // 查询主机是否是vios
            ResHost host = this.resHostMapper.selectByPrimaryKey(record.getAllocateResHostSid());
            // 插入cpu_pool_mpar_rel
            ResCpuPoolMparRel cpuPoolRel = new ResCpuPoolMparRel();
            cpuPoolRel.setResCpuPoolSid(record.getResCpuPoolSid());
            cpuPoolRel.setResParSid(record.getResVmSid());
            if (WebConstants.IsViosFlag.YES.equals(host.getIsViosFlag())) {
                cpuPoolRel.setResParType(1);
            }
            cpuPoolRel.setCpuValue(record.getPowerCpuUsedUnits());
            this.resCpuPoolMparRelMapper.insertSelective(cpuPoolRel);
            // 插入res_vm_network
            ResVmNetwork vmNetwork = new ResVmNetwork();
            vmNetwork.setResVmSid(record.getResVmSid());
            vmNetwork.setIpAddress(record.getVmIp());
            vmNetwork.setStatus(record.getStatus());
            this.resVmNetworkMapper.insertSelective(vmNetwork);

            return 1;
        } else {
            return 0;
        }

    }

}