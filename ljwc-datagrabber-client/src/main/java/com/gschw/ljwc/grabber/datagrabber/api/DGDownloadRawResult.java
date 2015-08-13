package com.gschw.ljwc.grabber.datagrabber.api;

import com.gschw.ljwc.auth.Identity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 8/12/15.
 */
public class DGDownloadRawResult {
    @NotNull
    private Identity taskIdentity;

    private boolean success;

    private byte[] data;

    @JsonCreator
    public DGDownloadRawResult(
            @JsonProperty("taskIdentity") Identity taskIdentity,
            @JsonProperty("success") boolean success,
            @JsonProperty("data") byte[] data) {
        this.taskIdentity = taskIdentity;
        this.success = success;
        this.data = data;
    }

    public static DGDownloadRawResult createFailure(Identity taskIdentity) {
        return new DGDownloadRawResult(taskIdentity, false, null);
    }

    public static DGDownloadRawResult createSuccess(Identity taskIdentity, byte[] data) {
        return new DGDownloadRawResult(taskIdentity, true, data);
    }

    @JsonProperty("taskIdentity")
    public Identity getTaskIdentity() {
        return taskIdentity;
    }

    @JsonProperty("success")
    public boolean isSuccess() {
        return success;
    }

    @JsonProperty("data")
    public byte[] getData() {
        return data;
    }
}
