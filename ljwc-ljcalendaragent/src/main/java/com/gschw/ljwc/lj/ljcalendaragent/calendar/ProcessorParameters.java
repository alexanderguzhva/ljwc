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
    @URL
    @NotBlank
    private String calendarServiceUrl;

    @NotNull
    private Identity processorIdentity;


    public ProcessorParameters() {
    }

    @JsonProperty("calendarServiceUrl")
    public String getCalendarServiceUrl() {
        return calendarServiceUrl;
    }

    @JsonProperty("calendarServiceUrl")
    public void setCalendarServiceUrl(String calendarServiceUrl) {
        this.calendarServiceUrl = calendarServiceUrl;
    }

    @JsonProperty("processorIdentity")
    public Identity getProcessorIdentity() {
        return processorIdentity;
    }

    @JsonProperty("processorIdentity")
    public void setProcessorIdentity(Identity processorIdentity) {
        this.processorIdentity = processorIdentity;
    }
}
