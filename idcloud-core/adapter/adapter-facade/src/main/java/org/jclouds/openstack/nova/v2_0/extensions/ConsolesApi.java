package org.jclouds.openstack.nova.v2_0.extensions;


import com.google.common.annotations.Beta;

import org.jclouds.fallbacks.MapHttp4xxCodesToExceptions;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.nova.v2_0.binders.BindConsoleToJsonPayload;
import org.jclouds.openstack.nova.v2_0.domain.Console;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by qct on 2016/3/31.
 *
 * @author qct
 */
@Beta
@Extension(of = ServiceType.COMPUTE, namespace = "http://docs.openstack.org/compute/ext/fake_xml")
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
public interface ConsolesApi {
    /**
     * Gets the specified server Console.
     *
     * @param serverId Server id
     * @param type     see {@link Console.Type}
     * @return a Console object containing the console url and type.
     */
    @Named("consoles:getConsole")
    @POST
    @Path("/servers/{serverId}/action")
    @SelectJson("console")
    @Produces(MediaType.APPLICATION_JSON)
    @Fallback(MapHttp4xxCodesToExceptions.class)
    @MapBinder(BindConsoleToJsonPayload.class)
    Console getConsole(@PathParam("serverId") String serverId, @PayloadParam("type") Console.Type type);
}

