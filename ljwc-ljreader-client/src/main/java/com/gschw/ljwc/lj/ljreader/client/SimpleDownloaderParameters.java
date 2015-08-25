package com.gschw.ljwc.lj.ljreader.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for {@link SimpleDownloader}
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
