package com.gschw.ljwc.lj.dbwriter.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.grabber.datagrabber.api.*;
import com.gschw.ljwc.grabber.datagrabber.client.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Created by nop on 7/6/15.
 */
public class LJDBWriterClient implements IDataGrabberPushResultClient {
    private static Logger logger = LoggerFactory.getLogger(LJDBWriterClient.class);

    private Client client;

    private LJDBWriterClientParameters parameters;

    //
    public LJDBWriterClient(Client client, LJDBWriterClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    //
    public DataGrabberPushReply pushReply(DataGrabberIdentity identity, DataGrabberPushRequest request) {
        try {
            URI finalPath = UriBuilder.fromPath(parameters.getServiceURI()).path(identity.getUuid()).path("data").build();

            Invocation invocation = client.target(finalPath)
                    .request()
                    .buildPost(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

            Response response = invocation.invoke();

            //
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                return DataGrabberPushReply.createFailed();
            }

        } catch(Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return DataGrabberPushReply.createFailed();
        }

        return DataGrabberPushReply.createSucceeded();
    }

}
