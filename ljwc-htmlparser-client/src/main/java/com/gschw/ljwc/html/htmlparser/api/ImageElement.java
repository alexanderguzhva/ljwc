package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 5/14/15.
 */
public class ImageElement {
    @JsonProperty("tag")
    public String tag;

    @JsonProperty("src")
    public String src;

    @JsonProperty("width")
    public String width;

    @JsonProperty("height")
    public String height;

    @JsonProperty("alt")
    public String alt;

    public ImageElement(String tag, String src, String width, String height, String alt) {
        this.tag = tag;
        this.src = src;
        this.width = width;
        this.height = height;
        this.alt = alt;
    }

    public ImageElement() {

    }


}
