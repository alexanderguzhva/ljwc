package com.gschw.ljwc.grabber.datagrabber.client;

import com.gschw.ljwc.grabber.datagrabber.api.*;

/**
 * Created by nop on 6/30/15.
 */
public interface IDataGrabberPushResultClient {
    DataGrabberPushReply pushReply(DataGrabberIdentity identity, DataGrabberPushRequest request);
}
