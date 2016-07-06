package com.h3c.idcloud.core.service.charge.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillDetailMapper;
import com.h3c.idcloud.core.pojo.dto.charge.BillDetail;
import com.h3c.idcloud.core.service.charge.api.BillDetailService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    private BillDetailMapper billDetailMapper;

    private static final Logger logger = LoggerFactory.getLogger(BillDetailServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.billDetailMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public BillDetail selectByPrimaryKey(Long billDetailSid) {
        return this.billDetailMapper.selectByPrimaryKey(billDetailSid);
    }

    public List<BillDetail> selectByParams(Criteria example) {
        return this.billDetailMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long billDetailSid) {
        return this.billDetailMapper.deleteByPrimaryKey(billDetailSid);
    }

    public int updateByPrimaryKeySelective(BillDetail record) {
        return this.billDetailMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BillDetail record) {
        return this.billDetailMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.billDetailMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(BillDetail record, Criteria example) {
        return this.billDetailMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(BillDetail record, Criteria example) {
        return this.billDetailMapper.updateByParams(record, example.getCondition());
    }

    public int insert(BillDetail record) {
        return this.billDetailMapper.insert(record);
    }

    public int insertSelective(BillDetail record) {
        return this.billDetailMapper.insertSelective(record);
    }
}