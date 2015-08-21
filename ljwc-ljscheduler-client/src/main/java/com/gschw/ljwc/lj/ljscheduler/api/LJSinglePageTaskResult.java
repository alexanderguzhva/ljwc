package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.auth.Identity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 8/21/15.
 */
public class LJSinglePageTaskResult implements ILJTaskIdentifiable {
    @NotNull
    private Identity taskIdentity;

    @JsonProperty("taskIdentity")
    public Identity getTaskIdentity() {
        return taskIdentity;
    }


    //
    private List<LJSinglePageElement> elements;

    @JsonProperty
    public List<LJSinglePageElement> getElements() {
        return elements;
    }

    public void addElement(LJSinglePageElement element) {
        elements.add(element);
    }

    //
    @JsonCreator
    public LJSinglePageTaskResult(@JsonProperty("taskIdentity") @NotNull Identity taskIdentity) {
        this.taskIdentity = taskIdentity;

        this.elements = new ArrayList<>();
    }
}
