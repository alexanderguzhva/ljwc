package com.gschw.ljwc.hbaselib.core;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


import com.gschw.ljwc.hbaselib.core.*;
import com.google.common.base.Throwables;
import org.glassfish.jersey.internal.util.Base64;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nop on 5/5/15.
 */

public class HBaseReader {
    private static Logger logger = LoggerFactory.getLogger(HBaseReader.class);

    private Client client;

    private final HBaseSettings settings;

    //
    public HBaseReader(Client client, HBaseSettings settings) {
        this.client = client;
        this.settings = settings;
    }

    //
    public List<HBaseDataElement> read(byte[] key) {
        String uri = new String(key);

        String normalizedKeyName;
        try {
            normalizedKeyName = URLEncoder.encode(uri, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }

        ////
        Response response = client.target(settings.getRESTServiceURI())
                .path(String.format("/%s/%s", settings.getTableName(), normalizedKeyName))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .buildGet()
                .invoke();

        CellSet cellSet = response.readEntity(CellSet.class);

        ////
        ArrayList<HBaseDataElement> elements = new ArrayList<>();

        ////
        for (Row row : cellSet.getRows()) {
            HBaseDataElement hBaseDataElement = new HBaseDataElement();
            hBaseDataElement.setKey(row.getKey());
            for (Cell cell : row.getCells()) {
                String columnName = new String(cell.getColumn());
                if (!(settings.getColFNName()).equals(columnName))
                    continue;

                ////
                hBaseDataElement.setData(cell.getValue());
                hBaseDataElement.setTimestamp(new DateTime(cell.getTimestamp()));
            }

            elements.add(hBaseDataElement);
        }

        return elements;
    }
}
