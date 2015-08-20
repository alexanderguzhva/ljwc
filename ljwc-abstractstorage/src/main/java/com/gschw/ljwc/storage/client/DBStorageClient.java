package com.gschw.ljwc.storage.client;

import com.gschw.ljwc.storage.DBStorageElement;
import com.gschw.ljwc.storage.DBStorageElementsCollection;
import com.gschw.ljwc.storage.IDBStorage;
import org.glassfish.jersey.uri.UriComponent;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nop on 7/10/15.
 */
public class DBStorageClient implements IDBStorage {
    private static Logger logger = LoggerFactory.getLogger(DBStorageClient.class);

    private DBStorageClientParameters parameters;

    private Client client;

    public DBStorageClient(Client client, DBStorageClientParameters parameters) {
        this.parameters = parameters;
        this.client = client;
    }

    @Override
    public boolean write(DBStorageElement element) {
        if (element == null)
            return false;

        ////
        List<DBStorageElement> elements = new ArrayList<>();
        elements.add(element);

        return write(elements);
    }

    @Override
    public boolean write(List<DBStorageElement> elements) {
        if (elements == null)
            return false;

        ////
        String url = parameters.getServiceUrl();

        DBStorageElementsCollection elementsCollection = new DBStorageElementsCollection(elements);

        try {
            Response response =
                    client
                            .target(url)
                            .path("/")
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildPost(Entity.entity(elementsCollection, MediaType.APPLICATION_JSON_TYPE))
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            return (response.getStatus() == Response.Status.OK.getStatusCode());

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }

    }

    @Override
    public List<DBStorageElement> read() {
        ////
        String url = parameters.getServiceUrl();

        try {
            Response response =
                    client
                            .target(url)
                            .path("/")
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildGet()
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            ////
            DBStorageElementsCollection elements = response.readEntity(DBStorageElementsCollection.class);
            if (elements == null)
                return null;

            return elements.getElements();

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }



    @Override
    public List<DBStorageElement> read(String key) {
        if (key == null || key.isEmpty())
            return null;

        ////
        String url = parameters.getServiceUrl();
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        try {
            WebTarget target =
                    client
                            .target(url)
                            .path(encodedElementUrl)
                            .path("element");

            Response response = target
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .buildGet()
                    .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            ////
            DBStorageElementsCollection elements = response.readEntity(DBStorageElementsCollection.class);
            if (elements == null)
                return null;

            return elements.getElements();

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public DBStorageElement read(String key, DateTime timestamp) {
        if (key == null || key.isEmpty())
            return null;

        ////
        if (timestamp == null)
            return null;

        ////
        String url = parameters.getServiceUrl();
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        try {
            WebTarget target =
                    client
                            .target(url)
                            .path(encodedElementUrl)
                            .path("element");
            if (timestamp != null)
                target = target.queryParam("timestamp", new Long(timestamp.getMillis()).toString());

            Response response = target
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .buildGet()
                    .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            ////
            DBStorageElement element = response.readEntity(DBStorageElement.class);
            return element;

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }


    @Override
    public DBStorageElement readLast(String key) {
        if (key == null || key.isEmpty())
            return null;

        ////
        if (key == null || key.isEmpty())
            return null;

        ////
        String url = parameters.getServiceUrl();
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        try {
            Response response =
                    client
                            .target(url)
                            .path(encodedElementUrl)
                            .path("lastelement")
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildGet()
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            ////
            DBStorageElement element = response.readEntity(DBStorageElement.class);
            return element;

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }


    @Override
    public boolean exists(String key) {
        if (key == null || key.isEmpty())
            return false;

        ////
        String url = parameters.getServiceUrl();
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        try {
            Response response =
                    client
                            .target(url)
                            .path(encodedElementUrl)
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildGet()
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            return (response.getStatus() == Response.Status.OK.getStatusCode());

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    @Override
    public boolean exists(String key, DateTime timestamp) {
        if (key == null || key.isEmpty())
            return false;
        if (timestamp == null)
            return false;

        ////
        String url = parameters.getServiceUrl();
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        String sTimestamp = new Long(timestamp.getMillis()).toString();

        try {
            Response response =
                    client
                            .target(url)
                            .path(encodedElementUrl)
                            .queryParam("timestamp", sTimestamp)
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildGet()
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            return (response.getStatus() == Response.Status.OK.getStatusCode());

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }

    }

    @Override
    public boolean remove(String key) {
        if (key == null || key.isEmpty())
            return false;

        ////
        String url = parameters.getServiceUrl();
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        try {
            Response response =
                    client
                        .target(url)
                        .path(encodedElementUrl)
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildDelete()
                        .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            return (response.getStatus() == Response.Status.OK.getStatusCode());

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }


    @Override
    public boolean remove(String key, DateTime timestamp) {
        if (key == null || key.isEmpty())
            return false;

        if (timestamp == null)
            return false;

        ////
        String url = parameters.getServiceUrl();
        String encodedElementUrl = UriComponent.encode(key, UriComponent.Type.UNRESERVED);

        String sTimestamp = new Long(timestamp.getMillis()).toString();

        try {
            Response response =
                    client
                            .target(url)
                            .path(encodedElementUrl)
                            .queryParam("timestamp", sTimestamp)
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildDelete()
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            return (response.getStatus() == Response.Status.OK.getStatusCode());

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    @Override
    public boolean clear() {
        String url = parameters.getServiceUrl();

        try {
            Response response =
                    client
                        .target(url)
                        .path("/")
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildDelete()
                        .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            return (response.getStatus() == Response.Status.OK.getStatusCode());

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }
}
