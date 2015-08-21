package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by hadoop on 8/21/15.
 */
public class LJSinglePageElement {
    @URL
    @NotBlank
    private String url;

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }


    //
    private LJSinglePageElementCategory category;

    @JsonProperty("category")
    public LJSinglePageElementCategory getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(LJSinglePageElementCategory category) {
        this.category = category;
    }


    //
    public LJSinglePageElement() {
        category = LJSinglePageElementCategory.UNKNOWN;
    }

    public LJSinglePageElement(String url, LJSinglePageElementCategory category) {
        this.url = url;
        this.category = category;
    }
}
