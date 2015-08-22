package com.gschw.ljwc.lj.ljcalendaragent.managed;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 8/22/15.
 */
public class ProcessorManagerParameters {
    private int shutdownTimeoutMsec;

    @JsonProperty("shutdownTimeoutMsec")
    public int getShutdownTimeoutMsec() {
        return shutdownTimeoutMsec;
    }

    @JsonProperty("shutdownTimeoutMsec")
    public void setShutdownTimeoutMsec(int shutdownTimeoutMsec) {
        this.shutdownTimeoutMsec = shutdownTimeoutMsec;
    }

    //
    private int queueTimerRateMsec;

    @JsonProperty("queueTimerRateMsec")
    public int getQueueTimerRateMsec() {
        return queueTimerRateMsec;
    }

    @JsonProperty("queueTimerRateMsec")
    public void setQueueTimerRateMsec(int queueTimerRateMsec) {
        this.queueTimerRateMsec = queueTimerRateMsec;
    }

    //

    public ProcessorManagerParameters() {
        this.shutdownTimeoutMsec = 5000;
        this.queueTimerRateMsec = 2000;
    }
}
