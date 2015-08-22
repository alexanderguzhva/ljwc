package com.gschw.ljwc.lj.executor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.lj.ljscheduler.client.LJTaskClientParameters;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hadoop on 8/21/15.
 */
public class DWConfiguration extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("httpClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }



    @NotNull
    private LJTaskClientParameters ljSinglePageClientParameters;

    @JsonProperty("singlePageClientParameters")
    public LJTaskClientParameters getLjSinglePageClientParameters() {
        return ljSinglePageClientParameters;
    }

    public void setLjSinglePageClientParameters(LJTaskClientParameters ljSinglePageClientParameters) {
        this.ljSinglePageClientParameters = ljSinglePageClientParameters;
    }

    //
    @NotNull
    private LJTaskClientParameters ljCalendarClientParameters;

    @JsonProperty("calendarClientParameters")
    public LJTaskClientParameters getLjCalendarClientParameters() {
        return ljCalendarClientParameters;
    }

    public void setLjCalendarClientParameters(LJTaskClientParameters ljCalendarClientParameters) {
        this.ljCalendarClientParameters = ljCalendarClientParameters;
    }
}
