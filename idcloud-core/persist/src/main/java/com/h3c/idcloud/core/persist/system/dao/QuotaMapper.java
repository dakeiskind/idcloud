package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.Quota;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotaMapper {
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
    int deleteByPrimaryKey(Long quotaSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Quota record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Quota record);

    /**
     * 根据条件查询记录集
     */
    List<Quota> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Quota selectByPrimaryKey(Long quotaSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Quota record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Quota record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Quota record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Quota record);
    
    /**
     *  查询一级业务下的剩余配额 
     */
    Long selectByQuotaUsage(Criteria example);
    
    /**
     * 根据一级业务查询出一级业务下未分配的配额
     * @param bizSid
     */
    List<Quota> selectParentBizQuota(Criteria example);
    
    
    /**
     * 查询子业务配额列表
     * @param example
     * @return
     */
    List<Quota> selectChildrenBizQuotas(Criteria example);
    
    /**
     * 查询部门已使用配额
     * @param orgSid 部门sid
     * @return 
     */
    List<Quota> selectDeptUsageQuotas(Long orgSid);
    
    /**
     * 根据条件统计已使用配额
     * 
     * @param example
     * @return
     */
	List<Map<String, Object>> countUsageQuotaByParams(Criteria example);
	
	/**
	 * 根据提交统计已分配的云主机数量
	 * @param example
	 * @return
	 */
	Long countUsageVmByParams(Criteria example);
	
	/**
	 * 根据提交统计已分配的存储使用量
	 * @param example
	 * @return
	 */
	Long countUsageStorageByParams(Criteria example);

	/**
	 * 统计各个配额的总量
	 * @param example
	 * @return
	 */
	List<Quota> countByQuotaKey(Criteria example);
}