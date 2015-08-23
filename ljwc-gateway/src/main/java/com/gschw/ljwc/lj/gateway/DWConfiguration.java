package com.gschw.ljwc.lj.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.lj.gateway.servlet.GatewayServletParameters;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloaderParameters;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by nop on 8/22/15.
 */
public class DWConfiguration extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("httpClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }


    //
    @Valid
    @NotNull
    private GatewayServletParameters gatewayServletParameters;

    @JsonProperty("gatewayServletParameters")
    public GatewayServletParameters getGatewayServletParameters() {
        return gatewayServletParameters;
    }

    @JsonProperty("gatewayServletParameters")
    public void setGatewayServletParameters(GatewayServletParameters gatewayServletParameters) {
        this.gatewayServletParameters = gatewayServletParameters;
    }


    //
    @NotNull
    private SimpleDownloaderParameters simpleDownloaderParameters;

    @JsonProperty("simpleDownloaderParameters")
    public SimpleDownloaderParameters getSimpleDownloaderParameters() {
        return simpleDownloaderParameters;
    }

    @JsonProperty("simpleDownloaderParameters")
    public void setSimpleDownloaderParameters(SimpleDownloaderParameters simpleDownloaderParameters) {
        this.simpleDownloaderParameters = simpleDownloaderParameters;
    }
}
