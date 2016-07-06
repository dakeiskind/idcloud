package com.h3c.idcloud.core.service.activiti.listener;

import com.h3c.idclod.core.service.activiti.api.ActivitiService;
import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.service.order.api.OrderService;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 订单请求审批完成监听器
 * 审批完成时更新订单的状态为已审批
 *
 * @author ChengQi
 *
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
public class OrderCompleteListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		ActivitiService activitiService = SpringContextHolder.getBean("activitiServiceImpl");
		OrderService orderService = activitiService.getOrderDubboService();
		//从任务变量中获取orderId
		String orderId = (String)execution.getVariable("orderId");
		//从任务变量中获取完成者
//		String completeUser = (String)delegateTask.getVariable("completeUser");
		String applyUserId = (String)execution.getVariable("applyUserId");
		//查询出订单
		Criteria criteria = new Criteria();
		criteria.put("orderId", orderId);
		Order order = orderService.selectByParams(criteria).get(0);
		//将订单状态设置为已审批
//		order.setStatus(WebConstants.OrderStatusCd.APPROVED);
		//设置订单为已支付
		order.setStatus(WebConstants.OrderStatusCd.PAYED);
		order.setTimePurchase(new Date());
		WebUtil.prepareUpdateParams(order, applyUserId);
		//更新订单信息
		orderService.updateByPrimaryKeySelective(order);
		
	}

}
