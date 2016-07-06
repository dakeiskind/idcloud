package org.jclouds.openstack.nova.v2_0.extensions;

import com.google.common.annotations.Beta;
import com.google.common.collect.FluentIterable;

import org.jclouds.Fallbacks;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Payload;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by qct on 2016/4/7.
 *
 * @author qct
 */
@Beta
@Extension(of = ServiceType.COMPUTE, namespace = "http://docs.openstack.org/compute/ext/fake_xml")
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
public interface FloatingIPApi {
    /**
     * Lists all Floating IP addresses
     *
     * @return all Floating IPs
     */
    @Named("floatingIP:list")
    @GET
    @Path("/os-floating-ips")
    @SelectJson("floating_ips")
    @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
    FluentIterable<FloatingIP> list();

    /**
     * Gets a specific Floating IP address
     *
     * @return all Floating IPs
     */
    @Named("floatingIP:get")
    @GET
    @Path("/os-floating-ips/{id}")
    @SelectJson("floating_ip")
    @Fallback(Fallbacks.NullOnNotFoundOr404.class)
    @Nullable
    FloatingIP get(@PathParam("id") String id);

    /**
     * Allocates a Floating IP address
     *
     * @return a newly created FloatingIP
     */
    @Named("floatingIP:create")
    @POST
    @Path("/os-floating-ips")
    @SelectJson("floating_ip")
    @Produces(MediaType.APPLICATION_JSON)
    @Payload("{}")
    @Fallback(Fallbacks.NullOnNotFoundOr404.class)
    @Nullable
    FloatingIP create();

    /**
     * Allocates a Floating IP address from a pool
     *
     * @param pool
     *         Pool to allocate IP address from
     * @return a newly created FloatingIP
     */
    @Named("floatingIP:allocateFromPool")
    @POST
    @Path("/os-floating-ips")
    @SelectJson("floating_ip")
    @Produces(MediaType.APPLICATION_JSON)
    @Payload("%7B\"pool\":\"{pool}\"%7D")
    @Fallback(Fallbacks.NullOnNotFoundOr404.class)
    @Nullable
    FloatingIP allocateFromPool(@PayloadParam("pool") String pool);

    /**
     * Deletes a Floating IP address
     *
     * @param id
     *           the Floating IP id
     */
    @Named("floatingIP:delete")
    @DELETE
    @Path("/os-floating-ips/{id}")
    void delete(@PathParam("id") String id);

    /**
     * Adds a Floating IP address to a Server
     *
     * @param serverId
     *           the server id
     * @param address
     *           the IP address to add
     *
     *           NOTE: Possibly move this to ServerApi?
     */
    @Named("floatingIP:add")
    @POST
    @Path("/servers/{id}/action")
    @Produces(MediaType.APPLICATION_JSON)
    @Payload("%7B\"addFloatingIp\":%7B\"address\":\"{address}\"%7D%7D")
    void addToServer(@PayloadParam("address") String address, @PathParam("id") String serverId);

    /**
     * Adds a Floating IP address to a Server
     *
     * @param serverId
     *           the server id
     * @param address
     *           the IP address to add
     * @param fixedAddress
     *           the fixed address to bind
     *
     *           NOTE: Possibly move this to ServerApi?
     */
    @Named("floatingIP:add")
    @POST
    @Path("/servers/{id}/action")
    @Produces(MediaType.APPLICATION_JSON)
    @Payload("%7B\"addFloatingIp\":%7B\"address\":\"{address}\"%2C\"fixed_address\":\"{fixed_address}\"%7D%7D")
    void addToServer(@PayloadParam("address") String address,
                     @PathParam("id") String serverId,
                     @PayloadParam("fixed_address") String fixedAddress);

    /**
     * Removes a Floating IP address from a Server
     *
     * @param serverId
     *           the server id
     * @param address
     *           the IP address to remove
     *
     *           NOTE: Possibly move this to ServerApi?
     */
    @Named("floatingIP:remove")
    @POST
    @Path("/servers/{id}/action")
    @Produces(MediaType.APPLICATION_JSON)
    @Payload("%7B\"removeFloatingIp\":%7B\"address\":\"{address}\"%7D%7D")
    void removeFromServer(@PayloadParam("address") String address, @PathParam("id") String serverId);
}