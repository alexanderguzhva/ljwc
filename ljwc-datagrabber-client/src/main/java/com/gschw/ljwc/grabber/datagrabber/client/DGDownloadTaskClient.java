package com.gschw.ljwc.grabber.datagrabber.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadRawResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadRawTask;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
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

        try {
            Response response =
                    client
                            .target(url)
                            .path(String.format("/session/%s/download", sessionIdentity.toString()))
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildPost(Entity.entity(dgDownloadTask, MediaType.APPLICATION_JSON_TYPE))
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            return response.readEntity(DGDownloadResult.class);

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public DGDownloadRawResult downloadRaw(Identity sessionIdentity, DGDownloadRawTask dgDownloadRawTask) {
        if (sessionIdentity == null || dgDownloadRawTask == null)
            return null;

        ////
        String url = parameters.getServiceUrl();

        try {
            Response response =
                    client
                            .target(url)
                            .path(String.format("/session/%s/downloadRaw", sessionIdentity.toString()))
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildPost(Entity.entity(dgDownloadRawTask, MediaType.APPLICATION_JSON_TYPE))
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            return response.readEntity(DGDownloadRawResult.class);

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
            Response response =
                    client
                            .target(url)
                            .path("/sessiongenerator")
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildGet()
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            return response.readEntity(Identity.class);

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

        try {
            Response response =
                    client
                            .target(url)
                            .path(String.format("/session/%s", sessionIdentity.toString()))
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
