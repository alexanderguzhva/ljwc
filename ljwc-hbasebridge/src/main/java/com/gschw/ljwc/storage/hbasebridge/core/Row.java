package com.gschw.ljwc.storage.hbasebridge.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nop on 6/24/15.
 */
public class Row {
    @JsonProperty("key")
    private byte[] key;

    @JsonProperty("Cell")
    private List<Cell> cells;


    public Row() {
        this.cells = new ArrayList<>();
    }

    //
    public Row(String key) {
        this.key = key.getBytes();
        this.cells = new ArrayList<>();
    }

    //
    public Row(byte[] key) {
        this.key = key;
        this.cells = new ArrayList<>();
    }

    //
    public Row(String key, List<Cell> cells) {
        this.key = key.getBytes();
        this.cells = new ArrayList<>(cells);
    }

    //
    public Row(byte[] key, List<Cell> cells) {
        this.key = key;
        this.cells = new ArrayList<>(cells);
    }

    //
    public void addCell(Cell cell) {
        cells.add(cell);
    }

    //
    public byte[] getKey() {
        return key;
    }

    //
    public void setKey(byte[] key) {
        this.key = key;
    }


    //
    public List<Cell> getCells() {
        return cells;
    }

    //

}
