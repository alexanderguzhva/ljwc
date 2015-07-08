package com.gschw.ljwc.hbaselib;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gschw.ljwc.hbaselib.core.*;


/**
 * Created by nop on 6/22/15.
 */
public class HBaseWriter {
    private static Logger logger = LoggerFactory.getLogger(HBaseWriter.class);

    private Client client;

    private final HBaseSettings settings;

    //
    public HBaseWriter(Client client, HBaseSettings settings) {
        this.client = client;
        this.settings = settings;
    }

    //
    public boolean write(HBaseDataElement element) {
        List<HBaseDataElement> elements = new ArrayList<>();
        elements.add(element);

        return write(elements);
    }

    //
    public boolean write(List<HBaseDataElement> elements) {
        if (elements == null || elements.size() == 0)
            return true;

        //// process
        CellSet cellSet = new CellSet();

        for (HBaseDataElement hBaseDataElement : elements) {
            Cell cell = new Cell(settings.getColFNName().getBytes(),
                    hBaseDataElement.getTimestamp().getMillis(),
                    hBaseDataElement.getData());

            //
            Row row = new Row(hBaseDataElement.getKey());
            row.addCell(cell);

            cellSet.addRow(row);
        }

        logger.info("Writing {} elements", cellSet.getRows().size());
        try {
            Invocation invocation = client.target(settings.getRESTServiceURI())
                    .path(String.format("/%s/fake", settings.getTableName()))
                    .request()
                    .buildPost(Entity.entity(cellSet, MediaType.APPLICATION_JSON_TYPE));

            Response response = invocation.invoke();
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return false;

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }

        return true;
    }

}
