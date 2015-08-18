package com.gschw.ljwc.lj.ljscheduler.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;

import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.client.Client;

/**
 * Created by nop on 7/15/15.
 */
public class LJDownloadTaskClient implements ILJDownloadTaskClient {
    private static Logger logger = LoggerFactory.getLogger(LJDownloadTaskClient.class);

    private Client client;

    private LJDownloadTaskClientParameters parameters;

    public LJDownloadTaskClient(Client client, LJDownloadTaskClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    @Override
    public LJDownloadTask acquireTask(Identity clientIdentity) {
        ////
        String url = parameters.getServiceUrl();

        try {
            Response response =
                    client
                            .target(url)
                            .path("/generator")
                            .queryParam("clientIdentity", clientIdentity)
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildGet()
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            LJDownloadTask task = response.readEntity(LJDownloadTask.class);
            return task;

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public boolean completeElement(Identity elementIdentity, boolean success) {
        ////
        String url = parameters.getServiceUrl();

        try {
            Response response =
                    client
                            .target(url)
                            .path(String.format("/element/%s", elementIdentity))
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildPost(Entity.entity(new Boolean(success), MediaType.APPLICATION_JSON_TYPE))
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return false;

            return true;

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    @Override
    public boolean download(LJDownloadTask task, long timeoutInMsec) {
        return download(task, new Long(timeoutInMsec));
    }

    @Override
    public boolean download(LJDownloadTask task) {
        return download(task, null);
    }

    private boolean download(LJDownloadTask task, Long timeoutInMsec) {
        ////
        String url = parameters.getServiceUrl();

        try {
            WebTarget target = client
                            .target(url)
                            .path("/download");

            if (timeoutInMsec != null)
                target = target.queryParam("timeout");

            Response response = target
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildPost(Entity.entity(task, MediaType.APPLICATION_JSON_TYPE))
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return false;

            return true;

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }
}
