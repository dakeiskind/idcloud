package com.h3c.idcloud.core.service.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * Created by swq on 4/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {
                "classpath:spring-context-test.xml"
        }
)
@Transactional
public class BaseCodeTest {

    @Test
    public void swqBaseTest(){
        Calendar time = Calendar.getInstance();
        System.out.println("getActualMinimum=============="+time.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println("getActualMaximum=============="+time.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("getMinimum=============="+time.getMinimum(Calendar.DAY_OF_MONTH));
        System.out.println("getMaximum=============="+time.getMaximum(Calendar.DAY_OF_MONTH));

    }
}
