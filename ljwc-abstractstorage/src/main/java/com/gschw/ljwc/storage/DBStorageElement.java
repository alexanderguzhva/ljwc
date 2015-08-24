package com.gschw.ljwc.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * An element of {@link IDBStorage}-based database
 */
public class DBStorageElement {
    /**
     * A key.
     */
    @NotBlank
    private String key;

    /**
     * Returns the key.
     *
     * @return Key.
     */
    @JsonProperty
    public String getKey() {
        return key;
    }



    /**
     * A timestamp (number of milliseconds since 1970).
     */
    @NotNull
    private long timestamp;

    /**
     * Returns the timestamp.
     *
     * @return Timestamp.
     */
    public DateTime getTimestamp() {
        return new DateTime(timestamp);
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp  Timestamp.
     */
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp.getMillis();
    }


    /**
     * Data dictionary.
     */
    private Map<String, byte[]> data;

    /**
     * Returns a data dictionary.
     *
     * @return Data dictionary.
     */
    @JsonProperty
    public Map<String, byte[]> getData() {
        return data;
    }

    /**
     * Metadata dictionary.
     */
    private Map<String, byte[]> meta;

    /**
     * Returns a metadata dictionary.
     *
     * @return Metadata dictionary.
     */
    @JsonProperty
    public Map<String, byte[]> getMeta() {
        return meta;
    }


    /**
     * Constructor.
     *
     * @param key  Key.
     */
    @JsonCreator
    public DBStorageElement(@JsonProperty("key") String key) {
        this.key = key;

        data = new HashMap<>();
        meta = new HashMap<>();
    }


}
