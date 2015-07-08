package com.gschw.ljwc.lj.ljscheduler.client;

import com.gschw.ljwc.grabber.datagrabber.api.*;
import com.gschw.ljwc.grabber.datagrabber.client.*;

import javax.ws.rs.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 6/29/15.
 */
public class LJSchedulerClient implements IDataGrabberPullRequestClient, IDataGrabberPushResultClient {
    private static Logger logger = LoggerFactory.getLogger(LJSchedulerClient.class);

    private Client client;

    //
    public DataGrabberPullReply pullRequest(DataGrabberIdentity identity, DataGrabberPushRequest request) {
        return null;
    }

    //
    public DataGrabberPushReply pushReply(DataGrabberIdentity identity, DataGrabberPushRequest request) {
        return null;
    }

}
