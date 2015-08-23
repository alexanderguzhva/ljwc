package com.gschw.ljwc.storage.hbasebridge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.storage.hbasebridge.core.HBaseDBSettings;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by nop on 8/23/15.
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
    private HBaseDBSettings hbaseDBSettings;

    @JsonProperty("hbaseDBSettings")
    public HBaseDBSettings getHbaseDBSettings() {
        return hbaseDBSettings;
    }

    @JsonProperty("hbaseDBSettings")
    public void setHbaseDBSettings(HBaseDBSettings hbaseDBSettings) {
        this.hbaseDBSettings = hbaseDBSettings;
    }
}
