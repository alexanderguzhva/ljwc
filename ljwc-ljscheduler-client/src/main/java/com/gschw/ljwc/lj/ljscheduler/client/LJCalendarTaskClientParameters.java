package com.gschw.ljwc.lj.ljscheduler.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by nop on 8/15/15.
 */
public class LJCalendarTaskClientParameters {
    @URL
    @NotBlank
    private String serviceUrl;

    public LJCalendarTaskClientParameters() {
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
