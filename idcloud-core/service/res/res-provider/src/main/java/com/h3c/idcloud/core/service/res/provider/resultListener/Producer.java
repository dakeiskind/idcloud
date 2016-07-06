package com.h3c.idcloud.core.service.res.provider.resultListener;


import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;

/**
 * Producer 类.
 *
 * @author Chaohong.Mao
 */
public class Producer {

	public void producerInitialized() {
		MQHelper.startListenerForProducer(SpringContextHolder.getApplicationContext().getBean("vmResultListener"), "vm");
		MQHelper.startListenerForProducer(SpringContextHolder.getApplicationContext().getBean("vdResultListener"), "disk");
		MQHelper.startListenerForProducer(SpringContextHolder.getApplicationContext().getBean("networkResultListener"), "network");
	}
}
