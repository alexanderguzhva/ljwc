package com.gschw.ljwc.grabber.datagrabber.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 7/12/15.
 */
public interface IDGDownloadTaskResource {
    @Path("/sessiongenerator")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response generateSession();

    @Path("/session/{sessionIdentity}/download")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response download(@PathParam("sessionIdentity") @NotNull Identity sessionIdentity, @NotNull DGDownloadTask task);

    @Path("/session/{sessionIdentity}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    Response deleteSession(@PathParam("sessionIdentity") @NotNull Identity sessionIdentity);
}
