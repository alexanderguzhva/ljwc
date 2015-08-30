package com.gschw.ljwc.lj.gateway.servlet;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Parameters for {@link GatewayServlet}
 */
public class GatewayServletParameters {

    //
    @NotBlank
    private String rootUrl;

    @JsonProperty("rootUrl")
    public String getRootUrl() {
        return rootUrl;
    }

    @JsonProperty("rootUrl")
    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    //

    public GatewayServletParameters() {
    }
}
