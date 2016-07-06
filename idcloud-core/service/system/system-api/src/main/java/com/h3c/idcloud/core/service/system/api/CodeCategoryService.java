package com.h3c.idcloud.core.service.system.api;


import com.h3c.idcloud.core.pojo.dto.system.CodeCategory;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import java.util.List;



public interface CodeCategoryService {
    int countByParams(Criteria example);

    CodeCategory selectByPrimaryKey(Long categorySid);

    List<CodeCategory> selectByParams(Criteria example);

    int deleteByPrimaryKey(Long categorySid);

    int updateByPrimaryKeySelective(CodeCategory record);

    int updateByPrimaryKey(CodeCategory record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(CodeCategory record, Criteria example);

    int updateByParams(CodeCategory record, Criteria example);

    int insert(CodeCategory record);

    int insertSelective(CodeCategory record);
    
    /**
     * 新增代码分类
     * 
     * @param codeCategory
     * @return
     */
    boolean insertCodeCategory(CodeCategory codeCategory);  
    
    /**
     * 删除代码分类
     * 
     * @param categorySid
     * @return
     */
    boolean deleteCodeCategory(Long categorySid);       
}