package com.gschw.ljwc.storage.hbasebridge.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Settings for {@link HBaseDB}
 */
public class HBaseDBSettings {
    @Valid
    @NotNull
    private HBaseConnectionSettings connectionSettings;

    @JsonProperty("connectionSettings")
    public HBaseConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }

    @JsonProperty("connectionSettings")
    public void setConnectionSettings(HBaseConnectionSettings connectionSettings) {
        this.connectionSettings = connectionSettings;
    }

    //
    public HBaseDBSettings() {
    }
}
