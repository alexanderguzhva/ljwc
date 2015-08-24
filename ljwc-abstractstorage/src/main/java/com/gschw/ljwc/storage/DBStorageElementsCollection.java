package com.gschw.ljwc.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A collection of {@link DBStorageElement} elements.
 */
public class DBStorageElementsCollection {
    /**
     * A list of elements.
     */
    @NotNull
    private List<DBStorageElement> elements;

    /**
     * Returns elements.
     *
     * @return Elements.
     */
    @JsonProperty
    public List<DBStorageElement> getElements() {
        return elements;
    }

    /**
     * Adds the given element to the collection.
     *
     * @param element  The element to add.
     */
    public void addElement(DBStorageElement element) {
        elements.add(element);
    }




    /**
     * Default cnostructor.
     */
    public DBStorageElementsCollection() {
        elements = new ArrayList<>();
    }

    /**
     * Constuctor.
     *
     * @param elements  Elements to store in a collection.
     */
    public DBStorageElementsCollection(Collection<? extends  DBStorageElement> elements) {
        this.elements = new ArrayList<>(elements);
    }


}
