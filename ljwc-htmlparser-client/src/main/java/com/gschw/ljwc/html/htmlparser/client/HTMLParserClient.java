package com.gschw.ljwc.html.htmlparser.client;

import com.gschw.ljwc.html.htmlparser.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by nop on 6/29/15.
 */

public class HTMLParserClient {
    private static Logger logger = LoggerFactory.getLogger(HTMLParserClient.class);

    private Client client;

    private URI serviceURI;

    public HTMLParserClient(Client client, URI serviceURI) {
        this.client = client;
        this.serviceURI = serviceURI;
    }

    public ElementsCollection parse(byte[] data) {
        logger.info("Going to parse {} bytes", data.length);

        HTMLParserData parserData = new HTMLParserData(data);

        WebTarget target = client.target(serviceURI);
        Response response =
                target.request()
                        .buildPost(Entity.entity(parserData, MediaType.APPLICATION_JSON_TYPE))
                        .invoke();

        if (response.getStatus() != Response.Status.OK.getStatusCode())
            return null;

        return response.readEntity(ElementsCollection.class);
    }

}
