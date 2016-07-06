package com.h3c.idcloud.infra.log.aspect;


import com.alibaba.dubbo.rpc.RpcException;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;
import com.h3c.idcloud.infra.log.observable.ActionTraceObservable;
import com.h3c.idcloud.infrastructure.common.constants.BusinessMessageConstants;
import com.h3c.idcloud.infrastructure.common.pojo.BaseServiceObj;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;
import com.h3c.idcloud.infrastructure.common.util.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Calendar;
import java.util.Date;

/**
 * 用户操作痕迹切面实现类
 * Created by swq on 2/3/2016.
 */

@Aspect
@Component
public class ActionTraceAspect {

    private static final Logger logger = LoggerFactory.getLogger(ActionTraceAspect.class);


    /**
     * 定义切点为全部方法拦截
     */
    @Pointcut("execution(* com.h3c.idcloud.core.service..*.*(..))")
    public void allServiceMethodPointcut(){}

    /**
     * 指定注解切点拦截
     */
    @Pointcut("@annotation(ActionTrace)")
    public void actionTraceAnnotationPointcut(){}

    /**
     * 调用前 调用参数验证
     * @param pjp
     * @param actionTrace
     */
    @Before(value="allServiceMethodPointcut() && @annotation(ActionTrace)",argNames = "JoinPoint,ActionTrace")
    public void beforeHandleAction(JoinPoint pjp,ActionTrace actionTrace) throws Throwable{
        Object[] args = pjp.getArgs();
        for(Object arg:args){
            if(StringUtil.isNullOrEmpty(arg))
                throw new RpcException(RpcException.BIZ_EXCEPTION,WebUtil.getMessage(BusinessMessageConstants.ActionTrace.COMMON_ARGS_IS_NULL));
        }
    }

    /**
     * 异常操作拦截处理
     * @param joinPoint
     * @param actionTrace
     * @param throwable
     * @throws Throwable
     */
    @AfterThrowing(value="allServiceMethodPointcut() && @annotation(ActionTrace)",argNames = "JoinPoint,ActionTrace,Throwable",throwing="Throwable")
    public void afterThrowingHandle(JoinPoint joinPoint, ActionTrace actionTrace,Throwable throwable) throws Throwable{
        logger.debug("ActionTraceAspect------afterThrowingHandle------Method:"+MethodSignature.class.cast(joinPoint.getSignature()).getMethod().getName() +
                " Throwable:"+throwable.getMessage());
        BaseServiceObj baseServiceObj = new BaseServiceObj();
        baseServiceObj.setActionDetail(WebUtil.getMessage(actionTrace.actionDetail()));
        LogRecord logInfo = getLogRecord(actionTrace,baseServiceObj,new Date(),new Date());
        logInfo.setActResult("0");
        logInfo.setActFailureReason(throwable.getMessage());
        recordActionTrace(logInfo);
        throw throwable;
    }

    /**
     * 拦截主要操作方法
     * @param proceedingJoinPoint
     * @param actionTrace
     * @return Object
     * @throws Throwable
     */
    @Around(value="allServiceMethodPointcut() && @annotation(ActionTrace)",argNames = "ProceedingJoinPoint,ActionTrace")
    public Object handleAction(ProceedingJoinPoint proceedingJoinPoint, ActionTrace actionTrace) throws Throwable{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Date startDate = Calendar.getInstance().getTime();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        Date endDate = Calendar.getInstance().getTime();
        logger.info("ActionTraceAspect------handleAction------Method:"+MethodSignature.class.cast(proceedingJoinPoint.getSignature()).getMethod().getName()
        +" Args:"+ JsonUtil.toJson(proceedingJoinPoint.getArgs())+" Time:"+stopWatch.getTotalTimeMillis()+" ms");
        //组装操作记录信息
        LogRecord logInfo = getLogRecord(actionTrace,result,startDate,endDate);
        //入库
        recordActionTrace(logInfo);
        return result;
    }

    /**
     * 组装action log
     * @param actionTrace
     * @param result
     * @return LogRecord
     * @throws RpcException
     */
    private LogRecord getLogRecord(ActionTrace actionTrace, Object result,Date startDate,Date endDate) throws RpcException {
        LogRecord logInfo = new LogRecord();
        logInfo.setActTarget(WebUtil.getMessage(actionTrace.actionTarget()));
        logInfo.setActName(WebUtil.getMessage(actionTrace.actionName()));
        if(!actionTrace.isUsedDefaultDetail()){
            //获取自定义操作详情
            if(result instanceof BaseServiceObj){
                BaseServiceObj baseServiceObj =  ( BaseServiceObj)result;
                logInfo.setActDetail(baseServiceObj.getActionDetail());
            }else{
                throw new RpcException(RpcException.BIZ_EXCEPTION,"ActionTrace must be extend BaseServiceObj and return is not null!");
            }
        }else{
            logInfo.setActDetail(actionTrace.actionDetail());
        }
        logInfo.setActStartDate(startDate);
        //TODO 设置当前登陆账号及IP信息
        logInfo.setAccount("SWQ");
        logInfo.setActEndDate(endDate);
        logInfo.setActLevel(actionTrace.actionLevel());
        // 1成功 0失败
        logInfo.setActResult("1");
        logger.info("ActionTraceAspect------getLogRecord------logInfo:"+JsonUtil.toJson(logInfo));
        return logInfo;
    }


    /**
     * 开启独立线程隔离事物，添加action log数据到队列中，使用观察者结合队列实现的简易mq来进行处理
     * @param logInfo
     */
    public void recordActionTrace(LogRecord logInfo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActionTraceObservable logObservable = ActionTraceObservable.getActionTraceObservable();
                logObservable.addLogQueue(logInfo,logObservable);
            }
        }).start();

    }

}
