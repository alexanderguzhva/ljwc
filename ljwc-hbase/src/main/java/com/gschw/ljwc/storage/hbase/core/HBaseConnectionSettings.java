package com.gschw.ljwc.storage.hbase.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nop on 6/24/15.
 */
public class HBaseConnectionSettings {
    private String restServiceURI;

    @JsonProperty("RESTServiceURI")
    public String getRESTServiceURI() {
        return restServiceURI;
    }

    @JsonProperty("RESTServiceURI")
    public void setRESTServiceURI(String restServiceURI) {
        this.restServiceURI = restServiceURI;
    }


    //
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

    public HBaseConnectionSettings(String restServiceURI, String tableName) {
        this.restServiceURI = restServiceURI;
        this.tableName = tableName;
    }
}
