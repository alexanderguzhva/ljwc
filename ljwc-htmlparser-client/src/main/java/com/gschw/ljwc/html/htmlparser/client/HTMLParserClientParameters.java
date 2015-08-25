package com.gschw.ljwc.html.htmlparser.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Parameters for {@link HTMLParserClient}
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
