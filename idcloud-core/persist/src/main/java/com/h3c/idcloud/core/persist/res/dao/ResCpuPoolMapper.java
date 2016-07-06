package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResCpuPool;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res cpu pool mapper.
 */
@Repository
public interface ResCpuPoolMapper {
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
     * @param resCpuPoolSid the res cpu pool sid
     * @return the int
     */
    int deleteByPrimaryKey(String resCpuPoolSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResCpuPool record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResCpuPool record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResCpuPool> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param resVeSid the res ve sid
     * @return the list
     */
    List<ResCpuPool> selectByPowerVe(String resVeSid);

    /**
     * 根据主机查询
     *
     * @param resVeSid the res ve sid
     * @return the list
     */
    List<ResCpuPool> selectByPoolWithSum(String resVeSid);

    /**
     * 根据主键查询记录
     *
     * @param resCpuPoolSid the res cpu pool sid
     * @return the res cpu pool
     */
    ResCpuPool selectByPrimaryKey(String resCpuPoolSid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResCpuPool record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResCpuPool record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResCpuPool record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResCpuPool record);
}