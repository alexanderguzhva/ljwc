package com.gschw.ljwc.lj.ljscheduler.resouces;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

/**
 * Created by nop on 7/15/15.
 */
public interface ILJDownloadTaskResource {
    @GET
    @Path("taskgenerator")
    @Produces(MediaType.APPLICATION_JSON)
    Response acquireTask(@QueryParam("clientIdentity") @NotNull Identity clientIdentity);

    @POST
    @Path("{elementIdentity}")
    Response completeElement(@PathParam("elementIdentity") @NotNull Identity elementIdentity, @NotNull Boolean success);

    @POST
    @Path("download")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response download(@NotNull LJDownloadTask task, @NotNull Long timeout);
}
