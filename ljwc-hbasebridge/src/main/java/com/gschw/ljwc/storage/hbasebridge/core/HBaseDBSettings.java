package com.gschw.ljwc.storage.hbasebridge.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by nop on 6/24/15.
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
    @Valid
    @NotNull
    private HBaseSystemDataSettings systemDataSettings;

    @JsonProperty("systemDataSettings")
    public HBaseSystemDataSettings getSystemDataSettings() {
        return systemDataSettings;
    }

    @JsonProperty("systemDataSettings")
    public void setSystemDataSettings(HBaseSystemDataSettings systemDataSettings) {
        this.systemDataSettings = systemDataSettings;
    }

    //
    public HBaseDBSettings() {
    }
}
