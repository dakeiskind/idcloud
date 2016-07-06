//package com.hptsic.cloud.service.service;
//
//import java.util.List;
//import java.util.Map;
//
//import com.hptsic.cloud.alarm.pojo.AlarmData;
//import com.hptsic.cloud.common.pojo.Criteria;
//import com.hptsic.cloud.resource.pojo.ResourceTopology;
//
//public interface ServiceTopologyService {
//	/**
//     * 根据条件查询记录集
//     */
//    List<ResourceTopology> selectByParams(Criteria example);
//    
//    /**
//     * 根据主键查询记录
//     */
//    ResourceTopology selectByPrimaryKey(Long resTopologySid);
//    
//    /**
//     * 根据主键更新属性不为空的记录
//     */
//    int updateByPrimaryKeySelective(ResourceTopology record);
//    
//    /**
//     * 新增拓扑
//     */
//    int insertSelective(ResourceTopology record);
//    
//    /**
//     * 查询父节点
//     */
//    ResourceTopology selectTopologyParentNode(Long topologyParentSid);
//    
//    /**
//     * 删除拓扑
//     */
//    int deleteByPrimaryKey(Long resTopologySid);
//    
//    /**
//     * 拓扑下面的子集个数
//     */
//    int selectTopologyChildCount(Long resTopologySid);
//    
//    /**
//	 * 获取报警信息
//	 * 
//	 * @param searchType
//	 * @param searchSid
//	 * @return
//	 * @throws Exception
//	 */
//	public List<AlarmData> getAlarms(String searchType,String searchSid) throws Exception;
//	
//	List<ResourceTopology> selectTopologyByParams(Criteria example);
//	
//	List<ResourceTopology> selectPoolTopologyByParams(Criteria example);
//}
