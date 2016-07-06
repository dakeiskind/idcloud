package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResOsSoftware;
import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.core.pojo.dto.res.ResVe;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResWebConsole;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;
import com.h3c.idcloud.core.pojo.instance.ResNetworkInst;
import com.h3c.idcloud.core.pojo.instance.ResVmCheck;
import com.h3c.idcloud.core.pojo.instance.ResVmInst;
import com.h3c.idcloud.core.pojo.instance.ResVmOptInst;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.activiti.engine.impl.util.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 接口 虚拟机操作服务.
 *
 * @author Chaohong.Mao
 */
public interface ResVmService {

    /**
     * Select by primary key res vm.
     *
     * @param resVmSid the res vm sid
     * @return the res vm
     */
    ResVm selectByPrimaryKey(String resVmSid);

    /**
     * 统计数据条数
     *
     * @param example the example
     * @return int int
     */
    int countByParams(Criteria example);

    /**
     * 查询虚拟机列表
     *
     * @param example the example
     * @return list list
     */
    List<ResVm> selectByParams(Criteria example);

    /**
     * 查询虚拟机列表
     *
     * @param params params
     * @return list list
     */
    List<ResVm> selectBaseInfoByParams(Map<String, String> params);

    /**
     * 获取虚拟机信息
     *
     * @param resVmSid the res vm sid
     * @return vm info
     */
    ResVm getVmInfo(String resVmSid);

    /**
     * 调用南向接口查询当然虚拟机信息
     *
     * @param resVm 虚机
     * @return 查询虚机信息 vm current info
     *
     * @throws Exception the exception
     */
    ResVm getVmCurrentInfo(ResVm resVm) throws Exception;

    /**
     * 创建虚拟机
     *
     * @param resVmInst 虚拟机实例pojo
     * @return ResInstResult 资源层共通返回结果
     *
     * @throws Exception the exception
     * @implSpec json : <pre> {     "region": "10",     "zone": "12e0c825-3ff5-11e5-8c09-005056ba3c46",     "password": "",     "hostname": "test",     "instance": [{          "instanceCategory": "idc-S",          "cpu": "1",          "memory": "1"     }],     "systemDisk": [{          "systemDiskCategory": "cloud_efficiency",          "systemDiskSize": "40",          "systemDiskDevice": "/dev/xvda"     }],     "dataDisk": [{          "dataDiskCategory": "cloud_ssd",          "dataDiskSize": "100",          "dataDiskSnapshot": "",          "dataDiskDevice": "",          "dataDiskDeletewithinstance": "true",          "dataDiskInstanceId": null     }, {          "dataDiskCategory": "cloud_efficiency",          "dataDiskSize": "150",          "dataDiskSnapshot": "",          "dataDiskDevice": "",          "dataDiskDeletewithinstance": "true",          "dataDiskInstanceId": null     }],     "networkType": "vpc",     "networks": ["e581e6fa-efe3-11e5-9f4e-005056a50931"],     "bandwidth": "0",     "os": {          "imageType": "public",          "imageId": "605cdc9a-585c-11e5-8dea-005056a5742a"     },     "keyPair": "ccd98605-77ba-11e5-b6e5-005056a52fbf",     "securityGroup": "ccd98605-77ba-11e5-b6e5-005056a52fbf" } </pre>
     */
    ResInstResult createVm(ResCommonInst resVmInst);

    /**
     * 为虚拟机安装软件
     *
     * @param resVmSid  虚机sid
     * @param softwares 软件列表
     * @return ResInstResult 资源层共通返回结果
     *
     * @throws Exception the exception
     */
    ResInstResult installSoftware(String resVmSid, List<ResOsSoftware> softwares) throws Exception;

    /**
     * 操作虚拟机
     *
     * @param resVmSid   虚拟机Sid
     * @param baseInput  the base input
     * @param action     操作名称
     * @param rebootType 重启类型
     * @return 资源层共通返回结果 res inst result
     */
    ResInstResult operateVm(String resVmSid, ServiceBaseInput baseInput, String action, String rebootType);

    /**
     * 调整虚拟机（CPU,MEMORY）
     *
     * @param resVmSid  虚拟机Sid
     * @param mgtObjSid 管理对象Sid
     * @param cpu       cpu个数
     * @param memory    memory大小
     * @return ResInstResult 资源层共通返回结果
     */
    ResInstResult reconfigVm(String resVmSid, long mgtObjSid, int cpu, long memory);

    /**
     * 删除虚拟机
     *
     * @param resCommonInst the res common inst
     * @return ResInstResult 资源层共通返回结果
     */
    ResInstResult deleteVm(ResCommonInst resCommonInst);

    /**
     * 处理工单后，删除虚拟机
     *
     * @param resVmSid 虚拟机Sid
     * @return ResInstResult 操作结果
     */
    boolean deleteVmAfterDeal(String resVmSid);

    /**
     * 添加虚拟机网络
     *
     * @param resVmSid  虚拟机Sid
     * @param vmNetList 添加的网络集合
     * @return ResInstResult 资源层共通返回结果
     */
    ResInstResult addNetsForVm(String resVmSid, List<ResNetworkInst> vmNetList);

    /**
     * 删除虚拟机网络
     *
     * @param resVmSid  虚拟机Sid
     * @param vmNetList 删除的网络集合
     * @return ResInstResult 资源层共通返回结果
     */
    ResInstResult deleteNetsForVm(String resVmSid, List<ResNetworkInst> vmNetList);

