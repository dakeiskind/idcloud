package com.h3c.idcloud.core.service.res.api;


import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;
import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.core.pojo.dto.res.ResVpc;
import com.h3c.idcloud.core.pojo.instance.ResCommonInst;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;
import java.util.Map;

/**
 * 接口 网络资源.
 *
 * @author Chaohong.Mao
 */
public interface ResNetworkService {

    /**
     * 查询网络
     *
     * @param example the example
     * @return the res network
     */
    List<ResNetwork> selectByParams(Criteria example);

    /**
     * 查询网络，用主键
     *
     * @param resNetworkSid 网络资源Sid
     * @return the res network
     */
    ResNetwork selectByPrimaryKey(String resNetworkSid);

    /**
     * 用主键删除网络资源.
     *
     * @param resNetworkSid 网络资源Sid
     * @return 影响条数 int
     */
    int deleteByPrimaryKey(String resNetworkSid);

    /**
     * 用主键更新网络资源.
     *
     * @param network the network
     * @return int
     */
    int updateByPrimaryKeySelective(ResNetwork network);

    /**
     * 插入数据（仅仅有值的部分）.
     *
     * @param record 数据记录
     * @return 影响条数 int
     */
    int insertSelective(ResNetwork record);

    /**
     * 插入自定义网络.
     *
     * @param resCommonInst the res net inst
     * @return 影响条数 string
     */
    String createNetworkWithSub(ResCommonInst resCommonInst);

    /**
     * 创建自定义子网
     *
     * @param resCommonInst the res common inst
     * @return string
     */
    String createSubnet(ResCommonInst resCommonInst);


    /**
     * 查找网络下的所有子网
     *
     * @param resVpcSid the res vpc sid
     * @return the list
     */
    Map<String, String> findSubnetByNetwork(String resVpcSid);

    /**
     * 查找租户下所有网络
     *
     * @param baseInput the res base inst
     * @return the list
     */
    List<ResVpc> findNetworkByTenant(ServiceBaseInput baseInput);

    /**
     * 统计拓扑结构下的网络个数
     *
     * @param resTopologySid 资源拓扑Sid
     * @return the res network
     */
    ResNetwork statisticalTopologyOfNetwork(String resTopologySid);

    /**
     * 根据条件查询记录集
     *
     * @param example 查询参数
     * @return the list
     */
    List<ResNetwork> selectByBizParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example 查询参数
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfNetWork(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example 查询参数
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfEveryNetWork(Criteria example);

    /**
     * Select by biz params of every net work type list.
     *
     * @param example 查询参数
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfEveryNetWorkType(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example 查询参数
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfEverySubNetWorkType(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example 查询参数
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfCountNetWorkType(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example 查询参数
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfNetWorkType(Criteria example);

    /**
     * 统计业务下的网络信息
     *
     * @param example 查询参数
     * @return the res network
     */
    ResNetwork statisticalBizOfNetwork(Criteria example);

    /**
     * 查询企业自定义网络
     *
     * @param example 查询参数
     * @return the list
     */
    List<ResNetwork> selectCustomNetworkByMgtObjSid(Criteria example);

    /**
     * 查询管理对象的外部网络
     *
     * @param resCommonInst the res common inst
     * @return 影响条数 int
     */
    int deleteSubNets(ResCommonInst resCommonInst);

    /**
     * 查询管理对象的外部网络
     *
     * @param resCommonInst the res common inst
     * @return 影响条数 int
     */
    int deleteNetwork(ResCommonInst resCommonInst);

    /**
     * 查询私有网络下面的子网
     *
     * @param example
     * @return
     */
    List<ResNetwork> selectSubnetInVpc(Criteria example);

}