package com.gschw.ljwc.lj.ljreader.client;

import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import org.glassfish.jersey.uri.UriComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by nop on 8/22/15.
 */
public class SimpleDownloader {
    private static Logger logger = LoggerFactory.getLogger(SimpleDownloader.class);

    private Client client;

    private SimpleDownloaderParameters parameters;

    public SimpleDownloader(Client client, SimpleDownloaderParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    public byte[] download(String url) {
        try {
            String encodedElementUrl = UriComponent.encode(url, UriComponent.Type.UNRESERVED);

            WebTarget target = client
                    .target(parameters.getServiceUrl())
                    .path(encodedElementUrl);
            Response response = target.request().get();

            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            ////
            try (InputStream inputStream = response.readEntity(InputStream.class)) {
                return ByteStreams.toByteArray(inputStream);
            }

        } catch(Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
