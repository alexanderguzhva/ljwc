package com.gschw.ljwc.lj.ljscheduler.client;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;

/**
 * Created by nop on 7/15/15.
 */
public interface ILJDownloadTaskClient {
    LJDownloadTask acquireTask(Identity clientIdentity);
    boolean completeElement(Identity elementIdentity, boolean success);
}
