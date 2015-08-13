package com.gschw.ljwc.grabber.datagrabber.api;

import com.gschw.ljwc.auth.Identity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 8/12/15.
 */
public class DGDownloadRawTask {
    @URL
    @NotBlank
    private String url;

    @NotNull
    private Identity taskIdentity;

    @JsonCreator
    public DGDownloadRawTask(@JsonProperty("taskIdentity") Identity taskIdentity, @JsonProperty("url") String url) {
        this.taskIdentity = taskIdentity;
        this.url = url;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("taskIdentity")
    public Identity getTaskIdentity() {
        return taskIdentity;
    }
}
