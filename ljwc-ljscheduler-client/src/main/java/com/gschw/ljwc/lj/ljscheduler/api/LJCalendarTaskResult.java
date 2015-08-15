package com.gschw.ljwc.lj.ljscheduler.api;

import com.gschw.ljwc.auth.Identity;

import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * Created by nop on 8/12/15.
 */
public class LJCalendarTaskResult {
    @NotNull
    private Identity taskIdentity;

    public Identity getTaskIdentity() {
        return taskIdentity;
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
