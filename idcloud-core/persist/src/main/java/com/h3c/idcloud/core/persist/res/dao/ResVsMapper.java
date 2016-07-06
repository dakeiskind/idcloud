package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVs;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res vs mapper.
 */
@Repository
public interface ResVsMapper {
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
     * @param resVsSid the res vs sid
     * @return the int
     */
    int deleteByPrimaryKey(String resVsSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResVs record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResVs record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResVs> selectByParams(Criteria example);

    /**
     * 根据交换机集合查询虚拟化环境
     *
     * @param example the example
     * @return the list
     */
    List<ResVs> selectResVeByResVsSet(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param resHostSid the res host sid
     * @return the list
     */
    List<ResVs> selectResVsByPveHost(String resHostSid);

    /**
     * 根据主键查询记录
     *
     * @param resVsSid the res vs sid
     * @return the res vs
     */
    ResVs selectByPrimaryKey(String resVsSid);

    /**
     * 根据主机sid查询
     *
     * @param resHostSid the res host sid
     * @return the list
     */
    List<ResVs> selectByHostSid(String resHostSid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResVs record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResVs record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVs record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResVs record);
}