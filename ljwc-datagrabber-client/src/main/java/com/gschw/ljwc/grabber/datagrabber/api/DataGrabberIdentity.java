package com.gschw.ljwc.grabber.datagrabber.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 5/11/15.
 */
public class DataGrabberIdentity implements Comparable<DataGrabberIdentity> {
    @NotNull
    public String uuid;

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public DataGrabberIdentity() {
    }

    public DataGrabberIdentity(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataGrabberIdentity)) return false;

        DataGrabberIdentity that = (DataGrabberIdentity) o;

        return !(uuid != null ? !uuid.equals(that.uuid) : that.uuid != null);

    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(DataGrabberIdentity grabberIdentity) {
        return (grabberIdentity.uuid.compareTo(this.uuid));
    }
}
