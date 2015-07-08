package com.gschw.ljwc.grabber.datagrabber.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by nop on 6/29/15.
 */
public class DataGrabberRequest {
    private String url;

    @JsonCreator
    public DataGrabberRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    @JsonProperty("uri")
    public String getUrl() {
        return url;
    }
}
