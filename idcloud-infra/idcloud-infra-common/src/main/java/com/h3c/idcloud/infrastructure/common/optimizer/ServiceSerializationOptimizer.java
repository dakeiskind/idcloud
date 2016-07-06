package com.h3c.idcloud.infrastructure.common.optimizer;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.h3c.idcloud.infrastructure.common.util.ClassLoaderUtil;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * kryo序列化 想使kryo打到最佳序列化性能，被序列化的类必须存在zero-arg constructor
 * dubbo 使用kryo存在bug，dubbo service在调用另一个dubbo service时 String类型参数，会序列化失败，
 * 第二个dubbo service在序列化的时候会把String 【参数】当做Class进行序列化，从而抛出ClassNotFoundException
 * Created by swq on 4/19/2016.
 */
public class ServiceSerializationOptimizer implements SerializationOptimizer {

    @Override
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<>();

        //pojo扫描全注册
        Set<Class<?>> scanSlasses =  ClassLoaderUtil.scanClassesByPackage("com.h3c.idcloud.core.pojo");
        scanSlasses.forEach(classE ->{
            classes.add(classE);
        });
        return classes;
    }
}
