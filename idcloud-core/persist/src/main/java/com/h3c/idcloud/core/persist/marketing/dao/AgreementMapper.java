package com.h3c.idcloud.core.persist.marketing.dao;

import com.h3c.idcloud.core.pojo.dto.marketing.Agreement;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AgreementMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria criteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long agreementSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Agreement record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Agreement record);

    /**
     * 根据条件查询记录集
     */
    List<Agreement> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Agreement selectByPrimaryKey(Long agreementSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Agreement record,
                                @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Agreement record,
                       @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Agreement record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Agreement record);
}