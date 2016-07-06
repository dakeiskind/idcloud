package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResStorage;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res storage mapper.
 *
 * @author Chaohong.Mao
 */
@Repository
public interface ResStorageMapper {
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
     * @param resSid the res sid
     * @return the int
     */
    int deleteByPrimaryKey(String resSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResStorage record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResStorage record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResStorage> selectByParams(Criteria example);

    /**
     * 根据Power主机SID查询其关联的存储
     *
     * @param resHostSid the res host sid
     * @return the list
     */
    List<ResStorage> selectViosStorageByPowerHostSid(String resHostSid);

    /**
     * 根据Power主机SID查询其关联的存储
     *
     * @param resViosSid the res vios sid
     * @return the list
     */
    List<ResStorage> selectAvaliableStorageByVios(String resViosSid);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResStorage> selectByBizParams(Criteria example);

    /**
     * 查询出计算资源池中还可以关联的存储
     *
     * @param example the example
     * @return the list
     */
    List<ResStorage> selectRelativeableStorageByResTopologySid(Criteria example);

    /**
     * 根据主机sid查询相关的存储
     *
     * @param example the example
     * @return the list
     */
    List<ResStorage> selectStorageByHostSid(Criteria example);

    /**
     * 根据主机sid查询相关的存储
     *
     * @param resHostSid the res host sid
     * @return the list
     */
    List<ResStorage> selectStoByHostSid(String resHostSid);

    /**
     * 根据主机sid查询已分配给资源分区的存储
     *
     * @param resHostSid the res host sid
     * @return the list
     */
    List<ResStorage> selectAllocateStoByHostSid(String resHostSid);

    /**
     * 统计资源拓扑结构下的存储信息
     *
     * @param resTopologySid the res topology sid
     * @return the res storage
     */
    ResStorage statisticalTopologyOfStorage(String resTopologySid);

    /**
     * 统计资源拓扑结构下的存储容量信息
     *
     * @param resTopologySid the res topology sid
     * @return the res storage
     */
    ResStorage statisticalTopologyOfStorageVolume(String resTopologySid);

    /**
     * 统计业务下的存储信息
     *
     * @param example the example
     * @return the res storage
     */
    ResStorage statisticalBizOfStorage(Criteria example);

    /**
     * 统计资源拓扑结构下的存储分配信息
     *
     * @param resTopologySid the res topology sid
     * @return the res storage
     */
    ResStorage statisticalTopologyOfStorageAllotInfo(String resTopologySid);

    /**
     * 统计RZ下的存储分配信息
     *
     * @param resTopologySid the res topology sid
     * @return the res storage
     */
    ResStorage statisticalRzOfStorageAllotInfo(String resTopologySid);

    /**
     * 统计集群下存储的监控信息
     *
     * @param resVcSid the res vc sid
     * @return the res storage
     */
    ResStorage statisticalClusterOfStorage(String resVcSid);

    /**
     * 统计集群下存储的容量信息
     *
     * @param resVcSid the res vc sid
     * @return the res storage
     */
    ResStorage statisticalClusterOfStorageVolume(String resVcSid);

    /**
     * 统计存储分类下的存储信息
     *
     * @param resTopologySid the res topology sid
     * @return the res storage
     */
    ResStorage statisticalRscOfStorage(String resTopologySid);

    /**
     * 统计集群下存储的分配信息
     *
     * @param resVcSid the res vc sid
     * @return the res storage
     */
    ResStorage statisticalClusterOfStorageAllotInfo(String resVcSid);


    /**
     * 统计主机下存储的监控信息
     *
     * @param resHostSid the res host sid
     * @return the res storage
     */
    ResStorage statisticalHostOfStorage(String resHostSid);

    /**
     * 统计主机下存储的容量信息
     *
     * @param resHostSid the res host sid
     * @return the res storage
     */
    ResStorage statisticalHostOfStorageVolume(String resHostSid);

    /**
     * 统计主机下存储的分配信息
     *
     * @param resHostSid the res host sid
     * @return the res storage
     */
    ResStorage statisticalHostOfStorageAllotInfo(String resHostSid);

    /**
     * 统计资源分区下的存储信息
     *
     * @param resTopologySid the res topology sid
     * @return the res storage
     */
    ResStorage statisticalRzOfStorage(String resTopologySid);

    /**
     * 统计存储分类下的存储容量
     *
     * @param resTopologySid the res topology sid
     * @return the res storage
     */
    ResStorage statisticalVolumeRzOfStorage(String resTopologySid);

    /**
     * 根据主键查询记录
     *
     * @param resSid the res sid
     * @return the res storage
     */
    ResStorage selectByPrimaryKey(String resSid);

    /**
     * 根据UUID查询主键记录
     *
     * @param example the example
     * @return the list
     */
    List<ResStorage> selectPrimaryKeyByUUID(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResStorage record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResStorage record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResStorage record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResStorage record);

    /**
     * Select storage res sum list.
     *
     * @param example the example
     * @return the list
     */
    List<ResStorage> selectStorageResSum(Criteria example);

    /**
     * Statistical storage pool of rz list.
     *
     * @param example the example
     * @return the list
     */
    List<ResStorage> statisticalStoragePoolOfRz(Criteria example);

    /**
     * 根据HostSid和Storage名称，唯一确定存储
     *
     * @param example the example
     * @return list list
     */
    List<ResStorage> selectStoByHostWithStoName(Criteria example);

    /**
     * Select storage res sum by rel list.
     *
     * @param example the example
     * @return list list
     */
    List<ResStorage> selectStorageResSumByRel(Criteria example);

    /**
     * Select base info by params list.
     *
     * @param example the example
     * @return list list
     */
    List<ResStorage> selectBaseInfoByParams(Criteria example);

}