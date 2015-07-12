package com.gschw.ljwc.grabber.datagrabber.client;

import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;

/**
 * Created by nop on 7/12/15.
 */
public interface IDGDownloadTaskClient {
    DGDownloadResult download(DGDownloadTask dgDownloadTask);
}
