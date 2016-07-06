package com.h3c.idcloud.core.persist.res.dao;

import com.h3c.idcloud.core.pojo.dto.res.ResVpcPortsIp;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResVpcPortsIpMapper {
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
    int deleteByPrimaryKey(String resPortSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ResVpcPortsIp record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ResVpcPortsIp record);

    /**
     * 根据条件查询记录集
     */
    List<ResVpcPortsIp> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    ResVpcPortsIp selectByPrimaryKey(String resPortSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ResVpcPortsIp record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ResVpcPortsIp record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ResVpcPortsIp record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ResVpcPortsIp record);
}