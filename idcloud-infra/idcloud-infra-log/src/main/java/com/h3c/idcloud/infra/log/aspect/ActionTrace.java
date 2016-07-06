package com.h3c.idcloud.infra.log.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户痕迹自定义注解
 * Created by swq on 2/3/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
public @interface ActionTrace {

    //操作模块
    String actionTarget() default "";
    //操作名称
    String actionName() default "";
    //操作级别
    String actionLevel() default "1";
    //操作具体内容
    String actionDetail() default "";
    //是否使用注解描述的操作详细，默认true
    boolean isUsedDefaultDetail() default true;

}