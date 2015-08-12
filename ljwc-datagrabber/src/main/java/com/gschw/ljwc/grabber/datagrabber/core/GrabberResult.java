package com.gschw.ljwc.grabber.datagrabber.core;

/**
 * Created by nop on 5/16/15.
 */

public class GrabberResult {
    private final byte[] data;
    private final String uri;

    public GrabberResult(String uri, byte[] data) {
        this.data = data;
        this.uri = uri;
    }

    public byte[] getData() {
        return data;
    }

    public String getUri() {
        return uri;
    }
}
