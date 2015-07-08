package com.gschw.ljwc.storage.hbase.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nop on 6/24/15.
 */
public class CellSet {
    @JsonProperty("Row")
    private List<Row> rows;

    public CellSet() {
        this.rows = new ArrayList<>();
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    public List<Row> getRows() {
        return rows;
    }
}
