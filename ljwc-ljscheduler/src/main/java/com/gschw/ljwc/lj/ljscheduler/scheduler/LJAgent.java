package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;

import java.util.List;

/**
 * Created by nop on 7/24/15.
 */
public class LJAgent {
    private Identity ljagentIdentity;

    private List<DownloadableElement> elementsSucceeded;
    private List<DownloadableElement> elementsFailed;

    private long nSucceeded;
    private long nFailed;

    public void addFailure(DownloadableElement element) {
        elementsFailed.add(element);
        nFailed += 1;
    }

    public void addSuccess(DownloadableElement element) {
        elementsSucceeded.add(element);
        nSucceeded += 1;
    }
}
