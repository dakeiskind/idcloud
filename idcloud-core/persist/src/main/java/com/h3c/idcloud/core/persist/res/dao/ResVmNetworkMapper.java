package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接口 Res vm network mapper.
 *
 * @author Chaohong.Mao
 */
@Repository
public interface ResVmNetworkMapper {
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
     * @param resVmSid the res vm sid
     * @return the int
     */
    int deleteByPrimaryKey(String resVmSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     *
     * @param record the record
     * @return the int
     */
    int insert(ResVmNetwork record);

    /**
     * 保存属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ResVmNetwork record);

    /**
     * 根据条件查询记录集
     *
     * @param example the example
     * @return the list
     */
    List<ResVmNetwork> selectByParams(Criteria example);

    /**
     * 根据条件查询虚拟机网络
     *
     * @param example the example
     * @return the list
     */
    List<ResVmNetwork> selectVMNetByParams(Criteria example);

    /**
     * 根据主键查询记录
     *
     * @param resVmSid the res vm sid
     * @return the res vm network
     */
    ResVmNetwork selectByPrimaryKey(String resVmSid);

    /**
     * 根据虚拟机查询记录
     *
     * @param resVmSid the res vm sid
     * @return the list
     */
    List<ResVmNetwork> selectByVmSid(String resVmSid);

    /**
     * 根据ip查询记录
     *
     * @param ipAddress the ip address
     * @return the res vm network
     */
    ResVmNetwork selectByIp(String ipAddress);

    /**
     * 根据NetSid查询记录
     *
     * @param resNetworkSid the res network sid
     * @return the res vm network
     */
    List<ResVmNetwork> selectByNetSid(String resNetworkSid);

    /**
     * 根据VPC查询记录
     *
     * @param resNetworkSid the res network sid
     * @return the res vm network
     */
    List<ResVmNetwork> selectByVpc(String resNetworkSid);

    /**
     * 根据条件更新属性不为空的记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParamsSelective(@Param("record") ResVmNetwork record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     *
     * @param record    the record
     * @param condition the condition
     * @return the int
     */
    int updateByParams(@Param("record") ResVmNetwork record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(ResVmNetwork record);

    /**
     * 根据主键更新记录
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(ResVmNetwork record);

    /**
     * Select nets by params list.
     *
     * @param example the example
     * @return list list
     */
    List<ResVmNetwork> selectNetsByParams(Criteria example);
}