package com.gschw.ljwc.lj.ljscheduler.client;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTaskResult;

/**
 * Created by hadoop on 8/21/15.
 */
public interface ILJSinglePageTaskClient {
    LJSinglePageTask acquireTask(Identity clientIdentity);
    boolean complete(LJSinglePageTaskResult result);
    boolean download(LJSinglePageTask task, long timeoutInMsec);
    boolean download(LJSinglePageTask task);
}
