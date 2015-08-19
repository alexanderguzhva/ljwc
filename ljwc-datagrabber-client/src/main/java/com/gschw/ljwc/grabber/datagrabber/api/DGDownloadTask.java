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

    @JsonProperty
    public Identity getTaskIdentity() {
        return taskIdentity;
    }

    @JsonProperty
    public void setTaskIdentity(Identity taskIdentity) {
        this.taskIdentity = taskIdentity;
    }



    @URL
    @NotBlank
    private String url;

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public void setUrl(String url) {
        this.url = url;
    }



    @URL
    private String uploadServiceURL;

    @JsonProperty
    public String getUploadServiceURL() {
        return uploadServiceURL;
    }

    @JsonProperty
    public void setUploadServiceURL(String uploadServiceURL) {
        this.uploadServiceURL = uploadServiceURL;
    }


    //
    private boolean returnDataInReply;

    @JsonProperty
    public boolean isReturnDataInReply() {
        return returnDataInReply;
    }

    @JsonProperty
    public void setReturnDataInReply(boolean returnDataInReply) {
        this.returnDataInReply = returnDataInReply;
    }

    //
    private boolean uploadDataToBase;

    @JsonProperty
    public boolean isUploadDataToBase() {
        return uploadDataToBase;
    }

    @JsonProperty
    public void setUploadDataToBase(boolean uploadDataToBase) {
        this.uploadDataToBase = uploadDataToBase;
    }

    //
    public DGDownloadTask()
    {
        returnDataInReply = false;
        uploadDataToBase = true;
    }




}
