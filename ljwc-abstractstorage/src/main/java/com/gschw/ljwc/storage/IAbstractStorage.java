package com.gschw.ljwc.storage;

import java.util.List;
import com.gschw.ljwc.storage.StorageElementCollection;
import com.gschw.ljwc.storage.StorageElement;
import com.gschw.ljwc.storage.StorageReadOperationResult;
import com.gschw.ljwc.storage.StorageWriteOperationResult;

/**
 * Created by nop on 6/22/15.
 */
public interface IAbstractStorage {
    StorageWriteOperationResult write(StorageElementCollection elements);
    StorageReadOperationResult read(byte[] key);
}
