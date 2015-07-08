package com.gschw.ljwc.storage.hbase.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 6/24/15.
 */
public class Cell {
    @JsonProperty("column")
    private byte[] column;

    @JsonProperty("timestamp")
    private long timestamp = Constants.LATEST_TIMESTAMP;

    @JsonProperty("$")
    private byte[] value;

    //
    public Cell() {
    }

    //
    public Cell(byte[] column, long timestamp, byte[] value) {
        this.column = column;
        this.timestamp = timestamp;
        this.value = value;
    }

    //
    public byte[] getColumn() {
        return column;
    }

    //
    public void setColumn(byte[] column) {
        this.column = column;
    }


    //
    public long getTimestamp() {
        return timestamp;
    }

    //
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    //
    public byte[] getValue() {
        return value;
    }

    //
    public void setValue(byte[] value) {
        this.value = value;
    }

}
