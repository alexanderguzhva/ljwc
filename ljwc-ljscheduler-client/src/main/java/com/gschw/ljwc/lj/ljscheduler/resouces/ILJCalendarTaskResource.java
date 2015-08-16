package com.gschw.ljwc.lj.ljscheduler.resouces;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 8/15/15.
 */
public interface ILJCalendarTaskResource {
    @GET
    @Path("calendargenerator")
    @Produces(MediaType.APPLICATION_JSON)
    Response acquireTask(@QueryParam("clientIdentity") @NotNull Identity clientIdentity);

    @POST
    @Path("calendarurls")
    Response completeElement(@NotNull LJCalendarTaskResult result);

    @POST
    @Path("download")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response download(@NotNull LJCalendarTask task, @NotNull Long timeout);
}
