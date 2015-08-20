package com.gschw.ljwc.html.htmlparser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.html.htmlparser.core.SimpleDownloaderParameters;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by nop on 6/29/15.
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
