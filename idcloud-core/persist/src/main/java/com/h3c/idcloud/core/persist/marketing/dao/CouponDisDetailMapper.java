package com.h3c.idcloud.core.persist.marketing.dao;



import com.h3c.idcloud.core.pojo.dto.marketing.CouponDisDetail;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CouponDisDetailMapper {
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
    int deleteByPrimaryKey(Long distributionDetailSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(CouponDisDetail record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(CouponDisDetail record);

    /**
     * 根据条件查询记录集
     */
    List<CouponDisDetail> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    CouponDisDetail selectByPrimaryKey(Long distributionDetailSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") CouponDisDetail record,
                                @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") CouponDisDetail record,
                       @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(CouponDisDetail record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(CouponDisDetail record);
}