package org.jclouds.openstack.nova.v2_0.extensions;

import com.google.common.annotations.Beta;
import com.google.common.collect.FluentIterable;

import org.jclouds.Fallbacks;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.nova.v2_0.binders.BindKeyPairToJsonPayload;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;
import org.jclouds.openstack.nova.v2_0.functions.internal.ParseKeyPairs;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.Payload;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
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
 * Created by qct on 2016/4/1.
 *
 * @author qct
 */
@Beta
@Extension(of = ServiceType.COMPUTE, namespace = "http://docs.openstack.org/compute/ext/fake_xml")
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/os-keypairs")
public interface KeyPairApi {
    /**
     * Lists all Key Pairs.
     *
     * @return all Key Pairs
     */
    @Named("keypair:list")
    @GET
    @ResponseParser(ParseKeyPairs.class)
    @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
    FluentIterable<KeyPair> list();

    /**
     * Creates a {@link KeyPair}.
     *
     * @return the created {@link KeyPair}.
     */
    @Named("keypair:create")
    @POST
    @SelectJson("keypair")
    @Produces(MediaType.APPLICATION_JSON)
    @Payload("%7B\"keypair\":%7B\"name\":\"{name}\"%7D%7D")
    KeyPair create(@PayloadParam("name") String name);


    /**
     * Creates a {@link KeyPair} with a public key.
     *
     * @return the created {@link KeyPair}.
     */
    @Named("keypair:create")
    @POST
    @SelectJson("keypair")
    @Produces(MediaType.APPLICATION_JSON)
    @MapBinder(BindKeyPairToJsonPayload.class)
    KeyPair createWithPublicKey(@PayloadParam("name") String name,
                                @PayloadParam("public_key") String publicKey);

    /**
     * Gets a specific {@link KeyPair} by name.
     *
     * @param name the name of the {@link KeyPair}
     * @return the specified {@link KeyPair}, otherwise null.
     */
    @Named("keypair:get")
    @GET
    @Path("/{name}")
    @SelectJson("keypair")
    @Consumes(MediaType.APPLICATION_JSON)
    @Fallback(Fallbacks.NullOnNotFoundOr404.class)
    @Nullable
    KeyPair get(@PathParam("name") String name);

    /**
     * Deletes a {@link KeyPair}.
     *
     * @param name the name of the {@link KeyPair}
     * @return {@code true} if the {@link KeyPair} was deleted, otherwise {@code false}.
     */
    @Named("keypair:delete")
    @DELETE
    @Path("/{name}")
    @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
    boolean delete(@PathParam("name") String name);
}
