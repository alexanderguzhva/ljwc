package com.gschw.ljwc.storage.memorydb.core;

import com.gschw.ljwc.storage.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by nop on 6/24/15.
 */
public class MemoryDB implements IAbstractStorage {

    private final Map<byte[], KeyBucket> buckets;

    private MemoryDBSettings settings;

    public MemoryDB(MemoryDBSettings settings) {
        this.settings = settings;

        buckets = new HashMap<>();
    }

    //
    public StorageWriteOperationResult write(StorageElementCollection elements) {
        if (elements == null)
            return StorageWriteOperationResult.createSuccess();

        ////
        if (elements.getElements() == null ||
            elements.getElements().size() == 0) {
            return StorageWriteOperationResult.createSuccess();
        }

        ////
        for (StorageElement element : elements.getElements()) {
            KeyBucket bucket = buckets.get(element.getKey());
            if (bucket == null) {
                bucket = new KeyBucket(element.getKey());
                buckets.put(element.getKey(), bucket);
            }

            bucket.addElement(element);
        }

        ////
        return StorageWriteOperationResult.createSuccess();
    }

    //
    public StorageReadOperationResult read(byte[] key) {
        if (key == null)
            return StorageReadOperationResult.createFailed();

        ////
        KeyBucket bucket = buckets.get(key);
        if (key == null)
            return StorageReadOperationResult.createFailed();

        ////
        StorageElementCollection elements = new StorageElementCollection(bucket.getElements());
        return new StorageReadOperationResult(key, elements);
    }

}
