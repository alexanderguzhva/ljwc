package com.gschw.ljwc.grabber.datagrabber.client;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by nop on 7/12/15.
 */
public class DGDownloadTaskClientParameters {
    public DGDownloadTaskClientParameters() {
    }

    @URL
    @NotBlank
    private String serviceUrl;

    public String getServiceUrl() {
        return serviceUrl;
    }
}
