package com.gschw.ljwc.html.htmlparser.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by hadoop on 8/19/15.
 */
public class HTMLParserClientParameters {
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

    //

    public HTMLParserClientParameters() {
    }
}
