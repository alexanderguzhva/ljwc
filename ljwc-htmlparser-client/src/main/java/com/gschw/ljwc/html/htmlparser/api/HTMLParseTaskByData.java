package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 5/16/15.
 */
public class HTMLParseTaskByData {
    private final byte[] data;

    @JsonCreator
    public HTMLParseTaskByData(@JsonProperty("data") byte[] data) {
        this.data = data;
    }

    @JsonProperty("data")
    public byte[] getData() {
        return data;
    }
}
