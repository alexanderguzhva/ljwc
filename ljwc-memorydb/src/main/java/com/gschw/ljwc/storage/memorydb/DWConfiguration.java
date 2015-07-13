package com.gschw.ljwc.storage.memorydb;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.gschw.ljwc.storage.memorydb.core.MemoryDBSettings;

/**
 * Created by nop on 6/22/15.
 */
public class DWConfiguration extends Configuration {
    //@Valid
    //@NotNull
    //private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    //@JsonProperty("httpClient")
    //public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }


    @Valid
    @NotNull
    private MemoryDBSettings memoryDBSettings = new MemoryDBSettings();

    @JsonProperty("MemoryDBSettings")
    public MemoryDBSettings getMemoryDBSettings() {
        return memoryDBSettings;
    }
}
