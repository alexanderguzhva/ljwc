package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

import com.gschw.ljwc.auth.Identity;

/**
 * Created by nop on 8/12/15.
 */
public class LJCalendarTask {
    @NotNull
    private Identity taskIdentity;

    @URL
    @NotBlank
    private String url;

    @JsonCreator
    public LJCalendarTask(@JsonProperty("taskIdentity") Identity taskIdentity, @JsonProperty("url") String url) {
        this.taskIdentity = taskIdentity;
        this.url = url;
    }

    @JsonProperty("taskIdentity")
    public Identity getTaskIdentity() {
        return taskIdentity;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }
}
