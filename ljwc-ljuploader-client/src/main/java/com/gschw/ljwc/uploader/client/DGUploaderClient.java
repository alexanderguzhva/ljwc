package com.gschw.ljwc.uploader.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.uploader.api.DGUploadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
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
            WebTarget target = client
                            .target(url)
                            .path("/");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = null;
            try {
                response = target
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .buildPost(Entity.entity(uploadTask, MediaType.APPLICATION_JSON_TYPE))
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
