package com.gschw.ljwc.storage;

import org.joda.time.DateTime;

import java.util.List;

/**
 * A simple interface for a DB.
 */
public interface IDBStorage {
    /**
     * Writes an element to the DB.
     * @param element The element to write to the DB.
     * @return true if element was written; otherwise, false.
     */
    boolean write(DBStorageElement element);

    /**
     * Writes elements to the DB.
     * @param elements The collection of elements to write to the DB.
     * @return true if at least one of elemtns was written; otherwise, false.
     */
    boolean write(List<DBStorageElement> elements);

    /**
     * Reads all elements from the DB.
     * @return A collection of elements.
     */
    List<DBStorageElement> read();

    /**
     * Reads elements with the given key.
     * @param key A key.
     * @return A collection of elements with the given key.
     */
    List<DBStorageElement> read(String key);

    /**
     * Reads an element with the given key and the given timestamp.
     * @param key A key.
     * @param timestamp A timestamp.
     * @return An element if one exists; null, otherwise.
     */
    DBStorageElement read(String key, DateTime timestamp);
    DBStorageElement readLast(String key);

    boolean exists(String key);
    boolean exists(String key, DateTime timestamp);

    boolean remove(String key);
    boolean remove(String key, DateTime timestamp);

    boolean clear();
}
