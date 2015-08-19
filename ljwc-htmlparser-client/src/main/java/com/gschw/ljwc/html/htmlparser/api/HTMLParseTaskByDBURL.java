package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hadoop on 8/19/15.
 */
public class HTMLParseTaskByDBURL {
    private final String url;

    @JsonCreator
    public HTMLParseTaskByDBURL(@JsonProperty("url") String url) {
        this.url = url;
    }

    @JsonProperty
    public String getUrl() {
        return url;
    }
}
