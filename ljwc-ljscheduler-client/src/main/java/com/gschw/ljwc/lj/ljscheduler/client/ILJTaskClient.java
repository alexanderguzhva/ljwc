package com.gschw.ljwc.lj.ljscheduler.client;

import com.gschw.ljwc.auth.Identity;

/**
 * Created by hadoop on 8/21/15.
 */
public interface ILJTaskClient<T, U> {
    T acquireTask(Identity clientIdentity);
    boolean complete(U result);
    U download(T task, long timeoutInMsec);
    U download(T task);
    boolean enqueue(T task);
}
