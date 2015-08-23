package com.gschw.ljwc.lj.ljreader.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 8/22/15.
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
