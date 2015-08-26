package com.gschw.ljwc.storage.hbasebridge.core;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.google.common.base.Throwables;
import com.gschw.ljwc.storage.DBStorageElement;
import com.gschw.ljwc.storage.IDBStorage;
import org.glassfish.jersey.uri.UriComponent;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;

import java.util.Map;

/**
 * Created by nop on 6/24/15.
 */
public class HBaseDB implements IDBStorage {
    private static Logger logger = LoggerFactory.getLogger(HBaseDB.class);

    private HBaseDBSettings settings;

    private Client client;

    //
    public HBaseDB(Client client, HBaseDBSettings settings) {
        this.settings = settings;
        this.client = client;
    }



    @Override
    public boolean write(DBStorageElement element) {
        List<DBStorageElement> elements = new ArrayList<>();
        elements.add(element);

        return write(element);
    }

    @Override
    public boolean write(List<DBStorageElement> elements) {
        if (elements == null || elements.size() == 0)
            return true;

        //// process
        CellSet cellSet = new CellSet();

        for (DBStorageElement element : elements) {
            Row row = new Row(element.getKey());

            //// add data
            for (Map.Entry<String, byte[]> entry : element.getData().entrySet()) {
                String cellName = String.format("d:%s", entry.getKey());
                Cell cell = new Cell(cellName.getBytes(),
                        element.getTimestamp().getMillis(),
                        entry.getValue());

                row.addCell(cell);
            }

            //// add meta
            for (Map.Entry<String, byte[]> entry : element.getMeta().entrySet()) {
                String cellName = String.format("m:%s", entry.getKey());
                Cell cell = new Cell(cellName.getBytes(),
                        element.getTimestamp().getMillis(),
                        entry.getValue());

                row.addCell(cell);
            }

            ////
            cellSet.addRow(row);
        }

        ////
        HBaseConnectionSettings connectionSettings = settings.getConnectionSettings();

        ////
        try {
            WebTarget target = client
                            .target(connectionSettings.getServiceUrl())
                            .path(connectionSettings.getTableName())
                            .path("fake");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildPost(Entity.entity(cellSet, MediaType.APPLICATION_JSON_TYPE))
                        .invoke();

                logger.debug("hbase returned {}", response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return false;

                return true;
            } finally {
                if (response != null)
                    response.close();
            }
        } catch(Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }


    //
    private static List<DBStorageElement> fromRow(Row row) {
        String rowName = new String(row.getKey());

        Map<Long, DBStorageElement> elements =
                new HashMap<>();
        //List<DBStorageElement> elements = new Ar
        //        new DBStorageElement(name);

        for (Cell cell : row.getCells()) {
            String columnName = new String(cell.getColumn());

            String[] sSplit = columnName.split(":");
            if (sSplit.length != 2) {
                logger.warn("Strange column name {}, skipping it", columnName);
                continue;
            }

            if (sSplit[0].equals("d")) {
                Long ts = new Long(cell.getTimestamp());
                DBStorageElement element = elements.get(ts);
                if (element == null) {
                    element = new DBStorageElement(rowName);
                    element.setTimestamp(new DateTime(cell.getTimestamp()));
                    elements.put(ts, element);
                }

                element.getData().put(sSplit[1], cell.getValue());
            } else if (sSplit[0].equals("m")) {
                Long ts = new Long(cell.getTimestamp());
                DBStorageElement element = elements.get(ts);
                if (element == null) {
                    element = new DBStorageElement(rowName);
                    element.setTimestamp(new DateTime(cell.getTimestamp()));
                    elements.put(ts, element);
                }

                element.getMeta().put(sSplit[1], cell.getValue());
            } else {
                logger.warn("Strange column name {}, skipping it", columnName);
                continue;
            }
        }

        return new ArrayList<>(elements.values());
    }



    @Override
    public List<DBStorageElement> read() {
        return null;
    }

    @Override
    public List<DBStorageElement> read(String key) {
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        HBaseConnectionSettings connectionSettings = settings.getConnectionSettings();

        //// todo: valid requests
        ////  curl -X GET http://localhost:60001/ljdb/1.txt//?v=4
        ////  curl -X GET http://localhost:60001/ljdb/1.txt/d:d,d:m/0,9999999999999?v=10
        ////  curl -X GET http://localhost:60001/ljdb/1.txt//0,9999999999999?v=10
        ////    reply: ><CellSet><Row key="MS50eHQ="><Cell column="ZDpk" timestamp="1440473677207">cXFx</Cell><Cell column="ZDpk" timestamp="1440473543471">cXVhY2s=</Cell><Cell column="ZDpk" timestamp="1440473535021">d29vZg==</Cell></Row></CellSet>
        ////  so, fromRow() needs to be changed
        try {
            WebTarget target = client
                            .target(connectionSettings.getServiceUrl())
                            .path(connectionSettings.getTableName())
                            .path(encodedElementUrl);

            logger.debug("Calling {}", target.getUri().toString());

            CellSet cellSet = null;
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildGet()
                        .invoke();

                logger.debug("hbase returned {}", response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return null;

                //
                cellSet = response.readEntity(CellSet.class);
            } finally {
                if (response != null)
                    response.close();
            }

            ////
            if (cellSet == null)
                return null;

            ////
            List<DBStorageElement> elements = new ArrayList<>();
            for (Row row : cellSet.getRows()) {
                elements.addAll(fromRow(row));
            }
            return elements;

        } catch(Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public DBStorageElement read(String key, DateTime timestamp) {
        return null;
    }

    @Override
    public DBStorageElement readLast(String key) {
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        HBaseConnectionSettings connectionSettings = settings.getConnectionSettings();

        try {
            WebTarget target = client
                    .target(connectionSettings.getServiceUrl())
                    .path(connectionSettings.getTableName())
                    .path(encodedElementUrl);
            logger.debug("Calling {}", target.getUri().toString());

            CellSet cellSet = null;
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildGet()
                        .invoke();

                logger.debug("hbase returned {}", response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return null;

                //
                cellSet = response.readEntity(CellSet.class);
            } finally {
                if (response != null)
                    response.close();
            }


            ////
            if (cellSet == null)
                return null;

            ////
            if (cellSet.getRows().size() == 0)
                return null;

            if (cellSet.getRows().size() > 1) {
                logger.warn("Strange, too many rows ({}), expected 1", cellSet.getRows().size());
            }

            //// todo: valid requests
            ////  curl -X GET http://localhost:60001/ljdb/1.txt//?v=4
            ////  curl -X GET http://localhost:60001/ljdb/1.txt/d:d,d:m/0,9999999999999?v=10
            ////  curl -X GET http://localhost:60001/ljdb/1.txt//0,9999999999999?v=10
            ////    reply: ><CellSet><Row key="MS50eHQ="><Cell column="ZDpk" timestamp="1440473677207">cXFx</Cell><Cell column="ZDpk" timestamp="1440473543471">cXVhY2s=</Cell><Cell column="ZDpk" timestamp="1440473535021">d29vZg==</Cell></Row></CellSet>
            ////  so, fromRow() needs to be changed
            List<DBStorageElement> elements = fromRow(cellSet.getRows().get(0));
            if (elements.size() == 0)
                return null;
            if (elements.size() > 1) {
                logger.warn("Strange, fromRow returned too many rows ({}), expected 1", elements.size());
            }

            return elements.get(0);

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public boolean exists(String key) {
        return false;
    }

    @Override
    public boolean exists(String key, DateTime timestamp) {
        return false;
    }

    @Override
    public boolean remove(String key) {
        return false;
    }

    @Override
    public boolean remove(String key, DateTime timestamp) {
        return false;
    }

    @Override
    public boolean clear() {
        return false;
    }
}
