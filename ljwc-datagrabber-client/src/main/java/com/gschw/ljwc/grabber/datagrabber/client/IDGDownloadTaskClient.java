package com.gschw.ljwc.grabber.datagrabber.client;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadRawResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadRawTask;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;

/**
 * Created by nop on 7/12/15.
 */
public interface IDGDownloadTaskClient {
    DGDownloadResult download(Identity sessionIdentity, DGDownloadTask dgDownloadTask);
    DGDownloadRawResult downloadRaw(Identity sessionIdentity, DGDownloadRawTask dgDownloadRawTask);

    Identity createSession();
    boolean deleteSession(Identity sessionIdentity);
}
