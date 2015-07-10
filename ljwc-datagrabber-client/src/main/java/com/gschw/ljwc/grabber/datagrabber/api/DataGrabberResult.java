package com.gschw.ljwc.grabber.datagrabber.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 6/29/15.
 */
public class DataGrabberResult {
    @NotNull
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
    public boolean isSuccess() { return success; }

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

    //
    public DataGrabberRequest extractRequest() {
        return new DataGrabberRequest(url);
    }
}
