package com.h3c.idcloud.core.rest.user.impl;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by qct on 2016/1/27.
 */
public class UserRestImplTest {

    @Test
    public void testGetUserById() throws Exception {
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();

        POJOResourceFactory noDefaults = new POJOResourceFactory(UserRestImpl.class);
        dispatcher.getRegistry().addResourceFactory(noDefaults);

        String url = "/rest/users/100";

        MockHttpRequest request = MockHttpRequest.get(url);
        // res being your resource object
//        request.content(res.toJSONString().getBytes());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatus());
    }
}