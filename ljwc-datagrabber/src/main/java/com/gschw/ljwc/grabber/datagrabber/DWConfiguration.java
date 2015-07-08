package com.gschw.ljwc.grabber.datagrabber;

import com.gschw.ljwc.grabber.datagrabber.core.GrabberParameters;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private GrabberParameters grabberParameters;

    @JsonProperty("grabberParameters")
    public GrabberParameters getGrabberParameters() {
        return grabberParameters;
    }

    @JsonProperty("grabberParameters")
    public void setGrabberParameters(GrabberParameters grabberParameters) {
        this.grabberParameters = grabberParameters;
    }

}
