package com.gschw.ljwc.storage.memorydb.core;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.gschw.ljwc.storage.DBStorageElement;

import org.joda.time.DateTime;

/**
 * Created by nop on 6/24/15.
 */
public class KeyBucket {
    private String key;
    private final Map<DateTime, DBStorageElement> elements;

    public KeyBucket(String key) {
        this.key = key;
        this.elements = new HashMap<>();
    }

    public KeyBucket(String key, List<DBStorageElement> elements) {
        this.key = key;
        this.elements = new HashMap<>();

        addElements(elements);
    }

    //
    public String getKey() {
        return key;
    }

    //
    public void addElements(Collection<? extends DBStorageElement> elements) {
        if (elements != null && elements.size() > 0) {
            for (DBStorageElement element : elements)
                addElement(element);
        }
    }

    //
    public void addElement(DBStorageElement element) {

        if (element == null)
            return;

        if (elements.containsKey(element.getTimestamp())) {
            elements.replace(element.getTimestamp(), element);
        } else {
            elements.put(element.getTimestamp(), element);
        }
    }

    //
    public DBStorageElement getElement(DateTime timestamp) {
        return elements.get(timestamp);
    }

    //
    public boolean contains(DateTime timestamp) {
        return elements.containsKey(timestamp);
    }

    //
    public List<DBStorageElement> getElements() {
        return new ArrayList<DBStorageElement>(elements.values());
    }
}
