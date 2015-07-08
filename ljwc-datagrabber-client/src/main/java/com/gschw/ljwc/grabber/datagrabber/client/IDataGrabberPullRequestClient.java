package com.gschw.ljwc.grabber.datagrabber.client;

import com.gschw.ljwc.grabber.datagrabber.api.*;

/**
 * Created by nop on 6/30/15.
 */
public interface IDataGrabberPullRequestClient {
    DataGrabberPullReply pullRequest(DataGrabberIdentity identity, DataGrabberPullRequest request);
}
