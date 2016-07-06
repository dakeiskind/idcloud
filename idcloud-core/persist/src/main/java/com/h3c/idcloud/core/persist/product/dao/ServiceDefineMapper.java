package com.h3c.idcloud.core.persist.product.dao;

import com.h3c.idcloud.core.pojo.dto.product.ServiceDefine;
import com.h3c.idcloud.core.pojo.vo.common.CommonServiceCode;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 */
@Repository
public interface ServiceDefineMapper {
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
    int deleteByPrimaryKey(Long serviceSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ServiceDefine record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceDefine record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceDefine> selectByParams(Criteria example);

    /**
     * 根据条件查询记录集
     */
    List<ServiceDefine> selectServiceByCatalogSidList(Criteria example);

    /**
     * 根据主键查询记录
     */
    ServiceDefine selectByPrimaryKey(Long serviceSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") ServiceDefine record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") ServiceDefine record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ServiceDefine record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ServiceDefine record);



    List<CommonServiceCode> findCommonServiceCodes(Criteria example);
}
