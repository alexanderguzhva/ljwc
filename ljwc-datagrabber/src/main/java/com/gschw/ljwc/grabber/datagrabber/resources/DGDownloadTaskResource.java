package com.gschw.ljwc.grabber.datagrabber.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import com.gschw.ljwc.grabber.datagrabber.core.Grabber;
import com.gschw.ljwc.grabber.datagrabber.core.GrabberResult;
import com.gschw.ljwc.grabber.datagrabber.core.GrabbersKeeper;
import com.gschw.ljwc.uploader.api.DGUploadTask;
import com.gschw.ljwc.uploader.client.DGUploaderClient;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 7/12/15.
 */
public class DGDownloadTaskResource implements IDGDownloadTaskResource {
    private GrabbersKeeper keeper;

    private DGUploaderClient uploaderClient;

    public DGDownloadTaskResource(GrabbersKeeper keeper, DGUploaderClient uploaderClient) {
        this.keeper = keeper;
        this.uploaderClient = uploaderClient;
    }

    @Path("/sessiongenerator")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateSession() {
        Identity identity = keeper.createSession();
        if (identity == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok().build();
    }

    @Path("/session/{sessionIdentity}/download")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response download(@PathParam("sessionIdentity") @NotNull Identity sessionIdentity, @NotNull DGDownloadTask task) {
        Grabber grabber = keeper.getBySession(sessionIdentity);
        if (grabber == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        ////
        GrabberResult result = grabber.grab(task.getUrl());
        if (result == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        //// todo
        DGUploadTask uploadTask =
                new DGUploadTask(
                        task.getTaskIdentity(),
                        task.getUrl(),
                        result.getData());
        boolean uploadResult = uploaderClient.uploadResult(task.getUploadServiceURL(), uploadTask);

        if (uploadResult)
            return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("/session/{sessionIdentity}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSession(@PathParam("sessionIdentity") @NotNull Identity sessionIdentity) {
        if (keeper.deleteSession(sessionIdentity))
            return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}