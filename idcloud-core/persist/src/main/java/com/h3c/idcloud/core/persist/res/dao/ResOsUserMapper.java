package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResOsUser;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res os user mapper.
 */
@Repository
public interface ResOsUserMapper {
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
     * @param osUserSid the os user sid
     * @return the int
     */
    int deleteByPrimaryKey(String osUserSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResOsUser record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResOsUser record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResOsUser> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param osUserSid the os user sid
     * @return the res os user
     */
    ResOsUser selectByPrimaryKey(String osUserSid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResOsUser record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResOsUser record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResOsUser record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResOsUser record);
}