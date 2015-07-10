package com.gschw.ljwc.lj.ljscheduler.api;

/**
 * Created by nop on 7/6/15.
 */
public class NotificationPushReply {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    private NotificationPushReply(boolean success) {
        this.success = success;
    }

    public static NotificationPushReply createSucceeded() {
        return new NotificationPushReply(true);
    }

    public static NotificationPushReply createFailed() {
        return new NotificationPushReply(false);
    }

}
