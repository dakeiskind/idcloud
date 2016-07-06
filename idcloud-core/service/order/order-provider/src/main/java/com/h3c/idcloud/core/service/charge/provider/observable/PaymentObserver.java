/**
 * 
 */
package com.h3c.idcloud.core.service.charge.provider.observable;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import com.h3c.idcloud.core.service.charge.api.PlatformOnlineRechargeOpService;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author swq
 *
 */
public class PaymentObserver implements Observer {

	private static Logger logger = LoggerFactory.getLogger(PaymentObserver.class);
	private static volatile PaymentObserver payObserver = null;
	private static PlatformOnlineRechargeOpService platformOnlineRechargeOpService;
	private static volatile boolean isLock = false;
	
	public PaymentObserver(PaymentObservable payObservable) {
		payObservable.addObserver(this);
	}
	private PaymentObserver(){};
	
	public static PaymentObserver getPayObserver(){
        if(payObserver == null){
            synchronized (PaymentObserver.class) {
                if(payObserver == null){
                	payObserver = new PaymentObserver();
                }
            }
        }
        return payObserver;
	}   
	
	/**
	 * pay消息消费者
	 * @param payObservable
	 */
	public static void payObserverConsumer(PaymentObservable payObservable){
		logger.info("PaymentObserver------------payObserverConsumer started isLock="+isLock+" payObservable="+payObservable.hashCode());
		if(!isLock){
			while(true){
				isLock = true;
				Map<String,String> payMap = payObservable.getPayQueue().poll();
				logger.info("PaymentObserver------------payObserverConsumer getPayQueue().poll()="+ JsonUtil.toJson(payMap));
				logger.info("PaymentObserver--------------------------------------payObserverConsumer PayQueue size="+payObservable.getPayQueue().size());
				//充值操作
				platformOnlineRechargeOpService = SpringContextHolder.getBean("platformOnlineRechargeOpServiceImpl");
				Long depositeSid = Long.parseLong(payMap.get("depositeSid").toString());
				String thirdPaymentNo = payMap.get("thirdPaymentNo");
				try {
					platformOnlineRechargeOpService.executeDepositeOperation(depositeSid, thirdPaymentNo);
				} catch (Exception e) {
					logger.error("PaymentObserver-----------------------------error"+e);
					payObservable.addPayQueue(payMap, payObservable);
				}
				
				if(payObservable.getPayQueue().isEmpty()){
					logger.info("PaymentObserver--------------------------------------payObserverConsumer isEmpty");
					isLock = false;
					break;
				}
				
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@SuppressWarnings("static-access")
	@Override
	public void update(Observable observable, Object arg) {
		PaymentObservable payObservable = (PaymentObservable)observable;
		Queue<Map<String,String>> payQueue = payObservable.getPayQueue();
		logger.info("PaymentObserver--------------------------------------update invoke queue size="+payQueue.size());
		PaymentObserver.getPayObserver().payObserverConsumer(payObservable);
	}

}
