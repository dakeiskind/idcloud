package com.h3c.idcloud.infra.log.observable;

import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.persist.system.dao.LogRecordMapper;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Observable;
import java.util.Observer;
import java.util.Queue;


/**
 * action trace 观察者模式
 * @author swq
 *
 */
public class ActionTraceObserver implements Observer  {

	private static Logger logger = LoggerFactory.getLogger(ActionTraceObserver.class);
	private static volatile ActionTraceObserver actionTraceObserver = null;
	private static volatile boolean isLock = false;
	
	public ActionTraceObserver(ActionTraceObservable actionTraceObservable) {
		actionTraceObservable.addObserver(this);
	}
	private ActionTraceObserver(){}

	/**
	 * 单例 get ActionTraceObserver
	 * @return
     */
	public static ActionTraceObserver getActionTraceObserver(){
        if(actionTraceObserver == null){
            synchronized (ActionTraceObserver.class) {
                if(actionTraceObserver == null){
                	actionTraceObserver = new ActionTraceObserver();
                }
            }
        }
        return actionTraceObserver;
	}   
	
	/**
	 * log消息消费者，处理队列中的日志
	 * 独立事物不受主方法事物控制，日志数据始终会提交
	 * @param actionTraceObservable
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,noRollbackFor=RpcException.class)
	public static void logObserverConsumer(ActionTraceObservable actionTraceObservable){
		logger.debug("ActionTraceObserver------------logObserverConsumer started isLock="+isLock+" actionTraceObservable="+actionTraceObservable.hashCode());
		if(!isLock){
			while(true){
				isLock = true;
				LogRecord loginfo = actionTraceObservable.getLogQueue().poll();
				logger.debug("ActionTraceObserver------------logObserverConsumer getLogQueue().poll()="+ JsonUtil.toJson(loginfo));
				logger.debug("ActionTraceObserver--------------------------------------logObserverConsumer LogQueue size="+actionTraceObservable.getLogQueue().size());
				logger.debug("ActionTraceObserver--------------------------------------logObserverConsumer get logRecordService:");
				try{
					Object insertResult = null;
					LogRecordMapper logRecordMapper = SpringContextHolder.getBean("logRecordMapper");
					insertResult = logRecordMapper.insert(loginfo);
					logger.debug("ActionTraceObserver--------------------------------------logObserverConsumer insertResult:"+insertResult.toString());
				}catch (RpcException rpce){
					rpce.printStackTrace();
					logger.error("ActionTraceObserver------------logObserverConsumer RpcException loginfo="+JsonUtil.toJson(loginfo)+" message="+rpce.getMessage());
				}catch (Exception e){
					e.printStackTrace();
					logger.error("ActionTraceObserver------------logObserverConsumer RpcException loginfo="+JsonUtil.toJson(loginfo)+" message="+e.getMessage());
				}
				if(actionTraceObservable.getLogQueue().isEmpty()){
					logger.debug("ActionTraceObserver--------------------------------------logObserverConsumer isEmpty");
					isLock = false;
					break;
				}
				
			}
		}
		
	}
	
	@SuppressWarnings("static-access")
	public void update(Observable o, Object arg) {
		ActionTraceObservable actionTraceObservable = (ActionTraceObservable)o;
		Queue<LogRecord> logQueue = actionTraceObservable.getLogQueue();
		logger.debug("ActionTraceObserver--------------------------------------update invoke queue size="+logQueue.size());
		ActionTraceObserver.getActionTraceObserver().logObserverConsumer(actionTraceObservable);
	}

}
