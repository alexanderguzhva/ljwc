package com.gschw.ljwc.lj.ljcalendaragent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClientParameters;
import com.gschw.ljwc.lj.ljcalendaragent.calendar.ProcessorParameters;
import com.gschw.ljwc.lj.ljscheduler.client.LJCalendarTaskClientParameters;
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

    //
    private DGDownloadTaskClientParameters downloadTaskClientParameters;

    @JsonProperty("downloadTaskClientParameters")
    public DGDownloadTaskClientParameters getDownloadTaskClientParameters() {
        return downloadTaskClientParameters;
    }

    @JsonProperty("downloadTaskClientParameters")
    public void setDownloadTaskClientParameters(DGDownloadTaskClientParameters downloadTaskClientParameters) {
        this.downloadTaskClientParameters = downloadTaskClientParameters;
    }

    //
    private LJCalendarTaskClientParameters calendarTaskClientParameters;

    @JsonProperty("ljCalendarTaskClientParameters")
    public LJCalendarTaskClientParameters getCalendarTaskClientParameters() {
        return calendarTaskClientParameters;
    }

    @JsonProperty("ljCalendarTaskClientParameters")
    public void setCalendarTaskClientParameters(LJCalendarTaskClientParameters calendarTaskClientParameters) {
        this.calendarTaskClientParameters = calendarTaskClientParameters;
    }
}
