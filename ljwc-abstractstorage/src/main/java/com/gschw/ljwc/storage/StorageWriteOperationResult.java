package com.gschw.ljwc.storage;

/**
 * Created by nop on 6/22/15.
 */
public class StorageWriteOperationResult {
    private boolean success;

    private StorageWriteOperationResult(boolean success) {
        this.success = success;
    }

    public static StorageWriteOperationResult createFailed() {
        return new StorageWriteOperationResult(false);
    }

    public static StorageWriteOperationResult createSuccess() {
        return new StorageWriteOperationResult(true);
    }

}
