package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res topology mapper.
 */
@Repository
public interface ResTopologyMapper {
    /**
     * 根据条件查询记录总数
     *
     * @param example the example
     * @return the int
     */
    int countByParams(Criteria example);

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
     * @param resTopologySid the res topology sid
     * @return the int
     */
    int deleteByPrimaryKey(String resTopologySid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResTopology record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResTopology record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResTopology> selectByParams(Criteria example);

    /**
     * 查询虚拟化环境 --- 主机
     *
     * @param example the example
     * @return the list
     */
    List<ResTopology> selectVirtualTopologyTree(Criteria example);

    /**
     * 查询虚拟化环境 --- 存储
     *
     * @param example the example
     * @return the list
     */
    List<ResTopology> selectVirtualStorageTopologyTree(Criteria example);

    /**
     * 查询资源池拓扑结构
     *
     * @param example the example
     * @return the list
     */
    List<ResTopology> selectPoolTopologyTree(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param resTopologySid the res topology sid
     * @return the res topology
     */
    ResTopology selectByPrimaryKey(String resTopologySid);

    /**
     * 查询存储所属的集群
     *
     * @param example the example
     * @return the list
     */
    List<ResTopology> selectVcByStorageSid(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResTopology record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResTopology record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResTopology record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResTopology record);

    /**
     * 根据条件查询资源分区拓扑
     *
     * @param param 查询条件
     * @return 资源拓扑 list
     */
    List<ResTopology> selectResZoneTopologyByParams(Criteria param);

    /**
     * 根据条件查询资源分区拓扑
     *
     * @param param 查询条件
     * @return 资源拓扑 list
     */
    List<ResTopology> selectResZoneTopologyChildByParams(Criteria param);

    /**
     * 根据资源分区查询虚拟化环境
     *
     * @param param 查询条件
     * @return 资源拓扑 list
     */
    List<ResTopology> selectVeByZone(Criteria param);

    /**
     * 根据资源分区以及类型查询
     *
     * @param param 查询条件
     * @return 资源拓扑
     */
    List<ResTopology> selectTopologyByType(Criteria param);

    /**
     * 根据虚拟机SID查询分区ID
     *
     * @param resVmSid 虚拟机SID
     * @return 分区ID
     */
    String selectZoneByVm(String resVmSid);

    /**
     * 设备管理拓扑
     *
     * @param example the example
     * @return list
     */
    List<ResTopology> selectPhysicalTopologyTree(Criteria example);
}