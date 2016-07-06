package com.h3c.idcloud.infra.log.observable;

import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import com.h3c.idcloud.infrastructure.common.pojo.BaseServiceObj;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Action trace 观察者模式
 * @author swq
 *
 */
public class ActionTraceObservable extends Observable{
	
    private static volatile ActionTraceObservable actionTraceObservable = null;
	
	private static Logger logger = LoggerFactory.getLogger(ActionTraceObservable.class);
	//日志队列
	public static Queue<LogRecord> logQueue =  new ConcurrentLinkedQueue<LogRecord>();

	private ActionTraceObservable(){}
	
	public Queue<LogRecord> getLogQueue() {
		return logQueue;
	}

	/**
	 * 单例模式获取ActionTraceObservable
	 * @return
     */
	public static ActionTraceObservable getActionTraceObservable(){
		if(actionTraceObservable == null){
            synchronized (ActionTraceObservable.class) {
                if(actionTraceObservable == null){
					actionTraceObservable = new ActionTraceObservable();
                }
            }
        }
        return actionTraceObservable;
	}

	/**
	 * 组装并回去log对象信息
	 * @param actionTarget 操作目标
	 * @param actionName 操作名称
	 * @param actionDetail 操作详细
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param actionLevel 等级
	 * @param actResult 结果
     * @param authUser 验证用户
     * @return
     */
	public static LogRecord getLogRecord(AuthUser authUser,String actionTarget,
										 String actionName,
										 String actionDetail,
										 Date startDate,
										 Date endDate,
										 String actionLevel,
										 String actResult
										 ){
		LogRecord logInfo = new LogRecord();
		logInfo.setActTarget(actionTarget);
		logInfo.setActName(actionName);
		logInfo.setActDetail(actionDetail);
		logInfo.setActStartDate(startDate);
		logInfo.setAccount(authUser.getAccount());
		logInfo.setActEndDate(endDate);
		logInfo.setActLevel(actionLevel);
		logInfo.setActResult(actResult);
		logInfo.setOpIp(authUser.getCurrentRequestIp());
		return logInfo;
	}

	/**
	 * 组装并回去log对象信息
	 * @param actionTarget 操作目标
	 * @param actionName 操作名称
	 * @param actionDetail 操作详细
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param authUser 验证用户
	 * @return
	 */
	public static LogRecord getLogRecord(AuthUser authUser,String actionTarget,
										 String actionName,
										 String actionDetail,
										 Date startDate,
										 Date endDate){
		LogRecord logInfo = new LogRecord();
		logInfo.setActTarget(actionTarget);
		logInfo.setActName(actionName);
		logInfo.setActDetail(actionDetail);
		logInfo.setActStartDate(startDate);
		logInfo.setAccount(authUser.getAccount());
		logInfo.setActEndDate(endDate);
		logInfo.setActLevel(WebConstants.ACTION_LEVEL.LEVEL2);
		logInfo.setOpIp(authUser.getCurrentRequestIp());
		logInfo.setActResult("1");
		return logInfo;
	}

	/**
	 * 添加日志信息到队列
	 * @param loginfo
	 * @param actionTraceObservable
	 */
	public void addLogQueue(LogRecord loginfo,ActionTraceObservable actionTraceObservable) {
		ActionTraceObserver observer = ActionTraceObserver.getActionTraceObserver();
		actionTraceObservable.addObserver(observer);
		logQueue.offer(loginfo);
		setChanged();    
        notifyObservers();
	}
}
