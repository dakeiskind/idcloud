package com.h3c.idcloud.core.service.res.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.pojo.scan.TemplateScan;
import com.h3c.idcloud.core.persist.res.dao.ResImageMapper;
import com.h3c.idcloud.core.persist.res.dao.ResSoftwareMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVeMapper;
import com.h3c.idcloud.core.persist.system.dao.CodeMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResImage;
import com.h3c.idcloud.core.pojo.dto.res.ResImageSoftWare;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.system.Code;
import com.h3c.idcloud.core.pojo.dto.system.TaskLog;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.service.res.api.ResImageService;
import com.h3c.idcloud.infra.log.LoggerTypeEnum;
import com.h3c.idcloud.infra.log.task.TaskLogger;
import com.h3c.idcloud.infra.log.task.TaskLoggerFactory;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RESTHttpResponse;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import com.h3c.idcloud.infrastructure.common.util.AuthUtil;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.KMPUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.RSETClientUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
@Service(version = "1.0.0")
@Component
public class ResImageServiceImpl implements ResImageService {

    private static final Logger logger = LoggerFactory.getLogger(ResImageServiceImpl.class);

    Map<String, Integer> base;
    @Autowired
    private ResImageMapper resImageMapper;
    @Autowired
    private ResVeMapper resveMapper;
    @Autowired
    private ResTopologyMapper resTopologyMapper;
    @Autowired
    private TaskLoggerFactory taskLogger;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private ResSoftwareMapper resSoftwareMapper;

    /**
     * 匹配到所有模板中存在的软件版本后，进行最优模板的排序(软件数量越多，模板最优)，从大到小
     *
     * @param map
     *
     * @return
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            int compare = map.get(k2).compareTo(map.get(k1));
            if (compare == 0) { return 1; } else { return compare; }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public static ArrayList<Entry<String, Integer>> sortMap(Map map) {
        List<Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, (obj1, obj2) -> obj1.getValue() - obj2.getValue());
        return (ArrayList<Entry<String, Integer>>) entries;
    }

    /**
     * 查询镜像
     *
     * @param example
     * @return
     */
    @Override
    public List<ResImage> selectByParams(Criteria example) {
        return this.resImageMapper.selectByParams(example);
    }

    @Override
    public ResInstResult getAllByImage(ResVe resves) {
        // 插入任务表
        ResTopology rt = this.resTopologyMapper.selectByPrimaryKey(resves.getResTopologySid());
        ResVe resve = this.resveMapper.selectByPrimaryKey(resves.getResTopologySid());
        TaskLog log = new TaskLog();
        User user = AuthUtil.getAuthUser();
        if (user != null) {
            log.setAccount(AuthUtil.getAuthUser().getAccount());
        } else {
            log.setAccount("admin");
        }
        log.setTaskDetail("扫描镜像环境");
        log.setTaskType(WebConstants.TaskType.CREATE_IMAGE);
        log.setTaskTarget(rt.getResTopologyName());
        TaskLogger taskLogger = this.taskLogger.getLogger(LoggerTypeEnum.ALL);
        TaskLog taskLog = taskLogger.start(log);
        Long taskId = taskLog.getTaskLogSid();
        ResInstResult result = new ResInstResult();
        try {
            TemplateScan templateScan = new TemplateScan();
            templateScan.setProviderType(resve.getVirtualPlatformType());
            //			templateScan.setTenantName("demo");
            templateScan.setAuthUrl(resve.getManagementUrl());
            templateScan.setAuthUser(resve.getManagementAccount());
            templateScan.setAuthPass(resve.getManagementPassword());
            templateScan.setVirtEnvType(resve.getVirtualPlatformType());
            templateScan.setVirtEnvUuid(resve.getResTopologySid());
            if ("HMC".equals(resve.getVirtualPlatformType())) {
                templateScan.setNimIp(resves.getNimIp());
                templateScan.setNimUser(resves.getNimUser());
                templateScan.setNimPassword(resves.getNimPassword());
            }

            logger.info("输入MQ参数：" + JsonUtil.toJson(templateScan));
            String msgId = MQHelper.sendMessage(templateScan);
            result = new ResInstResult(ResInstResult.SUCCESS, "", null);
        } catch (Exception e) {
            log = new TaskLog();
            log.setTaskLogSid(taskId);
            log.setTaskFailureReason("同步镜像环境异常：" + e.getMessage());
            taskLogger.fail(log);
            result = new ResInstResult(ResInstResult.FAILURE, e.getMessage());
        }
        return result;
    }

