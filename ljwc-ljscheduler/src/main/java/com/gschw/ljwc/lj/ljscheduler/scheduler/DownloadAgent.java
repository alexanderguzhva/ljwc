package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;

import java.util.List;

/**
 * Created by nop on 7/24/15.
 *
 * This class represents an DownloadAgent-client.
 */
public class DownloadAgent {
    /**
     * Identifies this DownloadAgent client.
     */
    private Identity agentIdentity;

    /**
     * A collection of elements that were successfully downloaded.
     * For debugging purposes only.
     */
    private List<DownloadableElement> elementsSucceeded;

    /**
     * A collection of elements that got failure.
     * For debugging purposes only.
     */
    private List<DownloadableElement> elementsFailed;

    /**
     * Total number of successful attempts.
      */
    private long nSucceeded;

    /**
     * Total number of failures.
     */
    private long nFailed;

    /**
     * Active sequence that is being processed.
     */
    private DownloadSequence activeSequence;

    public DownloadSequence getActiveSequence() {
        return activeSequence;
    }

    public void setActiveSequence(DownloadSequence activeSequence) {
        this.activeSequence = activeSequence;
    }



    public DownloadAgent(Identity agentIdentity) {
        this.agentIdentity = agentIdentity;
    }

    public void addFailure(DownloadableElement element) {
        elementsFailed.add(element);
        nFailed += 1;
    }

    public void addSuccess(DownloadableElement element) {
        elementsSucceeded.add(element);
        nSucceeded += 1;
    }
}
