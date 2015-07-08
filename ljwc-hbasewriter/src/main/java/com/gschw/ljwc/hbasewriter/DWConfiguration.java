package com.gschw.ljwc.hbasewriter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.hbaselib.core.HBaseSettings;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by nop on 6/22/15.
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
    private HBaseSettings hbaseSettings =
            new HBaseSettings("http://localhost:8080", "lj", "d:r");

    @JsonProperty("hbaseSettings")
    public HBaseSettings getHBaseSettings() {
        return hbaseSettings;
    }

    @JsonProperty("hbaseSettings")
    public void setHBaseSettings(HBaseSettings hbaseSettings) {
        this.hbaseSettings = hbaseSettings;
    }
}

