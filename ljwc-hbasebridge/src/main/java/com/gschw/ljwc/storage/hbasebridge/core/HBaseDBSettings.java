package com.gschw.ljwc.storage.hbasebridge.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 6/24/15.
 */
public class HBaseDBSettings {
    private HBaseConnectionSettings connectionSettings;

    @JsonProperty("connectionSettings")
    public HBaseConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }

    @JsonProperty("connectionSettings")
    public void setConnectionSettings(HBaseConnectionSettings connectionSettings) {
        this.connectionSettings = connectionSettings;
    }

    public HBaseDBSettings() {
    }
}
