package com.gschw.ljwc.grabber.datagrabber.api;

import java.util.Collection;
import java.util.List;

/**
 * Created by nop on 6/30/15.
 */
public class DataGrabberPushRequest {
    private List<DataGrabberResult> requests;

    public List<DataGrabberResult> getRequests() {
        return requests;
    }

    public void addRequest(DataGrabberResult result) {
        requests.add(result);
    }

    public void addRequests(Collection<? extends DataGrabberResult> results) {
        requests.addAll(results);
    }
}
