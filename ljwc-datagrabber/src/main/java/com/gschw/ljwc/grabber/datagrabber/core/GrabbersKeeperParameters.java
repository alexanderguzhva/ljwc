package com.gschw.ljwc.grabber.datagrabber.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 7/12/15.
 */
public class GrabbersKeeperParameters {
    @JsonProperty
    private int maximumNumberOfSessions;

    public GrabbersKeeperParameters() {
        maximumNumberOfSessions = 100;
    }

    public int getMaximumNumberOfSessions() {
        return maximumNumberOfSessions;
    }

    public void setMaximumNumberOfSessions(int maximumNumberOfSessions) {
        this.maximumNumberOfSessions = maximumNumberOfSessions;
    }
}
