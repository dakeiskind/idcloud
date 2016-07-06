package com.h3c.idcloud.core.service.system.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.system.dao.DeptMapper;
import com.h3c.idcloud.core.pojo.dto.system.Biz;
import com.h3c.idcloud.core.pojo.dto.system.Dept;
import com.h3c.idcloud.core.service.system.api.DeptService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0")
@Component
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper DeptMapper;

    private static final Logger logger = LoggerFactory.getLogger(DeptServiceImpl.class);
	
	@Override
	public List<Dept> selectByParams(Criteria criteria) {
		return this.DeptMapper.selectByParams(criteria);
	}
	
	@Override
	public List<Biz> selectByParamsBiz(Criteria criteria) {
		return this.DeptMapper.selectByParamsBiz(criteria);
	}	
	
	@Override
	public Biz selectByPrimaryKeyBizFull(Long bizSid) {
		return this.DeptMapper.selectByPrimaryKeyBizFull(bizSid);
	}	
}
