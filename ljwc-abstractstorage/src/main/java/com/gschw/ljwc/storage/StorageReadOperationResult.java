package com.gschw.ljwc.storage;

import java.util.List;
import com.gschw.ljwc.storage.StorageElement;
import com.gschw.ljwc.storage.StorageElementCollection;

/**
 * Created by nop on 6/22/15.
 */
public class StorageReadOperationResult {
    private byte[] key;

    private StorageElementCollection elements;

    private boolean success;

    private StorageReadOperationResult(boolean success) {
        this.success = success;
    }

    public StorageReadOperationResult(byte[] key, StorageElementCollection elements) {
        this.key = key;
        this.elements = elements;
        this.success = true;
    }

    //
    public static StorageReadOperationResult createFailed() {
        return new StorageReadOperationResult(false);
    }
}
