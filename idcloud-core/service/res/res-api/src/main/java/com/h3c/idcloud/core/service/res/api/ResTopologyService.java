package com.h3c.idcloud.core.service.res.api;

import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

/**
 * 接口 Res topology service.
 */
public interface ResTopologyService {

    /**
     * Delete by primary key int.
     *
     * @param resTopologySid the res topology sid
     *
     * @return the int
     */
    int deleteByPrimaryKey(String resTopologySid);

    /**
     * Delete rz by primary key int.
     *
     * @param resTopologySid the res topology sid
     *
     * @return the int
     */
    int deleteRzByPrimaryKey(String resTopologySid);

    /**
     * 新增资源分区
     *
     * @param record the record
     *
     * @return the string
     */
    String insertRzSelective(ResTopology record);


    /**
     * 获取所有的资源分区拓扑
     *
     * @return 资源分区拓扑 all res zone topology
     */
    List<ResTopology> getAllResZoneTopology();

    /**
     * 根据条件获取资源分区拓扑
     *
     * @param param the param
     *
     * @return 资源分区拓扑 res zone topology
     */
    List<ResTopology> getResZoneTopology(Criteria param);

    /**
     * 根据资源分区及条件获取其下子资源拓扑
     *
     * @param param the param
     *
     * @return 资源分区拓扑 res zone child topology
     */
    List<ResTopology> getResZoneChildTopology(Criteria param);

    /**
     * 查询拓扑资源结构
     *
     * @param param the param
     *
     * @return 资源分区拓扑 res zone child topology
     */
    List<ResTopology> selectVirtualTopologyTree(Criteria param);

    /**
     * 查询资源池拓扑结构
     *
     * @param param the param
     *
     * @return 资源分区拓扑 res zone child topology
     */
    List<ResTopology> selectPoolTopologyTree(Criteria param);

    /**
     * 查询资源存储视图拓扑结构
     *
     * @param param the param
     *
     * @return 资源分区拓扑 res zone child topology
     */
    List<ResTopology> selectVirtualStorageTopologyTree(Criteria param);

    /**
     * 根据拓扑SID查询topology信息
     *
     * @param resTopologySid
     *
     * @return
     */
    ResTopology selectByPrimaryKey(String resTopologySid);

    /**
     * 根据条件查询拓扑结构图
     *
     * @param param
     *
     * @return
     */
    List<ResTopology> selectByParams(Criteria param);

    /**
     *  插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(ResTopology record);

}