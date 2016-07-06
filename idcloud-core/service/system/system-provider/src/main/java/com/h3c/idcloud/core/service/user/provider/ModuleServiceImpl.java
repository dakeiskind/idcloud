package com.h3c.idcloud.core.service.user.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

import com.h3c.idcloud.core.persist.user.dao.ModuleMapper;
import com.h3c.idcloud.core.pojo.dto.user.Module;
import com.h3c.idcloud.core.service.user.api.ModuleService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0")
@Component
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    private static final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.moduleMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    /**
	 * 根据用户SID查询可用的功能模�?
	 * 
	 * @param userSid
	 * @return
	 */
	@Override
	public List<Module> selectByUserSid(Long userSid, Integer moduleCategory) {
		Criteria criteria = new Criteria();
		criteria.put("userSid", userSid);
		criteria.put("moduleCategory", moduleCategory);
		criteria.put("moduleType", WebConstants.ModuleType.MEMU);
		return this.moduleMapper.selectUserModules(criteria);
	}
	
    public Module selectByPrimaryKey(String moduleSid) {
        return this.moduleMapper.selectByPrimaryKey(moduleSid);
    }

    public List<Module> selectByParams(Criteria example) {
        return this.moduleMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(String moduleSid) {
        return this.moduleMapper.deleteByPrimaryKey(moduleSid);
    }

    public int updateByPrimaryKeySelective(Module record) {
        return this.moduleMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Module record) {
        return this.moduleMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.moduleMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Module record, Criteria example) {
        return this.moduleMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Module record, Criteria example) {
        return this.moduleMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Module record) {
        return this.moduleMapper.insert(record);
    }

    public int insertSelective(Module record) {
        return this.moduleMapper.insertSelective(record);
    }
}