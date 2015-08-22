package com.gschw.ljwc.lj.ljscheduler.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTaskResult;
import com.gschw.ljwc.lj.ljscheduler.pscheduler.SinglePageKeeper;
import com.gschw.ljwc.lj.ljscheduler.resouces.ILJSinglePageTaskResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hadoop on 8/21/15.
 */
@Path("/singlepage")
public class LJSinglePageResource implements ILJSinglePageTaskResource {
    private static Logger logger = LoggerFactory.getLogger(LJSinglePageResource.class);

    private SinglePageKeeper singlePageKeeper;

    public LJSinglePageResource(SinglePageKeeper singlePageKeeper) {
        this.singlePageKeeper = singlePageKeeper;
    }

    @GET
    @Path("generator")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response acquireTask(@QueryParam("clientIdentity") @NotNull Identity clientIdentity) {
        LJSinglePageTask task = singlePageKeeper.acquireTask(clientIdentity);
        if (task == null)
            return Response.noContent().build();

        return Response.ok().entity(task).build();
    }

    @POST
    @Path("urls")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response completeElement(@PathParam("elementIdentity") @NotNull Identity elementIdentity, @NotNull LJSinglePageTaskResult result) {
        boolean b = singlePageKeeper.complete(result);
        if (!b)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok().build();
    }

    @POST
    @Path("download")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response download(@NotNull LJSinglePageTask task, @QueryParam("timeout") Long timeout) {
        logger.info("Going to process {}", task.getUrl());

        if (timeout == null) {
            boolean b = singlePageKeeper.download(task);
            if (!b)
                return Response.status(Response.Status.BAD_REQUEST).build();

            return Response.ok().build();
        } else {
            boolean b = singlePageKeeper.download(task, timeout);
            if (!b)
                return Response.status(Response.Status.BAD_REQUEST).build();

            return Response.ok().build();
        }
    }
}
