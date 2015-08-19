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

    public HTMLParserClientParameters() {
    }
}
