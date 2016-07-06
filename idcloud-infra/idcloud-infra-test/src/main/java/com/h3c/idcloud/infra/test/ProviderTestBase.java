package com.h3c.idcloud.infra.test;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Service的测试基类
 *
 * @author Chaohong.Mao
 */
//@ContextConfiguration("classpath*:idc-spring-persistance.xml")
public class ProviderTestBase extends AbstractTransactionalJUnit4SpringContextTests {

    /** 服务暴露地址 */
    protected static String REGISTRY_ADDR = "multicast://224.10.10.1:12345";
    /** 服务默认版本号 */
    protected static String VERSION = "1.0.0";

    /**
     * 设置服务提供者
     *
     * @param <T> clazz 服务提供者API（interface）
     */
    protected <T> void setProvider(Class<T> clazz) {
        // 封装了所有与注册中心及服务提供方连接
        ServiceConfig<T> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig(clazz.getSimpleName() + "-provider"));
        service.setRegistry(new RegistryConfig(REGISTRY_ADDR));
        service.setInterface(clazz.getName()); // 弱类型接口名
        service.setVersion(VERSION);

        ApplicationContext testContext = new ClassPathXmlApplicationContext("spring-context-test.xml");
        service.setRef(testContext.getBean(clazz));
        // 暴露及注册服务
        service.export();
    }

    /**
     * 设置通用服务提供者
     *
     * @param genericService 通用服务提供者
     */
    protected void setGenericProvider(Class<?> facadeClass, TestGenericService genericService) {
        ServiceConfig<TestGenericService> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig(genericService.getClass().getSimpleName() + "-provider"));
        service.setRegistry(new RegistryConfig(REGISTRY_ADDR));
        service.setInterface(facadeClass.getName()); // 弱类型接口名
        service.setVersion(VERSION);
        service.setRef(genericService);
        // 暴露及注册服务
        service.export();
    }

    /**
     * Sets providers.
     *
     * @param clazzs the clazzs
     */
    protected void setProviders(Class<?>... clazzs) {
        if (clazzs != null && clazzs.length != 0) {
            for (Class clazz : clazzs) {
                setProvider(clazz);
            }
        }
    }

    /**
     * 获取服务消费者
     *
     * @param clazz 服务API（interface）
     * @return 消费者
     */
    protected <T> T getReference(Class<T> clazz) {
        ReferenceConfig<T> ref = new ReferenceConfig<>();
        ref.setApplication(new ApplicationConfig(clazz.getSimpleName() + "-consumer"));
        ref.setInterface(clazz);
        ref.setVersion(VERSION);
        ref.setRegistry(new RegistryConfig(REGISTRY_ADDR));
        return ref.get();
    }
}
