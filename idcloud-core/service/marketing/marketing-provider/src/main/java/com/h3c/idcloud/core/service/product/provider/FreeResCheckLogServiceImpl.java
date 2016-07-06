package com.h3c.idcloud.core.service.product.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.product.dao.FreeResCheckLogMapper;
import com.h3c.idcloud.core.pojo.dto.product.FreeResCheckLog;
import com.h3c.idcloud.core.service.product.api.FreeResCheckLogService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资源闲置回收-配置修改服务类
 * 
 * @author ssr
 *
 */
@Service(version = "1.0.0")
@Component
public class FreeResCheckLogServiceImpl implements FreeResCheckLogService {
    @Autowired
    private FreeResCheckLogMapper freeResCheckLogMapper;

    private static final Logger logger = LoggerFactory.getLogger(FreeResCheckLogServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.freeResCheckLogMapper.countByParams(example);
//        logger.debug("count: {}", count);
        return count;
    }

    public FreeResCheckLog selectByPrimaryKey(Long fresCheckLogSid) {
        return this.freeResCheckLogMapper.selectByPrimaryKey(fresCheckLogSid);
    }

    public FreeResCheckLog selectByFreeSid(Long fresSid) {
        return this.freeResCheckLogMapper.selectByFreeSid(fresSid);
    }
    
    public List<FreeResCheckLog> selectByParams(Criteria example) {
        return this.freeResCheckLogMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long fresCheckLogSid) {
        return this.freeResCheckLogMapper.deleteByPrimaryKey(fresCheckLogSid);
    }

    public int updateByPrimaryKeySelective(FreeResCheckLog record) {
        return this.freeResCheckLogMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(FreeResCheckLog record) {
        return this.freeResCheckLogMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.freeResCheckLogMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(FreeResCheckLog record, Criteria example) {
        return this.freeResCheckLogMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(FreeResCheckLog record, Criteria example) {
        return this.freeResCheckLogMapper.updateByParams(record, example.getCondition());
    }

    public int insert(FreeResCheckLog record) {
        return this.freeResCheckLogMapper.insert(record);
    }

    public int insertSelective(FreeResCheckLog record) {
        return this.freeResCheckLogMapper.insertSelective(record);
    }

	@Override
	public List<FreeResCheckLog> selectResByBizForSummary(Criteria criteria) {
		return this.freeResCheckLogMapper.selectResByBizForSummary(criteria);
	}

	@Override
	public FreeResCheckLog selectResByInstIdForCmId(Criteria example){
		return this.freeResCheckLogMapper.selectResByInstIdForCmId(example);
	}
}