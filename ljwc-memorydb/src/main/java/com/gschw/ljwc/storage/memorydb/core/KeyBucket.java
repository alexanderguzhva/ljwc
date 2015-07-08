package com.gschw.ljwc.storage.memorydb.core;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.gschw.ljwc.storage.StorageElement;

/**
 * Created by nop on 6/24/15.
 */
public class KeyBucket {
    private byte[] key;
    private final List<StorageElement> elements;

    public KeyBucket(byte[] key) {
        this.key = key;
        this.elements = new ArrayList<>();
    }

    public KeyBucket(byte[] key, List<StorageElement> elements) {
        this.key = key;
        this.elements = new ArrayList<>(elements);
    }

    //
    public byte[] getKey() {
        return key;
    }

    //
    public void addElement(StorageElement element) {
        elements.add(element);
    }

    //
    public List<StorageElement> getElements() {
        return new ArrayList<StorageElement>(elements);
    }
}
