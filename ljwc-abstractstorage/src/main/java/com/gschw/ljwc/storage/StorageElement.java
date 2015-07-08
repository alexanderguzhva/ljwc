package com.gschw.ljwc.storage;

import org.joda.time.DateTime;
import java.util.Map;

/**
 * Created by nop on 6/22/15.
 */
public class StorageElement {
    private byte[] key;

    private Map<byte[], byte[]> data;
    private Map<byte[], byte[]> meta;

    private DateTime timestamp;

    //
    public StorageElement(byte[] key) {
        this.key = key;
    }

    //
    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    //
    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }
}
