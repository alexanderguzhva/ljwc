package com.gschw.ljwc.grabber.datagrabber.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import org.glassfish.jersey.uri.UriComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 7/12/15.
 */
public class DGDownloadTaskClient implements IDGDownloadTaskClient {
    private static Logger logger = LoggerFactory.getLogger(DGDownloadTaskClient.class);

    private Client client;

    private DGDownloadTaskClientParameters parameters;

    public DGDownloadTaskClient(Client client, DGDownloadTaskClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    @Override
    public DGDownloadResult download(Identity sessionIdentity, DGDownloadTask dgDownloadTask) {
        if (sessionIdentity == null || dgDownloadTask == null)
            return null;

        ////
        String url = parameters.getServiceUrl();

        String encodedElementUrl = UriComponent.encode(sessionIdentity.toString(), UriComponent.Type.UNRESERVED);

        try {
            WebTarget target = client
                            .target(url)
                            .path("session")
                            .path(encodedElementUrl)
                            .path("download");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildPost(Entity.entity(dgDownloadTask, MediaType.APPLICATION_JSON_TYPE))
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return null;

                return response.readEntity(DGDownloadResult.class);
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
    public Identity createSession() {
        ////
        String url = parameters.getServiceUrl();

        try {
            WebTarget target = client
                            .target(url)
                            .path("/sessiongenerator");

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

                return response.readEntity(Identity.class);
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
    public boolean deleteSession(Identity sessionIdentity) {
        if (sessionIdentity == null)
            return false;

        ////
        String url = parameters.getServiceUrl();

        String encodedElementUrl = UriComponent.encode(sessionIdentity.toString(), UriComponent.Type.UNRESERVED);

        try {
            WebTarget target = client
                            .target(url)
                            .path("session")
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

}
