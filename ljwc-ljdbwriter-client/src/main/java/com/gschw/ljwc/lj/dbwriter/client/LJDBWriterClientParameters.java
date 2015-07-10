package com.gschw.ljwc.lj.dbwriter.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 7/6/15.
 */
public class LJDBWriterClientParameters {
    private String serviceURI;

    @JsonProperty("serviceURI")
    public String getServiceURI() {
        return serviceURI;
    }
}
