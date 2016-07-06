package com.h3c.idcloud.core.rest.user.impl;

import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.core.rest.user.UserRest;
import com.h3c.idcloud.core.service.user.api.UserService;
import com.h3c.idcloud.infra.test.InMemoryRestServer;
import com.h3c.idcloud.infra.test.ProviderTestBase;
import com.h3c.idcloud.infra.test.TestGenericService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static junit.framework.Assert.*;

/**
 * 使用泛用Service进行RESTful接口的测试
 *
 * @author Chaohong.Mao
 */
public class UserRestImplTestWithGenericProvider extends ProviderTestBase {

    public InMemoryRestServer server;

    /**
     * 预加载服务
     */
    @Before
    public void setup() throws IOException {
        // 测试用 通用服务提供者，将其发布出去，
        // 并设置其内部替换的Service的调用方法返回值

        // 创建一个通用的测试provider，替换掉REST接口中调用的所有provider
        TestGenericService genericService = new TestGenericService();
        // 创建返回值
        User user = new User();
        user.setUserSid(100001L);
        user.setAccount("test");
//        user.setAccountType("1");

        // 为这个通用的Service设定方法所对应的返回值，
        // 这里的方法是指provider所提供的方法
        genericService.setResultByMethod("queryUserById", user);
        genericService.setResultByMethod("register", true);
        genericService.setResultByMethod("modifyUser", true);
        genericService.setResultByMethod("login", true);

        // 将通用服务指定给需要替换的ServiceProvider
        super.setGenericProvider(UserService.class, genericService);

        // 取得REST接口
        ApplicationContext testContext = new ClassPathXmlApplicationContext("spring-context-test.xml");
        UserRest userRest = (UserRest) testContext.getBean("userRest");

        // 将REST接口实现发布到内嵌容器中，并启动
        server = InMemoryRestServer.create(userRest);
    }

    @After
    public void afterClass() throws Exception {
        // 停止内嵌容器
        if (server != null) {
            server.close();
        }
    }

    @Test
    public void testGetUserById() throws Exception {
        Long sid = 100001L;
        // GET方法的测试
        Response response = server.newRequest("/users/" + sid).request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String result = response.readEntity(String.class);
        System.out.println(result);
        assertNotNull(result);
        assertTrue(result.contains(sid.toString()));
    }

    @Test
    public void testRegister() throws Exception {
        User user = new User();
        user.setUserSid(100001L);
        user.setAccount("test");
//        user.setAccountType("1");
        // POST方法的测试
        Response response =
                server.newRequest("/users/register").request().post(Entity.json(user));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String result = response.readEntity(String.class);
    }

    @Test
    public void testModifyUser() throws Exception {
        User user = new User();
        user.setUserSid(100001L);
        user.setAccount("test222");
//        user.setAccountType("1");
        user.setPassword("test");
        Response response =
                server.newRequest("/users/modify").request().post(Entity.json(user));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String result = response.readEntity(String.class);
    }

    @Test
    public void testLogin() throws Exception {
        User user = new User();
        user.setUserSid(100001L);
        user.setAccount("test222");
//        user.setAccountType("1");
        user.setPassword("test");
        Response response =
                server.newRequest("/users/login").request().post(Entity.json(user));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String result = response.readEntity(String.class);
    }
}
