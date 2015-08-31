package com.gschw.ljwc.grabber.datagrabber.core;

/**
 * This is being produced by {@link Grabber} as a result of a request.
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
