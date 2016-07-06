package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.SysconfigMapper;
import com.h3c.idcloud.core.pojo.dto.system.Sysconfig;
import com.h3c.idcloud.core.service.system.api.SysconfigService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class SysconfigServiceImpl implements SysconfigService {
    @Autowired
    private SysconfigMapper sysconfigMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysconfigServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.sysconfigMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    @Override
    public String getValueByConfigKey(String configKey) {
        String result = "";
        Criteria criteria = new Criteria();
		criteria.put("configKey", configKey);
		List<Sysconfig> configs = sysconfigMapper.selectByParams(criteria);
		if (configs != null && configs.size() > 0) {
			result = configs.get(0).getConfigValue();
		}
        return result;
    }

    public Sysconfig selectByPrimaryKey(Long configSid) {
        return this.sysconfigMapper.selectByPrimaryKey(configSid);
    }

    public List<Sysconfig> selectByParams(Criteria example) {
        return this.sysconfigMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long configSid) {
        return this.sysconfigMapper.deleteByPrimaryKey(configSid);
    }

    public int updateByPrimaryKeySelective(Sysconfig record) {
        return this.sysconfigMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Sysconfig record) {
        return this.sysconfigMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.sysconfigMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Sysconfig record, Criteria example) {
        return this.sysconfigMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Sysconfig record, Criteria example) {
        return this.sysconfigMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Sysconfig record) {
        return this.sysconfigMapper.insert(record);
    }

    public int insertSelective(Sysconfig record) {
        return this.sysconfigMapper.insertSelective(record);
    }

    @Override
	public List<Sysconfig> selectConfigTypeByParams(Criteria criteria) {
		return this.sysconfigMapper.selectConfigTypeByParams(criteria);
	}
}