package com.gschw.ljwc.lj.ljscheduler.scheduler;

/**
 * Created by nop on 7/24/15.
 *
 * Available steps in processing a blog.
 */
public enum DownloadStep {
    /**
     * Undefined.
     */
    UNDEFINED,

    /**
     * A root page needs to be downloaded.
     */
    DOWNLOAD_ROOT,

    /**
     * Root page got downloaded.
     */
    DOWNLOADED_ROOT,

    /**
     * Elements (images, etc.) need to be downloaded.
     */
    DOWNLOAD_ELEMENTS,

    /**
     * Downloaded.
     */
    COMPLETE
}
