package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


/**
 * Created by nop on 8/12/15.
 */
public class LJCalendarTaskResultElement {
    /**
     * A single blog page. For ex., http://tema.livejournal.com/12356.html
     */
    @URL
    @NotBlank
    private String url;

    @JsonCreator
    public LJCalendarTaskResultElement(@JsonProperty("url") String url) {
        this.url = url;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }
}
