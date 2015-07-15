package com.gschw.ljwc.storage.client;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 7/10/15.
 */
public class DBStorageClientParameters {
    @URL
    @NotBlank
    private String serviceUrl;

    @JsonProperty
    public String getServiceUrl() {
        return serviceUrl;
    }

    @JsonProperty
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
