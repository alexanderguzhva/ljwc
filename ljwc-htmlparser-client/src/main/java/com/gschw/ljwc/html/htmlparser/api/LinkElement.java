package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 5/14/15.
 */
public class LinkElement {
    @JsonProperty("href")
    public String href;

    @JsonProperty("tag")
    public String tag;

    @JsonProperty("text")
    public String text;

    @JsonProperty("rel")
    public String rel;

    public LinkElement(String href, String tag, String text, String rel) {
        this.href = href;
        this.tag = tag;
        this.text = text;
        this.rel = rel;
    }

    public LinkElement() {
    }
}
