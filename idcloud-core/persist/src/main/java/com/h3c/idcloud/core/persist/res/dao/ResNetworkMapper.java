package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResNetwork;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res network mapper.
 *
 * @author Chaohong.Mao
 */
@Repository
public interface ResNetworkMapper {
    /**
     * 根据条件查询记录总数
     *
     * @param example the example
     * @return the int
     */
    int countByParams(Criteria example);

    /**
     * 根据条件查询记录总数
     *
     * @param example the example
     * @return the int
     */
    int countByBizParams(Criteria example);

    /**
     * 根据条件删除记录
     *
     * @param example the example
     * @return the int
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     *
     * @param resNetworkSid the res network sid
     * @return the int
     */
    int deleteByPrimaryKey(String resNetworkSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResNetwork record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResNetwork record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectByParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectByBizParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfNetWork(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfEverySubNetWorkType(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfCountNetWorkType(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfNetWorkType(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfEveryNetWork(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectByBizParamsOfEveryNetWorkType(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param resNetworkSid the res network sid
     * @return the res network
     */
    ResNetwork selectByPrimaryKey(String resNetworkSid);

    /**
     * 根据UUID查询记录
     *
     * @param uuid the uuid
     * @return the res network
     */
    ResNetwork selectByUuid(String uuid);

    /**
     * 查询管理对象的外部网络
     *
     * @param mgtObjSid the mgt obj sid
     * @return the list
     */
    List<ResNetwork> selectExtNetworkByMgtObjSid(Long mgtObjSid);

    /**
     * 统计业务下的网络信息
     *
     * @param example the example
     * @return the res network
     */
    ResNetwork statisticalBizOfNetwork(Criteria example);

    /**
     * 统计拓扑结构下的网络个数
     *
     * @param resTopologySid the res topology sid
     * @return the res network
     */
    ResNetwork statisticalTopologyOfNetwork(String resTopologySid);

    /**
     * 查询企业自定义网络
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectCustomNetworkByMgtObjSid(Criteria example);

    /**
     * 查询私有网络下面的子网
     *
     * @param example
     * @return
     */
    List<ResNetwork> selectSubnetInVpc(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResNetwork record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResNetwork record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResNetwork record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResNetwork record);

    /**
     * Select networks by host sid list.
     *
     * @param resHostSid the res host sid
     * @return the list
     */
    List<ResNetwork> selectNetworksByHostSid(String resHostSid);

    /**
     * Select net work all info by params list.
     *
     * @param example the example
     * @return the list
     */
    List<ResNetwork> selectNetWorkAllInfoByParams(Criteria example);

    /**
     * Select by vpc list.
     *
     * @param resVpcSid the res vpc sid
     * @return the list
     */
    List<ResNetwork> selectByVpc(String resVpcSid);
}