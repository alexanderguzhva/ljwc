package com.gschw.ljwc.grabber.datagrabber;

import com.gschw.ljwc.grabber.datagrabber.core.GrabberParameters;
import com.gschw.ljwc.grabber.datagrabber.core.GrabbersKeeperParameters;
import com.gschw.ljwc.uploader.client.DGUploaderClientParameters;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.uploader.client.DGUploaderClientParameters;
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
    private GrabberParameters grabberParameters;

    @JsonProperty
    public GrabberParameters getGrabberParameters() {
        return grabberParameters;
    }

    @JsonProperty
    public void setGrabberParameters(GrabberParameters grabberParameters) {
        this.grabberParameters = grabberParameters;
    }


    //
    //@NotNull
    private DGUploaderClientParameters uploaderClientParameters =
            new DGUploaderClientParameters();

    @JsonProperty
    public DGUploaderClientParameters getUploaderClientParameters() {
        return uploaderClientParameters;
    }

    @JsonProperty
    public void setUploaderClientParameters(DGUploaderClientParameters uploaderClientParameters) {
        this.uploaderClientParameters = uploaderClientParameters;
    }


    //
    @NotNull
    private GrabbersKeeperParameters grabbersKeeperParameters;

    @JsonProperty
    public GrabbersKeeperParameters getGrabbersKeeperParameters() {
        return grabbersKeeperParameters;
    }

    @JsonProperty
    public void setGrabbersKeeperParameters(GrabbersKeeperParameters grabbersKeeperParameters) {
        this.grabbersKeeperParameters = grabbersKeeperParameters;
    }


}
