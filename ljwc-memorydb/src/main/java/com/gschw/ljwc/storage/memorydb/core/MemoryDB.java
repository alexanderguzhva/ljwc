package com.gschw.ljwc.storage.memorydb.core;

import com.gschw.ljwc.storage.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by nop on 6/24/15.
 */
public class MemoryDB implements IDBStorage {

    private final Map<String, KeyBucket> buckets;

    private MemoryDBSettings settings;

    public MemoryDB(MemoryDBSettings settings) {
        this.settings = settings;

        buckets = new HashMap<>();
    }

    @Override
    public void write(DBStorageElement element) {
        List<DBStorageElement> elements = new ArrayList<>();
        elements.add(element);
    }

    @Override
    public void write(List<DBStorageElement> elements) {
        if (elements == null || elements.size() == 0)
            return;

        ////
        for (DBStorageElement element : elements) {
            KeyBucket bucket = buckets.get(element.getKey());
            if (bucket == null) {
                bucket = new KeyBucket(element.getKey());
                buckets.put(element.getKey(), bucket);
            }

            bucket.addElement(element);
        }
    }

    @Override
    public List<DBStorageElement> read(String key) {

        KeyBucket bucket = buckets.get(key);
        if (bucket == null)
            return null;

        return bucket.getElements();
    }

    @Override
    public DBStorageElement read(String key, DateTime timestamp) {
        KeyBucket bucket = buckets.get(key);
        if (bucket == null)
            return null;

        return bucket.getElement(timestamp);
    }

    @Override
    public boolean existsAny(String key) {
        return buckets.containsKey(key);
    }

    @Override
    public boolean exists(String key, DateTime timestamp) {
        KeyBucket bucket = buckets.get(key);
        if (bucket == null)
            return false;

        return bucket.contains(timestamp);
    }

    @Override
    public boolean remove(String key) {
        return (buckets.remove(key) != null);
    }

    @Override
    public void clear() {
        buckets.clear();
    }
}
