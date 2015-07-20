package com.gschw.ljwc.lj.ljscheduler.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.resouces.ILJDownloadTaskResource;
import com.gschw.ljwc.lj.ljscheduler.scheduler.TasksKeeper;

import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by nop on 7/19/15.
 */
@Path("/task")
public class LJDownloadTaskResource implements ILJDownloadTaskResource {

    private TasksKeeper tasksKeeper;

    public LJDownloadTaskResource(TasksKeeper tasksKeeper) {
        this.tasksKeeper = tasksKeeper;
    }

    @Override
    @GET
    @Path("{clientIdentity}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response acquireTask(@PathParam("clientIdentity") @NotNull Identity clientIdentity) {
        LJDownloadTask task = tasksKeeper.acquireTask(clientIdentity);
        if (task == null)
            return Response.noContent().build();

        return Response.ok().entity(task).build();
    }

    @Override
    @POST
    @Path("{elementIdentity}")
    public Response completeElement(@PathParam("elementIdentity") @NotNull Identity elementIdentity, boolean success) {
        return null;
    }
}
