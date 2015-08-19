package com.gschw.ljwc.html.htmlparser.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hadoop on 8/19/15.
 */
public class SimpleDownloaderParameters {
    private String serviceURL;

    @JsonProperty
    public String getServiceURL() {
        return serviceURL;
    }

    @JsonProperty
    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    //

    public SimpleDownloaderParameters() {
    }
}
