package com.gschw.ljwc.grabber.datagrabber.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.auth.Identity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 7/12/15.
 */
public class DGDownloadTask {
    @NotNull
    private Identity taskIdentity;

    @URL
    @NotBlank
    private String url;

    @URL
    @NotBlank
    private String uploadServiceURL;


    @JsonCreator
    public DGDownloadTask(@JsonProperty("taskIdentity") Identity taskIdentity, @JsonProperty("url") String url, @JsonProperty("uploadServiceURL") String uploadServiceURL) {
        this.taskIdentity = taskIdentity;
        this.url = url;
        this.uploadServiceURL = uploadServiceURL;
    }

    //
    @JsonProperty
    public Identity getTaskIdentity() {
        return taskIdentity;
    }

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public String getUploadServiceURL() {
        return uploadServiceURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DGDownloadTask)) return false;

        DGDownloadTask that = (DGDownloadTask) o;

        if (taskIdentity != null ? !taskIdentity.equals(that.taskIdentity) : that.taskIdentity != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return !(uploadServiceURL != null ? !uploadServiceURL.equals(that.uploadServiceURL) : that.uploadServiceURL != null);

    }

    @Override
    public int hashCode() {
        int result = taskIdentity != null ? taskIdentity.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (uploadServiceURL != null ? uploadServiceURL.hashCode() : 0);
        return result;
    }
}
