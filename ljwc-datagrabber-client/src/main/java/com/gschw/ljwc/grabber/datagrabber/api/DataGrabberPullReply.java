package com.gschw.ljwc.grabber.datagrabber.api;

import java.util.List;

/**
 * Created by nop on 6/30/15.
 */
public class DataGrabberPullReply {
    private List<DataGrabberRequest> requests;

    public List<DataGrabberRequest> getRequests() {
        return requests;
    }
}
