package com.gschw.ljwc.lj.ljcalendaragent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.lj.ljcalendaragent.calendar.ProcessorParameters;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by nop on 8/12/15.
 */
public class DWConfiguration extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("httpClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }


    private ProcessorParameters processorParameters;

    @JsonProperty("processorParameters")
    public ProcessorParameters getProcessorParameters() {
        return processorParameters;
    }

    @JsonProperty("processorParameters")
    public void setProcessorParameters(ProcessorParameters processorParameters) {
        this.processorParameters = processorParameters;
    }

}
