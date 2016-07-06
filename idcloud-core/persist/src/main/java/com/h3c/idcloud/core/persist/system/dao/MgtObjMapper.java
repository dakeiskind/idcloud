package com.h3c.idcloud.core.persist.system.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.MgtObj;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MgtObjMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long mgtObjSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgtObj record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgtObj record);

    /**
     * 根据条件查询记录集
     */
    List<MgtObj> selectByParams(Criteria example);

    List<MgtObj> selectMgtObjTreeByParams(Criteria example);

    List<MgtObj> findParentMgtObj(Criteria example);
    
    /**
     * 查询账户信息
     */
    List<MgtObj> selectByBillingAccount(Criteria example);

    /**
     * 根据主键查询记录
     */
    MgtObj selectByPrimaryKey(Long mgtObjSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") MgtObj record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") MgtObj record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgtObj record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgtObj record);

    List<ResTopology> selectResZoneTopologyByParams(Criteria example);

    List<ResTopology> selectVeListByHost(Criteria example);
    
    List<MgtObj> selectAllProject(Criteria example);

	List<ResTopology> selectMgtObjComByParams(Criteria example);

	List<MgtObj> selectMgtObjTreeByParams2(Criteria criteria);

	List<ResTopology> selectMgtObjResZoneTopologyByParams(Criteria example);

	List<MgtObj> selectBaseFileByParams(Criteria condition);
    
}