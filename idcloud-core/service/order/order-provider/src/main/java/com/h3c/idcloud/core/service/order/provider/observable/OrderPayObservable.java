package com.h3c.idcloud.core.service.order.provider.observable;

import com.h3c.idcloud.core.pojo.dto.order.Order;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;
import com.h3c.idcloud.infra.log.observable.ActionTraceObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Order pay 观察者模式
 * @author swq
 *
 */
public class OrderPayObservable extends Observable{

    private static volatile OrderPayObservable orderPayObservable = null;

	private static Logger logger = LoggerFactory.getLogger(OrderPayObservable.class);

	public static Queue<Order> orderQueue =  new ConcurrentLinkedQueue<Order>();

	private OrderPayObservable(){}
	
	public Queue<Order> getOrderQueue() {
		return orderQueue;
	}

	/**
	 * 单例模式获取OrderPayObservable
	 * @return
     */
	public static OrderPayObservable getOrderPayObservable(){
		if(orderPayObservable == null){
            synchronized (OrderPayObservable.class) {
                if(orderPayObservable == null){
					orderPayObservable = new OrderPayObservable();
                }
            }
        }
        return orderPayObservable;
	}

	/**
	 * 添加订单ID
	 * @param order
	 * @param orderPayObservable
	 */
	public void addOrderIdQueue(Order order,OrderPayObservable orderPayObservable) {
		OrderPayObserver observer = OrderPayObserver.getOrderPayObserver();
		orderPayObservable.addObserver(observer);
		orderQueue.offer(order);
		setChanged();    
        notifyObservers();
	}
}
