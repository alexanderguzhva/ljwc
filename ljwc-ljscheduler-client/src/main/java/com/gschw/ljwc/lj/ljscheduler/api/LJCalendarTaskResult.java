package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.auth.Identity;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nop on 8/12/15.
 */
public class LJCalendarTaskResult {
    @NotNull
    private Identity taskIdentity;

    @JsonProperty("taskIdentity")
    public Identity getTaskIdentity() {
        return taskIdentity;
    }

    @JsonCreator
    public LJCalendarTaskResult(@JsonProperty("taskIdentity") @NotNull Identity taskIdentity) {
        this.taskIdentity = taskIdentity;

        this.elements = new ArrayList<>();
    }

    //
    private List<LJCalendarTaskResultElement> elements;

    public List<LJCalendarTaskResultElement> getElements() {
        return elements;
    }

    public void addElement(LJCalendarTaskResultElement element) {
        elements.add(element);
    }
}
