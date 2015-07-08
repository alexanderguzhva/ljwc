package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 5/14/15.
 */
public class MediaElement {
    @JsonProperty("tag")
    public String tag;

    @JsonProperty("src")
    public String src;

    public MediaElement() {
    }

    public MediaElement(String tag, String src) {

        this.tag = tag;
        this.src = src;
    }
}
