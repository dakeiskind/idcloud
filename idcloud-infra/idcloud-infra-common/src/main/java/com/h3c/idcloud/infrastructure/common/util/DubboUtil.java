package com.h3c.idcloud.infrastructure.common.util;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;

/**
 * DubboAPI工具
 *
 * @author Chaohong.Mao
 */
public class DubboUtil {

    /** 默认注册中心 */
    public static final String DEFAULT_URL = PropertiesUtil.getProperty("dubbo.registry.address");
    /** 默认服务版本. */
    public static final String DEFAULT_VERSION = PropertiesUtil.getProperty("dubbo.service.version");;
    /** 默认Comsumer 名称. */
    public static final String DEFAULT_COMSUMER_APP_NAME = PropertiesUtil.getProperty("dubbo.common.consumer");;

    /**
     * 获得 服务引用.
     *
     * @param <T>     服务接口泛型
     * @param IFClazz 暴露的服务接口类
     * @param appName 消费方应用名（用于计算依赖关系，不是匹配条件，不要与提供方一样）
     * @param url     注册中心地址
     * @param version 服务版本
     * @return        服务实例
     */
    public static <T> T getReference(Class<T> IFClazz, String appName, String url, String version) {
        ReferenceConfig<T> ref = new ReferenceConfig<>();
        ref.setApplication(new ApplicationConfig(appName));
        ref.setInterface(IFClazz);
        ref.setVersion(version);
        ref.setRegistry(new RegistryConfig(url));
        return ReferenceConfigCache.getCache().get(ref);
    }

    /**
     * 获得 服务引用.
     *
     * @param <T>     服务接口泛型
     * @param IFClazz 暴露的服务接口类
     * @param url     注册中心地址
     * @param version 服务版本
     * @return        服务实例
     */
    public static <T> T getReference(Class<T> IFClazz, String url, String version) {
        return getReference(IFClazz, DEFAULT_COMSUMER_APP_NAME, url, version);
    }

    /**
     * 获得 服务引用.
     *
     * @param <T>     服务接口泛型
     * @param IFClazz 暴露的服务接口类
     * @return        服务实例
     */
    public static <T> T getReference(Class<T> IFClazz) {
        return getReference(IFClazz, DEFAULT_URL, DEFAULT_VERSION);
    }

    /**
     * 获得 服务引用.
     *
     * @param <T>     服务接口泛型
     * @param className 暴露的服务接口类名
     * @return        服务实例
     */
    public static <T> T getReference(String className) {
        ReferenceConfig<T> ref = new ReferenceConfig<>();
        ref.setApplication(new ApplicationConfig(DEFAULT_COMSUMER_APP_NAME));
        ref.setInterface(className);
        ref.setVersion(DEFAULT_VERSION);
        ref.setRegistry(new RegistryConfig(DEFAULT_URL));
        return ReferenceConfigCache.getCache().get(ref);
    }
}
