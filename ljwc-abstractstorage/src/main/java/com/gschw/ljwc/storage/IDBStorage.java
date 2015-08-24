package com.gschw.ljwc.storage;

import org.joda.time.DateTime;

import java.util.List;

/**
 * A simple interface for a DB. DB consists of {@link DBStorageElement} elements.
 */
public interface IDBStorage {
    /**
     * Writes an element to the DB.
     *
     * @param element  The element to write to the DB, not null.
     * @return true if element was written; otherwise, false.
     */
    boolean write(DBStorageElement element);

    /**
     * Writes elements to the DB.
     *
     * @param elements  The collection of elements to write to the DB, not null.
     * @return true if at least one of elemtns was written; otherwise, false.
     */
    boolean write(List<DBStorageElement> elements);

    /**
     * Reads all elements from the DB.
     *
     * @return A collection of elements.
     */
    List<DBStorageElement> read();

    /**
     * Reads elements with the given key.
     *
     * @param key  A key, not null.
     * @return A collection of elements with the given key.
     */
    List<DBStorageElement> read(String key);

    /**
     * Reads an element with the given key and the given timestamp.
     *
     * @param key  A key, not null.
     * @param timestamp A timestamp.
     * @return An element if one exists; null, otherwise. Also, a null is returned if the key is not found.
     */
    DBStorageElement read(String key, DateTime timestamp);

    /**
     * Reads an element with the given key and the latest datetime.
     *
     * @param key  A key, not null.
     * @return An element if one exists; null, otherwise. Also, a null is returned if the key is not found.
     */
    DBStorageElement readLast(String key);

    /**
     * Checks whether the DB contains elements with the given key.
     *
     * @param key  A key to check, not null.
     * @return true if any exist; false, otherwise.
     */
    boolean exists(String key);

    /**
     * Checks whether the DB contains an element with the given key and timestamp.
     *
     * @param key  A key to check, not null.
     * @param timestamp  A timestamp to check, not null.
     * @return true if one exists; false, otherwise.
     */
    boolean exists(String key, DateTime timestamp);

    /**
     * Removes all elements with the given key.
     *
     * @param key  A key to remove. not null.
     * @return true if the key was removed; false, otherwise.
     */
    boolean remove(String key);

    /**
     * Removed an element with the given key and timestamp.
     *
     * @param key  A key to remove, not null.
     * @param timestamp  A timestamp to remove, not null.
     * @return true if the element was removed; false, otherwise.
     */
    boolean remove(String key, DateTime timestamp);

    /**
     * Removes all elements from the DB.
     *
     * @return true if DB was successfully cleared; false, otherwise.
     */
    boolean clear();
}
