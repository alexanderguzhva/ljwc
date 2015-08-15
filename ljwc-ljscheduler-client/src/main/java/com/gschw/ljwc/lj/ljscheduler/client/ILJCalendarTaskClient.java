package com.gschw.ljwc.lj.ljscheduler.client;

import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.auth.Identity;

/**
 * Created by nop on 8/12/15.
 */
public interface ILJCalendarTaskClient {
    LJCalendarTask acquireTask(Identity clientIdentity);
    boolean complete(LJCalendarTaskResult result);
    boolean download(LJCalendarTask task, long timeoutInMsec);
    boolean download(LJCalendarTask task);
}
