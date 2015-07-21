package com.gschw.ljwc.lj.ljreader;

import com.gschw.ljwc.storage.client.DBStorageClientParameters;
import io.dropwizard.Configuration;

import io.dropwizard.client.JerseyClientConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 7/20/15.
 */
public class DWConfiguration  extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("httpClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }


    //
    private DBStorageClientParameters dbStorageClientParameters;

    @JsonProperty
    public DBStorageClientParameters getDbStorageClientParameters() {
        return dbStorageClientParameters;
    }

    @JsonProperty
    public void setDbStorageClientParameters(DBStorageClientParameters dbStorageClientParameters) {
        this.dbStorageClientParameters = dbStorageClientParameters;
    }

}
