package com.h3c.idcloud.core.persist.res.dao;


import com.h3c.idcloud.core.pojo.dto.res.ResIp;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res ip mapper.
 */
@Repository
public interface ResIpMapper {
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
     * @param resSid the res sid
     * @return the int
     */
    int deleteByPrimaryKey(String resSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResIp record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResIp record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResIp> selectByParams(Criteria example);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResIp> selectResIpByVM(Criteria example);

    /**
     * 根据网络sid查询其下的ip地址
     *
     * @param resNetworkSid the res network sid
     * @return the list
     */
    List<ResIp> selectIpsByResNetworkSid(String resNetworkSid);


    /**
     * 根据主键查询记录
     *
     * @param resSid the res sid
     * @return the res ip
     */
    ResIp selectByPrimaryKey(String resSid);

    /**
     * 统计网络池下的ip信息
     *
     * @param example the example
     * @return the res ip
     */
    ResIp statisticsIpInNetworkPool(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResIp record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResIp record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResIp record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResIp record);

    /**
     * 根据资源拓扑统计ip个数
     *
     * @param example the example
     * @return the int
     */
    int countByParamsForBar(Criteria example);

    /**
     * 查询虚拟机可以使用的Ip列表
     *
     * @param criteria the criteria
     * @return the list
     */
    List<ResIp> selectIpsByResNetworkSidAndVmSid(Criteria criteria);

    /**
     * Select net work info by params list.
     *
     * @param criteria the criteria
     * @return the list
     */
    List<ResIp> selectNetWorkInfoByParams(Criteria criteria);
}