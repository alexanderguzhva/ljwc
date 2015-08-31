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

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard remote REST-based implementation for {@link IDBStorage}.
 * This class expects to talk to a REST server that implements {@link IDBStorageResource}.
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
            WebTarget target = client
                            .target(url)
                            .path("/");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildPost(Entity.entity(elementsCollection, MediaType.APPLICATION_JSON_TYPE))
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                return (response.getStatus() == Response.Status.OK.getStatusCode());
            } finally {
                if (response != null)
                    response.close();
            }
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
            WebTarget target = client
                    .target(url)
                    .path("/");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildGet()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return null;

                ////
                DBStorageElementsCollection elements = response.readEntity(DBStorageElementsCollection.class);
                if (elements == null)
                    return null;

                return elements.getElements();
            } finally {
                if (response != null)
                    response.close();
            }
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

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildGet()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return null;

                ////
                DBStorageElementsCollection elements = response.readEntity(DBStorageElementsCollection.class);
                if (elements == null)
                    return null;

                return elements.getElements();
            } finally {
                if (response != null)
                    response.close();
            }
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
            WebTarget target = client
                            .target(url)
                            .path(encodedElementUrl)
                            .path("element");
            if (timestamp != null)
                target = target.queryParam("timestamp", new Long(timestamp.getMillis()).toString());

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildGet()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return null;

                ////
                DBStorageElement element = response.readEntity(DBStorageElement.class);
                return element;
            } finally {
                if (response != null)
                    response.close();
            }
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
            WebTarget target = client
                            .target(url)
                            .path(encodedElementUrl)
                            .path("lastelement");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildGet()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return null;

                ////
                DBStorageElement element = response.readEntity(DBStorageElement.class);
                return element;
            } finally {
                if (response != null)
                    response.close();
            }
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
            WebTarget target = client
                    .target(url)
                    .path(encodedElementUrl);

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildGet()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                return (response.getStatus() == Response.Status.OK.getStatusCode());
            } finally {
                if (response != null)
                    response.close();
            }
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
            WebTarget target = client
                            .target(url)
                            .path(encodedElementUrl)
                            .queryParam("timestamp", sTimestamp);

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildGet()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                return (response.getStatus() == Response.Status.OK.getStatusCode());
            } finally {
                if (response != null)
                    response.close();
            }
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
            WebTarget target = client
                            .target(url)
                            .path(encodedElementUrl);

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildDelete()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                return (response.getStatus() == Response.Status.OK.getStatusCode());
            } finally {
                if (response != null)
                    response.close();
            }
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
            WebTarget target = client
                            .target(url)
                            .path(encodedElementUrl)
                            .queryParam("timestamp", sTimestamp);

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildDelete()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                return (response.getStatus() == Response.Status.OK.getStatusCode());
            } finally {
                if (response != null)
                    response.close();
            }
        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    @Override
    public boolean clear() {
        String url = parameters.getServiceUrl();

        try {
            WebTarget target = client
                            .target(url)
                            .path("/");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildDelete()
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                return (response.getStatus() == Response.Status.OK.getStatusCode());
            } finally {
                if (response != null)
                    response.close();
            }
        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }
}
