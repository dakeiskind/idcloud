package persistance.res.dao;

import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * persist.res.dao
 * Created by Tono on 2016/1/21.
 */
@ContextConfiguration(
        locations = {"classpath:META-INF/spring/idc-spring-persistance.xml"}
)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(
        transactionManager = "transactionManager",
        defaultRollback = false
)
public class ResHostMapperTest {

    @Autowired
    private ResHostMapper resHostMapper;

    @Before
    public void initContext(){
//        this.resHostMapper = (ResHostMapper) context.getBean("resHostMapper");
    }

    @Test
    public void countByHostUUIDTest(){
        ResHost resHost = this.resHostMapper.countByHostUUID("0625D1T");
        Assert.assertNotNull(resHost);
        Assert.assertEquals(resHost.getUuid(), "0625D1T");
    }
}
