package com.gschw.ljwc.lj.ljscheduler.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;

import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
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
                            .path("/taskgenerator")
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
        return false;
    }
}
