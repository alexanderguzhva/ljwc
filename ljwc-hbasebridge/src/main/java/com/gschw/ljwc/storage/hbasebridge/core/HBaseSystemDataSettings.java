package com.gschw.ljwc.storage.hbasebridge.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by hadoop on 8/26/15.
 */
public class HBaseSystemDataSettings {

    //
    @NotBlank
    private String systemColumnFamilyName;

    @JsonProperty("systemColumnFamilyName")
    public String getSystemColumnFamilyName() {
        return systemColumnFamilyName;
    }

    @JsonProperty("systemColumnFamilyName")
    public void setSystemColumnFamilyName(String systemColumnFamilyName) {
        this.systemColumnFamilyName = systemColumnFamilyName;
    }


    //
    public HBaseSystemDataSettings() {
    }
}
