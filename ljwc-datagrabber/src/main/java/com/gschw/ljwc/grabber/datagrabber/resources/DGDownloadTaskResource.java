package com.gschw.ljwc.grabber.datagrabber.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import com.gschw.ljwc.grabber.datagrabber.core.Grabber;
import com.gschw.ljwc.grabber.datagrabber.core.GrabberResult;
import com.gschw.ljwc.grabber.datagrabber.core.GrabbersKeeper;
import com.gschw.ljwc.uploader.api.DGUploadTask;
import com.gschw.ljwc.uploader.client.DGUploaderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 7/12/15.
 */
@Path("/")
public class DGDownloadTaskResource implements IDGDownloadTaskResource {
    private static Logger logger = LoggerFactory.getLogger(DGDownloadTaskResource.class);

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

        return Response.ok().entity(identity).build();
    }

    @Path("/session/{sessionIdentity}/download")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response download(@PathParam("sessionIdentity") @NotNull Identity sessionIdentity, @NotNull DGDownloadTask task) {
        Grabber grabber = keeper.getBySession(sessionIdentity);
        if (grabber == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        ////
        GrabberResult result = grabber.grab(task.getUrl());
        if (result == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        ////
        DGUploadTask uploadTask =
                new DGUploadTask(
                        task.getTaskIdentity(),
                        task.getUrl(),
                        result.getData());
        boolean uploadResult = uploaderClient.upload(task.getUploadServiceURL(), uploadTask);

        logger.info("uploadClient returned {}", uploadResult);

        DGDownloadResult downloadResult = new DGDownloadResult(task.getTaskIdentity(), uploadResult);
        return Response.ok().entity(downloadResult).build();
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
