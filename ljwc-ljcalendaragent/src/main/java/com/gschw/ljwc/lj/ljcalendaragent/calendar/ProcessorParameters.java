package com.gschw.ljwc.lj.ljcalendaragent.calendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.auth.Identity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 8/15/15.
 */
public class ProcessorParameters {
    @NotNull
    private Identity processorIdentity;

    @JsonProperty("processorIdentity")
    public Identity getProcessorIdentity() {
        return processorIdentity;
    }

    @JsonProperty("processorIdentity")
    public void setProcessorIdentity(Identity processorIdentity) {
        this.processorIdentity = processorIdentity;
    }

    //
    @URL
    @NotBlank
    private String uploadServiceUrl;

    @JsonProperty("uploadServiceUrl")
    public String getUploadServiceUrl() {
        return uploadServiceUrl;
    }

    @JsonProperty("uploadServiceUrl")
    public void setUploadServiceUrl(String uploadServiceUrl) {
        this.uploadServiceUrl = uploadServiceUrl;
    }

    //
    public ProcessorParameters() {
    }


}
