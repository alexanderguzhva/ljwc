package com.gschw.ljwc.html.htmlparser.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hadoop on 8/19/15.
 */
public class SimpleDownloaderParameters {
    private String serviceUrl;

    @JsonProperty
    public String getServiceUrl() {
        return serviceUrl;
    }

    @JsonProperty
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    //
    public SimpleDownloaderParameters() {
    }
}