    /**
     * 整体重新配置虚拟机
     *
     * @param resVmInst 服务层POJO参数
     * @return 资源层共通返回结果 res inst result
     */
    ResInstResult reconfigVm(ResVmInst resVmInst);

    /**
     * 工单处理后，整体重新配置虚拟机
     *
     * @param resVmInst 虚机POJO
     * @return boolean 操作结果
     */
    boolean reconfigVmAfterDeal(ResVmInst resVmInst);

    /**
     * 资源勘察
     *
     * @param resVmCheck 虚机Check参数
     * @return 资源层共通返回结果 res inst result
     */
    ResInstResult checkResIsEnough(ResVmCheck resVmCheck);

    /**
     * 统计虚拟机信息
     *
     * @param resTopologySid 拓扑Sid
     * @return 虚机信息 res vm
     */
    ResVm statisticalTopologyOfVm(String resTopologySid);

    /**
     * 统计计算资源池下面的虚拟机
     *
     * @param resPoolSid 资源池Sid
     * @return 虚机信息 res vm
     */
    ResVm statisticalComputePoolOfVm(String resPoolSid);

    /**
     * 主机对应的虚机查询
     *
     * @param allocateResHostSid 寄主机Sid
     * @return 虚机信息 res vm
     */
    ResVm selectByResHostSid(String allocateResHostSid);

    /**
     * 重新配置IP（只修改数据库）
     *
     * @param resVm 虚拟机
     */
    void configIp(ResVm resVm);

    /**
     * 统计虚机列表
     *
     * @param example 统计参数
     * @return 虚机列表 list
     */
    List<ResVm> selectVmResSum(Criteria example);

    /**
     * 重新配置虚拟机时资源检查
     *
     * @param resVmInst 虚拟机实例pojo
     * @return ResInstResult 资源层共通返回结果
     */
    ResInstResult reconfigVmCheck(ResVmInst resVmInst);

    /***
     * 虚拟机批量操作
     *
     * @param resVmOptInst the res vm opt inst
     * @return 资源层共通返回结果 res inst result
     *
     * @throws JSONException the json exception
     * @throws IOException   the io exception
     */
    ResInstResult mulitOp(ResVmOptInst resVmOptInst) throws JSONException, IOException;

    /**
     * 创建虚拟机时资源检查
     *
     * @param resVmInstList 虚拟机实例列表
     * @return 资源层共通返回结果 res inst result
     */
    ResInstResult createVmCheck(List<ResVmInst> resVmInstList);

    /**
     * 同步虚拟机
     *
     * @param resVmSid 虚机sid
     * @return 资源层共通返回结果 res inst result
     *
     * @throws Exception the exception
     */
    ResInstResult synaVmInfo(String resVmSid) throws Exception;

    /**
     * 同步虚拟机 供同步主机调用
     *
     * @param resVm   虚拟机
     * @param resHost 寄主机
     * @param resVe   资源环境
     * @return boolean 同步结果
     *
     * @throws Exception the exception
     */
    boolean synaVmInfo(ResVm resVm, ResHost resHost, ResVe resVe) throws Exception;


    /**
     * 迁移虚拟机
     *
     * @param resVmSids     虚拟机Sid集合
     * @param resHostSid    寄主机Sid
     * @param resStorageSid 资源存储Sid
     * @return ResInstResult 资源层共通返回结果
     */
    ResInstResult migrateVm(List<String> resVmSids, String resHostSid, String resStorageSid);

    /**
     * 调用webconsole控制台
     *
     * @param params 参数
     * @return WebConsole控制台对象 res web console
     */
    ResWebConsole vmWebConsole(String params);

    /**
     * 检查虚拟机是否需要关机
     *
     * @param resVmInst 虚拟机实例pojo
     */
    void checkIsNeedPoweroff(ResVmInst resVmInst);

    /**
     * 更新更新虚拟机与网络关系
     *
     * @param resVm 资源虚机对象
     * @return booblea 操作结果
     */
    boolean updateVmNetByVm(ResVm resVm);

    /**
     * 虚拟机修改名称
     *
     * @param resVmSid  虚机Sid
     * @param newVmName 虚机名称
     * @return boolean boolean
     */
    boolean reVmName(String resVmSid, String newVmName);

    /**
     * 根据资源Sid查询所属虚拟化环境
     *
     * @param object 资源对象
     * @return 资源对象对应VE对象 VeByRes
     */
    ResVe getVeByRes(Object object);


    /**
     * 保存主机已分配的CPU、内存、以及主机下已分配的存储
     *
     * @param resStorage     存储资源
     * @param resStorageList 存储资源列表
     * @param allotSize      指定存储大小
     */
    void setResourceStorages(ResStorage resStorage, List<ResStorage> resStorageList, Long allotSize);

    /**
     * 查询存储下已经分配的存储大小
     *
     * @param resStorage 存储资源
     * @return 已分配存储大小 res vm allocate sto size
     */
    long getResVmAllocateStoSize(ResStorage resStorage);

    /**
     * 取得WebConsole控制台地址
     *
     * @param resVmSid  虚拟机SID
     * @param baseInput 基础对象
     * @return WebConsole web console
     */
    String getWebConsole(String resVmSid, ServiceBaseInput baseInput);

    /**
     * 插入PVE虚机.
     *
     * @param record 虚机信息
     * @return 插入条数 int
     */
    int insertPveVmSelective(ResVm record);


}