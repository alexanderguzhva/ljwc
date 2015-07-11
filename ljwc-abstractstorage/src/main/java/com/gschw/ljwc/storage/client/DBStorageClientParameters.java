package com.gschw.ljwc.storage.client;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by nop on 7/10/15.
 */
public class DBStorageClientParameters {
    @URL
    @NotBlank
    private String serviceUrl;

    public String getServiceUrl() {
        return serviceUrl;
    }
}
