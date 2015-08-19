package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hadoop on 8/19/15.
 */
public class HTMLParseResultByDBURL {
    private ElementsCollection elements;

    @JsonProperty
    public ElementsCollection getElements() {
        return elements;
    }

    @JsonProperty
    public void setElements(ElementsCollection elements) {
        this.elements = elements;
    }


    public HTMLParseResultByDBURL() {
    }

}
