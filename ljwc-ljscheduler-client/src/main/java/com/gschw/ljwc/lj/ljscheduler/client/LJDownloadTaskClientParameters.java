package com.gschw.ljwc.lj.ljscheduler.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class LJDownloadTaskClientParameters {
    @URL
    @NotBlank
    private String serviceUrl;

    public LJDownloadTaskClientParameters() {
    }

    @JsonProperty("serviceUrl")
    public String getServiceUrl() {
        return serviceUrl;
    }

    @JsonProperty("serviceUrl")
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
