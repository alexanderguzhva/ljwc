package com.gschw.ljwc.lj.ljscheduler.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import com.gschw.ljwc.lj.ljscheduler.calendar.CalendarKeeper;
import com.gschw.ljwc.lj.ljscheduler.client.ILJCalendarTaskClient;
import com.gschw.ljwc.lj.ljscheduler.resouces.ILJCalendarTaskResource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 8/15/15.
 */
@Path("/")
public class CalendarResource implements ILJCalendarTaskResource {

    private CalendarKeeper calendarKeeper;

    public CalendarResource(CalendarKeeper calendarKeeper) {
        this.calendarKeeper = calendarKeeper;
    }

    @GET
    @Path("calendargenerator")
    @Produces(MediaType.APPLICATION_JSON)
    public Response acquireTask(@QueryParam("clientIdentity") @NotNull Identity clientIdentity) {
        LJCalendarTask task = calendarKeeper.acquireTask(clientIdentity);
        if (task == null)
            return Response.noContent().build();

        return Response.ok().entity(task).build();
    }

    @POST
    @Path("calendarurls")
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
    public Response download(@NotNull LJCalendarTask task, @QueryParam("timeout") Long timeout) {
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
