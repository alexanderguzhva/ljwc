package com.gschw.ljwc.lj.ljscheduler.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import com.gschw.ljwc.lj.ljscheduler.calendar.CalendarKeeper;
import com.gschw.ljwc.lj.ljscheduler.resouces.ILJCalendarTaskResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 8/15/15.
 */
@Path("/calendar")
public class CalendarResource implements ILJCalendarTaskResource {
    private static Logger logger = LoggerFactory.getLogger(CalendarResource.class);

    private CalendarKeeper calendarKeeper;

    public CalendarResource(CalendarKeeper calendarKeeper) {
        this.calendarKeeper = calendarKeeper;
    }

    @GET
    @Path("generator")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response acquireTask(@QueryParam("clientIdentity") @NotNull Identity clientIdentity) {
        LJCalendarTask task = calendarKeeper.acquireTask(clientIdentity);
        if (task == null)
            return Response.noContent().build();

        return Response.ok().entity(task).build();
    }

    @POST
    @Path("urls")
    @Override
    public Response completeElement(@NotNull LJCalendarTaskResult result) {
        boolean b = calendarKeeper.complete(result);
        if (!b)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok().build();
    }

    @POST
    @Path("download")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response download(@NotNull LJCalendarTask task, @QueryParam("timeout") Long timeout) {
        logger.info("Going to process {}", task.getUrl());

        if (timeout == null) {
            boolean b = calendarKeeper.download(task);
            if (!b)
                return Response.status(Response.Status.BAD_REQUEST).build();

            return Response.ok().build();
        } else {
            boolean b = calendarKeeper.download(task, timeout);
            if (!b)
                return Response.status(Response.Status.BAD_REQUEST).build();

            return Response.ok().build();
        }
    }
}
