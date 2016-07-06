package org.jclouds.openstack.nova.v2_0.extensions;



import com.google.common.collect.FluentIterable;

import org.jclouds.Fallbacks;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.nova.v2_0.domain.VolumeAttachment;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.WrapWith;

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
 * Provides access to the OpenStack Compute (Nova) Volume Attachments Extension API.
 *
 * This API strictly handles attaching Volumes to Servers. To create and manage Volumes you need to use the Cinder API.
 * @see org.jclouds.openstack.cinder.v1.features.VolumeApi
 */
/**
 * Created by qct on 2016/4/21.
 *
 * @author qct
 */
@Extension(of = ServiceType.COMPUTE, namespace = "http://docs.openstack.org/compute/ext/fake_xml")
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/servers")
public interface VolumeAttachmentApi {
    /**
     * Lists Volume Attachments for a given Server.
     *
     * @param serverId The ID of the Server
     * @return All VolumeAttachments for the Server
     */
    @Named("volumeAttachment:list")
    @GET
    @Path("/{serverId}/os-volume_attachments")
    @SelectJson("volumeAttachments")
    @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
    FluentIterable<VolumeAttachment> listAttachmentsOnServer(@PathParam("serverId") String serverId);

    /**
     * Gets a specific Volume Attachment for a Volume and Server.
     *
     * @param volumeId The ID of the Volume
     * @param serverId The ID of the Server
     * @return The Volume Attachment.
     */
    @Named("volumeAttachment:get")
    @GET
    @Path("/{serverId}/os-volume_attachments/{id}")
    @SelectJson("volumeAttachment")
    @Fallback(Fallbacks.NullOnNotFoundOr404.class)
    @Nullable
    VolumeAttachment getAttachmentForVolumeOnServer(@PathParam("id") String volumeId,
                                                    @PathParam("serverId") String serverId);

    /**
     * Attaches a Volume to a Server.
     *
     * Note: If you are using KVM as your hypervisor then the actual device name in the Server will be different than
     * the one specified. When the Server sees a new device, it picks the next available name (which in most cases is
     * /dev/vdc) and the disk shows up there on the Server.
     *
     * @param serverId The ID of the Server
     * @param volumeId The ID of the Volume
     * @param device The name of the device this Volume will be identified as in the Server (e.g. /dev/vdc)
     * @return The Volume Attachment.
     */
    @Named("volumeAttachment:attach")
    @POST
    @Path("/{serverId}/os-volume_attachments")
    @SelectJson("volumeAttachment")
    @Produces(MediaType.APPLICATION_JSON)
    @WrapWith("volumeAttachment")
    VolumeAttachment attachVolumeToServerAsDevice(@PayloadParam("volumeId") String volumeId,
                                                  @PathParam("serverId") String serverId, @PayloadParam("device") String device);

    /**
     * Detaches a Volume from a server.
     *
     * Note: Make sure you've unmounted the volume first. Failure to do so could result in failure or data loss.
     *
     * @param volumeId The ID of the Volume
     * @param serverId The ID of the Server
     * @return true if successful
     */
    @Named("volumeAttachment:detach")
    @DELETE
    @Path("/{serverId}/os-volume_attachments/{id}")
    @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
    boolean detachVolumeFromServer(@PathParam("id") String volumeId,
                                   @PathParam("serverId") String serverId);
}