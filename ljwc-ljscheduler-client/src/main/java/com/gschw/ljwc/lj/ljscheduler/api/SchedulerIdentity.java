package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 6/29/15.
 */
public class SchedulerIdentity implements Comparable<SchedulerIdentity> {
    public String uuid;

    @JsonProperty("uuid")
    public String getUuid() {

        return uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {

        this.uuid = uuid;
    }

    public SchedulerIdentity() {
    }

    public SchedulerIdentity(String uuid) {

        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchedulerIdentity)) return false;

        SchedulerIdentity that = (SchedulerIdentity) o;

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
    public int compareTo(SchedulerIdentity identity) {
        return (identity.uuid.compareTo(this.uuid));
    }

}