    public boolean testImage(ResVe resve) throws Exception {
        String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
        String url = adapterUrl + "/vm/getTemplate";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("providerUrl", resve.getManagementUrl());
        map.put("authUser", resve.getManagementAccount());
        map.put("authPass", resve.getManagementPassword());
        String json = JsonUtil.toJson(map);
        RESTHttpResponse result = RSETClientUtil.post(url, json);
        return RESTHttpResponse.SUCCESS.equals(result.getStatus());
    }

    /**
     * 根据拓扑类型，查询镜像
     */
    @Override
    public List<ResImage> getResImagesByTopology(String topoloyType, String[] topoloyIds, String imageType) {
        String json;
        String ids = "";
        for (String topoloyId : topoloyIds) {
            ids += topoloyId + ",";
        }
        ids = ids.substring(0, ids.length() - 1);
        // 取得当前用户
        //		User user = AuthUtil.getAuthUser();
        //		ResTopology parentResT= resTopologyMapper.selectByPrimaryKey(resTopology.getResTopologySid());
        List<ResImage> resImageList = new ArrayList<ResImage>();
        List<ResTopology> resVeList = new ArrayList<ResTopology>();
        if (WebConstants.RES_TOPOLOGY_TYPE.RZ.equals(topoloyType)) {
            //查询虚拟化集群
            Criteria criteria = new Criteria();
            criteria.put("resZoneSids", ids);
            resVeList = this.resTopologyMapper.selectVeByZone(criteria);
        } else if (WebConstants.RES_TOPOLOGY_TYPE.VE.equals(topoloyType)) {
            for (String topoloyId : topoloyIds) {
                ResTopology resVe = new ResTopology();
                resVe.setResTopologySid(topoloyId);
                resVeList.add(resVe);
            }
        } else {
            //查询虚拟化环境
            Criteria criteria = new Criteria();
            criteria.put("parentTopologySids", ids);
            criteria.put("resTopologyType", WebConstants.RES_TOPOLOGY_TYPE.VE);
            resVeList = this.resTopologyMapper.selectByParams(criteria);
        }

        if (resVeList != null && resVeList.size() > 0) {
            //查询用户私有镜像
            for (ResTopology resVe : resVeList) {
                Criteria criteria2 = new Criteria();
                criteria2.put("resVeSid", resVe.getResTopologySid());
                //				criteria2.put("createdBy", user.getAccount());
                criteria2.put("imageType", imageType);
                criteria2.put("status", WebConstants.ImageStatus.AVAILABILITY);
                criteria2.setOrderByClause("A.IMAGE_NAME");
                List<ResImage> resImageByVe = this.resImageMapper.selectByParams(criteria2);
                resImageList.addAll(resImageByVe);
            }
        }
        //查询公有镜像
        //		Criteria criteria3=new Criteria();
        //		criteria3.put("createdBy", "admin");
        //		criteria3.put("imageType", WebConstants.ImageType.PUBLIC);
        //		criteria3.put("status", WebConstants.ImageStatus.AVAILABILITY);
        //		List<ResImage> resPublicImage=this.resImageService.selectByParams(criteria3);
        //		resImageList.addAll(resPublicImage);
        return resImageList;
    }

