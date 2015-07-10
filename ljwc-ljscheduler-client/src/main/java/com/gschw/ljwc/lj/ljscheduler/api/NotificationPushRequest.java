package com.gschw.ljwc.lj.ljscheduler.api;

import com.gschw.ljwc.grabber.datagrabber.api.DataGrabberRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by nop on 7/6/15.
 */
public class NotificationPushRequest {
    private List<DataGrabberRequest> succeededElements;
    private List<DataGrabberRequest> failedElements;

    @JsonProperty("succeededElements")
    public List<DataGrabberRequest> getSucceededElements() {
        return succeededElements;
    }

    @JsonProperty("failedElements")
    public List<DataGrabberRequest> getFailedElements() {
        return failedElements;
    }

    //
    public void addSucceededElement(DataGrabberRequest succeededRequest) {
        succeededElements.add(succeededRequest);
    }

    //
    public void addFailedElement(DataGrabberRequest failedRequest) {
        failedElements.add(failedRequest);
    }


}
