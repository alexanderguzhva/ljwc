package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;
import com.gschw.ljwc.auth.Identity;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by nop on 7/24/15.
 */
public class DownloadableElement {
    private LJDownloadElement element;

    // here goes the statistics for different ljagents
    private Map<LJAgent, DownloadableElementIdentityStatistics> statistics;

    public LJDownloadElement getElement() {
        return element;
    }

    //
    public DownloadableElement(LJDownloadElement element) {
        this.element = element;
        this.statistics = new HashMap<>();
    }

    //
    public void addFailure(LJAgent agent) {
        DownloadableElementIdentityStatistics stat = statistics.get(agent);
        if (stat == null)
            stat = new DownloadableElementIdentityStatistics();
        stat.addFailure();
    }

    //
    public void addSuccess(LJAgent agent) {
        DownloadableElementIdentityStatistics stat = statistics.get(agent);
        if (stat == null)
            stat = new DownloadableElementIdentityStatistics();
        stat.addSuccess();
    }
}
