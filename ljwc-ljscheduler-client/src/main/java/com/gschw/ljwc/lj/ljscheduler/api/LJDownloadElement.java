package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

import com.gschw.ljwc.auth.Identity;

/**
 * Created by nop on 7/15/15.
 */
public class LJDownloadElement {
    @NotNull
    private Identity identity;

    @URL
    @NotBlank
    private String url;

    @JsonCreator
    public LJDownloadElement(@JsonProperty("identity") Identity identity, @JsonProperty("url") String url) {
        this.identity = identity;
        this.url = url;
    }

    @JsonProperty
    public Identity getIdentity() {
        return identity;
    }

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "LJDownloadElement{" +
                "url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LJDownloadElement)) return false;

        LJDownloadElement element = (LJDownloadElement) o;

        if (identity != null ? !identity.equals(element.identity) : element.identity != null) return false;
        return !(url != null ? !url.equals(element.url) : element.url != null);

    }

    @Override
    public int hashCode() {
        int result = identity != null ? identity.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

}
