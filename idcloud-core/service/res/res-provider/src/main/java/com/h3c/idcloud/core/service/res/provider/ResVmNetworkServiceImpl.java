package com.h3c.idcloud.core.service.res.provider;



import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.res.dao.ResVmNetworkMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResVmNetwork;
import com.h3c.idcloud.core.service.res.api.ResVmNetworkService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Service(version = "1.0.0")
@Component
public class ResVmNetworkServiceImpl implements ResVmNetworkService {
    @Autowired
    private ResVmNetworkMapper resvmnetworkMapper;

    private static final Logger logger = LoggerFactory.getLogger(ResVmNetworkServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.resvmnetworkMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ResVmNetwork selectByPrimaryKey(String resVmSid) {
        return this.resvmnetworkMapper.selectByPrimaryKey(resVmSid);
    }

    public List<ResVmNetwork> selectByParams(Criteria example) {
        return this.resvmnetworkMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(String resVmSid) {
        return this.resvmnetworkMapper.deleteByPrimaryKey(resVmSid);
    }

    public int updateByPrimaryKeySelective(ResVmNetwork record) {
        return this.resvmnetworkMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ResVmNetwork record) {
        return this.resvmnetworkMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.resvmnetworkMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(ResVmNetwork record, Criteria example) {
        return this.resvmnetworkMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(ResVmNetwork record, Criteria example) {
        return this.resvmnetworkMapper.updateByParams(record, example.getCondition());
    }

    public int insert(ResVmNetwork record) {
        return this.resvmnetworkMapper.insert(record);
    }

    public int insertSelective(ResVmNetwork record) {
        return this.resvmnetworkMapper.insertSelective(record);
    }

	@Override
	public List<ResVmNetwork> selectNetsByParams(Criteria example) {
		return this.resvmnetworkMapper.selectNetsByParams(example);
	}
}