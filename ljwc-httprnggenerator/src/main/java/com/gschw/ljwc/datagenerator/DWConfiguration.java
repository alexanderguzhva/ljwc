package com.gschw.ljwc.datagenerator;


import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


import com.gschw.ljwc.datagenerator.core.DataGeneratorParameters;

/**
 * Created by nop on 7/10/15.
 */
public class DWConfiguration extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("httpClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }


    @Valid
    @NotNull
    private DataGeneratorParameters dataGeneratorParameters = new DataGeneratorParameters();

    @JsonProperty("dataGeneratorParameters")
    public DataGeneratorParameters getDataGeneratorParameters() {
        return dataGeneratorParameters;
    }
}
