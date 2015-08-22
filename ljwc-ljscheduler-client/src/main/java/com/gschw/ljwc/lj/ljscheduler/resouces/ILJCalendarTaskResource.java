package com.gschw.ljwc.lj.ljscheduler.resouces;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 8/15/15.
 */
public interface ILJCalendarTaskResource {
    Response acquireTask(@QueryParam("clientIdentity") @NotNull Identity clientIdentity);
    Response completeElement(@NotNull LJCalendarTaskResult result);
    Response download(@NotNull LJCalendarTask task, @QueryParam("timeout") Long timeout);
}
