/**
 * 
 */
package com.h3c.idcloud.core.service.charge.provider.observable;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author swq
 *
 */
public class PaymentObservable extends Observable {
	
	private static volatile PaymentObservable payObservable = null;
	
	private static Logger logger = LoggerFactory.getLogger(PaymentObservable.class);
	//支付队列
	public static BlockingQueue<Map<String,String>> payQueue =  new LinkedBlockingQueue<Map<String,String>>();

	private PaymentObservable(){}
	
	public BlockingQueue<Map<String,String>> getPayQueue() {
		return payQueue;
	}
	
	public static PaymentObservable getPayObservable(){
		if(payObservable == null){
            synchronized (PaymentObserver.class) {
                if(payObservable == null){
                	payObservable = new PaymentObservable();
                }
            }
        }
        return payObservable;
	}

	public void addPayQueue(Map<String,String> payQueueMap,PaymentObservable payObservable) {
		PaymentObserver payObserver = PaymentObserver.getPayObserver();
		payObservable.addObserver(payObserver);
		logger.info("PaymentObservable=========================addPayQueue info="+ JsonUtil.toJson(payQueueMap));
		payQueue.offer(payQueueMap);
		setChanged();    
        notifyObservers();
	}
}
