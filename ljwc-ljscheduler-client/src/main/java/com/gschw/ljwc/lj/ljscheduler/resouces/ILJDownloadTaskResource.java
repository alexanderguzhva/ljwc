package com.gschw.ljwc.lj.ljscheduler.resouces;

import com.gschw.ljwc.auth.Identity;

import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by nop on 7/15/15.
 */
public interface ILJDownloadTaskResource {
    @GET
    @Path("{clientIdentity}")
    @Produces(MediaType.APPLICATION_JSON)
    Response acquireTask(@PathParam("clientIdentity") @NotNull Identity clientIdentity);

    @POST
    @Path("{elementIdentity}")
    Response completeElement(@PathParam("elementIdentity") @NotNull Identity elementIdentity, boolean success);
}
