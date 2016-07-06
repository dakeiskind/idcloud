package com.h3c.idcloud.core.service.charge.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.charge.dao.BillMapper;
import com.h3c.idcloud.core.pojo.dto.charge.Bill;
import com.h3c.idcloud.core.service.charge.api.BillService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;

    private static final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.billMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Bill selectByPrimaryKey(Long billSid) {
        return this.billMapper.selectByPrimaryKey(billSid);
    }

    public List<Bill> selectByParams(Criteria example) {
        return this.billMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long billSid) {
        return this.billMapper.deleteByPrimaryKey(billSid);
    }

    public int updateByPrimaryKeySelective(Bill record) {
        return this.billMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Bill record) {
        return this.billMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.billMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Bill record, Criteria example) {
        return this.billMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Bill record, Criteria example) {
        return this.billMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Bill record) {
        return this.billMapper.insert(record);
    }

    public int insertSelective(Bill record) {
        return this.billMapper.insertSelective(record);
    }
}