package com.gschw.ljwc.lj.ljscheduler.client;

import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;

/**
 * Created by nop on 8/12/15.
 */
public interface ILJCalendarTaskClient {
    LJCalendarTask acquireTask(Identity clientIdentity);
    boolean complete(LJCalendarTaskResult result);
}
