package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by nop on 7/24/15.
 *
 * This class represents a download attempt.
 */
public class DownloadableElement {
    /**
     * Element that needs to be downloaded.
     */
    private LJDownloadElement element;

    /**
     * Statistics about download attempts.
     */
    private Map<DownloadAgent, DownloadableElementIdentityStatistics> statistics;

    public LJDownloadElement getElement() {
        return element;
    }

    //
    public DownloadableElement(LJDownloadElement element) {
        this.element = element;
        this.statistics = new HashMap<>();
    }

    /**
     * DownloadAgent failed.
     * @param downloadAgent DownloadAgent that failed in downloading this element.
     */
    public void addFailure(DownloadAgent downloadAgent) {
        DownloadableElementIdentityStatistics stat = statistics.get(downloadAgent);
        if (stat == null)
            stat = new DownloadableElementIdentityStatistics();
        stat.addFailure();
    }

    /**
     * DownloadAgent succeeded.
     * @param downloadAgent DownloadAgent that succeeded in download this element.
     */
    public void addSuccess(DownloadAgent downloadAgent) {
        DownloadableElementIdentityStatistics stat = statistics.get(downloadAgent);
        if (stat == null)
            stat = new DownloadableElementIdentityStatistics();
        stat.addSuccess();
    }
}
