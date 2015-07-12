package com.gschw.ljwc.grabber.datagrabber.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.auth.Identity;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 7/12/15.
 */
public class DGDownloadResult {
    @NotNull
    private Identity taskIdentity;

    private boolean success;

    @JsonCreator
    public DGDownloadResult(@JsonProperty("taskIdentity") Identity taskIdentity, @JsonProperty("success") boolean success) {
        this.taskIdentity = taskIdentity;
        this.success = success;
    }

    @JsonProperty
    public Identity getTaskIdentity() {
        return taskIdentity;
    }

    @JsonProperty
    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DGDownloadResult)) return false;

        DGDownloadResult that = (DGDownloadResult) o;

        if (success != that.success) return false;
        return !(taskIdentity != null ? !taskIdentity.equals(that.taskIdentity) : that.taskIdentity != null);

    }

    @Override
    public int hashCode() {
        int result = taskIdentity != null ? taskIdentity.hashCode() : 0;
        result = 31 * result + (success ? 1 : 0);
        return result;
    }
}
