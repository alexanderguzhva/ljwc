package com.gschw.ljwc.grabber.datagrabber.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 7/6/15.
 */
public class GrabberLogicParameters {
    private long sleepingTime;

    @JsonProperty("sleepingTime")
    public long getSleepingTime() {
        return sleepingTime;
    }

    //
    private long accumulateResultsLimit;

    @JsonProperty("accumulateResultsLimit")
    public long getAccumulateResultsLimit() {
        return accumulateResultsLimit;
    }
}
