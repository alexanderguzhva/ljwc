package com.gschw.ljwc.grabber.datagrabber.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.auth.Identity;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 7/12/15.
 */
public class DGDownloadResult {
    @NotNull
    private Identity taskIdentity;

    @JsonProperty
    public Identity getTaskIdentity() {
        return taskIdentity;
    }

    @JsonProperty
    public void setTaskIdentity(Identity taskIdentity) {
        this.taskIdentity = taskIdentity;
    }

    //
    private boolean uploadSuccess;

    @JsonProperty
    public boolean isUploadSuccess() {
        return uploadSuccess;
    }

    @JsonProperty
    public void setUploadSuccess(boolean uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
    }


    //
    private byte[] data;

    @JsonProperty
    public byte[] getData() {
        return data;
    }

    @JsonProperty
    public void setData(byte[] data) {
        this.data = data;
    }


    //
    public DGDownloadResult() {
    }

}
