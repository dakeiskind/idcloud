package com.h3c.idcloud.core.service.order.provider;

import java.util.Date;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.h3c.idcloud.core.persist.order.dao.OrderApvRecordMapper;
import com.h3c.idcloud.core.persist.order.dao.OrderDetailMapper;
import com.h3c.idcloud.core.pojo.dto.order.OrderApvRecord;
import com.h3c.idcloud.core.pojo.dto.order.OrderDetail;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.service.order.api.OrderApvRecordService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0")
@Component
public class OrderApvRecordServiceImpl implements OrderApvRecordService {
    @Autowired
    private OrderApvRecordMapper orderApvRecordMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderApvRecordServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.orderApvRecordMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public OrderApvRecord selectByPrimaryKey(Long apvRecordSid) {
        return this.orderApvRecordMapper.selectByPrimaryKey(apvRecordSid);
    }

    public List<OrderApvRecord> selectByParams(Criteria example) {
        return this.orderApvRecordMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Long apvRecordSid) {
        return this.orderApvRecordMapper.deleteByPrimaryKey(apvRecordSid);
    }

    public int updateByPrimaryKeySelective(OrderApvRecord record) {
        return this.orderApvRecordMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(OrderApvRecord record) {
        return this.orderApvRecordMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.orderApvRecordMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(OrderApvRecord record, Criteria example) {
        return this.orderApvRecordMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(OrderApvRecord record, Criteria example) {
        return this.orderApvRecordMapper.updateByParams(record, example.getCondition());
    }

    public int insert(OrderApvRecord record) {
        return this.orderApvRecordMapper.insert(record);
    }

    public int insertSelective(OrderApvRecord record) {
        return this.orderApvRecordMapper.insertSelective(record);
    }
    
	/**
	 * 审批订单
	 * 
	 * @param orderId
	 */
    public boolean insertProcessOrderApv(String orderId, Long serviceSid, String checkStatus, String Checkcomments ){
    	int result = 0;
        // TODO
//    	User user = AuthUtil.getAuthUser();
        User user = new User();
    	Criteria criteria = new Criteria();
    	criteria.put("orderId", orderId);
    	criteria.put("serviceSid", serviceSid);
    	List <OrderDetail> orderDetails = orderDetailMapper.selectByParams(criteria);
    	for (OrderDetail orderDetail : orderDetails) {
        	OrderApvRecord orderApvRecord = new OrderApvRecord();

        	orderApvRecord.setOrderId(orderId);
        	orderApvRecord.setServiceSid(serviceSid);
        	orderApvRecord.setDetailSid(orderDetail.getDetailSid());
        	orderApvRecord.setApprovorId(user.getAccount());
        	orderApvRecord.setApproveDate(new Date());
        	orderApvRecord.setApproveStatus(checkStatus);
        	orderApvRecord.setApproveOpinion(Checkcomments);
        	result = result + this.orderApvRecordMapper.insertSelective(orderApvRecord);
		}
    	
    	if (result == orderDetails.size()) {
    		return true;
    	} else {
    		return false;
    	}
    }

}