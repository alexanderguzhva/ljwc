package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import com.gschw.ljwc.auth.Identity;

/**
 * Created by nop on 7/15/15.
 */
public class LJDownloadTask {
    @NotNull
    private List<LJDownloadElement> elements;

    @NotNull
    private Identity identity;

    @NotNull
    private Identity assignedTo;

    @JsonCreator
    public LJDownloadTask(@JsonProperty("identity") Identity identity) {
        this.identity = identity;
        elements = new ArrayList<>();
    }



    @JsonProperty
    public List<LJDownloadElement> getElements() {
        return elements;
    }

    @JsonProperty
    public Identity getIdentity() {
        return identity;
    }

    @JsonProperty
    public Identity getAssignedTo() {
        return assignedTo;
    }

    @JsonProperty
    public void setAssignedTo(Identity assignedTo) {
        this.assignedTo = assignedTo;
    }

    //
    public void addElement(LJDownloadElement element) {
        elements.add(element);
    }
}
