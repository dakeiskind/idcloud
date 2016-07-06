package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplateSpec;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceTemplateSpecService {
    int countByParams(Criteria example);

    ServiceTemplateSpec selectByPrimaryKey(Long specSid);

    List<ServiceTemplateSpec> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long specSid);

    int updateByPrimaryKeySelective(ServiceTemplateSpec record);

    int insertSelective(ServiceTemplateSpec record);
    
    /**
     * 根据条件查询记录集
     */
    List<ServiceTemplateSpec> selectByTemplateSid(Long tempSid);
    
    /**
     * 根据主键删除记录
     */
    int deleteByTemplateSid(Long tempSid);
}