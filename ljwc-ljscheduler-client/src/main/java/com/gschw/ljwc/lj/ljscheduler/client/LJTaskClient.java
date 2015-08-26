package com.gschw.ljwc.lj.ljscheduler.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hadoop on 8/21/15.
 */
public abstract class LJTaskClient<T, U> implements ILJTaskClient<T, U>{
    private static Logger logger = LoggerFactory.getLogger(LJTaskClient.class);

    private Client client;

    private LJTaskClientParameters parameters;

    public LJTaskClient(Client client, LJTaskClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }



    @Override
    public abstract T acquireTask(Identity clientIdentity);

    protected T acquireTask(Identity clientIdentity, Class<T> tClass) {
        ////
        String url = parameters.getServiceUrl();

        try {
            WebTarget target = client
                            .target(url)
                            .path("/generator")
                            .queryParam("clientIdentity", clientIdentity);

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

                T task = response.readEntity(tClass);
                return task;
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
    public boolean complete(U result) {
        ////
        String url = parameters.getServiceUrl();

        try {

            WebTarget target = client
                            .target(url)
                            .path("/urls");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildPost(Entity.entity(result, MediaType.APPLICATION_JSON_TYPE))
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return false;

                return true;
            } finally {
                if (response != null)
                    response.close();
            }
        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    private U download(T task, Long timeoutInMsec, Class<U> uClass) {
        String url = parameters.getServiceUrl();

        try {
            WebTarget target = client
                    .target(url)
                    .path("/download");
            if (timeoutInMsec != null)
                target = target.queryParam("timeout", timeoutInMsec);

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildPost(Entity.entity(task, MediaType.APPLICATION_JSON_TYPE))
                        .invoke();

                logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
                if (response.getStatus() != Response.Status.OK.getStatusCode())
                    return null;

                U result = response.readEntity(uClass);
                return result;
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
    public abstract U download(T task, long timeoutInMsec);

    @Override
    public abstract U download(T task);

    protected U download(T task, long timeoutInMsec, Class<U> uClass) {
        return download(task, new Long(timeoutInMsec), uClass);
    }

    protected U download(T task, Class<U> uClass) {
        return download(task, null, uClass);
    }
}
