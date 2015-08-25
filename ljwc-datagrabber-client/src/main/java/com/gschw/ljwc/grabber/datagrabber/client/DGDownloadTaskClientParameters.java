package com.gschw.ljwc.grabber.datagrabber.client;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for {@link DGDownloadTaskClient}
 */
public class DGDownloadTaskClientParameters {
    public DGDownloadTaskClientParameters() {
    }

    @URL
    @NotBlank
    private String serviceUrl;

    @JsonProperty("serviceUrl")
    public String getServiceUrl() {
        return serviceUrl;
    }

    @JsonProperty("serviceUrl")
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
