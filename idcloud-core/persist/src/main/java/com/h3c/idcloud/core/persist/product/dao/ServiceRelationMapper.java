package com.h3c.idcloud.core.persist.product.dao;


import com.h3c.idcloud.core.pojo.dto.product.ServiceRelation;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRelationMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ServiceRelation record);

    /**
     * 根据条件查询记录集
     */
    List<ServiceRelation> selectByParams(Criteria example);
    
    /**
     * 根据条件更新
     */
    int updateByPrimaryKey(ServiceRelation record);

}