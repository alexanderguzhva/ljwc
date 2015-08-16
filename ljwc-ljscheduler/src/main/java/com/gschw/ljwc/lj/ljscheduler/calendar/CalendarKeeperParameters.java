package com.gschw.ljwc.lj.ljscheduler.calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 8/16/15.
 */
public class CalendarKeeperParameters {
    private long defaultTimeoutMsec;

    public CalendarKeeperParameters() {
    }

    @JsonProperty("defaultTimeoutMsec")
    public long getDefaultTimeoutMsec() {
        return defaultTimeoutMsec;
    }

    @JsonProperty("defaultTimeoutMsec")
    public void setDefaultTimeoutMsec(long defaultTimeoutMsec) {
        this.defaultTimeoutMsec = defaultTimeoutMsec;
    }
}
