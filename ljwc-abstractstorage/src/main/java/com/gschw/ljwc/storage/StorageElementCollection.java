package com.gschw.ljwc.storage;

import com.gschw.ljwc.storage.StorageElement;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nop on 6/22/15.
 */
public class StorageElementCollection {
    private final List<StorageElement> elements;

    //
    public StorageElementCollection() {
        this.elements = new ArrayList<>();
    }

    public StorageElementCollection(List<StorageElement> elements) {
        this.elements = new ArrayList<>(elements);
    }

    //
    public List<StorageElement> getElements() {
        return elements;
    }

    //
    public void addElements(Collection<? extends StorageElement> elements) {
        this.elements.addAll(elements);
    }
}
