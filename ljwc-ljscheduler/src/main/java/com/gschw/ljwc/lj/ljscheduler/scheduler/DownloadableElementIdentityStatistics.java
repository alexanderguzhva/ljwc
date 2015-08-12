package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;

/**
 * Created by nop on 7/24/15.
 * This class holds statistics about succeeded and failed attempts.
 */
public class DownloadableElementIdentityStatistics {
    /**
     * Number of succeeded attempts.
     */
    private long nSucceeded;

    /**
     * Number of failed attempts.
     */
    private long nFailed;

    /**
     * Adds a failure attempt.
     */
    public void addFailure() {
        nFailed += 1;
    }

    /**
     * Adds a success attempt.
     */
    public void addSuccess() {
        nSucceeded += 1;
    }
}
