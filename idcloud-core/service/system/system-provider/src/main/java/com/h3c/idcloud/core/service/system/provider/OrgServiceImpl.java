package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.OrgMapper;
import com.h3c.idcloud.core.pojo.dto.system.Org;
import com.h3c.idcloud.core.service.system.api.OrgService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgMapper OrgMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.OrgMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Org selectByPrimaryKey(Long orgSid) {
        return this.OrgMapper.selectByPrimaryKey(orgSid);
    }

    public List<Org> selectByParams(Criteria example) {
        return this.OrgMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long orgSid) {
        return this.OrgMapper.deleteByPrimaryKey(orgSid);
    }

    public int updateByPrimaryKeySelective(Org record) {
        return this.OrgMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Org record) {
        return this.OrgMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.OrgMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Org record, Criteria example) {
        return this.OrgMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Org record, Criteria example) {
        return this.OrgMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Org record) {
        return this.OrgMapper.insert(record);
    }

    public int insertSelective(Org record) {
        return this.OrgMapper.insertSelective(record);
    }

	@Override
	public List<Org> selectByParamsWithoutRoot(Criteria example) {
		// TODO Auto-generated method stub
		return this.OrgMapper.selectByParamsWithoutRoot(example);
	}
    
//	@Override
//	public List<ResourceTopology> selectOrgTopologyByParams(Criteria example) {
//		return this.resourceTopologyMapper.selectOrgTopologyByParams(example);
//	}
//	
//	@Override
//	public List<ResourceTopology> selectOrgPoolTopologyByParams(Criteria example) {
//		return this.resourceTopologyMapper.selectOrgPoolTopologyByParams(example);
//	}
}