package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceTemplate;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;

public interface ServiceTemplateService {
    int countByParams(Criteria example);

    ServiceTemplate selectByPrimaryKey(Long templateSid);

    List<ServiceTemplate> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long templateSid);

    int updateByPrimaryKeySelective(ServiceTemplate record);

    int insertSelective(ServiceTemplate record);
    
    /**
     * 根据serviceSid查询记录集
     */
    List<ServiceTemplate> selectByServiceSid(Long serviceSid);
    
    /**
     * 后台新增用户模板
     * 
     * @param template
     * @return
     */
    boolean insertPlatformServiceTemp(ServiceTemplate template);
    
    /**
     * 后台更新用户模板
     * 
     * @param template
     * @return
     */
    boolean updatePlatformServiceTemp(ServiceTemplate template);
    
}