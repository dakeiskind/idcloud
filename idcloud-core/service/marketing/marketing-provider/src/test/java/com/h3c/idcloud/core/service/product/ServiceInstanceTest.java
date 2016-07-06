package com.h3c.idcloud.core.service.product;

import com.h3c.idcloud.core.persist.res.dao.ResStatisticsMapper;
import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.service.product.api.ServiceInstanceService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.infra.test.ProviderTestBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by swq on 4/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {
//                "classpath:META-INF/spring/idc-spring-persistance.xml",
                "classpath:spring-context-test.xml"
        }
)
@Transactional
@TransactionConfiguration(
        transactionManager = "transactionManager",
        defaultRollback = false
)
public class ServiceInstanceTest  extends ProviderTestBase{

    @Autowired
    ServiceInstanceService serviceInstanceService;

    @Autowired
    ResStatisticsMapper resStatisticsMapper;

//    /**
//     * 预加载服务
//     */
//    @Before
//    public void setup() {
//        // 使用父类方法暴露provider
//        super.setProvider(TicketService.class);
//    }

    @Test
    public void serviceInstanceHistoryTest(){

//        ServiceInstance serviceInstance = serviceInstanceService.selectByPrimaryKey(10331L);
//        serviceInstanceService.deleteServiceInstance(serviceInstance);
        Map<String,Object> res = resStatisticsMapper.selectStatisticsVmInfo("swq");
        Map<String,Object> resFloatingIp = resStatisticsMapper.selectStatisticsFloatingIpInfo(1437L);
        System.out.println(res);
        System.out.println(resFloatingIp);
    }

    @Test
    public void resInvokeErrorCallbackTest(){
        serviceInstanceService.resInvokeErrorCallback("4ec70557-02d3-11e6-852e-0242ac117777","cs-zy9f30xg","RES-VM","位置错误消息");
    }

}
