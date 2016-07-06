package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.OrgbizMapper;
import com.h3c.idcloud.core.pojo.dto.system.Orgbiz;
import com.h3c.idcloud.core.pojo.dto.system.OrgbizKey;
import com.h3c.idcloud.core.service.system.api.OrgbizService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class OrgbizServiceImpl implements OrgbizService {
    @Autowired
    private OrgbizMapper orgbizMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrgbizServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.orgbizMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public List<Orgbiz> selectByParams(Criteria example) {
        return this.orgbizMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(OrgbizKey key) {
        return this.orgbizMapper.deleteByPrimaryKey(key);
    }

    public int deleteByParams(Criteria example) {
        return this.orgbizMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Orgbiz record, Criteria example) {
        return this.orgbizMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Orgbiz record, Criteria example) {
        return this.orgbizMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Orgbiz record) {
        return this.orgbizMapper.insert(record);
    }

    public int insertSelective(Orgbiz record) {
        return this.orgbizMapper.insertSelective(record);
    }

	@Override
	public List<Orgbiz> selectBizTreeByParams(Criteria example) {
		 return this.orgbizMapper.selectBizTreeByParams(example);
	}
	
	@Override
	public List<Orgbiz> selectSecondBizesByParams(Criteria example) {
		 return this.orgbizMapper.selectSecondBizesByParams(example);
	}

	@Override
	public List<Orgbiz> findBizByOrg(Criteria example) {
		return this.orgbizMapper.findBizByOrg(example);
	}

	@Override
	public List<Orgbiz> findParentBizByOrg(Criteria example) {
		return this.orgbizMapper.findParentBizByOrg(example);
	}

}