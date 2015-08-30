package com.gschw.ljwc.lj.ljagent.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotNull;

import com.gschw.ljwc.auth.Identity;

/**
 * Parameters for {@link Processor}
 */
public class ProcessorParameters {
    @URL
    @NotBlank
    private String uploaderServiceUrl;

    @JsonProperty("uploaderServiceUrl")
    public String getUploaderServiceUrl() {
        return uploaderServiceUrl;
    }

    @JsonProperty("uploaderServiceUrl")
    public void setUploaderServiceUrl(String uploaderServiceUrl) {
        this.uploaderServiceUrl = uploaderServiceUrl;
    }


    //
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
    public ProcessorParameters() {
    }



}
