package com.gschw.ljwc.lj.ljscheduler.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.resouces.ILJDownloadTaskResource;
import com.gschw.ljwc.lj.ljscheduler.scheduler.TasksKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

/**
 * Created by nop on 7/19/15.
 */
@Path("/task")
public class LJDownloadTaskResource implements ILJDownloadTaskResource {
    private static Logger logger = LoggerFactory.getLogger(LJDownloadTaskResource.class);

    private TasksKeeper tasksKeeper;

    public LJDownloadTaskResource(TasksKeeper tasksKeeper) {
        this.tasksKeeper = tasksKeeper;
    }

    @GET
    @Path("generator")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response acquireTask(@QueryParam("clientIdentity") @NotNull Identity clientIdentity) {
        LJDownloadTask task = tasksKeeper.acquireTask(clientIdentity);
        if (task == null)
            return Response.noContent().build();

        return Response.ok().entity(task).build();
    }

    @POST
    @Path("element/{elementIdentity}")
    @Override
    public Response completeElement(@PathParam("elementIdentity") @NotNull Identity elementIdentity, @NotNull Boolean success) {
        boolean bResult = tasksKeeper.completeElement(elementIdentity, success);
        if (!bResult)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok().build();
    }


    @POST
    @Path("download")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response download(@NotNull LJDownloadTask task, @QueryParam("timeout") Long timeout) {
        boolean b = tasksKeeper.download(task, timeout);
        if (!b)
            return Response.serverError().build();

        return Response.ok().build();
    }

}
