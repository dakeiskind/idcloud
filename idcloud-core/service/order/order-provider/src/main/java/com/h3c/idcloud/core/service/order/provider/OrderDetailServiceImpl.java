package com.h3c.idcloud.core.service.order.provider;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.order.dao.OrderDetailMapper;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.service.order.api.OrderDetailService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Service(version = "1.0.0")
@Component
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.orderDetailMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public OrderDetail selectByPrimaryKey(Long detailSid) {
        return this.orderDetailMapper.selectByPrimaryKey(detailSid);
    }

    public List<OrderDetail> selectByParams(Criteria example) {
        return this.orderDetailMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long detailSid) {
        return this.orderDetailMapper.deleteByPrimaryKey(detailSid);
    }

    public int updateByPrimaryKeySelective(OrderDetail record) {
        return this.orderDetailMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(OrderDetail record) {
        return this.orderDetailMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.orderDetailMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(OrderDetail record, Criteria example) {
        return this.orderDetailMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(OrderDetail record, Criteria example) {
        return this.orderDetailMapper.updateByParams(record, example.getCondition());
    }

    public int insert(OrderDetail record) {
        return this.orderDetailMapper.insert(record);
    }

    public int insertSelective(OrderDetail record) {
        return this.orderDetailMapper.insertSelective(record);
    }
}