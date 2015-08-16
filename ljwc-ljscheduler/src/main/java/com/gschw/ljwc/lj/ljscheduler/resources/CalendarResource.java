package com.gschw.ljwc.lj.ljscheduler.resources;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import com.gschw.ljwc.lj.ljscheduler.client.ILJCalendarTaskClient;

import javax.ws.rs.Path;

/**
 * Created by nop on 8/15/15.
 */
@Path("/")
public class CalendarResource implements ILJCalendarTaskClient {

    @Override
    public LJCalendarTask acquireTask(Identity clientIdentity) {
        return null;
    }

    @Override
    public boolean complete(LJCalendarTaskResult result) {
        return false;
    }

    @Override
    public boolean download(LJCalendarTask task, long timeoutInMsec) {
        return false;
    }

    @Override
    public boolean download(LJCalendarTask task) {
        return false;
    }
}
