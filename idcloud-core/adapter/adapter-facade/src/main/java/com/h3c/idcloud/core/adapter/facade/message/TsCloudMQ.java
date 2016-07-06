package com.h3c.idcloud.core.adapter.facade.message;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TsCloudMQ {

    public static void main(final String... args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Consumer tscloudConsumer = context.getBean(Consumer.class);

        tscloudConsumer.start();
    }
}
