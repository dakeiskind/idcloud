package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.CodeCategoryMapper;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.core.pojo.dto.system.CodeCategory;
import com.h3c.idcloud.core.service.system.api.CodeCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class CodeCategoryServiceImpl implements CodeCategoryService {
    @Autowired
    private CodeCategoryMapper codeCategoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(CodeCategoryServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.codeCategoryMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public CodeCategory selectByPrimaryKey(Long categorySid) {
        return this.codeCategoryMapper.selectByPrimaryKey(categorySid);
    }

    public List<CodeCategory> selectByParams(Criteria example) {
        return this.codeCategoryMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long categorySid) {
        return this.codeCategoryMapper.deleteByPrimaryKey(categorySid);
    }

    public int updateByPrimaryKeySelective(CodeCategory record) {
        return this.codeCategoryMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CodeCategory record) {
        return this.codeCategoryMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.codeCategoryMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(CodeCategory record, Criteria example) {
        return this.codeCategoryMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(CodeCategory record, Criteria example) {
        return this.codeCategoryMapper.updateByParams(record, example.getCondition());
    }

    public int insert(CodeCategory record) {
        return this.codeCategoryMapper.insert(record);
    }

    public int insertSelective(CodeCategory record) {
        return this.codeCategoryMapper.insertSelective(record);
    }
    
	/**
	 * 新增代码分类
	 */
	
	public boolean insertCodeCategory(CodeCategory codeCategory) {
		boolean result = false;
		
		try {
			// 插入代码分类
			int codeCategoryResult = this.codeCategoryMapper.insertSelective(codeCategory);

			if (codeCategoryResult == 1) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}    
	
	/**
	 * 删除代码分类
	 */
	
	public boolean deleteCodeCategory(Long categorySid) {
		boolean result = false;
		try {
			CodeCategory codeCategory = this.codeCategoryMapper.selectByPrimaryKey(categorySid);
			Criteria condition = new Criteria();
			condition.put("categorySid", codeCategory.getCategorySid());			
			int codeCategoryResult = this.codeCategoryMapper.deleteByPrimaryKey(categorySid);
			if (codeCategoryResult == 0) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}		
}