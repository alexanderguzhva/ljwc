package com.gschw.ljwc.hbaselib.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

/**
 * Created by nop on 5/10/15.
 */
public class HBaseSettings {
    @NotNull
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
    @NotNull
    private String tableName;

    @JsonProperty("tableName")
    public String getTableName() {
        return tableName;
    }

    @JsonProperty("tableName")
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    /*
    private String colFNName;

    @JsonProperty("colFNName")
    public String getColFNName() {
        return colFNName;
    }

    @JsonProperty("colFNName")
    public void setColFNName(String colFNName) {
        this.colFNName = colFNName;
    }
    */

    //
    public HBaseSettings() {
    }


    //public HBaseSettings(String restServiceURI, String tableName, String colFNName) {
    public HBaseSettings(String restServiceURI, String tableName) {
        this.restServiceURI = restServiceURI;
        this.tableName = tableName;
        //this.colFNName = colFNName;
    }
}
