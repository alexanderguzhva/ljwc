package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;

/**
 * Created by nop on 7/24/15.
 */
public class DownloadableElementIdentityStatistics {
    private long nSucceeded;
    private long nFailed;

    public void addFailure() {
        nFailed += 1;
    }

    public void addSuccess() {
        nSucceeded += 1;
    }
}
