package com.gschw.ljwc.storage.client;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for {@link DBStorageClient} class.
 */
public class DBStorageClientParameters {
    /**
     * An url for a remote service.
     */
    @URL
    @NotBlank
    private String serviceUrl;

    /**
     * Returns the url on a remote service.
     *
     * @return URL
     */
    @JsonProperty
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * Sets the url for a remove service
     *
     * @param serviceUrl  URL
     */
    @JsonProperty
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }



    /**
     * Default constructor.
     */
    public DBStorageClientParameters() {
    }
}
