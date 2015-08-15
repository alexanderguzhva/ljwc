package com.gschw.ljwc.lj.ljscheduler.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 8/15/15.
 */
public class LJCalendarTaskClient implements ILJCalendarTaskClient {
    private static Logger logger = LoggerFactory.getLogger(LJCalendarTaskClient.class);

    private Client client;

    private LJCalendarTaskClientParameters parameters;

    public LJCalendarTaskClient(Client client, LJCalendarTaskClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    @Override
    public LJCalendarTask acquireTask(Identity clientIdentity) {
        ////
        String url = parameters.getServiceUrl();

        try {
            Response response =
                    client
                            .target(url)
                            .path("/calendargenerator")
                            .queryParam("clientIdentity", clientIdentity)
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildGet()
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            LJCalendarTask task = response.readEntity(LJCalendarTask.class);
            return task;

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public boolean complete(LJCalendarTaskResult result) {
        ////
        String url = parameters.getServiceUrl();

        try {
            Response response =
                    client
                            .target(url)
                            .path("/calendarurls")
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildPost(Entity.entity(result, MediaType.APPLICATION_JSON_TYPE))
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
    public boolean download(LJCalendarTask task, long timeoutInMsec) {
        return download(task, new Long(timeoutInMsec));
    }

    @Override
    public boolean download(LJCalendarTask task) {
        return download(task, null);
    }

    private boolean download(LJCalendarTask task, Long timeoutInMsec) {
        String url = parameters.getServiceUrl();

        try {
            WebTarget target = client
                            .target(url)
                            .path("/downloadcalendar");
            if (timeoutInMsec != null)
                target = target.queryParam("timeout", timeoutInMsec);

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
