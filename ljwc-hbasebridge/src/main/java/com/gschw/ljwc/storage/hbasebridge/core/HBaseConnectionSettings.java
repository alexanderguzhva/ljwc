package com.gschw.ljwc.storage.hbasebridge.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by nop on 6/24/15.
 */
public class HBaseConnectionSettings {
    @URL
    private String serviceUrl;

    @JsonProperty("serviceUrl")
    public String getServiceUrl() {
        return serviceUrl;
    }

    @JsonProperty("serviceUrl")
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }


    //
    @NotBlank
    private String tableName;

    @JsonProperty("tableName")
    public String getTableName() {
        return tableName;
    }

    @JsonProperty("tableName")
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }



    //
    public HBaseConnectionSettings() {
    }

    public HBaseConnectionSettings(String serviceUrl, String tableName) {
        this.serviceUrl = serviceUrl;
        this.tableName = tableName;
    }
}
