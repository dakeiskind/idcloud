package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResVd;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res vd mapper.
 */
@Repository
public interface ResVdMapper {
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
     * @param resVdSid the res vd sid
     * @return the int
     */
    int deleteByPrimaryKey(String resVdSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResVd record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResVd record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResVd> selectByParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResVd> selectByPowerVe(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param resVdSid the res vd sid
     * @return the res vd
     */
    ResVd selectByPrimaryKey(String resVdSid);

    /**
     * 根据UUID查询记录
     *
     * @param uuid vd uuid
     * @return the res vd
     */
    ResVd selectByVdUUID(String uuid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResVd record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResVd record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVd record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResVd record);

    /**
     * Select vd res sum list.
     *
     * @param example the example
     * @return list
     */
    List<ResVd> selectVdResSum(Criteria example);

    /**
     * Select vd res sum by type list.
     *
     * @param example the example
     * @return list
     */
    List<ResVd> selectVdResSumByType(Criteria example);
}