package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResExtNetwork;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res network mapper.
 *
 * @author Chaohong.Mao
 */
@Repository
public interface ResExtNetworkMapper {
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
     * @param ResExtNetworkSid the res network sid
     * @return the int
     */
    int deleteByPrimaryKey(String ResExtNetworkSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResExtNetwork record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResExtNetwork record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResExtNetwork> selectByParams(Criteria example);

    /**
     * 根据条件查询可用外部网络
     *
     * @param zoneId the zone id
     * @return the list
     */
    List<ResExtNetwork> selectAvailableByZone(String zoneId);


    /**
     * 根据主键查询记录
     *
     * @param ResExtNetworkSid the res network sid
     * @return the res network
     */
    ResExtNetwork selectByPrimaryKey(String ResExtNetworkSid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResExtNetwork record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResExtNetwork record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResExtNetwork record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResExtNetwork record);

}