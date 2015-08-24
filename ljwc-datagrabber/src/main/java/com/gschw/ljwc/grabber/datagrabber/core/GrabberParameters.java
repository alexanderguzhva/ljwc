package com.gschw.ljwc.grabber.datagrabber.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for {@link Grabber}.
 */
public class GrabberParameters {
    private String userAgentString;
    private int timeoutMSec;
    private int gracefulSleepMSec;
    private int socketTimeoutMSec;

    @JsonProperty("userAgentString")
    public String getUserAgentString() {
        return userAgentString;
    }

    @JsonProperty("userAgentString")
    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    @JsonProperty("timeoutMSec")
    public int getTimeoutMSec() {
        return timeoutMSec;
    }

    @JsonProperty("timeoutMSec")
    public void setTimeoutMSec(int timeoutMSec) {
        this.timeoutMSec = timeoutMSec;
    }

    @JsonProperty("gracefulSleepMSec")
    public int getGracefulSleepMSec() {
        return gracefulSleepMSec;
    }

    @JsonProperty("gracefulSleepMSec")
    public void setGracefulSleepMSec(int gracefulSleepMSec) {
        this.gracefulSleepMSec = gracefulSleepMSec;
    }

    @JsonProperty("socketTimeoutMSec")
    public int getSocketTimeoutMSec() {
        return socketTimeoutMSec;
    }

    @JsonProperty("socketTimeoutMSec")
    public void setSocketTimeoutMSec(int socketTimeoutMSec) {
        this.socketTimeoutMSec = socketTimeoutMSec;
    }

    public GrabberParameters() {
    }
}
