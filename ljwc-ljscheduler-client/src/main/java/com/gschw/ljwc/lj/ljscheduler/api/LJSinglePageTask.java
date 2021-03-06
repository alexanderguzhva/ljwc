package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.auth.Identity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by hadoop on 8/21/15.
 */
public class LJSinglePageTask implements ILJTaskIdentifiable {
    @NotNull
    private Identity taskIdentity;

    /**
     * An url that needs to be processed.
     */
    @URL
    @NotBlank
    private String url;


    @NotNull
    private Identity assignedTo;

    public Identity getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Identity assignedTo) {
        this.assignedTo = assignedTo;
    }


    @JsonCreator
    public LJSinglePageTask(@JsonProperty("taskIdentity") Identity taskIdentity, @JsonProperty("url") String url) {
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
