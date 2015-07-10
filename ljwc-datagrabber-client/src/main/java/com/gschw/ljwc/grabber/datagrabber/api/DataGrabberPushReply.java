package com.gschw.ljwc.grabber.datagrabber.api;

/**
 * Created by nop on 6/30/15.
 */
public class DataGrabberPushReply {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    private DataGrabberPushReply(boolean success) {
        this.success = success;
    }

    public static DataGrabberPushReply createSucceeded() {
        return new DataGrabberPushReply(true);
    }

    public static DataGrabberPushReply createFailed() {
        return new DataGrabberPushReply(false);
    }
}