    /**
     * 组装共通的参数
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

    @Override
    public ResImageSoftWare getImageBySoftWare(ResImageSoftWare resImageSoftWare) {
        String installedSoftware = "";
        String installedSoftwareKey = "";
        Map<String, String> mapInstalledSoftware = new HashMap<String, String>();
        Map<String, String> mapUnInstalledSoftware = new HashMap<String, String>();
        Map<String, Integer> mapInstalledSoftwareNum = new HashMap<String, Integer>();
        Map<String, Integer> mapInstalledSoftwareSize = new HashMap<String, Integer>();
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        List<ResSoftware> resSoftwarelist = new ArrayList<ResSoftware>();
        try {
            Criteria criteria = new Criteria();
            criteria.put("resTopologyNameVE", resImageSoftWare.getResVeSid());
            criteria.put("osType", resImageSoftWare.getOsType());
            criteria.put("osVersion", resImageSoftWare.getOsVersion());
            criteria.put("status", WebConstants.ImageStatus.AVAILABILITY);
            List<ResImage> imageList = this.resImageMapper.selectByParams(criteria);
            //根据前台选择的中间件和数据库，与对应已经发布模板的数据库进行匹配
            //1.如果用户所选的软件都存在，择匹配软件最多的模板
            List<ResOsSoftware> instSoftwares = resImageSoftWare.getSoftwares();
            List<String> softwareVersion = new ArrayList<String>();
            if (instSoftwares != null) {
                for (ResOsSoftware software : instSoftwares) {
                    softwareVersion.add(software.getSoftwareVersion());
                }
            }
            int nullcount = 0;
            for (String aSoftwareVersion : softwareVersion) {
                if (Strings.isNullOrEmpty(aSoftwareVersion)) {
                    nullcount++;
                }
            }
            if (nullcount > 0) {
                for (ResImage anImageList : imageList) {
                    String software = anImageList.getInstalledSoftware();
                    if (Strings.isNullOrEmpty(software)) {
                        countMap.put(anImageList.getResImageSid(), 0);
                    } else {
                        String[] softwareSplit = software.split(",");
                        countMap.put(anImageList.getResImageSid(), softwareSplit.length);
                    }
                }
                ArrayList<Entry<String, Integer>> entries = sortMap(countMap);
                String resImageSid = entries.get(0).getKey();
                ResImage imageName = this.resImageMapper.selectByPrimaryKey(resImageSid);
                resImageSoftWare.setImageName(imageName.getImageName());
                resImageSoftWare.setImageSid(resImageSid);
                resImageSoftWare.setImageUUID(imageName.getUuid());
                resImageSoftWare.setIncludeSoftwareVersion("");
                resImageSoftWare.setWithoutSoftwareVersion("");
            } else {
                String softVersion = Joiner.on(",").join(softwareVersion);
                for (ResImage anImageList : imageList) {
                    int countSoft = 0;
                    String includeSoftwareVersion = "";
                    String withoutSoftwareVersion = "";
                    installedSoftware = anImageList.getInstalledSoftware();
                    installedSoftwareKey = anImageList.getResImageSid();
                    mapInstalledSoftware.put(installedSoftwareKey, installedSoftware);
                    if (Strings.isNullOrEmpty(softVersion)) {
                        if (Strings.isNullOrEmpty(installedSoftware)) {
                            List<String> strArrayVersion = Splitter.on(',').splitToList(softVersion);
                            for (String aStrArrayVersion : strArrayVersion) {
                                KMPUtil ktest = new KMPUtil(installedSoftware, aStrArrayVersion);
                                int theLoc = ktest.getIndexOfStr();
                                //判断模板中是否包含软件,匹配最多的
                                if (theLoc != -1) {
                                    includeSoftwareVersion = includeSoftwareVersion + aStrArrayVersion + ",";
                                    countSoft++;
                                    mapInstalledSoftware.put(installedSoftwareKey, includeSoftwareVersion);//包含的软件
                                } else {
                                    withoutSoftwareVersion = withoutSoftwareVersion + aStrArrayVersion + ",";
                                    mapUnInstalledSoftware.put(installedSoftwareKey, withoutSoftwareVersion);//不包含的软件
                                }
                            }
                        }
                    }
                    mapInstalledSoftwareNum.put(installedSoftwareKey, countSoft);
                }
                int value = 0;
                Set<Entry<String, Integer>> set2 = mapInstalledSoftwareNum.entrySet();
                for (Iterator<Entry<String, Integer>> iterator = set2.iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, Integer> entry = iterator.next();
                    value = value + entry.getValue();
                }
                //根据匹配度选择模板
                String imageSid = "";
                ok:
                if (value > 0) {
                    //如果匹配度不为0，查找软件最多的版本
                    Map<String, Integer> sortedMap2 = sortByValues(mapInstalledSoftwareNum);
                    //Set<String> keySet = sorted_map2.keySet();
                    int i = sortedMap2.size() - 1;
                    Map<Integer, Integer> mapKey = new HashMap<Integer, Integer>();
                    Map<Integer, String> mapValue = new HashMap<Integer, String>();
                    for (java.util.Map.Entry<String, Integer> entry : sortedMap2.entrySet()) {
                        //将原来MAP的VALUE放入新的MAP的VALUE里面
                        mapKey.put(i, entry.getValue());
                        //将原来MAP的KEY放入新的MAP的VALUE 里面
                        mapValue.put(i, entry.getKey());
                        i--;
                    }
                    imageSid = mapValue.get(sortedMap2.size() - 1);
                    resImageSoftWare.setImageSid(imageSid);
                    ResImage imageName = this.resImageMapper.selectByPrimaryKey(imageSid);
                    resImageSoftWare.setImageName(imageName.getImageName());
                    String unsoftVersion = mapUnInstalledSoftware.get(imageSid);
                    //					 String []unversionsplit = unsoftVersion.split(",");
                    String[] unversionsplit = null;
                    if (null != unsoftVersion) {
                        unsoftVersion = unsoftVersion.substring(0, unsoftVersion.length() - 1);
                        resImageSoftWare.setWithoutSoftwareVersion(unsoftVersion);
                        unversionsplit = unsoftVersion.split(",");
                    }
                    String softVesion = mapInstalledSoftware.get(imageSid);
                    softVesion = softVesion.substring(0, softVesion.length() - 1);
                    String[] versionSplit = softVesion.split(",");
                    resImageSoftWare.setIncludeSoftwareVersion(softVesion);
                    resImageSoftWare.setImageUUID(imageName.getUuid());
                    //已安装
                    criteria = new Criteria();
                    for (String aVersionSplit : versionSplit) {
                        for (ResOsSoftware instSoftware : instSoftwares) {
                            if (aVersionSplit.equals(instSoftware.getSoftwareVersion())) {
                                //软件分类
                                criteria.put("codeValue", aVersionSplit);
                                List<Code> codelist = this.codeMapper.selectByParams(criteria);
                                criteria.put("codeValue", codelist.get(0).getParentCodeValue());
                                List<Code> softwareTypelist = this.codeMapper.selectByParams(criteria);
                                instSoftware.setSoftwareType(softwareTypelist.get(0).getCodeValue());
                                instSoftware.setSoftwareTypeName(softwareTypelist.get(0).getCodeDisplay());
                                instSoftware.setSoftwareVersionName(codelist.get(0).getCodeDisplay());
                                if (softVesion.contains(aVersionSplit)) {
                                    instSoftware.setStatus(WebConstants.OsSoftwareStatus.INSTALLED);
                                } else {
                                    instSoftware.setStatus(WebConstants.OsSoftwareStatus.WAITING);
                                }

                                //安装介质
                                criteria = new Criteria();
                                criteria.put("softwareVersion", aVersionSplit);
                                criteria.put("osVersion", resImageSoftWare.getOsVersion());
                                criteria.put("osType", resImageSoftWare.getOsType());
                                List<ResSoftware> softwarreList = this.resSoftwareMapper.selectByParams(criteria);
                                if (softwarreList.size() > 0) {
                                    instSoftware.setCanAutoDeploy(true);
                                    instSoftware.setInstallPath(softwarreList.get(0).getInstallPath());
                                    //									 sss.get(a).setInstallPath(softwarreList.get(0).getInstallPath());
                                    //									 sss.get(a).setInstallUserGroup(softwarreList.get(0).getInstallUserGroup());
                                    //									 sss.get(a).setInstallUser(softwarreList.get(0).getInstallUser());
                                } else {
                                    instSoftware.setCanAutoDeploy(false);
                                }
                                criteria = new Criteria();
                                criteria.put("codeValue", aVersionSplit);
                                List<Code> codelists = this.codeMapper.findParentCodeByCodeVaule(criteria);
                                instSoftware.setSoftwareCategory(codelists.get(0).getCodeValue());
                                instSoftware.setSoftwareCategoryName(codelists.get(0).getCodeDisplay());
                            }
                        }
                    }
                    if (null != unsoftVersion && unversionsplit.length > 0) {
                        for (String anUnversionsplit : unversionsplit) {
                            for (ResOsSoftware instSoftware : instSoftwares) {
                                if (anUnversionsplit.equals(instSoftware.getSoftwareVersion())) {
                                    //软件分类
                                    criteria.put("codeValue", anUnversionsplit);
                                    List<Code> codelist = this.codeMapper.selectByParams(criteria);
                                    criteria.put("codeValue", codelist.get(0).getParentCodeValue());
                                    List<Code> softwareTypelist = this.codeMapper.selectByParams(criteria);
                                    instSoftware.setSoftwareType(softwareTypelist.get(0).getCodeValue());
                                    instSoftware.setSoftwareTypeName(softwareTypelist.get(0).getCodeDisplay());
                                    instSoftware.setSoftwareVersionName(codelist.get(0).getCodeDisplay());
                                    if (unsoftVersion.contains(anUnversionsplit)) {
                                        instSoftware.setStatus(WebConstants.OsSoftwareStatus.WAITING);
                                    } else {
                                        instSoftware.setStatus(WebConstants.OsSoftwareStatus.INSTALLED);
                                    }

                                    //安装介质
                                    criteria = new Criteria();
                                    criteria.put("softwareVersion", anUnversionsplit);
                                    criteria.put("osVersion", resImageSoftWare.getOsVersion());
                                    criteria.put("osType", resImageSoftWare.getOsType());
                                    List<ResSoftware> softwarreList = this.resSoftwareMapper.selectByParams(criteria);
                                    if (softwarreList.size() > 0) {
                                        instSoftware.setCanAutoDeploy(true);
                                        instSoftware.setInstallPath(softwarreList.get(0).getInstallPath());
                                        //										 sss.get(a).setInstallPath(softwarreList.get(0).getInstallPath());
                                        //										 sss.get(a).setInstallUserGroup(softwarreList.get(0).getInstallUserGroup());
                                        //										 sss.get(a).setInstallUser(softwarreList.get(0).getInstallUser());
                                    } else {
                                        instSoftware.setCanAutoDeploy(false);
                                    }
                                    criteria = new Criteria();
                                    criteria.put("codeValue", anUnversionsplit);
                                    List<Code> codelists = this.codeMapper.findParentCodeByCodeVaule(criteria);
                                    instSoftware.setSoftwareCategory(codelists.get(0).getCodeValue());
                                    instSoftware.setSoftwareCategoryName(codelists.get(0).getCodeDisplay());
                                }
                            }
                        }
                    }

                } else if (value == 0) {
                    //如果匹配为0。则随机寻找一个同资源环境下同系统类型同系统版本的无中间件和软件的模板
                    for (ResImage anImageList : imageList) {
                        if ("".equals(anImageList.getInstalledSoftware()) ||
                                null == anImageList.getInstalledSoftware()) {
                            //System.out.println(imageList.get(i).getResImageSid());
                            imageSid = anImageList.getResImageSid();
                            resImageSoftWare.setImageSid(imageSid);
                            ResImage imageName = this.resImageMapper.selectByPrimaryKey(imageSid);
                            resImageSoftWare.setImageName(imageName.getImageName());
                            resImageSoftWare.setWithoutSoftwareVersion(StringUtils.join(softwareVersion, ","));
                            resImageSoftWare.setIncludeSoftwareVersion("");
                            resImageSoftWare.setImageUUID(imageName.getUuid());
                            String[] unversionsplit = StringUtils.join(softwareVersion, ",").split(",");
                            for (String anUnversionsplit : unversionsplit) {
                                for (ResOsSoftware instSoftware : instSoftwares) {
                                    if (anUnversionsplit.equals(instSoftware.getSoftwareVersion())) {
                                        criteria.put("codeValue", anUnversionsplit);
                                        List<Code> codelist = this.codeMapper.selectByParams(criteria);
                                        criteria.put("codeValue", codelist.get(0).getParentCodeValue());
                                        List<Code> softwareTypelist = this.codeMapper.selectByParams(criteria);
                                        instSoftware.setSoftwareType(softwareTypelist.get(0).getCodeValue());
                                        instSoftware.setSoftwareTypeName(softwareTypelist.get(0).getCodeDisplay());
                                        instSoftware.setSoftwareVersionName(codelist.get(0).getCodeDisplay());
                                        instSoftware.setStatus(WebConstants.OsSoftwareStatus.WAITING);
                                        criteria = new Criteria();
                                        criteria.put("softwareVersion", anUnversionsplit);
                                        criteria.put("osVersion", resImageSoftWare.getOsVersion());
                                        criteria.put("osType", resImageSoftWare.getOsType());
                                        List<ResSoftware> softwarreList = this.resSoftwareMapper.selectByParams(criteria);
                                        if (softwarreList.size() > 0) {
                                            instSoftware.setCanAutoDeploy(true);
                                            instSoftware.setInstallPath(softwarreList.get(0).getInstallPath());
                                            //											 sss.get(a).setInstallPath(softwarreList.get(0).getInstallPath());
                                            //											 sss.get(a).setInstallUserGroup(softwarreList.get(0).getInstallUserGroup());
                                            //											 sss.get(a).setInstallUser(softwarreList.get(0).getInstallUser());
                                        } else {
                                            instSoftware.setCanAutoDeploy(false);
                                        }
                                        criteria = new Criteria();
                                        criteria.put("codeValue", anUnversionsplit);
                                        List<Code> codelists = this.codeMapper.findParentCodeByCodeVaule(criteria);
                                        instSoftware.setSoftwareCategory(codelists.get(0).getCodeValue());
                                        instSoftware.setSoftwareCategoryName(codelists.get(0).getCodeDisplay());
                                    }
                                }
                            }
                            break ok;
                        } else if (!"".equals(anImageList.getInstalledSoftware()) ||
                                null != anImageList.getInstalledSoftware()) {
                            String installedSoft = anImageList.getInstalledSoftware();
                            String[] installedSoftSize = installedSoft.split(",");
                            mapInstalledSoftwareSize.put(anImageList.getResImageSid(), installedSoftSize.length);
                        }
                    }
                    ArrayList<Entry<String, Integer>> entries = sortMap(mapInstalledSoftwareSize);
                    imageSid = entries.get(0).getKey();
                    ResImage imageName = this.resImageMapper.selectByPrimaryKey(imageSid);
                    resImageSoftWare.setImageName(imageName.getImageName());
                    resImageSoftWare.setWithoutSoftwareVersion(StringUtils.join(softwareVersion, ","));
                    resImageSoftWare.setIncludeSoftwareVersion("");
                    resImageSoftWare.setImageUUID(imageName.getUuid());
                    String[] unversionsplit = StringUtils.join(softwareVersion, ",").split(",");
                    for (String anUnversionsplit : unversionsplit) {
                        for (ResOsSoftware instSoftware : instSoftwares) {
                            if (anUnversionsplit.equals(instSoftware.getSoftwareVersion())) {
                                criteria.put("codeValue", anUnversionsplit);
                                List<Code> codelist = this.codeMapper.selectByParams(criteria);
                                criteria.put("codeValue", codelist.get(0).getParentCodeValue());
                                List<Code> softwareTypelist = this.codeMapper.selectByParams(criteria);
                                instSoftware.setSoftwareType(softwareTypelist.get(0).getCodeValue());
                                instSoftware.setSoftwareTypeName(softwareTypelist.get(0).getCodeDisplay());
                                instSoftware.setSoftwareVersionName(codelist.get(0).getCodeDisplay());
                                instSoftware.setStatus(WebConstants.OsSoftwareStatus.WAITING);
                                criteria = new Criteria();
                                criteria.put("softwareVersion", anUnversionsplit);
                                criteria.put("osVersion", resImageSoftWare.getOsVersion());
                                criteria.put("osType", resImageSoftWare.getOsType());
                                List<ResSoftware> softwarreList = this.resSoftwareMapper.selectByParams(criteria);
                                if (softwarreList.size() > 0) {
                                    instSoftware.setCanAutoDeploy(true);
                                    instSoftware.setInstallPath(softwarreList.get(0).getInstallPath());
                                    //									 sss.get(a).setInstallPath(softwarreList.get(0).getInstallPath());
                                    //									 sss.get(a).setInstallUserGroup(softwarreList.get(0).getInstallUserGroup());
                                    //									 sss.get(a).setInstallUser(softwarreList.get(0).getInstallUser());
                                } else {
                                    instSoftware.setCanAutoDeploy(false);
                                }
                                criteria = new Criteria();
                                criteria.put("codeValue", anUnversionsplit);
                                List<Code> codelists = this.codeMapper.findParentCodeByCodeVaule(criteria);
                                instSoftware.setSoftwareCategory(codelists.get(0).getCodeValue());
                                instSoftware.setSoftwareCategoryName(codelists.get(0).getCodeDisplay());
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resImageSoftWare;
    }

    /**
     * 比较value
     *
     * @param a
     * @param b
     *
     * @return
     */
    public int compare(Long a, Long b) {
        if (base.get(a).doubleValue() >= base.get(b).doubleValue()) {
            return -1;
        } else {
            return 1;
        }
    }


}