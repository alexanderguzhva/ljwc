package com.gschw.ljwc.hbaselib.core;

import org.joda.time.DateTime;

/**
 * Created by nop on 5/5/15.
 */
public class HBaseDataElement {
    private byte[] key;
    private DateTime timestamp;
    private byte[] data;

    public HBaseDataElement() {

    }

    //
    public byte[] getData() {
        return data;
    }

    //
    public void setData(byte[] data) {
        this.data = data;
    }


    //
    public byte[] getKey() {
        return key;
    }

    //
    public void setKey(byte[] key) { this.key = key;
    }


    //
    public DateTime getTimestamp() {
        return timestamp;
    }

    //
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

}
