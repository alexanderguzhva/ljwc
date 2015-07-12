package com.gschw.ljwc.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hadoop on 7/9/15.
 */
public class DBStorageElement {
    @NotBlank
    private String key;

    @NotNull
    private long timestamp;

    private Map<String, byte[]> data;
    private Map<String, byte[]> meta;

    @JsonCreator
    public DBStorageElement(@JsonProperty("key") String key) {
        this.key = key;

        data = new HashMap<>();
        meta = new HashMap<>();
    }

    @JsonProperty
    public String getKey() {
        return key;
    }

    public DateTime getTimestamp() {
        return new DateTime(timestamp);
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp.getMillis();
    }

    @JsonProperty
    public Map<String, byte[]> getData() {
        return data;
    }

    @JsonProperty
    public Map<String, byte[]> getMeta() {
        return meta;
    }

}
