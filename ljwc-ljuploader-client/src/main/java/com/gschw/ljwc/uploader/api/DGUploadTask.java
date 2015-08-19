package com.gschw.ljwc.uploader.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.auth.Identity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 7/12/15.
 */
public class DGUploadTask {
    @NotNull
    private Identity taskIdentity;

    @URL
    @NotBlank
    private String url;

    @NotBlank
    private byte[] data;

    @JsonCreator
    public DGUploadTask(@JsonProperty("taskIdentity") Identity taskIdentity, @JsonProperty("url") String url, @JsonProperty("data") byte[] data) {
        this.taskIdentity = taskIdentity;
        this.url = url;
        this.data = data;
    }

    @JsonProperty
    public Identity getTaskIdentity() {
        return taskIdentity;
    }

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public byte[] getData() {
        return data;
    }

}
