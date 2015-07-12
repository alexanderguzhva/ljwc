package com.gschw.ljwc.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hadoop on 7/9/15.
 */
public class DBStorageElementsCollection {
    @NotNull
    private List<DBStorageElement> elements;

    //
    public DBStorageElementsCollection() {
        elements = new ArrayList<>();
    }

    public DBStorageElementsCollection(Collection<? extends  DBStorageElement> elements) {
        this.elements = new ArrayList<>(elements);
    }

    @JsonProperty
    public List<DBStorageElement> getElements() {
        return elements;
    }

    public void addElement(DBStorageElement element) {
        elements.add(element);
    }

}
