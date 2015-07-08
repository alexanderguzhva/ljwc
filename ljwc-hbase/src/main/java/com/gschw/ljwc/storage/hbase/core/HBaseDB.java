package com.gschw.ljwc.storage.hbase.core;

import java.util.List;
import java.util.ArrayList;

import com.google.common.base.Throwables;
import com.gschw.ljwc.storage.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by nop on 6/24/15.
 */
public class HBaseDB implements IAbstractStorage {
    private static Logger logger = LoggerFactory.getLogger(HBaseDB.class);

    private HBaseDBSettings settings;

    private Client client;

    //
    public HBaseDB(HBaseDBSettings settings, Client client) {
        this.settings = settings;
        this.client = client;
    }

    //
    public StorageWriteOperationResult write(StorageElementCollection elements) {
        //// process
        CellSet cellSet = new CellSet();

        return null;
    }


    //
    public StorageReadOperationResult read(byte[] key) {
        String uri = new String(key);

        String normalizedKeyName;
        try {
            normalizedKeyName = URLEncoder.encode(uri, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return StorageReadOperationResult.createFailed();
        }

        ////
        HBaseConnectionSettings connectionSettings = settings.getConnectionSettings();

        ////
        Response response = client.target(connectionSettings.getRESTServiceURI())
                .path(String.format("/%s/%s", connectionSettings.getTableName(), normalizedKeyName))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildGet()
                .invoke();

        CellSet cellSet = response.readEntity(CellSet.class);

        ////
        List<StorageElement> elements = new ArrayList<>();
        for (Row row : cellSet.getRows()) {
            StorageElement element = new StorageElement(row.getKey());

            for (Cell cell : row.getCells()) {
                String columnName = new String(cell.getColumn());

                ////
                hBaseDataElement.setData(cell.getValue());
                element.setTimestamp(new DateTime(cell.getTimestamp()));
            }

            elements.add(element);
        }

        ////
        StorageElementCollection collection = new StorageElementCollection(elements);
        return new StorageReadOperationResult(key, collection);
    }


}
