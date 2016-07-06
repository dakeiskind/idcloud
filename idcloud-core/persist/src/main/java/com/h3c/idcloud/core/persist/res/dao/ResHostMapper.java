package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res host mapper.
 */
@Repository
public interface ResHostMapper {
    /**
     * 根据条件查询记录总数
     *
     * @param example the example
     * @return the int
     */
    int countByParams(Criteria example);

    /**
     * Count by biz params int.
     *
     * @param example the example
     * @return the int
     */
    int countByBizParams(Criteria example);

    /**
     * Count by in pc params int.
     *
     * @param example the example
     * @return the int
     */
    int countByInPcParams(Criteria example);

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
     * @param resHostSid the res host sid
     * @return the int
     */
    int deleteByPrimaryKey(String resHostSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResHost record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResHost record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectByParams(Criteria example);

    /**
     * Find host add to storage list.
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> findHostAddToStorage(Criteria example);

    /**
     * 根据pcSid查询其关联的主机
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectHostByPcTopologySid(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectResHostByParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectByParamsForScan(Criteria example);

    /**
     * 查询出网络关联的主机
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectRelationedHostByNetworkSid(Criteria example);

    /**
     * 查询出网络可以关联的主机
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectRelationHostByNetworkSid(Criteria example);

    /**
     * 查询主机下面关联的Vios数量
     *
     * @param topologySid the topology sid
     * @return list
     */
    List<ResHost> selectHostViosCnt(String topologySid);

    /**
     * 根据条件查询记录总数
     *
     * @param uuid the uuid
     * @return the res host
     */
    ResHost countByHostUUID(String uuid);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectByBizParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectHostInPool(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param resHostSid the res host sid
     * @return the res host
     */
    ResHost selectByPrimaryKey(String resHostSid);

    /**
     * 根据UUID查询主键记录
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectPrimaryKeyByUUID(Criteria example);

    /**
     * 根据集群查询主机记录
     *
     * @param parentTopologySid the parent topology sid
     * @return the list
     */
    List<ResHost> selectByResVcSid(String parentTopologySid);

    /**
     * 统计业务下的主机信息
     *
     * @param example the example
     * @return the res host
     */
    ResHost statisticalBizOfHost(Criteria example);

    /**
     * 统计资源分区下的主机分配信息
     *
     * @param resTopologySid the res topology sid
     * @return the res host
     */
    ResHost statisticalRzOfHostAllotInfo(String resTopologySid);

    /**
     * 统计资源拓扑下的主机分配信息
     *
     * @param resTopologySid the res topology sid
     * @return the res host
     */
    ResHost statisticalTopologyOfHostAllotInfo(String resTopologySid);

    /**
     * 统计资源拓扑下的Power主机分配信息
     *
     * @param resTopologySid the res topology sid
     * @return the res host
     */
    ResHost statisticalTopologyOfPowerHostAllotInfo(String resTopologySid);

    /**
     * 统计拓扑下的主机
     *
     * @param example the example
     * @return the res host
     */
    ResHost statisticalTopologyOfHost(Criteria example);

    /**
     * 统计存储池下的主机
     *
     * @param resPoolSid the res pool sid
     * @return the res host
     */
    ResHost statisticalStoragePoolOfHost(String resPoolSid);

    /**
     * 统计计算资源池下的主机
     *
     * @param resPoolSid the res pool sid
     * @return the res host
     */
    ResHost statisticalComputePoolOfHost(String resPoolSid);

    /**
     * 统计RZ下的主机
     *
     * @param resTopologySid
     * @return the res host
     */
    ResHost statisticalHostPoolOfRz(String resTopologySid);

    /**
     * 根据主键查询记录
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectHostByStorageSid(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResHost record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResHost record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResHost record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResHost record);

    /**
     * 根据条件查询统计后的主机下的资源总量
     *
     * @param example the example
     * @return list
     */
    List<ResHost> selectCountHostRes(Criteria example);

    /**
     * 根据条件查询统计资源
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectResByTopology(Criteria example);

    /**
     * 得到主机的总资源
     *
     * @param example the example
     * @return the list
     */
    List<ResHost> selectHostResSum(Criteria example);

    /**
     * 删除主机
     *
     * @param resHostSid the res host sid
     * @return the int
     */
    int deleteHostWithObjRes(String resHostSid);

    /**
     * Count by equip type list.
     *
     * @param params the params
     * @return list
     */
    List<ResHost> countByEquipType(Criteria params);

    /**
     * Statistical topology of server res host.
     *
     * @param example the example
     * @return the res host
     */
    ResHost statisticalTopologyOfServer(Criteria example);

    /**
     * Select host res sum by rel list.
     *
     * @param example the example
     * @return list
     */
    List<ResHost> selectHostResSumByRel(Criteria example);
}