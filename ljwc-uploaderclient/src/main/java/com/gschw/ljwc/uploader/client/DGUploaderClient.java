package com.gschw.ljwc.uploader.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.uploader.api.DGUploadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;

/**
 * Created by nop on 7/12/15.
 */
public class DGUploaderClient implements IDGUploaderClient {
    private static Logger logger = LoggerFactory.getLogger(DGUploaderClient.class);

    private Client client;

    private DGUploaderClientParameters parameters;

    public DGUploaderClient(Client client, DGUploaderClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    @Override
    public boolean upload(String uploadServiceURL, DGUploadTask uploadTask) {
        if (uploadServiceURL == null || uploadServiceURL.isEmpty())
            return false;

        ////
        String url = uploadServiceURL;

        try {
            Response response =
                    client
                            .target(url)
                            .path("/")
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .buildPost(Entity.entity(uploadTask, MediaType.APPLICATION_JSON_TYPE))
                            .invoke();

            logger.info("{} returned {}", url, response.getStatusInfo());
            return (response.getStatus() == Response.Status.OK.getStatusCode());

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }
}
