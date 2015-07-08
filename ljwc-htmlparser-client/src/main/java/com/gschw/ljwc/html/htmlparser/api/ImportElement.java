package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 5/14/15.
 */
public class ImportElement {
    @JsonProperty("tag")
    public String tag;

    @JsonProperty("href")
    public String href;

    @JsonProperty("rel")
    public String rel;

    public ImportElement(String tag, String href, String rel) {
        this.tag = tag;
        this.href = href;
        this.rel = rel;
    }

    public ImportElement() {
    }
}
