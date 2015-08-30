package com.gschw.ljwc.lj.ljagent.managed;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for {@link ProcessorManager}
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
    private int numberOfProcesorQueues;

    @JsonProperty("numberOfProcesorQueues")
    public int getNumberOfProcesorQueues() {
        return numberOfProcesorQueues;
    }

    @JsonProperty("numberOfProcesorQueues")
    public void setNumberOfProcesorQueues(int numberOfProcesorQueues) {
        this.numberOfProcesorQueues = numberOfProcesorQueues;
    }

    //
    public ProcessorManagerParameters() {
        this.shutdownTimeoutMsec = 5000;
        this.queueTimerRateMsec = 1000;
        this.numberOfProcesorQueues = 1;
    }
}
