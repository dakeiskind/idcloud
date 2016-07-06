package com.h3c.idcloud.core.service.system.provider;

import com.alibaba.dubbo.common.json.JSON;
import com.h3c.idcloud.core.pojo.dto.system.LogRecord;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.service.system.api.LogRecordService;
import com.h3c.idcloud.core.service.ticket.api.TicketService;
import com.h3c.idcloud.infra.test.ProviderTestBase;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by swq on 2/4/2016.
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
public class LogRecordServiceImplTest {

//    /**
//     * 预加载服务
//     */
//    @Before
//    public void setup() {
//        // 使用父类方法暴露provider
//        super.setProvider(LogRecordService.class);
//    }

    @Autowired
    private TicketService ticketService;

    @Test
    public void testTicketSendEmail(){
        String ticketType = WebConstants.ticketType.VM_AUTO_OPEN_FAILURE_TICKET;
        Map<String,Object> params = new HashMap<>();
        params.put("resId","4ec70557-02d3-11e6-852e-0242ac117777");
        params.put("instanceID","cs-zy9f30xg");
        params.put("errorMsg","位置错误");
        this.ticketService.createTicket(ticketType,params);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void insertValue(){
//        LogRecord logRecord = new LogRecord();
//        logRecord.setAccount("test");
//        logRecord.setActName("swq");
//        LogRecordService logRecordService = super.getReference(LogRecordService.class);
//        int result = logRecordService.insert(logRecord);
//        logger.info("---------"+result);
//        assertEquals(result,1);
    }


    @Test
    public void selectByPrimaryKey() throws IOException {
        // 使用父类方法得到引用类
//        LogRecordService logRecordService = super.getReference(LogRecordService.class);
//
//        LogRecord result = logRecordService.selectByPrimaryKey(1001L);
//        logger.info("---------"+result);
//        assertNotNull(result);
    }
}
