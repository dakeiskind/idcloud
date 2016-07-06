package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResOSUsageSum;
import com.h3c.idcloud.core.pojo.dto.res.ResObjectStorageUsage;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res object storage usage mapper.
 */
@Repository
public interface ResObjectStorageUsageMapper {
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
     * @param id the id
     * @return the int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResObjectStorageUsage record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResObjectStorageUsage record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResObjectStorageUsage> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param id the id
     * @return the res object storage usage
     */
    ResObjectStorageUsage selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResObjectStorageUsage record,
                                @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResObjectStorageUsage record,
                       @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResObjectStorageUsage record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResObjectStorageUsage record);

    /**
     * 根据条件查询记录
     *
     * @param params 可传入begin、end、account
     * @return list
     */
    List<ResObjectStorageUsage> selectByMap(Map params);

    /**
     * 根据条件获取统计量
     *
     * @param params 可传入begin、end、account
     * @return res os usage sum
     */
    ResOSUsageSum selectSum(Map params);
}