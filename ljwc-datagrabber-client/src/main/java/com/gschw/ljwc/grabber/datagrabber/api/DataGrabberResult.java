package com.gschw.ljwc.grabber.datagrabber.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by nop on 6/29/15.
 */
public class DataGrabberResult {
    private String url;
    private byte[] data;
    private boolean success;

    @JsonProperty("$")
    public byte[] getData() {
        return data;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("success")
    public boolean getSuccess() { return success; }

    public DataGrabberResult(String url, byte[] data, boolean success) {
        this.url = url;
        this.data = data;
        this.success = success;
    }

    public static DataGrabberResult createFailed(String url) {
        return new DataGrabberResult(url, null, false);
    }

    public static DataGrabberResult createSuccess(String url, byte[] data) {
        return new DataGrabberResult(url, data, true);
    }
}
