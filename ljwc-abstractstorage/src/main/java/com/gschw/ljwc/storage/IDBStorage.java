package com.gschw.ljwc.storage;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by hadoop on 7/9/15.
 */
public interface IDBStorage {
    boolean write(DBStorageElement element);
    boolean write(List<DBStorageElement> elements);

    List<DBStorageElement> read(String key);
    DBStorageElement read(String key, DateTime timestamp);

    boolean exists(String key);
    boolean exists(String key, DateTime timestamp);

    boolean remove(String key);
    boolean remove(String key, DateTime timestamp);

    boolean clear();
}
