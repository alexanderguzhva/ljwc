package com.gschw.ljwc.lj.ljscheduler.resouces;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTaskResult;

import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created by hadoop on 8/21/15.
 */
public interface ILJSinglePageTaskResource {
    Response acquireTask(@QueryParam("clientIdentity") @NotNull Identity clientIdentity);
    Response completeElement(@PathParam("elementIdentity") @NotNull Identity elementIdentity, @NotNull LJSinglePageTaskResult result);
    Response download(@NotNull LJSinglePageTask task, @QueryParam("timeout") Long timeout);
}
