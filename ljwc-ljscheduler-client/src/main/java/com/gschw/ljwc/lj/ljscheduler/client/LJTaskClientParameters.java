package com.gschw.ljwc.lj.ljscheduler.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by hadoop on 8/21/15.
 */
public class LJTaskClientParameters {
    @URL
    @NotBlank
    private String serviceUrl;

    public LJTaskClientParameters() {
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
