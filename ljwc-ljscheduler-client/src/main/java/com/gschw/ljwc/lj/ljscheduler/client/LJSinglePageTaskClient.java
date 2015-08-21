package com.gschw.ljwc.lj.ljscheduler.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hadoop on 8/21/15.
 */
public class LJSinglePageTaskClient implements ILJSinglePageTaskClient {
    private static Logger logger = LoggerFactory.getLogger(LJSinglePageTaskClient.class);

    private Client client;

    private LJSinglePageTaskClientParameters parameters;

    public LJSinglePageTaskClient(Client client, LJSinglePageTaskClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }


    @Override
    public LJSinglePageTask acquireTask(Identity clientIdentity) {
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

            LJSinglePageTask task = response.readEntity(LJSinglePageTask.class);
            return task;

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public boolean complete(LJSinglePageTaskResult result) {
        return false;
    }

    @Override
    public boolean download(LJSinglePageTask task, long timeoutInMsec) {
        return false;
    }

    @Override
    public boolean download(LJSinglePageTask task) {
        return false;
    }
}
