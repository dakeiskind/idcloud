package com.h3c.idcloud.core.persist.system.dao;

import java.util.List;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.Dept;
import com.h3c.idcloud.core.pojo.dto.system.Biz;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptMapper {

    /**
     * 根据条件查询记录集
     */
    List<Dept> selectByParams(Criteria criteria);
    /**
     * 根据条件查询记录集
     */
    List<Biz> selectByParamsBiz(Criteria criteria);    
    /**
     * 根据主键查询记录
     */
    Biz selectByPrimaryKeyBizFull(Long bizSid);
}
