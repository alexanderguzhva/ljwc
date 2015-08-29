package com.gschw.ljwc.html.htmlparser.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by nop on 8/28/15.
 */
public class BetterHTMLParserParameters {
    private List<LJStyle> styles;

    @JsonProperty("styles")
    public List<LJStyle> getStyles() {
        return styles;
    }


    //
    public BetterHTMLParserParameters() {
        styles = new ArrayList<>();
    }
}
